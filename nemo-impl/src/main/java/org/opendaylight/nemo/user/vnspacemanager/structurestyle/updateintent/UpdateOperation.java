/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.user.vnspacemanager.structurestyle.updateintent;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.WriteTransaction;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.controller.md.sal.common.api.data.TransactionCommitFailedException;
import org.opendaylight.nemo.user.tenantmanager.TenantManage;
import org.opendaylight.nemo.user.vnspacemanager.languagestyle.NEMOConstants;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.ActionName;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.Users;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.Operations;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Connection;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Flow;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Node;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.operations.Operation;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.operations.OperationBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.operations.OperationKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.User;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.UserKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.definitions.ActionDefinition;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.ParameterValues;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.condition.instance.ConditionSegment;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.condition.instance.condition.segment.ConditionParameterTargetValue;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.condition.parameter.definitions.ConditionParameterDefinition;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.condition.parameter.definitions.condition.parameter.definition.ParameterMatchPatterns;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.operation.instance.Action;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.util.concurrent.CheckedFuture;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;

/**
 * Created by z00293636 on 2015/8/31.
 */
public class UpdateOperation {

    private DataBroker dataBroker;
    private TenantManage tenantManage;
    private GetDefinitions getDefinitions;
    private ValueCheck valueCheck;
    private static final Logger LOG = LoggerFactory.getLogger(UpdateOperation.class);

    public UpdateOperation(DataBroker dataBroker, TenantManage tenantManage){
        this.dataBroker = dataBroker;
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
                WriteTransaction t = dataBroker.newWriteOnlyTransaction();
                if (userId != null && operation.getOperationId() != null)
                {
                    Operation operation1 = new OperationBuilder(operation).build();
                    OperationKey operationKey = new OperationKey(operation.getOperationId());

                    UserKey userKey = new UserKey(userId);

                    InstanceIdentifier<Operation> operationid = InstanceIdentifier.builder(Users.class).child(User.class, userKey).child(Operations.class).child(Operation.class,operationKey).build();
                    t.put(LogicalDatastoreType.CONFIGURATION, operationid, operation1,true);
                    CheckedFuture<Void, TransactionCommitFailedException> f = t.submit();
                    Futures.addCallback(f, new FutureCallback<Void>() {
                        @Override
                        public void onFailure(Throwable t) {
                            LOG.error("Could not write endpoint base container", t);
                        }

                        @Override
                        public void onSuccess(Void result) {

                        }
                    });

                    try {
                        f.get();
                    } catch (InterruptedException | ExecutionException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }

    private String checkInstance(UserId userId, Operation operation){
        String errorInfo = null;
        tenantManage.fetchVNSpace(userId);
        User user = tenantManage.getUser();

        if (operation.getPriority()==null){
            errorInfo = "The priority should not be empty.";
        }
        if (operation.getTargetObject()==null){
            errorInfo = "The target should not be empty.";
        }
        if (operation.getTargetObject()!=null){
            if (user!=null){
                if (user.getObjects()!=null){
                    Boolean targetExist = false;
                    if (user.getObjects().getNode()!=null){
                        List<Node> nodeList = user.getObjects().getNode();
                        for (Node node : nodeList){
                            if (node.getNodeId().getValue().equals(operation.getTargetObject().getValue())){
                                targetExist = true;
                            }
                        }
                    }
                    if (user.getObjects().getConnection()!=null){
                        List<Connection> connectionList = user.getObjects().getConnection();
                        for (Connection connection : connectionList){
                            if (connection.getConnectionId().getValue().equals(operation.getTargetObject().getValue())){
                                targetExist = true;
                            }
                        }
                    }
                    if (user.getObjects().getFlow()!=null){
                        List<Flow> flowList = user.getObjects().getFlow();
                        for (Flow flow : flowList){
                            if (flow.getFlowId().getValue().equals(operation.getTargetObject().getValue())){
                                targetExist = true;
                            }
                        }
                    }
                    if (!targetExist){
                        errorInfo = "The target object is not exist.";
                    }
                }
                else {
                    errorInfo = "The target object is not exist.";
                }
            }
            else {
                errorInfo = "The target object is not exist.";
            }
        }
        if (user!=null){
            if (user.getOperations()!=null){
                if (user.getOperations().getOperation()!=null){
                    List<Operation> operationList = user.getOperations().getOperation();
                    for (Operation operation1 : operationList){
                        if (operation.getOperationId().equals(operation1.getOperationId())){
                            if (!operation.getOperationName().equals(operation1.getOperationName())){
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

    private String checkDefinition(Operation operation){
        String errorInfo = null;

        if (operation.getAction() != null )
        {
            errorInfo = checkAction(operation);
        }
        if (errorInfo == null && operation.getConditionSegment() != null)
        {
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
        Map<String, ConditionParameterDefinition> conditionParameterDefinitionMap = getDefinitions.getConditionParameterDefinition();
        if (operation.getConditionSegment()!=null){
            if (conditionParameterDefinitionMap.isEmpty()){
                return "This condition has not been defined in data store.";
            }
            else {
                for (ConditionSegment conditionSegment :operation.getConditionSegment()){
                    if (conditionParameterDefinitionMap.containsKey(conditionSegment.getConditionParameterName().getValue())){
                        ConditionParameterDefinition conditionParameterDefinition = conditionParameterDefinitionMap.get(conditionSegment.getConditionParameterName().getValue());

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
