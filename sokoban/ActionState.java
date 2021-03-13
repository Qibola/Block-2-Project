package sokoban;

public class ActionState {

    public ActionState(Action action, State state) {
        this.action = action;
        this.state = state;
    }

    public Action getAction() {
        return action;
    }

    public State getState() {
        return state;
    }

    private Action action;
    private State state;

}
