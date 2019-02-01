package ru.javaops.webapp;

public class MainConcurrency {
    public static Object lock1 = new Object();
    public static Object lock2 = new Object();

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> lock(lock1, lock2));
        Thread thread2 = new Thread(() -> lock(lock2, lock1));
        thread1.start();
        thread2.start();
    }

    public static void lock(Object lock1, Object lock2) {
        synchronized (lock1) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("Error" + e.getMessage());
            }
            synchronized (lock2) {
            }
        }
    }
}