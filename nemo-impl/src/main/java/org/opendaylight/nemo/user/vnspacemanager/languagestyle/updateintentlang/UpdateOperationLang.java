/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.user.vnspacemanager.languagestyle.updateintentlang;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.nemo.user.tenantmanager.TenantManage;
import org.opendaylight.nemo.user.vnspacemanager.structurestyle.updateintent.UpdateOperation;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Connection;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Flow;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Node;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.operations.Operation;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.operations.OperationBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.operations.OperationKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.User;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.property.values.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.ParameterValuesBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.IntValue;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.IntValueBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.IntValueKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.RangeValue;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.RangeValueBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.StringValue;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.StringValueBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.StringValueKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.condition.instance.ConditionSegment;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.condition.instance.ConditionSegmentBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.condition.instance.ConditionSegmentKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.operation.instance.Action;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.operation.instance.ActionBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.operation.instance.ActionKey;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

/**
 * Created by z00293636 on 2015/11/6.
 */
public class UpdateOperationLang {
    private DataBroker dataBroker;
    private TenantManage tenantManage;
    private Operation operation;
    private UpdateOperation updateOperation;

    public UpdateOperationLang(DataBroker dataBroker, TenantManage tenantManage)
    {
        this.dataBroker = dataBroker;
        this.tenantManage = tenantManage;
        updateOperation = new UpdateOperation(dataBroker, tenantManage);
    }

    public String OperationHandling(UserId userId, String operationname, String target, String priority, LinkedHashMap<String,LinkedHashMap<String,String>> conditions,LinkedHashMap<String,LinkedHashMap<String,String>>actions)
    {
        String errorInfo = null;
        errorInfo = createOperation(userId,operationname,target,priority,conditions,actions);
        if (errorInfo==null)
        {
            errorInfo = updateOperation.OperationHandling(userId,this.operation);
        }
        return errorInfo;
    }

    private String createOperation(UserId userId, String operationname, String target, String priority, LinkedHashMap<String,LinkedHashMap<String,String>> conditions,LinkedHashMap<String,LinkedHashMap<String,String>>actions)
    {
        String errorInfo = null;
        tenantManage.fetchVNSpace(userId);
        User user = tenantManage.getUser();
        List<Operation> operationList = new ArrayList<Operation>();
        Boolean OperationExist = false;
        Boolean TargetExist = false;
        OperationBuilder operationBuilder = new OperationBuilder();

        if (user.getOperations()!=null)
        {
            if (user.getOperations().getOperation()!=null)
            {
                operationList = user.getOperations().getOperation();
            }
        }
        if (!operationList.isEmpty())
        {
            for (Operation operation1:operationList)
            {
                if (operation1.getOperationName().getValue().equals(operationname))
                {
                    OperationExist = true;
                    operationBuilder.setKey(operation1.getKey());
                    operationBuilder.setOperationId(operation1.getOperationId());
                }
            }
        }
        if (!OperationExist)
        {
            OperationId operationId = new OperationId(UUID.randomUUID().toString());
            operationBuilder.setKey(new OperationKey(operationId));
            operationBuilder.setOperationId(operationId);
        }

        if (user.getObjects()!=null)
        {
            if (user.getObjects().getNode()!=null)
            {
                for(Node node : user.getObjects().getNode())
                {
                    if (node.getNodeName().getValue().equals(target))
                    {
                        TargetExist = true;
                        operationBuilder.setTargetObject(node.getNodeId());
                    }
                }
            }
            if (user.getObjects().getConnection()!=null)
            {
                for (Connection connection : user.getObjects().getConnection())
                {
                    if (connection.getConnectionName().getValue().equals(target))
                    {
                        TargetExist = true;
                        operationBuilder.setTargetObject(connection.getConnectionId());
                    }
                }
            }
            if (user.getObjects().getFlow()!=null)
            {
                for (Flow flow: user.getObjects().getFlow())
                {
                    if (flow.getFlowName().getValue().equals(target))
                    {
                        TargetExist = true;
                        operationBuilder.setTargetObject(flow.getFlowId());
                    }
                }
            }
        }
        if (!TargetExist)
        {
            errorInfo = "The target "+target+" is not exist in user vn space.";
            return errorInfo;
        }
        operationBuilder.setOperationName(new OperationName(operationname));
        operationBuilder.setPriority(Long.parseLong(priority));

        if (!conditions.isEmpty() && errorInfo==null)
        {
            List<ConditionSegment> conditionSegmentList = new ArrayList<ConditionSegment>();
            for (String conditionname : conditions.keySet())
            {
                String[] condition = null;
                condition = conditionname.split(",");
                ConditionSegmentBuilder conditionSegmentBuilder = new ConditionSegmentBuilder();
                ConditionSegmentId conditionSegmentId = new ConditionSegmentId(UUID.randomUUID().toString());
                conditionSegmentBuilder.setKey(new ConditionSegmentKey(conditionSegmentId));
                conditionSegmentBuilder.setConditionSegmentId(conditionSegmentId);
                //todo

            }
            operationBuilder.setConditionSegment(conditionSegmentList);
        }

        if (!actions.isEmpty() && errorInfo ==null)
        {
            List<Action> actionList = new ArrayList<Action>();
            int order = 0;
            for (String actionname : actions.keySet())
            {
                ActionBuilder actionBuilder = new ActionBuilder();
                actionBuilder.setKey(new ActionKey(new ActionName(actionname)));
                actionBuilder.setActionName(new ActionName(actionname));
                actionBuilder.setOrder((long) order);

                ParameterValuesBuilder parameterValuesBuilder = new ParameterValuesBuilder();
                LinkedHashMap<String, String> actionvalues = actions.get(actionname);
                List<IntValue> intValueList = new ArrayList<IntValue>();
                List<StringValue> stringValueList = new ArrayList<StringValue>();
                RangeValue rangeValue = null;

                int actionorder = 0;
                for (String actionvalue : actionvalues.keySet())
                {
                    if (actionvalues.get(actionvalue).equals("string"))
                    {
                        if (intValueList.isEmpty()&&rangeValue==null) {
                            Boolean ParaExist = false;
                            StringValueBuilder stringValueBuilder = new StringValueBuilder();
                            if (user.getObjects() != null) {
                                if (user.getObjects().getNode() != null) {
                                    for (Node node : user.getObjects().getNode()) {
                                        if (node.getNodeName().getValue().equals(actionvalue)) {
                                            ParaExist = true;
                                            stringValueBuilder.setKey(new StringValueKey((long) actionorder, node.getNodeId().getValue()));
                                            stringValueBuilder.setValue(node.getNodeId().getValue());
                                        }
                                    }
                                }
                                if (user.getObjects().getConnection() != null) {
                                    for (Connection connection : user.getObjects().getConnection()) {
                                        if (connection.getConnectionName().getValue().equals(actionvalue)) {
                                            ParaExist = true;
                                            stringValueBuilder.setKey(new StringValueKey((long) actionorder, connection.getConnectionId().getValue()));
                                            stringValueBuilder.setValue(connection.getConnectionId().getValue());
                                        }
                                    }
                                }
                                if (user.getObjects().getFlow() != null) {
                                    for (Flow flow : user.getObjects().getFlow()) {
                                        if (flow.getFlowName().getValue().equals(actionvalue)) {
                                            ParaExist = true;
                                            stringValueBuilder.setKey(new StringValueKey((long) actionorder, flow.getFlowId().getValue()));
                                            stringValueBuilder.setValue(flow.getFlowId().getValue());
                                        }
                                    }
                                }
                                if (!ParaExist)
                                {
                                    errorInfo = "The parameter object is not exist in user vn space.";
                                }

                                stringValueBuilder.setOrder((long) actionorder);
                                stringValueList.add(stringValueBuilder.build());
                                actionorder++;

                            }
                        }

                        else
                        {
                            errorInfo = "The parameter"+actionvalue +" in action should be string";
                            return errorInfo;
                        }
                    }
                    if (actionvalues.get(actionvalue).equals("int"))
                    {
                        if (stringValueList.isEmpty()&&rangeValue==null)
                        {
                            IntValueBuilder intValueBuilder = new IntValueBuilder();
                            intValueBuilder.setKey(new IntValueKey((long)actionorder,Long.parseLong(actionvalue)));
                            intValueBuilder.setOrder((long)actionorder);
                            intValueBuilder.setValue(Long.parseLong(actionvalue));
                            intValueList.add(intValueBuilder.build());
                            actionorder ++;
                        }
                        else
                        {
                            errorInfo = "The parameter"+actionvalue +" in action should be int";
                            return errorInfo;
                        }
                    }
                    if (actionvalues.get(actionvalue).equals("range"))
                    {
                        if (intValueList.isEmpty()&&stringValueList.isEmpty())
                        {
                            String[] rangevalue = new String[2];
                            rangevalue = actionvalue.split(",");
                            RangeValueBuilder rangeValueBuilder = new RangeValueBuilder();
                            if (Long.parseLong(rangevalue[0])<Long.parseLong(rangevalue[1]))
                            {
                                rangeValueBuilder.setMin(Long.parseLong(rangevalue[0]));
                                rangeValueBuilder.setMax(Long.parseLong(rangevalue[1]));
                            }
                            else
                            {
                                rangeValueBuilder.setMin(Long.parseLong(rangevalue[1]));
                                rangeValueBuilder.setMax(Long.parseLong(rangevalue[0]));
                            }
                            rangeValue = rangeValueBuilder.build();
                            actionorder ++;
                        }
                        else
                        {
                            errorInfo = "The parameter"+actionvalue +" in action should be range";
                            return errorInfo;
                        }
                    }
                }
                if (intValueList.isEmpty())
                {
                    intValueList = null;
                    parameterValuesBuilder.setIntValue(intValueList);
                }
                if (stringValueList.isEmpty())
                {
                    stringValueList = null;
                    parameterValuesBuilder.setStringValue(stringValueList);
                }
                parameterValuesBuilder.setIntValue(intValueList).setStringValue(stringValueList).setRangeValue(rangeValue);
                actionBuilder.setParameterValues(parameterValuesBuilder.build());
                actionList.add(actionBuilder.build());
            }
            operationBuilder.setAction(actionList);
        }

        setOperation(operationBuilder.build());
        return  errorInfo;
    }

    private void setOperation(Operation operation1)
    {
        this.operation = operation1;
    }

}
