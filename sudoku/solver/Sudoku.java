package sudoku.solver;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Sudoku (represented as a Constraint Satisfaction Problem - a directed constraint graph)
 *
 * Problem formulation
 * Variables: { Cell1, ..., Cell81 }
 * Domain: {1,2,3,4,5,6,7,8,9}
 * Constraints: {[x,y].value != [:,y].value and != [x,:].value and != box([x,y]).value }
 */
public class Sudoku {

    public static final int GRID_SIZE = 9;
    public static final int BOX_SIZE = 3;

    public Sudoku(Grid grid) {
        this.grid = grid;
    }

    public void solve() {
        AC3Tuple result = ac3();
        if (!result.isSolvable()) {
            System.err.println("Sudoku is not solvable");
            return;
        }
        if (!isSolved()) {
            backtrackingSearch();
        }
    }

    public void backtrackingSearch() {
        boolean result = backtrackingSearch(false);
        if (result == false) {
            System.err.println("Sudoku is not solvable");
        }
    }

    public boolean backtrackingSearch(boolean solved) {
        if (isSolved()) {
            return true;
        }
        Cell cell = selectUnassignedVariable();
        // No domain value order => cell.getDomains()
        for (int value : cell.getDomains()) {
            // Clone old domains
            List<Integer> oldDomains = new LinkedList<>(cell.getDomains());
            // Assign value to cell
            cell.setDomain(value);
            // if assigned value is arc consistent
            AC3Tuple ac3Tuple = ac3();
            if (ac3Tuple.isSolvable()) {
                if (backtrackingSearch(solved)) {
                    return true;
                }
            }
            // Rollback
            for (Map.Entry<Cell, List<Integer>> cellDomain : ac3Tuple.getRemovedDomains().entrySet()) {
                cellDomain.getKey().addDomains(cellDomain.getValue());
            }
            cell.addDomains(oldDomains);
        }
        // Failure
        return false;
    }

    private Cell selectUnassignedVariable() {
        // Minimum Remaining Values strategy
        // Retrieves a cell with the least possibilities of assignments
        return Arrays.stream(grid.getCells())
                .flatMap(Stream::of)
                .filter(cell -> cell.getDomains().size() > 1)
                .min(Comparator.comparingInt(o -> o.getDomains().size()))
                .get();
    }

    /**
     * AC-3 Algorithm
     * @return true if sudoku is solvable.
     */
    private AC3Tuple ac3() {
        Queue<Arc> arcs = initArcs();
        Map<Cell, List<Integer>> removedDomains = new HashMap<>();
        while (!arcs.isEmpty()) {
            Arc arc = arcs.poll();
            if (arcReduce(arc.getX(), arc.getY(), removedDomains)) {
                if (arc.getX().getDomains().isEmpty()) {
                    return new AC3Tuple(false, removedDomains);
                } else {
                    addNeighborArcs(arc.getX(), arc.getY(), arcs);
                }
            }
        }
        return new AC3Tuple(true, removedDomains);
    }

    private boolean arcReduce(Cell x, Cell y, Map<Cell, List<Integer>> removedDomains) {
        boolean removed = false;
        // For each available domain
        for (int valueX : x.getDomains()) {
            // Doesn't satisfy any constraint
            if (!satisfiesConstraint(valueX, y)) {
                removed = true;
                // Add value of cell x to the map of removed domain values
                if (removedDomains.get(x) != null) {
                    removedDomains.get(x).add(valueX);
                } else {
                    LinkedList<Integer> domains = new LinkedList<>();
                    domains.add(valueX);
                    removedDomains.put(x, domains);
                }
            }
        }
        if (removedDomains.get(x) != null) {
            x.removeDomains(removedDomains.get(x));
        }
        return removed;
    }

    /**
     * Retrieve neighbor arcs of cell x excluding cell y
     * @param x Cell x
     * @param y Cell y
     * @return a list of arcs
     */
    private void addNeighborArcs(Cell x, Cell y, Queue<Arc> arcs) {
        int xRow = positionToBoxSize(x.getPosition().getX());
        int xColumn = positionToBoxSize(x.getPosition().getY());
        for (int i = 0; i < GRID_SIZE; i++) {
            int boxRow = xRow + i % BOX_SIZE;
            int boxColumn = xColumn + Math.floorDiv(i, BOX_SIZE);

            // Row arcs
            if (i != x.getPosition().getY() && grid.getCell(x.getPosition().getX(), i) != y) {
                arcs.add(new Arc(grid.getCell(x.getPosition().getX(), i), x));
            }
            // Column arcs
            if (i != x.getPosition().getX() && grid.getCell(i, x.getPosition().getY()) != y) {
                arcs.add(new Arc(grid.getCell(i, x.getPosition().getY()), x));
            }
            // Box arcs
            if (!(boxRow == x.getPosition().getX() && boxColumn == x.getPosition().getY()) &&
                    grid.getCell(boxRow, boxColumn) != y) {
                arcs.add(new Arc(grid.getCell(boxRow, boxColumn), x));
            }
        }
    }

    /**
     * Map value to box size
     */
    private int positionToBoxSize(int value) {
        return value - (value % BOX_SIZE);
    }

    private Queue<Arc> initArcs() {
        Queue<Arc> queue = new ArrayDeque<>();
        for (int rowColBox = 0; rowColBox < GRID_SIZE; rowColBox++) {

            int xBox = rowColBox % BOX_SIZE * BOX_SIZE;
            int yBox = rowColBox - (rowColBox % BOX_SIZE);
            for (int i = 0; i < GRID_SIZE - 1; i++) {
                int boxRowCurrent = xBox + i % BOX_SIZE;
                int boxColumnCurrent = yBox + Math.floorDiv(i, BOX_SIZE);
                for (int j = i + 1; j < GRID_SIZE; j++) {
                    int boxRow = xBox + j % BOX_SIZE;
                    int boxColumn = yBox + Math.floorDiv(j, BOX_SIZE);
                    // Row arcs
                    queue.add(new Arc(grid.getCell(rowColBox, i), grid.getCell(rowColBox, j)));
                    queue.add(new Arc(grid.getCell(rowColBox, j), grid.getCell(rowColBox, i)));
                    // Column arcs
                    queue.add(new Arc(grid.getCell(i, rowColBox), grid.getCell(j, rowColBox)));
                    queue.add(new Arc(grid.getCell(j, rowColBox), grid.getCell(i, rowColBox)));
                    // Box arcs
                    queue.add(new Arc(grid.getCell(boxRowCurrent, boxColumnCurrent), grid.getCell(boxRow, boxColumn)));
                    queue.add(new Arc(grid.getCell(boxRow, boxColumn), grid.getCell(boxRowCurrent, boxColumnCurrent)));
                }
            }
        }
        return queue;
    }

    /**
     * Constraint between x and y
     * @param valueX value of a cell
     * @param valueY value of a cell
     * @return
     */
    private boolean isDifferent(int valueX, int valueY) {
        return valueX != valueY;
    }

    /**
     * if no value y.getDomains() allows (x,y) to satisfy the constraint
     * @return
     */
    private boolean satisfiesConstraint(int valueX, Cell y) {
        boolean found = false;
        for (int valueY : y.getDomains()) {
            if (isDifferent(valueX, valueY)) {
                found = true;
                break;
            }
        }
        return found;
    }

    private boolean isSolved() {
        return Arrays.stream(grid.getCells())
                .flatMap(Stream::of)
                .filter(cell -> cell.getDomains().size() > 1)
                .count() == 0;
    }

    public String retrieveDomains() {
        StringBuilder cellNumbers = new StringBuilder();
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                cellNumbers.append(listToString(grid.getCell(i, j).getDomains()));
            }
            cellNumbers.append("\n");
        }
        return cellNumbers.toString();
    }

    public String printResult() {
        StringBuilder cellNumbers = new StringBuilder();
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                cellNumbers.append(grid.getCell(i, j).getDomains().get(0));
            }
            cellNumbers.append("\n");
        }
        return cellNumbers.toString();
    }

    private String listToString(List<Integer> list) {
        return list.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(",","{","}"));
    }

    private Grid grid;

}
