package ca.mcgill.ecse420.a1;

public class MatrixMultiplicationHelpers {
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
	public static void printMatrix(double[][] matrix) {
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
	public static double[][] generateIdentityMatrix(int matrix_size) {
		double[][] identity = new double[matrix_size][matrix_size];
		for (int i = 0; i < matrix_size; i++) {
			identity[i][i] = 1.0;
		}
		return identity;
	}

	/**
	 * Compare two matrices of the same size
	 * @param matrixA is the first matrix 
	 * @param matrixB is the second matrix
	 * @return true if both matrices are identical
	 */
	public static boolean compareMatrices(double[][] matrixA, double[][] matrixB) {
		
		if (matrixA.length != matrixB.length) {
			System.out.println("Matrices are not the same size");
			return false;
		} else if (matrixA[0].length != matrixB[0].length) {
			System.out.println("Matrices are not the same size");
			return false;
		} 
		int n = matrixA.length;

		for (int i = 0; i < n; i++) {
			for (int j=0; j < n; j++) {
				if (matrixA[i][j] != matrixB[i][j]) {
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * Verify that a given matrix is the correct size, since A x I = A, 
	 * @param matrix the matrix to verify the size of
	 * @param isSequential is true if the matrix multiplication method was sequential, else it is parallel
	 */
	public static boolean verify(double[][] matrix, boolean isSequential) {
		int n = matrix.length;
		double[][] I = generateIdentityMatrix(n);
		double[][] check = new double[n][n];
		
		if (isSequential) {
			System.out.println("Verify matrix size (sequential):");

			check = MatrixMultiplication.sequentialMultiplyMatrix(matrix, I);
			compareMatrices(check, matrix);

		} else {
			System.out.println("Verify matrix size (parallel):");
			
			check = MatrixMultiplication.parallelMultiplyMatrix(matrix, I);
			compareMatrices(check, matrix);

		}
		return true;
	}

	/**
	 * 
	 * @param task to get the length of duration
	 * @return duration of the task
	 */
	public static double measureTime(Runnable task) {
		long startTime = System.nanoTime();

		task.run();

		long endTime = System.nanoTime();

		double duration = (endTime-startTime)/1000000.0;

		return duration;
	}
}
