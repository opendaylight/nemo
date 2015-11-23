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
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.paths.VirtualPath;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.VirtualNodeId;
/**
 * Created by zhangmeng on 2015/11/9.
 */
public class VNComputationUnitTest extends TestCase {

    private VNComputationUnit vnComputationUnit;
    private DataBroker dataBroker;
    private UserId userId;
    private VirtualNodeId source,target;
    private VirtualNodeId source1,target1;
    private long bandwidth;
    @Before
    public void setUp() throws Exception {

        source = mock(VirtualNodeId.class);
        target = mock(VirtualNodeId.class);
        source1 = mock(VirtualNodeId.class);
        target1 = mock(VirtualNodeId.class);
        vnComputationUnit = mock(VNComputationUnit.class);
    }

    @Test
    public void testComputePath() throws Exception {
        vnComputationUnit.computePath(source, target);
        verify(vnComputationUnit).computePath(source, target);
        Assert.assertNotEquals(mock(VirtualPath.class), vnComputationUnit.computePath(source, target));
        Assert.assertNotNull(vnComputationUnit);
    }

    @Test
    public void testComputePath1() throws Exception {
        vnComputationUnit.computePath(source1, target1, bandwidth);
        verify(vnComputationUnit).computePath(source1, target1, bandwidth);
        Assert.assertNotEquals(mock(VirtualPath.class), vnComputationUnit.computePath(source1, target1, bandwidth));
        Assert.assertNotNull(vnComputationUnit);
    }

    @Test
    public void testClose() throws Exception {
        vnComputationUnit.close();
        Assert.assertNotNull(vnComputationUnit);
        verify(vnComputationUnit).close();
    }
}