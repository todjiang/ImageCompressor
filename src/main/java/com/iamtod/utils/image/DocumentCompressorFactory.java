package com.iamtod.utils.image;

/**
 * Document Compressor Factory for all support types
 *
 * @author todjiang
 * @since 6/14/2016
 */
public class DocumentCompressorFactory {

	private static final String JPG_TYPE = "JPG";
	private static final String PNG_TYPE = "PNG";
	private static final String PDF_TYPE = "PDF";

	private DocumentCompressorFactory() {}

	/**
	 * Find the image compressor base doc type
	 *
	 * @param imageType jpg, png, pdf
	 * @return
	 */
	public static DocumentCompressor findImageCompressor(String imageType) {
		if (JPG_TYPE.equalsIgnoreCase(imageType)) {
			return new ImageCompressor(JPG_TYPE);
		} else if (PNG_TYPE.equalsIgnoreCase(imageType)) {
			return new ImageCompressor(PNG_TYPE);
		} else if (PDF_TYPE.equalsIgnoreCase(imageType)) {
			return new PDFCompressor();
		} else {
			return new NotSupportedCompressor(imageType);
		}
	}


	public static DocumentCompressor findImageCompressorByFileName(String fileName) {
		return findImageCompressor(fileName.substring(fileName.lastIndexOf('.') + 1));
	}
}
