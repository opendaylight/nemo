package org.opendaylight.nemo.tool.eclipse.plugin.rest.actions.model;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by hj on 10/29/15.
 */
public class NemoInput implements INemoInput {
	private User user;
	private INemoItem iNemoItem;
	private boolean begin;

	public NemoInput(User user, INemoItem iNemoItem) {
		this.user = user;
		this.iNemoItem = iNemoItem;
	}
	public NemoInput(User user) {
		this.user = user;
		this.iNemoItem = null;
		begin = true;
	}
	@Override
	public String toJsonFormatString() {
		JSONObject dataJs = new JSONObject();
		JSONObject inputValueJson = new JSONObject();
		inputValueJson.put("user-id", user.getUser_id());
		
		if (iNemoItem == null&&begin) {
			inputValueJson.put("user-name", user.getUser_name());
			inputValueJson.put("user-password", user.getUser_password());
			inputValueJson.put("user-role", user.getUser_role());
			dataJs.put("input", inputValueJson);
			return dataJs.toString();
		}
		if (iNemoItem == null) {
			dataJs.put("input", inputValueJson);
			return dataJs.toString();
		}
		// item js array;
		JSONArray itemJsArray = new JSONArray();
		String key = "node";
		if (iNemoItem instanceof Node) {
			key = "node";
		}
		if (iNemoItem instanceof Connection) {
			key = "connection";
		}
		if (iNemoItem instanceof Flow) {
			key = "flow";
		}
		if (iNemoItem instanceof Operation) {
			key = "operation";
		}
		itemJsArray.put(0, iNemoItem.toJSONObject());
		JSONObject objectsJs = new JSONObject();
		objectsJs.put(key, itemJsArray);
		if (iNemoItem instanceof Operation)
			inputValueJson.put("operations", objectsJs);
		else
			inputValueJson.put("objects", objectsJs);
		dataJs.put("input", inputValueJson);
		return dataJs.toString();
	}
}
