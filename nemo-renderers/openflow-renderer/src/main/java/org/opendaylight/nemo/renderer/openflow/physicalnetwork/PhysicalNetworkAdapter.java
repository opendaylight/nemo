/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.nemo.renderer.openflow.physicalnetwork;

import com.google.common.base.Optional;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.DataChangeListener;
import org.opendaylight.controller.md.sal.common.api.data.AsyncDataBroker.DataChangeScope;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
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
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.opendaylight.yangtools.yang.binding.NotificationListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.CountDownLatch;

public class PhysicalNetworkAdapter {
    private static final Logger log = LoggerFactory.getLogger(PhysicalNetworkAdapter.class);
    private static final String DEFAULT_TOPOLOGY_ID = "flow:1";

    final private DataBroker dataBroker;
    private PhyConfigLoader phyConfigLoader;
    private DataBrokerAdapter dataBrokerAdapter;
    private PhysicalFlowUtils physicalFlowUtils;
    private FlowUtils ofFlowUtils;
    private NotificationProviderService notificationProviderService;

    private CopyOnWriteArraySet<String> nodeIdSet;
    private CopyOnWriteArraySet<PhysicalLink> physicalLinkSet;
    private Timer phyTimer;
    private boolean running = false;
    private Integer mutex = 0;
    private ListenerRegistration<NotificationListener> ofPacketInListenerReg;
    private ListenerRegistration<DataChangeListener> ofNodesListenerReg;
    private ListenerRegistration<DataChangeListener> ofLinksListenerReg;

    public PhysicalNetworkAdapter(DataBroker dataBroker
            , NotificationProviderService notificationProviderService
            , PhyConfigLoader phyConfigLoader
            , FlowUtils ofFlowUtils) {
        this.dataBroker = dataBroker;
        this.notificationProviderService = notificationProviderService;
        this.ofFlowUtils = ofFlowUtils;
        this.phyConfigLoader = phyConfigLoader;
        this.dataBrokerAdapter = new DataBrokerAdapter(dataBroker);
        physicalFlowUtils = new PhysicalFlowUtils(dataBroker);
        nodeIdSet = new CopyOnWriteArraySet<String>();
        physicalLinkSet = new CopyOnWriteArraySet<PhysicalLink>();

        phyTimer = new Timer();


        registerListeners();
        initOFNodes();
        initOFLinks();
    }

    public void close() {
        if (ofPacketInListenerReg != null)
            ofPacketInListenerReg.close();
        if (ofLinksListenerReg != null)
            ofLinksListenerReg.close();
        if (ofNodesListenerReg != null)
            ofNodesListenerReg.close();
        if (phyConfigLoader != null)
            phyConfigLoader.close();
        log.debug("Clear....\r\n{}", nodeIdSet);
        nodeIdSet.clear();
        physicalLinkSet.clear();
    }

    public PhyConfigLoader getPhyConfigLoader() {
        return phyConfigLoader;
    }

    /**
     * OFNode instance identifier
     *
     * @return
     */
    private InstanceIdentifier<Node> getOFNodeInstanceIdentifier() {
        return InstanceIdentifier.builder(Nodes.class).child(Node.class).build();
    }

    private InstanceIdentifier<Link> getOFLinkInstanceIdentifier() {
        return InstanceIdentifier.builder(NetworkTopology.class).child(Topology.class, new TopologyKey(new TopologyId(DEFAULT_TOPOLOGY_ID))).child(Link.class).build();
    }

    private InstanceIdentifier<Topology> getOFTopologyInstanceIdentifier() {
        return InstanceIdentifier.builder(NetworkTopology.class).child(Topology.class, new TopologyKey(new TopologyId(DEFAULT_TOPOLOGY_ID))).build();
    }

    private InstanceIdentifier<Nodes> getOFNodesInstanceIdentifier() {
        return InstanceIdentifier.builder(Nodes.class).build();
    }

    private InstanceIdentifier<NodeConnector> getOFPortInstanceIdentifier(NodeKey nodeKey, NodeConnectorKey connectorKey) {
        return InstanceIdentifier.builder(Nodes.class).child(Node.class, nodeKey).child(NodeConnector.class, connectorKey).build();
    }

    private void registerListeners() {
        InstanceIdentifier<Node> nodeInsId = getOFNodeInstanceIdentifier();
        InstanceIdentifier<Link> linkInsId = getOFLinkInstanceIdentifier();
        ofNodesListenerReg = dataBroker.registerDataChangeListener(LogicalDatastoreType.OPERATIONAL, nodeInsId, new OFNodeListener(this), DataChangeScope.SUBTREE);
        ofLinksListenerReg = dataBroker.registerDataChangeListener(LogicalDatastoreType.OPERATIONAL, linkInsId, new OFLinkListener(this), DataChangeScope.SUBTREE);
        ofPacketInListenerReg = notificationProviderService.registerNotificationListener(new OFPacketInListener(ofFlowUtils));
    }

    private void initOFLinks() {
        InstanceIdentifier<Topology> topologyInsId = getOFTopologyInstanceIdentifier();
        ListenableFuture<Optional<Topology>> topologyFuture = dataBroker.newReadOnlyTransaction().read(LogicalDatastoreType.OPERATIONAL, topologyInsId);
        Futures.addCallback(topologyFuture, new FutureCallback<Optional<Topology>>() {
            @Override
            public void onSuccess(Optional<Topology> result) {
                if (result.isPresent() && result.get() instanceof Topology) {
                    Topology topology = result.get();
                    if (topology != null && topology.getLink() != null)
                        for (Link link : topology.getLink()) {
                            ofLinkAdded(link);
                        }
                }

                return;
            }

            @Override
            public void onFailure(Throwable t) {
                log.error("Can not read the link info of topology {}: {}", DEFAULT_TOPOLOGY_ID, t);
                return;
            }
        });
    }

    private void initOFNodes() {
        InstanceIdentifier<Nodes> nodesInsId = getOFNodesInstanceIdentifier();
        ListenableFuture<Optional<Nodes>> nodesFuture = dataBroker.newReadOnlyTransaction().read(LogicalDatastoreType.OPERATIONAL, nodesInsId);
        Futures.addCallback(nodesFuture, new FutureCallback<Optional<Nodes>>() {
            @Override
            public void onSuccess(Optional<Nodes> result) {
                if (result.isPresent() && result.get() instanceof Nodes) {
                    Nodes nodes = result.get();
                    if (nodes != null && nodes.getNode() != null)
                        for (Node node : nodes.getNode()) {
                            ofNodeAdded(node);
                        }
                }
                return;
            }

            @Override
            public void onFailure(Throwable t) {
                log.error("Can not read node information: {}", t);
                return;
            }
        });

    }

    protected void ofNodeAdded(Node node) {
        log.debug("OF node added: {}.", node.getKey());
        String strNodeId = node.getId().getValue();
        // Add default flow entry. - Don't do this here, because it will
        // result in that the openflow plugin can't discover the topology.
        // Flow entries for LLDP are deployed manually through a shell script.
        // Flow entries for ARP are added in class FlowUtils.
//        physicalFlowUtils.configArpPEntry(strNodeId);
//        physicalFlowUtils.configLLDPEntry(strNodeId);

        PhysicalNodeId nodeId = new PhysicalNodeId(strNodeId);
        PhysicalNodeBuilder nodeBuilder = new PhysicalNodeBuilder();
        nodeBuilder.setNodeId(nodeId);
        nodeBuilder.setKey(new PhysicalNodeKey(nodeId));
        List<PhysicalPort> physicalPortList = new ArrayList<PhysicalPort>();
        List<NodeConnector> nodeConnectors = node.getNodeConnector();
        if (nodeConnectors == null || nodeConnectors.size() == 0) {
            log.error("Node : {}, without port.", strNodeId);
        }
        if (nodeConnectors != null) {
            for (NodeConnector nodeConnector : nodeConnectors) {
                PhysicalPort physicalPort = getPhysicalPort(node.getKey(), nodeConnector);
                if (physicalPort != null) {
                    physicalPortList.add(physicalPort);
                }
            }
        }
        nodeBuilder.setPhysicalPort(physicalPortList);

        PhysicalNode confPhyNode = phyConfigLoader.getPhysicalNode(nodeId);
        if (confPhyNode != null) {
            nodeBuilder.setNodeType(confPhyNode.getNodeType());
            nodeBuilder.setAttribute(confPhyNode.getAttribute());
        } else {
            log.warn("Find one OF Node {},does not have info in config file.", node.getKey());
        }
        boolean result = dataBrokerAdapter.addPhysicalNode(nodeBuilder.build());
//        if (result) {
        nodeIdSet.add(strNodeId);
        log.debug("Add....{}\r\n{}", strNodeId, nodeIdSet);
//        }
    }

    protected void ofNodeRemoved(Node node) {
        log.debug("OF node removed: {}.", node.getKey());
        String strNodeId = node.getId().getValue();
        PhysicalNodeId nodeId = new PhysicalNodeId(strNodeId);
        PhysicalNode confPhyNode = phyConfigLoader.getPhysicalNode(nodeId);
        if (confPhyNode == null) {
            log.warn("Find one OF Node removed {},does not have info in config file.", node.getKey());
        }
        boolean result = dataBrokerAdapter.removePhysicalNode(new PhysicalNodeKey(nodeId));
//        if (result) {
        nodeIdSet.remove(strNodeId);
        log.debug("Remove....{}\r\n{}", strNodeId, nodeIdSet);
//        }
    }

    private PhysicalPort getPhysicalPort(NodeKey nodeKey, NodeConnector nodeConnector) {
        String strConnectorId = nodeConnector.getId().getValue();
        if (strConnectorId.contains("LOCAL"))
            return null;
        PhysicalPortId physicalPortId = new PhysicalPortId(strConnectorId);
        log.debug("Get port {} : {}.", nodeKey, nodeConnector.getId().getValue());
        FlowCapableNodeConnector flowCapableNodeConnector = getOFPort(nodeKey, nodeConnector.getKey());
        if (flowCapableNodeConnector != null) {
            PhysicalPortBuilder physicalPortBuilder = new PhysicalPortBuilder();
            physicalPortBuilder.setPortId(physicalPortId);
            physicalPortBuilder.setKey(new PhysicalPortKey(physicalPortId));
            physicalPortBuilder.setBandwidth(PhyConfigLoader.DEFAULT_LINK_BANDWIDTH);
            MacAddress macAddress = flowCapableNodeConnector.getHardwareAddress();
            physicalPortBuilder.setMacAddress(macAddress);

            PhysicalPort confPhyPort = phyConfigLoader.getPhysicalPort(physicalPortId);
            if (confPhyPort != null) {
                log.debug("Set port {} : {}.\r\n {} \r\n{}", nodeKey, nodeConnector.getId().getValue(), confPhyPort.getPortType().toString(), confPhyPort.getAttribute());
//                long bandwidth = flowCapableNodeConnector.getMaximumSpeed() > 0 ? flowCapableNodeConnector.getMaximumSpeed() : confPhyPort.getBandwidth();
                physicalPortBuilder.setPortType(confPhyPort.getPortType());
                physicalPortBuilder.setAttribute(confPhyPort.getAttribute());
            } else {
                log.warn("Can not get config info of {}-{} form data broker.", nodeKey.getId(), strConnectorId);
            }
            return physicalPortBuilder.build();
        } else {
            log.warn("Can not read OF port info of {}-{} form .", nodeKey.getId(), strConnectorId);
        }
        return null;
    }

    private FlowCapableNodeConnector getOFPort(final NodeKey nodeKey, final NodeConnectorKey nodeConnectorKey) {
        final FlowCapableNodeConnector[] flowCapableNodeConnector = {null};

        InstanceIdentifier<NodeConnector> nodeConnectorInsId = getOFPortInstanceIdentifier(nodeKey, nodeConnectorKey);
        final CountDownLatch downLatch = new CountDownLatch(1);
        ListenableFuture<Optional<NodeConnector>> nodeConnectorFuture = dataBroker.newReadOnlyTransaction().read(LogicalDatastoreType.OPERATIONAL, nodeConnectorInsId);
        Futures.addCallback(nodeConnectorFuture, new FutureCallback<Optional<NodeConnector>>() {
            @Override
            public void onSuccess(Optional<NodeConnector> result) {
                if (result.isPresent() && result.get() instanceof NodeConnector) {
                    flowCapableNodeConnector[0] = result.get().getAugmentation(FlowCapableNodeConnector.class);
                    downLatch.countDown();
                }

                return;
            }

            @Override
            public void onFailure(Throwable t) {
                log.error("Can not read the information of node connector {}-{} : {}", nodeKey, nodeConnectorKey, t);
                downLatch.countDown();
                return;
            }
        });

        try {
            downLatch.await();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            log.error("Exception:",e);
        }

        return flowCapableNodeConnector[0];
    }

    protected void ofLinkAdded(Link link) {
        log.debug("OF link added:{}.", link.getKey());

        String srcNode = link.getSource().getSourceNode().getValue();
        String srcTp = link.getSource().getSourceTp().getValue();
        String dstNode = link.getDestination().getDestNode().getValue();
        String dstTp = link.getDestination().getDestTp().getValue();

        String strLinkId = link.getLinkId().getValue();
        PhysicalLinkBuilder linkBuilder = new PhysicalLinkBuilder();
        linkBuilder.setLinkId(new PhysicalLinkId(strLinkId));
        linkBuilder.setSrcNodeId(new PhysicalNodeId(srcNode));
        linkBuilder.setSrcPortId(new PhysicalPortId(srcTp));
        linkBuilder.setDestNodeId(new PhysicalNodeId(dstNode));
        linkBuilder.setDestPortId(new PhysicalPortId(dstTp));

        linkBuilder.setBandwidth(PhyConfigLoader.DEFAULT_LINK_BANDWIDTH);
        linkBuilder.setDelay(PhyConfigLoader.DEFAULT_LINK_DELAY);
        linkBuilder.setLossRate(PhyConfigLoader.DEFAULT_LINK_LOSS_RATE);

        PhysicalLinkId physicalLinkId = new PhysicalLinkId(strLinkId);
        PhysicalLink physicalLink = phyConfigLoader.getPhysicalLink(physicalLinkId);
        if (physicalLink != null) {
            linkBuilder.setMetric(physicalLink.getMetric());
        } else {
            log.warn("Can not find conf info of {}.", link.getKey());
        }
        synchronized (mutex) {
            physicalLinkSet.add(linkBuilder.build());
            if (!running) {
                phyTimer.schedule(new PhyTransmit(), 10, 500);
                running = true;
            }
        }
    }

    protected void ofLinkRemoved(Link link) {
        log.debug("OF link removed:{}.", link.getKey());
        String strLinkId = link.getLinkId().getValue();
        PhysicalLinkId linkId = new PhysicalLinkId(strLinkId);
        PhysicalLink confPhyLink = phyConfigLoader.getPhysicalLink(linkId);
        if (confPhyLink == null) {
            log.warn("Can not find conf info of {} while remove.", link);
        }

        dataBrokerAdapter.removePhysicalLink(new PhysicalLinkKey(linkId));
    }

    class PhyTransmit extends TimerTask {

        @Override
        public void run() {
            synchronized (mutex) {
                for (PhysicalLink physicalLink : physicalLinkSet) {
                    handleLink(physicalLink);
                }
                // Cancel timer. - Don't cancel timer, because this will result in
                // that some physical links aren't wrote into data store sometimes.
//                if (physicalLinkSet.size() == 0) {
//                    phyTimer.cancel();
//                    running = false;
//                }
            }
        }

        private void handleLink(PhysicalLink physicalLink) {
            String srcNodeId = physicalLink.getSrcNodeId().getValue();
            String dsrNodeId = physicalLink.getDestNodeId().getValue();
            if (nodeIdSet.contains(srcNodeId) && nodeIdSet.contains(dsrNodeId)) {
                physicalLinkSet.remove(physicalLink);
                log.debug("Put [{}]-[{}] to data broker.", srcNodeId, dsrNodeId);
                dataBrokerAdapter.addPhysicalLink(physicalLink);
            }
        }
    }
}
