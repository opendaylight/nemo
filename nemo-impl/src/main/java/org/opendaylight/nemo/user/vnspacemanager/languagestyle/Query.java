/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.user.vnspacemanager.languagestyle;

import org.opendaylight.nemo.user.tenantmanager.TenantManage;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Connection;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Flow;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Node;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.operations.Operation;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.User;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.connection.instance.EndNode;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.flow.instance.MatchItem;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.node.instance.Property;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.node.instance.SubNode;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.property.values.IntValue;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.property.values.RangeValue;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.property.values.StringValue;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.condition.instance.ConditionSegment;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.operation.instance.Action;

import java.util.List;


/**
 * Created by z00293636 on 2015/11/24.
 */
public class Query {
    private TenantManage tenantManage;

    public Query(TenantManage tenantManage){
        this.tenantManage = tenantManage;
    }

    public String query(UserId userId,String item, String target){
        String errorInfo = null;
        tenantManage.fetchVNSpace(userId);
        User user = tenantManage.getUser();

        if (target!=null){
            Boolean targetExist = false;
            if (user!=null){
                if (user.getObjects()!=null){
                    if (user.getObjects().getNode()!=null){
                        List<Node> nodeList = user.getObjects().getNode();
                        for (Node node : nodeList){
                            if (node.getNodeName().getValue().equals(target)){
                                targetExist = true;
                                errorInfo = queryNode(user,item, node);
                            }
                        }
                    }
                    if (user.getObjects().getConnection()!=null){
                        List<Connection> connectionList = user.getObjects().getConnection();
                        for (Connection connection : connectionList){
                            if (connection.getConnectionName().getValue().equals(target)){
                                targetExist = true;
                                errorInfo = queryConnection(user,item,connection);
                            }
                        }
                    }
                    if (user.getObjects().getFlow()!=null){
                        List<Flow> flowList = user.getObjects().getFlow();
                        for (Flow flow : flowList){
                            if (flow.getFlowName().getValue().equals(target)){
                                targetExist = true;
                                errorInfo = queryFlow(item,flow);
                            }
                        }
                    }
                }
                if (user.getOperations()!=null){
                    if (user.getOperations().getOperation()!=null){
                        List<Operation> operationList = user.getOperations().getOperation();
                        for (Operation operation : operationList){
                            if (operation.getOperationName().getValue().equals(target)){
                                targetExist = true;
                                errorInfo = queryOperation(user,item,operation);
                            }
                        }
                    }
                }
            }
            if (!targetExist){
                return "The target " + target + " is not exist in user vn space.";
            }
        }
        else {
           if (item.equals(NEMOConstants.AllNodes)){
               errorInfo = queryAllNode(user);
           }
            else if (item.equals(NEMOConstants.AllConnections)){
               errorInfo = queryAllConnection(user);
           }
            else if (item.equals(NEMOConstants.AllFlows)){
               errorInfo = queryAllFlow(user);
           }
            else if (item.equals(NEMOConstants.AllOperations)){
               errorInfo = queryAllOperation(user);
           }
            else {
               return "The item is not supported.";
           }
        }
        return errorInfo;
    }

    private String queryNode(User user,String item, Node node){
        String errorInfo = NEMOConstants.Results+":";
        if (item.equals(NEMOConstants.Type)){
            errorInfo += node.getNodeType().getValue();
        }
        else if (item.equals(NEMOConstants.SubNodes)||item.equals(NEMOConstants.Contain)){
            List<SubNode> subNodeList = node.getSubNode();
            if (subNodeList != null){
                for (SubNode subNode : subNodeList){
                    for (Node node1:user.getObjects().getNode()){
                        if (subNode.getNodeId().equals(node1.getNodeId())){
                            errorInfo += node1.getNodeName().getValue() + ",";
                        }
                    }
                }
                errorInfo = errorInfo.substring(0,errorInfo.length()-1);
            }
        }
       else if (item.equals(NEMOConstants.Property)){
            List<Property> properties = node.getProperty();
           if (properties!=null){
                for (Property property : properties){
                    errorInfo += property.getPropertyName().getValue()+":";
                    List<IntValue> intValues = property.getPropertyValues().getIntValue();
                    List<StringValue> stringValues = property.getPropertyValues().getStringValue();
                    org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.property.values.RangeValue rangeValue = property.getPropertyValues().getRangeValue();
                    if (intValues!=null){
                        for (IntValue intValue : intValues){
                            errorInfo += intValue.getValue()+",";
                        }
                        errorInfo = errorInfo.substring(0,errorInfo.length()-1);
                    }
                    if (stringValues!=null){
                        for (StringValue stringValue : stringValues){
                            errorInfo += stringValue.getValue() +",";
                        }
                        errorInfo = errorInfo.substring(0,errorInfo.length()-1);
                    }
                    if (rangeValue!=null){
                        errorInfo+="["+rangeValue.getMin()+","+rangeValue.getMax()+"]";
                    }
                }
            }
       }
        else {
            List<Property> properties = node.getProperty();
           Boolean itemExist = false;
            if (properties != null){
                for (Property property : properties){
                    if (property.getPropertyName().getValue().equals(item)){
                        itemExist = true;
                        List<IntValue> intValues = property.getPropertyValues().getIntValue();
                        List<StringValue> stringValues = property.getPropertyValues().getStringValue();
                        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.property.values.RangeValue rangeValue = property.getPropertyValues().getRangeValue();
                        if (intValues!=null){
                            for (IntValue intValue : intValues){
                                errorInfo += intValue.getValue()+",";
                            }
                            errorInfo = errorInfo.substring(0,errorInfo.length()-1);
                        }
                        if (stringValues!=null){
                            for (StringValue stringValue : stringValues){
                                errorInfo += stringValue.getValue() +",";
                            }
                            errorInfo = errorInfo.substring(0,errorInfo.length()-1);
                        }
                        if (rangeValue!=null){
                            errorInfo+="["+rangeValue.getMin()+","+rangeValue.getMax()+"]";
                        }
                    }
                }
            }
           if (!itemExist){
               return "The item " + item + " is not included in "+node.getNodeName().getValue()+";";
           }
       }
        return errorInfo;
    }

    private String queryConnection( User user, String item, Connection connection){
        String errorInfo =NEMOConstants.Results+":";
        if (item.equals(NEMOConstants.Type)){
            errorInfo += connection.getConnectionType().getValue();
        }
        else if (item.equals(NEMOConstants.Endnodes)){
            List<EndNode> endNodeList = connection.getEndNode();
            if (endNodeList != null){
                for (EndNode endNode : endNodeList){
                    for (Node node : user.getObjects().getNode()){
                        if (endNode.getNodeId().equals(node.getNodeId())){
                            errorInfo+=node.getNodeName().getValue()+",";
                        }
                    }
                }
                errorInfo = errorInfo.substring(0,errorInfo.length()-1);
            }
        }
        else if (item.equals(NEMOConstants.Property)){
            List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.connection.instance.Property> properties = connection.getProperty();
            if (properties!=null){
                for (org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.connection.instance.Property property : properties){
                    errorInfo += property.getPropertyName().getValue()+":";
                    List<IntValue> intValues = property.getPropertyValues().getIntValue();
                    List<StringValue> stringValues = property.getPropertyValues().getStringValue();
                    org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.property.values.RangeValue rangeValue = property.getPropertyValues().getRangeValue();
                    if (intValues!=null){
                        for (IntValue intValue : intValues){
                            errorInfo += intValue.getValue()+",";
                        }
                        errorInfo = errorInfo.substring(0,errorInfo.length()-1);
                    }
                    if (stringValues!=null){
                        for (StringValue stringValue : stringValues){
                            errorInfo += stringValue.getValue() +",";
                        }
                        errorInfo = errorInfo.substring(0,errorInfo.length()-1);
                    }
                    if (rangeValue!=null){
                        errorInfo+="["+rangeValue.getMin()+","+rangeValue.getMax()+"]";
                    }
                }
            }
        }
        else {
            List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.connection.instance.Property> properties = connection.getProperty();
            Boolean itemExist = false;
            if (properties!=null){
                for (org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.connection.instance.Property property : properties){
                    if (property.getPropertyName().getValue().equals(item)){
                        itemExist = true;
                        List<IntValue> intValues = property.getPropertyValues().getIntValue();
                        List<StringValue> stringValues = property.getPropertyValues().getStringValue();
                        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.property.values.RangeValue rangeValue = property.getPropertyValues().getRangeValue();
                        if (intValues!=null){
                            for (IntValue intValue : intValues){
                                errorInfo += intValue.getValue()+",";
                            }
                            errorInfo = errorInfo.substring(0,errorInfo.length()-1);
                        }
                        if (stringValues!=null){
                            for (StringValue stringValue : stringValues){
                                errorInfo += stringValue.getValue() +",";
                            }
                            errorInfo = errorInfo.substring(0,errorInfo.length()-1);
                        }
                        if (rangeValue!=null){
                            errorInfo+="["+rangeValue.getMin()+","+rangeValue.getMax()+"]";
                        }
                    }
                }
            }
            if (!itemExist){
                return "The item " + item + " is not included in "+connection.getConnectionName().getValue()+";";
            }
        }
        return errorInfo;
    }

    private String queryFlow( String item, Flow flow){
        String errorInfo =NEMOConstants.Results+":";
        if (item.equals(NEMOConstants.Match)){
            List<MatchItem> matchItems = flow.getMatchItem();
            for (MatchItem matchItem: matchItems){
                errorInfo += matchItem.getMatchItemName().getValue()+":";
                Long intValue = matchItem.getMatchItemValue().getIntValue();
                String stringValue = matchItem.getMatchItemValue().getStringValue();
                org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.match.item.instance.match.item.value.RangeValue rangeValue = matchItem.getMatchItemValue().getRangeValue();
                if (intValue!=null){
                    errorInfo +=intValue;
                }
                if (stringValue!=null){
                    errorInfo += stringValue;
                }
                if (rangeValue!=null){
                    errorInfo+="["+rangeValue.getMin()+","+rangeValue.getMax()+"]";
                }
            }
        }
        else if (item.equals(NEMOConstants.Property)){
            List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.flow.instance.Property> properties = flow.getProperty();
            if (properties!=null){
                for (org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.flow.instance.Property property:properties){
                    errorInfo += property.getPropertyName().getValue()+":";
                    List<IntValue> intValues = property.getPropertyValues().getIntValue();
                    List<StringValue> stringValues = property.getPropertyValues().getStringValue();
                    org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.property.values.RangeValue rangeValue = property.getPropertyValues().getRangeValue();
                    if (intValues!=null){
                        for (IntValue intValue : intValues){
                            errorInfo += intValue.getValue()+",";
                        }
                        errorInfo = errorInfo.substring(0,errorInfo.length()-1);
                    }
                    if (stringValues!=null){
                        for (StringValue stringValue : stringValues){
                            errorInfo += stringValue.getValue() +",";
                        }
                        errorInfo = errorInfo.substring(0,errorInfo.length()-1);
                    }
                    if (rangeValue!=null){
                        errorInfo+="["+rangeValue.getMin()+","+rangeValue.getMax()+"]";
                    }
                }
            }
        }
        else {
            List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.flow.instance.Property> properties = flow.getProperty();
            Boolean itemExist = false;
            if (properties!=null){
                for (org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.flow.instance.Property property:properties){
                    if (property.getPropertyName().getValue().equals(item)){
                        itemExist = true;
                        List<IntValue> intValues = property.getPropertyValues().getIntValue();
                        List<StringValue> stringValues = property.getPropertyValues().getStringValue();
                        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.property.values.RangeValue rangeValue = property.getPropertyValues().getRangeValue();
                        if (intValues!=null){
                            for (IntValue intValue : intValues){
                                errorInfo += intValue.getValue()+",";
                            }
                            errorInfo = errorInfo.substring(0,errorInfo.length()-1);
                        }
                        if (stringValues!=null){
                            for (StringValue stringValue : stringValues){
                                errorInfo += stringValue.getValue() +",";
                            }
                            errorInfo = errorInfo.substring(0,errorInfo.length()-1);
                        }
                        if (rangeValue!=null){
                            errorInfo+="["+rangeValue.getMin()+","+rangeValue.getMax()+"]";
                        }
                    }
                }
            }
            if (!itemExist){
                return "The item " + item + " is not included in "+flow.getFlowName().getValue()+";";
            }
        }
        return errorInfo;
    }

    private String queryOperation(User user, String item, Operation operation){
        String errorInfo =NEMOConstants.Results+":";
        if (item.equals(NEMOConstants.Priority)){
            errorInfo += operation.getPriority();
        }
        else if (item.equals(NEMOConstants.Target)){
            if (user.getObjects().getNode()!=null){
                for (Node node:user.getObjects().getNode()){
                    if (node.getNodeId().getValue().equals(operation.getTargetObject().getValue())){
                        errorInfo += node.getNodeName().getValue();
                    }
                }
            }
            if (user.getObjects().getConnection()!=null){
                for (Connection connection : user.getObjects().getConnection()){
                    if (connection.getConnectionId().getValue().equals(operation.getTargetObject().getValue())){
                        errorInfo += connection.getConnectionName().getValue();
                    }
                }
            }
            if (user.getObjects().getFlow()!=null){
                for (Flow flow : user.getObjects().getFlow()){
                    if (flow.getFlowId().getValue().equals(operation.getTargetObject().getValue())){
                        errorInfo += flow.getFlowName().getValue();
                    }
                }
            }
        }
        else if (item.equals(NEMOConstants.Condition)){
            List<ConditionSegment> conditionSegments = operation.getConditionSegment();
            if (conditionSegments!=null){
                for (ConditionSegment conditionSegment : conditionSegments){
                    if (conditionSegment.getPrecursorRelationOperator()!=null){
                        int preRelation = conditionSegment.getPrecursorRelationOperator().getIntValue();
                        switch (preRelation) {
                            case 0:
                                break;
                            case 1:
                                errorInfo += NEMOConstants.and+"(";
                                break;
                            case 2:
                                errorInfo += NEMOConstants.or+"(";
                                break;
                            case 3:
                                errorInfo += NEMOConstants.not+"(";
                                break;
                        }
                   }
                    else {
                        errorInfo += "(";
                    }
                    errorInfo += conditionSegment.getConditionParameterName().getValue();
                    int matchPattern = conditionSegment.getConditionParameterMatchPattern().getIntValue();
                    switch (matchPattern){
                        case 0:
                            errorInfo += NEMOConstants.less_than;
                            break;
                        case 1:
                            errorInfo += NEMOConstants.not_less_than;
                            break;
                        case 2:
                            errorInfo += NEMOConstants.equal;
                            break;
                        case 3:
                            errorInfo += NEMOConstants.not_equal;
                            break;
                        case 4:
                            errorInfo += NEMOConstants.greater_than;
                            break;
                        case 5:
                            errorInfo += NEMOConstants.not_greater_than;
                            break;
                        case 6:
                            errorInfo += NEMOConstants.between;
                            break;
                    }
                    Long intValue = conditionSegment.getConditionParameterTargetValue().getIntValue();
                    String stringValue = conditionSegment.getConditionParameterTargetValue().getStringValue();
                    //todo rangevalue
                    if (intValue!=null){
                        errorInfo +=intValue;
                    }
                    if (stringValue!=null){
                        errorInfo+=stringValue;
                    }
                    errorInfo += ")";
                }
            }
        }
       else if (item.equals(NEMOConstants.Action)) {
            List<Action> actions = operation.getAction();
            if (actions!=null){
                for (Action action : actions) {
                    errorInfo += action.getActionName().getValue() + ":";
                    List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.IntValue> intValues = action.getParameterValues().getIntValue();
                    List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.StringValue> stringValues = action.getParameterValues().getStringValue();
                    org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.RangeValue rangeValue = action.getParameterValues().getRangeValue();
                    if (intValues!=null){
                        for (org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.IntValue intValue : intValues){
                            errorInfo += intValue.getValue()+",";
                        }
                        errorInfo = errorInfo.substring(0,errorInfo.length()-1);
                    }
                    if (stringValues!=null){
                        for (org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.StringValue stringValue : stringValues){
                            errorInfo += stringValue.getValue() +",";
                        }
                        errorInfo = errorInfo.substring(0,errorInfo.length()-1);
                    }
                    if (rangeValue != null) {
                        errorInfo += "[" + rangeValue.getMin() + "," + rangeValue.getMax() + "]";
                    }
                }
            }
        }
        else {
            List<Action> actions = operation.getAction();
            Boolean itemExist = false;
            if (actions!=null){
                for (Action action : actions){
                    if (action.getActionName().getValue().equals(item)){
                        itemExist = true;
                        errorInfo += action.getActionName().getValue() + ":";
                        List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.IntValue> intValues = action.getParameterValues().getIntValue();
                        List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.StringValue> stringValues = action.getParameterValues().getStringValue();
                        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.RangeValue rangeValue = action.getParameterValues().getRangeValue();
                        if (intValues!=null){
                            for (org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.IntValue intValue : intValues){
                                errorInfo += intValue.getValue()+",";
                            }
                            errorInfo = errorInfo.substring(0,errorInfo.length()-1);
                        }
                        if (stringValues!=null){
                            for (org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.StringValue stringValue : stringValues){
                                errorInfo += stringValue.getValue() +",";
                            }
                            errorInfo = errorInfo.substring(0,errorInfo.length()-1);
                        }
                        if (rangeValue != null) {
                            errorInfo += "[" + rangeValue.getMin() + "," + rangeValue.getMax() + "]";
                        }
                    }
                }
            }
            if (!itemExist){
                return "The item " + item + " is not included in the target " +operation.getOperationName().getValue();
            }
        }
        return errorInfo;
    }

    private String queryAllNode(User user){
        String errorInfo =NEMOConstants.Results+":";
        if (user.getObjects()!=null){
            if (user.getObjects().getNode()!=null){
                List<Node> nodeList = user.getObjects().getNode();
                for (Node node : nodeList){
                    errorInfo += NEMOConstants.Id +":" + node.getNodeId().getValue()+" ";
                    errorInfo += NEMOConstants.Name +":" + node.getNodeName().getValue()+" ";
                    errorInfo += NEMOConstants.Type +":" + node.getNodeType().getValue()+" ";
                    if (node.getSubNode()!=null){
                        errorInfo+=NEMOConstants.SubNodes+":";
                        List<SubNode> subNodeList = node.getSubNode();
                        for (SubNode subNode :subNodeList){
                            for (Node node1:nodeList){
                                if (node1.getNodeId().equals(subNode.getNodeId())){
                                    errorInfo+=node1.getNodeName().getValue()+",";
                                }
                            }
                        }
                        errorInfo = errorInfo.substring(0,errorInfo.length()-1);
                    }
                    if (node.getProperty()!=null){
                        errorInfo += NEMOConstants.Property+":";
                        for (Property property:node.getProperty()){
                            errorInfo+=property.getPropertyName().getValue()+":";
                            List<IntValue> intValues = property.getPropertyValues().getIntValue();
                            List<StringValue> stringValues = property.getPropertyValues().getStringValue();
                            RangeValue rangeValue = property.getPropertyValues().getRangeValue();
                            if (intValues!=null){
                                for (IntValue intValue : intValues){
                                    errorInfo += intValue.getValue()+",";
                                }
                                errorInfo = errorInfo.substring(0,errorInfo.length()-1);
                            }
                            if (stringValues!=null){
                                for (StringValue stringValue : stringValues){
                                    errorInfo += stringValue.getValue() +",";
                                }
                                errorInfo = errorInfo.substring(0,errorInfo.length()-1);
                            }
                            if (rangeValue!=null){
                                errorInfo+="["+rangeValue.getMin()+","+rangeValue.getMax()+"]";
                            }
                            errorInfo +=" ";
                        }
                    }
                }
            }
        }
        return errorInfo;
    }

    private String queryAllConnection(User user){
        String errorInfo =NEMOConstants.Results+":";
        if (user.getObjects()!=null){
            if (user.getObjects().getConnection()!=null){
                List<Connection> connectionList = user.getObjects().getConnection();
                for (Connection connection : connectionList){
                    errorInfo += NEMOConstants.Id +":" +connection.getConnectionId().getValue()+ " ";
                    errorInfo+= NEMOConstants.Name +":" + connection.getConnectionName().getValue()+" ";
                    errorInfo += NEMOConstants.Type +":"+connection.getConnectionType().getValue()+" ";
                    if (connection.getEndNode()!=null){
                        errorInfo += NEMOConstants.Endnodes +":";
                        List<EndNode> endNodeList = connection.getEndNode();
                        for (EndNode endNode:endNodeList){
                            for (Node node:user.getObjects().getNode()){
                                if (endNode.getNodeId().equals(node.getNodeId())){
                                    errorInfo+=node.getNodeName().getValue()+",";
                                }
                            }
                        }
                        errorInfo = errorInfo.substring(0,errorInfo.length()-1) + " ";
                    }
                    if (connection.getProperty()!=null){
                        errorInfo += NEMOConstants.Property +":";
                        for (org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.connection.instance.Property property:connection.getProperty()){
                            errorInfo+=property.getPropertyName().getValue()+":";
                            List<IntValue> intValues = property.getPropertyValues().getIntValue();
                            List<StringValue> stringValues = property.getPropertyValues().getStringValue();
                            RangeValue rangeValue = property.getPropertyValues().getRangeValue();
                            if (intValues!=null){
                                for (IntValue intValue : intValues){
                                    errorInfo += intValue.getValue()+",";
                                }
                                errorInfo = errorInfo.substring(0,errorInfo.length()-1);
                            }
                            if (stringValues!=null){
                                for (StringValue stringValue : stringValues){
                                    errorInfo += stringValue.getValue() +",";
                                }
                                errorInfo = errorInfo.substring(0,errorInfo.length()-1);
                            }
                            if (rangeValue!=null){
                                errorInfo+="["+rangeValue.getMin()+","+rangeValue.getMax()+"]";
                            }
                            errorInfo +=" ";
                        }
                    }
                }
            }
        }
        return errorInfo;
    }

    private String queryAllFlow(User user){
        String errorInfo =NEMOConstants.Results+":";
        if (user.getObjects()!=null){
            if (user.getObjects().getFlow()!=null){
                List<Flow> flowList = user.getObjects().getFlow();
                for (Flow flow : flowList){
                    errorInfo += NEMOConstants.Id +":" + flow.getFlowId().getValue()+" ";
                    errorInfo += NEMOConstants.Name +":" +flow.getFlowName().getValue()+" ";
                    if (flow.getMatchItem()!=null){
                        errorInfo += NEMOConstants.Match+": ";
                        List<MatchItem> matchItemList = flow.getMatchItem();
                        for (MatchItem matchItem : matchItemList){
                            errorInfo += matchItem.getMatchItemName().getValue()+";";
                            Long intValues = matchItem.getMatchItemValue().getIntValue();
                            String stringValues = matchItem.getMatchItemValue().getStringValue();
                            org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.match.item.instance.match.item.value.RangeValue rangeValue = matchItem.getMatchItemValue().getRangeValue();
                            if (intValues!=null){
                                errorInfo+=intValues;
                            }
                            if (stringValues!=null){
                                errorInfo += stringValues;
                            }
                            if (rangeValue!=null){
                                errorInfo+="["+rangeValue.getMin()+","+rangeValue.getMin()+"]";
                            }
                        }
                    }
                    if (flow.getProperty()!=null){
                        errorInfo += NEMOConstants.Property+": ";
                        List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.flow.instance.Property> properties = flow.getProperty();
                        for (org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.flow.instance.Property property :properties){
                            errorInfo+=property.getPropertyName().getValue()+":";
                            List<IntValue> intValues = property.getPropertyValues().getIntValue();
                            List<StringValue> stringValues = property.getPropertyValues().getStringValue();
                            RangeValue rangeValue = property.getPropertyValues().getRangeValue();
                            if (intValues!=null){
                                for (IntValue intValue : intValues){
                                    errorInfo += intValue.getValue()+",";
                                }
                                errorInfo = errorInfo.substring(0,errorInfo.length()-1);
                            }
                            if (stringValues!=null){
                                for (StringValue stringValue : stringValues){
                                    errorInfo += stringValue.getValue() +",";
                                }
                                errorInfo = errorInfo.substring(0,errorInfo.length()-1);
                            }
                            if (rangeValue!=null){
                                errorInfo+="["+rangeValue.getMin()+","+rangeValue.getMax()+"]";
                            }
                            errorInfo +=" ";
                        }
                    }
                }
            }
        }
        return errorInfo;
    }

    private String queryAllOperation(User user){
        String errorInfo =NEMOConstants.Results+":";
        if (user.getOperations()!=null){
            if (user.getOperations().getOperation()!=null){
                List<Operation> operationList = user.getOperations().getOperation();
                for (Operation operation : operationList){
                    errorInfo += NEMOConstants.Id+":" + operation.getOperationId().getValue() +" ";
                    errorInfo += NEMOConstants.Name+":" + operation.getOperationName().getValue()+" ";
                    errorInfo += NEMOConstants.Priority+":" + operation.getPriority()+" ";
                    errorInfo += NEMOConstants.Target+":";
                    if (user.getObjects().getNode()!=null){
                        for (Node node:user.getObjects().getNode()){
                            if (node.getNodeId().equals(operation.getTargetObject())){
                                errorInfo+=node.getNodeName().getValue()+" ";
                            }
                        }
                    }
                    if (user.getObjects().getConnection()!=null){
                        for (Connection connection:user.getObjects().getConnection()){
                            if (connection.getConnectionId().equals(operation.getTargetObject())){
                                errorInfo+=connection.getConnectionName().getValue()+" ";
                            }
                        }
                    }
                    if (user.getObjects().getFlow()!=null){
                        for (Flow flow:user.getObjects().getFlow()){
                            if (flow.getFlowId().equals(operation.getTargetObject())){
                                errorInfo += flow.getFlowName().getValue()+" ";
                            }
                        }
                    }
                    if (operation.getConditionSegment()!=null){
                        errorInfo += NEMOConstants.Condition+": ";
                        List<ConditionSegment> conditionSegmentList = operation.getConditionSegment();
                        for (ConditionSegment conditionSegment : conditionSegmentList){
                            int preRelation = conditionSegment.getPrecursorRelationOperator().getIntValue();
                            switch (preRelation) {
                                case 0:
                                    break;
                                case 1:
                                    errorInfo += NEMOConstants.and+"(";
                                    break;
                                case 2:
                                    errorInfo += NEMOConstants.or+"(";
                                    break;
                                case 3:
                                    errorInfo += NEMOConstants.not+"(";
                                    break;
                            }
                            errorInfo += conditionSegment.getConditionParameterName().getValue();
                            int matchPattern = conditionSegment.getConditionParameterMatchPattern().getIntValue();
                            switch (matchPattern){
                                case 0:
                                    errorInfo += NEMOConstants.less_than;
                                    break;
                                case 1:
                                    errorInfo += NEMOConstants.not_less_than;
                                    break;
                                case 2:
                                    errorInfo += NEMOConstants.equal;
                                    break;
                                case 3:
                                    errorInfo += NEMOConstants.not_equal;
                                    break;
                                case 4:
                                    errorInfo += NEMOConstants.greater_than;
                                    break;
                                case 5:
                                    errorInfo += NEMOConstants.not_greater_than;
                                    break;
                                case 6:
                                    errorInfo += NEMOConstants.between;
                                    break;
                            }
                            Long intValue = conditionSegment.getConditionParameterTargetValue().getIntValue();
                            String stringValue = conditionSegment.getConditionParameterTargetValue().getStringValue();
                            //todo rangevalue
                            if (intValue!=null){
                                errorInfo +=intValue;
                            }
                            if (stringValue!=null){
                                errorInfo+=stringValue;
                            }
                        }

                        }
                    if (operation.getAction()!=null){
                        errorInfo += NEMOConstants.Action+": ";
                        List<Action> actionList = operation.getAction();
                        for (Action action : actionList){
                            errorInfo += action.getActionName().getValue() +":";
                            List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.IntValue> intValues = action.getParameterValues().getIntValue();
                            List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.StringValue> stringValues = action.getParameterValues().getStringValue();
                            org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.RangeValue rangeValue = action.getParameterValues().getRangeValue();
                            if (intValues!=null){
                                for (org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.IntValue intValue : intValues){
                                    errorInfo += intValue.getValue()+",";
                                }
                                errorInfo = errorInfo.substring(0,errorInfo.length()-1);
                            }
                            if (stringValues!=null){
                                for (org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.StringValue stringValue : stringValues){
                                    errorInfo += stringValue.getValue() +",";
                                }
                                errorInfo = errorInfo.substring(0,errorInfo.length()-1);
                            }
                            if (rangeValue!=null){
                                errorInfo += "["+rangeValue.getMin()+","+ rangeValue.getMax()+"]";
                            }
                            errorInfo +=")";
                        }
                    }
                    }
                }
            }
        return errorInfo;
        }
}
