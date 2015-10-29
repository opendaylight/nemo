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
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.paths.VirtualPath;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.paths.VirtualPathBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.VirtualPort;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.VirtualPortBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.intent.vn.mapping.results.UserIntentVnMapping;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.intent.vn.mapping.results.user.intent.vn.mapping.IntentVnMappingResult;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.intent.vn.mapping.results.user.intent.vn.mapping.IntentVnMappingResultBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.intent.vn.mapping.results.user.intent.vn.mapping.intent.vn.mapping.result.VirtualResource;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.intent.vn.mapping.results.user.intent.vn.mapping.intent.vn.mapping.result.VirtualResourceBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.ActionName;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.IntentId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.NodeId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.NodeType;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Connection;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Flow;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Node;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.operations.Operation;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.User;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.StringValue;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.condition.instance.ConditionSegment;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.operation.instance.Action;
import org.opendaylight.yangtools.yang.binding.DataObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * Resolve the user's operation intent that might act on the node, connection
 * or flow. Perform the monitoring if the operation contains a condition which
 * determines when to execute the operation. Resolve the actions included in
 * the operation and decide how to implement them on the physical network.
 *
 * @author Zhigang Ji
 */
public class OperationResolver {
    private static final Logger LOG = LoggerFactory.getLogger(OperationResolver.class);

    private final DataBroker dataBroker;

    /**
     * The action resolver to resolve actions in the operation.
     */
    private ActionResolver actionResolver;

    public OperationResolver(DataBroker dataBroker) {
        super();

        this.dataBroker = dataBroker;

        actionResolver = new ActionResolver();

        LOG.debug("Initialized the renderer common operation resolver.");

        return;
    }

    /**
     * Resolve the user's operation intent and perform the monitoring for
     * the condition that it contains. Generate the intent mapping results
     * for the operation in the data store, which denote how to implement
     * it on the physical network.
     *
     * @param user The user for the operation.
     * @param operation The operation to be resolved.
     * @param virtualNetwork The virtual network for the user.
     * @param userIntentVnMapping The intent-vn mapping for the user.
     */
    protected void resolveOperation(User user, Operation operation, VirtualNetwork virtualNetwork,
                                    UserIntentVnMapping userIntentVnMapping)
            throws IntentResolutionException {
        DataObject dataObject = IntentResolverUtils
                .getObject(user.getObjects(), operation.getTargetObject());

        if ( null == dataObject ) {
            throw new IntentResolutionException("The target object of the operation " +
                    operation.getOperationId().getValue() + " does not exist.");
        }

        List<Operation> operations = user.getOperations().getOperation();
        List<Operation> sameTargetObjectOperations = IntentResolverUtils
                .getSameTargetObjectOperations(operations, operation);

        if ( !sameTargetObjectOperations.isEmpty() ) {
            List<Operation> greaterPriorityOperations = new LinkedList<Operation>();
            List<Operation> equalPriorityOperations = new LinkedList<Operation>();

            IntentResolverUtils.getGreaterAndEqualPriorityOperations(sameTargetObjectOperations,
                    operation, greaterPriorityOperations, equalPriorityOperations);

            if ( greaterPriorityOperations.isEmpty() ) {
                if ( !equalPriorityOperations.isEmpty() ) {
                    Operation conflictingOperation = IntentResolverUtils
                            .getConflictingOperation(equalPriorityOperations, operation);

                    if ( null != conflictingOperation ) {
                        throw new IntentResolutionException("The operation " +
                                operation.getOperationId().getValue() + " conflicts with the one " +
                                conflictingOperation.getOperationId().getValue() + ".");
                    }
                }
            } else {
                return;
            }
        }

        List<ConditionSegment> conditionSegments = operation.getConditionSegment();

        if ( null != conditionSegments && !conditionSegments.isEmpty() ) {
            // TODO

            return;
        }

        List<Action> actions = operation.getAction();

        if ( null != actions && !actions.isEmpty() ) {
            if ( dataObject instanceof Node ) {
                actionResolver.resolveActions(user, operation, (Node)dataObject,
                        virtualNetwork, userIntentVnMapping);
            } else if ( dataObject instanceof Connection ) {
                actionResolver.resolveActions(user, operation, (Connection)dataObject,
                        virtualNetwork, userIntentVnMapping);
            } else {
                actionResolver.resolveActions(user, operation, (Flow)dataObject,
                        virtualNetwork, userIntentVnMapping);
            }
        }

        return;
    }

    /**
     * Perform the monitoring for the operation that contains a condition
     * which determines when to execute the operation.
     *
     * @author Zhigang Ji
     */
    private class ConditionMonitor {
        // TODO
    }

    /**
     * Resolve the actions included in the operation and decide how to
     * implement them on the physical network.
     *
     * @author Zhigang Ji
     */
    private class ActionResolver {
        /**
         * TODO
         *
         * @param user TODO
         * @param operation TODO
         * @param node TODO
         * @param virtualNetwork TODO
         * @param userIntentVnMapping TODO
         */
        protected void resolveActions(User user, Operation operation, Node node,
                                      VirtualNetwork virtualNetwork,
                                      UserIntentVnMapping userIntentVnMapping)
                throws IntentResolutionException {
            // TODO

            return;
        }

        /**
         * TODO
         *
         * @param user TODO
         * @param operation TODO
         * @param connection TODO
         * @param virtualNetwork TODO
         * @param userIntentVnMapping TODO
         */
        protected void resolveActions(User user, Operation operation, Connection connection,
                                      VirtualNetwork virtualNetwork,
                                      UserIntentVnMapping userIntentVnMapping)
                throws IntentResolutionException {
            // TODO

            return;
        }

        /**
         * TODO
         *
         * @param user TODO
         * @param operation TODO
         * @param flow TODO
         * @param virtualNetwork TODO
         * @param userIntentVnMapping TODO
         */
        protected void resolveActions(User user, Operation operation, Flow flow,
                                      VirtualNetwork virtualNetwork,
                                      UserIntentVnMapping userIntentVnMapping)
                throws IntentResolutionException {
            List<Action> actions = operation.getAction();

            ActionName actionName = new ActionName("go-through");
            Action goThroughAction = IntentResolverUtils.getAction(actions, actionName);

            if ( null != goThroughAction ) {
                resolveGoThroughAction(user, operation, flow, goThroughAction,
                        virtualNetwork, userIntentVnMapping);

                return;
            }

            throw new IntentResolutionException("Unsupported action combination.");

//            return;
        }
    }

    /**
     * TODO
     *
     * @param user TODO
     * @param operation TODO
     * @param flow TODO
     * @param goThroughAction TODO
     * @param virtualNetwork TODO
     * @param userIntentVnMapping TODO
     */
    private void resolveGoThroughAction(User user, Operation operation, Flow flow,
                                        Action goThroughAction,
                                        VirtualNetwork virtualNetwork,
                                        UserIntentVnMapping userIntentVnMapping)
            throws IntentResolutionException {
        List<StringValue> parameterValues = goThroughAction.getParameterValues().getStringValue();

        if ( !parameterValues.isEmpty() ) {
            List<Node> nodes = user.getObjects().getNode();

            if ( null == nodes || nodes.isEmpty() ) {
                throw new IntentResolutionException("The nodes specified by the action parameters " +
                        "of the operation " + operation.getOperationId().getValue() + " does not exist.");
            }

            NodeId nodeId = new NodeId(parameterValues.get(0).getValue());
            Node node = IntentResolverUtils.getNode(nodes, nodeId);

            if ( null == node ) {
                throw new IntentResolutionException("The node " + nodeId.getValue() + " specified by the" +
                        " action parameter of the operation " + operation.getOperationId().getValue() +
                        " does not exist.");
            }

            List<VirtualNode> virtualNodes = virtualNetwork.getVirtualNodes().getVirtualNode();
            List<VirtualLink> virtualLinks = virtualNetwork.getVirtualLinks().getVirtualLink();
            List<VirtualPath> virtualPaths = virtualNetwork.getVirtualPaths().getVirtualPath();
            List<IntentVnMappingResult> intentVnMappingResults = userIntentVnMapping.getIntentVnMappingResult();
            List<VirtualResource> virtualResources = null;

            if ( node.getNodeType().equals(new NodeType("chain-group")) ) {
                if ( IntentResolverUtils.checkAllLayer2OperatingMode(node.getSubNode(), nodes) ) {
                    // TODO
                } else if ( IntentResolverUtils.checkAllLayer3OperatingMode(node.getSubNode(), nodes) ) {
                    IntentVnMappingResult intentVnMappingResult = IntentResolverUtils.getIntentVnMappingResult(
                            intentVnMappingResults, new IntentId(node.getNodeId().getValue()));

                    if ( null == intentVnMappingResult ) {
                        throw new IntentResolutionException("Can not get the intent-vn mapping result " +
                                "for the node " + node.getNodeId().getValue() + ".");
                    }

                    VirtualNode sourceVirtualNode = IntentResolverUtils.getSourceVirtualRouterOfFlow(
                            virtualNodes, flow, nodes, intentVnMappingResults);

                    if ( null == sourceVirtualNode ) {
                        throw new IntentResolutionException("Can not get the source virtual node " +
                                "of the flow " + flow.getFlowId().getValue() + ".");
                    }

                    VirtualNode destinationVirtualNode = IntentResolverUtils.getDestinationVirtualRouterOfFlow(
                            virtualNodes, flow, nodes, intentVnMappingResults);

                    if ( null == destinationVirtualNode ) {
                        throw new IntentResolutionException("Can not get the destination virtual node " +
                                "of the flow " + flow.getFlowId().getValue() + ".");
                    }

                    List<VirtualResource> virtualResources1 = IntentResolverUtils
                            .sortVirtualResources(intentVnMappingResult.getVirtualResource());

                    VirtualNodeId virtualNodeId = new VirtualNodeId(virtualResources1.get(0)
                            .getParentVirtualResourceEntityId().getValue());
                    VirtualNode virtualNode = IntentResolverUtils.getVirtualNode(virtualNodes, virtualNodeId);

                    if ( null == virtualNode ) {
                        throw new IntentResolutionException("Can not get the virtual node" +
                                " created for the first sub-node of the node " +
                                intentVnMappingResult.getIntentId().getValue() + ".");
                    }

                    virtualNodeId = new VirtualNodeId(virtualResources1.get(virtualResources1.size() - 1)
                            .getParentVirtualResourceEntityId().getValue());
                    VirtualNode virtualNode1 = IntentResolverUtils.getVirtualNode(virtualNodes, virtualNodeId);

                    if ( null == virtualNode1 ) {
                        throw new IntentResolutionException("Can not get the virtual node" +
                                " created for the last sub-node of the node " +
                                intentVnMappingResult.getIntentId().getValue() + ".");
                    }

                    virtualResources = new ArrayList<VirtualResource>(virtualResources1.size() + 2);
                    VirtualLink virtualLink = IntentResolverUtils.getVirtualLink(virtualLinks,
                            sourceVirtualNode.getNodeId(), virtualNode.getNodeId());

                    if ( null == virtualLink ) {
                        VirtualPort virtualPort = new VirtualPortBuilder()
                                .setPortId(new VirtualPortId(UUID.randomUUID().toString()))
                                .setPortType(VirtualPort.PortType.Internal)
                                .build();
                        sourceVirtualNode.getVirtualPort().add(virtualPort);

                        VirtualPort virtualPort1 = new VirtualPortBuilder()
                                .setPortId(new VirtualPortId(UUID.randomUUID().toString()))
                                .setPortType(VirtualPort.PortType.Internal)
                                .build();
                        virtualNode.getVirtualPort().add(virtualPort1);

                        virtualLink = new VirtualLinkBuilder()
                                .setLinkId(new VirtualLinkId(UUID.randomUUID().toString()))
                                .setSrcNodeId(virtualNode.getNodeId())
                                .setSrcPortId(virtualPort1.getPortId())
                                .setDestNodeId(sourceVirtualNode.getNodeId())
                                .setDestPortId(virtualPort.getPortId())
                                .setBandwidth(0L)
                                .build();
                        virtualLinks.add(virtualLink);

                        virtualLink = new VirtualLinkBuilder()
                                .setLinkId(new VirtualLinkId(UUID.randomUUID().toString()))
                                .setSrcNodeId(sourceVirtualNode.getNodeId())
                                .setSrcPortId(virtualPort.getPortId())
                                .setDestNodeId(virtualNode.getNodeId())
                                .setDestPortId(virtualPort1.getPortId())
                                .setBandwidth(0L)
                                .build();
                        virtualLinks.add(virtualLink);
                    }

                    List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.path.instance.VirtualLink> virtualLinks1 =
                            new ArrayList<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.path.instance.VirtualLink>(1);
                    org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.path.instance.VirtualLink virtualLink1 =
                            new org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.path.instance.VirtualLinkBuilder()
                                    .setLinkId(virtualLink.getLinkId())
                                    .setOrder(0L)
                                    .build();
                    virtualLinks1.add(virtualLink1);

                    VirtualPath virtualPath = new VirtualPathBuilder()
                            .setPathId(new VirtualPathId(UUID.randomUUID().toString()))
                            .setVirtualLink(virtualLinks1)
                            .setBandwidth(0L)
                            .build();
                    virtualPaths.add(virtualPath);

                    VirtualResource virtualResource = new VirtualResourceBuilder()
                            .setVirtualResourceId(new VirtualResourceId(UUID.randomUUID().toString()))
                            .setVirtualResourceType(VirtualResource.VirtualResourceType.Vpath)
                            .setVirtualResourceEntityId(new VirtualResourceEntityId(virtualPath.getPathId().getValue()))
                            .setOrder(0L)
                            .build();
                    virtualResources.add(virtualResource);

                    for ( VirtualResource virtualResource1 : virtualResources1 ) {
                        virtualResource = new VirtualResourceBuilder(virtualResource1)
                                .setVirtualResourceId(new VirtualResourceId(UUID.randomUUID().toString()))
                                .setOrder((long)virtualResources.size())
                                .build();
                        virtualResources.add(virtualResource);
                    }

                    virtualLink = IntentResolverUtils.getVirtualLink(virtualLinks,
                            virtualNode1.getNodeId(), destinationVirtualNode.getNodeId());

                    if ( null == virtualLink ) {
                        VirtualPort virtualPort = new VirtualPortBuilder()
                                .setPortId(new VirtualPortId(UUID.randomUUID().toString()))
                                .setPortType(VirtualPort.PortType.Internal)
                                .build();
                        virtualNode1.getVirtualPort().add(virtualPort);

                        VirtualPort virtualPort1 = new VirtualPortBuilder()
                                .setPortId(new VirtualPortId(UUID.randomUUID().toString()))
                                .setPortType(VirtualPort.PortType.Internal)
                                .build();
                        destinationVirtualNode.getVirtualPort().add(virtualPort1);

                        virtualLink = new VirtualLinkBuilder()
                                .setLinkId(new VirtualLinkId(UUID.randomUUID().toString()))
                                .setSrcNodeId(destinationVirtualNode.getNodeId())
                                .setSrcPortId(virtualPort1.getPortId())
                                .setDestNodeId(virtualNode1.getNodeId())
                                .setDestPortId(virtualPort.getPortId())
                                .setBandwidth(0L)
                                .build();
                        virtualLinks.add(virtualLink);

                        virtualLink = new VirtualLinkBuilder()
                                .setLinkId(new VirtualLinkId(UUID.randomUUID().toString()))
                                .setSrcNodeId(virtualNode1.getNodeId())
                                .setSrcPortId(virtualPort.getPortId())
                                .setDestNodeId(destinationVirtualNode.getNodeId())
                                .setDestPortId(virtualPort1.getPortId())
                                .setBandwidth(0L)
                                .build();
                        virtualLinks.add(virtualLink);
                    }

                    virtualLinks1 = new ArrayList<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.path.instance.VirtualLink>(1);
                    virtualLink1 = new org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.path.instance.VirtualLinkBuilder()
                                    .setLinkId(virtualLink.getLinkId())
                                    .setOrder(0L)
                                    .build();
                    virtualLinks1.add(virtualLink1);

                    virtualPath = new VirtualPathBuilder()
                            .setPathId(new VirtualPathId(UUID.randomUUID().toString()))
                            .setVirtualLink(virtualLinks1)
                            .setBandwidth(0L)
                            .build();
                    virtualPaths.add(virtualPath);

                    virtualResource = new VirtualResourceBuilder()
                            .setVirtualResourceId(new VirtualResourceId(UUID.randomUUID().toString()))
                            .setVirtualResourceType(VirtualResource.VirtualResourceType.Vpath)
                            .setVirtualResourceEntityId(new VirtualResourceEntityId(virtualPath.getPathId().getValue()))
                            .setOrder((long) virtualResources.size())
                            .build();
                    virtualResources.add(virtualResource);
                } else {
                    // TODO
                }
            } else {
                // TODO
            }

            IntentVnMappingResult intentVnMappingResult = new IntentVnMappingResultBuilder()
                    .setIntentId(new IntentId(operation.getOperationId().getValue()))
                    .setIntentType(IntentVnMappingResult.IntentType.Operation)
                    .setVirtualResource(virtualResources)
                    .build();

            intentVnMappingResults.add(intentVnMappingResult);
        }

        return;
    }
}
