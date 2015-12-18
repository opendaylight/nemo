/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.user.vnspacemanager.structurestyle.updateintent;

import org.opendaylight.nemo.user.vnspacemanager.structurestyle.updateintent.UpdateNode;
import org.opendaylight.nemo.user.tenantmanager.TenantManage;
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
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.node.definitions.NodeDefinition;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.Objects;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Node;
import org.opendaylight.nemo.user.tenantmanager.TenantManage;
import org.opendaylight.nemo.user.vnspacemanager.languagestyle.NEMOConstants;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.NodeType;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.Users;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.Objects;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.NodeBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.NodeKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.User;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.UserKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.NodeDefinitions;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.node.definitions.NodeDefinition;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.node.instance.Property;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.definitions.PropertyDefinition;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.PropertyValues;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.property.values.StringValue;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.node.definitions.NodeDefinition;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.PropertyName;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.PropertyValues;
import org.opendaylight.controller.md.sal.binding.api.ReadOnlyTransaction;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.NodeId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.NodeName;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.NodeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

import com.google.common.util.concurrent.CheckedFuture;

/**
 * Created by ldzd11 on 2015/12/18.
 */
public class UpdateNodeTest {

    private UpdateNode updateNode;
    private Node node;
    private Node node2;
    private NodeName nodeName;
    private NodeName nodeName2;
    private List<Node> nodeList;
    private DataBroker dataBroker;
    private TenantManage tenantManage;
    private Property property;
    private List<Property> propertyList;
    private PropertyName propertyName;
    private PropertyName propertyName2;
    private StringValue stringValue;
    private List<StringValue> stringValues;
    private PropertyValues propertyValues;
    private ValueCheck valueCheck;
    private PropertyDefinition propertyDefinition;
    private NodeType nodeType;
    private NodeType nodeType2;
    private List<PropertyDefinition> propertyDefinitions;
    private UserId userId;
    private WriteTransaction writeTransaction;
    private User user;
    private NodeId nodeId;
    private NodeKey nodeKey;
    private Objects objects;
    @org.junit.Before
    public void setUp() throws Exception {
        dataBroker = mock(DataBroker.class);
        tenantManage = mock(TenantManage.class);
        propertyValues = mock(PropertyValues.class);
        valueCheck = mock(ValueCheck.class);
        nodeId = mock(NodeId.class);
        nodeName = mock(NodeName.class);
        nodeName2 = mock(NodeName.class);

        propertyName = mock(PropertyName.class);
        propertyName2 = mock(PropertyName.class);

        property = mock(Property.class);
        propertyList = new ArrayList<Property>(1);
        propertyList.add(property);

        stringValue = mock(StringValue.class);
        stringValues = new ArrayList<StringValue>(1);
        stringValues.add(stringValue);
        nodeType = mock(NodeType.class);

        propertyDefinition=  mock(PropertyDefinition.class);
        propertyDefinitions = new ArrayList<PropertyDefinition>(1);
        propertyDefinitions.add(propertyDefinition);

        node = mock(Node.class);
        node2 = mock(Node.class);
        userId = mock(UserId.class);
        user = mock(User.class);
        objects = mock(Objects.class);
        nodeKey = mock(NodeKey.class);

        nodeList = new ArrayList<Node>(1);
        nodeList.add(node2);
        writeTransaction = mock(WriteTransaction.class);



        updateNode = new UpdateNode(dataBroker,tenantManage);
        nodeType2 = mock(NodeType.class);



    }

    @org.junit.Test
    public void testNodeHandling() throws Exception {
        //into checkDefinition
        CheckedFuture connectiondefinitionFuture = mock(CheckedFuture.class);
        ReadOnlyTransaction readOnlyTransaction = mock(ReadOnlyTransaction.class);
        when(dataBroker.newReadOnlyTransaction()).thenReturn(readOnlyTransaction);
        when(readOnlyTransaction.read(any(LogicalDatastoreType.class), any(InstanceIdentifier.class))).thenReturn(connectiondefinitionFuture);

        Class<UpdateNode> class1 = UpdateNode.class;
        Class<GetDefinitions> class2 = GetDefinitions.class;
        Field field = class1.getDeclaredField("getDefinitions");
        field.setAccessible(true);
        Field field1 = class2.getDeclaredField("nodeDefinitionList");
        field1.setAccessible(true);


        List<NodeDefinition> nodeDefinitionList = new ArrayList<NodeDefinition>();
        NodeDefinition nodeDefinition = mock(NodeDefinition.class);
        when(nodeDefinition.getNodeType()).thenReturn(nodeType);
        nodeDefinitionList.add(nodeDefinition);

        field1.set(field.get(updateNode), nodeDefinitionList);
        Assert.assertTrue((List<NodeDefinition>) field1.get(field.get(updateNode)) == nodeDefinitionList);

        when(node.getNodeType()).thenReturn(nodeType);
        when(node.getProperty()).thenReturn(null);
        when(nodeDefinition.getPropertyDefinition()).thenReturn(null);



        when(tenantManage.getUser()).thenReturn(null);
        //error = null  continue
        when(node.getNodeId()).thenReturn(nodeId);
        when(node.getKey()).thenReturn(nodeKey);
        when(dataBroker.newWriteOnlyTransaction()).thenReturn(writeTransaction);
        CheckedFuture<Void, TransactionCommitFailedException> f;
        f=mock(CheckedFuture.class);
        when(writeTransaction.submit()).thenReturn(f);
        Assert.assertEquals(updateNode.NodeHandling(userId, node), null);
        //verify(node).getProperty();
        //Assert.assertEquals(updateNode.NodeHandling(userId,node),"The property propertyname has not been defined.")

    }
    @Test
    public void testcheckInstance() throws Exception {
        Class<UpdateNode> class1 = UpdateNode.class;
        Method method = class1.getDeclaredMethod("checkInstance", new Class[]{UserId.class, Node.class});
        method.setAccessible(true);

        when(tenantManage.getUser()).thenReturn(user);
        when(user.getObjects()).thenReturn(objects);
        when(user.getObjects().getNode()).thenReturn(nodeList);
        when(node.getNodeId()).thenReturn(nodeId);
        when(node2.getNodeId()).thenReturn(nodeId);
        when(node.getNodeName()).thenReturn(nodeName);
        when(node2.getNodeName()).thenReturn(nodeName2);
        Assert.assertEquals(method.invoke(updateNode, userId, node), "The node name should not be changed.");

        when(node.getNodeName()).thenReturn(nodeName);
        when(node2.getNodeName()).thenReturn(nodeName);
        when(node.getNodeType()).thenReturn(nodeType);
        when(node2.getNodeType()).thenReturn(nodeType2);
        Assert.assertEquals(method.invoke(updateNode, userId, node), "The node type should not be changed.");


    }

@Test
    public void testcheckDefinition() throws Exception {

        Class<UpdateNode> class3 = UpdateNode.class;
        Method method = class3.getDeclaredMethod("checkDefinition", new Class[]{Node.class});
        method.setAccessible(true);

        CheckedFuture nodedefinitionFuture = mock(CheckedFuture.class);
        ReadOnlyTransaction readOnlyTransaction = mock(ReadOnlyTransaction.class);
        when(dataBroker.newReadOnlyTransaction()).thenReturn(readOnlyTransaction);
        when(readOnlyTransaction.read(any(LogicalDatastoreType.class), any(InstanceIdentifier.class))).thenReturn(nodedefinitionFuture);

        List<NodeDefinition> nodeDefinitionList = new ArrayList<NodeDefinition>();
        List<NodeDefinition> nodeDefinitionListnull = new ArrayList<NodeDefinition>();
        NodeDefinition nodeDefinition = mock(NodeDefinition.class);
        nodeDefinitionList.add(nodeDefinition);
        when(nodeDefinition.getNodeType()).thenReturn(nodeType);

        Class<UpdateNode> class1 = UpdateNode.class;
        Class<GetDefinitions> class2 = GetDefinitions.class;
        Field field = class1.getDeclaredField("getDefinitions");
        field.setAccessible(true);
        Field field1 = class2.getDeclaredField("nodeDefinitionList");
        field1.setAccessible(true);


        System.out.println(field1.get(field.get(updateNode)));
        field1.set(field.get(updateNode), nodeDefinitionList);
        System.out.println(field1.get(field.get(updateNode)));
        Assert.assertTrue((List<NodeDefinition>) field1.get(field.get(updateNode)) == nodeDefinitionList);

        when(node.getNodeType()).thenReturn(nodeType);
        when(node.getProperty()).thenReturn(propertyList);
        when(nodeDefinition.getPropertyDefinition()).thenReturn(propertyDefinitions);

        when(property.getPropertyName()).thenReturn(propertyName);
        when(propertyDefinition.getPropertyName()).thenReturn(propertyName2);

        when(property.getPropertyName().getValue()).thenReturn(new String("propertyname"));
        Assert.assertEquals(method.invoke(updateNode, node), "The property propertyname has not been defined.");

        field1.set(field.get(updateNode), nodeDefinitionList);
        when(node.getNodeType()).thenReturn(nodeType);
        List<Property> propertyListnull = new ArrayList<Property>();
        List<PropertyDefinition> propertyDefinitionListnull = new ArrayList<PropertyDefinition>();

        when(node.getProperty()).thenReturn(propertyListnull);
        when(nodeDefinition.getPropertyDefinition()).thenReturn(propertyDefinitionListnull);
        Assert.assertEquals(method.invoke(updateNode, node), null);

        field1.set(field.get(updateNode), nodeDefinitionListnull);
        Assert.assertEquals(method.invoke(updateNode, node), "This type of Node has not been defined.");

        field1.set(field.get(updateNode), nodeDefinitionList);
        when(node.getNodeType()).thenReturn(nodeType2);
        Assert.assertEquals(method.invoke(updateNode, node), "This type of Node has not been defined.");


/*
        (String)method.invoke(updateNode,node)*/
    }

    @Test
    public void testcheckProperty() throws Exception {
        Class<UpdateNode> class1 = UpdateNode.class;
        Method method = class1.getDeclaredMethod("checkProperty", new Class[]{List.class, List.class});
        method.setAccessible(true);
        when(property.getPropertyName()).thenReturn(propertyName);
        when(propertyDefinition.getPropertyName()).thenReturn(propertyName2);
        when(property.getPropertyName().getValue()).thenReturn(new String("propertyname"));
        Assert.assertEquals(method.invoke(updateNode, propertyList,propertyDefinitions), "The property propertyname has not been defined.");

    }


    @Test
    public void testcheckPredefine() throws Exception {
        Class<UpdateNode> class1 = UpdateNode.class;
        Method method = class1.getDeclaredMethod("checkPredefine", new Class[]{List.class});
        method.setAccessible(true);

        when(property.getPropertyName()).thenReturn(propertyName);
        when(property.getPropertyName().getValue()).thenReturn(new String("mac-address"));
        when(property.getPropertyValues()).thenReturn(propertyValues);
        when(property.getPropertyValues().getStringValue()).thenReturn(stringValues);
        when(stringValue.getValue()).thenReturn(new String("test"));
        when(valueCheck.checkMac(stringValue.getValue())).thenReturn(false);
        Assert.assertEquals(method.invoke(updateNode, propertyList), "The mac-address is not legal.");

        when(property.getPropertyName().getValue()).thenReturn(new String("ip-address"));
        when(property.getPropertyValues().getStringValue()).thenReturn(stringValues);
        when(stringValue.getValue()).thenReturn(new String("/"));
        when(valueCheck.checkIpPrefix(stringValue.getValue())).thenReturn(false);
        Assert.assertEquals(method.invoke(updateNode, propertyList), "The ip-address is not legal.");

        when(property.getPropertyName().getValue()).thenReturn(new String("gateway-ip"));
        when(property.getPropertyValues().getStringValue()).thenReturn(stringValues);
        when(valueCheck.checkIpAddress(stringValue.getValue())).thenReturn(false);
        Assert.assertEquals(method.invoke(updateNode, propertyList), "The gateway-ip is not legal.");

        when(property.getPropertyName().getValue()).thenReturn(new String("ac-info-network"));
        when(property.getPropertyValues().getStringValue()).thenReturn(stringValues);
        when(stringValue.getValue()).thenReturn(new String("layer4"));
        Assert.assertEquals(method.invoke(updateNode, propertyList), "The ac-info-network is not legal.");

        when(property.getPropertyName().getValue()).thenReturn(new String("operating-mode"));
        when(property.getPropertyValues().getStringValue()).thenReturn(stringValues);
        when(stringValue.getValue()).thenReturn(new String("layer4"));
        Assert.assertEquals(method.invoke(updateNode, propertyList), "The operating-mode is not legal.");

    }
}