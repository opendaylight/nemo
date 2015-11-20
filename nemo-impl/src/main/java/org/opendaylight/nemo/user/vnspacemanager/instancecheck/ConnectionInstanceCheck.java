/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.user.vnspacemanager.instancecheck;

import org.opendaylight.nemo.user.tenantmanager.TenantManage;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Connection;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Node;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.User;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.connection.instance.EndNode;

import java.util.List;

/**
 * Created by z00293636 on 2015/9/10.
 */
public class ConnectionInstanceCheck {
    private TenantManage tenantManage;

    public ConnectionInstanceCheck(TenantManage tenantManage)
    {
        this.tenantManage = tenantManage;
    }

    public String checkConnInstance(UserId userId, Connection connection)
    {
        String errorInfo = null;
        tenantManage.fetchVNSpace(userId);
        User user = tenantManage.getUser();

        if (user != null)
        {
            if (user.getObjects() != null)
            {
                if (user.getObjects().getConnection() != null)
                {
                    List<Connection> connectionList = tenantManage.getUser().getObjects().getConnection();

                    for (Connection connection1 : connectionList)
                    {
                        if (connection1.getConnectionId().equals(connection.getConnectionId()))
                        {
                            if (!connection1.getConnectionType().equals(connection.getConnectionType()))
                            {
                                errorInfo = "The connection type should not be changed.";
                                break;
                            }
                            if (!connection1.getConnectionName().equals(connection.getConnectionName()))
                            {
                                errorInfo = "The End node should not be changed.";
                                break;
                            }

                        }
                    }
                }
                if (connection.getEndNode()!=null)
                {
                    if (user.getObjects().getNode() != null)
                    {
                        List<EndNode> nodeList = connection.getEndNode();
                        List<Node> nodeList1 = user.getObjects().getNode();

                        for (EndNode endNode : nodeList)
                        {
                            Boolean EndNodeExist = false;
                            for (Node node : nodeList1)
                            {
                                if (node.getNodeId().equals(endNode.getNodeId()))
                                {
                                    EndNodeExist = true;
                                }
                            }
                            if ( !EndNodeExist)
                            {
                                errorInfo = "There are no endnode" + endNode.getNodeId().toString() +" in this user vn space.";
                                break;
                            }
                        }
                    }
                }
            }
            else
            {
                if (connection.getEndNode() != null)
                {
                    errorInfo = "There are no nodes in user vn space.";
                }
            }
        }
        else
        {
            if (connection.getEndNode() != null)
            {
                errorInfo = "There are no nodes in user vn space.";
            }
        }

        return errorInfo;
    }
}
