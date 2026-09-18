package ca.mcgill.ecse420.a1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import java.util.List;
import java.util.ArrayList;

public class MatrixMultiplication {
	
	private static final int NUMBER_THREADS = 1;
	private static final int MATRIX_SIZE = 3;

    public static void main(String[] args) {
		
		// Generate two random matrices, same size
		double[][] a = MatrixMultiplicationHelpers.generateRandomMatrix(MATRIX_SIZE, MATRIX_SIZE);
		double[][] b = MatrixMultiplicationHelpers.generateRandomMatrix(MATRIX_SIZE, MATRIX_SIZE);
		sequentialMultiplyMatrix(a, b);
		parallelMultiplyMatrix(a, b);

		System.out.println("matrix A:");
		MatrixMultiplicationHelpers.printMatrix(a);
		
		System.out.println("matrix B:");
		MatrixMultiplicationHelpers.printMatrix(b);

		System.out.println("AxB (sequential):");
		MatrixMultiplicationHelpers.printMatrix(sequentialMultiplyMatrix(a, b));	
		
		System.out.println(MatrixMultiplicationHelpers.verifySize(sequentialMultiplyMatrix(a, b), true));

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
		double[][] result = new double[MATRIX_SIZE][MATRIX_SIZE];
		double numRows = (double) MATRIX_SIZE / (double) NUMBER_THREADS;

		List<Callable<Object>> tasks = new ArrayList<Callable<Object>>();

		int rowStart = 0;
		int rowEnd = 0;


		ExecutorService threadPool = Executors.newFixedThreadPool(NUMBER_THREADS);
		try{
			threadPool.invokeAll(tasks);
			threadPool.shutdown();
			threadPool.awaitTermination(10, TimeUnit.MINUTES);
		} catch(Exception e){
			e.printStackTrace();
			System.exit(1);
		}
		
		return result;
	}
 
}
