package com.iatod.utils.image;

import com.google.common.io.Files;
import com.iamtod.utils.image.DocumentCompressor;
import com.iamtod.utils.image.DocumentCompressorFactory;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author junjiang
 * @since 2016/9/30
 */
public class ImageCompressorTest {

    @Test
    public void testJPG() throws IOException {
        String inputImageFileName = "Passport-lrg-txt.jpg";
        DocumentCompressor compressor = DocumentCompressorFactory.findImageCompressor("JPG");

        String path = "C:\\Github\\todjiang\\ImageCompressor\\src\\test\\java\\resources\\" + inputImageFileName;

        byte[] imageByteArray = Files.toByteArray(new File(path));

        byte[] compressedImageByteArray = compressor.compress(imageByteArray);

        assertNotNull(compressedImageByteArray);
        assertTrue(compressedImageByteArray.length < imageByteArray.length);

        Files.write(compressedImageByteArray, new File(path.replaceAll("\\.", "_compressed.")));
    }
}
