package org.opendaylight.nemo.intent.algorithm;
import edu.uci.ics.jung.algorithms.filters.EdgePredicateFilter;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.EdgeType;
import org.apache.commons.collections15.Predicate;
import org.apache.commons.collections15.Transformer;
import org.opendaylight.nemo.intent.algorithm.*;


import java.util.Collection;
import java.util.List;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

/**
 * Created by zhangmeng on 2015/11/6.
 */
public class RoutingAlgorithmTest extends TestCase {
    private Graph<Vertex,Edge> graph;
    private DijkstraShortestPath<Vertex, Edge> dijkstraShortestPath;
    private RoutingAlgorithm routingAlgorithm;

    @Before
    public void setUp() throws Exception {
        graph = mock(DirectedSparseGraph.class);
        dijkstraShortestPath = mock(DijkstraShortestPath.class);
        routingAlgorithm = mock(RoutingAlgorithm.class);
    }

    @Test
    public void testGetVertex() throws Exception {
        Vertex vertex = routingAlgorithm.getVertex(null);
        Assert.assertNull(vertex);
    }

    @Test
    public void testGetEdge() throws Exception {
        Edge edge = routingAlgorithm.getEdge(null);
        Assert.assertNull(edge);
    }

    @Test
    public void testGetVertices() throws Exception {
        Collection<Vertex> collection = routingAlgorithm.getVertices();
        Assert.assertNotNull(collection);
    }

    @Test
    public void testAddVertex() throws Exception {
        Vertex vertex = mock(Vertex.class);
        Assert.assertNotNull(vertex);
        routingAlgorithm.addVertex(vertex);
        verify(routingAlgorithm).addVertex(vertex);
        Assert.assertNotNull(routingAlgorithm);
    }

    @Test
    public void testAddEdge() throws Exception {
        Edge edge = mock(Edge.class);
        Assert.assertNotNull(edge);
        routingAlgorithm.addEdge(edge);
        verify(routingAlgorithm).addEdge(edge);
        Assert.assertNotNull(routingAlgorithm);
    }


    @Test
    public void testRemoveVertex() throws Exception {
        String flag = "test";
        routingAlgorithm.removeVertex(flag);
        Assert.assertNotNull(routingAlgorithm);
        verify(routingAlgorithm).removeVertex(flag);
    }

    @Test
    public void testRemoveEdge() throws Exception {
        String flag = "test";
        routingAlgorithm.removeEdge(flag);
        Assert.assertNotNull(routingAlgorithm);
        verify(routingAlgorithm).removeEdge(flag);
    }

    @Test
    public void testComputePath() throws Exception {
        List<Edge> list = mock(List.class);
        list = routingAlgorithm.computePath(mock(Vertex.class),mock(Vertex.class));
        Assert.assertNotNull(list);
    }

    @Test
    public void testComputePath1() throws Exception {
        List<Edge> list = mock(List.class);
        list = routingAlgorithm.computePath(mock(Vertex.class),mock(Vertex.class));
        Assert.assertNotNull(list);
    }

    @Test
    public void testToString() throws Exception {
        Assert.assertNotNull(routingAlgorithm.toString());
    }
}