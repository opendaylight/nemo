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
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.hosts.PhysicalHost;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.PhysicalHostId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.PhysicalHostName;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.operations.OperationBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.operations.OperationKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.template.definitions.TemplateDefinition;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.template.instances.TemplateInstance;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.User;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.connection.instance.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.flow.instance.MatchItem;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.flow.instance.MatchItemBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.flow.instance.MatchItemKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.match.item.instance.MatchItemValueBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.node.instance.Property;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.node.instance.SubNode;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.node.instance.SubNodeBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.node.instance.SubNodeKey;
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
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.definition.grouping.TemplateParameter;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.definition.grouping._abstract.intents.AbstractObjects;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.definition.grouping._abstract.intents._abstract.objects.AbstractConnection;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.definition.grouping._abstract.intents._abstract.objects.AbstractFlow;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.definition.grouping._abstract.intents._abstract.objects.AbstractNode;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.definition.grouping._abstract.intents._abstract.operations.AbstractOperation;

import java.util.*;

/**
 * Created by z00293636 on 2015/12/4.
 */
public class UpdateTemplateInstance {
    private TenantManage tenantManage;
    Map<String, String> abstractInstanceIdMap = new HashMap<String, String>();
    Map<String, String> nameIdMap = new HashMap<String, String>();
    private GetDefinitions getDefinitions;

    public UpdateTemplateInstance(DataBroker dataBroker, TenantManage tenantManage){
        this.tenantManage = tenantManage;
        getDefinitions = new GetDefinitions(dataBroker);
    }

    public String checkTemplateInstance(UserId userId, TemplateInstance templateInstance){
        String errorInfo = null;
        TemplateDefinition definition = null;
        if (tenantManage.getTempalteDefinition(userId)!=null){
            if (tenantManage.getTempalteDefinition(userId).containsKey(templateInstance.getTemplateName())){
                definition = tenantManage.getTempalteDefinition(userId).get(templateInstance.getTemplateName());
            }
        }
        else if (tenantManage.getDefinitionDataStore(userId)!=null){
            if (tenantManage.getDefinitionDataStore(userId).containsKey(templateInstance.getTemplateName())){
                definition = tenantManage.getDefinitionDataStore(userId).get(templateInstance.getTemplateName());
            }
        }
        else if (definition==null) {
            Map<UserId, User> usersMap = tenantManage.getUsers();
            for (User user : usersMap.values()) {
                if (user.getUserRole().getValue().equals(NEMOConstants.admin)) {
                    if (tenantManage.getDefinitionDataStore(user.getUserId()) != null) {
                        if (tenantManage.getDefinitionDataStore(user.getUserId()).containsKey(templateInstance.getTemplateName())) {
                            definition = tenantManage.getDefinitionDataStore(user.getUserId()).get(templateInstance.getTemplateName());
                        }
                    }
                }
            }
        }

        if (definition!=null){
            List<TemplateParameter> templateParameters = definition.getTemplateParameter();
            HashMap<ParameterName, TemplateParameter.ParameterValueType> definitionMap = new HashMap<ParameterName, TemplateParameter.ParameterValueType>();
            for (TemplateParameter parameter : templateParameters){
                definitionMap.put(parameter.getParameterName(),parameter.getParameterValueType());
            }

            List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.TemplateParameter> instanceParameters = templateInstance.getTemplateParameter();
            HashMap<ParameterName, org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.ParameterValues> instaceParameterMap = new HashMap<ParameterName, org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.ParameterValues>();
            for (org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.TemplateParameter parameter : instanceParameters){
                if (definitionMap.containsKey(parameter.getParameterName())){
                    if (definitionMap.get(parameter.getParameterName()).getIntValue()==0 &&
                            !(parameter.getParameterValues().getIntValue()==null&&parameter.getParameterValues().getStringValue()!=null&&parameter.getParameterValues().getRangeValue()==null)){
                        return "The value type should be string";
                    }
                    if (definitionMap.get(parameter.getParameterName()).getIntValue()==1 &&
                            !(parameter.getParameterValues().getIntValue()!=null&&parameter.getParameterValues().getStringValue()==null&&parameter.getParameterValues().getRangeValue()==null)){
                        return "The value type should be string";
                    }
                    if (definitionMap.get(parameter.getParameterName()).getIntValue()==2 &&
                            !(parameter.getParameterValues().getIntValue()==null&&parameter.getParameterValues().getStringValue()==null&&parameter.getParameterValues().getRangeValue()!=null)){
                        return "The value type should be string";
                    }
                    else {
                        instaceParameterMap.put(parameter.getParameterName(), parameter.getParameterValues());
                    }
                }
                else {
                    return "The parameter " + parameter.getParameterName().getValue() + " is not defined.";
                }
            }
            errorInfo = createInstance(userId,definition,instaceParameterMap,templateInstance.getTemplateInstanceName().getValue());
        }
        else {
            if (templateInstance!=null){
                return "The template " + templateInstance.getTemplateName().getValue() + " is not exist.";
            }
        }

        return errorInfo;
    }

    //get template definitions defined by admin role
    private TemplateDefinition getTempalteDefinition(TemplateName templateName){
        Map<UserId, User> usersMap = tenantManage.getUsers();
        User userAdmin = null;
        Map<TemplateName, TemplateDefinition> definitionMap = new HashMap<TemplateName, TemplateDefinition>();
        for (User user : usersMap.values()){
            if (user.getUserRole().getValue().equals("admin")){
               userAdmin = user;
            }
        }
        if (userAdmin!=null){
            definitionMap = tenantManage.getTempalteDefinition(userAdmin.getUserId());
        }
        return definitionMap.isEmpty()?null:definitionMap.get(templateName);
    }

    private String createInstance(UserId userId, TemplateDefinition definition, HashMap<ParameterName, org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.ParameterValues> instaceParameterMap, String instanceName){
       String errorInfo = null;
        if (definition.getTemplateParameter()!=null){
           if (definition.getAbstractIntents().getAbstractObjects()!=null){
               AbstractObjects objects = definition.getAbstractIntents().getAbstractObjects();
               if (objects.getAbstractNode()!=null){
                   List<AbstractNode> abstractNodeList = objects.getAbstractNode();
                   for (AbstractNode abstractNode : abstractNodeList){
                       NodeBuilder nodeBuilder = new NodeBuilder();
                       NodeId nodeId = null;
                       if (abstractNode.getNodeType().getValue().equals(NEMOConstants.host)){
                           Map<PhysicalHostName, PhysicalHost> physicalHostMap = getDefinitions.getPhysicalHost();
                           if (physicalHostMap.containsKey(new PhysicalHostName(abstractNode.getNodeName().getValue()))){
                               PhysicalHostId physicalHostId = physicalHostMap.get(new PhysicalHostName(abstractNode.getNodeName().getValue())).getHostId();
                               nodeBuilder.setKey(new NodeKey(new NodeId(physicalHostId.getValue())))
                                       .setNodeId(new NodeId(physicalHostId.getValue()));
                           }
                           else {
                               return "The host " + abstractNode.getNodeName().getValue() + " is not exist in physical network.";
                           }
                       }else {
                           nodeId = new NodeId(UUID.randomUUID().toString());
                           nodeBuilder.setKey(new NodeKey(nodeId)).setNodeId(nodeId);
                       }
                       nodeBuilder.setKey(new NodeKey(nodeId)).setNodeId(nodeId);
                       if (instaceParameterMap.containsKey(new ParameterName(abstractNode.getNodeName().getValue()))){
                           nodeBuilder.setNodeName(new NodeName(instaceParameterMap.get(new ParameterName(abstractNode.getNodeName())).getStringValue().get(0).getValue()));
                       }else {
                           nodeBuilder.setNodeName(new NodeName(instanceName + "." + abstractNode.getNodeName().getValue()));
                       }

                       if (instaceParameterMap.containsKey(new ParameterName(abstractNode.getNodeType().getValue()))){
                           nodeBuilder.setNodeType(new NodeType(instaceParameterMap.get(new ParameterName(abstractNode.getNodeType())).getStringValue().get(0).getValue()));
                       }else {
                           nodeBuilder.setNodeType(abstractNode.getNodeType());
                       }

                       if (abstractNode.getSubNode()!=null){
                           List<SubNode> subNodeList = new LinkedList<SubNode>();
                           for (SubNode subNode : abstractNode.getSubNode()){
                               if (abstractInstanceIdMap.containsKey(subNode.getNodeId().getValue())){
                                   SubNodeBuilder subNodeBuilder = new SubNodeBuilder();
                                   subNodeBuilder.setKey(new SubNodeKey(new NodeId(abstractInstanceIdMap.get(subNode.getNodeId().getValue()))))
                                                 .setOrder(subNode.getOrder())
                                                 .setNodeId(new NodeId(abstractInstanceIdMap.get(subNode.getNodeId().getValue())));
                                   subNodeList.add(subNodeBuilder.build());
                               }
                               else {
                                   return "The subnode " + subNode.getNodeId().getValue() + " is not exist.";
                               }
                           }
                           nodeBuilder.setSubNode(subNodeList.isEmpty() ? null : subNodeList);
                       }

                       abstractInstanceIdMap.put(abstractNode.getNodeId().getValue(),nodeId.getValue());
                       nameIdMap.put(abstractNode.getNodeName().getValue(),abstractNode.getNodeId().getValue());

                       if (abstractNode.getProperty()!=null){
                           List<Property> propertyList = new LinkedList<Property>();
                           for (Property property : abstractNode.getProperty()){
                               if (property.getPropertyName().getValue().equals(NEMOConstants.sub_nodes)){
                                   if (property.getPropertyValues().getStringValue()==null||property.getPropertyValues().getIntValue()!=null||property.getPropertyValues().getRangeValue()!=null){
                                       return "The subnodes type should be string.";
                                   }
                                   else {
                                       List<StringValue> subnodes = property.getPropertyValues().getStringValue();
                                       List<SubNode> subNodeList = new LinkedList<SubNode>();
                                       Long order = 0L;
                                       for (StringValue value : subnodes){
                                           if (instaceParameterMap.containsKey(new ParameterName(value.getValue()))){
                                               List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.parameter.values.StringValue> stringValues = instaceParameterMap.get(new ParameterName(value.getValue())).getStringValue();
                                               for (org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.parameter.values.StringValue stringValue : stringValues){
                                                   if (tenantManage.getObjectId(userId,stringValue.getValue())!=null){
                                                       NodeId subNodeId = new NodeId(tenantManage.getObjectId(userId,stringValue.getValue()));
                                                       SubNodeBuilder subNodeBuilder = new SubNodeBuilder();
                                                       subNodeBuilder.setKey(new SubNodeKey(subNodeId))
                                                                     .setNodeId(nodeId)
                                                                     .setOrder(order);
                                                       subNodeList.add(subNodeBuilder.build());
                                                       order ++;
                                                   }
                                                   else {
                                                       return  "The subnode " + stringValue.getValue() + " is not exist.";
                                                   }
                                               }
                                           }
                                       }
                                       nodeBuilder.setSubNode(subNodeList);
                                   }
                               }
                               else {
                                   Property property1 = createNodeProperty(property,instaceParameterMap);
                                   if (property1==null){
                                       return "The property instance is not consistent with template definition.";
                                   }else {
                                       propertyList.add(property1);
                                   }
                               }
                           }
                           nodeBuilder.setProperty(propertyList);
                       }
                       tenantManage.setNode(userId,nodeBuilder.getNodeId(),nodeBuilder.build());
                   }
               }
                if (objects.getAbstractConnection()!=null){
                    List<AbstractConnection> abstractConnectionList = objects.getAbstractConnection();
                    for (AbstractConnection abstractConnection : abstractConnectionList){
                        ConnectionBuilder connectionBuilder = new ConnectionBuilder();
                        ConnectionId connectionId = new ConnectionId(UUID.randomUUID().toString());
                        connectionBuilder.setKey(new ConnectionKey(connectionId)).setConnectionId(connectionId)
                                         .setConnectionName(new ConnectionName(instanceName + "." + abstractConnection.getConnectionName().getValue()))
                                         .setConnectionType(abstractConnection.getConnectionType());

                        abstractInstanceIdMap.put(abstractConnection.getConnectionId().getValue(),connectionId.getValue());
                        nameIdMap.put(abstractConnection.getConnectionName().getValue(),abstractConnection.getConnectionId().getValue());

                        if (abstractConnection.getEndNode()!=null){
                            List<EndNode> endNodeList = new LinkedList<EndNode>();
                            for (EndNode endNode : abstractConnection.getEndNode()){
                                if (abstractInstanceIdMap.containsKey(endNode.getNodeId().getValue())){
                                    EndNodeBuilder endNodeBuilder = new EndNodeBuilder();
                                    endNodeBuilder.setKey(new EndNodeKey(new NodeId(abstractInstanceIdMap.get(endNode.getNodeId().getValue()))))
                                                  .setOrder(endNode.getOrder())
                                                  .setNodeId(new NodeId(abstractInstanceIdMap.get(endNode.getNodeId().getValue())));
                                    endNodeList.add(endNodeBuilder.build());
                                }
                            }
                                connectionBuilder.setEndNode(endNodeList);
                        }
                        if (abstractConnection.getProperty()!=null){
                            List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.connection.instance.Property> propertyList = new LinkedList<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.connection.instance.Property>();
                            for (org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.connection.instance.Property property : abstractConnection.getProperty()){
                                org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.connection.instance.Property property1 = createConnProperty(property, instaceParameterMap);
                                if (property1==null){
                                    return "The property instance is not consistent with template definition.";
                                }else {
                                    propertyList.add(createConnProperty(property,instaceParameterMap));
                                }
                            }
                            connectionBuilder.setProperty(propertyList);
                        }
                        tenantManage.setConnection(userId,connectionBuilder.getConnectionId(),connectionBuilder.build());
                    }
                }
               if (objects.getAbstractFlow()!=null){
                   List<AbstractFlow> abstractFlowList = objects.getAbstractFlow();
                   for (AbstractFlow abstractFlow : abstractFlowList){
                       FlowBuilder flowBuilder = new FlowBuilder();
                       FlowId flowId = new FlowId(UUID.randomUUID().toString());
                       flowBuilder.setKey(new FlowKey(flowId)).setFlowId(flowId)
                                    .setFlowName(new FlowName(instanceName+"."+abstractFlow.getFlowName().getValue()));

                       abstractInstanceIdMap.put(abstractFlow.getFlowId().getValue(),flowId.getValue());
                       nameIdMap.put(abstractFlow.getFlowName().getValue(),abstractFlow.getFlowId().getValue());

                       if (abstractFlow.getMatchItem()!=null){
                           List<MatchItem> matchItemList = new LinkedList<MatchItem>();
                           for (MatchItem matchItem : abstractFlow.getMatchItem()){
                               MatchItem matchItem1 = createMatchItem(matchItem,instaceParameterMap);
                               if (matchItem1==null){
                                   return  "The match instance is not consistent with template definition.";
                               }else {
                                   matchItemList.add(matchItem1);
                               }
                           }
                           flowBuilder.setMatchItem(matchItemList);
                       }
                       tenantManage.setFlow(userId,flowBuilder.getFlowId(),flowBuilder.build());
                   }
               }
           }

            if (definition.getAbstractIntents().getAbstractOperations()!=null){
                List<AbstractOperation> abstractOperationList = definition.getAbstractIntents().getAbstractOperations().getAbstractOperation();
                if (abstractOperationList!=null){
                    for (AbstractOperation abstractOperation : abstractOperationList){
                        OperationBuilder operationBuilder = new OperationBuilder();
                        OperationId operationId = new OperationId(UUID.randomUUID().toString());
                        operationBuilder.setKey(new OperationKey(operationId)).setOperationId(operationId)
                                        .setOperationName(new OperationName(instanceName + "." + abstractOperation.getOperationName().getValue()));

                        String prority = abstractOperation.getOperationName().getValue()+"."+NEMOConstants.Priority;
                        if (instaceParameterMap.containsKey(new ParameterName(prority))){
                            if (instaceParameterMap.get(new ParameterName(prority)).getIntValue()==null||instaceParameterMap.get(new ParameterName(prority)).getStringValue()!=null
                                    ||instaceParameterMap.get(new ParameterName(prority)).getRangeValue()!=null){
                                return "The "+NEMOConstants.Priority +" should one integer.";
                            }else {
                                if (instaceParameterMap.get(new ParameterName(prority)).getIntValue().size()!=1){
                                    return "The "+NEMOConstants.Priority +" should one integer.";
                                }
                                else {
                                    operationBuilder.setPriority(instaceParameterMap.get(new ParameterName(prority)).getIntValue().get(0).getValue());
                                }
                            }
                        }
                        else {
                            operationBuilder.setPriority(abstractOperation.getPriority());
                        }

                        String target = NEMOConstants.Target+"."+abstractOperation.getOperationName().getValue() ;
                        if (instaceParameterMap.containsKey(new ParameterName(target))){
                            if (instaceParameterMap.get(new ParameterName(target)).getIntValue()!=null||instaceParameterMap.get(new ParameterName(target)).getStringValue()==null
                                    ||instaceParameterMap.get(new ParameterName(target)).getRangeValue()!=null){
                                return "The "+NEMOConstants.Target +" should one string.";
                            }
                            else {

                                if (nameIdMap.containsKey(instaceParameterMap.get(new ParameterName(target)))){
                                    String abstractId = nameIdMap.get(instaceParameterMap.get(new ParameterName(target)));
                                    operationBuilder.setTargetObject(new ObjectId(abstractInstanceIdMap.get(abstractId)));
                                }
                                else {
                                    return "The "+NEMOConstants.Target + " is not exist.";
                                }
                            }
                        }else {
                            String objectId = abstractInstanceIdMap.get(abstractOperation.getTargetObject().getValue());
                            operationBuilder.setTargetObject(new ObjectId(objectId));
                        }

                        if (abstractOperation.getConditionSegment()!=null){
                            List<ConditionSegment> conditionSegmentList = abstractOperation.getConditionSegment();
                            List<ConditionSegment> conditionSegments = new LinkedList<ConditionSegment>();
                            for (ConditionSegment conditionSegment : conditionSegmentList){
                                ConditionSegment conditionSegment1 = createCondition(conditionSegment,instaceParameterMap);
                                if (conditionSegment1==null){
                                    return "The Condition instance is not consistent with Condition definition.";
                                }
                                else {
                                    conditionSegments.add(conditionSegment1);
                                }
                            }
                            operationBuilder.setConditionSegment(conditionSegments);
                        }
                        if (abstractOperation.getAction()!=null){
                            List<Action> actionList = abstractOperation.getAction();
                            List<Action> actions = new LinkedList<Action>();
                            for (Action action : actionList){
                                Action action1 = createAction(action,instaceParameterMap);
                                if (action1==null){
                                    return "The Action instance is not consistent with template definition.";
                                }
                                else
                                {
                                    actions.add(action1);
                                }
                            }
                            operationBuilder.setAction(actions);
                        }
                        tenantManage.setOperation(userId,operationBuilder.getOperationId(),operationBuilder.build());
                    }
                }
            }
       }
        return errorInfo;
    }

    private Property createNodeProperty(Property property, HashMap<ParameterName, org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.ParameterValues> instaceParameterMap){
        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.node.instance.PropertyBuilder propertyBuilder = new org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.node.instance.PropertyBuilder();
        propertyBuilder.setPropertyName(property.getPropertyName());
        propertyBuilder.setKey(new org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.node.instance.PropertyKey(property.getPropertyName()));
        PropertyValuesBuilder propertyValuesBuilder = new PropertyValuesBuilder();

        List<StringValue> stringValues2 = new LinkedList<StringValue>();
        List<IntValue> intValues2 = new LinkedList<IntValue>();
        RangeValue rangeValue2 = null;
        if (property.getPropertyValues().getStringValue()!=null){
            List<StringValue> stringValues = property.getPropertyValues().getStringValue();
            for (StringValue stringValue : stringValues){
                if (instaceParameterMap.containsKey(new ParameterName(stringValue.getValue()))){
                    if (instaceParameterMap.get(new ParameterName(stringValue.getValue())).getStringValue()!=null){
                        List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.parameter.values.StringValue> stringValues1 = instaceParameterMap.get(new ParameterName(stringValue.getValue())).getStringValue();
                        for (org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.parameter.values.StringValue stringValue1 : stringValues1){
                            stringValues2.add(new StringValueBuilder().setKey(new StringValueKey(stringValue1.getOrder(),stringValue1.getValue())).setValue(stringValue1.getValue()).setOrder(stringValue1.getOrder()).build());
                        }
                    }

                    if (instaceParameterMap.get(new ParameterName(stringValue.getValue())).getIntValue()!=null){
                        List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.parameter.values.IntValue> intValues1 = instaceParameterMap.get(new ParameterName(stringValue.getValue())).getIntValue();
                        for (org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.parameter.values.IntValue intValue : intValues1){
                            intValues2.add(new IntValueBuilder().setKey(new IntValueKey(intValue.getOrder(),intValue.getValue())).setValue(intValue.getValue()).setOrder(intValue.getOrder()).build());
                        }
                    }

                    if (instaceParameterMap.get(new ParameterName(stringValue.getValue())).getRangeValue()!=null){
                        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.parameter.values.RangeValue rangeValue1 = instaceParameterMap.get(new ParameterName(stringValue.getValue())).getRangeValue();
                        rangeValue2 = new RangeValueBuilder().setMax(rangeValue1.getMax()).setMin(rangeValue1.getMin()).build();
                        propertyValuesBuilder.setRangeValue(rangeValue2);
                    }

                }
                else {
                    stringValues2.add(stringValue);
                }
            }
        }
        if (property.getPropertyValues().getIntValue()!=null){
            for (IntValue intValue : property.getPropertyValues().getIntValue()){
                intValues2.add(intValue);
            }
        }
        if (property.getPropertyValues().getRangeValue()!=null){
            rangeValue2 = property.getPropertyValues().getRangeValue();
        }

        propertyValuesBuilder.setStringValue(stringValues2.isEmpty()?null:stringValues2);
        propertyValuesBuilder.setIntValue(intValues2.isEmpty()?null:intValues2);
        propertyValuesBuilder.setRangeValue(rangeValue2);
        propertyBuilder.setPropertyValues(propertyValuesBuilder.build());

        return propertyBuilder.build();
    }

    private org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.connection.instance.Property createConnProperty(org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.connection.instance.Property property, HashMap<ParameterName, org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.ParameterValues> instaceParameterMap){
        PropertyBuilder propertyBuilder = new PropertyBuilder();
        propertyBuilder.setPropertyName(property.getPropertyName())
                        .setKey(new PropertyKey(property.getPropertyName()));

        PropertyValuesBuilder propertyValuesBuilder = new PropertyValuesBuilder();
        List<StringValue> stringValues = new LinkedList<StringValue>();
        List<IntValue> intValues = new LinkedList<IntValue>();
        RangeValue rangeValue = null;

        if (property.getPropertyValues().getStringValue()!=null){
            List<StringValue> stringValueList = property.getPropertyValues().getStringValue();
            for (StringValue stringValue : stringValueList){
                if (instaceParameterMap.containsKey(new ParameterName(stringValue.getValue()))){
                    org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.ParameterValues values = instaceParameterMap.get(new ParameterName(stringValue.getValue()));
                    if (values.getStringValue()!=null){
                        List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.parameter.values.StringValue> stringValues1 = values.getStringValue();
                        for (org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.parameter.values.StringValue value : stringValues1){
                            stringValues.add(new StringValueBuilder().setKey(new StringValueKey(value.getOrder(),value.getValue())).setOrder(value.getOrder()).setValue(value.getValue()).build());
                        }
                    }
                    if (values.getIntValue()!=null){
                        List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.parameter.values.IntValue> intValues1 = values.getIntValue();
                        for (org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.parameter.values.IntValue value : intValues1){
                            intValues.add(new IntValueBuilder().setKey(new IntValueKey(value.getOrder(),value.getValue())).setOrder(value.getOrder()).setValue(value.getValue()).build());
                        }
                    }
                    if (values.getRangeValue()!=null){
                        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.parameter.values.RangeValue rangeValue1 = values.getRangeValue();
                        rangeValue = new RangeValueBuilder().setMax(rangeValue1.getMax()).setMin(rangeValue1.getMin()).build();
                    }
                }else {
                    stringValues.add(stringValue);
                }
            }
        }
        if (property.getPropertyValues().getIntValue()!=null){
            for (IntValue intValue : property.getPropertyValues().getIntValue()){
                intValues.add(intValue);
            }
        }
        if (property.getPropertyValues().getRangeValue()!=null){
            rangeValue = property.getPropertyValues().getRangeValue();
        }

        propertyValuesBuilder.setStringValue(stringValues.isEmpty()?null:stringValues)
                            .setIntValue(intValues.isEmpty()?null:intValues)
                            .setRangeValue(rangeValue);
        propertyBuilder.setPropertyValues(propertyValuesBuilder.build());

        return propertyBuilder.build();
    }

    private MatchItem createMatchItem(MatchItem matchItem, HashMap<ParameterName, org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.ParameterValues> instaceParameterMap){
        MatchItemBuilder matchItemBuilder = new MatchItemBuilder();
        matchItemBuilder.setMatchItemName(matchItem.getMatchItemName());
        matchItemBuilder.setKey(new MatchItemKey(matchItem.getMatchItemName()));

        MatchItemValueBuilder valueBuilder = new MatchItemValueBuilder();

        if (matchItem.getMatchItemValue().getStringValue()!=null){
            String stringValue = matchItem.getMatchItemValue().getStringValue();
            if (instaceParameterMap.containsKey(new ParameterName(stringValue))){
                org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.ParameterValues parameterValues = instaceParameterMap.get(new ParameterName(matchItem.getMatchItemValue().getStringValue()));
                if (parameterValues.getStringValue()!=null){
                    List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.parameter.values.StringValue> stringValues1 = parameterValues.getStringValue();
                    if (stringValues1.size()!=1){
                        return null;
                    }else {
                        valueBuilder.setStringValue(stringValues1.get(0).getValue());
                    }
                }
                if (parameterValues.getIntValue()!=null){
                    List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.parameter.values.IntValue> intValues1 = parameterValues.getIntValue();
                    if (intValues1.size()!=1){
                        return null;
                    }else {
                        valueBuilder.setIntValue(intValues1.get(0).getValue());
                    }
                }
                if (parameterValues.getRangeValue()!=null){
                    org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.parameter.values.RangeValue rangeValue = parameterValues.getRangeValue();
                    valueBuilder.setRangeValue(new  org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.match.item.instance.match.item.value.RangeValueBuilder().setMax(rangeValue.getMax()).setMin(rangeValue.getMin()).build());
                }

            }
        }
        if (matchItem.getMatchItemValue().getIntValue()!=null){
            valueBuilder.setIntValue(matchItem.getMatchItemValue().getIntValue());
        }
        if (matchItem.getMatchItemValue().getRangeValue()!=null){
            valueBuilder.setRangeValue(matchItem.getMatchItemValue().getRangeValue());
        }
        matchItemBuilder.setMatchItemValue(valueBuilder.build());
        return matchItemBuilder.build();
    }

    private Action createAction(Action action, HashMap<ParameterName, org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.ParameterValues> instaceParameterMap){
        ActionBuilder actionBuilder = new ActionBuilder();
        actionBuilder.setKey(new ActionKey(action.getActionName()))
                     .setActionName(action.getActionName())
                     .setOrder(action.getOrder());

        if (action.getParameterValues()!=null){
            List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.StringValue> stringValues = new LinkedList<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.StringValue>();
            List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.IntValue> intValues = new LinkedList<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.IntValue>();
            org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.RangeValue rangeValue = null;
            ParameterValuesBuilder valuesBuilder = new ParameterValuesBuilder();

            if (action.getParameterValues().getStringValue()!=null){
                List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.StringValue> stringValues1 = action.getParameterValues().getStringValue();
                for (org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.StringValue stringValue : stringValues1){
                    if (instaceParameterMap.containsKey(new ParameterName(stringValue.getValue()))){
                        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.ParameterValues parameterValues = instaceParameterMap.get(new ParameterName(stringValue.getValue()));
                        if (parameterValues.getStringValue()!=null){
                            List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.parameter.values.StringValue> stringValues2 = parameterValues.getStringValue();
                            for (org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.parameter.values.StringValue value : stringValues2){
                                String targetValue = null;
                                if (nameIdMap.containsKey(value.getValue())){
                                    targetValue = abstractInstanceIdMap.get(nameIdMap.get(value.getValue()));
                                }
                                else {
                                    targetValue= value.getValue();
                                }
                                stringValues.add(new org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.StringValueBuilder()
                                        .setKey(new org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.StringValueKey(value.getOrder(),targetValue))
                                        .setOrder(value.getOrder())
                                        .setValue(targetValue).build());
                            }
                        }
                        if (parameterValues.getIntValue()!=null){
                            List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.parameter.values.IntValue> intValues2 = parameterValues.getIntValue();
                            for (org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.parameter.values.IntValue value : intValues2){
                                intValues.add(new org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.IntValueBuilder()
                                        .setKey(new org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.IntValueKey(value.getOrder(),value.getValue()))
                                        .setOrder(value.getOrder()).setValue(value.getValue()).build());
                            }
                        }
                        if (parameterValues.getRangeValue()!=null){
                            org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.parameter.values.RangeValue rangeValue2 = parameterValues.getRangeValue();
                            rangeValue = new org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.RangeValueBuilder()
                                    .setMin(rangeValue2.getMax()).setMin(rangeValue2.getMin()).build();
                        }
                    }
                    else {
                        if (nameIdMap.containsKey(stringValue.getValue())){
                            String objectId = abstractInstanceIdMap.get(nameIdMap.get(stringValue.getValue()));
                            stringValues.add(new org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.StringValueBuilder()
                                    .setKey(new org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.StringValueKey(stringValue.getOrder(),objectId))
                                    .setOrder(stringValue.getOrder())
                                    .setValue(objectId).build());
                        }
                        else {
                            stringValues.add(stringValue);
                        }
                    }
                }
            }
            if (action.getParameterValues().getIntValue()!=null){
                for (org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.IntValue intValue : action.getParameterValues().getIntValue()){
                    intValues.add(intValue);
                }
            }
            if (action.getParameterValues().getRangeValue()!=null){
                org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.RangeValue rangeValue2 = action.getParameterValues().getRangeValue();
                rangeValue = new org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.RangeValueBuilder()
                        .setMin(rangeValue2.getMax()).setMin(rangeValue2.getMin()).build();
            }

            valuesBuilder.setStringValue(stringValues.isEmpty()?null:stringValues)
                    .setIntValue(intValues.isEmpty()?null:intValues)
                    .setRangeValue(rangeValue);
            actionBuilder.setParameterValues(valuesBuilder.build());
        }
        return actionBuilder.build();
    }

    private ConditionSegment createCondition(ConditionSegment conditionSegment,  HashMap<ParameterName, org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.ParameterValues> instaceParameterMap){
        ConditionSegmentBuilder conditionBuilder = new ConditionSegmentBuilder();
        ConditionSegmentId conditionSegmentId = new ConditionSegmentId(UUID.randomUUID().toString());
        conditionBuilder.setKey(new ConditionSegmentKey(conditionSegmentId))
                        .setConditionSegmentId(conditionSegmentId)
                        .setConditionParameterName(conditionSegment.getConditionParameterName())
                        .setConditionParameterMatchPattern(conditionSegment.getConditionParameterMatchPattern())
                        .setPrecursorRelationOperator(conditionSegment.getPrecursorRelationOperator())
                        .setOrder(conditionSegment.getOrder());
        ConditionParameterTargetValueBuilder targetValueBuilder = new ConditionParameterTargetValueBuilder();
        if (conditionSegment.getConditionParameterTargetValue().getStringValue()!=null){
            String targetValue = conditionSegment.getConditionParameterTargetValue().getStringValue();
            if (instaceParameterMap.containsKey(new ParameterName(targetValue))){
                if (instaceParameterMap.get(new ParameterName(targetValue)).getStringValue()!=null){
                    List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.parameter.values.StringValue> stringValues = instaceParameterMap.get(new ParameterName(targetValue)).getStringValue();
                    if (stringValues.size()!=1){
                        return null;
                    }
                    else {
                        targetValueBuilder.setStringValue(stringValues.get(0).getValue());
                    }
                }
                if (instaceParameterMap.get(new ParameterName(targetValue)).getIntValue()!=null){
                    List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.parameter.values.IntValue> intValues = instaceParameterMap.get(new ParameterName(targetValue)).getIntValue();
                    if (intValues.size()!=1){
                        return null;
                    }
                    else {
                        targetValueBuilder.setIntValue(intValues.get(0).getValue());
                    }
                }
                if (instaceParameterMap.get(new ParameterName(targetValue)).getRangeValue()!=null){
                    org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.parameter.values.RangeValue rangeValue = instaceParameterMap.get(new ParameterName(targetValue)).getRangeValue();
                    //targetValueBuilder.setRangeValue(new RangeValueBuilder().setMin(rangeValue.getMin()).setMax(rangeValue.getMax()).build());
                }
            }
        }
        if (conditionSegment.getConditionParameterTargetValue().getIntValue()!=null){
            targetValueBuilder.setIntValue(conditionSegment.getConditionParameterTargetValue().getIntValue());
        }
        if (conditionSegment.getConditionParameterTargetValue().getRangeValue()!=null){
            targetValueBuilder.setRangeValue(conditionSegment.getConditionParameterTargetValue().getRangeValue());
        }

        conditionBuilder.setConditionParameterTargetValue(targetValueBuilder.build());
        return conditionBuilder.build();
    }
}

