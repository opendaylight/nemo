/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.user.transactionmanager;

import org.opendaylight.nemo.user.transactionmanager.TransactionEnd;

import static org.mockito.Mockito.mock;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.opendaylight.nemo.user.tenantmanager.AAA;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.EndTransactionInput;
/**
 * Created by wangjunfei on 2015/11/9.
 */
public class TransactionEndTest {
    private AAA aaa;
    private EndTransactionInput input;
    private TransactionEnd end;

    @org.junit.Before
    public void setUp() throws Exception {
        aaa = mock(AAA.class);
        input = mock(EndTransactionInput.class);
        end = mock(TransactionEnd.class);
    }

    @org.junit.Test
    public void testTransactionend() throws Exception {
        Assert.assertNull(end.transactionend(aaa, input));
    }
}