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

import org.opendaylight.nemo.tool.sandbox.utils.Config;
import org.opendaylight.nemo.tool.sandbox.utils.HexString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import java.lang.reflect.Method;
import java.lang.reflect.Field;

/**
 * Created by Thomas Liu on 2016/1/14.
 */
public class SwitchTest extends TestCase {

    private String name;
    private long dataPathId;
    private Switch switchTest;
    private Class class1;
    private Method method;


    @Before
    public void setUp() throws Exception {
        name = new String("name1");
        dataPathId = 1L;
        switchTest = new Switch(name,dataPathId);
        class1 = Switch.class;
        org.opendaylight.nemo.tool.sandbox.models.Connector connector = new org.opendaylight.nemo.tool.sandbox.models.Connector("connectorname",1);
        switchTest.addConnectors(connector);
    }

    @Test
    public void testGetDataPathId() throws Exception {
        Assert.assertNotNull(switchTest.getDataPathId());
    }

    @Test
    public void testGenerateCommands() throws Exception {
        method = class1.getDeclaredMethod("generateCommands",new Class[]{});
        method.setAccessible(true);
        Assert.assertNotNull(method.invoke(switchTest));

    }

    @Test
    public void testToString() throws Exception {
        Assert.assertNotNull(switchTest.toString());

    }
}