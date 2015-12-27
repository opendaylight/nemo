/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.intent;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.*;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.nemo.intent.NodeMapper;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Node;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.NodeType;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.connection.instance.EndNode;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.ConnectionId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.NodeId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.Objects;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.VirtualNetwork;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.intent.vn.mapping.results.UserIntentVnMapping;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.ConnectionType;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Connection;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.User;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.intent.vn.mapping.results.user.intent.vn.mapping.IntentVnMappingResult;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.IntentId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.VirtualNodes;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.nodes.VirtualNode;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.intent.vn.mapping.results.user.intent.vn.mapping.intent.vn.mapping.result.VirtualResource;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.VirtualNodeId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.VirtualResourceEntityId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.node.instance.Property;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.PropertyName;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.PropertyValues;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.property.values.StringValue;
import java.util.List;
import java.util.ArrayList;
/**
 * Created by wangjunfei on 2015/12/28.
 */
 public class ConnectionMapperTest extends ConnectionMapper{
    public ConnectionMapperTest(){
        super(mock(DataBroker.class),mock(NodeMapper.class));
    }
    private User user;
    private Connection connection;
    private VirtualNetwork virtualNetwork;
    private UserIntentVnMapping userIntentVnMapping;
    private DataBroker dataBroker;
    private NodeMapper nodeMapper;
    private ConnectionType connectionType;
    private ConnectionMapper connectionMapper;
    @Before
    public void setUp() throws Exception {
        user = mock(User.class);
        connection = mock(Connection.class);
        virtualNetwork = mock(VirtualNetwork.class);
        userIntentVnMapping = mock(UserIntentVnMapping.class);
        dataBroker = mock(DataBroker.class);
        nodeMapper = mock(NodeMapper.class);
        connectionMapper = new ConnectionMapper(dataBroker,nodeMapper);
    }
	 @Test
    public void testResolveConnection() throws Exception {
		
       //first branch connectionType unknown
		try{
            ConnectionType connectionTypetest1 = new ConnectionType("test");
            when(connection.getConnectionType()).thenReturn(connectionTypetest1);
            connectionMapper.resolveConnection(user,connection,virtualNetwork,userIntentVnMapping);
		}
		catch(IntentResolutionException excepted){
            Assert.assertEquals("Unknown connection type.",excepted.getMessage());
		}
		
		// branch connectionType p2p null == endNode1
		try{
            ConnectionType connectionTypetest2 = new ConnectionType("p2p");
            when(connection.getConnectionType()).thenReturn(connectionTypetest2);
            List<Node> nodes = new ArrayList<Node>(3);
			Node node1 = mock(Node.class);
			Node node2 = mock(Node.class);
            nodes.add(node1);
			nodes.add(node2);
            Objects objects = mock(Objects.class);
            when(user.getObjects()).thenReturn(objects);
            when(objects.getNode()).thenReturn(nodes);
            NodeId endnodeId1 = mock(NodeId.class);
			List<EndNode> endNodes = new ArrayList<EndNode>(3);
			EndNode endNode1 = mock(EndNode.class);
            endNodes.add(endNode1);
            when(connection.getEndNode()).thenReturn(endNodes);
            when(endNodes.get(0).getNodeId()).thenReturn(endnodeId1);
            NodeId nodeId = mock(NodeId.class);
            when(node1.getNodeId()).thenReturn(nodeId);
			when(node2.getNodeId()).thenReturn(nodeId);
			
            when(endnodeId1.getValue()).thenReturn("aaa");
            ConnectionId connectionId = mock(ConnectionId.class);
            when(connection.getConnectionId()).thenReturn(connectionId);
            when(connectionId.getValue()).thenReturn("1");
            connectionMapper.resolveConnection(user,connection,virtualNetwork,userIntentVnMapping);
        }
        catch(IntentResolutionException excepted){
            Assert.assertEquals("The end-node aaa of the connection 1 does not exist.",excepted.getMessage());
        }
		
		 // branch connectionType p2p null == endNode2
        try{
			ConnectionType connectionTypetest2 = new ConnectionType("p2p");
            when(connection.getConnectionType()).thenReturn(connectionTypetest2);
            List<Node> nodes = new ArrayList<Node>(3);
			Node node1 = mock(Node.class);
			Node node2 = mock(Node.class);
            nodes.add(node1);
			nodes.add(node2);
            Objects objects = mock(Objects.class);
            when(user.getObjects()).thenReturn(objects);
            when(objects.getNode()).thenReturn(nodes);
            NodeId endnodeId1 = mock(NodeId.class);
			List<EndNode> endNodes = new ArrayList<EndNode>(3);
            EndNode endNode1 = mock(EndNode.class);
			EndNode endNode2 = mock(EndNode.class);
            endNodes.add(endNode1);
			endNodes.add(endNode2);
            when(connection.getEndNode()).thenReturn(endNodes);
            when(endNodes.get(0).getNodeId()).thenReturn(endnodeId1);
            NodeId nodeId1 = endnodeId1;
            when(node1.getNodeId()).thenReturn(nodeId1);
			NodeId endnodeId2 = mock(NodeId.class);
            when(endNodes.get(1).getNodeId()).thenReturn(endnodeId2);
			NodeId nodeId2 = mock(NodeId.class);
            when(node2.getNodeId()).thenReturn(nodeId2);

            when(endnodeId2.getValue()).thenReturn("bbb");
            ConnectionId connectionId = mock(ConnectionId.class);
            when(connection.getConnectionId()).thenReturn(connectionId);
            when(connectionId.getValue()).thenReturn("1");
            connectionMapper.resolveConnection(user,connection,virtualNetwork,userIntentVnMapping);
        }
        catch(IntentResolutionException excepted){
            Assert.assertEquals("The end-node bbb of the connection 1 does not exist.",excepted.getMessage());
        }
		
		// branch endNode1.getNodeType().equals(hostNodeType)
        try{
            ConnectionType connectionTypetest2 = new ConnectionType("p2p");
            when(connection.getConnectionType()).thenReturn(connectionTypetest2);
            List<Node> nodes = new ArrayList<Node>(3);
			Node node1 = mock(Node.class);
			Node node2 = mock(Node.class);
            nodes.add(node1);
			nodes.add(node2);
            Objects objects = mock(Objects.class);
            when(user.getObjects()).thenReturn(objects);
            when(objects.getNode()).thenReturn(nodes);
            NodeId endnodeId1 = mock(NodeId.class);
			List<EndNode> endNodes = new ArrayList<EndNode>(3);
            EndNode endNode1 = mock(EndNode.class);
			EndNode endNode2 = mock(EndNode.class);
            endNodes.add(endNode1);
			endNodes.add(endNode2);
            when(connection.getEndNode()).thenReturn(endNodes);
            when(endNodes.get(0).getNodeId()).thenReturn(endnodeId1);
            NodeId nodeId1 = endnodeId1;
            when(node1.getNodeId()).thenReturn(nodeId1);
			NodeId endnodeId2 = mock(NodeId.class);
            when(endNodes.get(1).getNodeId()).thenReturn(endnodeId2);
			NodeId nodeId2 = endnodeId2;
            when(node2.getNodeId()).thenReturn(nodeId2);

            NodeType hostNodeTypetest = new NodeType("host");
            when(node1.getNodeType()).thenReturn(hostNodeTypetest);

            when(nodeId1.getValue()).thenReturn("aaa");
            ConnectionId connectionId = mock(ConnectionId.class);
            when(connection.getConnectionId()).thenReturn(connectionId);
            when(connectionId.getValue()).thenReturn("2");
            connectionMapper.resolveConnection(user,connection,virtualNetwork,userIntentVnMapping);
        }
        catch(IntentResolutionException excepted){
            Assert.assertEquals("The end-node aaa of the connection 2 can not be host type.",excepted.getMessage());
        }
		
		// branch endNode1.getNodeType().equals(serviceChainGroupNodeType)
        try{
            ConnectionType connectionTypetest2 = new ConnectionType("p2p");
            when(connection.getConnectionType()).thenReturn(connectionTypetest2);
            List<Node> nodes = new ArrayList<Node>(3);
			Node node1 = mock(Node.class);
			Node node2 = mock(Node.class);
            nodes.add(node1);
			nodes.add(node2);
            Objects objects = mock(Objects.class);
            when(user.getObjects()).thenReturn(objects);
            when(objects.getNode()).thenReturn(nodes);
            NodeId endnodeId1 = mock(NodeId.class);
			List<EndNode> endNodes = new ArrayList<EndNode>(3);
            EndNode endNode1 = mock(EndNode.class);
			EndNode endNode2 = mock(EndNode.class);
            endNodes.add(endNode1);
			endNodes.add(endNode2);
            when(connection.getEndNode()).thenReturn(endNodes);
            when(endNodes.get(0).getNodeId()).thenReturn(endnodeId1);
            NodeId nodeId1 = endnodeId1;
            when(node1.getNodeId()).thenReturn(nodeId1);
			NodeId endnodeId2 = mock(NodeId.class);
            when(endNodes.get(1).getNodeId()).thenReturn(endnodeId2);
			NodeId nodeId2 = endnodeId2;
            when(node2.getNodeId()).thenReturn(nodeId2);

            NodeType serviceChainGroupNodeTypetest = new NodeType("chain-group");
            when(node1.getNodeType()).thenReturn(serviceChainGroupNodeTypetest);

            connectionMapper.resolveConnection(user,connection,virtualNetwork,userIntentVnMapping);
        }
        catch(IntentResolutionException excepted){
            Assert.assertNull(excepted.getMessage());
        }
		
		// branch endNode1.getNodeType().equals(firewallNodeType)
        try{
            ConnectionType connectionTypetest2 = new ConnectionType("p2p");
            when(connection.getConnectionType()).thenReturn(connectionTypetest2);
            List<Node> nodes = new ArrayList<Node>(3);
			Node node1 = mock(Node.class);
			Node node2 = mock(Node.class);
            nodes.add(node1);
			nodes.add(node2);
            Objects objects = mock(Objects.class);
            when(user.getObjects()).thenReturn(objects);
            when(objects.getNode()).thenReturn(nodes);
            NodeId endnodeId1 = mock(NodeId.class);
			List<EndNode> endNodes = new ArrayList<EndNode>(3);
            EndNode endNode1 = mock(EndNode.class);
			EndNode endNode2 = mock(EndNode.class);
            endNodes.add(endNode1);
			endNodes.add(endNode2);
            when(connection.getEndNode()).thenReturn(endNodes);
            when(endNodes.get(0).getNodeId()).thenReturn(endnodeId1);
            NodeId nodeId1 = endnodeId1;
            when(node1.getNodeId()).thenReturn(nodeId1);
			NodeId endnodeId2 = mock(NodeId.class);
            when(endNodes.get(1).getNodeId()).thenReturn(endnodeId2);
			NodeId nodeId2 = endnodeId2;
            when(node2.getNodeId()).thenReturn(nodeId2);

            NodeType firewallNodeTypetest = new NodeType("fw");
            when(node1.getNodeType()).thenReturn(firewallNodeTypetest);

            when(nodeId1.getValue()).thenReturn("aaa");
            ConnectionId connectionId = mock(ConnectionId.class);
            when(connection.getConnectionId()).thenReturn(connectionId);
            when(connectionId.getValue()).thenReturn("2");
            connectionMapper.resolveConnection(user,connection,virtualNetwork,userIntentVnMapping);
        }
        catch(IntentResolutionException excepted){
            Assert.assertEquals("The end-node aaa of the connection 2 can not be fw type.",excepted.getMessage());
        }
		
		// branch endNode1.getNodeType().equals(loadbalancerNodeType)
        try{
            ConnectionType connectionTypetest2 = new ConnectionType("p2p");
            when(connection.getConnectionType()).thenReturn(connectionTypetest2);
            List<Node> nodes = new ArrayList<Node>(3);
			Node node1 = mock(Node.class);
			Node node2 = mock(Node.class);
            nodes.add(node1);
			nodes.add(node2);
            Objects objects = mock(Objects.class);
            when(user.getObjects()).thenReturn(objects);
            when(objects.getNode()).thenReturn(nodes);
            NodeId endnodeId1 = mock(NodeId.class);
			List<EndNode> endNodes = new ArrayList<EndNode>(3);
            EndNode endNode1 = mock(EndNode.class);
			EndNode endNode2 = mock(EndNode.class);
            endNodes.add(endNode1);
			endNodes.add(endNode2);
            when(connection.getEndNode()).thenReturn(endNodes);
            when(endNodes.get(0).getNodeId()).thenReturn(endnodeId1);
            NodeId nodeId1 = endnodeId1;
            when(node1.getNodeId()).thenReturn(nodeId1);
			NodeId endnodeId2 = mock(NodeId.class);
            when(endNodes.get(1).getNodeId()).thenReturn(endnodeId2);
			NodeId nodeId2 = endnodeId2;
            when(node2.getNodeId()).thenReturn(nodeId2);

            NodeType loadbalancerNodeTypetest = new NodeType("lb");
            when(node1.getNodeType()).thenReturn(loadbalancerNodeTypetest);

            when(nodeId1.getValue()).thenReturn("aaa");
            ConnectionId connectionId = mock(ConnectionId.class);
            when(connection.getConnectionId()).thenReturn(connectionId);
            when(connectionId.getValue()).thenReturn("2");
            connectionMapper.resolveConnection(user,connection,virtualNetwork,userIntentVnMapping);
        }
        catch(IntentResolutionException excepted){
            Assert.assertEquals("The end-node aaa of the connection 2 can not be lb type.",excepted.getMessage());
        }
		
		// branch endNode1.getNodeType().equals(cacheNodeType)
        try{
            ConnectionType connectionTypetest2 = new ConnectionType("p2p");
            when(connection.getConnectionType()).thenReturn(connectionTypetest2);
            List<Node> nodes = new ArrayList<Node>(3);
			Node node1 = mock(Node.class);
			Node node2 = mock(Node.class);
            nodes.add(node1);
			nodes.add(node2);
            Objects objects = mock(Objects.class);
            when(user.getObjects()).thenReturn(objects);
            when(objects.getNode()).thenReturn(nodes);
            NodeId endnodeId1 = mock(NodeId.class);
			List<EndNode> endNodes = new ArrayList<EndNode>(3);
            EndNode endNode1 = mock(EndNode.class);
			EndNode endNode2 = mock(EndNode.class);
            endNodes.add(endNode1);
			endNodes.add(endNode2);
            when(connection.getEndNode()).thenReturn(endNodes);
            when(endNodes.get(0).getNodeId()).thenReturn(endnodeId1);
            NodeId nodeId1 = endnodeId1;
            when(node1.getNodeId()).thenReturn(nodeId1);
			NodeId endnodeId2 = mock(NodeId.class);
            when(endNodes.get(1).getNodeId()).thenReturn(endnodeId2);
			NodeId nodeId2 = endnodeId2;
            when(node2.getNodeId()).thenReturn(nodeId2);

            NodeType cacheNodeTypetest = new NodeType("cache");
            when(node1.getNodeType()).thenReturn(cacheNodeTypetest);

            when(nodeId1.getValue()).thenReturn("aaa");
            ConnectionId connectionId = mock(ConnectionId.class);
            when(connection.getConnectionId()).thenReturn(connectionId);
            when(connectionId.getValue()).thenReturn("2");
            connectionMapper.resolveConnection(user,connection,virtualNetwork,userIntentVnMapping);
        }
        catch(IntentResolutionException excepted){
            Assert.assertEquals("The end-node aaa of the connection 2 can not be cache type.",excepted.getMessage());
        }
		
		// branch endNode1.getNodeType().equals(unknownNodeType)
        try{
            ConnectionType connectionTypetest2 = new ConnectionType("p2p");
            when(connection.getConnectionType()).thenReturn(connectionTypetest2);
            List<Node> nodes = new ArrayList<Node>(3);
			Node node1 = mock(Node.class);
			Node node2 = mock(Node.class);
            nodes.add(node1);
			nodes.add(node2);
            Objects objects = mock(Objects.class);
            when(user.getObjects()).thenReturn(objects);
            when(objects.getNode()).thenReturn(nodes);
            NodeId endnodeId1 = mock(NodeId.class);
			List<EndNode> endNodes = new ArrayList<EndNode>(3);
            EndNode endNode1 = mock(EndNode.class);
			EndNode endNode2 = mock(EndNode.class);
            endNodes.add(endNode1);
			endNodes.add(endNode2);
            when(connection.getEndNode()).thenReturn(endNodes);
            when(endNodes.get(0).getNodeId()).thenReturn(endnodeId1);
            NodeId nodeId1 = endnodeId1;
            when(node1.getNodeId()).thenReturn(nodeId1);
			NodeId endnodeId2 = mock(NodeId.class);
            when(endNodes.get(1).getNodeId()).thenReturn(endnodeId2);
			NodeId nodeId2 = endnodeId2;
            when(node2.getNodeId()).thenReturn(nodeId2);

            NodeType unknownNodeType = new NodeType("test");
            when(node1.getNodeType()).thenReturn(unknownNodeType);

            ConnectionId connectionId = mock(ConnectionId.class);
            when(connection.getConnectionId()).thenReturn(connectionId);
            when(connectionId.getValue()).thenReturn("2");
            connectionMapper.resolveConnection(user,connection,virtualNetwork,userIntentVnMapping);
        }
        catch(IntentResolutionException excepted){
            Assert.assertEquals("Unknown node type for the end-nodes of the connection 2.",excepted.getMessage());
        }
		
		//branch endNode1.getNodeType().equals(layer2GroupNodeType) endNode2.getNodeType().equals(hostNodeType)
        try{
            ConnectionType connectionTypetest2 = new ConnectionType("p2p");
            when(connection.getConnectionType()).thenReturn(connectionTypetest2);
            List<Node> nodes = new ArrayList<Node>(3);
			Node node1 = mock(Node.class);
			Node node2 = mock(Node.class);
            nodes.add(node1);
			nodes.add(node2);
            Objects objects = mock(Objects.class);
            when(user.getObjects()).thenReturn(objects);
            when(objects.getNode()).thenReturn(nodes);
            NodeId endnodeId1 = mock(NodeId.class);
			List<EndNode> endNodes = new ArrayList<EndNode>(3);
            EndNode endNode1 = mock(EndNode.class);
			EndNode endNode2 = mock(EndNode.class);
            endNodes.add(endNode1);
			endNodes.add(endNode2);
            when(connection.getEndNode()).thenReturn(endNodes);
            when(endNodes.get(0).getNodeId()).thenReturn(endnodeId1);
            NodeId nodeId1 = endnodeId1;
            when(node1.getNodeId()).thenReturn(nodeId1);
			NodeId endnodeId2 = mock(NodeId.class);
            when(endNodes.get(1).getNodeId()).thenReturn(endnodeId2);
			NodeId nodeId2 = endnodeId2;
            when(node2.getNodeId()).thenReturn(nodeId2);
            
            NodeType layer2GroupNodeTypetest = new NodeType("l2-group");
			NodeType hostNodeTypetest = new NodeType("host");
            when(node1.getNodeType()).thenReturn(layer2GroupNodeTypetest);
            when(node2.getNodeType()).thenReturn(hostNodeTypetest);

            when(nodeId2.getValue()).thenReturn("bbb");
            ConnectionId connectionId = mock(ConnectionId.class);
            when(connection.getConnectionId()).thenReturn(connectionId);
            when(connectionId.getValue()).thenReturn("2G");
            connectionMapper.resolveConnection(user,connection,virtualNetwork,userIntentVnMapping);
        }
        catch(IntentResolutionException excepted){
            Assert.assertEquals("The end-node bbb of the connection 2G can not be host type.",excepted.getMessage());
        }
		
		//branch endNode1.getNodeType().equals(layer2GroupNodeType) endNode2.getNodeType().equals(serviceChainGroupNodeType)
        try{
            ConnectionType connectionTypetest2 = new ConnectionType("p2p");
            when(connection.getConnectionType()).thenReturn(connectionTypetest2);
            List<Node> nodes = new ArrayList<Node>(3);
			Node node1 = mock(Node.class);
			Node node2 = mock(Node.class);
            nodes.add(node1);
			nodes.add(node2);
            Objects objects = mock(Objects.class);
            when(user.getObjects()).thenReturn(objects);
            when(objects.getNode()).thenReturn(nodes);
            NodeId endnodeId1 = mock(NodeId.class);
			List<EndNode> endNodes = new ArrayList<EndNode>(3);
            EndNode endNode1 = mock(EndNode.class);
			EndNode endNode2 = mock(EndNode.class);
            endNodes.add(endNode1);
			endNodes.add(endNode2);
            when(connection.getEndNode()).thenReturn(endNodes);
            when(endNodes.get(0).getNodeId()).thenReturn(endnodeId1);
            NodeId nodeId1 = endnodeId1;
            when(node1.getNodeId()).thenReturn(nodeId1);
			NodeId endnodeId2 = mock(NodeId.class);
            when(endNodes.get(1).getNodeId()).thenReturn(endnodeId2);
			NodeId nodeId2 = endnodeId2;
            when(node2.getNodeId()).thenReturn(nodeId2);
            
            NodeType layer2GroupNodeTypetest = new NodeType("l2-group");
			NodeType serviceChainGroupNodeTypetest = new NodeType("chain-group");
            when(node1.getNodeType()).thenReturn(layer2GroupNodeTypetest);
            when(node2.getNodeType()).thenReturn(serviceChainGroupNodeTypetest);

            connectionMapper.resolveConnection(user,connection,virtualNetwork,userIntentVnMapping);
        }
        catch(IntentResolutionException excepted){
            Assert.assertNull(excepted.getMessage());
        }
		
		//branch endNode1.getNodeType().equals(layer2GroupNodeType) endNode2.getNodeType().equals(firewallNodeType)
        try{
            ConnectionType connectionTypetest2 = new ConnectionType("p2p");
            when(connection.getConnectionType()).thenReturn(connectionTypetest2);
            List<Node> nodes = new ArrayList<Node>(3);
			Node node1 = mock(Node.class);
			Node node2 = mock(Node.class);
            nodes.add(node1);
			nodes.add(node2);
            Objects objects = mock(Objects.class);
            when(user.getObjects()).thenReturn(objects);
            when(objects.getNode()).thenReturn(nodes);
            NodeId endnodeId1 = mock(NodeId.class);
			List<EndNode> endNodes = new ArrayList<EndNode>(3);
            EndNode endNode1 = mock(EndNode.class);
			EndNode endNode2 = mock(EndNode.class);
            endNodes.add(endNode1);
			endNodes.add(endNode2);
            when(connection.getEndNode()).thenReturn(endNodes);
            when(endNodes.get(0).getNodeId()).thenReturn(endnodeId1);
            NodeId nodeId1 = endnodeId1;
            when(node1.getNodeId()).thenReturn(nodeId1);
			NodeId endnodeId2 = mock(NodeId.class);
            when(endNodes.get(1).getNodeId()).thenReturn(endnodeId2);
			NodeId nodeId2 = endnodeId2;
            when(node2.getNodeId()).thenReturn(nodeId2);
            
            NodeType layer2GroupNodeTypetest = new NodeType("l2-group");
			NodeType firewallNodeTypetest = new NodeType("fw");
            when(node1.getNodeType()).thenReturn(layer2GroupNodeTypetest);
            when(node2.getNodeType()).thenReturn(firewallNodeTypetest);

            when(nodeId2.getValue()).thenReturn("bbb");
            ConnectionId connectionId = mock(ConnectionId.class);
            when(connection.getConnectionId()).thenReturn(connectionId);
            when(connectionId.getValue()).thenReturn("2G");
            connectionMapper.resolveConnection(user,connection,virtualNetwork,userIntentVnMapping);
        }
        catch(IntentResolutionException excepted){
            Assert.assertEquals("The end-node bbb of the connection 2G can not be fw type.",excepted.getMessage());
        }
		
		//branch endNode1.getNodeType().equals(layer2GroupNodeType) endNode2.getNodeType().equals(loadbalancerNodeType)
        try{
            ConnectionType connectionTypetest2 = new ConnectionType("p2p");
            when(connection.getConnectionType()).thenReturn(connectionTypetest2);
            List<Node> nodes = new ArrayList<Node>(3);
			Node node1 = mock(Node.class);
			Node node2 = mock(Node.class);
            nodes.add(node1);
			nodes.add(node2);
            Objects objects = mock(Objects.class);
            when(user.getObjects()).thenReturn(objects);
            when(objects.getNode()).thenReturn(nodes);
            NodeId endnodeId1 = mock(NodeId.class);
			List<EndNode> endNodes = new ArrayList<EndNode>(3);
            EndNode endNode1 = mock(EndNode.class);
			EndNode endNode2 = mock(EndNode.class);
            endNodes.add(endNode1);
			endNodes.add(endNode2);
            when(connection.getEndNode()).thenReturn(endNodes);
            when(endNodes.get(0).getNodeId()).thenReturn(endnodeId1);
            NodeId nodeId1 = endnodeId1;
            when(node1.getNodeId()).thenReturn(nodeId1);
			NodeId endnodeId2 = mock(NodeId.class);
            when(endNodes.get(1).getNodeId()).thenReturn(endnodeId2);
			NodeId nodeId2 = endnodeId2;
            when(node2.getNodeId()).thenReturn(nodeId2);
            
            NodeType layer2GroupNodeTypetest = new NodeType("l2-group");
			NodeType loadbalancerNodeTypetest = new NodeType("lb");
            when(node1.getNodeType()).thenReturn(layer2GroupNodeTypetest);
            when(node2.getNodeType()).thenReturn(loadbalancerNodeTypetest);

            when(nodeId2.getValue()).thenReturn("bbb");
            ConnectionId connectionId = mock(ConnectionId.class);
            when(connection.getConnectionId()).thenReturn(connectionId);
            when(connectionId.getValue()).thenReturn("2G");
            connectionMapper.resolveConnection(user,connection,virtualNetwork,userIntentVnMapping);
        }
        catch(IntentResolutionException excepted){
            Assert.assertEquals("The end-node bbb of the connection 2G can not be lb type.",excepted.getMessage());
        }
		
		//branch endNode1.getNodeType().equals(layer2GroupNodeType) endNode2.getNodeType().equals(cacheNodeType)
        try{
            ConnectionType connectionTypetest2 = new ConnectionType("p2p");
            when(connection.getConnectionType()).thenReturn(connectionTypetest2);
            List<Node> nodes = new ArrayList<Node>(3);
			Node node1 = mock(Node.class);
			Node node2 = mock(Node.class);
            nodes.add(node1);
			nodes.add(node2);
            Objects objects = mock(Objects.class);
            when(user.getObjects()).thenReturn(objects);
            when(objects.getNode()).thenReturn(nodes);
            NodeId endnodeId1 = mock(NodeId.class);
			List<EndNode> endNodes = new ArrayList<EndNode>(3);
            EndNode endNode1 = mock(EndNode.class);
			EndNode endNode2 = mock(EndNode.class);
            endNodes.add(endNode1);
			endNodes.add(endNode2);
            when(connection.getEndNode()).thenReturn(endNodes);
            when(endNodes.get(0).getNodeId()).thenReturn(endnodeId1);
            NodeId nodeId1 = endnodeId1;
            when(node1.getNodeId()).thenReturn(nodeId1);
			NodeId endnodeId2 = mock(NodeId.class);
            when(endNodes.get(1).getNodeId()).thenReturn(endnodeId2);
			NodeId nodeId2 = endnodeId2;
            when(node2.getNodeId()).thenReturn(nodeId2);
            
            NodeType layer2GroupNodeTypetest = new NodeType("l2-group");
			NodeType cacheNodeTypetest = new NodeType("cache");
            when(node1.getNodeType()).thenReturn(layer2GroupNodeTypetest);
            when(node2.getNodeType()).thenReturn(cacheNodeTypetest);

            when(nodeId2.getValue()).thenReturn("bbb");
            ConnectionId connectionId = mock(ConnectionId.class);
            when(connection.getConnectionId()).thenReturn(connectionId);
            when(connectionId.getValue()).thenReturn("2G");
            connectionMapper.resolveConnection(user,connection,virtualNetwork,userIntentVnMapping);
        }
        catch(IntentResolutionException excepted){
            Assert.assertEquals("The end-node bbb of the connection 2G can not be cache type.",excepted.getMessage());
        }
		
		//branch endNode1.getNodeType().equals(layer2GroupNodeType)  endNode2.getNodeType().equals(layer2GroupNodeType)
        //intentVnMappingResult1 = null
        try{
            ConnectionType connectionTypetest2 = new ConnectionType("p2p");
            when(connection.getConnectionType()).thenReturn(connectionTypetest2);
            List<Node> nodes = new ArrayList<Node>(3);
			Node node1 = mock(Node.class);
			Node node2 = mock(Node.class);
            nodes.add(node1);
			nodes.add(node2);
            Objects objects = mock(Objects.class);
            when(user.getObjects()).thenReturn(objects);
            when(objects.getNode()).thenReturn(nodes);
            NodeId endnodeId1 = mock(NodeId.class);
			List<EndNode> endNodes = new ArrayList<EndNode>(3);
            EndNode endNode1 = mock(EndNode.class);
			EndNode endNode2 = mock(EndNode.class);
            endNodes.add(endNode1);
			endNodes.add(endNode2);
            when(connection.getEndNode()).thenReturn(endNodes);
            when(endNodes.get(0).getNodeId()).thenReturn(endnodeId1);
            NodeId nodeId1 = endnodeId1;
            when(node1.getNodeId()).thenReturn(nodeId1);
			NodeId endnodeId2 = mock(NodeId.class);
            when(endNodes.get(1).getNodeId()).thenReturn(endnodeId2);
			NodeId nodeId2 = endnodeId2;
            when(node2.getNodeId()).thenReturn(nodeId2);

            NodeType layer2GroupNodeTypetest = new NodeType("l2-group");
            when(node1.getNodeType()).thenReturn(layer2GroupNodeTypetest);
            when(node2.getNodeType()).thenReturn(layer2GroupNodeTypetest);

            List<IntentVnMappingResult> intentVnMappingResults = new ArrayList<IntentVnMappingResult>(3);
            IntentVnMappingResult intentVnMappingResult1 = mock(IntentVnMappingResult.class);
            IntentVnMappingResult intentVnMappingResult2 = mock(IntentVnMappingResult.class);
            intentVnMappingResults.add(intentVnMappingResult1);
            intentVnMappingResults.add(intentVnMappingResult2);
            when(userIntentVnMapping.getIntentVnMappingResult()).thenReturn(intentVnMappingResults);
            when(node1.getNodeId()).thenReturn(nodeId1);
            when(nodeId1.getValue()).thenReturn("11111111-1111-1111-1111-111111111111");
            IntentId intentId1 = new IntentId("00000000-0000-0000-0000-000000000000");
			IntentId intentId2 = new IntentId("00000000-0000-0000-0000-000000000000");
            when(intentVnMappingResult1.getIntentId()).thenReturn(intentId1);
            when(intentVnMappingResult2.getIntentId()).thenReturn(intentId2);
			
            connectionMapper.resolveConnection(user,connection,virtualNetwork,userIntentVnMapping);
        }
        catch(IntentResolutionException excepted){
            Assert.assertEquals("Can not get the intent-vn mapping result for the node 11111111-1111-1111-1111-111111111111.",excepted.getMessage());
        }
		
		//branch endNode1.getNodeType().equals(layer2GroupNodeType)  endNode2.getNodeType().equals(layer2GroupNodeType)
        //intentVnMappingResult2 = null
        try{
            ConnectionType connectionTypetest2 = new ConnectionType("p2p");
            when(connection.getConnectionType()).thenReturn(connectionTypetest2);
            List<Node> nodes = new ArrayList<Node>(3);
			Node node1 = mock(Node.class);
			Node node2 = mock(Node.class);
            nodes.add(node1);
			nodes.add(node2);
            Objects objects = mock(Objects.class);
            when(user.getObjects()).thenReturn(objects);
            when(objects.getNode()).thenReturn(nodes);
            NodeId endnodeId1 = mock(NodeId.class);
			List<EndNode> endNodes = new ArrayList<EndNode>(3);
            EndNode endNode1 = mock(EndNode.class);
			EndNode endNode2 = mock(EndNode.class);
            endNodes.add(endNode1);
			endNodes.add(endNode2);
            when(connection.getEndNode()).thenReturn(endNodes);
            when(endNodes.get(0).getNodeId()).thenReturn(endnodeId1);
            NodeId nodeId1 = endnodeId1;
            when(node1.getNodeId()).thenReturn(nodeId1);
			NodeId endnodeId2 = mock(NodeId.class);
            when(endNodes.get(1).getNodeId()).thenReturn(endnodeId2);
			NodeId nodeId2 = endnodeId2;
            when(node2.getNodeId()).thenReturn(nodeId2);
			
            NodeType layer2GroupNodeTypetest = new NodeType("l2-group");
            when(node1.getNodeType()).thenReturn(layer2GroupNodeTypetest);
            when(node2.getNodeType()).thenReturn(layer2GroupNodeTypetest);

            List<IntentVnMappingResult> intentVnMappingResults = new ArrayList<IntentVnMappingResult>(3);
            IntentVnMappingResult intentVnMappingResult1 = mock(IntentVnMappingResult.class);
            IntentVnMappingResult intentVnMappingResult2 = mock(IntentVnMappingResult.class);
            intentVnMappingResults.add(intentVnMappingResult1);
            intentVnMappingResults.add(intentVnMappingResult2);
            when(userIntentVnMapping.getIntentVnMappingResult()).thenReturn(intentVnMappingResults);
            when(node1.getNodeId()).thenReturn(nodeId1);
            when(nodeId1.getValue()).thenReturn("11111111-1111-1111-1111-111111111111");
            IntentId intentId1 = new IntentId("11111111-1111-1111-1111-111111111111");
			IntentId intentId2 = new IntentId("00000000-0000-0000-0000-000000000000");
            when(intentVnMappingResult1.getIntentId()).thenReturn(intentId1);
			when(node2.getNodeId()).thenReturn(nodeId2);
            when(nodeId2.getValue()).thenReturn("22222222-2222-2222-2222-222222222222");
            when(intentVnMappingResult2.getIntentId()).thenReturn(intentId2);

            connectionMapper.resolveConnection(user,connection,virtualNetwork,userIntentVnMapping);
        }
        catch(IntentResolutionException excepted){
            Assert.assertEquals("Can not get the intent-vn mapping result for the node 22222222-2222-2222-2222-222222222222.",excepted.getMessage());
        }
		
		//branch endNode1.getNodeType().equals(layer2GroupNodeType)  endNode2.getNodeType().equals(layer2GroupNodeType)
        //virtualRouter1 = null
        try{
            ConnectionType connectionTypetest2 = new ConnectionType("p2p");
            when(connection.getConnectionType()).thenReturn(connectionTypetest2);
            List<Node> nodes = new ArrayList<Node>(3);
			Node node1 = mock(Node.class);
			Node node2 = mock(Node.class);
            nodes.add(node1);
			nodes.add(node2);
            Objects objects = mock(Objects.class);
            when(user.getObjects()).thenReturn(objects);
            when(objects.getNode()).thenReturn(nodes);
            NodeId endnodeId1 = mock(NodeId.class);
			List<EndNode> endNodes = new ArrayList<EndNode>(3);
            EndNode endNode1 = mock(EndNode.class);
			EndNode endNode2 = mock(EndNode.class);
            endNodes.add(endNode1);
			endNodes.add(endNode2);
            when(connection.getEndNode()).thenReturn(endNodes);
            when(endNodes.get(0).getNodeId()).thenReturn(endnodeId1);
            NodeId nodeId1 = endnodeId1;
            when(node1.getNodeId()).thenReturn(nodeId1);
			NodeId endnodeId2 = mock(NodeId.class);
            when(endNodes.get(1).getNodeId()).thenReturn(endnodeId2);
			NodeId nodeId2 = endnodeId2;
            when(node2.getNodeId()).thenReturn(nodeId2);
			
            NodeType layer2GroupNodeTypetest = new NodeType("l2-group");
            when(node1.getNodeType()).thenReturn(layer2GroupNodeTypetest);
            when(node2.getNodeType()).thenReturn(layer2GroupNodeTypetest);

            List<IntentVnMappingResult> intentVnMappingResults = new ArrayList<IntentVnMappingResult>(3);
            IntentVnMappingResult intentVnMappingResult1 = mock(IntentVnMappingResult.class);
            IntentVnMappingResult intentVnMappingResult2 = mock(IntentVnMappingResult.class);
            intentVnMappingResults.add(intentVnMappingResult1);
            intentVnMappingResults.add(intentVnMappingResult2);
            when(userIntentVnMapping.getIntentVnMappingResult()).thenReturn(intentVnMappingResults);
            when(node1.getNodeId()).thenReturn(nodeId1);
            when(nodeId1.getValue()).thenReturn("11111111-1111-1111-1111-111111111111");
            IntentId intentId1 = new IntentId("11111111-1111-1111-1111-111111111111");
			IntentId intentId2 = new IntentId("22222222-2222-2222-2222-222222222222");
            when(intentVnMappingResult1.getIntentId()).thenReturn(intentId1);
			when(node2.getNodeId()).thenReturn(nodeId2);
            when(nodeId2.getValue()).thenReturn("22222222-2222-2222-2222-222222222222");
            when(intentVnMappingResult2.getIntentId()).thenReturn(intentId2);

            List<VirtualNode> virtualNodestest = new ArrayList<VirtualNode>(3);
            VirtualNode virtualNode1 = mock(VirtualNode.class);
			VirtualNode virtualNode2 = mock(VirtualNode.class);
            virtualNodestest.add(virtualNode1);
			virtualNodestest.add(virtualNode2);
            VirtualNodes virtualNodes = mock(VirtualNodes.class);
            when(virtualNetwork.getVirtualNodes()).thenReturn(virtualNodes);
            when(virtualNodes.getVirtualNode()).thenReturn(virtualNodestest);
			
            List<VirtualResource> virtualResources = new ArrayList<VirtualResource>(3);
            VirtualResource virtualResource = mock(VirtualResource.class);
            virtualResources.add(virtualResource);
            when(intentVnMappingResult1.getVirtualResource()).thenReturn(virtualResources);
            VirtualResourceEntityId virtualResourceEntityId = mock(VirtualResourceEntityId.class);
            when(virtualResource.getVirtualResourceEntityId()).thenReturn(virtualResourceEntityId);
            when(virtualResourceEntityId.getValue()).thenReturn("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
            
			VirtualNodeId virtualNodeId1 = new VirtualNodeId("00000000-0000-0000-0000-000000000000");
			VirtualNodeId virtualNodeId2 = new VirtualNodeId("00000000-0000-0000-0000-000000000000");
            when(virtualNode1.getNodeId()).thenReturn(virtualNodeId1);
			when(virtualNode2.getNodeId()).thenReturn(virtualNodeId2);
			
            connectionMapper.resolveConnection(user,connection,virtualNetwork,userIntentVnMapping);
        }
        catch(IntentResolutionException excepted){
            Assert.assertEquals("Can not get the virtual node created for the node 11111111-1111-1111-1111-111111111111.",excepted.getMessage());
        }
		
		//branch endNode1.getNodeType().equals(layer2GroupNodeType)  endNode2.getNodeType().equals(layer3GroupNodeType)
        try{
            ConnectionType connectionTypetest2 = new ConnectionType("p2p");
            when(connection.getConnectionType()).thenReturn(connectionTypetest2);
            List<Node> nodes = new ArrayList<Node>(3);
			Node node1 = mock(Node.class);
			Node node2 = mock(Node.class);
            nodes.add(node1);
			nodes.add(node2);
            Objects objects = mock(Objects.class);
            when(user.getObjects()).thenReturn(objects);
            when(objects.getNode()).thenReturn(nodes);
            NodeId endnodeId1 = mock(NodeId.class);
			List<EndNode> endNodes = new ArrayList<EndNode>(3);
            EndNode endNode1 = mock(EndNode.class);
			EndNode endNode2 = mock(EndNode.class);
            endNodes.add(endNode1);
			endNodes.add(endNode2);
            when(connection.getEndNode()).thenReturn(endNodes);
            when(endNodes.get(0).getNodeId()).thenReturn(endnodeId1);
            NodeId nodeId1 = endnodeId1;
            when(node1.getNodeId()).thenReturn(nodeId1);
			NodeId endnodeId2 = mock(NodeId.class);
            when(endNodes.get(1).getNodeId()).thenReturn(endnodeId2);
			NodeId nodeId2 = endnodeId2;
            when(node2.getNodeId()).thenReturn(nodeId2);

            NodeType layer2GroupNodeTypetest = new NodeType("l2-group");
			NodeType layer3GroupNodeTypetest = new NodeType("l3-group");
            when(node1.getNodeType()).thenReturn(layer2GroupNodeTypetest);
            when(node2.getNodeType()).thenReturn(layer3GroupNodeTypetest);
			
            connectionMapper.resolveConnection(user,connection,virtualNetwork,userIntentVnMapping);
        }
        catch(IntentResolutionException excepted){
            Assert.assertNull(excepted.getMessage());
        }
		
		//branch endNode1.getNodeType().equals(layer2GroupNodeType)  endNode2.getNodeType().equals(externalGroupNodeType)
        //checkExternalLayer3Group
		//intentVnMappingResult1 = null
        try{
            ConnectionType connectionTypetest2 = new ConnectionType("p2p");
            when(connection.getConnectionType()).thenReturn(connectionTypetest2);
            List<Node> nodes = new ArrayList<Node>(3);
			Node node1 = mock(Node.class);
			Node node2 = mock(Node.class);
            nodes.add(node1);
			nodes.add(node2);
            Objects objects = mock(Objects.class);
            when(user.getObjects()).thenReturn(objects);
            when(objects.getNode()).thenReturn(nodes);
            NodeId endnodeId1 = mock(NodeId.class);
			List<EndNode> endNodes = new ArrayList<EndNode>(3);
            EndNode endNode1 = mock(EndNode.class);
			EndNode endNode2 = mock(EndNode.class);
            endNodes.add(endNode1);
			endNodes.add(endNode2);
            when(connection.getEndNode()).thenReturn(endNodes);
            when(endNodes.get(0).getNodeId()).thenReturn(endnodeId1);
            NodeId nodeId1 = endnodeId1;
            when(node1.getNodeId()).thenReturn(nodeId1);
			NodeId endnodeId2 = mock(NodeId.class);
            when(endNodes.get(1).getNodeId()).thenReturn(endnodeId2);
			NodeId nodeId2 = endnodeId2;
            when(node2.getNodeId()).thenReturn(nodeId2);

            NodeType layer2GroupNodeTypetest = new NodeType("l2-group");
			NodeType externalGroupNodeTypetest = new NodeType("ext-group");
            when(node1.getNodeType()).thenReturn(layer2GroupNodeTypetest);
            when(node2.getNodeType()).thenReturn(externalGroupNodeTypetest);

			Property property = mock(Property.class);
            List<Property> propertys = new ArrayList<Property>(3);
            propertys.add(property);
            when(node2.getProperty()).thenReturn(propertys);
            PropertyName propertyNametest = new PropertyName("ac-info-network");
            when(property.getPropertyName()).thenReturn(propertyNametest);
            PropertyValues propertyValues = mock(PropertyValues.class);
            when(property.getPropertyValues()).thenReturn(propertyValues);
            List<StringValue> stringValues = new ArrayList<StringValue>(3);
            StringValue stringValue = mock(StringValue.class);
            stringValues.add(stringValue);
            when(propertyValues.getStringValue()).thenReturn(stringValues);
            when(stringValues.get(0).getValue()).thenReturn("layer3");
			
            List<IntentVnMappingResult> intentVnMappingResults = new ArrayList<IntentVnMappingResult>(3);
            IntentVnMappingResult intentVnMappingResult1 = mock(IntentVnMappingResult.class);
            IntentVnMappingResult intentVnMappingResult2 = mock(IntentVnMappingResult.class);
            intentVnMappingResults.add(intentVnMappingResult1);
            intentVnMappingResults.add(intentVnMappingResult2);
            when(userIntentVnMapping.getIntentVnMappingResult()).thenReturn(intentVnMappingResults);
            when(node1.getNodeId()).thenReturn(nodeId1);
            when(nodeId1.getValue()).thenReturn("11111111-1111-1111-1111-111111111111");
            IntentId intentId1 = new IntentId("00000000-0000-0000-0000-000000000000");
			IntentId intentId2 = new IntentId("00000000-0000-0000-0000-000000000000");
            when(intentVnMappingResult1.getIntentId()).thenReturn(intentId1);
            when(intentVnMappingResult2.getIntentId()).thenReturn(intentId2);
			
            connectionMapper.resolveConnection(user,connection,virtualNetwork,userIntentVnMapping);
        }
        catch(IntentResolutionException excepted){
            Assert.assertEquals("Can not get the intent-vn mapping result for the node 11111111-1111-1111-1111-111111111111.",excepted.getMessage());
        }
		
		//branch endNode1.getNodeType().equals(layer2GroupNodeType)  endNode2.getNodeType().equals(externalGroupNodeType)
        //checkExternalLayer3Group
		//intentVnMappingResult2 = null
        try{
            ConnectionType connectionTypetest2 = new ConnectionType("p2p");
            when(connection.getConnectionType()).thenReturn(connectionTypetest2);
            List<Node> nodes = new ArrayList<Node>(3);
			Node node1 = mock(Node.class);
			Node node2 = mock(Node.class);
            nodes.add(node1);
			nodes.add(node2);
            Objects objects = mock(Objects.class);
            when(user.getObjects()).thenReturn(objects);
            when(objects.getNode()).thenReturn(nodes);
            NodeId endnodeId1 = mock(NodeId.class);
			List<EndNode> endNodes = new ArrayList<EndNode>(3);
            EndNode endNode1 = mock(EndNode.class);
			EndNode endNode2 = mock(EndNode.class);
            endNodes.add(endNode1);
			endNodes.add(endNode2);
            when(connection.getEndNode()).thenReturn(endNodes);
            when(endNodes.get(0).getNodeId()).thenReturn(endnodeId1);
            NodeId nodeId1 = endnodeId1;
            when(node1.getNodeId()).thenReturn(nodeId1);
			NodeId endnodeId2 = mock(NodeId.class);
            when(endNodes.get(1).getNodeId()).thenReturn(endnodeId2);
			NodeId nodeId2 = endnodeId2;
            when(node2.getNodeId()).thenReturn(nodeId2);
			
			NodeType layer2GroupNodeTypetest = new NodeType("l2-group");
			NodeType externalGroupNodeTypetest = new NodeType("ext-group");
            when(node1.getNodeType()).thenReturn(layer2GroupNodeTypetest);
            when(node2.getNodeType()).thenReturn(externalGroupNodeTypetest);
			
			Property property = mock(Property.class);
            List<Property> propertys = new ArrayList<Property>(3);
            propertys.add(property);
            when(node2.getProperty()).thenReturn(propertys);
            PropertyName propertyNametest = new PropertyName("ac-info-network");
            when(property.getPropertyName()).thenReturn(propertyNametest);
            PropertyValues propertyValues = mock(PropertyValues.class);
            when(property.getPropertyValues()).thenReturn(propertyValues);
            List<StringValue> stringValues = new ArrayList<StringValue>(3);
            StringValue stringValue = mock(StringValue.class);
            stringValues.add(stringValue);
            when(propertyValues.getStringValue()).thenReturn(stringValues);
            when(stringValues.get(0).getValue()).thenReturn("layer3");
			
            List<IntentVnMappingResult> intentVnMappingResults = new ArrayList<IntentVnMappingResult>(3);
            IntentVnMappingResult intentVnMappingResult1 = mock(IntentVnMappingResult.class);
            IntentVnMappingResult intentVnMappingResult2 = mock(IntentVnMappingResult.class);
            intentVnMappingResults.add(intentVnMappingResult1);
            intentVnMappingResults.add(intentVnMappingResult2);
            when(userIntentVnMapping.getIntentVnMappingResult()).thenReturn(intentVnMappingResults);
            when(node1.getNodeId()).thenReturn(nodeId1);
            when(nodeId1.getValue()).thenReturn("11111111-1111-1111-1111-111111111111");
            IntentId intentId1 = new IntentId("11111111-1111-1111-1111-111111111111");
			IntentId intentId2 = new IntentId("00000000-0000-0000-0000-000000000000");
            when(intentVnMappingResult1.getIntentId()).thenReturn(intentId1);
			when(node2.getNodeId()).thenReturn(nodeId2);
            when(nodeId2.getValue()).thenReturn("22222222-2222-2222-2222-222222222222");
            when(intentVnMappingResult2.getIntentId()).thenReturn(intentId2);

            connectionMapper.resolveConnection(user,connection,virtualNetwork,userIntentVnMapping);
        }
        catch(IntentResolutionException excepted){
            Assert.assertEquals("Can not get the intent-vn mapping result for the node 22222222-2222-2222-2222-222222222222.",excepted.getMessage());
        }
		
		//branch endNode1.getNodeType().equals(layer2GroupNodeType)  endNode2.getNodeType().equals(externalGroupNodeType)
        //checkExternalLayer3Group
		//virtualRouter1 = null
        try{
            ConnectionType connectionTypetest2 = new ConnectionType("p2p");
            when(connection.getConnectionType()).thenReturn(connectionTypetest2);
            List<Node> nodes = new ArrayList<Node>(3);
			Node node1 = mock(Node.class);
			Node node2 = mock(Node.class);
            nodes.add(node1);
			nodes.add(node2);
            Objects objects = mock(Objects.class);
            when(user.getObjects()).thenReturn(objects);
            when(objects.getNode()).thenReturn(nodes);
            NodeId endnodeId1 = mock(NodeId.class);
			List<EndNode> endNodes = new ArrayList<EndNode>(3);
            EndNode endNode1 = mock(EndNode.class);
			EndNode endNode2 = mock(EndNode.class);
            endNodes.add(endNode1);
			endNodes.add(endNode2);
            when(connection.getEndNode()).thenReturn(endNodes);
            when(endNodes.get(0).getNodeId()).thenReturn(endnodeId1);
            NodeId nodeId1 = endnodeId1;
            when(node1.getNodeId()).thenReturn(nodeId1);
			NodeId endnodeId2 = mock(NodeId.class);
            when(endNodes.get(1).getNodeId()).thenReturn(endnodeId2);
			NodeId nodeId2 = endnodeId2;
            when(node2.getNodeId()).thenReturn(nodeId2);
			
			NodeType layer2GroupNodeTypetest = new NodeType("l2-group");
			NodeType externalGroupNodeTypetest = new NodeType("ext-group");
            when(node1.getNodeType()).thenReturn(layer2GroupNodeTypetest);
            when(node2.getNodeType()).thenReturn(externalGroupNodeTypetest);

			Property property = mock(Property.class);
            List<Property> propertys = new ArrayList<Property>(3);
            propertys.add(property);
            when(node2.getProperty()).thenReturn(propertys);
            PropertyName propertyNametest = new PropertyName("ac-info-network");
            when(property.getPropertyName()).thenReturn(propertyNametest);
            PropertyValues propertyValues = mock(PropertyValues.class);
            when(property.getPropertyValues()).thenReturn(propertyValues);
            List<StringValue> stringValues = new ArrayList<StringValue>(3);
            StringValue stringValue = mock(StringValue.class);
            stringValues.add(stringValue);
            when(propertyValues.getStringValue()).thenReturn(stringValues);
            when(stringValues.get(0).getValue()).thenReturn("layer3");
			
            List<IntentVnMappingResult> intentVnMappingResults = new ArrayList<IntentVnMappingResult>(3);
            IntentVnMappingResult intentVnMappingResult1 = mock(IntentVnMappingResult.class);
            IntentVnMappingResult intentVnMappingResult2 = mock(IntentVnMappingResult.class);
            intentVnMappingResults.add(intentVnMappingResult1);
            intentVnMappingResults.add(intentVnMappingResult2);
            when(userIntentVnMapping.getIntentVnMappingResult()).thenReturn(intentVnMappingResults);
            when(node1.getNodeId()).thenReturn(nodeId1);
            when(nodeId1.getValue()).thenReturn("11111111-1111-1111-1111-111111111111");
            IntentId intentId1 = new IntentId("11111111-1111-1111-1111-111111111111");
			IntentId intentId2 = new IntentId("22222222-2222-2222-2222-222222222222");
            when(intentVnMappingResult1.getIntentId()).thenReturn(intentId1);
			when(node2.getNodeId()).thenReturn(nodeId2);
            when(nodeId2.getValue()).thenReturn("22222222-2222-2222-2222-222222222222");
            when(intentVnMappingResult2.getIntentId()).thenReturn(intentId2);

            List<VirtualNode> virtualNodestest = new ArrayList<VirtualNode>(3);
            VirtualNode virtualNode1 = mock(VirtualNode.class);
			VirtualNode virtualNode2 = mock(VirtualNode.class);
            virtualNodestest.add(virtualNode1);
			virtualNodestest.add(virtualNode2);
            VirtualNodes virtualNodes = mock(VirtualNodes.class);
            when(virtualNetwork.getVirtualNodes()).thenReturn(virtualNodes);
            when(virtualNodes.getVirtualNode()).thenReturn(virtualNodestest);
			
            List<VirtualResource> virtualResources = new ArrayList<VirtualResource>(3);
            VirtualResource virtualResource = mock(VirtualResource.class);
            virtualResources.add(virtualResource);
            when(intentVnMappingResult1.getVirtualResource()).thenReturn(virtualResources);
            VirtualResourceEntityId virtualResourceEntityId = mock(VirtualResourceEntityId.class);
            when(virtualResource.getVirtualResourceEntityId()).thenReturn(virtualResourceEntityId);
            when(virtualResourceEntityId.getValue()).thenReturn("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
            
			VirtualNodeId virtualNodeId1 = new VirtualNodeId("00000000-0000-0000-0000-000000000000");
			VirtualNodeId virtualNodeId2 = new VirtualNodeId("00000000-0000-0000-0000-000000000000");
            when(virtualNode1.getNodeId()).thenReturn(virtualNodeId1);
			when(virtualNode2.getNodeId()).thenReturn(virtualNodeId2);
			
            connectionMapper.resolveConnection(user,connection,virtualNetwork,userIntentVnMapping);
        }
        catch(IntentResolutionException excepted){
            Assert.assertEquals("Can not get the virtual node created for the node 11111111-1111-1111-1111-111111111111.",excepted.getMessage());
        }
		
		//branch endNode1.getNodeType().equals(layer2GroupNodeType)  endNode2.getNodeType().equals(externalGroupNodeType)
        //checkExternalLayer3Group false
		//intentVnMappingResult1 = null
        try{
            ConnectionType connectionTypetest2 = new ConnectionType("p2p");
            when(connection.getConnectionType()).thenReturn(connectionTypetest2);
            List<Node> nodes = new ArrayList<Node>(3);
			Node node1 = mock(Node.class);
			Node node2 = mock(Node.class);
            nodes.add(node1);
			nodes.add(node2);
            Objects objects = mock(Objects.class);
            when(user.getObjects()).thenReturn(objects);
            when(objects.getNode()).thenReturn(nodes);
            NodeId endnodeId1 = mock(NodeId.class);
			List<EndNode> endNodes = new ArrayList<EndNode>(3);
            EndNode endNode1 = mock(EndNode.class);
			EndNode endNode2 = mock(EndNode.class);
            endNodes.add(endNode1);
			endNodes.add(endNode2);
            when(connection.getEndNode()).thenReturn(endNodes);
            when(endNodes.get(0).getNodeId()).thenReturn(endnodeId1);
            NodeId nodeId1 = endnodeId1;
            when(node1.getNodeId()).thenReturn(nodeId1);
			NodeId endnodeId2 = mock(NodeId.class);
            when(endNodes.get(1).getNodeId()).thenReturn(endnodeId2);
			NodeId nodeId2 = endnodeId2;
            when(node2.getNodeId()).thenReturn(nodeId2);

            NodeType layer2GroupNodeTypetest = new NodeType("l2-group");
			NodeType externalGroupNodeTypetest = new NodeType("ext-group");
            when(node1.getNodeType()).thenReturn(layer2GroupNodeTypetest);
            when(node2.getNodeType()).thenReturn(externalGroupNodeTypetest);

			Property property = mock(Property.class);
            List<Property> propertys = new ArrayList<Property>(3);
            propertys.add(property);
            when(node2.getProperty()).thenReturn(propertys);
            PropertyName propertyNametest = mock(PropertyName.class);
            when(property.getPropertyName()).thenReturn(propertyNametest);
			
            List<IntentVnMappingResult> intentVnMappingResults = new ArrayList<IntentVnMappingResult>(3);
            IntentVnMappingResult intentVnMappingResult1 = mock(IntentVnMappingResult.class);
            IntentVnMappingResult intentVnMappingResult2 = mock(IntentVnMappingResult.class);
            intentVnMappingResults.add(intentVnMappingResult1);
            intentVnMappingResults.add(intentVnMappingResult2);
            when(userIntentVnMapping.getIntentVnMappingResult()).thenReturn(intentVnMappingResults);
            when(node1.getNodeId()).thenReturn(nodeId1);
            when(nodeId1.getValue()).thenReturn("11111111-1111-1111-1111-111111111111");
            IntentId intentId1 = new IntentId("00000000-0000-0000-0000-000000000000");
			IntentId intentId2 = new IntentId("00000000-0000-0000-0000-000000000000");
            when(intentVnMappingResult1.getIntentId()).thenReturn(intentId1);
            when(intentVnMappingResult2.getIntentId()).thenReturn(intentId2);
			
            connectionMapper.resolveConnection(user,connection,virtualNetwork,userIntentVnMapping);
        }
        catch(IntentResolutionException excepted){
            Assert.assertEquals("Can not get the intent-vn mapping result for the node 11111111-1111-1111-1111-111111111111.",excepted.getMessage());
        }
		
		//branch endNode1.getNodeType().equals(layer2GroupNodeType)  endNode2.getNodeType().equals(externalGroupNodeType)
        //checkExternalLayer3Group false
		//intentVnMappingResult2 = null
        try{
            ConnectionType connectionTypetest2 = new ConnectionType("p2p");
            when(connection.getConnectionType()).thenReturn(connectionTypetest2);
            List<Node> nodes = new ArrayList<Node>(3);
			Node node1 = mock(Node.class);
			Node node2 = mock(Node.class);
            nodes.add(node1);
			nodes.add(node2);
            Objects objects = mock(Objects.class);
            when(user.getObjects()).thenReturn(objects);
            when(objects.getNode()).thenReturn(nodes);
            NodeId endnodeId1 = mock(NodeId.class);
			List<EndNode> endNodes = new ArrayList<EndNode>(3);
            EndNode endNode1 = mock(EndNode.class);
			EndNode endNode2 = mock(EndNode.class);
            endNodes.add(endNode1);
			endNodes.add(endNode2);
            when(connection.getEndNode()).thenReturn(endNodes);
            when(endNodes.get(0).getNodeId()).thenReturn(endnodeId1);
            NodeId nodeId1 = endnodeId1;
            when(node1.getNodeId()).thenReturn(nodeId1);
			NodeId endnodeId2 = mock(NodeId.class);
            when(endNodes.get(1).getNodeId()).thenReturn(endnodeId2);
			NodeId nodeId2 = endnodeId2;
            when(node2.getNodeId()).thenReturn(nodeId2);
			
			NodeType layer2GroupNodeTypetest = new NodeType("l2-group");
			NodeType externalGroupNodeTypetest = new NodeType("ext-group");
            when(node1.getNodeType()).thenReturn(layer2GroupNodeTypetest);
            when(node2.getNodeType()).thenReturn(externalGroupNodeTypetest);
			
			Property property = mock(Property.class);
            List<Property> propertys = new ArrayList<Property>(3);
            propertys.add(property);
            when(node2.getProperty()).thenReturn(propertys);
            PropertyName propertyNametest = mock(PropertyName.class);
            when(property.getPropertyName()).thenReturn(propertyNametest);
			
            List<IntentVnMappingResult> intentVnMappingResults = new ArrayList<IntentVnMappingResult>(3);
            IntentVnMappingResult intentVnMappingResult1 = mock(IntentVnMappingResult.class);
            IntentVnMappingResult intentVnMappingResult2 = mock(IntentVnMappingResult.class);
            intentVnMappingResults.add(intentVnMappingResult1);
            intentVnMappingResults.add(intentVnMappingResult2);
            when(userIntentVnMapping.getIntentVnMappingResult()).thenReturn(intentVnMappingResults);
            when(node1.getNodeId()).thenReturn(nodeId1);
            when(nodeId1.getValue()).thenReturn("11111111-1111-1111-1111-111111111111");
            IntentId intentId1 = new IntentId("11111111-1111-1111-1111-111111111111");
			IntentId intentId2 = new IntentId("00000000-0000-0000-0000-000000000000");
            when(intentVnMappingResult1.getIntentId()).thenReturn(intentId1);
			when(node2.getNodeId()).thenReturn(nodeId2);
            when(nodeId2.getValue()).thenReturn("22222222-2222-2222-2222-222222222222");
            when(intentVnMappingResult2.getIntentId()).thenReturn(intentId2);

            connectionMapper.resolveConnection(user,connection,virtualNetwork,userIntentVnMapping);
        }
        catch(IntentResolutionException excepted){
            Assert.assertEquals("Can not get the intent-vn mapping result for the node 22222222-2222-2222-2222-222222222222.",excepted.getMessage());
        }
		
		//branch endNode1.getNodeType().equals(layer2GroupNodeType)  endNode2.getNodeType().equals(externalGroupNodeType)
        //checkExternalLayer3Group fasle
		//virtualRouter1 = null
        try{
            ConnectionType connectionTypetest2 = new ConnectionType("p2p");
            when(connection.getConnectionType()).thenReturn(connectionTypetest2);
            List<Node> nodes = new ArrayList<Node>(3);
			Node node1 = mock(Node.class);
			Node node2 = mock(Node.class);
            nodes.add(node1);
			nodes.add(node2);
            Objects objects = mock(Objects.class);
            when(user.getObjects()).thenReturn(objects);
            when(objects.getNode()).thenReturn(nodes);
            NodeId endnodeId1 = mock(NodeId.class);
			List<EndNode> endNodes = new ArrayList<EndNode>(3);
            EndNode endNode1 = mock(EndNode.class);
			EndNode endNode2 = mock(EndNode.class);
            endNodes.add(endNode1);
			endNodes.add(endNode2);
            when(connection.getEndNode()).thenReturn(endNodes);
            when(endNodes.get(0).getNodeId()).thenReturn(endnodeId1);
            NodeId nodeId1 = endnodeId1;
            when(node1.getNodeId()).thenReturn(nodeId1);
			NodeId endnodeId2 = mock(NodeId.class);
            when(endNodes.get(1).getNodeId()).thenReturn(endnodeId2);
			NodeId nodeId2 = endnodeId2;
            when(node2.getNodeId()).thenReturn(nodeId2);
			
			NodeType layer2GroupNodeTypetest = new NodeType("l2-group");
			NodeType externalGroupNodeTypetest = new NodeType("ext-group");
            when(node1.getNodeType()).thenReturn(layer2GroupNodeTypetest);
            when(node2.getNodeType()).thenReturn(externalGroupNodeTypetest);

			Property property = mock(Property.class);
            List<Property> propertys = new ArrayList<Property>(3);
            propertys.add(property);
            when(node2.getProperty()).thenReturn(propertys);
            PropertyName propertyNametest = mock(PropertyName.class);
            when(property.getPropertyName()).thenReturn(propertyNametest);
			
            List<IntentVnMappingResult> intentVnMappingResults = new ArrayList<IntentVnMappingResult>(3);
            IntentVnMappingResult intentVnMappingResult1 = mock(IntentVnMappingResult.class);
            IntentVnMappingResult intentVnMappingResult2 = mock(IntentVnMappingResult.class);
            intentVnMappingResults.add(intentVnMappingResult1);
            intentVnMappingResults.add(intentVnMappingResult2);
            when(userIntentVnMapping.getIntentVnMappingResult()).thenReturn(intentVnMappingResults);
            when(node1.getNodeId()).thenReturn(nodeId1);
            when(nodeId1.getValue()).thenReturn("11111111-1111-1111-1111-111111111111");
            IntentId intentId1 = new IntentId("11111111-1111-1111-1111-111111111111");
			IntentId intentId2 = new IntentId("22222222-2222-2222-2222-222222222222");
            when(intentVnMappingResult1.getIntentId()).thenReturn(intentId1);
			when(node2.getNodeId()).thenReturn(nodeId2);
            when(nodeId2.getValue()).thenReturn("22222222-2222-2222-2222-222222222222");
            when(intentVnMappingResult2.getIntentId()).thenReturn(intentId2);

            List<VirtualNode> virtualNodestest = new ArrayList<VirtualNode>(3);
            VirtualNode virtualNode1 = mock(VirtualNode.class);
			VirtualNode virtualNode2 = mock(VirtualNode.class);
            virtualNodestest.add(virtualNode1);
			virtualNodestest.add(virtualNode2);
            VirtualNodes virtualNodes = mock(VirtualNodes.class);
            when(virtualNetwork.getVirtualNodes()).thenReturn(virtualNodes);
            when(virtualNodes.getVirtualNode()).thenReturn(virtualNodestest);
			
            List<VirtualResource> virtualResources = new ArrayList<VirtualResource>(3);
            VirtualResource virtualResource = mock(VirtualResource.class);
            virtualResources.add(virtualResource);
            when(intentVnMappingResult1.getVirtualResource()).thenReturn(virtualResources);
            VirtualResourceEntityId virtualResourceEntityId = mock(VirtualResourceEntityId.class);
            when(virtualResource.getVirtualResourceEntityId()).thenReturn(virtualResourceEntityId);
            when(virtualResourceEntityId.getValue()).thenReturn("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
            
			VirtualNodeId virtualNodeId1 = new VirtualNodeId("00000000-0000-0000-0000-000000000000");
			VirtualNodeId virtualNodeId2 = new VirtualNodeId("00000000-0000-0000-0000-000000000000");
            when(virtualNode1.getNodeId()).thenReturn(virtualNodeId1);
			when(virtualNode2.getNodeId()).thenReturn(virtualNodeId2);
			
            connectionMapper.resolveConnection(user,connection,virtualNetwork,userIntentVnMapping);
        }
        catch(IntentResolutionException excepted){
            Assert.assertEquals("Can not get the virtual node created for the node 11111111-1111-1111-1111-111111111111.",excepted.getMessage());
        }
		
		//branch endNode1.getNodeType().equals(layer3GroupNodeType) endNode2.getNodeType().equals(hostNodeType)
		try{
            ConnectionType connectionTypetest2 = new ConnectionType("p2p");
            when(connection.getConnectionType()).thenReturn(connectionTypetest2);
            List<Node> nodes = new ArrayList<Node>(3);
			Node node1 = mock(Node.class);
			Node node2 = mock(Node.class);
            nodes.add(node1);
			nodes.add(node2);
            Objects objects = mock(Objects.class);
            when(user.getObjects()).thenReturn(objects);
            when(objects.getNode()).thenReturn(nodes);
            NodeId endnodeId1 = mock(NodeId.class);
			List<EndNode> endNodes = new ArrayList<EndNode>(3);
            EndNode endNode1 = mock(EndNode.class);
			EndNode endNode2 = mock(EndNode.class);
            endNodes.add(endNode1);
			endNodes.add(endNode2);
            when(connection.getEndNode()).thenReturn(endNodes);
            when(endNodes.get(0).getNodeId()).thenReturn(endnodeId1);
            NodeId nodeId1 = endnodeId1;
            when(node1.getNodeId()).thenReturn(nodeId1);
			NodeId endnodeId2 = mock(NodeId.class);
            when(endNodes.get(1).getNodeId()).thenReturn(endnodeId2);
			NodeId nodeId2 = endnodeId2;
            when(node2.getNodeId()).thenReturn(nodeId2);
            
            NodeType layer3GroupNodeTypetest = new NodeType("l3-group");
			NodeType hostNodeTypetest = new NodeType("host");
            when(node1.getNodeType()).thenReturn(layer3GroupNodeTypetest);
            when(node2.getNodeType()).thenReturn(hostNodeTypetest);

            when(nodeId2.getValue()).thenReturn("bbb");
            ConnectionId connectionId = mock(ConnectionId.class);
            when(connection.getConnectionId()).thenReturn(connectionId);
            when(connectionId.getValue()).thenReturn("3G");
            connectionMapper.resolveConnection(user,connection,virtualNetwork,userIntentVnMapping);
        }
        catch(IntentResolutionException excepted){
            Assert.assertEquals("The end-node bbb of the connection 3G can not be host type.",excepted.getMessage());
        }
		
		//branch endNode1.getNodeType().equals(layer3GroupNodeType) endNode2.getNodeType().equals(firewallNodeType)
        try{
            ConnectionType connectionTypetest2 = new ConnectionType("p2p");
            when(connection.getConnectionType()).thenReturn(connectionTypetest2);
            List<Node> nodes = new ArrayList<Node>(3);
			Node node1 = mock(Node.class);
			Node node2 = mock(Node.class);
            nodes.add(node1);
			nodes.add(node2);
            Objects objects = mock(Objects.class);
            when(user.getObjects()).thenReturn(objects);
            when(objects.getNode()).thenReturn(nodes);
            NodeId endnodeId1 = mock(NodeId.class);
			List<EndNode> endNodes = new ArrayList<EndNode>(3);
            EndNode endNode1 = mock(EndNode.class);
			EndNode endNode2 = mock(EndNode.class);
            endNodes.add(endNode1);
			endNodes.add(endNode2);
            when(connection.getEndNode()).thenReturn(endNodes);
            when(endNodes.get(0).getNodeId()).thenReturn(endnodeId1);
            NodeId nodeId1 = endnodeId1;
            when(node1.getNodeId()).thenReturn(nodeId1);
			NodeId endnodeId2 = mock(NodeId.class);
            when(endNodes.get(1).getNodeId()).thenReturn(endnodeId2);
			NodeId nodeId2 = endnodeId2;
            when(node2.getNodeId()).thenReturn(nodeId2);
            
            NodeType layer3GroupNodeTypetest = new NodeType("l3-group");
			NodeType firewallNodeTypetest = new NodeType("fw");
            when(node1.getNodeType()).thenReturn(layer3GroupNodeTypetest);
            when(node2.getNodeType()).thenReturn(firewallNodeTypetest);

            when(nodeId2.getValue()).thenReturn("bbb");
            ConnectionId connectionId = mock(ConnectionId.class);
            when(connection.getConnectionId()).thenReturn(connectionId);
            when(connectionId.getValue()).thenReturn("3G");
            connectionMapper.resolveConnection(user,connection,virtualNetwork,userIntentVnMapping);
        }
        catch(IntentResolutionException excepted){
            Assert.assertEquals("The end-node bbb of the connection 3G can not be fw type.",excepted.getMessage());
        }
		
		//branch endNode1.getNodeType().equals(layer3GroupNodeType) endNode2.getNodeType().equals(loadbalancerNodeType)
        try{
            ConnectionType connectionTypetest2 = new ConnectionType("p2p");
            when(connection.getConnectionType()).thenReturn(connectionTypetest2);
            List<Node> nodes = new ArrayList<Node>(3);
			Node node1 = mock(Node.class);
			Node node2 = mock(Node.class);
            nodes.add(node1);
			nodes.add(node2);
            Objects objects = mock(Objects.class);
            when(user.getObjects()).thenReturn(objects);
            when(objects.getNode()).thenReturn(nodes);
            NodeId endnodeId1 = mock(NodeId.class);
			List<EndNode> endNodes = new ArrayList<EndNode>(3);
            EndNode endNode1 = mock(EndNode.class);
			EndNode endNode2 = mock(EndNode.class);
            endNodes.add(endNode1);
			endNodes.add(endNode2);
            when(connection.getEndNode()).thenReturn(endNodes);
            when(endNodes.get(0).getNodeId()).thenReturn(endnodeId1);
            NodeId nodeId1 = endnodeId1;
            when(node1.getNodeId()).thenReturn(nodeId1);
			NodeId endnodeId2 = mock(NodeId.class);
            when(endNodes.get(1).getNodeId()).thenReturn(endnodeId2);
			NodeId nodeId2 = endnodeId2;
            when(node2.getNodeId()).thenReturn(nodeId2);
            
            NodeType layer3GroupNodeTypetest = new NodeType("l3-group");
			NodeType loadbalancerNodeTypetest = new NodeType("lb");
            when(node1.getNodeType()).thenReturn(layer3GroupNodeTypetest);
            when(node2.getNodeType()).thenReturn(loadbalancerNodeTypetest);

            when(nodeId2.getValue()).thenReturn("bbb");
            ConnectionId connectionId = mock(ConnectionId.class);
            when(connection.getConnectionId()).thenReturn(connectionId);
            when(connectionId.getValue()).thenReturn("3G");
            connectionMapper.resolveConnection(user,connection,virtualNetwork,userIntentVnMapping);
        }
        catch(IntentResolutionException excepted){
            Assert.assertEquals("The end-node bbb of the connection 3G can not be lb type.",excepted.getMessage());
        }
		
		//branch endNode1.getNodeType().equals(layer3GroupNodeType) endNode2.getNodeType().equals(cacheNodeType)
        try{
            ConnectionType connectionTypetest2 = new ConnectionType("p2p");
            when(connection.getConnectionType()).thenReturn(connectionTypetest2);
            List<Node> nodes = new ArrayList<Node>(3);
			Node node1 = mock(Node.class);
			Node node2 = mock(Node.class);
            nodes.add(node1);
			nodes.add(node2);
            Objects objects = mock(Objects.class);
            when(user.getObjects()).thenReturn(objects);
            when(objects.getNode()).thenReturn(nodes);
            NodeId endnodeId1 = mock(NodeId.class);
			List<EndNode> endNodes = new ArrayList<EndNode>(3);
            EndNode endNode1 = mock(EndNode.class);
			EndNode endNode2 = mock(EndNode.class);
            endNodes.add(endNode1);
			endNodes.add(endNode2);
            when(connection.getEndNode()).thenReturn(endNodes);
            when(endNodes.get(0).getNodeId()).thenReturn(endnodeId1);
            NodeId nodeId1 = endnodeId1;
            when(node1.getNodeId()).thenReturn(nodeId1);
			NodeId endnodeId2 = mock(NodeId.class);
            when(endNodes.get(1).getNodeId()).thenReturn(endnodeId2);
			NodeId nodeId2 = endnodeId2;
            when(node2.getNodeId()).thenReturn(nodeId2);
            
            NodeType layer3GroupNodeTypetest = new NodeType("l3-group");
			NodeType cacheNodeTypetest = new NodeType("cache");
            when(node1.getNodeType()).thenReturn(layer3GroupNodeTypetest);
            when(node2.getNodeType()).thenReturn(cacheNodeTypetest);

            when(nodeId2.getValue()).thenReturn("bbb");
            ConnectionId connectionId = mock(ConnectionId.class);
            when(connection.getConnectionId()).thenReturn(connectionId);
            when(connectionId.getValue()).thenReturn("3G");
            connectionMapper.resolveConnection(user,connection,virtualNetwork,userIntentVnMapping);
        }
        catch(IntentResolutionException excepted){
            Assert.assertEquals("The end-node bbb of the connection 3G can not be cache type.",excepted.getMessage());
        }
		
		//branch endNode1.getNodeType().equals(layer3GroupNodeType) endNode2.getNodeType().equals(layer2GroupNodeType)
        try{
            ConnectionType connectionTypetest2 = new ConnectionType("p2p");
            when(connection.getConnectionType()).thenReturn(connectionTypetest2);
            List<Node> nodes = new ArrayList<Node>(3);
			Node node1 = mock(Node.class);
			Node node2 = mock(Node.class);
            nodes.add(node1);
			nodes.add(node2);
            Objects objects = mock(Objects.class);
            when(user.getObjects()).thenReturn(objects);
            when(objects.getNode()).thenReturn(nodes);
            NodeId endnodeId1 = mock(NodeId.class);
			List<EndNode> endNodes = new ArrayList<EndNode>(3);
            EndNode endNode1 = mock(EndNode.class);
			EndNode endNode2 = mock(EndNode.class);
            endNodes.add(endNode1);
			endNodes.add(endNode2);
            when(connection.getEndNode()).thenReturn(endNodes);
            when(endNodes.get(0).getNodeId()).thenReturn(endnodeId1);
            NodeId nodeId1 = endnodeId1;
            when(node1.getNodeId()).thenReturn(nodeId1);
			NodeId endnodeId2 = mock(NodeId.class);
            when(endNodes.get(1).getNodeId()).thenReturn(endnodeId2);
			NodeId nodeId2 = endnodeId2;
            when(node2.getNodeId()).thenReturn(nodeId2);
            
            NodeType layer3GroupNodeTypetest = new NodeType("l3-group");
			NodeType layer2GroupNodeTypetest = new NodeType("l2-group");
            when(node1.getNodeType()).thenReturn(layer3GroupNodeTypetest);
            when(node2.getNodeType()).thenReturn(layer2GroupNodeTypetest);

            connectionMapper.resolveConnection(user,connection,virtualNetwork,userIntentVnMapping);
        }
        catch(IntentResolutionException excepted){
            Assert.assertNull(excepted.getMessage());
        }
		
		//branch endNode1.getNodeType().equals(layer3GroupNodeType) endNode2.getNodeType().equals(layer3GroupNodeType)
        try{
            ConnectionType connectionTypetest2 = new ConnectionType("p2p");
            when(connection.getConnectionType()).thenReturn(connectionTypetest2);
            List<Node> nodes = new ArrayList<Node>(3);
			Node node1 = mock(Node.class);
			Node node2 = mock(Node.class);
            nodes.add(node1);
			nodes.add(node2);
            Objects objects = mock(Objects.class);
            when(user.getObjects()).thenReturn(objects);
            when(objects.getNode()).thenReturn(nodes);
            NodeId endnodeId1 = mock(NodeId.class);
			List<EndNode> endNodes = new ArrayList<EndNode>(3);
            EndNode endNode1 = mock(EndNode.class);
			EndNode endNode2 = mock(EndNode.class);
            endNodes.add(endNode1);
			endNodes.add(endNode2);
            when(connection.getEndNode()).thenReturn(endNodes);
            when(endNodes.get(0).getNodeId()).thenReturn(endnodeId1);
            NodeId nodeId1 = endnodeId1;
            when(node1.getNodeId()).thenReturn(nodeId1);
			NodeId endnodeId2 = mock(NodeId.class);
            when(endNodes.get(1).getNodeId()).thenReturn(endnodeId2);
			NodeId nodeId2 = endnodeId2;
            when(node2.getNodeId()).thenReturn(nodeId2);
            
            NodeType layer3GroupNodeTypetest = new NodeType("l3-group");
            when(node1.getNodeType()).thenReturn(layer3GroupNodeTypetest);
            when(node2.getNodeType()).thenReturn(layer3GroupNodeTypetest);

            connectionMapper.resolveConnection(user,connection,virtualNetwork,userIntentVnMapping);
        }
        catch(IntentResolutionException excepted){
            Assert.assertNull(excepted.getMessage());
        }
		
		//branch endNode1.getNodeType().equals(layer3GroupNodeType)  endNode2.getNodeType().equals(externalGroupNodeType)
        //checkExternalLayer3Group
		//intentVnMappingResult1 = null
        try{
            ConnectionType connectionTypetest2 = new ConnectionType("p2p");
            when(connection.getConnectionType()).thenReturn(connectionTypetest2);
            List<Node> nodes = new ArrayList<Node>(3);
			Node node1 = mock(Node.class);
			Node node2 = mock(Node.class);
            nodes.add(node1);
			nodes.add(node2);
            Objects objects = mock(Objects.class);
            when(user.getObjects()).thenReturn(objects);
            when(objects.getNode()).thenReturn(nodes);
            NodeId endnodeId1 = mock(NodeId.class);
			List<EndNode> endNodes = new ArrayList<EndNode>(3);
            EndNode endNode1 = mock(EndNode.class);
			EndNode endNode2 = mock(EndNode.class);
            endNodes.add(endNode1);
			endNodes.add(endNode2);
            when(connection.getEndNode()).thenReturn(endNodes);
            when(endNodes.get(0).getNodeId()).thenReturn(endnodeId1);
            NodeId nodeId1 = endnodeId1;
            when(node1.getNodeId()).thenReturn(nodeId1);
			NodeId endnodeId2 = mock(NodeId.class);
            when(endNodes.get(1).getNodeId()).thenReturn(endnodeId2);
			NodeId nodeId2 = endnodeId2;
            when(node2.getNodeId()).thenReturn(nodeId2);

            NodeType layer3GroupNodeTypetest = new NodeType("l3-group");
			NodeType externalGroupNodeTypetest = new NodeType("ext-group");
            when(node1.getNodeType()).thenReturn(layer3GroupNodeTypetest);
            when(node2.getNodeType()).thenReturn(externalGroupNodeTypetest);

			Property property = mock(Property.class);
            List<Property> propertys = new ArrayList<Property>(3);
            propertys.add(property);
            when(node2.getProperty()).thenReturn(propertys);
            PropertyName propertyNametest = new PropertyName("ac-info-network");
            when(property.getPropertyName()).thenReturn(propertyNametest);
            PropertyValues propertyValues = mock(PropertyValues.class);
            when(property.getPropertyValues()).thenReturn(propertyValues);
            List<StringValue> stringValues = new ArrayList<StringValue>(3);
            StringValue stringValue = mock(StringValue.class);
            stringValues.add(stringValue);
            when(propertyValues.getStringValue()).thenReturn(stringValues);
            when(stringValues.get(0).getValue()).thenReturn("layer3");
			
            List<IntentVnMappingResult> intentVnMappingResults = new ArrayList<IntentVnMappingResult>(3);
            IntentVnMappingResult intentVnMappingResult1 = mock(IntentVnMappingResult.class);
            IntentVnMappingResult intentVnMappingResult2 = mock(IntentVnMappingResult.class);
            intentVnMappingResults.add(intentVnMappingResult1);
            intentVnMappingResults.add(intentVnMappingResult2);
            when(userIntentVnMapping.getIntentVnMappingResult()).thenReturn(intentVnMappingResults);
            when(node1.getNodeId()).thenReturn(nodeId1);
            when(nodeId1.getValue()).thenReturn("11111111-1111-1111-1111-111111111111");
            IntentId intentId1 = new IntentId("00000000-0000-0000-0000-000000000000");
			IntentId intentId2 = new IntentId("00000000-0000-0000-0000-000000000000");
            when(intentVnMappingResult1.getIntentId()).thenReturn(intentId1);
            when(intentVnMappingResult2.getIntentId()).thenReturn(intentId2);
			
            connectionMapper.resolveConnection(user,connection,virtualNetwork,userIntentVnMapping);
        }
        catch(IntentResolutionException excepted){
            Assert.assertEquals("Can not get the intent-vn mapping result for the node 11111111-1111-1111-1111-111111111111.",excepted.getMessage());
        }
		
		//branch endNode1.getNodeType().equals(layer3GroupNodeType)  endNode2.getNodeType().equals(externalGroupNodeType)
        //checkExternalLayer3Group
		//intentVnMappingResult2 = null
        try{
            ConnectionType connectionTypetest2 = new ConnectionType("p2p");
            when(connection.getConnectionType()).thenReturn(connectionTypetest2);
            List<Node> nodes = new ArrayList<Node>(3);
			Node node1 = mock(Node.class);
			Node node2 = mock(Node.class);
            nodes.add(node1);
			nodes.add(node2);
            Objects objects = mock(Objects.class);
            when(user.getObjects()).thenReturn(objects);
            when(objects.getNode()).thenReturn(nodes);
            NodeId endnodeId1 = mock(NodeId.class);
			List<EndNode> endNodes = new ArrayList<EndNode>(3);
            EndNode endNode1 = mock(EndNode.class);
			EndNode endNode2 = mock(EndNode.class);
            endNodes.add(endNode1);
			endNodes.add(endNode2);
            when(connection.getEndNode()).thenReturn(endNodes);
            when(endNodes.get(0).getNodeId()).thenReturn(endnodeId1);
            NodeId nodeId1 = endnodeId1;
            when(node1.getNodeId()).thenReturn(nodeId1);
			NodeId endnodeId2 = mock(NodeId.class);
            when(endNodes.get(1).getNodeId()).thenReturn(endnodeId2);
			NodeId nodeId2 = endnodeId2;
            when(node2.getNodeId()).thenReturn(nodeId2);
			
			NodeType layer3GroupNodeTypetest = new NodeType("l3-group");
			NodeType externalGroupNodeTypetest = new NodeType("ext-group");
            when(node1.getNodeType()).thenReturn(layer3GroupNodeTypetest);
            when(node2.getNodeType()).thenReturn(externalGroupNodeTypetest);
			
			Property property = mock(Property.class);
            List<Property> propertys = new ArrayList<Property>(3);
            propertys.add(property);
            when(node2.getProperty()).thenReturn(propertys);
            PropertyName propertyNametest = new PropertyName("ac-info-network");
            when(property.getPropertyName()).thenReturn(propertyNametest);
            PropertyValues propertyValues = mock(PropertyValues.class);
            when(property.getPropertyValues()).thenReturn(propertyValues);
            List<StringValue> stringValues = new ArrayList<StringValue>(3);
            StringValue stringValue = mock(StringValue.class);
            stringValues.add(stringValue);
            when(propertyValues.getStringValue()).thenReturn(stringValues);
            when(stringValues.get(0).getValue()).thenReturn("layer3");
			
            List<IntentVnMappingResult> intentVnMappingResults = new ArrayList<IntentVnMappingResult>(3);
            IntentVnMappingResult intentVnMappingResult1 = mock(IntentVnMappingResult.class);
            IntentVnMappingResult intentVnMappingResult2 = mock(IntentVnMappingResult.class);
            intentVnMappingResults.add(intentVnMappingResult1);
            intentVnMappingResults.add(intentVnMappingResult2);
            when(userIntentVnMapping.getIntentVnMappingResult()).thenReturn(intentVnMappingResults);
            when(node1.getNodeId()).thenReturn(nodeId1);
            when(nodeId1.getValue()).thenReturn("11111111-1111-1111-1111-111111111111");
            IntentId intentId1 = new IntentId("11111111-1111-1111-1111-111111111111");
			IntentId intentId2 = new IntentId("00000000-0000-0000-0000-000000000000");
            when(intentVnMappingResult1.getIntentId()).thenReturn(intentId1);
			when(node2.getNodeId()).thenReturn(nodeId2);
            when(nodeId2.getValue()).thenReturn("22222222-2222-2222-2222-222222222222");
            when(intentVnMappingResult2.getIntentId()).thenReturn(intentId2);

            connectionMapper.resolveConnection(user,connection,virtualNetwork,userIntentVnMapping);
        }
        catch(IntentResolutionException excepted){
            Assert.assertEquals("Can not get the intent-vn mapping result for the node 22222222-2222-2222-2222-222222222222.",excepted.getMessage());
        }
		
		//branch endNode1.getNodeType().equals(layer3GroupNodeType)  endNode2.getNodeType().equals(externalGroupNodeType)
        //checkExternalLayer3Group
		//virtualRouter1 = null
        try{
            ConnectionType connectionTypetest2 = new ConnectionType("p2p");
            when(connection.getConnectionType()).thenReturn(connectionTypetest2);
            List<Node> nodes = new ArrayList<Node>(3);
			Node node1 = mock(Node.class);
			Node node2 = mock(Node.class);
            nodes.add(node1);
			nodes.add(node2);
            Objects objects = mock(Objects.class);
            when(user.getObjects()).thenReturn(objects);
            when(objects.getNode()).thenReturn(nodes);
            NodeId endnodeId1 = mock(NodeId.class);
			List<EndNode> endNodes = new ArrayList<EndNode>(3);
            EndNode endNode1 = mock(EndNode.class);
			EndNode endNode2 = mock(EndNode.class);
            endNodes.add(endNode1);
			endNodes.add(endNode2);
            when(connection.getEndNode()).thenReturn(endNodes);
            when(endNodes.get(0).getNodeId()).thenReturn(endnodeId1);
            NodeId nodeId1 = endnodeId1;
            when(node1.getNodeId()).thenReturn(nodeId1);
			NodeId endnodeId2 = mock(NodeId.class);
            when(endNodes.get(1).getNodeId()).thenReturn(endnodeId2);
			NodeId nodeId2 = endnodeId2;
            when(node2.getNodeId()).thenReturn(nodeId2);
			
			NodeType layer3GroupNodeTypetest = new NodeType("l3-group");
			NodeType externalGroupNodeTypetest = new NodeType("ext-group");
            when(node1.getNodeType()).thenReturn(layer3GroupNodeTypetest);
            when(node2.getNodeType()).thenReturn(externalGroupNodeTypetest);

			Property property = mock(Property.class);
            List<Property> propertys = new ArrayList<Property>(3);
            propertys.add(property);
            when(node2.getProperty()).thenReturn(propertys);
            PropertyName propertyNametest = new PropertyName("ac-info-network");
            when(property.getPropertyName()).thenReturn(propertyNametest);
            PropertyValues propertyValues = mock(PropertyValues.class);
            when(property.getPropertyValues()).thenReturn(propertyValues);
            List<StringValue> stringValues = new ArrayList<StringValue>(3);
            StringValue stringValue = mock(StringValue.class);
            stringValues.add(stringValue);
            when(propertyValues.getStringValue()).thenReturn(stringValues);
            when(stringValues.get(0).getValue()).thenReturn("layer3");
			
            List<IntentVnMappingResult> intentVnMappingResults = new ArrayList<IntentVnMappingResult>(3);
            IntentVnMappingResult intentVnMappingResult1 = mock(IntentVnMappingResult.class);
            IntentVnMappingResult intentVnMappingResult2 = mock(IntentVnMappingResult.class);
            intentVnMappingResults.add(intentVnMappingResult1);
            intentVnMappingResults.add(intentVnMappingResult2);
            when(userIntentVnMapping.getIntentVnMappingResult()).thenReturn(intentVnMappingResults);
            when(node1.getNodeId()).thenReturn(nodeId1);
            when(nodeId1.getValue()).thenReturn("11111111-1111-1111-1111-111111111111");
            IntentId intentId1 = new IntentId("11111111-1111-1111-1111-111111111111");
			IntentId intentId2 = new IntentId("22222222-2222-2222-2222-222222222222");
            when(intentVnMappingResult1.getIntentId()).thenReturn(intentId1);
			when(node2.getNodeId()).thenReturn(nodeId2);
            when(nodeId2.getValue()).thenReturn("22222222-2222-2222-2222-222222222222");
            when(intentVnMappingResult2.getIntentId()).thenReturn(intentId2);

            List<VirtualNode> virtualNodestest = new ArrayList<VirtualNode>(3);
            VirtualNode virtualNode1 = mock(VirtualNode.class);
			VirtualNode virtualNode2 = mock(VirtualNode.class);
            virtualNodestest.add(virtualNode1);
			virtualNodestest.add(virtualNode2);
            VirtualNodes virtualNodes = mock(VirtualNodes.class);
            when(virtualNetwork.getVirtualNodes()).thenReturn(virtualNodes);
            when(virtualNodes.getVirtualNode()).thenReturn(virtualNodestest);
			
            List<VirtualResource> virtualResources = new ArrayList<VirtualResource>(3);
            VirtualResource virtualResource = mock(VirtualResource.class);
            virtualResources.add(virtualResource);
            when(intentVnMappingResult1.getVirtualResource()).thenReturn(virtualResources);
            VirtualResourceEntityId virtualResourceEntityId = mock(VirtualResourceEntityId.class);
            when(virtualResource.getVirtualResourceEntityId()).thenReturn(virtualResourceEntityId);
            when(virtualResourceEntityId.getValue()).thenReturn("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
            
			VirtualNodeId virtualNodeId1 = new VirtualNodeId("00000000-0000-0000-0000-000000000000");
			VirtualNodeId virtualNodeId2 = new VirtualNodeId("00000000-0000-0000-0000-000000000000");
            when(virtualNode1.getNodeId()).thenReturn(virtualNodeId1);
			when(virtualNode2.getNodeId()).thenReturn(virtualNodeId2);
			
            connectionMapper.resolveConnection(user,connection,virtualNetwork,userIntentVnMapping);
        }
        catch(IntentResolutionException excepted){
            Assert.assertEquals("Can not get the virtual node created for the node 11111111-1111-1111-1111-111111111111.",excepted.getMessage());
        }
		
		//branch endNode1.getNodeType().equals(layer3GroupNodeType)  endNode2.getNodeType().equals(externalGroupNodeType)
        //checkExternalLayer3Group false
		//intentVnMappingResult1 = null
        try{
            ConnectionType connectionTypetest2 = new ConnectionType("p2p");
            when(connection.getConnectionType()).thenReturn(connectionTypetest2);
            List<Node> nodes = new ArrayList<Node>(3);
			Node node1 = mock(Node.class);
			Node node2 = mock(Node.class);
            nodes.add(node1);
			nodes.add(node2);
            Objects objects = mock(Objects.class);
            when(user.getObjects()).thenReturn(objects);
            when(objects.getNode()).thenReturn(nodes);
            NodeId endnodeId1 = mock(NodeId.class);
			List<EndNode> endNodes = new ArrayList<EndNode>(3);
            EndNode endNode1 = mock(EndNode.class);
			EndNode endNode2 = mock(EndNode.class);
            endNodes.add(endNode1);
			endNodes.add(endNode2);
            when(connection.getEndNode()).thenReturn(endNodes);
            when(endNodes.get(0).getNodeId()).thenReturn(endnodeId1);
            NodeId nodeId1 = endnodeId1;
            when(node1.getNodeId()).thenReturn(nodeId1);
			NodeId endnodeId2 = mock(NodeId.class);
            when(endNodes.get(1).getNodeId()).thenReturn(endnodeId2);
			NodeId nodeId2 = endnodeId2;
            when(node2.getNodeId()).thenReturn(nodeId2);

            NodeType layer3GroupNodeTypetest = new NodeType("l3-group");
			NodeType externalGroupNodeTypetest = new NodeType("ext-group");
            when(node1.getNodeType()).thenReturn(layer3GroupNodeTypetest);
            when(node2.getNodeType()).thenReturn(externalGroupNodeTypetest);

			Property property = mock(Property.class);
            List<Property> propertys = new ArrayList<Property>(3);
            propertys.add(property);
            when(node2.getProperty()).thenReturn(propertys);
            PropertyName propertyNametest = mock(PropertyName.class);
            when(property.getPropertyName()).thenReturn(propertyNametest);
			
            List<IntentVnMappingResult> intentVnMappingResults = new ArrayList<IntentVnMappingResult>(3);
            IntentVnMappingResult intentVnMappingResult1 = mock(IntentVnMappingResult.class);
            IntentVnMappingResult intentVnMappingResult2 = mock(IntentVnMappingResult.class);
            intentVnMappingResults.add(intentVnMappingResult1);
            intentVnMappingResults.add(intentVnMappingResult2);
            when(userIntentVnMapping.getIntentVnMappingResult()).thenReturn(intentVnMappingResults);
            when(node1.getNodeId()).thenReturn(nodeId1);
            when(nodeId1.getValue()).thenReturn("11111111-1111-1111-1111-111111111111");
            IntentId intentId1 = new IntentId("00000000-0000-0000-0000-000000000000");
			IntentId intentId2 = new IntentId("00000000-0000-0000-0000-000000000000");
            when(intentVnMappingResult1.getIntentId()).thenReturn(intentId1);
            when(intentVnMappingResult2.getIntentId()).thenReturn(intentId2);
			
            connectionMapper.resolveConnection(user,connection,virtualNetwork,userIntentVnMapping);
        }
        catch(IntentResolutionException excepted){
            Assert.assertEquals("Can not get the intent-vn mapping result for the node 11111111-1111-1111-1111-111111111111.",excepted.getMessage());
        }
		
		//branch endNode1.getNodeType().equals(layer3GroupNodeType)  endNode2.getNodeType().equals(externalGroupNodeType)
        //checkExternalLayer3Group false
		//intentVnMappingResult2 = null
        try{
            ConnectionType connectionTypetest2 = new ConnectionType("p2p");
            when(connection.getConnectionType()).thenReturn(connectionTypetest2);
            List<Node> nodes = new ArrayList<Node>(3);
			Node node1 = mock(Node.class);
			Node node2 = mock(Node.class);
            nodes.add(node1);
			nodes.add(node2);
            Objects objects = mock(Objects.class);
            when(user.getObjects()).thenReturn(objects);
            when(objects.getNode()).thenReturn(nodes);
            NodeId endnodeId1 = mock(NodeId.class);
			List<EndNode> endNodes = new ArrayList<EndNode>(3);
            EndNode endNode1 = mock(EndNode.class);
			EndNode endNode2 = mock(EndNode.class);
            endNodes.add(endNode1);
			endNodes.add(endNode2);
            when(connection.getEndNode()).thenReturn(endNodes);
            when(endNodes.get(0).getNodeId()).thenReturn(endnodeId1);
            NodeId nodeId1 = endnodeId1;
            when(node1.getNodeId()).thenReturn(nodeId1);
			NodeId endnodeId2 = mock(NodeId.class);
            when(endNodes.get(1).getNodeId()).thenReturn(endnodeId2);
			NodeId nodeId2 = endnodeId2;
            when(node2.getNodeId()).thenReturn(nodeId2);
			
			NodeType layer3GroupNodeTypetest = new NodeType("l3-group");
			NodeType externalGroupNodeTypetest = new NodeType("ext-group");
            when(node1.getNodeType()).thenReturn(layer3GroupNodeTypetest);
            when(node2.getNodeType()).thenReturn(externalGroupNodeTypetest);
			
			Property property = mock(Property.class);
            List<Property> propertys = new ArrayList<Property>(3);
            propertys.add(property);
            when(node2.getProperty()).thenReturn(propertys);
            PropertyName propertyNametest = mock(PropertyName.class);
            when(property.getPropertyName()).thenReturn(propertyNametest);
			
            List<IntentVnMappingResult> intentVnMappingResults = new ArrayList<IntentVnMappingResult>(3);
            IntentVnMappingResult intentVnMappingResult1 = mock(IntentVnMappingResult.class);
            IntentVnMappingResult intentVnMappingResult2 = mock(IntentVnMappingResult.class);
            intentVnMappingResults.add(intentVnMappingResult1);
            intentVnMappingResults.add(intentVnMappingResult2);
            when(userIntentVnMapping.getIntentVnMappingResult()).thenReturn(intentVnMappingResults);
            when(node1.getNodeId()).thenReturn(nodeId1);
            when(nodeId1.getValue()).thenReturn("11111111-1111-1111-1111-111111111111");
            IntentId intentId1 = new IntentId("11111111-1111-1111-1111-111111111111");
			IntentId intentId2 = new IntentId("00000000-0000-0000-0000-000000000000");
            when(intentVnMappingResult1.getIntentId()).thenReturn(intentId1);
			when(node2.getNodeId()).thenReturn(nodeId2);
            when(nodeId2.getValue()).thenReturn("22222222-2222-2222-2222-222222222222");
            when(intentVnMappingResult2.getIntentId()).thenReturn(intentId2);

            connectionMapper.resolveConnection(user,connection,virtualNetwork,userIntentVnMapping);
        }
        catch(IntentResolutionException excepted){
            Assert.assertEquals("Can not get the intent-vn mapping result for the node 22222222-2222-2222-2222-222222222222.",excepted.getMessage());
        }
		
		//branch endNode1.getNodeType().equals(layer3GroupNodeType)  endNode2.getNodeType().equals(externalGroupNodeType)
        //checkExternalLayer3Group fasle
		//virtualRouter1 = null
        try{
            ConnectionType connectionTypetest2 = new ConnectionType("p2p");
            when(connection.getConnectionType()).thenReturn(connectionTypetest2);
            List<Node> nodes = new ArrayList<Node>(3);
			Node node1 = mock(Node.class);
			Node node2 = mock(Node.class);
            nodes.add(node1);
			nodes.add(node2);
            Objects objects = mock(Objects.class);
            when(user.getObjects()).thenReturn(objects);
            when(objects.getNode()).thenReturn(nodes);
            NodeId endnodeId1 = mock(NodeId.class);
			List<EndNode> endNodes = new ArrayList<EndNode>(3);
            EndNode endNode1 = mock(EndNode.class);
			EndNode endNode2 = mock(EndNode.class);
            endNodes.add(endNode1);
			endNodes.add(endNode2);
            when(connection.getEndNode()).thenReturn(endNodes);
            when(endNodes.get(0).getNodeId()).thenReturn(endnodeId1);
            NodeId nodeId1 = endnodeId1;
            when(node1.getNodeId()).thenReturn(nodeId1);
			NodeId endnodeId2 = mock(NodeId.class);
            when(endNodes.get(1).getNodeId()).thenReturn(endnodeId2);
			NodeId nodeId2 = endnodeId2;
            when(node2.getNodeId()).thenReturn(nodeId2);
			
			NodeType layer3GroupNodeTypetest = new NodeType("l3-group");
			NodeType externalGroupNodeTypetest = new NodeType("ext-group");
            when(node1.getNodeType()).thenReturn(layer3GroupNodeTypetest);
            when(node2.getNodeType()).thenReturn(externalGroupNodeTypetest);

			Property property = mock(Property.class);
            List<Property> propertys = new ArrayList<Property>(3);
            propertys.add(property);
            when(node2.getProperty()).thenReturn(propertys);
            PropertyName propertyNametest = mock(PropertyName.class);
            when(property.getPropertyName()).thenReturn(propertyNametest);
			
            List<IntentVnMappingResult> intentVnMappingResults = new ArrayList<IntentVnMappingResult>(3);
            IntentVnMappingResult intentVnMappingResult1 = mock(IntentVnMappingResult.class);
            IntentVnMappingResult intentVnMappingResult2 = mock(IntentVnMappingResult.class);
            intentVnMappingResults.add(intentVnMappingResult1);
            intentVnMappingResults.add(intentVnMappingResult2);
            when(userIntentVnMapping.getIntentVnMappingResult()).thenReturn(intentVnMappingResults);
            when(node1.getNodeId()).thenReturn(nodeId1);
            when(nodeId1.getValue()).thenReturn("11111111-1111-1111-1111-111111111111");
            IntentId intentId1 = new IntentId("11111111-1111-1111-1111-111111111111");
			IntentId intentId2 = new IntentId("22222222-2222-2222-2222-222222222222");
            when(intentVnMappingResult1.getIntentId()).thenReturn(intentId1);
			when(node2.getNodeId()).thenReturn(nodeId2);
            when(nodeId2.getValue()).thenReturn("22222222-2222-2222-2222-222222222222");
            when(intentVnMappingResult2.getIntentId()).thenReturn(intentId2);

            List<VirtualNode> virtualNodestest = new ArrayList<VirtualNode>(3);
            VirtualNode virtualNode1 = mock(VirtualNode.class);
			VirtualNode virtualNode2 = mock(VirtualNode.class);
            virtualNodestest.add(virtualNode1);
			virtualNodestest.add(virtualNode2);
            VirtualNodes virtualNodes = mock(VirtualNodes.class);
            when(virtualNetwork.getVirtualNodes()).thenReturn(virtualNodes);
            when(virtualNodes.getVirtualNode()).thenReturn(virtualNodestest);
			
            List<VirtualResource> virtualResources = new ArrayList<VirtualResource>(3);
            VirtualResource virtualResource = mock(VirtualResource.class);
            virtualResources.add(virtualResource);
            when(intentVnMappingResult1.getVirtualResource()).thenReturn(virtualResources);
            VirtualResourceEntityId virtualResourceEntityId = mock(VirtualResourceEntityId.class);
            when(virtualResource.getVirtualResourceEntityId()).thenReturn(virtualResourceEntityId);
            when(virtualResourceEntityId.getValue()).thenReturn("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
            
			VirtualNodeId virtualNodeId1 = new VirtualNodeId("00000000-0000-0000-0000-000000000000");
			VirtualNodeId virtualNodeId2 = new VirtualNodeId("00000000-0000-0000-0000-000000000000");
            when(virtualNode1.getNodeId()).thenReturn(virtualNodeId1);
			when(virtualNode2.getNodeId()).thenReturn(virtualNodeId2);
			
            connectionMapper.resolveConnection(user,connection,virtualNetwork,userIntentVnMapping);
        }
        catch(IntentResolutionException excepted){
            Assert.assertEquals("Can not get the virtual node created for the node 11111111-1111-1111-1111-111111111111.",excepted.getMessage());
        }
		
		//branch endNode1.getNodeType().equals(externalGroupNodeType) endNode2.getNodeType().equals(hostNodeType)
        try{
            ConnectionType connectionTypetest2 = new ConnectionType("p2p");
            when(connection.getConnectionType()).thenReturn(connectionTypetest2);
            List<Node> nodes = new ArrayList<Node>(3);
			Node node1 = mock(Node.class);
			Node node2 = mock(Node.class);
            nodes.add(node1);
			nodes.add(node2);
            Objects objects = mock(Objects.class);
            when(user.getObjects()).thenReturn(objects);
            when(objects.getNode()).thenReturn(nodes);
            NodeId endnodeId1 = mock(NodeId.class);
			List<EndNode> endNodes = new ArrayList<EndNode>(3);
            EndNode endNode1 = mock(EndNode.class);
			EndNode endNode2 = mock(EndNode.class);
            endNodes.add(endNode1);
			endNodes.add(endNode2);
            when(connection.getEndNode()).thenReturn(endNodes);
            when(endNodes.get(0).getNodeId()).thenReturn(endnodeId1);
            NodeId nodeId1 = endnodeId1;
            when(node1.getNodeId()).thenReturn(nodeId1);
			NodeId endnodeId2 = mock(NodeId.class);
            when(endNodes.get(1).getNodeId()).thenReturn(endnodeId2);
			NodeId nodeId2 = endnodeId2;
            when(node2.getNodeId()).thenReturn(nodeId2);
            
            NodeType externalGroupNodeTypetest = new NodeType("ext-group");
			NodeType hostNodeTypetest = new NodeType("host");
            when(node1.getNodeType()).thenReturn(externalGroupNodeTypetest);
            when(node2.getNodeType()).thenReturn(hostNodeTypetest);

            when(nodeId2.getValue()).thenReturn("bbb");
            ConnectionId connectionId = mock(ConnectionId.class);
            when(connection.getConnectionId()).thenReturn(connectionId);
            when(connectionId.getValue()).thenReturn("ext");
            connectionMapper.resolveConnection(user,connection,virtualNetwork,userIntentVnMapping);
        }
        catch(IntentResolutionException excepted){
            Assert.assertEquals("The end-node bbb of the connection ext can not be host type.",excepted.getMessage());
        }
		
		//branch endNode1.getNodeType().equals(externalGroupNodeType) endNode2.getNodeType().equals(serviceChainGroupNodeType)
        try{
            ConnectionType connectionTypetest2 = new ConnectionType("p2p");
            when(connection.getConnectionType()).thenReturn(connectionTypetest2);
            List<Node> nodes = new ArrayList<Node>(3);
			Node node1 = mock(Node.class);
			Node node2 = mock(Node.class);
            nodes.add(node1);
			nodes.add(node2);
            Objects objects = mock(Objects.class);
            when(user.getObjects()).thenReturn(objects);
            when(objects.getNode()).thenReturn(nodes);
            NodeId endnodeId1 = mock(NodeId.class);
			List<EndNode> endNodes = new ArrayList<EndNode>(3);
            EndNode endNode1 = mock(EndNode.class);
			EndNode endNode2 = mock(EndNode.class);
            endNodes.add(endNode1);
			endNodes.add(endNode2);
            when(connection.getEndNode()).thenReturn(endNodes);
            when(endNodes.get(0).getNodeId()).thenReturn(endnodeId1);
            NodeId nodeId1 = endnodeId1;
            when(node1.getNodeId()).thenReturn(nodeId1);
			NodeId endnodeId2 = mock(NodeId.class);
            when(endNodes.get(1).getNodeId()).thenReturn(endnodeId2);
			NodeId nodeId2 = endnodeId2;
            when(node2.getNodeId()).thenReturn(nodeId2);
            
            NodeType externalGroupNodeTypetest = new NodeType("ext-group");
			NodeType serviceChainGroupNodeTypetest = new NodeType("chain-group");
            when(node1.getNodeType()).thenReturn(externalGroupNodeTypetest);
            when(node2.getNodeType()).thenReturn(serviceChainGroupNodeTypetest);

            connectionMapper.resolveConnection(user,connection,virtualNetwork,userIntentVnMapping);
        }
        catch(IntentResolutionException excepted){
            Assert.assertNull(excepted.getMessage());
        }
		
		//branch endNode1.getNodeType().equals(externalGroupNodeType) endNode2.getNodeType().equals(firewallNodeType)
        try{
            ConnectionType connectionTypetest2 = new ConnectionType("p2p");
            when(connection.getConnectionType()).thenReturn(connectionTypetest2);
            List<Node> nodes = new ArrayList<Node>(3);
			Node node1 = mock(Node.class);
			Node node2 = mock(Node.class);
            nodes.add(node1);
			nodes.add(node2);
            Objects objects = mock(Objects.class);
            when(user.getObjects()).thenReturn(objects);
            when(objects.getNode()).thenReturn(nodes);
            NodeId endnodeId1 = mock(NodeId.class);
			List<EndNode> endNodes = new ArrayList<EndNode>(3);
            EndNode endNode1 = mock(EndNode.class);
			EndNode endNode2 = mock(EndNode.class);
            endNodes.add(endNode1);
			endNodes.add(endNode2);
            when(connection.getEndNode()).thenReturn(endNodes);
            when(endNodes.get(0).getNodeId()).thenReturn(endnodeId1);
            NodeId nodeId1 = endnodeId1;
            when(node1.getNodeId()).thenReturn(nodeId1);
			NodeId endnodeId2 = mock(NodeId.class);
            when(endNodes.get(1).getNodeId()).thenReturn(endnodeId2);
			NodeId nodeId2 = endnodeId2;
            when(node2.getNodeId()).thenReturn(nodeId2);
            
            NodeType externalGroupNodeTypetest = new NodeType("ext-group");
			NodeType firewallNodeTypetest = new NodeType("fw");
            when(node1.getNodeType()).thenReturn(externalGroupNodeTypetest);
            when(node2.getNodeType()).thenReturn(firewallNodeTypetest);

            when(nodeId2.getValue()).thenReturn("bbb");
            ConnectionId connectionId = mock(ConnectionId.class);
            when(connection.getConnectionId()).thenReturn(connectionId);
            when(connectionId.getValue()).thenReturn("ext");
            connectionMapper.resolveConnection(user,connection,virtualNetwork,userIntentVnMapping);
        }
        catch(IntentResolutionException excepted){
            Assert.assertEquals("The end-node bbb of the connection ext can not be fw type.",excepted.getMessage());
        }
		
		//branch endNode1.getNodeType().equals(externalGroupNodeType) endNode2.getNodeType().equals(loadbalancerNodeType)
        try{
            ConnectionType connectionTypetest2 = new ConnectionType("p2p");
            when(connection.getConnectionType()).thenReturn(connectionTypetest2);
            List<Node> nodes = new ArrayList<Node>(3);
			Node node1 = mock(Node.class);
			Node node2 = mock(Node.class);
            nodes.add(node1);
			nodes.add(node2);
            Objects objects = mock(Objects.class);
            when(user.getObjects()).thenReturn(objects);
            when(objects.getNode()).thenReturn(nodes);
            NodeId endnodeId1 = mock(NodeId.class);
			List<EndNode> endNodes = new ArrayList<EndNode>(3);
            EndNode endNode1 = mock(EndNode.class);
			EndNode endNode2 = mock(EndNode.class);
            endNodes.add(endNode1);
			endNodes.add(endNode2);
            when(connection.getEndNode()).thenReturn(endNodes);
            when(endNodes.get(0).getNodeId()).thenReturn(endnodeId1);
            NodeId nodeId1 = endnodeId1;
            when(node1.getNodeId()).thenReturn(nodeId1);
			NodeId endnodeId2 = mock(NodeId.class);
            when(endNodes.get(1).getNodeId()).thenReturn(endnodeId2);
			NodeId nodeId2 = endnodeId2;
            when(node2.getNodeId()).thenReturn(nodeId2);
            
            NodeType externalGroupNodeTypetest = new NodeType("ext-group");
			NodeType loadbalancerNodeTypetest = new NodeType("lb");
            when(node1.getNodeType()).thenReturn(externalGroupNodeTypetest);
            when(node2.getNodeType()).thenReturn(loadbalancerNodeTypetest);

            when(nodeId2.getValue()).thenReturn("bbb");
            ConnectionId connectionId = mock(ConnectionId.class);
            when(connection.getConnectionId()).thenReturn(connectionId);
            when(connectionId.getValue()).thenReturn("ext");
            connectionMapper.resolveConnection(user,connection,virtualNetwork,userIntentVnMapping);
        }
        catch(IntentResolutionException excepted){
            Assert.assertEquals("The end-node bbb of the connection ext can not be lb type.",excepted.getMessage());
        }
		
		//branch endNode1.getNodeType().equals(externalGroupNodeType) endNode2.getNodeType().equals(cacheNodeType)
        try{
            ConnectionType connectionTypetest2 = new ConnectionType("p2p");
            when(connection.getConnectionType()).thenReturn(connectionTypetest2);
            List<Node> nodes = new ArrayList<Node>(3);
			Node node1 = mock(Node.class);
			Node node2 = mock(Node.class);
            nodes.add(node1);
			nodes.add(node2);
            Objects objects = mock(Objects.class);
            when(user.getObjects()).thenReturn(objects);
            when(objects.getNode()).thenReturn(nodes);
            NodeId endnodeId1 = mock(NodeId.class);
			List<EndNode> endNodes = new ArrayList<EndNode>(3);
            EndNode endNode1 = mock(EndNode.class);
			EndNode endNode2 = mock(EndNode.class);
            endNodes.add(endNode1);
			endNodes.add(endNode2);
            when(connection.getEndNode()).thenReturn(endNodes);
            when(endNodes.get(0).getNodeId()).thenReturn(endnodeId1);
            NodeId nodeId1 = endnodeId1;
            when(node1.getNodeId()).thenReturn(nodeId1);
			NodeId endnodeId2 = mock(NodeId.class);
            when(endNodes.get(1).getNodeId()).thenReturn(endnodeId2);
			NodeId nodeId2 = endnodeId2;
            when(node2.getNodeId()).thenReturn(nodeId2);
            
            NodeType externalGroupNodeTypetest = new NodeType("ext-group");
			NodeType cacheNodeTypetest = new NodeType("cache");
            when(node1.getNodeType()).thenReturn(externalGroupNodeTypetest);
            when(node2.getNodeType()).thenReturn(cacheNodeTypetest);

            when(nodeId2.getValue()).thenReturn("bbb");
            ConnectionId connectionId = mock(ConnectionId.class);
            when(connection.getConnectionId()).thenReturn(connectionId);
            when(connectionId.getValue()).thenReturn("ext");
            connectionMapper.resolveConnection(user,connection,virtualNetwork,userIntentVnMapping);
        }
        catch(IntentResolutionException excepted){
            Assert.assertEquals("The end-node bbb of the connection ext can not be cache type.",excepted.getMessage());
        }
		
		//branch endNode1.getNodeType().equals(externalGroupNodeType) checkExternalLayer3Group 
        //endNode2.getNodeType().equals(layer2GroupNodeType)
		//intentVnMappingResult1 = null
        try{
            ConnectionType connectionTypetest2 = new ConnectionType("p2p");
            when(connection.getConnectionType()).thenReturn(connectionTypetest2);
            List<Node> nodes = new ArrayList<Node>(3);
			Node node1 = mock(Node.class);
			Node node2 = mock(Node.class);
            nodes.add(node1);
			nodes.add(node2);
            Objects objects = mock(Objects.class);
            when(user.getObjects()).thenReturn(objects);
            when(objects.getNode()).thenReturn(nodes);
            NodeId endnodeId1 = mock(NodeId.class);
			List<EndNode> endNodes = new ArrayList<EndNode>(3);
            EndNode endNode1 = mock(EndNode.class);
			EndNode endNode2 = mock(EndNode.class);
            endNodes.add(endNode1);
			endNodes.add(endNode2);
            when(connection.getEndNode()).thenReturn(endNodes);
            when(endNodes.get(0).getNodeId()).thenReturn(endnodeId1);
            NodeId nodeId1 = endnodeId1;
            when(node1.getNodeId()).thenReturn(nodeId1);
			NodeId endnodeId2 = mock(NodeId.class);
            when(endNodes.get(1).getNodeId()).thenReturn(endnodeId2);
			NodeId nodeId2 = endnodeId2;
            when(node2.getNodeId()).thenReturn(nodeId2);

			NodeType externalGroupNodeTypetest = new NodeType("ext-group");
            when(node1.getNodeType()).thenReturn(externalGroupNodeTypetest);
			NodeType layer2GroupNodeTypetest = new NodeType("l2-group");
            when(node2.getNodeType()).thenReturn(layer2GroupNodeTypetest);
			Property property = mock(Property.class);
            List<Property> propertys = new ArrayList<Property>(3);
            propertys.add(property);
            when(node1.getProperty()).thenReturn(propertys);
            PropertyName propertyNametest = new PropertyName("ac-info-network");
            when(property.getPropertyName()).thenReturn(propertyNametest);
            PropertyValues propertyValues = mock(PropertyValues.class);
            when(property.getPropertyValues()).thenReturn(propertyValues);
            List<StringValue> stringValues = new ArrayList<StringValue>(3);
            StringValue stringValue = mock(StringValue.class);
            stringValues.add(stringValue);
            when(propertyValues.getStringValue()).thenReturn(stringValues);
            when(stringValues.get(0).getValue()).thenReturn("layer3");
			
			List<IntentVnMappingResult> intentVnMappingResults = new ArrayList<IntentVnMappingResult>(3);
            IntentVnMappingResult intentVnMappingResult1 = mock(IntentVnMappingResult.class);
            IntentVnMappingResult intentVnMappingResult2 = mock(IntentVnMappingResult.class);
            intentVnMappingResults.add(intentVnMappingResult1);
            intentVnMappingResults.add(intentVnMappingResult2);
            when(userIntentVnMapping.getIntentVnMappingResult()).thenReturn(intentVnMappingResults);
            when(node2.getNodeId()).thenReturn(nodeId2);
            when(nodeId2.getValue()).thenReturn("22222222-2222-2222-2222-222222222222");
            IntentId intentId1 = new IntentId("00000000-0000-0000-0000-000000000000");
			IntentId intentId2 = new IntentId("00000000-0000-0000-0000-000000000000");
            when(intentVnMappingResult1.getIntentId()).thenReturn(intentId1);
            when(intentVnMappingResult2.getIntentId()).thenReturn(intentId2);
			
            connectionMapper.resolveConnection(user,connection,virtualNetwork,userIntentVnMapping);
        }
        catch(IntentResolutionException excepted){
            Assert.assertEquals("Can not get the intent-vn mapping result for the node 22222222-2222-2222-2222-222222222222.",excepted.getMessage());
        }
		
		//branch endNode1.getNodeType().equals(externalGroupNodeType) checkExternalLayer3Group 
        //endNode2.getNodeType().equals(layer2GroupNodeType)
		//intentVnMappingResult2 = null
        try{
            ConnectionType connectionTypetest2 = new ConnectionType("p2p");
            when(connection.getConnectionType()).thenReturn(connectionTypetest2);
            List<Node> nodes = new ArrayList<Node>(3);
			Node node1 = mock(Node.class);
			Node node2 = mock(Node.class);
            nodes.add(node1);
			nodes.add(node2);
            Objects objects = mock(Objects.class);
            when(user.getObjects()).thenReturn(objects);
            when(objects.getNode()).thenReturn(nodes);
            NodeId endnodeId1 = mock(NodeId.class);
			List<EndNode> endNodes = new ArrayList<EndNode>(3);
            EndNode endNode1 = mock(EndNode.class);
			EndNode endNode2 = mock(EndNode.class);
            endNodes.add(endNode1);
			endNodes.add(endNode2);
            when(connection.getEndNode()).thenReturn(endNodes);
            when(endNodes.get(0).getNodeId()).thenReturn(endnodeId1);
            NodeId nodeId1 = endnodeId1;
            when(node1.getNodeId()).thenReturn(nodeId1);
			NodeId endnodeId2 = mock(NodeId.class);
            when(endNodes.get(1).getNodeId()).thenReturn(endnodeId2);
			NodeId nodeId2 = endnodeId2;
            when(node2.getNodeId()).thenReturn(nodeId2);

			NodeType externalGroupNodeTypetest = new NodeType("ext-group");
            when(node1.getNodeType()).thenReturn(externalGroupNodeTypetest);
			NodeType layer2GroupNodeTypetest = new NodeType("l2-group");
            when(node2.getNodeType()).thenReturn(layer2GroupNodeTypetest);
			Property property = mock(Property.class);
            List<Property> propertys = new ArrayList<Property>(3);
            propertys.add(property);
            when(node1.getProperty()).thenReturn(propertys);
            PropertyName propertyNametest = new PropertyName("ac-info-network");
            when(property.getPropertyName()).thenReturn(propertyNametest);
            PropertyValues propertyValues = mock(PropertyValues.class);
            when(property.getPropertyValues()).thenReturn(propertyValues);
            List<StringValue> stringValues = new ArrayList<StringValue>(3);
            StringValue stringValue = mock(StringValue.class);
            stringValues.add(stringValue);
            when(propertyValues.getStringValue()).thenReturn(stringValues);
            when(stringValues.get(0).getValue()).thenReturn("layer3");
			
            List<IntentVnMappingResult> intentVnMappingResults = new ArrayList<IntentVnMappingResult>(3);
            IntentVnMappingResult intentVnMappingResult1 = mock(IntentVnMappingResult.class);
            IntentVnMappingResult intentVnMappingResult2 = mock(IntentVnMappingResult.class);
            intentVnMappingResults.add(intentVnMappingResult1);
            intentVnMappingResults.add(intentVnMappingResult2);
            when(userIntentVnMapping.getIntentVnMappingResult()).thenReturn(intentVnMappingResults);
            when(node2.getNodeId()).thenReturn(nodeId2);
            when(nodeId2.getValue()).thenReturn("22222222-2222-2222-2222-222222222222");
            IntentId intentId1 = new IntentId("22222222-2222-2222-2222-222222222222");
			IntentId intentId2 = new IntentId("00000000-0000-0000-0000-000000000000");
            when(intentVnMappingResult1.getIntentId()).thenReturn(intentId1);
			when(node1.getNodeId()).thenReturn(nodeId1);
            when(nodeId1.getValue()).thenReturn("11111111-1111-1111-1111-111111111111");
            when(intentVnMappingResult2.getIntentId()).thenReturn(intentId2);

            connectionMapper.resolveConnection(user,connection,virtualNetwork,userIntentVnMapping);
        }
        catch(IntentResolutionException excepted){
            Assert.assertEquals("Can not get the intent-vn mapping result for the node 11111111-1111-1111-1111-111111111111.",excepted.getMessage());
        }
		
		//branch endNode1.getNodeType().equals(externalGroupNodeType) checkExternalLayer3Group 
        //endNode2.getNodeType().equals(layer2GroupNodeType)
		//virtualRouter1 = null
        try{
            ConnectionType connectionTypetest2 = new ConnectionType("p2p");
            when(connection.getConnectionType()).thenReturn(connectionTypetest2);
            List<Node> nodes = new ArrayList<Node>(3);
			Node node1 = mock(Node.class);
			Node node2 = mock(Node.class);
            nodes.add(node1);
			nodes.add(node2);
            Objects objects = mock(Objects.class);
            when(user.getObjects()).thenReturn(objects);
            when(objects.getNode()).thenReturn(nodes);
            NodeId endnodeId1 = mock(NodeId.class);
			List<EndNode> endNodes = new ArrayList<EndNode>(3);
            EndNode endNode1 = mock(EndNode.class);
			EndNode endNode2 = mock(EndNode.class);
            endNodes.add(endNode1);
			endNodes.add(endNode2);
            when(connection.getEndNode()).thenReturn(endNodes);
            when(endNodes.get(0).getNodeId()).thenReturn(endnodeId1);
            NodeId nodeId1 = endnodeId1;
            when(node1.getNodeId()).thenReturn(nodeId1);
			NodeId endnodeId2 = mock(NodeId.class);
            when(endNodes.get(1).getNodeId()).thenReturn(endnodeId2);
			NodeId nodeId2 = endnodeId2;
            when(node2.getNodeId()).thenReturn(nodeId2);

			NodeType externalGroupNodeTypetest = new NodeType("ext-group");
            when(node1.getNodeType()).thenReturn(externalGroupNodeTypetest);
			NodeType layer2GroupNodeTypetest = new NodeType("l2-group");
            when(node2.getNodeType()).thenReturn(layer2GroupNodeTypetest);
			Property property = mock(Property.class);
            List<Property> propertys = new ArrayList<Property>(3);
            propertys.add(property);
            when(node1.getProperty()).thenReturn(propertys);
            PropertyName propertyNametest = new PropertyName("ac-info-network");
            when(property.getPropertyName()).thenReturn(propertyNametest);
            PropertyValues propertyValues = mock(PropertyValues.class);
            when(property.getPropertyValues()).thenReturn(propertyValues);
            List<StringValue> stringValues = new ArrayList<StringValue>(3);
            StringValue stringValue = mock(StringValue.class);
            stringValues.add(stringValue);
            when(propertyValues.getStringValue()).thenReturn(stringValues);
            when(stringValues.get(0).getValue()).thenReturn("layer3");
			
            List<IntentVnMappingResult> intentVnMappingResults = new ArrayList<IntentVnMappingResult>(3);
            IntentVnMappingResult intentVnMappingResult1 = mock(IntentVnMappingResult.class);
            IntentVnMappingResult intentVnMappingResult2 = mock(IntentVnMappingResult.class);
            intentVnMappingResults.add(intentVnMappingResult1);
            intentVnMappingResults.add(intentVnMappingResult2);
            when(userIntentVnMapping.getIntentVnMappingResult()).thenReturn(intentVnMappingResults);
            when(node2.getNodeId()).thenReturn(nodeId2);
            when(nodeId2.getValue()).thenReturn("22222222-2222-2222-2222-222222222222");
            IntentId intentId1 = new IntentId("22222222-2222-2222-2222-222222222222");
			IntentId intentId2 = new IntentId("11111111-1111-1111-1111-111111111111");
            when(intentVnMappingResult1.getIntentId()).thenReturn(intentId1);
			when(node1.getNodeId()).thenReturn(nodeId1);
            when(nodeId1.getValue()).thenReturn("11111111-1111-1111-1111-111111111111");
            when(intentVnMappingResult2.getIntentId()).thenReturn(intentId2);

            List<VirtualNode> virtualNodestest = new ArrayList<VirtualNode>(3);
            VirtualNode virtualNode1 = mock(VirtualNode.class);
			VirtualNode virtualNode2 = mock(VirtualNode.class);
            virtualNodestest.add(virtualNode1);
			virtualNodestest.add(virtualNode2);
            VirtualNodes virtualNodes = mock(VirtualNodes.class);
            when(virtualNetwork.getVirtualNodes()).thenReturn(virtualNodes);
            when(virtualNodes.getVirtualNode()).thenReturn(virtualNodestest);
			
            List<VirtualResource> virtualResources = new ArrayList<VirtualResource>(3);
            VirtualResource virtualResource = mock(VirtualResource.class);
            virtualResources.add(virtualResource);
            when(intentVnMappingResult1.getVirtualResource()).thenReturn(virtualResources);
            VirtualResourceEntityId virtualResourceEntityId = mock(VirtualResourceEntityId.class);
            when(virtualResource.getVirtualResourceEntityId()).thenReturn(virtualResourceEntityId);
            when(virtualResourceEntityId.getValue()).thenReturn("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
            
			VirtualNodeId virtualNodeId1 = new VirtualNodeId("00000000-0000-0000-0000-000000000000");
			VirtualNodeId virtualNodeId2 = new VirtualNodeId("00000000-0000-0000-0000-000000000000");
            when(virtualNode1.getNodeId()).thenReturn(virtualNodeId1);
			when(virtualNode2.getNodeId()).thenReturn(virtualNodeId2);
			
            connectionMapper.resolveConnection(user,connection,virtualNetwork,userIntentVnMapping);
        }
        catch(IntentResolutionException excepted){
            Assert.assertEquals("Can not get the virtual node created for the node 22222222-2222-2222-2222-222222222222.",excepted.getMessage());
        }
		
		//branch endNode1.getNodeType().equals(externalGroupNodeType) checkExternalLayer3Group 
        //endNode2.getNodeType().equals(layer3GroupNodeType)
        try{
            ConnectionType connectionTypetest2 = new ConnectionType("p2p");
            when(connection.getConnectionType()).thenReturn(connectionTypetest2);
            List<Node> nodes = new ArrayList<Node>(3);
			Node node1 = mock(Node.class);
			Node node2 = mock(Node.class);
            nodes.add(node1);
			nodes.add(node2);
            Objects objects = mock(Objects.class);
            when(user.getObjects()).thenReturn(objects);
            when(objects.getNode()).thenReturn(nodes);
            NodeId endnodeId1 = mock(NodeId.class);
			List<EndNode> endNodes = new ArrayList<EndNode>(3);
            EndNode endNode1 = mock(EndNode.class);
			EndNode endNode2 = mock(EndNode.class);
            endNodes.add(endNode1);
			endNodes.add(endNode2);
            when(connection.getEndNode()).thenReturn(endNodes);
            when(endNodes.get(0).getNodeId()).thenReturn(endnodeId1);
            NodeId nodeId1 = endnodeId1;
            when(node1.getNodeId()).thenReturn(nodeId1);
			NodeId endnodeId2 = mock(NodeId.class);
            when(endNodes.get(1).getNodeId()).thenReturn(endnodeId2);
			NodeId nodeId2 = endnodeId2;
            when(node2.getNodeId()).thenReturn(nodeId2);

			NodeType externalGroupNodeTypetest = new NodeType("ext-group");
            when(node1.getNodeType()).thenReturn(externalGroupNodeTypetest);
			NodeType layer3GroupNodeTypetest = new NodeType("l3-group");
            when(node2.getNodeType()).thenReturn(layer3GroupNodeTypetest);
			Property property = mock(Property.class);
            List<Property> propertys = new ArrayList<Property>(3);
            propertys.add(property);
            when(node1.getProperty()).thenReturn(propertys);
            PropertyName propertyNametest = new PropertyName("ac-info-network");
            when(property.getPropertyName()).thenReturn(propertyNametest);
            PropertyValues propertyValues = mock(PropertyValues.class);
            when(property.getPropertyValues()).thenReturn(propertyValues);
            List<StringValue> stringValues = new ArrayList<StringValue>(3);
            StringValue stringValue = mock(StringValue.class);
            stringValues.add(stringValue);
            when(propertyValues.getStringValue()).thenReturn(stringValues);
            when(stringValues.get(0).getValue()).thenReturn("layer3");
				
            connectionMapper.resolveConnection(user,connection,virtualNetwork,userIntentVnMapping);
        }
        catch(IntentResolutionException excepted){
            Assert.assertNull(excepted.getMessage());
        }
		
		//branch endNode1.getNodeType().equals(externalGroupNodeTypetest) checkExternalLayer3Group
		//endNode2.getNodeType().equals(externalGroupNodeType) checkExternalLayer3Group false
		//intentVnMappingResult1 = null
        try{
            ConnectionType connectionTypetest2 = new ConnectionType("p2p");
            when(connection.getConnectionType()).thenReturn(connectionTypetest2);
            List<Node> nodes = new ArrayList<Node>(3);
			Node node1 = mock(Node.class);
			Node node2 = mock(Node.class);
            nodes.add(node1);
			nodes.add(node2);
            Objects objects = mock(Objects.class);
            when(user.getObjects()).thenReturn(objects);
            when(objects.getNode()).thenReturn(nodes);
            NodeId endnodeId1 = mock(NodeId.class);
			List<EndNode> endNodes = new ArrayList<EndNode>(3);
            EndNode endNode1 = mock(EndNode.class);
			EndNode endNode2 = mock(EndNode.class);
            endNodes.add(endNode1);
			endNodes.add(endNode2);
            when(connection.getEndNode()).thenReturn(endNodes);
            when(endNodes.get(0).getNodeId()).thenReturn(endnodeId1);
            NodeId nodeId1 = endnodeId1;
            when(node1.getNodeId()).thenReturn(nodeId1);
			NodeId endnodeId2 = mock(NodeId.class);
            when(endNodes.get(1).getNodeId()).thenReturn(endnodeId2);
			NodeId nodeId2 = endnodeId2;
            when(node2.getNodeId()).thenReturn(nodeId2);

			NodeType externalGroupNodeTypetest = new NodeType("ext-group");
            when(node1.getNodeType()).thenReturn(externalGroupNodeTypetest);
            when(node2.getNodeType()).thenReturn(externalGroupNodeTypetest);
			Property property = mock(Property.class);
            List<Property> propertys = new ArrayList<Property>(3);
            propertys.add(property);
            when(node1.getProperty()).thenReturn(propertys);
            PropertyName propertyNametest = new PropertyName("ac-info-network");
            when(property.getPropertyName()).thenReturn(propertyNametest);
            PropertyValues propertyValues = mock(PropertyValues.class);
            when(property.getPropertyValues()).thenReturn(propertyValues);
            List<StringValue> stringValues = new ArrayList<StringValue>(3);
            StringValue stringValue = mock(StringValue.class);
            stringValues.add(stringValue);
            when(propertyValues.getStringValue()).thenReturn(stringValues);
            when(stringValues.get(0).getValue()).thenReturn("layer3");
            when(node2.getProperty()).thenReturn(propertys);
            when(property.getPropertyName()).thenReturn(propertyNametest);
            when(property.getPropertyValues()).thenReturn(propertyValues);
            when(propertyValues.getStringValue()).thenReturn(stringValues);
            when(stringValues.get(0).getValue()).thenReturn("layer3");
			
            List<IntentVnMappingResult> intentVnMappingResults = new ArrayList<IntentVnMappingResult>(3);
            IntentVnMappingResult intentVnMappingResult1 = mock(IntentVnMappingResult.class);
            IntentVnMappingResult intentVnMappingResult2 = mock(IntentVnMappingResult.class);
            intentVnMappingResults.add(intentVnMappingResult1);
            intentVnMappingResults.add(intentVnMappingResult2);
            when(userIntentVnMapping.getIntentVnMappingResult()).thenReturn(intentVnMappingResults);
            when(node1.getNodeId()).thenReturn(nodeId1);
            when(nodeId1.getValue()).thenReturn("11111111-1111-1111-1111-111111111111");
            IntentId intentId1 = new IntentId("00000000-0000-0000-0000-000000000000");
			IntentId intentId2 = new IntentId("00000000-0000-0000-0000-000000000000");
            when(intentVnMappingResult1.getIntentId()).thenReturn(intentId1);
            when(intentVnMappingResult2.getIntentId()).thenReturn(intentId2);
			
            connectionMapper.resolveConnection(user,connection,virtualNetwork,userIntentVnMapping);
        }
        catch(IntentResolutionException excepted){
            Assert.assertEquals("Can not get the intent-vn mapping result for the node 11111111-1111-1111-1111-111111111111.",excepted.getMessage());
        }
		
		//branch endNode1.getNodeType().equals(externalGroupNodeTypetest) checkExternalLayer3Group
		//endNode2.getNodeType().equals(externalGroupNodeType) checkExternalLayer3Group false
		//intentVnMappingResult2 = null
        try{
            ConnectionType connectionTypetest2 = new ConnectionType("p2p");
            when(connection.getConnectionType()).thenReturn(connectionTypetest2);
            List<Node> nodes = new ArrayList<Node>(3);
			Node node1 = mock(Node.class);
			Node node2 = mock(Node.class);
            nodes.add(node1);
			nodes.add(node2);
            Objects objects = mock(Objects.class);
            when(user.getObjects()).thenReturn(objects);
            when(objects.getNode()).thenReturn(nodes);
            NodeId endnodeId1 = mock(NodeId.class);
			List<EndNode> endNodes = new ArrayList<EndNode>(3);
            EndNode endNode1 = mock(EndNode.class);
			EndNode endNode2 = mock(EndNode.class);
            endNodes.add(endNode1);
			endNodes.add(endNode2);
            when(connection.getEndNode()).thenReturn(endNodes);
            when(endNodes.get(0).getNodeId()).thenReturn(endnodeId1);
            NodeId nodeId1 = endnodeId1;
            when(node1.getNodeId()).thenReturn(nodeId1);
			NodeId endnodeId2 = mock(NodeId.class);
            when(endNodes.get(1).getNodeId()).thenReturn(endnodeId2);
			NodeId nodeId2 = endnodeId2;
            when(node2.getNodeId()).thenReturn(nodeId2);

			NodeType externalGroupNodeTypetest = new NodeType("ext-group");
            when(node1.getNodeType()).thenReturn(externalGroupNodeTypetest);
            when(node2.getNodeType()).thenReturn(externalGroupNodeTypetest);
			Property property = mock(Property.class);
            List<Property> propertys = new ArrayList<Property>(3);
            propertys.add(property);
            when(node1.getProperty()).thenReturn(propertys);
            PropertyName propertyNametest = new PropertyName("ac-info-network");
            when(property.getPropertyName()).thenReturn(propertyNametest);
            PropertyValues propertyValues = mock(PropertyValues.class);
            when(property.getPropertyValues()).thenReturn(propertyValues);
            List<StringValue> stringValues = new ArrayList<StringValue>(3);
            StringValue stringValue = mock(StringValue.class);
            stringValues.add(stringValue);
            when(propertyValues.getStringValue()).thenReturn(stringValues);
            when(stringValues.get(0).getValue()).thenReturn("layer3");
            when(node2.getProperty()).thenReturn(propertys);
            when(property.getPropertyName()).thenReturn(propertyNametest);
            when(property.getPropertyValues()).thenReturn(propertyValues);
            when(propertyValues.getStringValue()).thenReturn(stringValues);
            when(stringValues.get(0).getValue()).thenReturn("layer3");
			
            List<IntentVnMappingResult> intentVnMappingResults = new ArrayList<IntentVnMappingResult>(3);
            IntentVnMappingResult intentVnMappingResult1 = mock(IntentVnMappingResult.class);
            IntentVnMappingResult intentVnMappingResult2 = mock(IntentVnMappingResult.class);
            intentVnMappingResults.add(intentVnMappingResult1);
            intentVnMappingResults.add(intentVnMappingResult2);
            when(userIntentVnMapping.getIntentVnMappingResult()).thenReturn(intentVnMappingResults);
            when(node1.getNodeId()).thenReturn(nodeId1);
            when(nodeId1.getValue()).thenReturn("11111111-1111-1111-1111-111111111111");
            IntentId intentId1 = new IntentId("11111111-1111-1111-1111-111111111111");
			IntentId intentId2 = new IntentId("00000000-0000-0000-0000-000000000000");
            when(intentVnMappingResult1.getIntentId()).thenReturn(intentId1);
			when(node2.getNodeId()).thenReturn(nodeId2);
            when(nodeId2.getValue()).thenReturn("22222222-2222-2222-2222-222222222222");
            when(intentVnMappingResult2.getIntentId()).thenReturn(intentId2);

            connectionMapper.resolveConnection(user,connection,virtualNetwork,userIntentVnMapping);
        }
        catch(IntentResolutionException excepted){
            Assert.assertEquals("Can not get the intent-vn mapping result for the node 22222222-2222-2222-2222-222222222222.",excepted.getMessage());
        }
		
		//branch endNode1.getNodeType().equals(externalGroupNodeTypetest) checkExternalLayer3Group
		//endNode2.getNodeType().equals(externalGroupNodeType) checkExternalLayer3Group false
		//virtualRouter1 = null
        try{
            ConnectionType connectionTypetest2 = new ConnectionType("p2p");
            when(connection.getConnectionType()).thenReturn(connectionTypetest2);
            List<Node> nodes = new ArrayList<Node>(3);
			Node node1 = mock(Node.class);
			Node node2 = mock(Node.class);
            nodes.add(node1);
			nodes.add(node2);
            Objects objects = mock(Objects.class);
            when(user.getObjects()).thenReturn(objects);
            when(objects.getNode()).thenReturn(nodes);
            NodeId endnodeId1 = mock(NodeId.class);
			List<EndNode> endNodes = new ArrayList<EndNode>(3);
            EndNode endNode1 = mock(EndNode.class);
			EndNode endNode2 = mock(EndNode.class);
            endNodes.add(endNode1);
			endNodes.add(endNode2);
            when(connection.getEndNode()).thenReturn(endNodes);
            when(endNodes.get(0).getNodeId()).thenReturn(endnodeId1);
            NodeId nodeId1 = endnodeId1;
            when(node1.getNodeId()).thenReturn(nodeId1);
			NodeId endnodeId2 = mock(NodeId.class);
            when(endNodes.get(1).getNodeId()).thenReturn(endnodeId2);
			NodeId nodeId2 = endnodeId2;
            when(node2.getNodeId()).thenReturn(nodeId2);

			NodeType externalGroupNodeTypetest = new NodeType("ext-group");
            when(node1.getNodeType()).thenReturn(externalGroupNodeTypetest);
            when(node2.getNodeType()).thenReturn(externalGroupNodeTypetest);
			Property property = mock(Property.class);
            List<Property> propertys = new ArrayList<Property>(3);
            propertys.add(property);
            when(node1.getProperty()).thenReturn(propertys);
            PropertyName propertyNametest = new PropertyName("ac-info-network");
            when(property.getPropertyName()).thenReturn(propertyNametest);
            PropertyValues propertyValues = mock(PropertyValues.class);
            when(property.getPropertyValues()).thenReturn(propertyValues);
            List<StringValue> stringValues = new ArrayList<StringValue>(3);
            StringValue stringValue = mock(StringValue.class);
            stringValues.add(stringValue);
            when(propertyValues.getStringValue()).thenReturn(stringValues);
            when(stringValues.get(0).getValue()).thenReturn("layer3");
            when(node2.getProperty()).thenReturn(propertys);
            when(property.getPropertyName()).thenReturn(propertyNametest);
            when(property.getPropertyValues()).thenReturn(propertyValues);
            when(propertyValues.getStringValue()).thenReturn(stringValues);
            when(stringValues.get(0).getValue()).thenReturn("layer3");
			
            List<IntentVnMappingResult> intentVnMappingResults = new ArrayList<IntentVnMappingResult>(3);
            IntentVnMappingResult intentVnMappingResult1 = mock(IntentVnMappingResult.class);
            IntentVnMappingResult intentVnMappingResult2 = mock(IntentVnMappingResult.class);
            intentVnMappingResults.add(intentVnMappingResult1);
            intentVnMappingResults.add(intentVnMappingResult2);
            when(userIntentVnMapping.getIntentVnMappingResult()).thenReturn(intentVnMappingResults);
            when(node1.getNodeId()).thenReturn(nodeId1);
            when(nodeId1.getValue()).thenReturn("11111111-1111-1111-1111-111111111111");
            IntentId intentId1 = new IntentId("11111111-1111-1111-1111-111111111111");
			IntentId intentId2 = new IntentId("22222222-2222-2222-2222-222222222222");
            when(intentVnMappingResult1.getIntentId()).thenReturn(intentId1);
			when(node2.getNodeId()).thenReturn(nodeId2);
            when(nodeId2.getValue()).thenReturn("22222222-2222-2222-2222-222222222222");
            when(intentVnMappingResult2.getIntentId()).thenReturn(intentId2);

            List<VirtualNode> virtualNodestest = new ArrayList<VirtualNode>(3);
            VirtualNode virtualNode1 = mock(VirtualNode.class);
			VirtualNode virtualNode2 = mock(VirtualNode.class);
            virtualNodestest.add(virtualNode1);
			virtualNodestest.add(virtualNode2);
            VirtualNodes virtualNodes = mock(VirtualNodes.class);
            when(virtualNetwork.getVirtualNodes()).thenReturn(virtualNodes);
            when(virtualNodes.getVirtualNode()).thenReturn(virtualNodestest);
			
            List<VirtualResource> virtualResources = new ArrayList<VirtualResource>(3);
            VirtualResource virtualResource = mock(VirtualResource.class);
            virtualResources.add(virtualResource);
            when(intentVnMappingResult1.getVirtualResource()).thenReturn(virtualResources);
			VirtualResourceEntityId virtualResourceEntityId = mock(VirtualResourceEntityId.class);
            when(virtualResource.getParentVirtualResourceEntityId()).thenReturn(virtualResourceEntityId);
            when(virtualResourceEntityId.getValue()).thenReturn("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
            
			VirtualNodeId virtualNodeId1 = new VirtualNodeId("00000000-0000-0000-0000-000000000000");
			VirtualNodeId virtualNodeId2 = new VirtualNodeId("00000000-0000-0000-0000-000000000000");
            when(virtualNode1.getNodeId()).thenReturn(virtualNodeId1);
			when(virtualNode2.getNodeId()).thenReturn(virtualNodeId2);
			
            connectionMapper.resolveConnection(user,connection,virtualNetwork,userIntentVnMapping);
        }
        catch(IntentResolutionException excepted){
            Assert.assertEquals("Can not get the virtual node created for the node 11111111-1111-1111-1111-111111111111.",excepted.getMessage());
        }
		
		//branch connectionType p2mp
		try{
            ConnectionType connectionTypetest3 = new ConnectionType("p2mp");
            when(connection.getConnectionType()).thenReturn(connectionTypetest3);
            connectionMapper.resolveConnection(user,connection,virtualNetwork,userIntentVnMapping);
		}
		catch(IntentResolutionException excepted){
            Assert.assertNull(excepted.getMessage());
		}
		
		//branch connectionType mesh
		try{
            ConnectionType connectionTypetest4 = new ConnectionType("mesh");
            when(connection.getConnectionType()).thenReturn(connectionTypetest4);
            connectionMapper.resolveConnection(user,connection,virtualNetwork,userIntentVnMapping);
		}
		catch(IntentResolutionException excepted){
            Assert.assertNull(excepted.getMessage());
		}
		
	}
 }