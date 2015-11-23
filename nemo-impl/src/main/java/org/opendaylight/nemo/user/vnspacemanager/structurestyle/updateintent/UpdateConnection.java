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
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.controller.md.sal.common.api.data.TransactionCommitFailedException;
import org.opendaylight.nemo.user.tenantmanager.TenantManage;
import org.opendaylight.nemo.user.vnspacemanager.instancecheck.ConnectionInstanceCheck;
import org.opendaylight.nemo.user.vnspacemanager.syntaxcheck.ConnectionDefinitionCheck;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.Users;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.Objects;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Connection;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.ConnectionBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.ConnectionKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.User;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.UserKey;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.util.concurrent.CheckedFuture;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;

/**
 * Created by z00293636 on 2015/8/31.
 */
public class UpdateConnection {

    private DataBroker dataBroker;
    private TenantManage tenantManage;
    private ConnectionDefinitionCheck connectionDefinitionCheck;
    private ConnectionInstanceCheck connectionInstanceCheck;
    private static final Logger LOG = LoggerFactory.getLogger(UpdateConnection.class);

    public UpdateConnection(DataBroker dataBroker, TenantManage tenantManage)
    {
        this.dataBroker = dataBroker;
        this.tenantManage = tenantManage;
        connectionDefinitionCheck = new ConnectionDefinitionCheck(dataBroker);
        connectionInstanceCheck = new ConnectionInstanceCheck(tenantManage);
    }

    public String ConnectionHandling(UserId userId, Connection connection)
    {
        String errorDefinition = connectionDefinitionCheck.CheckConnectionDefinition(connection);
        String errorInstance = connectionInstanceCheck.checkConnInstance(userId,connection);
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
            if (userId != null && connection != null)
            {
//                ConnectionBuilder connectionBuilder = new ConnectionBuilder();
//                connectionBuilder.setConnectionId(connection.getConnectionId());
//                connectionBuilder.setConnectionName(connection.getConnectionName());
//                connectionBuilder.setConnectionType(connection.getConnectionType());
//                connectionBuilder.setEndNode(connection.getEndNode());
//                connectionBuilder.setProperty(connection.getProperty());
//
//                Connection connection1 = connectionBuilder.build();

                Connection connection1 = new ConnectionBuilder(connection).build();
                ConnectionKey connectionKey = new ConnectionKey(connection.getConnectionId());

                UserKey userKey = new UserKey(userId);

                InstanceIdentifier<Connection> connectionid = InstanceIdentifier.builder(Users.class).child(User.class, userKey).child(Objects.class).child(Connection.class,connectionKey).build();
                t.put(LogicalDatastoreType.CONFIGURATION, connectionid, connection1,true);
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
