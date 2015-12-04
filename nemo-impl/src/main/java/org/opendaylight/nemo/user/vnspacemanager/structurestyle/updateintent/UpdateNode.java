/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.user.vnspacemanager.structurestyle.updateintent;

import com.google.common.base.Optional;
import com.google.common.util.concurrent.CheckedFuture;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.WriteTransaction;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.controller.md.sal.common.api.data.TransactionCommitFailedException;
import org.opendaylight.nemo.user.tenantmanager.TenantManage;
import org.opendaylight.nemo.user.vnspacemanager.languagestyle.NEMOConstants;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.NodeType;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.Users;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.Objects;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Node;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.NodeBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.NodeKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.User;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.UserKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.NodeDefinitions;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.node.definitions.NodeDefinition;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.node.instance.Property;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.definitions.PropertyDefinition;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.PropertyValues;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.property.values.StringValue;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by z00293636 on 2015/8/31.
 */
public class UpdateNode {

    private DataBroker dataBroker;
    private TenantManage tenantManage;
    private final SettableFuture<List<NodeDefinition>> nodeDefinitionListFuture = SettableFuture.create();
    private ValueCheck valueCheck;
    private static final Logger LOG = LoggerFactory.getLogger(UpdateNode.class);

    public UpdateNode(DataBroker dataBroker,TenantManage tenantManage){
        this.dataBroker = dataBroker;
        this.tenantManage = tenantManage;
        valueCheck = new ValueCheck();
    }


    public String NodeHandling(UserId userId,Node node){
        String errorInfo = null;
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
                WriteTransaction t = dataBroker.newWriteOnlyTransaction();
                if (userId != null && node.getNodeId() !=null){
                    Node node1 = new NodeBuilder(node).build();
                    NodeKey nodeKey = new NodeKey(node.getKey());
                    UserKey userKey = new UserKey(userId);

                    InstanceIdentifier<Node> nodeid = InstanceIdentifier.builder(Users.class).child(User.class, userKey).child(Objects.class).child(Node.class,nodeKey).build();
                    t.put(LogicalDatastoreType.CONFIGURATION, nodeid, node1,true);
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
        }
        return errorInfo;
    }

    private String checkInstance(UserId userId, Node node){
        String errorInfo = null;
        tenantManage.fetchVNSpace(userId);
        User user = tenantManage.getUser();

        if (user!=null){
            if (user.getObjects()!=null){
                if (user.getObjects().getNode()!=null){
                    List<Node> nodeList = user.getObjects().getNode();
                    for (Node node1 : nodeList){
                        if (node1.getNodeId().equals(node.getNodeId())){
                            if ( !node1.getNodeName().equals(node.getNodeName())) {
                                errorInfo = "The node name should not be changed.";
                                break;
                            }
                            if  (!node1.getNodeType().equals(node.getNodeType())){
                                errorInfo = "The node type should not be changed.";
                                break;
                            }
                        }
                    }
                }
            }
        }
        return errorInfo;
    }

    private String checkDefinition(Node node){
        String errorInfo = null;
        fetchNodeDefinitions();
        final Map<NodeType, NodeDefinition> map = getNodeDefinitions();
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
                            errorInfo = "The required property "+ propertyDefinition.getPropertyName().getValue() + "is not included in the intent.";
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

    private void fetchNodeDefinitions(){
        InstanceIdentifier<NodeDefinitions> nodedefinitionId = InstanceIdentifier.builder(NodeDefinitions.class).build();
        ListenableFuture<Optional<NodeDefinitions>> nodedefinitionFuture = dataBroker.newReadOnlyTransaction().read(LogicalDatastoreType.CONFIGURATION, nodedefinitionId);
        Futures.addCallback(nodedefinitionFuture, new FutureCallback<Optional<NodeDefinitions>>() {
            @Override
            public void onSuccess(Optional<NodeDefinitions> result){
               setNodeDefinitionListFuture(result.get().getNodeDefinition());
            }

            @Override
            public void onFailure(Throwable t){
                LOG.error("Can not read node definitions information.", t);
            }
        });
        return ;
    }

    private void setNodeDefinitionListFuture(List<NodeDefinition> nodeDefinitionList) {
        this.nodeDefinitionListFuture.set(nodeDefinitionList);
    }

   private List<NodeDefinition> getNodeDefinitionList(){
       try{
           return nodeDefinitionListFuture.get(1, TimeUnit.SECONDS);
       }catch (InterruptedException | ExecutionException | TimeoutException e) {
           LOG.error("Cannot read role information.", e);
           return null;
       }
   }

    private Map<NodeType, NodeDefinition> getNodeDefinitions(){
        final Map<NodeType, NodeDefinition> nodeDefinitionMap = new HashMap<NodeType, NodeDefinition>();
        final List<NodeDefinition> nodeDefinitionList = getNodeDefinitionList();
        if (nodeDefinitionList!=null){
            for (NodeDefinition nodeDefinition : nodeDefinitionList){
                nodeDefinitionMap.put(nodeDefinition.getNodeType(),nodeDefinition);
            }
        }
        return nodeDefinitionMap;
    }

}

