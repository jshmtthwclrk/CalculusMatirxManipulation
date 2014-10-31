package proj.gauss;

import Jama.Matrix;
import proj.MatrixHelper;

import java.util.List;

public abstract class GaussNewton {
    private List<Matrix> dataPoints;
    private Matrix bigBVector;

    public Matrix performEfficientGaussNewton(List<Matrix> dataPoints, Matrix guesses, int iterations) {
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

    public Matrix performInefficientGaussNewton(List<Matrix> dataPoints, Matrix guesses, int iterations) {
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

    private Matrix getRVector() {
        Matrix rVector = new Matrix(dataPoints.size(), 1);
        for (int i = 0; i < dataPoints.size(); i++) {
            double yi = dataPoints.get(i).get(1, 0);
            double fResult = lineFunction(i);
            rVector.set(i, 0, yi - fResult);
        }
        return rVector;
    }

    private Matrix getJacobianMatrix() {
        Matrix jacobian = new Matrix(dataPoints.size(), 3);
        for (int i = 0; i < dataPoints.size(); i++) {
            for (int j = 0; j < 3; j++) {
                jacobian.set(i, j, getJacobiCell(i, j));
            }
        }
        return jacobian;
    }

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
    private Matrix[] qr_fact_househ(Matrix jacobi) {
        return null;
    }
}