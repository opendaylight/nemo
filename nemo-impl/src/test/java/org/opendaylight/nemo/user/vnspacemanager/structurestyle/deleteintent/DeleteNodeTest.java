/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.user.vnspacemanager.structurestyle.deleteintent;
import org.opendaylight.nemo.user.vnspacemanager.structurestyle.*;
import org.opendaylight.nemo.user.vnspacemanager.structurestyle.deleteintent.DeleteNode;
import static org.mockito.Mockito.mock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
//import org.opendaylight.nemo.user.vnspacemanager.structurestyle.deletenode;

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


import org.opendaylight.nemo.user.vnspacemanager.languagestyle.NEMOConstants;

import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.connection.instance.EndNode;
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
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.operation.instance.Action;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.Operations;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.operations.OperationKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.OperationId;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.nemo.user.tenantmanager.TenantManage;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.FlowId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.Users;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.Objects;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Flow;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.FlowKey;

import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.UserKey;
import org.opendaylight.controller.md.sal.binding.api.WriteTransaction;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.opendaylight.controller.md.sal.common.api.data.TransactionCommitFailedException;
import com.google.common.util.concurrent.CheckedFuture;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.UUID;
import org.opendaylight.controller.md.sal.binding.api.ReadOnlyTransaction;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
public class DeleteNodeTest{
	private DataBroker dataBroker;
    private TenantManage tenantManage;
    private DeleteNode deletenode;
	private Node node;
	private Operation operation;
	
	private NodeId nodeId;
	private UserId userId;
	private OperationId operationId;
	private ObjectId objectId;
	
	private Connection connection;
	private ConnectionId connectionId;
	private List<EndNode> endNodeList;
	private EndNode endNode;
	
	private WriteTransaction writetransaction;
	
	private Map<NodeId, Node> nodeMap;
	private Map<NodeId, Node> nodeDataStore;
	private Map<ConnectionId, Connection> connectionMap;
	private Map<ConnectionId, Connection> connectionDataStore;
	private Map<String, String> userNameIdMap;
	private Map<OperationId, Operation> operationMap;
	private Map<OperationId, Operation> operationDataStore;
	
	@org.junit.Before
	public void setUp() throws Exception {
		dataBroker=mock(DataBroker.class);
		tenantManage=mock(TenantManage.class);
		
		writetransaction=mock(WriteTransaction.class);
		
		node = mock(Node.class);
		nodeId = mock(NodeId.class);
		objectId = mock(ObjectId.class);
		operation = mock(Operation.class);
		operationId = mock(OperationId.class);
		
		connection = mock(Connection.class);
		connectionId = mock(ConnectionId.class);
		endNode = mock(EndNode.class);
		endNodeList = new ArrayList<EndNode>();
		endNodeList.add(endNode);
		
		deletenode = new DeleteNode(dataBroker,tenantManage);
		
		nodeMap = new HashMap<NodeId, Node>();
		nodeMap.put(nodeId,node);
		
		nodeDataStore = new HashMap<NodeId, Node>();
		nodeDataStore.put(nodeId,node);
		
		connectionMap = new HashMap<ConnectionId, Connection>();
		connectionMap.put(connectionId,connection);
		
		connectionDataStore = new HashMap<ConnectionId, Connection>();
		connectionDataStore.put(connectionId,connection);
		
		userNameIdMap = new HashMap<String, String>();
		userNameIdMap.put(new String("00001111-0000-0000-0000-000011112222"),new String("00001111-0000-0000-0000-000011112222"));
		
		operationMap = new HashMap<OperationId, Operation>();
	    operationMap.put(operationId,operation);
	    
	    operationDataStore = new HashMap<OperationId, Operation>();
	    operationDataStore.put(operationId,operation);
	}
	
	@org.junit.Test
	public void  DeleteNodeHandlingTest() throws Exception{
		CheckedFuture connectiondefinitionFuture = mock(CheckedFuture.class);
	    ReadOnlyTransaction readOnlyTransaction = mock(ReadOnlyTransaction.class);
		when(dataBroker.newReadOnlyTransaction()).thenReturn(readOnlyTransaction);
		   
		//when(operation.getTargetObject()).thenReturn(objectId);
		//when(objectId.getValue()).thenReturn(new String("00001111-0000-0000-0000-000011112222"));
		when(nodeId.getValue()).thenReturn(new String("00001111-0000-0000-0000-000011112222"));
		when(operationId.getValue()).thenReturn(new String("00001111-0000-0000-0000-000011112222"));
		   
		when(tenantManage.getNode(userId)).thenReturn(nodeMap);
		when(tenantManage.getUserNameIdMap(userId)).thenReturn(userNameIdMap);
		
		when(tenantManage.getConnection(userId)).thenReturn(connectionMap);
		when(connection.getEndNode()).thenReturn(endNodeList);
		when(endNode.getNodeId()).thenReturn(nodeId);
		when(connection.getConnectionId()).thenReturn(connectionId);
		when(connectionId.getValue()).thenReturn(new String("00001111-0000-0000-0000-000011112222"));
		when(tenantManage.getName(userId,new String("00001111-0000-0000-0000-000011112222"))).thenReturn(new String("00001111-0000-0000-0000-000011112222"));
		
		Assert.assertEquals(deletenode.DeleNodeHandling(userId,nodeId),null);
		
		when(tenantManage.getNode(userId)).thenReturn(null);
		when(tenantManage.getNodeDataStore(userId)).thenReturn(nodeDataStore);
		Assert.assertEquals(deletenode.DeleNodeHandling(userId,nodeId),null);
		
		when(tenantManage.getConnection(userId)).thenReturn(null);
		when(tenantManage.getConnectionDataStore(userId)).thenReturn(connectionDataStore);
		Assert.assertEquals(deletenode.DeleNodeHandling(userId,nodeId),null);
		
		when(tenantManage.getConnectionDataStore(userId)).thenReturn(null);
		//when(tenantManage.getOperation(userId)).thenReturn(null);
		//when(tenantManage.getOperationDataStore(userId)).thenReturn(operationDataStore);
		when(tenantManage.getOperation(userId)).thenReturn(operationMap);
		when(operation.getTargetObject()).thenReturn(objectId);
		when(objectId.getValue()).thenReturn(new String("00001111-0000-0000-0000-000011112222"));
		when(operation.getOperationId()).thenReturn(operationId);
		when(operationId.getValue()).thenReturn(new String("00001111-0000-0000-0000-000011112222"));
		when(tenantManage.getUserNameIdMap(userId)).thenReturn(userNameIdMap);
		
		Assert.assertEquals(deletenode.DeleNodeHandling(userId,nodeId),null);
		
		when(tenantManage.getOperation(userId)).thenReturn(null);
		when(tenantManage.getOperationDataStore(userId)).thenReturn(operationDataStore);
		
		Assert.assertEquals(deletenode.DeleNodeHandling(userId,nodeId),null);
		
		when(tenantManage.getNodeDataStore(userId)).thenReturn(null);
		Assert.assertEquals(deletenode.DeleNodeHandling(userId,nodeId),"The node instance " +nodeId.getValue()+" is not exist.");
	}
}
