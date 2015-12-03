/*

* Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.

*

* This program and the accompanying materials are made available under the

* terms of the Eclipse Public License v1.0 which accompanies this distribution,

* and is available at http://www.eclipse.org/legal/epl-v10.html

*/
package org.opendaylight.nemo.user.tenantmanager;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.ReadOnlyTransaction;
import org.opendaylight.controller.md.sal.binding.api.WriteTransaction;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserRoleName;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.RegisterUserInput;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.User;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.user.rev151010.user.roles.UserRole;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;

import com.google.common.base.Optional;
import com.google.common.util.concurrent.CheckedFuture;
/**
 * Created by zhangmeng on 2015/11/20.
 */
public class TenantManageTest extends TestCase {
    private TenantManage tenantManage;
    private DataBroker dataBroker;
    private Map<UserRoleName, UserRole> userRoles;
    private Map<UserId, User> users ;
    private User user;
    @Override
    @Before
    public void setUp() throws Exception {
        userRoles = new HashMap<UserRoleName, UserRole>();
        users = new HashMap<UserId, User>();
        user = mock(User.class);

        dataBroker = mock(DataBroker.class);
        tenantManage = new TenantManage(dataBroker);
    }

    @Test
    public void testGetUser() throws Exception {
        user = tenantManage.getUser();
        Assert.assertNotEquals(mock(User.class),user);
    }

    @Test
    public void testGetUserRoles() throws Exception {
        //ListenableFuture<Optional<UserRoles>> userRolesFuture = mock(ListenableFuture.class);
        CheckedFuture userRolesFuture = mock(CheckedFuture.class);
        ReadOnlyTransaction readOnlyTransaction = mock(ReadOnlyTransaction.class);
//      when(dataBroker.newReadOnlyTransaction().read(any(LogicalDatastoreType.class),
//      any(InstanceIdentifier.class))).thenReturn(userRolesFuture);
        when(dataBroker.newReadOnlyTransaction()).thenReturn(readOnlyTransaction);
        when(readOnlyTransaction.read(any(LogicalDatastoreType.class), any(InstanceIdentifier.class))).thenReturn(userRolesFuture);
        when(userRolesFuture.get()).thenReturn(Optional.absent());
        tenantManage.getUserRoles();
        verify(dataBroker).newReadOnlyTransaction();
        verify(readOnlyTransaction).read(any(LogicalDatastoreType.class), any(InstanceIdentifier.class));
        Assert.assertNotNull(tenantManage);
    }

    @Test
    public void testGetUsers() throws Exception {
        CheckedFuture usersFuture = mock(CheckedFuture.class);
        ReadOnlyTransaction readOnlyTransaction = mock(ReadOnlyTransaction.class);

        when(dataBroker.newReadOnlyTransaction()).thenReturn(readOnlyTransaction);
        when(readOnlyTransaction.read(any(LogicalDatastoreType.class), any(InstanceIdentifier.class))).thenReturn(usersFuture);
        when(usersFuture.get()).thenReturn(Optional.absent());
        tenantManage.getUsers();
        verify(dataBroker).newReadOnlyTransaction();
        verify(readOnlyTransaction).read(any(LogicalDatastoreType.class), any(InstanceIdentifier.class));
        Assert.assertNotNull(tenantManage);
    }

    @Test
    public void testFetchVNSpace() throws Exception {
        CheckedFuture usersFuture = mock(CheckedFuture.class);
        ReadOnlyTransaction readOnlyTransaction = mock(ReadOnlyTransaction.class);

        when(dataBroker.newReadOnlyTransaction()).thenReturn(readOnlyTransaction);
        when(readOnlyTransaction.read(any(LogicalDatastoreType.class), any(InstanceIdentifier.class))).thenReturn(usersFuture);
        when(usersFuture.get()).thenReturn(Optional.absent());
        tenantManage.fetchVNSpace(mock(UserId.class));
        verify(dataBroker).newReadOnlyTransaction();
        verify(readOnlyTransaction).read(any(LogicalDatastoreType.class), any(InstanceIdentifier.class));
        Assert.assertNotNull(tenantManage);
    }

    @Test // As a parameter registerUserInput has no data , so the test case just test a small part
    public void testAddUser() throws Exception {
        RegisterUserInput registerUserInput = mock(RegisterUserInput.class);
        WriteTransaction writeTransaction = mock(WriteTransaction.class);

        when(dataBroker.newWriteOnlyTransaction()).thenReturn(writeTransaction);
        when(registerUserInput.getUserId()).thenReturn(null);
        tenantManage.addUser(registerUserInput);

        CheckedFuture f = mock(CheckedFuture.class);
        when(registerUserInput.getUserId()).thenReturn(mock(UserId.class));
        when(writeTransaction.submit()).thenReturn(f);
        tenantManage.addUser(registerUserInput);

        verify(dataBroker,times(2)).newWriteOnlyTransaction();
        verify(registerUserInput,times(4)).getUserId();
        Assert.assertNotNull(tenantManage);
    }
}