/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.nemo.tool.sandbox.models;

import org.opendaylight.nemo.tool.sandbox.utils.Config;
import org.opendaylight.nemo.tool.sandbox.utils.HexString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by hj on 12/8/15.
 */
public class Switch extends Node {
    private static Logger log = LoggerFactory.getLogger(Switch.class);
    private final long dataPathId;
    private final String mac;

    public Switch(String name, long dataPathId) {
        super(NodeType.SWITCH, name);
        this.dataPathId = dataPathId;

        this.mac = HexString.toHexString(dataPathId);
    }

    public long getDataPathId() {
        return dataPathId;
    }

    @Override
    protected List<String> generateCommands() {
        List<String> commands = new ArrayList<String>();

        String controllerSocket = Config.getControllerSocket();
        String strInterface = "";
        Collections.sort(connectors);
        for (Connector connector : this.connectors) {
            strInterface += ("," + connector.getConnectorName());
        }
        if (this.connectors.isEmpty()) {
            log.warn("Switch {} does not have ports.");
            strInterface = "";
        } else {
            strInterface = strInterface.substring(1);
        }
        String ofDataPathCreate = "ofdatapath";
        if (!strInterface.equals("")) {
            ofDataPathCreate += (" -i " + strInterface);
        }
        ofDataPathCreate += (" punix:/tmp/sw" + dataPathId + " -d " + mac + " 1> /tmp/sw" + dataPathId + "-ofd.log 2> /tmp/sw" + dataPathId + "-ofd.log &");
        commands.add(ofDataPathCreate);

        String ofProtocol = "ofprotocol unix:/tmp/sw" + dataPathId + " tcp:" + controllerSocket + " --fail=closed  1> /tmp/sw" + dataPathId + "-ofp.log 2>/tmp/sw" + dataPathId + "-ofp.log &";
        commands.add(ofProtocol);
        String lldp = "dpctl unix:/tmp/sw" + dataPathId + " flow-mod cmd=add,table=0 eth_type=0x88cc  apply:output=ctrl:0xff";
        commands.add(lldp);
        String arp = "dpctl unix:/tmp/sw" + dataPathId + " flow-mod cmd=add,table=0 eth_type=0x0806  apply:output=ctrl:0xff";
        commands.add(arp);

        return commands;
    }

    @Override
    public String toString() {
        return "Switch{" +
                "name=" + getName() +
                ", dataPathId=" + dataPathId +
                ", mac='" + mac + '\'' +
                ", connectors=" + connectors +
                '}';
    }
}
