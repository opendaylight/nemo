/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.nemo.intent;

import com.google.common.base.Optional;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.ReadWriteTransaction;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.nemo.intent.computation.PNComputationUnit;
import org.opendaylight.nemo.intent.computation.PNResourcesTracker;
import org.opendaylight.nemo.intent.computation.VNComputationUnit;
import org.opendaylight.nemo.intent.computation.VNMappingUnit;
import org.opendaylight.nemo.intent.condition.ConditionManager;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.PhysicalNetwork;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.PhysicalPaths;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.paths.PhysicalPath;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.paths.PhysicalPathKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.VirtualNetworks;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.VirtualNetwork;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.VirtualNetworkBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.VirtualNetworkKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.arps.VirtualArp;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.links.VirtualLink;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.nodes.VirtualNode;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.paths.VirtualPath;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.routes.VirtualRoute;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.IntentVnMappingResults;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.VnPnMappingResults;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.intent.vn.mapping.results.UserIntentVnMapping;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.intent.vn.mapping.results.UserIntentVnMappingBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.intent.vn.mapping.results.UserIntentVnMappingKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.intent.vn.mapping.results.user.intent.vn.mapping.IntentVnMappingResult;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.vn.pn.mapping.results.UserVnPnMapping;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.vn.pn.mapping.results.UserVnPnMappingBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.vn.pn.mapping.results.UserVnPnMappingKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.vn.pn.mapping.results.user.vn.pn.mapping.VnPnMappingResult;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.NodeType;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.PhysicalPathId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.VirtualNetworkId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.Users;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Connection;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Flow;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Node;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.operations.Operation;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.User;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.UserKey;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Provide the user intent resolution APIs and distribute the user intents
 * to corresponding handling classes.
 *
 * @author Zhigang Ji
 */
public class IntentResolver implements AutoCloseable {
    private static final Logger LOG = LoggerFactory.getLogger(IntentResolver.class);

    private final DataBroker dataBroker;

    /**
     * The node mapper to resolve the user's node intent.
     */
    private NodeMapper nodeMapper;

    /**
     * The connection mapper to resolve the user's connection intent.
     */
    private ConnectionMapper connectionMapper;

    /**
     * The flow manager to resolve the user's flow intent.
     */
    private FlowManager flowManager;

    /**
     * The operation resolver to resolve the user's operation intent.
     */
    private OperationResolver operationResolver;

    /**
     * The condition manager to resolve and manage the condition in the user's operation.
     */
    private ConditionManager conditionManager;

    /**
     * The physical network computation unit.
     */
    private PNComputationUnit pnComputationUnit;

    /**
     * The virtual network computation unit for all users.
     */
    private Map<UserId, VNComputationUnit> vnComputationUnits;

    /**
     * Track the physical resource, re-resolve intent if related physical resource removed.
     */
    private PNResourcesTracker pnResourcesTracker;

    /**
     * The virtual network mapping unit.
     */
    private VNMappingUnit vnMappingUnit;

    public IntentResolver(DataBroker dataBroker) {
        super();

        this.dataBroker = dataBroker;

        nodeMapper = new NodeMapper(dataBroker);
        connectionMapper = new ConnectionMapper(dataBroker, nodeMapper);
        flowManager = new FlowManager(dataBroker);
        conditionManager = new ConditionManager(this);

        pnComputationUnit = new PNComputationUnit(dataBroker);
        vnComputationUnits = new HashMap<UserId, VNComputationUnit>();
        pnResourcesTracker = new PNResourcesTracker(dataBroker, this);
        vnMappingUnit = new VNMappingUnit(dataBroker, pnComputationUnit, pnResourcesTracker);

        operationResolver = new OperationResolver(dataBroker, conditionManager, vnComputationUnits);

        LOG.debug("Initialized the renderer common intent resolver.");

        return;
    }

    /**
     * Resolve the user's intents to generate the virtual network, then map
     * the virtual network into the underlying physical network, finally, store
     * the generated intent mapping results into the data store, and various
     * renderers configure the underlying networks according to these results.
     *
     * @param userId The user id for the intents to be resolved.
     */
    public void resolveIntent(UserId userId) throws Exception {
        VNComputationUnit vnComputationUnit = vnComputationUnits.get(userId);

        VirtualNetworkId virtualNetworkId = new VirtualNetworkId(userId.getValue());
        VirtualNetworkKey virtualNetworkKey = new VirtualNetworkKey(virtualNetworkId);

        InstanceIdentifier<VirtualNetwork> virtualNetworkIid = InstanceIdentifier
                .builder(VirtualNetworks.class)
                .child(VirtualNetwork.class, virtualNetworkKey)
                .build();
        InstanceIdentifier<UserIntentVnMapping> userIntentVnMappingIid = InstanceIdentifier
                .builder(IntentVnMappingResults.class)
                .child(UserIntentVnMapping.class, new UserIntentVnMappingKey(userId))
                .build();
        InstanceIdentifier<UserVnPnMapping> userVnPnMappingIid = InstanceIdentifier
                .builder(VnPnMappingResults.class)
                .child(UserVnPnMapping.class, new UserVnPnMappingKey(virtualNetworkId))
                .build();

        if ( null != vnComputationUnit ) {
            conditionManager.clear(userId);

            vnComputationUnit.close();
            vnComputationUnits.remove(userId);

            ReadWriteTransaction readWriteTransaction = dataBroker.newReadWriteTransaction();

            Optional<UserVnPnMapping> result;

            try {
                result = readWriteTransaction.read(LogicalDatastoreType.CONFIGURATION, userVnPnMappingIid).get();
            } catch ( InterruptedException exception ) {
                throw new IntentResolutionException("Can not read the vn-pn mapping results for the user " +
                        userId.getValue() + ".");
            } catch ( ExecutionException exception ) {
                throw new IntentResolutionException("Can not read the vn-pn mapping results for the user " +
                        userId.getValue() + ".");
            }

            if ( result.isPresent() ) {
                UserVnPnMapping userVnPnMapping = result.get();
                List<VnPnMappingResult> vnPnMappingResults = userVnPnMapping.getVnPnMappingResult();
                InstanceIdentifier<PhysicalPath> physicalPathIid;
                PhysicalPathId physicalPathId;

                for ( VnPnMappingResult vnPnMappingResult : vnPnMappingResults ) {
                    if ( VnPnMappingResult.VirtualResourceType.Vlink == vnPnMappingResult.getVirtualResourceType() ) {
                        physicalPathId = new PhysicalPathId(vnPnMappingResult.getPhysicalResourceEntityId().getValue());
                        physicalPathIid = InstanceIdentifier.builder(PhysicalNetwork.class)
                                .child(PhysicalPaths.class)
                                .child(PhysicalPath.class, new PhysicalPathKey(physicalPathId))
                                .build();

                        readWriteTransaction.delete(LogicalDatastoreType.OPERATIONAL, physicalPathIid);
                    }
                }
            }

//            readWriteTransaction.delete(LogicalDatastoreType.CONFIGURATION, virtualNetworkIid);
//            readWriteTransaction.delete(LogicalDatastoreType.CONFIGURATION, userIntentVnMappingIid);
//            readWriteTransaction.delete(LogicalDatastoreType.CONFIGURATION, userVnPnMappingIid);
            // TODO: 删除intent processing status

            readWriteTransaction.submit();
        }

        ReadWriteTransaction readWriteTransaction = dataBroker.newReadWriteTransaction();

        InstanceIdentifier<User> userIid = InstanceIdentifier.builder(Users.class)
                .child(User.class, new UserKey(userId))
                .build();
        Optional<User> result;

        try {
            result = readWriteTransaction.read(LogicalDatastoreType.CONFIGURATION, userIid).get();
        } catch ( InterruptedException exception ) {
            throw new IntentResolutionException("Can not read the data of the user " +
                    userId.getValue() + ".");
        } catch ( ExecutionException exception ) {
            throw new IntentResolutionException("Can not read the data of the user " +
                    userId.getValue() + ".");
        }

        if ( !result.isPresent() ) {
            throw new IntentResolutionException("The data of the user " +
                    userId.getValue() + " does not exist.");
        }

        User user = result.get();

        if ( null != user.getObjects() ) {
            VirtualNodes virtualNodes = new VirtualNodesBuilder()
                    .setVirtualNode(new LinkedList<VirtualNode>())
                    .build();
            VirtualLinks virtualLinks = new VirtualLinksBuilder()
                    .setVirtualLink(new LinkedList<VirtualLink>())
                    .build();
            VirtualPaths virtualPaths = new VirtualPathsBuilder()
                    .setVirtualPath(new LinkedList<VirtualPath>())
                    .build();
            VirtualRoutes virtualRoutes = new VirtualRoutesBuilder()
                    .setVirtualRoute(new LinkedList<VirtualRoute>())
                    .build();
            VirtualArps virtualArps = new VirtualArpsBuilder()
                    .setVirtualArp(new LinkedList<VirtualArp>())
                    .build();
            VirtualNetwork virtualNetwork = new VirtualNetworkBuilder()
                    .setNetworkId(virtualNetworkId)
                    .setUserId(userId)
                    .setVirtualNodes(virtualNodes)
                    .setVirtualLinks(virtualLinks)
                    .setVirtualPaths(virtualPaths)
                    .setVirtualRoutes(virtualRoutes)
                    .setVirtualArps(virtualArps)
                    .build();

            UserIntentVnMapping userIntentVnMapping = new UserIntentVnMappingBuilder()
                    .setUserId(userId)
                    .setVirtualNetworkId(virtualNetworkId)
                    .setIntentVnMappingResult(new LinkedList<IntentVnMappingResult>())
                    .build();

            UserVnPnMapping userVnPnMapping = new UserVnPnMappingBuilder()
                    .setVirtualNetworkId(virtualNetworkId)
                    .setUserId(userId)
                    .setVnPnMappingResult(new LinkedList<VnPnMappingResult>())
                    .build();

            List<PhysicalPath> physicalPaths = new LinkedList<PhysicalPath>();

            List<Node> nodes = user.getObjects().getNode();
            List<Node> hosts = new LinkedList<Node>();
            List<Node> layer2Groups = new LinkedList<Node>();
            List<Node> layer3Groups = new LinkedList<Node>();
            List<Node> externalLayer3Groups = new LinkedList<Node>();
            List<Node> serviceChainGroups = new LinkedList<Node>();
            List<Node> serviceFunctions = new LinkedList<Node>();

            NodeType hostNodeType = new NodeType("host");
            NodeType layer2GroupNodeType = new NodeType("l2-group");
            NodeType layer3GroupNodeType = new NodeType("l3-group");
            NodeType externalGroupNodeType = new NodeType("ext-group");
            NodeType serviceChainGroupNodeType = new NodeType("chain-group");
            NodeType firewallNodeType = new NodeType("fw");
            NodeType loadbalancerNodeType = new NodeType("lb");
            NodeType cacheNodeType = new NodeType("cache");
            NodeType nodeType;

            if ( null != nodes ) {
                for ( Node node : nodes ) {
                    nodeType = node.getNodeType();

                    if ( nodeType.equals(hostNodeType) ) {
                        hosts.add(node);
                    }

                    if ( nodeType.equals(layer2GroupNodeType) ) {
                        layer2Groups.add(node);
                    }

                    if ( nodeType.equals(layer3GroupNodeType) ) {
                        layer3Groups.add(node);
                    }

                    if ( nodeType.equals(externalGroupNodeType) ) {
                        if ( IntentResolverUtils.checkExternalLayer3Group(node) ) {
                            externalLayer3Groups.add(node);
                        }
                    }

                    if ( nodeType.equals(serviceChainGroupNodeType) ) {
                        serviceChainGroups.add(node);
                    }

                    if ( nodeType.equals(firewallNodeType)
                            || nodeType.equals(loadbalancerNodeType)
                            || nodeType.equals(cacheNodeType) ) {
                        serviceFunctions.add(node);
                    }
                }

                IntentResolverUtils.copyPhysicalNetworkConfigToOperational(dataBroker);

                for ( Node node : hosts ) {
                    nodeMapper.resolveHost(user, node, virtualNetwork, userIntentVnMapping);
                }

                for ( Node node : layer2Groups ) {
                    nodeMapper.resolveLayer2Group(user, node, virtualNetwork, userIntentVnMapping);
                }

                for ( Node node : externalLayer3Groups ) {
                    nodeMapper.resolveExternalLayer3Group(user, node, virtualNetwork, userIntentVnMapping);
                }

                for ( Node node : layer3Groups ) {
                    nodeMapper.resolveLayer3Group(user, node, virtualNetwork, userIntentVnMapping);
                }

                for ( Node node : serviceFunctions ) {
                    nodeMapper.resolveServiceFunction(user, node, virtualNetwork, userIntentVnMapping);
                }

                for ( Node node : serviceChainGroups ) {
                    nodeMapper.resolveServiceChainGroup(user, node, virtualNetwork, userIntentVnMapping);
                }
            }

            List<Connection> connections = user.getObjects().getConnection();

            if ( null != connections ) {
                for ( Connection connection : connections ) {
                    connectionMapper.resolveConnection(user, connection, virtualNetwork, userIntentVnMapping);
                }
            }

            List<Flow> flows = user.getObjects().getFlow();

            if ( null != flows ) {
                for ( Flow flow : flows ) {
                    flowManager.resolveFlow(userId, flow);
                }
            }

            List<Operation> operationsApplyingToNode = new LinkedList<Operation>();
            List<Operation> operationsApplyingToConnection = new LinkedList<Operation>();
            List<Operation> operationsApplyingToFlow = new LinkedList<Operation>();

            if ( null != user.getOperations() ) {
                List<Operation> operations = user.getOperations().getOperation();

                if ( null != operations ) {
                    operationResolver.classifyOperations(user, operations, operationsApplyingToNode,
                            operationsApplyingToConnection, operationsApplyingToFlow);
                }
            }

            if ( !operationsApplyingToNode.isEmpty() ) {
                for ( Operation operation : operationsApplyingToNode ) {
                    operationResolver.resolveOperation(user, operation, virtualNetwork, userIntentVnMapping);
                }
            }

            if ( !operationsApplyingToConnection.isEmpty() ) {
                for ( Operation operation : operationsApplyingToConnection ) {
                    operationResolver.resolveOperation(user, operation, virtualNetwork, userIntentVnMapping);
                }
            }



            vnMappingUnit.virtualNetworkMapping(virtualNetwork, userVnPnMapping, physicalPaths);
            vnComputationUnit = new VNComputationUnit(dataBroker, virtualNetwork);
            vnComputationUnits.put(userId, vnComputationUnit);

            int currentVirtualLinkNum = virtualLinks.getVirtualLink().size();

            if ( !operationsApplyingToFlow.isEmpty() ) {
                for ( Operation operation : operationsApplyingToFlow ) {
                    operationResolver.resolveOperation(user, operation, virtualNetwork, userIntentVnMapping);
                }
            }

            List<VirtualLink> virtualLinkList = virtualLinks.getVirtualLink();
            List<VirtualLink> unmappedVirtualLinkList =
                    virtualLinkList.subList(currentVirtualLinkNum, virtualLinkList.size());

            vnMappingUnit.virtualNetworkMapping(virtualNetwork,
                    unmappedVirtualLinkList, userVnPnMapping, physicalPaths);

            LOG.debug("{}", virtualNetwork);
            LOG.debug("{}", userIntentVnMapping);
            LOG.debug("{}", userVnPnMapping);
            LOG.debug("{}", physicalPaths);

            readWriteTransaction.put(LogicalDatastoreType.CONFIGURATION, virtualNetworkIid, virtualNetwork, true);
            readWriteTransaction.put(LogicalDatastoreType.CONFIGURATION, userIntentVnMappingIid, userIntentVnMapping, true);
            readWriteTransaction.put(LogicalDatastoreType.CONFIGURATION, userVnPnMappingIid, userVnPnMapping, true);

            InstanceIdentifier<PhysicalPath> physicalPathIid;

            for ( PhysicalPath physicalPath : physicalPaths ) {
                physicalPathIid = InstanceIdentifier.builder(PhysicalNetwork.class)
                        .child(PhysicalPaths.class)
                        .child(PhysicalPath.class, new PhysicalPathKey(physicalPath.getPathId()))
                        .build();

                readWriteTransaction.put(LogicalDatastoreType.OPERATIONAL, physicalPathIid, physicalPath, true);
            }

            readWriteTransaction.submit();
        }

        return;
    }

    @Override
    public void close() throws Exception {
        if ( null != pnComputationUnit ) {
            pnComputationUnit.close();
        }

        for ( VNComputationUnit vnComputationUnit : vnComputationUnits.values() ) {
            if ( null != vnComputationUnit ) {
                vnComputationUnit.close();
            }
        }

        if ( null != pnResourcesTracker ) {
            pnResourcesTracker.close();
        }

        if ( null != vnMappingUnit ) {
            vnMappingUnit.close();
        }

        return;
    }
}
