/*
 * Copyright (c) 2016 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.intent;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import com.google.common.base.Optional;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.ReadWriteTransaction;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.nemo.intent.computation.PNComputationUnit;
import org.opendaylight.nemo.intent.computation.PNResourcesTracker;
import org.opendaylight.nemo.intent.computation.VNComputationUnit;
import org.opendaylight.nemo.intent.computation.VNMappingUnit;
import org.opendaylight.nemo.intent.condition.ConditionManager;
import org.opendaylight.nemo.user.vnspacemanager.structurestyle.updateintent.UpdateIntent;
import org.opendaylight.nemo.user.vnspacemanager.structurestyle.updateintent.UpdateNode;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.PhysicalNetwork;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.PhysicalPaths;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.paths.PhysicalPath;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.paths.PhysicalPathKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.VirtualNetworks;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.VirtualNetwork;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.VirtualNetworkBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.VirtualNetworkKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.arps.VirtualArp;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.links.VirtualLink;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.nodes.VirtualNode;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.paths.VirtualPath;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.routes.VirtualRoute;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.IntentVnMappingResults;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.VnPnMappingResults;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.intent.vn.mapping.results.UserIntentVnMapping;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.intent.vn.mapping.results.UserIntentVnMappingBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.intent.vn.mapping.results.UserIntentVnMappingKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.intent.vn.mapping.results.user.intent.vn.mapping.IntentVnMappingResult;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.vn.pn.mapping.results.UserVnPnMapping;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.vn.pn.mapping.results.UserVnPnMappingBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.vn.pn.mapping.results.UserVnPnMappingKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.vn.pn.mapping.results.user.vn.pn.mapping.VnPnMappingResult;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.NodeType;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.PhysicalPathId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.VirtualNetworkId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.Users;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Connection;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Flow;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Node;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.Objects;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.operations.Operation;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.User;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.UserKey;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import java.util.List;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.LinkedList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.*;
import java.util.List;

import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.PhysicalLinks;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.PhysicalNodes;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.google.common.util.concurrent.CheckedFuture;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.opendaylight.controller.md.sal.binding.api.ReadOnlyTransaction;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.Operations;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.Results;
import static org.junit.Assert.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.VirtualResourceInstance.VirtualResourceType;
import org.opendaylight.controller.md.sal.common.api.data.TransactionCommitFailedException;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.PhysicalResourceEntityId;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.PhysicalNodeId;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.support.membermodification.MemberMatcher;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.ObjectId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.PhysicalHosts;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.hosts.PhysicalHost;
import static org.mockito.Mockito.doNothing;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.NodeId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.node.instance.Property;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.property.values.StringValue;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.PropertyValues;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.PropertyName;
/**
 * Created by ldzd11 on 2015/12/24.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest(IntentResolverUtils.class)
public class IntentResolverTest {
    private IntentResolver intentResolver;
    private  DataBroker dataBroker;
    private UserId userId;
    private List<VnPnMappingResult> vnPnMappingResults;
    private VnPnMappingResult vnPnMappingResult;
    private  VirtualResourceType virtualResourceType;
    private PhysicalResourceEntityId physicalResourceEntityId;
    private Objects objects;
    private List<Node> nodeList;
    private Node node;
    private NodeType nodeType;
    private List<Connection> connections;
    private Connection connection;
    private List<Flow> flows;
    private Flow flow;
    private Operations operations;
    private  List<Operation> operationList;
    private Operation operation;
    private  ObjectId objectId;
    private  NodeId nodeId;
    private List<PhysicalHost> physicalHostList;
    private PhysicalHost physicalHost;
    private Property property;
    private List<Property> propertyList;
    private PropertyValues propertyValues;
    private StringValue stringValue;
    private List<StringValue> stringValueList;


    @Before
    public void setUp() throws Exception {
        dataBroker = mock(DataBroker.class);
        userId = mock(UserId.class);

        vnPnMappingResults =  new ArrayList<VnPnMappingResult>();
        vnPnMappingResult  = mock(VnPnMappingResult.class);
        vnPnMappingResults.add(vnPnMappingResult);

        virtualResourceType = VnPnMappingResult.VirtualResourceType.Vlink;
        physicalResourceEntityId  =mock(PhysicalResourceEntityId.class);
        objects = mock(Objects.class);
        nodeList = new ArrayList<Node>();
        node = mock(Node.class);
        nodeList.add(node);
        nodeType = mock(NodeType.class);
        connections = new ArrayList<Connection>();
        connection = mock(Connection.class);
        connections.add(connection);
        flows = new ArrayList<Flow>();
        flow = mock(Flow.class);
        flows.add(flow);
        operations = mock(Operations.class);
        operationList = new ArrayList<Operation>();
        operation = mock(Operation.class);
        operationList.add(operation);
        objectId = mock(ObjectId.class);
        nodeId = mock(NodeId.class);

        physicalHostList = new ArrayList<PhysicalHost>();
        physicalHost = mock(PhysicalHost.class);
        physicalHostList.add(physicalHost);
        property = mock(Property.class);
        propertyList = new ArrayList<Property>();
        propertyList.add(property);
        propertyValues = mock(PropertyValues.class);
        stringValueList = new ArrayList<StringValue>();
        stringValue  = mock(StringValue.class);
        stringValueList.add(stringValue);




        //into PNC to result
        ReadOnlyTransaction readOnlyTransaction = mock(ReadOnlyTransaction.class);
        when(dataBroker.newReadOnlyTransaction()).thenReturn(readOnlyTransaction);

        CheckedFuture physicalnodesFuture = mock(CheckedFuture.class);
        CheckedFuture physicallinksFuture = mock(CheckedFuture.class);
        when(readOnlyTransaction.read(any(LogicalDatastoreType.class), any(InstanceIdentifier.class))).thenReturn(physicalnodesFuture).thenReturn(physicallinksFuture);

        //////////////////first result/////////////////////
        PhysicalNodes physicalNodes = mock(PhysicalNodes.class);
        Optional<PhysicalNodes> result = Optional.of(physicalNodes);
        Assert.assertTrue(result.isPresent());
        when(physicalnodesFuture.get()).thenReturn(result);

        ///////////////////Second result///////////////////////
        PhysicalLinks physicalLinks = mock(PhysicalLinks.class);
        Optional<PhysicalLinks> result1 = Optional.of(physicalLinks);
        Assert.assertTrue(result1.isPresent());
        when(physicallinksFuture.get()).thenReturn(result1);

        intentResolver = new IntentResolver(dataBroker);
    }

    @Test
    public void testResolveIntent() throws Exception {
        PowerMockito.mockStatic(IntentResolverUtils.class);
        PowerMockito.doNothing().when(IntentResolverUtils.class);
        IntentResolverUtils.copyPhysicalNetworkConfigToOperational(dataBroker);

        Class<IntentResolver> class1 = IntentResolver.class;

        CheckedFuture userVnPnMappingIidFuture = mock(CheckedFuture.class);
        CheckedFuture userFuture = mock(CheckedFuture.class);
        CheckedFuture physicalHostsFuture = mock(CheckedFuture.class);

        ReadWriteTransaction readWriteTransaction = mock(ReadWriteTransaction.class);
        when(dataBroker.newReadWriteTransaction()).thenReturn(readWriteTransaction);
        ReadOnlyTransaction readOnlyTransaction = mock(ReadOnlyTransaction.class);
        when(dataBroker.newReadOnlyTransaction()).thenReturn(readOnlyTransaction);

        when(readWriteTransaction.read(any(LogicalDatastoreType.class), any(InstanceIdentifier.class))).thenReturn(userVnPnMappingIidFuture).thenReturn(userFuture);
        when(readOnlyTransaction.read(any(LogicalDatastoreType.class), any(InstanceIdentifier.class))).thenReturn(physicalHostsFuture);


        when(userId.getValue()).thenReturn(new String("00001111-0000-0000-0000-000011112222"));
        //////////////152  forvnComputationUnits.get(userId) into if null != vnComputationUnit
        Map<UserId, VNComputationUnit> vnComputationUnits = new HashMap<UserId, VNComputationUnit>();
        VNComputationUnit vnComputationUnit = mock(VNComputationUnit.class);
        vnComputationUnits.put(userId, vnComputationUnit);

        Field field1 = class1.getDeclaredField("vnComputationUnits");
        field1.setAccessible(true);
        field1.set(intentResolver, vnComputationUnits);//for 170
        //into null!=vncomputionUnit

        ////////////////////the result1////////////////////////
        UserVnPnMapping userVnPnMapping = mock(UserVnPnMapping.class);
        Optional<UserVnPnMapping> result1 = Optional.of(userVnPnMapping);
        Assert.assertTrue(result1.isPresent());
        when(userVnPnMappingIidFuture.get()).thenReturn(result1);

        //187 into result1.is present
        when(userVnPnMapping.getVnPnMappingResult()).thenReturn(vnPnMappingResults);
        when(vnPnMappingResult.getVirtualResourceType()).thenReturn(virtualResourceType);
        when(vnPnMappingResult.getPhysicalResourceEntityId()).thenReturn(physicalResourceEntityId);
        when(vnPnMappingResult.getPhysicalResourceEntityId().getValue()).thenReturn(new String("00001111-0000-0000-0000-000011112222"));

        //end of 212
        //////////////////the result2////////////////////////////////
        User user = mock(User.class);
        Optional<User> result2 = Optional.of(user);
        Assert.assertTrue(result2.isPresent());
        when(userFuture.get()).thenReturn(result2);

        ////////into null!-user.getObjects
        when(user.getObjects()).thenReturn(objects);
        when(user.getObjects().getNode()).thenReturn(nodeList);
        ////////////////////1/////////////////////////////////
        nodeType = new NodeType("host");
        when(node.getNodeType()).thenReturn(nodeType);
        ////////////////////into nodeMapper.resolverHosr for nodes
        NodeMapper nodeMapper = mock(NodeMapper.class);
        Field field2 = class1.getDeclaredField("nodeMapper");
        field2.setAccessible(true);
        field2.set(intentResolver, nodeMapper);//for 170
        doNothing().when(nodeMapper).resolveHost(any(User.class), any(Node.class), any(VirtualNetwork.class), any(UserIntentVnMapping.class));

        ///////////////////2//////////////////////////////
        nodeType = new NodeType("l2-group");
        when(node.getNodeType()).thenReturn(nodeType);
        ////////////////////into nodeMapper.resolverHosr for nodes
        doNothing().when(nodeMapper).resolveLayer2Group(any(User.class), any(Node.class), any(VirtualNetwork.class), any(UserIntentVnMapping.class));

        //////////////////////3///////////////////////////////
        nodeType = new NodeType("l3-group");
        when(node.getNodeType()).thenReturn(nodeType);
        ////////////////////into nodeMapper.resolverHosr for nodes
        doNothing().when(nodeMapper).resolveLayer3Group(any(User.class), any(Node.class), any(VirtualNetwork.class), any(UserIntentVnMapping.class));

        //////////////////////4///////////////////////////////
        nodeType = new NodeType("ext-group");
        when(node.getNodeType()).thenReturn(nodeType);
        ////////////////////into nodeMapper.resolverHosr for nodes
        when(node.getProperty()).thenReturn(propertyList);
        PropertyName propertyName = new PropertyName("ac-info-network");
        when(property.getPropertyName()).thenReturn(propertyName);
        when(property.getPropertyValues()).thenReturn(propertyValues);
        when(property.getPropertyValues().getStringValue()).thenReturn(stringValueList);
        when(property.getPropertyValues().getStringValue().get(0).getValue()).thenReturn(new String("layer3"));
        doNothing().when(nodeMapper).resolveExternalLayer3Group(any(User.class), any(Node.class), any(VirtualNetwork.class), any(UserIntentVnMapping.class));

        //////////////////////5///////////////////////////////
        nodeType = new NodeType("chain-group");
        when(node.getNodeType()).thenReturn(nodeType);
        ////////////////////into nodeMapper.resolverHosr for nodes
        doNothing().when(nodeMapper).resolveServiceChainGroup(any(User.class), any(Node.class), any(VirtualNetwork.class), any(UserIntentVnMapping.class));

        //////////////////////6///////////////////////////////
        nodeType = new NodeType("fw");
        when(node.getNodeType()).thenReturn(nodeType);
        ////////////////////into nodeMapper.resolverHosr for nodes
        doNothing().when(nodeMapper).resolveServiceFunction(any(User.class), any(Node.class), any(VirtualNetwork.class), any(UserIntentVnMapping.class));

        //////////////////////6///////////////////////////////
        nodeType = new NodeType("lb");
        when(node.getNodeType()).thenReturn(nodeType);
        ////////////////////into nodeMapper.resolverHosr for nodes
        doNothing().when(nodeMapper).resolveServiceFunction(any(User.class), any(Node.class), any(VirtualNetwork.class), any(UserIntentVnMapping.class));

        //////////////////////6///////////////////////////////
        nodeType = new NodeType("cache");
        when(node.getNodeType()).thenReturn(nodeType);
        ////////////////////into nodeMapper.resolverHosr for nodes
        doNothing().when(nodeMapper).resolveServiceFunction(any(User.class), any(Node.class), any(VirtualNetwork.class), any(UserIntentVnMapping.class));


        //////////////////////connection///////////////////////////
        when(user.getObjects().getConnection()).thenReturn(connections);
        ConnectionMapper connectionMapper = mock(ConnectionMapper.class);
        Field field4 = class1.getDeclaredField("connectionMapper");
        field4.setAccessible(true);
        field4.set(intentResolver, connectionMapper);//for 170
        doNothing().when(connectionMapper).resolveConnection(any(User.class), any(Connection.class), any(VirtualNetwork.class), any(UserIntentVnMapping.class));

        //////////////////////flow///////////////////////////
        when(user.getObjects().getFlow()).thenReturn(flows);
        //////////////////////operation///////////////////////////
        when(user.getOperations()).thenReturn(operations);
        when(user.getOperations().getOperation()).thenReturn(operationList);


        //into operationResolver.classifyOperations
        OperationResolver operationResolver =mock(OperationResolver.class);
        Field field3 = class1.getDeclaredField("operationResolver");
        field3.setAccessible(true);
        field3.set(intentResolver, operationResolver);
        doNothing().when(operationResolver).classifyOperations(any(User.class), any(List.class), any(List.class), any(List.class), any(List.class));
        doNothing().when(operationResolver).resolveOperation(any(User.class), any(Operation.class), any(VirtualNetwork.class), any(UserIntentVnMapping.class));

        //392vnMappingUnit
        VNMappingUnit vnMappingUnit = mock(VNMappingUnit.class);
        Field field5 = class1.getDeclaredField("vnMappingUnit");
        field5.setAccessible(true);
        field5.set(intentResolver, vnMappingUnit);//for 170
        doNothing().when(vnMappingUnit).virtualNetworkMapping(any(VirtualNetwork.class), any(UserVnPnMapping.class), any(List.class));

        intentResolver.resolveIntent(userId);
        
    }

    @Test
    public void testClose() throws Exception {
        Class<IntentResolver> class1 = IntentResolver.class;
        PNComputationUnit pnComputationUnit = mock(PNComputationUnit.class);

        VNComputationUnit vnComputationUnit  = mock(VNComputationUnit.class);
        Map<UserId, VNComputationUnit> vnComputationUnits = new HashMap<UserId, VNComputationUnit>();
        vnComputationUnits.put(userId,vnComputationUnit);

        PNResourcesTracker pnResourcesTracker = mock(PNResourcesTracker.class);
        VNMappingUnit vnMappingUnit = mock(VNMappingUnit.class);


        Field field1 = class1.getDeclaredField("pnComputationUnit");
        field1.setAccessible(true);
        field1.set(intentResolver, pnComputationUnit);

        Field field2 = class1.getDeclaredField("vnComputationUnits");
        field2.setAccessible(true);
        field2.set(intentResolver, vnComputationUnits);

        Field field3 = class1.getDeclaredField("pnResourcesTracker");
        field3.setAccessible(true);
        field3.set(intentResolver, pnResourcesTracker);

        Field field4 = class1.getDeclaredField("vnMappingUnit");
        field4.setAccessible(true);
        field4.set(intentResolver, vnMappingUnit);

        intentResolver.close();

    }
}