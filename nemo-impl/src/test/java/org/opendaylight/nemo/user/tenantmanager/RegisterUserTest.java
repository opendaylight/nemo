/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.user.tenantmanager;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import org.opendaylight.nemo.user.tenantmanager.RegisterUser;
import org.opendaylight.nemo.user.tenantmanager.TenantManage;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserRoleName;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.RegisterUserInput;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.User;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.user.rev151010.user.roles.UserRole;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
/**
 * Created by zhangmeng on 2015/12/15.
 */
public class RegisterUserTest extends TestCase {
    private TenantManage tenantManage;
    private RegisterUser registerUser;
    @Override
    @Before
    public void setUp() throws Exception {
        tenantManage = mock(TenantManage.class);

        registerUser = new RegisterUser(tenantManage);
    }

    @Test
    public void testRegisterUser() throws Exception {
        RegisterUserInput input = mock(RegisterUserInput.class);
        UserRoleName userRoleName = mock(UserRoleName.class);
        UserRole userRole = mock(UserRole.class);
        UserId userId = mock(UserId.class);
        User user = mock(User.class);
        Map<UserRoleName, UserRole> userRoles = new HashMap<UserRoleName, UserRole>();
        Map<UserId, User> users = new HashMap<UserId, User>();

        when(tenantManage.getUsers()).thenReturn(users);
        when(tenantManage.getUserRoles()).thenReturn(userRoles);

        //test if
        Assert.assertTrue(userRoles.isEmpty());
        Assert.assertTrue(registerUser.registerUser(input).equals("There are no roles be defined."));

        //test else
        userRoles.put(userRoleName, userRole);
        when(input.getUserRole())
                .thenReturn(new UserRoleName("admin"))
                .thenReturn(userRoleName);
        Assert.assertTrue(registerUser.registerUser(input).equals("The role is not defined in the data store."));
        verify(input).getUserRole();
        Assert.assertTrue(!userRoles.containsKey(new UserRoleName("admin")));

        users.put(userId,user);
        when(input.getUserId())
                .thenReturn(userId)
                .thenReturn(new UserId("00001111-0000-0000-0000-000011112222"));

        Assert.assertTrue(registerUser.registerUser(input).equals("The user has been registered."));

        doNothing().when(tenantManage).addUser(any(RegisterUserInput.class));
        Assert.assertTrue(registerUser.registerUser(input) == null);
        verify(tenantManage).addUser(any(RegisterUserInput.class));




    }
}