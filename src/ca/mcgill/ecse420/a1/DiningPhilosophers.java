package ca.mcgill.ecse420.a1;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DiningPhilosophers {

    static int NUMBER_OF_PHILOSOPHERS = 5;
    static Philosopher[] philosophers;
    static Object[] chopsticks;
    static Lock napkin = new ReentrantLock();
    
    public static void main(String[] args) {

        philosophers = new Philosopher[NUMBER_OF_PHILOSOPHERS];
        chopsticks = new Object[NUMBER_OF_PHILOSOPHERS];

        ExecutorService executor = Executors.newFixedThreadPool(NUMBER_OF_PHILOSOPHERS);
        Philosopher p;
        for (int i = 0; i < philosophers.length; i++) {
            p = new Philosopher();
            p.num = i;
            philosophers[i] = p;
            chopsticks[i] = new Object();
            executor.execute(p);
        }
    }

    public static class Philosopher implements Runnable {

        int num = 0;
        int random_min = 0;
        int random_max = 1000;
        Random randomizer = new Random();
        int wait_time;

        @Override
        public void run() {

            int left_chopstick = this.num - 1;
            if (this.num == 0) {
                left_chopstick = NUMBER_OF_PHILOSOPHERS - 1;
            }
            int right_chopstick = this.num + 1;
            if (this.num == NUMBER_OF_PHILOSOPHERS - 1) {
                right_chopstick = 0;
            }
            
            while (true) { 
                System.out.println("Philosopher " + this.num + " is thinking...");
                wait_time = randomizer.nextInt(random_min, random_max);
                try {
                    Thread.sleep(wait_time);
                } catch (Exception e) {
                }

                System.out.println("Philosopher " + this.num + " is attempting to eat...");
                napkin.lock();
                synchronized (chopsticks[left_chopstick]) {
                    System.out.println("Philosopher " + this.num + " has acquired their left " +
                        "chopstick, number " + left_chopstick + ". They are attempting to take " +
                        "their right chopstick.");
                    synchronized (chopsticks[right_chopstick]) {
                        System.out.println("Philosopher " + this.num + " has acquired their " +
                            "right chopstick, number " + right_chopstick + ". They are eating.");
                        wait_time = randomizer.nextInt(random_min, random_max);
                        try {
                            Thread.sleep(wait_time);
                        } catch (Exception e) {
                        }
                    }
                }
                napkin.unlock();
            }            
        }
    }
}
