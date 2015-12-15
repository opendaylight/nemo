/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package user.tenantmanager;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.WriteTransaction;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.controller.md.sal.common.api.data.TransactionCommitFailedException;
import org.opendaylight.nemo.user.tenantmanager.TenantManage;
import org.opendaylight.controller.md.sal.binding.api.ReadOnlyTransaction;
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
import static org.mockito.Mockito.*;
/**
 * Created by zhangmeng on 2015/12/15.
 */
public class TenantManageTest extends TestCase {
    private DataBroker dataBroker;
    private TenantManage tenantManage;
    @Before
    public void setUp() throws Exception {
        dataBroker = mock(DataBroker.class);

        tenantManage = new TenantManage(dataBroker);
    }

    @Test
    public void testGetUser() throws Exception {
        User result;
        result = tenantManage.getUser();
        Assert.assertTrue(result == null);
    }

    @Test
    public void testGetUserRoles() throws Exception {
        Map<UserRoleName, UserRole> result;
        UserRoles userRoles = mock(UserRoles.class);
        ReadOnlyTransaction readOnlyTransaction = mock(ReadOnlyTransaction.class);
        CheckedFuture checkedFuture = mock(CheckedFuture.class);
        Optional<UserRoles> optional = Optional.of(userRoles);;
        Assert.assertTrue(optional.isPresent());


        when(dataBroker.newReadOnlyTransaction()).thenReturn(readOnlyTransaction);
        when(readOnlyTransaction.read(any(LogicalDatastoreType.class), any(InstanceIdentifier.class))).thenReturn(checkedFuture);
        when(checkedFuture.get()).thenReturn(optional);
//        when(userRoles.getUserRole()).thenReturn(new UserRoleName("admin"));

        result = tenantManage.getUserRoles();
        Assert.assertTrue(result.isEmpty());
        verify(userRoles).getUserRole();
    }

    @Test
    public void testGetUsers_FetchVNSpace() throws Exception {
        Map<UserId, User> result;
        Users users = mock(Users.class);
        User user = mock(User.class);
        UserId userId = mock(UserId.class);
        ReadOnlyTransaction readOnlyTransaction = mock(ReadOnlyTransaction.class);
        CheckedFuture checkedFuture = mock(CheckedFuture.class);
        Optional<Users> usersOpt = Optional.of(users);
        List<User> userList = new ArrayList<User>();

        userList.add(user);

        when(dataBroker.newReadOnlyTransaction()).thenReturn(readOnlyTransaction);
        when(readOnlyTransaction.read(any(LogicalDatastoreType.class), any(InstanceIdentifier.class))).thenReturn(checkedFuture);
        when(checkedFuture.get()).thenReturn(usersOpt);
        when(users.getUser()).thenReturn(userList);
        when(user.getUserId()).thenReturn(userId);

        result = tenantManage.getUsers();  //hashmap with userid user
        Assert.assertTrue(!result.isEmpty() && result.size() == 1);
        verify(users).getUser();
        verify(user).getUserId();

        tenantManage.fetchVNSpace(userId);
        Assert.assertTrue(tenantManage.getUser() != null);
    }

    @Test
    public void testAddUser() throws Exception {
        RegisterUserInput registerUserInput = mock(RegisterUserInput.class);
        WriteTransaction writeTransaction = mock(WriteTransaction.class);
        CheckedFuture checkedFuture = mock(CheckedFuture.class);
        UserId userId = mock(UserId.class);

        when(dataBroker.newWriteOnlyTransaction()).thenReturn(writeTransaction);
        when(writeTransaction.submit()).thenReturn(checkedFuture);
        when(registerUserInput.getUserId())
                .thenReturn(null)
                .thenReturn(userId);

        tenantManage.addUser(registerUserInput);
        verify(registerUserInput).getUserId();

        tenantManage.addUser(registerUserInput);
        verify(registerUserInput,times(4)).getUserId();

    }
}