package org.opendaylight.nemo.user.vnspacemanager.instancecheck;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;

import org.opendaylight.nemo.user.tenantmanager.TenantManage;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Connection;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Node;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.User;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.connection.instance.EndNode;

import java.util.List;
/**
 * Created by wangjunfei on 2015/11/10.
 */
public class ConnectionInstanceCheckTest {
    private TenantManage tenantManage;
    private ConnectionInstanceCheck connectionInstanceCheck;
    private UserId userId;
    private Connection connection;

    @org.junit.Before
    public void setUp() throws Exception {
        userId = mock(UserId.class);
        connection = mock(Connection.class);
        tenantManage = mock(TenantManage.class);
        connectionInstanceCheck = mock(ConnectionInstanceCheck.class);
    }

    @org.junit.Test
    public void testCheckConnInstance() throws Exception {
        Assert.assertNull(connectionInstanceCheck.checkConnInstance(userId,connection));
    }
}