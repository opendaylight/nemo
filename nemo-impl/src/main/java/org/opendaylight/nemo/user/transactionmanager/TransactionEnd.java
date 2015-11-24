/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.user.transactionmanager;

import org.opendaylight.nemo.user.tenantmanager.AAA;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.EndTransactionInput;

/**
 * Created by z00293636 on 2015/9/2.
 */
public class TransactionEnd {
    public String transactionend(AAA aaa,EndTransactionInput input)
    {
        String errorInfo = null;
        errorInfo = aaa.checkUser(input);
        return errorInfo;
    }
}
