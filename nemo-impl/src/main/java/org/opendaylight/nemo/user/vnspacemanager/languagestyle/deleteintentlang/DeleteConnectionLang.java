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
import org.opendaylight.nemo.user.vnspacemanager.structurestyle.deleteintent.DeleteConnection;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.ConnectionId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;

/**
 * Created by z00293636 on 2015/11/6.
 */
public class DeleteConnectionLang {
    private TenantManage tenantManage;
    private DeleteConnection deleteConnection;

    public DeleteConnectionLang(DataBroker dataBroker, TenantManage tenantManage){
        this.tenantManage = tenantManage;
        deleteConnection = new DeleteConnection(dataBroker, tenantManage);
    }

    public String DeleteConnectionHandling(UserId userId, String connectionname){
        if (tenantManage.getObjectId(userId,connectionname)!=null){
            ConnectionId connectionId = new ConnectionId(tenantManage.getObjectId(userId,connectionname));
            return deleteConnection.DeleteConnectionHandling(userId,connectionId);
        }
        else {
            return "The connection " + connectionname + " is not exist.";
        }
    }
}
