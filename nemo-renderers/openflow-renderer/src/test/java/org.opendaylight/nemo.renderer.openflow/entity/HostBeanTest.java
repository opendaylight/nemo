package org.opendaylight.nemo.renderer.openflow.entity;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.opendaylight.nemo.renderer.openflow.entity.HostBean;
import java.util.LinkedList;
import static org.mockito.Mockito.*;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by zhangmeng on 2015/11/8.
 */
public class HostBeanTest extends TestCase {

    private HostBean hostBean;
    private String hostName;
    private List<String>  IPAddressList;
    private String macAddress;
    private String nodeID;
    private String connectorID;
    @Before
    public void setUp() throws Exception {
        hostBean = new HostBean();
        hostName = null;
        IPAddressList = new LinkedList<String>();
        macAddress = null;
        nodeID = null;
        connectorID = null;
    }

    @Test
    public void testGetHostName() throws Exception {
        Assert.assertNull(hostBean.getHostName());
        hostName = "test";
        hostBean.setHostName(hostName);
        Assert.assertEquals("test",hostBean.getHostName());
        hostName = null;
    }

    @Test
    public void testSetHostName() throws Exception {
        Assert.assertNull(hostBean.getHostName());
        hostName = "test";
        hostBean.setHostName(hostName);
        Assert.assertEquals("test",hostBean.getHostName());
    }

    @Test
    public void testGetIPAddressList() throws Exception {
        Assert.assertNull(hostBean.getIPAddressList());
        IPAddressList.add("test");
        hostBean.setStringList(IPAddressList);
        Assert.assertNotNull(hostBean.getIPAddressList());
        IPAddressList.clear();
    }

    @Test
    public void testSetStringList() throws Exception {
        Assert.assertNull(hostBean.getIPAddressList());
        IPAddressList.add("test");
        hostBean.setStringList(IPAddressList);
        Assert.assertNotNull(hostBean.getIPAddressList());
    }

    @Test
    public void testGetMacAddress() throws Exception {
        Assert.assertNull(hostBean.getMacAddress());
        macAddress = "test";
        hostBean.setMacAddress(macAddress);
        Assert.assertEquals("test",hostBean.getMacAddress());
        macAddress = null;
    }

    @Test
    public void testSetMacAddress() throws Exception {
        Assert.assertNull(hostBean.getMacAddress());
        macAddress = "test";
        hostBean.setMacAddress(macAddress);
        Assert.assertEquals("test",hostBean.getMacAddress());
    }

    @Test
    public void testGetNodeID() throws Exception {
        Assert.assertNull(hostBean.getNodeID());
        nodeID = "test";
        hostBean.setNodeID(nodeID);
        Assert.assertEquals("test",hostBean.getNodeID());
        nodeID = null;
    }

    @Test
    public void testSetNodeID() throws Exception {
        Assert.assertNull(hostBean.getNodeID());
        nodeID = "test";
        hostBean.setNodeID(nodeID);
        Assert.assertEquals("test",hostBean.getNodeID());
    }

    @Test
    public void testGetConnectorID() throws Exception {
        Assert.assertNull(hostBean.getConnectorID());
        connectorID = "test";
        hostBean.setConnectorID(connectorID);
        Assert.assertEquals("test",hostBean.getConnectorID());
        connectorID = null;
    }

    @Test
    public void testSetConnectorID() throws Exception {
        Assert.assertNull(hostBean.getConnectorID());
        connectorID = "test";
        hostBean.setConnectorID(connectorID);
        Assert.assertEquals("test",hostBean.getConnectorID());
    }
}