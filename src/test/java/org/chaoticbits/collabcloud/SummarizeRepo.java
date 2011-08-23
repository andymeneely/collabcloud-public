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
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.imageio.ImageIO;

import org.apache.log4j.PropertyConfigurator;
import org.chaoticbits.collabcloud.codeprocessor.CloudWeights;
import org.chaoticbits.collabcloud.codeprocessor.java.JavaSummarizeVisitor;
import org.chaoticbits.collabcloud.vc.git.GitLoader;
import org.chaoticbits.collabcloud.vc.git.GitLoaderTest;
import org.chaoticbits.collabcloud.visualizer.Intersector;
import org.chaoticbits.collabcloud.visualizer.SpiralIterator;

public class SummarizeRepo {
	private static final File TEST_BED = new File("testgitrepo");

	private static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(SummarizeRepo.class);

	private static final int WIDTH = 800;
	private static final int HEIGHT = 800;

	public static void main(String[] args) throws ParseException, IOException {
		PropertyConfigurator.configure("log4j.properties");
		CloudWeights weights = getWeights();
		weights = new GitLoader(new File(TEST_BED.getAbsolutePath() + "/.git"), GitLoaderTest.SECOND_COMMIT_ID).crossWithDiff(weights);
		System.out.println("==Weights after Diff Adjustment==");
		System.out.println(weights);
		layoutWords(weights);
	}

	private static CloudWeights getWeights() throws ParseException, IOException {
		List<File> files = recurseFiles(TEST_BED);
		CloudWeights weights = new CloudWeights();
		for (File file : files) {
			CompilationUnit unit = JavaParser.parse(file);
			weights = unit.accept(new JavaSummarizeVisitor(), weights);
		}
		return weights;
	}

	private static List<File> recurseFiles(File topDir) {
		List<File> list = new ArrayList<File>();
		File[] files = topDir.listFiles();
		for (File file : files) {
			if (file.isDirectory())
				list.addAll(recurseFiles(file));
			else if (file.getName().endsWith(".java"))
				list.add(file);
		}
		return list;
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
		Font font = new Font("Courier New", Font.PLAIN, 150);
		FontRenderContext frc = new FontRenderContext(null, true, true);
		Set<Shape> placedShapes = new HashSet<Shape>();
		Intersector intersector = new Intersector(10, 15.0d);
		List<Entry<String, Double>> entries = weights.sortedEntries();
		for (Entry<String, Double> entry : entries) {
			log.info("Laying out " + entry.getKey() + "...");
			// TODO Convert weights to font sizes
			font = font.deriveFont(35f * (float) Math.log(entry.getValue()));
			char[] chars = entry.getKey().toCharArray();
			GlyphVector glyph = font.layoutGlyphVector(frc, chars, 0, chars.length, Font.LAYOUT_LEFT_TO_RIGHT);
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
						break;
					}
				}
				if (canPlace) {
					int randColor = (int) (Math.random() * 150.0f + 25.0f);
					g2d.setColor(new Color(randColor, randColor, randColor));
					g2d.fill(nextShape);
					placedShapes.add(nextShape);
					break;
				}
			}
		}
		log.info("Writing image...");
		ImageIO.write(bi, "PNG", new File("output/summarizerepo.png"));
		log.info("Done!");
	}
}
