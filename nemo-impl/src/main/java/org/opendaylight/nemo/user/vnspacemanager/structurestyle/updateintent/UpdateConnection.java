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
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.ConnectionType;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Connection;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.connection.definitions.ConnectionDefinition;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.connection.instance.EndNode;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.connection.instance.Property;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.definitions.PropertyDefinition;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.PropertyValues;

import java.util.List;
import java.util.Map;

/**
 * Created by z00293636 on 2015/8/31.
 */
public class UpdateConnection {
    private TenantManage tenantManage;
    private GetDefinitions getDefinitions;

    public UpdateConnection(DataBroker dataBroker, TenantManage tenantManage){
        this.tenantManage = tenantManage;
        getDefinitions = new GetDefinitions(dataBroker);
    }

    public String ConnectionHandling(UserId userId, Connection connection){
        String errorInfo = null;
        errorInfo =checkDefinition(connection);
        if (errorInfo != null){
            return errorInfo;
        }
        else {
            errorInfo = checkInstance(userId,connection);
            if (errorInfo != null){
                return errorInfo;
            }
            else {
                tenantManage.setConnection(userId,connection.getConnectionId(),connection);
            }
        }
        return errorInfo;
    }

    private String checkInstance(UserId userId, Connection connection){
        if (tenantManage.getConnection(userId)!=null){
            if (tenantManage.getConnection(userId).containsKey(connection.getConnectionId())){
                Connection connExist = tenantManage.getConnection(userId).get(connection.getConnectionId());
                if (!connExist.getConnectionName().equals(connection.getConnectionName())){
                    return "The connection name should not be changed.";
                }
                if (!connExist.getConnectionType().equals(connection.getConnectionType())){
                    return "The connection type should not be changed.";
                }
                if (!connExist.getEndNode().equals(connection.getEndNode())){
                    return "The connection end node should not be changed.";
                }
            }
        }

        if (tenantManage.getConnectionDataStore(userId)!=null){
            if (tenantManage.getConnectionDataStore(userId).containsKey(connection.getConnectionId())){
                Connection connExist = tenantManage.getConnectionDataStore(userId).get(connection.getConnectionId());
                if (!connExist.getConnectionName().equals(connection.getConnectionName())){
                    return "The connection name should not be changed.";
                }
                if (!connExist.getConnectionType().equals(connection.getConnectionType())){
                    return "The connection type should not be changed.";
                }
                if (!connExist.getEndNode().equals(connection.getEndNode())){
                    return "The connection end node should not be changed.";
                }
            }
        }

        if (connection.getEndNode()!=null){
            List<EndNode> endNodeList = connection.getEndNode();
            for (EndNode endNode : endNodeList){
                Boolean endNodeExist = false;
                if (tenantManage.getNode(userId)!=null){
                    if (tenantManage.getNode(userId).containsKey(endNode.getNodeId())){
                        endNodeExist = true;
                    }
                }
                if (tenantManage.getNodeDataStore(userId)!=null){
                    if (tenantManage.getNodeDataStore(userId).containsKey(endNode.getNodeId())){
                        endNodeExist = true;
                    }
                }
                if (!endNodeExist){
                    return "The endnode "+ endNode.getNodeId().getValue() +" is not exist;";
                }
            }
        }
        return null;
    }

    private String checkDefinition(Connection connection){
        String errorInfo = null;
        Map<ConnectionType, ConnectionDefinition> connectionDefinitionMap = getDefinitions.getConnectionDefinition();
        if (connectionDefinitionMap.isEmpty()){
            return "This type of connection has not been defined.";
        }
        else {
            if (connectionDefinitionMap.containsKey(connection.getConnectionType())){
                List<Property> connectionProperties = connection.getProperty();
                List<PropertyDefinition> propertyDefinitions = connectionDefinitionMap.get(connection.getConnectionType()).getPropertyDefinition();
                if (connectionProperties != null && propertyDefinitions ==null)
                {
                    errorInfo = "There are no properties for this type of connection.";
                }
                else if (connectionProperties != null && propertyDefinitions != null)
                {
                    errorInfo = checkProperty(connection.getProperty(), propertyDefinitions);
                }
            }
            else {
                return "This type of connection has not been defined.";
            }
        }
        return errorInfo;
    }

    private String checkProperty(List<Property> connectionProperty, List<PropertyDefinition> propertyDefinitionList ){
        String errorInfo = null;
        for (Property property : connectionProperty)
        {
            Boolean properyHasDefine = false;
            if (errorInfo != null)
            {
                break;
            }
            else
            {
                for (PropertyDefinition propertyDefinition : propertyDefinitionList)
                {
                    if (property.getPropertyName().equals(propertyDefinition.getPropertyName()))
                    {
                        properyHasDefine = true;
                        PropertyValues propertyValues = property.getPropertyValues();
                        PropertyDefinition.PropertyValueType propertyValueType = propertyDefinition.getPropertyValueType();

                        if (propertyValues != null && propertyValueType != null)
                        {
                            if (propertyDefinition.getIsReadOnly()!=null
                                    && PropertyDefinition.IsReadOnly.ReadOnly == propertyDefinition.getIsReadOnly())
                            {
                                if (propertyDefinition.getIsRequired().getIntValue() == 1)
                                {
                                    errorInfo = "The property value type of " + property.getPropertyName().toString() + " is read only.";
                                    break;
                                }
                            }
                            else
                            {
                                if (propertyValueType.getIntValue() == 0 && !(propertyValues.getIntValue() == null && propertyValues.getStringValue() != null && propertyValues.getRangeValue() == null)) {
                                    errorInfo = "The property value type of " + property.getPropertyName().toString() + " should be string.";
                                    break;
                                }
                                if (propertyValueType.getIntValue() == 1 && !(propertyValues.getIntValue() != null && propertyValues.getStringValue() == null && propertyValues.getRangeValue() == null)) {
                                    errorInfo = "The property value type of" + property.getPropertyName().toString() + " should be integer.";
                                    break;
                                }
                                if (propertyValueType.getIntValue() == 2 && !(propertyValues.getIntValue() == null && propertyValues.getStringValue() == null && propertyValues.getRangeValue() != null)) {
                                    errorInfo = "The property value type of" + property.getPropertyName().toString() + " should be range.";
                                    break;
                                }
                            }
                        }
                    }
                }
                if (!properyHasDefine) {
                    errorInfo = "This type of property" + property.getPropertyName().toString() + " has not been defined.";
                }
            }
        }

        if (errorInfo == null)
        {
            Boolean requiredProperty = false;
            for (PropertyDefinition propertyDefinition : propertyDefinitionList)
            {
                if (propertyDefinition.getIsRequired()!=null)
                {
                    if (propertyDefinition.getIsRequired().getIntValue() == 0)
                    {
                        for (Property property : connectionProperty)
                        {
                            if (property.getPropertyName().equals(propertyDefinition.getPropertyName()))
                            {
                                requiredProperty = true;
                            }
                        }
                        if (!requiredProperty)
                        {
                            errorInfo = "The required property" + propertyDefinition.getPropertyName().toString() + "is not included in the intent.";
                        }
                    }
                }
            }
        }
        return errorInfo;
    }
}
