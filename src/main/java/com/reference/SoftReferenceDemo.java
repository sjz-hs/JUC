package com.reference;

import java.lang.ref.SoftReference;

/**
 * 内存够用就用 不够就回收
 *
 * 缓存 解决OOM
 */
public class SoftReferenceDemo {

    public static void main(String[] args) throws Exception {
        SoftReference<byte[]> m = new SoftReference<byte[]>(new byte[1024 * 1024 * 5]);
        //m = null;
        System.out.println(m.get());
        System.gc();
        Thread.sleep(500);
        System.out.println(m.get());
        //内存不够会回收 强引用
        byte[] b=new byte[1024*1024*6];
        System.out.println(m.get());

    }
}
