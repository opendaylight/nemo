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

import static org.junit.Assert.*;
import edu.uci.ics.jung.algorithms.filters.EdgePredicateFilter;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.EdgeType;
import org.apache.commons.collections15.Predicate;
import org.apache.commons.collections15.Transformer;
import org.opendaylight.nemo.intent.algorithm.Edge;
import org.opendaylight.nemo.intent.algorithm.RoutingAlgorithm;
import org.opendaylight.nemo.intent.algorithm.Vertex;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import static org.mockito.Mockito.*;
/**
 * Created by zhangmeng on 2015/11/25.
 */
public class RoutingAlgorithmTest extends TestCase {
    private RoutingAlgorithm routingAlgorithm;
    @Before
    public void setUp() throws Exception {
        routingAlgorithm = new RoutingAlgorithm();
    }

    @Test
    public void testVertexAndEdge() throws Exception {
        //test get Vertices
        Collection<Vertex> collection ;
        collection = routingAlgorithm.getVertices();
        Assert.assertEquals(true, collection.isEmpty());

        //add vertex
        Vertex vertex = new Vertex("test");
        Vertex vertex1 = new Vertex("test1");
        Vertex vertex2 = new Vertex("test2");
        Vertex vertex3 = new Vertex("test3");
        routingAlgorithm.addVertex(vertex);
        routingAlgorithm.addVertex(vertex1);
        routingAlgorithm.addVertex(vertex2);
        routingAlgorithm.addVertex(vertex3);

        //test get vertex
        Vertex vertex_test = routingAlgorithm.getVertex("null");
        Assert.assertNull(vertex_test);
        vertex_test = routingAlgorithm.getVertex("test");
        Assert.assertNotNull(vertex1);

        //add edge
        Edge edge = new Edge("edge","test","test1",1,1);
        Edge edge1 = new Edge("edge1","test2","test3",1,1);
        Edge edge2 = new Edge("edge2","test1","test2",1,1);
        routingAlgorithm.addEdge(edge);
        routingAlgorithm.addEdge(edge1);
        routingAlgorithm.addEdge(edge2);

        //test get edge
        Edge edge_test = routingAlgorithm.getEdge("null");
        Assert.assertNull(edge_test);
        edge_test = routingAlgorithm.getEdge("edge");
        Assert.assertNotNull(edge_test);
        edge_test = routingAlgorithm.getEdge("edge1");
        Assert.assertNotNull(edge_test);

        //test get Vertices
        collection = routingAlgorithm.getVertices();
        Assert.assertEquals(4,collection.size());

        //test update edge
        edge = new Edge("edge","test","test1",0,0);
        routingAlgorithm.updateEdge(edge);
        edge_test = routingAlgorithm.getEdge("edge");
        Assert.assertEquals(0,edge_test.getMetric());
        Assert.assertEquals(0,edge_test.getBandwidth());

        //test computePath
        List<Edge> path = new LinkedList<Edge>();
        Assert.assertEquals(0,path.size());
        path = routingAlgorithm.computePath(vertex,vertex1);
        Assert.assertEquals(1, path.size());
        path.clear();
        path = routingAlgorithm.computePath(vertex1,vertex3,1);
        Assert.assertEquals(2,path.size());

        //test remove
        //remove edge
        routingAlgorithm.removeEdge("null");
        edge_test = routingAlgorithm.getEdge("edge");
        Assert.assertNotNull(edge_test);
        routingAlgorithm.removeEdge("edge");
        edge_test = routingAlgorithm.getEdge("edge");
        Assert.assertNull(edge_test);

        //remove Vertex
        routingAlgorithm.removeVertex("null");
        vertex_test = routingAlgorithm.getVertex("test");
        Assert.assertNotNull(vertex_test);
        routingAlgorithm.removeVertex("test");
        vertex_test = routingAlgorithm.getVertex("test");
        Assert.assertNull(vertex_test);

        //test toString
        String s = new String();
        Assert.assertEquals(0,s.length());
        s = routingAlgorithm.toString();
        Assert.assertTrue(s.length() > 1);

    }
    
}