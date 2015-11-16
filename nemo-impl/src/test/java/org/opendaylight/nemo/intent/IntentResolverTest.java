/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.intent;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Assert;
import org.junit.Test;
import org.opendaylight.nemo.intent.*;
import org.opendaylight.nemo.intent.computation.PNComputationUnit;
import org.opendaylight.nemo.intent.computation.VNComputationUnit;
import org.opendaylight.nemo.intent.computation.VNMappingUnit;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import java.util.Map;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by zhangmeng on 2015/11/10.
 */
public class IntentResolverTest extends TestCase {
    private DataBroker dataBroker;
    private NodeMapper nodeMapper;
    private ConnectionMapper connectionMapper;
    private FlowManager flowManager;
    private OperationResolver operationResolver;
    private PNComputationUnit pnComputationUnit;
    private Map<UserId, VNComputationUnit> vnComputationUnits;
    private VNMappingUnit vnMappingUnit;

    private IntentResolver intentResolver;

    @Before
    public void setUp() throws Exception {
        dataBroker = mock(DataBroker.class);
        intentResolver = mock(IntentResolver.class);
    }

    @Test
    public void testResolveIntent() throws Exception {
        UserId userId = mock(UserId.class);
        intentResolver.resolveIntent(userId);
        verify(intentResolver).resolveIntent(any(UserId.class));
        Assert.assertNotNull(intentResolver);
    }

    @Test
    public void testClose() throws Exception {
        intentResolver.close();
        verify(intentResolver).close();
        Assert.assertNotNull(intentResolver);
    }
}