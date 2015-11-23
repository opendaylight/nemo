/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.user.vnspacemanager.syntaxcheck;

import java.util.List;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Node;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.NodeDefinitions;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.node.definitions.NodeDefinition;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.node.instance.Property;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.definitions.PropertyDefinition;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.PropertyValues;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

/**
 * Created by z00293636 on 2015/9/2.
 */
public class NodeDefinitionCheck {

    private DataBroker dataBroker;
    private List<NodeDefinition> nodeDefinitionList;
    private static final Logger LOG = LoggerFactory.getLogger(NodeDefinitionCheck.class);

    public NodeDefinitionCheck(DataBroker dataBroker)
    {
        this.dataBroker = dataBroker;
        nodeDefinitionList = null;
    }

    public String CheckNodeDefinition(Node node)
    {
        String errorInfo = null;
        Boolean NodeHasDefined = false;

        fetchNodeDefinitions();
        if (nodeDefinitionList != null)
        {
            for (NodeDefinition nodeDefinition : nodeDefinitionList)
            {
                if (nodeDefinition.getNodeType().equals(node.getNodeType()))
                {
                    NodeHasDefined = true;
                    List<Property> nodeProperties = node.getProperty();
                    List<PropertyDefinition> nodePropertyDefinitions = nodeDefinition.getPropertyDefinition();

                    if (nodeProperties != null && nodePropertyDefinitions == null)
                    {
                        errorInfo = "This type of node has no properties.";
                        break;
                    }
                    else if (nodeProperties != null && nodePropertyDefinitions != null)
                    {
                        errorInfo = checkProperty(nodeProperties, nodeDefinition.getPropertyDefinition());
                        if (errorInfo != null)
                        {
                            break;
                        }
                    }
                }
            }
        }

        if (!NodeHasDefined)
        {
            errorInfo = "This type of Node has not been defined.";
        }
        return errorInfo;
    }

    private String checkProperty(List<Property> nodeProperties, List<PropertyDefinition> nodePropertyDefinitions)
    {
        Boolean propertyHasDefine = false;
        String errorInfo = null;

        for (Property property : nodeProperties)
        {
            if (errorInfo != null)
            {
                break;
            }
            else
            {
                for (PropertyDefinition propertydefinition : nodePropertyDefinitions)
                {
                    if (property.getPropertyName().equals(propertydefinition.getPropertyName()))
                    {
                        propertyHasDefine = true;
                        PropertyValues propertyValues = property.getPropertyValues();
                        PropertyDefinition.PropertyValueType propertyValueType = propertydefinition.getPropertyValueType();

                        if (propertyValues != null && propertyValueType != null)
                        {
                            if (propertydefinition.getIsReadOnly()!=null)
                            {
                                if (propertydefinition.getIsRequired().getIntValue() == 1)
                                {
                                    errorInfo = "The property"+ property.getPropertyName().toString()+" is readonly, can not be written.";
                                    break;
                                }
                            }
                            else if (propertyValueType.getIntValue() == 0 && !(propertyValues.getIntValue() == null && propertyValues.getStringValue() != null && propertyValues.getRangeValue() == null))
                            {
                                errorInfo =  "The property value type"+property.getPropertyName().toString()+" should be string";
                                break;
                            }
                            else if (propertyValueType.getIntValue() == 1 && !(propertyValues.getIntValue() != null && propertyValues.getStringValue() == null && propertyValues.getRangeValue() == null))
                            {
                                errorInfo =  "The property value type"+property.getPropertyName().toString()+" should be integer";
                                break;
                            }
                            else if (propertyValueType.getIntValue() == 2 && !(propertyValues.getIntValue() == null && propertyValues.getStringValue() == null && propertyValues.getRangeValue() != null))
                            {
                                errorInfo =  "The property value type"+property.getPropertyName().toString()+" should be range";
                                break;
                            }
                        }
                    }
                  }

                if (!propertyHasDefine)
                {
                    errorInfo = "The property"+property.getPropertyName().toString()+"has not been defined.";
                }
            }
        }

        if (errorInfo == null)
        {
            Boolean requiredProperty = false;
            for (PropertyDefinition propertyDefinition : nodePropertyDefinitions)
            {
                if (propertyDefinition.getIsRequired()!=null)
                {
                    if (propertyDefinition.getIsRequired().getIntValue() ==0)
                    {
                       for (Property property: nodeProperties)
                       {
                           if (property.getPropertyName().equals(propertyDefinition.getPropertyName()))
                           {
                                requiredProperty = true;
                           }
                       }
                        if (!requiredProperty)
                        {
                            errorInfo = "The required property "+ propertyDefinition.getPropertyName().toString() + "is not included in the intent.";
                            break;
                        }
                    }
                }
            }
        }
        return errorInfo;
    }

    private void fetchNodeDefinitions()
    {
        InstanceIdentifier<NodeDefinitions> nodedefinitionId = InstanceIdentifier.builder(NodeDefinitions.class).build();
        ListenableFuture<Optional<NodeDefinitions>> nodedefinitionFuture = dataBroker.newReadOnlyTransaction().read(LogicalDatastoreType.CONFIGURATION, nodedefinitionId);
        Futures.addCallback(nodedefinitionFuture, new FutureCallback<Optional<NodeDefinitions>>() {
            @Override
            public void onSuccess(Optional<NodeDefinitions> result)
            {
                setNodeDefinitionList(result.get().getNodeDefinition());
                return;
            }

            @Override
            public void onFailure(Throwable t)
            {
                LOG.error("Can not read node definitions information.", t);
                return;
            }
        });
        return ;
    }

    private void setNodeDefinitionList(List<NodeDefinition> nodeDefinitionsList)
    {
        this.nodeDefinitionList = nodeDefinitionsList;
    }
}
