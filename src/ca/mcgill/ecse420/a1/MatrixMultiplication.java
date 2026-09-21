package ca.mcgill.ecse420.a1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import java.util.List;
import java.util.ArrayList;

public class MatrixMultiplication {
	
	private static final int NUMBER_THREADS = 50;
	private static final int MATRIX_SIZE = 4000;

    public static void main(String[] args) {
		
		// Generate two random matrices, same size
		double[][] a = MatrixMultiplicationHelpers.generateRandomMatrix(MATRIX_SIZE, MATRIX_SIZE);
		double[][] b = MatrixMultiplicationHelpers.generateRandomMatrix(MATRIX_SIZE, MATRIX_SIZE);
		//sequentialMultiplyMatrix(a, b);
		//parallelMultiplyMatrix(a, b);

		//System.out.println("matrix A:");
		//MatrixMultiplicationHelpers.printMatrix(a);
		
		//System.out.println("matrix B:");
		//MatrixMultiplicationHelpers.printMatrix(b);

		System.out.println("AxB (sequential):");
		long startSeq = System.nanoTime();
		double[][] matrixSeq = sequentialMultiplyMatrix(a, b);
		long endSeq = System.nanoTime();

		long sequentialTime = endSeq - startSeq;
		System.out.println("Sequential: " + sequentialTime / 1000000.0 + " ms");

		//MatrixMultiplicationHelpers.printMatrix(resultSequential);	
		//System.out.println(MatrixMultiplicationHelpers.verify(matrixSeq, true));
		
		System.out.println("AxB (parallel):");
		long startPar = System.nanoTime();
		double[][] matrixPar = parallelMultiplyMatrix(a, b);
		long endPar = System.nanoTime();

		long parallelTime = endPar - startPar;
		System.out.println("Parallel: " + parallelTime / 1000000.0 + " ms");

		//MatrixMultiplicationHelpers.printMatrix(resultParallel);			
		//System.out.println(MatrixMultiplicationHelpers.verify(matrixPar, false));
		
		/*double seqTime = MatrixMultiplicationHelpers.measureTime(() -> sequentialMultiplyMatrix(a, b));
		double parTime = MatrixMultiplicationHelpers.measureTime(() -> parallelMultiplyMatrix(a, b));

		System.out.println("Sequential: " + seqTime + " ms");
		System.out.println("Parallel: " + parTime + " ms");*/
		
		//System.out.println("Compare parallel and sequential results:");
		//System.out.println(MatrixMultiplicationHelpers.compareMatrices(parallelMultiplyMatrix(a, b), sequentialMultiplyMatrix(a, b)));
	}
	
	/**
	 * Returns the result of a sequential matrix multiplication
	 * The two matrices are randomly generated
	 * @param a is the first matrix
	 * @param b is the second matrix
	 * @return the result of the multiplication
	 * */
	public static double[][] sequentialMultiplyMatrix(double[][] a, double[][] b) {
		int n = a.length;
		double[][] result = new double[n][n];

		for(int i = 0; i < n; i++) { // iterate over row
			for(int j = 0; j < n; j++) { // iterate over column
				double sum = 0;

				for(int k = 0; k < n; k++) { // iterate over cell in the result matrix
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
		int n = a.length;
		double[][] result = new double[n][n];
		
		double numRows = (double) n / (double) NUMBER_THREADS;
		int numRowsDown = (int) Math.floor(numRows); // round down to closest integer
		int numRowsUp = (int) Math.ceil(numRows); // round up to closest integer

		double rowsThread = numRows % 1; // to get the decimal fraction
		int numThreadsUp = (int) (rowsThread * NUMBER_THREADS);
		int numThreadsDown = NUMBER_THREADS - 1 - numThreadsUp;

		// rowsThread is an integer (no decimal)
		if (rowsThread == 0.0){
			numThreadsUp = NUMBER_THREADS;
			numThreadsDown = 0;
			rowsThread = 1.0;
		}

		List<Callable<Object>> tasks = new ArrayList<Callable<Object>>();

		int rowStart = 0;
		int rowEnd = 0;

		for (int threadCount=0; threadCount < NUMBER_THREADS; threadCount++){
			if (threadCount < numThreadsUp){ // rounding up case
				rowEnd = rowStart + numRowsUp;
			} else if (threadCount < (numThreadsUp + numThreadsDown)){ // rounding down case
				rowEnd = rowStart + numRowsDown;
			} else {
				rowEnd = n;
			}

			MultiplyRowRange multiply = new MultiplyRowRange(a, b, result, rowStart, rowEnd);
			rowStart = rowEnd; // start becomes the end, for the next thread range

			tasks.add(Executors.callable(multiply));
		}

		ExecutorService threadPool = Executors.newFixedThreadPool(NUMBER_THREADS);
		try{
			threadPool.invokeAll(tasks);
			threadPool.shutdown();
			threadPool.awaitTermination(10, TimeUnit.MINUTES);
		} catch(Exception e){
			// error catch
			e.printStackTrace();
			System.exit(1); 
		}
		
		return result;
	}
 
}
