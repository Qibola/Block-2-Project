package sokoban;

import java.util.HashSet;

public final class ImmutableState {

    /**
     *
     * @param goals Multiple goals
     * @param walls Multiple walls
     */
    public ImmutableState(final HashSet<Point2D> goals, final HashSet<Point2D> walls) {
        this.goals = goals;
        this.walls = walls;
    }

    public HashSet<Point2D> getGoals() {
        return goals;
    }

    public HashSet<Point2D> getWalls() {
        return walls;
    }

    private final HashSet<Point2D> goals;
    private final HashSet<Point2D> walls;

}
