/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.user.vnspacemanager.structurestyle.updateintent;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.nemo.user.tenantmanager.TenantManage;
import org.opendaylight.nemo.user.vnspacemanager.languagestyle.NEMOConstants;
import org.opendaylight.nemo.user.vnspacemanager.languagestyle.updateintentlang.UpdateTemplateInstanceLang;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Node;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.template.instances.TemplateInstanceBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.template.instances.TemplateInstanceKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.User;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.node.definitions.NodeDefinition;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.node.instance.Property;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.node.instance.SubNode;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.definitions.PropertyDefinition;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.PropertyValues;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.property.values.StringValue;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.TemplateParameter;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.TemplateParameterBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.TemplateParameterKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.ParameterValuesBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.parameter.values.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by z00293636 on 2015/8/31.
 */
public class UpdateNode {
    private TenantManage tenantManage;
    private GetDefinitions getDefinitions;
    private ValueCheck valueCheck;
    private UpdateTemplateInstance updateTemplateInstance;
    private UpdateTemplateInstanceLang updateTemplateInstanceLang;

    public UpdateNode(DataBroker dataBroker,TenantManage tenantManage){
        this.tenantManage = tenantManage;
        getDefinitions = new GetDefinitions(dataBroker);
        valueCheck = new ValueCheck();
        updateTemplateInstance = new UpdateTemplateInstance(dataBroker, tenantManage);
        updateTemplateInstanceLang = new UpdateTemplateInstanceLang(dataBroker, tenantManage);
    }


    public String NodeHandling(UserId userId,Node node){
        String errorInfo = null;
        boolean nodeModel = false;
        if (tenantManage.getTempalteDefinition(userId)!=null){
            if (tenantManage.getTempalteDefinition(userId).containsKey(new TemplateName(node.getNodeType().getValue()))){
                nodeModel = true;
            }
        }
        else if (tenantManage.getDefinitionDataStore(userId)!=null){
            if (tenantManage.getDefinitionDataStore(userId).containsKey(new TemplateName(node.getNodeType().getValue()))){
                nodeModel = true;
            }
        }
        else if (!nodeModel){
            Map<UserId, User> usersMap = tenantManage.getUsers();
            for (User user : usersMap.values()) {
                if (user.getUserRole().getValue().equals(NEMOConstants.admin)) {
                    if (tenantManage.getDefinitionDataStore(user.getUserId()) != null) {
                        if (tenantManage.getDefinitionDataStore(user.getUserId()).containsKey(new TemplateName(node.getNodeType().getValue()))) {
                            nodeModel = true;
                        }
                    }
                }
            }
        }

        if (nodeModel){
            if (node.getSubNode()!=null){
                return "Subnodes should not be included in template instance.";
            }
            else {
                TemplateInstanceBuilder builder = new TemplateInstanceBuilder();
                builder.setKey(new TemplateInstanceKey(new TemplateInstanceId(node.getNodeId().getValue())))
                        .setTemplateInstanceId(new TemplateInstanceId(node.getNodeId().getValue()))
                        .setTemplateInstanceName(new TemplateInstanceName(node.getNodeName().getValue()))
                        .setTemplateName(new TemplateName(node.getNodeType().getValue()));
                if (node.getProperty()!=null){
                    List<Property> nodeProeprty = node.getProperty();
                    List<TemplateParameter> parameters = new LinkedList<TemplateParameter>();
                    for (Property property : nodeProeprty){
                        TemplateParameterBuilder parameterBuilder = new TemplateParameterBuilder();
                        parameterBuilder.setKey(new TemplateParameterKey(new ParameterName(property.getPropertyName().getValue())))
                                        .setParameterName(new ParameterName(property.getPropertyName().getValue()));
                        ParameterValuesBuilder valuesBuilder = new ParameterValuesBuilder();
                        List<IntValue> intValueList = new LinkedList<IntValue>();
                        List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.parameter.values.StringValue> stringValueList
                                = new LinkedList<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.parameter.values.StringValue>();
                        RangeValue rangeValue = null;
                        if (property.getPropertyValues().getIntValue()!=null){
                            for (org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.property.values.IntValue intValue : property.getPropertyValues().getIntValue()){
                                IntValueBuilder intValueBuilder = new IntValueBuilder();
                                intValueBuilder.setKey(new IntValueKey(intValue.getOrder(),intValue.getValue()))
                                                .setOrder(intValue.getOrder())
                                                .setValue(intValue.getValue());
                                intValueList.add(intValueBuilder.build());
                            }
                        }
                        if (property.getPropertyValues().getStringValue()!=null){
                            for (StringValue stringValue : property.getPropertyValues().getStringValue()){
                                StringValueBuilder stringValueBuilder = new StringValueBuilder();
                                stringValueBuilder.setKey(new StringValueKey(stringValue.getOrder(),stringValue.getValue()))
                                                  .setOrder(stringValue.getOrder())
                                                  .setValue(stringValue.getValue());
                                stringValueList.add(stringValueBuilder.build());
                            }
                        }
                        if (property.getPropertyValues().getRangeValue()!=null){
                            RangeValueBuilder rangeValueBuilder = new RangeValueBuilder();
                            rangeValueBuilder.setMin(property.getPropertyValues().getRangeValue().getMin())
                                             .setMax(property.getPropertyValues().getRangeValue().getMax());
                            rangeValue = rangeValueBuilder.build();
                        }
                        valuesBuilder.setIntValue(intValueList).setStringValue(stringValueList).setRangeValue(rangeValue);
                        parameterBuilder.setParameterValues(valuesBuilder.build());
                        parameters.add(parameterBuilder.build());
                    }
                    builder.setTemplateParameter(parameters);
                }

                errorInfo = updateTemplateInstance.checkTemplateInstance(userId,builder.build());
            }
        }
        else {
            errorInfo = checkDefinition(node);
            if (errorInfo !=null){
                return errorInfo;
            }
            else{
                errorInfo = checkInstance(userId, node);
                if (errorInfo!=null){
                    return errorInfo;
                }
                else {
                    tenantManage.setNode(userId, node.getNodeId(),node);
                }
            }
        }
        return errorInfo;
    }

    private String checkInstance(UserId userId, Node node){
        if (tenantManage.getNode(userId)!=null){
            if (tenantManage.getNode(userId).containsKey(node.getNodeId())){
                Node nodeExist = tenantManage.getNode(userId).get(node.getNodeId());
                if (!nodeExist.getNodeName().equals(node.getNodeName())){
                    return  "The node name should not be changed.";
                }
                if (!nodeExist.getNodeType().equals(node.getNodeType())){
                    return "The node type should not be changed.";
                }
            }
        }

        if (tenantManage.getNodeDataStore(userId)!=null){
            if (tenantManage.getNodeDataStore(userId).containsKey(node.getNodeId())){
                Node nodeExist = tenantManage.getNodeDataStore(userId).get(node.getNodeId());
                if (!nodeExist.getNodeName().equals(node.getNodeName())){
                    return  "The node name should not be changed.";
                }
                if (!nodeExist.getNodeType().equals(node.getNodeType())){
                    return "The node type should not be changed.";
                }
            }
        }

        if (node.getSubNode()!=null){
            List<SubNode> subNodeList = node.getSubNode();
            Boolean subNodeExist = false;
            for (SubNode subNode : subNodeList){
                if (tenantManage.getNode(userId)!=null){
                    if (tenantManage.getNode(userId).containsKey(subNode.getNodeId())){
                        subNodeExist = true;
                    }
                }
                if (tenantManage.getNodeDataStore(userId)!=null){
                    if (tenantManage.getNodeDataStore(userId)!=null){
                        if (tenantManage.getNodeDataStore(userId).containsKey(subNode.getNodeId())){
                            subNodeExist = true;
                        }
                    }
                }
                if (!subNodeExist){
                    return "The sub-node " + subNode.getNodeId().getValue() + " is not exist.";
                }
            }
        }
        return null;
    }

    private String checkDefinition(Node node){
        String errorInfo = null;
        Map<NodeType, NodeDefinition> map = getDefinitions.getNodeDefinition();
        if (map.isEmpty()){
            return "This type of Node has not been defined.";
        }
        else {
            if (map.containsKey(node.getNodeType())){
                List<Property> nodeProperties = node.getProperty();
                List<PropertyDefinition> nodePropertyDefinitions = map.get(node.getNodeType()).getPropertyDefinition();

                if (nodeProperties != null && nodePropertyDefinitions == null){
                    errorInfo = "This type of node has no properties.";
                }
                else if (nodeProperties != null && nodePropertyDefinitions != null){
                    errorInfo = checkProperty(nodeProperties, nodePropertyDefinitions);
                    if (errorInfo != null){
                    }
                    else {
                        errorInfo = checkPredefine(nodeProperties);
                    }
                }
            }
            else {
                return "This type of Node has not been defined.";
            }
        }
        return errorInfo;
    }

    private String checkProperty(List<Property> nodeProperties, List<PropertyDefinition> nodePropertyDefinitions){
        Boolean propertyHasDefine = false;
        String errorInfo = null;

        for (Property property : nodeProperties){
            if (errorInfo != null){
                break;
            }
            else{
                for (PropertyDefinition propertydefinition : nodePropertyDefinitions){
                    if (property.getPropertyName().equals(propertydefinition.getPropertyName())){
                        propertyHasDefine = true;
                        PropertyValues propertyValues = property.getPropertyValues();
                        PropertyDefinition.PropertyValueType propertyValueType = propertydefinition.getPropertyValueType();

                        if (propertyValues != null && propertyValueType != null){
                            if (propertydefinition.getIsReadOnly()!=null
                                    && PropertyDefinition.IsReadOnly.ReadOnly == propertydefinition.getIsReadOnly()){
                                if (propertydefinition.getIsRequired().getIntValue() == 1){
                                    errorInfo = "The property "+ property.getPropertyName().getValue()+" is readonly, can not be written.";
                                    break;
                                }
                            }
                            else if (propertyValueType.getIntValue() == 0 && !(propertyValues.getIntValue() == null
                                    && propertyValues.getStringValue() != null && propertyValues.getRangeValue() == null)){
                                errorInfo =  "The property value type "+property.getPropertyName().getValue()+" should be string";
                                break;
                            }
                            else if (propertyValueType.getIntValue() == 1 && !(propertyValues.getIntValue() != null
                                    && propertyValues.getStringValue() == null && propertyValues.getRangeValue() == null)){
                                errorInfo =  "The property value type "+property.getPropertyName().getValue()+" should be integer";
                                break;
                            }
                            else if (propertyValueType.getIntValue() == 2 && !(propertyValues.getIntValue() == null
                                    && propertyValues.getStringValue() == null && propertyValues.getRangeValue() != null)){
                                errorInfo =  "The property value type "+property.getPropertyName().getValue()+" should be range";
                                break;
                            }
                        }
                    }
                }

                if (!propertyHasDefine){
                    errorInfo = "The property "+property.getPropertyName().getValue()+" has not been defined.";
                }
            }
        }

        if (errorInfo == null){
            Boolean requiredProperty = false;
            for (PropertyDefinition propertyDefinition : nodePropertyDefinitions){
                if (propertyDefinition.getIsRequired()!=null){
                    if (propertyDefinition.getIsRequired().getIntValue() ==0){
                        for (Property property: nodeProperties){
                            if (property.getPropertyName().equals(propertyDefinition.getPropertyName())){
                                requiredProperty = true;
                            }
                        }
                        if (!requiredProperty){
                            errorInfo = "The required property "+ propertyDefinition.getPropertyName().getValue() + " is not included in the intent.";
                            break;
                        }
                    }
                }
            }
        }
        return errorInfo;
    }

    private String checkPredefine(List<Property> nodeProperties){
        String errorInfo = null;
        for (Property property : nodeProperties){
            if (property.getPropertyName().getValue().equals(NEMOConstants.mac_address)){
                List<StringValue> stringValues = property.getPropertyValues().getStringValue();
                Boolean legalValue = true;
                for (StringValue stringValue : stringValues){
                   legalValue = valueCheck.checkMac(stringValue.getValue());
                }
                if (!legalValue){
                    errorInfo = "The " + NEMOConstants.mac_address + " is not legal.";
                }
            }
            if (property.getPropertyName().getValue().equals(NEMOConstants.ip_address)){
                List<StringValue> stringValues = property.getPropertyValues().getStringValue();
                Boolean legalValue = true;
                for (StringValue stringValue : stringValues){
                    if (stringValue.getValue().contains("/")){
                        legalValue = valueCheck.checkIpPrefix(stringValue.getValue());
                    }
                    else {
                        legalValue = valueCheck.checkIpAddress(stringValue.getValue());
                    }
                }
                if (!legalValue){
                    errorInfo = "The " + NEMOConstants.ip_address + " is not legal.";
                }
            }
            if (property.getPropertyName().getValue().equals(NEMOConstants.gateway_ip)){
                List<StringValue> stringValues = property.getPropertyValues().getStringValue();
                Boolean legalValue = true;
                for (StringValue stringValue : stringValues){
                    legalValue = valueCheck.checkIpAddress(stringValue.getValue());
                }
                if (!legalValue){
                    errorInfo = "The " + NEMOConstants.gateway_ip + " is not legal.";
                }
            }
            if (property.getPropertyName().getValue().equals(NEMOConstants.ac_info_network)){
                List<StringValue> stringValues = property.getPropertyValues().getStringValue();
                Boolean legalValue = true;
                for (StringValue stringValue : stringValues){
                   if (!(stringValue.getValue().equals(NEMOConstants.layer2)||stringValue.getValue().equals(NEMOConstants.layer3))){
                       errorInfo = "The " + NEMOConstants.ac_info_network + " is not legal.";
                   }
                }
            }
            if (property.getPropertyName().getValue().equals(NEMOConstants.operating_mode)){
                List<StringValue> stringValues = property.getPropertyValues().getStringValue();
                Boolean legalValue = true;
                for (StringValue stringValue : stringValues){
                    if (!(stringValue.getValue().equals(NEMOConstants.layer2)||stringValue.getValue().equals(NEMOConstants.layer3))){
                        errorInfo = "The " + NEMOConstants.operating_mode + " is not legal.";
                    }
                }
            }
        }
        return errorInfo;
    }
}
