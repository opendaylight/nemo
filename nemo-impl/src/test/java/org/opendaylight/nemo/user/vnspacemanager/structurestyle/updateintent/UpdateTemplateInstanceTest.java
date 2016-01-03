/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.user.vnspacemanager.structurestyle.updateintent;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.ParameterValues;
import static org.junit.Assert.*;

import static org.junit.Assert.*;
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
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.parameter.values.IntValue;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.definition.grouping.AbstractIntents;
//import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.definition.grouping.AbstractObjects;
//import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.definition.grouping.AbstractNode;

import java.lang.String;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import static org.mockito.Mockito.*;
/**
 * Created by zhangmeng on 2015/12/30.
 */
public class UpdateTemplateInstanceTest extends TestCase {
    private DataBroker dataBroker;
    private TenantManage tenantManage;
    private UpdateTemplateInstance updateTemplateInstance;
    private Class<UpdateTemplateInstance> class1;
    private Method method;
    private Field field;
    private TemplateInstance templateInstance;
    private UserId userId;
    //private TemplateDefinition templateDefinition;


    @Before
    public void setUp() throws Exception {
        dataBroker = mock(DataBroker.class);
        tenantManage = mock(TenantManage.class);
        class1 = UpdateTemplateInstance.class;
        updateTemplateInstance = new UpdateTemplateInstance(dataBroker,tenantManage);
        templateInstance = mock(TemplateInstance.class);
        userId = mock(UserId.class);
        //templateDefinition = mock(TemplateDefinition.class);
    }

    @Test
    public void testCheckTemplateInstance1() throws Exception {
        //test if1
        TemplateName templateName = new TemplateName("tename");
        TemplateDefinition templateDefinition = mock(TemplateDefinition.class);
        Map<TemplateName, TemplateDefinition> templateMap = new HashMap<TemplateName, TemplateDefinition>();
        templateMap.put(templateName,templateDefinition);
        when(tenantManage.getTempalteDefinition(userId)).thenReturn(templateMap);
        when(templateInstance.getTemplateName()).thenReturn(templateName);
        when(tenantManage.getTempalteDefinition(userId)).thenReturn(templateMap);
        when(templateInstance.getTemplateName()).thenReturn(templateName);
        //when(templateMap.containsKey(templateName)).thenReturn(true);

        //test (definition!=null)
        List<TemplateParameter> templateParameters = new ArrayList<TemplateParameter>();
        TemplateParameter templateParameter = mock(TemplateParameter.class);
        templateParameters.add(templateParameter);
        when(templateDefinition.getTemplateParameter()).thenReturn(templateParameters);
        ParameterName parameterName = new ParameterName("ParameterName");
        when(templateParameter.getParameterName()).thenReturn(parameterName);
        when(templateParameter.getParameterValueType()).thenReturn(TemplateParameter.ParameterValueType.Range);



        List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.TemplateParameter> instanceParameters = new ArrayList<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.TemplateParameter>();
        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.TemplateParameter templateParameter2 = mock(org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.TemplateParameter.class);
        instanceParameters.add(templateParameter2);
        when(templateInstance.getTemplateParameter()).thenReturn(instanceParameters);
        when(templateParameter2.getParameterName()).thenReturn(parameterName);
        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.ParameterValues parameterValues = mock(org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.ParameterValues.class);
        when(templateParameter2.getParameterValues()).thenReturn(parameterValues);
        when(parameterValues.getIntValue()).thenReturn(null);
        when(parameterValues.getStringValue()).thenReturn(null);
        when(parameterValues.getRangeValue()).thenReturn(null);
        //when(parameterValues.getRangeValue()).thenReturn(mock(org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.parameter.values.RangeValue.class));
        when(templateParameter2.getParameterName()).thenReturn(parameterName);
        when(templateParameter2.getParameterValues()).thenReturn(parameterValues);
        TemplateInstanceName templateInstanceName = mock(TemplateInstanceName.class);
        when(templateInstance.getTemplateInstanceName()).thenReturn(templateInstanceName);

        Assert.assertTrue(updateTemplateInstance.checkTemplateInstance(userId, templateInstance) == "The value type should be string");
        //System.out.println("###########  Result1 : "+updateTemplateInstance.checkTemplateInstance(userId, templateInstance)+"   #################");
        //Assert.assertTrue(updateTemplateInstance.checkTemplateInstance(userId, templateInstance) == null);




    }

    public void testCheckTemplateInstance2() throws Exception {
        TemplateName templateName = new TemplateName("tename");
        TemplateDefinition templateDefinition = mock(TemplateDefinition.class);
        Map<TemplateName, TemplateDefinition> templateMap = new HashMap<TemplateName, TemplateDefinition>();
        templateMap.put(templateName,templateDefinition);
        when(tenantManage.getTempalteDefinition(userId)).thenReturn(null);
        when(tenantManage.getDefinitionDataStore(userId)).thenReturn(templateMap);
        when(templateInstance.getTemplateName()).thenReturn(templateName);
        when(tenantManage.getDefinitionDataStore(userId)).thenReturn(templateMap);
        when(templateInstance.getTemplateName()).thenReturn(templateName);

        List<TemplateParameter> templateParameters = new ArrayList<TemplateParameter>();
        TemplateParameter templateParameter = mock(TemplateParameter.class);
        templateParameters.add(templateParameter);
        when(templateDefinition.getTemplateParameter()).thenReturn(templateParameters);
        ParameterName parameterName = new ParameterName("ParameterName");
        when(templateParameter.getParameterName()).thenReturn(parameterName);
        when(templateParameter.getParameterValueType()).thenReturn(TemplateParameter.ParameterValueType.Int);

        List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.TemplateParameter> instanceParameters = new ArrayList<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.TemplateParameter>();
        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.TemplateParameter templateParameter2 = mock(org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.TemplateParameter.class);
        instanceParameters.add(templateParameter2);
        when(templateInstance.getTemplateParameter()).thenReturn(instanceParameters);
        when(templateParameter2.getParameterName()).thenReturn(parameterName);
        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.ParameterValues parameterValues = mock(org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.ParameterValues.class);
        when(templateParameter2.getParameterValues()).thenReturn(parameterValues);
        List<IntValue> intValues = new ArrayList<IntValue>();
        when(parameterValues.getIntValue()).thenReturn(intValues);
        Assert.assertTrue(updateTemplateInstance.checkTemplateInstance(userId, templateInstance) == "The value type should be string");
        //System.out.println("###########  Result2 : "+updateTemplateInstance.checkTemplateInstance(userId, templateInstance)+"   #################");

    }

    public void testCheckTemplateInstance3() throws Exception {
        TemplateName templateName = new TemplateName("tename");
        TemplateDefinition templateDefinition = mock(TemplateDefinition.class);
        Map<TemplateName, TemplateDefinition> templateMap = new HashMap<TemplateName, TemplateDefinition>();
        templateMap.put(templateName,templateDefinition);
        when(tenantManage.getTempalteDefinition(userId)).thenReturn(null);
        when(tenantManage.getDefinitionDataStore(userId)).thenReturn(null);
        Map<UserId, User> usersMap = new HashMap<UserId, User>();
        UserId userId2 = mock(UserId.class);
        User user = mock(User.class);
        usersMap.put(userId2,user);
        when(tenantManage.getUsers()).thenReturn(usersMap);
        when(user.getUserRole()).thenReturn(new  org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserRoleName(NEMOConstants.admin));
        when(user.getUserId()).thenReturn(userId2);
        when(tenantManage.getDefinitionDataStore(userId2)).thenReturn(templateMap);
        when(user.getUserId()).thenReturn(userId2);
        when(templateInstance.getTemplateName()).thenReturn(templateName);
        when(tenantManage.getDefinitionDataStore(userId2)).thenReturn(templateMap);
        when(user.getUserId()).thenReturn(userId2);
        when(templateInstance.getTemplateName()).thenReturn(templateName);
        when(tenantManage.getDefinitionDataStore(userId2)).thenReturn(templateMap);

        List<TemplateParameter> templateParameters = new ArrayList<TemplateParameter>();
        TemplateParameter templateParameter = mock(TemplateParameter.class);
        templateParameters.add(templateParameter);
        when(templateDefinition.getTemplateParameter()).thenReturn(templateParameters);
        ParameterName parameterName = new ParameterName("ParameterName");
        when(templateParameter.getParameterName()).thenReturn(parameterName);
        when(templateParameter.getParameterValueType()).thenReturn(TemplateParameter.ParameterValueType.String);

        List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.TemplateParameter> instanceParameters = new ArrayList<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.TemplateParameter>();
        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.TemplateParameter templateParameter2 = mock(org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.TemplateParameter.class);
        instanceParameters.add(templateParameter2);
        when(templateInstance.getTemplateParameter()).thenReturn(instanceParameters);
        when(templateParameter2.getParameterName()).thenReturn(parameterName);
        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.ParameterValues parameterValues = mock(org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.ParameterValues.class);
        when(templateParameter2.getParameterValues()).thenReturn(parameterValues);
        when(parameterValues.getIntValue()).thenReturn(null);
        when(parameterValues.getStringValue()).thenReturn(null);
        when(parameterValues.getRangeValue()).thenReturn(null);
        Assert.assertTrue(updateTemplateInstance.checkTemplateInstance(userId, templateInstance) == "The value type should be string");
        //System.out.println("###########  Result3 : "+updateTemplateInstance.checkTemplateInstance(userId, templateInstance)+"   #################");
    }

    public void testCheckTemplateInstance4() throws Exception {
        TemplateName templateName = new TemplateName("tename");
        TemplateDefinition templateDefinition = mock(TemplateDefinition.class);
        Map<TemplateName, TemplateDefinition> templateMap = new HashMap<TemplateName, TemplateDefinition>();
        templateMap.put(templateName,templateDefinition);
        when(tenantManage.getTempalteDefinition(userId)).thenReturn(null);
        when(tenantManage.getDefinitionDataStore(userId)).thenReturn(templateMap);
        when(templateInstance.getTemplateName()).thenReturn(templateName);
        when(tenantManage.getDefinitionDataStore(userId)).thenReturn(templateMap);
        when(templateInstance.getTemplateName()).thenReturn(templateName);

        List<TemplateParameter> templateParameters = new ArrayList<TemplateParameter>();
        TemplateParameter templateParameter = mock(TemplateParameter.class);
        templateParameters.add(templateParameter);
        when(templateDefinition.getTemplateParameter()).thenReturn(templateParameters);
        ParameterName parameterName = new ParameterName("ParameterName");
        when(templateParameter.getParameterName()).thenReturn(parameterName);
        when(templateParameter.getParameterValueType()).thenReturn(TemplateParameter.ParameterValueType.Int);

        List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.TemplateParameter> instanceParameters = new ArrayList<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.TemplateParameter>();
        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.TemplateParameter templateParameter2 = mock(org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.TemplateParameter.class);
        instanceParameters.add(templateParameter2);
        when(templateInstance.getTemplateParameter()).thenReturn(instanceParameters);
        when(templateParameter2.getParameterName()).thenReturn(new ParameterName("ParameterName2"));
        when(templateParameter2.getParameterName()).thenReturn(new ParameterName("ParameterName2"));
        //Assert.assertTrue(updateTemplateInstance.checkTemplateInstance(userId, templateInstance) == "The parameter ParameterName2 is not defined.");
        System.out.println("###########  Result4 : "+updateTemplateInstance.checkTemplateInstance(userId, templateInstance)+"   #################");

    }

    public void testCheckTemplateInstance5() throws Exception {
        TemplateName templateName = new TemplateName("tename");
        TemplateDefinition templateDefinition = mock(TemplateDefinition.class);
        Map<TemplateName, TemplateDefinition> templateMap = new HashMap<TemplateName, TemplateDefinition>();
        templateMap.put(templateName,templateDefinition);
        when(tenantManage.getTempalteDefinition(userId)).thenReturn(null);
        when(tenantManage.getDefinitionDataStore(userId)).thenReturn(null);
        Map<UserId, User> usersMap = new HashMap<UserId, User>();
        UserId userId2 = mock(UserId.class);
        User user = mock(User.class);
        usersMap.put(userId2,user);
        when(tenantManage.getUsers()).thenReturn(usersMap);

        when(user.getUserRole()).thenReturn(new  org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserRoleName("asd"));
        when(templateInstance.getTemplateName()).thenReturn(templateName);
        //Assert.assertTrue(updateTemplateInstance.checkTemplateInstance(userId, templateInstance) == "The template tename is not exist.");
        System.out.println("###########  Result5 : "+updateTemplateInstance.checkTemplateInstance(userId, templateInstance)+"   #################");

    }



    @Test
    public void testGetTempalteDefinition() throws Exception {
        method = class1.getDeclaredMethod("getTempalteDefinition",new Class[]{
                TemplateName.class
        });
        method.setAccessible(true);

        UserId userId = mock(UserId.class);
        User user = mock(User.class);
        TemplateName templateName = new TemplateName("tename");
        TemplateDefinition templateDefinition = mock(TemplateDefinition.class);
        Map<UserId, User> usersMap = new HashMap<UserId, User>();
        Map<TemplateName, TemplateDefinition> templateMap = new HashMap<TemplateName, TemplateDefinition>();


        usersMap.put(userId,user);
        templateMap.put(templateName,templateDefinition);

        when(tenantManage.getUsers()).thenReturn(usersMap);
        when(user.getUserRole()).thenReturn(new UserRoleName("admin"));
        when(user.getUserId()).thenReturn(userId);
        when(tenantManage.getTempalteDefinition(userId)).thenReturn(templateMap);
        Assert.assertTrue(method.invoke(updateTemplateInstance, templateName) != null);
    }

    @Test
    public void testgetcreateInstance() throws Exception {
        method = class1.getDeclaredMethod("createInstance",new Class[]{
                UserId.class,
                TemplateDefinition.class,
                HashMap.class,
                String.class
        });
        method.setAccessible(true);
        UserId userid = mock(UserId.class);
        TemplateDefinition definition = mock(TemplateDefinition.class);
        Map<ParameterName, org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.ParameterValues> instaceParameterMap = new HashMap<ParameterName, org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.ParameterValues>();
        ParameterName parameterName = new ParameterName("ParameterName");
        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.ParameterValues parameterValues = mock(org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.ParameterValues.class);
        instaceParameterMap.put(parameterName,parameterValues);
        String instanceName = new String("instance");

        //test
        List<TemplateParameter> teplateParameters = new ArrayList<TemplateParameter>();
        TemplateParameter parameter = mock(TemplateParameter.class);
        teplateParameters.add(parameter);
        when(definition.getTemplateParameter()).thenReturn(teplateParameters);
        AbstractIntents abstractIntents = mock(AbstractIntents.class);
        AbstractObjects abstractObjects = mock(AbstractObjects.class);
        when(definition.getAbstractIntents()).thenReturn(abstractIntents);
        when(abstractIntents.getAbstractObjects()).thenReturn(abstractObjects);
        when(definition.getAbstractIntents()).thenReturn(abstractIntents);
        when(abstractIntents.getAbstractObjects()).thenReturn(abstractObjects);
        List<AbstractNode> abstractNodes = new ArrayList<AbstractNode>();
        AbstractNode abstractNode = mock(AbstractNode.class);
        abstractNodes.add(abstractNode);
        when(abstractObjects.getAbstractNode()).thenReturn(abstractNodes);
        when(abstractObjects.getAbstractNode()).thenReturn(abstractNodes);
        when(abstractNode.getNodeType()).thenReturn(new NodeType("host"));
        Map<PhysicalHostName, PhysicalHost> physicalHostMap = new HashMap<PhysicalHostName, PhysicalHost>();
        PhysicalHostName physicalHostName = new PhysicalHostName("hostname");
        PhysicalHost physicalHost = mock(PhysicalHost.class);
        physicalHostMap.put(physicalHostName,physicalHost);
        field = class1.getDeclaredField("getDefinitions");
        field.setAccessible(true);
        GetDefinitions getDefinitions = mock(GetDefinitions.class);
        field.set(updateTemplateInstance,getDefinitions);
        when(getDefinitions.getPhysicalHost()).thenReturn(physicalHostMap);
        when(abstractNode.getNodeName()).thenReturn(new NodeName("hostname"));
        PhysicalHostId physicalHostId = new PhysicalHostId("11111111-1111-1111-1111-111111111111");
        when(abstractNode.getNodeName()).thenReturn(new NodeName("hostname"));
        when(physicalHost.getHostId()).thenReturn(physicalHostId);
        when(abstractNode.getNodeName()).thenReturn(new NodeName("ParameterName"));
        when(abstractNode.getNodeName()).thenReturn(new NodeName("ParameterName"));
        List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.parameter.values.StringValue> stringValues = new ArrayList<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.parameter.values.StringValue>();
        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.parameter.values.StringValue stringValue = mock(org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.parameter.values.StringValue.class);
        stringValues.add(stringValue);
        when(parameterValues.getStringValue()).thenReturn(stringValues);
        when(stringValue.getValue()).thenReturn("test");
        when(abstractNode.getNodeType()).thenReturn(new NodeType("ParameterName"));
        when(abstractNode.getNodeType()).thenReturn(new NodeType("ParameterName"));
        when(parameterValues.getStringValue()).thenReturn(stringValues);
        when(stringValue.getValue()).thenReturn("test");

        // if (abstractNode.getSubNode()!=null)
        List<SubNode> subNodeList = new LinkedList<SubNode>();
        SubNode subNode = mock(SubNode.class);
        subNodeList.add(subNode);
        when(abstractNode.getSubNode()).thenReturn(subNodeList);

        field = class1.getDeclaredField("abstractInstanceIdMap");
        field.setAccessible(true);
        Map<String,String> abstractInstanceIdMap = new HashMap<String,String>();
        String s1 = "11111111-1111-1111-1111-111111111111";
        String s2 = "11111111-1111-1111-1111-111111111111";
        abstractInstanceIdMap.put(s1,s2);
        field.set(updateTemplateInstance,abstractInstanceIdMap);
        when(subNode.getNodeId()).thenReturn(new NodeId("11111111-1111-1111-1111-111111111111"));
        when(subNode.getNodeId()).thenReturn(new NodeId("11111111-1111-1111-1111-111111111111"));
        when(subNode.getNodeId()).thenReturn(new NodeId("11111111-1111-1111-1111-111111111111"));

        when(abstractNode.getNodeId()).thenReturn(new NodeId("11111111-1111-1111-1111-111111111111"));
        when(abstractNode.getNodeName()).thenReturn(new NodeName("hostname"));
        when(abstractNode.getNodeId()).thenReturn(new NodeId("11111111-1111-1111-1111-111111111111"));

        //if (abstractNode.getProperty()!=null)
        List<Property> propertyList = new LinkedList<Property>();
        Property property = mock(Property.class);
        propertyList.add(property);
        //when(abstractNode.getProperty()).thenReturn(propertyList);
        when(abstractNode.getProperty()).thenReturn(null);
        when(property.getPropertyName()).thenReturn(new PropertyName(NEMOConstants.sub_nodes));
        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.PropertyValues propertyValues = mock(org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.PropertyValues.class);
        List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.property.values.StringValue> stringValues2 = new ArrayList<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.property.values.StringValue>();
        //List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.property.values.IntValue> intValues = new ArrayList<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.property.values.IntValue>();
        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.property.values.StringValue stringValue2 = mock(org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.property.values.StringValue.class);
        //org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.property.values.IntValue intValue = mock(org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.property.values.IntValue.class);
        RangeValue rangeValue = mock(RangeValue.class);
        stringValues2.add(stringValue2);
        //intValues.add(intValue);
        when(property.getPropertyValues()).thenReturn(propertyValues);
        when(propertyValues.getStringValue()).thenReturn(stringValues2);
        when(property.getPropertyValues()).thenReturn(propertyValues);
        when(propertyValues.getIntValue()).thenReturn(null);
        when(property.getPropertyValues()).thenReturn(propertyValues);
        when(propertyValues.getRangeValue()).thenReturn(null);

        List<StringValue> subnodes = new ArrayList<StringValue>();
        StringValue subnode = mock(StringValue.class);
        subnodes.add(subnode);
        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.PropertyValues propertyValues2 = mock(org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.PropertyValues.class);
        when(property.getPropertyValues()).thenReturn(propertyValues2);
        when(propertyValues2.getStringValue()).thenReturn(subnodes);
        when(subnode.getValue()).thenReturn("ParameterName");
        List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.parameter.values.StringValue> stringValues3 = new ArrayList<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.parameter.values.StringValue>();
        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.parameter.values.StringValue stringValue3 = mock(org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.parameter.values.StringValue.class);
        stringValues3.add(stringValue3);
        when(subnode.getValue()).thenReturn("ParameterName");
        when(parameterValues.getStringValue()).thenReturn(stringValues3);
        String s3 = "test";
        when(stringValue3.getValue()).thenReturn(s3);
        when(tenantManage.getObjectId(userid,s3)).thenReturn("test");
        when(stringValue3.getValue()).thenReturn(s3);
        when(tenantManage.getObjectId(userid,s3)).thenReturn("test");


        //if (objects.getAbstractConnection()!=null)
        List<AbstractConnection> abstractConnections = new ArrayList<AbstractConnection>();
        AbstractConnection abstractConnection = mock(AbstractConnection.class);
        abstractConnections.add(abstractConnection);
        when(abstractObjects.getAbstractConnection()).thenReturn(abstractConnections);
        when(abstractObjects.getAbstractConnection()).thenReturn(abstractConnections);
        when(abstractConnection.getConnectionName()).thenReturn(new ConnectionName("connname"));
        when(abstractConnection.getConnectionType()).thenReturn(new ConnectionType("conntype"));
        when(abstractConnection.getConnectionId()).thenReturn(new ConnectionId("11111111-1111-1111-1111-111111111111"));
        when(abstractConnection.getConnectionName()).thenReturn(new ConnectionName("connname"));
        when(abstractConnection.getConnectionId()).thenReturn(new ConnectionId("11111111-1111-1111-1111-111111111111"));
        List<EndNode> endNodeList = new LinkedList<EndNode>();
        EndNode endNode = mock(EndNode.class);
        endNodeList.add(endNode);
        when(abstractConnection.getEndNode()).thenReturn(endNodeList);
        when(abstractConnection.getEndNode()).thenReturn(endNodeList);
        abstractInstanceIdMap.clear();
        abstractInstanceIdMap.put(s1,s2);
        when(endNode.getNodeId()).thenReturn(new NodeId("11111111-1111-1111-1111-111111111111"));
        when(endNode.getNodeId()).thenReturn(new NodeId("11111111-1111-1111-1111-111111111111"));
        when(endNode.getNodeId()).thenReturn(new NodeId("11111111-1111-1111-1111-111111111111"));
        List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.connection.instance.Property> propertyList2 = new LinkedList<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.connection.instance.Property>();
        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.connection.instance.Property property2 = mock(org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.connection.instance.Property.class);
        propertyList2.add(property2);
        when(abstractConnection.getProperty()).thenReturn(propertyList2);
        when(abstractConnection.getProperty()).thenReturn(propertyList2);

        //test createConnProperty() time1
        when(property2.getPropertyName()).thenReturn(new PropertyName("createConnProperty"));
        when(property2.getPropertyName()).thenReturn(new PropertyName("createConnProperty"));

        List<StringValue> stringValuesConn = new LinkedList<StringValue>();
        StringValue stringValueConn = mock(StringValue.class);
        stringValuesConn.add(stringValueConn);
        List<IntValue> intValuesConn = new LinkedList<IntValue>();
        IntValue intValueConn = mock(IntValue.class);
        intValuesConn.add(intValueConn);
        RangeValue rangeValueConn = mock(RangeValue.class);
        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.PropertyValues propertyValuesConn = mock(org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.PropertyValues.class);
        when(property2.getPropertyValues()).thenReturn(propertyValuesConn);
        when(propertyValuesConn.getStringValue()).thenReturn(stringValuesConn);
        when(stringValueConn.getValue()).thenReturn("ParameterName");
        when(stringValueConn.getValue()).thenReturn("ParameterName");
        List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.parameter.values.StringValue> stringValues4 = new ArrayList<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.parameter.values.StringValue>();
        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.parameter.values.StringValue stringValue4 = mock(org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.parameter.values.StringValue.class);
        stringValues4.add(stringValue4);
        when(parameterValues.getStringValue()).thenReturn(stringValues4);
        when(parameterValues.getStringValue()).thenReturn(stringValues4);
        when(stringValue4.getValue()).thenReturn("stringValue4");
        when(stringValue4.getValue()).thenReturn("stringValue4");
        when(parameterValues.getIntValue()).thenReturn(intValuesConn);
        when(parameterValues.getIntValue()).thenReturn(intValuesConn);
        when(intValueConn.getValue()).thenReturn(1L);
        when(intValueConn.getValue()).thenReturn(1L);
        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.parameter.values.RangeValue rangeValue2 = mock(org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.parameter.values.RangeValue.class);
        when(parameterValues.getRangeValue()).thenReturn(rangeValue2);
        when(parameterValues.getRangeValue()).thenReturn(rangeValue2);
        when(rangeValueConn.getMax()).thenReturn(2L);
        when(rangeValueConn.getMin()).thenReturn(1L);

        List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.property.values.IntValue> intValues2 = new ArrayList<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.property.values.IntValue>();
        when(property2.getPropertyValues()).thenReturn(propertyValuesConn);
        when(propertyValuesConn.getIntValue()).thenReturn(intValues2);
        when(property2.getPropertyValues()).thenReturn(propertyValuesConn);
        when(propertyValuesConn.getIntValue()).thenReturn(intValues2);

        when(property2.getPropertyValues()).thenReturn(propertyValuesConn);
        when(propertyValuesConn.getRangeValue()).thenReturn(rangeValueConn);
        when(property2.getPropertyValues()).thenReturn(propertyValuesConn);
        when(propertyValuesConn.getRangeValue()).thenReturn(rangeValueConn);

        //test createConnProperty() time2
        when(property2.getPropertyName()).thenReturn(new PropertyName("createConnProperty"));
        when(property2.getPropertyName()).thenReturn(new PropertyName("createConnProperty"));

        when(property2.getPropertyValues()).thenReturn(propertyValuesConn);
        when(propertyValuesConn.getStringValue()).thenReturn(stringValuesConn);
        when(stringValueConn.getValue()).thenReturn("ParameterName");
        when(stringValueConn.getValue()).thenReturn("ParameterName");
        when(parameterValues.getStringValue()).thenReturn(stringValues4);
        when(parameterValues.getStringValue()).thenReturn(stringValues4);
        when(stringValue4.getValue()).thenReturn("stringValue4");
        when(stringValue4.getValue()).thenReturn("stringValue4");
        when(parameterValues.getIntValue()).thenReturn(intValuesConn);
        when(parameterValues.getIntValue()).thenReturn(intValuesConn);
        when(intValueConn.getValue()).thenReturn(1L);
        when(intValueConn.getValue()).thenReturn(1L);
        when(parameterValues.getRangeValue()).thenReturn(rangeValue2);
        when(parameterValues.getRangeValue()).thenReturn(rangeValue2);
        when(rangeValueConn.getMax()).thenReturn(2L);
        when(rangeValueConn.getMin()).thenReturn(1L);
        when(property2.getPropertyValues()).thenReturn(propertyValuesConn);
        when(propertyValuesConn.getIntValue()).thenReturn(intValues2);
        when(property2.getPropertyValues()).thenReturn(propertyValuesConn);
        when(propertyValuesConn.getIntValue()).thenReturn(intValues2);
        when(property2.getPropertyValues()).thenReturn(propertyValuesConn);
        when(propertyValuesConn.getRangeValue()).thenReturn(rangeValueConn);
        when(property2.getPropertyValues()).thenReturn(propertyValuesConn);
        when(propertyValuesConn.getRangeValue()).thenReturn(rangeValueConn);

        // if (objects.getAbstractFlow()!=null)
        List<AbstractFlow> abstractFlows = new ArrayList<AbstractFlow>();
        AbstractFlow abstractFlow = mock(AbstractFlow.class);
        abstractFlows.add(abstractFlow);
        when(abstractObjects.getAbstractFlow()).thenReturn(abstractFlows);
        when(abstractObjects.getAbstractFlow()).thenReturn(abstractFlows);
        when(abstractFlow.getFlowName()).thenReturn(new FlowName("FlowName"));
        //abstractInstanceIdMap.clear();
        when(abstractFlow.getFlowId()).thenReturn(new FlowId("11111111-1111-1111-1111-111111111111"));
        List<MatchItem> matchItems = new LinkedList<MatchItem>();
        MatchItem matchItem = mock(MatchItem.class);
        matchItems.add(matchItem);
        when(abstractFlow.getMatchItem()).thenReturn(matchItems);
        when(abstractFlow.getMatchItem()).thenReturn(matchItems);
        //test createMatchItem
        when(matchItem.getMatchItemName()).thenReturn(new MatchItemName("MatchItemName"));
        when(matchItem.getMatchItemName()).thenReturn(new MatchItemName("MatchItemName"));
        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.match.item.instance.MatchItemValue matchItemValue = mock(org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.match.item.instance.MatchItemValue.class);
        when(matchItem.getMatchItemValue()).thenReturn(matchItemValue); //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        when(matchItemValue.getStringValue()).thenReturn("ParameterName");
        when(matchItem.getMatchItemValue()).thenReturn(matchItemValue);
        when(matchItemValue.getStringValue()).thenReturn("ParameterName");
        when(matchItem.getMatchItemValue()).thenReturn(matchItemValue);
        when(matchItemValue.getStringValue()).thenReturn("ParameterName");
        List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.parameter.values.StringValue> stringValuesMatch = new LinkedList<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.parameter.values.StringValue>();
        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.parameter.values.StringValue stringValueMatch = mock(org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.parameter.values.StringValue.class);
        stringValuesMatch.add(stringValueMatch);
        List<IntValue> intValuesMatch = new LinkedList<IntValue>();
        IntValue intValueMatch = mock(IntValue.class);
        intValuesMatch.add(intValueMatch);
        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.parameter.values.RangeValue rangeValueMatch = mock(org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.parameter.values.RangeValue.class);
        when(parameterValues.getStringValue()).thenReturn(stringValuesMatch);
        when(parameterValues.getStringValue()).thenReturn(stringValuesMatch);
        when(stringValueMatch.getValue()).thenReturn("value");
        when(parameterValues.getIntValue()).thenReturn(intValuesMatch);
        when(parameterValues.getIntValue()).thenReturn(intValuesMatch);
        when(intValueMatch.getValue()).thenReturn(2L);
        when(parameterValues.getRangeValue()).thenReturn(rangeValueMatch);
        when(parameterValues.getRangeValue()).thenReturn(rangeValueMatch);
        when(rangeValueMatch.getMax()).thenReturn(2L);
        when(rangeValueMatch.getMin()).thenReturn(1L);
        when(matchItem.getMatchItemValue()).thenReturn(matchItemValue);
        when(matchItemValue.getIntValue()).thenReturn(1L);
        when(matchItem.getMatchItemValue()).thenReturn(matchItemValue);
        when(matchItemValue.getIntValue()).thenReturn(2L);
        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.match.item.instance.match.item.value.RangeValue rangeValue3 = mock(org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.match.item.instance.match.item.value.RangeValue.class);
        when(matchItem.getMatchItemValue()).thenReturn(matchItemValue);
        when(matchItemValue.getRangeValue()).thenReturn(rangeValue3);
        when(matchItem.getMatchItemValue()).thenReturn(matchItemValue);
        when(matchItemValue.getRangeValue()).thenReturn(rangeValue3);


        //if (definition.getAbstractIntents().getAbstractOperations()!=null)
        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.definition.grouping._abstract.intents.AbstractOperations abstractOperations = mock(org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.definition.grouping._abstract.intents.AbstractOperations.class);
        List<AbstractOperation> abstractOperationList = new ArrayList<AbstractOperation>();
        AbstractOperation abstractOperation = mock(AbstractOperation.class);
        abstractOperationList.add(abstractOperation);
        when(definition.getAbstractIntents()).thenReturn(abstractIntents);
        when(abstractIntents.getAbstractOperations()).thenReturn(abstractOperations);
        when(definition.getAbstractIntents()).thenReturn(abstractIntents);
        when(abstractIntents.getAbstractOperations()).thenReturn(abstractOperations);
        when(abstractOperations.getAbstractOperation()).thenReturn(abstractOperationList);
        instaceParameterMap.clear();
        parameterName = new ParameterName("OperationName"+"."+NEMOConstants.Priority);
        instaceParameterMap.put(parameterName,parameterValues);
        when(abstractOperation.getOperationName()).thenReturn(new OperationName("OperationName"));
        when(abstractOperation.getOperationName()).thenReturn(new OperationName("OperationName"));
        List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.parameter.values.IntValue> intValuesOp = new ArrayList<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.parameter.values.IntValue>();
        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.parameter.values.IntValue intValueOp = mock(org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.parameter.values.IntValue.class);;
        intValuesOp.add(intValueOp);
        when(parameterValues.getIntValue()).thenReturn(intValuesOp);
        when(parameterValues.getStringValue()).thenReturn(null);
        when(parameterValues.getRangeValue()).thenReturn(null);
        when(parameterValues.getIntValue()).thenReturn(intValuesOp);
        when(intValueOp.getValue()).thenReturn(1L);

        //when(abstractOperation.getTargetObject()).thenReturn(new org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.ObjectId("11111111-1111-1111-1111-111111111111"));


        parameterName = new ParameterName(NEMOConstants.Target+"."+"OperationName");
        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.ParameterValues parameterValues2 = mock(org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.ParameterValues.class);
        instaceParameterMap.put(parameterName,parameterValues2);
        when(abstractOperation.getOperationName()).thenReturn(new OperationName("OperationName"));
        List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.parameter.values.StringValue> stringValuesOp = new ArrayList<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.parameter.values.StringValue>();
        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.parameter.values.StringValue stringValueOp = mock(org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.parameter.values.StringValue.class);;
        stringValuesOp.add(stringValueOp);
        Map<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.ParameterValues, String> nameIdMap = new HashMap<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.ParameterValues, String>();
        String s4 = NEMOConstants.Target+"."+"OperationName";
        String s5 = "11111111-1111-1111-1111-111111111111";
        nameIdMap.put(parameterValues2,s5);
        field = class1.getDeclaredField("nameIdMap");
        field.setAccessible(true);
        field.set(updateTemplateInstance,nameIdMap);
        when(parameterValues2.getIntValue()).thenReturn(null);
        when(parameterValues2.getStringValue()).thenReturn(stringValuesOp);
        when(parameterValues2.getRangeValue()).thenReturn(null);


        //if (abstractOperation.getConditionSegment()!=null)
        List<ConditionSegment> conditionSegments = new LinkedList<ConditionSegment>();
        ConditionSegment conditionSegment = mock(ConditionSegment.class);
        conditionSegments.add(conditionSegment);
        when(abstractOperation.getConditionSegment()).thenReturn(conditionSegments);

        //test createCondition
        //instaceParameterMap.clear();
        ParameterName parameterNamecondition = new ParameterName("ParameterName");
        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.ParameterValues parameterValuesCodition = mock(org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.ParameterValues.class);
        instaceParameterMap.put(parameterNamecondition,parameterValuesCodition);
        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.condition.instance.condition.segment.ConditionParameterTargetValue  conditionParameterTargetValue = mock(org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.condition.instance.condition.segment.ConditionParameterTargetValue.class);
        when(conditionSegment.getConditionParameterTargetValue()).thenReturn(conditionParameterTargetValue);
        when(conditionParameterTargetValue.getStringValue()).thenReturn("ParameterName");
        when(conditionSegment.getConditionParameterTargetValue()).thenReturn(conditionParameterTargetValue);
        when(conditionParameterTargetValue.getStringValue()).thenReturn("ParameterName");
        List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.parameter.values.StringValue> stringValuesCod = new LinkedList<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.parameter.values.StringValue>();
        List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.parameter.values.IntValue> intValuesCod = new LinkedList<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.parameter.values.IntValue>();
        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.parameter.values.StringValue stringValueCod = mock(org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.parameter.values.StringValue.class);
        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.parameter.values.IntValue intValueCod = mock(org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.parameter.values.IntValue.class);
        stringValuesCod.add(stringValueCod);
        intValuesCod.add(intValueCod);
        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.parameter.values.RangeValue rangeValueCod = mock(org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.parameter.values.RangeValue.class);
        when(parameterValuesCodition.getStringValue()).thenReturn(stringValuesCod);
        when(parameterValuesCodition.getStringValue()).thenReturn(stringValuesCod);
        when(stringValueCod.getValue()).thenReturn("stringValueCod");
        when(parameterValuesCodition.getIntValue()).thenReturn(intValuesCod);
        when(parameterValuesCodition.getIntValue()).thenReturn(intValuesCod);
        when(intValueCod.getValue()).thenReturn(1L);
        when(parameterValuesCodition.getRangeValue()).thenReturn(rangeValueCod);
        when(parameterValuesCodition.getRangeValue()).thenReturn(rangeValueCod);
        when(conditionSegment.getConditionParameterTargetValue()).thenReturn(conditionParameterTargetValue);
        when(conditionParameterTargetValue.getIntValue()).thenReturn(1L);
        when(conditionSegment.getConditionParameterTargetValue()).thenReturn(conditionParameterTargetValue);
        when(conditionParameterTargetValue.getIntValue()).thenReturn(1L);
        when(conditionSegment.getConditionParameterTargetValue()).thenReturn(conditionParameterTargetValue);
        when(conditionParameterTargetValue.getRangeValue()).thenReturn(mock(org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.condition.instance.condition.segment.condition.parameter.target.value.RangeValue.class));
        when(conditionSegment.getConditionParameterTargetValue()).thenReturn(conditionParameterTargetValue);
        when(conditionParameterTargetValue.getRangeValue()).thenReturn(mock(org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.condition.instance.condition.segment.condition.parameter.target.value.RangeValue.class));

        //if (abstractOperation.getAction()==null)
       /* List<Action> actions = new LinkedList<Action>();
        Action action = mock(Action.class);
        actions.add(action);*/
        when(abstractOperation.getAction()).thenReturn(null);

        method.invoke(updateTemplateInstance, userid, definition, instaceParameterMap, instanceName);
        System.out.println("###########  Result XXXX "+method.invoke(updateTemplateInstance,userid,definition,instaceParameterMap,instanceName)+" ####");
       // Assert.assertTrue(method.invoke(userid,definition,instaceParameterMap,instanceName) != null);

    }


    @Test
    public void testcreateAction() throws Exception {
        method = class1.getDeclaredMethod("createAction",new Class[]{
                Action.class,
                HashMap.class,
        });
        method.setAccessible(true);
        Action action = mock(Action.class);
        HashMap<ParameterName, org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.ParameterValues> instaceParameterMap = new HashMap<ParameterName, org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.ParameterValues>();
        ParameterName parameterNameAc = new ParameterName("ParameterName");
        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.ParameterValues parameterValuesAc = mock(org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.ParameterValues.class);
        instaceParameterMap.put(parameterNameAc,parameterValuesAc);

        //test createAction
        when(action.getActionName()).thenReturn(new ActionName("action"));
        when(action.getActionName()).thenReturn(new ActionName("action"));

        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.ParameterValues parameterValuesAction = mock(org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.ParameterValues.class);
        when(action.getParameterValues()).thenReturn(parameterValuesAction);

        List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.StringValue> stringValuesAc1 = new LinkedList<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.StringValue>();
        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.StringValue stringValueAc1 = mock(org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.StringValue.class);
        List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.IntValue> intValuesAc1 = new LinkedList<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.IntValue>();
        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.IntValue intValueAc1 = mock(org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.IntValue.class);
        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.RangeValue rangeValueAc1 = mock(org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.RangeValue.class);
        stringValuesAc1.add(stringValueAc1);
        intValuesAc1.add(intValueAc1);
        when(action.getParameterValues()).thenReturn(parameterValuesAction);
        when(parameterValuesAction.getStringValue()).thenReturn(stringValuesAc1);
         when(action.getParameterValues()).thenReturn(parameterValuesAction);
        when(parameterValuesAction.getStringValue()).thenReturn(stringValuesAc1);
        when(stringValueAc1.getValue()).thenReturn("ParameterName");
        when(stringValueAc1.getValue()).thenReturn("ParameterName");
        List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.parameter.values.StringValue> stringValuesAc2 = new LinkedList<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.parameter.values.StringValue>();
        List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.parameter.values.IntValue> intValuesAc2 = new LinkedList<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.parameter.values.IntValue>();
        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.parameter.values.StringValue stringValueAc2 = mock(org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.parameter.values.StringValue.class);
        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.parameter.values.IntValue intValueAc2 = mock(org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.parameter.values.IntValue.class);
        stringValuesAc2.add(stringValueAc2);
        intValuesAc2.add(intValueAc2);
        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.parameter.values.RangeValue rangeValueAc2 = mock(org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.parameter.values.RangeValue.class);
        when(parameterValuesAc.getStringValue()).thenReturn(stringValuesAc2);
        when(parameterValuesAc.getStringValue()).thenReturn(stringValuesAc2);


        Map<String, String> nameIdMap = new HashMap<String, String>();
        String s1 = "s1";
        String s2 = "11111111-1111-1111-1111-111111111111";
        nameIdMap.put(s1,s2);
        field = class1.getDeclaredField("nameIdMap");
        field.setAccessible(true);
        field.set(updateTemplateInstance,nameIdMap);

        field = class1.getDeclaredField("abstractInstanceIdMap");
        field.setAccessible(true);
        Map<String,String> abstractInstanceIdMap = new HashMap<String,String>();
        String s3 = "11111111-1111-1111-1111-111111111111";
        String s4 = "11111111-1111-1111-1111-111111111111";
        abstractInstanceIdMap.put(s3,s4);
        field.set(updateTemplateInstance,abstractInstanceIdMap);

        when(stringValueAc2.getValue()).thenReturn("s1");
        when(stringValueAc2.getValue()).thenReturn("s1");

        when(parameterValuesAc.getIntValue()).thenReturn(intValuesAc2);
        when(parameterValuesAc.getIntValue()).thenReturn(intValuesAc2);
        when(intValueAc2.getValue()).thenReturn(1L);
        when(intValueAc2.getValue()).thenReturn(1L);

        when(parameterValuesAc.getRangeValue()).thenReturn(rangeValueAc2);
        when(parameterValuesAc.getRangeValue()).thenReturn(rangeValueAc2);
        when(rangeValueAc2.getMax()).thenReturn(2L);
        when(rangeValueAc2.getMin()).thenReturn(1L);

        when(action.getParameterValues()).thenReturn(parameterValuesAction);
        when(parameterValuesAction.getIntValue()).thenReturn(intValuesAc1);
        when(action.getParameterValues()).thenReturn(parameterValuesAction);
        when(parameterValuesAction.getIntValue()).thenReturn(intValuesAc1);

        when(action.getParameterValues()).thenReturn(parameterValuesAction);
        when(parameterValuesAction.getRangeValue()).thenReturn(rangeValueAc1);
        when(action.getParameterValues()).thenReturn(parameterValuesAction);
        when(parameterValuesAction.getRangeValue()).thenReturn(rangeValueAc1);
        when(rangeValueAc1.getMax()).thenReturn(2L);
        when(rangeValueAc1.getMin()).thenReturn(1L);



        method.invoke(updateTemplateInstance, action,instaceParameterMap);
        //System.out.println("###########  Result XXXXXXXX "+method.invoke(updateTemplateInstance, action,instaceParameterMap)+" ####");
    }
}