package com.aspose.html.tests;

import com.aspose.html.ApiClient;
import com.aspose.html.Configuration;
import com.aspose.html.ConverterBuilder;
import com.aspose.html.JobBuilder;
import com.aspose.html.VectorizationBuilder;
import com.aspose.html.api.StorageApi;
import com.aspose.html.model.FilesUploadResult;
import com.aspose.html.model.ObjectExist;
import com.aspose.html.model.OperationResult;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import retrofit2.Response;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Storage -> storage conversion and vectorization must write the result to
 * exactly the storage path the user requested. The output path is given the
 * way a user would write it: a relative path without a leading '/'.
 */
public class ConvertStorageToStorageTest extends BaseTest {

    private static final String FOLDER = "StorageToStorageTest";
    private static StorageApi storageApi;

    @BeforeAll
    public static void init() throws IOException {
        storageApi = new ApiClient().createService(StorageApi.class);
        upload("test1.html");
        upload("car.png");
    }

    @AfterAll
    public static void cleanup() throws IOException {
        storageApi.deleteFolder(FOLDER, null, true).execute();
    }

    private static void upload(String fileName) throws IOException {
        File f = new File(Configuration.getTestSrcDir(), fileName);
        RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"), f);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", f.getName(), body);
        Response<FilesUploadResult> res = storageApi.uploadFile(part, FOLDER, null).execute();
        assertTrue(res.isSuccessful(), "Upload of " + fileName + " failed: HTTP " + res.code());
        assertTrue(res.body().getErrors() == null || res.body().getErrors().isEmpty(),
                "Upload of " + fileName + " reported errors");
    }

    private static void assertStorageFileExists(String path) throws IOException {
        Response<ObjectExist> res = storageApi.objectExists(path, null, null).execute();
        assertTrue(res.isSuccessful(), "objectExists failed: HTTP " + res.code());
        ObjectExist body = res.body();
        assertNotNull(body);
        assertTrue(body.isExists(), "Result not found at the requested storage path '" + path + "'");
        assertFalse(body.isFolder(), "'" + path + "' must be a file, not a folder");
    }

    @Test
    public void convertHtmlStorageToStorageLandsAtRequestedPath() throws IOException {
        String outputFile = FOLDER + "/storageToStorage.pdf";

        JobBuilder builder = new ConverterBuilder()
                .fromStorageFile(FOLDER + "/test1.html")
                .saveToStorage(outputFile);

        OperationResult result = api.convert(builder);
        assertEquals("completed", result.status);
        assertStorageFileExists(outputFile);
    }

    @Test
    public void vectorizeImageStorageToStorageLandsAtRequestedPath() throws IOException {
        String outputFile = FOLDER + "/vectorizeStorageToStorage.svg";

        JobBuilder builder = new VectorizationBuilder()
                .fromStorageFile(FOLDER + "/car.png")
                .saveToStorage(outputFile);

        OperationResult result = api.vectorize(builder);
        assertEquals("completed", result.status);
        assertStorageFileExists(outputFile);
    }
}
