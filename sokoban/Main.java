package sokoban;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        LinkedList<String> inputList = new LinkedList<>();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            if (!scanner.hasNextLine()) {
                break;
            }
            inputList.add(scanner.nextLine());
        }
        callSokoban(inputList);
    }

    private static void callSokoban(LinkedList<String> inputList) {
        int m = inputList.getFirst().length();
        int n = inputList.size();
        char[][] inputGrid = new char[n][m];

        Point2D player = new Point2D(1, 1);
        HashSet<Point2D> walls = new HashSet<>();
        HashSet<Point2D> boxes = new HashSet<>();
        HashSet<Point2D> goals = new HashSet<>();
        for (int i = 0; i < n; i++) {
            inputGrid[i] = inputList.pop().toCharArray();
            for (int j = 0; j < m; j++) {
                if (inputGrid[i][j] == '@') {
                    // i = row = y-axis
                    // j = column = x-axis
                    player = new Point2D(j, i);
                } else if (inputGrid[i][j] == '#') {
                    walls.add(new Point2D(j, i));
                } else if (inputGrid[i][j] == '$') {
                    boxes.add(new Point2D(j, i));
                } else if (inputGrid[i][j] == '.') {
                    goals.add(new Point2D(j, i));
                }
            }
        }
        Sokoban sokoban = new Sokoban(walls, player, boxes, goals);
        System.out.println(sokoban.bfsSequence());
    }

}
