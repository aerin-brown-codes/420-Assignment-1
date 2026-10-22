package ca.mcgill.ecse420.a2;

public class BakeryLock implements Lock{
    private volatile boolean[] flag;
	private volatile int[] label;

    public BakeryLock (int n){
        flag = new boolean[n];
        label = new int[n];
    }

    @Override
    public void lock() {

    }

    @Override 
    public void unlock(){

    }
}
