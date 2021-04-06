package edu.maven.selftry.suo;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class testCondition {
    private final ReentrantLock reentrantLock;
    private final Condition full;
    private final Condition empty;
    private final int MAX_SIZE;
    private int count;

    public testCondition(int n){
        reentrantLock = new ReentrantLock();
        full = reentrantLock.newCondition();
        empty = reentrantLock.newCondition();
        MAX_SIZE = n;
        count = 0;
    }

    public void add(){
        reentrantLock.lock();
        try {
            while(count >= MAX_SIZE){
                System.out.println("满了不准放");
                full.await();
            }
            count++;
            System.out.println(Thread.currentThread().getName() + " puts " + count);
            empty.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            reentrantLock.unlock();
        }
    }

    public void take(){
        reentrantLock.lock();
        try {
            while(count <= 0){
                System.out.println("没得东西拿");
                empty.await();
            }
            count--;
            System.out.println(Thread.currentThread().getName() + " takes " + count);
            full.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            reentrantLock.unlock();
        }
    }

    public static void main(String[] args){
        testCondition store = new testCondition(5);

        Thread produce1 = new Producer(store);
        Thread produce2 = new Producer(store);
        Thread consume1 = new Consumer(store);
        Thread consume2 = new Consumer(store);

        produce1.setName("Producer 1");
        produce2.setName("Producer 2");
        consume1.setName("Consumer 1");
        consume2.setName("Consumer 2");

        produce1.start();
        produce2.start();
        consume1.start();
        consume2.start();

    }
}

class Producer extends Thread{
    private testCondition store;

    Producer(testCondition store){
        this.store = store;
    }

    @Override
    public void run(){
        while(true) {
            store.add();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Consumer extends Thread{
    private testCondition store;

    Consumer(testCondition store){
        this.store = store;
    }

    @Override
    public void run(){
        while(true) {
            store.take();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}