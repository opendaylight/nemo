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
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by zhangmeng on 2016/1/14.
 */
public class HexStringTest extends TestCase {
    private HexString hexString;
    @Test
    public void testToHexString() throws Exception {
        Assert.assertTrue(HexString.toHexString(1L,6) != null);
        Assert.assertTrue(HexString.toHexString(1L) != null);
    }

    @Test
    public void testFromHexString() throws Exception {
        Assert.assertTrue(HexString.fromHexString("22:32") != null);
    }

    @Test
    public void testToLong() throws Exception {
//        System.out.println(HexString.toLong("11:22"));
        Assert.assertTrue(HexString.toLong("11:22") != 0);
    }
}