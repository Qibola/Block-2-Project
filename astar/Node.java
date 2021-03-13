package astar;

/**
 * Graph node
 * maintains states for the graph
 * and states for A*
 */
public class Node {

    public Node(int id, int heuristic) {
        this.id = id;
        this.heuristic = heuristic;
    }

    public int getHeuristic() {
        return heuristic;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        return id == node.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "Node{" +
                "heuristic=" + heuristic +
                ", id=" + id +
                '}';
    }

    // Immutable states
    private final int heuristic;
    private final int id;

}
