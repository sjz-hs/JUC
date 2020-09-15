package com.mashibing.jdk18;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;

public class StampedLockDemo {

    //线程池
    static ExecutorService service = Executors.newFixedThreadPool(10);
    static StampedLock lock = new StampedLock();
    static long milli = 5000;
    static int count = 0;

    //写锁
    private static long writeLock() {
        long stamp = lock.writeLock(); //获取排他写锁

        count += 1;

        lock.unlockWrite(stamp); //释放写锁

        System.out.println("write success");

        return System.currentTimeMillis();
    }

    // 线程池的使用
    private static void readLock() {//普通的读锁
        service.submit(() -> {
            int currentCount = 0;
            long stamp = lock.readLock(); //获取排他读锁
            try {
                currentCount = count; //获取变量值
                try {
                    TimeUnit.MILLISECONDS.sleep(milli);//模拟读取需要花费20秒
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } finally {
                lock.unlockRead(stamp); //释放读锁
            }
            //先读
            System.out.println("readLock==" + currentCount); //显示最新的变量值
        });
        /*===================================================*/
        try {
            TimeUnit.MILLISECONDS.sleep(500);//要等一等读锁先获得  读锁先获取
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void optimisticRead() {
        service.submit(() -> {
            //尝试获取
            long stamp = lock.tryOptimisticRead(); //尝试获取乐观读锁

            //判断是否写入
            int currentCount = count; //获取变量值
            try {
                //业务处理
                TimeUnit.MILLISECONDS.sleep(milli);//模拟读取需要花费20秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //判断时候进入过写模式   可以有效减少一个读锁
            if (!lock.validate(stamp)) { //判断count是否进入写模式
                stamp = lock.readLock(); //已经进入写模式，没办法只能老老实实的获取读锁
                try {
                    currentCount = count; //成功获取到读锁，并重新获取最新的变量值
                } finally {
                    lock.unlockRead(stamp); //释放读锁
                }
            }
            //走到这里，说明count还没有被写，那么可以不用加读锁，减少了读锁的开销
            System.out.println("optimisticRead==" + currentCount); //显示最新的变量值
        });


        //阻塞
        try {
            TimeUnit.MILLISECONDS.sleep(500);//要等一等读锁先获得
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws InterruptedException {
//        long l1 = System.currentTimeMillis();
//        readLock();
//        long l2 = writeLock();
//        System.out.println(l2 - l1);
        long l1 = System.currentTimeMillis();
        optimisticRead();
        long l2 = writeLock();
        System.out.println(l2 - l1);
        TimeUnit.SECONDS.sleep(1);
        service.shutdown();
    }


}
