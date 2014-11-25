package proj.gauss;

import Jama.Matrix;



/**
 * Created by jshmtthwclrk on 11/24/14.
 *
 */
public class Test extends GivensRotation {

    private static Matrix a = new Matrix(3,3);

    public static void main(String[] args) {
        testCode();
    }
    public static void testCode() {

        a.set(0,0,4);
        a.set(0,1,3);
        a.set(0,2,2);
        a.set(1,0,3);
        a.set(1,1,2);
        a.set(1,2,1);
        a.set(2,0,2);
        a.set(2,1,1);
        a.set(2,2,4);

        Matrix[] matrix = qr_fact_givens(a);

        System.out.println(matrix[0]);
        System.out.println(matrix[1]);
    }
}
