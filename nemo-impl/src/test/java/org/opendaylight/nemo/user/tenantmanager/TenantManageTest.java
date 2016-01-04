/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.user.tenantmanager;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
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
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.user.rev151010.user.roles.UserRole;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.NodeName;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.ConnectionName;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.FlowName;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.OperationName;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ExecutionException;
import static org.mockito.Mockito.*;
import org.opendaylight.controller.md.sal.binding.api.ReadOnlyTransaction;
import com.google.common.util.concurrent.CheckedFuture;

/**
 * Created by zhangmeng on 2015/12/31.
 */
public class TenantManageTest extends TestCase {
    private DataBroker dataBroker;
    private TenantManage tenantManage;
    private Class<TenantManage> class1;
    private Field field_userNodeMap;
    private Field field_userConnectionMap;
    private Field field_userFlowMap;
    private Field field_userOperationMap;
    private Field field_userTemplateDefinitionMap;
    private Field field_userTemplateInstanceMap;
    private Field field_userDeleteIntent;
    private Field field_userNameIdMap;

    @Before
    public void setUp() throws Exception {
        dataBroker = mock(DataBroker.class);
        tenantManage = new TenantManage(dataBroker);
        class1 = TenantManage.class;

        field_userNodeMap = class1.getDeclaredField("userNodeMap");
        field_userConnectionMap = class1.getDeclaredField("userConnectionMap");
        field_userDeleteIntent = class1.getDeclaredField("userDeleteIntent");
        field_userFlowMap = class1.getDeclaredField("userFlowMap");
        field_userNameIdMap = class1.getDeclaredField("userNameIdMap");
        field_userOperationMap = class1.getDeclaredField("userOperationMap");
        field_userTemplateDefinitionMap = class1.getDeclaredField("userTemplateDefinitionMap");
        field_userTemplateInstanceMap = class1.getDeclaredField("userTemplateInstanceMap");

        field_userTemplateInstanceMap.setAccessible(true);
        field_userNameIdMap.setAccessible(true);
        field_userDeleteIntent.setAccessible(true);
        field_userNodeMap.setAccessible(true);
        field_userConnectionMap.setAccessible(true);
        field_userTemplateDefinitionMap.setAccessible(true);
        field_userFlowMap.setAccessible(true);
        field_userOperationMap.setAccessible(true);
    }

    @Test
    public void testGetUser() throws Exception {
        Class<TenantManage> class1 = TenantManage.class;
        Method method = class1.getDeclaredMethod("setUser", new Class[]{User.class});
        method.setAccessible(true);

        Assert.assertTrue(tenantManage.getUser() == null);
        method.invoke(tenantManage, mock(User.class));
        Assert.assertTrue(tenantManage.getUser() != null);
    }

    @Test
    public void testGetUserRoles() throws Exception {
        UserRole userRole = mock(UserRole.class);
        UserRoles userRoles = mock(UserRoles.class);
        UserRoleName userRoleName = mock(UserRoleName.class);
        ReadOnlyTransaction readOnlyTransaction = mock(ReadOnlyTransaction.class);
        CheckedFuture checkedFuture = mock(CheckedFuture.class);
        List<UserRole> userRoleList = new ArrayList<UserRole>();
        Optional<UserRoles> userRolesOpt = Optional.of(userRoles);

        userRoleList.add(userRole);

        when(dataBroker.newReadOnlyTransaction()).thenReturn(readOnlyTransaction);
        when(readOnlyTransaction.read(any(LogicalDatastoreType.class), any(InstanceIdentifier.class))).thenReturn(checkedFuture);
        when(checkedFuture.get()).thenReturn(userRolesOpt);
        when(userRoles.getUserRole()).thenReturn(userRoleList);
        when(userRole.getRoleName()).thenReturn(userRoleName);

        Assert.assertTrue(tenantManage.getUserRoles().containsKey(userRoleName));
        verify(userRole).getRoleName();

    }

    @Test
    public void testFetchVNSpace_GetUsers_setUser() throws Exception {
        Assert.assertTrue(tenantManage.getUser() == null);

        UserId userId = mock(UserId.class);
        Users users = mock(Users.class);
        User user = mock(User.class);
        List<User> userList = new ArrayList<User>();
        ReadOnlyTransaction readOnlyTransaction = mock(ReadOnlyTransaction.class);
        CheckedFuture checkedFuture = mock(CheckedFuture.class);
        Optional<Users> usersOpt = Optional.of(users);

        userList.add(user);

        when(dataBroker.newReadOnlyTransaction()).thenReturn(readOnlyTransaction);
        when(readOnlyTransaction.read(any(LogicalDatastoreType.class), any(InstanceIdentifier.class))).thenReturn(checkedFuture);
        when(checkedFuture.get()).thenReturn(usersOpt);
        when(users.getUser()).thenReturn(userList);
        when(user.getUserId()).thenReturn(userId);

        Assert.assertTrue(tenantManage.getUsers().containsKey(userId));
        verify(user).getUserId();
        tenantManage.fetchVNSpace(userId);
        Assert.assertTrue(tenantManage.getUser() == user);
        verify(user,times(2)).getUserId();
    }


    @Test
    public void testAddUser() throws Exception {
        UserId userId = mock(UserId.class);
        RegisterUserInput registerUserInput = mock(RegisterUserInput.class);
        WriteTransaction writeTransaction = mock(WriteTransaction.class);
        CheckedFuture checkedFuture = mock(CheckedFuture.class);

        when(registerUserInput.getUserId()).thenReturn(userId);
        when(dataBroker.newWriteOnlyTransaction()).thenReturn(writeTransaction);
        when(writeTransaction.submit()).thenReturn(checkedFuture);
//        when(checkedFuture.get()).whenReturn(Optional.of(users));

        tenantManage.addUser(registerUserInput);
        verify(checkedFuture).get();
    }

    @Test
    public void testTransactionHandling_writeDataStore() throws Exception {
        NodeId nodeId = mock(NodeId.class);
        Node node = mock(Node.class);
        UserId userId = mock(UserId.class);
        ConnectionId connectionId = mock(ConnectionId.class);
        Connection connection = mock(Connection.class);
        FlowId flowId = mock(FlowId.class);
        Flow flow = mock(Flow.class);
        OperationId operationId = mock(OperationId.class);
        Operation operation = mock(Operation.class);
        TemplateName templateName = mock(TemplateName.class);
        TemplateDefinition templateDefinition = mock(TemplateDefinition.class);
        TemplateInstanceId templateInstanceId = mock(TemplateInstanceId.class);
        TemplateInstance templateInstance = mock(TemplateInstance.class);
        WriteTransaction writeTransaction = mock(WriteTransaction.class);
        CheckedFuture checkedFuture = mock(CheckedFuture.class);
        Map<NodeId, Node> nodeMap = new HashMap<NodeId, Node>();
        Map<UserId, Map<NodeId, Node>> userNodeMap = mock(Map.class);
        Map<ConnectionId, Connection> connectionMap = new HashMap<ConnectionId, Connection>();
        Map<UserId, Map<ConnectionId, Connection>> userConnectionMap = mock(Map.class);
        Map<FlowId, Flow> flowMap = new HashMap<FlowId, Flow>();
        Map<UserId, Map<FlowId, Flow>> userFlowMap = mock(Map.class);
        Map<OperationId, Operation> operationMap = new HashMap<OperationId, Operation>();
        Map<String, String> deleteObject = new HashMap<String, String>();
        Map<UserId, Map<OperationId, Operation>> userOperationMap = mock(Map.class);
        Map<TemplateName, TemplateDefinition> templateDefinitionMap = new HashMap<TemplateName, TemplateDefinition>();
        Map<UserId, Map<TemplateName, TemplateDefinition>> userTemplateDefinitionMap = mock(Map.class);
        Map<TemplateInstanceId, TemplateInstance> templateInstanceMap = new HashMap<TemplateInstanceId, TemplateInstance>();
        Map<UserId, Map<TemplateInstanceId, TemplateInstance>> userTemplateInstanceMap = mock(Map.class);
        Map<UserId, Map<String, String>> userDeleteIntent = mock(Map.class);

        nodeMap.put(nodeId, node);
        connectionMap.put(connectionId, connection);
        flowMap.put(flowId, flow);
        operationMap.put(operationId, operation);
        templateDefinitionMap.put(templateName, templateDefinition);
        templateInstanceMap.put(templateInstanceId, templateInstance);

        //get into method "writeDataStore" arg(userId)
        when(dataBroker.newWriteOnlyTransaction()).thenReturn(writeTransaction);
        when(writeTransaction.submit()).thenReturn(checkedFuture);
        ////get into method "getNode" arg(userId)
        field_userNodeMap.set(tenantManage, userNodeMap);
        when(userNodeMap.isEmpty()).thenReturn(false);
        when(userNodeMap.get(userId)).thenReturn(nodeMap);
        ////get into method "getConnection" arg(userId)
        field_userConnectionMap.set(tenantManage, userConnectionMap);
        when(userConnectionMap.isEmpty()).thenReturn(false);
        when(userConnectionMap.get(userId)).thenReturn(connectionMap);
        ////get into method "getFlow" arg(userId)
        field_userFlowMap.set(tenantManage, userFlowMap);
        when(userFlowMap.isEmpty()).thenReturn(false);
        when(userFlowMap.get(userId)).thenReturn(flowMap);
        ////get into method "getOperation" arg(userId)
        field_userOperationMap.set(tenantManage, userOperationMap);
        when(userOperationMap.isEmpty()).thenReturn(false);
        when(userOperationMap.get(userId)).thenReturn(operationMap);
        ////get into method "getTempalteDefinition" arg(userId)
        field_userTemplateDefinitionMap.set(tenantManage, userTemplateDefinitionMap);
        when(userTemplateDefinitionMap.isEmpty()).thenReturn(false);
        when(userTemplateDefinitionMap.get(userId)).thenReturn(templateDefinitionMap);
        ////get into method "getTemplateInstance" arg(userId)
        field_userTemplateInstanceMap.set(tenantManage, userTemplateInstanceMap);
        when(userTemplateInstanceMap.isEmpty()).thenReturn(false);
        when(userTemplateInstanceMap.get(userId)).thenReturn(templateInstanceMap);
        tenantManage.transactionHandling(userId);
        verify(userNodeMap, times(3)).isEmpty();
        verify(userConnectionMap, times(3)).isEmpty();
        verify(userFlowMap, times(3)).isEmpty();
        verify(userOperationMap,times(3)).isEmpty();
        verify(userTemplateDefinitionMap, times(3)).isEmpty();
        verify(userTemplateInstanceMap,times(3)).isEmpty();
        verify(dataBroker,times(6)).newWriteOnlyTransaction();

        //get into method "deleteDataStore" arg(userId)
        field_userDeleteIntent.set(tenantManage, userDeleteIntent);
        when(userDeleteIntent.get(userId)).thenReturn(deleteObject);
        deleteObject.put("11111111-1111-1111-1111-111111111111", NEMOConstants.node);
        tenantManage.transactionHandling(userId);
        verify(dataBroker, times(13)).newWriteOnlyTransaction();
        deleteObject.clear();
        deleteObject.put("11111111-1111-1111-1111-111111111111", NEMOConstants.connection);
        tenantManage.transactionHandling(userId);
        verify(dataBroker,times(20)).newWriteOnlyTransaction();
        deleteObject.clear();
        deleteObject.put("11111111-1111-1111-1111-111111111111", NEMOConstants.flow);
        tenantManage.transactionHandling(userId);
        verify(dataBroker,times(27)).newWriteOnlyTransaction();
        deleteObject.clear();
        deleteObject.put("11111111-1111-1111-1111-111111111111", NEMOConstants.operation);
        tenantManage.transactionHandling(userId);
        verify(dataBroker,times(34)).newWriteOnlyTransaction();
    }

    @Test
    public void testSetNode_setUserNameIdMap_GetNode() throws Exception {
        UserId userId = mock(UserId.class);
        NodeId nodeId = mock(NodeId.class);
        Node node = mock(Node.class);
//        NodeName nodeName

        Map<UserId, Map<NodeId, Node>> userNodeMap = mock(Map.class);
        Map<UserId, Map<String, String>> userNameIdMap = mock(Map.class);

        Assert.assertTrue(tenantManage.getNode(userId) == null);
        Assert.assertTrue(tenantManage.getUserNameIdMap(userId) == null);
        Assert.assertTrue(((Map) field_userNameIdMap.get(tenantManage)).isEmpty());
        Assert.assertTrue(((Map)field_userNodeMap.get(tenantManage)).isEmpty());
        when(node.getNodeName()).thenReturn(mock(NodeName.class));
        when(node.getNodeName().getValue()).thenReturn("name");
        tenantManage.setNode(userId, nodeId, node);
        Assert.assertTrue(tenantManage.getNode(userId) != null);
        Assert.assertTrue(tenantManage.getUserNameIdMap(userId) != null);
        Assert.assertTrue(!((Map) field_userNameIdMap.get(tenantManage)).isEmpty());
        Assert.assertTrue(!((Map) field_userNodeMap.get(tenantManage)).isEmpty());

        field_userNodeMap.set(tenantManage, userNodeMap);
        when(userNodeMap.containsKey(userId)).thenReturn(true);
        when(userNodeMap.get(userId)).thenReturn(new HashMap<NodeId, Node>());
        field_userNameIdMap.set(tenantManage, userNameIdMap);
        when(userNameIdMap.containsKey(userId)).thenReturn(true);
        when(userNameIdMap.get(userId)).thenReturn(new HashMap<String, String>());
        tenantManage.setNode(userId, nodeId, node);
        verify(userNodeMap).get(userId);
        verify(userNameIdMap).get(userId);

    }

    @Test
    public void testSetConnection_GetConnection() throws Exception {
        UserId userId = mock(UserId.class);
        ConnectionId connectionId = mock(ConnectionId.class);
        Connection connection = mock(Connection.class);

        Map<UserId, Map<ConnectionId, Connection>> userConnectionMap = mock(Map.class);

        Assert.assertTrue(tenantManage.getConnection(userId) == null);
        Assert.assertTrue(((Map) field_userConnectionMap.get(tenantManage)).isEmpty());
        when(connection.getConnectionName()).thenReturn(mock(ConnectionName.class));
        when(connection.getConnectionName().getValue()).thenReturn("connection");
        tenantManage.setConnection(userId, connectionId, connection);
        Assert.assertTrue(tenantManage.getConnection(userId) != null);
        Assert.assertTrue(!((Map) field_userConnectionMap.get(tenantManage)).isEmpty());

        field_userConnectionMap.set(tenantManage, userConnectionMap);
        when(userConnectionMap.containsKey(userId)).thenReturn(true);
        when(userConnectionMap.get(userId)).thenReturn(new HashMap<ConnectionId, Connection>());
        tenantManage.setConnection(userId, connectionId, connection);
        verify(userConnectionMap).get(userId);
    }

    @Test
    public void testSetFlow_GetFlow() throws Exception {
        UserId userId = mock(UserId.class);
        FlowId flowId = mock(FlowId.class);
        Flow flow = mock(Flow.class);

        Map<UserId, Map<FlowId, Flow>> userFlowMap = mock(Map.class);

        Assert.assertTrue(tenantManage.getFlow(userId) == null);
        Assert.assertTrue(((Map) field_userFlowMap.get(tenantManage)).isEmpty());
        when(flow.getFlowName()).thenReturn(mock(FlowName.class));
        when(flow.getFlowName().getValue()).thenReturn("name");
        tenantManage.setFlow(userId, flowId, flow);
        Assert.assertTrue(tenantManage.getFlow(userId) != null);
        Assert.assertTrue(!((Map) field_userFlowMap.get(tenantManage)).isEmpty());

        field_userFlowMap.set(tenantManage, userFlowMap);
        when(userFlowMap.containsKey(userId)).thenReturn(true);
        when(userFlowMap.get(userId)).thenReturn(new HashMap<FlowId,Flow>());
        tenantManage.setFlow(userId, flowId, flow);
        verify(userFlowMap).get(userId);
    }


    @Test
    public void testSetOperation_GetOperation() throws Exception {
        UserId userId = mock(UserId.class);
        OperationId operationId = mock(OperationId.class);
        Operation operation = mock(Operation.class);

        Map<UserId, Map<OperationId, Operation>> userOperationMap = mock(Map.class);

        Assert.assertTrue(tenantManage.getOperation(userId) == null);
        Assert.assertTrue(((Map) field_userOperationMap.get(tenantManage)).isEmpty());
        when(operation.getOperationName()).thenReturn(mock(OperationName.class));
        when(operation.getOperationName().getValue()).thenReturn("OperationName");
        tenantManage.setOperation(userId, operationId, operation);
        Assert.assertTrue(tenantManage.getOperation(userId) != null);
        Assert.assertTrue(!((Map) field_userOperationMap.get(tenantManage)).isEmpty());

        field_userOperationMap.set(tenantManage, userOperationMap);
        when(userOperationMap.containsKey(userId)).thenReturn(true);
        when(userOperationMap.get(userId)).thenReturn(new HashMap<OperationId, Operation>());
        tenantManage.setOperation(userId, operationId, operation);
        verify(userOperationMap).get(userId);
    }

    @Test
    public void testSetTemplateDefinition_GetTempalteDefinition() throws Exception {
        UserId userId = mock(UserId.class);
        TemplateName templateName = mock(TemplateName.class);
        TemplateDefinition definition = mock(TemplateDefinition.class);

        Map<UserId, Map<TemplateName, TemplateDefinition>> userTemplateDefinitionMap = mock(Map.class);

        Assert.assertTrue(tenantManage.getTempalteDefinition(userId) == null);
        Assert.assertTrue(((Map) field_userTemplateDefinitionMap.get(tenantManage)).isEmpty());
        tenantManage.setTemplateDefinition(userId, templateName, definition);
        Assert.assertTrue(tenantManage.getTempalteDefinition(userId) != null);
        Assert.assertTrue(!((Map) field_userTemplateDefinitionMap.get(tenantManage)).isEmpty());

        field_userTemplateDefinitionMap.set(tenantManage, userTemplateDefinitionMap);
        when(userTemplateDefinitionMap.containsKey(userId)).thenReturn(true);
        when(userTemplateDefinitionMap.get(userId)).thenReturn(new HashMap<TemplateName, TemplateDefinition>());
        tenantManage.setTemplateDefinition(userId, templateName, definition);
        verify(userTemplateDefinitionMap).get(userId);
    }

    @Test
    public void testSetUserTemplateInstance_GetTemplateInstance() throws Exception {
        UserId userId = mock(UserId.class);
        TemplateInstanceId instanceId = mock(TemplateInstanceId.class);
        TemplateInstance instance = mock(TemplateInstance.class);

        Map<UserId, Map<TemplateInstanceId, TemplateInstance>> userTemplateInstanceMap = mock(Map.class);

        Assert.assertTrue(tenantManage.getTemplateInstance(userId) == null);
        Assert.assertTrue(((Map) field_userTemplateInstanceMap.get(tenantManage)).isEmpty());
        tenantManage.setUserTemplateInstance(userId, instanceId, instance);
        Assert.assertTrue(tenantManage.getTemplateInstance(userId) != null);
        Assert.assertTrue(!((Map) field_userTemplateInstanceMap.get(tenantManage)).isEmpty());

        field_userTemplateInstanceMap.set(tenantManage, userTemplateInstanceMap);
        when(userTemplateInstanceMap.containsKey(userId)).thenReturn(true);
        when(userTemplateInstanceMap.get(userId)).thenReturn(new HashMap<TemplateInstanceId, TemplateInstance>());
        tenantManage.setUserTemplateInstance(userId, instanceId, instance);
        verify(userTemplateInstanceMap).get(userId);
    }


    @Test
    public void testSetUserDeleteIntent() throws Exception {
        UserId userId = mock(UserId.class);
        String type = "type";
        String objectId = "objectId";

        Map<UserId, Map<String, String>> userDeleteIntent = mock(Map.class);

        Assert.assertTrue(((Map) field_userDeleteIntent.get(tenantManage)).isEmpty());
        tenantManage.setUserDeleteIntent(userId, type, objectId);
        Assert.assertTrue(!((Map) field_userDeleteIntent.get(tenantManage)).isEmpty());

        field_userDeleteIntent.set(tenantManage, userDeleteIntent);
        when(userDeleteIntent.containsKey(userId)).thenReturn(true);
        when(userDeleteIntent.get(userId)).thenReturn(new HashMap<String, String>());
        tenantManage.setUserDeleteIntent(userId, type, objectId);
        verify(userDeleteIntent).get(userId);

    }

    @Test
    public void testGetObjectId() throws Exception {
        UserId userId = mock(UserId.class);
        String name = "name";

        Map<UserId, Map<String, String>> userNameIdMap = mock(Map.class);
        Map<String, String> temp = mock(Map.class);

        Assert.assertTrue(tenantManage.getObjectId(userId,name) == null);
        field_userNameIdMap.set(tenantManage, userNameIdMap);
        when(userNameIdMap.containsKey(userId)).thenReturn(true);
        when(userNameIdMap.get(userId)).thenReturn(temp);
        when(userNameIdMap.get(userId).containsKey(name)).thenReturn(true);
        when(userNameIdMap.get(userId).get(name)).thenReturn("test");
        Assert.assertTrue(tenantManage.getObjectId(userId,name).equals("test"));

    }

    @Test
    public void testGetName() throws Exception {
        UserId userId = mock(UserId.class);
        String objectId = "objectId";

        Map<UserId, Map<String, String>> userNameIdMap = mock(Map.class);
        Map<String, String> temp = new HashMap<String, String>();

        temp.put("test", "objectId");

        Assert.assertTrue(tenantManage.getName(userId, objectId) == null);
        field_userNameIdMap.set(tenantManage, userNameIdMap);
        when(userNameIdMap.containsKey(userId)).thenReturn(true);
        when(userNameIdMap.get(userId)).thenReturn(temp);
        Assert.assertTrue(tenantManage.getName(userId, objectId).equals("test"));
    }

    @Test
    public void testGetNodeDataStore() throws Exception {
        UserId userId = mock(UserId.class);

        Objects objects = mock(Objects.class);
        Operations operations = mock(Operations.class);
        TemplateDefinitions templateDefinitions = mock(TemplateDefinitions.class);
        TemplateInstances templateInstances = mock(TemplateInstances.class);
        Node node = mock(Node.class);
        Connection connection = mock(Connection.class);
        Flow flow = mock(Flow.class);
        Operation operation = mock(Operation.class);
        TemplateDefinition templateDefinition = mock(TemplateDefinition.class);
        TemplateInstance templateInstance = mock(TemplateInstance.class);
        User user = mock(User.class);
        Users users = mock(Users.class);
        ReadOnlyTransaction readOnlyTransaction = mock(ReadOnlyTransaction.class);
        CheckedFuture checkedFuture = mock(CheckedFuture.class);
        Optional<Users> usersOpt = Optional.of(users);
        List<User> userList = new ArrayList<User>();
        List<Node> nodeList = new ArrayList<Node>();
        List<Connection> connectionList = new ArrayList<Connection>();
        List<Flow> flowList = new ArrayList<Flow>();
        List<Operation> operationList = new ArrayList<Operation>();
        List<TemplateDefinition> templateDefinitionList = new ArrayList<TemplateDefinition>();
        List<TemplateInstance> templateInstanceList = new ArrayList<TemplateInstance>();

        userList.add(user);
        nodeList.add(node);
        connectionList.add(connection);
        flowList.add(flow);
        operationList.add(operation);
        templateDefinitionList.add(templateDefinition);
        templateInstanceList.add(templateInstance);

        //get into method "fetchVNSpace" arg(userId)
        ////get into method "getUsers()"
        when(dataBroker.newReadOnlyTransaction()).thenReturn(readOnlyTransaction);
        when(readOnlyTransaction.read(any(LogicalDatastoreType.class), any(InstanceIdentifier.class))).thenReturn(checkedFuture);
        when(checkedFuture.get()).thenReturn(usersOpt);
        when(users.getUser()).thenReturn(userList);//input =  users
        when(user.getUserId()).thenReturn(userId);
        /* get into method "getUser" and method "getUser" returned user */
        when(user.getObjects()).thenReturn(objects);
        when(user.getObjects().getNode()).thenReturn(nodeList);
        when(user.getObjects().getConnection()).thenReturn(connectionList);
        when(user.getObjects().getFlow()).thenReturn(flowList);
        when(user.getOperations()).thenReturn(operations);
        when(user.getOperations().getOperation()).thenReturn(operationList);
        when(user.getTemplateDefinitions()).thenReturn(templateDefinitions);
        when(user.getTemplateDefinitions().getTemplateDefinition()).thenReturn(templateDefinitionList);
        when(user.getTemplateInstances()).thenReturn(templateInstances);
        when(user.getTemplateInstances().getTemplateInstance()).thenReturn(templateInstanceList);

        tenantManage.getNodeDataStore(userId);
        tenantManage.getConnectionDataStore(userId);
        tenantManage.getDefinitionDataStore(userId);
        tenantManage.getFlowDataStore(userId);
        tenantManage.getInstanceDataStore(userId);
        tenantManage.getOperationDataStore(userId);
        verify(objects,times(2)).getNode();
        verify(objects,times(2)).getConnection();
        verify(objects,times(2)).getFlow();
        verify(operations,times(2)).getOperation();
        verify(templateDefinitions,times(2)).getTemplateDefinition();
        verify(templateInstances,times(2)).getTemplateInstance();

    }

}