package com.mashibing.handwrite;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class SpinlockDemo {

       // AtomicReference对象创建初始值为null

    private AtomicReference<Thread> threadAtomicReference = new AtomicReference<>();

 

    public void lock() {

        Thread currentThread  = Thread.currentThread();

              // 模拟自旋

        while(!threadAtomicReference.compareAndSet(null,currentThread)) {

 

        }

        System.out.println(Thread.currentThread().getName()+"\t get lock");

    }

 

    public void unLock(){

        Thread currentThread  = Thread.currentThread();

        threadAtomicReference.compareAndSet(currentThread,null);

        System.out.println(Thread.currentThread().getName()+"\t release lock");

    }

 

    public static void main(String[] args) {

        SpinlockDemo spinlockDemo = new SpinlockDemo();

        // T1线程使用自旋方式获取锁，然后模拟5s业务处理

        new Thread(()-> {

            spinlockDemo.lock();

            try {

                System.out.println(Thread.currentThread().getName()+"\t 5s Action");

                TimeUnit.SECONDS.sleep(5);

                System.out.println(Thread.currentThread().getName()+"\t 5s Finsh");

            } catch (InterruptedException e) {

                e.printStackTrace();

            }

            spinlockDemo.unLock();

        },"T1").start();

 

        // T1线程使用自旋方式获取锁，然后模拟3s业务处理

        new Thread(()-> {

            spinlockDemo.lock();

            try {

                System.out.println(Thread.currentThread().getName()+"\t 5s Action");

                TimeUnit.SECONDS.sleep(5);

                System.out.println(Thread.currentThread().getName()+"\t 5s Finsh");

            } catch (InterruptedException e) {

                e.printStackTrace();

            }

            spinlockDemo.unLock();

        },"T2").start();

    }

}