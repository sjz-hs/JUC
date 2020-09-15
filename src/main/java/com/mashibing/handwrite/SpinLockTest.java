package com.mashibing.handwrite;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 手写了一个自旋锁，直接调用spinLock()、unSpinLock()即可完成多线程加锁的控制
 * 代码中使用了原子引用类，原子引用类的底层原理就是CAS，CAS思想就是自旋，我们这里借用了这个思想，使用while循环完成自旋
 */
public class SpinLockTest {

    //定义原子引用类，保证原子性，volatile保证可见性、有序性  定义
    private volatile AtomicReference<Thread> atomicReference = new AtomicReference<>();

    public void spinLock(){
        Thread thread = Thread.currentThread();
        while (!atomicReference.compareAndSet(null, thread)){}
        System.out.println(thread.getName() + "huoqu");
    }

    public void unSpinLock(){
        Thread thread = Thread.currentThread();
        while (!atomicReference.compareAndSet(thread, null)){}
        System.out.println(thread.getName() + "shi fang ");
    }

    public static void main(String[] args) {
        SpinLockTest spinLockTest = new SpinLockTest();

        new Thread(new Runnable() {
            @Override
            public void run() {
                spinLockTest.spinLock();
                System.out.println("thread1 zhixing");
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println();
                spinLockTest.unSpinLock();
            }
        }, "thread1").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                spinLockTest.spinLock();
                System.out.println("thread2 zhixing");
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println();
                spinLockTest.unSpinLock();
            }
        }, "thread2").start();
    }

}