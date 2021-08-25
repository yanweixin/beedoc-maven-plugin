package com.bee.beedoc.util;

import com.bee.beedoc.util.IOUtils;
import org.junit.jupiter.api.Test;

import java.io.File;

class IOUtilsTest {

    @Test
    void touchFile() {
        IOUtils.touchFile(new File("target"), "touch.txt", "hello world!");
    }
}