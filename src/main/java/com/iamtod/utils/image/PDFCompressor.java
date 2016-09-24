package com.iamtod.utils.image;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Convert large PDF file to jpg type, suppose doc size will be reduced
 *
 * @author junjiang
 * @since 7/7/2016
 */
public class PDFCompressor implements DocumentCompressor {

	private static final String CLASS_NAME = "PDFCompressor-PDF";

	private static final int PAGE = 0;

	@Override
	public byte[] compress(byte[] byteArrayDoc) {
//		CalTransaction ct = CalUtil.buildTransaction("DocumentCompressor", CLASS_NAME);
//		ct.addData("originalSize", String.valueOf(byteArrayDoc.length));
//		ct.addData("docType", "PDF");

		PDDocument document = null;
		try {
			document = PDDocument.load(byteArrayDoc);
			if (document == null) {
//				CalUtil.warningEvent("LOAD_PDF_FAILED", "Load PDF document array failed...");
			}
			PDFRenderer pdfRenderer = new PDFRenderer(document);

			// PDF may have multiple pages, but only load the first page
			BufferedImage bim = pdfRenderer.renderImageWithDPI(PAGE, 300, ImageType.RGB);

			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			ImageIO.write(bim, "jpg", byteArrayOutputStream);
			byte[] output = byteArrayOutputStream.toByteArray();
//			ct.addData("compressedSize", String.valueOf(output.length));
			return output;
		} catch (Exception e) {
//			CalUtil.exceptionEvent(CLASS_NAME, "Compress image failed...", e);
		} finally {
			if (document != null) {
				try {
					document.close();
				} catch (IOException e) {
				}
			}
		}

		return new byte[0];
	}
}
