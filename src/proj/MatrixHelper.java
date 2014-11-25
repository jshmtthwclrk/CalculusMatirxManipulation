package proj;

import Jama.Matrix;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * A helper class with basic matrix methods
 */
public class MatrixHelper {
    /**
     * Recursively computes the determinant of the matrix
     * @param m the matrix to compute the determinant
     * @return the determinant
     */
    public static double getDeterminant(Matrix m) {
        BigDecimal determinant = BigDecimal.ZERO;
        if (m.getRowDimension() != m.getColumnDimension()) {
            throw new RuntimeException("Matrix must be square to take the determinant");
        } else if (m.getRowDimension() == 1) {
            return m.get(0, 0);
        }
        for (int i = 0; i < m.getColumnDimension(); i++) {
            int sign = (i % 2 == 0) ? 1 : -1;
            determinant = determinant.add(BigDecimal.valueOf(m.get(0, i)).multiply(BigDecimal.valueOf(getDeterminant(getCrossedOutMatrix(m, 0, i)))).multiply(BigDecimal.valueOf(sign)));
        }
        return determinant.doubleValue();
    }

    /**
     * Multiplies two matrices together
     * @param a first matrix
     * @param b second matrix
     * @return the two matrices multiplied together
     */
    public static Matrix multiply(Matrix a, Matrix b) {
        if (a.getColumnDimension() != b.getRowDimension()) {
            throw new RuntimeException("These matrices can't be multiplied. Incorrect dimensions.");
        }
        Matrix output = new Matrix(a.getRowDimension(), b.getColumnDimension());
        for (int k = 0; k < a.getRowDimension(); k++) {
            for (int i = 0; i < b.getColumnDimension(); i++) {
                BigDecimal sum = BigDecimal.ZERO;
                for (int j = 0; j < a.getColumnDimension(); j++) {
                    sum = sum.add(BigDecimal.valueOf(a.get(k, j)).multiply(BigDecimal.valueOf(b.get(j, i))));
                }
                output.set(k, i, sum.doubleValue());
            }
        }
        return output;
    }

    /**
     * Solves the system of equations using the upper triangular matrix
     * @param upperTriangular matrix
     * @param b
     * @return the answer to the problem
     */
    public static Matrix solveWithUpperTriangular(Matrix upperTriangular, Matrix b) {
        Matrix output = new Matrix(b.getRowDimension(), 1);
        for (int i = b.getRowDimension() - 1; i >= 0; i--) {
            BigDecimal number = BigDecimal.ZERO;
            for (int j = b.getRowDimension() - 1; j > i; j--) {
                number = number.add(BigDecimal.valueOf(output.get(j, 0)).multiply(BigDecimal.valueOf(upperTriangular.get(i, j))));
            }
            output.set(i, 0, BigDecimal.valueOf(b.get(i, 0)).subtract(number).divide(BigDecimal.valueOf(upperTriangular.get(i, i)), MathContext.DECIMAL32).doubleValue());
        }
        return output;
    }

    /**
     * Finds the trace of the matrix
     * @param m matrix to find the trace of
     * @return trace of the matrix
     */
    public static double getTrace(Matrix m) {
        if (m.getColumnDimension() != m.getRowDimension()) {
            throw new RuntimeException("Can't calculate trace of non-square matrix.");
        }
        BigDecimal sum = BigDecimal.ZERO;
        for (int i = 0; i < m.getColumnDimension(); i++) {
            sum = sum.add(BigDecimal.valueOf(m.get(i, i)));
        }
        return sum.doubleValue();
    }

    /**
     * Calculates the inverse of the matrix by using the cofactor matrix and
     * the determinant
     * @param m the matrix to compute the inverse
     * @return the inverse
     */
    public static Matrix getInverse(Matrix m) {
        Matrix cofactoredMatrix = getCofactor(m);
        double determinant = getDeterminant(m);
        if (determinant == 0) {
            return null;
        }
        return cofactoredMatrix.transpose().times(1 / determinant);
    }

    /**
     * Calculates the cofactor of a matrix
     * @param m the matrix to compute the cofactor
     * @return the cofactor
     */
    private static Matrix getCofactor(Matrix m) {
        Matrix result = new Matrix(m.getRowDimension(), m.getColumnDimension());
        for (int i = 0; i < m.getRowDimension(); i++) {
            int signOne = (i % 2 == 0) ? 1 : -1;
            for (int j = 0; j < m.getColumnDimension(); j++) {
                int signTwo = (j % 2 == 0) ? 1 : -1;
                result.set(i, j, signOne*signTwo*getDeterminant(getCrossedOutMatrix(m, i, j)));
            }
        }
        return result;
    }

    /**
     * Calculates the portion of the matrix remaining after crossing out the xth
     * row and yth column
     * @param m the matrix to cross out
     * @return the crossed out matrix
     */
    private static Matrix getCrossedOutMatrix(Matrix m, int x, int y) {
        Matrix result = new Matrix(m.getRowDimension()-1, m.getColumnDimension()-1);
        int rowPos = -1;
        for (int i = 0; i < m.getRowDimension(); i++) {
            if (i != x) {
                int colPos = -1;
                rowPos++;
                for (int j = 0; j < m.getColumnDimension(); j++) {
                    if (j != y) {
                        colPos++;
                        result.set(rowPos, colPos, m.get(i, j));
                    }
                }
            }
        }
        return result;
    }

    /**
     * Calculates the dot product of two nx1 matrices (vectors)
     * @param a the first nx1 matrix (vector)
     * @param b the second nx1 matrix (vector)
     * @return the dot product
     */
    public static double dotProduct(Matrix a, Matrix b) {
        if (a.getColumnDimension() != 1 || b.getColumnDimension() != 1 || a.getRowDimension() != b.getRowDimension()) {
            throw new RuntimeException("Must be vectors of the same dimension to find the dot product.");
        }
        BigDecimal res = BigDecimal.ZERO;
        for (int i = 0; i < a.getRowDimension(); i++) {
            res = res.add(BigDecimal.valueOf(a.get(i, 0)).multiply(BigDecimal.valueOf(b.get(i, 0))));
        }
        return res.doubleValue();
    }

    /**
     * Calculates the norm of an nx1 matrix (vector)
     * @param m the nx1 matrix (vector)
     * @return the norm
     */
    public static Matrix norm(Matrix m) {
        return m.times(1/Math.sqrt(dotProduct(m, m)));
    }
}