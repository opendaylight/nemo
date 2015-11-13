package org.opendaylight.nemo.renderer.openflow.entity;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.*;
import org.opendaylight.nemo.renderer.openflow.entity.PortBean;
import java.util.LinkedList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by zhangmeng on 2015/11/8.
 */
public class PortBeanTest extends TestCase {
    private PortBean portBean;
    private String portID;
    private String portIPAddress;
    private String mask;
    private String location;
    private String bandwidth;
    @Before
    public void setUp() throws Exception {
        portBean = new PortBean();
        portID = portIPAddress = mask = location = bandwidth = null;
    }

    @Test
    public void testGetPortID() throws Exception {
        Assert.assertNull(portBean.getPortID());
        portID = "test";
        portBean.setPortID(portID);
        Assert.assertEquals(portID,portBean.getPortID());
        portID = null;
    }

    @Test
    public void testSetPortID() throws Exception {
        Assert.assertNull(portBean.getPortID());
        portID = "test";
        portBean.setPortID(portID);
        Assert.assertEquals(portID,portBean.getPortID());
    }

    @Test
    public void testGetPortIPAddress() throws Exception {
        Assert.assertNull(portBean.getPortIPAddress());
        portIPAddress = "test";
        portBean.setPortIPAddress(portIPAddress);
        Assert.assertEquals(portIPAddress,portBean.getPortIPAddress());
        portIPAddress = null;
    }

    @Test
    public void testSetPortIPAddress() throws Exception {
        Assert.assertNull(portBean.getPortIPAddress());
        portIPAddress = "test";
        portBean.setPortIPAddress(portIPAddress);
        Assert.assertEquals(portIPAddress,portBean.getPortIPAddress());
    }

    @Test
    public void testGetMask() throws Exception {
        Assert.assertNull(portBean.getMask());
        mask = "test";
        portBean.setMask(mask);
        Assert.assertEquals(mask,portBean.getMask());
        mask = null;
    }

    @Test
    public void testSetMask() throws Exception {
        Assert.assertNull(portBean.getMask());
        mask = "test";
        portBean.setMask(mask);
        Assert.assertEquals(mask,portBean.getMask());
    }

    @Test
    public void testGetLocation() throws Exception {
        Assert.assertNull(portBean.getLocation());
        location = "test";
        portBean.setLocation(location);
        Assert.assertEquals(location,portBean.getLocation());
        location = null;
    }

    @Test
    public void testSetLocation() throws Exception {
        Assert.assertNull(portBean.getLocation());
        location = "test";
        portBean.setLocation(location);
        Assert.assertEquals(location,portBean.getLocation());
    }

    @Test
    public void testGetBandwidth() throws Exception {
        Assert.assertNull(portBean.getBandwidth());
        bandwidth = "test";
        portBean.setBandwidth(bandwidth);
        Assert.assertEquals(bandwidth,portBean.getBandwidth());
        bandwidth = null;
    }

    @Test
    public void testSetBandwidth() throws Exception {
        Assert.assertNull(portBean.getBandwidth());
        bandwidth = "test";
        portBean.setBandwidth(bandwidth);
        Assert.assertEquals(bandwidth,portBean.getBandwidth());
    }
}