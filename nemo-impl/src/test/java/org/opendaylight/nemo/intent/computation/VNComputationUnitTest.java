/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.intent.computation;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.ReadWriteTransaction;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.VirtualNodeInstance.NodeType;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.VirtualNetwork;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.VirtualLinks;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.VirtualNodes;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.VirtualPaths;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.VirtualRoutes;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.links.VirtualLink;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.nodes.VirtualNode;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.paths.VirtualPath;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.routes.VirtualRoute;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.VirtualLinkId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.VirtualNodeId;
/**
 * Created by zhangmeng on 2015/12/3.
 */
public class VNComputationUnitTest extends TestCase {
    private VNComputationUnit vnComputationUnit;
    private DataBroker dataBroker;
    private VirtualNetwork virtualNetwork;
    private VirtualNode virtualNode;
    private VirtualNode virtualNode1;
    private VirtualNode virtualNode2;
    private VirtualNodeId nodeId;
    private VirtualNodeId nodeId1;
    private VirtualNodeId nodeId2;

    @Override
    @Before
    public void setUp() throws Exception {
        dataBroker = mock(DataBroker.class);
        virtualNetwork = mock(VirtualNetwork.class);
        UserId userId = mock(UserId.class);
         virtualNode = mock(VirtualNode.class);
         virtualNode1 = mock(VirtualNode.class);
         virtualNode2 = mock(VirtualNode.class);
        VirtualNodes virtualNodes = mock(VirtualNodes.class);
        nodeId = mock(VirtualNodeId.class);
        nodeId1 = mock(VirtualNodeId.class);
        nodeId2= mock(VirtualNodeId.class);
        NodeType nodeType = VirtualNode.NodeType.Vrouter;
        VirtualLink virtualLink = mock(VirtualLink.class);
        VirtualLinks virtualLinks = mock(VirtualLinks.class);
        VirtualLinkId linkId = mock(VirtualLinkId.class);
        VirtualNodeId srcVirtualNodeId = mock(VirtualNodeId.class);
        VirtualNodeId destVirtualNodeId = mock(VirtualNodeId.class);
        List<VirtualNode> virtualNodes_list  = new ArrayList<>();
        List<VirtualLink> virtualLinks_list = new ArrayList<>();

        virtualNodes_list.add(virtualNode);
        virtualNodes_list.add(virtualNode1);
        virtualNodes_list.add(virtualNode2);
        virtualLinks_list.add(virtualLink);

        when(virtualNetwork.getUserId()).thenReturn(userId);
        when(virtualNetwork.getVirtualNodes()).thenReturn(virtualNodes);
        when(virtualNodes.getVirtualNode()).thenReturn(virtualNodes_list);
        when(virtualNode.getNodeId()).thenReturn(nodeId);
        when(virtualNode1.getNodeId()).thenReturn(nodeId1);
        when(virtualNode2.getNodeId()).thenReturn(nodeId2);
        when(nodeId.getValue()).thenReturn(new String("src"));
        when(nodeId1.getValue()).thenReturn(new String("dst"));
        when(nodeId2.getValue()).thenReturn(new String("null"));

        when(virtualNode.getNodeType())
                .thenReturn(null)
                .thenReturn(nodeType);
        when(virtualNode1.getNodeType())
                .thenReturn(null)
                .thenReturn(nodeType);
        when(virtualNode2.getNodeType())
                .thenReturn(null)
                .thenReturn(nodeType);
        when(virtualNetwork.getVirtualLinks()).thenReturn(virtualLinks);
        when(virtualLinks.getVirtualLink()).thenReturn(virtualLinks_list);

        when(virtualLink.getLinkId()).thenReturn(linkId);
        when(linkId.getValue()).thenReturn(new String("00001111-0000-0000-0000-000011112222"));

        when(virtualLink.getSrcNodeId()).thenReturn(srcVirtualNodeId);
        when(srcVirtualNodeId.getValue()).thenReturn(new String("src"));

        when(virtualLink.getDestNodeId()).thenReturn(destVirtualNodeId);
        when(destVirtualNodeId.getValue()).thenReturn(new String("dst"));

        when(virtualLink.getMetric()).thenReturn((long)1);

        when(virtualLink.getBandwidth()).thenReturn((long)1);

        vnComputationUnit = new VNComputationUnit(dataBroker,virtualNetwork);
        vnComputationUnit = new VNComputationUnit(dataBroker,virtualNetwork);

        verify(virtualNetwork,times(2)).getUserId();
        verify(virtualNetwork,times(2)).getVirtualNodes();
        verify(virtualNodes,times(2)).getVirtualNode();
        verify(virtualNode,times(3)).getNodeId();
        verify(virtualNode1, times(3)).getNodeId();
        verify(virtualNode2, times(3)).getNodeId();
        verify(nodeId, times(2)).getValue();
        verify(nodeId1, times(2)).getValue();
        verify(nodeId2, times(2)).getValue();
        verify(virtualNode, times(2)).getNodeType();
        verify(virtualNode1, times(2)).getNodeType();
        verify(virtualNetwork,times(2)).getVirtualLinks();
        verify(virtualLinks,times(2)).getVirtualLink();
        verify(virtualLink,times(2)).getBandwidth();

    }

    @Test
    public void testComputePath() throws Exception {
        VirtualNodeId source = mock(VirtualNodeId.class);
        VirtualNodeId target = mock(VirtualNodeId.class);
        VirtualPath result;

        when(source.getValue()).thenReturn(new String("src"));
        when(target.getValue())
                .thenReturn(new String("null"))
                .thenReturn(new String("dst"));

        result = vnComputationUnit.computePath(source,target);
        Assert.assertTrue(result == null);

        result = vnComputationUnit.computePath(source,target);
        Assert.assertTrue(result != null);

        verify(source, times(2)).getValue();
        verify(target,times(2)).getValue();

    }

    @Test
    public void testComputePath1() throws Exception {
        VirtualNodeId source = mock(VirtualNodeId.class);
        VirtualNodeId target = mock(VirtualNodeId.class);
        VirtualPath result;

        when(source.getValue()).thenReturn(new String("src"));
        when(target.getValue())
                .thenReturn(new String("null"))
                .thenReturn(new String("dst"));

        result = vnComputationUnit.computePath(source,target,1);
        Assert.assertTrue(result == null);

        result = vnComputationUnit.computePath(source,target,1);
        Assert.assertTrue(result != null);

        verify(source,times(2)).getValue();
        verify(target,times(2)).getValue();

    }

    @Test
    public void testClose() throws Exception {
        vnComputationUnit.close();
        Assert.assertTrue(vnComputationUnit != null);
    }

    @Test
    public void testPrivateComputeRoute_withArgs() throws Exception{
        VirtualNetwork virtualNetwork = mock(VirtualNetwork.class);
        VirtualRoutes virtualRoutes = mock(VirtualRoutes.class);
        VirtualRoute virtualRoute = mock(VirtualRoute.class);
        VirtualPaths virtualPaths = mock(VirtualPaths.class);
        VirtualPath virtualPath = mock(VirtualPath.class);
        List<VirtualPath> virtualPathList = new ArrayList<>();
        List<VirtualRoute> virtualRouteList = new ArrayList<>();


        Class<VNComputationUnit> class1 = VNComputationUnit.class;
        Method method = class1.getDeclaredMethod("computeRoute",new Class[]{VirtualNetwork.class});
        method.setAccessible(true);

        when(virtualNetwork.getVirtualRoutes()).thenReturn(virtualRoutes);
        when(virtualRoutes.getVirtualRoute()).thenReturn(virtualRouteList);
        when(virtualNetwork.getVirtualPaths()).thenReturn(virtualPaths);
        when(virtualPaths.getVirtualPath()).thenReturn(virtualPathList);

        method.invoke(vnComputationUnit, virtualNetwork);

        Assert.assertTrue(nodeId.equals(nodeId));
        verify(virtualNetwork).getVirtualRoutes();
        verify(virtualNetwork).getVirtualPaths();
        verify(virtualPaths).getVirtualPath();
        verify(virtualRoutes).getVirtualRoute();
    }

    @Test
    public void testPrivateComputeRoute_withoutArgs() throws Exception{
        ReadWriteTransaction readWriteTransaction = mock(ReadWriteTransaction.class);

        Class<VNComputationUnit> class1 = VNComputationUnit.class;
        Method method = class1.getDeclaredMethod("computeRoute");
        method.setAccessible(true);

        when(dataBroker.newReadWriteTransaction()).thenReturn(readWriteTransaction);


        //method.invoke(vnComputationUnit);
    }
}
