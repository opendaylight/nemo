/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.renderer.cli;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.path.instance.PhysicalLink;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.NodeType;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.Objects;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.PhysicalNetwork;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.PhysicalLinks;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.PhysicalPaths;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.nodes.PhysicalNode;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.paths.PhysicalPath;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.paths.PhysicalPathBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.VirtualNetwork;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.intent.vn.mapping.results.UserIntentVnMapping;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.intent.vn.mapping.results.user.intent.vn.mapping.IntentVnMappingResult;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.intent.vn.mapping.results.user.intent.vn.mapping.intent.vn.mapping.result.VirtualResource;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.vn.pn.mapping.results.UserVnPnMapping;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.vn.pn.mapping.results.user.vn.pn.mapping.VnPnMappingResult;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.ConnectionId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.NodeId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.PhysicalResourceEntityId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.PhysicalLinkId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.PhysicalNodeId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.PhysicalPortId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.VirtualResourceEntityId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.IntentId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Connection;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Node;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.User;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.connection.instance.EndNode;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.VirtualResourceInstance.VirtualResourceType;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.PhysicalLinkId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
/**
 * Created by zhangmeng on 2015/12/7.
 */
public class CliBuilderTest extends TestCase {
    private CliBuilder cliBuilder;
    private DataBroker dataBroker;

    @Before
    public void setUp() throws Exception {
        dataBroker = mock(DataBroker.class);
        cliBuilder = new CliBuilder(dataBroker);
    }

    @Test
    public void testInit() throws Exception {
        List<PhysicalNode> physicalNodes = new ArrayList<PhysicalNode>();
        cliBuilder.init(physicalNodes);
    }

    @Test//
    public void testIsFullMeshTopology() throws Exception {
//        Object configElementOnePE ;
//        Object configElementAnother;
//        Class classes = cliBuilder.getClass().getDeclaringClass();
//
//            int i = classes.getModifiers();
//            String s = Modifier.toString(i);
//            configElementOnePE = classes.getConstructor().newInstance();
//            configElementAnother = classes.getConstructor().newInstance();
//
//
//        PhysicalNodeId physicalNodeId = mock(PhysicalNodeId.class);
//        PhysicalNodeId physicalNodeId1 = mock(PhysicalNodeId.class);
//        List<List> peersList = new ArrayList<List>();
//        List peers = new ArrayList();
//
//        peers.add(configElementOnePE);
//        peers.add(configElementAnother);
//        peersList.add(peers);
//
//        when(configElementOnePE.getPhysicalNodeId()).thenReturn(physicalNodeId);
//        when(configElementAnother.getPhysicalNodeId()).thenReturn(physicalNodeId1);
//
//        cliBuilder.isFullMeshTopology(peersList);

//        Class classes[] = CliBuilder.class.getClass().getDeclaredClasses();
//        Assert.assertTrue(classes.length == 1);
//        Class c = classes[0];


    }

    @Test
    public void testUpdateCliExecutionSequence() throws Exception {
        User user = mock(User.class);
        VirtualNetwork virtualNetwork = mock(VirtualNetwork.class);
        UserIntentVnMapping userIntentVnMapping = mock(UserIntentVnMapping.class);
        UserVnPnMapping userVnPnMapping = mock(UserVnPnMapping.class);
        PhysicalNetwork physicalNetwork = mock(PhysicalNetwork.class);
        Objects objects = mock(Objects.class);
        Connection connection = mock(Connection.class);
        EndNode endnode = mock(EndNode.class);
        EndNode endnode1 = mock(EndNode.class);
        NodeId nodeId = mock(NodeId.class);
        NodeId nodeId1 = mock(NodeId.class);
        Node node = mock(Node.class);
        NodeType nodeType = mock(NodeType.class);
        IntentVnMappingResult intentVnMappingResult = mock(IntentVnMappingResult.class);
        IntentId intentId = mock(IntentId.class);
        VirtualResource virtualResource = mock(VirtualResource.class);
        VnPnMappingResult vnPnMappingResult = mock(VnPnMappingResult.class);
        VirtualResourceType virtualResourceType = VirtualResource.VirtualResourceType.Vport;
        VirtualResourceEntityId virtualResourceEntityId = mock(VirtualResourceEntityId.class);
        PhysicalResourceEntityId physicalResourceEntityId = mock(PhysicalResourceEntityId.class);
        PhysicalResourceEntityId physicalResourceEntityId_parent = mock(PhysicalResourceEntityId.class);
        PhysicalPaths physicalPaths = mock(PhysicalPaths.class);
        PhysicalPath physicalPath = mock(PhysicalPath.class);
        PhysicalLink physicalLink = mock(PhysicalLink.class);
        PhysicalLinkId physicalLinkId = mock(PhysicalLinkId.class);
        PhysicalLinkId physicalLinkId1 = mock(PhysicalLinkId.class);
        PhysicalLinks physicalLinksInNetwork = mock(PhysicalLinks.class);
        PhysicalNodeId physicalNodeId_src = mock(PhysicalNodeId.class);
        PhysicalNodeId physicalNodeId_dst = mock(PhysicalNodeId.class);
        org.opendaylight.yang.gen.v1.urn.opendaylight.params
                .xml.ns.yang.generic.physical.network.rev151010
                .physical.path.instance.PhysicalLink physicalLink_special =
                mock(org.opendaylight.yang.gen.v1.urn.opendaylight.params
                        .xml.ns.yang.generic.physical.network.rev151010
                        .physical.path.instance.PhysicalLink.class);
        org.opendaylight.yang.gen.v1.urn.opendaylight.params
                .xml.ns.yang.generic.physical.network.rev151010
                .physical.path.instance.PhysicalLink physicalLink_special1 =
                mock(org.opendaylight.yang.gen.v1.urn.opendaylight.params
                        .xml.ns.yang.generic.physical.network.rev151010
                        .physical.path.instance.PhysicalLink.class);
        org.opendaylight.yang.gen.v1.urn.opendaylight.params
                .xml.ns.yang.generic.physical.network.rev151010
                .physical.network.physical.links
                .PhysicalLink physicalLink_getSrcNodeIdforLinkInPath =
                mock(org.opendaylight.yang.gen.v1.urn.opendaylight.params
                        .xml.ns.yang.generic.physical.network.rev151010
                        .physical.network.physical.links
                        .PhysicalLink.class);
        List<PhysicalLink> physicalLinkList = new ArrayList<PhysicalLink>();
        List<Connection> connectionList = new ArrayList<Connection>();
        List<EndNode> endNodeList = new ArrayList<EndNode>();
        List<Node> nodeList = new ArrayList<Node>();
        List<IntentVnMappingResult> intentVnMappingResults = new ArrayList<IntentVnMappingResult>();
        List<VirtualResource> virtualResources = new ArrayList<VirtualResource>();
        List<VnPnMappingResult> vnPnMappingResults = new ArrayList<VnPnMappingResult>();
        List<PhysicalPath> physicalPathList = new ArrayList<PhysicalPath>();
        List< org.opendaylight.yang.gen.v1.urn.opendaylight.params
                .xml.ns.yang.generic.physical.network.rev151010
                .physical.path.instance.PhysicalLink> physicalLinks =
                new ArrayList<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.path.instance.PhysicalLink>();
        List<org.opendaylight.yang.gen.v1.urn.opendaylight.params
                .xml.ns.yang.generic.physical.network.rev151010
                .physical.network.physical.links.PhysicalLink>
                physicalLinksInNetworkList =
                new ArrayList<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.links.PhysicalLink>();


        intentVnMappingResults.add(intentVnMappingResult);
        connectionList.add(connection);
        endNodeList.add(endnode);
        nodeList.add(node);
        virtualResources.add(virtualResource);
        vnPnMappingResults.add(vnPnMappingResult);
        physicalPathList.add(physicalPath);
        physicalLinkList.add(physicalLink);
        physicalLinks.add(physicalLink_special);
        physicalLinks.add(physicalLink_special1);
        physicalLinksInNetworkList.add(physicalLink_getSrcNodeIdforLinkInPath);
//        endNodeList.add(endnode1);

        when(user.getObjects())
                .thenReturn(null)//test null
                .thenReturn(objects)//get into circle
                .thenReturn(objects)//List<Connection> connectionList = user.getObjects().getConnection();
                .thenReturn(objects);//used in method "isConnectTwoExtGroup"
        //test null
        cliBuilder.updateCliExecutionSequence(user, virtualNetwork, userIntentVnMapping, userVnPnMapping, physicalNetwork);
        verify(user).getObjects();

        //test not null
        when(objects.getConnection()).thenReturn(connectionList);
        when(connection.getEndNode())
                .thenReturn(endNodeList)
                .thenReturn(endNodeList);//used in method "isConnectTwoExtGroup"
        ////get into method "isConnectTwoExtGroup"
        when(endnode.getNodeId()).thenReturn(nodeId);
        when(endnode1.getNodeId()).thenReturn(nodeId1);
        when(objects.getNode()).thenReturn(nodeList);
        when(node.getNodeId()).thenReturn(nodeId);//if(node.getNodeId().equals(nodeId))
        when(node.getNodeType()).thenReturn(nodeType);
        when(nodeType.getValue())
                .thenReturn(new String("null"))
                .thenReturn(new String("ext-group"));//test not null

        endNodeList.add(endnode1);
        Assert.assertTrue(endNodeList.size() == 2);
        Assert.assertTrue(endNodeList.get(1) == endnode1 && endNodeList.get(0) == endnode);

        //test null
        cliBuilder.updateCliExecutionSequence(user, virtualNetwork, userIntentVnMapping, userVnPnMapping, physicalNetwork);
        verify(nodeType).getValue();

        ////get into method "getNodeVnPnMappingResult".  args(nodeId,userIntentVnMapping,userVnPnMapping)
        when(userIntentVnMapping.getIntentVnMappingResult()).thenReturn(intentVnMappingResults);
        when(intentVnMappingResult.getIntentId()).thenReturn(intentId);
        when(nodeId.getValue()).thenReturn(new String("test"));
        when(intentId.getValue()).thenReturn(new String("test"));
        when(intentVnMappingResult.getVirtualResource()).thenReturn(virtualResources);
        when(virtualResource.getVirtualResourceType()).thenReturn(virtualResourceType);
        when(userVnPnMapping.getVnPnMappingResult()).thenReturn(vnPnMappingResults);
        when(vnPnMappingResult.getVirtualResourceEntityId()).thenReturn(virtualResourceEntityId);
        when(virtualResource.getVirtualResourceEntityId()).thenReturn(virtualResourceEntityId);
        when(virtualResourceEntityId.getValue()).thenReturn(new String("test"));
        //get out of method "getNodeVnPnMappingResult"  ....  and vnPnMappingResult is returned
        when(vnPnMappingResult.getPhysicalResourceEntityId()).thenReturn(physicalResourceEntityId);
        when(physicalResourceEntityId.getValue()).thenReturn(new String("test"));
        when(vnPnMappingResult.getParentPhysicalResourceEntityId()).thenReturn(physicalResourceEntityId_parent);
        when(physicalResourceEntityId_parent.getValue()).thenReturn(new String("test"));
        /////get into method "getNodeVnPnMappingResult" again. args(nodeId,userIntentVnMapping,userVnPnMapping)
        when(nodeId1.getValue()).thenReturn(new String("test"));
        /*
        get out of method "getNodeVnPnMappingResult"  ....  and vnPnMappingResult is returned
                no more when and return is needed!
        */
        ////get into method "getPhysicalPathforConnection" args (onePhysicalNodeId, otherPhysicalNodeId, physicalNetwork)
        when(physicalNetwork.getPhysicalPaths()).thenReturn(physicalPaths);
        when(physicalPaths.getPhysicalPath())
                .thenReturn(null)
                .thenReturn(physicalPathList);

        cliBuilder.updateCliExecutionSequence(user, virtualNetwork, userIntentVnMapping, userVnPnMapping, physicalNetwork);

        //////get into method "sortPhysicalLinksOfPhysicalPath" args(physicalPath)
        when(physicalPath.getPhysicalLink())
                .thenReturn(physicalLinkList)//if ( physicalPath.getPhysicalLink().isEmpty()|| 1 == physicalPath.getPhysicalLink().size() )
                .thenReturn(physicalLinkList)
                .thenReturn(physicalLinks);//back to method "getPhysicalPathforConnection"
        Assert.assertTrue(physicalLinkList.size() == 1);
        ////get out of method "sortPhysicalLinksOfPhysicalPath" args(physicalPath)
        when(physicalLink_special.getLinkId()).thenReturn(physicalLinkId);
        when(physicalLink_special1.getLinkId()).thenReturn(physicalLinkId1);
        //////get into method "getSrcNodeIdforLinkInPath"
        PhysicalLinkId physicalLinkId_getSrcNodeIdforLinkInPath = mock(PhysicalLinkId.class);
        when(physicalNetwork.getPhysicalLinks()).thenReturn(physicalLinksInNetwork);
        when(physicalLinksInNetwork.getPhysicalLink()).thenReturn(physicalLinksInNetworkList);
        when(physicalLink_getSrcNodeIdforLinkInPath.getLinkId()).thenReturn(physicalLinkId_getSrcNodeIdforLinkInPath);
        when(physicalLinkId_getSrcNodeIdforLinkInPath.getValue()).thenReturn(new String("test"));
        when(physicalLinkId.getValue()).thenReturn(new String("test"));
        when(physicalLink_getSrcNodeIdforLinkInPath.getSrcNodeId()).thenReturn(physicalNodeId_src);
        ////get out of  method "getSrcNodeIdforLinkInPath"
        ////// get into method "getDestNodeIdforLinkInPath"
        when(physicalLinkId1.getValue()).thenReturn(new String("test"));
        when(physicalLink_getSrcNodeIdforLinkInPath.getDestNodeId()).thenReturn(physicalNodeId_dst);
        //// return into method "getPhysicalPathforConnection"
        when(physicalNodeId_src.getValue()).thenReturn(new String("test1"));
        when(physicalNodeId_dst.getValue()).thenReturn(new String("test"));

        cliBuilder.updateCliExecutionSequence(user, virtualNetwork, userIntentVnMapping, userVnPnMapping, physicalNetwork);

    }

    @Test
    public void testClose() throws Exception {

    }
}