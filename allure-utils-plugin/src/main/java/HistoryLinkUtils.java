import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.categoriestrend.Category;
import models.durationtrend.Duration;
import models.history.History;
import models.historytrend.HistoryTrend;
import models.retrytrend.Retry;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;

public final class HistoryLinkUtils {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static final String HISTORY_TREND_FILE_NAME = "history-trend.json";
    private static final String HISTORY_FILE_NAME = "history.json";
    private static final String CATEGORIES_TREND_FILE_NAME = "categories-trend.json";
    private static final String DURATION_TREND_FILE_NAME = "duration-trend.json";
    private static final String RETRY_TREND_FILE_NAME = "retry-trend.json";

    public static String getAbsoluteUrlToIndexHtml(final String baseUrl, final String uniqName) {
        return baseUrl + "/" + uniqName + "/index.html";
    }

    public static void processHistoryTrend(final File historyTrendFile,
                                           final String baseUrl,
                                           final String uniqName,
                                           final File output) throws IOException {
        HistoryTrend[] historyTrends = MAPPER.readValue(historyTrendFile, HistoryTrend[].class);
        historyTrends[0].reportUrl = getAbsoluteUrlToIndexHtml(baseUrl, uniqName);
        historyTrends[0].reportName = uniqName;
        MAPPER.writeValue(output, historyTrends);
    }

    public static void processHistory(final File historyFile,
                                      final String baseUrl,
                                      final String uniqName,
                                      final File output) throws IOException {
        LinkedHashMap<String, History> history = MAPPER.readValue(historyFile,
                new TypeReference<LinkedHashMap<String, History>>() {
                });

        for (String key : history.keySet()) {
            if (history.get(key).items.get(0).reportUrl == null || history.get(key).items.get(0).reportUrl.isEmpty()) {
                history.get(key)
                        .items.get(0).reportUrl = getAbsoluteUrlToIndexHtml(baseUrl, uniqName) + "#suites/" + key + "/"
                        + history.get(key).items.get(0).uid;
            }
        }
        MAPPER.writeValue(output, history);
    }

    public static void processCategoriesTrend(final File categoriesTrendFile,
                                              final String baseUrl,
                                              final String uniqName,
                                              final File output) throws IOException {
        Category[] categories = MAPPER.readValue(categoriesTrendFile, Category[].class);

        categories[0].reportUrl = getAbsoluteUrlToIndexHtml(baseUrl, uniqName);
        categories[0].reportName = uniqName;

        MAPPER.writeValue(output, categories);
    }

    public static void processDurationTrend(final File durationTrendFile,
                                            final String baseUrl,
                                            final String uniqName,
                                            final File output) throws IOException {
        Duration[] durations = MAPPER.readValue(durationTrendFile, Duration[].class);

        durations[0].reportUrl = getAbsoluteUrlToIndexHtml(baseUrl, uniqName);
        durations[0].reportName = uniqName;

        MAPPER.writeValue(output, durations);
    }

    public static void processRetryTrend(final File retryTrendFile,
                                         final String baseUrl,
                                         final String uniqName,
                                         final File output) throws IOException {
        Retry[] retries = MAPPER.readValue(retryTrendFile, Retry[].class);

        retries[0].reportUrl = getAbsoluteUrlToIndexHtml(baseUrl, uniqName);
        retries[0].reportName = uniqName;

        MAPPER.writeValue(output, retries);
    }

    public static void processAllFiles(final File historyDir,
                                       final String baseUrl,
                                       final String uniqName,
                                       final File outputDirectory) throws IOException {
        processHistoryTrend(new File(historyDir, HISTORY_TREND_FILE_NAME),
                baseUrl,
                uniqName,
                new File(outputDirectory, HISTORY_TREND_FILE_NAME));

        processHistory(new File(historyDir, HISTORY_FILE_NAME),
                baseUrl,
                uniqName,
                new File(outputDirectory, HISTORY_FILE_NAME));

        processCategoriesTrend(new File(historyDir, CATEGORIES_TREND_FILE_NAME),
                baseUrl,
                uniqName,
                new File(outputDirectory, CATEGORIES_TREND_FILE_NAME));

        processDurationTrend(new File(historyDir, DURATION_TREND_FILE_NAME),
                baseUrl,
                uniqName,
                new File(outputDirectory, DURATION_TREND_FILE_NAME));

        processRetryTrend(new File(historyDir, RETRY_TREND_FILE_NAME),
                baseUrl,
                uniqName,
                new File(outputDirectory, RETRY_TREND_FILE_NAME));
    }

    public static void processAllFiles(final File historyDir,
                                       final String baseUrl,
                                       final String uniqName) throws IOException {
        processAllFiles(historyDir, baseUrl, uniqName, historyDir);
    }
}
