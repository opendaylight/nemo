/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.renderer.openflow;

import com.sun.security.sasl.util.AbstractSaslImpl;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.opendaylight.nemo.renderer.openflow.physicalnetwork.PhyConfigLoader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.opendaylight.nemo.renderer.openflow.FlowUtils;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.opendaylight.controller.liblldp.Ethernet;
import org.opendaylight.controller.liblldp.HexEncode;
import org.opendaylight.controller.liblldp.NetUtils;
import org.opendaylight.controller.liblldp.PacketException;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.WriteTransaction;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.nemo.renderer.openflow.physicalnetwork.PhyConfigLoader;
import com.google.common.util.concurrent.CheckedFuture;
import org.opendaylight.nemo.renderer.openflow.utils.ARP;
import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.inet.types.rev130715.IpAddress;
import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.inet.types.rev130715.IpPrefix;
import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.inet.types.rev130715.Ipv4Address;
import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.inet.types.rev130715.Ipv4Prefix;
import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.yang.types.rev130715.MacAddress;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.dec.mpls.ttl._case.DecMplsTtl;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.dec.mpls.ttl._case.DecMplsTtlBuilder;import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.dec.nw.ttl._case.DecNwTtl;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.dec.nw.ttl._case.DecNwTtlBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.output.action._case.OutputAction;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.output.action._case.OutputActionBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.pop.mpls.action._case.PopMplsAction;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.pop.mpls.action._case.PopMplsActionBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.push.mpls.action._case.PushMplsAction;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.push.mpls.action._case.PushMplsActionBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.set.field._case.SetField;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.set.field._case.SetFieldBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.list.Action;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.list.ActionBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.FlowCapableNode;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.FlowId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.meters.Meter;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.meters.MeterBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.meters.MeterKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.tables.Table;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.tables.TableKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.tables.table.Flow;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.tables.table.FlowBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.tables.table.FlowKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.flow.Instructions;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.flow.InstructionsBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.flow.Match;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.flow.MatchBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.instruction.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.instruction.apply.actions._case.ApplyActions;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.instruction.apply.actions._case.ApplyActionsBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.instruction.go.to.table._case.GoToTable;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.instruction.go.to.table._case.GoToTableBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.instruction.write.metadata._case.WriteMetadata;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.instruction.write.metadata._case.WriteMetadataBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.list.Instruction;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.list.InstructionBuilder; import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.node.NodeConnector;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.node.NodeConnectorKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.nodes.Node;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.nodes.NodeKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.l2.types.rev130827.EtherType;
import org.opendaylight.yang.gen.v1.urn.opendaylight.meter.types.rev130918.BandId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.meter.types.rev130918.MeterBandType;
import org.opendaylight.yang.gen.v1.urn.opendaylight.meter.types.rev130918.MeterFlags;
import org.opendaylight.yang.gen.v1.urn.opendaylight.meter.types.rev130918.MeterId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.meter.types.rev130918.band.type.band.type.Drop;
import org.opendaylight.yang.gen.v1.urn.opendaylight.meter.types.rev130918.band.type.band.type.DropBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.meter.types.rev130918.meter.MeterBandHeadersBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.meter.types.rev130918.meter.meter.band.headers.MeterBandHeader;
import org.opendaylight.yang.gen.v1.urn.opendaylight.meter.types.rev130918.meter.meter.band.headers.MeterBandHeaderBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.meter.types.rev130918.meter.meter.band.headers.MeterBandHeaderKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.meter.types.rev130918.meter.meter.band.headers.meter.band.header.MeterBandTypesBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.ethernet.match.fields.EthernetDestinationBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.ethernet.match.fields.EthernetSourceBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.ethernet.match.fields.EthernetTypeBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.match.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.match.layer._3.match.Ipv4Match;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.match.layer._3.match.Ipv4MatchBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.service.rev130709.PacketProcessingService; import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.service.rev130709.PacketReceived;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.service.rev130709.TransmitPacketInput;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.service.rev130709.TransmitPacketInputBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.PhysicalNetwork;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.PhysicalLinks;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.PhysicalPaths;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.links.PhysicalLink;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.nodes.PhysicalNode;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.paths.PhysicalPath;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.paths.PhysicalPathBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.node.instance.PhysicalPort;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.VirtualNetwork;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.arps.VirtualArp;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.arps.VirtualArpBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.links.VirtualLink;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.nodes.VirtualNode;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.paths.VirtualPath;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.VirtualPort;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.intent.vn.mapping.results.UserIntentVnMapping;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.intent.vn.mapping.results.user.intent.vn.mapping.IntentVnMappingResult;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.intent.vn.mapping.results.user.intent.vn.mapping.intent.vn.mapping.result.VirtualResource;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.vn.pn.mapping.results.UserVnPnMapping;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.vn.pn.mapping.results.user.vn.pn.mapping.VnPnMappingResult;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.IntentId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.MatchItemName;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.PropertyName;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.operations.Operation;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.User;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.flow.instance.MatchItem;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.node.instance.Property;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.match.item.instance.MatchItemValue;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.PhysicalPortId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.VirtualNetworkId;

/**
 * Created by zhangmeng on 2015/12/14.
 */
public class FlowUtilsTest extends TestCase {
    private FlowUtils flowUtils;
    private DataBroker dataBroker;
    private PacketProcessingService packetProcessingService;
    private PhyConfigLoader phyConfigLoader;
    @Before
    public void setUp() throws Exception {
        dataBroker = mock(DataBroker.class);
        packetProcessingService = mock(PacketProcessingService.class);
        phyConfigLoader = mock(PhyConfigLoader.class);
        flowUtils = new FlowUtils(dataBroker,packetProcessingService,phyConfigLoader);
    }

    @Test
    public void testInit() throws Exception {
        List<PhysicalNode> physicalNodes = new ArrayList<PhysicalNode>();
        flowUtils.init(physicalNodes);
    }

    @Test
    public void testUpdateFlowTable() throws Exception {

    }

    @Test
    public void testDeleteFlowEntries() throws Exception {

    }

    @Test
    public void testHandleArp() throws Exception {

    }

    @Test
    public void testClose() throws Exception {
        flowUtils.close();
        Assert.assertTrue(flowUtils != null);
    }

    @Test
    public void testAssignMPLSLabelForPPath() throws Exception {
        PhysicalPath physicalPath = mock(PhysicalPath.class);
        PhysicalPathId physicalPathId = mock(PhysicalPathId.class);
        PhysicalLinkId physicalLinkId = mock(PhysicalLinkId.class);
        PhysicalNodeId physicalDestNodeId = mock(PhysicalNodeId.class);
        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.links.PhysicalLink
                physicalLink = mock(org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.links.PhysicalLink.class);
        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.path.instance.PhysicalLink
                physicalLink_path = mock(org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.path.instance.PhysicalLink.class);
        List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.links.PhysicalLink>
                physicalLinksList = new ArrayList<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.links.PhysicalLink>();
        List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.path.instance.PhysicalLink>
                physicalLinksInPath = new ArrayList<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.path.instance.PhysicalLink>();

        when(physicalPath.getPhysicalLink()).thenReturn(physicalLinksInPath);
        when(physicalPath.getPathId()).thenReturn(physicalPathId);

        flowUtils.assignMPLSLabelForPPath(physicalPath, physicalLinksList);
        verify(physicalPath).getPhysicalLink();
        verify(physicalPath).getPathId();

        physicalLinksList.add(physicalLink);
        physicalLinksInPath.add(physicalLink_path);
        when(physicalLink_path.getLinkId()).thenReturn(physicalLinkId);
        when(physicalLink.getLinkId()).thenReturn(physicalLinkId);
        when(physicalLinkId.getValue())
                .thenReturn(new String("1"))
                .thenReturn(new String("2"))
                .thenReturn(new String("1"))
                .thenReturn(new String("1"));
        flowUtils.assignMPLSLabelForPPath(physicalPath, physicalLinksList);
        verify(physicalPath,times(2)).getPathId();
        verify(physicalLink).getLinkId();
        verify(physicalLink_path).getLinkId();
        verify(physicalLinkId,times(2)).getValue();

        when(physicalLink.getDestNodeId()).thenReturn(physicalDestNodeId);
        flowUtils.assignMPLSLabelForPPath(physicalPath, physicalLinksList);
        verify(physicalLinkId,times(4)).getValue();
        verify(physicalPath,times(3)).getPathId();
    }

    @Test
    public void testTheLastTwoPrivateMethod() throws Exception{
        Class<FlowUtils> class1 = FlowUtils.class;

        //test generateDefaultGatewayIpAddress
        String ipPrefix = new String("192.168.1.1/16");
        IpAddress ipAddress;

        Method method = class1.getDeclaredMethod("generateDefaultGatewayIpAddress", new Class[]{String.class});
        method.setAccessible(true);
        ipAddress =(IpAddress) method.invoke(flowUtils,new Object[]{ipPrefix});
        Assert.assertTrue(ipAddress != null);

        //test convertMacAddressToByteArray6
        MacAddress macAddress = mock(MacAddress.class);
        byte[] result;
        Method method1 = class1.getDeclaredMethod("convertMacAddressToByteArray6", new Class[]{MacAddress.class});
        method1.setAccessible(true);
        when(macAddress.getValue()).thenReturn(new String("2001:da8:215:3f0:847a:4267:b2a3:892f"));
        result = (byte[])method1.invoke(flowUtils, macAddress);
        verify(macAddress).getValue();
        Assert.assertTrue(result.length != 0);
    }

    @Test
    public void testConvertByteArray6ToMacAddress() throws Exception{
        Class<FlowUtils> class1 = FlowUtils.class;
        Method method = class1.getDeclaredMethod("convertByteArray6ToMacAddress",new Class[]{byte[].class});
        method.setAccessible(true);
        Method method1 = class1.getDeclaredMethod("convertMacAddressToByteArray6", new Class[]{MacAddress.class});
        method1.setAccessible(true);

        MacAddress result;
        MacAddress macAddress = mock(MacAddress.class);
        byte[] byteArray;

        when(macAddress.getValue()).thenReturn(new String("2001:da8:215:3f0:847a:4267:b2a3:892f"));
        byteArray = (byte[])method1.invoke(flowUtils, macAddress);
        result = (MacAddress)method.invoke(flowUtils,byteArray);
        Assert.assertTrue(result != null);
    }

    @Test
    public void testConvertByteArray4ToIpAddress() throws Exception{
        Class<FlowUtils> class1 = FlowUtils.class;
        Method method = class1.getDeclaredMethod("convertByteArray4ToIpAddress",new Class[]{byte[].class});
        method.setAccessible(true);

        IpAddress result;
        byte[] byteArray = "192.168.1.1".getBytes();
        result = (IpAddress)method.invoke(flowUtils,byteArray);
        Assert.assertTrue(result != null);
    }

    @Test
    public void testConvertIpAddressToIpPrefix() throws Exception{
        Class<FlowUtils> class1 = FlowUtils.class;
        Method method = class1.getDeclaredMethod("convertIpAddressToIpPrefix", new Class[]{IpAddress.class});
        method.setAccessible(true);

        IpAddress ipAddress = mock(IpAddress.class);
        Ipv4Address ipv4Address = mock(Ipv4Address.class);
        IpPrefix result;

        when(ipAddress.getIpv4Address()).thenReturn(ipv4Address);
        when(ipv4Address.getValue()).thenReturn(new String("192.168.1.1"));
        result = (IpPrefix)method.invoke(flowUtils,ipAddress);
        Assert.assertTrue(result != null);
    }

    @Test
    public void testConvertNodeConnectorRefToPhysicalNodeId() throws Exception{
    }
    @Test
    public void testConvertNodeConnectorRefToPhysicalPortId() throws Exception{
    }
    @Test
    public void testCreateFlowPath_CreateTablePath_CreateNodePath() throws Exception{
        Class<FlowUtils> class1 = FlowUtils.class;
        Method method = class1.getDeclaredMethod("createFlowPath", new Class[]{NodeId.class, Short.class, FlowId.class});
        method.setAccessible(true);

        NodeId nodeId = mock(NodeId.class);
        Short tableId = 1;
        FlowId flowId = mock(FlowId.class);
        InstanceIdentifier<Flow> instanceIdentifier = mock(InstanceIdentifier.class);

        instanceIdentifier = (InstanceIdentifier<Flow>) method.invoke(flowUtils,nodeId,tableId,flowId);
        Assert.assertTrue(instanceIdentifier != null);
    }

    @Test
    public void testCreateNodeRef() throws Exception{
    }
    @Test
    public void testCreateNodeId() throws Exception{
        Class<FlowUtils> class1 = FlowUtils.class;
        Method method = class1.getDeclaredMethod("createNodeId", new Class[]{PhysicalNodeId.class});
        method.setAccessible(true);

        PhysicalNodeId physicalNodeId = mock(PhysicalNodeId.class);
        NodeId result;

        when(physicalNodeId.getValue()).thenReturn(new String("test"));
        result = (NodeId) method.invoke(flowUtils,physicalNodeId);
        Assert.assertTrue(result != null);
    }
    @Test
    public void testCreateNodeConnectorRef_CreateNodeConnectorPath_CreateNodePath_CreateNodeConnectorId() throws Exception{
        Class<FlowUtils> class1 = FlowUtils.class;
        Method method = class1.getDeclaredMethod("createNodeConnectorRef", new Class[]{PhysicalNodeId.class, PhysicalPortId.class});
        method.setAccessible(true);

        PhysicalNodeId physicalNodeId = mock(PhysicalNodeId.class);
        PhysicalPortId physicalPortId = mock(PhysicalPortId.class);
        NodeConnectorRef result;

        when(physicalNodeId.getValue()).thenReturn(new String("test"));
        when(physicalPortId.getValue()).thenReturn(new String("test"));
        result = (NodeConnectorRef)method.invoke(flowUtils,physicalNodeId,physicalPortId);
        Assert.assertTrue(result != null);
    }
    @Test
    public void testBaseFlowBuilder() throws Exception{
        Class<FlowUtils> class1 = FlowUtils.class;
        Method method = class1.getDeclaredMethod("baseFlowBuilder");
        method.setAccessible(true);

        method.invoke(flowUtils);
    }
    @Test
    public void testCreateArpPacket() throws Exception{
        Class<FlowUtils> class1 = FlowUtils.class;
        Method method = class1.getDeclaredMethod("createArpPacket",new Class[]{Ethernet.class,ARP.class});
        method.setAccessible(true);

        Ethernet ethernet = mock(Ethernet.class);
        ARP arp = mock(ARP.class);
        byte[] byteArray = "192.168.1.1".getBytes();
        byte[] result;

        doNothing().when(ethernet).setRawPayload(any(byte[].class));
        when(ethernet.serialize()).thenReturn(byteArray);
        when(arp.serialize()).thenReturn(byteArray);

        result = (byte[]) method.invoke(flowUtils,ethernet,arp);
        Assert.assertTrue(result.equals(byteArray));
    }
    @Test
    public void testSendPacketOut() throws Exception{
    }
    @Test
    public void testCreateMatch_getMatchItem() throws Exception{
        Class<FlowUtils> class1 = FlowUtils.class;
        Method method = class1.getDeclaredMethod("createMatch", new Class[]{UserId.class,
                org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Flow.class,
                PhysicalPort.class});
        method.setAccessible(true);

        UserId userId = mock(UserId.class);
        Match result;
        MatchItemValue matchItemValue = mock(MatchItemValue.class);
        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Flow
                nemoFlow = mock(org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Flow.class);
        PhysicalPort physicalPort = mock(PhysicalPort.class);
        MatchItem matchItem = mock(MatchItem.class);
        PhysicalPortId physicalPortId = mock(PhysicalPortId.class);
        List<MatchItem> matchItems = new ArrayList<MatchItem>();

        matchItems.add(matchItem);

        when(nemoFlow.getMatchItem()).thenReturn(matchItems);

        //get into method "getMatchItem" args(matchitem src-ip)  args(matchitem dst-ip)
        when(matchItem.getMatchItemName())
                .thenReturn(new MatchItemName("src-ip")) //test src
                .thenReturn(new MatchItemName("dst-ip"));//test dst

        //return out
        when(matchItem.getMatchItemValue()).thenReturn(matchItemValue);
        when(matchItemValue.getStringValue()).thenReturn(new String("192.168.1.1/24"));
        when(physicalPort.getPortId()).thenReturn(physicalPortId);
        when(physicalPortId.getValue()).thenReturn(new String("test"));

        result = (Match)method.invoke(flowUtils,null,nemoFlow,physicalPort);
        Assert.assertTrue(result != null);
    }
    @Test
    public void testConfigFlowTableEntryForOperation() throws Exception{
        Class<FlowUtils> class1 = FlowUtils.class;
        Method method = class1.getDeclaredMethod("configFlowTableEntryForOperation",
                new Class[]{UserId.class,
                        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Flow.class,
                        PhysicalPort.class,
                        PhysicalPath.class,
                        short.class,
                        boolean.class});
        method.setAccessible(true);

        UserId userId = mock(UserId.class);
        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Flow
                nemoFlow = mock(org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Flow.class);
        PhysicalPort inPhysicalPort = mock(PhysicalPort.class);
        PhysicalPath outPhysicalPath = mock(PhysicalPath.class);
        short operationPriority ;
        boolean layer3Forwarding;

    }
}
