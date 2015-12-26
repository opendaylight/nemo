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
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.FlowId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.operations.Operation;

/**
 * Created by z00293636 on 2015/9/2.
 */
public class DeleteFlow {
    private TenantManage tenantManage;

    public DeleteFlow(DataBroker dataBroker, TenantManage tenantManage){
        this.tenantManage = tenantManage;
    }

    public String DeleteFlowHandling(UserId userId, FlowId flowId){
        Boolean flowExist = false;

        if (tenantManage.getFlow(userId)!=null){
            if (tenantManage.getFlow(userId).containsKey(flowId)){
                flowExist = true;
                tenantManage.getFlow(userId).remove(flowId);
                tenantManage.getUserNameIdMap(userId).remove(tenantManage.getName(userId,flowId.getValue()));
            }
        }
        if (tenantManage.getFlowDataStore(userId)!=null){
            if (tenantManage.getFlowDataStore(userId).containsKey(flowId)){
                flowExist = true;
                tenantManage.setUserDeleteIntent(userId, NEMOConstants.flow, flowId.getValue());
                tenantManage.getUserNameIdMap(userId).remove(tenantManage.getName(userId,flowId.getValue()));
            }
        }

        if (!flowExist){
            return "The flow instance " +flowId.getValue()+" is not exist.";
        }
        else {
            if (tenantManage.getOperation(userId)!=null){
                for (Operation operation : tenantManage.getOperation(userId).values()){
                    if (operation.getTargetObject().getValue().equals(flowId.getValue())){
                        tenantManage.getOperation(userId).remove(operation.getOperationId());
                        tenantManage.getUserNameIdMap(userId).remove(tenantManage.getName(userId,operation.getOperationId().getValue()));
                    }
                }
            }
            if (tenantManage.getOperationDataStore(userId)!=null){
                for (Operation operation : tenantManage.getOperationDataStore(userId).values()){
                    if (operation.getTargetObject().getValue().equals(flowId.getValue())){
                        tenantManage.setUserDeleteIntent(userId, NEMOConstants.operation, operation.getOperationId().getValue());
                        tenantManage.getUserNameIdMap(userId).remove(tenantManage.getName(userId,operation.getOperationId().getValue()));
                    }
                }
            }
        }
        return null;
    }
}
