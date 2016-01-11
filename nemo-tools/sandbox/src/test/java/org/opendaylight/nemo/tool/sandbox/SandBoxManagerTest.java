/*
 * Copyright (c) 2016 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.tool.sandbox;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import org.json.JSONObject;
import org.junit.runner.RunWith;
import org.opendaylight.nemo.tool.sandbox.models.*;
import org.opendaylight.nemo.tool.sandbox.utils.PropertyLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.support.membermodification.MemberMatcher;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
/**
 * Created by zhangmeng on 2016/1/14.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(CmdExecutor.class)
public class SandBoxManagerTest extends TestCase {
    private SandBoxManager sandBoxManager;
    private Method method;
    @Before
    public void setUp() throws Exception {
        sandBoxManager = SandBoxManager.getInstance();
    }

    @Test
    public void testGetInstance() throws Exception {
        sandBoxManager = SandBoxManager.getInstance();
    }

    @Test
    public void testGets() throws Exception {
        Field field = SandBoxManager.class.getDeclaredField("network");
        field.setAccessible(true);

        Network network = mock(Network.class);
        JSONObject jsonObject = mock(JSONObject.class);

        //no powermock and network=null
        field.set(sandBoxManager, null);
        org.junit.Assert.assertTrue(!sandBoxManager.createNetwork(null));
        org.junit.Assert.assertTrue(!sandBoxManager.createNetwork());
        org.junit.Assert.assertTrue(sandBoxManager.getHosts() == null);
        org.junit.Assert.assertTrue(sandBoxManager.getExternals() == null);
        org.junit.Assert.assertTrue(sandBoxManager.getSwitches() == null);
        org.junit.Assert.assertTrue(sandBoxManager.getLinks() == null);
        org.junit.Assert.assertTrue(sandBoxManager.destroyNetwork());

        field.set(sandBoxManager, network);
        PowerMockito.mockStatic(CmdExecutor.class);
        PowerMockito.when(CmdExecutor.open()).thenReturn(true);
        when(network.hostJsonNode()).thenReturn(jsonObject);
        when(network.externalJsonNode()).thenReturn(jsonObject);
        when(network.nodeJsonNode()).thenReturn(jsonObject);
        when(network.innerLinkJsonNode()).thenReturn(jsonObject);
        when(network.execute(any(String.class), any(String.class))).thenReturn("test");
        org.junit.Assert.assertTrue(sandBoxManager.execute("1","1").equals("test"));
        org.junit.Assert.assertTrue(sandBoxManager.getHosts() != null);
        org.junit.Assert.assertTrue(sandBoxManager.getExternals() != null);
        org.junit.Assert.assertTrue(sandBoxManager.getSwitches() != null);
        org.junit.Assert.assertTrue(sandBoxManager.getLinks() != null);

        org.junit.Assert.assertTrue(sandBoxManager.createNetwork(PropertyLoader.readLines("/Network", "Network file does not exist.")));
        org.junit.Assert.assertTrue(sandBoxManager.createNetwork());
        org.junit.Assert.assertTrue(sandBoxManager.destroyNetwork());
    }
    @Test
    public void testGetNodeType() throws Exception {
        method = SandBoxManager.class.getDeclaredMethod("getNodeType",new Class[]{String.class});
        method.setAccessible(true);

        org.junit.Assert.assertTrue(method.invoke(sandBoxManager, "Switch") == NodeType.SWITCH);
        org.junit.Assert.assertTrue(method.invoke(sandBoxManager, "Router") == NodeType.ROUTER);
        org.junit.Assert.assertTrue(method.invoke(sandBoxManager, "Cache") == NodeType.CACHE);
        org.junit.Assert.assertTrue(method.invoke(sandBoxManager, "Firewall") == NodeType.FIREWALL);
        org.junit.Assert.assertTrue(method.invoke(sandBoxManager, "Vm") == NodeType.VM);
        org.junit.Assert.assertTrue(method.invoke(sandBoxManager,"1") == NodeType.UNKNOWN);

    }

    @Test
    public void testHandleLink() throws Exception {
        method = SandBoxManager.class.getDeclaredMethod("handleLink",new Class[]{String.class});
        method.setAccessible(true);

        org.junit.Assert.assertTrue(method.invoke(sandBoxManager, "12") == null);
        org.junit.Assert.assertTrue(method.invoke(sandBoxManager,"1,2,3,4,5") != null);
    }

    @Test
    public void testSaveConnector() throws Exception {
        method = SandBoxManager.class.getDeclaredMethod("saveConnector",new Class[]{
                Connector.class,
                Map.class
        });
        method.setAccessible(true);

        Connector connector = mock(Connector.class);
        Map<String, List<Connector>> connectorMap = mock(Map.class);

        when(connector.getNodeName()).thenReturn("test");
        when(connectorMap.containsKey(any(String.class)))
                .thenReturn(true)
                .thenReturn(false);
        when(connectorMap.get(any(String.class))).thenReturn(mock(List.class));
        method.invoke(sandBoxManager, connector, connectorMap);
        method.invoke(sandBoxManager, connector, connectorMap);
        verify(connectorMap,times(2)).containsKey(any(String.class));
    }

    @Test
    public void testParserHost() throws Exception {
        method = SandBoxManager.class.getDeclaredMethod("parserHost",new Class[]{
                String[].class,
                Map.class,
                Host.class
        });
        method.setAccessible(true);

        String[] args = {"1","2","3","1:2"};
        Map<String, List<Connector>> connectorMap = mock(Map.class);
        Host host = mock(Host.class);
        Connector connector = mock(Connector.class);
        List<Connector> connectors = new ArrayList<Connector>();

        connectors.add(connector);

        when(host.getName()).thenReturn("test");
        when(connectorMap.get(host.getName())).thenReturn(connectors);

        method.invoke(sandBoxManager, args, connectorMap, host);
        verify(host,times(2)).getName();
    }

    @Test
    public void testHandleFirewall() throws Exception {
        method = SandBoxManager.class.getDeclaredMethod("handleFirewall",new Class[]{
                String.class,
                Map.class
        });
        method.setAccessible(true);

        String line = "test";
        Map<String, List<Connector>> connectorMap = mock(Map.class);

        org.junit.Assert.assertTrue(method.invoke(sandBoxManager,line,connectorMap) == null);
        line = "1,2,3,1:2";
        when(connectorMap.get(any(String.class))).thenReturn(null);
        org.junit.Assert.assertTrue(method.invoke(sandBoxManager,line,connectorMap) != null);
    }

    @Test
    public void testHandleCache() throws Exception {
        method = SandBoxManager.class.getDeclaredMethod("handleCache",new Class[]{
                String.class,
                Map.class
        });
        method.setAccessible(true);

        String line = "test";
        Map<String, List<Connector>> connectorMap = mock(Map.class);

        org.junit.Assert.assertTrue(method.invoke(sandBoxManager,line,connectorMap) == null);
        line = "1,2,3,1:2";
        when(connectorMap.get(any(String.class))).thenReturn(null);
        org.junit.Assert.assertTrue(method.invoke(sandBoxManager,line,connectorMap) != null);

    }

    @Test
    public void testHandleVirtualMachine() throws Exception {
        method = SandBoxManager.class.getDeclaredMethod("handleVirtualMachine",new Class[]{
                String.class,
                Map.class
        });
        method.setAccessible(true);

        String line = "test";
        Map<String, List<Connector>> connectorMap = mock(Map.class);

        org.junit.Assert.assertTrue(method.invoke(sandBoxManager,line,connectorMap) == null);
        line = "1,2,3,1:2";
        when(connectorMap.get(any(String.class))).thenReturn(null);
        org.junit.Assert.assertTrue(method.invoke(sandBoxManager,line,connectorMap) != null);
    }

    @Test
    public void testHandleSwitch() throws Exception {
        method = SandBoxManager.class.getDeclaredMethod("handleSwitch",new Class[]{
                String.class,
                Map.class
        });
        method.setAccessible(true);

        List<Connector> connectorList = new ArrayList<Connector>();
        Connector connector = mock(Connector.class);
        String line = "test";
        Map<String, List<Connector>> connectorMap = mock(Map.class);

        connectorList.add(connector);

        org.junit.Assert.assertTrue(method.invoke(sandBoxManager, line, connectorMap) == null);
        line = "Router,2,3";
        when(connectorMap.get(any(String.class))).thenReturn(connectorList);
        org.junit.Assert.assertTrue(method.invoke(sandBoxManager, line, connectorMap) != null);

    }

    @Test
    public void testGenerateNetwork() throws Exception {
        method = SandBoxManager.class.getDeclaredMethod("generateNetwork",new Class[]{List.class});
        method.setAccessible(true);

        List<String> list = new ArrayList<String>();
        list.add("Switch");
        method.invoke(sandBoxManager, list);
        list.clear();
        list.add("Vm");
        method.invoke(sandBoxManager, list);
        list.clear();
        list.add("Cache");
        method.invoke(sandBoxManager, list);
        list.clear();
        list.add("Firewall");
        method.invoke(sandBoxManager, list);

    }
}