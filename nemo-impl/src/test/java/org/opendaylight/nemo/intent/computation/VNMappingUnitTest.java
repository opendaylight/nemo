/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.intent.computation;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.DataChangeListener;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.paths.PhysicalPath;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.VirtualNetwork;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.vn.pn.mapping.results.UserVnPnMapping;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.PhysicalNodeId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.PhysicalPortId;
import org.opendaylight.yangtools.concepts.ListenerRegistration;
/**
 * Created by zhangmeng on 2015/11/9.
 */
public class VNMappingUnitTest extends TestCase {
    private VNMappingUnit vnMappingUnit;
    private DataBroker dataBroker;
    private PNComputationUnit pnComputationUnit;
    private Map<PhysicalNodeId, Map<PhysicalPortId, ListenerRegistration<DataChangeListener>>> physicalPortChangeListenerRegs;
    private ListenerRegistration<DataChangeListener> physicalNodeChangeListenerReg;
    private ListenerRegistration<DataChangeListener> physicalLinkChangeListenerReg;

    @Before
    public void setUp() throws Exception {
        dataBroker = mock(DataBroker.class);
        pnComputationUnit = mock(PNComputationUnit.class);
        vnMappingUnit =  mock(VNMappingUnit.class);

    }

    @Test
    public void testVirtualNetworkMapping() throws Exception {
        VirtualNetwork virtualNetwork = mock(VirtualNetwork.class);
        UserVnPnMapping userVnPnMapping = mock(UserVnPnMapping.class);
        List<PhysicalPath> physicalPaths = new ArrayList<PhysicalPath>();
        vnMappingUnit.virtualNetworkMapping(virtualNetwork,userVnPnMapping,physicalPaths);
        Assert.assertNotNull(vnMappingUnit);
        verify(vnMappingUnit).virtualNetworkMapping(any(VirtualNetwork.class),any(UserVnPnMapping.class),any(List.class));
    }

    @Test
    public void testClose() throws Exception {
        Assert.assertNotNull(vnMappingUnit);
        vnMappingUnit.close();
        verify(vnMappingUnit).close();
    }
}