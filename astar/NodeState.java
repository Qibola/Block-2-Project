package astar;

import java.util.LinkedList;
import java.util.List;

public class NodeState {

    public NodeState(Node node) {
        this.f = Integer.MAX_VALUE;
        this.g = Integer.MAX_VALUE;
        this.node = node;
        this.nodeList = new LinkedList<>();
    }

    public NodeState(Node node, LinkedList<Node> nodeList) {
        this.f = Integer.MAX_VALUE;
        this.g = Integer.MAX_VALUE;
        this.node = node;
        this.nodeList = nodeList;
    }

    public Node getNode() {
        return node;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getF() {
        return f;
    }

    public void setF(int f) {
        this.f = f;
    }

    public LinkedList<Node> getNodeList() {
        return nodeList;
    }

    private int g;
    private int f;
    private Node node;
    private LinkedList<Node> nodeList;

}
