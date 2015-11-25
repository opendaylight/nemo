package org.opendaylight.nemo.intent.computation;
import org.junit.Before;
import org.junit.Test;
import org.opendaylight.nemo.intent.computation.VNMappingUnitUtils;

import static org.junit.Assert.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.vn.pn.mapping.results.user.vn.pn.mapping.VnPnMappingResult;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.VirtualResourceEntityId;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.*;
/**
 * Created by zhangmeng on 2015/11/25.
 */
public class VNMappingUnitUtilsTest extends VNMappingUnitUtils {
    private List<VnPnMappingResult> vnPnMappingResults;
    private VirtualResourceEntityId virtualResourceEntityId;
    @Before
    public void setUp() throws Exception {
        vnPnMappingResults = new LinkedList<VnPnMappingResult>();
        virtualResourceEntityId = mock(VirtualResourceEntityId.class);
    }

    @Test
    public void testGetVnPnMappingResult() throws Exception {
        VnPnMappingResult vnPnMappingResult = mock(VnPnMappingResult.class);
        vnPnMappingResults.add(vnPnMappingResult);
        when(vnPnMappingResult.getVirtualResourceEntityId()).thenReturn(virtualResourceEntityId);
        VNMappingUnitUtils.getVnPnMappingResult(vnPnMappingResults,virtualResourceEntityId);
    }
}