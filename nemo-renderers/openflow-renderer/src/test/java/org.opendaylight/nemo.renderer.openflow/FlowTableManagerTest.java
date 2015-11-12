package org.opendaylight.nemo.renderer.openflow;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import org.opendaylight.nemo.renderer.openflow.FlowTableManager;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.nemo.renderer.openflow.ResourceManager;
import org.opendaylight.nemo.renderer.openflow.entity.ResourceBean;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
/**
 * Created by zhangmeng on 2015/11/11.
 */
public class FlowTableManagerTest extends TestCase {
    private FlowTableManager flowTableManager;
    private ResourceManager resourceManager;
    private DataBroker dataProvider;
    @Before
    public void setUp() throws Exception {
        resourceManager = mock(ResourceManager.class);
        dataProvider = mock(DataBroker.class);

        flowTableManager = new FlowTableManager(dataProvider,resourceManager);
    }

    @Test
    public void testClose() throws Exception {
        flowTableManager.close();
	Assert.assertNotNull(flowTableManager);
    }
}