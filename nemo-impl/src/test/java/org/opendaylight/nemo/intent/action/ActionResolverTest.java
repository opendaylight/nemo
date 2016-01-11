/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.nemo.intent.action;


import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import  java.io.*;
//import  java.util.*;

import org.junit.runner.RunWith;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.nemo.intent.IntentResolutionException;
import org.opendaylight.nemo.intent.IntentResolverUtils;
import org.opendaylight.nemo.intent.NodeMapper;
import org.opendaylight.nemo.intent.computation.VNComputationUnit;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.VirtualNetwork;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.links.VirtualLink;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.links.VirtualLinkBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.nodes.VirtualNode;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.paths.VirtualPath;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.paths.VirtualPathBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.VirtualPort;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.VirtualPortBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.intent.vn.mapping.results.UserIntentVnMapping;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.intent.vn.mapping.results.user.intent.vn.mapping.IntentVnMappingResult;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.intent.vn.mapping.results.user.intent.vn.mapping.IntentVnMappingResultBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.intent.vn.mapping.results.user.intent.vn.mapping.intent.vn.mapping.result.VirtualResource;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.intent.vn.mapping.results.user.intent.vn.mapping.intent.vn.mapping.result.VirtualResourceBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Connection;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Flow;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Node;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.operations.Operation;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.User;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.connection.instance.Property;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.IntValue;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.StringValue;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.operation.instance.Action;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.ParameterValues;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.PropertyValues;
//import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.property.values.IntValue;
//import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.*;
//import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.ConnectionId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.VirtualLinks;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.VirtualNodes;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.VirtualPaths;
//import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.Objects;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.flow.instance.MatchItem;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.match.item.instance.MatchItemValue;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.node.instance.SubNode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.support.membermodification.MemberMatcher;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.lang.model.type.ArrayType;
//import java.sql.Connection;
import java.lang.reflect.Array;
import java.util.*;
import java.lang.*;

import java.lang.reflect.Method;
import java.lang.reflect.Field;



/**
 * Created by Thomas Liu on 2015/12/7.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ActionResolverUtils.class,IntentResolverUtils.class})
public class ActionResolverTest extends TestCase {

    private ActionResolver actionResolverTest;
    private DataBroker dataBroker;
    private Map<UserId, VNComputationUnit> vnComputationUnits;

    private User user;
    private Operation operation;
    private Node node;
    private Connection connection;
    private Flow flow;
    private VirtualNetwork virtualNetwork;
    private  UserIntentVnMapping userIntentVnMapping;
    private UserId userId ;

    List<Action> actions;
    private Action action;
    List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.IntValue> parameterValues1;
    private org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.IntValue intValue1;
    List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.property.values.IntValue> parameterValues2;
    private org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.property.values.IntValue intValue2;
    private ParameterValues parametervalues;
    private PropertyValues propertyValues;
    private Property property,property2;
    private List<Property> properties;
    private List<IntentVnMappingResult> intentVnMappingResults;
    private ConnectionId connectionId;
    private List<VirtualResource> virtualResources;
    private VirtualLinks virtualLink;
    private List<VirtualLink> virtualLinks;
    private VirtualResourceEntityId virtualResourceEntityId;
    private Class class1;
    private Method method;
    private Field field;


    //deny
    private List<Node> nodes;
    private List<VirtualNode> nodes2;

    private List<VirtualNode> virtualNodes;
    //private List<IntentVnMappingResult> intentVnMappingResults;
    private org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.Objects objects;
    private VirtualNodes virtualNode;
    private List<MatchItem> matchItem;
    private MatchItemValue matchItemValue;
    private NodeId nodeId;
    private List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.node.instance.Property> properties2;
    private List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.property.values.StringValue> stringValue;

    //gothrough
    private List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.StringValue> stringValue2;
    private List<Node> nodes3;
    private List<Node> nodes4;
    private org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.Objects objects2;
    private VirtualPaths virtualPath;
    private List<VirtualPath> virtualPaths;
    private List<SubNode> subNodes;
    private List<VirtualPort> virtualPorts1,virtualPorts2;



    @org.junit.Before
    public void setUp() throws Exception {
        userId = new UserId("11111111-1111-1111-1111-111111111111");
        dataBroker = mock(DataBroker.class);
        vnComputationUnits = new HashMap<UserId, VNComputationUnit>();
        vnComputationUnits.put(userId, mock(VNComputationUnit.class));
        actionResolverTest = new ActionResolver(dataBroker,vnComputationUnits);


        user = mock(User.class);
        operation = mock(Operation.class);
        node = mock(Node.class);
        connection = mock(Connection.class);
        flow = mock(Flow.class);
        virtualNetwork = mock(VirtualNetwork.class);
        userIntentVnMapping = mock(UserIntentVnMapping.class);



        actions = new ArrayList<Action>();
        actions.add(mock(Action.class));
        parameterValues1 = new ArrayList<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.IntValue>(){{ add(mock(org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.IntValue.class)); }};
        parameterValues2 = new ArrayList<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.property.values.IntValue>(){{ add(mock(org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.property.values.IntValue.class)); }};
        parametervalues = mock(ParameterValues.class);
        property = mock(Property.class);
        properties = new ArrayList<Property>(){{add(mock(Property.class));}};
        propertyValues = mock(PropertyValues.class);
        intentVnMappingResults = new ArrayList<IntentVnMappingResult>(){{add(mock(IntentVnMappingResult.class));}};
        connectionId = mock(ConnectionId.class);
        virtualResources = new ArrayList<VirtualResource>(){{add(mock(VirtualResource.class));}};
        virtualLink = mock(VirtualLinks.class);
        virtualLinks = new ArrayList<VirtualLink>(){{add(mock(VirtualLink.class));}};
        virtualResourceEntityId = mock(VirtualResourceEntityId.class);

        //deny
        nodes = new ArrayList<Node>(){{add(mock(Node.class));}};
        //nodes2 = new ArrayList<VirtualNode>(){{add(mock(VirtualNode.class));}};
        virtualNodes = new ArrayList<VirtualNode>(){{add(mock(VirtualNode.class));}};
        //intentVnMappingResults = new ArrayList<IntentVnMappingResult>(){{add(mock(IntentVnMappingResult.class));}};
        objects = mock(org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.Objects.class);
        virtualNode = mock(VirtualNodes.class);
        matchItem = new ArrayList<MatchItem>(){{add(mock(MatchItem.class));}};
        matchItemValue = mock(MatchItemValue.class);
        nodeId = mock(NodeId.class);
        properties2 = new ArrayList<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.node.instance.Property>(){{add(mock(org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.node.instance.Property.class));}};
        stringValue = new ArrayList<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.property.values.StringValue>(){{add(mock(org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.property.values.StringValue.class));}};


        //gothrough
        stringValue2 = new ArrayList<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.StringValue>(){{add(mock(org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.StringValue.class));}};
        nodes3 = new ArrayList<Node>(){{add(mock(Node.class));}};
        nodes4 = new ArrayList<Node>(){{add(mock(Node.class));}};
        objects2 = mock(org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.Objects.class);
        virtualPath = mock(VirtualPaths.class);
        virtualPaths = new ArrayList<VirtualPath>(){{add(mock(VirtualPath.class));}};
        subNodes = new ArrayList<SubNode>(){{add(mock(SubNode.class));}};
        virtualPorts1 = new ArrayList<VirtualPort>();
        virtualPorts2 = new ArrayList<VirtualPort>();
        class1 = ActionResolver.class;

    }

    @org.junit.Test
    public void testResolveActions() throws Exception {
        actionResolverTest.resolveActions(user,operation,node,virtualNetwork, userIntentVnMapping);
    }

    @org.junit.Test
    public void testResolveActions1() throws Exception {
        when(operation.getAction()).thenReturn(actions);
        Action qosBandwidthAction = mock(Action.class);
        PowerMockito.mockStatic(ActionResolverUtils.class);
        PowerMockito.when(ActionResolverUtils.getAction(any(List.class), any(ActionName.class))).thenReturn(qosBandwidthAction);
        //test resolveQosBandwidthAction()
        when(qosBandwidthAction.getParameterValues()).thenReturn(parametervalues);
        when(parametervalues.getIntValue()).thenReturn(parameterValues1);
        IntValue parameterValue = parameterValues1.get(0);
        when(parameterValue.getValue()).thenReturn(1L);
        when(connection.getProperty()).thenReturn(properties);
        Property property = mock(Property.class);
        PowerMockito.mockStatic(IntentResolverUtils.class);
        PowerMockito.when(IntentResolverUtils.getConnectionProperty(any(List.class), any(PropertyName.class))).thenReturn(property);
        when(property.getPropertyValues()).thenReturn(propertyValues);
        when(propertyValues.getIntValue()).thenReturn(parameterValues2);
        intValue2 = parameterValues2.get(0);
        when(intValue2.getValue()).thenReturn(2L);
        when(userIntentVnMapping.getIntentVnMappingResult()).thenReturn(intentVnMappingResults);
        IntentVnMappingResult intentVnMappingResult = mock(IntentVnMappingResult.class);
        when(connection.getConnectionId()).thenReturn(connectionId);
        PowerMockito.when(IntentResolverUtils.getIntentVnMappingResult(any(List.class), any(IntentId.class))).thenReturn(intentVnMappingResult);
        when(intentVnMappingResult.getVirtualResource()).thenReturn(virtualResources);
        when(virtualNetwork.getVirtualLinks()).thenReturn(virtualLink);
        when(virtualLink.getVirtualLink()).thenReturn(virtualLinks);
        when(virtualResources.get(0).getVirtualResourceEntityId()).thenReturn(virtualResourceEntityId);
        when(virtualResourceEntityId.getValue()).thenReturn("11111111-1111-1111-1111-111111111111");
        when(virtualLinks.get(0).getLinkId()).thenReturn(new VirtualLinkId("11111111-1111-1111-1111-111111111111"));
        actionResolverTest.resolveActions(user,operation,connection,virtualNetwork, userIntentVnMapping);
    }

    @org.junit.Test
    public void testResolveActionsDeny() throws Exception {
        //deny
        when(operation.getAction()).thenReturn(actions);
        Action denyAction = mock(Action.class);
        PowerMockito.mockStatic(ActionResolverUtils.class);
        PowerMockito.when(ActionResolverUtils.getAction(any(List.class), any(ActionName.class))).thenReturn(denyAction);
        when(user.getObjects()).thenReturn(objects);
        when(objects.getNode()).thenReturn(nodes);
        when(virtualNetwork.getVirtualNodes()).thenReturn(virtualNode);
        when(virtualNode.getVirtualNode()).thenReturn(virtualNodes);
        when(userIntentVnMapping.getIntentVnMappingResult()).thenReturn(intentVnMappingResults);
        VirtualNode sourceVirtualNode = mock(VirtualNode.class);
        PowerMockito.when(ActionResolverUtils.getSourceVirtualRouterOfFlow(virtualNodes, flow, nodes, intentVnMappingResults)).thenReturn(sourceVirtualNode);
        VirtualNode destinationVirtualNode = mock(VirtualNode.class);
        PowerMockito.when(ActionResolverUtils.getDestinationVirtualRouterOfFlow(virtualNodes, flow, nodes, intentVnMappingResults)).thenReturn(destinationVirtualNode);
        when(user.getUserId()).thenReturn(userId);
        VNComputationUnit vnComputationUnit = vnComputationUnits.get(userId);
        //List<VirtualPath>  virtualPath = new ArrayList<VirtualPath>(){{ add(mock(VirtualPath.class));}};
        VirtualPath virtualPath = mock(VirtualPath.class);
        VirtualNodeId vnodeIds = mock(VirtualNodeId.class);
        VirtualNodeId vnodeIdd = mock(VirtualNodeId.class);
        when(sourceVirtualNode.getNodeId()).thenReturn(vnodeIds);
        when(destinationVirtualNode.getNodeId()).thenReturn(vnodeIdd);
        when(vnComputationUnit.computePath(vnodeIds, vnodeIdd)).thenReturn(virtualPath);
        List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.path.instance.VirtualLink> virtualLinks = new ArrayList<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.path.instance.VirtualLink>(){{add(mock(org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.path.instance.VirtualLink.class));}};
        when(virtualPath.getVirtualLink()).thenReturn(virtualLinks);
        VirtualPaths virtualPaths = mock(VirtualPaths.class);
        List<VirtualPath> virtualPathList = new ArrayList<VirtualPath>();
        when(virtualNetwork.getVirtualPaths()).thenReturn(virtualPaths);
        when(virtualPaths.getVirtualPath()).thenReturn(virtualPathList);
        when(virtualPath.getPathId()).thenReturn(new org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.VirtualPathId("11111111-1111-1111-1111-111111111111"));
        when(operation.getOperationId()).thenReturn(new OperationId("11111111-1111-1111-1111-111111111111"));
        actionResolverTest.resolveActions(user,operation,flow,virtualNetwork, userIntentVnMapping);
    }
    @org.junit.Test
    public void testResolveActionsAllow() throws Exception
    {

        //allow
        when(operation.getAction()).thenReturn(actions);
        Action allowAction = mock(Action.class);
        PowerMockito.mockStatic(ActionResolverUtils.class);
        PowerMockito.when(ActionResolverUtils.getAction(any(List.class), any(ActionName.class))).thenReturn(null);
        PowerMockito.when(ActionResolverUtils.getAction(any(List.class), any(ActionName.class))).thenReturn(allowAction);

        when(user.getObjects()).thenReturn(objects);
        when(objects.getNode()).thenReturn(nodes);
        when(virtualNetwork.getVirtualNodes()).thenReturn(virtualNode);
        when(virtualNode.getVirtualNode()).thenReturn(virtualNodes);
        when(userIntentVnMapping.getIntentVnMappingResult()).thenReturn(intentVnMappingResults);
        VirtualNode sourceVirtualNode = mock(VirtualNode.class);
        PowerMockito.when(ActionResolverUtils.getSourceVirtualRouterOfFlow(virtualNodes, flow, nodes, intentVnMappingResults)).thenReturn(sourceVirtualNode);
        VirtualNode destinationVirtualNode = mock(VirtualNode.class);
        PowerMockito.when(ActionResolverUtils.getDestinationVirtualRouterOfFlow(virtualNodes, flow, nodes, intentVnMappingResults)).thenReturn(destinationVirtualNode);
        when(user.getUserId()).thenReturn(userId);
        VNComputationUnit vnComputationUnit = vnComputationUnits.get(userId);
        //List<VirtualPath>  virtualPath = new ArrayList<VirtualPath>(){{ add(mock(VirtualPath.class));}};
        VirtualPath virtualPath = mock(VirtualPath.class);
        VirtualNodeId vnodeIds = mock(VirtualNodeId.class);
        VirtualNodeId vnodeIdd = mock(VirtualNodeId.class);
        when(sourceVirtualNode.getNodeId()).thenReturn(vnodeIds);
        when(destinationVirtualNode.getNodeId()).thenReturn(vnodeIdd);
        when(vnComputationUnit.computePath(vnodeIds, vnodeIdd)).thenReturn(virtualPath);
        List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.path.instance.VirtualLink> virtualLinks = new ArrayList<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.path.instance.VirtualLink>(){{add(mock(org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.path.instance.VirtualLink.class));}};
        when(virtualPath.getVirtualLink()).thenReturn(virtualLinks);
        VirtualPaths virtualPaths = mock(VirtualPaths.class);
        List<VirtualPath> virtualPathList = new ArrayList<VirtualPath>();
        when(virtualNetwork.getVirtualPaths()).thenReturn(virtualPaths);
        when(virtualPaths.getVirtualPath()).thenReturn(virtualPathList);
        when(virtualPath.getPathId()).thenReturn(new org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.VirtualPathId("11111111-1111-1111-1111-111111111111"));
        when(operation.getOperationId()).thenReturn(new OperationId("11111111-1111-1111-1111-111111111111"));
        actionResolverTest.resolveActions(user,operation,flow,virtualNetwork, userIntentVnMapping);
    }

    @org.junit.Test
    public void testResolveActionsGoChain() throws Exception {
        Node nodex = mock(Node.class);
        Action goThroughAction = mock(Action.class);
        method = class1.getDeclaredMethod("resolveGoThroughChainGroupAction",new Class[]{
                User.class,
                Operation.class,
                Flow.class,
                Action.class,
                Node.class,
                VirtualNetwork.class,
                UserIntentVnMapping.class
        });
        method.setAccessible(true);
        /*ParameterValues parameterValue = mock(ParameterValues.class);
        List<StringValue> parameterValues = new ArrayList<StringValue>();
        StringValue stringValue = mock(StringValue.class);
        parameterValues.add(stringValue);
        when(goThroughAction.getParameterValues()).thenReturn(parameterValue);
        when(parameterValue.getStringValue()).thenReturn(parameterValues);
        List<Node> nodes = new ArrayList<Node>();

        nodes.add(nodex);
        when(user.getObjects()).thenReturn(objects);
        when(objects.getNode()).thenReturn(nodes);
        when(stringValue.getValue()).thenReturn(new String("11111111-1111-1111-1111-111111111111"));
        //PowerMockito.mockStatic(IntentResolverUtils.class);
        //PowerMockito.when(IntentResolverUtils.getNode(any(List.class), any(NodeId.class))).thenReturn(nodex);
        when(nodex.getNodeId()).thenReturn(new NodeId("11111111-1111-1111-1111-111111111111"));
        when(nodex.getNodeType()).thenReturn(new NodeType("chain-group"));*/

        when(user.getObjects()).thenReturn(objects2);
        when(objects2.getNode()).thenReturn(nodes3);
        when(virtualNetwork.getVirtualNodes()).thenReturn(virtualNode);
        when(virtualNode.getVirtualNode()).thenReturn(virtualNodes);
        when(virtualNetwork.getVirtualLinks()).thenReturn(virtualLink);
        when(virtualLink.getVirtualLink()).thenReturn(virtualLinks);
        when(virtualNetwork.getVirtualPaths()).thenReturn(virtualPath);
        when(virtualPath.getVirtualPath()).thenReturn(virtualPaths);
        when(userIntentVnMapping.getIntentVnMappingResult()).thenReturn(intentVnMappingResults);

        when(nodex.getSubNode()).thenReturn(new ArrayList<SubNode>());
        PowerMockito.mockStatic(IntentResolverUtils.class);
        PowerMockito.when(IntentResolverUtils.checkAllLayer2OperatingMode(any(List.class), any(List.class))).thenReturn(false);
        when(nodex.getSubNode()).thenReturn(new ArrayList<SubNode>());
        PowerMockito.when(IntentResolverUtils.checkAllLayer3OperatingMode(any(List.class), any(List.class))).thenReturn(true);
        IntentVnMappingResult intentVnMappingResult = mock(IntentVnMappingResult.class);
        when(nodex.getNodeId()).thenReturn(new NodeId("11111111-1111-1111-1111-111111111111"));
        PowerMockito.when(IntentResolverUtils.getIntentVnMappingResult(any(List.class), any(IntentId.class))).thenReturn(intentVnMappingResult);
        VirtualNode sourceVirtualNode = mock(VirtualNode.class);
        PowerMockito.mockStatic(ActionResolverUtils.class);
        PowerMockito.when(ActionResolverUtils.getSourceVirtualRouterOfFlow(virtualNodes, flow, nodes3, intentVnMappingResults)).thenReturn(sourceVirtualNode);
        VirtualNode destinationVirtualNode = mock(VirtualNode.class);
        PowerMockito.when(ActionResolverUtils.getDestinationVirtualRouterOfFlow(virtualNodes, flow, nodes3, intentVnMappingResults)).thenReturn(destinationVirtualNode);
        List<VirtualResource> virtualResources1 = new ArrayList<VirtualResource>(){{add(mock(VirtualResource.class));}};
        when(intentVnMappingResult.getVirtualResource()).thenReturn(new ArrayList<VirtualResource>());
        PowerMockito.when(ActionResolverUtils.sortVirtualResources(any(List.class))).thenReturn(virtualResources1);
        when(virtualResources1.get(0).getParentVirtualResourceEntityId()).thenReturn(new VirtualResourceEntityId("11111111-1111-1111-1111-111111111111"));
        VirtualNode virtualNode = mock(VirtualNode.class);
        PowerMockito.mockStatic(IntentResolverUtils.class);
        PowerMockito.when(IntentResolverUtils.getVirtualNode(any(List.class), any(VirtualNodeId.class))).thenReturn(virtualNode);
        when(virtualResources1.get(0).getParentVirtualResourceEntityId()).thenReturn(new VirtualResourceEntityId("11111111-1111-1111-1111-111111111111"));
        VirtualNode virtualNode1 = mock(VirtualNode.class);
        PowerMockito.when(IntentResolverUtils.getVirtualNode(any(List.class), any(VirtualNodeId.class))).thenReturn(virtualNode1);
        VirtualLink virtualLink = mock(VirtualLink.class);
        when(sourceVirtualNode.getNodeId()).thenReturn(mock(VirtualNodeId.class));
        when(virtualNode.getNodeId()).thenReturn(mock(VirtualNodeId.class));
        PowerMockito.when(IntentResolverUtils.getVirtualLink(any(List.class), any(VirtualNodeId.class), any(VirtualNodeId.class))).thenReturn(virtualLink);
        when(virtualNode.getNodeId()).thenReturn(mock(VirtualNodeId.class));
        when(sourceVirtualNode.getNodeId()).thenReturn(mock(VirtualNodeId.class));
        when(virtualNode.getNodeId()).thenReturn(mock(VirtualNodeId.class));
        when(sourceVirtualNode.getNodeId()).thenReturn(mock(VirtualNodeId.class));
        when(virtualLink.getLinkId()).thenReturn(mock(VirtualLinkId.class));
        when(virtualNode1.getNodeId()).thenReturn(mock(VirtualNodeId.class));
        when(destinationVirtualNode.getNodeId()).thenReturn(mock(VirtualNodeId.class));
        when(destinationVirtualNode.getNodeId()).thenReturn(mock(VirtualNodeId.class));
        when(virtualNode1.getNodeId()).thenReturn(mock(VirtualNodeId.class));
        when(virtualNode1.getNodeId()).thenReturn(mock(VirtualNodeId.class));
        when(destinationVirtualNode.getNodeId()).thenReturn(mock(VirtualNodeId.class));
        when(virtualLink.getLinkId()).thenReturn(mock(VirtualLinkId.class));
        when(operation.getOperationId()).thenReturn(new OperationId("11111111-1111-1111-1111-111111111111"));
        method.invoke(actionResolverTest,user,operation,flow,goThroughAction,nodex,virtualNetwork, userIntentVnMapping);
    }


    @org.junit.Test
    public void testResolveActionsGoNormal() throws Exception {
        //ResolveActions()
        field = class1.getDeclaredField("vnComputationUnits");
        Map<UserId, VNComputationUnit> vnComputationUnits = new HashMap<UserId, VNComputationUnit>();
        VNComputationUnit vnComputationUnit = mock(VNComputationUnit.class);
        vnComputationUnits.put(userId,vnComputationUnit);
        field.setAccessible(true);
        field.set(actionResolverTest,vnComputationUnits);
        Node nodex = mock(Node.class);
        Action goThroughAction = mock(Action.class);
        method = class1.getDeclaredMethod("resolveGoThroughNormalGroupAction",new Class[]{
                User.class,
                Operation.class,
                Flow.class,
                Action.class,
                VirtualNetwork.class,
                UserIntentVnMapping.class
        });
        method.setAccessible(true);
        IntentVnMappingResult intentVnMappingResult = mock(IntentVnMappingResult.class);
        when(user.getObjects()).thenReturn(objects2);
        when(objects2.getNode()).thenReturn(nodes3);
        when(goThroughAction.getParameterValues()).thenReturn(parametervalues);
        when(parametervalues.getStringValue()).thenReturn(stringValue2);
        when(stringValue2.get(0).getValue()).thenReturn(new String("11111111-1111-1111-1111-111111111111"));
        PowerMockito.mockStatic(IntentResolverUtils.class);
        PowerMockito.when(IntentResolverUtils.getNode(any(List.class), any(NodeId.class))).thenReturn(nodex);
        when(virtualNetwork.getVirtualNodes()).thenReturn(virtualNode);
        when(virtualNode.getVirtualNode()).thenReturn(virtualNodes);
        when(userIntentVnMapping.getIntentVnMappingResult()).thenReturn(intentVnMappingResults);
        PowerMockito.mockStatic(ActionResolverUtils.class);
        PowerMockito.when(ActionResolverUtils.sortParameterValues(any(List.class))).thenReturn(stringValue2);
        when(stringValue2.get(0).getValue()).thenReturn(new String("11111111-1111-1111-1111-111111111111"));
        when(intentVnMappingResults.get(0).getVirtualResource()).thenReturn(virtualResources);
        when(stringValue.get(0).getValue()).thenReturn(new String("11111111-1111-1111-1111-111111111111"));
        PowerMockito.mockStatic(IntentResolverUtils.class);
        PowerMockito.when(IntentResolverUtils.getIntentVnMappingResult(any(List.class), any(IntentId.class))).thenReturn(intentVnMappingResult);
        when(intentVnMappingResult.getVirtualResource()).thenReturn(virtualResources);

        when(virtualResources.get(0).getVirtualResourceType()).thenReturn(VirtualResource.VirtualResourceType.Vnode);
        when(virtualResources.get(0).getVirtualResourceEntityId()).thenReturn(virtualResourceEntityId);
        when(virtualResourceEntityId.getValue()).thenReturn("11111111-1111-1111-1111-111111111111");

        PowerMockito.when(IntentResolverUtils.getVirtualNode(any(List.class), any(VirtualNodeId.class))).thenReturn(virtualNodes.get(0));
        VirtualNode sourceVirtualNode = mock(VirtualNode.class);
        PowerMockito.mockStatic(ActionResolverUtils.class);
        PowerMockito.when(ActionResolverUtils.getSourceVirtualRouterOfFlow(virtualNodes, flow, nodes3, intentVnMappingResults)).thenReturn(sourceVirtualNode);
        VirtualNode destinationVirtualNode = mock(VirtualNode.class);
        PowerMockito.when(ActionResolverUtils.getDestinationVirtualRouterOfFlow(virtualNodes, flow, nodes3, intentVnMappingResults)).thenReturn(destinationVirtualNode);
        when(user.getUserId()).thenReturn(userId);

        VirtualPath virtualPath1 = mock(VirtualPath.class);

        VirtualNodeId virtualNodeId1 = new VirtualNodeId("11111111-1111-1111-1111-111111111111");
        VirtualNodeId virtualNodeId2 = new VirtualNodeId("11111111-1111-1111-1111-111111111112");
        when(sourceVirtualNode.getNodeId()).thenReturn(virtualNodeId1);
        when(virtualNodes.get(0).getNodeId()).thenReturn(virtualNodeId2);
        when(sourceVirtualNode.getNodeId()).thenReturn(virtualNodeId1);
        when(virtualNodes.get(0).getNodeId()).thenReturn(virtualNodeId2);
        when(vnComputationUnit.computePath(virtualNodeId1,virtualNodeId2)).thenReturn(virtualPath1);

        when(destinationVirtualNode.getNodeId()).thenReturn(virtualNodeId1);
        when(virtualNodes.get(0).getNodeId()).thenReturn(virtualNodeId2);
        when(virtualNodes.get(0).getNodeId()).thenReturn(virtualNodeId2);
        when(destinationVirtualNode.getNodeId()).thenReturn(virtualNodeId1);
        when(vnComputationUnit.computePath(virtualNodeId2,virtualNodeId1)).thenReturn(virtualPath1);
        VirtualPaths virtualPaths = mock(VirtualPaths.class);
        List<VirtualPath> virtualPathList = new ArrayList<VirtualPath>();
        when(virtualNetwork.getVirtualPaths()).thenReturn(virtualPaths);
        when(virtualPaths.getVirtualPath()).thenReturn(virtualPathList);





        //method.invoke(actionResolverTest,user,operation,flow,goThroughAction,virtualNetwork, userIntentVnMapping);
        /*when(operation.getAction()).thenReturn(actions);
        when(actions.get(0).getActionName()).thenReturn(new ActionName("go-through"));
        when(actions.get(0).getParameterValues()).thenReturn(parametervalues);
        when(parametervalues.getStringValue()).thenReturn(stringValue2);

        //resolveGoThroughAction()
        when(user.getObjects()).thenReturn(objects);
        when(objects.getNode()).thenReturn(nodes4);
        when(stringValue2.get(0).getValue()).thenReturn(new String("11111111-1111-1111-1111-111111111111"));
        when(nodes4.get(0).getNodeId()).thenReturn(new NodeId("11111111-1111-1111-1111-111111111111"));

        //normal
        when(nodes4.get(0).getNodeType()).thenReturn(new NodeType("aaa-group"));
        //resolveGoThroughNormalGroupAction()
        when(user.getObjects()).thenReturn(objects2);
        when(objects2.getNode()).thenReturn(nodes4);
        when(actions.get(0).getParameterValues()).thenReturn(parametervalues);
        when(parametervalues.getStringValue()).thenReturn(stringValue2);

        when(stringValue2.get(0).getValue()).thenReturn(new String("11111111-1111-1111-1111-111111111111"));
        when(nodes4.get(0).getNodeId()).thenReturn(new NodeId("11111111-1111-1111-1111-111111111111"));

        when(virtualNetwork.getVirtualNodes()).thenReturn(virtualNode);
        when(virtualNode.getVirtualNode()).thenReturn(virtualNodes);




        when(userIntentVnMapping.getIntentVnMappingResult()).thenReturn(intentVnMappingResults);

        when(stringValue2.get(0).getValue()).thenReturn(new String("11111111-1111-1111-1111-111111111111"));
        when(intentVnMappingResults.get(0).getIntentId()).thenReturn(new IntentId("11111111-1111-1111-1111-111111111111"));
        when(intentVnMappingResults.get(0).getVirtualResource()).thenReturn(virtualResources);
        when(virtualResources.get(0).getVirtualResourceType()).thenReturn(VirtualResource.VirtualResourceType.Vnode);
        when(virtualResources.get(0).getVirtualResourceEntityId()).thenReturn(virtualResourceEntityId);
        when(virtualResourceEntityId.getValue()).thenReturn("11111111-1111-1111-1111-111111111111");

        when(virtualNodes.get(0).getNodeId()).thenReturn(new VirtualNodeId("11111111-1111-1111-1111-111111111111"));
        when(virtualNodes.get(0).getNodeType()).thenReturn(VirtualNode.NodeType.Vrouter);

        //getSourceVirtualRouterOfFlow()
        when(flow.getMatchItem()).thenReturn(matchItem);//matchItem
        when(matchItem.get(0).getMatchItemName()).thenReturn(new MatchItemName("src-ip"));
        when(matchItem.get(0).getMatchItemValue()).thenReturn(matchItemValue);//matchItemValue
        when(matchItemValue.getStringValue()).thenReturn(new String("src-ip"));
        when(nodes.get(0).getNodeType()).thenReturn(new NodeType("l2-group"));
        when(nodes.get(0).getProperty()).thenReturn(properties2);
        when(properties2.get(0).getPropertyName()).thenReturn(new PropertyName("ip-prefix"));
        when(properties2.get(0).getPropertyValues()).thenReturn(propertyValues);
        when(propertyValues.getStringValue()).thenReturn(stringValue);
        when(stringValue.get(0).getValue()).thenReturn(new String("src-ip"));
        when(nodes.get(0).getNodeId()).thenReturn(nodeId);
        when(nodeId.getValue()).thenReturn(new String("11111111-1111-1111-1111-111111111111"));
        when(intentVnMappingResults.get(0).getIntentId()).thenReturn(new IntentId("11111111-1111-1111-1111-111111111111"));
        when(intentVnMappingResults.get(0).getVirtualResource()).thenReturn(virtualResources);
        when(virtualResources.get(0).getVirtualResourceType()).thenReturn(VirtualResource.VirtualResourceType.Vport);
        when(virtualResources.get(0).getParentVirtualResourceEntityId()).thenReturn(virtualResourceEntityId);
        when(virtualResourceEntityId.getValue()).thenReturn(new String("11111111-1111-1111-1111-111111111111"));
        when(virtualNodes.get(0).getNodeId()).thenReturn(new VirtualNodeId("11111111-1111-1111-1111-111111111111"));
        when(virtualNodes.get(0).getNodeType()).thenReturn(VirtualNode.NodeType.Vrouter);

        //getDestinationVirtualRouterOfFlow()
        when(flow.getMatchItem()).thenReturn(matchItem);//matchItem
        when(matchItem.get(0).getMatchItemName()).thenReturn(new MatchItemName("dst-ip"));
        when(matchItem.get(0).getMatchItemValue()).thenReturn(matchItemValue);//matchItemValue
        when(matchItemValue.getStringValue()).thenReturn(new String("dst-ip"));
        when(nodes.get(0).getNodeType()).thenReturn(new NodeType("l2-group"));
        when(nodes.get(0).getProperty()).thenReturn(properties2);
        when(properties2.get(0).getPropertyName()).thenReturn(new PropertyName("ip-prefix"));
        when(properties2.get(0).getPropertyValues()).thenReturn(propertyValues);
        when(propertyValues.getStringValue()).thenReturn(stringValue);
        when(stringValue.get(0).getValue()).thenReturn(new String("src-ip"));
        when(nodes.get(0).getNodeId()).thenReturn(nodeId);
        when(nodeId.getValue()).thenReturn(new String("11111111-1111-1111-1111-111111111111"));
        when(intentVnMappingResults.get(0).getIntentId()).thenReturn(new IntentId("11111111-1111-1111-1111-111111111111"));
        when(intentVnMappingResults.get(0).getVirtualResource()).thenReturn(virtualResources);
        when(virtualResources.get(0).getVirtualResourceType()).thenReturn(VirtualResource.VirtualResourceType.Vport);
        when(virtualResources.get(0).getParentVirtualResourceEntityId()).thenReturn(virtualResourceEntityId);
        when(virtualResourceEntityId.getValue()).thenReturn(new String("11111111-1111-1111-1111-111111111111"));
        when(virtualNodes.get(0).getNodeId()).thenReturn(new VirtualNodeId("11111111-1111-1111-1111-111111111111"));
        when(virtualNodes.get(0).getNodeType()).thenReturn(VirtualNode.NodeType.Vrouter);


        //actionResolverTest.resolveActions(user,operation,flow,virtualNetwork, userIntentVnMapping);*/


    }


}