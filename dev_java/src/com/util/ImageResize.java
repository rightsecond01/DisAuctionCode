package com.util;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ImageResize {
	public static ImageIcon resizeImage(String fileName, int maxWidth, int maxHeight) {

		String data = fileName;
		BufferedImage src, dest;
		ImageIcon icon;
		try {
			src = ImageIO.read(new File(data));
			int width = src.getWidth();
			int height = src.getHeight();
			if (width > maxWidth) {
				float widthRatio = maxWidth / (float) width;
				width = (int) (width * widthRatio);
				height = (int) (height * widthRatio);
			}
			if (height > maxHeight) {
				float heightRatio = maxHeight / (float) height;
				width = (int) (width * heightRatio);
				height = (int) (height * heightRatio);
			}
			dest = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = dest.createGraphics();
			AffineTransform at = AffineTransform.getScaleInstance((double) width / src.getWidth(),
					(double) height / src.getHeight());
			g.drawRenderedImage(src, at);
			icon = new ImageIcon(dest);
			return icon;
		} catch (Exception e) {
			System.out.println("This image can not be resized. Please check the path and type of file.");
			return null;
		}

	}

}
