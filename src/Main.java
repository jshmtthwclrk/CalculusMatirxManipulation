import java.io.File;
import java.util.Scanner;

public class Main {
    private static Scanner scanner;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        File file = getFile();
        double[] guesses = getPoints();
        int iterations = getIterationNum();
    }

    private static int getIterationNum() {
        String input;
        int iterations = -1;
        do {
            System.out.print("Iterations > ");
            input = scanner.nextLine();
            if (input.matches("\\s*\\d+\\s*")) {
                iterations = Integer.parseInt(input.replace(" ", ""));
            }
        } while (iterations < 1);
        return iterations;
    }

    private static File getFile() {
        String input;
        File file = null;
        do {
            System.out.print("Text file with points > ");
            input = scanner.nextLine();
            try {
                file = new File(input);
            } catch (Exception e) {}
        } while (file != null && (!file.exists() || file.isDirectory()));
        return file;
    }

    private static double[] getPoints() {
        String input;
        double[] guesses = null;
        do {
            System.out.print("Guesses of coefficients > ");
            input = scanner.nextLine();
            if (input.matches("\\(\\s*\\-?\\d+(\\.\\d+)?\\s*,\\s*\\-?\\d+(\\.\\d+)?\\s*,\\s*\\-?\\d+(\\.\\d+)?\\s*\\)")) {
                String[] guessesString = input.replace("(", "").replace(")", "").replace(" ", "").split(",");
                guesses = new double[3];
                for (int i = 0; i < 3; i++) {
                    guesses[i] = Double.parseDouble(guessesString[i]);
                }
            }
        } while (guesses == null);
        return guesses;
    }
}