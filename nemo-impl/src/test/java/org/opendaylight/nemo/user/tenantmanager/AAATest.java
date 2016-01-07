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
import org.opendaylight.nemo.user.tenantmanager.AAA;
import org.opendaylight.nemo.user.tenantmanager.TenantManage;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.User;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
/**
 * Created by zhangmeng on 2015/12/15.
 */
public class AAATest extends TestCase {
    private TenantManage tenantManage;
    private AAA aaa;
    @Override
    @Before
    public void setUp() throws Exception {
        tenantManage = mock(TenantManage.class);
        aaa = new AAA(tenantManage);
    }

    @Test
    public void testCheckUser() throws Exception {
        UserId userId = mock(UserId.class);
        User user = mock(User.class);
        Map<UserId, User> users = new HashMap<UserId, User>();

        when(tenantManage.getUsers())
                .thenReturn(null)
                .thenReturn(users);
        Assert.assertTrue(aaa.checkUser(userId).equals("The user is not exist."));
        verify(tenantManage).getUsers();

        users.put(userId, user);
        Assert.assertTrue(users != null);
        Assert.assertTrue(aaa.checkUser(userId) == null);
        verify(tenantManage,times(2)).getUsers();
    }
}