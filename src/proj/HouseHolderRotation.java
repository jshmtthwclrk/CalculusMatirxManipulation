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

    public static Matrix[] qr_fact_househ(Matrix A) {

        double[][] matrixA = A.getArrayCopy();
        int m = A.getRowDimension();
        int n = A.getColumnDimension();
        double[] rD = new double[n];
        Matrix[] qrFactorization = new Matrix[1];
        Matrix Q = new Matrix(m, n);
        Matrix R = new Matrix(n, n);

        for (int a = 0; a < n; a++) { //columns
            double preMag;
            double magnitude;
            for (int b = a; b < m; b++) { //rows
                    preMag = preMag + Math.pow(matrixA[b][a], 2.0);
                }
            magnitude = Math.sqrt(preMag);
            if ((magnitude > 0.0) || (magnitude < 0.0)) {
                if (matrixA[a][a] < 0) {
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
        qrFactorization[0] = Q;

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
        qrFactorization[1] = R;

        return qrFactorization;
    }
}
/*            if (matrixA[i][i] > 0) {
                vectorMag = -vectorMag; //finding beta
            }

            matrixA[i][i] = matrixA[i][i] - vectorMag; //u~ = a1 - beta
            double uMag = 0; //magnitude of ~u
            for (int j = 0; j < m; j++) {
                uMag = Math.hypot(uMag, matrixA[j][i]);
            }

            for (int j = 0; j < m; j++) {
                matrixA[j][i] = matrixA[j][i] / uMag; //finding u by u~ / ~u mag. btw havent found tilde u (a1 - beta(I))
            }
            matrixA[i][i] = matrixA[i][i] + 1; //not sure what this does
            // still need to do H1 = I - 2uuT*/