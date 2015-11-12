package org.opendaylight.nemo.renderer.openflow.entity;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.opendaylight.nemo.renderer.openflow.entity.HostBean;
import org.opendaylight.nemo.renderer.openflow.entity.LinkBean;
import org.opendaylight.nemo.renderer.openflow.entity.NodeBean;
import org.opendaylight.nemo.renderer.openflow.entity.ResourceBean;
import java.util.LinkedList;

import java.util.List;

import static org.junit.Assert.*;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
/**
 * Created by zhangmeng on 2015/11/8.
 */
public class ResourceBeanTest extends TestCase {

    private ResourceBean resourceBean;
    private List<NodeBean> nodelist;
    private List<LinkBean> linklist;
    private List<HostBean> hostlist;

    @Before
    public void setUp() throws Exception {
        resourceBean = new ResourceBean();
        nodelist = new LinkedList<NodeBean>();
        linklist = new LinkedList<LinkBean>();
        hostlist = new LinkedList<HostBean>();
    }

    @Test
    public void testGetNodelist() throws Exception {
        Assert.assertNull(resourceBean.getNodelist());
        nodelist.add(new NodeBean());
        resourceBean.setNodelist(nodelist);
        Assert.assertNotNull(resourceBean.getNodelist());
        nodelist.clear();
    }

    @Test
    public void testSetNodelist() throws Exception {
        Assert.assertNull(resourceBean.getNodelist());
        nodelist.add(new NodeBean());
        resourceBean.setNodelist(nodelist);
        Assert.assertNotNull(resourceBean.getNodelist());
    }

    @Test
    public void testGetLinklist() throws Exception {
        Assert.assertNull(resourceBean.getLinklist());
        linklist.add(new LinkBean());
        resourceBean.setLinklist(linklist);
        Assert.assertNotNull(resourceBean.getLinklist());
        linklist.clear();
    }

    @Test
    public void testSetLinklist() throws Exception {
        Assert.assertNull(resourceBean.getLinklist());
        linklist.add(new LinkBean());
        resourceBean.setLinklist(linklist);
        Assert.assertNotNull(resourceBean.getLinklist());
    }

    @Test
    public void testGetHostlist() throws Exception {
        Assert.assertNull(resourceBean.getHostlist());
        hostlist.add(new HostBean());
        resourceBean.setHostlist(hostlist);
        Assert.assertNotNull(resourceBean.getHostlist());
        hostlist.clear();
    }

    @Test
    public void testSetHostlist() throws Exception {
        Assert.assertNull(resourceBean.getHostlist());
        hostlist.add(new HostBean());
        resourceBean.setHostlist(hostlist);
        Assert.assertNotNull(resourceBean.getHostlist());
    }
}