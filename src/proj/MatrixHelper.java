package proj;

import Jama.Matrix;

public class MatrixHelper {
    public static double getDeterminant(Matrix m) {
        double determinant = 0;
        if (m.getRowDimension() != m.getColumnDimension()) {
            throw new RuntimeException("Matrix must be square to take the determinant");
        } else if (m.getRowDimension() == 1) {
            return m.get(0, 0);
        }
        for (int i = 0; i < m.getColumnDimension(); i++) {
            int sign = (i % 2 == 0) ? 1 : -1;
            determinant += sign * m.get(0, i) * getDeterminant(getCrossedOutMatrix(m, 0, i));
        }
        return determinant;
    }

    public static Matrix getInverse(Matrix matrix) {
        Matrix cofactoredMatrix = getCofactor(matrix);
        return cofactoredMatrix.transpose().times(1/getDeterminant(matrix));
    }

    public static Matrix getCofactor(Matrix m) {
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

    private static Matrix getCrossedOutMatrix(Matrix m, int x, int y) {
        Matrix result = new Matrix(m.getRowDimension()-1, m.getColumnDimension()-1);
        int rowPos = -1;
        for (int i = 0; i < m.getRowDimension(); i++) {
            if (i != x) {
                rowPos++;
                int colPos = -1;
                for (int j = 0; j < m.getColumnDimension(); j++) {
                    if (j != y) {
                        result.set(rowPos, ++colPos, m.get(i, j));
                    }
                }
            }
        }
        return result;
    }
}
