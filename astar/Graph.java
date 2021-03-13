package astar;

import java.util.*;

public class Graph {

    private Node[] nodes;
    private List<Edge>[] adjacentList;

    /**
     * Creates a graph and maintains a fixed length of nodes
     * @param n Number of nodes
     */
    public Graph(int n) {
        nodes = new Node[n];
        adjacentList = new LinkedList[n];
    }

    /**
     * Add a new node
     * @param nodeId Index of a node
     * @param heuristic heuristic
     */
    public void addNode(int nodeId, int heuristic) {
        Node node = new Node(nodeId, heuristic);
        nodes[nodeId] = node;
        adjacentList[nodeId] = new LinkedList<>();
    }

    public void addEdge(int nodeSrcId, int nodeDestId, int costs) {
        adjacentList[nodeSrcId].add(new Edge(nodes[nodeDestId], costs));
    }

    public Node getNode(int nodeId) {
        return nodes[nodeId];
    }

    public List<Edge> getEdges(int nodeId) {
        return adjacentList[nodeId];
    }

    @Override
    public String toString() {
        return "Graph{" +
                "adjacentList=" + adjacentList +
                '}';
    }
}
