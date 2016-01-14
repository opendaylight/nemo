package org.opendaylight.nemo.tool.eclipse.plugin.rest.actions;

import java.util.ArrayList;
import java.util.List;

import org.opendaylight.nemo.tool.eclipse.plugin.rest.actions.model.*;

/**
 * Created by hj on 10/29/15.
 */
public class NemoParserUtils {
	public static String getIpv4(String engine) {
		engine = engine.replace("Engines:", "").trim();
		engine = engine.replace(";", "").trim();
		String[] ipv4s = engine.split(",");
		String[] ipv4_sub = ipv4s[0].split("\\.");
		if (ipv4_sub.length == 4) {
			return ipv4_sub[0].trim() + "." + ipv4_sub[1].trim() + "."
					+ ipv4_sub[2].trim() + "." + ipv4_sub[3].trim();
		}
		return null;
	}

	public static User getUser(String user_create) {
		user_create = user_create.replace("CREATE", "").trim();
		String[] param = user_create.split(" ");
		if (param.length == 3) {
			User user = new User(param[0], param[1], param[2]);
			user.setUser_id(UUIDUtils.getUUId(user.getUser_name()));
			return user;
		}
		return null;
	}

	public static Node getNode(String node) {
		String nodeName = getNodeName(node);
		String nodeId = UUIDUtils.getUUId(nodeName);
		String nodeType = getType(node);
		List<String> contains = getContains(node);
		List<Property> properties = getProperties(node);
		return new Node(nodeId, nodeName, nodeType, properties, contains);
	}

	public static Connection getConnection(String connection) {
		String conName = getConnectionName(connection);
		String conId = UUIDUtils.getUUId(conName);
		String conType = getType(connection);
		List<String> endNodes = getEndNodes(connection);
		List<Property> properties = getProperties(connection);
		return new Connection(conId, conName, conType, endNodes, properties);
	}

	public static Flow getFlow(String flow) {
		String flowName = getFlowName(flow);
		String flowId = UUIDUtils.getUUId(flowName);
		List<Match> matches = getMatches(flow);
		return new Flow(flowId, flowName, matches);
	}

	public static Operation getOperation(String operation) {
		String opName = getOpName(operation);
		String opId = UUIDUtils.getUUId(opName);
		String targetObject = getTargetObj(operation);
		String priority = getPriority(operation);
		List<Action> actions = getActions(operation);
		return new Operation(opId, opName, targetObject, priority, actions);
	}

	private static String getNodeName(String node) {
		return getParam("Node", node);
	}

	private static String getConnectionName(String node) {
		return getParam("Connection", node);
	}

	private static String getFlowName(String node) {
		return getParam("Flow", node);
	}

	private static String getOpName(String node) {
		return getParam("Operation", node);
	}

	private static String getTargetObj(String node) {
		return getParam("Target", node);
	}

	private static String getPriority(String node) {
		return getParam("Priority", node);
	}

	private static String getType(String node) {
		return getParam("Type", node);
	}

	private static List<String> getContains(String node) {
		if (node.indexOf("Contain") < 0)
			return null;
		String str = node.substring(node.indexOf("Contain"));
		String sub = null;
		if (str.indexOf("Property") > 0) {
			sub = str.substring(0, str.indexOf("Property"));
		} else {
			sub = str.replaceAll(";", "").trim();
		}
		String[] params = sub.split(" ");
		String[] nodes = params[1].split(",");
		List<String> nodeList = new ArrayList<String>();
		for (String nodeName : nodes) {
			String uuId = UUIDUtils.getUUId(nodeName.trim());
			nodeList.add(uuId);
		}
		return nodeList;
	}

	private static List<String> getEndNodes(String node) {
		if (node.indexOf("Endnodes") < 0)
			return null;
		String str = node.substring(node.indexOf("Endnodes"));
		String sub = null;
		if (str.indexOf("Property") > 0) {
			sub = str.substring(0, str.indexOf("Property"));
		} else {
			sub = str.replaceAll(";", "").trim();
		}
		String[] params = sub.split(" ");
		String[] nodes = params[1].split(",");
		List<String> nodeList = new ArrayList<String>();
		for (String nodeName : nodes) {
			String uuId = UUIDUtils.getUUId(nodeName.trim());
			nodeList.add(uuId);
		}
		return nodeList;
	}

	private static List<Property> getProperties(String node) {
		if (node.indexOf("Property") < 0)
			return null;
		String str = node.substring(node.indexOf("Property"))
				.replaceAll(";", "").trim();


		String[] properties = str.replaceAll("Property","").trim().split(",");
		List<Property> propertyList = new ArrayList<Property>();
		for (String property : properties) {
			String pName = property.substring(0, property.indexOf(":"));
			List<String> pValues = new ArrayList<String>();
			pValues.add(property.substring(property.indexOf(":") + 1).trim());
			propertyList.add(new Property(pName, pValues));

		}
		return propertyList;
	}

	private static List<Action> getActions(String node) {
		if (node.indexOf("Action") < 0)
			return null;
		String str = node.substring(node.indexOf("Action")).replaceAll(";", "")
				.trim();


		String[] actions_str = str.replaceAll("Action","").trim().split(",");
		List<Action> actionList = new ArrayList<Action>();
		for (String action_s : actions_str) {
			String[] ss = action_s.split(":");
			if (ss.length == 1) {
				actionList.add(new Action(ss[0].trim(), null));
			}
			if (ss.length == 2) {
				String aName = ss[0].trim();
				List<String> aValues = new ArrayList<String>();
				String[] vss = ss[1].trim().split(",");
				for (String v : vss) {
					aValues.add(v.trim());
				}
				ActionValue actionValue = new ActionValue(aValues);
				actionList.add(new Action(aName, actionValue));
			}
		}
		return actionList;
	}

	private static List<Match> getMatches(String flow) {
		if (flow.indexOf("Match") < 0)
			return null;
		String str = flow.substring(flow.indexOf("Match")).replaceAll(";", "")
				.trim();

		String[] matches_str = str.replaceAll("Match","").trim().split(",");
		List<Match> matches = new ArrayList<Match>();
		for (String match : matches_str) {
			String[] ss = match.split(":");
			if (ss.length == 2) {
				String matchName = ss[0].trim();
				MatchValue matchValue = new MatchValue(ss[1].trim());
				matches.add(new Match(matchName, matchValue));
			}
		}
		return matches;
	}

	private static String getParam(String paramName, String nemo) {
		if (nemo.indexOf(paramName) < 0) {
			return null;
		}
		String str = nemo.substring(nemo.indexOf(paramName));
		String[] params = str.split(" ");
		if (params.length < 2)
			return null;
		return params[1].trim();
	}

	public static void main(String[] args) {
		String s = "IMPORT Node branch Type ext-group Property location:openflow:3:4,ac-info-network:layer3,ip-prifix:192.168.12.0/24,ac-info-protocol:static ;";
		List<Property> pL = getProperties(s);
		System.out.println(pL);
	}
}
