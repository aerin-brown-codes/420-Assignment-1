package ca.mcgill.ecse420.a1;

public class MatrixMultiplicationHelpers {
	private static final int NUMBER_THREADS = 1;
	private static final int MATRIX_SIZE = 3;
    /**
	 * Populates a matrix of given size with randomly generated integers between 0-10.
	 * @param numRows number of rows
	 * @param numCols number of cols
	 * @return matrix
	 */
	public static double[][] generateRandomMatrix (int numRows, int numCols) {
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
	public static void printMatrix(double[][] matrix){
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
	public static double[][] generateIdentityMatrix() {
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
	public static boolean verifySize(double[][] matrix, boolean isSequential) {
		double[][] I = generateIdentityMatrix();
		double[][] check = new double[MATRIX_SIZE][MATRIX_SIZE];
		
		if (isSequential){
			System.out.println("Verify matrix size (sequential):");

			check = MatrixMultiplication.sequentialMultiplyMatrix(matrix, I);
			for (int i = 0; i < MATRIX_SIZE; i++) {
				for (int j=0; j < MATRIX_SIZE; j++){
					if (check[i][j] != matrix[i][j]){
						return false;
					}
				}
			}

		} else{
			System.out.println("Verify matrix size (parallel):");
			
			check = MatrixMultiplication.parallelMultiplyMatrix(matrix, I);
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
