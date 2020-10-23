package com.reference;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

// 弱引用  vip

/**
 * ThreadLocal  开始谈 线程本地对象
 * ThreadLocal.set() ==> 当前线程的Map  Entry() extend WeakReference
 *
 *
 * WeakHashMap  k==null  会回收
 *
 *
 * tl ==null 但是存在当前thread==>Map 如果为强引用 就永远不会回收   弱引用只要垃圾回收就会回收  key  value Thread 一直运行 线程中的Map不会回收
 *
 * 防止内存泄漏
 *
 * value  ==> remove()  否则也会内存泄漏
 *
 *     super(k);  （回收key）
 *     value = v;
 *
 */
public class WeakReferenceDemo {
    //只要发生回收就会回收
    public static void main(String[] args) throws InterruptedException {
       Map<Object,M> map=new HashMap<>();
       Object o=new Object();
        M m = new M();
        map.put(o, m);
        o=null;
        System.gc();
        TimeUnit.SECONDS.sleep(2);
        Set<Map.Entry<Object, M>> entries = map.entrySet();
        for (Map.Entry<Object, M> entry : entries) {
            System.out.println(entry.getValue());
        }


        WeakHashMap<Object,M> weakHashMap=new WeakHashMap<>();
        Object o1=new Object();
        M m1 = new M();
        weakHashMap.put(o1,m1);
        o1=null;

        System.gc();

        TimeUnit.SECONDS.sleep(2);

        Set<Map.Entry<Object, M>> entries1 = weakHashMap.entrySet();



        System.out.println(1);
        for (Map.Entry<Object, M> objectMEntry : entries1) {
            System.out.println(objectMEntry.getValue()+"===");
        }
        System.out.println(2);

        //weakQueueKnow();
        //norMalWeekReference();
        //WeakHashMap?  理解
    }

    private static void norMalWeekReference() {
        WeakReference<M> weakReference=new WeakReference<>(new M());
        System.out.println(weakReference.get());
        System.gc();
        System.out.println(weakReference.get());
    }


    private static void weakQueueKnow() throws InterruptedException {
        Object o=new Object();
        ReferenceQueue<Object> queue=new ReferenceQueue<>();
        WeakReference<Object> reference=new WeakReference<>(o,queue);
        System.out.println(o);
        o=null;
        System.gc();
        Thread.sleep(1000);
        System.out.println(o);
        System.out.println("引用:"+reference.get());
        System.out.println("队列:"+queue.poll());
    }
}
