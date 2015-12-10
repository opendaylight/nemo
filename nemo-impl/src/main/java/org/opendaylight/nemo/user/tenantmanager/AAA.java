/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.user.tenantmanager;

import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.User;

import java.util.Map;

/**
 * Created by z00293636 on 2015/8/29.
 */

/* test user-name and user-password */
public class AAA {

   private TenantManage tenantManage;

    public AAA(TenantManage tenantManage)
    {
        this.tenantManage = tenantManage;
    }

    public String checkUser(UserId userId)
    {
        final Map<UserId, User> users = tenantManage.getUsers();
        String errorInfo = null;
        final User user = (users != null) ? users.get(userId) : null;

        if (user == null) {
            errorInfo = "The user is not exist.";
        }

        return errorInfo;
    }
}
