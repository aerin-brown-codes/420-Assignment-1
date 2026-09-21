package ca.mcgill.ecse420.a1;

public class MultiplyRowRange implements Runnable {
    private double[][] a;
    private double[][] b;
    private double[][] result;
    private int startRow;
    private int endRow;

    public MultiplyRowRange(double[][] a, double[][] b, double[][] result, int start, int end){
        this.a = a;
        this.b = b;
        this.result = result;
        this.startRow = start;
        this.endRow = end;
    }

    @Override 
    public void run(){
        int n = a.length; // same as MATRIX_SIZE

        for (int i=startRow; i<endRow; i++){ // loop through row
            for (int j=0; j<n; j++){ // loop through column
                result[i][j] = 0; // initialize cell
                for (int k=0; k<n; k++){ // dot product, to get the value of each cell
                    result[i][j] += a[i][k] * b[k][j];
                }
            }
        }
    }
}
