/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.user.vnspacemanager.structurestyle.updateintent;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.opendaylight.nemo.user.tenantmanager.TenantManage;
import com.google.common.base.Optional;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.ConnectionId;

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
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.ConnectionType;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.Users;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.Objects;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Connection;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.ConnectionBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.ConnectionKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Node;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.User;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.UserKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.ConnectionDefinitions;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.connection.definitions.ConnectionDefinition;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.connection.instance.EndNode;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.connection.instance.Property;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.definitions.PropertyDefinition;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.PropertyValues;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.PropertyName;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.ConnectionName;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.NodeId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import java.util.LinkedList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.*;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.opendaylight.controller.md.sal.binding.api.ReadOnlyTransaction;

import com.google.common.util.concurrent.CheckedFuture;

import static org.junit.Assert.*;

/**
 * Created by ldzd11 on 2015/12/21.
 */
public class UpdateConnectionTest {

    UpdateConnection updateConnection;

    private DataBroker dataBroker;
    private TenantManage tenantManage;
    private List<Property> propertyList;
    private PropertyName propertyName;
    private PropertyName propertyName2;
    private Property property;
    private Connection connection;
    private Connection connection2;
    private NodeId nodeId;
    private NodeId nodeId2;


    private List<Connection> connectionList;
    private ConnectionType connectionType;
    private ConnectionType connectionType2;
    private ConnectionName connectionName;
    private ConnectionName connectionName2;
    private  User user;
    private UserId userId;
    private ConnectionId connectionId;
    private Objects objects;



    private PropertyDefinition propertyDefinition;
    private List<PropertyDefinition> propertyDefinitionList;
    @Before
    public void setUp() throws Exception {
        dataBroker = mock(DataBroker.class);
        tenantManage = mock(TenantManage.class);

        property = mock(Property.class);
        propertyList = new ArrayList<Property>(1);
        propertyList.add(property);

        propertyName = mock(PropertyName.class);
        propertyName2 = mock(PropertyName.class);

        connectionName = mock(ConnectionName.class);
        connectionName2 = mock(ConnectionName.class);

        propertyDefinition = mock(PropertyDefinition.class);
        propertyDefinitionList = new ArrayList<PropertyDefinition>(1);
        propertyDefinitionList.add(propertyDefinition);

        connectionType = mock(ConnectionType.class);
        connectionType2 = mock(ConnectionType.class);
        connection = mock(Connection.class);
        connection2 = mock(Connection.class);
        connectionList = new ArrayList<Connection>(1);
        connectionList.add(connection);
        objects = mock(Objects.class);

        user = mock(User.class);
        userId = mock(UserId.class);
        nodeId = mock(NodeId.class);
        nodeId2 = mock(NodeId.class);


        connectionId = mock(ConnectionId.class);
        updateConnection = new UpdateConnection(dataBroker,tenantManage);

    }

    @Test
    public void testConnectionHandling() throws Exception {
        CheckedFuture connectiondefinitionFuture = mock(CheckedFuture.class);
        ReadOnlyTransaction readOnlyTransaction = mock(ReadOnlyTransaction.class);
        when(dataBroker.newReadOnlyTransaction()).thenReturn(readOnlyTransaction);
        when(readOnlyTransaction.read(any(LogicalDatastoreType.class), any(InstanceIdentifier.class))).thenReturn(connectiondefinitionFuture);

        List<ConnectionDefinition> connectionDefinitionList = new ArrayList<ConnectionDefinition>();
        List<ConnectionDefinition> connectionDefinitionListnull = new ArrayList<ConnectionDefinition>();
        ConnectionDefinition connectionDefinition = mock(ConnectionDefinition.class);
        connectionDefinitionList.add(connectionDefinition);
        when(connectionDefinition.getConnectionType()).thenReturn(connectionType);


        Class<UpdateConnection> class1 = UpdateConnection.class;
        Class<GetDefinitions> class2 = GetDefinitions.class;
        Field field = class1.getDeclaredField("getDefinitions");
        field.setAccessible(true);
        Field field1 = class2.getDeclaredField("connectionDefinitionsList");
        field1.setAccessible(true);

        field1.set(field.get(updateConnection), connectionDefinitionList);
        when(connection.getConnectionType()).thenReturn(connectionType);
        when(connection.getProperty()).thenReturn(null);
        when(connectionDefinition.getPropertyDefinition()).thenReturn(null);

        when(tenantManage.getUser()).thenReturn(null);
        when(connection.getEndNode()).thenReturn(null);
        WriteTransaction writeTransaction = mock(WriteTransaction.class);
        when(dataBroker.newWriteOnlyTransaction()).thenReturn(writeTransaction);
        ConnectionId connectionId = mock(ConnectionId.class);
        when(connection.getConnectionId()).thenReturn(connectionId);

        when(dataBroker.newWriteOnlyTransaction()).thenReturn(writeTransaction);
        CheckedFuture<Void, TransactionCommitFailedException> f;
        f=mock(CheckedFuture.class);
        when(writeTransaction.submit()).thenReturn(f);
        Assert.assertEquals(updateConnection.ConnectionHandling(userId,connection),null);








    }

    @Test
    public void testcheckInstance() throws Exception {
        Class<UpdateConnection> class1 = UpdateConnection.class;
        Method method = class1.getDeclaredMethod("checkInstance", new Class[]{UserId.class, Connection.class});
        method.setAccessible(true);
        when(tenantManage.getUser()).thenReturn(user);
        when(user.getObjects()).thenReturn(objects);

        when(user.getObjects().getConnection()).thenReturn(connectionList);
        when(connection.getConnectionId()).thenReturn(connectionId);
        when(connection2.getConnectionId()).thenReturn(connectionId);
        when(connection.getConnectionType()).thenReturn(connectionType);
        when(connection2.getConnectionType()).thenReturn(connectionType2);
        //Assert.assertTrue(connectionType == connectionType2);
       // Assert.assertTrue(connectionType != connectionType2);

       // String result = (String) method.invoke(updateConnection,userId,connection2);
       // System.out.println(result+"3232");
       // Assert.assertTrue(result.equals("1"));
        Assert.assertEquals(method.invoke(updateConnection, userId, connection2), "The connection type should not be changed.");

        when(connection.getConnectionId()).thenReturn(connectionId);
        when(connection2.getConnectionId()).thenReturn(connectionId);
        when(connection.getConnectionType()).thenReturn(connectionType);
        when(connection2.getConnectionType()).thenReturn(connectionType);
        when(connection.getConnectionName()).thenReturn(connectionName);
        when(connection2.getConnectionName()).thenReturn(connectionName2);
        Assert.assertEquals(method.invoke(updateConnection, userId, connection2), "The End node should not be changed.");

        EndNode endNode = mock(EndNode.class);
        List<EndNode> endnodeList = new ArrayList<EndNode>(1);
        endnodeList.add(endNode);

        Node node = mock(Node.class);
        List<Node> nodeList = new ArrayList<Node>(1);
        nodeList.add(node);

        when(user.getObjects().getConnection()).thenReturn(null);
        when(connection2.getEndNode()).thenReturn(endnodeList);
        when(user.getObjects().getNode()).thenReturn(nodeList);
        when(endNode.getNodeId()).thenReturn(nodeId);
        when(node.getNodeId()).thenReturn(nodeId2);
        Assert.assertEquals(method.invoke(updateConnection, userId, connection2), "The endnode is not exist in user vn space.");

        when(user.getObjects().getNode()).thenReturn(null);
        Assert.assertEquals(method.invoke(updateConnection, userId, connection2), "The endnodes are not exist in the user vn space.");

        when(user.getObjects()).thenReturn(null);
        when(connection2.getEndNode()).thenReturn(endnodeList);
        Assert.assertEquals(method.invoke(updateConnection, userId, connection2), "There are no nodes in user vn space.");

        when(tenantManage.getUser()).thenReturn(null);
        when(connection.getEndNode()).thenReturn(endnodeList);
        Assert.assertEquals(method.invoke(updateConnection, userId, connection2),"There are no nodes in user vn space.");


    }

    @Test
    public void testcheckDefinition() throws Exception {
        Class<UpdateConnection> class3 = UpdateConnection.class;
        Method method = class3.getDeclaredMethod("checkDefinition",new Class[]{Connection.class});
        method.setAccessible(true);

        CheckedFuture connectiondefinitionFuture = mock(CheckedFuture.class);
        ReadOnlyTransaction readOnlyTransaction = mock(ReadOnlyTransaction.class);
        when(dataBroker.newReadOnlyTransaction()).thenReturn(readOnlyTransaction);
        when(readOnlyTransaction.read(any(LogicalDatastoreType.class), any(InstanceIdentifier.class))).thenReturn(connectiondefinitionFuture);

        List<ConnectionDefinition> connectionDefinitionList = new ArrayList<ConnectionDefinition>();
        List<ConnectionDefinition> connectionDefinitionListnull = new ArrayList<ConnectionDefinition>();
        ConnectionDefinition connectionDefinition = mock(ConnectionDefinition.class);
        connectionDefinitionList.add(connectionDefinition);
        when(connectionDefinition.getConnectionType()).thenReturn(connectionType);


        Class<UpdateConnection> class1 = UpdateConnection.class;
        Class<GetDefinitions> class2 = GetDefinitions.class;
        Field field = class1.getDeclaredField("getDefinitions");
        field.setAccessible(true);
        Field field1 = class2.getDeclaredField("connectionDefinitionsList");
        field1.setAccessible(true);


      //  when(connection.getConnectionType()).thenReturn(connectionType);
     //   when(connection.getProperty()).thenReturn(propertyList);
     //   when(connectionDefinition.getPropertyDefinition()).thenReturn(propertyDefinitionList);
      //  Assert.assertEquals(method.invoke(updateConnection, connection), "There are no properties for this type of connection.");

        field1.set(field.get(updateConnection), connectionDefinitionList);
        when(connection.getConnectionType()).thenReturn(connectionType);
        when(connection.getProperty()).thenReturn(propertyList);
        when(connectionDefinition.getPropertyDefinition()).thenReturn(null);
       // when(connection.getConnectionType()).thenReturn(connectionType2);
        Assert.assertEquals(method.invoke(updateConnection, connection), "There are no properties for this type of connection.");

        field1.set(field.get(updateConnection), connectionDefinitionList);
        when(connection.getConnectionType()).thenReturn(connectionType);
        when(connection.getProperty()).thenReturn(propertyList);
        when(connectionDefinition.getPropertyDefinition()).thenReturn(propertyDefinitionList);
        when(property.getPropertyName()).thenReturn(propertyName);
        when(propertyDefinition.getPropertyName()).thenReturn(propertyName2);
        when(property.getPropertyName().toString()).thenReturn(new String("propertyname"));
        Assert.assertEquals(method.invoke(updateConnection, connection), "This type of propertypropertyname has not been defined.");

        when(connection.getConnectionType()).thenReturn(connectionType2);
        Assert.assertEquals(method.invoke(updateConnection, connection),"This type of connection has not been defined.");

                field1.set(field.get(updateConnection), connectionDefinitionListnull);
        Assert.assertEquals(method.invoke(updateConnection, connection), "This type of connection has not been defined.");

    }

    @Test
    public void testcheckProperty() throws Exception {
        Class<UpdateConnection> class1 = UpdateConnection.class;
        Method method = class1.getDeclaredMethod("CheckProperty",new Class[]{List.class,List.class});
        method.setAccessible(true);
        when(property.getPropertyName()).thenReturn(propertyName);
        when(propertyDefinition.getPropertyName()).thenReturn(propertyName2);
        when(property.getPropertyName().toString()).thenReturn(new String("propertyname"));
        Assert.assertEquals(method.invoke(updateConnection, propertyList, propertyDefinitionList), "This type of propertypropertyname has not been defined.");




    }
}