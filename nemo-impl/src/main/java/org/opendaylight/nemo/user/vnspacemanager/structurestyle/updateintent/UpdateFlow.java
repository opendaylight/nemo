/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.user.vnspacemanager.structurestyle.updateintent;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.WriteTransaction;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.controller.md.sal.common.api.data.TransactionCommitFailedException;
import org.opendaylight.nemo.user.tenantmanager.TenantManage;
import org.opendaylight.nemo.user.vnspacemanager.languagestyle.NEMOConstants;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.MatchItemName;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.Users;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.Objects;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Flow;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.FlowBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.FlowKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.User;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.UserKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.flow.instance.MatchItem;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.match.item.definitions.MatchItemDefinition;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.match.item.instance.MatchItemValue;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.util.concurrent.CheckedFuture;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;

/**
 * Created by z00293636 on 2015/8/31.
 */
public class UpdateFlow {

    private DataBroker dataBroker;
    private TenantManage tenantManage;
    private GetDefinitions getDefinitions;
    private ValueCheck valueCheck;
    private static final Logger LOG = LoggerFactory.getLogger(UpdateFlow.class);

    public UpdateFlow(DataBroker dataBroker, TenantManage tenantManage)
    {
        this.dataBroker = dataBroker;
        this.tenantManage = tenantManage;
        getDefinitions = new GetDefinitions(dataBroker);
        valueCheck = new ValueCheck();
    }

    public String FlowHandling(UserId userId, Flow flow){
        String errorInfo = null;
        errorInfo = checkDefinition(flow);
        if (errorInfo!=null){
            return errorInfo;
        }
        else {
            errorInfo = checkInstance(userId,flow);
            if (errorInfo!=null){
                return errorInfo;
            }
            else {
                WriteTransaction t = dataBroker.newWriteOnlyTransaction();
                if (userId!=null && flow!=null){
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

                    try {
                        f.get();
                    } catch (InterruptedException | ExecutionException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }

    private String checkInstance(UserId userId, Flow flow){
        String errorInfo = null;
        tenantManage.fetchVNSpace(userId);
        User user = tenantManage.getUser();
        if (user != null){
            if (user.getObjects() != null){
                if (user.getObjects().getFlow() != null){
                    List<Flow> flowList = tenantManage.getUser().getObjects().getFlow();
                    for (Flow flow1 : flowList){
                        if (flow1.getFlowId().equals(flow.getFlowId())){
                            if (!flow1.getFlowName().equals(flow.getFlowName())){
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

    private String checkPredefine(List<MatchItem> matchItems){
        String errorInfo = null;
        for (MatchItem matchItem :matchItems){
            if (matchItem.getMatchItemName().getValue().equals(NEMOConstants.src_ip)||matchItem.getMatchItemName().getValue().equals(NEMOConstants.dst_ip)){
               String stringValues = matchItem.getMatchItemValue().getStringValue();
                Boolean legalValue = true;
                if (stringValues.contains("/")){
                    legalValue = valueCheck.checkIpPrefix(stringValues);
                }
                else {
                    legalValue = valueCheck.checkIpAddress(stringValues);
                }
                if (!legalValue){
                    errorInfo = "The " + NEMOConstants.ip_address + " is not legal.";
                }
            }
            if (matchItem.getMatchItemName().getValue().equals(NEMOConstants.src_mac)||matchItem.getMatchItemName().getValue().equals(NEMOConstants.dst_mac)){
                String stringValues = matchItem.getMatchItemValue().getStringValue();
                Boolean legalValue = valueCheck.checkMac(stringValues);
                if (!legalValue){
                    errorInfo = "The " + NEMOConstants.mac_address + " is not legal.";
                }
            }
        }
        return errorInfo;
    }

    private String checkDefinition(Flow flow){
        String errorInfo = null;
        Map<MatchItemName, MatchItemDefinition> matchItemDefinitionMap =getDefinitions.getMatchItemDefinition();

        if (flow.getMatchItem() != null)
        {
            if (matchItemDefinitionMap.isEmpty()){
                return "The match item has not been defined.";
            }
            else {
                for (MatchItem matchItem : flow.getMatchItem())
                {
                    if (matchItemDefinitionMap.containsKey(matchItem.getMatchItemName())){
                        MatchItemValue matchItemValue = matchItem.getMatchItemValue();
                        MatchItemDefinition.MatchItemValueType matchItemValueType = matchItemDefinitionMap.get(matchItem.getMatchItemName()).getMatchItemValueType();
                        if (matchItemValue != null && matchItemValueType != null)
                        {
                            if (matchItemValueType.getIntValue()==0 && !(matchItemValue.getIntValue()==null&&matchItemValue.getStringValue()!=null&&matchItemValue.getRangeValue()==null))
                            {
                                errorInfo = "The match item value type for" +matchItem.getMatchItemName().toString()+"should be string.";
                                break;
                            }

                            if (matchItemValueType.getIntValue()==1 && !(matchItemValue.getIntValue()!=null&&matchItemValue.getStringValue()==null&&matchItemValue.getRangeValue()==null))
                            {
                                errorInfo = "The match item value type for" + matchItem.getMatchItemName().toString()+"should be integer.";
                                break;
                            }

                            if (matchItemValueType.getIntValue()==2 && !(matchItemValue.getIntValue()==null&&matchItemValue.getStringValue()==null&&matchItemValue.getRangeValue()!=null))
                            {
                                errorInfo = "The match item value type for" + matchItem.getMatchItemName().toString()+"should be range.";
                                break;
                            }
                        }
                    }
                    else {
                        return "The match item has not been defined.";
                    }

                }
            }
            if (errorInfo==null){
                errorInfo = checkPredefine(flow.getMatchItem());
            }
        }
        return errorInfo;
    }
}
