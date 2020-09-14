package com.reference;

import java.io.IOException;

public class NormalReferende {

    public static void main(String[] args) throws IOException {

        M m=new M();

        m=null;

        System.gc();

        System.in.read();

    }
}
