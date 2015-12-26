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
import org.opendaylight.nemo.user.vnspacemanager.structurestyle.deleteintent.DeleteFlow;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.FlowId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;

/**
 * Created by z00293636 on 2015/11/6.
 */
public class DeleteFlowLang {
    private TenantManage tenantManage;
    private DeleteFlow deleteFlow;

    public DeleteFlowLang(DataBroker dataBroker, TenantManage tenantManage){
        this.tenantManage = tenantManage;
        deleteFlow = new DeleteFlow(dataBroker, tenantManage);
    }

    public String DeleteFlowHandling(UserId userId, String flowname){
        if (tenantManage.getObjectId(userId,flowname)!=null){
            FlowId flowId = new FlowId(tenantManage.getObjectId(userId,flowname));
            return deleteFlow.DeleteFlowHandling(userId,flowId);
        }
        else {
            return "The flow " + flowname + " is not exist.";
        }
    }
}
