/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.intent.computation;

import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.vn.pn.mapping.results.user.vn.pn.mapping.VnPnMappingResult;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.VirtualResourceEntityId;

/**
 * Created by zhangmeng on 2015/11/10.
 */
public class VNMappingUnitUtilsTest extends VNMappingUnitUtils {
    private VNMappingUnitUtils vnMappingUnitUtils;
    List<VnPnMappingResult> vnPnMappingResults;
    VirtualResourceEntityId virtualResourceEntityId;
    VnPnMappingResult test;
    @Before
    public void setUp() throws Exception {
        vnPnMappingResults = new ArrayList<VnPnMappingResult>();
        virtualResourceEntityId = mock(VirtualResourceEntityId.class);
        test = mock(VnPnMappingResult.class);
        //vnMappingUnitUtils = new VNMappingUnitUtils();
    }

    @Test
    public void testGetVnPnMappingResult() throws Exception {
        test = VNMappingUnitUtils.getVnPnMappingResult(vnPnMappingResults,virtualResourceEntityId);
        //verify(this).getVnPnMappingResult(any(List.class), any(VirtualResourceEntityId.class));
        Assert.assertNotEquals(test,mock(VnPnMappingResult.class));
    }
}