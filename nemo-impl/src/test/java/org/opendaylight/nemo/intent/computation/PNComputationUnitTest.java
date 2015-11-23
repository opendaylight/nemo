/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.intent.computation;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
/**
 * Created by zhangmeng on 2015/11/9.
 */
public class PNComputationUnitTest extends TestCase {
    private PNComputationUnit pnComputationUnit;
    private DataBroker dataBroker;
    @Before
    public void setUp() throws Exception {

        pnComputationUnit = mock(PNComputationUnit.class);
    }

    @Test
    public void testClose() throws Exception {
        pnComputationUnit.close();
        Assert.assertNotNull(pnComputationUnit);
        verify(pnComputationUnit).close();
    }
}