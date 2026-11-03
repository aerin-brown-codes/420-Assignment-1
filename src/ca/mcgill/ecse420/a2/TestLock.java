package ca.mcgill.ecse420.a2;

public class TestLock {
    private static volatile int count;
    private static final int WAIT_LOCK = 500;
    private static final int WAIT_CONDITION = 1000;

    public static void main(String[] args) {
        // Lock lock = new BakeryLock(2);
        // testLock(lock, 2);
        int n=8;
        Lock lock = new FilterLock(n);
        testLock(lock, n);
        
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
                int local_count = count;

                try{
                    Thread.sleep(WAIT_CONDITION);
                } catch(InterruptedException e){
                    e.printStackTrace();
                }

                count = local_count + 1;

                System.out.println(count);
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

        

       //new java.util.Scanner(System.in).nextLine();
    }
}
