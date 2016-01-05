/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.user.vnspacemanager.languagestyle.updateintentlang;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.LinkedHashMap;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.nemo.user.tenantmanager.TenantManage;
import org.opendaylight.nemo.user.vnspacemanager.languagestyle.NEMOConstants;
import org.opendaylight.nemo.user.vnspacemanager.structurestyle.updateintent.UpdateFlow;
import org.opendaylight.nemo.user.vnspacemanager.structurestyle.updateintent.UpdateOperation;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Connection;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Flow;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Node;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.Operations;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.operations.Operation;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.operations.OperationBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.operations.OperationKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.User;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.ParameterValuesBuilder;
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
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.condition.instance.condition.segment.ConditionParameterTargetValueBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.operation.instance.Action;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.operation.instance.ActionBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.operation.instance.ActionKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.Operations;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.OperationName;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.OperationId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.NodeName;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.NodeId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.ConnectionName;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.ConnectionId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.FlowName;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.FlowId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.Objects;


/**
 * Created by Thomas Liu on 2015/12/15.
 */
public class UpdateOperationLangTest extends TestCase {

    private UpdateOperationLang updateOperationLangTest;
    private DataBroker dataBroker;
    private TenantManage tenantManage;
    private UserId userId;
    private String operationname;
    private String target;
    private String priority;
    private LinkedHashMap<String, LinkedHashMap<String,String>> conditions,actions;
    private LinkedHashMap<String,String> condition,action;
    private Operations operation;
    private List<Operation> operationList;
    private Objects object;
    private List<Flow> flows;
    private List<Node> nodes;
    private List<Connection> connections;
    private User user;
    private Method method;
    private Field field;
    private Class class1;


    @org.junit.Before
    public void setUp() throws Exception {
        dataBroker = mock(DataBroker.class);
        tenantManage = mock(TenantManage.class);
        updateOperationLangTest = new UpdateOperationLang(dataBroker,tenantManage);
        conditions = new LinkedHashMap<String, LinkedHashMap<String,String>>();
        actions = new LinkedHashMap<String, LinkedHashMap<String,String>>();
        operation = mock(Operations.class);
        operationList = new ArrayList<Operation>(){{add(mock(Operation.class));}};
        operationname = new String("o1");
        object = mock(Objects.class);
        flows = new ArrayList<Flow>(){{add(mock(Flow.class));}};
        nodes = new ArrayList<Node>(){{add(mock(Node.class));}};
        connections = new ArrayList<Connection>(){{add(mock(Connection.class));}};
        user = mock(User.class);
        conditions = new LinkedHashMap<String, LinkedHashMap<String,String>>();
        actions = new LinkedHashMap<String, LinkedHashMap<String,String>>();
        priority = "100";
        class1 = UpdateOperationLang.class;

    }

    @org.junit.Test
    public void testOperationHandling() throws Exception {
        field = class1.getDeclaredField("updateOperation");
        UpdateOperation updateOperation = mock(UpdateOperation.class);
        field.setAccessible(true);
        field.set(updateOperationLangTest,updateOperation);
        field = class1.getDeclaredField("operation");
        Operation operation2 = mock(Operation.class);
        field.setAccessible(true);
        field.set(updateOperationLangTest,operation2);

        condition = new LinkedHashMap<String,String>(){{
            put(new String("group"),NEMOConstants.string);
        }};
        conditions.put(",a,equal",condition);
        conditions.put("not,b,not_equal",condition);
        conditions.put("and,c,greater_than",condition);
        condition = new LinkedHashMap<String,String>(){{
            put(new String("100"),NEMOConstants.integer);
        }};
        conditions.put("or,d,less_than",condition);
        conditions.put("not,e,not_less_than",condition);
        conditions.put("and,f,not_greater_than",condition);
        condition = new LinkedHashMap<String,String>(){{
            put(new String("100,200"),NEMOConstants.range);
        }};
        conditions.put("or,g,between",condition);
        condition = new LinkedHashMap<String,String>(){{
            put(new String("200,100"),NEMOConstants.range);
        }};
        conditions.put("or,g,between",condition);
        action = new LinkedHashMap<String,String>(){{
            //put(new String("1"),NEMOConstants.range);
            //put(new String("group"),NEMOConstants.string);
            //put(new String("100"),NEMOConstants.integer);
            put(new String("100,200"),NEMOConstants.range);
            put(new String("200,100"),NEMOConstants.range);
        }};
        actions.put(new String("action1"),action);

        HashMap<OperationId,Operation> operationMap = new HashMap<OperationId,Operation>();
        OperationId operationId = new OperationId("11111111-1111-1111-1111-111111111111");
        Operation operation = mock(Operation.class);
        operationMap.put(operationId,operation);
        when(tenantManage.getObjectId(userId,operationname)).thenReturn("11111111-1111-1111-1111-111111111111");
        when(tenantManage.getObjectId(userId,operationname)).thenReturn("11111111-1111-1111-1111-111111111111");
        when(tenantManage.getOperation(userId)).thenReturn(operationMap);
        when(tenantManage.getOperation(userId)).thenReturn(operationMap);

        //if (tenantManage.getObjectId(userId,target)!=null)
        when(tenantManage.getObjectId(userId,target)).thenReturn("11111111-1111-1111-1111-111111111111");
        when(tenantManage.getObjectId(userId, target)).thenReturn("11111111-1111-1111-1111-111111111111");
        //test createCondition
        //test createAction
        when(updateOperation.OperationHandling(userId,operation)).thenReturn("OperationHandling");
        System.out.println("****************************************** Result1: "+updateOperationLangTest.OperationHandling(userId, operationname, target, priority, conditions,actions));


        when(tenantManage.getObjectId(userId,operationname)).thenReturn(null);
        when(tenantManage.getObjectId(userId,target)).thenReturn(null);
        System.out.println("****************************************** Result2: "+updateOperationLangTest.OperationHandling(userId, operationname, target, priority, conditions,actions));

        when(tenantManage.getObjectId(userId, operationname)).thenReturn("11111111-1111-1111-1111-111111111111");
        when(tenantManage.getObjectId(userId,operationname)).thenReturn("11111111-1111-1111-1111-111111111111");
        operationMap.clear();
        when(tenantManage.getOperation(userId)).thenReturn(operationMap);
        operationMap.put(operationId,operation);
        when(tenantManage.getOperationDataStore(userId)).thenReturn(operationMap);
        when(tenantManage.getOperationDataStore(userId)).thenReturn(operationMap);
        when(tenantManage.getObjectId(userId,target)).thenReturn(null);
        System.out.println("****************************************** Result3: "+updateOperationLangTest.OperationHandling(userId, operationname, target, priority, conditions,actions));

        conditions.clear();
        actions.clear();
        when(tenantManage.getObjectId(userId, operationname)).thenReturn(null);
        when(tenantManage.getObjectId(userId,target)).thenReturn("11111111-1111-1111-1111-111111111111");
        when(updateOperation.OperationHandling(userId,operation)).thenReturn("OperationHandling");
        System.out.println("****************************************** Result4: "+updateOperationLangTest.OperationHandling(userId, operationname, target, priority, conditions,actions));
    }
    @org.junit.Test
    public void testcreateAction() throws Exception {
        method = class1.getDeclaredMethod("createAction",new Class[]{
                UserId.class,
                LinkedHashMap.class,
        });
        method.setAccessible(true);

        actions.clear();
        action = new LinkedHashMap<String,String>(){{
            put(new String("100"),NEMOConstants.integer);
            put(new String("group"),NEMOConstants.string);
        }};
        actions.put(new String("action1"),action);
        System.out.println("****************************************** Result5: "+method.invoke(updateOperationLangTest,userId,actions));

        actions.clear();
        action = new LinkedHashMap<String,String>(){{
            put(new String("group"),NEMOConstants.string);
            put(new String("100"),NEMOConstants.integer);
        }};
        actions.put(new String("action1"),action);
        System.out.println("****************************************** Result6: "+method.invoke(updateOperationLangTest,userId,actions));

        actions.clear();
        action = new LinkedHashMap<String,String>(){{
            put(new String("100"),NEMOConstants.integer);
            put(new String("100,200"),NEMOConstants.range);

        }};
        actions.put(new String("action1"),action);
        System.out.println("****************************************** Result7: "+method.invoke(updateOperationLangTest,userId,actions));

        actions.clear();
        action = new LinkedHashMap<String,String>(){{
            put(new String("group"),NEMOConstants.string);
            put(new String("200,100"),NEMOConstants.range);
        }};
        actions.put(new String("action1"),action);
        System.out.println("****************************************** Result8: "+method.invoke(updateOperationLangTest,userId,actions));

        actions.clear();
        action = new LinkedHashMap<String,String>(){{
            put(new String("100,200"),NEMOConstants.range);
            put(new String("100"),NEMOConstants.integer);
        }};
        actions.put(new String("action1"),action);
        System.out.println("****************************************** Result9: "+method.invoke(updateOperationLangTest,userId,actions));

        actions.clear();
        action = new LinkedHashMap<String,String>(){{
            put(new String("100,200"),NEMOConstants.range);
            put(new String("group"),NEMOConstants.string);
        }};
        actions.put(new String("action1"),action);
        System.out.println("****************************************** Result10: "+method.invoke(updateOperationLangTest,userId,actions));
    }
}

