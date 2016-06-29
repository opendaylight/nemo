/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.intent;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import com.google.common.base.Optional;
import com.google.common.util.concurrent.CheckedFuture;
import org.junit.runner.RunWith;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.ReadOnlyTransaction;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.PhysicalPortId;
import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.inet.types.rev130715.IpAddress;
import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.inet.types.rev130715.IpPrefix;
import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.inet.types.rev130715.Ipv4Prefix;
import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.yang.types.rev130715.MacAddress;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.host.instance.IpAddresses;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.VirtualNodes;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.PhysicalNodeId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.PhysicalNetwork;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.attribute.instance.AttributeValue;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.attribute.instance.AttributeValueBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.PhysicalHosts;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.hosts.PhysicalHost;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.VirtualNetwork;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.arps.VirtualArp;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.arps.VirtualArpBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.links.VirtualLink;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.links.VirtualLinkBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.nodes.VirtualNode;
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
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.VirtualArps;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.PropertyName;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Node;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.Objects;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.User;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.node.instance.Property;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.node.instance.SubNode;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.property.values.StringValue;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.VirtualPaths;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.VirtualPort;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.VirtualLinks;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.NodeId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.PropertyValues;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.VirtualResourceEntityId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.VirtualNodeId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.support.membermodification.MemberMatcher;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
/**
 * Created by zhangmeng on 2015/12/22.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(IntentResolverUtils.class)
public class NodeMapperTest extends TestCase {
    private DataBroker dataBroker ;
    private NodeMapper nodeMapper ;
    @Before
    public void setUp() throws Exception {
        dataBroker = mock(DataBroker.class);
        nodeMapper = new NodeMapper(dataBroker);
    }

    @Test
    public void testResolveNode() throws Exception {
        User user = mock(User.class);
        Node node = mock(Node.class);
        VirtualNetwork virtualNetwork = mock(VirtualNetwork.class);
        UserIntentVnMapping userIntentVnMapping = mock(UserIntentVnMapping.class);
        NodeType nodeType = mock(NodeType.class);

        when(node.getNodeType())
                .thenReturn(new NodeType("host"));
        //get into method "resolveHost" args(user,node,virtualNetwork,userIntentVnMapping)
        VirtualNodes virtualNodes = mock(VirtualNodes.class);
        VirtualNode virtualNode = mock(VirtualNode.class);
        IpAddress ipAddress = mock(IpAddress.class);
        IpAddresses ipAddresses = mock(IpAddresses.class);
        MacAddress macAddress = mock(MacAddress.class);
        PhysicalPortId physicalPortId = mock(PhysicalPortId.class);
        ReadOnlyTransaction readOnlyTransaction = mock(ReadOnlyTransaction.class);
        PhysicalNodeId physicalNodeId = mock(PhysicalNodeId.class);
        PhysicalHost physicalHost = mock(PhysicalHost.class);
        IntentVnMappingResult intentVnMappingResult = mock(IntentVnMappingResult.class);
        PhysicalHosts physicalHosts_resolveHost = mock(PhysicalHosts.class);
        VirtualArps virtualArps = mock(VirtualArps.class);
        NodeId nodeId = mock(NodeId.class);
        Optional<PhysicalHosts> result_resolveHost = Optional.of(physicalHosts_resolveHost);
        CheckedFuture checkedFuture = mock(CheckedFuture.class);
        List<PhysicalHost> physicalHostList_resolveHost = new ArrayList<PhysicalHost>();
        List<VirtualNode> virtualNodeList_resolveHost = new ArrayList<VirtualNode>();
        List<IpAddress> ipAddressList = new ArrayList<IpAddress>();
        List<IntentVnMappingResult> intentVnMappingResultList = new ArrayList<IntentVnMappingResult>();

//        virtualNodeList_resolveHost.add(virtualNode);
        ipAddressList.add(ipAddress);

        when(dataBroker.newReadOnlyTransaction()).thenReturn(readOnlyTransaction);
        when(readOnlyTransaction.read(any(LogicalDatastoreType.class), any(InstanceIdentifier.class))).thenReturn(checkedFuture);
        when(checkedFuture.get()).thenReturn(result_resolveHost);
        when(physicalHosts_resolveHost.getPhysicalHost()).thenReturn(physicalHostList_resolveHost);
            //powermock
        PowerMockito.mockStatic(IntentResolverUtils.class);
        PowerMockito.when(IntentResolverUtils.getPhysicalHost(physicalHostList_resolveHost, node)).thenReturn(physicalHost);
        Assert.assertTrue(physicalHost != null);
        when(physicalHost.getNodeId()).thenReturn(physicalNodeId);
        when(physicalNodeId.getValue()).thenReturn(new String("00001111-0000-0000-0000-000011112222"));
        when(virtualNetwork.getVirtualNodes()).thenReturn(virtualNodes);
        when(virtualNodes.getVirtualNode()).thenReturn(virtualNodeList_resolveHost);
        when(virtualNetwork.getVirtualArps()).thenReturn(virtualArps);
        when(virtualArps.getVirtualArp()).thenReturn(new ArrayList<VirtualArp>());
        when(physicalHost.getMacAddress()).thenReturn(macAddress);
        when(physicalHost.getPortId()).thenReturn(physicalPortId);
        when(physicalPortId.getValue()).thenReturn(new String("portid"));
        when(physicalHost.getIpAddresses()).thenReturn(ipAddresses);
        when(ipAddresses.getIpAddress()).thenReturn(ipAddressList);
        when(userIntentVnMapping.getIntentVnMappingResult()).thenReturn(intentVnMappingResultList);
        when(node.getNodeId()).thenReturn(nodeId);
        when(nodeId.getValue()).thenReturn(new String("00001111-0000-0000-0000-000011112222"));

        nodeMapper.resolveNode(user, node, virtualNetwork, userIntentVnMapping);
        verify(nodeId).getValue();
    }

    @Test
    public void testResolveNode1() throws Exception {
        User user = mock(User.class);
        Node node = mock(Node.class);
        VirtualNetwork virtualNetwork = mock(VirtualNetwork.class);
        UserIntentVnMapping userIntentVnMapping = mock(UserIntentVnMapping.class);
        NodeType nodeType = mock(NodeType.class);

        Node node1 = mock(Node.class);
        Node node2 = mock(Node.class);
        SubNode subNode = mock(SubNode.class);
        SubNode subNode1 = mock(SubNode.class);
        NodeId nodeId = mock(NodeId.class);
        NodeId node1_id = mock(NodeId.class);
        NodeId node2_id = mock(NodeId.class);
        NodeId subnodeId = mock(NodeId.class);
        NodeId subnodeId1 = mock(NodeId.class);
        VirtualNode virtualNode = mock(VirtualNode.class);
        VirtualNode virtualNode1 = mock(VirtualNode.class);
        Property property = mock(Property.class);
        VirtualNodes virtualNodes_temp = mock(VirtualNodes.class);
        VirtualResource virtualResource = mock(VirtualResource.class);
        IntentVnMappingResult intentVnMappingResult = mock(IntentVnMappingResult.class);
        VirtualResourceEntityId virtualResourceEntityId = mock(VirtualResourceEntityId.class);
        Property locationProperty = mock(Property.class);
        Property ipPrefixProperty = mock(Property.class);
        PropertyValues propertyValues = mock(PropertyValues.class);
        VirtualNodeId virtualNodeId = mock(VirtualNodeId.class);
        VirtualLinks virtualLinks_temp = mock(VirtualLinks.class);
        VirtualPort virtualPort = mock(VirtualPort.class);
        StringValue stringValue = mock(StringValue.class);
        Objects objects = mock(Objects.class);
        List<SubNode> subNodes = new ArrayList<SubNode>();
        List<Node> nodeList = new ArrayList<Node>();
        List<VirtualResource> virtualResourceList = new ArrayList<VirtualResource>();
        List<Property> propertyList = new ArrayList<Property>();
        List<VirtualNode> virtualNodes = new ArrayList<VirtualNode>();
        List<IntentVnMappingResult> intentVnMappingResultList = new ArrayList<IntentVnMappingResult>();
        List<StringValue> stringValueList = new ArrayList<StringValue>();
        List<VirtualLink> virtualLinks = new ArrayList<VirtualLink>();
        List<VirtualPort> virtualPortList = new ArrayList<VirtualPort>();

        subNodes.add(subNode);
        subNodes.add(subNode1);
        stringValueList.add(stringValue);
        virtualResourceList.add(virtualResource);
        Assert.assertTrue(subNodes.size() == 2);
        Assert.assertTrue(subNodes.get(0) == subNode && subNodes.get(1) == subNode1);

        when(node.getNodeType()).thenReturn(new NodeType("l2-group"));
        when(user.getObjects()).thenReturn(objects);
        when(objects.getNode()).thenReturn(nodeList);
        when(node.getSubNode()).thenReturn(subNodes);
        when(node.getNodeId()).thenReturn(nodeId);
        when(node.getProperty()).thenReturn(propertyList);
        when(virtualNetwork.getVirtualNodes()).thenReturn(virtualNodes_temp);
        when(virtualNodes_temp.getVirtualNode()).thenReturn(virtualNodes);
        when(userIntentVnMapping.getIntentVnMappingResult()).thenReturn(intentVnMappingResultList);
        //get into for circle subnodes
        when(subNode.getNodeId()).thenReturn(subnodeId);
        when(subNode1.getNodeId()).thenReturn(subnodeId1);
        //power mock
        PowerMockito.mockStatic(IntentResolverUtils.class);
        PowerMockito.when(IntentResolverUtils.getNode(nodeList, subNode.getNodeId())).thenReturn(node1);
        PowerMockito.when(IntentResolverUtils.getNode(nodeList, subNode1.getNodeId())).thenReturn(node2);
        when(node1.getNodeType()).thenReturn(new NodeType("test1"));
        when(node2.getNodeType()).thenReturn(new NodeType("test2"));
        when(node1.getNodeId()).thenReturn(node1_id);
        when(node2.getNodeId()).thenReturn(node2_id);
        when(node1_id.getValue()).thenReturn(new String("00001111-0000-0000-0000-000011112222"));
        when(node2_id.getValue()).thenReturn(new String("00001111-0000-0000-0000-000011112222"));
        PowerMockito.when(IntentResolverUtils.getIntentVnMappingResult(intentVnMappingResultList,
                new IntentId(node1.getNodeId().getValue())))
                .thenReturn(intentVnMappingResult);
        PowerMockito.when(IntentResolverUtils.getIntentVnMappingResult(intentVnMappingResultList,
                new IntentId(node2.getNodeId().getValue()))).
                thenReturn(intentVnMappingResult);
        when(intentVnMappingResult.getVirtualResource()).thenReturn(virtualResourceList);
        when(virtualResource.getParentVirtualResourceEntityId()).thenReturn(virtualResourceEntityId);
        when(virtualResourceEntityId.getValue()).thenReturn(new String("00001111-1111-0000-0000-000011112222"));
        PowerMockito.when(IntentResolverUtils.getVirtualNode(virtualNodes,
                new VirtualNodeId(intentVnMappingResult.getVirtualResource().get(0).getParentVirtualResourceEntityId().getValue())))
                .thenReturn(virtualNode)
                .thenReturn(virtualNode1);
        PowerMockito.when(IntentResolverUtils.getNodeProperty(node.getProperty(), new PropertyName("location")))
                .thenReturn(locationProperty);
        PowerMockito.when(IntentResolverUtils.getNodeProperty(node.getProperty(), new PropertyName("ip-prefix")))
                .thenReturn(locationProperty);//use locationProperty instead of ipPrefixProperty
        when(locationProperty.getPropertyValues()).thenReturn(propertyValues);
        when(propertyValues.getStringValue()).thenReturn(stringValueList);
        when(stringValue.getValue())
                .thenReturn(new String("1"))
                .thenReturn(new String("192.168.1.1/24"));

        when(virtualNetwork.getVirtualLinks()).thenReturn(virtualLinks_temp);
        when(virtualLinks_temp.getVirtualLink()).thenReturn(virtualLinks);
        when(virtualNode.getVirtualPort()).thenReturn(virtualPortList);
        when(virtualNode.getNodeId()).thenReturn(virtualNodeId);
        when(virtualNode1.getNodeId()).thenReturn(virtualNodeId);
        when(virtualNode1.getVirtualPort()).thenReturn(virtualPortList);
        when(nodeId.getValue()).thenReturn(new String("00001111-1111-0000-0000-000011112222"));


        nodeMapper.resolveNode(user, node, virtualNetwork, userIntentVnMapping);
        verify(stringValue,times(2)).getValue();

    }
    @Test
    public void testResolveNode2() throws Exception {
        User user = mock(User.class);
        Node node = mock(Node.class);
        VirtualNetwork virtualNetwork = mock(VirtualNetwork.class);
        UserIntentVnMapping userIntentVnMapping = mock(UserIntentVnMapping.class);

        when(node.getNodeType()).thenReturn(new NodeType("l3-group"));

        nodeMapper.resolveNode(user, node, virtualNetwork, userIntentVnMapping);
    }

    @Test
    public void testResolveNode3() throws Exception {
        User user = mock(User.class);
        Node node = mock(Node.class);
        VirtualNetwork virtualNetwork = mock(VirtualNetwork.class);
        UserIntentVnMapping userIntentVnMapping = mock(UserIntentVnMapping.class);

        NodeId nodeId = mock(NodeId.class);
        Property property = mock(Property.class);
        Property property1 = mock(Property.class);
        StringValue stringValue = mock(StringValue.class);
        PhysicalNodeId physicalNodeId = mock(PhysicalNodeId.class);
        VirtualNodes virtualNodes_temp = mock(VirtualNodes.class);
        PropertyValues propertyValues = mock(PropertyValues.class);
        List<VirtualNode> virtualNodes = new ArrayList<VirtualNode>();
        List<StringValue> stringValueList = new ArrayList<StringValue>();
        List<IntentVnMappingResult> intentVnMappingResultList = new ArrayList<IntentVnMappingResult>();

        stringValueList.add(stringValue);

        PowerMockito.mockStatic(IntentResolverUtils.class);
        PowerMockito.when(IntentResolverUtils.checkExternalLayer3Group(node))
                .thenReturn(false)
                .thenReturn(true);
        when(node.getNodeType()).thenReturn(new NodeType("ext-group"));
        //test false
        nodeMapper.resolveNode(user, node, virtualNetwork, userIntentVnMapping);
        //true
        when(node.getProperty()).thenReturn(new ArrayList<Property>());
        PowerMockito.when(IntentResolverUtils.getNodeProperty(node.getProperty(), new PropertyName("location")))
                .thenReturn(property);
        PowerMockito.when(IntentResolverUtils.generatePhysicalNodeIdFromNodeLocationProperty(property))
                .thenReturn(physicalNodeId);
        when(physicalNodeId.getValue()).thenReturn(new String("test"));
        when(virtualNetwork.getVirtualNodes()).thenReturn(virtualNodes_temp);
        when(virtualNodes_temp.getVirtualNode()).thenReturn(virtualNodes);
        PowerMockito.when(IntentResolverUtils.getNodeProperty(node.getProperty(), new PropertyName("ip-prefix")))
                .thenReturn(property1);
        when(property1.getPropertyValues()).thenReturn(propertyValues);
        when(propertyValues.getStringValue()).thenReturn(stringValueList);
        when(stringValue.getValue())
                .thenReturn("192.168.1.1/24")
                .thenReturn("Test");
        when(property.getPropertyValues()).thenReturn(propertyValues);
        when(node.getNodeId()).thenReturn(nodeId);
        when(nodeId.getValue()).thenReturn(new String("00001111-1111-0000-0000-000011112222"));
        when(userIntentVnMapping.getIntentVnMappingResult()).thenReturn(intentVnMappingResultList);
        nodeMapper.resolveNode(user, node, virtualNetwork, userIntentVnMapping);
        verify(stringValue,times(2)).getValue();
    }
    @Test
    public void testResolveNode4() throws Exception {
        User user = mock(User.class);
        Node node = mock(Node.class);
        VirtualNetwork virtualNetwork = mock(VirtualNetwork.class);
        UserIntentVnMapping userIntentVnMapping = mock(UserIntentVnMapping.class);

        SubNode subNode = mock(SubNode.class);
        SubNode subNode1 = mock(SubNode.class);
        NodeId nodeId = mock(NodeId.class);
        Objects objects = mock(Objects.class);
        IntentVnMappingResult intentVnMappingResult = mock(IntentVnMappingResult.class);
        IntentVnMappingResult intentVnMappingResult_elseif = mock(IntentVnMappingResult.class);
        VirtualResource virtualResource = mock(VirtualResource.class);
        VirtualLink virtualLink = mock(VirtualLink.class);
        VirtualNode virtualNode = mock(VirtualNode.class);
        VirtualNodes virtualNodes_temp = mock(VirtualNodes.class);
        VirtualPaths virtualPaths_temp = mock(VirtualPaths.class);
        VirtualLinks virtualLinks_temp = mock(VirtualLinks.class);
        VirtualResourceEntityId virtualResourceEntityId = mock(VirtualResourceEntityId.class);
        List<Node> nodes = new ArrayList<Node>();
        List<SubNode> subNodes = new ArrayList<SubNode>();
        List<IntentVnMappingResult> intentVnMappingResults = new ArrayList<IntentVnMappingResult>();
        List<VirtualResource> virtualResourceList = new ArrayList<VirtualResource>();
        List<VirtualNode> virtualNodes = new ArrayList<VirtualNode>();
        List<VirtualLink> virtualLinks = new ArrayList<VirtualLink>();
        List<VirtualPath> virtualPaths = new ArrayList<VirtualPath>();
        List<VirtualPort> virtualPortList = new ArrayList<VirtualPort>();

        virtualResourceList.add(virtualResource);

        when(node.getNodeType()).thenReturn(new NodeType("chain-group"));
        when(node.getSubNode()).thenReturn(subNodes);
        when(node.getNodeId()).thenReturn(nodeId);

        //test null
        Assert.assertTrue(subNodes.isEmpty());
        nodeMapper.resolveNode(user, node, virtualNetwork, userIntentVnMapping);

        //test not null
        subNodes.add(subNode);

        when(userIntentVnMapping.getIntentVnMappingResult()).thenReturn(intentVnMappingResults);
        //test size=1
        when(subNode.getNodeId()).thenReturn(nodeId);
        when(subNode1.getNodeId()).thenReturn(nodeId);
        when(nodeId.getValue()).thenReturn("00001111-0000-0000-0000-000011112222");
        PowerMockito.mockStatic(IntentResolverUtils.class);
        PowerMockito.when(IntentResolverUtils.getIntentVnMappingResult(intentVnMappingResults, new IntentId(subNode.getNodeId().getValue())))
                .thenReturn(intentVnMappingResult);
        when(intentVnMappingResult.getVirtualResource()).thenReturn(virtualResourceList);
        nodeMapper.resolveNode(user, node, virtualNetwork, userIntentVnMapping);
        verify(node).getNodeId();
        //test size > 1
        subNodes.add(subNode1);
        PowerMockito.when(IntentResolverUtils.sortSubNodes(subNodes)).thenReturn(subNodes);
        when(user.getObjects()).thenReturn(objects);
        when(objects.getNode()).thenReturn(nodes);
//        Iterator<SubNode> iterator = subNodes.iterator();
//        Assert.assertTrue(subNode == iterator.next());
//        Assert.assertTrue(subNode1 == iterator.next());
//        PowerMockito.doNothing().when(IntentResolverUtils.class):IntentResolverUtils.checkAllLayer2OperatingMode(subNodes, nodes);
        PowerMockito.when(IntentResolverUtils.checkAllLayer2OperatingMode(subNodes, nodes))
                .thenReturn(true)
                .thenReturn(false);
        PowerMockito.when(IntentResolverUtils.checkAllLayer3OperatingMode(subNodes, nodes))
                .thenReturn(false)
                .thenReturn(true);

        nodeMapper.resolveNode(user, node, virtualNetwork, userIntentVnMapping);
        nodeMapper.resolveNode(user, node, virtualNetwork, userIntentVnMapping);
        verify(node, times(3)).getNodeId();
        //test else if
        when(virtualNetwork.getVirtualNodes()).thenReturn(virtualNodes_temp);
        when(virtualNodes_temp.getVirtualNode()).thenReturn(virtualNodes);
        when(virtualNetwork.getVirtualLinks()).thenReturn(virtualLinks_temp);
        when(virtualLinks_temp.getVirtualLink()).thenReturn(virtualLinks);
        when(virtualNetwork.getVirtualPaths()).thenReturn(virtualPaths_temp);
        when(virtualPaths_temp.getVirtualPath()).thenReturn(virtualPaths);
        PowerMockito.when(IntentResolverUtils.getIntentVnMappingResult(intentVnMappingResults, new IntentId(subNode1.getNodeId().getValue())))
                .thenReturn(intentVnMappingResult);
        when(virtualResource.getParentVirtualResourceEntityId()).thenReturn(virtualResourceEntityId);
        when(virtualResourceEntityId.getValue()).thenReturn(new String("00001111-1111-0000-0000-000011112222"));
        PowerMockito.when(IntentResolverUtils.getVirtualLink(any(List.class), any(VirtualNodeId.class), any(VirtualNodeId.class)))
                .thenReturn(null);
        PowerMockito.when(IntentResolverUtils.getVirtualNode(any(List.class), any(VirtualNodeId.class)))
                .thenReturn(virtualNode);
        when(virtualNode.getVirtualPort()).thenReturn(virtualPortList);

        nodeMapper.resolveNode(user, node, virtualNetwork, userIntentVnMapping);

    }

    @Test
    public void testResolveNode5() throws Exception {
        User user = mock(User.class);
        Node node = mock(Node.class);
        VirtualNetwork virtualNetwork = mock(VirtualNetwork.class);
        UserIntentVnMapping userIntentVnMapping = mock(UserIntentVnMapping.class);

        NodeId nodeId = mock(NodeId.class);
        Property locationProperty = mock(Property.class);
        Property operatingModeProperty = mock(Property.class);
        StringValue stringValue = mock(StringValue.class);
        PhysicalNodeId physicalNodeId = mock(PhysicalNodeId.class);
        PropertyValues propertyValues = mock(PropertyValues.class);
        VirtualNodes virtualNodes_temp = mock(VirtualNodes.class);
        List<StringValue> stringValueList = new ArrayList<StringValue>();
        List<VirtualNode> virtualNodes = new ArrayList<VirtualNode>();
        List<IntentVnMappingResult> intentVnMappingResultList = new ArrayList<IntentVnMappingResult>();

        stringValueList.add(stringValue);

        when(node.getNodeType())
                .thenReturn(new NodeType("fw"))
                .thenReturn(new NodeType("lb"))
                .thenReturn(new NodeType("cache"));

        when(node.getProperty()).thenReturn(new ArrayList<Property>());
        PowerMockito.mockStatic(IntentResolverUtils.class);
        PowerMockito.when(IntentResolverUtils.getNodeProperty(node.getProperty(), new PropertyName("location")))
                .thenReturn(locationProperty);
        PowerMockito.when(IntentResolverUtils.getNodeProperty(node.getProperty(), new PropertyName("operating-mode")))
                .thenReturn(operatingModeProperty);
        PowerMockito.when(IntentResolverUtils.generatePhysicalNodeIdFromNodeLocationProperty(locationProperty))
                .thenReturn(physicalNodeId);
        when(physicalNodeId.getValue()).thenReturn(new String("test"));
        when(operatingModeProperty.getPropertyValues()).thenReturn(propertyValues);
        when(propertyValues.getStringValue()).thenReturn(stringValueList);
        when(stringValue.getValue())
                .thenReturn(new String("layer3"))
                .thenReturn(new String("layer2"));
        when(virtualNetwork.getVirtualNodes()).thenReturn(virtualNodes_temp);
        when(virtualNodes_temp.getVirtualNode()).thenReturn(virtualNodes);
        when(locationProperty.getPropertyValues()).thenReturn(propertyValues);
        when(node.getNodeId()).thenReturn(nodeId);
        when(nodeId.getValue()).thenReturn(new String("00001111-1111-0000-0000-000011112222"));
        when(userIntentVnMapping.getIntentVnMappingResult()).thenReturn(intentVnMappingResultList);

        nodeMapper.resolveNode(user, node, virtualNetwork, userIntentVnMapping);
        verify(node).getNodeId();
    }
    @Test
    public void testResolveExternalLayer2Group() throws Exception {
        Class<NodeMapper> class1 = NodeMapper.class;
        Method method = class1.getDeclaredMethod("resolveExternalLayer2Group", new Class[]{
                User.class,
                Node.class,
                VirtualNetwork.class,
                UserIntentVnMapping.class,
                boolean.class
        });
        method.setAccessible(true);

        boolean generatingVirtualRouter = true;
        User user = mock(User.class);
        Node node = mock(Node.class);
        Property property = mock(Property.class);
        Property property1 = mock(Property.class);
        NodeId nodeId = mock(NodeId.class);
        VirtualNetwork virtualNetwork = mock(VirtualNetwork.class);
        UserIntentVnMapping userIntentVnMapping = mock(UserIntentVnMapping.class);
        PhysicalNodeId physicalNodeId = mock(PhysicalNodeId.class);
        VirtualNodes virtualNodes_temp = mock(VirtualNodes.class);
        PropertyValues propertyValues = mock(PropertyValues.class);
        StringValue stringValue = mock(StringValue.class);

        List<Property> propertyList = new ArrayList<Property>();
        List<VirtualNode> virtualNodes = new ArrayList<VirtualNode>();
        List<StringValue> stringValueList = new ArrayList<StringValue>();
        List<IntentVnMappingResult> intentVnMappingResultList = new ArrayList<IntentVnMappingResult>();

        stringValueList.add(stringValue);

        when(node.getProperty()).thenReturn(propertyList);
        PowerMockito.mockStatic(IntentResolverUtils.class);
        PowerMockito.when(IntentResolverUtils.getNodeProperty(node.getProperty(), new PropertyName("location")))
                .thenReturn(property);
        PowerMockito.when(IntentResolverUtils.generatePhysicalNodeIdFromNodeLocationProperty(property))
                .thenReturn(physicalNodeId);
        when(physicalNodeId.getValue()).thenReturn(new String("test"));
        when(virtualNetwork.getVirtualNodes()).thenReturn(virtualNodes_temp);
        when(virtualNodes_temp.getVirtualNode()).thenReturn(virtualNodes);
        PowerMockito.when(IntentResolverUtils.getNodeProperty(node.getProperty(), new PropertyName("ip-prefix")))
                .thenReturn(property1);
        when(property1.getPropertyValues()).thenReturn(propertyValues);
        when(propertyValues.getStringValue()).thenReturn(stringValueList);
        when(stringValue.getValue())
                .thenReturn(new String("192.168.1.1/24"))
                .thenReturn(new String("test"));
        when(property.getPropertyValues()).thenReturn(propertyValues);
        when(node.getNodeId()).thenReturn(nodeId);
        when(nodeId.getValue()).thenReturn(new String("00001111-1111-0000-0000-000011112222"));
        when(userIntentVnMapping.getIntentVnMappingResult()).thenReturn(intentVnMappingResultList);

        method.invoke(nodeMapper, user, node, virtualNetwork, userIntentVnMapping, generatingVirtualRouter);
        verify(node).getNodeId();

    }
}
