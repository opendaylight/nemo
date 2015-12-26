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
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.template.definitions.TemplateDefinition;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.connection.definitions.ConnectionDefinition;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.connection.instance.EndNode;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.flow.instance.MatchItem;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.match.item.definitions.MatchItemDefinition;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.node.definitions.NodeDefinition;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.node.instance.Property;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.node.instance.SubNode;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.definitions.PropertyDefinition;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.property.values.StringValue;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.definitions.ActionDefinition;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.condition.instance.ConditionSegment;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.condition.parameter.definitions.ConditionParameterDefinition;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.operation.instance.Action;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.definition.grouping.AbstractIntents;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.definition.grouping.TemplateParameter;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.definition.grouping._abstract.intents._abstract.objects.AbstractConnection;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.definition.grouping._abstract.intents._abstract.objects.AbstractFlow;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.definition.grouping._abstract.intents._abstract.objects.AbstractNode;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.definition.grouping._abstract.intents._abstract.operations.AbstractOperation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by z00293636 on 2015/12/2.
 */
public class UpdateTemplateDefinition {
    private TenantManage tenantManage;
    private GetDefinitions getDefinitions;

    public UpdateTemplateDefinition(DataBroker dataBroker, TenantManage tenantManage){
        this.tenantManage = tenantManage;
        getDefinitions = new GetDefinitions(dataBroker);
    }

    public String checkTemplateDefinition(UserId userId, TemplateDefinition templateDefinition){
        String errorInfo = null;
        Boolean templateDefined = false;

        if (tenantManage.getTempalteDefinition(userId)!=null){
            if (tenantManage.getTempalteDefinition(userId).containsKey(templateDefinition.getTemplateName())){
                templateDefined = true;
            }
        }
        if (templateDefined){
            return "The template " + templateDefinition.getTemplateName().getValue() + " has been defined.";
        }
        else {
            List<TemplateParameter> list = templateDefinition.getTemplateParameter();
            HashMap<ParameterName, TemplateParameter.ParameterValueType> map = new HashMap<ParameterName, TemplateParameter.ParameterValueType>();
            for (TemplateParameter templateParameter : list){
                map.put(templateParameter.getParameterName(), templateParameter.getParameterValueType());
            }
            AbstractIntents abstractIntents = templateDefinition.getAbstractIntents();
            if (abstractIntents.getAbstractObjects()!=null){
                if (abstractIntents.getAbstractObjects().getAbstractNode()!=null){
                    List<AbstractNode> nodeList = abstractIntents.getAbstractObjects().getAbstractNode();
                    Map<NodeId,AbstractNode> nodeMap = new HashMap<NodeId, AbstractNode>();
                    for (AbstractNode node : nodeList){
                        nodeMap.put(node.getNodeId(),node);
                    }
                    for (AbstractNode node : nodeMap.values()){
                        if (node.getSubNode()!=null){
                            for (SubNode subNode : node.getSubNode()){
                                if (!nodeMap.containsKey(subNode.getNodeId())){
                                    return "The sub node is not defined.";
                                }
                            }
                        }
                        errorInfo = checkNodeTemplate(node,map);
                        if (errorInfo!=null){
                            return errorInfo;
                        }
                    }
                }
                if (abstractIntents.getAbstractObjects().getAbstractConnection()!=null){
                    List<AbstractConnection> connectionList = abstractIntents.getAbstractObjects().getAbstractConnection();
                    for (AbstractConnection connection : connectionList){
                        List<EndNode> endNodeList = connection.getEndNode();
                        if (abstractIntents.getAbstractObjects().getAbstractNode()!=null){
                            List<AbstractNode> nodeList = abstractIntents.getAbstractObjects().getAbstractNode();
                            Map<NodeId,AbstractNode> nodeMap = new HashMap<NodeId, AbstractNode>();
                            for (AbstractNode node : nodeList){
                                nodeMap.put(node.getNodeId(),node);
                            }
                            for (EndNode endNode : endNodeList){
                                if (!nodeMap.containsKey(endNode.getNodeId())){
                                    return "The end node is not exist.";
                                }
                            }
                        }
                        else {
                            return "There are no end nodes exist.";
                        }
                        errorInfo = checkConnectionTemplate(connection, map);
                        if (errorInfo!=null){
                            return errorInfo;
                        }
                    }
                }
                if (abstractIntents.getAbstractObjects().getAbstractFlow()!=null){
                    List<AbstractFlow> flowList = abstractIntents.getAbstractObjects().getAbstractFlow();
                    for (AbstractFlow flow : flowList){
                        errorInfo = checkFlowTemplate(flow,map);
                        if (errorInfo!=null){
                            return errorInfo;
                        }
                    }
                }
            }
            if (abstractIntents.getAbstractOperations()!=null){
                if (abstractIntents.getAbstractOperations().getAbstractOperation()!=null){
                    List<AbstractOperation> operationList = abstractIntents.getAbstractOperations().getAbstractOperation();
                    for (AbstractOperation operation : operationList){
                        ObjectId objectId = operation.getTargetObject();
                        Boolean targetExist = false;
                        if (abstractIntents.getAbstractObjects()!=null){
                            if (abstractIntents.getAbstractObjects().getAbstractNode()!=null) {
                                List<AbstractNode> nodeList = abstractIntents.getAbstractObjects().getAbstractNode();
                                Map<NodeId, AbstractNode> nodeMap = new HashMap<NodeId, AbstractNode>();
                                for (AbstractNode node : nodeList) {
                                    nodeMap.put(node.getNodeId(), node);
                                }
                                if (nodeMap.containsKey(new NodeId(objectId))){
                                    targetExist = true;
                                }
                            }
                            if (abstractIntents.getAbstractObjects().getAbstractConnection()!=null){
                                List<AbstractConnection> connectionList = abstractIntents.getAbstractObjects().getAbstractConnection();
                                Map<ConnectionId, AbstractConnection> connectionMap = new HashMap<ConnectionId, AbstractConnection>();
                                for (AbstractConnection connection : connectionList){
                                    connectionMap.put(connection.getConnectionId(),connection);
                                }
                                if (connectionMap.containsKey(new ConnectionId(objectId))){
                                    targetExist = true;
                                }
                            }
                            if (abstractIntents.getAbstractObjects().getAbstractFlow()!=null) {
                                List<AbstractFlow> flowList = abstractIntents.getAbstractObjects().getAbstractFlow();
                                Map<FlowId, AbstractFlow> flowMap = new HashMap<FlowId, AbstractFlow>();
                                for (AbstractFlow flow : flowList){
                                    flowMap.put(flow.getFlowId(),flow);
                                }
                                if (flowMap.containsKey(new FlowId(objectId))){
                                    targetExist = true;
                                }
                            }
                        }
                        else {
                            return "The target is not exist.";
                        }

                        if (!targetExist){
                            return "The target is not exist.";
                        }
                        errorInfo = checkOperationTemplate(operation, map);
                        if (errorInfo!=null){
                            return errorInfo;
                        }
                    }
                }
            }
            if (errorInfo == null){
                tenantManage.setTemplateDefinition(userId,templateDefinition.getTemplateName(),templateDefinition);
            }

        }
        return errorInfo;
    }

    private String checkNodeTemplate(AbstractNode node, Map<ParameterName, TemplateParameter.ParameterValueType> parameterValueTypeMap){
        String errorInfo = null;
        Map<NodeType, NodeDefinition> nodeDefinitions = getDefinitions.getNodeDefinition();
        if (nodeDefinitions.containsKey(node.getNodeType())){
            NodeDefinition nodeDefinition = nodeDefinitions.get(node.getNodeType());
            List<PropertyDefinition> propertyDefinitions = nodeDefinition.getPropertyDefinition();
            Map<PropertyName, PropertyDefinition> nodePropertyDefinition = new HashMap<PropertyName, PropertyDefinition>();
            if (propertyDefinitions!=null){
                for (PropertyDefinition propertyDefinition : propertyDefinitions){
                    nodePropertyDefinition.put(propertyDefinition.getPropertyName(),propertyDefinition);
                }
            }

            if (node.getProperty()!=null){
                for (Property property : node.getProperty()){
                    if (nodePropertyDefinition.containsKey(property.getPropertyName())){
                        PropertyDefinition.PropertyValueType type = nodePropertyDefinition.get(property.getPropertyName()).getPropertyValueType();
                       if (type.getIntValue()==0){
                           List<StringValue> stringValues = property.getPropertyValues().getStringValue();
                           for (StringValue stringValue : stringValues){
                                TemplateParameter.ParameterValueType valueType = parameterValueTypeMap.get(new ParameterName(stringValue.getValue()));
                               if (valueType!=null){
                                   if (type.getIntValue()!= valueType.getIntValue()){
                                       return  "The property " + property.getPropertyName().getValue() + " type is not right.";
                                   }
                               }
                            }
                       }
                    }
                    else {
                        errorInfo = "The property name " + property.getPropertyName().getValue() + " is not defined.";
                        return errorInfo;
                    }
                }
            }
        }
        else {
            errorInfo = "The node type " + node.getNodeType().getValue() + " is not defined.";
            return errorInfo;
        }
        return errorInfo;
    }

    private String checkConnectionTemplate(AbstractConnection connection,  Map<ParameterName, TemplateParameter.ParameterValueType> parameterValueTypeMap){
        Map<ConnectionType, ConnectionDefinition> connDefinitions = getDefinitions.getConnectionDefinition();
        if (connDefinitions.containsKey(connection.getConnectionType())){
            ConnectionDefinition connectionDefinition = connDefinitions.get(connection.getConnectionType());
            List<PropertyDefinition> propertyDefinitions = connectionDefinition.getPropertyDefinition();
            Map<PropertyName, PropertyDefinition> connPropertyDefinition = new HashMap<PropertyName, PropertyDefinition>();
            if (propertyDefinitions != null){
                for (PropertyDefinition propertyDefinition : propertyDefinitions){
                    connPropertyDefinition.put(propertyDefinition.getPropertyName(),propertyDefinition);
                }
            }

            if (connection.getProperty()!=null){
                for (org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.connection.instance.Property property : connection.getProperty()){
                    if (connPropertyDefinition.containsKey(property.getPropertyName())){
                        PropertyDefinition.PropertyValueType type = connPropertyDefinition.get(property.getPropertyName()).getPropertyValueType();
                        if (type.getIntValue()==0){
                            List<StringValue> stringValues = property.getPropertyValues().getStringValue();
                            for (StringValue stringValue : stringValues){
                                TemplateParameter.ParameterValueType valueType = parameterValueTypeMap.get(new ParameterName(stringValue.getValue()));
                                if (valueType!=null){
                                    if (type.getIntValue()!= valueType.getIntValue()){
                                        return "The property " + property.getPropertyName().getValue() + " type is not right.";
                                    }
                                }
                            }
                        }
                    }
                    else {
                        return "The property name " + property.getPropertyName().getValue() + " is not defined.";
                    }
                }
            }
        }
        else {
            return "The connection type " + connection.getConnectionType().getValue() + " is not defined.";
        }
        return null;
    }

    private String checkFlowTemplate(AbstractFlow flow, Map<ParameterName, TemplateParameter.ParameterValueType> parameterValueTypeMap){
        Map<MatchItemName, MatchItemDefinition> matchItemDefinitionMap = getDefinitions.getMatchItemDefinition();
        List<MatchItem> matchItemList = flow.getMatchItem();
        for (MatchItem matchItem : matchItemList){
            if (matchItemDefinitionMap.containsKey(matchItem.getMatchItemName())){
                MatchItemDefinition matchItemDefinition = matchItemDefinitionMap.get(matchItem.getMatchItemName());
                MatchItemDefinition.MatchItemValueType type = matchItemDefinition.getMatchItemValueType();

                if (type.getIntValue()==0){
                   String stringValues = matchItem.getMatchItemValue().getStringValue();
                    TemplateParameter.ParameterValueType valueType = parameterValueTypeMap.get(new ParameterName(stringValues));
                    if (valueType!=null){
                        if (type.getIntValue() != valueType.getIntValue()){
                            return "The match item " + stringValues+" type is not right.";
                        }
                    }
                }
            }
            else {
                return "The match item " + matchItem.getMatchItemName().getValue() +" is not defined.";
            }
        }
        return null;
    }

    private String checkOperationTemplate(AbstractOperation operation, Map<ParameterName, TemplateParameter.ParameterValueType> parameterValueTypeMap){
        Map<ParameterName, ConditionParameterDefinition> conditionParameterDefinitionMap = getDefinitions.getConditionParameterDefinition();
        Map<ActionName, ActionDefinition> actionDefinitionMap = getDefinitions.getActionDefinition();
        List<ConditionSegment> conditionSegmentList = operation.getConditionSegment();
        List<Action> actionList = operation.getAction();
        if (conditionSegmentList!=null){
            for (ConditionSegment conditionSegment : conditionSegmentList){
                if (conditionParameterDefinitionMap.containsKey(conditionSegment.getConditionParameterName())){
                    if (conditionSegment.getConditionParameterTargetValue()!=null){
                        ConditionParameterDefinition definition = conditionParameterDefinitionMap.get(conditionSegment.getConditionParameterName());
                        ConditionParameterDefinition.ParameterValueType type = definition.getParameterValueType();

                        if (type.getIntValue()==0){
                            String valuePrameter = conditionSegment.getConditionParameterTargetValue().getStringValue();
                            TemplateParameter.ParameterValueType valueType = parameterValueTypeMap.get(new ParameterName(valuePrameter));
                            if (valueType!=null){
                                if (type.getIntValue()!=valueType.getIntValue()){
                                    return "The condition " + conditionSegment.getConditionParameterName().getValue() +" type is not right.";
                                }
                            }
                        }
                    }
                }
                else {
                    return "The Condition " + conditionSegment.getConditionParameterName().getValue() + " is not defined.";
                }
            }
        }
        if (actionList!=null){
            for (Action action : actionList){
                if (actionDefinitionMap.containsKey(action.getActionName())){
                    if (action.getParameterValues()!=null){
                        ActionDefinition actionDefinition = actionDefinitionMap.get(action.getActionName());
                        ActionDefinition.ParameterValueType type = actionDefinition.getParameterValueType();

                        if (type.getIntValue()==0){
                            List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.StringValue> stringValues = action.getParameterValues().getStringValue();
                            for (org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.StringValue stringValue : stringValues){
                                TemplateParameter.ParameterValueType valueType = parameterValueTypeMap.get(new ParameterName(stringValue.getValue()));
                                if (valueType!=null){
                                    if (type.getIntValue()!=valueType.getIntValue()){
                                        return "The action " + action.getActionName().getValue() +" type is not right.";
                                    }
                                }
                            }
                        }
                    }
                }
                else {
                    return "The action " + action.getActionName().getValue() + " is not defined.";
                }
            }
        }
        return null;
    }
}
