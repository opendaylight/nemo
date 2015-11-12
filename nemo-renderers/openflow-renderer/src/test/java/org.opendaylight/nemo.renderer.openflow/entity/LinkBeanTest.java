package org.opendaylight.nemo.renderer.openflow.entity;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.*;
import java.util.LinkedList;
import org.opendaylight.nemo.renderer.openflow.entity.LinkBean;
import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

/**
 * Created by zhangmeng on 2015/11/8.
 */
public class LinkBeanTest extends TestCase {
    private LinkBean linkBean;

    private String linkID;
    private String leftNodeID;
    private String leftPortID;
    private String rightPortID;
    private String rightNodeID;
    private String linkBandwidth;

    @Before
    public void setUp() throws Exception {
        linkBean = new LinkBean();
        linkID = leftNodeID = leftPortID = rightPortID = rightNodeID = linkBandwidth = null;
    }

    @Test
    public void testGetLinkID() throws Exception {
        Assert.assertNull(linkBean.getLinkID());
        linkID = "test";
        linkBean.setLinkID(linkID);
        Assert.assertEquals(linkID,linkBean.getLinkID());
        linkID = null;
    }

    @Test
    public void testSetLinkID() throws Exception {
        Assert.assertNull(linkBean.getLinkID());
        linkID = "test";
        linkBean.setLinkID(linkID);
        Assert.assertEquals(linkID,linkBean.getLinkID());
    }

    @Test
    public void testGetLeftNodeID() throws Exception {
        Assert.assertNull(linkBean.getLeftNodeID());
        leftNodeID = "test";
        linkBean.setLeftNodeID(leftNodeID);
        Assert.assertEquals(leftNodeID,linkBean.getLeftNodeID());
        leftNodeID = null;
    }

    @Test
    public void testSetLeftNodeID() throws Exception {
        Assert.assertNull(linkBean.getLeftNodeID());
        leftNodeID = "test";
        linkBean.setLeftNodeID(leftNodeID);
        Assert.assertEquals(leftNodeID,linkBean.getLeftNodeID());
    }

    @Test
    public void testGetLeftPortID() throws Exception {
        Assert.assertNull(linkBean.getLeftPortID());
        leftPortID = "test";
        linkBean.setLeftPortID(leftPortID);
        Assert.assertEquals(leftPortID,linkBean.getLeftPortID());
        leftPortID = null;
    }

    @Test
    public void testSetLeftPortID() throws Exception {
        Assert.assertNull(linkBean.getLeftPortID());
        leftPortID = "test";
        linkBean.setLeftPortID(leftPortID);
        Assert.assertEquals(leftPortID,linkBean.getLeftPortID());
    }

    @Test
    public void testGetRightNodeID() throws Exception {
        Assert.assertNull(linkBean.getRightNodeID());
        rightNodeID = "test";
        linkBean.setRightNodeID(rightNodeID);
        Assert.assertEquals(rightNodeID,linkBean.getRightNodeID());
        rightNodeID = null;
    }

    @Test
    public void testSetRightNodeID() throws Exception {
        Assert.assertNull(linkBean.getRightNodeID());
        rightNodeID = "test";
        linkBean.setRightNodeID(rightNodeID);
        Assert.assertEquals(rightNodeID,linkBean.getRightNodeID());
    }

    @Test
    public void testGetRightPortID() throws Exception {
        Assert.assertNull(linkBean.getRightPortID());
        rightPortID = "test";
        linkBean.setRightPortID(rightPortID);
        Assert.assertEquals(rightPortID,linkBean.getRightPortID());
        rightPortID = null;
    }

    @Test
    public void testSetRightPortID() throws Exception {
        Assert.assertNull(linkBean.getRightPortID());
        rightPortID = "test";
        linkBean.setRightPortID(rightPortID);
        Assert.assertEquals(rightPortID,linkBean.getRightPortID());
    }

    @Test
    public void testGetLinkBandwidth() throws Exception {
        Assert.assertNull(linkBean.getLinkBandwidth());
        linkBandwidth = "test";
        linkBean.setLinkBandwidth(linkBandwidth);
        Assert.assertEquals(linkBandwidth,linkBean.getLinkBandwidth());
        linkBandwidth = null;
    }

    @Test
    public void testSetLinkBandwidth() throws Exception {
        Assert.assertNull(linkBean.getLinkBandwidth());
        linkBandwidth = "test";
        linkBean.setLinkBandwidth(linkBandwidth);
        Assert.assertEquals(linkBandwidth,linkBean.getLinkBandwidth());
    }
}