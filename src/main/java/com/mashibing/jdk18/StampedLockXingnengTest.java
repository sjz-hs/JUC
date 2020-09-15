package com.mashibing.jdk18;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.StampedLock;

public class StampedLockXingnengTest {
    static ExecutorService service = Executors.newCachedThreadPool();
    static StampedLock lock = new StampedLock();
    static int count = 0;
    public static void main(String[] args) throws InterruptedException {
        long begin  = System.currentTimeMillis();
        List<Callable<Object>> list = new ArrayList<>();
        for(int i = 0;i < 10000;i++){
            list.add(() -> {
                //readLock();
                optimisticRead();
                return null;
            });
        }
        service.invokeAll(list);
        long end  = System.currentTimeMillis();
        System.out.println(end-begin);
        //乐观95 91 102 98 92
        //悲观265 253 293 265 287
    }


    private static void readLock() {//普通的读锁
        int currentCount = 0;
        long stamp = lock.readLock(); //获取排他读锁
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlockRead(stamp); //释放读锁
        }
    }


    private static void optimisticRead() {
        long stamp = lock.tryOptimisticRead(); //尝试获取乐观读锁
        int currentCount = count; //获取变量值
        if (!lock.validate(stamp)) { //判断count是否进入写模式
            stamp = lock.readLock(); //已经进入写模式，没办法只能老老实实的获取读锁
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlockRead(stamp); //释放读锁
            }
        }
    }
}
