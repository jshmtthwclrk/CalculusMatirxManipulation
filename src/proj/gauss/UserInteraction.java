package proj.gauss;

import Jama.Matrix;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserInteraction {
    public static int getIterationNum(Scanner scanner) {
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

    public static List<Matrix> getDataPoints(Scanner scanner) {
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
                    if (line.matches("\\s*\\-?\\d+(\\.\\d*)?\\s*,\\s*\\-?\\d+(\\.\\d*)?\\s*")) {
                        String[] point = line.replace(" ", "").split(",");
                        Matrix dataPoint = new Matrix(2, 1);
                        dataPoint.set(0, 0, Double.parseDouble(point[0]));
                        dataPoint.set(1, 0, Double.parseDouble(point[1]));
                        dataPoints.add(dataPoint);
                    } else if (!line.matches("^\\s*$")) {
                        file = null;
                        break;
                    }
                }
            } catch (Exception e) {}
        } while (file == null || (!file.exists() || file.isDirectory() || dataPoints.size() < 1));
        return dataPoints;
    }

    public static Matrix getUserGuessPoints(Scanner scanner) {
        String input;
        Matrix guesses = null;
        do {
            System.out.print("Guesses of coefficients > ");
            input = scanner.nextLine();
            if (input.matches("\\(\\s*\\-?\\d+(\\.\\d+)?\\s*,\\s*\\-?\\d+(\\.\\d+)?\\s*,\\s*\\-?\\d+(\\.\\d+)?\\s*\\)")) {
                String[] guessesString = input.replace("(", "").replace(")", "").replace(" ", "").split(",");
                guesses = new Matrix(3, 1);
                for (int i = 0; i < 3; i++) {
                    guesses.set(i, 0, Double.parseDouble(guessesString[i]));
                }
            }
        } while (guesses == null);
        return guesses;
    }
}
