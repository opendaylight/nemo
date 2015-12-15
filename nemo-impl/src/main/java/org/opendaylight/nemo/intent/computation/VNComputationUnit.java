/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.nemo.intent.computation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.DataChangeListener;
import org.opendaylight.controller.md.sal.binding.api.ReadWriteTransaction;
import org.opendaylight.controller.md.sal.common.api.data.AsyncDataBroker.DataChangeScope;
import org.opendaylight.controller.md.sal.common.api.data.AsyncDataChangeEvent;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.nemo.intent.algorithm.Edge;
import org.opendaylight.nemo.intent.algorithm.RoutingAlgorithm;
import org.opendaylight.nemo.intent.algorithm.Vertex;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.VirtualNetworks;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.VirtualNetwork;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.VirtualNetworkKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.VirtualLinks;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.VirtualNodes;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.VirtualPaths;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.VirtualRoutes;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.links.VirtualLink;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.nodes.VirtualNode;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.paths.VirtualPath;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.paths.VirtualPathBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.paths.VirtualPathKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.routes.VirtualRoute;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.routes.VirtualRouteBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.routes.VirtualRouteKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.path.instance.VirtualLinkBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.VirtualLinkId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.VirtualNetworkId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.VirtualNodeId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.VirtualPathId;
import org.opendaylight.yangtools.concepts.ListenerRegistration;
import org.opendaylight.yangtools.yang.binding.DataObject;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;

/**
 * The virtual network computation unit implements the following functions:
 * (1) Maintain a user's virtual network topology information generated in
 *     terms of the user's intents through subscribing from the data store.
 * (2) Automatically recompute all routes of the virtual network when it
 *     changed, and store or update the routes into the data store.
 * (3) Provide the path computation with SLA constraints to any other modules
 *     that need it.
 *
 * @author Zhigang Ji
 */
public class VNComputationUnit implements AutoCloseable {
    private static final Logger LOG = LoggerFactory.getLogger(VNComputationUnit.class);

    private final DataBroker dataBroker;

    /**
     * The user id for the virtual network maintained by
     * this computation unit.
     */
    private UserId userId;

    /**
     * The routing algorithm instance.
     */
    private RoutingAlgorithm routingAlgorithm;

    /**
     * The virtual routers in the virtual network.
     */
    private Set<VirtualNodeId> virtualRouters;

    /**
     * The registration for the virtual node change listener.
     */
    private ListenerRegistration<DataChangeListener> virtualNodeChangeListenerReg;

    /**
     * The registration for the virtual link change listener.
     */
    private ListenerRegistration<DataChangeListener> virtualLinkChangeListenerReg;

    public VNComputationUnit(DataBroker dataBroker, UserId userId) {
        super();

        this.dataBroker = dataBroker;
        this.userId = userId;
        routingAlgorithm = new RoutingAlgorithm();
        virtualRouters = new HashSet<VirtualNodeId>();

        VirtualNetworkKey virtualNetworkKey = new VirtualNetworkKey(new VirtualNetworkId(userId.getValue()));
        InstanceIdentifier<VirtualNode> virtualNodeIid = InstanceIdentifier
                .builder(VirtualNetworks.class)
                .child(VirtualNetwork.class, virtualNetworkKey)
                .child(VirtualNodes.class)
                .child(VirtualNode.class)
                .build();
        InstanceIdentifier<VirtualLink> virtualLinkIid = InstanceIdentifier
                .builder(VirtualNetworks.class)
                .child(VirtualNetwork.class, virtualNetworkKey)
                .child(VirtualLinks.class)
                .child(VirtualLink.class)
                .build();

        virtualNodeChangeListenerReg = dataBroker.registerDataChangeListener(LogicalDatastoreType.CONFIGURATION,
                virtualNodeIid, new VirtualNodeChangeListener(), DataChangeScope.BASE);
        virtualLinkChangeListenerReg = dataBroker.registerDataChangeListener(LogicalDatastoreType.CONFIGURATION,
                virtualLinkIid, new VirtualLinkChangeListener(), DataChangeScope.BASE);

        LOG.debug("Initialized the virtual network computation unit for the user {}.", userId.getValue());

        return;
    }

    public VNComputationUnit(DataBroker dataBroker, VirtualNetwork virtualNetwork) {
        super();

        this.dataBroker = dataBroker;
        userId = virtualNetwork.getUserId();
        routingAlgorithm = new RoutingAlgorithm();
        virtualRouters = new HashSet<VirtualNodeId>();

        List<VirtualNode> virtualNodes = virtualNetwork.getVirtualNodes().getVirtualNode();
        Vertex vertex;

        for ( VirtualNode virtualNode : virtualNodes ) {
            vertex = new Vertex(virtualNode.getNodeId().getValue());
            routingAlgorithm.addVertex(vertex);

            if ( VirtualNode.NodeType.Vrouter == virtualNode.getNodeType() ) {
                virtualRouters.add(virtualNode.getNodeId());
            }
        }

        List<VirtualLink> virtualLinks = virtualNetwork.getVirtualLinks().getVirtualLink();

        for ( VirtualLink virtualLink : virtualLinks ) {
            routingAlgorithm.addEdge(new Edge(virtualLink));
        }

//        computeRoute(virtualNetwork);

        return;
    }

    /**
     * Compute a shortest virtual path from the given source vertex to
     * target one without any constraint.
     *
     * @param source The given source virtual node id.
     * @param target The given target virtual node id.
     * @return The virtual path if successful，or null otherwise.
     */
    public VirtualPath computePath(VirtualNodeId source, VirtualNodeId target) {
        List<Edge> edges = routingAlgorithm.computePath(routingAlgorithm.getVertex(source.getValue()),
                routingAlgorithm.getVertex(target.getValue()));

        if ( null == edges || edges.isEmpty() ) {
            return null;
        }

        List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.path.instance.VirtualLink> virtualLinks =
                new ArrayList<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.path.instance.VirtualLink>(edges.size());
        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.path.instance.VirtualLink virtualLink;
        long metric = 0;
        long delay = 0;

        for ( Edge edge : edges ) {
            virtualLink = new VirtualLinkBuilder()
                    .setLinkId(new VirtualLinkId(edge.getId()))
                    .setOrder((long) virtualLinks.size())
                    .build();
            virtualLinks.add(virtualLink);

            metric += edge.getMetric();
//            delay += edge.getDelay();
        }

        VirtualPath virtualPath = new VirtualPathBuilder()
                .setPathId(new VirtualPathId(UUID.randomUUID().toString()))
                .setVirtualLink(virtualLinks)
                .setMetric(metric)
                .setBandwidth(0L)
                .setDelay(delay)
                .build();

        return virtualPath;
    }

    /**
     * Compute a shortest virtual path with the given bandwidth from the
     * given source vertex to target one.
     *
     * @param source The given source virtual node id.
     * @param target The given target virtual node id.
     * @param bandwidth The given bandwidth for the virtual path.
     * @return The virtual path if successful，or null otherwise.
     */
    public VirtualPath computePath(VirtualNodeId source, VirtualNodeId target, long bandwidth) {
        List<Edge> edges = routingAlgorithm.computePath(routingAlgorithm.getVertex(source.getValue()),
                routingAlgorithm.getVertex(target.getValue()), bandwidth);

        if ( null == edges || edges.isEmpty() ) {
            return null;
        }

        List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.path.instance.VirtualLink> virtualLinks =
                new ArrayList<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.path.instance.VirtualLink>(edges.size());
        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.path.instance.VirtualLink virtualLink;
        long metric = 0;
        long delay = 0;

        for ( Edge edge : edges ) {
            edge.setBandwidth(edge.getBandwidth() - bandwidth);
            routingAlgorithm.updateEdge(edge);

            virtualLink = new VirtualLinkBuilder()
                    .setLinkId(new VirtualLinkId(edge.getId()))
                    .setOrder((long)virtualLinks.size())
                    .build();
            virtualLinks.add(virtualLink);

            metric += edge.getMetric();
//            delay += edge.getDelay();
        }

        VirtualPath virtualPath = new VirtualPathBuilder()
                .setPathId(new VirtualPathId(UUID.randomUUID().toString()))
                .setVirtualLink(virtualLinks)
                .setMetric(metric)
                .setBandwidth(bandwidth)
                .setDelay(delay)
                .build();

        return virtualPath;
    }

    @Override
    public void close() throws Exception {
        if ( null != virtualNodeChangeListenerReg ) {
            virtualNodeChangeListenerReg.close();
        }

        if ( null != virtualLinkChangeListenerReg ) {
            virtualLinkChangeListenerReg.close();
        }

        return;
    }

    /**
     * Compute the routes between all virtual routers in the virtual
     * network, and store or update them into the data store.
     */
    private void computeRoute() {
        Map<VirtualRouteKey, VirtualPath> routes = new HashMap<VirtualRouteKey, VirtualPath>();
        VirtualPath virtualPath;

        for ( VirtualNodeId source : virtualRouters ) {
            for ( VirtualNodeId target : virtualRouters ) {
                if ( !source.equals(target) ) {
                    virtualPath = computePath(source, target);

                    if ( null == virtualPath ) {
                        continue;
                    }

                    routes.put(new VirtualRouteKey(source, target), virtualPath);
                }
            }
        }

        updateRoute(routes);

        return;
    }

    /**
     * Compute the routes between all virtual routers in the virtual
     * network, and store them into the virtual network.
     *
     * @param virtualNetwork The virtual network to store the routes.
     */
    private void computeRoute(VirtualNetwork virtualNetwork) {
        List<VirtualRoute> virtualRoutes = virtualNetwork.getVirtualRoutes().getVirtualRoute();
        List<VirtualPath> virtualPaths = virtualNetwork.getVirtualPaths().getVirtualPath();
        VirtualRoute virtualRoute;
        VirtualPath virtualPath;

        for ( VirtualNodeId source : virtualRouters ) {
            for ( VirtualNodeId target : virtualRouters ) {
                if ( !source.equals(target) ) {
                    virtualPath = computePath(source, target);

                    if ( null == virtualPath ) {
                        continue;
                    }

                    virtualRoute = new VirtualRouteBuilder().setSrcNodeId(source)
                            .setDestNodeId(target)
                            .setPathId(virtualPath.getPathId())
                            .build();

                    virtualPaths.add(virtualPath);
                    virtualRoutes.add(virtualRoute);
                }
            }
        }

        return;
    }

    /**
     * Update the given routes into the data store. If the route
     * already exists, remove it's old virtual path and store the
     * given new one into the data store.
     *
     * @param routes The given routes to be updated.
     */
    private void updateRoute(Map<VirtualRouteKey, VirtualPath> routes) {
        ReadWriteTransaction readWriteTransaction = dataBroker.newReadWriteTransaction();

        VirtualNetworkKey virtualNetworkKey = new VirtualNetworkKey(new VirtualNetworkId(userId.getValue()));
        InstanceIdentifier<VirtualRoute> virtualRouteIid;
        InstanceIdentifier<VirtualPath> virtualPathIid;
        Optional<VirtualRoute> result;
        VirtualRoute virtualRoute;

        for ( Map.Entry<VirtualRouteKey, VirtualPath> route : routes.entrySet() ) {
            virtualRouteIid = InstanceIdentifier.builder(VirtualNetworks.class)
                    .child(VirtualNetwork.class, virtualNetworkKey)
                    .child(VirtualRoutes.class)
                    .child(VirtualRoute.class, route.getKey())
                    .build();

            try {
                result = readWriteTransaction.read(LogicalDatastoreType.CONFIGURATION, virtualRouteIid).get();
            } catch ( InterruptedException exception ) {
                LOG.error("Can not read the virtual route from the virtual node {} to {}.",
                        route.getKey().getSrcNodeId().getValue(), route.getKey().getDestNodeId().getValue());

                continue;
            } catch ( ExecutionException exception ) {
                LOG.error("Can not read the virtual route from the virtual node {} to {}.",
                        route.getKey().getSrcNodeId().getValue(), route.getKey().getDestNodeId().getValue());

                continue;
            }

            if ( result.isPresent() ) {
                virtualRoute = result.get();
                virtualPathIid = InstanceIdentifier.builder(VirtualNetworks.class)
                        .child(VirtualNetwork.class, virtualNetworkKey)
                        .child(VirtualPaths.class)
                        .child(VirtualPath.class, new VirtualPathKey(virtualRoute.getPathId()))
                        .build();
                readWriteTransaction.delete(LogicalDatastoreType.CONFIGURATION, virtualPathIid);
            }

            virtualPathIid = InstanceIdentifier.builder(VirtualNetworks.class)
                    .child(VirtualNetwork.class, virtualNetworkKey)
                    .child(VirtualPaths.class)
                    .child(VirtualPath.class, route.getValue().getKey())
                    .build();
            readWriteTransaction.put(LogicalDatastoreType.CONFIGURATION, virtualPathIid, route.getValue(), true);

            virtualRoute = new VirtualRouteBuilder().setSrcNodeId(route.getKey().getSrcNodeId())
                    .setDestNodeId(route.getKey().getDestNodeId())
                    .setPathId(route.getValue().getPathId())
                    .build();
            readWriteTransaction.put(LogicalDatastoreType.CONFIGURATION, virtualRouteIid, virtualRoute, true);
        }

        readWriteTransaction.submit();

        return;
    }

    /**
     * A listener to change events related to virtual nodes being
     * added, removed or updated.
     *
     * @author Zhigang Ji
     */
    private class VirtualNodeChangeListener implements DataChangeListener {
        @Override
        public void onDataChanged(AsyncDataChangeEvent<InstanceIdentifier<?>, DataObject> change) {
            if ( null == change ) {
                return;
            }

            Map<InstanceIdentifier<?>, DataObject> createdData = change.getCreatedData();

            if ( null != createdData && !createdData.isEmpty() ) {
                VirtualNode virtualNode;
                Vertex vertex;

                for ( DataObject dataObject : createdData.values() ) {
                    if ( dataObject instanceof VirtualNode ) {
                        virtualNode = (VirtualNode)dataObject;
                        vertex = new Vertex(virtualNode.getNodeId().getValue());

                        routingAlgorithm.addVertex(vertex);

                        if ( VirtualNode.NodeType.Vrouter == virtualNode.getNodeType() ) {
                            virtualRouters.add(virtualNode.getNodeId());
                        }
                    }
                }
            }

            return;
        }
    }

    /**
     * A listener to change events related to virtual links being
     * added, removed or updated.
     *
     * @author Zhigang Ji
     */
    private class VirtualLinkChangeListener implements DataChangeListener {
        @Override
        public void onDataChanged(AsyncDataChangeEvent<InstanceIdentifier<?>, DataObject> change) {
            if ( null == change ) {
                return;
            }

            Map<InstanceIdentifier<?>, DataObject> createdData = change.getCreatedData();

            if ( null != createdData && !createdData.isEmpty() ) {
                boolean needRerouting = false;

                for ( DataObject dataObject : createdData.values() ) {
                    if ( dataObject instanceof VirtualLink ) {
                        VirtualLink virtualLink = (VirtualLink)dataObject;
                        Edge edge = new Edge(virtualLink);

                        routingAlgorithm.addEdge(edge);

                        if ( virtualRouters.contains(virtualLink.getSrcNodeId())
                                && virtualRouters.contains(virtualLink.getDestNodeId()) ) {
                            needRerouting = true;
                        }
                    }
                }

                if ( needRerouting ) {
                    computeRoute();
                }
            }

            return;
        }
    }
}
