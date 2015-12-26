/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.user.vnspacemanager.structurestyle.updateintent;



import static org.mockito.Mockito.doNothing;

import org.junit.Before;
import org.junit.Test;
import org.opendaylight.nemo.user.tenantmanager.TenantManage;

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
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.MatchItemName;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.Users;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.Objects;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Flow;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.FlowBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.FlowKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.User;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.UserKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.MatchItemDefinitions;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.flow.instance.MatchItem;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.match.item.definitions.MatchItemDefinition;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.match.item.instance.MatchItemValue;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.FlowId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.FlowName;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

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

/**
 * Created by ldzd11 on 2015/12/22.
 */
public class UpdateFlowTest {

    private  UpdateFlow updateFlow;
    private DataBroker dataBroker;
    private TenantManage tenantManage;
    private MatchItemName matchItemName;
    private MatchItemName matchItemName2;
    private MatchItem matchItem;
    private List<MatchItem> matchItemList;
    private List<MatchItem> matchItemListnull;
    private User user;
    private UserId userId;
    private Objects objects;
    private List<Flow> flowList;
    private FlowId flowId;
    private FlowName flowName;
    private FlowName flowName2;



    private MatchItemValue matchItemValue;
    private Flow flow;
    private Flow flow2;

    @Before
    public void setUp() throws Exception {
        dataBroker = mock(DataBroker.class);
        tenantManage = mock(TenantManage.class);
        matchItemName = mock(MatchItemName.class);
        matchItemName2= mock(MatchItemName.class);
        matchItem = mock(MatchItem.class);
        matchItemList = new ArrayList<MatchItem>(1);
        matchItemListnull = new ArrayList<MatchItem>();
        userId = mock(UserId.class);
        user = mock(User.class);
        matchItemList.add(matchItem);
        flow = mock(Flow.class);
        flow2 = mock(Flow.class);
        flowId = mock(FlowId.class);
        flowName = mock(FlowName.class);
        flowName2 = mock(FlowName.class);

        matchItemValue = mock(MatchItemValue.class);
        objects = mock(Objects.class);
        flowList = new ArrayList<Flow>(1);
        flowList.add(flow2);

        updateFlow = new UpdateFlow(dataBroker,tenantManage);

    }




    @Test
    public void testFlowHandling() throws Exception {

        //into checkdefinition  for error = null
        CheckedFuture matchitemdefinitionFuture = mock(CheckedFuture.class);
        ReadOnlyTransaction readOnlyTransaction = mock(ReadOnlyTransaction.class);
        when(dataBroker.newReadOnlyTransaction()).thenReturn(readOnlyTransaction);
        when(readOnlyTransaction.read(any(LogicalDatastoreType.class), any(InstanceIdentifier.class))).thenReturn(matchitemdefinitionFuture);

        List<MatchItemDefinition> matchItemDefinitions = new ArrayList<MatchItemDefinition>();
        MatchItemDefinition matchItemDefinition = mock(MatchItemDefinition.class);
        matchItemDefinitions.add(matchItemDefinition);
        when(matchItemDefinition.getMatchItemName()).thenReturn(matchItemName);

        Class<UpdateFlow> class1 = UpdateFlow.class;
        Class<GetDefinitions> class2 = GetDefinitions.class;
        Field field = class1.getDeclaredField("getDefinitions");
        field.setAccessible(true);
        Field field1 = class2.getDeclaredField("matchItemDefinitionList");
        field1.setAccessible(true);

        field1.set(field.get(updateFlow), matchItemDefinitions);
        when(flow.getMatchItem()).thenReturn(null);
        //into checkinstance for error = null
        when(tenantManage.getFlow(userId)).thenReturn(null);
        when(tenantManage.getFlowDataStore(userId)).thenReturn(null);
        doNothing().when(tenantManage).setFlow(any(UserId.class),any(FlowId.class),any(Flow.class));


        Assert.assertEquals(updateFlow.FlowHandling(userId, flow), null);




    }

    @Test
    public void testcheckInstance() throws Exception {
        Class<UpdateFlow> class1 = UpdateFlow.class;
        Method method = class1.getDeclaredMethod("checkInstance",new Class[]{UserId.class, Flow.class});
        method.setAccessible(true);
        Map<FlowId,Flow> map1 = new HashMap<FlowId, Flow>();
        map1.put(flowId,flow2);
        when(tenantManage.getFlow(userId)).thenReturn(map1);
        when(flow.getFlowId()).thenReturn(flowId);
        when(flow2.getFlowName()).thenReturn(flowName2);
        when(flow.getFlowName()).thenReturn(flowName);
        Assert.assertEquals(method.invoke(updateFlow, userId, flow), "The flow name should not be changed.");

        when(tenantManage.getFlow(userId)).thenReturn(null);
        when(tenantManage.getFlowDataStore(userId)).thenReturn(map1);
        when(flow.getFlowId()).thenReturn(flowId);
        when(flow2.getFlowName()).thenReturn(flowName2);
        when(flow.getFlowName()).thenReturn(flowName);
        Assert.assertEquals(method.invoke(updateFlow, userId, flow), "The flow name should not be changed.");

        when(tenantManage.getFlow(userId)).thenReturn(null);
        when(tenantManage.getFlowDataStore(userId)).thenReturn(null);
        Assert.assertEquals(method.invoke(updateFlow, userId, flow), null);
    }

    @Test
    public void testcheckDefinition() throws Exception {
        Class<UpdateFlow> class3 = UpdateFlow.class;
        Method method = class3.getDeclaredMethod("checkDefinition",new Class[]{Flow.class});
        method.setAccessible(true);

        CheckedFuture matchitemdefinitionFuture = mock(CheckedFuture.class);
        ReadOnlyTransaction readOnlyTransaction = mock(ReadOnlyTransaction.class);
        when(dataBroker.newReadOnlyTransaction()).thenReturn(readOnlyTransaction);
        when(readOnlyTransaction.read(any(LogicalDatastoreType.class), any(InstanceIdentifier.class))).thenReturn(matchitemdefinitionFuture);


        List<MatchItemDefinition> matchItemDefinitions = new ArrayList<MatchItemDefinition>();
        List<MatchItemDefinition> matchItemDefinitionsnull = new ArrayList<MatchItemDefinition>();
        MatchItemDefinition matchItemDefinition = mock(MatchItemDefinition.class);
        matchItemDefinitions.add(matchItemDefinition);
        when(matchItemDefinition.getMatchItemName()).thenReturn(matchItemName);


        Class<UpdateFlow> class1 = UpdateFlow.class;
        Class<GetDefinitions> class2 = GetDefinitions.class;
        Field field = class1.getDeclaredField("getDefinitions");
        field.setAccessible(true);
        Field field1 = class2.getDeclaredField("matchItemDefinitionList");
        field1.setAccessible(true);

        //the else
        field1.set(field.get(updateFlow), matchItemDefinitions);
        when(flow.getMatchItem()).thenReturn(matchItemList);
        when(matchItem.getMatchItemName()).thenReturn(matchItemName2);
        Assert.assertEquals(method.invoke(updateFlow, flow), "The match item has not been defined.");

        //the upper
        field1.set(field.get(updateFlow), matchItemDefinitionsnull);
        Assert.assertEquals(method.invoke(updateFlow, flow), "The match item has not been defined.");

        //the if errorInfo == null into checkPredefine
        field1.set(field.get(updateFlow), matchItemDefinitions);
        when(flow.getMatchItem()).thenReturn(matchItemListnull);
        //into checkPredefine
        Assert.assertEquals(method.invoke(updateFlow, flow), null);




    }

    @Test
    public void testcheckPredefine() throws Exception {
        Class<UpdateFlow> class1 = UpdateFlow.class;
        Method method = class1.getDeclaredMethod("checkPredefine",new Class[]{List.class});
        method.setAccessible(true);

        when(matchItem.getMatchItemName()).thenReturn(matchItemName);
        when(matchItem.getMatchItemName().getValue()).thenReturn(new String("src-ip"));
        when(matchItem.getMatchItemValue()).thenReturn(matchItemValue);

        //stringValues.contains("/")
        when(matchItem.getMatchItemValue().getStringValue()).thenReturn(new String("110/"));
        //into checkIpPrefix(stringvalues)  legalValue=false
        Assert.assertEquals(method.invoke(updateFlow, matchItemList),"The " + NEMOConstants.ip_address + " is not legal.");

        when(matchItem.getMatchItemValue().getStringValue()).thenReturn(new String("110\\."));
        //into checkip address
        Assert.assertEquals(method.invoke(updateFlow, matchItemList),"The " + NEMOConstants.ip_address + " is not legal.");


        when(matchItem.getMatchItemName().getValue()).thenReturn(new String("src-mac"));
        when(matchItem.getMatchItemValue()).thenReturn(matchItemValue);
        when(matchItem.getMatchItemValue().getStringValue()).thenReturn(new String("110:"));
        //into valuecheck.checkMac
        Assert.assertEquals(method.invoke(updateFlow, matchItemList), "The " + NEMOConstants.mac_address + " is not legal.");











    }
}