package proj.gauss;

import Jama.Matrix;

import java.util.List;
import java.util.Scanner;

/**
 * A class containing the functionality to perform the quadratic Gauss-Newton
 */
public class QuadraticGaussNewton extends GaussNewton {
    public static void main(String[] args) {
        QuadraticGaussNewton.gn_qua();
    }

    /**
     * Runs the quadratic Gauss-Newton
     */
    public static void gn_qua() {
        Scanner scanner = new Scanner(System.in);
        List<Matrix> dataPoints = UserInteraction.getDataPoints(scanner);
        Matrix guesses = UserInteraction.getUserGuessPoints(scanner);
        int iterations = UserInteraction.getIterationNum(scanner);
        QuadraticGaussNewton quadraticGaussNewton = new QuadraticGaussNewton();
        Matrix result = quadraticGaussNewton.performOriginalGaussNewton(dataPoints, guesses, iterations);
        System.out.println("The coefficients for the best approximation of a quadratic function are (" + result.get(0, 0) + ", " + result.get(1, 0) + ", " + result.get(2, 0) + ")");
    }
}