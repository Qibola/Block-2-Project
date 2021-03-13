package astar;

import java.util.*;

public class Astar {

    /**
     * State (graph, initial node, goal node)
     * @param graph Immutable state graph
     * @param initialNode
     * @param goalNode
     */
    public Astar(Graph graph, Node initialNode, Node goalNode) {
        this.graph = graph;
        this.initialNode = initialNode;
        this.goalNode = goalNode;
        this.closed = new ArrayList<>();
        this.fringe = new LinkedList<>();
    }

    public void calculatePath() {
        // Create a node with an initial state
        // Fringe (nodes that haven't been expanded, PriorityQueue)
        // add initial node
        // f = g + h = h, costs (g) = 0
        NodeState initialNode = new NodeState(this.initialNode);
        initialNode.setF(this.initialNode.getHeuristic());
        initialNode.setG(0);
        fringe.add(initialNode);
        while (!fringe.isEmpty()) {
            // Remove lowest f-value
            NodeState node = fringe.poll();
            // Goal test
            if (node.getNode().getId() == goalNode.getId()) {
                StringBuilder stringBuilder = new StringBuilder();
                for (Node previousNode : node.getNodeList()) {
                    stringBuilder.append(previousNode.getId() + "\n");
                }
                stringBuilder.append(node.getNode().getId());
                System.out.println(node.getNode().getId());
                //  stringBuilder.toString()
                return;
            }
            System.out.println(node.getNode().getId());
            expand(node);
            // Expansion done, add node to closed
            closed.add(node.getNode());
        }
    }

    /**
     * expand fringe's priority queue
     * Problem's successor function + state update
     * @param node
     */
    private void expand(NodeState node) {
        // Successor function
        // For each neighbor
        for (Edge edge : graph.getEdges(node.getNode().getId())) {
            Node successor = edge.getNode();
            // if we've visited the neighbor of node
            if (!closed.contains(successor)) {
                // Actual costs (accumulated) g(x) = previous g(x) + actual edge cost
                int provisionalG = node.getG() + edge.getCost();
                int f = provisionalG + successor.getHeuristic();
                LinkedList<Node> nodeList = (LinkedList<Node>) node.getNodeList().clone();
                nodeList.add(node.getNode());
                NodeState successorNodeState = new NodeState(successor, nodeList);
                successorNodeState.setG(provisionalG);
                successorNodeState.setF(f);
                fringe.add(successorNodeState);
            }
        }
        Collections.sort(fringe, Comparator.comparingInt(NodeState::getF));
    }

    // States
    private Graph graph;
    private Node initialNode;
    private Node goalNode;
    // Close and fringe
    private ArrayList<Node> closed;
    private LinkedList<NodeState> fringe;

}
