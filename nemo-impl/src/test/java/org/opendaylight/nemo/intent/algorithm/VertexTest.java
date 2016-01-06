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
	Vertex vertex1 = new Vertex("test1");	
        Assert.assertTrue(vertex1.equals(vertex1));
	Assert.assertFalse(vertex1.equals(vertex));    
     }

    @Test
    public void testToString() throws Exception {
        Assert.assertNotNull(vertex.toString());
    }
}