/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.user.vnspacemanager.structurestyle.updateintent;

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
 * Created by z00293636 on 2015/8/31.
 */
public class UpdateOperation {

    private DataBroker dataBroker;
    private TenantManage tenantManage;
    private OperationDefinitionCheck operationDefinitionCheck;
    private OperationInstanceCheck operationInstanceCheck;
    private static final Logger LOG = LoggerFactory.getLogger(UpdateOperation.class);

    public UpdateOperation(DataBroker dataBroker, TenantManage tenantManage)
    {
        this.dataBroker = dataBroker;
        this.tenantManage = tenantManage;
        operationDefinitionCheck = new OperationDefinitionCheck(dataBroker);
        operationInstanceCheck = new OperationInstanceCheck(tenantManage);
    }

    public String OperationHandling(UserId userId, Operation operation)
    {
        String errorDefinition = operationDefinitionCheck.CheckDefinition(operation);
        String errorInstance = operationInstanceCheck.checkOperationInstance(userId, operation);

        if (errorDefinition != null)
        {
            return errorDefinition;
        }
        if (errorInstance != null)
        {
            return errorInstance;
        }
        else
        {
            WriteTransaction t = dataBroker.newWriteOnlyTransaction();
            if (userId != null && operation.getOperationId() != null)
            {
//                OperationBuilder operationBuilder = new OperationBuilder();
//                operationBuilder.setOperationId(operation.getOperationId());
//                operationBuilder.setOperationName(operation.getOperationName());
//                operationBuilder.setConditionSegment(operation.getConditionSegment());
//                operationBuilder.setTargetObject(operationBuilder.getTargetObject());
//                operationBuilder.setAction(operation.getAction());
//                operationBuilder.setPriority(operation.getPriority());
//
//                Operation operation1 = operationBuilder.build();
                Operation operation1 = new OperationBuilder(operation).build();
                OperationKey operationKey = new OperationKey(operation.getOperationId());

                UserKey userKey = new UserKey(userId);

                InstanceIdentifier<Operation> operationid = InstanceIdentifier.builder(Users.class).child(User.class, userKey).child(Operations.class).child(Operation.class,operationKey).build();
                t.put(LogicalDatastoreType.CONFIGURATION, operationid, operation1,true);
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
        return null;
    }
}
