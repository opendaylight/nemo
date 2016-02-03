/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.nemo.tool.sandbox.models;

import org.opendaylight.nemo.tool.sandbox.CmdExecutor;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Created by hj on 12/8/15.
 */
public class Cache extends Host {
    private static Logger log = LoggerFactory.getLogger(Cache.class);
    public Cache(String name,String uuId) {
        super(NodeType.CACHE,name, uuId);
    }

    @Override
    protected List<String> generateCommands() {
        List<String> commands = new ArrayList<String>(generateHost());
        //TODO: host script.
        //open route flag
        commands.add("ip netns exec "+getName()+"echo 1 > /proc/sys/net/ipv4/ip_forward");
        //add default route
        if(connectors.size()>0) {
            commands.add("ip netns exec route add default dev "+ connectors.get(0));
        }
        return commands;
    }

    @Override
    public void uninstall(){
        try {
            CmdExecutor.sshExecute("ip netns del " + getName());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            log.error("Exception:",e);
        }
    }
}
