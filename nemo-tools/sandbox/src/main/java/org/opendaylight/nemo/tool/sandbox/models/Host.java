/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.nemo.tool.sandbox.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
* Created by hj on 12/8/15.
*/
abstract public class Host extends Node {
    private HashMap<Integer/*connector name*/, String/*Ipv4*/> connectorIpv4Map;
    private final String uuid;

    protected Host(NodeType nodeType, String name,String uuid) {
        super(nodeType, name);
        this.uuid = uuid;
        connectorIpv4Map = new HashMap<Integer, String>();
    }

    public void putConnectorIpv4(int order, String ipv4) {
        connectorIpv4Map.put(order, ipv4);
    }

    public String getUuid(){
        return uuid;
    }

    public String getIpv4(int order){
        return connectorIpv4Map.get(order);
    }

    abstract void uninstall();

    protected List<String> generateHost() {
        List<String> hostCreateCommands = new ArrayList<String>();
        hostCreateCommands.add("ip netns add " + getName());
        for (Connector connector : connectors) {
            hostCreateCommands.add("ip link set " + connector.getConnectorName() + " netns " + getName());
        }

        for (Connector connector : connectors) {
            String ipv4 = connectorIpv4Map.get(connector.getOrder());
            if (ipv4 != null) {
                hostCreateCommands.add("ip netns exec " + getName() + " ifconfig " + connector.getConnectorName() + " " + ipv4 + " up");
            }
        }
        return hostCreateCommands;
    }

    @Override
    public String toString() {
        return "Host{" +
                "name=" + getName() + '\'' +
                "type=" + getNodeType() + '\'' +
                "connectors='" + connectors + '\'' +
                ", ipv4Map='" + connectorIpv4Map + '\'' +
                '}';
    }
}
