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
import org.opendaylight.nemo.user.vnspacemanager.structurestyle.deleteintent.DeleteFlow;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.FlowId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;

import java.lang.reflect.Field;

import static org.mockito.Mockito.*;
/**
 * Created by zhangmeng on 2015/12/28.
 */
public class DeleteFlowLangTest extends TestCase {
    private DataBroker dataBroker;
    private TenantManage tenantManage;
    private DeleteFlowLang deleteFlowLang;
    @Before
    public void setUp() throws Exception {
        dataBroker = mock(DataBroker.class);
        tenantManage = mock(TenantManage.class);

        deleteFlowLang = new DeleteFlowLang(dataBroker,tenantManage);
    }

    @Test
    public void testDeleteFlowHandling() throws Exception {
        Class<DeleteFlowLang> class1 = DeleteFlowLang.class;
        Field field = class1.getDeclaredField("deleteFlow");
        field.setAccessible(true);

        UserId userId = mock(UserId.class);
        String flowname = new String("test");
        DeleteFlow deleteFlow = mock(DeleteFlow.class);

        when(tenantManage.getObjectId(userId,flowname))
                .thenReturn(null)
                .thenReturn("00001111-0000-0000-0000-000011112222");

        Assert.assertTrue(deleteFlowLang.DeleteFlowHandling(userId, flowname).equals("The flow " + flowname + " is not exist."));

        field.set(deleteFlowLang, deleteFlow);
        when(deleteFlow.DeleteFlowHandling(any(UserId.class),any(FlowId.class))).thenReturn(new String("test"));
        Assert.assertTrue(deleteFlowLang.DeleteFlowHandling(userId, flowname).equals("test"));

    }
}