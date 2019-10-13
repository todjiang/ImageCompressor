package com.iatod.utils.image;

import com.iamtod.utils.image.DocumentCompressorFactory;
import org.junit.Assert;
import org.junit.Test;

public class DocumentCompressorFactoryTest {

    @Test
    public void test() {
        Assert.assertEquals("ImageCompressor",
                DocumentCompressorFactory.findImageCompressor("jpg").getName());

        Assert.assertEquals("PDFCompressor",
                DocumentCompressorFactory.findImageCompressor("pdf").getName());

        Assert.assertEquals("NotSupportedCompressor",
                DocumentCompressorFactory.findImageCompressor("NA").getName());
    }

    @Test
    public void testFindImageCompressorByFileName() {
        Assert.assertEquals("ImageCompressor",
                DocumentCompressorFactory.findImageCompressorByFileName("test.jpg").getName());


        Assert.assertEquals("PDFCompressor",
                DocumentCompressorFactory.findImageCompressorByFileName("xyz.123.pdf").getName());
    }
}
