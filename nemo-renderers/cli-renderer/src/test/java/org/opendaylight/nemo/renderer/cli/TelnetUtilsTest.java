/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.nemo.renderer.cli;

import static org.mockito.Mockito.mock;

import java.lang.reflect.Method;
import junit.framework.TestCase;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by zhangmeng on 2015/12/8.
 */
public class TelnetUtilsTest extends TestCase {
    private TelnetUtils telnetUtils;
    @Override
    @Before
    public void setUp() throws Exception {
        telnetUtils = new TelnetUtils();
    }

    @Test
    public void testClose() throws Exception {
        telnetUtils.close();
        Assert.assertTrue(telnetUtils != null);
    }

    @Test
    public void testIsClearOver() throws Exception {
        Assert.assertTrue(TelnetUtils.isClearOver());
    }

    @Test
    public void testSetClearOver() throws Exception {
        Boolean clearOver = false;
        TelnetUtils.setClearOver(clearOver);
        Assert.assertTrue(!TelnetUtils.isClearOver());
    }

    @Test
    public void testSet_GetInstanceOfClearCliTemplate() throws Exception {
//        Assert.assertTrue(TelnetUtils.getInstanceOfClearCliTemplate() == null);//test null
        String instanceOfClearCliTemplate = new String("test");
        TelnetUtils.setInstanceOfClearCliTemplate(instanceOfClearCliTemplate);
        Assert.assertTrue(TelnetUtils.getInstanceOfClearCliTemplate().equals("test"));
    }

    @Test
    public void testSet_GetInstanceOfNewCliTemplate() throws Exception {
//        Assert.assertEquals(TelnetUtils.getInstanceOfNewCliTemplate(),null);//test null
        String instanceOfNewCliTemplate = new String("test");
        TelnetUtils.setInstanceOfNewCliTemplate(instanceOfNewCliTemplate);
        Assert.assertTrue(TelnetUtils.getInstanceOfNewCliTemplate().equals("test"));
    }

    @Test
    public void testSet_GetCurrentDeviceName() throws Exception {
//        Assert.assertTrue(TelnetUtils.getCurrentDeviceName() == null); //test null
        String currentDeviceName = new String("test");
        TelnetUtils.setCurrentDeviceName(currentDeviceName);
        Assert.assertTrue(TelnetUtils.getCurrentDeviceName().equals("test"));

    }
    @Test
    public void testSet_GetConfigOver() throws Exception {
        Assert.assertTrue(!TelnetUtils.isConfigOver()); //test null
        Boolean configOver = true;
        TelnetUtils.setConfigOver(configOver);
        Assert.assertTrue(TelnetUtils.isConfigOver());
    }

    @Test
    public void testConfigL3vpnOnDeviceByCli() throws Exception {
        String deviceName = new String("deviceNamePE1");
        String clearOldConfigString = new String("clearOldConfigString");
        String cliExecutionSequences = new String("cliExecutionSequences");

        telnetUtils.configL3vpnOnDeviceByCli(deviceName,clearOldConfigString,cliExecutionSequences);

        Assert.assertTrue(!TelnetUtils.isConfigOver());
        Assert.assertEquals(TelnetUtils.getInstanceOfClearCliTemplate(), "clearOldConfigString");
        Assert.assertEquals(TelnetUtils.getInstanceOfNewCliTemplate(),"cliExecutionSequences");
        Assert.assertEquals(TelnetUtils.getCurrentDeviceName(),"deviceNamePE1");
    }

    @Test
    public void testStartConnectAndConfig() throws Exception {
        Class<TelnetUtils> class1 = TelnetUtils.class;
        Method method = class1.getDeclaredMethod("startConnectAndConfig",new Class[]{SimpleChannelUpstreamHandler.class});
        Method method1 = class1.getDeclaredMethod("sendNewConfig");
        method.setAccessible(true);
        method1.setAccessible(true);

        SimpleChannelUpstreamHandler simpleChannelUpstreamHandler = mock(SimpleChannelUpstreamHandler.class);
        Assert.assertEquals(TelnetUtils.getCurrentDeviceName(),"deviceNamePE1");
        method.invoke(telnetUtils,simpleChannelUpstreamHandler);
        method1.invoke(telnetUtils);
    }
}