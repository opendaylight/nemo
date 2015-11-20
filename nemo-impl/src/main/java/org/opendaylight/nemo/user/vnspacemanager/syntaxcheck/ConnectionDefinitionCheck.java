/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.user.vnspacemanager.syntaxcheck;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.PropertyName;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Connection;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.ConnectionDefinitions;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.connection.definitions.ConnectionDefinition;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.connection.instance.Property;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.definitions.PropertyDefinition;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.PropertyValues;
import org.opendaylight.controller.md.sal.binding.api.WriteTransaction;
import org.opendaylight.controller.md.sal.binding.api.ReadOnlyTransaction;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.yangtools.yang.common.RpcResult;
import org.opendaylight.yangtools.yang.common.RpcResultBuilder;
import org.opendaylight.yangtools.yang.common.RpcError.ErrorType;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import com.google.common.base.Optional;
import com.google.common.base.Function;
import com.google.common.util.concurrent.CheckedFuture;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

/**
 * Created by z00293636 on 2015/9/2.
 */
public class ConnectionDefinitionCheck {

    private DataBroker dataBroker;
    private List<ConnectionDefinition> connectionDefinitionList;
    private static final Logger LOG = LoggerFactory.getLogger(ConnectionDefinitionCheck.class);

    public ConnectionDefinitionCheck(DataBroker dataBroker)
    {
        this.dataBroker = dataBroker;
        connectionDefinitionList = null;
    }

    public String CheckConnectionDefinition(Connection connection)
    {
        fetchConnectionDefinitionList();
        boolean ConnectionHasDefined = false;
        String errorInfo = null;

        if (connectionDefinitionList != null)
        {
            for (ConnectionDefinition connectionDefinition : connectionDefinitionList)
            {
                if (connectionDefinition.getConnectionType().equals(connection.getConnectionType()))
                {
                    ConnectionHasDefined = true;
                    List<Property> connectionProperties = connection.getProperty();
                    List<PropertyDefinition> propertyDefinitions = connectionDefinition.getPropertyDefinition();

                    if (connectionProperties != null && propertyDefinitions ==null)
                    {
                        errorInfo = "There are no properties for this type of connection.";
                        break;
                    }
                    else if (connectionProperties != null && propertyDefinitions != null)
                    {
                        errorInfo = CheckProperty(connection.getProperty(), connectionDefinition.getPropertyDefinition());
                        if (errorInfo != null)
                        {
                            break;
                        }
                    }

                }
            }
        }

        if (!ConnectionHasDefined)
        {
            return "This type of connection has not been defined.";
        }
        return errorInfo;
    }

    private String CheckProperty(List<Property> connectionProperty, List<PropertyDefinition> propertyDefinitionList )
    {
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

    private void fetchConnectionDefinitionList()
    {
        InstanceIdentifier<ConnectionDefinitions> connectiondefinitionId = InstanceIdentifier.builder(ConnectionDefinitions.class).build();
        ListenableFuture<Optional<ConnectionDefinitions>> connectiondefinitionFuture = dataBroker.newReadOnlyTransaction().read(LogicalDatastoreType.CONFIGURATION, connectiondefinitionId);
        Futures.addCallback(connectiondefinitionFuture, new FutureCallback<Optional<ConnectionDefinitions>>() {
            @Override
            public void onSuccess(Optional<ConnectionDefinitions> result) {
                setConnectionDefinitionList(result.get().getConnectionDefinition());
                return;
            }

            @Override
            public void onFailure(Throwable t) {
                LOG.error("Can not read connection definition information.", t);

                return;
            }
        });
        return;
    }

    private void setConnectionDefinitionList(List<ConnectionDefinition> connectionDefinitionList)
    {
        this.connectionDefinitionList = connectionDefinitionList;
    }

}
