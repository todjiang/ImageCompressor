package com.iamtod.utils.image;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Convert large PDF file to jpg type, suppose doc size will be reduced
 *
 * @author todjiang
 * @since 7/7/2016
 */
public class PDFCompressor implements DocumentCompressor {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final int PAGE = 0;

	@Override
	public byte[] compress(byte[] byteArrayDoc) {
		logger.info("Start compress pdf, file size: {}", byteArrayDoc.length);

		PDDocument document = null;
		try {
			document = PDDocument.load(byteArrayDoc);
			if (document == null) {
				logger.error("Load PDF document array failed...");
				return new byte[0];
			}
			PDFRenderer pdfRenderer = new PDFRenderer(document);

			// PDF may have multiple pages, but only load the first page
			BufferedImage bim = pdfRenderer.renderImageWithDPI(PAGE, 300, ImageType.RGB);

			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			ImageIO.write(bim, "jpg", byteArrayOutputStream);
			byte[] output = byteArrayOutputStream.toByteArray();
			logger.info("Compressed file successful, size: {}", output.length);

			return output;
		} catch (Exception e) {
			logger.error("Compress pdf failed...", e);
		} finally {
			if (document != null) {
				try {
					document.close();
				} catch (IOException e) {
					// do nothing
				}
			}
		}

		return new byte[0];
	}

	@Override
	public boolean compress(String fileName) {
		return false;
	}

	@Override
	public String getName() {
		return "PDFCompressor";
	}
}
