package org.opendaylight.nemo.tool.eclipse.plugin.rest.actions.model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.opendaylight.nemo.tool.eclipse.plugin.rest.actions.UUIDUtils;

import java.util.List;

/**
 * Created by hj on 10/29/15.
 */
public class Connection implements INemoItem {
    private String connection_id;
    private String connection_name;
    private String connection_type;
    private List<String> end_nodes;
    private List<Property> properties;

    public Connection(String connection_id, String connection_name, String connection_type, List<String> end_nodes, List<Property> properties) {
        this.connection_id = connection_id;
        this.connection_name = connection_name;
        this.connection_type = connection_type;
        this.end_nodes = end_nodes;
        this.properties = properties;
    }

    @Override
    public JSONObject toJSONObject() {
        JSONObject jsonConnection = new JSONObject();
        jsonConnection.put("connection-id", connection_id);
        jsonConnection.put("connection-name", connection_name);
        jsonConnection.put("connection-type", connection_type);

        if(end_nodes!=null&&end_nodes.size()>0){
            //JS array,sub nodes
            JSONArray jsonEndNodeArray = new JSONArray();
            for (int i=0;i<end_nodes.size();i++){
                String value = end_nodes.get(i);
                
                JSONObject jsonEndNode = new JSONObject();
                jsonEndNode.put("order", i + "");
                jsonEndNode.put("node-id", value);
                jsonEndNodeArray.put(i, jsonEndNode);
            }
            jsonConnection.put("end-node", jsonEndNodeArray);
        }


        if(properties!=null&&properties.size()>0){
            //JS array,properties
            JSONArray jsonPropertyArray = new JSONArray();
            for (int i=0;i<properties.size();i++){
                JSONObject jsonProperty= properties.get(i).toJSONObject();
                jsonPropertyArray.put(i,jsonProperty);
            }
            jsonConnection.put("property", jsonPropertyArray);
        }


        return jsonConnection;
    }
}
