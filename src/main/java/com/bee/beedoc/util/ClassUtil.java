package com.bee.beedoc.util;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * @author weixin
 */
public class ClassUtil {
    private static final Logger LOGGER = Logger.getLogger(ClassUtil.class.getName());
    private static final String SOURCE_PATH_PREFIX = "src/main/java/";
    private static final String CLASS_FILE_EXTENSION = ".class";
    private static final String JAVA_FILE_EXTENSION = ".java";

    public static ClassLoader currentClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    public static String parsePackage(String path) {
        Objects.requireNonNull(path, "path cannot be null");
        String relativePath = path.substring(path.indexOf(SOURCE_PATH_PREFIX) + SOURCE_PATH_PREFIX.length());
        if (relativePath.endsWith(CLASS_FILE_EXTENSION) || relativePath.endsWith(JAVA_FILE_EXTENSION)) {
            int lastIndex = relativePath.lastIndexOf("/");
            relativePath = lastIndex < 0 ? "" : relativePath.substring(0, lastIndex);
        }
        return String.join(".", relativePath.split("/"));
    }

    public static void printClassLoader(ClassLoader classLoader) {
        if (null == classLoader) {
            return;
        }
        System.out.println("--------------------");
        System.out.println(classLoader);
        if (classLoader instanceof URLClassLoader) {
            URLClassLoader ucl = (URLClassLoader) classLoader;
            int i = 0;
            for (URL url : ucl.getURLs()) {
                System.out.println("url[" + (i++) + "]=" + url);
            }
        }
        printClassLoader(classLoader.getParent());
    }
}
