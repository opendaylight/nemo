package org.opendaylight.nemo.tool.eclipse.plugin.rest.actions.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by hj on 10/29/15.
 */
public class Flow implements INemoItem {
    private String flow_id;
    private String flow_name;
    private List<Match> matches;
    public Flow(String flow_id, String flow_name, List<Match> matches) {
        this.flow_id = flow_id;
        this.flow_name = flow_name;
        this.matches = matches;
    }

    @Override
    public JSONObject toJSONObject() {
        JSONObject flowJs = new JSONObject();
        flowJs.put("flow-id", flow_id);
        flowJs.put("flow-name", flow_name);

        if (matches != null && matches.size() > 0) {
            //Js array,matches
            JSONArray jsonMatchArray = new JSONArray();
            for (int i = 0; i < matches.size(); i++){
                Match match = matches.get(i);
                jsonMatchArray.put(i,match.toJSONObject());
            }
            flowJs.put("match-item",jsonMatchArray);
        }
        return flowJs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Flow flow = (Flow) o;

        if (flow_id != null ? !flow_id.equals(flow.flow_id) : flow.flow_id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return flow_id != null ? flow_id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Flow{" +
                "flow_id='" + flow_id + '\'' +
                ", flow_name='" + flow_name + '\'' +
                ", matches=" + matches +
                '}';
    }
}
