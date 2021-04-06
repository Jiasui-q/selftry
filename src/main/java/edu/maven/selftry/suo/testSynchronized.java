package edu.maven.selftry.suo;

public class testSynchronized {
    private final int MAX_SIZE;
    private int count;

    public testSynchronized(int n){
        MAX_SIZE = n;
        count = 0;
    }

    public synchronized void add(){
        try {
            while(count >= MAX_SIZE){
                System.out.println("满了不准放");
                this.wait();
            }
            count++;
            System.out.println(Thread.currentThread().getName() + " puts " + count);
            this.notifyAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void take(){
        try {
            while(count <= 0){
                System.out.println("没得东西拿");
                this.wait();
            }
            count--;
            System.out.println(Thread.currentThread().getName() + " takes " + count);
            this.notifyAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        testSynchronized store = new testSynchronized(5);

        Thread produce1 = new ProducerS(store);
        Thread produce2 = new ProducerS(store);
        Thread consume1 = new ConsumerS(store);
        Thread consume2 = new ConsumerS(store);

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

class ProducerS extends Thread{
    private testSynchronized store;

    ProducerS(testSynchronized store){
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

class ConsumerS extends Thread{
    private testSynchronized store;

    ConsumerS(testSynchronized store){
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
