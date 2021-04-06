package edu.maven.selftry.suo;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class testReadWriteLock {
    private static final ReentrantReadWriteLock test = new ReentrantReadWriteLock();

    public static void main(String[] args) {
        new Thread(() -> tryRead(Thread.currentThread())).start();
        new Thread(() -> tryRead(Thread.currentThread())).start();
        new Thread(() -> tryWrite(Thread.currentThread())).start();
        new Thread(() -> tryWrite(Thread.currentThread())).start();
        new Thread(() -> tryRead(Thread.currentThread())).start();
    }

    public static void tryRead(Thread thread){
        test.readLock().lock();
        System.out.println("我是" + thread.getName() + "， 我想读");
        try {
            if (!test.isWriteLocked()) {
                System.out.println("可以读啦！");
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(thread.getName() + " 读完了");
            }
        }finally {
            test.readLock().unlock();
        }
    }

    public static void tryWrite(Thread thread){
        test.writeLock().lock();
        System.out.println("我是" + thread.getName() + "， 我想写");
        try {
            if (test.isWriteLocked()) {
                System.out.println("可以写啦！");
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(thread.getName() + " 写完了");
            }
        }finally {
            test.writeLock().unlock();
        }
    }
}
