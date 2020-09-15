package com.mashibing.map;

public class MyMapDemo {

    public static void main(String[] args) {
        MyMap<String,Object> myMap=new MyMap<>();
        myMap.put("sjz","123");
        myMap.put("sjz","1234");
        myMap.put("sjz2","1235");
        System.out.println(myMap.size());
        System.out.println(myMap.get("sjz"));

    }
}
