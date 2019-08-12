package com.ch2;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.util.ImageResize;

public class Main {

	public static void main(String[] args) {
		String profilePath = "C:\\workspace_java\\dev_java\\src\\com\\images\\profile\\";
		File t = new File(profilePath + "1563952379783.png");
		
		String tmpPath = t.getParent();
		String tmpFileName = t.getName();
		int extPosition = tmpFileName.indexOf("png");

		if(extPosition != -1){
			String fullPath = tmpPath+"/"+tmpFileName;
			ImageIcon ic = ImageResize.resizeImage(fullPath, 100, 100);
			Image i = ic.getImage();
			BufferedImage bi = new BufferedImage(i.getWidth(null), i.getHeight(null), BufferedImage.TYPE_INT_RGB);

			Graphics2D g2 = bi.createGraphics();
			g2.drawImage(i, 0, 0, null);
			g2.dispose();

			String newFileName = tmpFileName.replaceFirst(".png", "_resize.png");
			String newPath = tmpPath + "\\ImageResize"+"\\"+newFileName;
			try {
				ImageIO.write(bi, "png", new File(newPath));
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println(newPath);
		}


	}

}
