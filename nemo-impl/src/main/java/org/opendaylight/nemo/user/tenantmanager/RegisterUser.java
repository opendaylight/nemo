/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.user.tenantmanager;

import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserRoleName;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.RegisterUserInput;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.User;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.user.rev151010.user.roles.UserRole;

import java.util.List;

/**
 * Created by z00293636 on 2015/9/20.
 */
public class RegisterUser {
    private TenantManage tenantManage;
    private List<UserRole> userRoleList;
    private List<User> usersList;

    public RegisterUser(TenantManage tenantManage)
    {
        this.tenantManage = tenantManage;
    }

    public String registerUser(RegisterUserInput input)
    {
        String errorInfo = null;

        tenantManage.fetchUserRoles();
        userRoleList = tenantManage.getUserRoleList();
        tenantManage.fetchUsers();
        usersList = tenantManage.getUsersList();

        if (userRoleList == null)
        {
            errorInfo = "There are no roles be defined.";
        }
        else
        {
            if (IfRoleExist(input.getUserRole()))
            {
                if (usersList != null && IfUserHasRegistered(input.getUserId()))
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
    private boolean IfRoleExist(UserRoleName userRoleName){
        Boolean roleExist = false;

        for (UserRole userRole : userRoleList)
        {
            if (userRole.getRoleName().equals(userRoleName))
            {
                roleExist = true;
            }
        }

        return roleExist;
    }

    private boolean IfUserHasRegistered(UserId userId){
        Boolean userHasRegistered = false;

        for (User user : usersList)
        {
            if (user.getUserId().equals(userId))
            {
                userHasRegistered = true;
            }
        }

        return userHasRegistered;
    }

}
