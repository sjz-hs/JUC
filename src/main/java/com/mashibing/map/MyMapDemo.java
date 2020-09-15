package com.mashibing.map;

import java.util.HashMap;

public class MyMapDemo {

    public static void main(String[] args) {
        HashMap hashMap=new HashMap();


        MyMap<String,Object> myMap=new MyMap<>();
        myMap.put("sjz","123");
        myMap.put("sjz","1234");
        myMap.put("sjz2","1235");
        System.out.println(myMap.size());
        System.out.println(myMap.get("sjz"));

    }
}
