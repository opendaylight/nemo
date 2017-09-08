/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.nemo.intent.computation;

import com.google.common.base.Optional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.DataObjectModification;
import org.opendaylight.controller.md.sal.binding.api.DataTreeChangeListener;
import org.opendaylight.controller.md.sal.binding.api.DataTreeIdentifier;
import org.opendaylight.controller.md.sal.binding.api.DataTreeModification;
import org.opendaylight.controller.md.sal.binding.api.ReadOnlyTransaction;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.nemo.intent.algorithm.Edge;
import org.opendaylight.nemo.intent.algorithm.RoutingAlgorithm;
import org.opendaylight.nemo.intent.algorithm.Vertex;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.PhysicalNetwork;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.PhysicalLinks;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.PhysicalNodes;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.PhysicalPaths;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.links.PhysicalLink;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.nodes.PhysicalNode;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.paths.PhysicalPath;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.paths.PhysicalPathBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.path.instance.PhysicalLinkBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.PhysicalLinkId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.PhysicalNodeId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.PhysicalPathId;
import org.opendaylight.yangtools.concepts.ListenerRegistration;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The physical network computation unit implements the following functions:
 * (1) Maintain the underlying physical network topology information through
 *     subscribing from the data store.
 * (2) Provide the tunnel computation with SLA constraints to the virtual
 *     network mapping computation unit.
 *
 * @author Zhigang Ji
 */
public class PNComputationUnit implements AutoCloseable {
    private static final Logger LOG = LoggerFactory.getLogger(PNComputationUnit.class);

    /**
     * The routing algorithm instance.
     */
    private RoutingAlgorithm routingAlgorithm;

    /**
     * The registration for the physical node change listener.
     */
    private ListenerRegistration<?> physicalNodeChangeListenerReg;

    /**
     * The registration for the physical link change listener.
     */
    private ListenerRegistration<?> physicalLinkChangeListenerReg;

    /**
     * The registration for the physical path change listener.
     */
    private ListenerRegistration<?> physicalPathChangeListenerReg;

    public PNComputationUnit(DataBroker dataBroker) {
        super();

        routingAlgorithm = new RoutingAlgorithm();

        InstanceIdentifier<PhysicalNode> physicalNodeIid = InstanceIdentifier
                .builder(PhysicalNetwork.class)
                .child(PhysicalNodes.class)
                .child(PhysicalNode.class)
                .build();
        InstanceIdentifier<PhysicalLink> physicalLinkIid = InstanceIdentifier
                .builder(PhysicalNetwork.class)
                .child(PhysicalLinks.class)
                .child(PhysicalLink.class)
                .build();
        InstanceIdentifier<PhysicalPath> physicalPathIid = InstanceIdentifier
                .builder(PhysicalNetwork.class)
                .child(PhysicalPaths.class)
                .child(PhysicalPath.class)
                .build();

        physicalNodeChangeListenerReg = dataBroker.registerDataTreeChangeListener(new DataTreeIdentifier<>(
                LogicalDatastoreType.OPERATIONAL, physicalNodeIid), new PhysicalNodeChangeListener());
        physicalLinkChangeListenerReg = dataBroker.registerDataTreeChangeListener(new DataTreeIdentifier<>(
                LogicalDatastoreType.OPERATIONAL, physicalLinkIid), new PhysicalLinkChangeListener());
        physicalPathChangeListenerReg = dataBroker.registerDataTreeChangeListener(new DataTreeIdentifier<>(
                LogicalDatastoreType.OPERATIONAL, physicalPathIid), new PhysicalPathChangeListener());

        ReadOnlyTransaction readOnlyTransaction = dataBroker.newReadOnlyTransaction();

        InstanceIdentifier<PhysicalNodes> physicalNodesIid = InstanceIdentifier
                .builder(PhysicalNetwork.class)
                .child(PhysicalNodes.class)
                .build();
        Optional<PhysicalNodes> result;

        try {
            result = readOnlyTransaction.read(LogicalDatastoreType.OPERATIONAL, physicalNodesIid).get();
        } catch ( InterruptedException exception ) {
            throw new RuntimeException("Can not read the physical nodes.");
        } catch ( ExecutionException exception ) {
            throw new RuntimeException("Can not read the physical nodes.");
        }

        if ( result.isPresent() ) {
            PhysicalNodes physicalNodes = result.get();
            Vertex vertex;

            for ( PhysicalNode physicalNode : physicalNodes.getPhysicalNode() ) {
                vertex = new Vertex(physicalNode.getNodeId().getValue());
                routingAlgorithm.addVertex(vertex);
            }
        }

        InstanceIdentifier<PhysicalLinks> physicalLinksIid = InstanceIdentifier
                .builder(PhysicalNetwork.class)
                .child(PhysicalLinks.class)
                .build();
        Optional<PhysicalLinks> result1;

        try {
            result1 = readOnlyTransaction.read(LogicalDatastoreType.OPERATIONAL, physicalLinksIid).get();
        } catch ( InterruptedException exception ) {
            throw new RuntimeException("Can not read the physical links.");
        } catch ( ExecutionException exception ) {
            throw new RuntimeException("Can not read the physical links.");
        }

        if ( result1.isPresent() ) {
            PhysicalLinks physicalLinks = result1.get();
            Edge edge;

            for ( PhysicalLink physicalLink : physicalLinks.getPhysicalLink() ) {
                edge = new Edge(physicalLink);
                routingAlgorithm.addEdge(edge);
            }
        }

        LOG.debug("Initialized the physical network computation unit.");

        return;
    }

    /**
     * Compute a shortest physical path from the given source vertex to
     * target one without any constraint.
     *
     * @param source The given source physical node id.
     * @param target The given target physical node id.
     * @return The physical path if successful，or null otherwise.
     */
    protected PhysicalPath computePath(PhysicalNodeId source, PhysicalNodeId target) {
        List<Edge> edges = routingAlgorithm.computePath(routingAlgorithm.getVertex(source.getValue()),
                routingAlgorithm.getVertex(target.getValue()));

        if ( null == edges || edges.isEmpty() ) {
            return null;
        }

        List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.path.instance.PhysicalLink> physicalLinks =
                new ArrayList<>(edges.size());
        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.path.instance.PhysicalLink physicalLink;
        long metric = 0;
        long delay = 0;

        for ( Edge edge : edges ) {
            physicalLink = new PhysicalLinkBuilder()
                    .setLinkId(new PhysicalLinkId(edge.getId()))
                    .setOrder((long) physicalLinks.size())
                    .build();
            physicalLinks.add(physicalLink);

            metric += edge.getMetric();
//            delay += edge.getDelay();
        }

        PhysicalPath physicalPath = new PhysicalPathBuilder()
                .setPathId(new PhysicalPathId(UUID.randomUUID().toString()))
                .setPhysicalLink(physicalLinks)
                .setMetric(metric)
                .setBandwidth(0L)
                .setDelay(delay)
                .build();

        return physicalPath;
    }

    /**
     * Compute a shortest physical path with the given bandwidth from the
     * given source vertex to target one.
     *
     * @param source The given source physical node id.
     * @param target The given target physical node id.
     * @param bandwidth The given bandwidth for the physical path.
     * @return The physical path if successful，or null otherwise.
     */
    protected PhysicalPath computePath(PhysicalNodeId source, PhysicalNodeId target, long bandwidth) {
        List<Edge> edges = routingAlgorithm.computePath(routingAlgorithm.getVertex(source.getValue()),
                routingAlgorithm.getVertex(target.getValue()), bandwidth);

        if ( null == edges || edges.isEmpty() ) {
            return null;
        }

        List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.path.instance.PhysicalLink> physicalLinks =
                new ArrayList<>(edges.size());
        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.path.instance.PhysicalLink physicalLink;
        long metric = 0;
        long delay = 0;

        for ( Edge edge : edges ) {
            edge.setBandwidth(edge.getBandwidth() - bandwidth);
            routingAlgorithm.updateEdge(edge);

            physicalLink = new PhysicalLinkBuilder()
                    .setLinkId(new PhysicalLinkId(edge.getId()))
                    .setOrder((long) physicalLinks.size())
                    .build();
            physicalLinks.add(physicalLink);

            metric += edge.getMetric();
//            delay += edge.getDelay();
        }

        PhysicalPath physicalPath = new PhysicalPathBuilder()
                .setPathId(new PhysicalPathId(UUID.randomUUID().toString()))
                .setPhysicalLink(physicalLinks)
                .setMetric(metric)
                .setBandwidth(bandwidth)
                .setDelay(delay)
                .build();

        return physicalPath;
    }

    @Override
    public void close() throws Exception {
        if ( null != physicalNodeChangeListenerReg ) {
            physicalNodeChangeListenerReg.close();
        }

        if ( null != physicalLinkChangeListenerReg ) {
            physicalLinkChangeListenerReg.close();
        }

        if ( null != physicalPathChangeListenerReg ) {
            physicalPathChangeListenerReg.close();
        }

        return;
    }

    /**
     * A listener to change events related to physical nodes being
     * added, removed or updated.
     *
     * @author Zhigang Ji
     */
    private class PhysicalNodeChangeListener implements DataTreeChangeListener<PhysicalNode> {
        @Override
        public void onDataTreeChanged(Collection<DataTreeModification<PhysicalNode>> changes) {
            for (DataTreeModification<PhysicalNode> change: changes) {
                DataObjectModification<PhysicalNode> rootNode = change.getRootNode();
                switch (rootNode.getModificationType()) {
                    case WRITE:
                        if (rootNode.getDataBefore() == null) {
                            PhysicalNode physicalNode = rootNode.getDataAfter();
                            Vertex vertex = new Vertex(physicalNode.getNodeId().getValue());

                            routingAlgorithm.addVertex(vertex);
                        }
                        break;
                    case DELETE:
                        routingAlgorithm.removeVertex(rootNode.getDataBefore().getNodeId().getValue());
                        break;
                    default:
                        break;
                }
            }
        }
    }

    /**
     * A listener to change events related to physical links being
     * added, removed or updated.
     *
     * @author Zhigang Ji
     */
    private class PhysicalLinkChangeListener implements DataTreeChangeListener<PhysicalLink> {
        @Override
        public void onDataTreeChanged(Collection<DataTreeModification<PhysicalLink>> changes) {
            for (DataTreeModification<PhysicalLink> change: changes) {
                DataObjectModification<PhysicalLink> rootNode = change.getRootNode();
                switch (rootNode.getModificationType()) {
                    case WRITE:
                        if (rootNode.getDataBefore() == null) {
                            routingAlgorithm.addEdge(new Edge(rootNode.getDataAfter()));
                        } else {
                            routingAlgorithm.updateEdge(new Edge(rootNode.getDataAfter()));
                        }
                        break;
                    case DELETE:
                        routingAlgorithm.removeEdge(rootNode.getDataBefore().getLinkId().getValue());
                        break;
                    default:
                        break;
                }
            }
        }
    }

    /**
     * A listener to change events related to physical paths being
     * added, removed or updated.
     *
     * @author Zhigang Ji
     */
    private class PhysicalPathChangeListener implements DataTreeChangeListener<PhysicalPath> {
        @Override
        public void onDataTreeChanged(Collection<DataTreeModification<PhysicalPath>> changes) {
            for (DataTreeModification<PhysicalPath> change: changes) {
                DataObjectModification<PhysicalPath> rootNode = change.getRootNode();
                PhysicalPath physicalPath;
                switch (rootNode.getModificationType()) {
                    case DELETE:
                        physicalPath = rootNode.getDataBefore();
                        long bandwidth = physicalPath.getBandwidth();

                        if (0 < bandwidth) {
                            List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.path.instance.PhysicalLink> physicalLinks = physicalPath
                                    .getPhysicalLink();

                            if (null != physicalLinks && !physicalLinks.isEmpty()) {
                                for (org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.path.instance.PhysicalLink physicalLink : physicalLinks) {
                                    Edge edge = routingAlgorithm.getEdge(physicalLink.getLinkId().getValue());

                                    if (null != edge) {
                                        edge.setBandwidth(edge.getBandwidth() + bandwidth);
                                        routingAlgorithm.updateEdge(edge);
                                    }
                                }
                            }
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    }
}
