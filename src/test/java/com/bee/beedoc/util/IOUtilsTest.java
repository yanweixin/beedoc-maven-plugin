package com.bee.beedoc.util;

import com.bee.beedoc.BaseTest;
import org.junit.jupiter.api.Test;

import java.io.File;

class IOUtilsTest extends BaseTest {

    @Test
    void touchFile() {
        IOUtils.touchFile(new File("target"), "touch.txt", "hello world!");
    }
}