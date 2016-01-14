package org.opendaylight.nemo.tool.eclipse.plugin.rest.actions.model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.opendaylight.nemo.tool.eclipse.plugin.rest.actions.UUIDUtils;

import java.util.List;

/**
 * Created by hj on 10/29/15.
 */
public class Node implements INemoItem{
    private String node_id;
    private String node_name;
    private String node_type;
    private List<Property> properties;
    private List<String> sub_nodes;

    public Node(String node_id, String node_name, String node_type, List<Property> properties, List<String> sub_nodes) {
        this.node_id = node_id;
        this.node_name = node_name;
        this.node_type = node_type;
        this.properties = properties;
        this.sub_nodes = sub_nodes;
    }

    @Override
    public JSONObject toJSONObject() {
        JSONObject jsonNode = new JSONObject();
        jsonNode.put("node-id",node_id);
        jsonNode.put("node-name",node_name);
        if(node_type!=null){
            jsonNode.put("node-type",node_type);
        }
        if(properties!=null&&properties.size()>0){
            //JS array,properties
            JSONArray jsonPropertyArray = new JSONArray();
            for (int i=0;i<properties.size();i++){
                JSONObject jsonProperty= properties.get(i).toJSONObject();
                jsonPropertyArray.put(i,jsonProperty);
            }
            jsonNode.put("property",jsonPropertyArray);
        }

        if(sub_nodes!=null&&sub_nodes.size()>0){
            //JS array,sub nodes
            JSONArray jsonSubNodeArray = new JSONArray();
            for (int i=0;i<sub_nodes.size();i++){
                String value = sub_nodes.get(i);
                
                JSONObject jsonSubNode = new JSONObject();
                jsonSubNode.put("order",i+"");
                jsonSubNode.put("node-id",value);
                jsonSubNodeArray.put(i,jsonSubNode);
            }
            jsonNode.put("sub-node",jsonSubNodeArray);
        }

        return jsonNode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        if (node_id != null ? !node_id.equals(node.node_id) : node.node_id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return node_id != null ? node_id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Node{" +
                "node_id='" + node_id + '\'' +
                ", node_name='" + node_name + '\'' +
                ", node_type='" + node_type + '\'' +
                ", properties=" + properties +
                ", sub_nodes=" + sub_nodes +
                '}';
    }
}
