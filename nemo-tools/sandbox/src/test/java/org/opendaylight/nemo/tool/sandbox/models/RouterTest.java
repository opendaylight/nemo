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

/**
 * Created by Thomas Liu on 2016/1/14.
 */
public class RouterTest extends TestCase {
    private String name;
    private long dataPathId;
    private Router routerTest;
    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void test1() throws Exception {
        name = new String("name1");
        dataPathId = 1L;
        routerTest = new Router(name,dataPathId);
    }
}