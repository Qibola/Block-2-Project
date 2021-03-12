package sudoku.solver;

import java.util.Arrays;

public class Grid {

    public Grid(Cell[][] cells) {
        this.cells = cells;
    }

    public Cell getCell(int x, int y) {
        return cells[x][y];
    }

    public Cell[][] getCells() {
        return cells;
    }

    @Override
    public String toString() {
        return "Grid{" +
                "cells=" + Arrays.toString(cells) +
                '}';
    }

    private Cell[][] cells;

}
