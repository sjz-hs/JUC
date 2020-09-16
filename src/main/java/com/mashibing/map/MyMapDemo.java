package com.mashibing.map;

import java.util.HashMap;

public class MyMapDemo {

    public static void main(String[] args) {
        HashMap hashMap=new HashMap();
        MyMap<String,Object> myMap=new MyMap<>();
        hashMap.put("sjz","123");
        hashMap.put("sjz","1234");
        hashMap.put(null,"1235");
        Object o = null;
        //System.out.println(o.hashCode());
        System.out.println(hashMap.get(null));
    }
}
