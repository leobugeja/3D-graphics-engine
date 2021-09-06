package renderer.geometry.primitives;

public class Matrix {

    private double[][] mat;
    private int[] size;

    public static void main(String[] args) {
        Matrix a = new Matrix(new double[][]{{2, 4},{5, 3}});
        Matrix b = new Matrix(new double[][]{{1, 0},{0, 1}});
        Matrix c = multiply(a, b);

    }

    public Matrix(int size) {
        this.size = new int[]{size, size};
        this.mat = new double[size][size];
    }

    public Matrix(double[][] elements) {
        this.size = new int[]{elements.length, elements[0].length}; // {rows, columns}
        this.mat = elements;
    }

    public static Matrix multiply(Matrix mat1, Matrix mat2) {
        if (mat1.size[1] != mat2.size[0]) {
            throw new ArithmeticException("Matrix dimensions do not match");
        }
        int rows = mat1.size[0];
        int columns = mat2.size[1];
        double[][] new_mat = new double[rows][columns];
        for (int r = 0; r < rows; r++) {
            for (int c = 0 ; c < columns ; c++) {
                new_mat[r][c] = dot(mat1.extractRow(r), mat2.extractColumn(c));
            }
        }
        return new Matrix(new_mat);
    }

    public static Vec3d multiply(Matrix mat1, Vec3d vec) {
        if (mat1.size[1] != 4) {
            throw new ArithmeticException("Matrix dimensions do not match size for vector multiplication");
        }
        double[] mat2 = {vec.x, vec.y, vec.z, 1};

        int rows = mat1.size[0];
        double[] new_mat = new double[rows];
        for (int r = 0; r < rows; r++) {
            new_mat[r] = dot(mat1.extractRow(r), mat2);
        }
        return new Vec3d(new_mat[0], new_mat[1], new_mat[2]);
    }



    public double extractElement(int row, int column) {
        return this.mat[row][column];
    }

    public double[] extractRow(int row) {
        return this.mat[row];
    }

    public double[] extractColumn(int column) {
        double[] col_vector = new double[this.size[0]];
        for (int r = 0; r < col_vector.length; r++) {
            col_vector[r] = this.mat[r][column];
        }
        return col_vector;
    }

    private static double dot(double[] vec1, double[] vec2) {
        if (vec1.length != vec2.length) {
            throw new ArithmeticException("Vectors not same length for dot product");
        }
        double sum = 0;
        for (int i = 0; i < vec1.length; i++) {
            sum += vec1[i]*vec2[i];
        }
        return sum;
    }


}
