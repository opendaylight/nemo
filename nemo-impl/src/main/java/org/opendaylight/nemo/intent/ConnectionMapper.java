/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.nemo.intent;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.VirtualNetwork;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.links.VirtualLink;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.links.VirtualLinkBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.nodes.VirtualNode;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.VirtualPort;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.VirtualPortBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.intent.vn.mapping.results.UserIntentVnMapping;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.intent.vn.mapping.results.user.intent.vn.mapping.IntentVnMappingResult;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.intent.vn.mapping.results.user.intent.vn.mapping.IntentVnMappingResultBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.intent.vn.mapping.results.user.intent.vn.mapping.intent.vn.mapping.result.VirtualResource;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.intent.vn.mapping.results.user.intent.vn.mapping.intent.vn.mapping.result.VirtualResourceBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Connection;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Node;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.User;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.connection.instance.Property;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Resolve the user's connection intent. Create the virtual link according
 * to the connection if necessary, and then map the created virtual link
 * into the physical network. Finally, store these intent mapping results
 * into the data store.
 *
 * @author Zhigang Ji
 */
public class ConnectionMapper {
    private static final Logger LOG = LoggerFactory.getLogger(ConnectionMapper.class);

    private final DataBroker dataBroker;

    /**
     * The node mapper to resolve the user's node intent.
     */
    private NodeMapper nodeMapper;

    public ConnectionMapper(DataBroker dataBroker, NodeMapper nodeMapper) {
        super();

        this.dataBroker = dataBroker;
        this.nodeMapper = nodeMapper;

        LOG.debug("Initialized the renderer common connection mapper.");

        return;
    }

    /**
     * Resolve the user's connection intent, perform the intent mapping
     * if necessary, and store the intent mapping results into data store.
     *
     * @param user The user for the connection.
     * @param connection The connection to be resolved.
     * @param virtualNetwork The virtual network for the user.
     * @param userIntentVnMapping The intent-vn mapping for the user.
     */
    protected void resolveConnection(User user, Connection connection,
                                     VirtualNetwork virtualNetwork,
                                     UserIntentVnMapping userIntentVnMapping)
            throws IntentResolutionException {
        ConnectionType connectionType = connection.getConnectionType();

        if ( connectionType.equals(new ConnectionType("p2p")) ) {
            resolveP2PConnection(user, connection, virtualNetwork, userIntentVnMapping, false, true, true);

            return;
        }

        if ( connectionType.equals(new ConnectionType("p2mp")) ) {
            resolveP2MPConnection(user, connection, virtualNetwork, userIntentVnMapping);

            return;
        }

        if ( connectionType.equals(new ConnectionType("mesh")) ) {
            resolveMeshConnection(user, connection, virtualNetwork, userIntentVnMapping);

            return;
        }

        throw new IntentResolutionException("Unknown connection type.");

//        return;
    }

    /**
     * TODO
     *
     * @param user TODO
     * @param connection TODO
     * @param virtualNetwork TODO
     * @param userIntentVnMapping TODO
     * @param tempConnection TODO
     * @param resolvingEndNode1 TODO
     * @param resolvingEndNode2 TODO
     * @return TODO
     */
    private List<VirtualLink> resolveP2PConnection(User user, Connection connection,
                                                   VirtualNetwork virtualNetwork,
                                                   UserIntentVnMapping userIntentVnMapping,
                                                   boolean tempConnection,
                                                   boolean resolvingEndNode1,
                                                   boolean resolvingEndNode2)
            throws IntentResolutionException {
        List<Node> nodes = user.getObjects().getNode();
        NodeId nodeId = connection.getEndNode().get(0).getNodeId();
        Node endNode1 = IntentResolverUtils.getNode(nodes, nodeId);

        if ( null == endNode1 ) {
            throw new IntentResolutionException("The end-node " + nodeId.getValue() +
                    " of the connection " + connection.getConnectionId().getValue() +
                    " does not exist.");
        }

        nodeId = connection.getEndNode().get(1).getNodeId();
        Node endNode2 = IntentResolverUtils.getNode(nodes, nodeId);

        if ( null == endNode2 ) {
            throw new IntentResolutionException("The end-node " + nodeId.getValue() +
                    " of the connection " + connection.getConnectionId().getValue() +
                    " does not exist.");
        }

        NodeType hostNodeType = new NodeType("host");
        NodeType layer2GroupNodeType = new NodeType("l2-group");
        NodeType layer3GroupNodeType = new NodeType("l3-group");
        NodeType externalGroupNodeType = new NodeType("ext-group");
        NodeType serviceChainGroupNodeType = new NodeType("chain-group");
        NodeType firewallNodeType = new NodeType("fw");
        NodeType loadbalancerNodeType = new NodeType("lb");
        NodeType cacheNodeType = new NodeType("cache");

        if ( endNode1.getNodeType().equals(hostNodeType) ) {
            throw new IntentResolutionException("The end-node " + endNode1.getNodeId().getValue() +
                    " of the connection " + connection.getConnectionId().getValue() +
                    " can not be host type.");
        }

        if ( endNode1.getNodeType().equals(layer2GroupNodeType) ) {
            if ( endNode2.getNodeType().equals(hostNodeType) ) {
                throw new IntentResolutionException("The end-node " + endNode2.getNodeId().getValue() +
                        " of the connection " + connection.getConnectionId().getValue() +
                        " can not be host type.");
            }

            if ( endNode2.getNodeType().equals(layer2GroupNodeType) ) {
                return resolveP2PConnectionBetweenLayer2Groups(user, connection,
                        endNode1, endNode2, virtualNetwork, userIntentVnMapping,
                        tempConnection, resolvingEndNode1, resolvingEndNode2);
            }

            if ( endNode2.getNodeType().equals(layer3GroupNodeType) ) {
                return resolveP2PConnectionBetweenLayer2AndLayer3Groups(user, connection,
                        endNode1, endNode2, virtualNetwork, userIntentVnMapping,
                        tempConnection, resolvingEndNode1, resolvingEndNode2);
            }

            if ( endNode2.getNodeType().equals(externalGroupNodeType) ) {
                if ( IntentResolverUtils.checkExternalLayer3Group(endNode2) ) {
                    return resolveP2PConnectionBetweenLayer2AndExternalLayer3Groups(user, connection,
                            endNode1, endNode2, virtualNetwork, userIntentVnMapping,
                            tempConnection, resolvingEndNode1, resolvingEndNode2);
                } else {
                    return resolveP2PConnectionBetweenLayer2AndExternalLayer2Groups(user, connection,
                            endNode1, endNode2, virtualNetwork, userIntentVnMapping,
                            tempConnection, resolvingEndNode1, resolvingEndNode2);
                }
            }

            if ( endNode2.getNodeType().equals(serviceChainGroupNodeType) ) {
                return null;
            }

            if ( endNode2.getNodeType().equals(firewallNodeType) ) {
                throw new IntentResolutionException("The end-node " + endNode2.getNodeId().getValue() +
                        " of the connection " + connection.getConnectionId().getValue() +
                        " can not be fw type.");
            }

            if ( endNode2.getNodeType().equals(loadbalancerNodeType) ) {
                throw new IntentResolutionException("The end-node " + endNode2.getNodeId().getValue() +
                        " of the connection " + connection.getConnectionId().getValue() +
                        " can not be lb type.");
            }

            if ( endNode2.getNodeType().equals(cacheNodeType) ) {
                throw new IntentResolutionException("The end-node " + endNode2.getNodeId().getValue() +
                        " of the connection " + connection.getConnectionId().getValue() +
                        " can not be cache type.");
            }
        }

        if ( endNode1.getNodeType().equals(layer3GroupNodeType) ) {
            if ( endNode2.getNodeType().equals(hostNodeType) ) {
                throw new IntentResolutionException("The end-node " + endNode2.getNodeId().getValue() +
                        " of the connection " + connection.getConnectionId().getValue() +
                        " can not be host type.");
            }

            if ( endNode2.getNodeType().equals(layer2GroupNodeType) ) {
                return resolveP2PConnectionBetweenLayer2AndLayer3Groups(user, connection,
                        endNode2, endNode1, virtualNetwork, userIntentVnMapping,
                        tempConnection, resolvingEndNode1, resolvingEndNode2);
            }

            if ( endNode2.getNodeType().equals(layer3GroupNodeType) ) {
                return resolveP2PConnectionBetweenLayer3Groups(user, connection, endNode1,
                        endNode2, virtualNetwork, userIntentVnMapping,
                        tempConnection, resolvingEndNode1, resolvingEndNode2);
            }

            if ( endNode2.getNodeType().equals(externalGroupNodeType) ) {
                if ( IntentResolverUtils.checkExternalLayer3Group(endNode2) ) {
                    return resolveP2PConnectionBetweenLayer3AndExternalLayer3Groups(user, connection,
                            endNode1, endNode2, virtualNetwork, userIntentVnMapping,
                            tempConnection, resolvingEndNode1, resolvingEndNode2);
                } else {
                    return resolveP2PConnectionBetweenLayer3AndExternalLayer2Groups(user, connection,
                            endNode1, endNode2, virtualNetwork, userIntentVnMapping,
                            tempConnection, resolvingEndNode1, resolvingEndNode2);
                }
            }

            if ( endNode2.getNodeType().equals(serviceChainGroupNodeType) ) {
                return null;
            }

            if ( endNode2.getNodeType().equals(firewallNodeType) ) {
                throw new IntentResolutionException("The end-node " + endNode2.getNodeId().getValue() +
                        " of the connection " + connection.getConnectionId().getValue() +
                        " can not be fw type.");
            }

            if ( endNode2.getNodeType().equals(loadbalancerNodeType) ) {
                throw new IntentResolutionException("The end-node " + endNode2.getNodeId().getValue() +
                        " of the connection " + connection.getConnectionId().getValue() +
                        " can not be lb type.");
            }

            if ( endNode2.getNodeType().equals(cacheNodeType) ) {
                throw new IntentResolutionException("The end-node " + endNode2.getNodeId().getValue() +
                        " of the connection " + connection.getConnectionId().getValue() +
                        " can not be cache type.");
            }
        }

        if ( endNode1.getNodeType().equals(externalGroupNodeType) ) {
            if ( endNode2.getNodeType().equals(hostNodeType) ) {
                throw new IntentResolutionException("The end-node " + endNode2.getNodeId().getValue() +
                        " of the connection " + connection.getConnectionId().getValue() +
                        " can not be host type.");
            }

            if ( endNode2.getNodeType().equals(serviceChainGroupNodeType) ) {
                return null;
            }

            if ( endNode2.getNodeType().equals(firewallNodeType) ) {
                throw new IntentResolutionException("The end-node " + endNode2.getNodeId().getValue() +
                        " of the connection " + connection.getConnectionId().getValue() +
                        " can not be fw type.");
            }

            if ( endNode2.getNodeType().equals(loadbalancerNodeType) ) {
                throw new IntentResolutionException("The end-node " + endNode2.getNodeId().getValue() +
                        " of the connection " + connection.getConnectionId().getValue() +
                        " can not be lb type.");
            }

            if ( endNode2.getNodeType().equals(cacheNodeType) ) {
                throw new IntentResolutionException("The end-node " + endNode2.getNodeId().getValue() +
                        " of the connection " + connection.getConnectionId().getValue() +
                        " can not be cache type.");
            }

            if ( IntentResolverUtils.checkExternalLayer3Group(endNode1) ) {
                if ( endNode2.getNodeType().equals(layer2GroupNodeType) ) {
                    return resolveP2PConnectionBetweenLayer2AndExternalLayer3Groups(user, connection,
                            endNode2, endNode1, virtualNetwork, userIntentVnMapping,
                            tempConnection, resolvingEndNode1, resolvingEndNode2);
                }

                if ( endNode2.getNodeType().equals(layer3GroupNodeType) ) {
                    return resolveP2PConnectionBetweenLayer3AndExternalLayer3Groups(user, connection,
                            endNode2, endNode1, virtualNetwork, userIntentVnMapping,
                            tempConnection, resolvingEndNode1, resolvingEndNode2);
                }

                if ( endNode2.getNodeType().equals(externalGroupNodeType) ) {
                    if ( IntentResolverUtils.checkExternalLayer3Group(endNode2) ) {
                        return resolveP2PConnectionBetweenExternalLayer3Groups(user, connection,
                                endNode1, endNode2, virtualNetwork, userIntentVnMapping,
                                tempConnection, resolvingEndNode1, resolvingEndNode2);
                    } else {
                        return resolveP2PConnectionBetweenExternalLayer2AndLayer3Groups(user, connection,
                                endNode2, endNode1, virtualNetwork, userIntentVnMapping,
                                tempConnection, resolvingEndNode1, resolvingEndNode2);
                    }
                }
            } else {
                if ( endNode2.getNodeType().equals(layer2GroupNodeType) ) {
                    return resolveP2PConnectionBetweenLayer2AndExternalLayer2Groups(user, connection,
                            endNode2, endNode1, virtualNetwork, userIntentVnMapping,
                            tempConnection, resolvingEndNode1, resolvingEndNode2);
                }

                if ( endNode2.getNodeType().equals(layer3GroupNodeType) ) {
                    return resolveP2PConnectionBetweenLayer3AndExternalLayer2Groups(user, connection,
                            endNode2, endNode1, virtualNetwork, userIntentVnMapping,
                            tempConnection, resolvingEndNode1, resolvingEndNode2);
                }

                if ( endNode2.getNodeType().equals(externalGroupNodeType) ) {
                    if ( IntentResolverUtils.checkExternalLayer3Group(endNode2) ) {
                        return resolveP2PConnectionBetweenExternalLayer2AndLayer3Groups(user, connection,
                                endNode1, endNode2, virtualNetwork, userIntentVnMapping,
                                tempConnection, resolvingEndNode1, resolvingEndNode2);
                    } else {
                        return resolveP2PConnectionBetweenExternalLayer2Groups(user, connection,
                                endNode1, endNode2, virtualNetwork, userIntentVnMapping,
                                tempConnection, resolvingEndNode1, resolvingEndNode2);
                    }
                }
            }
        }

        if ( endNode1.getNodeType().equals(serviceChainGroupNodeType) ) {
            return null;
        }

        if ( endNode1.getNodeType().equals(firewallNodeType) ) {
            throw new IntentResolutionException("The end-node " + endNode1.getNodeId().getValue() +
                    " of the connection " + connection.getConnectionId().getValue() +
                    " can not be fw type.");
        }

        if ( endNode1.getNodeType().equals(loadbalancerNodeType) ) {
            throw new IntentResolutionException("The end-node " + endNode1.getNodeId().getValue() +
                    " of the connection " + connection.getConnectionId().getValue() +
                    " can not be lb type.");
        }

        if ( endNode1.getNodeType().equals(cacheNodeType) ) {
            throw new IntentResolutionException("The end-node " + endNode1.getNodeId().getValue() +
                    " of the connection " + connection.getConnectionId().getValue() +
                    " can not be cache type.");
        }

        throw new IntentResolutionException("Unknown node type for the end-nodes of the connection " +
                connection.getConnectionId().getValue() + ".");

//        return null;
    }

    /**
     * TODO
     *
     * @param user TODO
     * @param connection TODO
     * @param virtualNetwork TODO
     * @param userIntentVnMapping TODO
     */
    private void resolveP2MPConnection(User user, Connection connection,
                                       VirtualNetwork virtualNetwork,
                                       UserIntentVnMapping userIntentVnMapping)
            throws IntentResolutionException {
        // TODO: 1、注意root是external layer2 group的情况，只能映射一次。

        return;
    }

    /**
     * TODO
     *
     * @param user TODO
     * @param connection TODO
     * @param virtualNetwork TODO
     * @param userIntentVnMapping TODO
     */
    private void resolveMeshConnection(User user, Connection connection,
                                       VirtualNetwork virtualNetwork,
                                       UserIntentVnMapping userIntentVnMapping)
            throws IntentResolutionException {
        // TODO: 1、注意各个end node是external layer2 group的情况，只能映射一次。

        return;
    }

    /**
     * TODO
     *
     * @param user TODO
     * @param connection TODO
     * @param endNode1 TODO
     * @param endNode2 TODO
     * @param virtualNetwork TODO
     * @param userIntentVnMapping TODO
     * @param tempConnection TODO
     * @param resolvingEndNode1 TODO
     * @param resolvingEndNode2 TODO
     * @return TODO
     */
    private List<VirtualLink> resolveP2PConnectionBetweenLayer2Groups(User user,
                                                                      Connection connection,
                                                                      Node endNode1,
                                                                      Node endNode2,
                                                                      VirtualNetwork virtualNetwork,
                                                                      UserIntentVnMapping userIntentVnMapping,
                                                                      boolean tempConnection,
                                                                      boolean resolvingEndNode1,
                                                                      boolean resolvingEndNode2)
            throws IntentResolutionException {
        List<IntentVnMappingResult> intentVnMappingResults = userIntentVnMapping.getIntentVnMappingResult();
        IntentVnMappingResult intentVnMappingResult1 = IntentResolverUtils
                .getIntentVnMappingResult(intentVnMappingResults, new IntentId(endNode1.getNodeId().getValue()));

        if ( null == intentVnMappingResult1 ) {
            throw new IntentResolutionException("Can not get the intent-vn mapping result for " +
                    "the node " + endNode1.getNodeId().getValue() + ".");
        }

        IntentVnMappingResult intentVnMappingResult2 = IntentResolverUtils
                .getIntentVnMappingResult(intentVnMappingResults, new IntentId(endNode2.getNodeId().getValue()));

        if ( null == intentVnMappingResult2 ) {
            throw new IntentResolutionException("Can not get the intent-vn mapping result for " +
                    "the node " + endNode2.getNodeId().getValue() + ".");
        }

        List<VirtualNode> virtualNodes = virtualNetwork.getVirtualNodes().getVirtualNode();
        VirtualNodeId virtualNodeId = new VirtualNodeId(intentVnMappingResult1.getVirtualResource().get(0)
                .getVirtualResourceEntityId().getValue());
        VirtualNode virtualRouter1 = IntentResolverUtils.getVirtualNode(virtualNodes, virtualNodeId);

        if ( null == virtualRouter1 ) {
            throw new IntentResolutionException("Can not get the virtual node created for " +
                    "the node " + endNode1.getNodeId().getValue() + ".");
        }

        virtualNodeId = new VirtualNodeId(intentVnMappingResult2.getVirtualResource().get(0)
                .getVirtualResourceEntityId().getValue());
        VirtualNode virtualRouter2 = IntentResolverUtils.getVirtualNode(virtualNodes, virtualNodeId);

        if ( null == virtualRouter2 ) {
            throw new IntentResolutionException("Can not get the virtual node created for " +
                    "the node " + endNode2.getNodeId().getValue() + ".");
        }

        Property property = IntentResolverUtils
                .getConnectionProperty(connection.getProperty(), new PropertyName("bandwidth"));
        long bandwidth = 0;

        if ( null != property ) {
            bandwidth = /*1024 * */property.getPropertyValues().getIntValue().get(0).getValue();
        }

        VirtualPort virtualPort1 = new VirtualPortBuilder()
                .setPortId(new VirtualPortId(UUID.randomUUID().toString()))
                .setPortType(VirtualPort.PortType.Internal)
                .setBandwidth(bandwidth)
                .build();
        virtualRouter1.getVirtualPort().add(virtualPort1);

        VirtualPort virtualPort2 = new VirtualPortBuilder()
                .setPortId(new VirtualPortId(UUID.randomUUID().toString()))
                .setPortType(VirtualPort.PortType.Internal)
                .setBandwidth(bandwidth)
                .build();
        virtualRouter2.getVirtualPort().add(virtualPort2);

        VirtualLink virtualLink1 = new VirtualLinkBuilder()
                .setLinkId(new VirtualLinkId(UUID.randomUUID().toString()))
                .setSrcNodeId(virtualRouter1.getNodeId())
                .setSrcPortId(virtualPort1.getPortId())
                .setDestNodeId(virtualRouter2.getNodeId())
                .setDestPortId(virtualPort2.getPortId())
                .setBandwidth(bandwidth)
                .build();
        VirtualLink virtualLink2 = new VirtualLinkBuilder()
                .setLinkId(new VirtualLinkId(UUID.randomUUID().toString()))
                .setSrcNodeId(virtualRouter2.getNodeId())
                .setSrcPortId(virtualPort2.getPortId())
                .setDestNodeId(virtualRouter1.getNodeId())
                .setDestPortId(virtualPort1.getPortId())
                .setBandwidth(bandwidth)
                .build();

        if ( tempConnection ) {
            List<VirtualLink> virtualLinks = new ArrayList<VirtualLink>(2);

            virtualLinks.add(virtualLink1);
            virtualLinks.add(virtualLink2);

            return virtualLinks;
        }

        List<VirtualLink> virtualLinks = virtualNetwork.getVirtualLinks().getVirtualLink();

        virtualLinks.add(virtualLink1);
        virtualLinks.add(virtualLink2);

        List<VirtualResource> virtualResources = new ArrayList<VirtualResource>(2);

        VirtualResource virtualResource = new VirtualResourceBuilder()
                .setVirtualResourceId(new VirtualResourceId(UUID.randomUUID().toString()))
                .setVirtualResourceType(VirtualResource.VirtualResourceType.Vlink)
                .setVirtualResourceEntityId(new VirtualResourceEntityId(virtualLink1.getLinkId().getValue()))
                .setOrder(0L)
                .build();
        virtualResources.add(virtualResource);

        virtualResource = new VirtualResourceBuilder()
                .setVirtualResourceId(new VirtualResourceId(UUID.randomUUID().toString()))
                .setVirtualResourceType(VirtualResource.VirtualResourceType.Vlink)
                .setVirtualResourceEntityId(new VirtualResourceEntityId(virtualLink2.getLinkId().getValue()))
                .setOrder(0L)
                .build();
        virtualResources.add(virtualResource);

        IntentVnMappingResult intentVnMappingResult = new IntentVnMappingResultBuilder()
                .setIntentId(new IntentId(connection.getConnectionId().getValue()))
                .setIntentType(IntentVnMappingResult.IntentType.Connection)
                .setVirtualResource(virtualResources)
                .build();

        intentVnMappingResults.add(intentVnMappingResult);

        return null;
    }

    /**
     * TODO
     *
     * @param user TODO
     * @param connection TODO
     * @param endNode1 TODO
     * @param endNode2 TODO
     * @param virtualNetwork TODO
     * @param userIntentVnMapping TODO
     * @param tempConnection TODO
     * @param resolvingEndNode1 TODO
     * @param resolvingEndNode2 TODO
     * @return TODO
     */
    private List<VirtualLink> resolveP2PConnectionBetweenLayer2AndLayer3Groups(User user,
                                                                               Connection connection,
                                                                               Node endNode1,
                                                                               Node endNode2,
                                                                               VirtualNetwork virtualNetwork,
                                                                               UserIntentVnMapping userIntentVnMapping,
                                                                               boolean tempConnection,
                                                                               boolean resolvingEndNode1,
                                                                               boolean resolvingEndNode2)
            throws IntentResolutionException {
        // TODO: l2-group - l3-group: 创建l2-group的gw vrouter与l3-group的sub-nodes的gw/acc vrouters间的vlinks（vports）。

        return null;
    }

    /**
     * TODO
     *
     * @param user TODO
     * @param connection TODO
     * @param endNode1 TODO
     * @param endNode2 TODO
     * @param virtualNetwork TODO
     * @param userIntentVnMapping TODO
     * @param tempConnection TODO
     * @param resolvingEndNode1 TODO
     * @param resolvingEndNode2 TODO
     * @return TODO
     */
    private List<VirtualLink> resolveP2PConnectionBetweenLayer2AndExternalLayer2Groups(User user,
                                                                                       Connection connection,
                                                                                       Node endNode1,
                                                                                       Node endNode2,
                                                                                       VirtualNetwork virtualNetwork,
                                                                                       UserIntentVnMapping userIntentVnMapping,
                                                                                       boolean tempConnection,
                                                                                       boolean resolvingEndNode1,
                                                                                       boolean resolvingEndNode2)
            throws IntentResolutionException {
        if ( resolvingEndNode2 ) {
            nodeMapper.resolveExternalLayer2Group(user, endNode2, virtualNetwork, userIntentVnMapping, true);
        }

        List<IntentVnMappingResult> intentVnMappingResults = userIntentVnMapping.getIntentVnMappingResult();
        IntentVnMappingResult intentVnMappingResult1 = IntentResolverUtils
                .getIntentVnMappingResult(intentVnMappingResults, new IntentId(endNode1.getNodeId().getValue()));

        if ( null == intentVnMappingResult1 ) {
            throw new IntentResolutionException("Can not get the intent-vn mapping result for " +
                    "the node " + endNode1.getNodeId().getValue() + ".");
        }

        IntentVnMappingResult intentVnMappingResult2 = IntentResolverUtils
                .getIntentVnMappingResult(intentVnMappingResults, new IntentId(endNode2.getNodeId().getValue()));

        if ( null == intentVnMappingResult2 ) {
            throw new IntentResolutionException("Can not get the intent-vn mapping result for " +
                    "the node " + endNode2.getNodeId().getValue() + ".");
        }

        List<VirtualNode> virtualNodes = virtualNetwork.getVirtualNodes().getVirtualNode();
        VirtualNodeId virtualNodeId = new VirtualNodeId(intentVnMappingResult1.getVirtualResource().get(0)
                .getVirtualResourceEntityId().getValue());
        VirtualNode virtualRouter1 = IntentResolverUtils.getVirtualNode(virtualNodes, virtualNodeId);

        if ( null == virtualRouter1 ) {
            throw new IntentResolutionException("Can not get the virtual node created for " +
                    "the node " + endNode1.getNodeId().getValue() + ".");
        }

        virtualNodeId = new VirtualNodeId(intentVnMappingResult2.getVirtualResource().get(0)
                .getParentVirtualResourceEntityId().getValue());
        VirtualNode virtualRouter2 = IntentResolverUtils.getVirtualNode(virtualNodes, virtualNodeId);

        if ( null == virtualRouter2 ) {
            throw new IntentResolutionException("Can not get the virtual node created for " +
                    "the node " + endNode2.getNodeId().getValue() + ".");
        }

        Property property = IntentResolverUtils
                .getConnectionProperty(connection.getProperty(), new PropertyName("bandwidth"));
        long bandwidth = 0;

        if ( null != property ) {
            bandwidth = /*1024 * */property.getPropertyValues().getIntValue().get(0).getValue();
        }

        VirtualPort virtualPort1 = new VirtualPortBuilder()
                .setPortId(new VirtualPortId(UUID.randomUUID().toString()))
                .setPortType(VirtualPort.PortType.Internal)
                .setBandwidth(bandwidth)
                .build();
        virtualRouter1.getVirtualPort().add(virtualPort1);

        VirtualPort virtualPort2 = new VirtualPortBuilder()
                .setPortId(new VirtualPortId(UUID.randomUUID().toString()))
                .setPortType(VirtualPort.PortType.Internal)
                .setBandwidth(bandwidth)
                .build();
        virtualRouter2.getVirtualPort().add(virtualPort2);

        VirtualLink virtualLink1 = new VirtualLinkBuilder()
                .setLinkId(new VirtualLinkId(UUID.randomUUID().toString()))
                .setSrcNodeId(virtualRouter1.getNodeId())
                .setSrcPortId(virtualPort1.getPortId())
                .setDestNodeId(virtualRouter2.getNodeId())
                .setDestPortId(virtualPort2.getPortId())
                .setBandwidth(bandwidth)
                .build();
        VirtualLink virtualLink2 = new VirtualLinkBuilder()
                .setLinkId(new VirtualLinkId(UUID.randomUUID().toString()))
                .setSrcNodeId(virtualRouter2.getNodeId())
                .setSrcPortId(virtualPort2.getPortId())
                .setDestNodeId(virtualRouter1.getNodeId())
                .setDestPortId(virtualPort1.getPortId())
                .setBandwidth(bandwidth)
                .build();

        if ( tempConnection ) {
            List<VirtualLink> virtualLinks = new ArrayList<VirtualLink>(2);

            virtualLinks.add(virtualLink1);
            virtualLinks.add(virtualLink2);

            return virtualLinks;
        }

        List<VirtualLink> virtualLinks = virtualNetwork.getVirtualLinks().getVirtualLink();

        virtualLinks.add(virtualLink1);
        virtualLinks.add(virtualLink2);

        List<VirtualResource> virtualResources = new ArrayList<VirtualResource>(2);

        VirtualResource virtualResource = new VirtualResourceBuilder()
                .setVirtualResourceId(new VirtualResourceId(UUID.randomUUID().toString()))
                .setVirtualResourceType(VirtualResource.VirtualResourceType.Vlink)
                .setVirtualResourceEntityId(new VirtualResourceEntityId(virtualLink1.getLinkId().getValue()))
                .setOrder(0L)
                .build();
        virtualResources.add(virtualResource);

        virtualResource = new VirtualResourceBuilder()
                .setVirtualResourceId(new VirtualResourceId(UUID.randomUUID().toString()))
                .setVirtualResourceType(VirtualResource.VirtualResourceType.Vlink)
                .setVirtualResourceEntityId(new VirtualResourceEntityId(virtualLink2.getLinkId().getValue()))
                .setOrder(0L)
                .build();
        virtualResources.add(virtualResource);

        IntentVnMappingResult intentVnMappingResult = new IntentVnMappingResultBuilder()
                .setIntentId(new IntentId(connection.getConnectionId().getValue()))
                .setIntentType(IntentVnMappingResult.IntentType.Connection)
                .setVirtualResource(virtualResources)
                .build();

        intentVnMappingResults.add(intentVnMappingResult);

        return null;
    }

    /**
     * TODO
     *
     * @param user TODO
     * @param connection TODO
     * @param endNode1 TODO
     * @param endNode2 TODO
     * @param virtualNetwork TODO
     * @param userIntentVnMapping TODO
     * @param tempConnection TODO
     * @param resolvingEndNode1 TODO
     * @param resolvingEndNode2 TODO
     * @return TODO
     */
    private List<VirtualLink> resolveP2PConnectionBetweenLayer2AndExternalLayer3Groups(User user,
                                                                                       Connection connection,
                                                                                       Node endNode1,
                                                                                       Node endNode2,
                                                                                       VirtualNetwork virtualNetwork,
                                                                                       UserIntentVnMapping userIntentVnMapping,
                                                                                       boolean tempConnection,
                                                                                       boolean resolvingEndNode1,
                                                                                       boolean resolvingEndNode2)
            throws IntentResolutionException {
        List<IntentVnMappingResult> intentVnMappingResults = userIntentVnMapping.getIntentVnMappingResult();
        IntentVnMappingResult intentVnMappingResult1 = IntentResolverUtils
                .getIntentVnMappingResult(intentVnMappingResults, new IntentId(endNode1.getNodeId().getValue()));

        if ( null == intentVnMappingResult1 ) {
            throw new IntentResolutionException("Can not get the intent-vn mapping result for " +
                    "the node " + endNode1.getNodeId().getValue() + ".");
        }

        IntentVnMappingResult intentVnMappingResult2 = IntentResolverUtils
                .getIntentVnMappingResult(intentVnMappingResults, new IntentId(endNode2.getNodeId().getValue()));

        if ( null == intentVnMappingResult2 ) {
            throw new IntentResolutionException("Can not get the intent-vn mapping result for " +
                    "the node " + endNode2.getNodeId().getValue() + ".");
        }

        List<VirtualNode> virtualNodes = virtualNetwork.getVirtualNodes().getVirtualNode();
        VirtualNodeId virtualNodeId = new VirtualNodeId(intentVnMappingResult1.getVirtualResource().get(0)
                .getVirtualResourceEntityId().getValue());
        VirtualNode virtualRouter1 = IntentResolverUtils.getVirtualNode(virtualNodes, virtualNodeId);

        if ( null == virtualRouter1 ) {
            throw new IntentResolutionException("Can not get the virtual node created for " +
                    "the node " + endNode1.getNodeId().getValue() + ".");
        }

        virtualNodeId = new VirtualNodeId(intentVnMappingResult2.getVirtualResource().get(0)
                .getParentVirtualResourceEntityId().getValue());
        VirtualNode virtualRouter2 = IntentResolverUtils.getVirtualNode(virtualNodes, virtualNodeId);

        if ( null == virtualRouter2 ) {
            throw new IntentResolutionException("Can not get the virtual node created for " +
                    "the node " + endNode2.getNodeId().getValue() + ".");
        }

        Property property = IntentResolverUtils
                .getConnectionProperty(connection.getProperty(), new PropertyName("bandwidth"));
        long bandwidth = 0;

        if ( null != property ) {
            bandwidth = /*1024 * */property.getPropertyValues().getIntValue().get(0).getValue();
        }

        VirtualPort virtualPort1 = new VirtualPortBuilder()
                .setPortId(new VirtualPortId(UUID.randomUUID().toString()))
                .setPortType(VirtualPort.PortType.Internal)
                .setBandwidth(bandwidth)
                .build();
        virtualRouter1.getVirtualPort().add(virtualPort1);

        VirtualPort virtualPort2 = new VirtualPortBuilder()
                .setPortId(new VirtualPortId(UUID.randomUUID().toString()))
                .setPortType(VirtualPort.PortType.Internal)
                .setBandwidth(bandwidth)
                .build();
        virtualRouter2.getVirtualPort().add(virtualPort2);

        VirtualLink virtualLink1 = new VirtualLinkBuilder()
                .setLinkId(new VirtualLinkId(UUID.randomUUID().toString()))
                .setSrcNodeId(virtualRouter1.getNodeId())
                .setSrcPortId(virtualPort1.getPortId())
                .setDestNodeId(virtualRouter2.getNodeId())
                .setDestPortId(virtualPort2.getPortId())
                .setBandwidth(bandwidth)
                .build();
        VirtualLink virtualLink2 = new VirtualLinkBuilder()
                .setLinkId(new VirtualLinkId(UUID.randomUUID().toString()))
                .setSrcNodeId(virtualRouter2.getNodeId())
                .setSrcPortId(virtualPort2.getPortId())
                .setDestNodeId(virtualRouter1.getNodeId())
                .setDestPortId(virtualPort1.getPortId())
                .setBandwidth(bandwidth)
                .build();

        if ( tempConnection ) {
            List<VirtualLink> virtualLinks = new ArrayList<VirtualLink>(2);

            virtualLinks.add(virtualLink1);
            virtualLinks.add(virtualLink2);

            return virtualLinks;
        }

        List<VirtualLink> virtualLinks = virtualNetwork.getVirtualLinks().getVirtualLink();

        virtualLinks.add(virtualLink1);
        virtualLinks.add(virtualLink2);

        List<VirtualResource> virtualResources = new ArrayList<VirtualResource>(2);

        VirtualResource virtualResource = new VirtualResourceBuilder()
                .setVirtualResourceId(new VirtualResourceId(UUID.randomUUID().toString()))
                .setVirtualResourceType(VirtualResource.VirtualResourceType.Vlink)
                .setVirtualResourceEntityId(new VirtualResourceEntityId(virtualLink1.getLinkId().getValue()))
                .setOrder(0L)
                .build();
        virtualResources.add(virtualResource);

        virtualResource = new VirtualResourceBuilder()
                .setVirtualResourceId(new VirtualResourceId(UUID.randomUUID().toString()))
                .setVirtualResourceType(VirtualResource.VirtualResourceType.Vlink)
                .setVirtualResourceEntityId(new VirtualResourceEntityId(virtualLink2.getLinkId().getValue()))
                .setOrder(0L)
                .build();
        virtualResources.add(virtualResource);

        IntentVnMappingResult intentVnMappingResult = new IntentVnMappingResultBuilder()
                .setIntentId(new IntentId(connection.getConnectionId().getValue()))
                .setIntentType(IntentVnMappingResult.IntentType.Connection)
                .setVirtualResource(virtualResources)
                .build();

        intentVnMappingResults.add(intentVnMappingResult);

        return null;
    }

    /**
     * TODO
     *
     * @param user TODO
     * @param connection TODO
     * @param endNode1 TODO
     * @param endNode2 TODO
     * @param virtualNetwork TODO
     * @param userIntentVnMapping TODO
     * @param tempConnection TODO
     * @param resolvingEndNode1 TODO
     * @param resolvingEndNode2 TODO
     * @return TODO
     */
    private List<VirtualLink> resolveP2PConnectionBetweenLayer3Groups(User user,
                                                                      Connection connection,
                                                                      Node endNode1,
                                                                      Node endNode2,
                                                                      VirtualNetwork virtualNetwork,
                                                                      UserIntentVnMapping userIntentVnMapping,
                                                                      boolean tempConnection,
                                                                      boolean resolvingEndNode1,
                                                                      boolean resolvingEndNode2)
            throws IntentResolutionException {
        // TODO: l3-group - l3-group: 创建第一l3-group的sub-nodes的gw/acc vrouters与第二l3-group的sub-nodes的gw/acc vrouters间的vlinks（vports）。

        return null;
    }

    /**
     * TODO
     *
     * @param user TODO
     * @param connection TODO
     * @param endNode1 TODO
     * @param endNode2 TODO
     * @param virtualNetwork TODO
     * @param userIntentVnMapping TODO
     * @param tempConnection TODO
     * @param resolvingEndNode1 TODO
     * @param resolvingEndNode2 TODO
     * @return TODO
     */
    private List<VirtualLink> resolveP2PConnectionBetweenLayer3AndExternalLayer2Groups(User user,
                                                                                       Connection connection,
                                                                                       Node endNode1,
                                                                                       Node endNode2,
                                                                                       VirtualNetwork virtualNetwork,
                                                                                       UserIntentVnMapping userIntentVnMapping,
                                                                                       boolean tempConnection,
                                                                                       boolean resolvingEndNode1,
                                                                                       boolean resolvingEndNode2)
            throws IntentResolutionException {
        // TODO: l3-group - ext-group<l2>: 为ext-group<l2>创建gw vrouter(vport)，创建新vrouter与l3-group的sub-nodes的gw/acc vrouters间的vlinks（vports）。

        return null;
    }

    /**
     * TODO
     *
     * @param user TODO
     * @param connection TODO
     * @param endNode1 TODO
     * @param endNode2 TODO
     * @param virtualNetwork TODO
     * @param userIntentVnMapping TODO
     * @param tempConnection TODO
     * @param resolvingEndNode1 TODO
     * @param resolvingEndNode2 TODO
     * @return TODO
     */
    private List<VirtualLink> resolveP2PConnectionBetweenLayer3AndExternalLayer3Groups(User user,
                                                                                       Connection connection,
                                                                                       Node endNode1,
                                                                                       Node endNode2,
                                                                                       VirtualNetwork virtualNetwork,
                                                                                       UserIntentVnMapping userIntentVnMapping,
                                                                                       boolean tempConnection,
                                                                                       boolean resolvingEndNode1,
                                                                                       boolean resolvingEndNode2)
            throws IntentResolutionException {
        // TODO: l3-group - ext-group<l3>: 创建ext-group<l3>的acc vrouter与l3-group的sub-nodes的gw/acc vrouters间的vlinks（vports）。

        return null;
    }

    /**
     * TODO
     *
     * @param user TODO
     * @param connection TODO
     * @param endNode1 TODO
     * @param endNode2 TODO
     * @param virtualNetwork TODO
     * @param userIntentVnMapping TODO
     * @param tempConnection TODO
     * @param resolvingEndNode1 TODO
     * @param resolvingEndNode2 TODO
     * @return TODO
     */
    private List<VirtualLink> resolveP2PConnectionBetweenExternalLayer2Groups(User user,
                                                                              Connection connection,
                                                                              Node endNode1,
                                                                              Node endNode2,
                                                                              VirtualNetwork virtualNetwork,
                                                                              UserIntentVnMapping userIntentVnMapping,
                                                                              boolean tempConnection,
                                                                              boolean resolvingEndNode1,
                                                                              boolean resolvingEndNode2)
            throws IntentResolutionException {
        boolean sameIpPrefix;

        sameIpPrefix = true;// TODO: for sameIpPrefix.

        if ( sameIpPrefix ) {
            if ( resolvingEndNode1 ) {
                nodeMapper.resolveExternalLayer2Group(user, endNode1, virtualNetwork, userIntentVnMapping, false);
            }

            if ( resolvingEndNode2 ) {
                nodeMapper.resolveExternalLayer2Group(user, endNode2, virtualNetwork, userIntentVnMapping, false);
            }
        } else {
            if ( resolvingEndNode1 ) {
                nodeMapper.resolveExternalLayer2Group(user, endNode1, virtualNetwork, userIntentVnMapping, true);
            }

            if ( resolvingEndNode2 ) {
                nodeMapper.resolveExternalLayer2Group(user, endNode2, virtualNetwork, userIntentVnMapping, true);
            }
        }

        List<IntentVnMappingResult> intentVnMappingResults = userIntentVnMapping.getIntentVnMappingResult();
        IntentVnMappingResult intentVnMappingResult1 = IntentResolverUtils
                .getIntentVnMappingResult(intentVnMappingResults, new IntentId(endNode1.getNodeId().getValue()));

        if ( null == intentVnMappingResult1 ) {
            throw new IntentResolutionException("Can not get the intent-vn mapping result for " +
                    "the node " + endNode1.getNodeId().getValue() + ".");
        }

        IntentVnMappingResult intentVnMappingResult2 = IntentResolverUtils
                .getIntentVnMappingResult(intentVnMappingResults, new IntentId(endNode2.getNodeId().getValue()));

        if ( null == intentVnMappingResult2 ) {
            throw new IntentResolutionException("Can not get the intent-vn mapping result for " +
                    "the node " + endNode2.getNodeId().getValue() + ".");
        }

        List<VirtualNode> virtualNodes = virtualNetwork.getVirtualNodes().getVirtualNode();
        VirtualNodeId virtualNodeId = new VirtualNodeId(intentVnMappingResult1.getVirtualResource().get(0)
                .getParentVirtualResourceEntityId().getValue());
        VirtualNode virtualRouter1 = IntentResolverUtils.getVirtualNode(virtualNodes, virtualNodeId);

        if ( null == virtualRouter1 ) {
            throw new IntentResolutionException("Can not get the virtual node created for " +
                    "the node " + endNode1.getNodeId().getValue() + ".");
        }

        virtualNodeId = new VirtualNodeId(intentVnMappingResult2.getVirtualResource().get(0)
                .getParentVirtualResourceEntityId().getValue());
        VirtualNode virtualRouter2 = IntentResolverUtils.getVirtualNode(virtualNodes, virtualNodeId);

        if ( null == virtualRouter2 ) {
            throw new IntentResolutionException("Can not get the virtual node created for " +
                    "the node " + endNode2.getNodeId().getValue() + ".");
        }

        Property property = IntentResolverUtils
                .getConnectionProperty(connection.getProperty(), new PropertyName("bandwidth"));
        long bandwidth = 0;

        if ( null != property ) {
            bandwidth = /*1024 * */property.getPropertyValues().getIntValue().get(0).getValue();
        }

        VirtualPort virtualPort1 = new VirtualPortBuilder()
                .setPortId(new VirtualPortId(UUID.randomUUID().toString()))
                .setPortType(VirtualPort.PortType.Internal)
                .setBandwidth(bandwidth)
                .build();
        virtualRouter1.getVirtualPort().add(virtualPort1);

        VirtualPort virtualPort2 = new VirtualPortBuilder()
                .setPortId(new VirtualPortId(UUID.randomUUID().toString()))
                .setPortType(VirtualPort.PortType.Internal)
                .setBandwidth(bandwidth)
                .build();
        virtualRouter2.getVirtualPort().add(virtualPort2);

        VirtualLink virtualLink1 = new VirtualLinkBuilder()
                .setLinkId(new VirtualLinkId(UUID.randomUUID().toString()))
                .setSrcNodeId(virtualRouter1.getNodeId())
                .setSrcPortId(virtualPort1.getPortId())
                .setDestNodeId(virtualRouter2.getNodeId())
                .setDestPortId(virtualPort2.getPortId())
                .setBandwidth(bandwidth)
                .build();
        VirtualLink virtualLink2 = new VirtualLinkBuilder()
                .setLinkId(new VirtualLinkId(UUID.randomUUID().toString()))
                .setSrcNodeId(virtualRouter2.getNodeId())
                .setSrcPortId(virtualPort2.getPortId())
                .setDestNodeId(virtualRouter1.getNodeId())
                .setDestPortId(virtualPort1.getPortId())
                .setBandwidth(bandwidth)
                .build();

        if ( tempConnection ) {
            List<VirtualLink> virtualLinks = new ArrayList<VirtualLink>(2);

            virtualLinks.add(virtualLink1);
            virtualLinks.add(virtualLink2);

            return virtualLinks;
        }

        List<VirtualLink> virtualLinks = virtualNetwork.getVirtualLinks().getVirtualLink();

        virtualLinks.add(virtualLink1);
        virtualLinks.add(virtualLink2);

        List<VirtualResource> virtualResources = new ArrayList<VirtualResource>(2);

        VirtualResource virtualResource = new VirtualResourceBuilder()
                .setVirtualResourceId(new VirtualResourceId(UUID.randomUUID().toString()))
                .setVirtualResourceType(VirtualResource.VirtualResourceType.Vlink)
                .setVirtualResourceEntityId(new VirtualResourceEntityId(virtualLink1.getLinkId().getValue()))
                .setOrder(0L)
                .build();
        virtualResources.add(virtualResource);

        virtualResource = new VirtualResourceBuilder()
                .setVirtualResourceId(new VirtualResourceId(UUID.randomUUID().toString()))
                .setVirtualResourceType(VirtualResource.VirtualResourceType.Vlink)
                .setVirtualResourceEntityId(new VirtualResourceEntityId(virtualLink2.getLinkId().getValue()))
                .setOrder(0L)
                .build();
        virtualResources.add(virtualResource);

        IntentVnMappingResult intentVnMappingResult = new IntentVnMappingResultBuilder()
                .setIntentId(new IntentId(connection.getConnectionId().getValue()))
                .setIntentType(IntentVnMappingResult.IntentType.Connection)
                .setVirtualResource(virtualResources)
                .build();

        intentVnMappingResults.add(intentVnMappingResult);

        return null;
    }

    /**
     * TODO
     *
     * @param user TODO
     * @param connection TODO
     * @param endNode1 TODO
     * @param endNode2 TODO
     * @param virtualNetwork TODO
     * @param userIntentVnMapping TODO
     * @param tempConnection TODO
     * @param resolvingEndNode1 TODO
     * @param resolvingEndNode2 TODO
     * @return TODO
     */
    private List<VirtualLink> resolveP2PConnectionBetweenExternalLayer2AndLayer3Groups(User user,
                                                                                       Connection connection,
                                                                                       Node endNode1,
                                                                                       Node endNode2,
                                                                                       VirtualNetwork virtualNetwork,
                                                                                       UserIntentVnMapping userIntentVnMapping,
                                                                                       boolean tempConnection,
                                                                                       boolean resolvingEndNode1,
                                                                                       boolean resolvingEndNode2)
            throws IntentResolutionException {
        if ( resolvingEndNode1 ) {
            nodeMapper.resolveExternalLayer2Group(user, endNode1, virtualNetwork, userIntentVnMapping, true);
        }

        List<IntentVnMappingResult> intentVnMappingResults = userIntentVnMapping.getIntentVnMappingResult();
        IntentVnMappingResult intentVnMappingResult1 = IntentResolverUtils
                .getIntentVnMappingResult(intentVnMappingResults, new IntentId(endNode1.getNodeId().getValue()));

        if ( null == intentVnMappingResult1 ) {
            throw new IntentResolutionException("Can not get the intent-vn mapping result for " +
                    "the node " + endNode1.getNodeId().getValue() + ".");
        }

        IntentVnMappingResult intentVnMappingResult2 = IntentResolverUtils
                .getIntentVnMappingResult(intentVnMappingResults, new IntentId(endNode2.getNodeId().getValue()));

        if ( null == intentVnMappingResult2 ) {
            throw new IntentResolutionException("Can not get the intent-vn mapping result for " +
                    "the node " + endNode2.getNodeId().getValue() + ".");
        }

        List<VirtualNode> virtualNodes = virtualNetwork.getVirtualNodes().getVirtualNode();
        VirtualNodeId virtualNodeId = new VirtualNodeId(intentVnMappingResult1.getVirtualResource().get(0)
                .getParentVirtualResourceEntityId().getValue());
        VirtualNode virtualRouter1 = IntentResolverUtils.getVirtualNode(virtualNodes, virtualNodeId);

        if ( null == virtualRouter1 ) {
            throw new IntentResolutionException("Can not get the virtual node created for " +
                    "the node " + endNode1.getNodeId().getValue() + ".");
        }

        virtualNodeId = new VirtualNodeId(intentVnMappingResult2.getVirtualResource().get(0)
                .getParentVirtualResourceEntityId().getValue());
        VirtualNode virtualRouter2 = IntentResolverUtils.getVirtualNode(virtualNodes, virtualNodeId);

        if ( null == virtualRouter2 ) {
            throw new IntentResolutionException("Can not get the virtual node created for " +
                    "the node " + endNode2.getNodeId().getValue() + ".");
        }

        Property property = IntentResolverUtils
                .getConnectionProperty(connection.getProperty(), new PropertyName("bandwidth"));
        long bandwidth = 0;

        if ( null != property ) {
            bandwidth = /*1024 * */property.getPropertyValues().getIntValue().get(0).getValue();
        }

        VirtualPort virtualPort1 = new VirtualPortBuilder()
                .setPortId(new VirtualPortId(UUID.randomUUID().toString()))
                .setPortType(VirtualPort.PortType.Internal)
                .setBandwidth(bandwidth)
                .build();
        virtualRouter1.getVirtualPort().add(virtualPort1);

        VirtualPort virtualPort2 = new VirtualPortBuilder()
                .setPortId(new VirtualPortId(UUID.randomUUID().toString()))
                .setPortType(VirtualPort.PortType.Internal)
                .setBandwidth(bandwidth)
                .build();
        virtualRouter2.getVirtualPort().add(virtualPort2);

        VirtualLink virtualLink1 = new VirtualLinkBuilder()
                .setLinkId(new VirtualLinkId(UUID.randomUUID().toString()))
                .setSrcNodeId(virtualRouter1.getNodeId())
                .setSrcPortId(virtualPort1.getPortId())
                .setDestNodeId(virtualRouter2.getNodeId())
                .setDestPortId(virtualPort2.getPortId())
                .setBandwidth(bandwidth)
                .build();
        VirtualLink virtualLink2 = new VirtualLinkBuilder()
                .setLinkId(new VirtualLinkId(UUID.randomUUID().toString()))
                .setSrcNodeId(virtualRouter2.getNodeId())
                .setSrcPortId(virtualPort2.getPortId())
                .setDestNodeId(virtualRouter1.getNodeId())
                .setDestPortId(virtualPort1.getPortId())
                .setBandwidth(bandwidth)
                .build();

        if ( tempConnection ) {
            List<VirtualLink> virtualLinks = new ArrayList<VirtualLink>(2);

            virtualLinks.add(virtualLink1);
            virtualLinks.add(virtualLink2);

            return virtualLinks;
        }

        List<VirtualLink> virtualLinks = virtualNetwork.getVirtualLinks().getVirtualLink();

        virtualLinks.add(virtualLink1);
        virtualLinks.add(virtualLink2);

        List<VirtualResource> virtualResources = new ArrayList<VirtualResource>(2);

        VirtualResource virtualResource = new VirtualResourceBuilder()
                .setVirtualResourceId(new VirtualResourceId(UUID.randomUUID().toString()))
                .setVirtualResourceType(VirtualResource.VirtualResourceType.Vlink)
                .setVirtualResourceEntityId(new VirtualResourceEntityId(virtualLink1.getLinkId().getValue()))
                .setOrder(0L)
                .build();
        virtualResources.add(virtualResource);

        virtualResource = new VirtualResourceBuilder()
                .setVirtualResourceId(new VirtualResourceId(UUID.randomUUID().toString()))
                .setVirtualResourceType(VirtualResource.VirtualResourceType.Vlink)
                .setVirtualResourceEntityId(new VirtualResourceEntityId(virtualLink2.getLinkId().getValue()))
                .setOrder(0L)
                .build();
        virtualResources.add(virtualResource);

        IntentVnMappingResult intentVnMappingResult = new IntentVnMappingResultBuilder()
                .setIntentId(new IntentId(connection.getConnectionId().getValue()))
                .setIntentType(IntentVnMappingResult.IntentType.Connection)
                .setVirtualResource(virtualResources)
                .build();

        intentVnMappingResults.add(intentVnMappingResult);

        return null;
    }

    /**
     * TODO
     *
     * @param user TODO
     * @param connection TODO
     * @param endNode1 TODO
     * @param endNode2 TODO
     * @param virtualNetwork TODO
     * @param userIntentVnMapping TODO
     * @param tempConnection TODO
     * @param resolvingEndNode1 TODO
     * @param resolvingEndNode2 TODO
     * @return TODO
     */
    private List<VirtualLink> resolveP2PConnectionBetweenExternalLayer3Groups(User user,
                                                                              Connection connection,
                                                                              Node endNode1,
                                                                              Node endNode2,
                                                                              VirtualNetwork virtualNetwork,
                                                                              UserIntentVnMapping userIntentVnMapping,
                                                                              boolean tempConnection,
                                                                              boolean resolvingEndNode1,
                                                                              boolean resolvingEndNode2)
            throws IntentResolutionException {
        List<IntentVnMappingResult> intentVnMappingResults = userIntentVnMapping.getIntentVnMappingResult();
        IntentVnMappingResult intentVnMappingResult1 = IntentResolverUtils
                .getIntentVnMappingResult(intentVnMappingResults, new IntentId(endNode1.getNodeId().getValue()));

        if ( null == intentVnMappingResult1 ) {
            throw new IntentResolutionException("Can not get the intent-vn mapping result for " +
                    "the node " + endNode1.getNodeId().getValue() + ".");
        }

        IntentVnMappingResult intentVnMappingResult2 = IntentResolverUtils
                .getIntentVnMappingResult(intentVnMappingResults, new IntentId(endNode2.getNodeId().getValue()));

        if ( null == intentVnMappingResult2 ) {
            throw new IntentResolutionException("Can not get the intent-vn mapping result for " +
                    "the node " + endNode2.getNodeId().getValue() + ".");
        }

        List<VirtualNode> virtualNodes = virtualNetwork.getVirtualNodes().getVirtualNode();
        VirtualNodeId virtualNodeId = new VirtualNodeId(intentVnMappingResult1.getVirtualResource().get(0)
                .getParentVirtualResourceEntityId().getValue());
        VirtualNode virtualRouter1 = IntentResolverUtils.getVirtualNode(virtualNodes, virtualNodeId);

        if ( null == virtualRouter1 ) {
            throw new IntentResolutionException("Can not get the virtual node created for " +
                    "the node " + endNode1.getNodeId().getValue() + ".");
        }

        virtualNodeId = new VirtualNodeId(intentVnMappingResult2.getVirtualResource().get(0)
                .getParentVirtualResourceEntityId().getValue());
        VirtualNode virtualRouter2 = IntentResolverUtils.getVirtualNode(virtualNodes, virtualNodeId);

        if ( null == virtualRouter2 ) {
            throw new IntentResolutionException("Can not get the virtual node created for " +
                    "the node " + endNode2.getNodeId().getValue() + ".");
        }

        Property property = IntentResolverUtils
                .getConnectionProperty(connection.getProperty(), new PropertyName("bandwidth"));
        long bandwidth = 0;

        if ( null != property ) {
            bandwidth = /*1024 * */property.getPropertyValues().getIntValue().get(0).getValue();
        }

        VirtualPort virtualPort1 = new VirtualPortBuilder()
                .setPortId(new VirtualPortId(UUID.randomUUID().toString()))
                .setPortType(VirtualPort.PortType.Internal)
                .setBandwidth(bandwidth)
                .build();
        virtualRouter1.getVirtualPort().add(virtualPort1);

        VirtualPort virtualPort2 = new VirtualPortBuilder()
                .setPortId(new VirtualPortId(UUID.randomUUID().toString()))
                .setPortType(VirtualPort.PortType.Internal)
                .setBandwidth(bandwidth)
                .build();
        virtualRouter2.getVirtualPort().add(virtualPort2);

        VirtualLink virtualLink1 = new VirtualLinkBuilder()
                .setLinkId(new VirtualLinkId(UUID.randomUUID().toString()))
                .setSrcNodeId(virtualRouter1.getNodeId())
                .setSrcPortId(virtualPort1.getPortId())
                .setDestNodeId(virtualRouter2.getNodeId())
                .setDestPortId(virtualPort2.getPortId())
                .setBandwidth(bandwidth)
                .build();
        VirtualLink virtualLink2 = new VirtualLinkBuilder()
                .setLinkId(new VirtualLinkId(UUID.randomUUID().toString()))
                .setSrcNodeId(virtualRouter2.getNodeId())
                .setSrcPortId(virtualPort2.getPortId())
                .setDestNodeId(virtualRouter1.getNodeId())
                .setDestPortId(virtualPort1.getPortId())
                .setBandwidth(bandwidth)
                .build();

        if ( tempConnection ) {
            List<VirtualLink> virtualLinks = new ArrayList<VirtualLink>(2);

            virtualLinks.add(virtualLink1);
            virtualLinks.add(virtualLink2);

            return virtualLinks;
        }

        List<VirtualLink> virtualLinks = virtualNetwork.getVirtualLinks().getVirtualLink();

        virtualLinks.add(virtualLink1);
        virtualLinks.add(virtualLink2);

        List<VirtualResource> virtualResources = new ArrayList<VirtualResource>(2);

        VirtualResource virtualResource = new VirtualResourceBuilder()
                .setVirtualResourceId(new VirtualResourceId(UUID.randomUUID().toString()))
                .setVirtualResourceType(VirtualResource.VirtualResourceType.Vlink)
                .setVirtualResourceEntityId(new VirtualResourceEntityId(virtualLink1.getLinkId().getValue()))
                .setOrder(0L)
                .build();
        virtualResources.add(virtualResource);

        virtualResource = new VirtualResourceBuilder()
                .setVirtualResourceId(new VirtualResourceId(UUID.randomUUID().toString()))
                .setVirtualResourceType(VirtualResource.VirtualResourceType.Vlink)
                .setVirtualResourceEntityId(new VirtualResourceEntityId(virtualLink2.getLinkId().getValue()))
                .setOrder(0L)
                .build();
        virtualResources.add(virtualResource);

        IntentVnMappingResult intentVnMappingResult = new IntentVnMappingResultBuilder()
                .setIntentId(new IntentId(connection.getConnectionId().getValue()))
                .setIntentType(IntentVnMappingResult.IntentType.Connection)
                .setVirtualResource(virtualResources)
                .build();

        intentVnMappingResults.add(intentVnMappingResult);

        return null;
    }
}
