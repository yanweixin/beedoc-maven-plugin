package com.bee.beedoc;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.util.List;

/**
 * Goal which touches a timestamp file.
 */
@Mojo(name = "sayHi", defaultPhase = LifecyclePhase.PROCESS_SOURCES)
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

    public void execute()
            throws MojoExecutionException {
        if (sourceDiretories == null || sourceDiretories.isEmpty()) {
            throw new MojoExecutionException("source not found");
        }
        for (String directory : sourceDiretories) {
            logger.info(directory);
        }
        logger.info("execute sayHi goal ended");
    }

}
