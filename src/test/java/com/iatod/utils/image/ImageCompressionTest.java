package com.iatod.utils.image;

import javax.imageio.*;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Iterator;

/**
 * Local image file compression test
 * Compress a image file which is more than 5MB
 * Copy image meta data to the new compressed image
 *
 * @author junjiang
 * @since 4/5/2016
 */
public class ImageCompressionTest {

	public static void main(String[] args)  {
		// set to a local image path
		File imageFile = new File("C:\\Users\\junjiang\\Pictures\\carson-passport.jpg");
		File compressedImageFile = new File("compressed_file.jpg");
		try {
			//build image writers
			Iterator<ImageWriter> imageWriters = ImageIO.getImageWritersByFormatName("jpg");
			if (!imageWriters.hasNext()) {
				throw new IllegalStateException("Writers Not Found!!");
			}
			ImageWriter imageWriter = imageWriters.next();

//			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//			MemoryCacheImageOutputStream imageOutputStream = new MemoryCacheImageOutputStream(outputStream);
			OutputStream outputStream = new FileOutputStream(compressedImageFile);
			ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(outputStream);
			imageWriter.setOutput(imageOutputStream);

			ImageWriteParam imageWriteParam = imageWriter.getDefaultWriteParam();
			//Set the compress quality metrics
			imageWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);

			float imageQuality = 0.5f;

			imageWriteParam.setCompressionQuality(imageQuality);

			InputStream inputStream = new FileInputStream(imageFile);
			//Create the buffered image
			BufferedImage bufferedImage = ImageIO.read(inputStream);

			// build image meta data
			ImageInputStream imageInputStream = ImageIO.createImageInputStream(imageFile);
			Iterator<ImageReader> iterator = ImageIO.getImageReaders(imageInputStream);
			ImageReader reader = iterator.next();
			reader.setInput(imageInputStream);
			IIOMetadata iIOMetadata = reader.getImageMetadata(0);

			try {
				imageWriter.write(null, new IIOImage(bufferedImage, null, iIOMetadata), imageWriteParam);

//				byte[] output = outputStream.toByteArray();
//				System.out.println("compressedSize" + String.valueOf(output.length) );
//				return output;
			} finally {
				// close all resource
				inputStream.close();
				imageInputStream.close();
				outputStream.close();
				imageOutputStream.close();
				imageWriter.dispose();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


}
