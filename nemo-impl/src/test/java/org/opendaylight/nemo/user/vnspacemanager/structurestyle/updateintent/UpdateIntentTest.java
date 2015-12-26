/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.user.vnspacemanager.structurestyle.updateintent;

import org.junit.Before;
import org.junit.Test;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.nemo.user.tenantmanager.AAA;
import org.opendaylight.nemo.user.tenantmanager.TenantManage;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.Objects;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Node;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.StructureStyleNemoUpdateInput;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.Results;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Connection;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Flow;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Node;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.operations.Operation;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;

import java.util.List;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.LinkedList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.*;
import java.util.List;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.template.definitions.TemplateDefinition;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.template.instances.TemplateInstance;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.TemplateInstances;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.TemplateDefinitions;

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
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.Operations;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.Results;
import static org.junit.Assert.*;

/**
 * Created by ldzd11 on 2015/12/22.
 */
public class UpdateIntentTest {
    private UpdateIntent updateIntent;
    private DataBroker dataBroker;
    private TenantManage tenantManage;
    private AAA aaa;
    private UserId userId;
    private Objects objects;
    private Node node;
    private List<Node> nodeList;
    private Connection connection;
    private List<Connection> connectionList;
    private Flow flow;
    private List<Flow> flowList;
    private Operations operations;
    private Operation operation;
    private List<Operation> operationList;
    private Results results;
    private StructureStyleNemoUpdateInput structureStyleNemoUpdateInput;
    private TemplateDefinition templateDefinition;
    private List<TemplateDefinition> templateDefinitionList;
    private TemplateInstance templateInstance;
    private List<TemplateInstance> templateInstanceList;
    private TemplateDefinitions templateDefinitions;
    private TemplateInstances templateInstances;


    @Before
    public void setUp() throws Exception {
        dataBroker = mock(DataBroker.class);
        tenantManage = mock(TenantManage.class);
        aaa = mock(AAA.class);
        structureStyleNemoUpdateInput = mock(StructureStyleNemoUpdateInput.class);
        userId = mock(UserId.class);
        objects = mock(Objects.class);
        node = mock(Node.class);
        nodeList = new ArrayList<Node>(1);
        nodeList.add(node);
        operations = mock(Operations.class);
        operation = mock(Operation.class);
        operationList = new ArrayList<Operation>(1);
        operationList.add(operation);

        connection = mock(Connection.class);
        connectionList = new ArrayList<Connection>(1);
        connectionList.add(connection);
        results = mock(Results.class);

        flow = mock(Flow.class);
        flowList = new ArrayList<Flow>(1);
        flowList.add(flow);

        templateDefinition = mock(TemplateDefinition.class);
        templateDefinitionList = new ArrayList<TemplateDefinition>();
        templateDefinitionList.add(templateDefinition);
        templateDefinitions = mock(TemplateDefinitions.class);

        templateInstance = mock(TemplateInstance.class);
        templateInstanceList = new ArrayList<TemplateInstance>();
        templateInstanceList.add(templateInstance);
        templateInstances = mock(TemplateInstances.class);



        updateIntent = new UpdateIntent(dataBroker,tenantManage);

    }

    @Test
    public void testUpdateIntent() throws Exception {
        when(structureStyleNemoUpdateInput.getUserId()).thenReturn(userId);
        when(aaa.checkUser(structureStyleNemoUpdateInput.getUserId())).thenReturn(null);
        //else
        when(structureStyleNemoUpdateInput.getObjects()).thenReturn(objects);
        //node
        when(structureStyleNemoUpdateInput.getObjects().getNode()).thenReturn(nodeList);
        ///////test//////
        Class<UpdateIntent> class_1 = UpdateIntent.class;
        Field field_1 = class_1.getDeclaredField("updateNode");
        field_1.setAccessible(true);

        UpdateNode updateNode_1 = mock(UpdateNode.class);
        field_1.set(updateIntent, updateNode_1);
        when(updateNode_1.NodeHandling(userId,node)).thenReturn(new String("node success"));
        Assert.assertEquals(updateIntent.updateIntent(aaa, structureStyleNemoUpdateInput), "node success");

        //connection
        when(structureStyleNemoUpdateInput.getObjects().getNode()).thenReturn(null);
        when(structureStyleNemoUpdateInput.getObjects().getConnection()).thenReturn(connectionList);

        Field field_2 = class_1.getDeclaredField("updateConnection");
        field_2.setAccessible(true);
        UpdateConnection updateConnection = mock(UpdateConnection.class);
        field_2.set(updateIntent, updateConnection);

        when(updateConnection.ConnectionHandling(userId,connection)).thenReturn(new String("connection success"));
        Assert.assertEquals(updateIntent.updateIntent(aaa, structureStyleNemoUpdateInput), "connection success");

        //flow
        when(structureStyleNemoUpdateInput.getObjects().getNode()).thenReturn(null);
        when(structureStyleNemoUpdateInput.getObjects().getConnection()).thenReturn(null);
        when(structureStyleNemoUpdateInput.getObjects().getFlow()).thenReturn(flowList);

        Field field_3 = class_1.getDeclaredField("updateFlow");
        field_3.setAccessible(true);
        UpdateFlow updateFlow = mock(UpdateFlow.class);
        field_3.set(updateIntent, updateFlow);
        when(updateFlow.FlowHandling(userId,flow)).thenReturn(new String("flow success"));
        Assert.assertEquals(updateIntent.updateIntent(aaa, structureStyleNemoUpdateInput), "flow success");

        //operations
        when(structureStyleNemoUpdateInput.getObjects()).thenReturn(null);
        when(structureStyleNemoUpdateInput.getOperations()).thenReturn(operations);
        when(structureStyleNemoUpdateInput.getOperations().getOperation()).thenReturn(operationList);


        Field field_4 = class_1.getDeclaredField("updateOperation");
        field_4.setAccessible(true);
        UpdateOperation updateOperation = mock(UpdateOperation.class);
        field_4.set(updateIntent, updateOperation);
        when(updateOperation.OperationHandling(userId,operation)).thenReturn(new String("operation success"));
        Assert.assertEquals(updateIntent.updateIntent(aaa, structureStyleNemoUpdateInput), "operation success");


        //getResults
        when(structureStyleNemoUpdateInput.getObjects()).thenReturn(null);
        when(structureStyleNemoUpdateInput.getOperations()).thenReturn(null);
        when(structureStyleNemoUpdateInput.getResults()).thenReturn(results);

        Field field_5 = class_1.getDeclaredField("updateResult");
        field_5.setAccessible(true);
        UpdateResult updateResult = mock(UpdateResult.class);
        field_5.set(updateIntent, updateResult);
        when(updateResult.ResultHandling(userId, results)).thenReturn(new String("result success"));
        Assert.assertEquals(updateIntent.updateIntent(aaa, structureStyleNemoUpdateInput), "result success");

        //getTemplateDefinitions
        when(structureStyleNemoUpdateInput.getObjects()).thenReturn(null);
        when(structureStyleNemoUpdateInput.getOperations()).thenReturn(null);
        when(structureStyleNemoUpdateInput.getResults()).thenReturn(null);
        when(structureStyleNemoUpdateInput.getTemplateDefinitions()).thenReturn(templateDefinitions);
        when(structureStyleNemoUpdateInput.getTemplateDefinitions().getTemplateDefinition()).thenReturn(templateDefinitionList);

        Field field_6 = class_1.getDeclaredField("updateTemplateDefinition");
        field_6.setAccessible(true);
        UpdateTemplateDefinition updateTemplateDefinition = mock(UpdateTemplateDefinition.class);
        field_6.set(updateIntent, updateTemplateDefinition);
        when(updateTemplateDefinition.checkTemplateDefinition(userId, templateDefinition)).thenReturn(new String("templateDefinition success"));
        Assert.assertEquals(updateIntent.updateIntent(aaa, structureStyleNemoUpdateInput), "templateDefinition success");


        //getTemplateInstances
        when(structureStyleNemoUpdateInput.getObjects()).thenReturn(null);
        when(structureStyleNemoUpdateInput.getOperations()).thenReturn(null);
        when(structureStyleNemoUpdateInput.getResults()).thenReturn(null);
        when(structureStyleNemoUpdateInput.getTemplateDefinitions()).thenReturn(null);
        when(structureStyleNemoUpdateInput.getTemplateInstances()).thenReturn(templateInstances);
        when(structureStyleNemoUpdateInput.getTemplateInstances().getTemplateInstance()).thenReturn(templateInstanceList);

        Field field_7 = class_1.getDeclaredField("updateTemplateInstance");
        field_7.setAccessible(true);
        UpdateTemplateInstance updateTemplateInstance = mock(UpdateTemplateInstance.class);
        field_7.set(updateIntent, updateTemplateInstance);
        when(updateTemplateInstance.checkTemplateInstance(userId, templateInstance)).thenReturn(new String("templateInstance success"));
        Assert.assertEquals(updateIntent.updateIntent(aaa, structureStyleNemoUpdateInput),"templateInstance success");








    }
}