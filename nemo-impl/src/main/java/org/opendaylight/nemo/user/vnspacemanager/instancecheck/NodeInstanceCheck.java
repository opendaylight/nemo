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
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.Objects;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Node;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.User;

import java.util.List;

/**
 * Created by z00293636 on 2015/9/10.
 */
public class NodeInstanceCheck {

    private TenantManage tenantManage;

    public NodeInstanceCheck(TenantManage tenantManage)
    {
        this.tenantManage = tenantManage;
    }

    public String checkNodeInstance(UserId userId, Node node)
    {
        String errorInfo = null;
        tenantManage.fetchVNSpace(userId);
        User user = tenantManage.getUser();
        if (user != null)
        {
            Objects objects = user.getObjects();
            if (objects != null)
            {
                if (objects.getNode() != null)
                {
                    List<Node> nodeList = objects.getNode();

                    for (Node node1 : nodeList)
                    {
                        if (node1.getNodeId().equals(node.getNodeId()))
                        {
                            if ( !node1.getNodeName().equals(node.getNodeName()))
                            {
                                errorInfo = "The node name should not be changed.";
                                break;
                            }
                            if  (!node1.getNodeType().equals(node.getNodeType()))
                            {
                                errorInfo = "The node type should not be changed.";
                                break;
                            }
                        }
                    }
                }
            }
        }
        return errorInfo;
    }
}
