package edu.maven.selftry.suo;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

public class testCAS {
    class CompareAndSwap {
        private int value;

        public synchronized int get() {
            return value;
        }

        public synchronized int compareAndSwap(int expected, int newValue) {
            int oldValue = value;
            System.out.println("Expected: " + expected + " Value: " + oldValue);

            if (oldValue == expected) {
                this.value = newValue;
            }

            return oldValue;
        }

        public synchronized boolean compareAndSet(int expected, int newValue) {
            return expected == compareAndSwap(expected, newValue);
        }
    }

    @Test
    public void testMyCAS() {
        final CompareAndSwap test = new CompareAndSwap();

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                int expected = test.get();
                boolean ifsuccess = test.compareAndSet(expected, (int) (Math.random() * 10));
                System.out.println(ifsuccess);
            }).start();
        }
    }

    @Test
    public void tesAtomicStamped() {
        String initial = "initial";
        String newRef = "newReference";

        final AtomicStampedReference<String> test = new AtomicStampedReference<String>(initial, 1);

        new Thread(() -> {
            System.out.println("版本号：" + test.getStamp());
            boolean b = test.compareAndSet(initial, newRef, 1, 2);
            System.out.println(b);
        }).start();
        new Thread(() -> {
            System.out.println("版本号：" + test.getStamp());
            boolean b = test.compareAndSet(initial, newRef, 1, 2);
            System.out.println(b);
        }).start();
    }

    class User {
        private int ID;
        private String name;

        public User(int ID, String name) {
            this.ID = ID;
            this.name = name;
        }

        int getID() {
            return ID;
        }

        String getName() {
            return name;
        }
    }

    @Test
    public void testAtomicReference() {
        User u1 = new User(1, "user1");
        User u2 = new User(2, "user2");

        final AtomicReference<User> test = new AtomicReference<>(u1);

        new Thread(() -> {
            System.out.println("ID: " + test.get().getID() + " Name: " + test.get().getName());
            boolean b = test.compareAndSet(u1, u2);
            System.out.println(b);
        }).start();
        new Thread(() -> {
            System.out.println("ID: " + test.get().getID() + " Name: " + test.get().getName());
            boolean b = test.compareAndSet(u1, u2);
            System.out.println(b);
        }).start();
    }
}