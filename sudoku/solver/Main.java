package sudoku.solver;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        ArrayList<String> inputList = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            if (!scanner.hasNextLine()) {
                break;
            }
            inputList.add(scanner.nextLine());
        }
        initSudoku(inputList);
    }

    private static void initSudoku(ArrayList<String> inputList) {
        Cell[][] cells = new Cell[9][9];
        for (int i = 0; i < inputList.size(); i++) {
            for (int j = 0; j < inputList.get(i).length(); j++) {
                cells[i][j] = new Cell(Integer.parseInt(inputList.get(i).charAt(j)+ ""), new Point2D(i, j));
            }
        }
        Grid grid = new Grid(cells);
        Sudoku sudoku = new Sudoku(grid);
        sudoku.solve();
        System.out.println(sudoku.printResult());
    }

}
