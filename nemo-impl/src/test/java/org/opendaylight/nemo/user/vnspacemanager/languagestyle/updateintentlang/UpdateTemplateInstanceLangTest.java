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
import org.opendaylight.nemo.user.vnspacemanager.languagestyle.updateintentlang.UpdateTemplateInstanceLang;
import org.opendaylight.nemo.user.vnspacemanager.structurestyle.updateintent.UpdateTemplateInstance;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.template.instances.TemplateInstance;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.template.instances.TemplateInstanceBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.template.instances.TemplateInstanceKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.TemplateParameter;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.TemplateParameterBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.TemplateParameterKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.ParameterValuesBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.template.rev151201.template.instance.grouping.template.parameter.parameter.values.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
/**
 * Created by zhangmeng on 2015/12/29.
 */
public class UpdateTemplateInstanceLangTest extends TestCase {
    private DataBroker dataBroker;
    private TenantManage tenantManage;
    private UpdateTemplateInstanceLang updateTemplateInstanceLang;
    @Before
    public void setUp() throws Exception {
        dataBroker = mock(DataBroker.class);
        tenantManage = mock(TenantManage.class);

        updateTemplateInstanceLang = new UpdateTemplateInstanceLang(dataBroker,tenantManage);
    }

    @Test
    public void testTemplateInstanceLang() throws Exception {
        Class<UpdateTemplateInstanceLang> class1 = UpdateTemplateInstanceLang.class;
        Field field = class1.getDeclaredField("updateTemplateInstance");
        field.setAccessible(true);

        UserId userId = mock(UserId.class);
        String nodeName = new String("nodename");
        String nodeType = new String("nodetype");
        LinkedHashMap<String, LinkedHashMap<String,String>> propertyList = new LinkedHashMap<String, LinkedHashMap<String, String>>();
        LinkedHashMap<String, String> values =  new LinkedHashMap<String, String>();
        Map map = mock(Map.class);
        UpdateTemplateInstance updateTemplateInstance = mock(UpdateTemplateInstance.class);
        String parameterName = new String("parameterName");

        propertyList.put(parameterName, values);
        values.put("string", NEMOConstants.string);
        field.set(updateTemplateInstanceLang, updateTemplateInstance);

        when(tenantManage.getTemplateInstance(userId))
                .thenReturn(map)
                .thenReturn(map)
                .thenReturn(null);
        when(tenantManage.getObjectId(userId, nodeName)).thenReturn("11111111-1111-1111-1111-111111111111");
        when(map.containsKey(any(TemplateInstanceId.class)))
                .thenReturn(true)
                .thenReturn(false);
        Assert.assertTrue(updateTemplateInstanceLang.templateInstanceLang(userId, nodeName, nodeType, propertyList).equals("The instance " + nodeName + " has exist."));
        verify(tenantManage,times(2)).getTemplateInstance(userId);
        Assert.assertTrue(propertyList.get(parameterName) == values);
        when(updateTemplateInstance.checkTemplateInstance(any(UserId.class), any(TemplateInstance.class)))
                .thenReturn(null)
                .thenReturn("int")
                .thenReturn("range");
        doNothing().when(tenantManage).setUserTemplateInstance(any(UserId.class), any(TemplateInstanceId.class), any(TemplateInstance.class));
        Assert.assertTrue(updateTemplateInstanceLang.templateInstanceLang(userId, nodeName, nodeType, propertyList) == null);
        values.clear();
        values.put("11", NEMOConstants.integer);
        Assert.assertTrue(updateTemplateInstanceLang.templateInstanceLang(userId, nodeName, nodeType, propertyList).equals("int"));
        values.clear();
        values.put("1,1", NEMOConstants.range);
        Assert.assertTrue(updateTemplateInstanceLang.templateInstanceLang(userId, nodeName, nodeType, propertyList).equals("range"));
        verify(updateTemplateInstance,times(3)).checkTemplateInstance(any(UserId.class), any(TemplateInstance.class));
    }
}