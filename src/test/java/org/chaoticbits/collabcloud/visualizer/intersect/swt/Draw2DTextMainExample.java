package org.chaoticbits.collabcloud.visualizer.intersect.swt;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.SWTGraphics;
import org.eclipse.draw2d.text.TextFlow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.widgets.Display;

public class Draw2DTextMainExample {
	public static void main(String[] args) {
		System.setProperty("user.home", System.getenv("USERPROFILE"));
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
	}
}
