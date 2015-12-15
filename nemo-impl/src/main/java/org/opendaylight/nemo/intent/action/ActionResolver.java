/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.nemo.intent.action;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.nemo.intent.IntentResolutionException;
import org.opendaylight.nemo.intent.IntentResolverUtils;
import org.opendaylight.nemo.intent.computation.VNComputationUnit;
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
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Connection;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Flow;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Node;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.operations.Operation;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.User;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.connection.instance.Property;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.IntValue;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.StringValue;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.operation.instance.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Resolve the actions included in the operation and decide how to
 * implement them on the physical network.
 *
 * @author Zhigang Ji
 */
public class ActionResolver {
    private static final Logger LOG = LoggerFactory.getLogger(ActionResolver.class);

    private final DataBroker dataBroker;

    /**
     * The virtual network computation unit for all users.
     */
    private Map<UserId, VNComputationUnit> vnComputationUnits;

    public ActionResolver(DataBroker dataBroker, Map<UserId, VNComputationUnit> vnComputationUnits) {
        super();

        this.dataBroker = dataBroker;
        this.vnComputationUnits = vnComputationUnits;

        LOG.debug("Initialized the renderer common action resolver.");

        return;
    }

    /**
     * TODO
     *
     * @param user TODO
     * @param operation TODO
     * @param node TODO
     * @param virtualNetwork TODO
     * @param userIntentVnMapping TODO
     * @throws IntentResolutionException
     */
    public void resolveActions(User user, Operation operation, Node node,
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
     * @throws IntentResolutionException
     */
    public void resolveActions(User user, Operation operation, Connection connection,
                               VirtualNetwork virtualNetwork,
                               UserIntentVnMapping userIntentVnMapping)
            throws IntentResolutionException {
        List<Action> actions = operation.getAction();

        ActionName actionName = new ActionName("qos-bandwidth");
        Action qosBandwidthAction = ActionResolverUtils.getAction(actions, actionName);

        if ( null != qosBandwidthAction ) {
            resolveQosBandwidthAction(user, operation, connection, qosBandwidthAction,
                    virtualNetwork, userIntentVnMapping);

            return;
        }

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
     * @throws IntentResolutionException
     */
    public void resolveActions(User user, Operation operation, Flow flow,
                               VirtualNetwork virtualNetwork,
                               UserIntentVnMapping userIntentVnMapping)
            throws IntentResolutionException {
        List<Action> actions = operation.getAction();

        ActionName actionName = new ActionName("deny");
        Action denyAction = ActionResolverUtils.getAction(actions, actionName);

        if ( null != denyAction ) {
            resolveDenyAction(user, operation, flow, denyAction,
                    virtualNetwork, userIntentVnMapping);

            return;
        }

        actionName = new ActionName("allow");
        Action allowAction = ActionResolverUtils.getAction(actions, actionName);

        if ( null != allowAction ) {
            resolveAllowAction(user, operation, flow, allowAction,
                    virtualNetwork, userIntentVnMapping);

            return;
        }

        actionName = new ActionName("go-through");
        Action goThroughAction = ActionResolverUtils.getAction(actions, actionName);

        if ( null != goThroughAction ) {
            resolveGoThroughAction(user, operation, flow, goThroughAction,
                    virtualNetwork, userIntentVnMapping);

            return;
        }

        throw new IntentResolutionException("Unsupported action combination.");

//        return;
    }

    /**
     * TODO
     *
     * @param user TODO
     * @param operation TODO
     * @param connection TODO
     * @param qosBandwidthAction TODO
     * @param virtualNetwork TODO
     * @param userIntentVnMapping TODO
     * @throws IntentResolutionException
     */
    private void resolveQosBandwidthAction(User user, Operation operation, Connection connection,
                                           Action qosBandwidthAction,
                                           VirtualNetwork virtualNetwork,
                                           UserIntentVnMapping userIntentVnMapping)
            throws IntentResolutionException {
        List<IntValue> parameterValues = qosBandwidthAction.getParameterValues().getIntValue();

        if ( parameterValues.isEmpty() ) {
            return;
        }

        IntValue parameterValue = parameterValues.get(0);
        long requiredBandwidth = parameterValue.getValue();

        PropertyName propertyName = new PropertyName("bandwidth");
        Property property = IntentResolverUtils
                .getConnectionProperty(connection.getProperty(), propertyName);
        long currentBandwidth;

        if ( null == property ) {
            currentBandwidth = 0;
        } else {
            currentBandwidth = property.getPropertyValues().getIntValue().get(0).getValue();
        }

        if ( currentBandwidth == requiredBandwidth ) {
            return;
        }

        List<IntentVnMappingResult> intentVnMappingResults = userIntentVnMapping.getIntentVnMappingResult();
        IntentVnMappingResult intentVnMappingResult = IntentResolverUtils
                .getIntentVnMappingResult(intentVnMappingResults, new IntentId(connection.getConnectionId()));

        if ( null == intentVnMappingResult ) {
            throw new IntentResolutionException("Can not get the intent-vn mapping result for " +
                    "the connection " + connection.getConnectionId().getValue() + ".");
        }

        List<VirtualResource> virtualResources = intentVnMappingResult.getVirtualResource();
        List<VirtualLink> virtualLinks = virtualNetwork.getVirtualLinks().getVirtualLink();
        VirtualLinkId virtualLinkId;

        for ( VirtualResource virtualResource : virtualResources ) {
            virtualLinkId = new VirtualLinkId(virtualResource.getVirtualResourceEntityId().getValue());

            ActionResolverUtils.modifyVirtualLinkBandwidth(virtualLinks, virtualLinkId, requiredBandwidth);
        }

        return;
    }

    /**
     * TODO
     *
     * @param user TODO
     * @param operation TODO
     * @param flow TODO
     * @param denyAction TODO
     * @param virtualNetwork TODO
     * @param userIntentVnMapping TODO
     * @throws IntentResolutionException
     */
    private void resolveDenyAction(User user, Operation operation, Flow flow,
                                   Action denyAction,
                                   VirtualNetwork virtualNetwork,
                                   UserIntentVnMapping userIntentVnMapping)
            throws IntentResolutionException {
        List<Node> nodes = user.getObjects().getNode();
        List<VirtualNode> virtualNodes = virtualNetwork.getVirtualNodes().getVirtualNode();
        List<IntentVnMappingResult> intentVnMappingResults = userIntentVnMapping.getIntentVnMappingResult();

        VirtualNode sourceVirtualNode = ActionResolverUtils
                .getSourceVirtualRouterOfFlow(virtualNodes, flow, nodes, intentVnMappingResults);

        if ( null == sourceVirtualNode ) {
            throw new IntentResolutionException("Can not get the source virtual node " +
                    "of the flow " + flow.getFlowId().getValue() + ".");
        }

        VirtualNode destinationVirtualNode = ActionResolverUtils
                .getDestinationVirtualRouterOfFlow(virtualNodes, flow, nodes, intentVnMappingResults);

        if ( null == destinationVirtualNode ) {
            throw new IntentResolutionException("Can not get the destination virtual node " +
                    "of the flow " + flow.getFlowId().getValue() + ".");
        }

        VNComputationUnit vnComputationUnit = vnComputationUnits.get(user.getUserId());

        if ( null == vnComputationUnit ) {
            throw new IntentResolutionException("Can not get the virtual network computation " +
                    "unit for the user " + user.getUserId().getValue() + ".");
        }

        VirtualPath virtualPath = vnComputationUnit
                .computePath(sourceVirtualNode.getNodeId(), destinationVirtualNode.getNodeId());

        if ( null == virtualPath || virtualPath.getVirtualLink().isEmpty() ) {
            throw new IntentResolutionException("Can not compute an available virtual path in " +
                    "the virtual network for the flow " + flow.getFlowId().getValue() + ".");
        }

        virtualNetwork.getVirtualPaths().getVirtualPath().add(virtualPath);

        List<VirtualResource> virtualResources = new ArrayList<VirtualResource>(1);

        VirtualResource virtualResource = new VirtualResourceBuilder()
                .setVirtualResourceId(new VirtualResourceId(UUID.randomUUID().toString()))
                .setVirtualResourceType(VirtualResource.VirtualResourceType.Vpath)
                .setVirtualResourceEntityId(new VirtualResourceEntityId(virtualPath.getPathId().getValue()))
                .setOrder(0L)
                .build();
        virtualResources.add(virtualResource);

        IntentVnMappingResult intentVnMappingResult = new IntentVnMappingResultBuilder()
                .setIntentId(new IntentId(operation.getOperationId().getValue()))
                .setIntentType(IntentVnMappingResult.IntentType.Operation)
                .setVirtualResource(virtualResources)
                .build();
        intentVnMappingResults.add(intentVnMappingResult);

        return;
    }

    /**
     * TODO
     *
     * @param user TODO
     * @param operation TODO
     * @param flow TODO
     * @param allowAction TODO
     * @param virtualNetwork TODO
     * @param userIntentVnMapping TODO
     * @throws IntentResolutionException
     */
    private void resolveAllowAction(User user, Operation operation, Flow flow,
                                    Action allowAction,
                                    VirtualNetwork virtualNetwork,
                                    UserIntentVnMapping userIntentVnMapping)
            throws IntentResolutionException {
        List<Node> nodes = user.getObjects().getNode();
        List<VirtualNode> virtualNodes = virtualNetwork.getVirtualNodes().getVirtualNode();
        List<IntentVnMappingResult> intentVnMappingResults = userIntentVnMapping.getIntentVnMappingResult();

        VirtualNode sourceVirtualNode = ActionResolverUtils
                .getSourceVirtualRouterOfFlow(virtualNodes, flow, nodes, intentVnMappingResults);

        if ( null == sourceVirtualNode ) {
            throw new IntentResolutionException("Can not get the source virtual node " +
                    "of the flow " + flow.getFlowId().getValue() + ".");
        }

        VirtualNode destinationVirtualNode = ActionResolverUtils
                .getDestinationVirtualRouterOfFlow(virtualNodes, flow, nodes, intentVnMappingResults);

        if ( null == destinationVirtualNode ) {
            throw new IntentResolutionException("Can not get the destination virtual node " +
                    "of the flow " + flow.getFlowId().getValue() + ".");
        }

        VNComputationUnit vnComputationUnit = vnComputationUnits.get(user.getUserId());

        if ( null == vnComputationUnit ) {
            throw new IntentResolutionException("Can not get the virtual network computation " +
                    "unit for the user " + user.getUserId().getValue() + ".");
        }

        VirtualPath virtualPath = vnComputationUnit
                .computePath(sourceVirtualNode.getNodeId(), destinationVirtualNode.getNodeId());

        if ( null == virtualPath || virtualPath.getVirtualLink().isEmpty() ) {
            throw new IntentResolutionException("Can not compute an available virtual path in " +
                    "the virtual network for the flow " + flow.getFlowId().getValue() + ".");
        }

        virtualNetwork.getVirtualPaths().getVirtualPath().add(virtualPath);

        List<VirtualResource> virtualResources = new ArrayList<VirtualResource>(1);

        VirtualResource virtualResource = new VirtualResourceBuilder()
                .setVirtualResourceId(new VirtualResourceId(UUID.randomUUID().toString()))
                .setVirtualResourceType(VirtualResource.VirtualResourceType.Vpath)
                .setVirtualResourceEntityId(new VirtualResourceEntityId(virtualPath.getPathId().getValue()))
                .setOrder(0L)
                .build();
        virtualResources.add(virtualResource);

        IntentVnMappingResult intentVnMappingResult = new IntentVnMappingResultBuilder()
                .setIntentId(new IntentId(operation.getOperationId().getValue()))
                .setIntentType(IntentVnMappingResult.IntentType.Operation)
                .setVirtualResource(virtualResources)
                .build();
        intentVnMappingResults.add(intentVnMappingResult);

        return;
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
     * @throws IntentResolutionException
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

            if ( node.getNodeType().equals(new NodeType("chain-group")) ) {
                resolveGoThroughChainGroupAction(user, operation, flow, goThroughAction, node,
                        virtualNetwork, userIntentVnMapping);
            } else {
                resolveGoThroughNormalGroupAction(user, operation, flow, goThroughAction,
                        virtualNetwork, userIntentVnMapping);
            }
        }

        return;
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
     * @throws IntentResolutionException
     */
    private void resolveGoThroughNormalGroupAction(User user, Operation operation, Flow flow,
                                                   Action goThroughAction,
                                                   VirtualNetwork virtualNetwork,
                                                   UserIntentVnMapping userIntentVnMapping)
            throws IntentResolutionException {
        List<Node> nodes = user.getObjects().getNode();
        List<StringValue> parameterValues = goThroughAction.getParameterValues().getStringValue();
        NodeId nodeId;
        Node node;

        for ( StringValue parameterValue : parameterValues ) {
            nodeId = new NodeId(parameterValue.getValue());
            node = IntentResolverUtils.getNode(nodes, nodeId);

            if ( null == node ) {
                throw new IntentResolutionException("The node " + nodeId.getValue() + " specified by the" +
                        " action parameter of the operation " + operation.getOperationId().getValue() +
                        " does not exist.");
            }
        }

        List<VirtualNode> virtualNodes = virtualNetwork.getVirtualNodes().getVirtualNode();
        List<IntentVnMappingResult> intentVnMappingResults = userIntentVnMapping.getIntentVnMappingResult();
        parameterValues = ActionResolverUtils.sortParameterValues(parameterValues);

        List<VirtualNode> virtualNodes1 = new ArrayList<VirtualNode>(parameterValues.size());
        IntentVnMappingResult intentVnMappingResult;
        List<VirtualResource> virtualResources;
        VirtualResource virtualResource;
        VirtualNodeId virtualNodeId;
        VirtualNode virtualNode;

        for ( StringValue parameterValue : parameterValues ) {
            intentVnMappingResult = IntentResolverUtils.getIntentVnMappingResult(
                    intentVnMappingResults, new IntentId(parameterValue.getValue()));

            if ( null == intentVnMappingResult ) {
                throw new IntentResolutionException("Can not get the intent-vn mapping result " +
                        "for the node " + parameterValue.getValue() + ".");
            }

            virtualResources = intentVnMappingResult.getVirtualResource();

            if ( 1 != virtualResources.size() ) {
                throw new IntentResolutionException("The type of the node " + parameterValue.getValue() +
                        " specified by the action parameter of the operation " +
                        operation.getOperationId().getValue() + " is not required," +
                        " according to the intent-vn mapping result.");
            }

            virtualResource = virtualResources.get(0);

            if ( VirtualResource.VirtualResourceType.Vport
                    == virtualResource.getVirtualResourceType() ) {
                virtualNodeId = new VirtualNodeId(virtualResource.getParentVirtualResourceEntityId().getValue());
            } else if ( VirtualResource.VirtualResourceType.Vnode
                    == virtualResource.getVirtualResourceType() ) {
                virtualNodeId = new VirtualNodeId(virtualResource.getVirtualResourceEntityId().getValue());
            } else {
                throw new IntentResolutionException("The type of the node " + parameterValue.getValue() +
                        " specified by the action parameter of the operation " +
                        operation.getOperationId().getValue() + " is not required," +
                        " according to the intent-vn mapping result.");
            }

            virtualNode = IntentResolverUtils.getVirtualNode(virtualNodes, virtualNodeId);

            if ( null == virtualNode ) {
                throw new IntentResolutionException("Can not get the virtual node created for" +
                        " the node " + parameterValue.getValue() + ".");
            }

            if ( VirtualNode.NodeType.Vrouter != virtualNode.getNodeType() ) {
                throw new IntentResolutionException("The type of the node " + parameterValue.getValue() +
                        " specified by the action parameter of the operation " +
                        operation.getOperationId().getValue() + " is not required," +
                        " according to the virtual node created for it.");
            }

            virtualNodes1.add(virtualNode);
        }

        VirtualNode sourceVirtualNode = ActionResolverUtils
                .getSourceVirtualRouterOfFlow(virtualNodes, flow, nodes, intentVnMappingResults);

        if ( null == sourceVirtualNode ) {
            throw new IntentResolutionException("Can not get the source virtual node " +
                    "of the flow " + flow.getFlowId().getValue() + ".");
        }

        VirtualNode destinationVirtualNode = ActionResolverUtils
                .getDestinationVirtualRouterOfFlow(virtualNodes, flow, nodes, intentVnMappingResults);

        if ( null == destinationVirtualNode ) {
            throw new IntentResolutionException("Can not get the destination virtual node " +
                    "of the flow " + flow.getFlowId().getValue() + ".");
        }

        VNComputationUnit vnComputationUnit = vnComputationUnits.get(user.getUserId());

        if ( null == vnComputationUnit ) {
            throw new IntentResolutionException("Can not get the virtual network computation " +
                    "unit for the user " + user.getUserId().getValue() + ".");
        }

        List<VirtualPath> virtualPaths = new ArrayList<VirtualPath>(virtualNodes1.size() + 1);
        Iterator<VirtualNode> iterator = virtualNodes1.iterator();
        VirtualNode firstVirtualNode = iterator.next();
        VirtualNode secondVirtualNode;
        VirtualPath virtualPath;

        if ( !sourceVirtualNode.getNodeId().equals(firstVirtualNode.getNodeId()) ) {
            virtualPath = vnComputationUnit
                    .computePath(sourceVirtualNode.getNodeId(), firstVirtualNode.getNodeId());

            if ( null == virtualPath || virtualPath.getVirtualLink().isEmpty() ) {
                throw new IntentResolutionException("Can not compute an available virtual path" +
                        " from the virtual node " + sourceVirtualNode.getNodeId().getValue() +
                        " to " + firstVirtualNode.getNodeId().getValue() + " in the virtual" +
                        " network for the flow " + flow.getFlowId().getValue() + ".");
            }

            virtualPaths.add(virtualPath);
        }

        while ( iterator.hasNext() ) {
            secondVirtualNode = iterator.next();

            if ( !firstVirtualNode.getNodeId().equals(secondVirtualNode.getNodeId()) ) {
                virtualPath = vnComputationUnit
                        .computePath(firstVirtualNode.getNodeId(), secondVirtualNode.getNodeId());

                if ( null == virtualPath || virtualPath.getVirtualLink().isEmpty() ) {
                    throw new IntentResolutionException("Can not compute an available virtual path" +
                            " from the virtual node " + firstVirtualNode.getNodeId().getValue() +
                            " to " + secondVirtualNode.getNodeId().getValue() + " in the virtual" +
                            " network for the flow " + flow.getFlowId().getValue() + ".");
                }

                virtualPaths.add(virtualPath);
            }

            firstVirtualNode = secondVirtualNode;
        }

        if ( !destinationVirtualNode.getNodeId().equals(firstVirtualNode.getNodeId()) ) {
            virtualPath = vnComputationUnit
                    .computePath(firstVirtualNode.getNodeId(), destinationVirtualNode.getNodeId());

            if ( null == virtualPath || virtualPath.getVirtualLink().isEmpty() ) {
                throw new IntentResolutionException("Can not compute an available virtual path" +
                        " from the virtual node " + firstVirtualNode.getNodeId().getValue() +
                        " to " + destinationVirtualNode.getNodeId().getValue() + " in the virtual" +
                        " network for the flow " + flow.getFlowId().getValue() + ".");
            }

            virtualPaths.add(virtualPath);
        }

        virtualPath = ActionResolverUtils.mergeVirtualPaths(virtualPaths);
        virtualNetwork.getVirtualPaths().getVirtualPath().add(virtualPath);

        virtualResources = new ArrayList<VirtualResource>(1);

        virtualResource = new VirtualResourceBuilder()
                .setVirtualResourceId(new VirtualResourceId(UUID.randomUUID().toString()))
                .setVirtualResourceType(VirtualResource.VirtualResourceType.Vpath)
                .setVirtualResourceEntityId(new VirtualResourceEntityId(virtualPath.getPathId().getValue()))
                .setOrder(0L)
                .build();
        virtualResources.add(virtualResource);

        intentVnMappingResult = new IntentVnMappingResultBuilder()
                .setIntentId(new IntentId(operation.getOperationId().getValue()))
                .setIntentType(IntentVnMappingResult.IntentType.Operation)
                .setVirtualResource(virtualResources)
                .build();
        intentVnMappingResults.add(intentVnMappingResult);

        return;
    }

    /**
     * TODO
     *
     * @param user TODO
     * @param operation TODO
     * @param flow TODO
     * @param goThroughAction TODO
     * @param chainGroup TODO
     * @param virtualNetwork TODO
     * @param userIntentVnMapping TODO
     * @throws IntentResolutionException
     */
    private void resolveGoThroughChainGroupAction(User user, Operation operation, Flow flow,
                                                  Action goThroughAction, Node chainGroup,
                                                  VirtualNetwork virtualNetwork,
                                                  UserIntentVnMapping userIntentVnMapping)
            throws IntentResolutionException {
        List<Node> nodes = user.getObjects().getNode();
        List<VirtualNode> virtualNodes = virtualNetwork.getVirtualNodes().getVirtualNode();
        List<VirtualLink> virtualLinks = virtualNetwork.getVirtualLinks().getVirtualLink();
        List<VirtualPath> virtualPaths = virtualNetwork.getVirtualPaths().getVirtualPath();
        List<IntentVnMappingResult> intentVnMappingResults = userIntentVnMapping.getIntentVnMappingResult();
        List<VirtualResource> virtualResources = null;

        if ( IntentResolverUtils.checkAllLayer2OperatingMode(chainGroup.getSubNode(), nodes) ) {
            // TODO
        } else if ( IntentResolverUtils.checkAllLayer3OperatingMode(chainGroup.getSubNode(), nodes) ) {
            IntentVnMappingResult intentVnMappingResult = IntentResolverUtils.getIntentVnMappingResult(
                    intentVnMappingResults, new IntentId(chainGroup.getNodeId().getValue()));

            if ( null == intentVnMappingResult ) {
                throw new IntentResolutionException("Can not get the intent-vn mapping result " +
                        "for the node " + chainGroup.getNodeId().getValue() + ".");
            }

            VirtualNode sourceVirtualNode = ActionResolverUtils.getSourceVirtualRouterOfFlow(
                    virtualNodes, flow, nodes, intentVnMappingResults);

            if ( null == sourceVirtualNode ) {
                throw new IntentResolutionException("Can not get the source virtual node " +
                        "of the flow " + flow.getFlowId().getValue() + ".");
            }

            VirtualNode destinationVirtualNode = ActionResolverUtils.getDestinationVirtualRouterOfFlow(
                    virtualNodes, flow, nodes, intentVnMappingResults);

            if ( null == destinationVirtualNode ) {
                throw new IntentResolutionException("Can not get the destination virtual node " +
                        "of the flow " + flow.getFlowId().getValue() + ".");
            }

            List<VirtualResource> virtualResources1 = ActionResolverUtils
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

        IntentVnMappingResult intentVnMappingResult = new IntentVnMappingResultBuilder()
                .setIntentId(new IntentId(operation.getOperationId().getValue()))
                .setIntentType(IntentVnMappingResult.IntentType.Operation)
                .setVirtualResource(virtualResources)
                .build();

        intentVnMappingResults.add(intentVnMappingResult);

        return;
    }
}
