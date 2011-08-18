package org.chaoticbits.collabcloud.visualizer;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public enum WordExample {

	AM_SIMPLE_NOT_INTERSECT(bigA(), bigM()), AM_INSIDE_NOT_INTERSECT(bigA(), insideM()), AM_INTERSECT(bigA(), intersectM()), AM_INTERSECT2(
			bigA(), intersectM2()), AM_REALLY_CLOSE_NO_INTERSECT(bigA(), reallyCloseM()), APPLES_ORANGES_NO_INTERSECT(apples(),
			orangesNoIntersect()), APPLES_ORANGES_INTERSECT(apples(), oranges());

	public Shape getFirst() {
		return first;
	}

	public Shape getSecond() {
		return second;
	};

	private static Font FONT;
	private static FontRenderContext FRC;

	private static Font getFont() {
		if (FONT == null)
			FONT = new Font("Franklin Gothic", Font.PLAIN, 150);
		return FONT;
	}

	private static FontRenderContext getFRC() {
		if (FRC == null)
			FRC = new FontRenderContext(null, true, true);
		return FRC;
	}

	private final Shape first;
	private final Shape second;

	private WordExample(Shape first, Shape second) {
		this.first = first;
		this.second = second;
	}

	private static Shape bigA() {
		return makeShape(getFont(), getFRC(), "a", 10, 100);
	}

	private static Shape bigM() {
		return makeShape(getFont(), getFRC(), "m", 90, 100);
	}

	private static Shape insideM() {
		Font deriveFont = getFont().deriveFont(25f);
		return makeShape(deriveFont, getFRC(), "m", 38, 85);
	}

	private static Shape intersectM() {
		Font deriveFont = getFont().deriveFont(45f);
		return makeShape(deriveFont, getFRC(), "m", 38, 85);
	}

	private static Shape intersectM2() {
		return makeShape(getFont(), getFRC(), "m", 10, 100);
	}

	private static Shape reallyCloseM() {
		Font deriveFont = getFont().deriveFont(45f);
		return makeShape(deriveFont, getFRC(), "m", 82, 70);
	}

	private static Shape apples() {
		return makeShape(getFont(), getFRC(), "apples", 10, 100);
	}

	private static Shape oranges() {
		return makeShape(getFont(), getFRC(), "oranges", 25, 200);
	}

	private static Shape orangesNoIntersect() {
		Font deriveFont = getFont().deriveFont(100f);
		return makeShape(deriveFont, getFRC(), "oranges", 200, 156);
	}

	private static Shape makeShape(Font font, FontRenderContext frc, String string, float x, float y) {
		char[] chars = string.toCharArray();
		Shape aShape = font.layoutGlyphVector(frc, chars, 0, chars.length, 0).getOutline(x, y);
		return aShape;
	}

	public static void main(String[] args) throws IOException {
		// Output these to buffered images for visual comparison
		for (WordExample eg : WordExample.values()) {
			BufferedImage bi = new BufferedImage(600, 600, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2d = bi.createGraphics();
			g2d.setColor(Color.WHITE);
			g2d.fillRect(0, 0, 600, 600);
			g2d.setColor(Color.BLUE);
			g2d.fill(eg.first);
			g2d.setColor(Color.DARK_GRAY);
			g2d.fill(eg.second);
			ImageIO.write(bi, "PNG", new File("output/" + eg.name() + ".png"));
		}
		System.out.println("Done!");
	}
}
