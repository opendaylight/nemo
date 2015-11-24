/*

* Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.

*

* This program and the accompanying materials are made available under the

* terms of the Eclipse Public License v1.0 which accompanies this distribution,

* and is available at http://www.eclipse.org/legal/epl-v10.html

*/
package org.opendaylight.nemo.user.transactionmanager;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.opendaylight.nemo.user.tenantmanager.AAA;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.BeginTransactionInput;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.user.rev151010.UserInstance;

/**
 * Created by zhangmeng on 2015/11/20.
 */
public class TransactionBeginTest extends TestCase {
    private TransactionBegin transactionBegin;
    @Before
    public void setUp() throws Exception {
        transactionBegin = new TransactionBegin();
    }

    @Test
    public void testTransactionbegin() throws Exception {
        AAA aaa = mock(AAA.class);
        BeginTransactionInput input = mock(BeginTransactionInput.class);
        when(aaa.checkUser(any(UserInstance.class))).thenReturn(new String("test"));
        String flag = transactionBegin.transactionbegin(aaa,input);
        verify(aaa).checkUser(any(UserInstance.class));
        Assert.assertEquals("test",flag);
    }
}