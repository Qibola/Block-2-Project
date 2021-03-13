package sokoban;

import java.util.LinkedList;
import java.util.List;

public class Node {

    public Node(State state) {
        this.state = state;
        this.parent = null;
        this.pathCost = 0;
        this.stepCost = 0;
        this.depth = 0;
        this.action = null;
        this.actions = new LinkedList<>();
    }

    public Node(Node parent, State state, Action action, int depth) {
        this.state = state;
        this.parent = parent;
        this.pathCost = 0;
        this.stepCost = 0;
        this.depth = depth;
        this.action = action;
    }

    public Node(Node parent, State state, List<Action> actions, int depth) {
        this.state = state;
        this.parent = parent;
        this.pathCost = 0;
        this.stepCost = 0;
        this.depth = depth;
        this.actions = actions;
    }

    public State getState() {
        return state;
    }

    public Node getParent() {
        return parent;
    }

    public Action getAction() {
        return action;
    }

    public List<Action> getActions() {
        return actions;
    }

    public int getDepth() {
        return depth;
    }

    // World's state
    private State state;
    // labyrinth.Node's state
    private Action action;
    private List<Action> actions;
    private Node parent;
    private int pathCost;
    private int stepCost;
    private int depth;

}
