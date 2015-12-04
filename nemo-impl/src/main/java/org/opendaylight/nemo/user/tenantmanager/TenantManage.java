/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.user.tenantmanager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

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

import com.google.common.base.Optional;
import com.google.common.util.concurrent.CheckedFuture;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;

/**
 * Created by z00293636 on 2015/8/29.
 */

/* maintain tenant information, including how many tenants are active, their information */
public class TenantManage {
    private static final Logger LOG = LoggerFactory.getLogger(TenantManage.class);
    private DataBroker dataBroker;
    private final SettableFuture<List<UserRole>> userRoleListFuture = SettableFuture.create();
    private final SettableFuture<List<User>> usersListFuture = SettableFuture.create();
    private User user;

    public TenantManage(DataBroker dataBroker)
    {
        this.dataBroker = dataBroker;
    }

    private void setUserRoleList(List<UserRole> userRoleList)
    {
        this.userRoleListFuture.set(userRoleList);
    }

    private void setUserList(List<User> userList)
    {
        this.usersListFuture.set(userList);
    }

    private void setUser(User user)
    {
        this.user = user;
    }

    public List<UserRole> getUserRoleList() {
        try {
            return userRoleListFuture.get(1, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            LOG.error("Cannot read role information.", e);
            return null;
        }
    }

    /**
     *
     * @return Map from UserRoleName to UserRole.  If no roles exist, an empty (not-null) map is returned.
     */
    public Map<UserRoleName, UserRole> getUserRoles() {
        final Map<UserRoleName, UserRole> map = new HashMap<>();
        final List<UserRole> userRoleList = getUserRoleList();
        if (userRoleList != null) {
            for (UserRole role : userRoleList) {
                map.put(role.getRoleName(), role);
            }
        }
        return map;
    }

    public List<User> getUsersList() {
        try {
            return usersListFuture.get(1, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            LOG.error("Cannot read user information.", e);
            return null;
        }
    }

    /**
     *
     * @return Map from UserId to User.  If no users exist, an empty (not-null) map is returned.
     */
    public Map<UserId, User> getUsers()
    {
        final Map<UserId, User> map = new HashMap<>();
        final List<User> userList = getUsersList();
        if (userList != null) {
            for (User user : userList) {
                map.put(user.getUserId(), user);
            }
        }
        return map;
    }

    public User getUser()
    {
        return user;
    }

    public void fetchUserRoles(){

        InstanceIdentifier<UserRoles> userRolesInsId = InstanceIdentifier.builder(UserRoles.class).build();
        ListenableFuture<Optional<UserRoles>> userRolesFuture = this.dataBroker.newReadOnlyTransaction().read(LogicalDatastoreType.CONFIGURATION, userRolesInsId);
        Futures.addCallback(userRolesFuture, new FutureCallback<Optional<UserRoles>>() {
            @Override
            public void onSuccess(Optional<UserRoles> result)
            {
                setUserRoleList(result.get().getUserRole());
            }

            @Override
            public void onFailure(Throwable t)
            {
                LOG.error("Can not read role information.", t);
            }
        });
    }

    public void fetchUsers(){
        InstanceIdentifier<Users> usersInsId = InstanceIdentifier.builder(Users.class).build();
        ListenableFuture<Optional<Users>> usersFuture = dataBroker.newReadOnlyTransaction().read(LogicalDatastoreType.CONFIGURATION, usersInsId);
        Futures.addCallback(usersFuture, new FutureCallback<Optional<Users>>() {
            @Override
            public void onSuccess(Optional<Users> result)
            {
                setUserList(result.get().getUser());
            }

            @Override
            public void onFailure(Throwable t)
            {
                LOG.error("Can not read users information.", t);
            }
        });
    }

    public void fetchVNSpace(UserId userId)
    {
        fetchUsers();
        final Map<UserId, User> users = getUsers();

        User user = users.get(userId);
        if (users.containsKey(userId) && user != null) {
            setUser(user);
        }
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
