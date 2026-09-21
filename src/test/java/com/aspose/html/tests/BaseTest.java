package com.aspose.html.tests;

import com.aspose.html.Configuration;
import com.aspose.html.HtmlApi;

public class BaseTest {
    public static HtmlApi api;
    public static String inputFile;
    public static String inputUrl;

    /**
     * Reads a credential from the environment, falling back to the literal.
     * Lets CI (or the SDK test agent) run against its own subscription without
     * editing this file, while a plain local checkout keeps working.
     */
    private static String credential(String primary, String secondary, String fallback) {
        String value = System.getenv(primary);
        if (value != null && !value.isEmpty()) return value;
        value = System.getenv(secondary);
        if (value != null && !value.isEmpty()) return value;
        return fallback;
    }

    static {
        Configuration.setBasePath("https://api.aspose.cloud");
        Configuration.setAuthPath("https://api.aspose.cloud/connect/token");
        Configuration.setUserAgent("WebKit");
        Configuration.setDebug(true);
        Configuration.setTestSrcDir("sourceTest");
        Configuration.setTestDstDir("destTest");
        api = new HtmlApi(
                credential("ASPOSE_CLIENT_SECRET", "APP_KEY", "c8dda7d6445d82635b8797d1c8edd153"),
                credential("ASPOSE_CLIENT_ID", "APP_SID", "2225baa2-097b-4731-9831-d0d56c28230f"));
    }
}
