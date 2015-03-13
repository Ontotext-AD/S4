package com.ontotext.s4.api.util;

import javax.tools.*;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

/**
 * @author Tsvetan Dimitrov <tsvetan.dimitrov23@gmail.com>
 *         <p/>
 *         Date added: 2015-03-01
 */
public class JarUtils {

    public static void createJar(String source, String jarName) throws IOException {
        Manifest manifest = new Manifest();
        manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
        if (compileSourceFiles(source)) {
            try (JarOutputStream target = new JarOutputStream(new FileOutputStream(jarName), manifest)) {
                add(new File(source), target);
            }
        }
    }

    private static void add(File source, JarOutputStream target) throws IOException {
        if (source.isDirectory()) {
            String name = source.getPath().replace("\\", "/");
            if (!name.isEmpty()) {
                if (!name.endsWith("/"))
                    name += "/";
                JarEntry entry = new JarEntry(name);
                entry.setTime(source.lastModified());
                target.putNextEntry(entry);
                target.closeEntry();
            }
            for (File nestedFile : source.listFiles()) {
                add(nestedFile, target);
            }
            return;
        }

        JarEntry entry = new JarEntry(source.getPath().replace("\\", "/"));
        entry.setTime(source.lastModified());
        target.putNextEntry(entry);

        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(source))) {
            byte[] buffer = new byte[1024];
            while (true) {
                int count = in.read(buffer);
                if (count == -1)
                    break;
                target.write(buffer, 0, count);
            }
            target.closeEntry();
        }
    }

    private static boolean compileSourceFiles(String sourcesFilePath) throws IOException {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        List<Path> files = FileUtils.listFiles(Paths.get(sourcesFilePath));
        List<String> fileNames = new ArrayList<>();
        for (Path file : files) {
            fileNames.add(file.toFile().getPath());
        }
        try (StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null)) {
            Iterable<? extends JavaFileObject> compilationUnits = fileManager
                    .getJavaFileObjectsFromStrings(fileNames);
            JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, null,
                    null, compilationUnits);
            return task.call();
        }
    }
}
