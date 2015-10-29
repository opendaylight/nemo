/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.user.tenantmanager;

import com.google.common.base.Optional;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.CheckedFuture;
import com.google.common.util.concurrent.ListenableFuture;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.WriteTransaction;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.controller.md.sal.common.api.data.TransactionCommitFailedException;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
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

import java.util.List;


/**
 * Created by z00293636 on 2015/8/29.
 */

/* maintain tenant information, including how many tenants are active, their information */
public class TenantManage {
    private static final Logger LOG = LoggerFactory.getLogger(TenantManage.class);
    private DataBroker dataBroker;
    private List<UserRole> userRoleList;
    private List<User> usersList ;
    private User user;

    public TenantManage(DataBroker dataBroker)
    {
        this.dataBroker = dataBroker;
    }

    private void setUserRoleList(List<UserRole> userRoleList)
    {
        this.userRoleList = userRoleList;
    }

    private void setUserList(List<User> userList)
    {
        this.usersList = userList;
    }

    private void setUser(User user)
    {
        this.user = user;
    }

    public List<UserRole> getUserRoleList()
    {
        return userRoleList;
    }

    public List<User> getUsersList()
    {
        return usersList;
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
                return;
            }

            @Override
            public void onFailure(Throwable t)
            {
                LOG.error("Can not read role information.", t);

                return;
            }
        });
         return;
    }

    public void fetchUsers(){
        InstanceIdentifier<Users> usersInsId = InstanceIdentifier.builder(Users.class).build();
        ListenableFuture<Optional<Users>> usersFuture = dataBroker.newReadOnlyTransaction().read(LogicalDatastoreType.CONFIGURATION, usersInsId);
        Futures.addCallback(usersFuture, new FutureCallback<Optional<Users>>() {
            @Override
            public void onSuccess(Optional<Users> result)
            {
                setUserList(result.get().getUser());
                return;
            }

            @Override
            public void onFailure(Throwable t)
            {
                LOG.error("Can not read users information.", t);

                return;
            }
        });
        return;
    }

    public void fetchVNSpace(UserId userId)
    {
        fetchUsers();
        if (getUsersList() != null)
        {
            for (User user : getUsersList())
            {
                if (user.getUserId().equals(userId))
                {
                    setUser(user);
                    break;
                }
            }
        }
        return;
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
