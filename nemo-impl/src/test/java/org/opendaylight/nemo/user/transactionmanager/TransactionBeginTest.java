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
import org.opendaylight.nemo.user.transactionmanager.TransactionBegin;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.BeginTransactionInput;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
/**
 * Created by zhangmeng on 2015/12/15.
 */
public class TransactionBeginTest extends TestCase {
    private AAA aaa;
    private BeginTransactionInput input;
    private TransactionBegin transactionBegin;
    @Override
    @Before
    public void setUp() throws Exception {
        aaa = mock(AAA.class);
        input =  mock(BeginTransactionInput.class);

        transactionBegin = new TransactionBegin();
    }

    @Test
    public void testTransactionbegin() throws Exception {
        String result;
        UserId userId = mock(UserId.class);

        when(aaa.checkUser(any(UserId.class))).thenReturn(new String("test"));
        when(input.getUserId()).thenReturn(userId);

        result = transactionBegin.transactionbegin(aaa,input);
        Assert.assertTrue(result.equals("test"));
    }
}