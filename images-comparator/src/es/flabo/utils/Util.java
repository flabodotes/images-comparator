package es.flabo.utils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Util {
	

	/**
	 * Normalize names
	 * 
	 * @param name
	 * @return Replace white spaces with _
	 */
	public static String normalize(String name) {
		return name.replaceAll(" ", "_");
	}

	/**
	 * Create directories
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static String makeDirs(String path) throws IOException {
		File out = new File(path);
		if (!out.exists()) {
			if (!out.mkdirs()) {
				throw new IOException("Couldn't create path: " + path);
			}
		}
		return out.getCanonicalPath();
	}

	public static int getVariation(File original, File current, File output) {
		int variation = 0;
		try {
			BufferedImage bufferCurrent = ImageIO.read(current);
			BufferedImage bufferOriginal = ImageIO.read(original);

			//System.out.println("Has alpha channel:" + bufferOriginal.getColorModel().hasAlpha());

			int[][] originalPixels = convertTo2D(bufferOriginal);
			int[][] currentPixels = convertTo2D(bufferCurrent);
			
			for (int row = 0; row < bufferOriginal.getHeight(); row++) {
				for (int col = 0; col < bufferOriginal.getWidth(); col++) {
					if (originalPixels[row][col] != currentPixels[row][col]) {
						variation = variation + 1;

						Color currentColor = new Color(bufferCurrent.getRGB(col, row));
						int red = currentColor.getRed();
						int green = currentColor.getGreen();
						int blue = currentColor.getBlue();
						//int alpha = c.getAlpha();

						final int RED_COLOR = (0 << 24) | (255 << 16) | (green << 8) | blue;
						final int BLACK_COLOR = (0 << 24) | (0 << 16) | (green << 8) | blue;

						if (red >= 225) {
							bufferCurrent.setRGB(col, row, BLACK_COLOR);
						} else {
							bufferCurrent.setRGB(col, row, RED_COLOR);
						}
					}
				}
			}

			if (variation > 0 && output!=null) {
				ImageIO.write(bufferCurrent, "PNG", output);				
			}

			bufferCurrent = null;// clean memory
			bufferOriginal = null;// clean memory
			
		} catch (IOException e) {
			System.out.println(Controller.PROCESS_ERROR_CODE);
		}
		return variation;
	}

	private static int[][] convertTo2D(BufferedImage image) {
		int width = image.getWidth();
		int height = image.getHeight();
		int[][] result = new int[height][width];

		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				result[row][col] = image.getRGB(col, row);
			}
		}

		return result;
	}

}
