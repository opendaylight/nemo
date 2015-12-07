/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.renderer.cli;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.nemo.renderer.cli.CliRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.mockito.Mockito.*;
/**
 * Created by zhangmeng on 2015/11/30.
 */
public class CliRendererTest extends TestCase {
    private DataBroker dataBroker;
    private CliRenderer cliRenderer;
    @Before
    public void setUp() throws Exception {
        dataBroker = mock(DataBroker.class);
        cliRenderer = new CliRenderer(dataBroker);
    }

    @Test
    public void testClose() throws Exception {
        cliRenderer.close();
        Assert.assertNotNull(cliRenderer);
    }
}