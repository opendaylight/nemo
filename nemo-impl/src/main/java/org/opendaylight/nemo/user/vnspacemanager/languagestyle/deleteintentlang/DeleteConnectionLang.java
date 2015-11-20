/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.user.vnspacemanager.languagestyle.deleteintentlang;

import org.opendaylight.nemo.user.tenantmanager.TenantManage;
import org.opendaylight.nemo.user.vnspacemanager.structurestyle.deleteintent.DeleteConnection;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Connection;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.User;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;

import java.util.List;

/**
 * Created by z00293636 on 2015/11/6.
 */
public class DeleteConnectionLang {
    private DataBroker dataBroker;
    private TenantManage tenantManage;
    private DeleteConnection deleteConnection;

    public DeleteConnectionLang(DataBroker dataBroker, TenantManage tenantManage)
    {
        this.dataBroker = dataBroker;
        this.tenantManage = tenantManage;
        deleteConnection = new DeleteConnection(dataBroker, tenantManage);
    }

    public String DeleteConnectionHandling(UserId userId, String connectionname)
    {
        String errorInfo = null;
        tenantManage.fetchVNSpace(userId);
        User user =tenantManage.getUser();
        if (user.getObjects()!=null)
        {
            if (!user.getObjects().getConnection().isEmpty())
            {
                List<Connection> connectionList = user.getObjects().getConnection();
                Boolean connExit = false;
                for (Connection connection1 : connectionList)
                {
                    if (connection1.getConnectionName().getValue().equals(connectionname))
                    {
                        connExit = true;
                        errorInfo = deleteConnection.DeleteConnectionHandling(userId,connection1.getConnectionId());
                    }
                }
                if (!connExit)
                {
                    errorInfo =  "The connection"+connectionname + "is not exist in this user vn space.";
                }
            }
            else
            {
                errorInfo =  "The connection"+connectionname + "is not exist in this user vn space.";
            }
        }
        else
        {
            errorInfo =  "The connection"+connectionname + "is not exist in this user vn space.";
        }
        return errorInfo;
    }
}
