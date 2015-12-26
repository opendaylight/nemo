/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.user.vnspacemanager.languagestyle.deleteintentlang;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.nemo.user.tenantmanager.TenantManage;
import org.opendaylight.nemo.user.vnspacemanager.structurestyle.deleteintent.DeleteNode;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.NodeId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;

import java.lang.reflect.Field;

import static org.mockito.Mockito.*;
/**
 * Created by zhangmeng on 2015/12/28.
 */
public class DeleteNodeLangTest extends TestCase {
    private DataBroker dataBroker;
    private TenantManage tenantManage;
    private DeleteNodeLang deleteNodeLang;
    @Before
    public void setUp() throws Exception {
        dataBroker = mock(DataBroker.class);
        tenantManage = mock(TenantManage.class);

        deleteNodeLang = new DeleteNodeLang(dataBroker,tenantManage);
    }

    @Test
    public void testDeleteNodeHandling() throws Exception {
        Class<DeleteNodeLang> class1 = DeleteNodeLang.class;
        Field field = class1.getDeclaredField("deleteNode");
        field.setAccessible(true);

        UserId userId = mock(UserId.class);
        String nodename = new String("test");
        DeleteNode deleteNode = mock(DeleteNode.class);

        when(tenantManage.getObjectId(userId,nodename))
                .thenReturn(null)
                .thenReturn(new String("00001111-0000-0000-0000-000011112222"));
        Assert.assertTrue(deleteNodeLang.DeleteNodeHandling(userId, nodename).equals("The node " + nodename + " is not exist."));
        verify(tenantManage).getObjectId(userId, nodename);

        field.set(deleteNodeLang, deleteNode);
        when(deleteNode.DeleNodeHandling(any(UserId.class),any(NodeId.class))).thenReturn(new String("test"));
        Assert.assertTrue(deleteNodeLang.DeleteNodeHandling(userId, nodename).equals("test"));
        verify(tenantManage,times(3)).getObjectId(userId, nodename);

    }
}