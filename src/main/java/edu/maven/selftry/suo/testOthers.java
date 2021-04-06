package edu.maven.selftry.suo;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class testOthers {
    @Test
    public void testFairSyn(){
        ReentrantLock fairSync = new ReentrantLock(true);

        for (int i = 1; i <= 5; i++){
            new Thread(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + " 入队");
                    fairSync.lock();
                    System.out.println(Thread.currentThread().getName() + "排到了");
                } finally {
                    fairSync.unlock();
                }
            }).start();
        }
    }

    public static void main(String[] args){
        //testSemaphore();
        testCountDown();
    }

    public static void testSemaphore(){
        Semaphore semaphore = new Semaphore(2);
        for (int i = 1; i <= 5; i++){
            new Thread(() -> {
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + " 开始工作");
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println(Thread.currentThread().getName() + " 结束工作");
                    semaphore.release();
                }
            }).start();
        }
    }

    public static void testCountDown(){
        CountDownLatch countDown = new CountDownLatch(2);
        for(int i = 0; i < 2; i++){
            new Thread(() -> {
                    try {
                        System.out.println(Thread.currentThread().getName() + " 开始工作");
                        Thread.sleep(20);
                        System.out.println(Thread.currentThread().getName() + " 结束工作");
                        countDown.countDown();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
        }
        new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + " 开始等待");
                countDown.await();
                System.out.println(Thread.currentThread().getName() + " 结束工作");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
