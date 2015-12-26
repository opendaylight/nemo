package org.opendaylight.nemo.tool.eclipse.plugin.rest.actions;


import java.util.HashMap;
import java.util.UUID;

/**
 * Created by hj on 10/29/15.
 * Not concurrent safe.
 */
public class UUIDUtils {
    private static HashMap<String/**/,String/*uuid*/> uuidMap = new HashMap<String, String>();

    public static String getUUId(String name){
        if(!uuidMap.containsKey(name)){
            uuidMap.put(name, UUID.randomUUID().toString());
        }
        return uuidMap.get(name);
        
    }
}
