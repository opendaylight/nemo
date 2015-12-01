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
import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.inet.types.rev100924.IpAddress;
import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.inet.types.rev100924.Ipv4Address;
import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.yang.types.rev100924.MacAddress;
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
}