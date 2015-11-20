/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.nemo.intent.algorithm;

/**
 * A vertex in the network topology.
 *
 * @author Zhigang Ji
 */
public class Vertex {
    private String id;

    public Vertex(String id) {
        super();

        this.id = id;

        return;
    }

    public String getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if ( obj instanceof Vertex ) {
            return ((Vertex)obj).getId().equals(id);
        }

        return false;
    }

    @Override
    public String toString() {
        return "Vertex{" +
                "id='" + id + '\'' +
                '}';
    }
}
