/*
 * Copyright (c) 2016 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.tool.sandbox;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import asg.cliche.Shell;
import asg.cliche.ShellFactory;

import java.io.IOException;
import java.lang.reflect.Field;
import static org.mockito.Mockito.*;

/**
 * Created by zhangmeng on 2016/1/14.
 */
public class EasyCliTest extends TestCase {
    private EasyCli easyCli;
    @Before
    public void setUp() throws Exception {
        easyCli = new EasyCli("1","2","3");
    }

    @Test
    public void testAdd() throws Exception {
        easyCli.add("4");
    }

    @Test
    public void testRun() throws Exception {
        Field field = EasyCli.class.getDeclaredField("shell");
        field.setAccessible(true);

        Shell shell = mock(Shell.class);

        field.set(easyCli,shell);
        doNothing().when(shell).commandLoop();
        easyCli.run();
        verify(shell).commandLoop();
    }
}