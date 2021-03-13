package astar;

/**
 * Unidirectional edge
 */
public class Edge {

    /**
     * @param node Destination node
     * @param costs Edge cost
     */
    public Edge(Node node, int costs) {
        this.node = node;
        this.cost = costs;
    }

    public Node getNode() {
        return node;
    }

    public int getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "node=" + node +
                ", cost=" + cost +
                '}';
    }

    private Node node;
    private int cost;
}
