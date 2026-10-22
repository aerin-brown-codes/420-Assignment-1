package ca.mcgill.ecse420.a2;

public class FilterLock implements Lock {
    private volatile int[] level;
    private volatile int[] victim;

    public FilterLock(int n){
        level = new int[n];
        victim = new int[n];
    }

    @Override
    public void lock() {

    }

    @Override 
    public void unlock(){

    }

}
