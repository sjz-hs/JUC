package com.mashibing.juc.c_021_03_VarHandle;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;

public class VarHandleTest {
    private String plainStr;    //普通变量
    private static String staticStr;    //静态变量
    private String reflectStr;    //通过反射生成句柄的变量
    private String[] arrayStr = new String[10];    //数组变量
 
    private static final VarHandle plainVar;    //普通变量句柄
    private static final VarHandle staticVar;    //静态变量句柄
    private static final VarHandle reflectVar;    //反射字段句柄
    private static final VarHandle arrayVar;    //数组句柄
 
    static {
        try {
            MethodHandles.Lookup l = MethodHandles.lookup();
            plainVar = l.findVarHandle(VarHandleTest.class, "plainStr", String.class);
            staticVar = l.findStaticVarHandle(VarHandleTest.class, "staticStr", String.class);
            reflectVar = l.unreflectVarHandle(VarHandleTest.class.getDeclaredField("reflectStr"));
            arrayVar = MethodHandles.arrayElementVarHandle(String[].class);
        } catch (ReflectiveOperationException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public void mainTest() {
        plainVar.set(this, "I am a plain string");    //实例变量的普通write操作
        staticVar.set("I am a static string");    //    静态变量的普通write操作
        reflectVar.set(this, "I am a string created by reflection");    //反射字段的普通write操作
        arrayVar.set(arrayStr, 0, "I am a string array element");    //数组变量的普通write操作

        String plainString = (String) plainVar.get(this);    //实例变量的普通read操作
        String staticString = (String) staticVar.get();    //    静态变量的普通read操作
        String reflectString = (String) reflectVar.get(this);    //反射字段的普通read操作
        String arrayStrElem = (String) arrayVar.get(arrayStr, 0);    //数组变量的普通read操作， 第二个参数是指数组下标，即第0个元素
    }

    public static void main(String[] args) {
        VarHandleTest varHandleTest=new VarHandleTest();
        varHandleTest.mainTest();
    }
 
}