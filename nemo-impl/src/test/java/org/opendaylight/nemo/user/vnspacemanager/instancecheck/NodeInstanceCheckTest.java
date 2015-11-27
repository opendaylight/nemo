package org.opendaylight.nemo.user.vnspacemanager.instancecheck;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import org.opendaylight.nemo.user.tenantmanager.TenantManage;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.Objects;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Node;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.User;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.NodeId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.NodeName;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.NodeType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas Liu on 2015/11/23.
 */
public class NodeInstanceCheckTest extends junit.framework.TestCase {
    private NodeInstanceCheck nodeInstanceCheckTest;
    private TenantManage tenantManage;
    private UserId userId;
    private Node node;
    private User user;
    private List<Node> nodeList;
    private Objects object;
    private NodeId nodeId,nodeId2;
    private NodeType nodeType,nodeType2;
    private NodeName nodeName,nodeName2;

    @org.junit.Before
    public void setUp() throws Exception {
        tenantManage = mock(TenantManage.class);
        nodeInstanceCheckTest = new NodeInstanceCheck(tenantManage);
        userId = mock(UserId.class);
        node = mock(Node.class);
        user = mock(User.class);
        object = mock(Objects.class);
        nodeList = new ArrayList<Node>();
        nodeType = mock(NodeType.class);
        nodeType2 = mock(NodeType.class);
        nodeId = mock(NodeId.class);
        nodeId2 = mock(NodeId.class);
        nodeName = mock(NodeName.class);
        nodeName2 = mock(NodeName.class);
       nodeList.add(mock(Node.class));

    }

    @org.junit.Test
    public void testCheckNodeInstance() throws Exception {
        doNothing().when(tenantManage).fetchVNSpace(userId);
        when(tenantManage.getUser()).thenReturn(user);
        when(user.getObjects()).thenReturn(object);
        when(object.getNode()).thenReturn(nodeList);
        when(object.getNode()).thenReturn(nodeList);
        when(nodeList.get(0).getNodeId()).thenReturn(nodeId);
        when(node.getNodeId()).thenReturn(nodeId2);
        when(nodeList.get(0).getNodeName()).thenReturn(nodeName);
        when(node.getNodeName()).thenReturn(nodeName2);
        when(nodeList.get(0).getNodeType()).thenReturn(nodeType);
        when(node.getNodeType()).thenReturn(nodeType2);
        nodeInstanceCheckTest.checkNodeInstance(userId,node);
        verify(tenantManage).getUser();
        verify(user).getObjects();
        verify(object,times(2)).getNode();
        verify(nodeList.get(0)).getNodeId();
        verify(node).getNodeId();

        doNothing().when(tenantManage).fetchVNSpace(userId);
        when(tenantManage.getUser()).thenReturn(null);
        nodeInstanceCheckTest.checkNodeInstance(userId,node);
        verify(tenantManage,times(2)).getUser();


        doNothing().when(tenantManage).fetchVNSpace(userId);
        when(tenantManage.getUser()).thenReturn(user);
        when(user.getObjects()).thenReturn(null);
        nodeInstanceCheckTest.checkNodeInstance(userId,node);
        verify(tenantManage,times(3)).getUser();
        verify(user,times(2)).getObjects();


        doNothing().when(tenantManage).fetchVNSpace(userId);
        when(tenantManage.getUser()).thenReturn(user);
        when(user.getObjects()).thenReturn(object);
        when(object.getNode()).thenReturn(null);
        nodeInstanceCheckTest.checkNodeInstance(userId,node);
        verify(tenantManage,times(4)).getUser();
        verify(user,times(3)).getObjects();
        verify(object,times(3)).getNode();


    }
}