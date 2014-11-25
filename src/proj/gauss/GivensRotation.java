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

    /**
     * This method takes in a Matrix A, and uses the Givens Rotation
     * to find the QR Factorization.
     *
     * @param a the matrix to preform the QR Factorization
     * @return matrixArr the Array of Q and R
     */
    public static Matrix[] qr_fact_givens(Matrix a) {
        Matrix[] matrixArr = new Matrix[2];
        // Set q equal to the identity matrix
        Matrix q = Matrix.identity(a.getColumnDimension(), a.getRowDimension());
        Matrix r = new Matrix(a.getColumnDimension(), a.getRowDimension());
        Matrix ident = Matrix.identity(a.getColumnDimension(), a.getRowDimension());

        for (int j = 0; j < a.getColumnDimension(); j++) {
            for (int i = j+1; i < a.getColumnDimension(); i++) {
                // Create g matrix
                Matrix g = Matrix.identity(a.getColumnDimension(), a.getRowDimension());
                // set both cosine and sine
                double c = a.get(j, j) / ( Math.sqrt(Math.pow(a.get(j, j), 2) + Math.pow(a.get(i, j), 2) ) );
                double s = - a.get(i, j) / ( Math.sqrt(Math.pow(a.get(j, j), 2) + Math.pow(a.get(i, j), 2) ) );
                // set Q entries equal to the appropriate sub matrix
                g.set(i, i, c);
                g.set(i, j, s);
                g.set(j, i, -s);
                g.set(j, j, c);

                // set the new A
                g = MatrixHelper.multiply(ident, g);
                a = MatrixHelper.multiply(g, a);
                q = MatrixHelper.multiply(q, g.transpose());
                r = a;
            } /* end of inner for loop */
        } /* end of for loop */

        // Set Array equal to new matrices
        matrixArr[0] = q;
        matrixArr[1] = r;

        return matrixArr;
    } /* end of qr_fact_givens(Matrix) */
} /* end of GivensRotation */
