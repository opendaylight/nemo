/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.user.vnspacemanager.languagestyle;

import org.opendaylight.nemo.user.vnspacemanager.languagestyle.Query;
import org.opendaylight.nemo.user.tenantmanager.TenantManage;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.Objects;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.NodeName;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.ConnectionName;
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
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.ConnectionType;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.ConnectionId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.NodeType;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.FlowName;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.MatchItemName;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.match.item.instance.MatchItemValue;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.operations.Operation;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.Operations;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.OperationName;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.NodeId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.node.instance.Property;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.PropertyName;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.PropertyValues;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.FlowId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.OperationId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.ObjectId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.condition.instance.ConditionSegment;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.condition.instance.ConditionSegment.PrecursorRelationOperator;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.ConditionParameterName;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.condition.instance.ConditionSegment.ConditionParameterMatchPattern;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.condition.instance.condition.segment.ConditionParameterTargetValue;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.operation.instance.Action;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.ActionName;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.ParameterValues;
import java.util.LinkedList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.*;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
/**
 * Created by ldzd11 on 2015/12/14.
 */
public class QueryTest {

    private Query query;
    private ParameterValues parameterValues;
    private ConditionParameterTargetValue conditionParameterTargetValue;
    private ActionName actionName;
    private PrecursorRelationOperator precursorRelationOperator;
   // private ConditionParameterMatchPattern conditionParameterMatchPattern;
    private ConditionParameterName conditionParameterName;
    private ObjectId objectId;
    private ConditionSegment conditionSegment;
    private OperationId operationId;
    private FlowId flowId;
    private PropertyName propertyName;
    private NodeId nodeId;
    private ConnectionId connectionId;
    private OperationName operationName;
    private MatchItemValue matchItemValue;
    private  MatchItemName matchItemName;
    private FlowName flowName;
    private NodeName nodeName;
    private TenantManage tenantManage;
    private ConnectionType connectionType;
    private NodeType nodeType;
    private Objects objects;
    private User user;
    private ConnectionName connectionName;
    private UserId userId;
    private String item;
    private String target;
    private Node node;
    private List<Node> nodelist;
    private Connection connection;
    private List<Connection> connectionList;

    private Flow flow;
    private List<Flow> flowList;

    private MatchItem matchItem;
    private List<MatchItem> matchItemList;

    private Operations operations;
    private Operation operation;
    private List<Operation> operationList;

    private  SubNode subNode;
    private List<SubNode> subNodeList;

    private  Action action;
    private List<Action> actionList;

    private  EndNode endNode;
    private List<EndNode> endNodeList;

    private Property property;
    private List<Property> propertyList;

    private org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.connection.instance.Property propertyconnection;
    private List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.connection.instance.Property> propertyconnectionList;

    private org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.flow.instance.Property propertyflow;
    private List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.flow.instance.Property> propertyflowList;
    private PropertyValues propertyValues;

    private List<ConditionSegment> conditionSegmentList;


    @org.junit.Before
    public void setUp() throws Exception {
        tenantManage = mock(TenantManage.class);
        parameterValues = mock(ParameterValues.class);
        actionName = mock(ActionName.class);
        conditionParameterTargetValue = mock(ConditionParameterTargetValue.class);
        //conditionParameterMatchPattern = mock(ConditionParameterMatchPattern.class);
        conditionParameterName = mock(ConditionParameterName.class);
       // precursorRelationOperator = mock(PrecursorRelationOperator.class);
        conditionSegment = mock(ConditionSegment.class);
        objectId = mock(ObjectId.class);
        operationId = mock(OperationId.class);
        flowId = mock(FlowId.class);
        connectionId = mock(ConnectionId.class);
        propertyValues = mock(PropertyValues.class);
        propertyName = mock(PropertyName.class);
        nodeId = mock(NodeId.class);
        operationName = mock(OperationName.class);
        matchItemValue = mock(MatchItemValue.class);
        matchItemName = mock(MatchItemName.class);
        flowName = mock(FlowName.class);
        connectionType = mock(ConnectionType.class);
        nodeType = mock(NodeType.class);
        userId = mock(UserId.class);
        item = new String();
        target = new String();
        user = mock(User.class);
        objects = mock(Objects.class);
        node=mock(Node.class);
        nodeName = mock(NodeName.class);
        nodelist=new ArrayList<Node>(1);
        nodelist.add(node);
        connectionName = mock(ConnectionName.class);

        connection = mock(Connection.class);
        connectionList = new ArrayList<Connection>(1);
        connectionList.add(connection);

        flow = mock(Flow.class);
        flowList = new ArrayList<Flow>(1);
        flowList.add(flow);

        matchItem = mock(MatchItem.class);
        matchItemList =  new ArrayList<MatchItem>(1);
        matchItemList.add(matchItem);

        operations = mock(Operations.class);
        operation = mock(Operation.class);
        operationList =  new ArrayList<Operation>(1);
        operationList.add(operation);

        subNode = mock(SubNode.class);
        subNodeList =  new ArrayList<SubNode>(1);
        subNodeList.add(subNode);

        endNode = mock(EndNode.class);
        endNodeList =  new ArrayList<EndNode>(1);
        endNodeList.add(endNode);

        property = mock(Property.class);
        propertyList =  new ArrayList<Property>(1);
        propertyList.add(property);

        propertyconnection = mock(org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.connection.instance.Property.class);
        propertyconnectionList =  new ArrayList<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.connection.instance.Property>(1);
        propertyconnectionList.add(propertyconnection);

        conditionSegment = mock(ConditionSegment.class);
        conditionSegmentList =  new ArrayList<ConditionSegment>(0);
        //conditionSegmentList.add(conditionSegment);

        propertyflow = mock(org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.flow.instance.Property.class);
        propertyflowList = new ArrayList<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.flow.instance.Property>(1);
        propertyflowList.add(propertyflow);

        action = mock(Action.class);
        actionList =  new ArrayList<Action>(1);
        actionList.add(action);

        query = new Query(tenantManage);

    }

    @org.junit.Test
    public void testQuery() throws Exception {

        when(tenantManage.getUser()).thenReturn(user);
        target = new String("test");
        when(user.getObjects()).thenReturn(objects);
        when(user.getObjects().getNode()).thenReturn(nodelist);
        when(node.getNodeName()).thenReturn(nodeName);
        when(node.getNodeName().getValue()).thenReturn(new String("test"));
        //to quertNode(user,item,node)
        item = new String("Type");
        when(node.getNodeType()).thenReturn(nodeType);
        when(node.getNodeType().getValue()).thenReturn(new String("node"));
        Assert.assertEquals(query.query(userId, item, target), "Results:node");

        item = new String("Subnodes");
        when(node.getSubNode()).thenReturn(subNodeList);
        when(user.getObjects().getNode()).thenReturn(nodelist);
        when(subNode.getNodeId()).thenReturn(nodeId);
        when(node.getNodeId()).thenReturn(nodeId);
        Assert.assertEquals(query.query(userId, item, target), "Results:test");

        item = new String("Property");
        when(node.getProperty()).thenReturn(propertyList);
        when(property.getPropertyName()).thenReturn(propertyName);
        when(property.getPropertyName().getValue()).thenReturn("propertyname");
        when(property.getPropertyValues()).thenReturn(propertyValues);
        when(property.getPropertyValues().getIntValue()).thenReturn(null);
        when(property.getPropertyValues().getStringValue()).thenReturn(null);
        when(property.getPropertyValues().getRangeValue()).thenReturn(null);
        Assert.assertEquals(query.query(userId, item, target), "Results:propertyname:");

        item = new String("exception");
        when(property.getPropertyName().getValue()).thenReturn(new String("exception"));
        Assert.assertEquals(query.query(userId, item, target), "Results:");

        when(property.getPropertyName().getValue()).thenReturn(new String("exceptionnull"));
        Assert.assertEquals(query.query(userId, item, target), "The item " + item + " is not included in " + node.getNodeName().getValue() + ";");


        //if
        when(user.getObjects().getNode()).thenReturn(null);
        when(user.getObjects().getConnection()).thenReturn(connectionList);
        when(connection.getConnectionName()).thenReturn(connectionName);
        when(connection.getConnectionName().getValue()).thenReturn(new String("test"));
        //to queryConnection(user,item,connection)
        item = new String("Type");
        when(connection.getConnectionType()).thenReturn(connectionType);
        when(connection.getConnectionType().getValue()).thenReturn(new String("connection"));
        Assert.assertEquals(query.query(userId, item, target), "Results:connection");

        item = new String("Endnodes");
        when(connection.getEndNode()).thenReturn(endNodeList);
        when(user.getObjects().getNode()).thenReturn(nodelist);
        when(endNode.getNodeId()).thenReturn(nodeId);
        when(node.getNodeId()).thenReturn(nodeId);
        Assert.assertEquals(query.query(userId, item, target), "Results:test");

        item = new String("Property");
        when(connection.getProperty()).thenReturn(propertyconnectionList);
        when(propertyconnection.getPropertyName()).thenReturn(propertyName);
        when(propertyconnection.getPropertyName().getValue()).thenReturn("propertyname");
        when(propertyconnection.getPropertyValues()).thenReturn(propertyValues);
        when(propertyconnection.getPropertyValues().getIntValue()).thenReturn(null);
        when(propertyconnection.getPropertyValues().getStringValue()).thenReturn(null);
        when(propertyconnection.getPropertyValues().getRangeValue()).thenReturn(null);
        Assert.assertEquals(query.query(userId, item, target), "Results:propertyname:");

        item = new String("exception");
        when(connection.getProperty()).thenReturn(propertyconnectionList);
        when(propertyconnection.getPropertyName().getValue()).thenReturn("exception");
        Assert.assertEquals(query.query(userId, item, target), "Results:");

        when(propertyconnection.getPropertyName().getValue()).thenReturn("exceptionnnull");
        Assert.assertEquals(query.query(userId, item, target), "The item " + item + " is not included in " + connection.getConnectionName().getValue() + ";");


        //if
        when(user.getObjects().getNode()).thenReturn(null);
        when(user.getObjects().getConnection()).thenReturn(null);
        when(user.getObjects().getFlow()).thenReturn(flowList);
        when(flow.getFlowName()).thenReturn(flowName);
        when(flow.getFlowName().getValue()).thenReturn("test");
        //to queryFlow(item,flow);
        item = new String("Match");
        when(flow.getMatchItem()).thenReturn(matchItemList);
        when(matchItem.getMatchItemName()).thenReturn(matchItemName);
        when(matchItem.getMatchItemName().getValue()).thenReturn(new String("flow"));
        when(matchItem.getMatchItemValue()).thenReturn(matchItemValue);
        when(matchItem.getMatchItemValue().getIntValue()).thenReturn(null);
        when(matchItem.getMatchItemValue().getStringValue()).thenReturn(null);
        when(matchItem.getMatchItemValue().getRangeValue()).thenReturn(null);
        Assert.assertEquals(query.query(userId, item, target), "Results:flow:");

        item = new String("Property");
        when(flow.getProperty()).thenReturn(propertyflowList);
        when(propertyflow.getPropertyName()).thenReturn(propertyName);
        when(propertyflow.getPropertyName().getValue()).thenReturn(new String("propertyname"));
        when(propertyflow.getPropertyValues()).thenReturn(propertyValues);
        when(propertyflow.getPropertyValues().getIntValue()).thenReturn(null);
        when(propertyflow.getPropertyValues().getStringValue()).thenReturn(null);
        when(propertyflow.getPropertyValues().getRangeValue()).thenReturn(null);
        Assert.assertEquals(query.query(userId, item, target), "Results:propertyname:");

        item = new String("exception");
        when(flow.getProperty()).thenReturn(propertyflowList);
        when(property.getPropertyName().getValue()).thenReturn(new String("exception"));
        Assert.assertEquals(query.query(userId, item, target), "Results:");

        when(property.getPropertyName().getValue()).thenReturn(new String("ecxeptionnull"));
        Assert.assertEquals(query.query(userId, item, target), "The item " + item + " is not included in " + flow.getFlowName().getValue() + ";");


        //if
        when(user.getObjects()).thenReturn(null);
        when(user.getOperations()).thenReturn(operations);
        when(user.getOperations().getOperation()).thenReturn(operationList);
        when(operation.getOperationName()).thenReturn(operationName);
        when(operation.getOperationName().getValue()).thenReturn(new String("test"));
        //to queryOperation(user,item,operation);
        item = new String("Priority");
        long a = 123;
        when(operation.getPriority()).thenReturn(a);
        Assert.assertEquals(query.query(userId, item, target), "Results:" + a);

        item = new String("Target");
        when(user.getObjects()).thenReturn(objects);
        when(user.getObjects().getNode()).thenReturn(nodelist);
        when(node.getNodeId().getValue()).thenReturn(new String("123"));
        when(operation.getTargetObject()).thenReturn(nodeId);
        when(operation.getTargetObject().getValue()).thenReturn(new String("123"));
        when(node.getNodeName()).thenReturn(nodeName);
        when(node.getNodeName().getValue()).thenReturn(new String("test"));

        when(user.getObjects().getConnection()).thenReturn(connectionList);
        when(connection.getConnectionId()).thenReturn(connectionId);
        when(operation.getTargetObject()).thenReturn(connectionId);
        when(operation.getTargetObject().getValue()).thenReturn(new String("123"));
        when(connection.getConnectionId().getValue()).thenReturn(new String("123"));
        when(connection.getConnectionName().getValue()).thenReturn(new String("connectionname"));

        when(user.getObjects().getFlow()).thenReturn(flowList);
        when(flow.getFlowId()).thenReturn(flowId);
        when(flow.getFlowId().getValue()).thenReturn(new String("123"));
        when(operation.getTargetObject()).thenReturn(flowId);
        when(operation.getTargetObject().getValue()).thenReturn(new String("123"));
        when(flow.getFlowName().getValue()).thenReturn(new String("flowname"));
        Assert.assertEquals(query.query(userId, item, target), "Results:testconnectionnameflowname");

        item = new String("Action");
        when(operation.getAction()).thenReturn(actionList);
        when(action.getActionName()).thenReturn(actionName);
        when(action.getActionName().getValue()).thenReturn(new String("actionname"));
        when(action.getParameterValues()).thenReturn(parameterValues);
        when(action.getParameterValues().getIntValue()).thenReturn(null);
        when(action.getParameterValues().getStringValue()).thenReturn(null);
        when(action.getParameterValues().getRangeValue()).thenReturn(null);
        Assert.assertEquals(query.query(userId, item, target), "Results:actionname:");

        item = new String("exception");
        when(operation.getAction()).thenReturn(actionList);
        when(action.getActionName()).thenReturn(actionName);
        when(action.getActionName().getValue()).thenReturn(new String("exception"));
        Assert.assertEquals(query.query(userId, item, target), "Results:exception:");

        when(action.getActionName().getValue()).thenReturn(new String("exceptionnull"));
        Assert.assertEquals(query.query(userId, item, target), "The item " + item + " is not included in the target " +operation.getOperationName().getValue());
        //Assert.assertEquals(query.query(userId, item, target), "Results:test" );

        //if
        when(tenantManage.getUser()).thenReturn(null);
        Assert.assertEquals(query.query(userId, item, target), "The target " + target + " is not exist in user vn space.");

        target = null;
        when(tenantManage.getUser()).thenReturn(user);
        //if
        item = new String("nodes");
        //into queryAllNode(user)
        when(user.getObjects()).thenReturn(objects);
        when(user.getObjects().getNode()).thenReturn(nodelist);

        when(node.getNodeId()).thenReturn(nodeId);
        when(node.getNodeName()).thenReturn(nodeName);
        when(node.getNodeType()).thenReturn(nodeType);

        when(node.getNodeId().getValue()).thenReturn(new String("id"));
        when(node.getNodeName().getValue()).thenReturn(new String("name2"));
        when(node.getNodeType().getValue()).thenReturn(new String("type"));

        when(node.getSubNode()).thenReturn(subNodeList);
        when(subNode.getNodeId()).thenReturn(nodeId);

        when(node.getProperty()).thenReturn(propertyList);
        when(property.getPropertyName()).thenReturn(propertyName);
        when(property.getPropertyName().getValue()).thenReturn(new String("property"));
        when(property.getPropertyValues()).thenReturn(propertyValues);
        when(property.getPropertyValues().getIntValue()).thenReturn(null);
        when(property.getPropertyValues().getStringValue()).thenReturn(null);
        when(property.getPropertyValues().getRangeValue()).thenReturn(null);

        Assert.assertEquals(query.query(userId, item, target), "Results:Id:id Name:name2 Type:type Subnodes:name2Property:property: ");







        //if
        item = new String("connections");
        //into queryAllConnection(user);
        when(user.getObjects()).thenReturn(objects);
        when(user.getObjects().getConnection()).thenReturn(connectionList);
        when(connection.getConnectionId()).thenReturn(connectionId);
        when(connection.getConnectionName()).thenReturn(connectionName);
        when(connection.getConnectionType()).thenReturn(connectionType);
        when(connection.getConnectionId().getValue()).thenReturn(new String("id"));
        when(connection.getConnectionName().getValue()).thenReturn(new String("name"));
        when(connection.getConnectionType().getValue()).thenReturn(new String("type"));
        when(connection.getEndNode()).thenReturn(endNodeList);
        when(user.getObjects().getNode()).thenReturn(nodelist);
        when(endNode.getNodeId()).thenReturn(nodeId);
        when(node.getNodeId()).thenReturn(nodeId);
        when(node.getNodeName().getValue()).thenReturn(new String("name2"));

        when(connection.getProperty()).thenReturn(propertyconnectionList);
        when(propertyconnection.getPropertyName()).thenReturn(propertyName);
        when(propertyconnection.getPropertyName().getValue()).thenReturn(new String("property"));
        when(propertyconnection.getPropertyValues()).thenReturn(propertyValues);
        when(propertyconnection.getPropertyValues().getIntValue()).thenReturn(null);
        when(propertyconnection.getPropertyValues().getStringValue()).thenReturn(null);
        when(propertyconnection.getPropertyValues().getRangeValue()).thenReturn(null);

        Assert.assertEquals(query.query(userId, item, target), "Results:Id:id Name:name Type:type Endnodes:name2 Property:property: ");


        //if
        item = new String("flows");
        //into queryAllFlow(user)
        when(user.getObjects()).thenReturn(objects);
        when(user.getObjects().getFlow()).thenReturn(flowList);
        when(flow.getFlowId()).thenReturn(flowId);
        when(flow.getFlowId().getValue()).thenReturn(new String("id"));
        when(flow.getFlowName().getValue()).thenReturn(new String("name"));
        when(flow.getMatchItem()).thenReturn(matchItemList);
        when(matchItem.getMatchItemName().getValue()).thenReturn(new String("name2"));
        when(matchItem.getMatchItemValue().getIntValue()).thenReturn(null);
        when(matchItem.getMatchItemValue().getStringValue()).thenReturn(null);
        when(matchItem.getMatchItemValue().getRangeValue()).thenReturn(null);

        when(flow.getProperty()).thenReturn(propertyflowList);
        when(propertyflow.getPropertyName()).thenReturn(propertyName);
        when(propertyflow.getPropertyName().getValue()).thenReturn(new String("property"));
        when(propertyflow.getPropertyValues()).thenReturn(propertyValues);
        when(propertyflow.getPropertyValues().getIntValue()).thenReturn(null);
        when(propertyflow.getPropertyValues().getStringValue()).thenReturn(null);
        when(propertyflow.getPropertyValues().getRangeValue()).thenReturn(null);

        Assert.assertEquals(query.query(userId, item, target), "Results:Id:id Name:name Match: name2;Property: property: ");

        //if
        item = new String("operations");
        //into queryAllOperation(user)'
        when(user.getOperations()).thenReturn(operations);
        when(operation.getOperationId()).thenReturn(operationId);
        when(operation.getOperationId().getValue()).thenReturn(new String("id"));
        when(operation.getOperationName().getValue()).thenReturn(new String("name"));
        when(operation.getPriority()).thenReturn(a);

        when(user.getObjects().getNode()).thenReturn(nodelist);
        when(node.getNodeId()).thenReturn(nodeId);
        when(operation.getTargetObject()).thenReturn(nodeId);
        when(node.getNodeName().getValue()).thenReturn(new String("name2"));

        when(user.getObjects().getFlow()).thenReturn(flowList);
        when(flow.getFlowId()).thenReturn(flowId);
        when(operation.getTargetObject()).thenReturn(flowId);
        when(flow.getFlowName().getValue()).thenReturn(new String("name3"));

        when(operation.getConditionSegment()).thenReturn(conditionSegmentList);
       // when(conditionSegment.getPrecursorRelationOperator()).thenReturn(precursorRelationOperator);
      //  when(conditionSegment.getPrecursorRelationOperator().getIntValue()).thenReturn(1);//case1

      //  when(conditionSegment.getConditionParameterName()).thenReturn(conditionParameterName);
        //when(conditionSegment.getConditionParameterName().getValue()).thenReturn(new String("?"));
       // when(conditionSegment.getConditionParameterMatchPattern()).thenReturn(conditionParameterMatchPattern);
        //when(conditionSegment.getConditionParameterMatchPattern().getIntValue()).thenReturn(1);//case2

        //when(conditionSegment.getConditionParameterTargetValue()).thenReturn(conditionParameterTargetValue);
        //when(conditionSegment.getConditionParameterTargetValue().getIntValue()).thenReturn(null);
       // when(conditionSegment.getConditionParameterTargetValue().getStringValue()).thenReturn(null);

        when(operation.getAction()).thenReturn(actionList);
        when(action.getActionName()).thenReturn(actionName);
        when(action.getActionName().getValue()).thenReturn(new String("actionname"));
        when(action.getParameterValues()).thenReturn(parameterValues);
        when(action.getParameterValues().getIntValue()).thenReturn(null);
        when(action.getParameterValues().getStringValue()).thenReturn(null);
        when(action.getParameterValues().getRangeValue()).thenReturn(null);

        Assert.assertEquals(query.query(userId, item, target), "Results:Id:id Name:name Priority:123 Target:name3 Condition: Action: actionname:)");
        //else
        //  Assert.assertEquals(query.query(userId, item, target), "The item is not supported.");





        //org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.IntValue intValue;








    }
}