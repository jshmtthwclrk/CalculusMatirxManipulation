package proj;

import Jama.Matrix;
import proj.HouseHolderRotation;

public class Test {
    public static void main(String[] args) {
        Matrix m = new Matrix(new double[][]{{3, 1, 44}, {0, 2, 30},{0,0,22}});
        Matrix b = new Matrix(new double[][]{{4}, {3},{4}});
        Matrix a = MatrixHelper.solveWithUpperTriangular(m, b);
        System.out.println(

        );
    }
}
