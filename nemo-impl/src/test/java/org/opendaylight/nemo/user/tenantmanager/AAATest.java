/*

* Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.

*

* This program and the accompanying materials are made available under the

* terms of the Eclipse Public License v1.0 which accompanies this distribution,

* and is available at http://www.eclipse.org/legal/epl-v10.html

*/
package org.opendaylight.nemo.user.tenantmanager;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.user.rev151010.UserInstance;

/**
 * Created by zhangmeng on 2015/11/20.
 */
public class AAATest extends TestCase {
    private AAA aaa;
    private TenantManage tenantManage;
    private UserInstance userInstance;

    @Before
    public void setUp() throws Exception {
        tenantManage = mock(TenantManage.class);
        aaa = new AAA(tenantManage);

        userInstance = mock(UserInstance.class);
    }

    @Test
    public void testCheckUser() throws Exception {
        doNothing().when(tenantManage).fetchUsers();
        when(tenantManage.getUsersList()).thenReturn(null);
        String acutal = aaa.checkUser(userInstance);
        String expected = "The user is not exist.";
        verify(tenantManage).fetchUsers();
        verify(tenantManage).getUsersList();
        Assert.assertNotNull(aaa);
        Assert.assertEquals(expected,acutal);
    }
}
