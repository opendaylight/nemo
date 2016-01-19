/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.tool.sandbox.utils;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by zhangmeng on 2016/1/14.
 */
public class ConfigTest extends TestCase {
    private Config config;
    @Before
    public void setUp() throws Exception {
        config = new Config();
    }

    @Test
    public void testAll() throws Exception{
        Assert.assertTrue(Config.getControllerSocket() != null);
        Assert.assertTrue(Config.isPrintProcess() == false);
        Assert.assertTrue(Config.getHostName() != null);
        Assert.assertTrue(Config.getHostUser() != null);
        Assert.assertTrue(Config.getHostPassword() != null);
        Assert.assertTrue(Config.getConfigPath() != null);
        Assert.assertTrue(Config.getConfigHostsFileName() != null);
        Assert.assertTrue(Config.getConfigExternalsFileName() != null);
        Assert.assertTrue(Config.getConfigLinksFileName() != null);
        Assert.assertTrue(Config.getConfigNodesFileName() != null);
    }
}