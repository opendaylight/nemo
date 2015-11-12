package org.test.user.vnspacemanager;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.opendaylight.nemo.user.vnspacemanager.VNSpaceManagement;

import static org.junit.Assert.*;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.NodeId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.NodeInstance;
import static org.mockito.Mockito.*;
/**
 * Created by zhangmeng on 2015/11/12.
 */
public class VNSpaceManagementTest extends TestCase {
    private VNSpaceManagement vnSpaceManagement;
    private DataBroker dataBroker;
    private UserId userId;
    private NodeId nodeId;
    private NodeInstance nodeInstance;
    @Before
    public void setUp() throws Exception {
        dataBroker = mock(DataBroker.class);
        userId = mock(UserId.class);
        nodeId = mock(NodeId.class);
        nodeInstance = mock(NodeInstance.class);
        vnSpaceManagement = new VNSpaceManagement(dataBroker);
    }

    @Test
    public void testGetNodeInstance() throws Exception {
        nodeInstance = vnSpaceManagement.getNodeInstance(userId,nodeId);
        Assert.assertNotSame(mock(NodeInstance.class),nodeInstance);
    }
}