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
import org.opendaylight.nemo.user.tenantmanager.TenantManage;
import org.opendaylight.nemo.user.vnspacemanager.instancecheck.ConnectionInstanceCheck;
import org.opendaylight.nemo.user.vnspacemanager.syntaxcheck.ConnectionDefinitionCheck;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Connection;
/**
 * Created by ldzd11 on 2015/11/9.
 */
public class UpdateConnectionTest {


    private UpdateConnection updateConnection;

    private UserId userId;
    private Connection connection;
    private DataBroker dataBroker;
    private TenantManage tenantManage;
    private ConnectionDefinitionCheck connectionDefinitionCheck;
    private ConnectionInstanceCheck connectionInstanceCheck;



    @org.junit.Before
    public void setUp() throws Exception {

        dataBroker = mock(DataBroker.class);
        tenantManage = mock(TenantManage.class);
        connectionDefinitionCheck = mock(ConnectionDefinitionCheck.class);
        connectionInstanceCheck = mock(ConnectionInstanceCheck.class);

        userId = mock(UserId.class);
        connection = mock(Connection.class);
        updateConnection = mock(UpdateConnection.class);
    }

    @org.junit.Test
    public void testConnectionHandling() throws Exception {

        Assert.assertNull(updateConnection.ConnectionHandling(userId, connection));

    }
}