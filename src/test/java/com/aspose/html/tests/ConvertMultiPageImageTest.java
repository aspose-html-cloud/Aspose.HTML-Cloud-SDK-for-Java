package com.aspose.html.tests;

import com.aspose.html.Configuration;
import com.aspose.html.ConverterBuilder;
import com.aspose.html.JobBuilder;
import com.aspose.html.model.OperationResult;
import com.aspose.html.options.ImageConversionOptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Converting a multi-page HTML document to an image format produces one image
 * per page, returned as a zip that must be named after the requested output
 * file: testfile.png -> testfile.png.zip.
 */
public class ConvertMultiPageImageTest extends BaseTest {

    @BeforeAll
    public static void init() {
        inputFile = Configuration.getTestSrcDir() + File.separator + "html_file_long.html";
        new File(Configuration.getTestDstDir()).mkdirs();
    }

    @ParameterizedTest(name = "{index} - long html -> {0} zip : local -> local")
    @ValueSource(strings = { "png", "jpeg", "bmp", "gif" })
    public void convertLongHtmlToImageReturnsZipNamedAfterOutput(String ext) {
        String outputFile = Configuration.getTestDstDir() + File.separator + "testfile." + ext;

        ImageConversionOptions opt = new ImageConversionOptions()
                .setHeight(300)
                .setWidth(1000)
                .setTopMargin(10)
                .setBottomMargin(10)
                .setLeftMargin(10)
                .setRightMargin(10);

        JobBuilder builder = new ConverterBuilder()
                .fromLocalFile(inputFile)
                .useOptions(opt)
                .saveToLocal(outputFile);

        OperationResult result = api.convert(builder);
        assertEquals("completed", result.status);

        File dst = new File(result.getFile());
        assertTrue(dst.exists() && dst.length() > 0, "Result file is missing or empty: " + dst);

        String name = Paths.get(result.getFile()).getFileName().toString().toLowerCase();
        String expected = ("testfile." + ext + ".zip").toLowerCase();
        assertTrue(name.contains(expected),
                "Expected result file name to contain '" + expected + "', got '" + name + "'");
    }
}
