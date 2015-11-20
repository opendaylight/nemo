/*

* Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.

*

* This program and the accompanying materials are made available under the

* terms of the Eclipse Public License v1.0 which accompanies this distribution,

* and is available at http://www.eclipse.org/legal/epl-v10.html

*/
package org.opendaylight.nemo.user.transactionmanager;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.opendaylight.nemo.user.tenantmanager.AAA;
import org.opendaylight.nemo.user.transactionmanager.TransactionEnd;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.EndTransactionInput;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.BeginTransactionInput;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserName;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserPassword;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserRoleName;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.User;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
/**
 * Created by zhangmeng on 2015/11/20.
 */
public class TransactionEndTest extends TestCase {
    private TransactionEnd transactionEnd;
    @Before
    public void setUp() throws Exception {
        transactionEnd = new TransactionEnd();
    }

    @Test
    public void testTransactionend() throws Exception {
        AAA aaa = mock(AAA.class);
        EndTransactionInput input = mock(EndTransactionInput.class);
        when(aaa.CheckUser(any(UserId.class),any(UserName.class),any(UserPassword.class),any(UserRoleName.class)))
                .thenReturn(new String("test"));
        String flag = transactionEnd.transactionend(aaa,input);
        verify(aaa).CheckUser(any(UserId.class),any(UserName.class),any(UserPassword.class),any(UserRoleName.class));
        Assert.assertEquals("test", flag);
    }
}