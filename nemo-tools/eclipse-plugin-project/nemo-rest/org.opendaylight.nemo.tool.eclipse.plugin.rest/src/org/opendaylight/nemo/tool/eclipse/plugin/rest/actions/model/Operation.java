package org.opendaylight.nemo.tool.eclipse.plugin.rest.actions.model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.opendaylight.nemo.tool.eclipse.plugin.rest.actions.UUIDUtils;

import java.util.List;

/**
 * Created by hj on 10/29/15.
 */
public class Operation implements INemoItem {
	private String operation_id;
	private String operation_name;
	private String target_object;
	private String priority = "0";
	private List<Action> actions;

	public Operation(String operation_id, String operation_name,
			String target_object, String priority, List<Action> actions) {
		this.operation_id = operation_id;
		this.operation_name = operation_name;
		this.target_object = UUIDUtils.getUUId(target_object);
		if (priority != null)
			this.priority = priority;
		this.actions = actions;
	}

	@Override
	public JSONObject toJSONObject() {
		JSONObject operationJs = new JSONObject();
		operationJs.put("operation-id", operation_id);
		operationJs.put("operation-name", operation_name);
		if (target_object != null) {
			operationJs.put("target-object", target_object);
		}
		if (priority != null) {
			operationJs.put("priority", priority);
		}

		if (actions != null && actions.size() > 0) {
			// JS array,actions array.
			JSONArray actionJsArray = new JSONArray();
			// remember add order to each action;
			for (int i = 0; i < actions.size(); i++) {
				Action action = actions.get(i);
				JSONObject actionJs = action.toJSONObject();
				actionJs.put("order", i + "");
				actionJsArray.put(i, actionJs);
			}
			operationJs.put("action", actionJsArray);
		}

		return operationJs;
	}
}
