/*
 * Copyright (c) 2016 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.tool.sandbox;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.color.CMMException;

import static org.junit.Assert.*;
import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import org.junit.runner.RunWith;
import org.opendaylight.nemo.tool.sandbox.utils.Config;
import org.opendaylight.nemo.tool.sandbox.utils.FileUtils;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.support.membermodification.MemberMatcher;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.mockito.Mockito.*;
/**
 * Created by zhangmeng on 2016/1/14.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(ch.ethz.ssh2.Connection.class)
public class CmdExecutorTest extends TestCase {
    private CmdExecutor cmdExecutor;
    private Class<CmdExecutor> class1;
    private Field field_sshConnection;
    @Before
    public void setUp() throws Exception {
        class1 = CmdExecutor.class;
        field_sshConnection = class1.getDeclaredField("sshConnection");
        field_sshConnection.setAccessible(true);

        cmdExecutor = new CmdExecutor();
    }

    @Test
    public void testOpen() throws Exception {
        String command = "cd ~";
        Session session = mock(Session.class);
        Connection sshConnection = mock(Connection.class);

        Assert.assertTrue(CmdExecutor.open() == false);
        PowerMockito.whenNew(Connection.class).withArguments(any(String.class)).thenReturn(sshConnection);
        when(sshConnection.authenticateWithPassword(any(String.class), any(String.class))).thenReturn(true);
        Assert.assertTrue(CmdExecutor.open() == false);
//        Assert.assertTrue(field_sshConnection.get(class1) == sshConnection);
//        verify(sshConnection).authenticateWithPassword(any(String.class), any(String.class));

        field_sshConnection.set(class1, sshConnection);
        when(sshConnection.openSession()).thenReturn(session);
        doNothing().when(session).execCommand(command);
        when(session.getStdout()).thenReturn(mock(InputStream.class));
        Assert.assertTrue(CmdExecutor.sshExecute(command) != null);
        verify(session).getStdout();
        CmdExecutor.queryInterfaceMac("eth0");
        CmdExecutor.queryInterfaceMac("1","eth0");

        CmdExecutor.close();
    }

    @Test
    public void testgetMacFromResult() throws Exception {
        Method method = class1.getDeclaredMethod("getMacFromResult",new Class[]{
                String.class
        });
        method.setAccessible(true);
        Assert.assertTrue(method.invoke(class1,"HWaddr") != null);
    }

}