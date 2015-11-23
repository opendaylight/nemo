package org.opendaylight.nemo.intent.algorithm;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by zhangmeng on 2015/11/5.
 */
public class VertexTest extends TestCase {
    private Vertex vertex;
    private String id;
    @Before
    public void setUp() throws Exception {
        vertex = new Vertex(id);
    }

    @Test
    public void testGetId() throws Exception {
        Assert.assertNull(vertex.getId());
        id = "test";
        vertex = new Vertex(id);
        Assert.assertEquals(new String("test"),vertex.getId());
    }

    @Test
        public void testEquals() throws Exception {
        //Vertex obj = new Vertex(null);
        id = "test";
        vertex = new Vertex(id);
        Assert.assertTrue(new String("test").equals(vertex.getId()));
    }

    @Test
    public void testToString() throws Exception {
        Assert.assertNotNull(vertex.toString());
    }
}