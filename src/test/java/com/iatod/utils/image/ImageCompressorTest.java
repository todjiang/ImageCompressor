package com.iatod.utils.image;

import com.google.common.io.Files;
import com.google.common.io.Resources;
import com.iamtod.utils.image.DocumentCompressor;
import com.iamtod.utils.image.DocumentCompressorFactory;
import org.junit.After;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author todjiang
 * @since 2016/9/30
 */
public class ImageCompressorTest {

    private static final String OUTPUT_FILE_NAME = "sample_passport_compressed.jpg";

    @After
    public void cleanUp() {
        File fileToDelete = new File(OUTPUT_FILE_NAME);
        if (fileToDelete.exists()) {
            boolean success = fileToDelete.delete();
            assertTrue(success);
        }
    }

    @Test
    public void testJPG() throws IOException {
        final String inputImageFileName = "sample1.jpg";

        DocumentCompressor compressor = DocumentCompressorFactory.findImageCompressorByFileName(inputImageFileName);

        byte[] imageByteArray = Resources.toByteArray(Resources.getResource(inputImageFileName));

        byte[] compressedImageByteArray = compressor.compress(imageByteArray);

        assertNotNull(compressedImageByteArray);

        assertTrue(compressedImageByteArray.length < imageByteArray.length);


        File outputFile = new File(OUTPUT_FILE_NAME);

        Files.write(compressedImageByteArray, outputFile);

        assertNotNull(outputFile);
    }
}
