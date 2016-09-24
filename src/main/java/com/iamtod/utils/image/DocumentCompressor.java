package com.iamtod.utils.image;

/**
 * Document Compressor Interface
 *
 * @author junjiang
 * @since 6/14/2016
 */
public interface DocumentCompressor {

	/**
	 *
	 * @param byteArrayDoc byte array format of document
	 * @return byte array of compressed document
	 */
	byte[] compress(byte[] byteArrayDoc);
}
