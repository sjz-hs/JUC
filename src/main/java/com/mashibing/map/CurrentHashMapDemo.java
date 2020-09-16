package com.mashibing.map;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CurrentHashMapDemo{

    public static void main(String[] args) {

        //hashMap 线程不安全

        //hashTable  synchronized
        ConcurrentHashMap map=new ConcurrentHashMap<>();
        Hashtable hashtable=new Hashtable();

        HashMap hashMap=new HashMap();


        //hashTable 整个加锁 锁了整个资源浪费啊

        /**
         * 分段锁
         *  JUC   Segment
         *
         */
    }
}
