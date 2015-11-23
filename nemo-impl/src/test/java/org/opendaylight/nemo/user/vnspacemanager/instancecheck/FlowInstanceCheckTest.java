/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.user.vnspacemanager.instancecheck;

import static org.mockito.Mockito.mock;

import org.junit.Assert;
import org.opendaylight.nemo.user.tenantmanager.TenantManage;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Flow;
/**
 * Created by wangjunfei on 2015/11/10.
 */
public class FlowInstanceCheckTest {
    private TenantManage tenantManage;
    private Flow flow;
    private FlowInstanceCheck flowInstanceCheck;
    private UserId userId;

    @org.junit.Before
    public void setUp() throws Exception {
        userId = mock(UserId.class);
        tenantManage = mock(TenantManage.class);
        flow = mock(Flow.class);
        flowInstanceCheck = mock(FlowInstanceCheck.class);
    }

    @org.junit.Test
    public void testCheckFlowInstance() throws Exception {
        Assert.assertNull(flowInstanceCheck.checkFlowInstance(userId,flow));
    }
}