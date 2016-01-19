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

import org.opendaylight.nemo.tool.sandbox.CmdExecutor;
import org.opendaylight.nemo.tool.sandbox.models.VirtualMachine;

import java.lang.reflect.Method;
import java.lang.reflect.Field;

import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.support.membermodification.MemberMatcher;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.opendaylight.nemo.tool.sandbox.CmdExecutor;

/**
 * Created by Thomas Liu on 2016/1/14.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({CmdExecutor.class})
public class VirtualMachineTest extends TestCase {
    String name,uuId;
    Cache cacheTest;
    private VirtualMachine virtualMachineTest;
    private Connector connector;
    private Class class1;
    private Method method;
    @Before
    public void setUp() throws Exception {
        name = new String("name1");
        uuId = new String("11111111-1111-1111-1111-111111111111");
        virtualMachineTest = new VirtualMachine(name,uuId);
        org.opendaylight.nemo.tool.sandbox.models.Connector connector = mock(org.opendaylight.nemo.tool.sandbox.models.Connector.class);
        virtualMachineTest.addConnectors(connector);
        class1 = VirtualMachine.class;

    }

    @Test
    public void testGenerateCommands() throws Exception {
        method = class1.getDeclaredMethod("generateCommands",new Class[]{});
        method.setAccessible(true);
        Assert.assertNotNull(method.invoke(virtualMachineTest));
    }

    @Test
    public void testUninstall() throws Exception {
        PowerMockito.mockStatic(CmdExecutor.class);
        PowerMockito.when(CmdExecutor.sshExecute(any(String.class))).thenReturn("null");
        virtualMachineTest.uninstall();

    }
}