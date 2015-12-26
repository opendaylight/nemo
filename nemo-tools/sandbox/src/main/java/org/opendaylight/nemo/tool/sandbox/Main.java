/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.nemo.tool.sandbox;

import org.opendaylight.nemo.tool.sandbox.northbound.RestServer;
import org.opendaylight.nemo.tool.sandbox.utils.PropertyLoader;

/**
 * Created by hj on 12/9/15.
 */
public class Main {
    public static void main(String []args){
        PropertyLoader.loadProperties("/sandbox.properties", "sandbox.properties does not exist.");
        int northboundPort = Integer.parseInt(System.getProperty("north.port","10081"));
        String socketAddress = "http://0.0.0.0:"+northboundPort+"/";
        RestServer.start(socketAddress);
        EasyCli easyCli = new EasyCli("sandbox","Authorized by Ip tech, Huawei",new Cli());
        easyCli.run();
    }
}
