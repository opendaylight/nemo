/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.nemo.intent;

import org.opendaylight.nemo.intent.NodeMapper;

//import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.StringValue;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.property.values.StringValue;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.PropertyValues;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.operation.instance.Action;
import org.opendaylight.yangtools.yang.binding.DataObject;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.property.values.StringValue;


import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.PropertyValues;
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

import com.google.common.base.Optional;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.ReadOnlyTransaction;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.inet.types.rev100924.IpAddress;
import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.inet.types.rev100924.IpPrefix;
import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.inet.types.rev100924.Ipv4Prefix;
import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.yang.types.rev100924.MacAddress;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.PhysicalNetwork;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.attribute.instance.AttributeValue;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.attribute.instance.AttributeValueBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.PhysicalHosts;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.VirtualNetwork;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.arps.VirtualArp;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.arps.VirtualArpBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.links.VirtualLink;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.links.VirtualLinkBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.nodes.VirtualNode;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.VirtualNodes;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.nodes.VirtualNodeBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.paths.VirtualPath;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.paths.VirtualPathBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.port.instance.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.port.instance.PhysicalResourceRequirement;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.port.instance.PhysicalResourceRequirementBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.intent.vn.mapping.results.UserIntentVnMapping;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.intent.vn.mapping.results.user.intent.vn.mapping.IntentVnMappingResult;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.intent.vn.mapping.results.user.intent.vn.mapping.IntentVnMappingResultBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.intent.vn.mapping.results.user.intent.vn.mapping.intent.vn.mapping.result.VirtualResource;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.intent.vn.mapping.results.user.intent.vn.mapping.intent.vn.mapping.result.VirtualResourceBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.IntentId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.NodeType;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.PropertyName;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Node;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.User;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.node.instance.Property;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.node.instance.SubNode;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.util.concurrent.CheckedFuture;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;


import java.util.*;
import java.util.concurrent.ExecutionException;

public class NodeMapperTest
{
	private NodeMapper nodeMapper;
	private DataBroker dataBroker;
	
	private User user;
    private Node node;
    private NodeId nodeId;
    private List<Node> nodeList;
    
	private PhysicalHost physicalHost;
	private PhysicalHosts physicalHosts;
    private PhysicalHostId physicalHostId;
    private List<PhysicalHost> physicalHostList;
    
    private VirtualNetwork virtualNetwork;
    private VirtualNode virtualNode;
    private VirtualNodes virtualNodes;
    private List<VirtualNode> virtualNodeList;
    private UserIntentVnMapping userIntentVnMapping;
    
    private Property property;
    private PropertyName propertyName;
    private PropertyValues propertyValues;
    private List<Property> propertyList;
    private StringValue stringValue;
    private List<StringValue> stringValueList;
    
	@org.junit.Before
    public void setUp() throws Exception {
		
		dataBroker=mock(DataBroker.class);
		nodeMapper=new NodeMapper(dataBroker);
		
		user = mock(User.class);
		node = mock(Node.class);
		nodeId = mock(NodeId.class);
        nodeList = new ArrayList<Node>(1);
        nodeList.add(node);
		
		physicalHost = mock(PhysicalHost.class);
		physicalHosts = mock(PhysicalHosts.class);
        physicalHostId = mock(PhysicalHostId.class);
        physicalHostList = new ArrayList<PhysicalHost>(1);
        physicalHostList.add(physicalHost);
        
        virtualNetwork = mock(VirtualNetwork.class);
        virtualNode = mock(VirtualNode.class);
        virtualNodes = mock(VirtualNodes.class);
        virtualNodeList = new ArrayList<VirtualNode>();
        virtualNodeList.add(virtualNode);
        
        userIntentVnMapping = mock(UserIntentVnMapping.class);
        
        stringValue = mock(StringValue.class);
        stringValueList = new ArrayList<StringValue>(1);
        stringValueList.add(stringValue);
        
        property = mock(Property.class);
        propertyList =  new ArrayList<Property>(1);
        propertyList.add(property);
        propertyValues = mock(PropertyValues.class);

        propertyName = mock(PropertyName.class);
        
	}
	
	@org.junit.Test
	public void resolveNodeTest() throws Exception {
        
		//nodeType = new NodeType("host")
        NodeType nodeType = new NodeType("host");
        when(node.getNodeType()).thenReturn(nodeType);
        
        CheckedFuture resultFuture = mock(CheckedFuture.class);
        ReadOnlyTransaction readOnlyTransaction = mock(ReadOnlyTransaction.class);

        when(dataBroker.newReadOnlyTransaction()).thenReturn(readOnlyTransaction);
        when(readOnlyTransaction.read(any(LogicalDatastoreType.class), any(InstanceIdentifier.class))).thenReturn(resultFuture); 
        
        Optional<PhysicalHosts> result = Optional.of(physicalHosts);
        when(resultFuture.get()).thenReturn(result); 
        
        when(node.getNodeId()).thenReturn(nodeId);
        when(node.getNodeId().getValue()).thenReturn(new String("00001111-0000-0000-0000-000011112222"));
        
        when(physicalHost.getHostId()).thenReturn(physicalHostId);

        try
    	{
        	nodeMapper.resolveNode(user, node, virtualNetwork, userIntentVnMapping);
    	}
    	catch(IntentResolutionException ex)
    	{
    		Assert.assertEquals(ex.getMessage(),"The physical host corresponding to the node " +
                    node.getNodeId().getValue() + " does not exist.");
    	}
        
        //nodeType = new NodeType("l2-group")
        NodeType nodeTypeOther = new NodeType("l2-group");
        when(node.getNodeType()).thenReturn(nodeTypeOther);
        
        
        Objects objects = mock(Objects.class);
    	when(user.getObjects()).thenReturn(objects);
    	
        when(objects.getNode()).thenReturn(nodeList);
        
        List<SubNode> subNodes = new ArrayList<SubNode>(1);
        SubNode subNode=mock(SubNode.class);
        subNodes.add(subNode);
        when(node.getSubNode()).thenReturn(subNodes);
        
        when(virtualNetwork.getVirtualNodes()).thenReturn(virtualNodes);
        when(virtualNodes.getVirtualNode()).thenReturn(virtualNodeList);
        
        when(subNode.getNodeId()).thenReturn(nodeId);
    	when(node.getNodeId()).thenReturn(nodeId);
    	
        try
    	{
        	nodeMapper.resolveNode(user, node, virtualNetwork, userIntentVnMapping);
    	}
    	catch(IntentResolutionException ex)
    	{
    		Assert.assertEquals(ex.getMessage(),"Can not get the intent-vn mapping result for the node 00001111-0000-0000-0000-000011112222.");
    	}
        
        //nodeType = new NodeType("l3-group")
        NodeType nodeType3 = new NodeType("l3-group");
        when(node.getNodeType()).thenReturn(nodeType3);
        NodeMapper nodeMapperother = mock(NodeMapper.class);
        nodeMapperother.resolveNode(user, node, virtualNetwork, userIntentVnMapping);
        verify(nodeMapperother,times(0)).resolveLayer3Group(user, node, virtualNetwork, userIntentVnMapping);
        
        //nodeType = new NodeType("ext-group")
        NodeType nodeType4 = new NodeType("ext-group");
        when(node.getNodeType()).thenReturn(nodeType4);
        
        when(node.getProperty()).thenReturn(propertyList);
        propertyName = new PropertyName("ac-info-network");
        when(property.getPropertyName()).thenReturn(propertyName);
        when(property.getPropertyValues()).thenReturn(propertyValues);
        when(propertyValues.getStringValue()).thenReturn(stringValueList);
        when(property.getPropertyValues().getStringValue().get(0).getValue()).thenReturn(new String("layer3"));
        
        try
    	{
        	nodeMapper.resolveNode(user, node, virtualNetwork, userIntentVnMapping);
    	}
    	catch(IntentResolutionException ex)
    	{
    		Assert.assertEquals(ex.getMessage(),"Can not get the location property of the node 00001111-0000-0000-0000-000011112222.");
    	}
        
        
        //nodeType = new NodeType("chain-group")
        NodeType nodeType5 = new NodeType("chain-group");
        when(node.getNodeType()).thenReturn(nodeType5);
        
        try
    	{
        	nodeMapper.resolveNode(user, node, virtualNetwork, userIntentVnMapping);
    	}
    	catch(IntentResolutionException ex)
    	{
    		Assert.assertEquals(ex.getMessage(),"Can not get the intent-vn mapping result for the node 00001111-0000-0000-0000-000011112222.");
    	}
        
        //nodeType = nodeType.equals(new NodeType("fw"))
        //|| nodeType.equals(new NodeType("lb"))
        //|| nodeType.equals(new NodeType("cache")) 
        NodeType nodeType6 = new NodeType("fw");
        when(node.getNodeType()).thenReturn(nodeType6);
        
        try
    	{
        	nodeMapper.resolveNode(user, node, virtualNetwork, userIntentVnMapping);
    	}
    	catch(IntentResolutionException ex)
    	{
    		Assert.assertEquals(ex.getMessage(),"Can not get the location property of the node 00001111-0000-0000-0000-000011112222.");
    	}
        
	}
	
	
	@org.junit.Test
	public void resolveHostTest() throws Exception {
		
		
	}
	
	@org.junit.Test
	public void resolveLayer2GroupTest() throws Exception {
		
		
	}
	
	@org.junit.Test
	public void resolveLayer3GroupTest() throws Exception {
		
		
	}
	
	@org.junit.Test
	public void resolveExternalLayer2GroupTest() throws Exception {
		
		
	}
	
	@org.junit.Test
	public void resolveExternalLayer3GroupTest() throws Exception {
		
		
	}
	
	@org.junit.Test
	public void resolveServiceChainGroupTest() throws Exception {
		
		
	}
	
	@org.junit.Test
	public void resolveServiceFunctionTest() throws Exception {
		
		
	}
	
}
