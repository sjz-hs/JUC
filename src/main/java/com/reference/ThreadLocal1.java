package com.reference;

import java.util.concurrent.TimeUnit;

public class ThreadLocal1 {

   static  ThreadLocal<Person> threadLocal=new ThreadLocal<>();



   //spring 事务连接
    public static void main(String[] args) {
        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(threadLocal.get());
        }).start();
        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            threadLocal.set(new Person("666"));
        }).start();

    }
}
