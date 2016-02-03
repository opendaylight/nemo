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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hj on 12/8/15.
 */
abstract public class Node {
    private static Logger log = LoggerFactory.getLogger(Node.class);
    private final String name;
    private final NodeType nodeType;
    protected List<Connector> connectors;

    public Node(NodeType nodeType, String name) {
        this.nodeType = nodeType;
        this.name = name;
        this.connectors = new ArrayList<Connector>();
    }

    public NodeType getNodeType() {
        return nodeType;
    }

    public String getName() {
        return name;
    }

    public void addConnectors(Connector connector) {
        connectors.add(connector);
    }

    public List<Connector> getConnectors() {
        return connectors;
    }

    public void install() {
        List<String> commands = generateCommands();
        if (commands == null) {
            log.error("Commands should not be null.");
            return;
        }
        for (String command : commands) {
            try {
                CmdExecutor.sshExecute(command);
            } catch (Exception e) {
                log.error("Error while execute [{}].", command, name, nodeType);
                // TODO Auto-generated catch block
                log.error("Exception:",e);
            }
        }
    }

    abstract protected List<String> generateCommands();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        if (name != null ? !name.equals(node.name) : node.name != null) return false;
        if (nodeType != node.nodeType) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (nodeType != null ? nodeType.hashCode() : 0);
        return result;
    }
}
