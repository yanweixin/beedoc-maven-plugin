package com.bee.beedoc.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

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
        try (FileWriter fileWriter = new FileWriter(touch)) {
            fileWriter.write(content);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "new file failed", e);
        } finally {
            LOGGER.info("finish touch file");
        }
    }

//    public static List<File> getAllFiles(Path dir) {
//        List<File> files = new ArrayList<>();
//        try {
//            files = Files.find(dir, 10, ((path, basicFileAttributes) -> basicFileAttributes.isRegularFile()))
//                    .map(Path::toFile).collect(Collectors.toList());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return files;
//    }


}
