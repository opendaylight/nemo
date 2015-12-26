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
import org.opendaylight.nemo.user.vnspacemanager.structurestyle.deleteintent.DeleteOperation;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.OperationId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;

import java.lang.reflect.Field;

import static org.mockito.Mockito.*;

/**
 * Created by zhangmeng on 2015/12/28.
 */
public class DeleteOperationLangTest extends TestCase {
    private DataBroker dataBroker;
    private TenantManage tenantManage;
    private DeleteOperationLang deleteOperationLang;
    @Before
    public void setUp() throws Exception {
        dataBroker = mock(DataBroker.class);
        tenantManage = mock(TenantManage.class);

        deleteOperationLang = new DeleteOperationLang(dataBroker,tenantManage);
    }

    @Test
    public void testDeleteOperationHandling() throws Exception {
        Class<DeleteOperationLang> class1 = DeleteOperationLang.class;
        Field field = class1.getDeclaredField("deleteOperation");
        field.setAccessible(true);

        UserId userId = mock(UserId.class);
        String operationname = new String("test");
        DeleteOperation deleteOperation = mock(DeleteOperation.class);

        when(tenantManage.getObjectId(userId,operationname))
                .thenReturn(null)
                .thenReturn(new String("00001111-0000-0000-0000-000011112222"));
        Assert.assertTrue(deleteOperationLang.DeleteOperationHandling(userId, operationname).equals("The operation " + operationname + " is not exist."));
        verify(tenantManage).getObjectId(userId, operationname);

        field.set(deleteOperationLang, deleteOperation);
        when(deleteOperation.DeleteOperationhandling(any(UserId.class),any(OperationId.class))).thenReturn(new String("test"));
        Assert.assertTrue(deleteOperationLang.DeleteOperationHandling(userId, operationname).equals("test"));
        verify(tenantManage,times(3)).getObjectId(userId, operationname);

    }
}