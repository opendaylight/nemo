package org.opendaylight.nemo.tool.eclipse.plugin.rest.actions.model;

import org.json.JSONObject;

/**
 * Created by hj on 10/29/15.
 */
public class Match implements INemoItem{
    private String matchName;
    private MatchValue value;

    public Match(String matchName, MatchValue value) {
        this.matchName = matchName;
        this.value = value;
    }

    @Override
    public JSONObject toJSONObject() {
        JSONObject matchJs = new JSONObject();
        matchJs.put("match-item-name",matchName);
        JSONObject js = new JSONObject();
        js.put("string-value", value.getValue());
        matchJs.put("match-item-value",js);
        return matchJs;
    }
}
