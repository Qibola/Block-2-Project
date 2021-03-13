package sokoban;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class Problem {

    /**
     * World state separated into non-movable entities (walls, goals) and movable objects (player, boxes)
     * A node should only be interested in movable objects
     * @param immutableState A collection of Point2D (Location of the walls and goals)
     * @param state A state (Location of the player and boxes)
     */
    public Problem(final ImmutableState immutableState, State state) {
        this.immutableState = immutableState;
        this.state = state;
        this.visited = new HashSet<>();
    }

    public State getInitialState() {
        return this.state;
    }

    /**
     * Assumption goalState.length = state.boxes.length and different Point2D for multiple goalState
     * @param state
     * @return
     */
    public boolean goalTest(State state) {
        for (Point2D goal : immutableState.getGoals()) {
            if (!state.getBoxes().contains(goal)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Takes the search problem (this) and the state and returns a corresponding list
     * Successor state function which takes a state (usually from a node)
     * @param state A node's state
     * @return List of actions which are available and the respective successor state
     */
    public List<ActionState> getSuccesorStates(State state) {
        List<ActionState> actionStateList = new LinkedList<>();
        // Visited optimization
        if (visited.contains(state)) {
            return actionStateList;
        }

        ActionState actionState = push(state, Action.LEFT);
        if (actionState != null) {
            actionStateList.add(actionState);
        }
        actionState = push(state, Action.UP);
        if (actionState != null) {
            actionStateList.add(actionState);
        }
        actionState = push(state, Action.DOWN);
        if (actionState != null) {
            actionStateList.add(actionState);
        }
        actionState = push(state, Action.RIGHT);
        if (actionState != null) {
            actionStateList.add(actionState);
        }
        visited.add(state);
        return actionStateList;
    }

    private ActionState push(State state, Action action) {
        // Player position
        Point2D current = state.getPlayer();
        int xOffset = 0;
        int yOffset = 0;
        switch(action){
            case LEFT:
                // Box
                xOffset = -1;
                yOffset = 0;
                break;
            case UP:
                xOffset = 0;
                yOffset = -1;
                break;
            case DOWN:
                xOffset = 0;
                yOffset = 1;
                break;
            case RIGHT:
                xOffset = 1;
                yOffset = 0;
                break;
        }
        Point2D nextPos = new Point2D(current.getX() + xOffset, current.getY() + yOffset);
        Point2D next2Pos = new Point2D(current.getX() + xOffset * 2, current.getY() + yOffset * 2);
        if (!state.getBoxes().contains(nextPos) && !immutableState.getWalls().contains(nextPos)) {
            // Case 1: Nothing in front of player
            // Player's position has changed.
            State newState = new State(nextPos, state.getBoxes());
            return new ActionState(action, newState);
        } else if(state.getBoxes().contains(nextPos)) {
            // Case 2: Box in front of player
            if (!immutableState.getWalls().contains(next2Pos) && !state.getBoxes().contains(next2Pos)) {
                // Case 2.1: No obstacles behind a box
                HashSet<Point2D> newBoxes = (HashSet)state.getBoxes().clone();
                // Update the box's position
                newBoxes.remove(nextPos);
                newBoxes.add(next2Pos);
                // Player's position has changed.
                State newState = new State(nextPos, newBoxes);
                return new ActionState(action, newState);
            }
        }
        // Case 3: Box behind a wall/box, Player in front of a wall
        return null;
    }

    private ImmutableState immutableState;
    private State state;
    private HashSet<State> visited;

}
