/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.nemo.intent;

import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.hosts.PhysicalHost;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.links.VirtualLink;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.nodes.VirtualNode;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.intent.vn.mapping.results.user.intent.vn.mapping.IntentVnMappingResult;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.intent.vn.mapping.results.user.intent.vn.mapping.intent.vn.mapping.result.VirtualResource;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.PhysicalHostId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.PhysicalNodeId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.VirtualNodeId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.Objects;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Connection;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Flow;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Node;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.operations.Operation;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.flow.instance.MatchItem;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.node.instance.Property;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.node.instance.SubNode;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.property.values.StringValue;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.operation.instance.Action;
import org.opendaylight.yangtools.yang.binding.DataObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Implement the common utilities frequently used in
 * the intent resolution.
 *
 * @author Zhigang Ji
 */
public class IntentResolverUtils {
    /**
     * Check whether the node is an external layer2 group or layer3 group.
     *
     * @param node The node to be checked.
     * @return True if the node is an external layer3 group.
     */
    protected static boolean checkExternalLayer3Group(Node node) {
        PropertyName propertyName = new PropertyName("ac-info-network");
        Property property = getNodeProperty(node.getProperty(), propertyName);

        if ( null != property ) {
            String propertyValue = property.getPropertyValues().getStringValue().get(0).getValue();

            if ( propertyValue.equals("layer3") ) {
                return true;
            }
        }

        return false;
    }

    /**
     * TODO
     *
     * @param physicalHosts TODO
     * @param node TODO
     * @return TODO
     */
    protected static PhysicalHost getPhysicalHost(List<PhysicalHost> physicalHosts, Node node) {
        PhysicalHostId physicalHostId = new PhysicalHostId(node.getNodeId().getValue());

        return getPhysicalHost(physicalHosts, physicalHostId);
    }

    /**
     * TODO
     *
     * @param properties TODO
     * @param propertyName TODO
     * @return TODO
     */
    protected static Property getNodeProperty(List<Property> properties, PropertyName propertyName) {
        if ( null != properties ) {
            for ( Property property : properties ) {
                if ( property.getPropertyName().equals(propertyName) ) {
                    return property;
                }
            }
        }

        return null;
    }

    /**
     * TODO
     *
     * @param property TODO
     * @return TODO
     */
    protected static PhysicalNodeId generatePhysicalNodeIdFromNodeLocationProperty(Property property) {
        String propertyValue = property.getPropertyValues().getStringValue().get(0).getValue();

        return new PhysicalNodeId(propertyValue.substring(0, propertyValue.lastIndexOf(':')));
    }

    /**
     * TODO
     *
     * @param intentVnMappingResults TODO
     * @param intentId TODO
     * @return TODO
     */
    protected static IntentVnMappingResult getIntentVnMappingResult(
            List<IntentVnMappingResult> intentVnMappingResults, IntentId intentId) {
        for ( IntentVnMappingResult intentVnMappingResult : intentVnMappingResults ) {
            if ( intentVnMappingResult.getIntentId().equals(intentId) ) {
                return intentVnMappingResult;
            }
        }

        return null;
    }

    /**
     * TODO
     *
     * @param virtualNodes TODO
     * @param virtualNodeId TODO
     * @return TODO
     */
    protected static VirtualNode getVirtualNode(List<VirtualNode> virtualNodes,
                                                VirtualNodeId virtualNodeId) {
        for ( VirtualNode virtualNode : virtualNodes ) {
            if ( virtualNode.getNodeId().equals(virtualNodeId) ) {
                return virtualNode;
            }
        }

        return null;
    }

    /**
     * TODO
     *
     * @param nodes TODO
     * @param nodeId TODO
     * @return TODO
     */
    protected static Node getNode(List<Node> nodes, NodeId nodeId) {
        for ( Node node : nodes ) {
            if ( node.getNodeId().equals(nodeId) ) {
                return node;
            }
        }

        return null;
    }

    /**
     * TODO
     *
     * @param properties TODO
     * @param propertyName TODO
     * @return TODO
     */
    protected static org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.connection.instance.Property getConnectionProperty(
            List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.connection.instance.Property> properties,
            PropertyName propertyName) {
        if ( null != properties ) {
            for ( org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.connection.instance.Property
                    property : properties ) {
                if ( property.getPropertyName().equals(propertyName) ) {
                    return property;
                }
            }
        }

        return null;
    }

    /**
     * TODO
     *
     * @param subNodes TODO
     * @return TODO
     */
    protected static List<SubNode> sortSubNodes(List<SubNode> subNodes) {
        if ( subNodes.isEmpty() || 1 == subNodes.size() ) {
            return subNodes;
        }

        List<SubNode> sortedSubNodes = new ArrayList<SubNode>(subNodes.size());
        sortedSubNodes.addAll(subNodes);

        for ( SubNode subNode : subNodes ) {
            sortedSubNodes.set(subNode.getOrder().intValue(), subNode);
        }

        return sortedSubNodes;
    }

    /**
     * TODO
     *
     * @param subNodes TODO
     * @param nodes TODO
     * @return TODO
     */
    protected static boolean checkAllLayer2OperatingMode(List<SubNode> subNodes, List<Node> nodes) {
        if ( subNodes.isEmpty() ) {
            return false;
        }

        Node node;
        PropertyName propertyName = new PropertyName("operating-mode");
        Property property;
        String propertyValue;

        for ( SubNode subNode : subNodes ) {
            node = getNode(nodes, subNode.getNodeId());

            if ( null == node ) {
                return false;
            }

            property = getNodeProperty(node.getProperty(), propertyName);

            if ( null == property ) {
                return false;
            }

            propertyValue = property.getPropertyValues().getStringValue().get(0).getValue();

            if ( !propertyValue.equals("layer2") ) {
                return false;
            }
        }

        return true;
    }

    /**
     * TODO
     *
     * @param subNodes TODO
     * @param nodes TODO
     * @return TODO
     */
    protected static boolean checkAllLayer3OperatingMode(List<SubNode> subNodes, List<Node> nodes) {
        if ( subNodes.isEmpty() ) {
            return false;
        }

        Node node;
        PropertyName propertyName = new PropertyName("operating-mode");
        Property property;
        String propertyValue;

        for ( SubNode subNode : subNodes ) {
            node = getNode(nodes, subNode.getNodeId());

            if ( null == node ) {
                return false;
            }

            property = getNodeProperty(node.getProperty(), propertyName);

            if ( null == property ) {
                return false;
            }

            propertyValue = property.getPropertyValues().getStringValue().get(0).getValue();

            if ( !propertyValue.equals("layer3") ) {
                return false;
            }
        }

        return true;
    }

    /**
     * TODO
     *
     * @param virtualLinks TODO
     * @param srcVirtualNodeId TODO
     * @param destVirtualNodeId TODO
     * @return TODO
     */
    protected static VirtualLink getVirtualLink(List<VirtualLink> virtualLinks,
                                                VirtualNodeId srcVirtualNodeId,
                                                VirtualNodeId destVirtualNodeId) {
        for ( VirtualLink virtualLink : virtualLinks ) {
            if ( virtualLink.getSrcNodeId().equals(srcVirtualNodeId)
                    && virtualLink.getDestNodeId().equals(destVirtualNodeId) ) {
                return virtualLink;
            }
        }

        return null;
    }

    /**
     * TODO
     *
     * @param objects TODO
     * @param objectId TODO
     * @return TODO
     */
    protected static DataObject getObject(Objects objects, ObjectId objectId) {
        List<Node> nodes = objects.getNode();

        if ( null != nodes ) {
            NodeId nodeId = new NodeId(objectId.getValue());
            Node node = getNode(nodes, nodeId);

            if ( null != node ) {
                return node;
            }
        }

        List<Connection> connections = objects.getConnection();

        if ( null != connections ) {
            ConnectionId connectionId = new ConnectionId(objectId.getValue());
            Connection connection = getConnection(connections, connectionId);

            if ( null != connection ) {
                return connection;
            }
        }

        List<Flow> flows = objects.getFlow();

        if ( null != flows ) {
            FlowId flowId = new FlowId(objectId.getValue());
            Flow flow = getFlow(flows, flowId);

            if ( null != flow ) {
                return flow;
            }
        }

        return null;
    }

    /**
     * TODO
     *
     * @param operations TODO
     * @param operation TODO
     * @return TODO
     */
    protected static List<Operation> getSameTargetObjectOperations(List<Operation> operations,
                                                                   Operation operation) {
        // TODO

        return new ArrayList<Operation>(0);
    }

    /**
     * TODO
     *
     * @param operations TODO
     * @param operation TODO
     * @param greaterPriorityOperations TODO
     * @param equalPriorityOperations TODO
     */
    protected static void getGreaterAndEqualPriorityOperations(List<Operation> operations, Operation operation,
                                                               List<Operation> greaterPriorityOperations,
                                                               List<Operation> equalPriorityOperations) {
        // TODO

        return;
    }

    /**
     * TODO
     *
     * @param operations TODO
     * @param operation TODO
     * @return TODO
     */
    protected static Operation getConflictingOperation(List<Operation> operations,
                                                       Operation operation) {
        // TODO

        return null;
    }

    /**
     * TODO
     *
     * @param operations TODO
     * @param operation TODO
     * @return TODO
     */
    protected static List<Operation> getConflictingOperations(List<Operation> operations,
                                                              Operation operation) {
        // TODO

        return null;
    }

    /**
     * TODO
     *
     * @param actions TODO
     * @param actionName TODO
     * @return TODO
     */
    protected static Action getAction(List<Action> actions, ActionName actionName) {
        for ( Action action : actions ) {
            if ( action.getActionName().equals(actionName) ) {
                return action;
            }
        }

        return null;
    }

    /**
     * TODO
     *
     * @param virtualNodes TODO
     * @param flow TODO
     * @param nodes TODO
     * @param intentVnMappingResults TODO
     * @return TODO
     */
    protected static VirtualNode getSourceVirtualRouterOfFlow(List<VirtualNode> virtualNodes,
                                                              Flow flow, List<Node> nodes,
                                                              List<IntentVnMappingResult> intentVnMappingResults) {
        MatchItemName matchItemName = new MatchItemName("src-ip");
        MatchItem matchItem = getMatchItem(flow.getMatchItem(), matchItemName);

        if ( null == matchItem ) {
            return null;
        }

        String matchItemValue = matchItem.getMatchItemValue().getStringValue();
        VirtualNode virtualNode = getVirtualRouterWithIpPrefix(virtualNodes,
                matchItemValue, nodes, intentVnMappingResults);

        return virtualNode;
    }

    /**
     * TODO
     *
     * @param virtualNodes TODO
     * @param flow TODO
     * @param nodes TODO
     * @param intentVnMappingResults TODO
     * @return TODO
     */
    protected static VirtualNode getDestinationVirtualRouterOfFlow(List<VirtualNode> virtualNodes,
                                                                   Flow flow, List<Node> nodes,
                                                                   List<IntentVnMappingResult> intentVnMappingResults) {
        MatchItemName matchItemName = new MatchItemName("dst-ip");
        MatchItem matchItem = getMatchItem(flow.getMatchItem(), matchItemName);

        if ( null == matchItem ) {
            return null;
        }

        String matchItemValue = matchItem.getMatchItemValue().getStringValue();
        VirtualNode virtualNode = getVirtualRouterWithIpPrefix(virtualNodes,
                matchItemValue, nodes, intentVnMappingResults);

        return virtualNode;
    }

    /**
     * TODO
     *
     * @param virtualResources TODO
     * @return TODO
     */
    protected static List<VirtualResource> sortVirtualResources(List<VirtualResource> virtualResources) {
        if ( virtualResources.isEmpty() || 1 == virtualResources.size() ) {
            return virtualResources;
        }

        List<VirtualResource> sortedVirtualResources = new ArrayList<VirtualResource>(virtualResources.size());
        sortedVirtualResources.addAll(virtualResources);

        for ( VirtualResource virtualResource : virtualResources ) {
            sortedVirtualResources.set(virtualResource.getOrder().intValue(), virtualResource);
        }

        return sortedVirtualResources;
    }

    /**
     * TODO
     *
     * @param physicalHosts TODO
     * @param physicalHostId TODO
     * @return TODO
     */
    private static PhysicalHost getPhysicalHost(List<PhysicalHost> physicalHosts,
                                                PhysicalHostId physicalHostId) {
        for ( PhysicalHost physicalHost : physicalHosts ) {
            if ( physicalHost.getHostId().equals(physicalHostId) ) {
                return physicalHost;
            }
        }

        return null;
    }

    /**
     * TODO
     *
     * @param connections TODO
     * @param connectionId TODO
     * @return TODO
     */
    private static Connection getConnection(List<Connection> connections, ConnectionId connectionId) {
        for ( Connection connection : connections ) {
            if ( connection.getConnectionId().equals(connectionId) ) {
                return connection;
            }
        }

        return null;
    }

    /**
     * TODO
     *
     * @param flows TODO
     * @param flowId TODO
     * @return TODO
     */
    private static Flow getFlow(List<Flow> flows, FlowId flowId) {
        for ( Flow flow : flows ) {
            if ( flow.getFlowId().equals(flowId) ) {
                return flow;
            }
        }

        return null;
    }

    /**
     * TODO
     *
     * @param matchItems TODO
     * @param matchItemName TODO
     * @return TODO
     */
    private static MatchItem getMatchItem(List<MatchItem> matchItems, MatchItemName matchItemName) {
        if ( null != matchItems ) {
            for ( MatchItem matchItem : matchItems ) {
                if ( matchItem.getMatchItemName().equals(matchItemName) ) {
                    return matchItem;
                }
            }
        }

        return null;
    }

    /**
     * TODO
     *
     * @param virtualNodes TODO
     * @param ipPrefix TODO
     * @param nodes TODO
     * @param intentVnMappingResults TODO
     * @return TODO
     */
    private static VirtualNode getVirtualRouterWithIpPrefix(List<VirtualNode> virtualNodes,
                                                            String ipPrefix, List<Node> nodes,
                                                            List<IntentVnMappingResult> intentVnMappingResults) {
        NodeType layer2GroupNodeType = new NodeType("l2-group");
        NodeType externalGroupNodeType = new NodeType("ext-group");
        PropertyName propertyName = new PropertyName("ip-prefix");
        Property property;
        List<StringValue> propertyValues;
        IntentVnMappingResult intentVnMappingResult;
        VirtualResource virtualResource;
        VirtualNodeId virtualNodeId;
        VirtualNode virtualNode;

        for ( Node node : nodes ) {
            if ( node.getNodeType().equals(layer2GroupNodeType)
                    || node.getNodeType().equals(externalGroupNodeType) ) {
                property = getNodeProperty(node.getProperty(), propertyName);

                if ( null != property ) {
                    propertyValues = property.getPropertyValues().getStringValue();

                    if ( containPropertyValue(propertyValues, ipPrefix) ) {
                        intentVnMappingResult = getIntentVnMappingResult(intentVnMappingResults,
                                new IntentId(node.getNodeId().getValue()));

                        if ( null == intentVnMappingResult ) {
                            return null;
                        }

                        virtualResource = intentVnMappingResult.getVirtualResource().get(0);

                        if ( VirtualResource.VirtualResourceType.Vport
                                == virtualResource.getVirtualResourceType() ) {
                            virtualNodeId = new VirtualNodeId(
                                    virtualResource.getParentVirtualResourceEntityId().getValue());
                        } else if ( VirtualResource.VirtualResourceType.Vnode
                                == virtualResource.getVirtualResourceType() ) {
                            virtualNodeId = new VirtualNodeId(
                                    virtualResource.getVirtualResourceEntityId().getValue());
                        } else {
                            return null;
                        }

                        virtualNode = getVirtualNode(virtualNodes, virtualNodeId);

                        if ( null == virtualNode ) {
                            return null;
                        }

                        if ( VirtualNode.NodeType.Vrouter == virtualNode.getNodeType() ) {
                            return virtualNode;
                        }
                    }
                }
            }
        }

        return null;
    }

    /**
     * TODO
     *
     * @param propertyValues TODO
     * @param propertyValue TODO
     * @return TODO
     */
    private static boolean containPropertyValue(List<StringValue> propertyValues, String propertyValue) {
        for ( StringValue stringValue : propertyValues ) {
            if ( stringValue.getValue().equals(propertyValue) ) {
                return true;
            }
        }

        return false;
    }
}
