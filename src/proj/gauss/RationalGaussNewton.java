package proj.gauss;

import Jama.Matrix;

import java.util.List;
import java.util.Scanner;

/**
 * A class containing the functionality to perform the rational Gauss-Newton
 */
public class RationalGaussNewton extends GaussNewton {
    public static void main(String[] args) {
        RationalGaussNewton.gn_rat();
    }

    /**
     * Runs the rational Gauss-Newton
     */
    public static void gn_rat() {
        Scanner scanner = new Scanner(System.in);
        List<Matrix> dataPoints = UserInteraction.getDataPoints(scanner);
        Matrix guesses = UserInteraction.getUserGuessPoints(scanner);
        int iterations = UserInteraction.getIterationNum(scanner);
        RationalGaussNewton rationalGaussNewton = new RationalGaussNewton();
        Matrix result = rationalGaussNewton.performModifiedGaussNewton(dataPoints, guesses, iterations);
        System.out.println("The coefficients for the best approximation of a rational function are (" + result.get(0, 0) + ", " + result.get(1, 0) + ", " + result.get(2, 0) + ")");
    }
}
