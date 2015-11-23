/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.user.vnspacemanager.structurestyle.updateintent;
import static org.mockito.Mockito.mock;

import org.junit.Assert;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.nemo.user.tenantmanager.AAA;
import org.opendaylight.nemo.user.tenantmanager.TenantManage;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.StructureStyleNemoUpdateInput;

/**
 * Created by ldzd11 on 2015/11/9.
 */
public class UpdateIntentTest {


    UpdateIntent updateIntent;
    private DataBroker dataBroker;
    private TenantManage tenantManage;
    private UpdateNode updateNode;
    private UpdateConnection updateConnection;
    private UpdateFlow updateFlow;
    private UpdateOperation updateOperation;
    private UpdateResult updateResult;
    private AAA aaa;
    private StructureStyleNemoUpdateInput structureStyleNemoUpdateInput;


    @org.junit.Before
    public void setUp() throws Exception {

        dataBroker = mock(DataBroker.class);
        tenantManage = mock(TenantManage.class);
        updateNode = mock(UpdateNode.class);
        updateConnection = mock(UpdateConnection.class);
        updateFlow = mock(UpdateFlow.class);
        updateOperation = mock(UpdateOperation.class);
        updateResult = mock(UpdateResult.class);
        aaa = mock(AAA.class);
        structureStyleNemoUpdateInput = mock(StructureStyleNemoUpdateInput.class);

        updateIntent = mock(UpdateIntent.class);
    }

    @org.junit.Test
    public void testUpdateIntent() throws Exception {
        Assert.assertNull(updateIntent.updateIntent(aaa,structureStyleNemoUpdateInput));
    }
}