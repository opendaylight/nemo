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
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.FlowId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
public class DeleteFlowTest{
	private DataBroker dataBroker;
    private TenantManage tenantManage;
	private DeleteFlow deleteflow;
	private UserId userId;
    private	FlowId flowId;
	@org.junit.Before
	public void setUp() throws Exception {
		dataBroker=mock(DataBroker.class);
		tenantManage=mock(TenantManage.class);
		deleteflow=mock(DeleteFlow.class);
		userId=mock(UserId.class);
		flowId=mock(FlowId.class);
	}
	@org.junit.Test
	public void  DeleteFlowHandlingTest() throws Exception{
		Assert.assertNull(deleteflow.DeleteFlowHandling(userId,flowId));
	}
}
