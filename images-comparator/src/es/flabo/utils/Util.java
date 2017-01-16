package es.flabo.utils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Util {

	private static final String EMPTY = "";

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

	public static double getVariation(File original, File current, File output) {
		try {
			// int r = 255;// red component 0...255
			// int g = 0;// green component 0...255
			// int b = 0;// blue component 0...255
			// int a = 127;// alpha (transparency) component 0...255
			// final int CHANGE_COLOR = (a << 24) | (r << 16) | (g << 8) | b;
			// final int CHANGE_COLOR = (r << 16) | (g << 8) | b;

			BufferedImage bufferCurrent = ImageIO.read(current);
			BufferedImage bufferOriginal = ImageIO.read(original);

			System.out.println("Tiene alpha:" + bufferOriginal.getColorModel().hasAlpha());

			int[][] originalPixels = convertTo2DUsingGetRGB(bufferOriginal);
			int[][] currentPixels = convertTo2DUsingGetRGB(bufferCurrent);

			double variation = 0;
			for (int row = 0; row < bufferOriginal.getHeight(); row++) {
				for (int col = 0; col < bufferOriginal.getWidth(); col++) {
					if (originalPixels[row][col] != currentPixels[row][col]) {
						variation = variation + 1;

						Color c = new Color(bufferCurrent.getRGB(col, row));
						int red = c.getRed();
						int green = c.getGreen();
						int blue = c.getBlue();
						int alpha = c.getAlpha();

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

			if (variation > 0) {
				boolean res = ImageIO.write(bufferCurrent, "PNG", output);
				System.out.println("Changes saved:" + res);
			} else {
				System.out.println("There are no changes");
			}

			bufferCurrent = null;// clean memory
			bufferOriginal = null;// clean memory
			return variation;
		} catch (IOException e) {
			System.out.println(e);
		}
		return Double.MAX_VALUE;
	}

	private static int[][] convertTo2DUsingGetRGB(BufferedImage image) {
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
