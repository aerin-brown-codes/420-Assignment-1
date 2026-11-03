package ca.mcgill.ecse420.a2;

public class BakeryLock implements Lock{
    private volatile boolean[] flag;
	private volatile int[] label;

    public BakeryLock (int n){
        flag = new boolean[n];
        label = new int[n];

        for (int i=0; i<n; i++){
            flag[i] = false;
            label[i] = 0;
        }
    }

    @Override
    public void lock() {
        flag[i] = true;
        label[i] = max(label[0], ..., label[n-1])+1;

        while(){

        }
    }

    @Override 
    public void unlock(){
        flag[i] = false;
    }
}
