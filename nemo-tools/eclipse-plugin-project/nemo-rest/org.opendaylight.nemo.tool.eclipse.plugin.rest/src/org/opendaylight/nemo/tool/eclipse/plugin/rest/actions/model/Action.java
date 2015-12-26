package org.opendaylight.nemo.tool.eclipse.plugin.rest.actions.model;

import org.json.JSONObject;

/**
 * Created by hj on 10/29/15.
 */
public class Action implements INemoItem {
    private String action_name;
    private ActionValue value;

    public Action(String action_name, ActionValue value) {
        this.action_name = action_name;
        this.value = value;
    }

    @Override
    public JSONObject toJSONObject() {
        JSONObject actionJs = new JSONObject();
        actionJs.put("action-name", action_name);
        if (value != null){
        	JSONObject js = new JSONObject();
            js.put("string-value", value.getJs());
            actionJs.put("parameter-values", js);
        }
        return actionJs;
    }
}
