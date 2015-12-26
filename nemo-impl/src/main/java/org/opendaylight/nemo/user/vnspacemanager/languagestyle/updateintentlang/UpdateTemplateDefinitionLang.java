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
import org.opendaylight.nemo.user.vnspacemanager.structurestyle.updateintent.GetDefinitions;
import org.opendaylight.nemo.user.vnspacemanager.structurestyle.updateintent.UpdateTemplateDefinition;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.hosts.PhysicalHost;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.PhysicalHostId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.PhysicalHostName;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.template.definitions.TemplateDefinitionBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.template.definitions.TemplateDefinitionKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.connection.instance.EndNode;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.connection.instance.EndNodeBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.connection.instance.EndNodeKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.flow.instance.MatchItem;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.flow.instance.MatchItemBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.flow.instance.MatchItemKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.match.item.instance.MatchItemValueBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.node.instance.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.PropertyValuesBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.property.values.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.ParameterValuesBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.condition.instance.ConditionSegment;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.condition.instance.ConditionSegmentBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.condition.instance.ConditionSegmentKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.condition.instance.condition.segment.ConditionParameterTargetValueBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.operation.instance.Action;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.operation.instance.ActionBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.operation.instance.ActionKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.definition.grouping.AbstractIntentsBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.definition.grouping.TemplateParameter;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.definition.grouping.TemplateParameterBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.definition.grouping.TemplateParameterKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.definition.grouping._abstract.intents.AbstractObjectsBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.definition.grouping._abstract.intents.AbstractOperationsBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.definition.grouping._abstract.intents._abstract.objects.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.definition.grouping._abstract.intents._abstract.operations.AbstractOperation;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.definition.grouping._abstract.intents._abstract.operations.AbstractOperationBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.definition.grouping._abstract.intents._abstract.operations.AbstractOperationKey;

import java.util.*;

/**
 * Created by z00293636 on 2015/12/7.
 */
public class UpdateTemplateDefinitionLang {
    private UpdateTemplateDefinition definition;
    private Map<NodeName, AbstractNode> abstractNodes;
    private Map<ConnectionName, AbstractConnection> abstractConnections;
    private Map<FlowName, AbstractFlow> abstractFlows;
    private Map<OperationName, AbstractOperation> abstractOperations;
    private GetDefinitions getDefinitions;

    public UpdateTemplateDefinitionLang(DataBroker dataBroker, TenantManage tenantManage){
        definition = new UpdateTemplateDefinition(dataBroker,tenantManage);
        abstractNodes = new HashMap<NodeName, AbstractNode>();
        abstractConnections = new HashMap<ConnectionName, AbstractConnection>();
        abstractFlows = new HashMap<FlowName, AbstractFlow>();
        abstractOperations = new HashMap<OperationName, AbstractOperation>();
        getDefinitions = new GetDefinitions(dataBroker);
    }

    public String templateDefinitionLang(UserId userId, String templateName, LinkedHashMap<String, String> parameters){
        TemplateDefinitionBuilder builder = new TemplateDefinitionBuilder();
        builder.setKey(new TemplateDefinitionKey(new TemplateName(templateName)))
                .setTemplateName(new TemplateName(templateName));

        List<TemplateParameter> templateParameters = new LinkedList<TemplateParameter>();
        for (String value : parameters.keySet()){
            TemplateParameterBuilder parameterBuilder = new TemplateParameterBuilder();
            parameterBuilder.setKey(new TemplateParameterKey(new ParameterName(value)))
                            .setParameterName(new ParameterName(value));

            if (parameters.get(value).equals(NEMOConstants.string)){
                parameterBuilder.setParameterValueType(TemplateParameter.ParameterValueType.String);
            }
            else if (parameters.get(value).equals(NEMOConstants.integer)){
                parameterBuilder.setParameterValueType(TemplateParameter.ParameterValueType.Int);
            }
            else if (parameters.get(value).equals(NEMOConstants.range)){
                parameterBuilder.setParameterValueType(TemplateParameter.ParameterValueType.Range);
            }
            templateParameters.add(parameterBuilder.build());
        }
        builder.setTemplateParameter(templateParameters);

        AbstractIntentsBuilder intentsBuilder = new AbstractIntentsBuilder();
        AbstractObjectsBuilder objectsBuilder = new AbstractObjectsBuilder();
        if (!abstractNodes.isEmpty()){
            List<AbstractNode> abstractNodeList = new LinkedList<AbstractNode>();
            for (AbstractNode node : abstractNodes.values()){
                abstractNodeList.add(node);
            }
            objectsBuilder.setAbstractNode(abstractNodeList);
        }else {
            objectsBuilder.setAbstractNode(null);
        }

        if (!abstractConnections.isEmpty()){
            List<AbstractConnection> abstractConnectionList = new LinkedList<AbstractConnection>();
            for (AbstractConnection connection : abstractConnections.values()){
                abstractConnectionList.add(connection);
            }
            objectsBuilder.setAbstractConnection(abstractConnectionList);
        }else {
            objectsBuilder.setAbstractConnection(null);
        }

        if (!abstractFlows.isEmpty()){
            List<AbstractFlow> abstractFlowList = new ArrayList<AbstractFlow>();
            for (AbstractFlow flow : abstractFlows.values()){
                abstractFlowList.add(flow);
            }
            objectsBuilder.setAbstractFlow(abstractFlowList);
        }else {
            objectsBuilder.setAbstractFlow(null);
        }

        AbstractOperationsBuilder operationsBuilder = new AbstractOperationsBuilder();
        if (!abstractOperations.isEmpty()){
            List<AbstractOperation> abstractOperationList = new LinkedList<AbstractOperation>();
            for (AbstractOperation operation : abstractOperations.values()){
                abstractOperationList.add(operation);
            }
            operationsBuilder.setAbstractOperation(abstractOperationList);
        }else {
            operationsBuilder.setAbstractOperation(null);
        }

        intentsBuilder.setAbstractObjects(objectsBuilder.build())
                      .setAbstractOperations(operationsBuilder.build());
        builder.setAbstractIntents(intentsBuilder.build());

        return definition.checkTemplateDefinition(userId,builder.build());
    }

    public String createAbstractNode(String name, String type, List<String> subnodes, LinkedHashMap<String, LinkedHashMap<String,String>> propertyList){
        AbstractNodeBuilder nodeBuilder = new AbstractNodeBuilder();
        NodeId nodeId = null;
        if (type.equals(NEMOConstants.host)){
            Map<PhysicalHostName, PhysicalHost> physicalHostMap = getDefinitions.getPhysicalHost();
            if (physicalHostMap.containsKey(new PhysicalHostName(name))){
                PhysicalHostId physicalHostId = physicalHostMap.get(new PhysicalHostName(name)).getHostId();
                nodeBuilder.setKey(new AbstractNodeKey(new AbstractNodeKey(new NodeId(physicalHostId.getValue()))))
                        .setNodeId(new NodeId(physicalHostId.getValue()));
            }
            else {
                return "The host " + name + " is not exist in physical network.";
            }
        }
        else {
            nodeId = new NodeId(UUID.randomUUID().toString());
        }

        nodeBuilder.setKey(new AbstractNodeKey(nodeId));
        nodeBuilder.setNodeId(nodeId);
        nodeBuilder.setNodeName(new NodeName(name));
        nodeBuilder.setNodeType(new NodeType(type));

        if (!subnodes.isEmpty()){
            if (abstractNodes.isEmpty()){
                return "There are no nodes.";
            }
            else {
                List<SubNode> subNodeList = new LinkedList<SubNode>();
                Long order = 0L;
                for (String nodeName : subnodes){
                    if (!abstractNodes.containsKey(new NodeName(nodeName))){
                        return "The subnode " + nodeName + " is not exist.";
                    }
                    else {
                        SubNodeBuilder subNodeBuilder = new SubNodeBuilder();
                        subNodeBuilder.setKey(new SubNodeKey(abstractNodes.get(new NodeName(nodeName)).getNodeId()))
                                        .setNodeId(abstractNodes.get(new NodeName(nodeName)).getNodeId())
                                        .setOrder(order);
                        order++;
                        subNodeList.add(subNodeBuilder.build());
                    }
                }
                nodeBuilder.setSubNode(subNodeList);
            }
        }

        if (!propertyList.isEmpty()){
            List<Property> properties = new LinkedList<Property>();
            for (String propertyName : propertyList.keySet()){
                PropertyBuilder builder = new PropertyBuilder();
                builder.setKey(new PropertyKey(new PropertyName(propertyName)))
                        .setPropertyName(new PropertyName(propertyName));

                Map<String, String> values = propertyList.get(propertyName);
                PropertyValuesBuilder valuesBuilder = new PropertyValuesBuilder();
                List<StringValue> stringValues = new LinkedList<StringValue>();
                List<IntValue> intValues = new LinkedList<IntValue>();
                RangeValue rangeValue = null;
                Long order = 0L;
                for (String value : values.keySet()){
                    if (values.get(value).equals(NEMOConstants.string)){
                        StringValueBuilder stringValueBuilder = new StringValueBuilder();
                        stringValueBuilder.setKey(new StringValueKey(order,value))
                                          .setOrder(order)
                                          .setValue(value);
                        order ++;
                        stringValues.add(stringValueBuilder.build());
                    }
                    if (values.get(value).equals(NEMOConstants.integer)){
                        IntValueBuilder intValueBuilder = new IntValueBuilder();
                        intValueBuilder.setKey(new IntValueKey(order, Long.parseLong(value)))
                                        .setOrder(order)
                                        .setValue(Long.parseLong(value));
                        order ++;
                        intValues.add(intValueBuilder.build());
                    }
                    if (values.get(value).equals(NEMOConstants.range)){
                        String[] range = value.split(",");
                        RangeValueBuilder rangeValueBuilder = new RangeValueBuilder();
                        if (Long.parseLong(range[0])<Long.parseLong(range[1])){
                            rangeValueBuilder.setMin(Long.parseLong(range[0]));
                            rangeValueBuilder.setMax(Long.parseLong(range[1]));
                        }
                        else{
                            rangeValueBuilder.setMin(Long.parseLong(range[1]));
                            rangeValueBuilder.setMax(Long.parseLong(range[0]));
                        }
                        rangeValue = rangeValueBuilder.build();
                    }
                }
                valuesBuilder.setStringValue(stringValues.isEmpty()?null:stringValues)
                             .setIntValue(intValues.isEmpty()?null:intValues)
                             .setRangeValue(rangeValue);
                builder.setPropertyValues(valuesBuilder.build());
                properties.add(builder.build());
            }
            nodeBuilder.setProperty(properties);
        }
        abstractNodes.put(new NodeName(name),nodeBuilder.build());
        return null;
    }

    public String createAbstractConnection(String name, String type, List<String> endnodes, LinkedHashMap<String, LinkedHashMap<String,String>> propertyList){
        AbstractConnectionBuilder connectionBuilder = new AbstractConnectionBuilder();
        ConnectionId connectionId = new ConnectionId(UUID.randomUUID().toString());
        connectionBuilder.setKey(new AbstractConnectionKey(connectionId))
                         .setConnectionId(connectionId)
                         .setConnectionName(new ConnectionName(name))
                         .setConnectionType(new ConnectionType(type));

        List<EndNode> endNodeList = new LinkedList<EndNode>();
        if (abstractNodes.isEmpty()){
            return "There are nodes exist.";
        }
        else {
            long order = 0L;
            for (String endNode : endnodes){
                if (!abstractNodes.containsKey(new NodeName(endNode))){
                    return "The endnode " + endNode + " is not exit.";
                }
                else {
                    EndNodeBuilder endNodeBuilder = new EndNodeBuilder();
                    endNodeBuilder.setKey(new EndNodeKey(abstractNodes.get(new NodeName(endNode)).getNodeId()))
                                  .setNodeId(abstractNodes.get(new NodeName(endNode)).getNodeId())
                                  .setOrder(order);
                    order++;
                    endNodeList.add(endNodeBuilder.build());
                }
            }
        }
        connectionBuilder.setEndNode(endNodeList.isEmpty()?null:endNodeList);

        if (!propertyList.isEmpty()){
            List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.connection.instance.Property> properties = new LinkedList<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.connection.instance.Property>();
            for (String propertyName : propertyList.keySet()){
                org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.connection.instance.PropertyBuilder builder = new org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.connection.instance.PropertyBuilder();
                builder.setKey(new org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.connection.instance.PropertyKey(new PropertyName(propertyName)))
                        .setPropertyName(new PropertyName(propertyName));

                Map<String, String> values = propertyList.get(propertyName);
                PropertyValuesBuilder valuesBuilder = new PropertyValuesBuilder();
                List<StringValue> stringValues = new LinkedList<StringValue>();
                List<IntValue> intValues = new LinkedList<IntValue>();
                RangeValue rangeValue = null;
                Long order = 0L;
                for (String value : values.keySet()){
                    if (values.get(value).equals(NEMOConstants.string)){
                        StringValueBuilder stringValueBuilder = new StringValueBuilder();
                        stringValueBuilder.setKey(new StringValueKey(order,value))
                                .setOrder(order)
                                .setValue(value);
                        order ++;
                        stringValues.add(stringValueBuilder.build());
                    }
                    if (values.get(value).equals(NEMOConstants.integer)){
                        IntValueBuilder intValueBuilder = new IntValueBuilder();
                        intValueBuilder.setKey(new IntValueKey(order, Long.parseLong(value)))
                                .setOrder(order)
                                .setValue(Long.parseLong(value));
                        order ++;
                        intValues.add(intValueBuilder.build());
                    }
                    if (values.get(value).equals(NEMOConstants.range)){
                        String[] range = value.split(",");
                        RangeValueBuilder rangeValueBuilder = new RangeValueBuilder();
                        if (Long.parseLong(range[0])<Long.parseLong(range[1])){
                            rangeValueBuilder.setMin(Long.parseLong(range[0]));
                            rangeValueBuilder.setMax(Long.parseLong(range[1]));
                        }
                        else{
                            rangeValueBuilder.setMin(Long.parseLong(range[1]));
                            rangeValueBuilder.setMax(Long.parseLong(range[0]));
                        }
                        rangeValue = rangeValueBuilder.build();
                    }
                }
                valuesBuilder.setStringValue(stringValues.isEmpty()?null:stringValues)
                        .setIntValue(intValues.isEmpty()?null:intValues)
                        .setRangeValue(rangeValue);
                builder.setPropertyValues(valuesBuilder.build());
                properties.add(builder.build());
            }
            connectionBuilder.setProperty(properties);
        }
        abstractConnections.put(new ConnectionName(name),connectionBuilder.build());
        return null;
    }

    public String createAbstractFlow(String name,LinkedHashMap<String,LinkedHashMap<String,String>> matches,LinkedHashMap<String,LinkedHashMap<String,String>> propertyList){
        AbstractFlowBuilder flowBuilder = new AbstractFlowBuilder();
        FlowId flowId = new FlowId(UUID.randomUUID().toString());
        flowBuilder.setKey(new AbstractFlowKey(flowId))
                   .setFlowId(flowId)
                   .setFlowName(new FlowName(name));

        List<MatchItem> matchItemList = new LinkedList<MatchItem>();
        for (String matchName : matches.keySet()){
            MatchItemBuilder matchItemBuilder = new MatchItemBuilder();
            matchItemBuilder.setKey(new MatchItemKey(new MatchItemName(matchName)))
                            .setMatchItemName(new MatchItemName(matchName));

            MatchItemValueBuilder valueBuilder = new MatchItemValueBuilder();
            Map<String, String> values = matches.get(matchName);
            for (String value : values.keySet()){
                if (values.get(value).equals(NEMOConstants.string)){
                    valueBuilder.setStringValue(value);
                }
                if (values.get(value).equals(NEMOConstants.integer)){
                    valueBuilder.setIntValue(Long.parseLong(value));
                }
                if (values.get(value).equals(NEMOConstants.range)){
                    String[] range = value.split(",");
                    org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.match.item.instance.match.item.value.RangeValueBuilder rangeValueBuilder = new org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.match.item.instance.match.item.value.RangeValueBuilder();
                    rangeValueBuilder.setMax(Long.parseLong(range[0]) > Long.parseLong(range[1]) ? Long.parseLong(range[0]) : Long.parseLong(range[1]));
                    rangeValueBuilder.setMin(Long.parseLong(range[0])<Long.parseLong(range[1])?Long.parseLong(range[0]):Long.parseLong(range[1]));
                    valueBuilder.setRangeValue(rangeValueBuilder.build());
                }
            }
            matchItemBuilder.setMatchItemValue(valueBuilder.build());
            matchItemList.add(matchItemBuilder.build());
        }
        flowBuilder.setMatchItem(matchItemList);

        //todo check property

        abstractFlows.put(new FlowName(name), flowBuilder.build());
        return null;
    }

    public String createAbstractOperation(String name, String target, String priority, LinkedHashMap<String,LinkedHashMap<String,String>> conditions,LinkedHashMap<String,LinkedHashMap<String,String>>actions){
        AbstractOperationBuilder operationBuilder = new AbstractOperationBuilder();
        OperationId operationId = new OperationId(UUID.randomUUID().toString());
        operationBuilder.setKey(new AbstractOperationKey(operationId))
                        .setOperationId(operationId)
                        .setOperationName(new OperationName(name))
                        .setPriority(Long.parseLong(priority));
        if (abstractNodes.containsKey(new NodeName(target))){
            operationBuilder.setTargetObject(abstractNodes.get(new NodeName(target)).getNodeId());
        }
        else if (abstractConnections.containsKey(new ConnectionName(target))){
            operationBuilder.setTargetObject(abstractConnections.get(new ConnectionName(target)).getConnectionId());
        }
        else if (abstractFlows.containsKey(new FlowName(target))){
            operationBuilder.setTargetObject(abstractFlows.get(new FlowName(target)).getFlowId());
        }

        if (!conditions.isEmpty()){
            List<ConditionSegment> conditionSegmentList = new LinkedList<ConditionSegment>();
            long order = 0;
            for (String conditionName : conditions.keySet()){
                String[] condition = conditionName.split(NEMOConstants.comma);
                ConditionSegmentBuilder conditionSegmentBuilder = new ConditionSegmentBuilder();
                ConditionSegmentId conditionSegmentId = new ConditionSegmentId(UUID.randomUUID().toString());
                conditionSegmentBuilder.setKey(new ConditionSegmentKey(conditionSegmentId))
                                       .setConditionSegmentId(conditionSegmentId)
                                       .setOrder(order);
                String preRelationOperator = condition[0];
                if (preRelationOperator == null){
                    conditionSegmentBuilder.setPrecursorRelationOperator(ConditionSegment.PrecursorRelationOperator.None);
                }
                else if (preRelationOperator.equals(NEMOConstants.not)){
                    conditionSegmentBuilder.setPrecursorRelationOperator(ConditionSegment.PrecursorRelationOperator.Not);
                }
                else if (preRelationOperator.equals(NEMOConstants.and)){
                    conditionSegmentBuilder.setPrecursorRelationOperator(ConditionSegment.PrecursorRelationOperator.And);
                }
                else if (preRelationOperator.equals(NEMOConstants.or)){
                    conditionSegmentBuilder.setPrecursorRelationOperator(ConditionSegment.PrecursorRelationOperator.Or);
                }

                String condtionName = condition[1];
                conditionSegmentBuilder.setConditionParameterName(new ConditionParameterName(condtionName));

                String matchPattern = condition[2];
                if (matchPattern.equals(NEMOConstants.equal)){
                    conditionSegmentBuilder.setConditionParameterMatchPattern(ConditionSegment.ConditionParameterMatchPattern.Equal);
                }
                else if (matchPattern.equals(NEMOConstants.not_equal)){
                    conditionSegmentBuilder.setConditionParameterMatchPattern(ConditionSegment.ConditionParameterMatchPattern.NotEqual);
                }
                else if (matchPattern.equals(NEMOConstants.greater_than)){
                    conditionSegmentBuilder.setConditionParameterMatchPattern(ConditionSegment.ConditionParameterMatchPattern.GreaterThan);
                }
                else if (matchPattern.equals(NEMOConstants.less_than)){
                    conditionSegmentBuilder.setConditionParameterMatchPattern(ConditionSegment.ConditionParameterMatchPattern.LessThan);
                }
                else if (matchPattern.equals(NEMOConstants.not_less_than)){
                    conditionSegmentBuilder.setConditionParameterMatchPattern(ConditionSegment.ConditionParameterMatchPattern.NotLessThan);
                }
                else if (matchPattern.equals(NEMOConstants.not_greater_than)){
                    conditionSegmentBuilder.setConditionParameterMatchPattern(ConditionSegment.ConditionParameterMatchPattern.NotGreaterThan);
                }
                else if (matchPattern.equals(NEMOConstants.between)){
                    conditionSegmentBuilder.setConditionParameterMatchPattern(ConditionSegment.ConditionParameterMatchPattern.Between);
                }

                Map<String, String> targetValue = conditions.get(conditionName);
                ConditionParameterTargetValueBuilder valueBuilder = new ConditionParameterTargetValueBuilder();
                for (String value : targetValue.keySet()){
                    if (targetValue.get(value).equals(NEMOConstants.string)){
                        valueBuilder.setStringValue(value);
                    }
                    else if (targetValue.get(value).equals(NEMOConstants.integer)){
                        valueBuilder.setIntValue(Long.parseLong(value));
                    }
                    else if (targetValue.get(value).equals(NEMOConstants.range)){
                        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.condition.instance.condition.segment.condition.parameter.target.value.RangeValueBuilder rangeValueBuilder = new org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.condition.instance.condition.segment.condition.parameter.target.value.RangeValueBuilder();
                        String[] values = value.split(NEMOConstants.comma);
                        rangeValueBuilder.setMax(Long.parseLong(values[0])>Long.parseLong(values[1])?Long.parseLong(values[0]):Long.parseLong(values[1]))
                                         .setMin(Long.parseLong(values[0])<Long.parseLong(values[1])?Long.parseLong(values[0]):Long.parseLong(values[1]));
                        valueBuilder.setRangeValue(rangeValueBuilder.build());
                    }
                }
                conditionSegmentBuilder.setConditionParameterTargetValue(valueBuilder.build());
                order ++;
                conditionSegmentList.add(conditionSegmentBuilder.build());
            }
            operationBuilder.setConditionSegment(conditionSegmentList);
        }

        if (!actions.isEmpty()){
            List<Action> actionList = new LinkedList<Action>();
            Long order = 0L;
            for (String actionName : actions.keySet()){
                ActionBuilder actionBuilder = new ActionBuilder();
                actionBuilder.setKey(new ActionKey(new ActionName(actionName)))
                             .setActionName(new ActionName(actionName))
                             .setOrder(order);

                Map<String, String> parameters = actions.get(actionName);
                if (parameters!=null){
                    ParameterValuesBuilder parameterValuesBuilder = new ParameterValuesBuilder();
                    Long valueOrder = 0L;
                    List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.StringValue> stringValues = new LinkedList<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.StringValue>();
                    List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.IntValue> intValues = new LinkedList<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.IntValue>();
                    org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.RangeValue rangeValue = null;

                    for (String value : parameters.keySet()){
                        if (parameters.get(value).equals(NEMOConstants.string)){
                            org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.StringValueBuilder valueBuilder = new org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.StringValueBuilder();
                            valueBuilder.setKey(new org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.StringValueKey(valueOrder,value))
                                    .setValue(value)
                                    .setOrder(valueOrder);
                            valueOrder++;
                            stringValues.add(valueBuilder.build());
                        }
                        if (parameters.get(value).equals(NEMOConstants.integer)){
                            org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.IntValueBuilder valueBuilder = new org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.IntValueBuilder();
                            valueBuilder.setKey(new org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.IntValueKey(order,Long.parseLong(value)))
                                    .setValue(Long.parseLong(value))
                                    .setOrder(valueOrder);
                            valueOrder++;
                            intValues.add(valueBuilder.build());
                        }
                        if (parameters.get(value).equals(NEMOConstants.range)){
                            org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.RangeValueBuilder rangeValueBuilder = new org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.RangeValueBuilder();
                            String[] range = value.split(",");
                            valueOrder++;
                            rangeValueBuilder.setMax(Long.parseLong(range[0])>Long.parseLong(range[1])?Long.parseLong(range[0]):Long.parseLong(range[1]));
                        }
                    }
                    if ((!stringValues.isEmpty()&&intValues.isEmpty()&&rangeValue==null)
                            ||(stringValues.isEmpty()&&!intValues.isEmpty()&&rangeValue==null)
                            ||stringValues.isEmpty()&&intValues.isEmpty()&&rangeValue!=null){
                        parameterValuesBuilder.setStringValue(stringValues.isEmpty()?null:stringValues)
                                .setIntValue(intValues.isEmpty()?null:intValues)
                                .setRangeValue(rangeValue);
                        actionBuilder.setParameterValues(parameterValuesBuilder.build());
                    }
                    else {
                        return "The action values are not consistent.";
                    }
                }
                actionList.add(actionBuilder.build());
            }
            operationBuilder.setAction(actionList);
        }
        abstractOperations.put(new OperationName(name),operationBuilder.build());
        return null;
    }
}
