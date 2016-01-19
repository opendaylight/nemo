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

/**
 * Created by zhangmeng on 2016/1/14.
 */
public class FileUtilsTest extends TestCase {
    private FileUtils fileUtils;
    @Before
    public void setUp() throws Exception {
        fileUtils = new FileUtils();
    }

    @Test
    public void testWrite() throws Exception {
        FileUtils.write("home","test");
    }
}