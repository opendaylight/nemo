/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.intent;

import static org.mockito.Mockito.mock;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Flow;
/**
 * Created by zhangmeng on 2015/11/9.
 */
public class FlowManagerTest extends FlowManager {
    public FlowManagerTest(){
        super(mock(DataBroker.class));
    }
    private UserId userId ;
    private Flow flow;
    @Before
    public void setUp() throws Exception {
        userId = mock(UserId.class);
        flow = mock(Flow.class);
    }

    @Test
    public void testResolveFlow() throws Exception {
        this.resolveFlow(userId,flow);
	Assert.assertNotNull(this);
    }
}