/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.nemo.tool.sandbox.models;

/**
 * Created by hj on 12/8/15.
 */
public class Connector implements Comparable<Connector> {
    private final int order;
    private final String nodeName;

    public Connector(String nodeName, int order) {
        this.order = order;
        this.nodeName = nodeName;
    }

    public String getConnectorName() {
        return  nodeName + "-" + order;
    }

    public int getOrder() {
        return order;
    }

    public String getNodeName() {
        return nodeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Connector that = (Connector) o;

        if (order != that.order) return false;
        if (nodeName != null ? !nodeName.equals(that.nodeName) : that.nodeName != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = order;
        result = 31 * result + (nodeName != null ? nodeName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SwitchInterface{" +
                "order=" + order +
                ", nodeName='" + nodeName + '\'' +
                '}';
    }

    @Override
    public int compareTo(Connector o) {
        return order - o.order;
    }
}
