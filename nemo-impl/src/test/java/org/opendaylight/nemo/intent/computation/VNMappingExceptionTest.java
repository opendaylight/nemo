/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.intent.computation;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by zhangmeng on 2015/11/10.
 */
public class VNMappingExceptionTest {
    private VNMappingException vnMappingException1;
    private VNMappingException vnMappingException2;
    private VNMappingException vnMappingException3;
    private VNMappingException vnMappingException4;

    private String message;
    private Throwable cause;
    @Before
    public void setUp() throws Exception {
        message = new String();
        cause = new Throwable();
    }

    @Test
    public void Construction()throws Exception{
        Assert.assertNull(vnMappingException1);
        Assert.assertNull(vnMappingException2);
        Assert.assertNull(vnMappingException3);
        Assert.assertNull(vnMappingException4);

        vnMappingException1 = new VNMappingException();
        vnMappingException2 = new VNMappingException(message);
        vnMappingException3 = new VNMappingException(cause);
        vnMappingException4 = new VNMappingException(message,cause);

        Assert.assertNotNull(vnMappingException1);
        Assert.assertNotNull(vnMappingException2);
        Assert.assertNotNull(vnMappingException3);
        Assert.assertNotNull(vnMappingException4);
    }
}