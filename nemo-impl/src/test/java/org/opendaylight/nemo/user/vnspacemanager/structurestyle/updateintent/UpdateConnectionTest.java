package org.opendaylight.nemo.user.vnspacemanager.structurestyle.updateintent;

import org.opendaylight.nemo.user.vnspacemanager.structurestyle.updateintent.UpdateConnection;
import org.opendaylight.nemo.user.tenantmanager.TenantManage;
import org.opendaylight.nemo.user.vnspacemanager.instancecheck.ConnectionInstanceCheck;
import org.opendaylight.nemo.user.vnspacemanager.syntaxcheck.ConnectionDefinitionCheck;

import static org.mockito.Mockito.mock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.nemo.user.tenantmanager.TenantManage;
import org.opendaylight.nemo.user.vnspacemanager.instancecheck.ConnectionInstanceCheck;
import org.opendaylight.nemo.user.vnspacemanager.syntaxcheck.ConnectionDefinitionCheck;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.Users;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.Objects;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Connection;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.ConnectionBuilder;
import org.opendaylight.controller.md.sal.binding.api.WriteTransaction;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.ConnectionKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.User;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.UserKey;
import org.opendaylight.controller.md.sal.binding.api.WriteTransaction;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.opendaylight.controller.md.sal.common.api.data.TransactionCommitFailedException;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.CheckedFuture;
import com.google.common.util.concurrent.FutureCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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