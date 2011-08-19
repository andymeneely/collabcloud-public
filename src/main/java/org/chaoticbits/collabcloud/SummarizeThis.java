package org.chaoticbits.collabcloud;

import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import javax.imageio.ImageIO;

import org.apache.log4j.PropertyConfigurator;
import org.chaoticbits.collabcloud.codeprocessor.CloudWeights;
import org.chaoticbits.collabcloud.codeprocessor.Summarizer;
import org.chaoticbits.collabcloud.visualizer.Intersector;
import org.chaoticbits.collabcloud.visualizer.SpiralIterator;

public class SummarizeThis {
	private static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(SummarizeThis.class);

	private static final int WIDTH = 800;
	private static final int HEIGHT = 800;

	/**
	 * @param args
	 * @throws IOException
	 * @throws ParseException
	 */
	public static void main(String[] args) throws ParseException, IOException {
		PropertyConfigurator.configure("log4j.properties");
		CloudWeights weights = getWeights();
		System.out.println("==Weights of this source code==");
		System.out.println(weights);
		layoutWords(weights);
	}

	private static CloudWeights getWeights() throws ParseException, IOException {
		File[] files = new File("src/main/java/org/chaoticbits/collabcloud/codeprocessor").listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(".java");
			}
		});
		CloudWeights weights = new CloudWeights();
		for (File file : files) {
			CompilationUnit unit = JavaParser.parse(file);
			weights = unit.accept(new Summarizer(), weights);
		}
		return weights;
	}

	private static void layoutWords(CloudWeights weights) throws IOException {
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
		List<Entry<String, Double>> entries = weights.sortedEntries();
		for (Entry<String, Double> entry : entries) {
			log.info("Laying out " + entry.getKey() + "...");
			// TODO Convert weights to font sizes
			font = font.deriveFont(35f * (float) Math.log(entry.getValue()));
			char[] chars = entry.getKey().toCharArray();
			GlyphVector glyph = font.layoutGlyphVector(frc, chars, 0, chars.length, 0);
			// TODO Need a placement strategy
			Point2D center = new Point2D.Double(Math.random() * 300 + 250.0f, Math.random() * 300 + 250.0f);
			SpiralIterator itr = new SpiralIterator(center, 200.0d, 50);
			while (itr.hasNext()) {
				boolean canPlace = true;
				Point2D next = itr.next();
				Shape nextShape = glyph.getOutline((float) next.getX(), (float) next.getY());
				// TODO Need a priority queue for quick cache-hits
				for (Shape placed : placedShapes) {
					if (intersector.intersect(nextShape, placed)) {
						canPlace = false;
						int randColor = (int) (Math.random() * 200.0f + 55.0f);
						g2d.setColor(new Color(randColor, randColor, randColor));
						break;
					}
				}
				if (canPlace) {
					g2d.fill(nextShape);
					placedShapes.add(nextShape);
					ImageIO.write(bi, "PNG", new File("output/summarizethis.png"));
					break;
				}
			}
		}
		log.info("Writing image...");
		ImageIO.write(bi, "PNG", new File("output/summarizethis.png"));
		log.info("Done!");
	}

}
