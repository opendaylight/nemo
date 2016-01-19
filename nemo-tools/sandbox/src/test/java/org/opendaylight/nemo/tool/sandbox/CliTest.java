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
import asg.cliche.Command;
import asg.cliche.Shell;
import asg.cliche.ShellDependent;
import static org.mockito.Mockito.*;
/**
 * Created by zhangmeng on 2016/1/14.
 */
public class CliTest extends TestCase {
    private Cli cli;
    @Before
    public void setUp() throws Exception {
        cli = new Cli();
    }

    @Test
    public void testCliSetShell() throws Exception {
        Shell shell = mock(Shell.class);
        cli.cliSetShell(shell);
    }

    @Test
    public void testInstall() throws Exception {
        cli.install();
    }

    @Test
    public void testUninstall() throws Exception {
        cli.uninstall();
    }
}