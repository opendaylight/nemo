/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.renderer.openflow.entity;

import org.opendaylight.nemo.renderer.openflow.entity.PortBean;
import java.util.List;

public class NodeBean {

    private String nodeID;
    private String nodeType;
    private String nodeCapacity;
    private List<PortBean> portList;

    public String getNodeID() {
        return nodeID;
    }

    public void setNodeID(String nodeID) {
        this.nodeID = nodeID;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public String getNodeCapacity() {
        return nodeCapacity;
    }

    public void setNodeCapacity(String nodeCapacity) {
        this.nodeCapacity = nodeCapacity;
    }

    public List<PortBean> getPortList() {
        return portList;
    }

    public void setPortList(List<PortBean> portList) {
        this.portList = portList;
    }

}

