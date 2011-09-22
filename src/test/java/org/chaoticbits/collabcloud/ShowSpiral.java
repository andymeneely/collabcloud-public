package org.chaoticbits.collabcloud;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.apache.log4j.PropertyConfigurator;
import org.chaoticbits.collabcloud.visualizer.spiral.SpiralIterator;

public class ShowSpiral {
	private static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(ShowSpiral.class);
	private static final int WIDTH = 800;
	private static final int HEIGHT = 800;

	public static void main(String[] args) throws Exception {
		PropertyConfigurator.configure("log4j.properties");
		BufferedImage bi = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = bi.createGraphics();
		g2d.setColor(Color.BLACK);
		SpiralIterator iterator = new SpiralIterator(new Point2D.Double(WIDTH / 2, HEIGHT / 2), 400, 1000, 1.0);
		Point2D last = new Point2D.Double(WIDTH / 2, HEIGHT / 2);
		while (iterator.hasNext()) {
			Point2D next = iterator.next();
			rect(g2d, next);
			line(g2d, last, next);
			last = next;
		}
		ImageIO.write(bi, "PNG", new File("output/spiral.png"));
		log.info("Done.");
	}

	private static void line(Graphics2D g2d, Point2D last, Point2D next) {
		g2d.drawLine((int) last.getX(), (int) last.getY(), (int) next.getX(), (int) next.getY());
	}

	private static void rect(Graphics2D g2d, Point2D next) {
		g2d.fillRect((int) next.getX(), (int) next.getY(), 3, 3);
	}
}
