/*
 * Copyright (c) 2016 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.nemo.tool.sandbox.northbound;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by zhangmeng on 2016/1/14.
 */
public class CreateRequestTest extends TestCase {
    private CreateRequest createRequest;
    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testInit()throws Exception{
        createRequest = new CreateRequest();
    }
}