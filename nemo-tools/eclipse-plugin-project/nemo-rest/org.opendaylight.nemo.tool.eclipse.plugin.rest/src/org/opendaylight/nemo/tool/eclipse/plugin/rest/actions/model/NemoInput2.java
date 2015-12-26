package org.opendaylight.nemo.tool.eclipse.plugin.rest.actions.model;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by hj on 10/29/15.
 */
public class NemoInput2 implements INemoInput {
	private User user;
	private String nemo;

	public NemoInput2(User user, String nemo) {
		this.user = user;
		this.nemo = nemo;
	}

	@Override
	public String toJsonFormatString() {
		JSONObject dataJs = new JSONObject();
		JSONObject inputValueJson = new JSONObject();
		inputValueJson.put("user-id", user.getUser_id());
		inputValueJson.put("nemo-statement", nemo);
		dataJs.put("input", inputValueJson);
		return dataJs.toString();

	}
}
