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
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.ConnectionId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.Users;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.Objects;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Connection;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.ConnectionKey;
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
public class DeleteConnection {

    private DataBroker dataBroker;
    private TenantManage tenantManage;
    private static final Logger LOG = LoggerFactory.getLogger(DeleteConnection.class);

    public DeleteConnection(DataBroker dataBroker, TenantManage tenantManage){
        this.dataBroker = dataBroker;
        this.tenantManage = tenantManage;
    }

    public String DeleteConnectionHandling(UserId userId, ConnectionId connectionId){
        Boolean ConnInstanceExist = false;
        String errorInfo = null;
        tenantManage.fetchVNSpace(userId);
        User user = tenantManage.getUser();

        if (user != null){
            if (user.getObjects() != null){
                if (user.getObjects().getConnection() != null){
                    List<Connection> connectionList = tenantManage.getUser().getObjects().getConnection();

                    for (Connection connection : connectionList){
                        if (connection.getConnectionId().equals(connectionId)){
                            ConnInstanceExist = true;
                            break;
                        }
                    }
                    if (ConnInstanceExist){
                        DeleteConnInstance(userId, connectionId);
                    }
                    else{
                        errorInfo = "The connection instance "+connectionId.getValue()+ " is not exit. Could not be deleted.";
                    }
                }
                else{
                    errorInfo = "There are no connection instances in data store.";
                }
            }
        }
        else{
            errorInfo = "There are no connection instances in data store.";
        }

        return errorInfo;
    }

    private void DeleteConnInstance(UserId userId, ConnectionId connectionId){
        WriteTransaction t = dataBroker.newWriteOnlyTransaction();
        UserKey userKey = new UserKey(userId);
        ConnectionKey connectionKey = new ConnectionKey(connectionId);

        InstanceIdentifier<Connection> connectionid = InstanceIdentifier.builder(Users.class).child(User.class, userKey).child(Objects.class).child(Connection.class,connectionKey).build();
        t.delete(LogicalDatastoreType.CONFIGURATION, connectionid);
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
