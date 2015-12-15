/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.nemo.intent.computation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.DataChangeListener;
import org.opendaylight.controller.md.sal.binding.api.ReadOnlyTransaction;
import org.opendaylight.controller.md.sal.common.api.data.AsyncDataBroker.DataChangeScope;
import org.opendaylight.controller.md.sal.common.api.data.AsyncDataChangeEvent;
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
import org.opendaylight.yangtools.yang.binding.DataObject;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;

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

    private final DataBroker dataBroker;

    /**
     * The routing algorithm instance.
     */
    private RoutingAlgorithm routingAlgorithm;

    /**
     * The registration for the physical node change listener.
     */
    private ListenerRegistration<DataChangeListener> physicalNodeChangeListenerReg;

    /**
     * The registration for the physical link change listener.
     */
    private ListenerRegistration<DataChangeListener> physicalLinkChangeListenerReg;

    /**
     * The registration for the physical path change listener.
     */
    private ListenerRegistration<DataChangeListener> physicalPathChangeListenerReg;

    public PNComputationUnit(DataBroker dataBroker) {
        super();

        this.dataBroker = dataBroker;
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

        physicalNodeChangeListenerReg = dataBroker.registerDataChangeListener(LogicalDatastoreType.OPERATIONAL,
                physicalNodeIid, new PhysicalNodeChangeListener(), DataChangeScope.BASE);
        physicalLinkChangeListenerReg = dataBroker.registerDataChangeListener(LogicalDatastoreType.OPERATIONAL,
                physicalLinkIid, new PhysicalLinkChangeListener(), DataChangeScope.BASE);
        physicalPathChangeListenerReg = dataBroker.registerDataChangeListener(LogicalDatastoreType.OPERATIONAL,
                physicalPathIid, new PhysicalPathChangeListener(), DataChangeScope.BASE);

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
                new ArrayList<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.path.instance.PhysicalLink>(edges.size());
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
                new ArrayList<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.path.instance.PhysicalLink>(edges.size());
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
    private class PhysicalNodeChangeListener implements DataChangeListener {
        @Override
        public void onDataChanged(AsyncDataChangeEvent<InstanceIdentifier<?>, DataObject> change) {
            if ( null == change ) {
                return;
            }

            Map<InstanceIdentifier<?>, DataObject> createdData = change.getCreatedData();

            if ( null != createdData && !createdData.isEmpty() ) {
                PhysicalNode physicalNode;
                Vertex vertex;

                for ( DataObject dataObject : createdData.values() ) {
                    if ( dataObject instanceof PhysicalNode ) {
                        physicalNode = (PhysicalNode)dataObject;
                        vertex = new Vertex(physicalNode.getNodeId().getValue());

                        routingAlgorithm.addVertex(vertex);
                    }
                }
            }

            Map<InstanceIdentifier<?>, DataObject> originalData = change.getOriginalData();
            Set<InstanceIdentifier<?>> removedPaths = change.getRemovedPaths();

            if ( null != removedPaths && !removedPaths.isEmpty() ) {
                DataObject dataObject;

                for ( InstanceIdentifier<?> instanceId : removedPaths ) {
                    dataObject = originalData.get(instanceId);

                    if ( null != dataObject && dataObject instanceof PhysicalNode ) {
                        routingAlgorithm.removeVertex(((PhysicalNode)dataObject).getNodeId().getValue());
                    }
                }
            }

            return;
        }
    }

    /**
     * A listener to change events related to physical links being
     * added, removed or updated.
     *
     * @author Zhigang Ji
     */
    private class PhysicalLinkChangeListener implements DataChangeListener {
        @Override
        public void onDataChanged(AsyncDataChangeEvent<InstanceIdentifier<?>, DataObject> change) {
            if ( null == change ) {
                return;
            }

            Map<InstanceIdentifier<?>, DataObject> createdData = change.getCreatedData();

            if ( null != createdData && !createdData.isEmpty() ) {
                for ( DataObject dataObject : createdData.values() ) {
                    if ( dataObject instanceof PhysicalLink ) {
                        PhysicalLink physicalLink = (PhysicalLink)dataObject;
                        routingAlgorithm.addEdge(new Edge(physicalLink));
                    }
                }
            }

            Map<InstanceIdentifier<?>, DataObject> updatedData = change.getUpdatedData();

            if ( null != updatedData && !updatedData.isEmpty() ) {
                for ( DataObject dataObject : updatedData.values() ) {
                    if ( dataObject instanceof PhysicalLink ) {
                        PhysicalLink physicalLink = (PhysicalLink)dataObject;
                        routingAlgorithm.updateEdge(new Edge(physicalLink));
                    }
                }
            }

            Map<InstanceIdentifier<?>, DataObject> originalData = change.getOriginalData();
            Set<InstanceIdentifier<?>> removedPaths = change.getRemovedPaths();

            if ( null != removedPaths && !removedPaths.isEmpty() ) {
                DataObject dataObject;

                for ( InstanceIdentifier<?> instanceId : removedPaths ) {
                    dataObject = originalData.get(instanceId);

                    if ( null != dataObject && dataObject instanceof PhysicalLink ) {
                        routingAlgorithm.removeEdge(((PhysicalLink)dataObject).getLinkId().getValue());
                    }
                }
            }

            return;
        }
    }

    /**
     * A listener to change events related to physical paths being
     * added, removed or updated.
     *
     * @author Zhigang Ji
     */
    private class PhysicalPathChangeListener implements DataChangeListener {
        @Override
        public void onDataChanged(AsyncDataChangeEvent<InstanceIdentifier<?>, DataObject> change) {
            if ( null == change ) {
                return;
            }

            Map<InstanceIdentifier<?>, DataObject> originalData = change.getOriginalData();
            Set<InstanceIdentifier<?>> removedPaths = change.getRemovedPaths();

            if ( null != removedPaths && !removedPaths.isEmpty() ) {
                DataObject dataObject;
                PhysicalPath physicalPath;
                long bandwidth;
                List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.path.instance.PhysicalLink> physicalLinks;
                Edge edge;

                for ( InstanceIdentifier<?> instanceId : removedPaths ) {
                    dataObject = originalData.get(instanceId);

                    if ( null != dataObject && dataObject instanceof PhysicalPath ) {
                        physicalPath = (PhysicalPath)dataObject;
                        bandwidth = physicalPath.getBandwidth();

                        if ( 0 < bandwidth ) {
                            physicalLinks = physicalPath.getPhysicalLink();

                            if ( null != physicalLinks && !physicalLinks.isEmpty() ) {
                                for ( org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.path.instance.PhysicalLink
                                        physicalLink : physicalLinks ) {
                                    edge = routingAlgorithm.getEdge(physicalLink.getLinkId().getValue());

                                    if ( null != edge ) {
                                        edge.setBandwidth(edge.getBandwidth() + bandwidth);
                                        routingAlgorithm.updateEdge(edge);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            return;
        }
    }
}
