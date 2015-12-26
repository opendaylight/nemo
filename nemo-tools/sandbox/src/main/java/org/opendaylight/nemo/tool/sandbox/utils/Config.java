/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.nemo.tool.sandbox.utils;

/**
 * Created by hj on 12/8/15.
 */
public class Config {
    private static String controllerSocket;
    private static boolean printProcess;
    private static String hostName;
    private static String hostUser;
    private static String hostPassword;

    private static String configPath;
    private static String configHostsFileName;
    private static String configExternalsFileName;
    private static String configLinksFileName;
    private static String configNodesFileName;

    static {
        controllerSocket = System.getProperty("controller-socket","localhost:6633");
        printProcess = Boolean.valueOf(System.getProperty("print-process","false"));
        hostName = System.getProperty("host.name","localhost");
        hostUser = System.getProperty("host.user","root");
        hostPassword = System.getProperty("host.password","123456");
        configPath = System.getProperty("config.path","/root");
        configHostsFileName = System.getProperty("config.hosts-file-name","host-resource.json");
        configExternalsFileName = System.getProperty("config.externals-file-name","external-resource.json");
        configLinksFileName = System.getProperty("config.links-file-name","link-resource.json");
        configNodesFileName = System.getProperty("config.nodes-file-name","node-resource.json");
    }

    public static String getControllerSocket(){
        return controllerSocket;
    }

    public static boolean isPrintProcess(){
        return printProcess;
    }

    public static String getHostName(){
        return hostName;
    }

    public static String getHostUser(){
        return hostUser;
    }

    public static String getHostPassword(){
        return hostPassword;
    }

    public static String getConfigPath() {
        return configPath;
    }

    public static String getConfigHostsFileName() {
        return configHostsFileName;
    }

    public static String getConfigExternalsFileName() {
        return configExternalsFileName;
    }

    public static String getConfigLinksFileName() {
        return configLinksFileName;
    }

    public static String getConfigNodesFileName() {
        return configNodesFileName;
    }
}
