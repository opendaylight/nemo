/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.intent.algorithm;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by zhangmeng on 2015/11/5.
 */
public class EdgeTest extends TestCase {

    private String id;
    private String src;
    private String dest;
    private long metric;
    private long bandwidth;
    private Edge edge;
    @Before
    public void setUp() throws Exception {
        id = null;
        src = null;
        dest = null;
        //metric = 0;
        //bandwidth = 0;
        //edge = new Edge(id,src,dest,metric,bandwidth);
    }

    @Test
    public void testGetId() throws Exception {
        edge = new Edge(id,src,dest,metric,bandwidth);
        Assert.assertEquals(null,edge.getId());
        id = "test";
        edge = new Edge(id,src,dest,metric,bandwidth);
        Assert.assertEquals(new String("test"),edge.getId());
    }

    @Test
    public void testGetSrc() throws Exception {
        edge = new Edge(id,src,dest,metric,bandwidth);
        Assert.assertEquals(null,edge.getSrc());
        src = "test";
        edge = new Edge(id,src,dest,metric,bandwidth);
        Assert.assertEquals(new String("test"),edge.getSrc());
    }

    @Test
    public void testGetDest() throws Exception {
        edge = new Edge(id,src,dest,metric,bandwidth);
        Assert.assertEquals(null,edge.getDest());
        dest = "test";
        edge = new Edge(id,src,dest,metric,bandwidth);
        Assert.assertEquals(new String("test"),edge.getDest());
    }

    @Test
    public void testGetMetric() throws Exception {
        edge = new Edge(id,src,dest,metric,bandwidth);
        Assert.assertEquals(0,edge.getMetric());
        metric = 1;
        edge = new Edge(id,src,dest,metric,bandwidth);
        Assert.assertEquals(1,edge.getMetric());
    }

    @Test
    public void testGetBandwidth() throws Exception {
        edge = new Edge(id,src,dest,metric,bandwidth);
        Assert.assertEquals(0,edge.getBandwidth());
        bandwidth = 1;
        edge = new Edge(id,src,dest,metric,bandwidth);
        Assert.assertEquals(1,edge.getBandwidth());
    }

    @Test
    public void testSetMetric() throws Exception {
        edge = new Edge(id,src,dest,metric,bandwidth);
        Assert.assertEquals(0,edge.getMetric());
        metric = 1;
        edge.setMetric(metric);
        Assert.assertEquals(metric,edge.getMetric());
    }

    @Test
    public void testSetBandwidth() throws Exception {
        edge = new Edge(id,src,dest,metric,bandwidth);
        Assert.assertEquals(0,edge.getBandwidth());
        bandwidth = 1;
        edge.setBandwidth(1);
        Assert.assertEquals(bandwidth,edge.getBandwidth());
    }

    @Test
    public void testEquals() throws Exception {
        String s = new String("tests");
        Edge obj = new Edge(s,s,s,1,1);

        id = src = dest = s;
        metric = bandwidth = 1;
        edge = new Edge(id,src,dest,metric,bandwidth);

        Edge edge1= new Edge("test1","test1","test1",1,1);

        Assert.assertTrue(obj.getId().equals(edge.getId()));
        Assert.assertTrue(obj.getSrc().equals(edge.getSrc()));
        Assert.assertTrue(obj.getDest().equals(edge.getDest()));
        Assert.assertTrue(obj.equals(obj));
        Assert.assertTrue(!obj.equals(edge1));


    }

    @Test
    public void testToString() throws Exception {
	edge = new Edge(id,src,dest,metric,bandwidth);
        Assert.assertNotNull(edge.toString());

    }
}
