package ca.mcgill.ecse420.a2;

public class TestLock {
    private static volatile int count;
    private static final int WAIT_LOCK = 500;
    private static final int WAIT_CONDITION = 1000;
    private static final int WAIT_LOOP = 5000;

    public static void main(String[] args) {
        System.out.println("Filter Lock Test");
        for (int n=2; n<9; n++){
            Lock lock = new FilterLock(n);
            testLock(lock, n);

            try {
                Thread.sleep(WAIT_LOOP);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        System.out.println("Bakery Lock Test");
        for (int n=2; n<9; n++){
            Lock lock = new BakeryLock(n);
            testLock(lock, n);

            try {
                Thread.sleep(WAIT_LOOP);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * Code to test a lock that works for n-thread mutual exclusion
     * @param lock The lock that's tested.
     */
    public static void testLock(Lock lock, int n) {
        ThreadId.clearThread();
        count = 0;
        Thread[] threads = new Thread[n];

        System.out.println("Test n: " + n);
        
        for (int i=0; i<n; i++){
            threads[i] = new Thread(()->{
                lock.lock();

                try{
                    Thread.sleep(WAIT_CONDITION);
                } catch(InterruptedException e){
                    e.printStackTrace();
                }

                System.out.println(++count);
                lock.unlock();
            });
        }

  
        for (Thread thread : threads){
            thread.start();

            try {
                Thread.sleep(WAIT_LOCK);
            } catch(InterruptedException e_thread){
                e_thread.printStackTrace();
            }
        }
    }
}
