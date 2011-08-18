package org.chaoticbits.collabcloud.visualizer;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public enum TwoLetterExamples {

	AM_SIMPLE_NOT_INTERSECT(bigA(), bigM()), AM_INSIDE_NOT_INTERSECT(bigA(), insideM()), AM_INTERSECT(
			bigA(), intersectM());

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

	private TwoLetterExamples(Shape first, Shape second) {
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

	private static Shape makeShape(Font font, FontRenderContext frc, String string, float x, float y) {
		char[] chars = string.toCharArray();
		Shape aShape = font.layoutGlyphVector(frc, chars, 0, chars.length, 0).getOutline(x, y);
		return aShape;
	}

	public static void main(String[] args) throws IOException {
		// Output these to buffered images for visual comparison
		for (TwoLetterExamples eg : TwoLetterExamples.values()) {
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
