/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.user.vnspacemanager.structurestyle.updateintent;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import com.google.common.base.Optional;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.ConnectionDefinitions;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.MatchItemDefinitions;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.NodeDefinitions;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.connection.definitions.ConnectionDefinition;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.match.item.definitions.MatchItemDefinition;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.node.definitions.NodeDefinition;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.ActionDefinitions;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.ConditionParameterDefinitions;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.definitions.ActionDefinition;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.condition.parameter.definitions.ConditionParameterDefinition;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.google.common.util.concurrent.CheckedFuture;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.opendaylight.controller.md.sal.binding.api.ReadOnlyTransaction;


import static org.junit.Assert.*;
import static org.junit.Assert.*;

/**
 * Created by ldzd11 on 2015/12/22.
 */
public class GetDefinitionsTest {

    private GetDefinitions getDefinitions;
    private DataBroker dataBroker;
    private NodeDefinition nodeDefinition;
    private List<NodeDefinition> nodeDefinitionList;
    private Map<NodeType,NodeDefinition> map;
    private NodeType nodeType;
    @Before
    public void setUp() throws Exception {
        dataBroker = mock(DataBroker.class);
        nodeDefinition = mock(NodeDefinition.class);
        nodeDefinitionList = new ArrayList<NodeDefinition>(1);
        nodeDefinitionList.add(nodeDefinition);
        nodeType = mock(NodeType.class);
        map = new HashMap<NodeType, NodeDefinition>();
        map.put(nodeType, nodeDefinition);

        getDefinitions = new GetDefinitions(dataBroker);
    }

    @Test
    public void testGetNodeDefinition() throws Exception {
        //fetchNodeDefinitions

        Class<GetDefinitions> class_1 = GetDefinitions.class;
        Field field = class_1.getDeclaredField("nodeDefinitionList");
        field.setAccessible(true);

        field.set(getDefinitions,nodeDefinitionList);

        CheckedFuture nodedefinitionFuture = mock(CheckedFuture.class);
        ReadOnlyTransaction readOnlyTransaction = mock(ReadOnlyTransaction.class);
        when(dataBroker.newReadOnlyTransaction()).thenReturn(readOnlyTransaction);
        when(readOnlyTransaction.read(any(LogicalDatastoreType.class), any(InstanceIdentifier.class))).thenReturn(nodedefinitionFuture);

        when(nodeDefinition.getNodeType()).thenReturn(nodeType);
        Assert.assertEquals(getDefinitions.getNodeDefinition(),map);

    }

    @Test
    public void testGetMatchItemDefinition() throws Exception {
        Map<MatchItemName, MatchItemDefinition> map1 = new HashMap<MatchItemName, MatchItemDefinition>();
        MatchItemName matchItemName = mock(MatchItemName.class);
        MatchItemDefinition matchItemDefinition = mock(MatchItemDefinition.class);
        map1.put(matchItemName, matchItemDefinition);

        List<MatchItemDefinition> matchItemDefinitionList = new ArrayList<MatchItemDefinition>();
        matchItemDefinitionList.add(matchItemDefinition);

        Class<GetDefinitions> class_1 = GetDefinitions.class;
        Field field = class_1.getDeclaredField("matchItemDefinitionList");
        field.setAccessible(true);

        field.set(getDefinitions, matchItemDefinitionList);


        CheckedFuture matchitemdefinitionFuture = mock(CheckedFuture.class);
        ReadOnlyTransaction readOnlyTransaction = mock(ReadOnlyTransaction.class);
        when(dataBroker.newReadOnlyTransaction()).thenReturn(readOnlyTransaction);
        when(readOnlyTransaction.read(any(LogicalDatastoreType.class), any(InstanceIdentifier.class))).thenReturn(matchitemdefinitionFuture);
        when(matchItemDefinition.getMatchItemName()).thenReturn(matchItemName);
        Assert.assertEquals(getDefinitions.getMatchItemDefinition(), map1);




    }

    @Test
    public void testGetConnectionDefinition() throws Exception {

        Map<ConnectionType, ConnectionDefinition> map1 = new HashMap<ConnectionType, ConnectionDefinition>();
        ConnectionType connectionType = mock(ConnectionType.class);
        ConnectionDefinition connectionDefinition = mock(ConnectionDefinition.class);
        map1.put(connectionType,connectionDefinition);
        List<ConnectionDefinition> connectionDefinitionList = new ArrayList<ConnectionDefinition>();
        connectionDefinitionList.add(connectionDefinition);

        Class<GetDefinitions> class_1 = GetDefinitions.class;
        Field field = class_1.getDeclaredField("connectionDefinitionsList");
        field.setAccessible(true);

        field.set(getDefinitions, connectionDefinitionList);

        CheckedFuture connectiondefinitionFuture = mock(CheckedFuture.class);
        ReadOnlyTransaction readOnlyTransaction = mock(ReadOnlyTransaction.class);
        when(dataBroker.newReadOnlyTransaction()).thenReturn(readOnlyTransaction);
        when(readOnlyTransaction.read(any(LogicalDatastoreType.class), any(InstanceIdentifier.class))).thenReturn(connectiondefinitionFuture);
        when(connectionDefinition.getConnectionType()).thenReturn(connectionType);
        Assert.assertEquals(getDefinitions.getConnectionDefinition(), map1);


    }

    @Test
    public void testGetActionDefinition() throws Exception {
        Map<ActionName,ActionDefinition> map1 = new HashMap<ActionName, ActionDefinition>();
        ActionName actionName = mock(ActionName.class);
        ActionDefinition actionDefinition = mock(ActionDefinition.class);
        map1.put(actionName,actionDefinition);
        List<ActionDefinition> actionDefinitionList = new ArrayList<ActionDefinition>();
        actionDefinitionList.add(actionDefinition);

        Class<GetDefinitions> class_1 = GetDefinitions.class;
        Field field = class_1.getDeclaredField("actionDefinitionList");
        field.setAccessible(true);

        field.set(getDefinitions, actionDefinitionList);


        CheckedFuture actiondefinitionFuture = mock(CheckedFuture.class);
        ReadOnlyTransaction readOnlyTransaction = mock(ReadOnlyTransaction.class);
        when(dataBroker.newReadOnlyTransaction()).thenReturn(readOnlyTransaction);
        when(readOnlyTransaction.read(any(LogicalDatastoreType.class), any(InstanceIdentifier.class))).thenReturn(actiondefinitionFuture);

        when(actionDefinition.getActionName()).thenReturn(actionName);
        Assert.assertEquals(getDefinitions.getActionDefinition(), map1);


    }

    @Test
    public void testGetConditionParameterDefinition() throws Exception {
        Map<ParameterName, ConditionParameterDefinition> map1 = new HashMap<ParameterName, ConditionParameterDefinition>();
        ParameterName parameterName = mock(ParameterName.class);
        ConditionParameterDefinition conditionParameterDefinition = mock(ConditionParameterDefinition.class);
        map1.put(parameterName,conditionParameterDefinition);

        List<ConditionParameterDefinition> conditionParameterDefinitionList = new ArrayList<ConditionParameterDefinition>();
        conditionParameterDefinitionList.add(conditionParameterDefinition);

        Class<GetDefinitions> class_1 = GetDefinitions.class;
        Field field = class_1.getDeclaredField("conditionParameterDefinitionList");
        field.setAccessible(true);

        field.set(getDefinitions, conditionParameterDefinitionList);


        CheckedFuture conditionparadefinitionFuture = mock(CheckedFuture.class);
        ReadOnlyTransaction readOnlyTransaction = mock(ReadOnlyTransaction.class);
        when(dataBroker.newReadOnlyTransaction()).thenReturn(readOnlyTransaction);
        when(readOnlyTransaction.read(any(LogicalDatastoreType.class), any(InstanceIdentifier.class))).thenReturn(conditionparadefinitionFuture);
        when(conditionParameterDefinition.getParameterName()).thenReturn(parameterName);

        Assert.assertEquals(getDefinitions.getConditionParameterDefinition(), map1);


    }
}