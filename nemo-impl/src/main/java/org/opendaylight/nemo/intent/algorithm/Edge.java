/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.nemo.intent.algorithm;

/**
 * An edge in the network topology.
 *
 * @author Zhigang Ji
 */
public class Edge {
    private String id;
    private String src;
    private String dest;
    private long metric;
    private long bandwidth;

    public Edge(String id, String src, String dest, long metric, long bandwidth) {
        super();

        this.id = id;
        this.src = src;
        this.dest = dest;
        this.metric = metric;
        this.bandwidth = bandwidth;

        return;
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
    public boolean equals(Object obj) {
        return ((Edge)obj).getId().equals(id);
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
