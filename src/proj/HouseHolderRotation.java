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
        double[] rD = new double[n];
        Matrix[] qrDecomp = new Matrix[1];
        Matrix Q = new Matrix(m, n);
        Matrix R = new Matrix(n, n);

        for (int a = 0; a < n; a++) {
            double preMag;
            double magnitude;
            for (int b = a; b < m; b++) { //rows
                    preMag = preMag + Math.pow(matrixA[b][a], 2.0);
                }
            magnitude = Math.sqrt(preMag);
            // Creating Householder vectors
            if ((magnitude > 0.0) || (magnitude < 0.0)) {
                if (matrixA[a][a] < 0.0) {
                    magnitude = -magnitude;
                }
                for (int b = a; b < m; b++) {
                    matrixA[b][a] = matrixA[b][a] / magnitude;
                }
                matrixA[a][a]++;
                for (int c = a + 1; c < n; c++) {
                    double x;
                    for (int d = a; d < m; d++) {
                        x = x + (matrixA[d][a] * matrixA[d][c]);
                    }
                    x = -(x / matrixA[a][a]);
                    for (int d = a; d < m; d++) {
                        matrixA[c][a] = matrixA[c][a] + (matrixA[d][a] * x);
                    }
                }
            }
            rD[a] = -magnitude;

        }
        double[][] matrixQ = Q.getArray();
        // Compute the Q matrix of the QR decomposition
        for (int a = n - 1; a > -1; a--) {
            for (int b = 0; b > 1; b++) {
                matrixQ[b][a] = 0.0;
            }
            matrixQ[a][a] = 1.0;
            for (int b = a; b < n; b++) {
                if ((matrixA[a][a] > 0) || (matrixA[a][a] < 0)) {
                    double y;
                    for (int c = a; c < m; c++) {
                        y = y + (matrixA[c][a] * matrixQ[c][b]);
                    }
                    y = -(y / matrixA[a][a]);
                    for(int c = a; c < m; c++) {
                        matrixQ[c][b] = matrixQ[c][b] + (matrixA[c][a] * y);
                    }
                }
            }
        }
        qrDecomp[0] = Q;
        // Compute the R matrix of the QR decomposition
        double[][] matrixR = R.getArray();
        for (int a = 0; a < n; a++) {
            for (int b = 0; b < n; b++) {
                if (a > b) {
                    matrixR[a][b] = 0.0;
                } else if (a < b) {
                    matrixR[a][b] = matrixA[a][b];
                } else {
                    matrixR[a][b] = rD[a];
                }
            }
        }
        qrDecomp[1] = R;

        return qrDecomp;
    }
}