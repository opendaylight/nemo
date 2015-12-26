/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.user.vnspacemanager.languagestyle.updateintentlang;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.nemo.user.tenantmanager.TenantManage;
import org.opendaylight.nemo.user.vnspacemanager.languagestyle.NEMOConstants;
import org.opendaylight.nemo.user.vnspacemanager.structurestyle.updateintent.UpdateFlow;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Flow;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.FlowBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.FlowKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.flow.instance.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.match.item.instance.MatchItemValueBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.match.item.instance.match.item.value.RangeValueBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.PropertyValuesBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.property.values.*;

import java.util.*;

/**
 * Created by z00293636 on 2015/11/5.
 */
public class UpdateFlowLang {
    private TenantManage tenantManage;
    private UpdateFlow updateFlow;
    private Flow flow;

    public UpdateFlowLang(DataBroker dataBroker, TenantManage tenantManage){
        this.tenantManage = tenantManage;
        updateFlow = new UpdateFlow(dataBroker,tenantManage);
    }

    public String FlowHandling(UserId userId, String flowname,LinkedHashMap<String,LinkedHashMap<String,String>> flowmatch,LinkedHashMap<String,LinkedHashMap<String,String>> propertyList){
        String errorInfo = null;
        if (!propertyList.isEmpty()){
            errorInfo = checkProperty(propertyList);
        }
        if (errorInfo==null){
            errorInfo = createFlow(userId,flowname,flowmatch,propertyList);
            if (errorInfo==null)
            {
                return updateFlow.FlowHandling(userId,this.flow);
            }
        }
        return errorInfo;
    }

    private String checkProperty(LinkedHashMap<String,LinkedHashMap<String,String>> flowproperty){
        String errorInfo = null;
        for (String propertyName : flowproperty.keySet()){
            LinkedHashMap<String,String> propertyValues= flowproperty.get(propertyName);
            Iterator<String> iterator = propertyValues.values().iterator();
            String valueType = iterator.next();

            while (iterator.hasNext()){
                if (valueType.equals(NEMOConstants.range)) {
                    errorInfo = "The property should just has one range value.";
                    return errorInfo;
                }
                else if (valueType.equals(NEMOConstants.string)){
                    if (!iterator.next().equals(NEMOConstants.string)){
                        return "The property " + propertyValues.get(iterator.next()) + " should be string.";
                    }
                }
                else if (valueType.equals(NEMOConstants.integer)){
                    if (!iterator.next().equals(NEMOConstants.integer)){
                        return "The property " + propertyValues.get(iterator.next()) + " should be int.";
                    }
                }
            }
        }
        return errorInfo;
    }

    private String createFlow(UserId userId, String flowname,LinkedHashMap<String,LinkedHashMap<String,String>> flowmatch,LinkedHashMap<String,LinkedHashMap<String,String>> propertyList ){
        String errorInfo = null;
        FlowBuilder flowBuilder = new FlowBuilder();
        Flow flow1 = null;
        if (tenantManage.getObjectId(userId,flowname)!=null){
            FlowId flowId = new FlowId(tenantManage.getObjectId(userId,flowname));
            if (tenantManage.getFlow(userId).containsKey(flowId)){
                flow1 = tenantManage.getFlow(userId).get(flowId);
            }
            else if (tenantManage.getFlowDataStore(userId).containsKey(flowId)){
                flow1 = tenantManage.getFlowDataStore(userId).get(flowId);
            }
        }
        if (flow1==null){
            FlowId flowId = new FlowId(UUID.randomUUID().toString());
            flowBuilder.setKey(new FlowKey(flowId))
                       .setFlowId(flowId);
        }
        else {
            flowBuilder.setKey(flow1.getKey())
                       .setFlowId(flow1.getFlowId());
        }

        flowBuilder.setFlowName(new FlowName(flowname));

        if (!flowmatch.isEmpty() && errorInfo==null){
            List<MatchItem> matchItemList = new ArrayList<MatchItem>();
            MatchItemBuilder matchItemBuilder = new MatchItemBuilder();
            for (String matchname : flowmatch.keySet()){
                matchItemBuilder.setMatchItemName(new MatchItemName(matchname));
                LinkedHashMap<String, String> matchvalue = flowmatch.get(matchname);
                MatchItemValueBuilder matchItemValueBuilder = new MatchItemValueBuilder();
                for (String value : matchvalue.keySet()){
                    if (matchvalue.get(value).equals(NEMOConstants.string)){
                        matchItemValueBuilder.setStringValue(value);
                    }
                    if (matchvalue.get(value).equals(NEMOConstants.integer)){
                        matchItemValueBuilder.setIntValue(Long.parseLong(value));
                    }
                    if (matchvalue.get(value).equals(NEMOConstants.range)){
                        String[] rangevalue = new String[2];
                        rangevalue = value.split(",");
                        RangeValueBuilder rangeValueBuilder = new RangeValueBuilder();
                        if (Long.parseLong(rangevalue[0])<Long.parseLong(rangevalue[1])){
                            rangeValueBuilder.setMin(Long.parseLong(rangevalue[0]));
                            rangeValueBuilder.setMax(Long.parseLong(rangevalue[1]));
                        }
                        else{
                            rangeValueBuilder.setMin(Long.parseLong(rangevalue[1]));
                            rangeValueBuilder.setMax(Long.parseLong(rangevalue[0]));
                        }
                        matchItemValueBuilder.setRangeValue(rangeValueBuilder.build());
                    }
                }
                matchItemBuilder.setMatchItemValue(matchItemValueBuilder.build());
                matchItemList.add(matchItemBuilder.build());
            }
            if (matchItemList.isEmpty()){
                matchItemList=null;
                flowBuilder.setMatchItem(matchItemList);
            }
            flowBuilder.setMatchItem(matchItemList);
        }

        if (errorInfo==null && ! propertyList.isEmpty()) {
            PropertyBuilder propertyBuilder = new PropertyBuilder();
            List<Property> flowproperty = new ArrayList<Property>();
            for (String propertyname : propertyList.keySet()){
                propertyBuilder.setKey(new PropertyKey(new PropertyName(propertyname)));
                propertyBuilder.setPropertyName(new PropertyName(propertyname));

                PropertyValuesBuilder propertyValuesBuilder = new PropertyValuesBuilder();
                LinkedHashMap<String, String> valuetype = propertyList.get(propertyname);
                List<IntValue> intValueList = new ArrayList<IntValue>();
                List<StringValue> stringValueList = new ArrayList<StringValue>();
                RangeValue Rangevalue = null;
                int order = 0;
                for (String value : valuetype.keySet()){
                    if (valuetype.get(value).equals(NEMOConstants.string)){
                        StringValueBuilder stringValueBuilder = new StringValueBuilder();
                        stringValueBuilder.setKey(new StringValueKey((long)order,value));
                        stringValueBuilder.setValue(value);
                        stringValueBuilder.setOrder((long)order);
                        stringValueList.add(stringValueBuilder.build());
                    }
                    if (valuetype.get(value).equals(NEMOConstants.integer)) {
                        IntValueBuilder intValueBuilder = new IntValueBuilder();
                        intValueBuilder.setKey(new IntValueKey((long)order,Long.parseLong(value)));
                        intValueBuilder.setValue(Long.parseLong(value));
                        intValueBuilder.setOrder((long)order);
                        intValueList.add(intValueBuilder.build());
                    }
                    if (valuetype.get(value).equals(NEMOConstants.range)){
                        String[] rangevalue = new String[2];
                        rangevalue = value.split(",");
                        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.property.values.RangeValueBuilder rangeValueBuilder
                                = new org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.property.values.RangeValueBuilder();
                        if (Long.parseLong(rangevalue[0])<Long.parseLong(rangevalue[1])){
                            rangeValueBuilder.setMin(Long.parseLong(rangevalue[0]));
                            rangeValueBuilder.setMax(Long.parseLong(rangevalue[1]));
                        }
                        else{
                            rangeValueBuilder.setMin(Long.parseLong(rangevalue[1]));
                            rangeValueBuilder.setMax(Long.parseLong(rangevalue[0]));
                        }
                        Rangevalue = rangeValueBuilder.build();
                        propertyValuesBuilder.setRangeValue(Rangevalue);
                    }
                    order++;
                }
                if (intValueList.isEmpty()){
                    intValueList = null;
                    propertyValuesBuilder.setIntValue(intValueList);
                }
                if (stringValueList.isEmpty()){
                    stringValueList = null;
                    propertyValuesBuilder.setStringValue(stringValueList);
                }
                propertyValuesBuilder.setIntValue(intValueList).setStringValue(stringValueList).setRangeValue(Rangevalue);
                propertyBuilder.setPropertyValues(propertyValuesBuilder.build());
                flowproperty.add(propertyBuilder.build());
            }
            flowBuilder.setProperty(flowproperty);
        }
        this.flow = flowBuilder.build();
        return errorInfo;
    }
}
