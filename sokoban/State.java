package sokoban;

import java.util.HashSet;

public class State {

    /**
     *
     * @param player One player
     * @param boxes Multiple boxes
     */
    public State(Point2D player, HashSet<Point2D> boxes) {
        this.player = player;
        this.boxes = boxes;
    }

    public Point2D getPlayer() {
        return player;
    }

    public HashSet<Point2D> getBoxes() {
        return boxes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        State state = (State) o;

        if (player != null ? !player.equals(state.player) : state.player != null) return false;
        return boxes != null ? boxes.equals(state.boxes) : state.boxes == null;
    }

    @Override
    public int hashCode() {
        int result = player != null ? player.hashCode() : 0;
        result = 31 * result + (boxes != null ? boxes.hashCode() : 0);
        return result;
    }

    Point2D player;
    HashSet<Point2D> boxes;

}
