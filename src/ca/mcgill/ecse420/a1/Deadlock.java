package ca.mcgill.ecse420.a1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Deadlock {

    static Dummy lockA = new Dummy();
    static Dummy lockB = new Dummy();

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.execute(new A());
        executor.execute(new B());
        executor.shutdown();
    }

    public static class Dummy {

    }

    public static class A implements Runnable {
        public void run() {
            synchronized (lockA) {
                System.out.println("A acquired lock A");
                try {
                    Thread.sleep(500);
                } catch (Exception e) {

                }
                System.out.println("A attempting to acquire lock B...");
                synchronized (lockB) {
                    System.out.println("A acquired lock B");
                    try {
                        Thread.sleep(500);
                    } catch (Exception e) {

                    }
                }
            }
        }
    }

    public static class B implements Runnable {
        public void run() {
            synchronized (lockB) {
                System.out.println("B acquired lock B");
                try {
                    Thread.sleep(500);
                } catch (Exception e) {

                }
                System.out.println("B attempting to acquire lock A...");
                synchronized (lockA) {
                    System.out.println("B acquired lock A");
                    try {
                        Thread.sleep(500);
                    } catch (Exception e) {

                    }
                }
            }
        }
    }

}