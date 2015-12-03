/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.user.tenantmanager;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.WriteTransaction;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.controller.md.sal.common.api.data.TransactionCommitFailedException;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserRoleName;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.RegisterUserInput;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.Users;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.User;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.UserBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.UserKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.user.rev151010.UserRoles;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.user.rev151010.user.roles.UserRole;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.CheckedFuture;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

/**
 * Created by z00293636 on 2015/8/29.
 */

/* maintain tenant information, including how many tenants are active, their information */
public class TenantManage {
    private static final Logger LOG = LoggerFactory.getLogger(TenantManage.class);
    private DataBroker dataBroker;
    private User user;

    public TenantManage(DataBroker dataBroker)
    {
        this.dataBroker = dataBroker;
    }

    private void setUser(User user)
    {
        this.user = user;
    }

    public User getUser()
    {
        return user;
    }

    /**
     *
     * @return null if an error was encountered, or an empty map if there was no
     *         error but no data was retrieved.
     */
    public Map<UserRoleName, UserRole> getUserRoles() {

        InstanceIdentifier<UserRoles> userRolesInsId = InstanceIdentifier.builder(UserRoles.class).build();
        ListenableFuture<Optional<UserRoles>> userRolesFuture = this.dataBroker.newReadOnlyTransaction().read(
                LogicalDatastoreType.CONFIGURATION, userRolesInsId);

        final Optional<UserRoles> userRolesOpt;
        try {
            // TODO: consider time out here?
            userRolesOpt = userRolesFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            LOG.error("Cannot read role information.", e);
            return null;
        }

        // TODO: change to Java 8 lambda expressions
        return userRolesOpt.transform(new Function<UserRoles, Map<UserRoleName, UserRole>>() {
            @Override
            public Map<UserRoleName, UserRole> apply(UserRoles input) {
                return Maps.uniqueIndex(input.getUserRole(), new Function<UserRole, UserRoleName>() {
                    @Override
                    public UserRoleName apply(UserRole role) {
                        return role.getRoleName();
                    }
                });
            }
        }).or(new HashMap<UserRoleName, UserRole>());
    }

    /**
    *
    * @return null if an error was encountered, or an empty map if there was no
    *         error but no data was retrieved.
    */
    public Map<UserId, User> getUsers() {
        InstanceIdentifier<Users> usersInsId = InstanceIdentifier.builder(Users.class).build();
        ListenableFuture<Optional<Users>> usersFuture = dataBroker.newReadOnlyTransaction().read(
                LogicalDatastoreType.CONFIGURATION, usersInsId);

        final Optional<Users> usersOpt;
        try {
            // TODO: consider time out here?
            usersOpt = usersFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            LOG.error("Cannot read user information.", e);
            return null;
        }

        // TODO: change to Java 8 lambda expressions
        return usersOpt.transform(new Function<Users, Map<UserId, User>>() {
            @Override
            public Map<UserId, User> apply(Users input) {
                return Maps.uniqueIndex(input.getUser(), new Function<User, UserId>() {
                    @Override
                    public UserId apply(User user) {
                        return user.getUserId();
                    }
                });
            }
        }).or(new HashMap<UserId, User>());
    }

    public void fetchVNSpace(UserId userId)
    {
        final Map<UserId, User> users = getUsers();
        setUser((users != null) ? users.get(userId) : null);
    }

    public void addUser(RegisterUserInput registerUserInput){
        WriteTransaction t = dataBroker.newWriteOnlyTransaction();
        if (registerUserInput.getUserId() != null)
        {
            User user = new UserBuilder(registerUserInput).build();
//            UserBuilder userBuilder = new UserBuilder();
//            userBuilder.setUserId(registerUserInput.getUserId());
//            userBuilder.setUserName(registerUserInput.getUserName());
//            userBuilder.setUserPassword(registerUserInput.getUserPassword());
//            userBuilder.setUserRole(registerUserInput.getUserRole());
//
//            User user = userBuilder.build();
            UserKey userKey = new UserKey(registerUserInput.getUserId());

            InstanceIdentifier<User> userid = InstanceIdentifier.builder(Users.class).child(User.class, userKey).build();

            t.put(LogicalDatastoreType.CONFIGURATION, userid, user,true);
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
}
