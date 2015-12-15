/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package user.vnspacemanager.languagestyle.deleteintentlang;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import org.opendaylight.nemo.user.tenantmanager.TenantManage;
import org.opendaylight.nemo.user.vnspacemanager.languagestyle.deleteintentlang.DeleteConnectionLang;
import org.opendaylight.nemo.user.vnspacemanager.languagestyle.deleteintentlang.DeleteNodeLang;
import org.opendaylight.nemo.user.vnspacemanager.structurestyle.deleteintent.DeleteNode;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Node;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.User;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.Objects;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.NodeName;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.NodeId;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.*;
/**
 * Created by zhangmeng on 2015/12/10.
 */
public class DeleteNodeLangTest extends TestCase {
    private DataBroker dataBroker;
    private TenantManage tenantManage;
    private DeleteNode deleteNode;
    private DeleteNodeLang deleteNodeLang;
    @Before
    public void setUp() throws Exception {
        dataBroker = mock(DataBroker.class);
        tenantManage = mock(TenantManage.class);
        deleteNode = mock(DeleteNode.class);

        deleteNodeLang = new DeleteNodeLang(dataBroker,tenantManage);
    }

    @Test
    public void testDeleteNodeHandling() throws Exception {
        User user = mock(User.class);
        UserId userId = mock(UserId.class);
        Objects objects = mock(Objects.class);
        Node node = mock(Node.class);
        NodeName nodeName = mock(NodeName.class);
        NodeId nodeId = mock(NodeId.class);
        String nodename = new String("test");
        String result;
        List<Node> nodeList = new ArrayList<Node>();

        doNothing().when(tenantManage).fetchVNSpace(userId);
        when(tenantManage.getUser()).thenReturn(user);
        when(user.getObjects())
                .thenReturn(null)
                .thenReturn(objects);

        result = deleteNodeLang.DeleteNodeHandling(userId,nodename);
        Assert.assertTrue(result.equals("The node " + nodename + " is not exist in this user vn space."));
        verify(user).getObjects();

        when(objects.getNode()).thenReturn(nodeList);
        Assert.assertTrue(nodeList.isEmpty());
        result = deleteNodeLang.DeleteNodeHandling(userId,nodename);
        Assert.assertTrue(result.equals("The node " + nodename + " is not exist in this user vn space."));

        nodeList.add(node);
        when(node.getNodeName()).thenReturn(nodeName);
        when(nodeName.getValue())
                .thenReturn("")
                .thenReturn(nodename);
        Assert.assertTrue(!nodeList.isEmpty());
        result = deleteNodeLang.DeleteNodeHandling(userId,nodename);
        Assert.assertTrue(result.equals("The node " + nodename + " is not exist in this user vn space."));

        Class<DeleteNodeLang> class1 = DeleteNodeLang.class;
        Field field = class1.getDeclaredField("deleteNode");
        field.setAccessible(true);
        field.set(deleteNodeLang, deleteNode);
        System.out.println(field.get(deleteNodeLang));
        when(node.getNodeId()).thenReturn(nodeId);
        when(deleteNode.DeleNodeHandling(any(UserId.class), any(NodeId.class))).thenReturn(new String("test"));
        result = deleteNodeLang.DeleteNodeHandling(userId,nodename);
        Assert.assertTrue(result.equals("test"));
        verify(node).getNodeId();
    }
}