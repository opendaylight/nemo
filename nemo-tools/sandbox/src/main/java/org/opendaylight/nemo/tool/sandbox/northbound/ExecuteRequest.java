/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.nemo.tool.sandbox.northbound;

/**
 * Created by hj on 12/14/15.
 */
public class ExecuteRequest {
    private String hostName;
    private String command;

    public ExecuteRequest(){

    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    @Override
    public String toString() {
        return "ExecuteRequest{" +
                "hostName='" + hostName + '\'' +
                ", command='" + command + '\'' +
                '}';
    }
}
