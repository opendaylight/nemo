/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.nemo.renderer.cli.physicalnetwork;

import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.PhysicalNodeInstance;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.PhysicalPortInstance;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by hj on 11/5/15.
 */
public class Utils {

    protected static PhysicalNodeInstance.NodeType getNodeType(String strType){
        switch (strType){
            case "switch":
                return PhysicalNodeInstance.NodeType.Switch;
            case "router":
                return PhysicalNodeInstance.NodeType.Router;
            case "firewall":
                return PhysicalNodeInstance.NodeType.Firewall;
            case "loadbalancer":
                return PhysicalNodeInstance.NodeType.Loadbalancer;
        }
        return null;
    }
    protected static PhysicalPortInstance.PortType getPortType(String strType){
        switch (strType){
            case "external":
                return PhysicalPortInstance.PortType.External;
        }
        return PhysicalPortInstance.PortType.Internal;
    }
    protected static String readFile(String Path){
        BufferedReader reader = null;
        String laststr = "";
        try{
            FileInputStream fileInputStream = new FileInputStream(Path);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            reader = new BufferedReader(inputStreamReader);
            String tempString = null;
            while((tempString = reader.readLine()) != null){
                laststr += tempString;
            }
            reader.close();
        }catch(IOException e){
            // TODO Auto-generated catch block
        }finally{
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                }
            }
        }
        return laststr;
    }

}
