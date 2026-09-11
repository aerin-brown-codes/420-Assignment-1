package ca.mcgill.ecse420.a1;

import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Deadlock {

    Dummy lockA = new Dummy();
    Dummy lockB = new Dummy();

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.execute(new A());
        executor.execute(new B());
        executor.shutdown();
    }

    public class Dummy {

    }

    public static class A implements Runnable {
        public void run() {
            synchronized (lockA) {
                Thread.sleep(5)
                synchronized (lockB) {
                    Thread.sleep(1)
                }
            }
        }
    }

    public static class B implements Runnable {
        public void run() {
            synchronized (lockB) {
                Thread.sleep(5)
                synchronized (lockA) {
                    Thread.sleep(1)
                }
            }
        }
    }

}