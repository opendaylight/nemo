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
import com.google.common.base.Optional;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.DataChangeListener;
import org.opendaylight.controller.md.sal.common.api.data.AsyncDataBroker.DataChangeScope;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.controller.md.sal.binding.api.WriteTransaction;
import org.opendaylight.controller.sal.binding.api.NotificationProviderService;
import org.opendaylight.nemo.renderer.openflow.FlowUtils;
import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.yang.types.rev130715.MacAddress;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.FlowCapableNodeConnector;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.Nodes;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.node.NodeConnector;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.node.NodeConnectorKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.nodes.Node;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.nodes.NodeKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.links.PhysicalLink;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.links.PhysicalLinkBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.links.PhysicalLinkKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.nodes.PhysicalNode;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.nodes.PhysicalNodeBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.nodes.PhysicalNodeKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.node.instance.PhysicalPort;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.node.instance.PhysicalPortBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.node.instance.PhysicalPortKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.PhysicalLinkId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.PhysicalNodeId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.PhysicalPortId;
import org.opendaylight.yang.gen.v1.urn.tbd.params.xml.ns.yang.network.topology.rev131021.NetworkTopology;
import org.opendaylight.yang.gen.v1.urn.tbd.params.xml.ns.yang.network.topology.rev131021.TopologyId;
import org.opendaylight.yang.gen.v1.urn.tbd.params.xml.ns.yang.network.topology.rev131021.network.topology.Topology;
import org.opendaylight.yang.gen.v1.urn.tbd.params.xml.ns.yang.network.topology.rev131021.network.topology.TopologyKey;
import org.opendaylight.yang.gen.v1.urn.tbd.params.xml.ns.yang.network.topology.rev131021.network.topology.topology.Link;
import org.opendaylight.yangtools.concepts.ListenerRegistration;
import org.opendaylight.yang.gen.v1.urn.tbd.params.xml.ns.yang.network.topology.rev131021.link.attributes.Source;
import org.opendaylight.yang.gen.v1.urn.tbd.params.xml.ns.yang.network.topology.rev131021.link.attributes.Destination;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.NodeConnectorId;
import org.opendaylight.yangtools.yang.binding.NotificationListener;
import org.opendaylight.controller.md.sal.binding.api.ReadOnlyTransaction;
import org.opendaylight.yang.gen.v1.urn.tbd.params.xml.ns.yang.network.topology.rev131021.LinkId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.NodeId;
import org.opendaylight.yang.gen.v1.urn.tbd.params.xml.ns.yang.network.topology.rev131021.TpId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.nodes.NodeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.util.concurrent.CheckedFuture;
import org.opendaylight.yang.gen.v1.urn.tbd.params.xml.ns.yang.network.topology.rev131021.network.topology.topology.LinkKey;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.CountDownLatch;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
/**
 * Created by zhangmeng on 2015/12/17.
 */
public class PhysicalNetworkAdapterTest extends TestCase {
    private DataBroker dataBroker;
    private NotificationProviderService notificationProviderService;
    private PhyConfigLoader phyConfigLoader;
    private FlowUtils ofFlowUtils;
    private PhysicalNetworkAdapter physicalNetworkAdapter;
    private ListenerRegistration<NotificationListener> ofPacketInListenerReg;
    private ListenerRegistration<DataChangeListener> ofNodesListenerReg;
    private ListenerRegistration<DataChangeListener> ofLinksListenerReg;
    @Before
    public void setUp() throws Exception {
        ofLinksListenerReg = mock(ListenerRegistration.class);
        ofPacketInListenerReg = mock(ListenerRegistration.class);
        ofNodesListenerReg = mock(ListenerRegistration.class);
        dataBroker = mock(DataBroker.class);
        notificationProviderService = mock(NotificationProviderService.class);
        phyConfigLoader = mock(PhyConfigLoader.class);
        ofFlowUtils = mock(FlowUtils.class);

        CheckedFuture checkedFuture = mock(CheckedFuture.class);
        ReadOnlyTransaction readOnlyTransaction = mock(ReadOnlyTransaction.class);

        when(dataBroker.newReadOnlyTransaction()).thenReturn(readOnlyTransaction);
        when(readOnlyTransaction.read(any(LogicalDatastoreType.class), any(InstanceIdentifier.class)))
                .thenReturn(checkedFuture);
        when(dataBroker.registerDataChangeListener(
                any(LogicalDatastoreType.class),
                any(InstanceIdentifier.class),
                any(OFNodeListener.class),
                any(DataChangeScope.class)))
                .thenReturn(ofNodesListenerReg)
                .thenReturn(ofLinksListenerReg);
        when(notificationProviderService.registerNotificationListener(any(OFPacketInListener.class))).thenReturn(ofPacketInListenerReg);

        physicalNetworkAdapter =  new PhysicalNetworkAdapter(dataBroker,
                notificationProviderService,
                phyConfigLoader,
                ofFlowUtils);

        verify(dataBroker,times(2)).newReadOnlyTransaction();
        verify(readOnlyTransaction,times(2)).read(any(LogicalDatastoreType.class), any(InstanceIdentifier.class));
        verify(dataBroker,times(2)).registerDataChangeListener(
                any(LogicalDatastoreType.class),
                any(InstanceIdentifier.class),
                any(OFNodeListener.class),
                any(DataChangeScope.class));
    }

    @Test
    public void testClose() throws Exception {
        physicalNetworkAdapter.close();
    }

    @Test
    public void testGetPhyConfigLoader() throws Exception {
        Assert.assertTrue(physicalNetworkAdapter.getPhyConfigLoader() == phyConfigLoader);
    }

    @Test
    public void testOfNodeAdded() throws Exception {
        Node node = mock(Node.class);
        NodeKey nodeKey = mock(NodeKey.class);
        NodeId nodeId = mock(NodeId.class);
        NodeConnector nodeConnector = mock(NodeConnector.class);
        List<NodeConnector> nodeConnectors = new ArrayList<NodeConnector>();
        PhysicalNode physicalNode = mock(PhysicalNode.class);
        WriteTransaction writeTransaction = mock(WriteTransaction.class);
        CheckedFuture checkedFuture_write = mock(CheckedFuture.class);
        NodeConnectorId nodeConnectorId = mock(NodeConnectorId.class);
        NodeConnectorKey nodeConnectorKey = mock(NodeConnectorKey.class);
        CheckedFuture checkedFuture_read = mock(CheckedFuture.class);
        ReadOnlyTransaction readOnlyTransaction = mock(ReadOnlyTransaction.class);

        when(node.getKey()).thenReturn(nodeKey);
        when(node.getId()).thenReturn(nodeId);
        when(nodeId.getValue()).thenReturn(new String("test"));//strNodeId
        when(node.getNodeConnector()).thenReturn(nodeConnectors);//size = 0
        when(phyConfigLoader.getPhysicalNode(any(PhysicalNodeId.class)))
                .thenReturn(null)
                .thenReturn(physicalNode);
        when(dataBroker.newWriteOnlyTransaction()).thenReturn(writeTransaction);
        when(writeTransaction.submit()).thenReturn(checkedFuture_write);

        physicalNetworkAdapter.ofNodeAdded(node);
        physicalNetworkAdapter.ofNodeAdded(node);
        verify(phyConfigLoader,times(2)).getPhysicalNode(any(PhysicalNodeId.class));
        verify(writeTransaction,times(2)).submit();
        verify(node,times(3)).getKey();
        //test nodeConnectors not null
        //get into method "getPhysicalPort" args(nodekey ,nodeConnector)
        nodeConnectors.add(nodeConnector);
        when(nodeConnector.getId()).thenReturn(nodeConnectorId);
        when(nodeConnectorId.getValue())
                .thenReturn(new String("LOCAL"))
                .thenReturn(new String("test1"));
        when(nodeConnector.getKey()).thenReturn(nodeConnectorKey);
        physicalNetworkAdapter.ofNodeAdded(node);
        verify(node,times(5)).getKey();
        ////get into method "getOFPort" args(nodekey nodeConnectorKey )
        when(dataBroker.newReadOnlyTransaction()).thenReturn(readOnlyTransaction);
        when(readOnlyTransaction.read(any(LogicalDatastoreType.class),any(InstanceIdentifier.class))).thenReturn(checkedFuture_read);
        //////get into method "getOFPortInstanceIdentifier" args(nodekey nodeConnectorKey )
        //return to method "getPhysicalPort"

//        physicalNetworkAdapter.ofNodeAdded(node);


    }
///////////////////////////// ////////////////////////////////////////////////
    @Test
    public void testOfNodeRemoved() throws Exception {
        Class<PhysicalNetworkAdapter> class1 = PhysicalNetworkAdapter.class;
        Method method = class1.getDeclaredMethod("ofNodeRemoved", new Class[]{Node.class});
        method.setAccessible(true);

        Node node = mock(Node.class);
        NodeKey nodeKey = mock(NodeKey.class);
        NodeId nodeId = mock(NodeId.class);
        PhysicalNode physicalNode = mock(PhysicalNode.class);
        WriteTransaction writeTransaction = mock(WriteTransaction.class);
        CheckedFuture checkedFuture = mock(CheckedFuture.class);

        when(node.getKey()).thenReturn(nodeKey);
        when(node.getId()).thenReturn(nodeId);
        when(nodeId.getValue()).thenReturn(new String("test"));
        when(phyConfigLoader.getPhysicalNode(any(PhysicalNodeId.class)))
                .thenReturn(null)
                .thenReturn(physicalNode);
        when(dataBroker.newWriteOnlyTransaction()).thenReturn(writeTransaction);
        when(writeTransaction.submit()).thenReturn(checkedFuture);

        physicalNetworkAdapter.ofNodeRemoved(node);
        physicalNetworkAdapter.ofNodeRemoved(node);
        verify(phyConfigLoader,times(2)).getPhysicalNode(any(PhysicalNodeId.class));
        verify(writeTransaction,times(2)).submit();
    }

    @Test
    public void testOfLinkAdded() throws Exception {
        Class<PhysicalNetworkAdapter> class1 = PhysicalNetworkAdapter.class;
        Field field = class1.getDeclaredField("running");
        field.setAccessible(true);

        Assert.assertTrue((Boolean) field.get(physicalNetworkAdapter) == false);

        Link link = mock(Link.class);
        LinkKey linkKey = mock(LinkKey.class);
        LinkId linkId = mock(LinkId.class);
        Source source = mock(Source.class);
        Destination destination = mock(Destination.class);
        org.opendaylight.yang.gen.v1.urn.tbd.params.xml.ns.yang.network.topology.rev131021.NodeId
                nodeId = mock(org.opendaylight.yang.gen.v1.urn.tbd.params.xml.ns.yang.network.topology.rev131021.NodeId.class);
        TpId tpId = mock(TpId.class);
        PhysicalLink physicalLink = mock(PhysicalLink.class);

        when(link.getKey()).thenReturn(linkKey);
        when(link.getLinkId()).thenReturn(linkId);
        when(linkId.getValue()).thenReturn(new String("test"));//strLinkId

        when(link.getSource()).thenReturn(source);
        when(link.getDestination()).thenReturn(destination);
        when(source.getSourceNode()).thenReturn(nodeId);
        when(source.getSourceTp()).thenReturn(tpId);
        when(destination.getDestNode()).thenReturn(nodeId);
        when(destination.getDestTp()).thenReturn(tpId);
        when(nodeId.getValue())
                .thenReturn(new String("srcNode"))//srcNode
                .thenReturn(new String("dstNode"));//dstNode
        when(tpId.getValue())
                .thenReturn(new String("srcTp"))//srcTp
                .thenReturn(new String("dstTp"));//dstTp


        when(phyConfigLoader.getPhysicalLink(any(PhysicalLinkId.class)))
                .thenReturn(null)
                .thenReturn(physicalLink);
        when(physicalLink.getMetric()).thenReturn(1L);

        physicalNetworkAdapter.ofLinkAdded(link);
        Assert.assertTrue((Boolean)field.get(physicalNetworkAdapter) == true);
        physicalNetworkAdapter.ofLinkAdded(link);
        verify(physicalLink).getMetric();

        physicalNetworkAdapter.close();
    }

    @Test
    public void testOfLinkRemoved() throws Exception {
        Class<PhysicalNetworkAdapter> class1 = PhysicalNetworkAdapter.class;
        Method method = class1.getDeclaredMethod("ofLinkRemoved", new Class[]{Link.class});
        method.setAccessible(true);

        Link link = mock(Link.class);
        LinkKey linkKey = mock(LinkKey.class);
        LinkId linkId = mock(LinkId.class);
        PhysicalLink physicalLink = mock(PhysicalLink.class);
        WriteTransaction writeTransaction = mock(WriteTransaction.class);
        InstanceIdentifier instanceIdentifier = mock(InstanceIdentifier.class);
        CheckedFuture checkedFuture = mock(CheckedFuture.class);

        when(link.getKey()).thenReturn(linkKey);
        when(link.getLinkId()).thenReturn(linkId);
        when(linkId.getValue()).thenReturn(new String("1"));
        when(phyConfigLoader.getPhysicalLink(any(PhysicalLinkId.class)))
                .thenReturn(null)
                .thenReturn(physicalLink);
        when(dataBroker.newWriteOnlyTransaction()).thenReturn(writeTransaction);
        when(writeTransaction.submit()).thenReturn(checkedFuture);

//        physicalNetworkAdapter.ofLinkRemoved(link);
//        physicalNetworkAdapter.ofLinkRemoved(link);
        method.invoke(physicalNetworkAdapter, link);
        method.invoke(physicalNetworkAdapter, link);

        verify(phyConfigLoader,times(2)).getPhysicalLink(any(PhysicalLinkId.class));
        verify(writeTransaction,times(2)).submit();
    }

    @Test
    public void testGetOFPortInstanceIdentifier() throws Exception{
        Class<PhysicalNetworkAdapter> class1 = PhysicalNetworkAdapter.class;
        Method method = class1.getDeclaredMethod("getOFPortInstanceIdentifier", new Class[]{NodeKey.class,NodeConnectorKey.class});
        method.setAccessible(true);

        NodeKey nodeKey = mock(NodeKey.class);
        NodeConnectorKey connectorKey = mock(NodeConnectorKey.class);
        InstanceIdentifier<NodeConnector> result;

        result = (InstanceIdentifier<NodeConnector>)method.invoke(physicalNetworkAdapter,nodeKey,connectorKey);
        Assert.assertTrue(result != null);

    }

    @Test
    public void testGetOFPort() throws Exception{
        Class<PhysicalNetworkAdapter> class1 = PhysicalNetworkAdapter.class;
        Method method = class1.getDeclaredMethod("getOFPort", new Class[]{NodeKey.class,NodeConnectorKey.class});
        method.setAccessible(true);

        NodeKey nodeKey = mock(NodeKey.class);
        NodeConnectorKey connectorKey = mock(NodeConnectorKey.class);
        CheckedFuture checkedFuture_read = mock(CheckedFuture.class);
        ReadOnlyTransaction readOnlyTransaction = mock(ReadOnlyTransaction.class);

        when(dataBroker.newReadOnlyTransaction()).thenReturn(readOnlyTransaction);
        when(readOnlyTransaction.read(any(LogicalDatastoreType.class),any(InstanceIdentifier.class))).thenReturn(checkedFuture_read);

//        method.invoke(physicalNetworkAdapter,nodeKey,connectorKey);
    }
}
