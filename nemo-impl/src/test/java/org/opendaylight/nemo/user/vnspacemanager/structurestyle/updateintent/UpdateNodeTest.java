/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.user.vnspacemanager.structurestyle.updateintent;

import org.opendaylight.nemo.user.vnspacemanager.structurestyle.updateintent.UpdateNode;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.template.definitions.TemplateDefinition;

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
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.node.instance.SubNode;
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
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.nemo.user.tenantmanager.TenantManage;
import org.opendaylight.nemo.user.vnspacemanager.languagestyle.NEMOConstants;
import org.opendaylight.nemo.user.vnspacemanager.languagestyle.updateintentlang.UpdateTemplateInstanceLang;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Node;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.template.instances.TemplateInstanceBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.template.instances.TemplateInstanceKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.User;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.node.definitions.NodeDefinition;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.node.instance.Property;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.definitions.PropertyDefinition;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.PropertyValues;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.property.values.StringValue;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.TemplateParameter;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.TemplateParameterBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.TemplateParameterKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.ParameterValuesBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.parameter.values.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.template.instances.TemplateInstance;


import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserRoleName;
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
import static org.mockito.Mockito.doNothing;

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
    private org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.property.values.RangeValue rangeValue;
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
    private SubNode subNode;
    private UserRoleName userRoleName;
    private org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.property.values.IntValue intValue;
    private List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.property.values.IntValue> intValueList;
    private List<SubNode> subNodeList;
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

        subNode = mock(SubNode.class);
        subNodeList = new ArrayList<SubNode>();
        subNodeList.add(subNode);

        intValue = mock(org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.property.values.IntValue.class);
        intValueList = new ArrayList<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.property.values.IntValue>();
        intValueList.add(intValue);

        rangeValue = mock(org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.property.values.RangeValue.class);



        updateNode = new UpdateNode(dataBroker,tenantManage);
        nodeType2 = mock(NodeType.class);
        userRoleName = mock(UserRoleName.class);



    }

    @org.junit.Test
    public void testNodeHandling() throws Exception {

        Class<UpdateNode> class1 = UpdateNode.class;
        TemplateName templateName = new TemplateName("aaa");
        TemplateDefinition templateDefinition = mock(TemplateDefinition.class);
        Map<TemplateName, TemplateDefinition> map1 = new HashMap<TemplateName, TemplateDefinition>();
        map1.put(templateName, templateDefinition);
       // userTemplateDefinitionMap.put(userId, map1);
        //field1.set(field.get(updateNode), userTemplateDefinitionMap);

        when(tenantManage.getTempalteDefinition(userId)).thenReturn(map1);
        when(node.getNodeType()).thenReturn(nodeType);
        when(node.getNodeType().getValue()).thenReturn(new String("aaa"));

        when(tenantManage.getDefinitionDataStore(userId)).thenReturn(map1)
;       when(node.getNodeType()).thenReturn(nodeType);
        when(node.getNodeType().getValue()).thenReturn(new String("aaa"));

        ///////////////////////////////////////////////////////////////
        when(tenantManage.getTempalteDefinition(userId)).thenReturn(null);
        when(tenantManage.getDefinitionDataStore(userId)).thenReturn(null);
        Map<UserId, User> map2 = new HashMap<UserId,User>();
        UserId userId1 = mock(UserId.class);
        User user1 = mock(User.class);
        map2.put(userId1, user1);
        when(tenantManage.getUsers()).thenReturn(map2);
        when(user1.getUserRole()).thenReturn(userRoleName);
        when(user1.getUserRole().getValue()).thenReturn(NEMOConstants.admin);
        when(user1.getUserId()).thenReturn(userId1);
        when(tenantManage.getDefinitionDataStore(user1.getUserId())).thenReturn(map1);
        when(tenantManage.getDefinitionDataStore(user1.getUserId())).thenReturn(map1);
        /////////////////////////////////////////////////////////////////////////////////


        //first nodeModel = true 79 if
        when(node.getSubNode()).thenReturn(subNodeList);
        Assert.assertEquals(updateNode.NodeHandling(userId, node), "Subnodes should not be included in template instance.");
        //else 83
        when(node.getSubNode()).thenReturn(null);
        when(node.getNodeId()).thenReturn(nodeId);
        when(node.getNodeId().getValue()).thenReturn(new String("00001111-0000-0000-0000-000011112222"));
        when(node.getNodeName()).thenReturn(nodeName);
        when(node.getNodeName().getValue()).thenReturn(new String("nodename"));
        when(node.getProperty()).thenReturn(propertyList);
        when(property.getPropertyName()).thenReturn(propertyName);
        when(property.getPropertyName().getValue()).thenReturn(new String("propertyname"));
        when(property.getPropertyValues()).thenReturn(propertyValues);
        //intValue
        when(property.getPropertyValues().getIntValue()).thenReturn(intValueList);
        long a = 1;
        long b = 2;
        when(intValue.getOrder()).thenReturn(a);
        when(intValue.getValue()).thenReturn(a);
        //stringValue
        when(property.getPropertyValues().getStringValue()).thenReturn(stringValues);
        when(stringValue.getOrder()).thenReturn(a);
        when(stringValue.getValue()).thenReturn(new String("stringvalue"));
        //RangeValue
        when(property.getPropertyValues().getRangeValue()).thenReturn(rangeValue);
        when(property.getPropertyValues().getRangeValue().getMin()).thenReturn(a);
        when(property.getPropertyValues().getRangeValue().getMax()).thenReturn(b);

        UpdateTemplateInstance updateTemplateInstance = mock(UpdateTemplateInstance.class);
        Field field1 = class1.getDeclaredField("updateTemplateInstance");
        field1.setAccessible(true);
        field1.set(updateNode, updateTemplateInstance);
        when(updateTemplateInstance.checkTemplateInstance(any(UserId.class), any(TemplateInstance.class))).thenReturn(new String("success"));
        Assert.assertEquals(updateNode.NodeHandling(userId, node), "success");


        when(tenantManage.getTempalteDefinition(userId)).thenReturn(null);
        when(tenantManage.getDefinitionDataStore(userId)).thenReturn(null);
        Map<UserId, User> mapnull = new HashMap<UserId, User>();
        when(tenantManage.getUsers()).thenReturn(mapnull);

        //into checkDefinition
        CheckedFuture connectiondefinitionFuture = mock(CheckedFuture.class);
        ReadOnlyTransaction readOnlyTransaction = mock(ReadOnlyTransaction.class);
        when(dataBroker.newReadOnlyTransaction()).thenReturn(readOnlyTransaction);
        when(readOnlyTransaction.read(any(LogicalDatastoreType.class), any(InstanceIdentifier.class))).thenReturn(connectiondefinitionFuture);

        //Class<UpdateNode> class1 = UpdateNode.class;
        Class<GetDefinitions> class2 = GetDefinitions.class;
        Field field = class1.getDeclaredField("getDefinitions");
        field.setAccessible(true);
        Field field2 = class2.getDeclaredField("nodeDefinitionList");
        field2.setAccessible(true);


        List<NodeDefinition> nodeDefinitionList = new ArrayList<NodeDefinition>();
        NodeDefinition nodeDefinition = mock(NodeDefinition.class);
        when(nodeDefinition.getNodeType()).thenReturn(nodeType);
        nodeDefinitionList.add(nodeDefinition);

        field2.set(field.get(updateNode), nodeDefinitionList);
       // System.out.print("2");
       // Assert.assertTrue((List<NodeDefinition>) field1.get(field.get(updateNode)) == nodeDefinitionList);

        when(node.getNodeType()).thenReturn(nodeType);
        when(node.getProperty()).thenReturn(null);
        when(nodeDefinition.getPropertyDefinition()).thenReturn(null);

        //into checkinstance
        when(tenantManage.getNode(userId)).thenReturn(null);
        when(tenantManage.getNodeDataStore(userId)).thenReturn(null);
        //into else tenantManage.setNode
        when(node.getNodeId()).thenReturn(nodeId);
        doNothing().when(tenantManage).setNode(any(UserId.class), any(NodeId.class), any(Node.class));
        Assert.assertEquals(updateNode.NodeHandling(userId, node), null);
    }
    @Test
    public void testcheckInstance() throws Exception {
        Class<UpdateNode> class1 = UpdateNode.class;
        Method method = class1.getDeclaredMethod("checkInstance", new Class[]{UserId.class, Node.class});
        method.setAccessible(true);

        Class<TenantManage> class2 = TenantManage.class;
        Field field = class1.getDeclaredField("tenantManage");
        field.setAccessible(true);
        Field field1 = class2.getDeclaredField("userNodeMap");
        field1.setAccessible(true);

         Map<UserId, Map<NodeId, Node>> userNodeMap = new HashMap<UserId, Map<NodeId, Node>>();
        Map<UserId, Map<NodeId, Node>> userNodeMap2 = new HashMap<UserId, Map<NodeId, Node>>();

        Map<NodeId, Node> map1 = new HashMap<NodeId, Node>();
         map1.put(nodeId, node2);
        userNodeMap.put(userId, map1);
        field1.set(field.get(updateNode), userNodeMap);

        when(tenantManage.getNode(userId)).thenReturn(map1);
        when(node.getNodeId()).thenReturn(nodeId);
        when(node2.getNodeName()).thenReturn(nodeName);
        when(node.getNodeName()).thenReturn(nodeName2);
        Assert.assertEquals(method.invoke(updateNode, userId, node), "The node name should not be changed.");

        when(node2.getNodeName()).thenReturn(nodeName);
        when(node.getNodeName()).thenReturn(nodeName);
        when(node2.getNodeType()).thenReturn(nodeType2);
        when(node.getNodeType()).thenReturn(nodeType);
        Assert.assertEquals(method.invoke(updateNode, userId, node), "The node type should not be changed.");

        field1.set(field.get(updateNode), userNodeMap2);
        Field field2 = class2.getDeclaredField("user");
        field2.setAccessible(true);
        field2.set(field.get(updateNode), user);
        when(user.getObjects()).thenReturn(objects);
        when(user.getObjects().getNode()).thenReturn(nodeList);
        when(node2.getNodeId()).thenReturn(nodeId);//map.put(nodeId,node2)
        //when(tenantManage.getNodeDataStore(userId)).thenReturn(map1);
        when(node.getNodeId()).thenReturn(nodeId);
        when(node2.getNodeName()).thenReturn(nodeName2);
        when(node.getNodeName()).thenReturn(nodeName);
        Assert.assertEquals(method.invoke(updateNode, userId, node), "The node name should not be changed.");

        when(node2.getNodeName()).thenReturn(nodeName);
        when(node.getNodeName()).thenReturn(nodeName);
        when(node2.getNodeType()).thenReturn(nodeType2);
        when(node.getNodeType()).thenReturn(nodeType);
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