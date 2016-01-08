/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.intent.action;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import org.junit.runner.RunWith;
import org.opendaylight.nemo.intent.IntentResolverUtils;
import org.opendaylight.nemo.intent.computation.VNMappingUnit;
import org.opendaylight.nemo.intent.computation.VNMappingUnitUtils;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.nodes.VirtualNode;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.paths.VirtualPath;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.paths.VirtualPathBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.path.instance.VirtualLink;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.path.instance.VirtualLinkBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.intent.vn.mapping.results.user.intent.vn.mapping.IntentVnMappingResult;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.intent.vn.mapping.results.user.intent.vn.mapping.intent.vn.mapping.result.VirtualResource;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.PropertyValues;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.VirtualLinkId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.VirtualNodeId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.VirtualPathId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Flow;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Node;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.flow.instance.MatchItem;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.node.instance.Property;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.property.values.StringValue;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.operation.instance.Action;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.match.item.instance.MatchItemValue;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.VirtualResourceEntityId;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.support.membermodification.MemberMatcher;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import static org.mockito.Mockito.*;
/**
 * Created by zhangmeng on 2015/12/25.
 */

public class ActionResolverUtilsTest extends TestCase {
    private ActionResolverUtils actionResolverUtils;
    @Before
    public void setUp() throws Exception {
        actionResolverUtils = new ActionResolverUtils();
    }

    @Test
    public void testGetAction() throws Exception {
        List<Action> actions = new ArrayList<Action>();
        ActionName actionName = mock(ActionName.class);
        Action action = mock(Action.class);

        actions.add(action);

        when(action.getActionName())
                .thenReturn(mock(ActionName.class))
                .thenReturn(actionName);

        Assert.assertTrue(ActionResolverUtils.getAction(actions,actionName) == null);
        Assert.assertTrue(ActionResolverUtils.getAction(actions,actionName) == action);

    }

    @Test
    public void testModifyVirtualLinkBandwidth() throws Exception {
        List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.links.VirtualLink>
                virtualLinks = new ArrayList<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.links.VirtualLink>();
        VirtualLinkId virtualLinkId = mock(VirtualLinkId.class);
        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.links.VirtualLink
                virtualLink = mock(org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.links.VirtualLink.class);
        long bandwidth = 1L;

        virtualLinks.add(virtualLink);

        when(virtualLink.getLinkId())
                .thenReturn(mock(VirtualLinkId.class))
                .thenReturn(virtualLinkId);

        ActionResolverUtils.modifyVirtualLinkBandwidth(virtualLinks, virtualLinkId, bandwidth);
        ActionResolverUtils.modifyVirtualLinkBandwidth(virtualLinks,virtualLinkId,bandwidth);
        verify(virtualLink, times(4)).getLinkId();
    }

    @Test
    public void testSortVirtualResources() throws Exception {
        List<VirtualResource> virtualResources = new ArrayList<VirtualResource>();
        Assert.assertTrue(ActionResolverUtils.sortVirtualResources(virtualResources) == virtualResources);

        VirtualResource virtualResource = mock(VirtualResource.class);
        VirtualResource virtualResource1 = mock(VirtualResource.class);

        virtualResources.add(virtualResource);
        virtualResources.add(virtualResource1);

        when(virtualResource.getOrder()).thenReturn(1L);
        when(virtualResource1.getOrder()).thenReturn(0L);
        Assert.assertTrue(ActionResolverUtils.sortVirtualResources(virtualResources).get(0) == virtualResource1);
    }

    @Test
    public void testSortParameterValues() throws Exception {
        List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.StringValue>
                parameterValues = new ArrayList<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.StringValue>();
        Assert.assertTrue(ActionResolverUtils.sortParameterValues(parameterValues) == parameterValues);

        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.StringValue
                parameterValue = mock(org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.StringValue.class);
        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.StringValue
                parameterValue1 = mock(org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.StringValue.class);

        parameterValues.add(parameterValue);
        parameterValues.add(parameterValue1);

        when(parameterValue.getOrder()).thenReturn(1L);
        when(parameterValue1.getOrder()).thenReturn(0L);
        Assert.assertTrue(ActionResolverUtils.sortParameterValues(parameterValues).get(0) == parameterValue1);
    }

    @Test
    public void testGetSourceVirtualRouterOfFlow() throws Exception {
        List<VirtualNode> virtualNodes = new ArrayList<VirtualNode>();
        VirtualNode virtualNode = mock(VirtualNode.class);
        Flow flow = mock(Flow.class);
        NodeId nodeId = mock(NodeId.class);
        Node node = mock(Node.class);
        Property property = mock(Property.class);
        VirtualResource virtualResource = mock(VirtualResource.class);
        PropertyValues propertyValues_temp = mock(PropertyValues.class);
        MatchItem matchItem = mock(MatchItem.class);
        StringValue stringValue = mock(StringValue.class);
        MatchItemValue matchItemValue = mock(MatchItemValue.class);
        IntentVnMappingResult intentVnMappingResult = mock(IntentVnMappingResult.class);
        VirtualResourceEntityId virtualResourceEntityId = mock(VirtualResourceEntityId.class);

        List<Property> propertyList = new ArrayList<Property>();
        List<Node> nodes = new ArrayList<Node>();
        List<IntentVnMappingResult> intentVnMappingResults = new ArrayList<IntentVnMappingResult>();
        List<MatchItem> matchItems = new ArrayList<MatchItem>();
        List<StringValue> propertyValues = new ArrayList<StringValue>();
        List<VirtualResource> virtualResourceList = new ArrayList<VirtualResource>();

        matchItems.add(matchItem);
        intentVnMappingResults.add(intentVnMappingResult);
        nodes.add(node);
        virtualNodes.add(virtualNode);
        propertyValues.add(stringValue);
        virtualResourceList.add(virtualResource);
        propertyList.add(property);


        //get into method "getMatchItem" args(matchItems ,new MatchItemName("src-ip"))
        when(flow.getMatchItem()).thenReturn(matchItems);
        when(matchItem.getMatchItemName())
                .thenReturn(mock(MatchItemName.class))
                .thenReturn(new MatchItemName("src-ip"));
        //return null
        Assert.assertTrue(ActionResolverUtils.getSourceVirtualRouterOfFlow(virtualNodes, flow, nodes, intentVnMappingResults) == null);
        //not null
        when(matchItem.getMatchItemValue()).thenReturn(matchItemValue);
        when(matchItemValue.getStringValue()).thenReturn(new String("test"));
        //get into method "getVirtualRouterWithIpPrefix" args(virtualNodes,matchItemValue,nodes, intentVnMappingResults)
        when(node.getNodeType()).thenReturn(new NodeType("l2-group"));
        when(node.getProperty()).thenReturn(propertyList);
//        PowerMockito.mockStatic(IntentResolverUtils.class);
//        PowerMockito.when(IntentResolverUtils.getNodeProperty(any(List.class), any(PropertyName.class))).thenReturn(property);
        ////get into method "getNodeProperty" arg(propertyList,new PropertyName("ip-prefix"))
        when(property.getPropertyName()).thenReturn(new PropertyName("ip-prefix"));
        when(property.getPropertyValues()).thenReturn(propertyValues_temp);
        when(propertyValues_temp.getStringValue()).thenReturn(propertyValues);
        when(stringValue.getValue()).thenReturn(new String("test"));
        when(node.getNodeId()).thenReturn(nodeId);
        when(nodeId.getValue()).thenReturn(new String("00001111-0000-0000-0000-000011112222"));
//        PowerMockito.when(IntentResolverUtils.getIntentVnMappingResult(intentVnMappingResults, new IntentId(node.getNodeId().getValue())))
//                .thenReturn(intentVnMappingResult);
        ////get into method "getIntentVnMappingResult" args(intentVnMappingResults,new IntentId(new String("00001111-0000-0000-0000-000011112222")))
        when(intentVnMappingResult.getIntentId()).thenReturn(new IntentId(new String("00001111-0000-0000-0000-000011112222")));
        when(intentVnMappingResult.getVirtualResource()).thenReturn(virtualResourceList);
        when(virtualResource.getVirtualResourceType()).thenReturn(VirtualResource.VirtualResourceType.Vport);
        when(virtualResource.getParentVirtualResourceEntityId()).thenReturn(virtualResourceEntityId);
        when(virtualResourceEntityId.getValue()).thenReturn(new String("00001111-1111-0000-0000-000011112222"));
//        PowerMockito.when(IntentResolverUtils.getVirtualNode(any(List.class), any(VirtualNodeId.class)))
//                .thenReturn(virtualNode);
        ////get into method "getVirtualNode" arg(virtualNodes,new VirtualNodeId(new String("00001111-1111-0000-0000-000011112222")))
        when(virtualNode.getNodeId()).thenReturn(new VirtualNodeId(new String("00001111-1111-0000-0000-000011112222")));
        when(virtualNode.getNodeType()).thenReturn(VirtualNode.NodeType.Vrouter);
        Assert.assertTrue(ActionResolverUtils.getSourceVirtualRouterOfFlow(virtualNodes, flow, nodes, intentVnMappingResults) != null);

    }

    @Test
    public void testGetDestinationVirtualRouterOfFlow() throws Exception {
        List<VirtualNode> virtualNodes = new ArrayList<VirtualNode>();
        Flow flow = mock(Flow.class);
        List<Node> nodes = new ArrayList<Node>();
        List<IntentVnMappingResult> intentVnMappingResults = new ArrayList<IntentVnMappingResult>();

        MatchItem matchItem = mock(MatchItem.class);
        MatchItemValue matchItemValue = mock(MatchItemValue.class);
        List<MatchItem> matchItems = new ArrayList<MatchItem>();

        matchItems.add(matchItem);

        when(flow.getMatchItem()).thenReturn(matchItems);
        when(matchItem.getMatchItemName())
                .thenReturn(mock(MatchItemName.class))
                .thenReturn(new MatchItemName("dst-ip"));
        //test null
        Assert.assertTrue(ActionResolverUtils.getDestinationVirtualRouterOfFlow(virtualNodes, flow, nodes, intentVnMappingResults) == null);

        when(matchItem.getMatchItemValue()).thenReturn(matchItemValue);
        when(matchItemValue.getStringValue()).thenReturn(new String("test"));
        //get into method "getVirtualRouterWithIpPrefix" args(virtualNodes,matchItemValue,nodes, intentVnMappingResults)
        //return null
        Assert.assertTrue(ActionResolverUtils.getDestinationVirtualRouterOfFlow(virtualNodes, flow, nodes, intentVnMappingResults) == null);

    }

    @Test
    public void testMergeVirtualPaths() throws Exception {
        List<VirtualPath> virtualPaths = new ArrayList<VirtualPath>();

        VirtualPath virtualPath = mock(VirtualPath.class);
        VirtualPath virtualPath1 = mock(VirtualPath.class);
        VirtualLink virtualLink = mock(VirtualLink.class);
        List<VirtualLink> virtualLinkList = new ArrayList<VirtualLink>();
        
        virtualLinkList.add(virtualLink);
        
        // when virtualPaths is empty
        Assert.assertTrue(ActionResolverUtils.mergeVirtualPaths(virtualPaths) != null);
        // size 1
        virtualPaths.add(virtualPath);
        Assert.assertTrue(ActionResolverUtils.mergeVirtualPaths(virtualPaths) == virtualPath);
        // size >1
        virtualPaths.add(virtualPath1);
        when(virtualPath.getVirtualLink()).thenReturn(virtualLinkList);
        when(virtualPath1.getVirtualLink()).thenReturn(virtualLinkList);
        when(virtualPath.getMetric()).thenReturn(1L);
        when(virtualPath.getDelay()).thenReturn(1L);
        when(virtualPath1.getMetric()).thenReturn(1L);
        when(virtualPath1.getDelay()).thenReturn(1L);
        when(virtualPath.getBandwidth()).thenReturn(1L);
        when(virtualPath1.getBandwidth()).thenReturn(1L);
        Assert.assertTrue(ActionResolverUtils.mergeVirtualPaths(virtualPaths) != null);
        verify(virtualPath).getMetric();
        verify(virtualPath1).getMetric();

    }
}
