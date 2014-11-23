package proj;

import Jama.Matrix;

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
        double determinant = 0;
        if (m.getRowDimension() != m.getColumnDimension()) {
            throw new RuntimeException("Matrix must be square to take the determinant");
        } else if (m.getRowDimension() == 1) {
            return m.get(0, 0);
        }
        for (int i = 0; i < m.getColumnDimension(); i++) {
            int sign = (i % 2 == 0) ? 1 : -1;
            determinant += m.get(0, i) * getDeterminant(getCrossedOutMatrix(m, 0, i)) * sign;
        }
        return determinant;
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
        double res = 0;
        for (int i = 0; i < a.getRowDimension(); i++) {
            res += a.get(i, 0) * b.get(i, 0);
        }
        return res;
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