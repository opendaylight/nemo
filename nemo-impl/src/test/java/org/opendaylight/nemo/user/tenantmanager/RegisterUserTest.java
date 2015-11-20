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
import static org.junit.Assert.*;
import org.opendaylight.nemo.user.tenantmanager.RegisterUser;
import org.opendaylight.nemo.user.tenantmanager.TenantManage;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserRoleName;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.RegisterUserInput;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.User;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.user.rev151010.user.roles.UserRole;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.*;
/**
 * Created by zhangmeng on 2015/11/20.
 */
public class RegisterUserTest extends TestCase {
    private RegisterUser registerUser;
    private TenantManage tenantManage;
    private RegisterUserInput input;
    @Before
    public void setUp() throws Exception {
        tenantManage = mock(TenantManage.class);
        registerUser = new RegisterUser(tenantManage);

        input = mock(RegisterUserInput.class);
    }

    @Test
    public void testRegisterUser() throws Exception {

        //no data test
        doNothing().when(tenantManage).fetchUserRoles();
        when(tenantManage.getUserRoleList()).thenReturn(null);//return nothing
        doNothing().when(tenantManage).fetchUsers();
        when(tenantManage.getUsersList()).thenReturn(null);
        registerUser.registerUser(input);

        //data exists . and test other branch
        when(tenantManage.getUserRoleList()).thenReturn(new LinkedList<UserRole>());//return nothing
        when(tenantManage.getUsersList()).thenReturn(new LinkedList<User>());
        when(input.getUserRole()).thenReturn(mock(UserRoleName.class));
        registerUser.registerUser(input);

        verify(tenantManage,times(2)).getUserRoleList();
        verify(tenantManage,times(2)).getUsersList();
        Assert.assertNotNull(tenantManage);
        Assert.assertNotNull(registerUser);
    }
}