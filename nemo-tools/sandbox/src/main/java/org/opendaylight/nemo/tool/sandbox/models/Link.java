/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.nemo.tool.sandbox.models;

import org.opendaylight.nemo.tool.sandbox.CmdExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Created by hj on 12/8/15.
 */
public class Link {
    private static Logger log = LoggerFactory.getLogger(Link.class);
    private final Connector srcInterface;
    private final Connector dstInterface;

    public Link(Connector srcInterface, Connector dstInterface) {
        this.srcInterface = srcInterface;
        this.dstInterface = dstInterface;
    }

    public Connector getSrcConnector() {
        return srcInterface;
    }

    public Connector getDstConnector() {
        return dstInterface;
    }

    public void install() {
        String linkAddCmd = "ip link add name " + srcInterface.getConnectorName() + " type veth peer name " + dstInterface.getConnectorName();
        try {
            CmdExecutor.sshExecute(linkAddCmd);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            log.error("Exception:",e);
        }
    }

    public void uninstall() {
        String linkDelCmd = "ip link del " + srcInterface.getConnectorName();
        try {
            CmdExecutor.sshExecute(linkDelCmd);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            log.error("Exception:",e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Link link = (Link) o;

        if (dstInterface != null ? !dstInterface.equals(link.dstInterface) : link.dstInterface != null) return false;
        if (srcInterface != null ? !srcInterface.equals(link.srcInterface) : link.srcInterface != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = srcInterface != null ? srcInterface.hashCode() : 0;
        result = 31 * result + (dstInterface != null ? dstInterface.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Link{" +
                "srcInterface=" + srcInterface +
                ", dstInterface=" + dstInterface +
                '}';
    }
}
