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
import org.opendaylight.nemo.user.vnspacemanager.structurestyle.deleteintent.DeleteConnection;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.ConnectionId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;

import java.lang.reflect.Field;

import static org.mockito.Mockito.*;
/**
 * Created by zhangmeng on 2015/12/28.
 */
public class DeleteConnectionLangTest extends TestCase {
    private DataBroker dataBroker;
    private TenantManage tenantManage;
    private DeleteConnectionLang deleteConnectionLang;
    @Before
    public void setUp() throws Exception {
        dataBroker = mock(DataBroker.class);
        tenantManage = mock(TenantManage.class);

        deleteConnectionLang = new DeleteConnectionLang(dataBroker,tenantManage);
    }

    @Test
    public void testDeleteConnectionHandling() throws Exception {
        Class<DeleteConnectionLang> class1 = DeleteConnectionLang.class;
        Field field = class1.getDeclaredField("deleteConnection");
        field.setAccessible(true);

        UserId userId = mock(UserId.class);
        String connectionname = new String("test");

        DeleteConnection deleteConnection = mock(DeleteConnection.class);

        when(tenantManage.getObjectId(userId,connectionname))
                .thenReturn(null)
                .thenReturn(new String("00001111-0000-0000-0000-000011112222"));
        field.set(deleteConnectionLang, deleteConnection);
        when(deleteConnection.DeleteConnectionHandling(any(UserId.class),any(ConnectionId.class))).thenReturn(new String("test"));

        Assert.assertTrue(deleteConnectionLang.DeleteConnectionHandling(userId, connectionname).equals("The connection " + connectionname + " is not exist."));
        Assert.assertTrue(deleteConnectionLang.DeleteConnectionHandling(userId,connectionname).equals("test"));
        verify(tenantManage,times(3)).getObjectId(userId,connectionname);
    }
}