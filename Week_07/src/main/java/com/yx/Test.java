package com.yx;

import java.lang.ref.SoftReference;

/**
 * @author yangxing
 * @version 1.0
 * @date 2021/1/9 0009 14:55
 */
public class Test {

    public static void main(String[] args) {
        Object a = new Object();
        SoftReference<Object> reference = new SoftReference(a);
        if (reference.get() != null){
            a = reference.get();
        } else {
            a = new Object();
            reference = new SoftReference<>(a);
        }

    }
}
