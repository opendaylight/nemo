/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.user.vnspacemanager.languagestyle.deleteintentlang;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.nemo.user.tenantmanager.TenantManage;
import org.opendaylight.nemo.user.vnspacemanager.structurestyle.deleteintent.DeleteOperation;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.OperationId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;

/**
 * Created by z00293636 on 2015/11/6.
 */
public class DeleteOperationLang {
    private TenantManage tenantManage;
    private DeleteOperation deleteOperation;

    public DeleteOperationLang(DataBroker dataBroker, TenantManage tenantManage){
        this.tenantManage = tenantManage;
        deleteOperation = new DeleteOperation(dataBroker,tenantManage);
    }

    public String DeleteOperationHandling(UserId userId, String operationname){
        if (tenantManage.getObjectId(userId,operationname)!=null){
            OperationId operationId = new OperationId(tenantManage.getObjectId(userId,operationname));
            return deleteOperation.DeleteOperationhandling(userId,operationId);
        }
        else {
            return "The operation " + operationname + " is not exist.";
        }
    }

}
