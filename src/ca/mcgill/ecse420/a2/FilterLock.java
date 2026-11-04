package ca.mcgill.ecse420.a2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.TimeUnit;

public class FilterLock implements Lock{
    private volatile int[] level;
    private volatile int[] victim;

    public FilterLock(int n){
        level = new int[n];
        victim = new int[n];

        for (int i = 0; i < n; i++) {
            level[i] = 0;
        }
    }

    @Override
    public void lock() {
        int i = ThreadId.getThreadId();
        int n = level.length;
        for (int L=1; L<n; L++){ // 1 level at a time
            level[i] = L; // announce intention to enter level L
            victim[L] = i; // give priority to anyone but me

            for (int k=0; k<n; k++){
                if (k != i){
                    while (level[k] >= L && victim[L] == i){};
                } else{
                    continue; 
                }
            }
        }
    }

    @Override 
    public void unlock(){
        int i = ThreadId.getThreadId();
        level[i] = 0;
    }

}
