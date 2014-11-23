package proj.gauss;

import Jama.Matrix;
import proj.MatrixHelper;

import java.util.List;

/**
 * An abstract class that performs Gauss Newton. Can perform both the precise
 * method (using householder or givens)
 * or the imprecise method (taking inverses)
 */
public abstract class GaussNewton {
    private List<Matrix> dataPoints;
    private Matrix bigBVector;

    /**
     * Performs Gauss Newton with modification. Uses QR factorization done using
     * Givens rotations.
     * @param dataPoints the list of data points being estimated
     * @param guesses the 3x1 matrix of guesses for initial A, B, and C values
     * @param iterations the number of times to perform the method
     * @return a 3x1 matrix containing the A, B, and C points for the
     *         equation corresponding to the type of Gauss Newton being
     *         performed
     */
    public Matrix performModifiedGaussNewton(List<Matrix> dataPoints, Matrix guesses, int iterations) {
        this.dataPoints = dataPoints;
        bigBVector = new Matrix(3, 1);
        bigBVector.set(0, 0, guesses.get(0, 0));
        bigBVector.set(1, 0, guesses.get(1, 0));
        bigBVector.set(2, 0, guesses.get(2, 0));
        for (int i = 0; i < iterations; i++) {
            Matrix rVector = getRVector();
            Matrix jacobi = getJacobianMatrix();
            Matrix[] qrMatrices = qr_fact_househ(jacobi);
            Matrix qMatrix = qrMatrices[0];
            Matrix rMatrix = qrMatrices[1];
            bigBVector = bigBVector.minus(MatrixHelper.getInverse(rMatrix).times(qMatrix.transpose()).times(rVector));
        }
        return bigBVector;
    }

    /**
     * Performs Gauss Newton without modification. Takes the inverse of matrices
     * which makes it imprecise.
     * @param dataPoints the list of data points being estimated
     * @param guesses the 3x1 matrix of guesses for initial A, B, and C values
     * @param iterations the number of times to perform the method
     * @return a 3x1 matrix containing the A, B, and C points for the
     *         equation corresponding to the type of Gauss Newton being
     *         performed
     */
    public Matrix performOriginalGaussNewton(List<Matrix> dataPoints, Matrix guesses, int iterations) {
        this.dataPoints = dataPoints;
        bigBVector = new Matrix(3, 1);
        bigBVector.set(0, 0, guesses.get(0, 0));
        bigBVector.set(1, 0, guesses.get(1, 0));
        bigBVector.set(2, 0, guesses.get(2, 0));
        for (int i = 0; i < iterations; i++) {
            Matrix rVector = getRVector();
            Matrix jacobi = getJacobianMatrix();
            bigBVector = bigBVector.minus(MatrixHelper.getInverse(jacobi.transpose().times(jacobi)).times(jacobi.transpose()).times(rVector));
        }
        return bigBVector;
    }

    /**
     * Gets the vector composed of residuals
     * @return an nx1 matrix where n is the number of data points
     */
    private Matrix getRVector() {
        Matrix rVector = new Matrix(dataPoints.size(), 1);
        for (int i = 0; i < dataPoints.size(); i++) {
            double yi = dataPoints.get(i).get(1, 0);
            double fResult = lineFunction(i);
            rVector.set(i, 0, yi - fResult);
        }
        return rVector;
    }

    /**
     * Gets the Jacobian matrix
     * @return an nx3 matrix where n is the number of data points
     */
    private Matrix getJacobianMatrix() {
        Matrix jacobian = new Matrix(dataPoints.size(), 3);
        for (int i = 0; i < dataPoints.size(); i++) {
            for (int j = 0; j < 3; j++) {
                jacobian.set(i, j, getJacobiCell(i, j));
            }
        }
        return jacobian;
    }

    /**
     * Performs the function associated with the type of line being estimated.
     * Uses the current B vector and the ith data point
     * @param i specifies which data point to use in calculating the result
     * @return an nx3 matrix where n is the number of data points
     */
    private double lineFunction(int i) {
        double a = bigBVector.get(0, 0);
        double b = bigBVector.get(1, 0);
        double c = bigBVector.get(2, 0);
        double x = dataPoints.get(i).get(0, 0);
        if (this instanceof QuadraticGaussNewton) {
            return a*x*x+b*x+c;
        } else if (this instanceof ExponentialGaussNewton) {
            return a*Math.pow(Math.E, b*x)+c;
        } else if (this instanceof LogarithmicGaussNewton) {
            return a*Math.log(x+b)+c;
        } else {
            return ((a*x)/(x+b))+c;
        }
    }

    /**
     * Performs the function associated with the type of line being estimated.
     * Uses the current B vector and the ith data point
     * @param i specifies which data point to use in calculating the result
     * @return an nx3 matrix where n is the number of data points
     */
    private double getJacobiCell(int i, int j) {
        double a = bigBVector.get(0, 0);
        double b = bigBVector.get(1, 0);
        double x = dataPoints.get(i).get(0, 0);
        switch (j) {
            case 0:
                if (this instanceof QuadraticGaussNewton) {
                    return -Math.pow(x, 2);
                } else if (this instanceof ExponentialGaussNewton) {
                    return -Math.pow(Math.E, b*x);
                } else if (this instanceof LogarithmicGaussNewton) {
                    return -Math.log(x+b);
                } else if (this instanceof RationalGaussNewton) {
                    return -x/(x+b);
                }
            case 1:
                if (this instanceof QuadraticGaussNewton) {
                    return -x;
                } else if (this instanceof ExponentialGaussNewton) {
                    return -a*x*Math.pow(Math.E, b*x);
                } else if (this instanceof LogarithmicGaussNewton) {
                    return -a/(x+b);
                } else if (this instanceof RationalGaussNewton) {
                    return (a*x)/Math.pow(b+x, 2);
                }
            case 2:
                if (this instanceof QuadraticGaussNewton) {
                    return -1;
                } else if (this instanceof ExponentialGaussNewton) {
                    return -1;
                } else if (this instanceof LogarithmicGaussNewton) {
                    return -1;
                } else if (this instanceof RationalGaussNewton) {
                    return -1;
                }
            default:
                return 0;
        }
    }

    //This function is just a placeholder
    private Matrix[] qr_fact_househ(Matrix jacobi) { //private Matrix[] HouseHolderRotation.qr_fact_househ(Matrix jacobi)
        return null;
    }
}