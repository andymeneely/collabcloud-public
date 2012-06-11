package org.chaoticbits.collabcloud.visualizer.intersect.swt;

import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.SWTGraphics;
import org.eclipse.draw2d.graph.Path;
import org.eclipse.draw2d.text.TextFlow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.widgets.Display;

public enum Draw2DWordExample {

	AM_SIMPLE_NOT_INTERSECT(bigA(), bigM()), AM_INSIDE_NOT_INTERSECT(bigA(), insideM()), AM_INTERSECT(bigA(),
			intersectM()), AM_INTERSECT2(bigA(), intersectM2()), AM_REALLY_CLOSE_NO_INTERSECT(bigA(), reallyCloseM()), AM_REALLY_CLOSE_INTERSECT(
			bigA(), reallyCloseMIntersect()), APPLES_ORANGES_NO_INTERSECT(apples(), orangesNoIntersect()), APPLES_ORANGES_INTERSECT(
			apples(), oranges());

	public Path getFirst() {
		return first;
	}

	public Path getSecond() {
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

	private final Path first;
	private final Path second;

	private Draw2DWordExample(Path first, Path second) {
		this.first = first;
		this.second = second;
	}

	private static Path bigA() {
		return makeShape(getFont(), getFRC(), "a", 10, 100);
	}

	private static Path bigM() {
		return makeShape(getFont(), getFRC(), "m", 90, 100);
	}

	private static Path insideM() {
		Font deriveFont = getFont().deriveFont(25f);
		return makeShape(deriveFont, getFRC(), "m", 38, 85);
	}

	private static Path intersectM() {
		Font deriveFont = getFont().deriveFont(45f);
		return makeShape(deriveFont, getFRC(), "m", 38, 85);
	}

	private static Path intersectM2() {
		return makeShape(getFont(), getFRC(), "m", 10, 100);
	}

	private static Path reallyCloseM() {
		Font deriveFont = getFont().deriveFont(45f);
		return makeShape(deriveFont, getFRC(), "m", 82, 70);
	}

	private static Path reallyCloseMIntersect() {
		Font deriveFont = getFont().deriveFont(45f);
		return makeShape(deriveFont, getFRC(), "m", 81, 94);
	}

	private static Path apples() {
		return makeShape(getFont(), getFRC(), "apples", 10, 100);
	}

	private static Path oranges() {
		return makeShape(getFont(), getFRC(), "oranges", 25, 200);
	}

	private static Path orangesNoIntersect() {
		Font deriveFont = getFont().deriveFont(100f);
		return makeShape(deriveFont, getFRC(), "oranges", 200, 156);
	}

	private static Path makeShape(Font font, FontRenderContext frc, String string, float x, float y) {
		throw new IllegalStateException("unimplemented!");
		// char[] chars = string.toCharArray();
		// Path aShape = font.layoutGlyphVector(frc, chars, 0, chars.length, 0).getOutline(x, y);
		// return aShape;
	}

	public static void main(String[] args) throws IOException {
		// Output these to buffered images for visual comparison
		Display display = new Display();
		// final Shell shell = new Shell(display, SWT.SHELL_TRIM | SWT.NO_BACKGROUND | SWT.NO_REDRAW_RESIZE);
		// shell.setSize(800, 800);
		// LightweightSystem lws = new LightweightSystem(shell);
		TextFlow text = new TextFlow("Hello!");
		org.eclipse.swt.graphics.Font font = new org.eclipse.swt.graphics.Font(display, new FontData("Franklin Gothic",
				150, 0));
		text.setFont(font);
		Image image = new Image(display, 800, 800);
		Graphics graphics = new SWTGraphics(new GC(image));
		text.paint(graphics);
		graphics.dispose();
		ImageLoader loader = new ImageLoader();
		loader.data = new ImageData[] { image.getImageData() };
		loader.save("swthello.png", SWT.IMAGE_PNG);

		// for (Draw2DWordExample eg : Draw2DWordExample.values()) {
		// BufferedImage bi = new BufferedImage(600, 600, BufferedImage.TYPE_INT_ARGB);
		// Graphics2D g2d = bi.createGraphics();
		// g2d.setColor(Color.WHITE);
		// g2d.fillRect(0, 0, 600, 600);
		// g2d.setColor(Color.BLUE);
		// g2d.fill(eg.first);
		// g2d.setColor(Color.DARK_GRAY);
		// g2d.fill(eg.second);
		// ImageIO.write(bi, "PNG", new File("output/draw2d" + eg.name() + ".png"));
		// }
		System.out.println("Done!");
	}
}
