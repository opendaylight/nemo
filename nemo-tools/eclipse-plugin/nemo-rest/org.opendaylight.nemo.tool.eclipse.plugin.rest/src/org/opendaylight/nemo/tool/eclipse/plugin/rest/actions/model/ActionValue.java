package org.opendaylight.nemo.tool.eclipse.plugin.rest.actions.model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.opendaylight.nemo.tool.eclipse.plugin.rest.actions.UUIDUtils;

import java.util.List;

/**
 * Created by hj on 10/29/15.
 */
public class ActionValue implements INemoItem {
    private List<String> values;

    public ActionValue(List<String> values) {
        this.values = values;
    }
    public JSONArray getJs(){
    	JSONArray valueArrayJs =new  JSONArray();
        for (int i=0;i<values.size();i++){
            String value = values.get(i);
            JSONObject valueJs = new JSONObject();
            valueJs.put("value",UUIDUtils.getUUId(value));
            valueJs.put("order",i+"");
            valueArrayJs.put(i,valueJs);
        }
        return valueArrayJs;
    }
    @Override
    public JSONObject toJSONObject() {
            //js array,values
//        JSONArray valueArrayJs =new  JSONArray();
//        for (int i=0;i<values.size();i++){
//            String value = values.get(i);
//            JSONObject valueJs = new JSONObject();
//            valueJs.put("value",value);
//            valueJs.put("order",i+"");
//            valueArrayJs.put(i,valueJs);
//        }
        return null;
    }
}
