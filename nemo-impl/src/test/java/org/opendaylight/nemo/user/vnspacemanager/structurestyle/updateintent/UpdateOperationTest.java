/*
 * Copyright (c) 2016 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.user.vnspacemanager.structurestyle.updateintent;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.CheckedFuture;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.WriteTransaction;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.controller.md.sal.common.api.data.TransactionCommitFailedException;
import org.opendaylight.nemo.user.vnspacemanager.languagestyle.NEMOConstants;
import org.opendaylight.nemo.user.vnspacemanager.languagestyle.NEMOParse.NEMOparserConstants;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.RegisterUserInput;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.Users;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.Objects;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.Operations;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.TemplateDefinitions;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.TemplateInstances;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.operations.Operation;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.operations.OperationKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.template.definitions.TemplateDefinition;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.template.definitions.TemplateDefinitionKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.template.instances.TemplateInstance;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.template.instances.TemplateInstanceKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.User;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.UserBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.UserKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.user.rev151010.UserRoles;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.condition.instance.ConditionSegment.ConditionParameterMatchPattern;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.user.rev151010.user.roles.UserRole;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.ConditionParameterName;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ExecutionException;
import static org.junit.Assert.*;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.nemo.user.tenantmanager.TenantManage;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.OperationName;
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
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.ObjectId;
import java.lang.reflect.Method;
import java.util.Map;
import static org.mockito.Mockito.*;
/**
 * Created by zhangmeng on 2016/1/13.
 */
public class UpdateOperationTest extends TestCase {
    private DataBroker dataBroker;
    private TenantManage tenantManage;
    private UpdateOperation updateOperation;
    private Class<UpdateOperation> class1;
    private Field field_getDefinitions;
    private Field field_valueCheck;
    @Before
    public void setUp() throws Exception {
        class1 = UpdateOperation.class;
        field_getDefinitions = class1.getDeclaredField("getDefinitions");
        field_getDefinitions.setAccessible(true);
        field_valueCheck = class1.getDeclaredField("valueCheck");
        field_valueCheck.setAccessible(true);

        dataBroker = mock(DataBroker.class);
        tenantManage = mock(TenantManage.class);

        updateOperation = new UpdateOperation(dataBroker,tenantManage);
    }

    @Test
    public void testOperationHandling() throws Exception {
        UserId userId = mock(UserId.class);
        Operation operation = mock(Operation.class);
        GetDefinitions getDefinitions = mock(GetDefinitions.class);


        field_getDefinitions.set(updateOperation, getDefinitions);

        //get into method "checkDefinition" arg(operation)
        ////get into method "checkAction" arg(operation)
        when(operation.getAction())
                .thenReturn(mock(List.class))
                .thenReturn(null);
        when(getDefinitions.getActionDefinition()).thenReturn(null);
        ////get into method "checkCondition" arg(operation)
        when(operation.getConditionSegment())
                .thenReturn(mock(List.class))
                .thenReturn(null);
        when(getDefinitions.getConditionParameterDefinition()).thenReturn(null);

        //get into method "checkInstance" arg(userId,operation)
        when(operation.getPriority()).thenReturn(null);
        Assert.assertTrue(updateOperation.OperationHandling(userId, operation).equals("The priority should not be empty."));
    }

    @Test
    public void testCheckInstance() throws Exception {
        Method method = class1.getDeclaredMethod("checkInstance", new Class[]{
                UserId.class,
                Operation.class
        });
        method.setAccessible(true);

        UserId userId = mock(UserId.class);
        ObjectId objectId = mock(ObjectId.class);
        Operation operation = mock(Operation.class);
        Operation operation1 = mock(Operation.class);
        Map<NodeId, Node> nodeMap = mock(Map.class);
        Map<FlowId,Flow> flowMap = mock(Map.class);
        Map<ConnectionId, Connection> connectionMap = mock(Map.class);
        Map<OperationId, Operation> operationMap = mock(Map.class);

        when(operation.getPriority())
                .thenReturn(null)
                .thenReturn(1L);
        Assert.assertTrue(method.invoke(updateOperation, userId, operation).equals("The priority should not be empty."));
        verify(operation).getPriority();
        when(operation.getTargetObject())
                .thenReturn(null)
                .thenReturn(objectId);
        Assert.assertTrue(method.invoke(updateOperation, userId, operation).equals("The target should not be empty."));
        verify(operation).getTargetObject();

        when(tenantManage.getOperation(userId))
                .thenReturn(operationMap)
                .thenReturn(operationMap)
                .thenReturn(operationMap)
                .thenReturn(null);
        when(operation.getOperationId()).thenReturn(mock(OperationId.class));
        when(operationMap.containsKey(operation.getOperationId())).thenReturn(true);
        when(operationMap.get(operation.getOperationId())).thenReturn(operation1);
        when(operation.getOperationName()).thenReturn(mock(OperationName.class));
        when(operation1.getOperationName()).thenReturn(mock(OperationName.class));
        Assert.assertTrue(method.invoke(updateOperation, userId, operation).equals("The operation name should not be changed."));

        when(tenantManage.getOperationDataStore(userId))
                .thenReturn(operationMap)
                .thenReturn(operationMap)
                .thenReturn(operationMap)
                .thenReturn(null);
        Assert.assertTrue(method.invoke(updateOperation, userId, operation).equals("The operation name should not be changed."));
        verify(operation1,times(2)).getOperationName();
        Assert.assertTrue(method.invoke(updateOperation, userId, operation).equals("The target " + operation.getTargetObject().getValue() + " is not exist."));

        when(tenantManage.getNode(userId)).thenReturn(nodeMap);
        when(tenantManage.getConnection(userId)).thenReturn(connectionMap);
        when(tenantManage.getFlow(userId)).thenReturn(flowMap);
        when(tenantManage.getNodeDataStore(userId)).thenReturn(nodeMap);
        when(tenantManage.getConnectionDataStore(userId)).thenReturn(connectionMap);
        when(tenantManage.getFlowDataStore(userId)).thenReturn(flowMap);
        when(nodeMap.containsKey(new NodeId(operation.getTargetObject()))).thenReturn(true);
        when(connectionMap.containsKey(new ConnectionId(operation.getTargetObject()))).thenReturn(true);
        when(flowMap.containsKey(new FlowId(operation.getTargetObject()))).thenReturn(true);
        Assert.assertTrue(method.invoke(updateOperation, userId, operation) == null);
        verify(nodeMap,times(2)).containsKey(new NodeId(operation.getTargetObject()));
        verify(connectionMap,times(2)).containsKey(new ConnectionId(operation.getTargetObject()));
        verify(flowMap,times(2)).containsKey(new FlowId(operation.getTargetObject()));
    }

    @Test
    public void testCheckAction() throws Exception {
        Method method = class1.getDeclaredMethod("checkAction", new Class[]{Operation.class});
        method.setAccessible(true);

        Action action = mock(Action.class);
        ActionName actionName = mock(ActionName.class);
        Operation operation = mock(Operation.class);
        ParameterValues parameterValues = mock(ParameterValues.class);
        GetDefinitions getDefinitions = mock(GetDefinitions.class);
        ActionDefinition.ParameterValueType parameterValueType = ActionDefinition.ParameterValueType.Int;
        List<Action> actionList = new ArrayList<Action>();
        Map<ActionName, ActionDefinition> actionDefinitionMap = mock(Map.class);

        field_getDefinitions.set(updateOperation, getDefinitions);
        actionList.add(action);

        when(getDefinitions.getActionDefinition()).thenReturn(actionDefinitionMap);
        when(operation.getAction())
                .thenReturn(null)
                .thenReturn(actionList);
        //test null
        Assert.assertTrue(method.invoke(updateOperation, operation) == null);
        verify(getDefinitions).getActionDefinition();

        //test empty
        when(actionDefinitionMap.isEmpty())
                .thenReturn(true)
                .thenReturn(false);
        Assert.assertTrue(method.invoke(updateOperation, operation).equals("The action type has not been defined."));
        verify(getDefinitions, times(2)).getActionDefinition();
        //test not empty
        when(action.getActionName()).thenReturn(actionName);
        when(actionDefinitionMap.containsKey(action.getActionName()))
                .thenReturn(false)
                .thenReturn(true);
        Assert.assertTrue(method.invoke(updateOperation, operation).equals("The action type has not been defined."));
        verify(actionDefinitionMap).containsKey(action.getActionName());

        when(action.getParameterValues()).thenReturn(parameterValues);
        when(actionDefinitionMap.get(action.getActionName())).thenReturn(mock(ActionDefinition.class));
        when(actionDefinitionMap.get(action.getActionName()).getParameterValueType())
                .thenReturn(ActionDefinition.ParameterValueType.String)
                .thenReturn(parameterValueType)
                .thenReturn(ActionDefinition.ParameterValueType.Range);
        when(parameterValues.getIntValue()).thenReturn(null);
        when(parameterValues.getStringValue()).thenReturn(null);
        when(parameterValues.getRangeValue()).thenReturn(null);
        //org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.IntValue
        Assert.assertTrue(method.invoke(updateOperation, operation).equals("The value type of" + action.getActionName().toString() + "should be string."));
        Assert.assertTrue(method.invoke(updateOperation, operation).equals("The value type of" + action.getActionName().toString() + "should be integer."));
        Assert.assertTrue(method.invoke(updateOperation, operation).equals("The value type of" + action.getActionName().toString() + "should be range."));

    }

    @Test
    public void testCheckCondition() throws Exception {
        Method method = class1.getDeclaredMethod("checkCondition", new Class[]{Operation.class});
        method.setAccessible(true);

        ValueCheck valueCheck = mock(ValueCheck.class);
        Operation operation = mock(Operation.class);
        ConditionSegment conditionSegment = mock(ConditionSegment.class);
        ConditionParameterName conditionParameterName = mock(ConditionParameterName.class);
        GetDefinitions getDefinitions = mock(GetDefinitions.class);
        ConditionParameterTargetValue conditionParameterTargetValue = mock(ConditionParameterTargetValue.class);
        ParameterMatchPatterns parameterMatchPatterns = mock(ParameterMatchPatterns.class);
        ConditionParameterMatchPattern conditionParameterMatchPattern = ConditionParameterMatchPattern.Equal;
        ConditionParameterDefinition conditionParameterDefinition = mock(ConditionParameterDefinition.class);
        List<ParameterMatchPatterns.ParameterMatchPattern> conditionParameterMatchPatterns = new ArrayList<ParameterMatchPatterns.ParameterMatchPattern>();
        List<ConditionSegment> conditionSegmentList = new ArrayList<ConditionSegment>();
        Map<ParameterName, ConditionParameterDefinition> conditionParameterDefinitionMap = mock(Map.class);

        field_getDefinitions.set(updateOperation, getDefinitions);
        field_valueCheck.set(updateOperation, valueCheck);
        conditionSegmentList.add(conditionSegment);
        conditionParameterMatchPatterns.add(ParameterMatchPatterns.ParameterMatchPattern.NotEqual);

        when(getDefinitions.getConditionParameterDefinition()).thenReturn(conditionParameterDefinitionMap);
        when(operation.getConditionSegment())
                .thenReturn(null)
                .thenReturn(conditionSegmentList);
        Assert.assertTrue(method.invoke(updateOperation, operation) == null);

        when(conditionParameterDefinitionMap.isEmpty())
                .thenReturn(true)
                .thenReturn(false);
        Assert.assertTrue(method.invoke(updateOperation, operation).equals("This condition has not been defined in data store."));

        when(conditionSegment.getConditionParameterName()).thenReturn(conditionParameterName);
        when(conditionParameterName.getValue()).thenReturn(NEMOConstants.time);
        when(conditionParameterDefinitionMap.containsKey(new ParameterName(conditionSegment.getConditionParameterName().getValue())))
                .thenReturn(false)
                .thenReturn(true);
        Assert.assertTrue(method.invoke(updateOperation, operation).equals("This condition has not been defined in data store."));

        when(conditionParameterDefinitionMap.get(new ParameterName(conditionSegment.getConditionParameterName().getValue())))
                .thenReturn(conditionParameterDefinition);
        when(conditionSegment.getConditionParameterMatchPattern()).thenReturn(conditionParameterMatchPattern);
        when(conditionParameterDefinition.getParameterMatchPatterns())
                .thenReturn(null)
                .thenReturn(parameterMatchPatterns);
        Assert.assertTrue(method.invoke(updateOperation, operation).equals("No match patterns have been defined in data store."));

        when(conditionParameterDefinition.getParameterMatchPatterns().getParameterMatchPattern())
                .thenReturn(null)
                .thenReturn(conditionParameterMatchPatterns);
        when(conditionSegment.getConditionParameterTargetValue()).thenReturn(conditionParameterTargetValue);
        when(conditionSegment.getConditionParameterTargetValue().getStringValue()).thenReturn("test");
        when(valueCheck.checkTime(conditionSegment.getConditionParameterTargetValue().getStringValue())).thenReturn(false);
        Assert.assertTrue(method.invoke(updateOperation, operation).equals("There are no match pattern in match pattrn list."));
        Assert.assertTrue(method.invoke(updateOperation, operation).equals("The " + NEMOConstants.time + " is not legal."));

        conditionParameterMatchPatterns.clear();
        conditionParameterMatchPatterns.add(ParameterMatchPatterns.ParameterMatchPattern.Equal);
        Assert.assertTrue(method.invoke(updateOperation, operation).equals("The " + NEMOConstants.time + " is not legal."));

        when(conditionParameterDefinition.getParameterValueType())
                .thenReturn(ConditionParameterDefinition.ParameterValueType.String)
                .thenReturn(ConditionParameterDefinition.ParameterValueType.String)
                .thenReturn(ConditionParameterDefinition.ParameterValueType.Int)
                .thenReturn(ConditionParameterDefinition.ParameterValueType.Int)
                .thenReturn(ConditionParameterDefinition.ParameterValueType.Range)
                .thenReturn(ConditionParameterDefinition.ParameterValueType.Range);
        when(conditionParameterTargetValue.getIntValue()).thenReturn(null);
        when(conditionParameterTargetValue.getStringValue()).thenReturn(null);
        when(conditionParameterTargetValue.getRangeValue()).thenReturn(null);
//        System.out.println(method.invoke(updateOperation, operation));
//        System.out.println(method.invoke(updateOperation, operation));
//        System.out.println(method.invoke(updateOperation, operation));
        Assert.assertTrue(method.invoke(updateOperation, operation).equals("The value type of " + conditionSegment.getConditionParameterName().getValue() + " should be string."));
        Assert.assertTrue(method.invoke(updateOperation, operation).equals("The value type of " + conditionSegment.getConditionParameterName().getValue() + " should be integer."));
        Assert.assertTrue(method.invoke(updateOperation, operation).equals("The value type of " + conditionSegment.getConditionParameterName().getValue() + " should be range."));

    }
}