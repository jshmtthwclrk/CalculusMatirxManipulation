package proj.gauss;

import Jama.Matrix;

import java.util.List;
import java.util.Scanner;

public class LogarithmicGaussNewton extends GaussNewton {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Matrix> dataPoints = UserInteraction.getDataPoints(scanner);
        Matrix guesses = UserInteraction.getUserGuessPoints(scanner);
        int iterations = UserInteraction.getIterationNum(scanner);
        LogarithmicGaussNewton logarithmicGaussNewton = new LogarithmicGaussNewton();
        Matrix result = logarithmicGaussNewton.performInefficientGaussNewton(dataPoints, guesses, iterations);
        System.out.println("The coefficients for the best approximation are (" + result.get(0, 0) + ", " + result.get(1, 0) + ", " + result.get(2, 0) + ")");
    }
}
