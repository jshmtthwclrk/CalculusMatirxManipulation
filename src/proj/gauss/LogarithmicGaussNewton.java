package proj.gauss;

import Jama.Matrix;

import java.util.List;
import java.util.Scanner;

/**
 * A class containing the functionality to perform the logarithmic Gauss-Newton
 */
public class LogarithmicGaussNewton extends GaussNewton {
    public static void main(String[] args) {
        LogarithmicGaussNewton.gn_log();
    }

    /**
     * Runs the logarithmic Gauss-Newton
     */
    public static void gn_log() {
        Scanner scanner = new Scanner(System.in);
        List<Matrix> dataPoints = UserInteraction.getDataPoints(scanner);
        Matrix guesses = UserInteraction.getUserGuessPoints(scanner);
        int iterations = UserInteraction.getIterationNum(scanner);
        LogarithmicGaussNewton logarithmicGaussNewton = new LogarithmicGaussNewton();
        Matrix result = logarithmicGaussNewton.performModifiedGaussNewton(dataPoints, guesses, iterations);
        System.out.println("The coefficients for the best approximation of a logarithmic function are (" + result.get(0, 0) + ", " + result.get(1, 0) + ", " + result.get(2, 0) + ")");
    }
}
