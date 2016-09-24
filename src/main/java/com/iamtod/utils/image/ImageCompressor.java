package com.iamtod.utils.image;

import javax.imageio.*;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.MemoryCacheImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Iterator;

/**
 *  Image Compressor for JPG and PNG
 *
 * @author junjiang
 * @since 6/14/2016
 */
public class ImageCompressor implements DocumentCompressor {

	private static final String IMAGE_TYPE = "jpg";
	// This ratio that indicating the desired quality level, it is between 0 and 1
	public static final float IMAGE_QUALITY_LEVEL = 0.5f;

	private String docType;
	private String className;

	/**
	 *
	 * @param docType document type: JPG, PNG
	 */
	public ImageCompressor(String docType) {
		this.docType = docType;
		this.className = "ImageCompressor-" + docType;
	}


	@Override
	public byte[] compress(byte[] byteArrayDoc) {
//		CalTransaction ct = CalUtil.buildTransaction("DocumentCompressor", className);
//		ct.addData("originalSize", String.valueOf(byteArrayDoc.length));
//		ct.addData("docType", docType);
		// TODO:  add log

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
//			ct.addData("compressedSize", String.valueOf(output.length));
			return output;
		} catch (Exception e) {
//			CalUtil.exceptionEvent(className, "Compress image failed...", e);

		} finally {
			try {
				outputStream.close();
				imageOutputStream.close();
			} catch (IOException e) {
//				CalUtil.warningEvent(className + "_RESOURCE_RELEASE_FAILED",
//					"Exceptions happened resource release...");
			}
			imageWriter.dispose();
		}

		// return empty byte array instead of null
		return new byte[0];
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
//			CalUtil.infoEvent("FAILED_TO_BUILD_IMAGE_METADATA", "Failed to build image metadata...");
		} finally {
			// close all resource
			if (imageInputStream != null)
				try {
					imageInputStream.close();
				} catch (IOException e) {
				}
		}

		return iIOMetadata;
	}
}
