package com.reference;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;
import java.util.List;

/**
 * 虚引用  管理堆外内存  NIO ===》直接内存（堆外内存）
 *  一个对象指向堆外  当此对象回收时检测  做到回收
 *
 *  JVM   方法区    永久代   <==  1.8  ==>  metaSpace
 *
 */
public class PhantomReferenceDemo {

    private static  final List<Object> LIST=new ArrayList<>();
    private static  final ReferenceQueue<M> QUEUE=new ReferenceQueue<>();
    //虚引用 适合做一些后置处理
    public static void main(String[] args) throws InterruptedException {
        PhantomReference<M> phantomReference=new PhantomReference<>(new M(),QUEUE);
        new Thread(()->{
            while(true){
                LIST.add(new byte[1024*1024]);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
                //虚引用里get不到
                System.out.println(phantomReference.get());
            }

        }).start();
        new Thread(()->{
            while(true){
                Reference<? extends  M> poll=QUEUE.poll();
                if(poll!=null){
                    System.out.println("finish  back"+poll);
                }
            }
        }).start();
        Thread.sleep(500);
    }
}
