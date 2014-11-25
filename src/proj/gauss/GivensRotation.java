package proj.gauss;

import Jama.Matrix;
import proj.MatrixHelper;

/**
 * Created by jshmtthwclrk on 10/29/14.
 *
 * Class representing a Givens Rotation
 *
 * @author Joshua Clark
 * @version 1.0
 */
public class GivensRotation {
    private static double cosine;
    private static double sine;

    /**
     * Constructs a new Givens Rotation
     *
     * @param theta the angle of the rotation
     */
    public GivensRotation(double theta) {
        cosine = Math.cos(theta);
        sine = Math.sin(theta);
    } /* end of GivensRotation(double) */

    /**
     * Creates a new Givens Rotation which when applied from the left to a
     * column vector V = [..., a, ..., b]t
     * where a and b are the ith and jth entries in the vector
     * produces a column vector [..., r, ..., 0]t = Gt * V that has zero in the
     * jth position and some value r in the ith position.
     *
     * @param a the ith dimension entry
     * @param b the jth dimension entry
     */
    public GivensRotation(double a, double b) {
        if (b == 0) {
            cosine = 1;
            sine = 0;
        } else if (Math.abs(b) > Math.abs(a)) {
            double t = -a / b;
            sine = 1 / Math.sqrt(1 + t * t);
            cosine = sine * t;
        } else {
            double t = -b / a;
            cosine = 1 / Math.sqrt(1 + t * t);
            sine = cosine * t;
        } /* end of if, else if, else statement */
    } /* end of GivensRotation(double, double) */

    /**
     *
     *
     * @param a the matrix to preform the QR Factorization
     * @return matrixArr the Array of Q and R
     */
    public static Matrix[] qr_fact_givens(Matrix a) {
        Matrix[] matrixArr = new Matrix[2];
        // Set q equal to the identity matrix
        Matrix q = Matrix.identity(a.getColumnDimension(), a.getRowDimension());
        Matrix r = new Matrix(a.getColumnDimension(), a.getRowDimension());

        for (int j = 0; j < a.getColumnDimension(); j++) {
            for (int i = j+1; i < a.getColumnDimension(); i++) {
                // Create g matrix
                Matrix g = Matrix.identity(a.getColumnDimension(), a.getRowDimension());
                // set both cosine and sine
                double c = a.get(j, j) / Math.sqrt(Math.pow(a.get(j, j), 2) + Math.pow(a.get(i, j), 2));
                double s = -a.get(i,i) / Math.sqrt(Math.pow(a.get(j, j), 2) + Math.pow(a.get(i, j), 2));
                // set Q entries equal to the appropriate sub matrix
                g.set(i, i, c);
                g.set(j, j, c);
                g.set(i, j, s);
                g.set(j, i, -s);

                // set the new A
                a = MatrixHelper.multiply(g, a);
                q = MatrixHelper.multiply(q, g.transpose());
                r = MatrixHelper.multiply(g, a);
            }
        } /* end of for loop */

        // Set Array equal to new matrices
        matrixArr[0] = q;
        matrixArr[1] = r;

        return matrixArr;
    } /* end of qr_fact_givens(Matrix) */

    public static int[] findLargeVal(Matrix a) {
        // Find the largest value entry in Matrix a
        double largeVal = 0;
        int[] index = null;
        int i, j;
        for (j = 0; j < a.getColumnDimension(); j++) {
            for (i = 0; i < a.getRowDimension(); i++) {
                if (i != j && i > j) {
                    if (a.get(i, j) > largeVal) {
                        largeVal = a.get(i, j);
                        index[0] = i;
                        index[1] = j;
                    } /* end of inner if statement */
                } /* end of if statement */
            } /* end of inner for loop */
        } /* end of for loop */
        return index;
    }

    /**
     * Apply this rotation from the left to the given matrix.
     * Sets A = Gt * A
     * @param a the matrix to apply rotation
     * @param i the first row to affect in the rotation
     * @param j the second row
     */
    public static void applyLeft(Matrix a, int i, int j) {
        // Loops through all of the columns, only affecting rows i and j
        for (int k = 0; k < a.getColumnDimension(); k++) {
            double vi = a.get(i, k);
            double vj = a.get(j, k);
            a.set(i, k, cosine * vi - sine * vj);
            a.set(j, k, sine * vi + cosine * vj);
        } /* end of for loop */
    } /* end of applyLeft */

    /**
     * Apply this rotation from the right to the given matrix.
     * Sets A = A * G
     * @param a the matrix to apply rotation
     * @param i the first column to affect the rotation
     * @param j the second column
     */
    public static void applyRight(Matrix a, int i, int j) {
        // Loops through all of the rows, only affecting columns i and j
        for (int k = 0; k < a.getRowDimension(); k++) {
            double vi = a.get(k, i);
            double vj = a.get(k, j);
            a.set(k, i, cosine * vi - sine * vj);
            a.set(k, j, sine * vi + cosine * vj);
        } /* end of for loop */
    } /* end of applyRight */
} /* end of GivensRotation */
