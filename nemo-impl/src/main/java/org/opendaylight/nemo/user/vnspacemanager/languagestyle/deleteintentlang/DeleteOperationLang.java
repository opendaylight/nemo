/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.user.vnspacemanager.languagestyle.deleteintentlang;

import org.opendaylight.nemo.user.tenantmanager.TenantManage;
import org.opendaylight.nemo.user.vnspacemanager.structurestyle.deleteintent.DeleteOperation;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.operations.Operation;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.User;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;

import java.util.List;

/**
 * Created by z00293636 on 2015/11/6.
 */
public class DeleteOperationLang {
    private DataBroker dataBroker;
    private TenantManage tenantManage;
    private DeleteOperation deleteOperation;

    public DeleteOperationLang(DataBroker dataBroker, TenantManage tenantManage)
    {
        this.dataBroker = dataBroker;
        this.tenantManage = tenantManage;
        deleteOperation = new DeleteOperation(dataBroker,tenantManage);
    }

    public String DeleteOperationHandling(UserId userId, String operationname)
    {
        String errorInfo = null;
        tenantManage.fetchVNSpace(userId);
        User user = tenantManage.getUser();
        if (user.getOperations()!=null)
        {
            if (!user.getOperations().getOperation().isEmpty())
            {
                List<Operation> operationList = user.getOperations().getOperation();
                Boolean operationExist = false;
                for (Operation operation : operationList)
                {
                    if (operation.getOperationName().getValue().equals(operationname))
                    {
                        operationExist = true;
                        errorInfo = deleteOperation.DeleteOperationhandling(userId,operation.getOperationId());
                    }
                }
                if (!operationExist)
                {
                    errorInfo = "The operation"+operationname + "is not exist in this user vn space.";
                }
            }
            else
            {
                errorInfo = "The operation"+operationname + "is not exist in this user vn space.";
            }
        }
        else
        {
            errorInfo = "The operation"+operationname + "is not exist in this user vn space.";
        }
        return errorInfo;
    }
}
