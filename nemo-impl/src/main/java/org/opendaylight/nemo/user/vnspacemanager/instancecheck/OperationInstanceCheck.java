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
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Flow;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Node;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.operations.Operation;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.User;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.ParameterValues;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.StringValue;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.operation.instance.Action;

import java.util.ArrayList;
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
        List<Node> nodeList = new ArrayList<Node>();
        List<Connection> connectionList = new ArrayList<Connection>();
        List<Flow> flowList = new ArrayList<Flow>();

        if (user.getObjects()!=null)
        {
            if (user.getObjects().getNode()!=null)
            {
                nodeList = user.getObjects().getNode();
            }
            if (user.getObjects().getConnection()!=null)
            {
                connectionList = user.getObjects().getConnection();
            }
            if (user.getObjects().getFlow()!=null)
            {
                flowList = user.getObjects().getFlow();
            }
        }

        if (operation.getTargetObject()!=null)
        {
            Boolean TargetExist = false;
            for (Node node:nodeList)
            {
                if (node.getNodeId().getValue().equals(operation.getTargetObject()));
                {
                    TargetExist = true;
                    break;
                }
            }
            for (Connection connection:connectionList)
            {
                if (connection.getConnectionId().getValue().equals(operation.getTargetObject()))
                {
                    TargetExist = true;
                    break;
                }
            }
            for (Flow flow:flowList)
            {
                if (flow.getFlowId().getValue().equals(operation.getTargetObject()))
                {
                    TargetExist = true;
                    break;
                }
            }
            if (!TargetExist)
            {
                errorInfo = "The target is not exist in user vn space.";
                return errorInfo;
            }
        }
        if (operation.getAction()!=null)
        {
            List<Action> actionList = operation.getAction();
            for (Action action:actionList)
            {
                if (action.getParameterValues()!=null)
                {
                    ParameterValues parameterValues = action.getParameterValues();
                    if (parameterValues.getStringValue()!=null)
                    {
                        List<StringValue> stringValues = parameterValues.getStringValue();
                        for (StringValue stringValue:stringValues)
                        {
                            Boolean ParaExist = false;
                            for (Node node:nodeList)
                            {
                                if (node.getNodeId().getValue().equals(stringValue.getValue()));
                                {
                                    ParaExist = true;
                                    break;
                                }
                            }
                            for (Connection connection:connectionList)
                            {
                                if (connection.getConnectionId().getValue().equals(stringValue.getValue()))
                                {
                                    ParaExist = true;
                                    break;
                                }
                            }
                            for (Flow flow:flowList)
                            {
                                if (flow.getFlowId().getValue().equals(stringValue.getValue()))
                                {
                                    ParaExist = true;
                                    break;
                                }
                            }
                            if (!ParaExist)
                            {
                                errorInfo = "The parameter value is not included in the user vn space.";
                                return errorInfo;
                            }
                        }
                    }
                }
            }
        }
        if (errorInfo==null)
        {
            if (user.getOperations()!=null)
            {
                if (user.getOperations().getOperation()!=null)
                {
                    for (Operation operation1:user.getOperations().getOperation())
                    {
                        if (operation1.getOperationId().equals(operation.getOperationId()))
                        {
                            if (!operation1.getOperationName().equals(operation.getOperationName()))
                            {
                                errorInfo="The operation name should not be changed.";
                                return errorInfo;
                            }

                        }
                    }
                }
            }
        }
        return errorInfo;
    }
}
