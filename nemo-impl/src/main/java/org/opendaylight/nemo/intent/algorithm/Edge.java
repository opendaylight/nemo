/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.nemo.intent.algorithm;

import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.links.PhysicalLink;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.links.VirtualLink;

/**
 * An edge in the network topology.
 *
 * @author Zhigang Ji
 */
public class Edge {
    private final String id;
    private String src;
    private String dest;
    private long metric;
    private long bandwidth;

    public Edge(String id, String src, String dest, long metric, long bandwidth) {
        this.id = id;
        this.src = src;
        this.dest = dest;
        this.metric = metric;
        this.bandwidth = bandwidth;
    }

    public Edge(VirtualLink virtualLink) {
        this(virtualLink.getLinkId().getValue(), virtualLink.getSrcNodeId().getValue(), virtualLink.getDestNodeId()
                .getValue(), virtualLink.getMetric(), virtualLink.getBandwidth());
    }

    public Edge(PhysicalLink physicalLink) {
        this(physicalLink.getLinkId().getValue(), physicalLink.getSrcNodeId().getValue(), physicalLink.getDestNodeId()
                .getValue(), physicalLink.getMetric(), physicalLink.getBandwidth());
    }

    public String getId() {
        return id;
    }

    public String getSrc() {
        return src;
    }

    public String getDest() {
        return dest;
    }

    public long getMetric() {
        return metric;
    }

    public long getBandwidth() {
        return bandwidth;
    }

    public void setMetric(long metric) {
        this.metric = metric;

        return;
    }

    public void setBandwidth(long bandwidth) {
        this.bandwidth = bandwidth;

        return;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Edge other = (Edge) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "id='" + id + '\'' +
                ", src='" + src + '\'' +
                ", dest='" + dest + '\'' +
                ", metric=" + metric +
                ", bandwidth=" + bandwidth +
                '}';
    }
}
