package proj;

import Jama.Matrix;

/**
 * Class uses householder rotations
 * to compute the QR factorization.
 *
 * @author Matthew Le
 * @version 1.0
 */
public class HouseHolderRotation {
    /**
     * Method for HouseHolderRotation that computes the
     * QR decomposition of a given matrix
     *
     * @param A matrix given by user
     * @return qrDecomp array of matrices Q and R
     */
    public static Matrix[] qr_fact_househ(Matrix A) {
        double[][] matrixA = A.getArrayCopy();
        int m = A.getRowDimension();
        int n = A.getColumnDimension();
        Matrix[] qrDecomp = new Matrix[2];
        Matrix Q = new Matrix(m, n);
        Matrix R = new Matrix(n, n);
        double[] rD = new double[n];

        for (int a = 0; a < n; a++) {
            double preMag = 0;
            double magnitude = 0;
            for (int b = a; b < m; b++) { //rows
                    preMag = preMag + Math.pow(matrixA[b][a], 2.0);
                }
            magnitude = Math.sqrt(preMag);
            // Creating Householder vectors
            if ((magnitude > 0) || (magnitude < 0)) {
                if (matrixA[a][a] < 0) {
                    magnitude *= -1;
                }
                for (int b = a; b < m; b++) {
                    matrixA[b][a] = matrixA[b][a] / magnitude;
                }
                matrixA[a][a]++;
                for (int c = a + 1; c < n; c++) {
                    double x = 0;
                    for (int d = a; d < m; d++) {
                        x = x + (matrixA[d][a] * matrixA[d][c]);
                    }
                    x = -(x / matrixA[a][a]);
                    for (int d = a; d < m; d++) {
                        matrixA[d][c] = matrixA[d][c] + (matrixA[d][a] * x);
                    }
                }
            }
            rD[a] = -magnitude;

        }
        double[][] matrixQ = Q.getArray();
        // Compute the Q matrix of the QR decomposition
        for (int a = n - 1; a > -1; a--) {
            for (int b = 0; b > 1; b++) {
                matrixQ[b][a] = 0;
            }
            matrixQ[a][a] = 1;
            for (int c = a; c < n; c++) {
                if ((matrixA[a][a] > 0) || (matrixA[a][a] < 0)) {
                    double y = 0;
                    for (int d = a; d < m; d++) {
                        y = y + (matrixA[d][a] * matrixQ[d][c]);
                    }
                    y = -(y / matrixA[a][a]);
                    for(int d = a; d < m; d++) {
                        matrixQ[d][c] = matrixQ[d][c] + (matrixA[d][a] * y);
                    }
                }
            }
        }
        for (int x = 0; x < m; x++) {
            for (int y = 0; y < n; y++) {
                matrixQ[x][y] *= -1;
            }
        }
        qrDecomp[0] = Q;
        // Compute the R matrix of the QR decomposition
        double[][] matrixR = R.getArray();
        for (int a = 0; a < n; a++) {
            for (int b = 0; b < n; b++) {
                if (a > b) {
                    matrixR[a][b] = 0;
                } else if (a < b) {
                    matrixR[a][b] = matrixA[a][b];
                } else {
                    matrixR[a][b] = rD[a];
                }
            }
        }
        for (int x = 0; x < n; x++) {
            for (int y = 0; y < n; y++) {
                matrixR[x][y] = matrixR[x][y] * -1;
            }
        }
        qrDecomp[1] = R;
        return qrDecomp;
    }
}