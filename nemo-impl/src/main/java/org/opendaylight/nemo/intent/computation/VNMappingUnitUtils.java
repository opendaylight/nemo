/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.nemo.intent.computation;

import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.vn.pn.mapping.results.user.vn.pn.mapping.VnPnMappingResult;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.VirtualResourceEntityId;

import java.util.List;

/**
 * Implement the utilities used in the virtual
 * network mapping.
 *
 * @author Zhigang Ji
 */
public class VNMappingUnitUtils {
    /**
     * TODO
     *
     * @param vnPnMappingResults TODO
     * @param virtualResourceEntityId TODO
     * @return TODO
     */
    protected static VnPnMappingResult getVnPnMappingResult(List<VnPnMappingResult> vnPnMappingResults,
                                                            VirtualResourceEntityId virtualResourceEntityId) {
        for ( VnPnMappingResult vnPnMappingResult : vnPnMappingResults ) {
            if ( vnPnMappingResult.getVirtualResourceEntityId().equals(virtualResourceEntityId) ) {
                return vnPnMappingResult;
            }
        }

        return null;
    }
}
