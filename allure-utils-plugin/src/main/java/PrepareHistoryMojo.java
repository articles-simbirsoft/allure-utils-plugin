import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Mojo(name = "prepare-history")
public class PrepareHistoryMojo extends AbstractMojo {
    @Parameter(property = "allure-utils-plugin.results.directory", defaultValue = "target/allure-results")
    protected String resultsDirectory;

    @Parameter(property = "allure-utils-plugin.report.baseurl", defaultValue = "..")
    protected String baseUrl;

    @Parameter(property = "allure-utils-plugin.report.base-path", defaultValue = "${project.reporting.outputDirectory}/results")
    protected String outputDirectory;

    @Parameter(property = "allure-utils-plugin.report.lastDir", defaultValue = "last")
    protected String lastDirName;

    @Override
    public void execute() throws MojoExecutionException {
        File outputDir = new File(outputDirectory);
        File resultsDir = new File(resultsDirectory);
        File lastReportDir = new File(outputDir, lastDirName);

        if (!HistoryFilesUtils.isFirstReport(lastReportDir)) {
            try {
                String uniqName = getUniqName();
                HistoryLinkUtils.processAllFiles(
                        new File(lastReportDir, HistoryFilesUtils.HISTORY_DIR_NAME), baseUrl, uniqName);
                HistoryFilesUtils.moveHistoryToResults(lastReportDir, resultsDir);
                HistoryFilesUtils.moveLastReportToUniq(lastReportDir, new File(outputDir, uniqName));
            } catch (IOException e) {
                getLog().error(e);
                throw new MojoExecutionException(e.toString());
            }
        }


    }

    private static String getUniqName() {
        return UUID.randomUUID().toString();
    }
}
