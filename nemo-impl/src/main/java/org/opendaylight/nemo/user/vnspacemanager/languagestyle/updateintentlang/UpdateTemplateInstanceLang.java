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
import org.opendaylight.nemo.user.vnspacemanager.structurestyle.updateintent.UpdateTemplateInstance;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.template.instances.TemplateInstanceBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.template.instances.TemplateInstanceKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.TemplateParameter;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.TemplateParameterBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.TemplateParameterKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.ParameterValuesBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.parameter.values.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by z00293636 on 2015/12/8.
 */
public class UpdateTemplateInstanceLang {
    private DataBroker dataBroker;
    private TenantManage tenantManage;
    private UpdateTemplateInstance updateTemplateInstance;
    private static final Logger LOG = LoggerFactory.getLogger(UpdateTemplateInstanceLang.class);

    public UpdateTemplateInstanceLang(DataBroker dataBroker, TenantManage tenantManage){
        this.dataBroker = dataBroker;
        this.tenantManage = tenantManage;
        updateTemplateInstance = new UpdateTemplateInstance(dataBroker,tenantManage);
    }

    public String templateInstanceLang(UserId userId, String nodeName, String nodeType, LinkedHashMap<String, LinkedHashMap<String,String>> propertyList){
        Boolean instanceExist = false;
        if (tenantManage.getTemplateInstance(userId)!=null){
            String objectId = tenantManage.getObjectId(userId,nodeName);
            if (tenantManage.getTemplateInstance(userId).containsKey(new TemplateInstanceId(objectId))){
                instanceExist = true;
            }
        }

        if (!instanceExist){
            TemplateInstanceBuilder instanceBuilder = new TemplateInstanceBuilder();
            TemplateInstanceId templateInstanceId = new TemplateInstanceId(UUID.randomUUID().toString());
            instanceBuilder.setKey(new TemplateInstanceKey(templateInstanceId))
                    .setTemplateInstanceId(templateInstanceId)
                    .setTemplateInstanceName(new TemplateInstanceName(nodeName))
                    .setTemplateName(new TemplateName(nodeType));

            if (!propertyList.isEmpty()){
                List<TemplateParameter> templateParameterList = new LinkedList<TemplateParameter>();
                for (String parameterName : propertyList.keySet()){
                    TemplateParameterBuilder parameterBuilder = new TemplateParameterBuilder();
                    parameterBuilder.setKey(new TemplateParameterKey(new ParameterName(parameterName)))
                            .setParameterName(new ParameterName(parameterName));

                    Map<String, String> values = propertyList.get(parameterName);
                    ParameterValuesBuilder valuesBuilder = new ParameterValuesBuilder();
                    List<StringValue> stringValues = new LinkedList<StringValue>();
                    List<IntValue> intValues = new LinkedList<IntValue>();
                    RangeValue rangeValue = null;
                    Long order = 0L;
                    for (String value : values.keySet()){
                        if (values.get(value).equals(NEMOConstants.string)){
                            StringValueBuilder stringValueBuilder = new StringValueBuilder();
                            stringValueBuilder.setKey(new StringValueKey(order,value))
                                    .setValue(value)
                                    .setOrder(order);
                            order++;
                            stringValues.add(stringValueBuilder.build());
                        }
                        if (values.get(value).equals(NEMOConstants.integer)){
                            IntValueBuilder intValueBuilder = new IntValueBuilder();
                            intValueBuilder.setKey(new IntValueKey(order,Long.parseLong(value)))
                                    .setOrder(Long.parseLong(value))
                                    .setOrder(order);
                            order++;
                            intValues.add(intValueBuilder.build());
                        }
                        if (values.get(value).equals(NEMOConstants.range)){
                            RangeValueBuilder rangeValueBuilder = new RangeValueBuilder();
                            String[] range = value.split(",");
                            rangeValueBuilder.setMax(Long.parseLong(range[0])>Long.parseLong(range[1])?Long.parseLong(range[0]):Long.parseLong(range[1]))
                                    .setMin(Long.parseLong(range[0])<Long.parseLong(range[1])?Long.parseLong(range[0]):Long.parseLong(range[1]));
                            order++;
                            rangeValue = rangeValueBuilder.build();
                        }
                    }
                    if ((!stringValues.isEmpty()&&intValues.isEmpty()&&rangeValue==null)
                            ||(stringValues.isEmpty()&&!intValues.isEmpty()&&rangeValue==null)
                            ||stringValues.isEmpty()&&intValues.isEmpty()&&rangeValue!=null){
                        valuesBuilder.setStringValue(stringValues.isEmpty()?null:stringValues)
                                .setIntValue(intValues.isEmpty()?null:intValues)
                                .setRangeValue(rangeValue);
                        parameterBuilder.setParameterValues(valuesBuilder.build());
                    }else {
                        return "The value types are not consistent.";
                    }
                    templateParameterList.add(parameterBuilder.build());
                }
                instanceBuilder.setTemplateParameter(templateParameterList);
            }

            String errorInfo = updateTemplateInstance.checkTemplateInstance(userId,instanceBuilder.build());

            if (errorInfo!=null){
                return errorInfo;
            }
            else {
                tenantManage.setUserTemplateInstance(userId,instanceBuilder.getTemplateInstanceId(),instanceBuilder.build());
            }
        }
        else {
            return "The instance " + nodeName + " has exist.";
        }
        return null;
    }
}
