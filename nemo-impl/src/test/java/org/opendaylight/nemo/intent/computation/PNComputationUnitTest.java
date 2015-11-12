package org.test.computation;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.opendaylight.nemo.intent.algorithm.RoutingAlgorithm;
import org.opendaylight.controller.md.sal.binding.api.*;
import javax.xml.crypto.Data;
import org.opendaylight.nemo.intent.computation.PNComputationUnit;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
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