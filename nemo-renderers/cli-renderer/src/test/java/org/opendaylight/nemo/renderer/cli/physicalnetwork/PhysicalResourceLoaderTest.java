/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.renderer.cli.physicalnetwork;

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
 * Created by zhangmeng on 2015/12/8.
 */
public class PhysicalResourceLoaderTest extends TestCase {
    private DataBroker dataBroker;
    private PhysicalResourceLoader physicalResourceLoader;
    @Before
    public void setUp() throws Exception {
        dataBroker = mock(DataBroker.class);
        physicalResourceLoader = new PhysicalResourceLoader(dataBroker);
    }

    @Test
    public void testGetPhysicalNode() throws Exception {
        PhysicalNodeId physicalNodeId = mock(PhysicalNodeId.class);
        PhysicalNode physicalNode ;

        Class<PhysicalResourceLoader> class1 = PhysicalResourceLoader.class;
        Method method = class1.getDeclaredMethod("getPhysicalNode",new Class[]{PhysicalNodeId.class});
        method.setAccessible(true);

        physicalNode = (PhysicalNode) method.invoke(physicalResourceLoader,physicalNodeId);

        Assert.assertTrue( physicalNode == null);
    }

    @Test
    public void testGetPhysicalPort() throws Exception {
        PhysicalPortId physicalPortId = mock(PhysicalPortId.class);
        PhysicalPort physicalPort;

        Class<PhysicalResourceLoader> class1 = PhysicalResourceLoader.class;
        Method method = class1.getDeclaredMethod("getPhysicalPort",new Class[]{PhysicalPortId.class});
        method.setAccessible(true);

        physicalPort = (PhysicalPort) method.invoke(physicalResourceLoader,physicalPortId);

        Assert.assertTrue(physicalPort == null);
    }

    @Test
    public void testGetPhysicalLink() throws Exception {
        PhysicalLinkId physicalLinkId = mock(PhysicalLinkId.class);
        PhysicalLink physicalLink;

        Class<PhysicalResourceLoader> class1 = PhysicalResourceLoader.class;
        Method method = class1.getDeclaredMethod("getPhysicalLink",new Class[]{PhysicalLinkId.class});
        method.setAccessible(true);

        physicalLink = (PhysicalLink) method.invoke(physicalResourceLoader,physicalLinkId);

        Assert.assertTrue(physicalLink == null);
    }

    @Test
    public void testClose() throws Exception {
        physicalResourceLoader.close();
    }

    @Test
    public void testBuildHosts()throws Exception{
        Class<PhysicalResourceLoader> class1 = PhysicalResourceLoader.class;
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


        list = (List<PhysicalHost>) method.invoke(physicalResourceLoader,hostsNode);
        Assert.assertTrue(list.size() == 1);
    }

    @Test
    public void testBuildPortAttributes()throws Exception{
        Class<PhysicalResourceLoader>class1 = PhysicalResourceLoader.class;
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
        attributeList = (List<Attribute>)method.invoke(physicalResourceLoader,attributes);
        Assert.assertTrue(attributeList.size() == 0);
        //new AttributeName("zm");
        attributeList = (List<Attribute>)method.invoke(physicalResourceLoader,attributes);
        Assert.assertTrue(attributeList.size() == 1);
        verify(portAttribute,times(3)).path(any(String.class));
        verify(jsonNode_temp,times(3)).asText();
    }

    @Test
    public void testBuildLinks()throws Exception{
        List<PhysicalLink> list;
        Class<PhysicalResourceLoader> class1 = PhysicalResourceLoader.class;
        Method method = class1.getDeclaredMethod("buildLinks", new Class[]{JsonNode.class});
        method.setAccessible(true);

        JsonNode linksRoot = mock(JsonNode.class);
        JsonNode links = mock(JsonNode.class);
        JsonNode link = mock(JsonNode.class);
        JsonNode link_temp_buildlink = mock(JsonNode.class);

        when(linksRoot.path(any(String.class))).thenReturn(links);
        when(links.size()).thenReturn(1);
        when(links.get(any(Integer.class))).thenReturn(link);
        //get into method "build link" args(link)
        when(link.get(any(String.class))).thenReturn(link_temp_buildlink);
        when(link_temp_buildlink.asText())
                .thenReturn(new String("1"))//new PhysicalLinkId(strLinkId)
                .thenReturn(new String("2"))//new PhysicalNodeId
                .thenReturn(new String("3"))//new PhysicalPortId
                .thenReturn(new String("4"))//new PhysicalNodeId
                .thenReturn(new String("5"))//new PhysicalPortId
                .thenReturn(new String(""));//linkNode.get("link-bandwidth").asText().equals("")
        when(link_temp_buildlink.asLong()).thenReturn((long) 1);

        list = (List<PhysicalLink>)method.invoke(physicalResourceLoader,linksRoot);
        Assert.assertTrue(list.size() == 1);
        verify(link_temp_buildlink,times(6)).asText();
    }

    @Test
    public void testBuildNodes()throws Exception{
        Class<PhysicalResourceLoader> class1 = PhysicalResourceLoader.class;
        Method method = class1.getDeclaredMethod("buildNodes", new Class[]{JsonNode.class});
        method.setAccessible(true);

        List<PhysicalNode> result = new ArrayList<PhysicalNode>();
        JsonNode nodesRoot = mock(JsonNode.class);
        JsonNode nodes = mock(JsonNode.class);
        JsonNode node = mock(JsonNode.class);
        JsonNode node_temp_buildnode = mock(JsonNode.class);
        JsonNode ports = mock(JsonNode.class);
        JsonNode attributes = mock(JsonNode.class);
        JsonNode port = mock(JsonNode.class);
        JsonNode port_temp_buildport = mock(JsonNode.class);
        JsonNode portAttributes = mock(JsonNode.class);
        JsonNode portAttribute = mock(JsonNode.class);
        JsonNode port_temp_buildPortAttribute = mock(JsonNode.class);
        JsonNode portAttribute_father = mock(JsonNode.class);
        JsonNode attribute_temp_father  = mock(JsonNode.class);

        when(nodesRoot.path(any(String.class))).thenReturn(nodes);
        when(nodes.size()).thenReturn(1);
        when(nodes.get(any(Integer.class))).thenReturn(node);
        //get into method "build Node"
        when(node.get(any(String.class))).thenReturn(node_temp_buildnode);
        when(node_temp_buildnode.asText())
                .thenReturn(new String(""))//test null
                .thenReturn(new String("test"))//node id
                .thenReturn(new String("switch"));// node type
        result = (List<PhysicalNode>) method.invoke(physicalResourceLoader,nodesRoot);
        Assert.assertTrue(result.size() == 0);
        verify(node_temp_buildnode).asText();
        when(node.path(any(String.class)))
                .thenReturn(ports)//get into method "build ports"
                .thenReturn(attributes); //get into method "build attributes"
        ////get into method"build ports"
        when(ports.size()).thenReturn(1);
        when(ports.get(any(Integer.class))).thenReturn(port);
        //////get into method "build port"
        when(port.get(any(String.class))).thenReturn(port_temp_buildport);
        when(port_temp_buildport.asText())
                .thenReturn(new String("test"))//port id
                .thenReturn(new String("external"))//port type
                .thenReturn(new String("00:11:22:33:44:55"))//if(!(port.get("port-mac-address").asText().equals("")))  get in
                .thenReturn(new String("00:11:22:33:44:55"))//mac address
                .thenReturn(new String(""));// if(port.get("bandwidth").asText().equals(""))  get in
        when(port.path(any(String.class))).thenReturn(portAttributes);
        ////////get into method "buildPortAttributes" args(portAttributes)
        when(portAttributes.size()).thenReturn(1);
        when(portAttributes.get(any(Integer.class))).thenReturn(portAttribute);
        ////////// get into method "buildPortAttribute" args(portAttribute)
        when(portAttribute.path(any(String.class))).thenReturn(port_temp_buildPortAttribute);
        when(port_temp_buildPortAttribute.asText())
                .thenReturn(new String("test"))//ATTRIBUTE_NAME
                .thenReturn(new String("test"));//ATTRIBUTE_VALUE
        ////return to method "buildnode" and get into method "..............buildNodeAttributes"
        when(attributes.size()).thenReturn(1);
        when(attributes.get(any(Integer.class))).thenReturn(portAttribute_father);
        //////get into method "..............buildNodeAttribute"
        when(portAttribute_father.path(any(String.class))).thenReturn(attribute_temp_father);
        when(attribute_temp_father.asText())
                .thenReturn(new String("test"))//ATTRIBUTE_NAME
                .thenReturn(new String("test"));//ATTRIBUTE_VALUE
        result = (List<PhysicalNode>) method.invoke(physicalResourceLoader,nodesRoot); //return empty list
        Assert.assertTrue(result.size() == 1);
        verify(port_temp_buildport,times(5)).asText();
        verify(attribute_temp_father,times(2)).asText();
    }
}
