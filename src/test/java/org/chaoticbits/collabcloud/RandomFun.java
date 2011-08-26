package org.chaoticbits.collabcloud;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.imageio.ImageIO;

import org.chaoticbits.collabcloud.visualizer.Intersector;

public class RandomFun {

	private static final int WIDTH = 800;
	private static final int HEIGHT = 800;

	public static void main(String[] args) throws IOException {
		Map<String, Float> map = new LinkedHashMap<String, Float>();
		map.put("apples", 90f);
		map.put("oranges", 90f);

		map.put("bananas", 80f);
		map.put("grapefruit", 80f);

		map.put("fig", 60f);
		map.put("bananas", 60f);
		map.put("plums", 60f);

		map.put("lemon", 40f);
		map.put("kumquat", 40f);
		map.put("nectarine", 40f);
		map.put("pears", 40f);
		map.put("peaches", 40f);

		map.put("blueberry", 30f);
		map.put("ligonberry", 30f);
		map.put("strawberry", 30f);
		map.put("blackberry", 30f);
		map.put("cranberry", 30f);
		map.put("mulberry", 30f);

		map.put("tangerine", 15f);
		map.put("lime", 15f);
		map.put("melon", 15f);
		map.put("canteloupe", 15f);
		map.put("watermelon", 15f);
		map.put("mango", 15f);
		map.put("guava", 15f);
		map.put("passion fruit", 15f);

		BufferedImage bi = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = bi.createGraphics();
		RenderingHints renderHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		renderHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setRenderingHints(renderHints);
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, 800, 800);
		g2d.setColor(Color.DARK_GRAY);
		g2d.drawRect(0, 0, WIDTH - 1, HEIGHT - 1);
		Font font = new Font("Lucida Sans", Font.PLAIN, 150);
		FontRenderContext frc = new FontRenderContext(null, true, true);
		Set<Shape> placedShapes = new HashSet<Shape>();
		Intersector intersector = new Intersector(10, 15.0d);
		for (Entry<String, Float> entry : map.entrySet()) {
			System.out.printf("Laying out %s... ", entry.getKey());
			char[] chars = entry.getKey().toCharArray();
			font = font.deriveFont(entry.getValue());
			GlyphVector glyph = font.layoutGlyphVector(frc, chars, 0, chars.length, 0);
			float startX = (float) (Math.random() * 300 + 150.0f);
			float startY = (float) (Math.random() * 300 + 150.0f);
			boolean rotate = Math.random() > 0.7d;
			for (double rTheta = 0.0; rTheta < 150.0d; rTheta += 0.01) { // Iterate over a spiral
				boolean canPlace = true;
				float x = (float) (rTheta * Math.cos(rTheta) + startX);
				float y = (float) (rTheta * Math.sin(rTheta) + startY);
				// g2d.fillRect((int) x, (int) y, 1, 1);
				// System.out.printf("Trying %s: %f, %f\n", entry.getKey(), x, y);
				Shape nextShape = glyph.getOutline(x, y);
				if (rotate) {
					// System.out.printf(" (rotating %s) ", entry.getKey());
					Rectangle2D bounds2d = nextShape.getBounds2D();
					AffineTransform rotateInstance = AffineTransform.getRotateInstance(3 * Math.PI / 2, bounds2d.getCenterX(),
							bounds2d.getCenterY());
					nextShape = rotateInstance.createTransformedShape(nextShape);
				}
				for (Shape placed : placedShapes) {
					if (intersector.intersect(nextShape, placed)) {
						canPlace = false;
						int randColor = (int) (Math.random() * 200.0f + 25.0f);
						g2d.setColor(new Color(randColor, randColor, randColor));
						break;
					}
				}
				if (canPlace) {
					System.out.println("Got it!");
					g2d.fill(nextShape);
					placedShapes.add(nextShape);
					break;
				}
			}
		}

		ImageIO.write(bi, "PNG", new File("output/randomfun.png"));
		System.out.println("Done!");

	}
}
