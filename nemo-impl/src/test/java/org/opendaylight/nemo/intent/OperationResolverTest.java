/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.nemo.intent;
import  org.opendaylight.nemo.intent.OperationResolver;

import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.VirtualNetwork;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.links.VirtualLink;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.links.VirtualLinkBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.nodes.VirtualNode;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.paths.VirtualPath;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.paths.VirtualPathBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.VirtualPort;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.VirtualPortBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.intent.vn.mapping.results.UserIntentVnMapping;
import org.opendaylight.nemo.intent.computation.VNComputationUnit;
import org.opendaylight.nemo.intent.condition.ConditionManager;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.intent.vn.mapping.results.user.intent.vn.mapping.IntentVnMappingResult;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.intent.vn.mapping.results.user.intent.vn.mapping.IntentVnMappingResultBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.intent.vn.mapping.results.user.intent.vn.mapping.intent.vn.mapping.result.VirtualResource;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.intent.vn.mapping.results.user.intent.vn.mapping.intent.vn.mapping.result.VirtualResourceBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.ActionName;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.IntentId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.NodeId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.NodeType;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Connection;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Flow;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Node;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.operations.Operation;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.User;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.StringValue;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.condition.instance.ConditionSegment;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.operation.instance.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.Objects;


import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.hosts.PhysicalHost;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.links.VirtualLink;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.nodes.VirtualNode;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.intent.vn.mapping.results.user.intent.vn.mapping.IntentVnMappingResult;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.intent.vn.mapping.results.user.intent.vn.mapping.intent.vn.mapping.result.VirtualResource;
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
import org.opendaylight.yangtools.yang.binding.DataObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.Map;
import java.util.HashMap;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class OperationResolverTest {

    private DataBroker dataBroker;
    private OperationResolver operationResolver;
    
    private User user;
    private UserId userId;
    private Operation operation;
    private OperationId operationId;
    private VirtualNetwork virtualNetwork;
    private UserIntentVnMapping userIntentVnMapping;
    private DataObject dataObject;
    
    private VNComputationUnit vnComputationUnit;
    private Map<UserId, VNComputationUnit> vnComputationUnits;
    private ConditionManager conditionManager;
    
    private List<Operation> operations;
    private List<Operation> operationsApplyingToNode;
    private List<Operation> operationsApplyingToConnection;
    private List<Operation> operationsApplyingToFlow;

    private Connection connection;
	private List<Node> nodeList;
	private Node node;
	
    @org.junit.Before
    public void setUp() throws Exception {
    	
    	dataBroker=mock(DataBroker.class);
    	user=mock(User.class);
    	operation=mock(Operation.class);
    	operationId=mock(OperationId.class);
    	virtualNetwork=mock(VirtualNetwork.class);
    	userIntentVnMapping=mock(UserIntentVnMapping.class);
    	conditionManager=mock(ConditionManager.class);
    	
    	userId=mock(UserId.class);
    	vnComputationUnit=mock(VNComputationUnit.class);
    	vnComputationUnits=new HashMap<UserId, VNComputationUnit>();
    	vnComputationUnits.put(userId,vnComputationUnit);
    	
    	
    	operationResolver=new OperationResolver(dataBroker, conditionManager,
                vnComputationUnits);
    	
    	operations = new ArrayList<Operation>(1);
    	operations.add(operation);
    	
    	operationsApplyingToNode = new ArrayList<Operation>();
    	operationsApplyingToConnection = new ArrayList<Operation>();
    	operationsApplyingToFlow = new ArrayList<Operation>();
    	
    	connection = mock(Connection.class);
    	node = mock(Node.class);
    	nodeList = new ArrayList<Node>();
    	nodeList.add(node);
    	
    }
    
    @org.junit.Test
    public void resolveOperationTest() throws Exception {
    	
    	ObjectId objectId = mock(ObjectId.class);
    	when(operation.getTargetObject()).thenReturn(objectId);
    	
    	Objects objects = mock(Objects.class);
    	when(user.getObjects()).thenReturn(objects);
    	
    	List<Node> nodes = new ArrayList<Node>(1);
    	Node node = mock(Node.class);
    	nodes.add(node);
    	
    	when(objects.getNode()).thenReturn(nodes);
    	when(objectId.getValue()).thenReturn(new String("00001111-0000-0000-0000-000011112222"));
    	
    	NodeId nodeId = mock(NodeId.class);
    	
    	when(node.getNodeId()).thenReturn(nodeId);
    	when(operation.getOperationId()).thenReturn(operationId);
    	when(operationId.getValue()).thenReturn(new String("00001111-0000-0000-0000-000011112222"));
    	//when(IntentResolverUtils.getNode(nodes, nodeId)).thenReturn(node);

    	//when(IntentResolverUtils.getObject(any(Objects.class), any(ObjectId.class))).thenReturn(dataObject);
    	try
    	{
    		operationResolver.resolveOperation(user, operation, virtualNetwork, userIntentVnMapping);
    	}
    	catch(IntentResolutionException ex)
    	{
    		Assert.assertEquals(ex.getMessage(),"The target object of the operation " +
                    operation.getOperationId().getValue() + " does not exist.");
    		
    	}
    	
    	
    	
    
    }
    
    @org.junit.Test
    public void classifyOperationsTest() throws Exception {
        
    	ObjectId objectId = mock(ObjectId.class);
    	when(operation.getTargetObject()).thenReturn(objectId);
    	
    	Objects objects = mock(Objects.class);
    	when(user.getObjects()).thenReturn(objects);
    	
    	
    	
    	when(objects.getNode()).thenReturn(nodeList);
        when(objectId.getValue()).thenReturn(new String("00001111-0000-0000-0000-000011112222"));
        NodeId nodeId2 = new NodeId(objectId.getValue());
        //into getnode
        when(node.getNodeId()).thenReturn(nodeId2);
    	
    	
    	try
    	{
    		operationResolver.classifyOperations(user, operations, operationsApplyingToNode, operationsApplyingToConnection, operationsApplyingToFlow);
    	}
    	catch(IntentResolutionException ex)
    	{
    		Assert.assertEquals(ex.getMessage(),"The target object of the operation " +
                        operation.getOperationId().getValue() + " does not exist.");
    		
    	}
    	
    	
    }
}
    
    
    
    
    