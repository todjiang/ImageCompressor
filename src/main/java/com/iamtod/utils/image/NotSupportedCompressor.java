package com.iamtod.utils.image;

/**
 * Have a default compressor for those non-supported type and do nothing in compress method
 *
 * @author todjiang
 * @since 6/14/2016
 */
public class NotSupportedCompressor implements DocumentCompressor {

	private String docType;

	public NotSupportedCompressor(String docType) {
		this.docType = docType;
	}

	public String getDocType() {
		return docType;
	}

	@Override
	public byte[] compress(byte[] doc) {
		return new byte[0];
	}

	@Override
	public boolean compress(String fileName) {
		return false;
	}

	@Override
	public String getName() {
		return "NotSupportedCompressor";
	}


}
