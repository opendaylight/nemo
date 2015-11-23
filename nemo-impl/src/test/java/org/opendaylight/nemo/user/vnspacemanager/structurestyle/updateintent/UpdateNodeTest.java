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
import org.opendaylight.nemo.user.vnspacemanager.instancecheck.NodeInstanceCheck;
import org.opendaylight.nemo.user.vnspacemanager.syntaxcheck.NodeDefinitionCheck;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Node;
/**
 * Created by ldzd11 on 2015/11/9.
 */
public class UpdateNodeTest {


    private  UpdateNode updateNode;

    private DataBroker dataBroker;
    private NodeDefinitionCheck nodeCheck;
    private NodeInstanceCheck nodeInstanceCheck;
    private UserId userId;
    private Node node;

    private  TenantManage tenantManage;
    @org.junit.Before
    public void setUp() throws Exception {
        dataBroker = mock(DataBroker.class);
        nodeCheck = mock(NodeDefinitionCheck.class);
        nodeInstanceCheck = mock(NodeInstanceCheck.class);

        tenantManage = mock(TenantManage.class);
        userId = mock(UserId.class);
        node = mock(Node.class);


        updateNode = mock(UpdateNode.class);

    }

    @org.junit.Test
    public void testNodeHandling() throws Exception {
        Assert.assertNull(updateNode.NodeHandling(userId, node));

    }
}