package com.mashibing.unsafe;

import sun.misc.Unsafe;

import java.io.IOException;
import java.lang.reflect.Field;

public class Person {
    private int i=0;

    private static  Unsafe UNSAFE;

    private static long I_OFFSET;
    static {
        try {
            //反射获取
            Field field=Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            UNSAFE= (Unsafe) field.get(null);
            I_OFFSET=UNSAFE.objectFieldOffset(Person.class.getDeclaredField("i"));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
       Person person=new Person();

        new Thread(()->{
            System.out.println(I_OFFSET);
            while (true){
                boolean b = UNSAFE.compareAndSwapInt(person, I_OFFSET, person.i, person.i+1);
                if(b){
                    System.out.println(UNSAFE.getIntVolatile(person,I_OFFSET));
                }else{
                    System.out.println("false:"+UNSAFE.getIntVolatile(person,I_OFFSET));
                }
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }
        }).start();

        new Thread(()->{
            while (true){
                boolean b = UNSAFE.compareAndSwapInt(person, I_OFFSET, person.i, person.i+1);
                if(b){
                    System.out.println(UNSAFE.getIntVolatile(person,I_OFFSET));
                }else{
                    System.out.println("false:"+UNSAFE.getIntVolatile(person,I_OFFSET));
                }
            }
        }).start();
        System.in.read();

    }

}
