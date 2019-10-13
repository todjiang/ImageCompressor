package com.iamtod.utils.image;

/**
 * Document Compressor Interface
 *
 * @author todjiang
 * @since 6/14/2016
 */
public interface DocumentCompressor {

	/**
	 * Compress the document
	 *
	 * @param byteArrayDoc byte array format of document
	 * @return byte array of compressed document
	 */
	byte[] compress(byte[] byteArrayDoc);

	/**
	 * TODO: to be implemented...
	 *
	 * Compress a image file
	 *
	 * @param fileName
	 * @return True --> Compress successful, False --> Compress failed
	 */
	boolean compress(String fileName);


	/**
	 * there may be multiple compressor exists, return the name of compressor
	 *
	 * @return the name of compressor
	 */
	String getName();
}
