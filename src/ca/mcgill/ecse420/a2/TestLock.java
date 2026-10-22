package ca.mcgill.ecse420.a2;

public class TestLock {
    public static void main(String[] args) {
        Lock lock = new BakeryLock(2);
        testLock(lock);

        lock = new FilterLock(2);
        testLock(lock);
    }

    /**
     * Code to test a lock that works for n-thread mutual exclusion
     * @param lock The lock that's tested.
     */
    public static void testLock(Lock lock) {
    }
}
