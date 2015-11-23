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
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Node;

/**
 * Created by wangjunfei on 2015/11/10.
 */
public class NodeInstanceCheckTest {
    private TenantManage tenantManage;
    private UserId userId;
    private Node node;
    private NodeInstanceCheck nodeInstanceCheck;

    @org.junit.Before
    public void setUp() throws Exception {
        tenantManage = mock(TenantManage.class);
        userId = mock(UserId.class);
        node = mock(Node.class);
        nodeInstanceCheck = mock(NodeInstanceCheck.class);
    }

    @org.junit.Test
    public void testCheckNodeInstance() throws Exception {
        Assert.assertNull(nodeInstanceCheck.checkNodeInstance(userId,node));
    }
}