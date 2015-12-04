/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.user.vnspacemanager.structurestyle.updateintent;

import com.google.common.base.Optional;
import com.google.common.util.concurrent.*;
import com.google.common.base.Optional;
import com.google.common.util.concurrent.CheckedFuture;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.WriteTransaction;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.controller.md.sal.common.api.data.TransactionCommitFailedException;
import org.opendaylight.nemo.user.tenantmanager.TenantManage;
import org.opendaylight.nemo.user.vnspacemanager.languagestyle.NEMOConstants;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.ActionName;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.ParameterName;
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
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.ActionDefinitions;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.ConditionParameterDefinitions;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by z00293636 on 2015/8/31.
 */
public class UpdateOperation {

    private DataBroker dataBroker;
    private TenantManage tenantManage;
    private final SettableFuture<List<ActionDefinition>> actionDefinitionListFuture =  SettableFuture.create();
    private final SettableFuture<List<ConditionParameterDefinition>> conditionParameterDefinitionListFuture = SettableFuture.create();
    private ValueCheck valueCheck;
    private static final Logger LOG = LoggerFactory.getLogger(UpdateOperation.class);

    public UpdateOperation(DataBroker dataBroker, TenantManage tenantManage){
        this.dataBroker = dataBroker;
        this.tenantManage = tenantManage;
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
        fetchActionDefinitions();
        fetchConditionParaDefinitions();
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

        fetchActionDefinitions();
        Map<ActionName, ActionDefinition> actionDefinitionMap = getActionDefinition();
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
        fetchConditionParaDefinitions();
        Map<ParameterName, ConditionParameterDefinition> conditionParameterDefinitionMap = getParameterMatchPattern();
        if (operation.getConditionSegment()!=null){
            if (conditionParameterDefinitionMap.isEmpty()){
                return "This condition has not been defined in data store.";
            }
            else {
                for (ConditionSegment conditionSegment :operation.getConditionSegment()){
                    if (conditionParameterDefinitionMap.containsKey(conditionSegment.getConditionParameterName())){
                        ConditionParameterDefinition conditionParameterDefinition = conditionParameterDefinitionMap.get(conditionSegment.getConditionParameterName());

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

    private void fetchActionDefinitions(){
        InstanceIdentifier<ActionDefinitions> actiondefinitionId = InstanceIdentifier.builder(ActionDefinitions.class).build();
        ListenableFuture<Optional<ActionDefinitions>> actiondefinitionFuture = dataBroker.newReadOnlyTransaction().read(LogicalDatastoreType.CONFIGURATION, actiondefinitionId);
        Futures.addCallback(actiondefinitionFuture, new FutureCallback<Optional<ActionDefinitions>>() {
            @Override
            public void onSuccess(Optional<ActionDefinitions> result) {
                setActionDefinitionListFuture( result.get().getActionDefinition());
            }

            @Override
            public void onFailure(Throwable t) {
                LOG.error("Can not read action definition information.", t);
            }
        });
        return ;
    }

    private void fetchConditionParaDefinitions(){
        InstanceIdentifier<ConditionParameterDefinitions> conditionparadefinitionId = InstanceIdentifier.builder(ConditionParameterDefinitions.class).build();
        ListenableFuture<Optional<ConditionParameterDefinitions>> conditionparadefinitionFuture = dataBroker.newReadOnlyTransaction().read(LogicalDatastoreType.CONFIGURATION, conditionparadefinitionId);
        Futures.addCallback(conditionparadefinitionFuture, new FutureCallback<Optional<ConditionParameterDefinitions>>() {
            @Override
            public void onSuccess(Optional<ConditionParameterDefinitions> result) {
               setConditionParameterDefinitionListFuture(result.get().getConditionParameterDefinition());
            }

            @Override
            public void onFailure(Throwable t) {
                LOG.error("Can not read condition parameter definition information.", t);
            }
        });
        return ;
    }

    private void setConditionParameterDefinitionListFuture(List<ConditionParameterDefinition> conditionParameterDefinitions){
        this.conditionParameterDefinitionListFuture.set(conditionParameterDefinitions);
    }

    private void setActionDefinitionListFuture(List<ActionDefinition> actionDefinitions){
        this.actionDefinitionListFuture.set(actionDefinitions);
    }

    private List<ConditionParameterDefinition> getConditionParameterDefinitionList(){
        try{
            return conditionParameterDefinitionListFuture.get(1, TimeUnit.SECONDS);
        }catch (InterruptedException | ExecutionException | TimeoutException e) {
            LOG.error("Cannot read role information.", e);
            return null;
        }
    }

    private List<ActionDefinition> getActionDefinitionList(){
        try{
            return actionDefinitionListFuture.get(1, TimeUnit.SECONDS);
        }catch (InterruptedException | ExecutionException | TimeoutException e) {
            LOG.error("Cannot read role information.", e);
            return null;
        }
    }

    private Map<ParameterName, ConditionParameterDefinition> getParameterMatchPattern(){
        List<ConditionParameterDefinition> conditionParameterDefinitions = getConditionParameterDefinitionList();
        Map<ParameterName, ConditionParameterDefinition> conditionParameterDefinitionMap = new HashMap<ParameterName, ConditionParameterDefinition>();
        if (conditionParameterDefinitions!=null){
            for (ConditionParameterDefinition conditionParameterDefinition : conditionParameterDefinitions){
                conditionParameterDefinitionMap.put(conditionParameterDefinition.getParameterName(),conditionParameterDefinition);
            }
        }
        return conditionParameterDefinitionMap;
    }

    private Map<ActionName, ActionDefinition> getActionDefinition(){
        List<ActionDefinition> actionDefinitions = getActionDefinitionList();
        Map<ActionName,ActionDefinition> actionDefinitionMap = new HashMap<ActionName, ActionDefinition>();
        if (actionDefinitionMap!=null){
            for (ActionDefinition actionDefinition : actionDefinitions){
                actionDefinitionMap.put(actionDefinition.getActionName(),actionDefinition);
            }
        }
        return actionDefinitionMap;
    }
}
