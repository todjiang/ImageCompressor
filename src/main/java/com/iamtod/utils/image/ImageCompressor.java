package com.iamtod.utils.image;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.MemoryCacheImageOutputStream;
import java.awt.image.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;

/**
 *  Image Compressor for JPG and PNG, the image meta data included in compressed file as well
 *
 * @author todjiang
 * @since 6/14/2016
 */
public class ImageCompressor implements DocumentCompressor {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final String IMAGE_TYPE = "jpg";

	/**
	 * 	This ratio that indicating the desired quality level, it is between 0 and 1
 	 */
	private static final float IMAGE_QUALITY_LEVEL = 0.5f;

	private String docType;

	/**
	 *
	 * @param docType document type: JPG, PNG
	 */
	public ImageCompressor(String docType) {
		this.docType = docType;
	}


	@Override
	public byte[] compress(byte[] byteArrayDoc) {
		logger.info("Start compress {}, file size: {}", docType, byteArrayDoc.length);

		// Build image writers
		Iterator<ImageWriter> imageWriters = ImageIO.getImageWritersByFormatName(IMAGE_TYPE);
		if (!imageWriters.hasNext()) {
			throw new IllegalStateException("Writers Not Found!!");
		}
		ImageWriter imageWriter = imageWriters.next();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		MemoryCacheImageOutputStream imageOutputStream = new MemoryCacheImageOutputStream(outputStream);
		imageWriter.setOutput(imageOutputStream);

		ImageWriteParam imageWriteParam = imageWriter.getDefaultWriteParam();
		//Set the compress quality metrics
		imageWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		imageWriteParam.setCompressionQuality(IMAGE_QUALITY_LEVEL);

		try {
			// ByteArrayInputStream here can not be reused by buildImageMetaData method
			BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(byteArrayDoc));

			IIOMetadata iIOMetadata = buildImageMetaData(byteArrayDoc);

			imageWriter.write(null, new IIOImage(bufferedImage, null, iIOMetadata), imageWriteParam);
			byte[] output = outputStream.toByteArray();
			logger.info("Compressed file successful, size: {}", output.length);

			return output;
		} catch (Exception e) {
			logger.error("Compress image failed...", e);
		} finally {
			try {
				outputStream.close();
				imageOutputStream.close();
			} catch (IOException ioe) {
				logger.error("Exception in resource releasing...", ioe);
			}
			imageWriter.dispose();
		}

		// return empty byte array instead of null
		return new byte[0];
	}

	@Override
	public boolean compress(String fileName) {
		return false;
	}

	@Override
	public String getName() {
		return "ImageCompressor";
	}

	/**
	 * Build image meta data
	 *
	 * @param byteArrayDoc
	 * @return
	 */
	private IIOMetadata buildImageMetaData(byte[] byteArrayDoc) {
		ImageInputStream imageInputStream = null;
		IIOMetadata iIOMetadata = null;
		try {
			imageInputStream = ImageIO.createImageInputStream(new ByteArrayInputStream(byteArrayDoc));
			Iterator<ImageReader> readers = ImageIO.getImageReaders(imageInputStream);
			if (readers.hasNext()) {
				ImageReader reader = readers.next();
				reader.setInput(imageInputStream);
				//  read metadata of first image
				iIOMetadata = reader.getImageMetadata(0);
			}
		} catch (IOException e) {
			logger.error("Failed to build image metadata...", e);
		} finally {
			// close all resource
			if (imageInputStream != null) {
				try {
					imageInputStream.close();
				} catch (IOException e) {
					// do nothing
				}
			}
		}

		return iIOMetadata;
	}
}
