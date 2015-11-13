package org.opendaylight.nemo.renderer.openflow;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import static org.junit.Assert.*;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.WriteTransaction;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.nemo.renderer.openflow.FlowTableManager;
import org.opendaylight.nemo.renderer.openflow.OpenflowRenderer;
import org.opendaylight.nemo.renderer.openflow.ResourceManager;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import static org.mockito.Mockito.*;
/**
 * Created by zhangmeng on 2015/11/11.
 */
public class OpenflowRendererTest extends TestCase {
    private OpenflowRenderer openflowRenderer;
    private DataBroker dataBroker;
    private  ResourceManager resourceManager;
    private  FlowTableManager flowTableMng;
    @Before
    public void setUp() throws Exception {
        dataBroker = mock(DataBroker.class);
        openflowRenderer = new OpenflowRenderer(dataBroker);
    }

    @Test
    public void testClose() throws Exception {
	openflowRenderer.close();
        Assert.assertNotNull(openflowRenderer);
    }
}