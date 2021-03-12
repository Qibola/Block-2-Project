package sudoku.solver;

/**
 * Arc (represents a constraint, edge of a CSP)
 */
public class Arc {

    public Arc(Cell x, Cell y) {
        this.x = x;
        this.y = y;
    }

    public Cell getX() {
        return x;
    }

    public Cell getY() {
        return y;
    }

    private Cell x;
    private Cell y;

}
