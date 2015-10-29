/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.nemo.intent.algorithm;

import edu.uci.ics.jung.algorithms.filters.EdgePredicateFilter;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.EdgeType;
import org.apache.commons.collections15.Predicate;
import org.apache.commons.collections15.Transformer;

import java.util.Collection;
import java.util.List;

/**
 * Implement the SPF and CSPF algorithms based on the Dijkstra's algorithm.
 *
 * @author Zhigang Ji
 */
public class RoutingAlgorithm {
    /**
     * The current network topology graph.
     */
    private Graph<Vertex, Edge> graph;

    /**
     * The Dijkstra shortest path algorithm instance.
     */
    private DijkstraShortestPath<Vertex, Edge> dijkstraShortestPath;

    public RoutingAlgorithm() {
        super();

        graph = new DirectedSparseGraph<Vertex, Edge>();
        dijkstraShortestPath = new DijkstraShortestPath<Vertex, Edge>(graph, new Transformer<Edge, Number>() {
            @Override
            public Number transform(Edge edge) {
                return edge.getMetric();
            }
        }, false);

        return;
    }

    /**
     * Get the vertex with the given id from the network topology graph.
     *
     * @param vertexId The given vertex id.
     * @return The vertex with the given id.
     */
    public Vertex getVertex(String vertexId) {
        for ( Vertex vertex : graph.getVertices() ) {
            if ( vertex.getId().equals(vertexId) ) {
                return vertex;
            }
        }

        return null;
    }

    /**
     * Get the edge with the given id from the network topology graph.
     *
     * @param edgeId The given edge id.
     * @return The edge with the given id.
     */
    public Edge getEdge(String edgeId) {
        for ( Edge edge : graph.getEdges() ) {
            if ( edge.getId().equals(edgeId) ) {
                return edge;
            }
        }

        return null;
    }

    /**
     * Get all vertices from the network topology graph.
     *
     * @return The collection of all vertices.
     */
    public Collection<Vertex> getVertices() {
        return graph.getVertices();
    }

    /**
     * Add a vertex into the network topology graph.
     *
     * @param vertex The vertex to be added.
     */
    public void addVertex(Vertex vertex) {
        graph.addVertex(vertex);

        return;
    }

    /**
     * Add an edge into the network topology graph.
     *
     * @param edge The edge to be added.
     */
    public void addEdge(Edge edge) {
        graph.addEdge(edge, getVertex(edge.getSrc()), getVertex(edge.getDest()), EdgeType.DIRECTED);

        return;
    }

    /**
     * Update an edge in the network topology graph with the new one.
     *
     * @param newEdge The given new edge.
     */
    public void updateEdge(Edge newEdge) {
        Edge edge = getEdge(newEdge.getId());

        edge.setMetric(newEdge.getMetric());
        edge.setBandwidth(newEdge.getBandwidth());

        return;
    }

    /**
     * Remove the vertex with the given id from the network topology graph.
     *
     * @param vertexId The given vertex id.
     */
    public void removeVertex(String vertexId) {
        Vertex vertex = getVertex(vertexId);

        if ( null != vertex ) {
            graph.removeVertex(vertex);
        }

        return;
    }

    /**
     * Remove the edge with the given id from the network topology graph.
     *
     * @param edgeId The given edge id.
     */
    public void removeEdge(String edgeId) {
        Edge edge = getEdge(edgeId);

        if ( null != edge ) {
            graph.removeEdge(edge);
        }

        return;
    }

    /**
     * Compute a shortest path from the given source vertex to target
     * one without any constraint.
     *
     * @param source The given source vertex.
     * @param target The given target vertex.
     * @return A list of the edges on the shortest path, in order of
     * their occurrence on this path.
     */
    public List<Edge> computePath(Vertex source, Vertex target) {
        return dijkstraShortestPath.getPath(source, target);
    }

    /**
     * Compute a shortest path with the given bandwidth from the given
     * source vertex to target one.
     *
     * @param source The given source vertex.
     * @param target The given target vertex.
     * @param bandwidth The given bandwidth for the path.
     * @return A list of the edges on the shortest path, in order of
     * their occurrence on this path.
     */
    public List<Edge> computePath(Vertex source, Vertex target, final long bandwidth) {
        EdgePredicateFilter<Vertex, Edge> edgeEdgePredicateFilter = new EdgePredicateFilter<Vertex, Edge>(new Predicate<Edge>() {
            @Override
            public boolean evaluate(Edge edge) {
                return edge.getBandwidth() >= bandwidth;
            }
        });

        Graph<Vertex, Edge> filteredGraph = edgeEdgePredicateFilter.transform(graph);
        DijkstraShortestPath<Vertex, Edge> tempDijkstraShortestPath = new DijkstraShortestPath<Vertex, Edge>(filteredGraph, new Transformer<Edge, Number>() {
            @Override
            public Number transform(Edge edge) {
                return edge.getMetric();
            }
        }, false);

        return tempDijkstraShortestPath.getPath(source, target);
    }

    @Override
    public String toString() {
        return "RoutingAlgorithm{" +
                "vertices=" + graph.getVertices() +
                ", edges=" + graph.getEdges() +
                '}';
    }
}
