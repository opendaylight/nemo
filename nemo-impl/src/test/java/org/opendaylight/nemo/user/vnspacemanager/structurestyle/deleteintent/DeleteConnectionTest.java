/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.user.vnspacemanager.structurestyle.deleteintent;
import static org.mockito.Mockito.mock;

import org.junit.Assert;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.nemo.user.tenantmanager.TenantManage;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.ConnectionId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
public class  DeleteConnectionTest{
	    private DataBroker dataBroker;
        private TenantManage tenantManage;
        private DeleteConnection deleteconnection;
        private UserId userId;
        private ConnectionId connectionID;
@org.junit.Before
public void setUp() throws Exception {
	    dataBroker=mock(DataBroker.class);
		tenantManage=mock(TenantManage.class);
        deleteconnection=mock(DeleteConnection.class);
        userId=mock(UserId.class);
        connectionID=mock(ConnectionId.class);
        }
@org.junit.Test
public void DeleteConnectionHandlingTest() throws Exception{
        Assert.assertNull(deleteconnection.DeleteConnectionHandling(userId,connectionID));
        }
        }