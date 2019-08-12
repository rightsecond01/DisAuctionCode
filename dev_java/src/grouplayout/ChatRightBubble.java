package grouplayout;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class ChatRightBubble extends JPanel{
	   @Override
	   protected void paintComponent(final Graphics g) {
	      this.setAlignmentX(SwingConstants.RIGHT);
		   final Graphics2D g2d = (Graphics2D) g;
	      g2d.setColor(new Color(255,255,51));
	   }
}
