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
//import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.lang.reflect.Method; 
import java.util.*;
import org.junit.Assert;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.nemo.user.tenantmanager.TenantManage;
import org.opendaylight.nemo.user.vnspacemanager.languagestyle.NEMOConstants;
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

    }

    @org.junit.Test
    public void testOperationHandling() throws Exception {

        doNothing().when(tenantManage).fetchVNSpace(userId);
        when(tenantManage.getUser()).thenReturn(user);

        when(user.getOperations()).thenReturn(operation);
        when(user.getOperations()).thenReturn(operation);
        when(operation.getOperation()).thenReturn(operationList);
        when(user.getOperations()).thenReturn(operation);
        when(operation.getOperation()).thenReturn(operationList);

        when(operationList.get(0).getOperationName()).thenReturn(new OperationName("o1"));
        when(operationList.get(0).getOperationId()).thenReturn(new OperationId("11111111-1111-1111-1111-111111111111"));

        when(user.getObjects()).thenReturn(object);

        when(user.getObjects()).thenReturn(object);
        when(object.getNode()).thenReturn(nodes);
        when(user.getObjects()).thenReturn(object);
        when(object.getNode()).thenReturn(nodes);
        when(nodes.get(0).getNodeName()).thenReturn(new NodeName("node1"));
        when(nodes.get(0).getNodeId()).thenReturn(new NodeId("11111111-1111-1111-1111-111111111111"));

        when(user.getObjects()).thenReturn(object);
        when(object.getConnection()).thenReturn(connections);
        when(user.getObjects()).thenReturn(object);
        when(object.getConnection()).thenReturn(connections);
        when(connections.get(0).getConnectionName()).thenReturn(new ConnectionName("connection1"));
        when(connections.get(0).getConnectionId()).thenReturn(new ConnectionId("11111111-1111-1111-1111-111111111111"));


        when(user.getObjects()).thenReturn(object);
        when(object.getFlow()).thenReturn(flows);
        when(user.getObjects()).thenReturn(object);
        when(object.getFlow()).thenReturn(flows);
        when(flows.get(0).getFlowName()).thenReturn(new FlowName("flow1"));
        when(flows.get(0).getFlowId()).thenReturn(new FlowId("11111111-1111-1111-1111-111111111111"));


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

        action = new LinkedHashMap<String,String>(){{
            //put(new String("1"),NEMOConstants.range);
            put(new String("group"),NEMOConstants.string);
            put(new String("100"),NEMOConstants.integer);
            put(new String("100,200"),NEMOConstants.range);
        }};
        actions.put(new String("action1"),action);

        when(user.getObjects()).thenReturn(object);

        when(user.getObjects()).thenReturn(object);

        when(object.getNode()).thenReturn(nodes);
        when(user.getObjects()).thenReturn(object);
        when(object.getNode()).thenReturn(nodes);
        when(nodes.get(0).getNodeName()).thenReturn(new NodeName("node1"));
        when(nodes.get(0).getNodeId()).thenReturn(new NodeId("11111111-1111-1111-1111-111111111111"));
        when(nodes.get(0).getNodeId()).thenReturn(new NodeId("11111111-1111-1111-1111-111111111111"));

        when(object.getConnection()).thenReturn(connections);
        when(user.getObjects()).thenReturn(object);
        when(object.getConnection()).thenReturn(connections);
        when(connections.get(0).getConnectionName()).thenReturn(new ConnectionName("connection1"));
        when(connections.get(0).getConnectionId()).thenReturn(new ConnectionId("11111111-1111-1111-1111-111111111111"));
        when(connections.get(0).getConnectionId()).thenReturn(new ConnectionId("11111111-1111-1111-1111-111111111111"));


        when(user.getObjects()).thenReturn(object);
        when(object.getFlow()).thenReturn(flows);
        when(user.getObjects()).thenReturn(object);
        when(object.getFlow()).thenReturn(flows);
        when(flows.get(0).getFlowName()).thenReturn(new FlowName("flow1"));
        when(flows.get(0).getFlowId()).thenReturn(new FlowId("11111111-1111-1111-1111-111111111111"));
        when(flows.get(0).getFlowId()).thenReturn(new FlowId("11111111-1111-1111-1111-111111111111"));



        updateOperationLangTest.OperationHandling(userId,operationname,target,priority,conditions,actions);

    }
	@org.junit.Test
	public void createOperationTest() throws Exception{
	UserId userId1=mock(UserId.class);
	String operationname1="o1";
	String target1="opendaylight";
	String priority1="node";
	LinkedHashMap<String,LinkedHashMap<String,String>> conditions1=null;
	LinkedHashMap<String,LinkedHashMap<String,String>> actions1=null;
	Class<?>[] args=new Class<?>[6];
	args[0]=userId1.getClass();
	args[1]=operationname1.getClass();
	args[2]=target1.getClass();
	args[3]=priority1.getClass();
	args[4]=conditions1.getClass();
	args[5]=actions1.getClass();
	Object[] args1 = new Object[6]; 
	args1[0]=userId1;
	args1[1]=operationname1;
	args1[2]=target1;
	args1[3]=priority1;
	args1[4]=conditions1;
	args1[5]=actions1;
	Method methon=updateOperationLangTest.getClass().getDeclaredMethod("createOperation",args);
	methon.setAccessible(true);
    //branch1
    when(tenantManage.getObjectId(userId1,operationname1)).thenReturn(null);
	when(tenantManage.getObjectId(userId1,target1)).thenReturn(null);
	Assert.assertNotNull(methon.invoke(updateOperationLangTest,args));
	//branch2
	OperationId operationid=new OperationId("11111111-1111-1111-1111-111111111111");
	when(tenantManage.getObjectId(userId,operationname1)).thenReturn("11111111-1111-1111-1111-111111111111");
	when(tenantManage.getObjectId(userId,operationname1)).thenReturn("11111111-1111-1111-1111-111111111111");
	Map<OperationId, Operation> operations=new HashMap<OperationId,Operation>();
	Operation operation=mock(Operation.class);
	operations.put(operationid,operation);
	when((tenantManage.getOperation(any(UserId.class)))).thenReturn(operations);
	when(tenantManage.getOperationDataStore(any(UserId.class))).thenReturn(operations);
	when(tenantManage.getObjectId(userId1,target1)).thenReturn(null);
	Assert.assertNotNull(methon.invoke(updateOperationLangTest,args));
	//branch3
	actions1=new LinkedHashMap<String,LinkedHashMap<String,String>>();
	LinkedHashMap<String,String> action1 = new LinkedHashMap<String,String>(){{
            put(new String("group"),NEMOConstants.string);
            put(new String("100"),NEMOConstants.integer);
            put(new String("100,200"),NEMOConstants.range);
        }};
    actions1.put(new String("action1"),action1);
	args1[5]=actions1;
	when(tenantManage.getOperation(any(UserId.class))).thenReturn(operations);
	when(tenantManage.getOperationDataStore(any(UserId.class))).thenReturn(operations);
	when(tenantManage.getObjectId(userId1,target1)).thenReturn(null);
	when(tenantManage.getObjectId(userId,operationname1)).thenReturn("11111111-1111-1111-1111-111111111111");
	when(tenantManage.getObjectId(userId,operationname1)).thenReturn("11111111-1111-1111-1111-111111111111");
	Assert.assertNull(methon.invoke(updateOperationLangTest,args));
	}
}

