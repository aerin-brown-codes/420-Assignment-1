package ca.mcgill.ecse420.a1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MatrixMultiplication {
	
	private static final int NUMBER_THREADS = 1;
	private static final int MATRIX_SIZE = 3;

    public static void main(String[] args) {
		
		// Generate two random matrices, same size
		double[][] a = generateRandomMatrix(MATRIX_SIZE, MATRIX_SIZE);
		double[][] b = generateRandomMatrix(MATRIX_SIZE, MATRIX_SIZE);
		sequentialMultiplyMatrix(a, b);
		parallelMultiplyMatrix(a, b);

		System.out.println("matrix A:");
		printMatrix(a);
		
		System.out.println("matrix B:");
		printMatrix(b);

		System.out.println("AxB (sequential):");
		printMatrix(sequentialMultiplyMatrix(a, b));	
		
		System.out.println(verifySize(sequentialMultiplyMatrix(a, b), true));

	}
	
	/**
	 * Returns the result of a sequential matrix multiplication
	 * The two matrices are randomly generated
	 * @param a is the first matrix
	 * @param b is the second matrix
	 * @return the result of the multiplication
	 * */
	public static double[][] sequentialMultiplyMatrix(double[][] a, double[][] b) {

		double[][] result = new double[MATRIX_SIZE][MATRIX_SIZE];

		for(int i = 0; i < MATRIX_SIZE; i++) { // iterate over row
			for(int j = 0; j < MATRIX_SIZE; j++) { // iterate over column
				double sum = 0;

				for(int k = 0; k < MATRIX_SIZE; k++) { // iterate over cell in the result matrix
					sum += a[i][k] * b[k][j];
				}
				result[i][j] = sum;
			}
		}

		return result;
	}
	
	/**
	 * Returns the result of a concurrent matrix multiplication
	 * The two matrices are randomly generated
	 * @param a is the first matrix
	 * @param b is the second matrix
	 * @return the result of the multiplication
	 * */
    public static double[][] parallelMultiplyMatrix(double[][] a, double[][] b) {
		return new double[a.length][b[0].length];
	}
        
	/**
	 * Populates a matrix of given size with randomly generated integers between 0-10.
	 * @param numRows number of rows
	 * @param numCols number of cols
	 * @return matrix
	 */
	private static double[][] generateRandomMatrix (int numRows, int numCols) {
		double matrix[][] = new double[numRows][numCols];
		for (int row = 0 ; row < numRows ; row++ ) {
			for (int col = 0 ; col < numCols ; col++ ) {
				matrix[row][col] = (double) ((int) (Math.random() * 10.0));
			}
		}
        return matrix;
    }

	/**
	 * Print elements of a matrix
	 * @param matrix the matrix
	 */
	private static void printMatrix(double[][] matrix){
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				System.out.print(matrix[i][j] + "\t"); 
			}
			System.out.println(); 
    	}
	}

	/**
	 * Generate identity matrix, of same size as the given matrix
	 * @return identity matrix of the same size
	 */
	private static double[][] generateIdentityMatrix() {
		double[][] identity = new double[MATRIX_SIZE][MATRIX_SIZE];
		for (int i = 0; i < MATRIX_SIZE; i++) {
			identity[i][i] = 1.0;
		}
		return identity;
	}

	/**
	 * Verify that a given matrix is the correct size, since A x I = A, 
	 * @param matrix the matrix to verify the size of
	 * @param isSequential is true if the matrix multiplication method was sequential, else it is parallel
	 */
	private static boolean verifySize(double[][] matrix, boolean isSequential) {
		double[][] I = generateIdentityMatrix();
		double[][] check = new double[MATRIX_SIZE][MATRIX_SIZE];
		
		if (isSequential){
			System.out.println("Verify matrix size (sequential):");

			check = sequentialMultiplyMatrix(matrix, I);
			for (int i = 0; i < MATRIX_SIZE; i++) {
				for (int j=0; j < MATRIX_SIZE; j++){
					if (check[i][j] != matrix[i][j]){
						return false;
					}
				}
			}

		} else{
			System.out.println("Verify matrix size (parallel):");
			
			check = parallelMultiplyMatrix(matrix, I);
			for (int i = 0; i < MATRIX_SIZE; i++) {
				for (int j=0; j < MATRIX_SIZE; j++){
					if (check[i][j] != matrix[i][j]){
						return false;
					}
				}
			}
		}
		return true;
	}

}
