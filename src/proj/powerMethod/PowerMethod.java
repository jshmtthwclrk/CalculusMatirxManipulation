package proj.powerMethod;

import Jama.Matrix;
import proj.MatrixHelper;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class PowerMethod {
    public static void main(String[] args) throws Exception {
        FileWriter matrixWriter = new FileWriter("matrix.csv");
        FileWriter inverseWriter = new FileWriter("inverse.csv");
        matrixWriter.append("Determinant,Trace,Iterations\n");
        inverseWriter.append("Determinant,Trace,Iterations\n");
        Matrix guess = new Matrix(new double [][]{new double[]{1},new double[]{1}});
        List<Matrix> matrices = new ArrayList<Matrix>();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                for (int k = 0; k < 10; k++) {
                    for (int l = 0; l < 10; l++) {
                        matrices.add(new Matrix(new double[][]{new double[]{-2+.4*i, -2+.4*j}, new double[]{-2+.4*k, -2+.4*l}}));
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
                output = power_method(m, guess, .00005, 100);
                if (output != null) {
                    inverseWriter.append(MatrixHelper.getDeterminant(inverse) + "," + output.eigenvalue + "," + output.iterations + "\n");
                }
            }
        }
        matrixWriter.flush();
        matrixWriter.close();
        inverseWriter.flush();
        inverseWriter.close();
    }

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
        Double eigenvalueEstimate = null;
        Double prevEigenvalueEstimate = null;
        Double error = null;
        int iterations = 0;
        while (iterations < maxIterations && (error == null || Math.abs(error) > tolerance)) {
            iterations++;
            x = matrix.times(prevX);
            eigenvalueEstimate = MatrixHelper.dotProduct(guess, x) / MatrixHelper.dotProduct(guess, prevX);
            if (prevEigenvalueEstimate != null) {
                error = prevEigenvalueEstimate - eigenvalueEstimate;
            }
            prevX = x;
            prevEigenvalueEstimate = eigenvalueEstimate;
        }
        if (error != null && Math.abs(error) <= tolerance) {
            PowerMethodOutput result = new PowerMethodOutput();
            result.iterations = iterations;
            result.eigenvalue = eigenvalueEstimate;
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
