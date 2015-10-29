/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
 
package org.opendaylight.nemo.renderer.openflow.entity;

public class LinkBean {

    private String linkID;
    private String leftNodeID;
    private String leftPortID;
    private String rightPortID;
    private String linkBandwidth;

    public String getLinkID() {
        return linkID;
    }

    public void setLinkID(String linkID) {
        this.linkID = linkID;
    }

    public String getLeftNodeID() {
        return leftNodeID;
    }

    public void setLeftNodeID(String leftNodeID) {
        this.leftNodeID = leftNodeID;
    }

    public String getLeftPortID() {
        return leftPortID;
    }

    public void setLeftPortID(String leftPortID) {
        this.leftPortID = leftPortID;
    }

    public String getRightNodeID() {
        return rightNodeID;
    }

    public void setRightNodeID(String rightNodeID) {
        this.rightNodeID = rightNodeID;
    }

    private String rightNodeID;

    public String getRightPortID() {
        return rightPortID;
    }

    public void setRightPortID(String rightPortID) {
        this.rightPortID = rightPortID;
    }

    public String getLinkBandwidth() {
        return linkBandwidth;
    }

    public void setLinkBandwidth(String linkBandwidth) {
        this.linkBandwidth = linkBandwidth;
    }

}