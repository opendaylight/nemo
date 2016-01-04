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
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.LinkedHashMap;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.nemo.user.tenantmanager.TenantManage;
import org.opendaylight.nemo.user.vnspacemanager.languagestyle.NEMOConstants;
import org.opendaylight.nemo.user.vnspacemanager.structurestyle.updateintent.UpdateConnection;
import org.opendaylight.nemo.user.vnspacemanager.structurestyle.updateintent.UpdateFlow;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Connection;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Flow;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.FlowBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.FlowKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.User;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.flow.instance.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.match.item.instance.MatchItemValueBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.match.item.instance.match.item.value.RangeValueBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.PropertyValuesBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.property.values.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.FlowName;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.FlowId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.Objects;

/**
 * Created by Thomas Liu on 2015/12/15.
 */
public class UpdateFlowLangTest extends TestCase {
    private UpdateFlowLang updateFlowLangTest;
    private DataBroker dataBroker;
    private TenantManage tenantManage;
    private UserId userId;
    private String flowname;
    private LinkedHashMap<String, LinkedHashMap<String,String>> propertyList;
    private LinkedHashMap<String,LinkedHashMap<String,String>> flowmatch;

    private User user;
    private Objects object;
    private List<Flow> flows;
    private LinkedHashMap<String,String> matches,properties;
    private Class class1;
    private Method method;
    private Field field;


    @org.junit.Before
    public void setUp() throws Exception {
        dataBroker = mock(DataBroker.class);
        tenantManage = mock(TenantManage.class);
        updateFlowLangTest = new UpdateFlowLang(dataBroker,tenantManage);
        flowname = new String("flow1");
        class1 = UpdateFlowLang.class;

        user = mock(User.class);
        object = mock(Objects.class);
        flows = new ArrayList<Flow>(){{add(mock(Flow.class));}};
        flowmatch = new LinkedHashMap<String,LinkedHashMap<String,String>>();
        propertyList = new LinkedHashMap<String,LinkedHashMap<String,String>>();

    }

    @org.junit.Test
    public void testFlowHandling() throws Exception {

        field = class1.getDeclaredField("updateFlow");
        UpdateFlow updateFlow = mock(UpdateFlow.class);
        field.setAccessible(true);
        field.set(updateFlowLangTest,updateFlow);
        field = class1.getDeclaredField("flow");
        Flow flow2 = mock(Flow.class);
        field.setAccessible(true);
        field.set(updateFlowLangTest,flow2);
        matches = new LinkedHashMap<String,String>(){{
            put(new String("group"),NEMOConstants.string);
            put(new String("100"),NEMOConstants.integer);
            put(new String("100,200"),NEMOConstants.range);
            put(new String("200,100"),NEMOConstants.range);
        }};
        flowmatch.put(new String("s1"),matches);

        properties = new LinkedHashMap<String,String>(){{
            //put(new String("1"),NEMOConstants.range);
            //put(new String("group"),NEMOConstants.string);
            //put(new String("100"),NEMOConstants.integer);
            put(new String("100,200"),NEMOConstants.range);
        }};
        propertyList.put(new String("s1"),properties);
        //createflow
        when(tenantManage.getObjectId(userId,flowname)).thenReturn("11111111-1111-1111-1111-111111111111");
        HashMap<FlowId,Flow> flowMap = new HashMap<FlowId,Flow>();
        FlowId flowId = new FlowId("11111111-1111-1111-1111-111111111111");
        Flow flow = mock(Flow.class);
        flowMap.put(flowId,flow);
        when(tenantManage.getObjectId(userId,flowname)).thenReturn("11111111-1111-1111-1111-111111111111");
        when(tenantManage.getFlow(userId)).thenReturn(flowMap);
        when(tenantManage.getFlow(userId)).thenReturn(flowMap);
        when(updateFlow.FlowHandling(userId,flow2)).thenReturn("FlowHandling");
        System.out.println("&&&&&&&&&&&&&&&&&&&&&& Result1: " + updateFlowLangTest.FlowHandling(userId, flowname, flowmatch, propertyList));


        properties = new LinkedHashMap<String,String>(){{
            put(new String("100,200"),NEMOConstants.range);
            put(new String("1"),NEMOConstants.range);
        }};
        propertyList.put(new String("s1"),properties);
        System.out.println("&&&&&&&&&&&&&&&&&&&&&& Result2: " + updateFlowLangTest.FlowHandling(userId, flowname, flowmatch, propertyList));

    }

    @org.junit.Test
    public void testcheckProperty() throws Exception {
        System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&" );
        System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&" );
        System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&" );
        method = class1.getDeclaredMethod("checkProperty",new Class[]{
                LinkedHashMap.class,
        });
        method.setAccessible(true);

        properties = new LinkedHashMap<String,String>(){{
            put(new String("100"),NEMOConstants.integer);
            put(new String("group"),NEMOConstants.string);
            put(new String("100,200"),NEMOConstants.range);
        }};
        propertyList.put(new String("s1"),properties);
        System.out.println("&&&&&&&&&&&&&&&&&&&&&& Result3: " + method.invoke(updateFlowLangTest, propertyList));

        properties = new LinkedHashMap<String,String>(){{
            put(new String("group"),NEMOConstants.string);
            put(new String("100"),NEMOConstants.integer);
            put(new String("100,200"),NEMOConstants.range);
        }};
        propertyList.put(new String("s1"),properties);
        System.out.println("&&&&&&&&&&&&&&&&&&&&&& Result4: " +  method.invoke(updateFlowLangTest, propertyList));
    }


    @org.junit.Test
    public void testcreateFlow() throws Exception{
        method = class1.getDeclaredMethod("createFlow",new Class[]{
                UserId.class,
                String.class,
                LinkedHashMap.class,
                LinkedHashMap.class,
        });
        method.setAccessible(true);

        properties = new LinkedHashMap<String,String>(){{
            put(new String("100"),NEMOConstants.string);
        }};
        propertyList.put(new String("p1"),properties);
        matches = new LinkedHashMap<String,String>();
        flowmatch.put(new String("s1"),matches);
        when(tenantManage.getObjectId(userId,flowname)).thenReturn(null);
        System.out.println("&&&&&&&&&&&&&&&&&&&&&& Result5: " +  method.invoke(updateFlowLangTest, userId,flowname,flowmatch,propertyList));


        properties = new LinkedHashMap<String,String>(){{
            put(new String("200,100"),NEMOConstants.range);
        }};
        propertyList.put(new String("p1"),properties);
        when(tenantManage.getObjectId(userId,flowname)).thenReturn(null);
        System.out.println("&&&&&&&&&&&&&&&&&&&&&& Result6: " +  method.invoke(updateFlowLangTest, userId,flowname,flowmatch,propertyList));

        properties = new LinkedHashMap<String,String>(){{
            put(new String("1"),NEMOConstants.integer);
        }};
        propertyList.put(new String("p1"),properties);
        when(tenantManage.getObjectId(userId,flowname)).thenReturn(null);
        System.out.println("&&&&&&&&&&&&&&&&&&&&&& Result7: " +  method.invoke(updateFlowLangTest, userId,flowname,flowmatch,propertyList));

        properties = new LinkedHashMap<String,String>(){{
            put(new String("1"),NEMOConstants.integer);
        }};
        propertyList.put(new String("p1"),properties);
        HashMap<FlowId,Flow> flowMap = new HashMap<FlowId,Flow>();
        FlowId flowId = new FlowId("11111111-1111-1111-1111-111111111111");
        Flow flow = mock(Flow.class);
        when(tenantManage.getObjectId(userId,flowname)).thenReturn("11111111-1111-1111-1111-111111111111");
        when(tenantManage.getObjectId(userId,flowname)).thenReturn("11111111-1111-1111-1111-111111111111");
        when(tenantManage.getFlow(userId)).thenReturn(flowMap);
        flowMap.put(flowId,flow);
        when(tenantManage.getFlowDataStore(userId)).thenReturn(flowMap);
        when(tenantManage.getFlowDataStore(userId)).thenReturn(flowMap);
        System.out.println("&&&&&&&&&&&&&&&&&&&&&& Result8: " +  method.invoke(updateFlowLangTest, userId,flowname,flowmatch,propertyList));

    }
}