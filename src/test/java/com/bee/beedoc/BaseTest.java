package com.bee.beedoc;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class BaseTest {
    protected final Logger logger = Logger.getLogger(BaseTest.class.getName());

    @Test
    public void test() throws FileNotFoundException {
        File javaFile = Paths.get("src/main/java/com/bee/beedoc/MyMojo.java").toFile();
        CompilationUnit compilationUnit = StaticJavaParser.parse(javaFile);
        System.out.println(compilationUnit.toString());
    }
}
