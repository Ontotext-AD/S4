/*
* Copyright (c) 2015
*
* This file is part of the s4.ontotext.com REST client library, and is
* licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.ontotext.s4.api.util;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Tsvetan Dimitrov <tsvetan.dimitrov@ontotext.com>
 * <p/>
 * Date added: 2/23/15.
 */
public class FileUtils {

    private static class CopyFileVisitor extends SimpleFileVisitor<Path> {

        private final Path targetPath;
        private Path sourcePath = null;

        public CopyFileVisitor(Path targetPath) {
            this.targetPath = targetPath;
        }

        @Override
        public FileVisitResult preVisitDirectory(final Path dir, final BasicFileAttributes attrs) throws IOException {
            if (sourcePath == null) {
                sourcePath = dir;
            } else {
                Files.createDirectories(targetPath.resolve(sourcePath.relativize(dir)));
            }
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
            Files.copy(file, targetPath.resolve(sourcePath.relativize(file)));
            return FileVisitResult.CONTINUE;
        }
    }

//    public static void copy(String source, String target) throws IOException {
//        Path destDir = Files.createDirectories(Paths.get(target));
//        for (File sourceFile : listFiles(source)) {
//            Path sourcePath = sourceFile.toPath();
//            Files.copy(sourcePath, destDir.resolve(sourcePath.getFileName()));
//        }
//    }

    public static void copyDirectoryStructure(String source, String target) throws IOException {
        Files.walkFileTree(Paths.get(source), new CopyFileVisitor(Paths.get(target)));
    }

    public static String readFileToString(Path path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(path);
        return new String(encoded, encoding);
    }

//    public static List<File> listFiles(String filePath) {
//        File sourceDir = new File(filePath);
//        if (sourceDir.exists()) {
//            final File[] fileArray = sourceDir.listFiles();
//            if (fileArray != null) {
//                return Arrays.asList(fileArray);
//            }
//        }
//        return null;
//    }


    public static List<Path> listFiles(Path path) throws IOException {
        Deque<Path> stack = new ArrayDeque<>();
        final List<Path> files = new LinkedList<>();

        stack.push(path);

        while (!stack.isEmpty()) {
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(stack.pop())) {
                for (Path entry : stream) {
                    if (Files.isDirectory(entry)) {
                        stack.push(entry);
                    } else {
                        files.add(entry);
                    }
                }
            }
        }

        return files;
    }
}
