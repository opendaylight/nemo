package org.opendaylight.nemo.tool.eclipse.plugin.rest.actions.model;

import org.json.JSONObject;

/**
 * Created by hj on 10/29/15.
 */
public class MatchValue implements INemoItem {
    private String value;
    public String getValue(){
    	return value;
    }
    public MatchValue(String value) {
        this.value = value;
    }

    @Override
    public JSONObject toJSONObject() {
        JSONObject valueJson = new JSONObject();
        valueJson.put("match-item-value",value);
        return valueJson;
    }
}
