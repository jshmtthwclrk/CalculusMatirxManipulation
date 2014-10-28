import Jama.Matrix;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner scanner;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        List<Matrix> dataPoints = getDataPoints();
        double[] guesses = getUserGuessPoints();
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

    private static List<Matrix> getDataPoints() {
        String input;
        File file = null;
        List<Matrix> dataPoints = new ArrayList<Matrix>();
        do {
            System.out.print("Text file with points > ");
            input = scanner.nextLine();
            try {
                file = new File(input);
                Scanner fileScanner = new Scanner(file);
                while (fileScanner.hasNext()) {
                    String line = fileScanner.nextLine();
                    if (line.matches("\\s*\\-?\\d+(\\.\\d+)?\\s*,\\s*\\-?\\d+(\\.\\d+)?\\s*")) {
                        String[] point = line.replace(" ", "").split(",");
                        dataPoints.add(new Matrix(new double[][] {new double[] {Double.parseDouble(point[0]), Double.parseDouble(point[1])}}));
                    } else {
                        file = null;
                        break;
                    }
                }
            } catch (Exception e) {}
        } while (file != null && (!file.exists() || file.isDirectory()));
        return dataPoints;
    }

    private static double[] getUserGuessPoints() {
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