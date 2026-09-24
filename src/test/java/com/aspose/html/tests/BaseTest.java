package com.aspose.html.tests;

import com.aspose.html.Configuration;
import com.aspose.html.HtmlApi;

public class BaseTest {
    public static HtmlApi api;
    public static String inputFile;
    public static String inputUrl;

    /**
     * Reads a credential from the environment. Credentials are never stored in
     * the repository: set ASPOSE_CLIENT_ID and ASPOSE_CLIENT_SECRET (or the
     * APP_SID / APP_KEY aliases) before running the suite; the SDK test agent
     * does this automatically.
     */
    private static String credential(String primary, String secondary) {
        String value = System.getenv(primary);
        if (value != null && !value.isEmpty()) return value;
        value = System.getenv(secondary);
        if (value != null && !value.isEmpty()) return value;
        throw new IllegalStateException("Missing Aspose Cloud credentials: set "
                + primary + " or " + secondary + " in the environment.");
    }

    static {
        Configuration.setBasePath("https://api.aspose.cloud");
        Configuration.setAuthPath("https://api.aspose.cloud/connect/token");
        Configuration.setUserAgent("WebKit");
        Configuration.setDebug(true);
        Configuration.setTestSrcDir("sourceTest");
        Configuration.setTestDstDir("destTest");
        api = new HtmlApi(
                credential("ASPOSE_CLIENT_SECRET", "APP_KEY"),
                credential("ASPOSE_CLIENT_ID", "APP_SID"));
    }
}
