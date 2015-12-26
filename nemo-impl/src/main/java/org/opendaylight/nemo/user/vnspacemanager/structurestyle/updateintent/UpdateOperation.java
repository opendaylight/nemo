/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.user.vnspacemanager.structurestyle.updateintent;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.nemo.user.tenantmanager.TenantManage;
import org.opendaylight.nemo.user.vnspacemanager.languagestyle.NEMOConstants;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.operations.Operation;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.definitions.ActionDefinition;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.ParameterValues;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.condition.instance.ConditionSegment;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.condition.instance.condition.segment.ConditionParameterTargetValue;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.condition.parameter.definitions.ConditionParameterDefinition;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.condition.parameter.definitions.condition.parameter.definition.ParameterMatchPatterns;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.operation.instance.Action;

import java.util.List;
import java.util.Map;

/**
 * Created by z00293636 on 2015/8/31.
 */
public class UpdateOperation {
    private TenantManage tenantManage;
    private GetDefinitions getDefinitions;
    private ValueCheck valueCheck;

    public UpdateOperation(DataBroker dataBroker, TenantManage tenantManage){
        this.tenantManage = tenantManage;
        getDefinitions = new GetDefinitions(dataBroker);
        valueCheck = new ValueCheck();
    }

    public String OperationHandling(UserId userId, Operation operation){
        String errorInfo = null;
        errorInfo = checkDefinition(operation);
        if (errorInfo!=null){
            return errorInfo;
        }
        else {
            errorInfo = checkInstance(userId,operation);
            if (errorInfo!=null){
                return errorInfo;
            }
            else {
                tenantManage.setOperation(userId,operation.getOperationId(),operation);
            }
        }
        return null;
    }

    private String checkInstance(UserId userId, Operation operation){
        if (operation.getPriority()==null){
            return  "The priority should not be empty.";
        }
        if (operation.getTargetObject()==null){
            return  "The target should not be empty.";
        }

        if (tenantManage.getOperation(userId)!=null){
            if (tenantManage.getOperation(userId).containsKey(operation.getOperationId())){
                Operation operationExist = tenantManage.getOperation(userId).get(operation.getOperationId());
                if (!operationExist.getOperationName().equals(operation.getOperationName())){
                    return "The operation name should not be changed.";
                }
            }
        }
        if (tenantManage.getOperationDataStore(userId)!=null){
            if (tenantManage.getOperationDataStore(userId).containsKey(operation.getOperationId())){
                Operation operationExist = tenantManage.getOperationDataStore(userId).get(operation.getOperationId());
                if (!operationExist.getOperationName().equals(operation.getOperationName())){
                    return "The operation name should not be changed.";
                }
            }
        }

        Boolean targetExist = false;
        if (tenantManage.getNode(userId)!=null){
            if (tenantManage.getNode(userId).containsKey(new NodeId(operation.getTargetObject()))){
                targetExist = true;
            }
        }
        if (tenantManage.getConnection(userId)!=null){
            if (tenantManage.getConnection(userId).containsKey(new ConnectionId(operation.getTargetObject()))){
                targetExist = true;
            }
        }
        if (tenantManage.getFlow(userId)!=null){
            if (tenantManage.getFlow(userId).containsKey(new FlowId(operation.getTargetObject()))){
                targetExist = true;
            }
        }

        if (tenantManage.getNodeDataStore(userId)!=null){
            if (tenantManage.getNodeDataStore(userId).containsKey(new NodeId(operation.getTargetObject()))){
                targetExist = true;
            }
        }
        if (tenantManage.getConnectionDataStore(userId)!=null){
            if (tenantManage.getConnectionDataStore(userId).containsKey(new ConnectionId(operation.getTargetObject()))){
                targetExist = true;
            }
        }
        if (tenantManage.getFlowDataStore(userId)!=null){
            if (tenantManage.getFlowDataStore(userId).containsKey(new FlowId(operation.getTargetObject()))){
                targetExist = true;
            }
        }

        if (!targetExist){
            return "The target " + operation.getTargetObject().getValue() + " is not exist.";
        }
        return null;
    }

    private String checkDefinition(Operation operation){
        String errorInfo = null;

        if (operation.getAction() != null ){
            errorInfo = checkAction(operation);
        }
        if (errorInfo == null && operation.getConditionSegment() != null){
            errorInfo = checkCondition(operation);
        }
        return errorInfo;
    }

    private String checkAction(Operation operation){
        String errorInfo = null;
        Map<ActionName, ActionDefinition> actionDefinitionMap = getDefinitions.getActionDefinition();
        if (operation.getAction()!=null){
            if (actionDefinitionMap.isEmpty()){
                return "The action type has not been defined.";
            }
            else {
                for (Action action : operation.getAction()){
                    if (actionDefinitionMap.containsKey(action.getActionName())){
                        ParameterValues parameterValues = action.getParameterValues();
                        ActionDefinition.ParameterValueType parameterValueType = actionDefinitionMap.get(action.getActionName()).getParameterValueType();

                        if (parameterValues != null && parameterValueType != null)
                        {
                            if (parameterValueType.getIntValue() == 0 && !(parameterValues.getIntValue() == null && parameterValues.getStringValue() != null && parameterValues.getRangeValue() == null)) {
                                errorInfo = "The value type of" + action.getActionName().toString() + "should be string.";
                                break;
                            }
                            if (parameterValueType.getIntValue() == 1 && !(parameterValues.getIntValue() != null && parameterValues.getStringValue() == null && parameterValues.getRangeValue() == null)) {
                                errorInfo = "The value type of" + action.getActionName().toString() + "should be integer.";
                                break;
                            }
                            if (parameterValueType.getIntValue() == 2 && !(parameterValues.getIntValue() == null && parameterValues.getStringValue() == null && parameterValues.getRangeValue() != null)) {
                                errorInfo = "The value type of" + action.getActionName().toString() + "should be range.";
                                break;
                            }
                        }
                    }
                    else {
                        return "The action type has not been defined.";
                    }
                }
            }
        }
        return errorInfo;
    }

    private String checkCondition(Operation operation){
        String errorInfo = null;
        Map<ParameterName, ConditionParameterDefinition> conditionParameterDefinitionMap = getDefinitions.getConditionParameterDefinition();
        if (operation.getConditionSegment()!=null){
            if (conditionParameterDefinitionMap.isEmpty()){
                return "This condition has not been defined in data store.";
            }
            else {
                for (ConditionSegment conditionSegment :operation.getConditionSegment()){
                    if (conditionParameterDefinitionMap.containsKey(new ParameterName(conditionSegment.getConditionParameterName().getValue()))){
//                    if (conditionParameterDefinitionMap.containsKey(conditionSegment.getConditionParameterName())){
                        ConditionParameterDefinition conditionParameterDefinition = conditionParameterDefinitionMap.get(new ParameterName(conditionSegment.getConditionParameterName().getValue()));

                        if (conditionSegment.getConditionParameterMatchPattern() != null)
                        {
                            if (conditionParameterDefinition.getParameterMatchPatterns() != null)
                            {
                                List<ParameterMatchPatterns.ParameterMatchPattern> conditionParameterMatchPatterns = conditionParameterDefinition.getParameterMatchPatterns().getParameterMatchPattern();
                                if (conditionParameterMatchPatterns != null)
                                {
                                    Boolean matchpatternexist = false;
                                    for (ParameterMatchPatterns.ParameterMatchPattern parameterMatchPattern : conditionParameterMatchPatterns)
                                    {
                                        if (parameterMatchPattern.getIntValue() == conditionSegment.getConditionParameterMatchPattern().getIntValue())
                                        {
                                            matchpatternexist = true;
                                            break;
                                        }
                                    }
                                    if (!matchpatternexist)
                                    {
                                        errorInfo = "The match pattern has not defined in the condition.";
                                    }
                                }
                                else
                                {
                                    errorInfo = "There are no match pattern in match pattrn list.";
                                    break;
                                }
                            }
                            else
                            {
                                errorInfo = "No match patterns have been defined in data store.";
                                break;
                            }
                        }
                        if (conditionSegment.getConditionParameterTargetValue() != null)
                        {
                            if (conditionParameterDefinition.getParameterValueType() != null)
                            {
                                ConditionParameterTargetValue conditionParameterTargetValue = conditionSegment.getConditionParameterTargetValue();
                                ConditionParameterDefinition.ParameterValueType  parameterValueType = conditionParameterDefinition.getParameterValueType();

                                if (parameterValueType.getIntValue() == 0 && !(conditionParameterTargetValue.getIntValue() == null && conditionParameterTargetValue.getStringValue() != null && conditionParameterTargetValue.getRangeValue() == null)) {
                                    errorInfo = "The value type of " + conditionSegment.getConditionParameterName().getValue() + " should be string.";
                                    break;
                                }
                                if (parameterValueType.getIntValue() == 1 && !(conditionParameterTargetValue.getIntValue() != null && conditionParameterTargetValue.getStringValue() == null && conditionParameterTargetValue.getRangeValue() == null)) {
                                    errorInfo = "The value type of " + conditionSegment.getConditionParameterName().getValue() + " should be integer.";
                                    break;
                                }
                                if (parameterValueType.getIntValue() == 2 && !(conditionParameterTargetValue.getIntValue() == null && conditionParameterTargetValue.getStringValue() == null && conditionParameterTargetValue.getRangeValue() != null)) {
                                    errorInfo = "The value type of " + conditionSegment.getConditionParameterName().getValue() + " should be range.";
                                    break;
                                }

                            }
                        }
                        if (conditionSegment.getConditionParameterName().getValue().equals(NEMOConstants.time)){
                            Boolean legalValue = valueCheck.checkTime(conditionSegment.getConditionParameterTargetValue().getStringValue());
                            if (!legalValue){
                                errorInfo = "The " + NEMOConstants.time + " is not legal.";
                                break;
                            }
                        }
                    }
                    else {
                        return "This condition has not been defined in data store.";
                    }
                }
            }
        }
        return errorInfo;
    }
}
