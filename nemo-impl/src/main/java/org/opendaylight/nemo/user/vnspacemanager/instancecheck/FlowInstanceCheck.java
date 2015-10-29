/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.user.vnspacemanager.instancecheck;

import org.opendaylight.nemo.user.tenantmanager.TenantManage;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Flow;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.User;

import java.util.List;

/**
 * Created by z00293636 on 2015/9/10.
 */
public class FlowInstanceCheck {
    private TenantManage tenantManage;

    public FlowInstanceCheck(TenantManage tenantManage)
    {
        this.tenantManage = tenantManage;
    }

    public String checkFlowInstance(UserId userId, Flow flow)
    {
        String errorInfo = null;

        tenantManage.fetchVNSpace(userId);


        User user = tenantManage.getUser();
        if (user != null)
        {
            if (user.getObjects() != null)
            {
                if (user.getObjects().getFlow() != null)
                {
                    List<Flow> flowList = tenantManage.getUser().getObjects().getFlow();
                    for (Flow flow1 : flowList)
                    {
                        if (flow1.getFlowId() == flow.getFlowId())
                        {
                            if (flow1.getFlowName() != flow.getFlowName())
                            {
                                errorInfo = "The flow name should not be changed.";
                                break;
                            }
                        }
                    }
                }
            }
        }
        return errorInfo;
    }
}
