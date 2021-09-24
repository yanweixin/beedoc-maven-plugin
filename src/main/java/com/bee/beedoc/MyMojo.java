package com.bee.beedoc;

import com.bee.beedoc.parser.ParserUtil;
import com.bee.beedoc.util.IOUtils;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;

import javax.annotation.Nullable;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Goal which touches a timestamp file.
 *
 * @see org.apache.maven.plugin.AbstractMojo
 */
@Mojo(name = "sayHi", defaultPhase = LifecyclePhase.COMPILE, requiresDependencyResolution = ResolutionScope.COMPILE)
public class MyMojo extends AbstractMojo {
    private final Log logger = getLog();
    /**
     * Location of the file
     */
    @Parameter(defaultValue = "${project.build.directory}", property = "outputDir", required = true)
    private File outputDirectory;

    @Parameter(defaultValue = "${project}", readonly = true)
    private MavenProject project;

    @Parameter(defaultValue = "${project.compileSourceRoots}")
    private List<String> sourceDiretories;

    @Parameter(property = "excludeFiles", defaultValue = "${excludeFiles}")
    private List<String> excludeFiles;

    @Override
    public void execute()
            throws MojoExecutionException {
        if (sourceDiretories == null || sourceDiretories.isEmpty()) {
            throw new MojoExecutionException("source not found");
        }
        ClassLoader classLoader = getClassLoader("");
        for (String directory : sourceDiretories) {
            List<Path> paths = IOUtils.getAllFiles(Paths.get(directory));
            for (Path path : paths) {
                File file = path.toFile();
                processJava(file, classLoader);
            }
        }
        logger.info("execute sayHi goal ended");
    }

    private void processJava(File javaFile, ClassLoader classLoader) {
        if (!javaFile.getName().endsWith(".java")) {
            return;
        }
        try {
            CompilationUnit compilationUnit = StaticJavaParser.parse(javaFile);

            ParserUtil parserUtil = new ParserUtil(classLoader);
            parserUtil.walkNodes(compilationUnit);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private ClassLoader getClassLoader(@Nullable String a) {
        try {
            List<String> classpathElements = project.getCompileClasspathElements();
            classpathElements.add(project.getBuild().getOutputDirectory());
            classpathElements.add(project.getBuild().getTestOutputDirectory());
            URL[] urls = new URL[classpathElements.size()];
            for (int i = 0; i < classpathElements.size(); ++i) {
                urls[i] = new File(classpathElements.get(i)).toURI().toURL();
            }
            return new URLClassLoader(urls, getClass().getClassLoader());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
