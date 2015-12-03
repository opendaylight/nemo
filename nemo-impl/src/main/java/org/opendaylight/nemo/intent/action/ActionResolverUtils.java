/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.nemo.intent.action;

import org.opendaylight.nemo.intent.IntentResolverUtils;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.nodes.VirtualNode;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.paths.VirtualPath;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.paths.VirtualPathBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.path.instance.VirtualLink;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.path.instance.VirtualLinkBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.intent.vn.mapping.results.user.intent.vn.mapping.IntentVnMappingResult;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.intent.vn.mapping.results.user.intent.vn.mapping.intent.vn.mapping.result.VirtualResource;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.VirtualLinkId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.VirtualNodeId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.VirtualPathId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Flow;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Node;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.flow.instance.MatchItem;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.node.instance.Property;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.property.values.StringValue;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.operation.instance.Action;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * Implement the common utilities frequently used in
 * the action resolution.
 *
 * @author Zhigang Ji
 */
public class ActionResolverUtils {
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
     * @param virtualLinks TODO
     * @param virtualLinkId TODO
     * @param bandwidth TODO
     */
    protected static void modifyVirtualLinkBandwidth(
            List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.links.VirtualLink> virtualLinks,
            VirtualLinkId virtualLinkId, long bandwidth) {
        int i = 0;

        for ( org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.links.VirtualLink
                virtualLink : virtualLinks ) {
            if ( virtualLink.getLinkId().equals(virtualLinkId) ) {
                org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.links.VirtualLink virtualLink1 =
                        new org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.links.VirtualLinkBuilder(virtualLink)
                                .setBandwidth(bandwidth)
                                .build();

                virtualLinks.set(i, virtualLink1);

                return;
            }

            i++;
        }

        return;
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
     * @param parameterValues TODO
     * @return TODO
     */
    protected static List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.StringValue> sortParameterValues(
            List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.StringValue> parameterValues) {
        if ( parameterValues.isEmpty() || 1 == parameterValues.size() ) {
            return parameterValues;
        }

        List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.StringValue> sortedParameterValues =
                new ArrayList<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.StringValue>(parameterValues.size());
        sortedParameterValues.addAll(parameterValues);

        for ( org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.StringValue
                parameterValue : parameterValues ) {
            sortedParameterValues.set(parameterValue.getOrder().intValue(), parameterValue);
        }

        return sortedParameterValues;
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
     * @param virtualPaths TODO
     * @return TODO
     */
    protected static VirtualPath mergeVirtualPaths(List<VirtualPath> virtualPaths) {
        if ( virtualPaths.isEmpty() ) {
            VirtualPath virtualPath = new VirtualPathBuilder()
                    .setPathId(new VirtualPathId(UUID.randomUUID().toString()))
                    .setVirtualLink(new ArrayList<VirtualLink>(0))
                    .setMetric(0L)
                    .setBandwidth(0L)
                    .setDelay(0L)
                    .build();

            return virtualPath;
        }

        if ( 1 == virtualPaths.size() ) {
            return virtualPaths.get(0);
        }

        List<VirtualLink> virtualLinks = new LinkedList<VirtualLink>();
        VirtualLink virtualLink;
        long metric = 0;
        long bandwidth = Long.MAX_VALUE;
        long delay = 0;

        for ( VirtualPath virtualPath : virtualPaths ) {
            for ( VirtualLink virtualLink1 : virtualPath.getVirtualLink() ) {
                virtualLink = new VirtualLinkBuilder(virtualLink1)
                        .setOrder((long)virtualLinks.size())
                        .build();
                virtualLinks.add(virtualLink);
            }

            metric += virtualPath.getMetric();
            delay += virtualPath.getDelay();

            if ( virtualPath.getBandwidth() < bandwidth ) {
                bandwidth = virtualPath.getBandwidth();
            }
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
                property = IntentResolverUtils.getNodeProperty(node.getProperty(), propertyName);

                if ( null != property ) {
                    propertyValues = property.getPropertyValues().getStringValue();

                    if ( containPropertyValue(propertyValues, ipPrefix) ) {
                        intentVnMappingResult = IntentResolverUtils.getIntentVnMappingResult(
                                intentVnMappingResults, new IntentId(node.getNodeId().getValue()));

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

                        virtualNode = IntentResolverUtils.getVirtualNode(virtualNodes, virtualNodeId);

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
