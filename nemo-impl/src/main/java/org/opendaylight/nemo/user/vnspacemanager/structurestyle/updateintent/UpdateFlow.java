/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.user.vnspacemanager.structurestyle.updateintent;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.WriteTransaction;
import org.opendaylight.nemo.user.tenantmanager.TenantManage;
import org.opendaylight.nemo.user.vnspacemanager.instancecheck.FlowInstanceCheck;
import org.opendaylight.nemo.user.vnspacemanager.syntaxcheck.FlowDefinitionCheck;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.Users;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.Objects;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Flow;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.FlowBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.FlowKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.User;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.UserKey;
import org.opendaylight.controller.md.sal.binding.api.WriteTransaction;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.opendaylight.controller.md.sal.common.api.data.TransactionCommitFailedException;
import com.google.common.util.concurrent.CheckedFuture;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Created by z00293636 on 2015/8/31.
 */
public class UpdateFlow {

    private DataBroker dataBroker;
    private TenantManage tenantManage;
    private FlowDefinitionCheck flowDefinitionCheck;
    private FlowInstanceCheck flowInstanceCheck;
    private static final Logger LOG = LoggerFactory.getLogger(UpdateFlow.class);

    public UpdateFlow(DataBroker dataBroker, TenantManage tenantManage)
    {
        this.dataBroker = dataBroker;
        this.tenantManage = tenantManage;
        flowDefinitionCheck = new FlowDefinitionCheck(dataBroker);
        flowInstanceCheck = new FlowInstanceCheck(tenantManage);
    }

    public String FlowHandling(UserId userId, Flow flow)
    {
        String errorDefinition = flowDefinitionCheck.CheckDefinition(flow);
        String errorInstance = flowInstanceCheck.checkFlowInstance(userId,flow);

        if (errorDefinition != null)
        {
            return errorDefinition;
        }
        if (errorInstance != null)
        {
            return errorInstance;
        }
        else
        {
            WriteTransaction t = dataBroker.newWriteOnlyTransaction();
            if (userId!=null && flow!=null)
            {
//                FlowBuilder flowBuilder = new FlowBuilder();
//                flowBuilder.setFlowId(flow.getFlowId());
//                flowBuilder.setFlowName(flow.getFlowName());
//                flowBuilder.setMatchItem(flow.getMatchItem());
//                flowBuilder.setProperty(flow.getProperty());
//
//                Flow flow1 = flowBuilder.build();
                Flow flow1 = new FlowBuilder(flow).build();
                FlowKey flowKey = new FlowKey(flow.getFlowId());

                UserKey userKey = new UserKey(userId);

                InstanceIdentifier<Flow> flowid = InstanceIdentifier.builder(Users.class).child(User.class, userKey).child(Objects.class).child(Flow.class,flowKey).build();
                t.put(LogicalDatastoreType.CONFIGURATION, flowid, flow1,true);
                CheckedFuture<Void, TransactionCommitFailedException> f = t.submit();
                Futures.addCallback(f, new FutureCallback<Void>() {
                    @Override
                    public void onFailure(Throwable t) {
                        LOG.error("Could not write endpoint base container", t);
                    }

                    @Override
                    public void onSuccess(Void result) {
                    }
                });
            }
        }
        return null;
    }

}
