import java.util.Scanner;

public class Main {

    // 25 7
    // Player1 wins

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] input = scanner.nextLine().split(" ");
        int number1 = Integer.parseInt(input[0]);
        int number2 = Integer.parseInt(input[1]);
        EuclidsGame euclidsGame = new EuclidsGame(number1, number2);
        euclidsGame.perfectGame();
    }

}
