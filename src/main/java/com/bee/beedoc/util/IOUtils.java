package com.bee.beedoc.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IOUtils {
    private static final Logger LOGGER = Logger.getLogger(IOUtils.class.getName());

    private IOUtils() {
    }

    public static void touchFile(File dir, String fileName, String content) {
        if (!dir.exists() && !dir.mkdirs()) {
            LOGGER.warning("directory not exists");
            return;
        }
        File touch = new File(dir, fileName);
        try (FileWriter fileWriter = new FileWriter(touch, true);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
             PrintWriter printWriter = new PrintWriter(bufferedWriter)) {
            printWriter.println(content);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "new file failed", e);
        } finally {
            LOGGER.info("finish touch file");
        }
    }

    public static List<Path> getAllFiles(Path dir) {
        List<Path> paths = new ArrayList<>();
        try {
            Files.walkFileTree(dir, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if (attrs.isRegularFile()) {
                        paths.add(file);
                    }
                    return super.visitFile(file, attrs);
                }
            });
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "walk though file error", e);
        }
        return paths;
    }

}
