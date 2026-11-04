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
        int i = ThreadId.getThreadId();
        int n = label.length;

        flag[i] = true;

        int max_value = -1;
        for (int value : label){
            if (value > max_value){
                max_value = value;
            }
        }

        label[i] = max_value + 1;

        for (int k=0; k<n; k++){
            if (k != i){
                while (flag[k] && (label[k] < label[i] || (label[k] == label[i] && k<i))){};
            } else{
                continue; 
            }
        }
    }

    @Override 
    public void unlock(){
        int i = ThreadId.getThreadId();
        flag[i] = false;
    }
}
