package org.opendaylight.nemo.user.vnspacemanager.structurestyle.updateintent;
import org.opendaylight.nemo.user.vnspacemanager.structurestyle.updateintent.UpdateOperation;

import static org.mockito.Mockito.mock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.WriteTransaction;
import org.opendaylight.nemo.user.tenantmanager.TenantManage;
import org.opendaylight.nemo.user.vnspacemanager.instancecheck.OperationInstanceCheck;
import org.opendaylight.nemo.user.vnspacemanager.syntaxcheck.OperationDefinitionCheck;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.Users;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.Operations;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.operations.Operation;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.operations.OperationBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.operations.OperationKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.User;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.UserKey;
import org.opendaylight.controller.md.sal.binding.api.WriteTransaction;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.opendaylight.controller.md.sal.common.api.data.TransactionCommitFailedException;
import com.google.common.util.concurrent.CheckedFuture;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Created by ldzd11 on 2015/11/9.
 */
public class UpdateOperationTest {

    private UpdateOperation updateOperation;

    private DataBroker dataBroker;
    private TenantManage tenantManage;
    private OperationDefinitionCheck operationDefinitionCheck;
    private OperationInstanceCheck operationInstanceCheck;

    private UserId userId;
    private Operation operation;
    @org.junit.Before
    public void setUp() throws Exception {
        dataBroker = mock(DataBroker.class);
        tenantManage = mock(TenantManage.class);
        operationDefinitionCheck = mock(OperationDefinitionCheck.class);
        operationInstanceCheck = mock(OperationInstanceCheck.class);
        userId = mock(UserId.class);
        operation = mock(Operation.class);

        updateOperation = mock(UpdateOperation.class);

    }

    @org.junit.Test
    public void testOperationHandling() throws Exception {
        Assert.assertNull(updateOperation.OperationHandling(userId, operation));

    }
}