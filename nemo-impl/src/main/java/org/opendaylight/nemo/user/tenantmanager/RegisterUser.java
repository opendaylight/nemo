/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.user.tenantmanager;

import java.util.Map;

import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserRoleName;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.RegisterUserInput;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.User;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.user.rev151010.user.roles.UserRole;

/**
 * Created by z00293636 on 2015/9/20.
 */
public class RegisterUser {
    private final TenantManage tenantManage;

    public RegisterUser(TenantManage tenantManage)
    {
        this.tenantManage = tenantManage;
    }

    public String registerUser(RegisterUserInput input)
    {
        String errorInfo = null;

        Map<UserRoleName, UserRole> userRoles = tenantManage.getUserRoles();
        Map<UserId, User> users = tenantManage.getUsers();

        if (userRoles == null || userRoles.isEmpty())
        {
            errorInfo = "There are no roles be defined.";
        }
        else
        {
            if (userRoles.containsKey(input.getUserRole()))
            {
                if (users != null && users.containsKey(input.getUserId()))
                {
                    errorInfo = "The user has been registered.";
                }
                else
                {
                    tenantManage.addUser(input);
                }
            }
            else
            {
                errorInfo = "The role is not defined in the data store.";
            }
        }

        return errorInfo;
    }
}
