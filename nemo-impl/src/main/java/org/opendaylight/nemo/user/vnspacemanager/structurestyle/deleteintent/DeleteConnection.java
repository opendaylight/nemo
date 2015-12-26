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
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.ConnectionId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.operations.Operation;

/**
 * Created by z00293636 on 2015/9/2.
 */
public class DeleteConnection {
    private TenantManage tenantManage;

    public DeleteConnection(DataBroker dataBroker, TenantManage tenantManage){
        this.tenantManage = tenantManage;
    }

    public String DeleteConnectionHandling(UserId userId, ConnectionId connectionId){
         Boolean connectionExist = false;

        if (tenantManage.getConnection(userId)!=null){
            if (tenantManage.getConnection(userId).containsKey(connectionId)){
                connectionExist = true;
                tenantManage.getConnection(userId).remove(connectionId);
                tenantManage.getUserNameIdMap(userId).remove(tenantManage.getName(userId,connectionId.getValue()));
            }
        }
        if (tenantManage.getConnectionDataStore(userId)!=null){
            if (tenantManage.getConnectionDataStore(userId).containsKey(connectionId)){
                connectionExist = true;
                tenantManage.setUserDeleteIntent(userId, NEMOConstants.connection,connectionId.getValue());
                tenantManage.getUserNameIdMap(userId).remove(tenantManage.getName(userId,connectionId.getValue()));
            }
        }

        if (!connectionExist){
            return "The connection instance "+connectionId.getValue()+ " is not exit.";
        }
        else {
            if (tenantManage.getOperation(userId)!=null){
                for (Operation operation : tenantManage.getOperation(userId).values()){
                    if (operation.getTargetObject().getValue().equals(connectionId.getValue())){
                        tenantManage.getOperation(userId).remove(operation.getOperationId());
                        tenantManage.getUserNameIdMap(userId).remove(tenantManage.getName(userId,operation.getOperationId().getValue()));
                    }
                }
            }
            if (tenantManage.getOperationDataStore(userId)!=null){
                for (Operation operation : tenantManage.getOperationDataStore(userId).values()){
                    if (operation.getTargetObject().getValue().equals(connectionId.getValue())){
                        tenantManage.setUserDeleteIntent(userId, NEMOConstants.operation, operation.getOperationId().getValue());
                        tenantManage.getUserNameIdMap(userId).remove(tenantManage.getName(userId,operation.getOperationId().getValue()));
                    }
                }
            }
        }
        return null;
    }

}
