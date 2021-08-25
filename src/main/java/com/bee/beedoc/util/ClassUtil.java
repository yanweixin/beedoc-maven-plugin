package com.bee.beedoc.util;

public class ClassUtil {

    public static ClassLoader currentClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }
}
