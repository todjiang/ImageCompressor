package com.iatod.utils.image;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author junjiang
 * @since 7/13/2016
 */
public class PDFTest {
	public static void main(String[] args) throws IOException {
		String pdfFilename = "C:\\Github_pp\\Risk-R\\documentverificationserv\\reduced.pdf";
		PDDocument document = PDDocument.load(new File(pdfFilename));
		PDFRenderer pdfRenderer = new PDFRenderer(document);
		for (int page = 0; page < document.getNumberOfPages(); ++page) {
			BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB);

			// suffix in filename will be used as the file format
			ImageIOUtil.writeImage(bim, pdfFilename + "-" + (page+1) + ".jpg", 300);
		}
		document.close();
	}
}
