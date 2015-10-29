/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.user.tenantmanager;

import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserName;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserPassword;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserRoleName;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.User;

import java.util.List;

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

    public String CheckUser(UserId userId, UserName userName, UserPassword userPassword, UserRoleName userRoleName)
    {
        tenantManage.fetchUsers();
        List<User> userList = tenantManage.getUsersList();
        String errorInfo = null;
        Boolean userexist = false;

       if (userList != null)
        {
            for (User user : userList)
            {
                if (user.getUserId().equals(userId))
                {
                    userexist = true;
                    if (!user.getUserName().equals(userName))
                    {
                        errorInfo = "The user name is not right.";
                        break;
                    }
                    else if (!user.getUserPassword().equals(userPassword))
                    {
                        errorInfo = "The password is not right.";
                        break;
                    }
                    else if (!user.getUserRole().equals(userRoleName))
                    {
                        errorInfo = "The role is not right.";
                        break;
                    }
                }
             }

        }

        if (!userexist)
        {
            errorInfo = "The user is not exist.";
        }
        return errorInfo;
    }
}
