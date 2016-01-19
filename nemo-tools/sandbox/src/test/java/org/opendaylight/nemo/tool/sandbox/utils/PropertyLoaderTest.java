/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.tool.sandbox.utils;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by zhangmeng on 2016/1/14.
 */
public class PropertyLoaderTest extends TestCase {
    private PropertyLoader propertyLoader;
    @Before
    public void setUp() throws Exception {
        propertyLoader = new PropertyLoader();
    }

    @Test
    public void testLoadProperties() throws Exception {
        PropertyLoader.loadProperties("home","no file!");
    }

    @Test
    public void testReadLines() throws Exception {
        PropertyLoader.readLines("home","no file!");
    }
}