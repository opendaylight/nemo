/*
 * Copyright (c) 2016 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.tool.sandbox.northbound;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by zhangmeng on 2016/1/14.
 */
public class ExecuteRequestTest extends TestCase {
    private ExecuteRequest executeRequest;
    @Before
    public void setUp() throws Exception {
        executeRequest =  new ExecuteRequest();
    }

    @Test
    public void testGet_Set() throws Exception {
        Assert.assertTrue(executeRequest.getHostName() == null);
        executeRequest.setHostName("test");
        Assert.assertTrue(executeRequest.getHostName().equals("test"));

        Assert.assertTrue(executeRequest.getCommand() == null);
        executeRequest.setCommand("command");
        Assert.assertTrue(executeRequest.getCommand().equals("command"));

        //test to string
        Assert.assertTrue(executeRequest.toString().equals("ExecuteRequest{" +
                "hostName='" + "test" + '\'' +
                ", command='" + "command" + '\'' +
                '}'));
    }

}