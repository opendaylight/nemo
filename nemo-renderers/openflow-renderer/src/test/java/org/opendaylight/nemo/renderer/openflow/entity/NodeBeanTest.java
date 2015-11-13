package org.opendaylight.nemo.renderer.openflow.entity;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.opendaylight.nemo.renderer.openflow.entity.NodeBean;
import org.opendaylight.nemo.renderer.openflow.entity.PortBean;
import org.junit.*;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by zhangmeng on 2015/11/8.
 */
public class NodeBeanTest extends TestCase {

    private NodeBean nodeBean;
    private String nodeID;
    private String nodeType;
    private String nodeCapacity;
    private List<PortBean> portList;
    @Before
    public void setUp() throws Exception {
        nodeBean = new NodeBean();
        nodeID = nodeType = nodeCapacity = null;
        portList = new LinkedList<PortBean>();
    }

    @Test
    public void testGetNodeID() throws Exception {
        Assert.assertNull(nodeBean.getNodeID());
        nodeID = "test";
        nodeBean.setNodeID(nodeID);
        Assert.assertEquals(nodeID,nodeBean.getNodeID());
        nodeID = null;
    }

    @Test
    public void testSetNodeID() throws Exception {
        Assert.assertNull(nodeBean.getNodeID());
        nodeID = "test";
        nodeBean.setNodeID(nodeID);
        Assert.assertEquals(nodeID,nodeBean.getNodeID());
    }

    @Test
    public void testGetNodeType() throws Exception {
        Assert.assertNull(nodeBean.getNodeType());
        nodeType = "test";
        nodeBean.setNodeType(nodeType);
        Assert.assertEquals(nodeType,nodeBean.getNodeType());
        nodeType = null;
    }

    @Test
    public void testSetNodeType() throws Exception {
        Assert.assertNull(nodeBean.getNodeType());
        nodeType = "test";
        nodeBean.setNodeType(nodeType);
        Assert.assertEquals(nodeType,nodeBean.getNodeType());
    }

    @Test
    public void testGetNodeCapacity() throws Exception {
        Assert.assertNull(nodeBean.getNodeCapacity());
        nodeCapacity = "test";
        nodeBean.setNodeCapacity(nodeCapacity);
        Assert.assertEquals(nodeCapacity,nodeBean.getNodeCapacity());
        nodeCapacity = null;
    }

    @Test
    public void testSetNodeCapacity() throws Exception {
        Assert.assertNull(nodeBean.getNodeCapacity());
        nodeCapacity = "test";
        nodeBean.setNodeCapacity(nodeCapacity);
        Assert.assertEquals(nodeCapacity,nodeBean.getNodeCapacity());
    }

    @Test
    public void testGetPortList() throws Exception {
        Assert.assertNull(nodeBean.getPortList());
        portList.add(new PortBean());
        nodeBean.setPortList(portList);
        Assert.assertNotNull(nodeBean.getPortList());
        portList.clear();
    }

    @Test
    public void testSetPortList() throws Exception {
        Assert.assertNull(nodeBean.getPortList());
        portList.add(new PortBean());
        nodeBean.setPortList(portList);
        Assert.assertNotNull(nodeBean.getPortList());
    }
}