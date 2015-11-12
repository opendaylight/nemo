package org.opendaylight.nemo.user.vnspacemanager.instancecheck;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;

import org.opendaylight.nemo.user.tenantmanager.TenantManage;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.Objects;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Node;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.User;

import java.util.List;

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