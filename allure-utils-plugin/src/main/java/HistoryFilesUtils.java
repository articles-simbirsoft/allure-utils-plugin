import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;

public final class HistoryFilesUtils {
    public static final String HISTORY_DIR_NAME = "history";

    public static boolean isFirstReport(final File lastReportDir) {
        return !lastReportDir.exists();
    }

    public static void moveHistoryToResults(final File lastReportDir, final File resultsDir) throws IOException {
        File historyDir = new File(resultsDir, HISTORY_DIR_NAME);
        if (historyDir.exists()) {
            FileUtils.deleteDirectory(historyDir);
        }
        FileUtils.moveDirectory(new File(lastReportDir, HISTORY_DIR_NAME), historyDir);
    }

    public static void moveLastReportToUniq(final File lastReportDir, final File uniqNameDir) throws IOException {
        if (uniqNameDir.exists()) {
            throw new IllegalArgumentException("uniqNameDir");
        }
        FileUtils.moveDirectory(lastReportDir, uniqNameDir);
    }

    public static File[] getSortedByDateReports(final File reportsDir) {
        File[] reports = reportsDir.listFiles(File::isDirectory);
        if (reports != null) {
            return Arrays.stream(reports).sorted((o1, o2) -> {
                try {
                    BasicFileAttributes ao1 =
                            Files.readAttributes(o1.toPath(), BasicFileAttributes.class);
                    BasicFileAttributes ao2 =
                            Files.readAttributes(o2.toPath(), BasicFileAttributes.class);
                    return ao2.creationTime().compareTo(ao1.creationTime());
                } catch (IOException e) {
                    return 0;
                }
            }).toArray(File[]::new);
        }
        return new File[0];
    }
}
