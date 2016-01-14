package org.opendaylight.nemo.tool.eclipse.plugin.rest.actions;

import java.util.ArrayList;
import java.util.List;

import org.opendaylight.nemo.tool.eclipse.plugin.rest.actions.model.*;

/**
 * Created by hj on 10/29/15.
 */
public class NemoParserImpl2 implements INemoParser {
	private static final String BEGIN = "Transaction Begin";
	private static final String END = "Transaction ends";
	private static final String INTENT = "successfully";
	private String keepResult = "";
	private String errorInfo = "";

	private List<String> commands;
	private String targetIpv4;
	private String targetUrl = "language-style-nemo-request";
	private String baseUrl;
	private String TRANSACTION_BEGIN = "begin-transaction";
	private String TRANSACTION_END = "end-transaction";
	private String REGISTER_USER = "register-user";

	private User user;

	public NemoParserImpl2() {

		this.commands = new ArrayList<String>();
	}

	public String getErrorInfo() {
		return errorInfo;
	}

	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}

	public String getKeepResult() {
		return keepResult;
	}

	public void setKeepResult(String keepResult) {
		this.keepResult = keepResult;
	}

	@Override
	public void format(String content) {

		String[] comArray = content.split(";");
		for (String command : comArray) {
			String com = command.trim();
			if (!com.equals(""))
				commands.add(com);
		}
	}

	@Override
	public boolean findRest() {
		for (int i = 0; i < commands.size(); i++) {
			String command = commands.get(i);
			NemoType type = getType(command);
			if (type.equals(NemoType.ENGINE)) {
				targetIpv4 = NemoParserUtils.getIpv4(command);
				if (targetIpv4 != null) {

					baseUrl = "http://" + targetIpv4 + ":8181/restconf/operations/nemo-intent:";
					TRANSACTION_BEGIN = baseUrl + TRANSACTION_BEGIN;
					TRANSACTION_END = baseUrl + TRANSACTION_END;
					REGISTER_USER = baseUrl + REGISTER_USER;
					targetUrl = baseUrl + targetUrl;

					commands.remove(i);
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean findUser() {

		for (int i = 0; i < commands.size(); i++) {
			String command = commands.get(i);
			NemoType type = getType(command);
			if (type.equals(NemoType.USER)) {
				user = NemoParserUtils.getUser(command);
				if (user != null) {
					commands.remove(i);
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean send() {
		String register = NemoClient.send(REGISTER_USER, new NemoInput(user).toJsonFormatString());
		// String register2 = NemoClient.send("http://191.4.3.31:10081/hello",
		// new NemoInput(user,null).toJsonFormatString());

		String begin = NemoClient.send(TRANSACTION_BEGIN, new NemoInput(user, null).toJsonFormatString());

		for (String nemo : commands) {
			String result = handleOne(nemo);
			if (result.indexOf(INTENT) < 0) {
				errorInfo = result;

			} else {
				keepResult += ("\r\n" + result);
			}
		}

		// TODO:end
		String end = NemoClient.send(TRANSACTION_END, new NemoInput(user, null).toJsonFormatString());
		keepResult += ("\r\n" + end);

		return true;
	}

	private String handleOne(String command) {
		return NemoClient.send(targetUrl, new NemoInput2(user, command).toJsonFormatString());
	}

	private NemoType getType(String nemo) {
		if (nemo.startsWith("Engines:"))
			return NemoType.ENGINE;
		if (nemo.startsWith("IMPORT")) {
			return NemoType.CREATE_NODE;
		}
		if (nemo.startsWith("CREATE")) {
			return createType(nemo);
		}
		if (nemo.startsWith("UPDATE")) {
			return updateType(nemo);
		}
		if (nemo.startsWith("DELETE")) {
			return deleteType(nemo);
		}
		return NemoType.UNKNOWN;
	}

	private NemoType createType(String nemo) {
		nemo = nemo.replace("CREATE", "").trim();
		if (nemo.startsWith("Node")) {
			return NemoType.CREATE_NODE;
		}
		if (nemo.startsWith("Connection")) {
			return NemoType.CREATE_CONN;
		}
		if (nemo.startsWith("Operation")) {
			return NemoType.CREATE_OPERATION;
		}
		if (nemo.startsWith("Flow")) {
			return NemoType.CREATE_FLOW;
		}
		return NemoType.USER;
	}

	private NemoType updateType(String nemo) {
		nemo = nemo.replace("UPDATE", "").trim();
		if (nemo.startsWith("Node")) {
			return NemoType.UPDATE_NODE;
		}
		if (nemo.startsWith("Connection")) {
			return NemoType.UPDATE_CONN;
		}
		if (nemo.startsWith("Flow")) {
			return NemoType.UPDATE_FLOW;
		}
		return null;
	}

	private NemoType deleteType(String nemo) {
		nemo = nemo.replace("DELETE", "").trim();
		if (nemo.startsWith("Node")) {
			return NemoType.DELETE_NODE;
		}
		if (nemo.startsWith("Connection")) {
			return NemoType.DELETE_CONN;
		}
		return null;
	}

}
