/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.user.vnspacemanager.structurestyle.deleteintent;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.nemo.user.tenantmanager.TenantManage;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.OperationId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.Users;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.Operations;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.operations.Operation;
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

import java.util.List;

/**
 * Created by z00293636 on 2015/9/2.
 */
public class DeleteOperation {

    private DataBroker dataBroker;
    private TenantManage tenantManage;
    private static final Logger LOG = LoggerFactory.getLogger(DeleteOperation.class);

    public DeleteOperation(DataBroker dataBroker, TenantManage tenantManage)
    {
        this.dataBroker = dataBroker;
        this.tenantManage = tenantManage;
    }

    public String DeleteOperationhandling(UserId userId, OperationId operationId)
    {
        Boolean OperationExist = false;
        String errorInfo = null;
        tenantManage.fetchVNSpace(userId);

        User user = tenantManage.getUser();

        if (user != null)
        {
            if (user.getOperations() != null)
            {
                if (user.getOperations().getOperation() != null)
                {
                    List<Operation> operationList = tenantManage.getUser().getOperations().getOperation();

                    for (Operation operation : operationList)
                    {
                        if (operation.getOperationId().equals(operationId))
                        {
                            OperationExist = true;
                            break;
                        }
                    }

                    if (OperationExist)
                    {
                        DeleteOperationInstance(userId,operationId);
                    }
                    else
                    {
                        errorInfo = "The operation instance" +operationId.toString()+"is not exist. Could not be deleted.";
                    }
                }
                else
                {
                    errorInfo = "There are no operation instances in the data store.";
                }
            }
        }
        else
        {
            errorInfo = "There are no user in the data store.";
        }
        return errorInfo;
    }

    private void DeleteOperationInstance(UserId userId, OperationId operationId)
    {
        WriteTransaction t = dataBroker.newWriteOnlyTransaction();
        UserKey userKey = new UserKey(userId);
        OperationKey operationKey = new OperationKey(operationId);

        InstanceIdentifier<Operation> operationid = InstanceIdentifier.builder(Users.class).child(User.class, userKey).child(Operations.class).child(Operation.class,operationKey).build();
        t.delete(LogicalDatastoreType.CONFIGURATION, operationid);
        CheckedFuture<Void, TransactionCommitFailedException> f = t.submit();
        Futures.addCallback(f, new FutureCallback<Void>() {
            @Override
            public void onFailure(Throwable t) {
                LOG.error("Could not write endpoint base container", t);
            }

            @Override
            public void onSuccess(Void result) {
            }
        });
    }
}
