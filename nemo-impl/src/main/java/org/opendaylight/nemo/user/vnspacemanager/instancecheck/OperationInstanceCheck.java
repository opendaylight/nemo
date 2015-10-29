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
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Connection;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Flow;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Node;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.operations.Operation;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.User;

import java.util.List;

/**
 * Created by z00293636 on 2015/9/10.
 */
public class OperationInstanceCheck {
    private TenantManage tenantManage;

    public OperationInstanceCheck(TenantManage tenantManage)
    {
        this.tenantManage = tenantManage;
    }

    public String checkOperationInstance(UserId userId, Operation operation)
    {
        String errorInfo = null;

        tenantManage.fetchVNSpace(userId);

        User user = tenantManage.getUser();
        if (user != null)
        {
            if (operation.getTargetObject() != null && user.getObjects() == null)
            {
                errorInfo = "There are no objects in data store.";
            }
            if (operation.getTargetObject() != null && user.getObjects() != null)
            {
                Objects objects = user.getObjects();
                Boolean targetExist = false;

               if (objects.getNode() != null )
               {
                   List<Node> nodeList = objects.getNode();
                   for (Node node:nodeList)
                   {
                       if (node.getNodeId().getValue().equals(operation.getTargetObject().getValue()))
                       {
                           targetExist = true;
                           break;
                       }
                   }
               }
                if (objects.getConnection() != null)
                {
                    List<Connection> connectionList = objects.getConnection();
                    for (Connection connection : connectionList)
                    {
                        if (connection.getConnectionId().getValue().equals(operation.getTargetObject().getValue()))
                        {
                            targetExist = true;
                            break;
                        }
                    }
                }
                if (objects.getFlow() != null)
                {
                    List<Flow> flowList = objects.getFlow();
                    for (Flow flow : flowList)
                    {
                        if (flow.getFlowId().getValue().equals(operation.getTargetObject().getValue()))
                        {
                            targetExist = true;
                            break;
                        }
                    }
                }
                if (!targetExist)
                {
                    errorInfo ="The target object is not included in vn space.";
                }
            }
            else
            {
                if (user.getOperations() != null)
                {
                    if (user.getOperations().getOperation() != null)
                    {
                        List<Operation> operationList = tenantManage.getUser().getOperations().getOperation();
                        for (Operation operation1 : operationList)
                        {
                            if (operation1.getOperationId().equals(operation.getOperationId()))
                            {
                                if (!operation1.getOperationName().equals(operation.getOperationName()))
                                {
                                    errorInfo = "The operation name should not be changed.";
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        else
        {
            if (operation.getTargetObject() != null)
            {
                errorInfo = "There are no objects in data store.";
            }
        }
        return errorInfo;
    }
}
