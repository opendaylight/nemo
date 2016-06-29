/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.renderer.openflow.physicalnetwork;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.nemo.renderer.openflow.physicalnetwork.PhyConfigLoader;
import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.inet.types.rev130715.IpAddress;
import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.inet.types.rev130715.Ipv4Address;
import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.yang.types.rev130715.MacAddress;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.PhysicalNodeInstance;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.PhysicalPortInstance;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.attribute.instance.AttributeValueBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.host.instance.IpAddressesBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.hosts.PhysicalHost;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.hosts.PhysicalHostBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.hosts.PhysicalHostKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.links.PhysicalLink;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.links.PhysicalLinkBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.nodes.PhysicalNode;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.nodes.PhysicalNodeBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.nodes.PhysicalNodeKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.node.instance.PhysicalPort;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.node.instance.PhysicalPortBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.port.instance.Attribute;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.port.instance.AttributeBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.port.instance.AttributeKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import static org.mockito.Mockito.*;
/**
 * Created by zhangmeng on 2015/11/30.
 */
public class PhyConfigLoaderTest extends TestCase {
    private PhyConfigLoader phyConfigLoader;
    private DataBroker dataBroker;
    @Override
    @Before
    public void setUp() throws Exception {
        dataBroker = mock(DataBroker.class);
        phyConfigLoader = new PhyConfigLoader(dataBroker);
    }

    @Test
    public void testGetExternalNetworkMac() throws Exception {
        Table<PhysicalNodeId, PhysicalPortId, MacAddress> table;
        table = phyConfigLoader.getExternalNetworkMac();
        Assert.assertTrue(table != null);
    }

    @Test
    public void testGetPhysicalNode() throws Exception {
        PhysicalNodeId physicalNodeId = mock(PhysicalNodeId.class);
        PhysicalNode physicalNode ;

        Class<PhyConfigLoader> class1 = PhyConfigLoader.class;
        Method method = class1.getDeclaredMethod("getPhysicalNode",new Class[]{PhysicalNodeId.class});
        method.setAccessible(true);

        physicalNode = (PhysicalNode) method.invoke(phyConfigLoader,physicalNodeId);

        Assert.assertTrue( physicalNode == null);
    }

    @Test
    public void testGetPhysicalPort() throws Exception {
        PhysicalPortId physicalPortId = mock(PhysicalPortId.class);
        PhysicalPort physicalPort;

        Class<PhyConfigLoader> class1 = PhyConfigLoader.class;
        Method method = class1.getDeclaredMethod("getPhysicalPort",new Class[]{PhysicalPortId.class});
        method.setAccessible(true);

        physicalPort = (PhysicalPort) method.invoke(phyConfigLoader,physicalPortId);

        Assert.assertTrue(physicalPort == null);
    }

    @Test
    public void testGetPhysicalLink() throws Exception {
        PhysicalLinkId physicalLinkId = mock(PhysicalLinkId.class);
        PhysicalLink physicalLink;

        Class<PhyConfigLoader> class1 = PhyConfigLoader.class;
        Method method = class1.getDeclaredMethod("getPhysicalLink",new Class[]{PhysicalLinkId.class});
        method.setAccessible(true);

        physicalLink = (PhysicalLink) method.invoke(phyConfigLoader,physicalLinkId);

        Assert.assertTrue(physicalLink == null);
    }

    @Test
    public void testClose() throws Exception {
        phyConfigLoader.close();
    }

    @Test
    public void testBuildExternals() throws Exception{
        JsonNode externalRoot = mock(JsonNode.class);
        JsonNode exNetworkNodes = mock(JsonNode.class);
        JsonNode exNetworkNode = mock(JsonNode.class);
        JsonNode jsonNode = mock(JsonNode.class);
        String nodeId = new String("1");
        String portId = new String("2");
        String peerMac = new String("00:11:22:33:44:55");

        Class<PhyConfigLoader> class1 = PhyConfigLoader.class;
        Method method = class1.getDeclaredMethod("buildExternals",new Class[]{JsonNode.class});
        method.setAccessible(true);

        when(externalRoot.path(any(String.class))).thenReturn(exNetworkNodes);
        when(exNetworkNodes.size()).thenReturn(1);
        when(exNetworkNodes.get(any(Integer.class))).thenReturn(exNetworkNode);

        //get into method "buildExNetwork"
        when(exNetworkNode.get(any(String.class))).thenReturn(jsonNode);
        when(jsonNode.asText())
                .thenReturn(nodeId)
                .thenReturn(portId)
                .thenReturn(peerMac);

        method.invoke(phyConfigLoader, externalRoot);

        verify(externalRoot).path(any(String.class));
        verify(exNetworkNodes,times(2)).size();
        verify(exNetworkNodes).get(any(Integer.class));
        verify(exNetworkNode,times(3)).get(any(String.class));
        verify(jsonNode,times(3)).asText();
    }


    @Test
    public PhysicalLink testBuildLink(JsonNode arg)throws Exception{
        if(arg == null)return null;

        PhysicalLink physicalLink = mock(PhysicalLink.class);
        JsonNode jsonNode = mock(JsonNode.class);
        JsonNode jsonNode1 = mock(JsonNode.class);


        Class<PhyConfigLoader> class1 = PhyConfigLoader.class;
        Method method = class1.getDeclaredMethod("buildLink",new Class[]{JsonNode.class});
        method.setAccessible(true);

        when(jsonNode.get(any(String.class)))
                .thenReturn(jsonNode1)
                .thenReturn(jsonNode1);
        when(jsonNode1.asText()).thenReturn(new String("test"));
        when(jsonNode1.asLong()).thenReturn((long) 1);

        physicalLink = (PhysicalLink)method.invoke(phyConfigLoader,jsonNode);
        Assert.assertTrue(physicalLink != null);
        Assert.assertTrue(physicalLink != mock(PhysicalLink.class));

        return physicalLink;
    }

    @Test
    public void testBuildLinks()throws Exception{
        List<PhysicalLink> list;
        JsonNode hostsNode = mock(JsonNode.class);
        JsonNode hosts = mock(JsonNode.class);
        JsonNode jsonNode = mock(JsonNode.class);
        JsonNode jsonNode1 = mock(JsonNode.class);
        PhysicalHost physicalHost;

        Class<PhyConfigLoader> class1 = PhyConfigLoader.class;
        Method method = class1.getDeclaredMethod("buildLinks",new Class[]{JsonNode.class});
        method.setAccessible(true);

        when(hostsNode.path(any(String.class))).thenReturn(hosts);
        when(hosts.size()).thenReturn(1);
        when(hosts.get(any(Integer.class))).thenReturn(jsonNode);
        when(jsonNode.get(any(String.class)))
                .thenReturn(jsonNode1)
                .thenReturn(jsonNode1);
        when(jsonNode1.asText()).thenReturn(new String("test"));
        when(jsonNode1.asLong()).thenReturn((long) 1);

        list = (List<PhysicalLink>)method.invoke(phyConfigLoader,hostsNode);
        Assert.assertTrue(list.size() == 1);
    }

    @Test
    public void testBuildHosts()throws Exception{
        Class<PhyConfigLoader> class1 = PhyConfigLoader.class;
        Method method = class1.getDeclaredMethod("buildHosts", new Class[]{JsonNode.class});
        method.setAccessible(true);

        JsonNode hostsNode = mock(JsonNode.class);
        JsonNode hosts = mock(JsonNode.class);
        JsonNode host = mock(JsonNode.class);
        JsonNode host_temp = mock(JsonNode.class);
        JsonNode host_temp1 = mock(JsonNode.class);
        JsonNode ipaddrs = mock(JsonNode.class);
        JsonNode ipaddr = mock(JsonNode.class);
        List<PhysicalHost> list;

        when(hostsNode.path(any(String.class))).thenReturn(hosts);
        when(hosts.size()).thenReturn(1);
        when(hosts.get(any(Integer.class))).thenReturn(host);
        //get into method "buildhost"
        when(host.get(any(String.class))).thenReturn(host_temp);
        when(host_temp.asText())
                .thenReturn(new String("00001111-0000-0000-0000-000011112222")) //HOST_ID
                .thenReturn(new String("hostName")) //HOST_NAME
                .thenReturn(new String("00:11:22:33:44:55")) //MAC_ADDRESS
                .thenReturn(new String("nodeId")) //NODE_ID
                .thenReturn(new String("connetionId"));//PhysicalPortId
        when(host.path(any(String.class))).thenReturn(ipaddrs);
        when(ipaddrs.size()).thenReturn(1);
        when(ipaddrs.get(any(Integer.class))).thenReturn(ipaddr);
        when(ipaddr.get(any(String.class))).thenReturn(host_temp1);
        when(host_temp1.asText()).thenReturn(new String("192.168.1.1"));//ipv4_address


        list = (List<PhysicalHost>) method.invoke(phyConfigLoader,hostsNode);
        Assert.assertTrue(list.size() == 1);
    }

    public void testBuildPortAttributes()throws Exception{
        Class<PhyConfigLoader>class1 = PhyConfigLoader.class;
        Method method = class1.getDeclaredMethod("buildPortAttributes",new Class[]{JsonNode.class});
        method.setAccessible(true);

        List<Attribute> attributeList;
        JsonNode attributes = mock(JsonNode.class);
        JsonNode portAttribute = mock(JsonNode.class);
        JsonNode jsonNode_temp = mock(JsonNode.class);

        when(attributes.size()).thenReturn(1);
        when(attributes.get(any(Integer.class))).thenReturn(portAttribute);
        //get into method "buildPortAttribute"
        when(portAttribute.path(any(String.class))).thenReturn(jsonNode_temp);
        when(jsonNode_temp.asText())
                .thenReturn(new String(""))//branch null
                .thenReturn(new String("zm"));
        attributeList = (List<Attribute>)method.invoke(phyConfigLoader,attributes);
        Assert.assertTrue(attributeList.size() == 0);
        //new AttributeName("zm");
        attributeList = (List<Attribute>)method.invoke(phyConfigLoader,attributes);
        Assert.assertTrue(attributeList.size() == 1);
        verify(portAttribute,times(3)).path(any(String.class));
        verify(jsonNode_temp,times(3)).asText();
    }

    @Test
    public void testBuildNodes() throws Exception{
        Class<PhyConfigLoader>class1 = PhyConfigLoader.class;
        Method method = class1.getDeclaredMethod("buildNodes", new Class[]{JsonNode.class});
        method.setAccessible(true);

        JsonNode nodesRoot = mock(JsonNode.class);
        JsonNode nodes = mock(JsonNode.class);
        JsonNode node = mock(JsonNode.class);
        JsonNode node_temp = mock(JsonNode.class);
        JsonNode port_temp = mock(JsonNode.class);
        JsonNode attribute_temp = mock(JsonNode.class);
        JsonNode attribute_temp_son = mock(JsonNode.class);
        JsonNode ports = mock(JsonNode.class);
        JsonNode attributes = mock(JsonNode.class);
        JsonNode portAttributes = mock(JsonNode.class);
        JsonNode portAttribute = mock(JsonNode.class);
        JsonNode attributes_son = mock(JsonNode.class);
        JsonNode port = mock(JsonNode.class);
        List<PhysicalNode> result;

        when(nodesRoot.path(any(String.class))).thenReturn(nodes);
        when(nodes.size()).thenReturn(1);
        when(nodes.get(any(Integer.class))).thenReturn(node);
        //get into method "buildnode"
        when(node.get(any(String.class))).thenReturn(node_temp);
        when(node_temp.asText())
                .thenReturn(new String(""))//branch null
                .thenReturn(new String("test")) //node_id
                .thenReturn(new String("switch")); //NODE_TYPE


        result = (List<PhysicalNode>) method.invoke(phyConfigLoader,nodesRoot); //return empty list
        Assert.assertTrue(result.size() == 0);
        verify(node_temp).asText();

        when(node.path(any(String.class)))
                .thenReturn(ports)//PORTS
                .thenReturn(attributes);//ATTRIBUTES
        ////get into method "buildports"
        when(ports.size()).thenReturn(1);
        when(ports.get(any(Integer.class))).thenReturn(port);
        ///////get into method "buildport"
        when(port.get(any(String.class))).thenReturn(port_temp);
        when(port_temp.asText())
                .thenReturn(new String("test"))//PORT_ID
                .thenReturn(new String("switch"));//PORT_TYPE
        when(port.path(any(String.class))).thenReturn(portAttributes);
        ////////get into method "buildportattributes"
        when(portAttributes.size()).thenReturn(1);
        when(portAttributes.get(any(Integer.class))).thenReturn(attributes_son);
        //////////get into method "buildPortAttribute"
        when(attributes_son.path(any(String.class))).thenReturn(attribute_temp_son);
        when(attribute_temp_son.asText())
                .thenReturn(new String("zm"))
                .thenReturn(new String("zm"));
        ////return to method "buildnode" and get into method "..............buildNodeAttributes"
        when(attributes.size()).thenReturn(1);
        when(attributes.get(any(Integer.class))).thenReturn(portAttribute);
        //////get into method "..............buildNodeAttribute"
        when(portAttribute.path(any(String.class))).thenReturn(attribute_temp);
        when(attribute_temp.asText())
                .thenReturn(new String("test"))//ATTRIBUTE_NAME
                .thenReturn(new String("test"));//ATTRIBUTE_VALUE

        result = (List<PhysicalNode>) method.invoke(phyConfigLoader,nodesRoot); //return empty list
        Assert.assertTrue(result.size() == 1);

    }

}
