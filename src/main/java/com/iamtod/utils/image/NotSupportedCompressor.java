package com.iamtod.utils.image;

/**
 * Have a default compressor for those non-supported type and do nothing in compress method
 *
 * @author junjiang
 * @since 6/14/2016
 */
public class NotSupportedCompressor implements DocumentCompressor {

	/**
	 * Print CAL in Not supported document type
	 *
	 */

	public NotSupportedCompressor() {
	}

	@Override
	public byte[] compress(byte[] doc) {
		return new byte[0];
	}

	@Override
	public boolean compress(String fileName) {
		return false;
	}
}
