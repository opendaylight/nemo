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
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
/**
 * Created by zhangmeng on 2015/12/15.
 */
public class TransactionEndTest extends TestCase {
    private AAA aaa;
    private EndTransactionInput input;
    private TransactionEnd transactionEnd;
    @Override
    @Before
    public void setUp() throws Exception {
        aaa = mock(AAA.class);
        input = mock(EndTransactionInput.class);

        transactionEnd = new TransactionEnd();
    }

    @Test
    public void testTransactionend() throws Exception {
        UserId userId = mock(UserId.class);

        when(aaa.checkUser(any(UserId.class))).thenReturn(new String("test"));
        when(input.getUserId()).thenReturn(userId);

        Assert.assertTrue(transactionEnd.transactionend(aaa,input).equals("test"));
    }
}