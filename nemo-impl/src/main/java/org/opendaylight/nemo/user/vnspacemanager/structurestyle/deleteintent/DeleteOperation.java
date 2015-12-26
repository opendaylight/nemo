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
import org.opendaylight.nemo.user.vnspacemanager.languagestyle.NEMOConstants;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.OperationId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;

/**
 * Created by z00293636 on 2015/9/2.
 */
public class DeleteOperation {
    private TenantManage tenantManage;

    public DeleteOperation(DataBroker dataBroker, TenantManage tenantManage){
        this.tenantManage = tenantManage;
    }

    public String DeleteOperationhandling(UserId userId, OperationId operationId){
        Boolean operationExist = false;

        if (tenantManage.getOperation(userId)!=null){
            if (tenantManage.getOperation(userId).containsKey(operationId)){
                operationExist = true;
                tenantManage.getOperation(userId).remove(operationId);
                tenantManage.getUserNameIdMap(userId).remove(tenantManage.getName(userId,operationId.getValue()));
            }
        }
        if (tenantManage.getOperationDataStore(userId)!=null){
            if (tenantManage.getOperationDataStore(userId).containsKey(operationId)){
                operationExist = true;
                tenantManage.setUserDeleteIntent(userId, NEMOConstants.operation, operationId.getValue());
                tenantManage.getUserNameIdMap(userId).remove(tenantManage.getName(userId,operationId.getValue()));
            }
        }
        if (!operationExist){
            return "The operation instance " +operationId.getValue()+" is not exist.";
        }
        return null;
    }
}
