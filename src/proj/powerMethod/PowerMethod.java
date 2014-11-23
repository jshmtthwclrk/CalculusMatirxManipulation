package proj.powerMethod;

import Jama.Matrix;
import proj.MatrixHelper;

import java.io.FileWriter;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * This class contains the power method functions
 */
public class PowerMethod {
    public static void main(String[] args) throws Exception {
        FileWriter matrixWriter = new FileWriter("matrix.csv");
        FileWriter inverseWriter = new FileWriter("inverse.csv");
        matrixWriter.append("Determinant,Trace,Iterations\n");
        inverseWriter.append("Determinant,Trace,Iterations\n");
        Matrix guess = new Matrix(new double [][]{new double[]{1},new double[]{1}});
        Matrix[] matrices = new Matrix[10000];
        int counter = 0;
        for (BigDecimal i = BigDecimal.ZERO; i.compareTo(new BigDecimal("10")) < 0; i = i.add(BigDecimal.ONE)) {
            for (BigDecimal j = BigDecimal.ZERO; j.compareTo(new BigDecimal("10")) < 0; j = j.add(BigDecimal.ONE)) {
                for (BigDecimal k = BigDecimal.ZERO; k.compareTo(new BigDecimal("10")) < 0; k = k.add(BigDecimal.ONE)) {
                    for (BigDecimal l = BigDecimal.ZERO; l.compareTo(new BigDecimal("10")) < 0; l = l.add(BigDecimal.ONE)) {
                        matrices[counter++] = new Matrix(new double[][]{new double[]{new BigDecimal("-2").add(new BigDecimal(".4").multiply(i)).doubleValue(), new BigDecimal("-2").add(new BigDecimal(".4").multiply(j)).doubleValue()}, new double[]{new BigDecimal("-2").add(new BigDecimal(".4").multiply(k)).doubleValue(), new BigDecimal("-2").add(new BigDecimal(".4").multiply(l)).doubleValue()}});
                    }
                }
            }
        }
        for (Matrix m : matrices) {
            PowerMethodOutput output = power_method(m, guess, .00005, 100);
            if (output != null) {
                matrixWriter.append(MatrixHelper.getDeterminant(m) + "," + output.eigenvalue + "," + output.iterations + "\n");
            }
            Matrix inverse = MatrixHelper.getInverse(m);
            if (inverse != null) {
                output = power_method(inverse, guess, .00005, 100);
                if (output != null) {
                    inverseWriter.append(MatrixHelper.getDeterminant(inverse) + "," + Math.abs(output.eigenvalue) + "," + output.iterations + "\n");
                }
            }
        }
        matrixWriter.flush();
        matrixWriter.close();
        inverseWriter.flush();
        inverseWriter.close();
    }

    /**
     * @param matrix to run the power method on
     * @param guess original guess of the eigenvector
     * @param tolerance that should be used when running the method
     * @param maxIterations to run
     * @return a powermethodoutput object containing an eigenvector, eigenvalue, and iterations used
     */
    public static PowerMethodOutput power_method(Matrix matrix, Matrix guess, double tolerance, int maxIterations) {
        if (matrix.getRowDimension() != matrix.getColumnDimension()) {
            throw new RuntimeException("Must be a square matrix to use the power method.");
        } else if (guess.getColumnDimension() != 1 || guess.getRowDimension() != matrix.getColumnDimension()) {
            throw new RuntimeException("The guess is not a correctly sized vector.");
        } else if (maxIterations < 2) {
            throw new RuntimeException("The maximum number of iterations must be more than 1.");
        }
        Matrix x = guess;
        Matrix prevX = x;
        BigDecimal eigenvalueEstimate = null;
        BigDecimal prevEigenvalueEstimate = null;
        BigDecimal error = null;
        int iterations = 0;
        while (iterations < maxIterations && (error == null || Math.abs(error.doubleValue()) > tolerance)) {
            iterations++;
            x = matrix.times(prevX);
            BigDecimal secondDotProd = new BigDecimal(MatrixHelper.dotProduct(guess, prevX));
            if (secondDotProd.equals(BigDecimal.ZERO)) {
                return null;
            }
            eigenvalueEstimate = BigDecimal.valueOf(MatrixHelper.dotProduct(guess, x)).divide(secondDotProd, MathContext.DECIMAL32);
            if (prevEigenvalueEstimate != null) {
                error = prevEigenvalueEstimate.subtract(eigenvalueEstimate);
            }
            prevX = x;
            prevEigenvalueEstimate = eigenvalueEstimate;
        }
        if (error != null && Math.abs(error.doubleValue()) <= tolerance) {
            PowerMethodOutput result = new PowerMethodOutput();
            result.iterations = iterations;
            result.eigenvalue = eigenvalueEstimate.doubleValue();
            result.eigenvector = MatrixHelper.norm(x);
            return result;
        } else {
            return null;
        }
    }

    public static class PowerMethodOutput {
        public double eigenvalue;
        public Matrix eigenvector;
        public int iterations;
    }
}
