package sokoban;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Sokoban {

    public Sokoban(HashSet<Point2D> walls, Point2D player, HashSet<Point2D> boxes, HashSet<Point2D> goals) {
        this.problem = new Problem(new ImmutableState(goals, walls), new State(player, boxes));
    }

    /**
     * A node contains the states - some description of the current world state (players, boxes)
     * A goal initial
     */
    public String bfsSequence() {
        // Action sequence (Actions a description of the actions available to the agent)
        // Create a node with an initial state
        Node initialNode = new Node(problem.getInitialState());
        // Fringe (nodes that haven't been expanded)
        Queue<Node> fringe = new LinkedList<>();
        fringe.add(initialNode);
        while (true) {
            if (fringe.isEmpty()) {
                // Return failure
                return "";
            }
            // Removes first inserted node
            Node node = fringe.poll();
            if (problem.goalTest(node.getState())) {
                // Return node sequence if the goal test was successful
                StringBuilder stringBuilder = new StringBuilder();
                for (Action action : node.getActions()) {
                    switch(action) {
                        case LEFT:
                            stringBuilder.append("L");
                            break;
                        case UP:
                            stringBuilder.append("U");
                            break;
                        case DOWN:
                            stringBuilder.append("D");
                            break;
                        case RIGHT:
                            stringBuilder.append("R");
                            break;
                    }
                }
                return stringBuilder.toString();
            }
            fringe.addAll(expand(node, problem));
        }
    }

    private List<Node> expand(Node node, Problem problem) {
        List<Node> successors = new LinkedList<>();
        // For each action based on the problem, the node's state
        List<ActionState> actionStateList = problem.getSuccesorStates(node.getState());
        for (ActionState actionState : actionStateList) {
            // Create a successor node
            // and apply the successor state + action
            LinkedList<Action> newActions = new LinkedList();
            newActions.addAll(node.getActions());
            newActions.add(actionState.getAction());
            Node sNode = new Node(node, actionState.getState(), newActions, node.getDepth() + 1);
            successors.add(sNode);
        }
        return successors;
    }

    private Problem problem;

}
