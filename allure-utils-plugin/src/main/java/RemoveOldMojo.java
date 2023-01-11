import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.IOException;

@Mojo(name = "remove-old")
public class RemoveOldMojo extends AbstractMojo {
    @Parameter(property = "allure-utils-plugin.report.baseurl", defaultValue = "${project.reporting.outputDirectory}/results")
    protected String outputDirectory;

    @Parameter(property = "allure-utils-plugin.report.maxReports", defaultValue = "21")
    protected int maxReports;

    @Override
    public void execute() throws MojoExecutionException {
        File[] sortedReports = HistoryFilesUtils.getSortedByDateReports(new File(outputDirectory));
        for (int i = maxReports; i < sortedReports.length; i++) {
            try {
                FileUtils.deleteDirectory(sortedReports[i]);
            } catch (IOException e) {
                getLog().error(e);
                throw new MojoExecutionException(e.toString());
            }
        }
    }
}
