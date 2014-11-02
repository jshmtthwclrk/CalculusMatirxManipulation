import Jama.Matrix;

/**
 * Created by jshmtthwclrk on 10/29/14.
 *
 * Class representing a Givens Rotation
 *
 * @author Joshua Clark
 * @version 1.0
 */
public class GivensRotation {
    private double cosine;
    private double sine;

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
     * Apply this rotation from the left to the given matrix.
     * Sets A = Gt * A
     * @param a the matrix to apply rotation
     * @param i the first row to affect in the rotation
     * @param j the second row
     */
    public void applyLeft(Matrix a, int i, int j) {
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
    public void applyRight(Matrix a, int i, int j) {
        // Loops through all of the rows, only affecting columns i and j
        for (int k = 0; k < a.getRowDimension(); k++) {
            double vi = a.get(k, i);
            double vj = a.get(k, j);
            a.set(k, i, cosine * vi - sine * vj);
            a.set(k, j, sine * vi + cosine * vj);
        } /* end of for loop */
    } /* end of applyRight */
} /* end of GivensRotation */
