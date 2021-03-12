package sudoku.solver;

import java.util.List;
import java.util.Map;

/**
 * A boolean, map tuple
 * boolean: if a given problem is solvable
 * map: the removed domain values within a cell
 */
public class AC3Tuple {

    public AC3Tuple(boolean solvable, Map<Cell, List<Integer>> removedDomains) {
        this.solvable = solvable;
        this.removedDomains = removedDomains;
    }

    public boolean isSolvable() {
        return solvable;
    }

    public Map<Cell, List<Integer>> getRemovedDomains() {
        return removedDomains;
    }

    private boolean solvable;
    private Map<Cell, List<Integer>> removedDomains;

}
