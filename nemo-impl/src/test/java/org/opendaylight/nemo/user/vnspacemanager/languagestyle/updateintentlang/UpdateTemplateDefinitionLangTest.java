/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.user.vnspacemanager.languagestyle.updateintentlang;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.nemo.user.tenantmanager.TenantManage;
import org.opendaylight.nemo.user.vnspacemanager.languagestyle.NEMOConstants;
import org.opendaylight.nemo.user.vnspacemanager.structurestyle.updateintent.GetDefinitions;
import org.opendaylight.nemo.user.vnspacemanager.structurestyle.updateintent.UpdateTemplateDefinition;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.hosts.PhysicalHost;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.PhysicalHostId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.PhysicalHostName;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.template.definitions.TemplateDefinition;
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
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.NodeId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.ConnectionId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.FlowId;
import java.lang.reflect.Field;
import java.util.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
/**
 * Created by zhangmeng on 2015/12/29.
 */
public class UpdateTemplateDefinitionLangTest extends TestCase {
    private DataBroker dataBroker;
    private TenantManage tenantManage;
    private UpdateTemplateDefinitionLang updateTemplateDefinitionLang;
    private Class<UpdateTemplateDefinitionLang> class1;
    private Field field_definition;
    private Field field_abstractNodes ;
    private Field field_abstractConnections;
    private Field field_abstractFlows;
    private Field field_abstractOperations;
    private Field field;

    @Before
    public void setUp() throws Exception {
        class1 = UpdateTemplateDefinitionLang.class;
        field_definition = class1.getDeclaredField("definition");
        field_abstractNodes = class1.getDeclaredField("abstractNodes");
        field_abstractConnections = class1.getDeclaredField("abstractConnections");
        field_abstractFlows = class1.getDeclaredField("abstractFlows");
        field_abstractOperations = class1.getDeclaredField("abstractOperations");
        field = class1.getDeclaredField("getDefinitions");
        field_definition.setAccessible(true);
        field_abstractNodes.setAccessible(true);
        field_abstractConnections.setAccessible(true);
        field_abstractFlows.setAccessible(true);
        field_abstractOperations.setAccessible(true);
        field.setAccessible(true);

        dataBroker = mock(DataBroker.class);
        tenantManage = mock(TenantManage.class);

        updateTemplateDefinitionLang = new UpdateTemplateDefinitionLang(dataBroker,tenantManage);
    }

    @Test
    public void testTemplateDefinitionLang() throws Exception {

        UserId userId = mock(UserId.class);
        NodeName nodeName = mock(NodeName.class);
        AbstractNode abstractNode = mock(AbstractNode.class);
        ConnectionName connectionName = mock(ConnectionName.class);
        AbstractConnection abstractConnection = mock(AbstractConnection.class);
        FlowName flowName = mock(FlowName.class);
        AbstractFlow abstractFlow = mock(AbstractFlow.class);
        OperationName operationName = mock(OperationName.class);
        AbstractOperation abstractOperation = mock(AbstractOperation.class);
        UpdateTemplateDefinition updateTemplateDefinition = mock(UpdateTemplateDefinition.class);
        String templateName = new String("test");
        String value = new String("string");
        LinkedHashMap<String, String> parameters = new LinkedHashMap<String, String>();
        Map<NodeName, AbstractNode> abstractNodes = new HashMap<NodeName, AbstractNode>();
        Map<ConnectionName, AbstractConnection> abstractConnections = new HashMap<ConnectionName, AbstractConnection>();
        Map<FlowName, AbstractFlow> abstractFlows = new HashMap<FlowName, AbstractFlow>();
        Map<OperationName, AbstractOperation> abstractOperations = new HashMap<OperationName, AbstractOperation>();

        parameters.put(value, NEMOConstants.string);
        abstractConnections.put(connectionName,abstractConnection);
        abstractNodes.put(nodeName, abstractNode);
        abstractFlows.put(flowName,abstractFlow);
        abstractOperations.put(operationName,abstractOperation);
        field_definition.set(updateTemplateDefinitionLang, updateTemplateDefinition);

        when(updateTemplateDefinition.checkTemplateDefinition(any(UserId.class),any(TemplateDefinition.class)))
                .thenReturn("1")
                .thenReturn("2")
                .thenReturn("3");
        Assert.assertTrue(updateTemplateDefinitionLang.templateDefinitionLang(userId, templateName, parameters).equals("1"));
        parameters.clear();
        parameters.put(value, NEMOConstants.integer);
        field_abstractNodes.set(updateTemplateDefinitionLang, abstractNodes);
        field_abstractConnections.set(updateTemplateDefinitionLang, abstractConnections);
        field_abstractFlows.set(updateTemplateDefinitionLang,abstractFlows);
        field_abstractOperations.set(updateTemplateDefinitionLang,abstractOperations);
        Assert.assertTrue(updateTemplateDefinitionLang.templateDefinitionLang(userId, templateName, parameters).equals("2"));
        parameters.clear();
        parameters.put(value, NEMOConstants.range);
        Assert.assertTrue(updateTemplateDefinitionLang.templateDefinitionLang(userId, templateName, parameters).equals("3"));
        verify(updateTemplateDefinition,times(3)).checkTemplateDefinition(any(UserId.class),any(TemplateDefinition.class));
    }

    @Test
    public void testCreateAbstractNode() throws Exception {
        String name = new String("name");
        String type = NEMOConstants.host;
        List<String> subnodes = new ArrayList<String>();
        LinkedHashMap<String, LinkedHashMap<String,String>> propertyList = new LinkedHashMap<String, LinkedHashMap<String, String>>();
        GetDefinitions getDefinitions = mock(GetDefinitions.class);
        String nodeName1 = new String("nodeName");
        String propertyName = new String("propertyName");
        String ValueName = new String("valuename");
        String Value = NEMOConstants.string;
        NodeName nodeName = new NodeName(nodeName1);
        AbstractNode abstractNode = mock(AbstractNode.class);
        PhysicalHostId physicalHostId = mock(PhysicalHostId.class);
        PhysicalHost physicalHost = mock(PhysicalHost.class);
        LinkedHashMap<String, String> values = new LinkedHashMap<String, String>();
        Map<PhysicalHostName, PhysicalHost> physicalHostMap = mock(Map.class);
        Map<NodeName, AbstractNode> abstractNodes = new HashMap<NodeName, AbstractNode>();

        field.set(updateTemplateDefinitionLang, getDefinitions);
        subnodes.add(nodeName1);

        when(getDefinitions.getPhysicalHost()).thenReturn(physicalHostMap);
        when(physicalHostMap.containsKey(any(PhysicalHostName.class)))
                .thenReturn(false)
                .thenReturn(true);
        Assert.assertTrue(updateTemplateDefinitionLang.createAbstractNode(name, type, subnodes, propertyList).equals("The host " + name + " is not exist in physical network."));
        when(physicalHostMap.get(any(PhysicalHostName.class))).thenReturn(physicalHost);
        when(physicalHost.getHostId()).thenReturn(physicalHostId);
        when(physicalHostId.getValue()).thenReturn("00001111-0000-0000-0000-000011112222");
        //test nodes empty
        field_abstractNodes.set(updateTemplateDefinitionLang, abstractNodes);
        Assert.assertTrue(updateTemplateDefinitionLang.createAbstractNode(name, type, subnodes, propertyList).equals("There are no nodes."));
        //nodes not empty propertyList empty
        abstractNodes.put(nodeName, abstractNode);
        Assert.assertTrue(updateTemplateDefinitionLang.createAbstractNode(name, type, subnodes, propertyList) == null);
        //propertyList not empty
        propertyList.put(propertyName,values);
        values.put(ValueName,Value);
        Assert.assertTrue(updateTemplateDefinitionLang.createAbstractNode(name, type, subnodes, propertyList) == null);
        values.clear();
        values.put("11", NEMOConstants.integer);
        Assert.assertTrue(updateTemplateDefinitionLang.createAbstractNode(name, type, subnodes, propertyList) == null);
        values.clear();
        values.put("1,1", NEMOConstants.range);
        Assert.assertTrue(updateTemplateDefinitionLang.createAbstractNode(name, type, subnodes, propertyList) == null);
        type = NEMOConstants.SubNodes;
        Assert.assertTrue(updateTemplateDefinitionLang.createAbstractNode(name, type, subnodes, propertyList) == null);

    }

    @Test
    public void testCreateAbstractConnection() throws Exception {
        String name = new String("name");
        String type = NEMOConstants.host;
        String endNode = new String("endNode");
        String propertyName = new String("propertyName");
        String ValueName = new String("valuename");
        String Value = NEMOConstants.string;
        List<String> endnodes = new ArrayList<String>();
        NodeName nodeName = mock(NodeName.class);
        NodeId nodeId = mock(NodeId.class);
        AbstractNode abstractNode = mock(AbstractNode.class);
        LinkedHashMap<String, String> values = new LinkedHashMap<String, String>();
        LinkedHashMap<String, LinkedHashMap<String,String>> propertyList = new LinkedHashMap<String, LinkedHashMap<String, String>>();
        Map<NodeName, AbstractNode> abstractNodes = new HashMap<NodeName, AbstractNode>();

        abstractNodes.put(nodeName, abstractNode);
        endnodes.add(endNode);

        //test abstractNodes empty
        Assert.assertTrue(updateTemplateDefinitionLang.createAbstractConnection(name, type, endnodes, propertyList).equals("There are nodes exist."));
        //abstractNodes not empty
        field_abstractNodes.set(updateTemplateDefinitionLang, abstractNodes);
        Assert.assertTrue(updateTemplateDefinitionLang.createAbstractConnection(name, type, endnodes, propertyList).equals("The endnode " + endNode + " is not exit."));
        abstractNodes.clear();
        abstractNodes.put(new NodeName(endNode), abstractNode);
        when(abstractNode.getNodeId()).thenReturn(nodeId);
        Assert.assertTrue(updateTemplateDefinitionLang.createAbstractConnection(name, type, endnodes, propertyList) == null);
        //test propertyList not null
        propertyList.put(propertyName, values);
        // string
        values.put(ValueName,Value);
        Assert.assertTrue(updateTemplateDefinitionLang.createAbstractConnection(name, type, endnodes, propertyList) == null);
        values.clear();
        values.put("11", NEMOConstants.integer);
        Assert.assertTrue(updateTemplateDefinitionLang.createAbstractConnection(name, type, endnodes, propertyList) == null);
        values.clear();
        values.put("1,1", NEMOConstants.range);
        Assert.assertTrue(updateTemplateDefinitionLang.createAbstractConnection(name, type, endnodes, propertyList) == null);

    }

    @Test
    public void testCreateAbstractFlow() throws Exception {
        String name = new String("name");
        String value = new String("value");
        String Value = NEMOConstants.string;
        String matchName = new String("matchName");
        LinkedHashMap<String,String> values = new LinkedHashMap<String, String>();
        LinkedHashMap<String,LinkedHashMap<String,String>> matches =  new LinkedHashMap<String, LinkedHashMap<String, String>>();
        LinkedHashMap<String,LinkedHashMap<String,String>> propertyList = new LinkedHashMap<String, LinkedHashMap<String, String>>();

        matches.put(matchName,values);

        //string
        values.put(value,Value);
        Assert.assertTrue(updateTemplateDefinitionLang.createAbstractFlow(name, matches, propertyList) == null);
        values.clear();
        values.put("11", NEMOConstants.integer);
        Assert.assertTrue(updateTemplateDefinitionLang.createAbstractFlow(name, matches, propertyList) == null);
        values.clear();
        values.put("1,1", NEMOConstants.range);
        Assert.assertTrue(updateTemplateDefinitionLang.createAbstractFlow(name, matches, propertyList) == null);

    }

    @Test
    public void testCreateAbstractOperation() throws Exception {
        String name = new String("name");
        String target = new String("target");
        String priority = new String("1");
        LinkedHashMap<String,LinkedHashMap<String,String>> conditions = new LinkedHashMap<String, LinkedHashMap<String, String>>();
        LinkedHashMap<String,LinkedHashMap<String,String>>actions = new LinkedHashMap<String, LinkedHashMap<String, String>>();

        String conditionName = new String("!,name,==");
        String value = new String("value");
        String actionName = new String("actionName");
        AbstractNode abstractNode = mock(AbstractNode.class);
        AbstractFlow abstractFlow = mock(AbstractFlow.class);
        AbstractConnection abstractConnection = mock(AbstractConnection.class);
        LinkedHashMap<String,String> parameters = new LinkedHashMap<String, String>();
        LinkedHashMap<String, String> targetValue = new LinkedHashMap<String, String>();
        Map<NodeName, AbstractNode> abstractNodes = mock(Map.class);
        Map<ConnectionName, AbstractConnection> abstractConnections = mock(Map.class);
        Map<FlowName, AbstractFlow> abstractFlows = mock(Map.class);

        field_abstractNodes.set(updateTemplateDefinitionLang, abstractNodes);
        field_abstractConnections.set(updateTemplateDefinitionLang, abstractConnections);
        field_abstractFlows.set(updateTemplateDefinitionLang, abstractFlows);

        when(abstractNodes.containsKey(any(NodeName.class))).thenReturn(true);
        when(abstractNodes.get(new NodeName(target))).thenReturn(abstractNode);
        when(abstractNode.getNodeId()).thenReturn(mock(NodeId.class));
        when(abstractConnections.containsKey(any(ConnectionName.class))).thenReturn(true);
        when(abstractConnections.get(new ConnectionName(target))).thenReturn(abstractConnection);
        when(abstractConnection.getConnectionId()).thenReturn(mock(ConnectionId.class));
        when(abstractFlows.containsKey(any(FlowName.class))).thenReturn(true);
        when(abstractFlows.get(new FlowName(target))).thenReturn(abstractFlow);
        when(abstractFlow.getFlowId()).thenReturn(mock(FlowId.class));
        Assert.assertTrue(updateTemplateDefinitionLang.createAbstractOperation(name, target, priority, conditions, actions) == null);
        //test conditions not empty
        targetValue.put(value,NEMOConstants.string); //string
        conditions.put(conditionName, targetValue);// not equal
//        System.out.println(",1,2".split(NEMOConstants.comma)[1] == null);
//        System.out.println("split" + conditionName.split(NEMOConstants.comma)[0] + "+" + conditionName.split(NEMOConstants.comma)[2]);
        Assert.assertTrue(updateTemplateDefinitionLang.createAbstractOperation(name, target, priority, conditions, actions) == null);
        targetValue.clear();
        conditions.clear();
        targetValue.put("11", NEMOConstants.integer); // int
        conditions.put("&&,name,!=", targetValue);// and notEqual
        Assert.assertTrue(updateTemplateDefinitionLang.createAbstractOperation(name, target, priority, conditions, actions) == null);
        targetValue.clear();
        conditions.clear();
        targetValue.put("1,1", NEMOConstants.range); // range
        conditions.put("||,name,>", targetValue);// and greater_than
        Assert.assertTrue(updateTemplateDefinitionLang.createAbstractOperation(name, target, priority, conditions, actions) == null);
        targetValue.clear();
        conditions.clear();
        targetValue.put("1,1", NEMOConstants.range); // range
        conditions.put(",name,<", targetValue);// null less_than
        Assert.assertTrue(updateTemplateDefinitionLang.createAbstractOperation(name, target, priority, conditions, actions) == null);
        targetValue.clear();
        conditions.clear();
        targetValue.put("1,1", NEMOConstants.range); // range
        conditions.put(",name,>=", targetValue);// null not_less_than
        Assert.assertTrue(updateTemplateDefinitionLang.createAbstractOperation(name, target, priority, conditions, actions) == null);
        targetValue.clear();
        conditions.clear();
        targetValue.put("1,1", NEMOConstants.range); // range
        conditions.put(",name,<=", targetValue);// null not_greater_than
        Assert.assertTrue(updateTemplateDefinitionLang.createAbstractOperation(name, target, priority, conditions, actions) == null);
        targetValue.clear();
        conditions.clear();
        targetValue.put("1,1", NEMOConstants.range); // range
        conditions.put(",name,*", targetValue);// null between
        Assert.assertTrue(updateTemplateDefinitionLang.createAbstractOperation(name, target, priority, conditions, actions) == null);
        targetValue.clear();
        conditions.clear();
        targetValue.put("1,1", NEMOConstants.range); // range
        conditions.put(",name,==", targetValue);// null equal
        Assert.assertTrue(updateTemplateDefinitionLang.createAbstractOperation(name, target, priority, conditions, actions) == null);

        //test actions not empty
        parameters.put("string", NEMOConstants.string);
        actions.put(actionName, parameters);
        Assert.assertTrue(updateTemplateDefinitionLang.createAbstractOperation(name, target, priority, conditions, actions) == null);
        actions.clear();
        parameters.clear();
        parameters.put("11", NEMOConstants.integer);
        actions.put(actionName, parameters);
        Assert.assertTrue(updateTemplateDefinitionLang.createAbstractOperation(name, target, priority, conditions, actions) == null);
        actions.clear();
        parameters.clear();
        parameters.put("1,1", NEMOConstants.range);
        actions.put(actionName,parameters);
        Assert.assertTrue(updateTemplateDefinitionLang.createAbstractOperation(name, target, priority, conditions, actions).equals("The action values are not consistent."));


    }
}