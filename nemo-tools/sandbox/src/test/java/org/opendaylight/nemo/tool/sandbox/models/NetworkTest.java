/*
 * Copyright (c) 2016 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.tool.sandbox.models;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import org.opendaylight.nemo.tool.sandbox.models.Cache;
import org.opendaylight.nemo.tool.sandbox.models.Connector;
import org.opendaylight.nemo.tool.sandbox.models.Network;

import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.support.membermodification.MemberMatcher;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.opendaylight.nemo.tool.sandbox.CmdExecutor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.opendaylight.nemo.tool.sandbox.CmdExecutor;
import org.opendaylight.nemo.tool.sandbox.utils.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * Created by Thomas Liu on 2016/1/14.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({CmdExecutor.class})
public class NetworkTest extends TestCase {
    private Network networkTest;

    @Before
    public void setUp() throws Exception {
        networkTest = new Network();

    }

    @Test
    public void testAddHost() throws Exception {
        networkTest.addHost(new Cache("src1","1"));
        networkTest.addHost(new Cache("dst2","1"));
    }

    @Test
    public void testAddSwitch() throws Exception {
        networkTest.addSwitch(new Switch("dst1", 1L));
        networkTest.addSwitch(new Switch("src2", 1L));
        networkTest.addSwitch(new Switch("src3", 1L));
        networkTest.addSwitch(new Switch("dst3", 1L));
    }

    @Test
    public void testAddLink() throws Exception {
        networkTest.addLink(new Link(new Connector("src1", 1), new Connector("dst1", 2)));
        networkTest.addLink(new Link(new Connector("src2", 1), new Connector("dst2", 2)));
        networkTest.addLink(new Link(new Connector("src3", 1), new Connector("dst3", 2)));

    }

    @Test
    public void testExecute() throws Exception {
        PowerMockito.mockStatic(CmdExecutor.class);
        PowerMockito.when(CmdExecutor.open()).thenReturn(true);
        PowerMockito.when(CmdExecutor.sshExecute(any(String.class))).thenReturn("null");
        PowerMockito.doNothing().when(CmdExecutor.class);
        CmdExecutor.close();
        Assert.assertNotNull(networkTest.execute("cache", "command"));

    }

    @Test
    public void testInstall() throws Exception {
        //test traversal
        networkTest.install();

    }

    @Test
    public void testUninstall() throws Exception {
        PowerMockito.mockStatic(CmdExecutor.class);
        PowerMockito.when(CmdExecutor.sshExecute(any(String.class))).thenReturn("null");
        PowerMockito.when(CmdExecutor.sshExecute(any(String.class))).thenReturn("null");
        PowerMockito.when(CmdExecutor.sshExecute(any(String.class))).thenReturn("null");
        PowerMockito.when(CmdExecutor.sshExecute(any(String.class))).thenReturn("null");
        PowerMockito.when(CmdExecutor.sshExecute(any(String.class))).thenReturn("null");
        PowerMockito.when(CmdExecutor.sshExecute(any(String.class))).thenReturn("null");

        networkTest.uninstall();

    }

    @Test
    public void testEchoConfig() throws Exception {
        PowerMockito.mockStatic(CmdExecutor.class);
        PowerMockito.when(CmdExecutor.sshExecute(any(String.class))).thenReturn("null");
        PowerMockito.when(CmdExecutor.sshExecute(any(String.class))).thenReturn("null");
        PowerMockito.when(CmdExecutor.sshExecute(any(String.class))).thenReturn("null");
        PowerMockito.when(CmdExecutor.sshExecute(any(String.class))).thenReturn("null");
        PowerMockito.when(CmdExecutor.sshExecute(any(String.class))).thenReturn("null");

        networkTest.echoConfig();

        //test hostJsonNode()



    }

    @Test
    public void testInnerLinkJsonNode() throws Exception {

        Assert.assertNotNull(networkTest.innerLinkJsonNode());


    }
}