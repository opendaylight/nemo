/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.intent;
import  org.opendaylight.nemo.intent.IntentResolverUtils;

import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.node.instance.Property;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.hosts.PhysicalHost;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.links.VirtualLink;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.nodes.VirtualNode;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.intent.vn.mapping.results.user.intent.vn.mapping.IntentVnMappingResult;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.intent.vn.mapping.results.user.intent.vn.mapping.intent.vn.mapping.result.VirtualResource;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.PhysicalHostId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.PhysicalNodeId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.VirtualNodeId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.Objects;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Connection;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Flow;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Node;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.operations.Operation;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.flow.instance.MatchItem;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.node.instance.Property;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.node.instance.SubNode;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.property.values.StringValue;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.PropertyValues;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.operation.instance.Action;
import org.opendaylight.yangtools.yang.binding.DataObject;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.property.values.StringValue;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.node.instance.SubNode;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.Objects;
import java.util.ArrayList;
import java.util.List;

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
/**
 * Created by ldzd11 on 2015/12/16.
 */
public class IntentResolverUtilsTest extends IntentResolverUtils{

    private IntentResolverUtils intentResolverUtils;
    private  StringValue stringValue;
    private List<StringValue> stringValueList;

    private Property property;
    private List<Property> propertyList;
    private PropertyName propertyName;

    private IntentVnMappingResult intentVnMappingResult;
    private List<IntentVnMappingResult> intentVnMappingResultList;

    private VirtualNode virtualNode;
    private List<VirtualNode> virtualNodeList;
    private VirtualNodeId virtualNodeId;
    private  VirtualNodeId srcVirtualNodeId;
    private VirtualNodeId destVirtualNodeId;

    private VirtualLink virtualLink;
    private List<VirtualLink> virtualLinkList;

    private Node node;
    private List<Node> nodeList;
    private List<Node> nodeListnull;
    private NodeId nodeId;

    private PhysicalHost physicalHost;
    private PhysicalHostId physicalHostId;
    private List<PhysicalHost> physicalHostList;
    private PropertyValues propertyValues;

    private SubNode subNode;
    private SubNode subNode2;
    private List<SubNode> subNodeList;
    private List<SubNode> subNodeList2;

    private Objects objects;
    private ObjectId objectId;

    private Connection connection;
    private List<Connection> connectionList;
    private List<Connection> connectionListnull;

    private Flow flow;
    private  List<Flow> flowList;

    private List<Operation> operationListnull;
    private List<Operation> operationList;
    private Operation operation;

    private ConnectionId connectionId;




    private org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.connection.instance.Property propertyconnection;
    private List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.connection.instance.Property> propertyListconnection;
   // private PropertyName propertyName;


    private IntentId intentId;


    @org.junit.Before
    public void setUp() throws Exception {
        property = mock(Property.class);
        propertyList =  new ArrayList<Property>(1);
        propertyList.add(property);

        propertyName = mock(PropertyName.class);

        intentVnMappingResult = mock(IntentVnMappingResult.class);
        intentVnMappingResultList = new ArrayList<IntentVnMappingResult>(1);
        intentVnMappingResultList.add(intentVnMappingResult);

        virtualNode = mock(VirtualNode.class);
        virtualNodeList = new ArrayList<VirtualNode>(1);
        virtualNodeList.add(virtualNode);

        virtualLink = mock(VirtualLink.class);
        virtualLinkList = new ArrayList<VirtualLink>(1);
        virtualLinkList.add(virtualLink);

        node = mock(Node.class);
        nodeList = new ArrayList<Node>(1);
        nodeListnull = new ArrayList<Node>(0);
        nodeList.add(node);

        intentId = mock(IntentId.class);
        virtualNodeId = mock(VirtualNodeId.class);
        srcVirtualNodeId = mock(VirtualNodeId.class);
        destVirtualNodeId = mock(VirtualNodeId.class);
        nodeId = mock(NodeId.class);

        physicalHost = mock(PhysicalHost.class);
        physicalHostId = mock(PhysicalHostId.class);
        physicalHostList = new ArrayList<PhysicalHost>(1);
        physicalHostList.add(physicalHost);

        stringValue = mock(StringValue.class);
        stringValueList = new ArrayList<StringValue>(1);
        stringValueList.add(stringValue);

        propertyconnection = mock(org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.connection.instance.Property.class);
        propertyListconnection = new ArrayList<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.connection.instance.Property>(1);
        propertyListconnection.add(propertyconnection);
        propertyValues = mock(PropertyValues.class);

        subNode = mock(SubNode.class);
        subNodeList = new ArrayList<SubNode>(1);
        subNodeList.add(subNode);
        subNode2 = mock(SubNode.class);
        subNodeList2 = new ArrayList<SubNode>(2);
        subNodeList2.add(subNode);
        subNodeList2.add(subNode2);

        objects = mock(Objects.class);
        objectId = mock(ObjectId.class);

        connection = mock(Connection.class);
        connectionList = new ArrayList<Connection>(1);
        connectionListnull = new ArrayList<Connection>(0);
        connectionList.add(connection);
        connectionId = mock(ConnectionId.class);

        flow = mock(Flow.class);
        flowList = new ArrayList<Flow>(1);
        flowList.add(flow);

        operationListnull = new ArrayList<Operation>(0);
        operation = mock(Operation.class);
        operationList = new ArrayList<Operation>(1);
        operationList.add(operation);


        intentResolverUtils = new IntentResolverUtils();

    }

    @org.junit.Test
    public void testCheckExternalLayer3Group() throws Exception {
        when(node.getProperty()).thenReturn(propertyList);
        //into getNodeProperty
        propertyName = new PropertyName("ac-info-network");
        when(property.getPropertyName()).thenReturn(propertyName);
        when(property.getPropertyValues()).thenReturn(propertyValues);
        when(property.getPropertyValues().getStringValue()).thenReturn(stringValueList);
        when(property.getPropertyValues().getStringValue().get(0).getValue()).thenReturn(new String("layer3"));

        Assert.assertEquals(intentResolverUtils.checkExternalLayer3Group(node),true);


    }

    @org.junit.Test
    public void testGetPhysicalHost() throws Exception {
        when(node.getNodeId()).thenReturn(nodeId);
        when(node.getNodeId().getValue()).thenReturn(new String("00001111-0000-0000-0000-000011112222"));
        physicalHostId = new PhysicalHostId(node.getNodeId().getValue());
        //into getPhysicalHost
        when(physicalHost.getHostId()).thenReturn(physicalHostId);
        Assert.assertEquals(intentResolverUtils.getPhysicalHost(physicalHostList,node),physicalHost);

    }

    @org.junit.Test
    public void testGetNodeProperty() throws Exception {
        when(property.getPropertyName()).thenReturn(propertyName);
        Assert.assertEquals(intentResolverUtils.getNodeProperty(propertyList,propertyName),property);

    }

    @org.junit.Test
    public void testGeneratePhysicalNodeIdFromNodeLocationProperty() throws Exception {
        when(property.getPropertyValues()).thenReturn(propertyValues);
        when(property.getPropertyValues().getStringValue()).thenReturn(stringValueList);
        when(property.getPropertyValues().getStringValue().get(0).getValue()).thenReturn(new String("test:"));
        Assert.assertEquals(intentResolverUtils.generatePhysicalNodeIdFromNodeLocationProperty(property),new PhysicalNodeId(new String("test")));

    }

    @org.junit.Test
    public void testGetIntentVnMappingResult() throws Exception {
        when(intentVnMappingResult.getIntentId()).thenReturn(intentId);
        Assert.assertEquals(intentResolverUtils.getIntentVnMappingResult(intentVnMappingResultList,intentId),intentVnMappingResult);


    }

    @org.junit.Test
    public void testGetVirtualNode() throws Exception {
        when(virtualNode.getNodeId()).thenReturn(virtualNodeId);
        Assert.assertEquals(intentResolverUtils.getVirtualNode(virtualNodeList,virtualNodeId),virtualNode);

    }

    @org.junit.Test
    public void testGetNode() throws Exception {
        when(node.getNodeId()).thenReturn(nodeId);
        Assert.assertEquals(intentResolverUtils.getNode(nodeList,nodeId),node);


    }

    @org.junit.Test
    public void testGetConnectionProperty() throws Exception {
        when(propertyconnection.getPropertyName()).thenReturn(propertyName);
        Assert.assertEquals(intentResolverUtils.getConnectionProperty(propertyListconnection, propertyName), propertyconnection);

    }

    @org.junit.Test
    public void testSortSubNodes() throws Exception {
        Assert.assertEquals(intentResolverUtils.sortSubNodes(subNodeList), subNodeList);

        List<SubNode> sortedSubNodes = new ArrayList<SubNode>(subNodeList2.size());
        sortedSubNodes.addAll(subNodeList2);

        long a = 1;
        long b = 0;
        when(subNode.getOrder()).thenReturn(a);
        when(subNode2.getOrder()).thenReturn(b);

        List<SubNode> sortedSubNodes2 = new ArrayList<SubNode>(subNodeList2.size());
        sortedSubNodes2.add(subNode2);
        sortedSubNodes2.add(subNode);

        Assert.assertEquals(intentResolverUtils.sortSubNodes(subNodeList2),sortedSubNodes2);




    }

    @org.junit.Test
    public void testCheckAllLayer2OperatingMode() throws Exception {
        //into getNode
        when(subNode.getNodeId()).thenReturn(nodeId);
        NodeId otherid ;
        otherid = mock(NodeId.class);
        when(node.getNodeId()).thenReturn(otherid);
        Assert.assertEquals(intentResolverUtils.checkAllLayer2OperatingMode(subNodeList, nodeList), false);

        when(node.getNodeId()).thenReturn(nodeId);
        when(subNode.getNodeId()).thenReturn(nodeId);
        //into getNodeProperty
        when(node.getProperty()).thenReturn(propertyList);
        propertyName = new PropertyName("operating-other");
        when(property.getPropertyName()).thenReturn(propertyName);
        Assert.assertEquals(intentResolverUtils.checkAllLayer2OperatingMode(subNodeList, nodeList), false);

        when(node.getNodeId()).thenReturn(nodeId);
        when(subNode.getNodeId()).thenReturn(nodeId);
        when(node.getProperty()).thenReturn(propertyList);
        propertyName = new PropertyName("operating-mode");
        when(property.getPropertyName()).thenReturn(propertyName);
        when(property.getPropertyValues()).thenReturn(propertyValues);
        when(property.getPropertyValues().getStringValue()).thenReturn(stringValueList);
        when(property.getPropertyValues().getStringValue().get(0).getValue()).thenReturn(new String("layer2"));

        Assert.assertEquals(intentResolverUtils.checkAllLayer2OperatingMode(subNodeList, nodeList), true);
    }

    @org.junit.Test
    public void testCheckAllLayer3OperatingMode() throws Exception {

        when(subNode.getNodeId()).thenReturn(nodeId);
        NodeId otherid ;
        otherid = mock(NodeId.class);
        when(node.getNodeId()).thenReturn(otherid);
        Assert.assertEquals(intentResolverUtils.checkAllLayer3OperatingMode(subNodeList, nodeList), false);

        when(node.getNodeId()).thenReturn(nodeId);
        when(subNode.getNodeId()).thenReturn(nodeId);
        //into getNodeProperty
        when(node.getProperty()).thenReturn(propertyList);
        propertyName = new PropertyName("operating-other");
        when(property.getPropertyName()).thenReturn(propertyName);
        Assert.assertEquals(intentResolverUtils.checkAllLayer3OperatingMode(subNodeList, nodeList), false);

        when(node.getNodeId()).thenReturn(nodeId);
        when(subNode.getNodeId()).thenReturn(nodeId);
        when(node.getProperty()).thenReturn(propertyList);
        propertyName = new PropertyName("operating-mode");
        when(property.getPropertyName()).thenReturn(propertyName);
        when(property.getPropertyValues()).thenReturn(propertyValues);
        when(property.getPropertyValues().getStringValue()).thenReturn(stringValueList);
        when(property.getPropertyValues().getStringValue().get(0).getValue()).thenReturn(new String("layer3"));

        Assert.assertEquals(intentResolverUtils.checkAllLayer3OperatingMode(subNodeList, nodeList), true);


    }

    @org.junit.Test
    public void testGetVirtualLink() throws Exception {
        when(virtualLink.getSrcNodeId()).thenReturn(srcVirtualNodeId);
        when(virtualLink.getDestNodeId()).thenReturn(destVirtualNodeId);
        Assert.assertEquals(intentResolverUtils.getVirtualLink(virtualLinkList,srcVirtualNodeId,destVirtualNodeId),virtualLink);


    }

    @org.junit.Test
    public void testGetObject() throws Exception {
        when(objects.getNode()).thenReturn(nodeList);
        when(objectId.getValue()).thenReturn(new String("00001111-0000-0000-0000-000011112222"));
        NodeId nodeId2 = new NodeId(objectId.getValue());
        //into getnode
        when(node.getNodeId()).thenReturn(nodeId2);
        Assert.assertEquals(intentResolverUtils.getObject(objects, objectId), node);

        when(objects.getNode()).thenReturn(nodeListnull);
        when(objects.getConnection()).thenReturn(connectionList);
        when(objectId.getValue()).thenReturn(new String("00001111-0000-0000-0000-000011112222"));
        ConnectionId connectionId2 = new ConnectionId(objectId.getValue());
        //into getconnection
        when(connection.getConnectionId()).thenReturn(connectionId2);
        Assert.assertEquals(intentResolverUtils.getObject(objects, objectId), connection);

        when(objects.getNode()).thenReturn(nodeListnull);
        when(objects.getConnection()).thenReturn(connectionListnull);
        when(objects.getFlow()).thenReturn(flowList);
        when(objectId.getValue()).thenReturn(new String("00001111-0000-0000-0000-000011112222"));
        FlowId flowId2 = new FlowId(objectId.getValue());
        //into getflow
        when(flow.getFlowId()).thenReturn(flowId2);
        Assert.assertEquals(intentResolverUtils.getObject(objects,objectId),flow);

    }

    @org.junit.Test
    public void testGetSameTargetObjectOperations() throws Exception {
        Assert.assertEquals(intentResolverUtils.getSameTargetObjectOperations(operationList,operation),operationListnull);

    }

    @org.junit.Test
    public void testGetGreaterAndEqualPriorityOperations() throws Exception {
     intentResolverUtils.getGreaterAndEqualPriorityOperations(operationList,operation,operationList,operationList);

    }

    @org.junit.Test
    public void testGetConflictingOperation() throws Exception {
        Assert.assertEquals(intentResolverUtils.getConflictingOperation(operationList,operation),null);

    }

    @org.junit.Test
    public void testGetConflictingOperations() throws Exception {
        Assert.assertEquals(intentResolverUtils.getConflictingOperations(operationList,operation),null);
    }
}