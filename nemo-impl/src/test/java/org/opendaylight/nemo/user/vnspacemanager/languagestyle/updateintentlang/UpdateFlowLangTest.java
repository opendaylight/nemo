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
import java.lang.reflect.Method; 
import java.util.*;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.Objects;
import org.opendaylight.nemo.user.tenantmanager.TenantManage;
import org.opendaylight.nemo.user.vnspacemanager.languagestyle.NEMOConstants;
import org.opendaylight.nemo.user.vnspacemanager.structurestyle.updateintent.UpdateFlow;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.*;
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
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Flow;
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


    @org.junit.Before
    public void setUp() throws Exception {
        dataBroker = mock(DataBroker.class);
        tenantManage = mock(TenantManage.class);
        updateFlowLangTest = new UpdateFlowLang(dataBroker,tenantManage);
        flowname = new String("flow1");

        user = mock(User.class);
        object = mock(Objects.class);
        flows = new ArrayList<Flow>(){{add(mock(Flow.class));}};
        flowmatch = new LinkedHashMap<String,LinkedHashMap<String,String>>();
        propertyList = new LinkedHashMap<String,LinkedHashMap<String,String>>();

    }

    @org.junit.Test
    public void testFlowHandling() throws Exception {
        doNothing().when(tenantManage).fetchVNSpace(userId);
        when(tenantManage.getUser()).thenReturn(user);

        when(user.getObjects()).thenReturn(object);
        when(user.getObjects()).thenReturn(object);
        when(object.getFlow()).thenReturn(flows);
        when(user.getObjects()).thenReturn(object);
        when(object.getFlow()).thenReturn(flows);

        when(flows.get(0).getFlowName()).thenReturn(new FlowName("flow1"));
        when(flows.get(0).getFlowId()).thenReturn(new FlowId("11111111-1111-1111-1111-111111111111"));

        matches = new LinkedHashMap<String,String>(){{
            put(new String("group"),NEMOConstants.string);
            put(new String("100"),NEMOConstants.integer);
            put(new String("100,200"),NEMOConstants.range);
        }};
        flowmatch.put(new String("1"),matches);

        properties = new LinkedHashMap<String,String>(){{
            //put(new String("1"),NEMOConstants.range);
            put(new String("group"),NEMOConstants.string);
            put(new String("100"),NEMOConstants.integer);
            put(new String("100,200"),NEMOConstants.range);
        }};
        propertyList.put(new String("1"),properties);


        updateFlowLangTest.FlowHandling(userId,flowname,flowmatch,propertyList);

    }
	@org.junit.Test
	public void createFlowTest() throws Exception{
		UserId userid=mock(UserId.class);
		String flowname="flowname";
		LinkedHashMap<String,LinkedHashMap<String,String>> flowmatch=null;
		LinkedHashMap<String,LinkedHashMap<String,String>> propertyList=null;
		doNothing().when(tenantManage).fetchVNSpace(userId);
		when(tenantManage.getUser()).thenReturn(user);
		when(user.getObjects()).thenReturn(null);
		Class<?>[] args=new Class<?>[4];
		args[0]=userid.getClass();
		args[1]=flowname.getClass();
		args[2]=flowmatch.getClass();
		args[3]=propertyList.getClass();
		Object[] args1 = new Object[4]; 
		args1[0]=userid;
		args1[1]=flowname;
		args1[2]=flowmatch;
		args1[3]=propertyList;
		Method setupdateintentlang= updateFlowLangTest.getClass().getDeclaredMethod("createFlow",args);  
		setupdateintentlang.setAccessible(true);
		Assert.assertNull(setupdateintentlang.invoke(updateFlowLangTest,args1));
		//branch 2
		Objects objects=mock(Objects.class);
		Flow flow=mock(Flow.class);
		doNothing().when(tenantManage).fetchVNSpace(userId);
		when(tenantManage.getUser()).thenReturn(user);
		List<Flow> flows = new ArrayList<Flow>();
		flows.add(flow);
		FlowId flowId = new FlowId(UUID.randomUUID().toString());
		when(user.getObjects()).thenReturn(objects);
		when(objects.getFlow()).thenReturn(flows);
		when(flow.getFlowName().getValue()).thenReturn(flowname);
		when(flow.getKey()).thenReturn(new FlowKey(flowId));
		when(flow.getFlowId()).thenReturn(flowId);
		Assert.assertNull(setupdateintentlang.invoke(updateFlowLangTest,args1));
		//branch 3
		doNothing().when(tenantManage).fetchVNSpace(userId);
		when(tenantManage.getUser()).thenReturn(user);
		when(user.getObjects()).thenReturn(objects);
		when(objects.getFlow()).thenReturn(flows);
		when(flow.getFlowName().getValue()).thenReturn(flowname);
		when(flow.getKey()).thenReturn(new FlowKey(flowId));
		when(flow.getFlowId()).thenReturn(flowId);
        flowmatch = new LinkedHashMap<String,LinkedHashMap<String,String>>();

		args1[0]=userid;
		args1[1]=flowname;
		args1[3]=flowmatch;
		args1[4]=propertyList;
        matches = new LinkedHashMap<String,String>(){{
            put(new String("group"),NEMOConstants.string);
            put(new String("100"),NEMOConstants.integer);
            put(new String("100,200"),NEMOConstants.range);
			put(new String("300,200"),NEMOConstants.range);
        }};
		flowmatch.put(new String("1"),matches);
		Assert.assertNull(setupdateintentlang.invoke(updateFlowLangTest,args1));
		//branch 4
        propertyList = new LinkedHashMap<String,LinkedHashMap<String,String>>();
        properties = new LinkedHashMap<String,String>(){{
            put(new String("group"),NEMOConstants.string);
            put(new String("100"),NEMOConstants.integer);
            put(new String("100,200"),NEMOConstants.range);
			put(new String("300,200"),NEMOConstants.range);
        }};
        propertyList.put(new String("1"),properties);
		args1[4]=propertyList;
        Assert.assertNotNull(setupdateintentlang.invoke(updateFlowLangTest,args1));	
        	
	}
}