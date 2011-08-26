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
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import javax.imageio.ImageIO;

import org.apache.log4j.PropertyConfigurator;
import org.chaoticbits.collabcloud.codeprocessor.CloudWeights;
import org.chaoticbits.collabcloud.codeprocessor.java.JavaSummarizeVisitor;
import org.chaoticbits.collabcloud.vc.git.GitLoader;
import org.chaoticbits.collabcloud.vc.git.GitLoaderTest;
import org.chaoticbits.collabcloud.visualizer.Intersector;
import org.chaoticbits.collabcloud.visualizer.LastHitCache;
import org.chaoticbits.collabcloud.visualizer.LastHitCache.IHitCheck;
import org.chaoticbits.collabcloud.visualizer.SpiralIterator;

public class SummarizeRepo {
	private static final File TEST_BED = new File("testgitrepo");
	// private static final File THIS_REPO = new File("");
	// private static final String THIS_REPO_SECOND_COMMIT_ID = "4cfde077a84185b06117bcff5d47c53644463b1f";
	private static final Random rand = new Random(1234567L);

	private static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(SummarizeRepo.class);

	private static final Intersector intersector = new Intersector(10, 0.1d);
	private static final IHitCheck<Shape> checker = new IHitCheck<Shape>() {
		public boolean hits(Shape a, Shape b) {
			return intersector.intersect(a, b);
		}
	};

	private static final int WIDTH = 800;
	private static final int HEIGHT = 800;

	public static void main(String[] args) throws ParseException, IOException {
		PropertyConfigurator.configure("log4j.properties");
		CloudWeights weights = getWeights(TEST_BED);
		weights = new GitLoader(new File(TEST_BED.getAbsolutePath() + "/.git"), GitLoaderTest.SECOND_COMMIT_ID).crossWithDiff(weights);
		// CloudWeights weights = getWeights(new File(THIS_REPO.getAbsolutePath() + "/src"));
		// weights = new GitLoader(new File(THIS_REPO.getAbsolutePath() + "/.git"),
		// THIS_REPO_SECOND_COMMIT_ID).crossWithDiff(weights);
		System.out.println("==Weights after Diff Adjustment==");
		System.out.println(weights);
		layoutWords(weights);
	}

	private static CloudWeights getWeights(File dir) throws ParseException, IOException {
		List<File> files = recurseFiles(dir);
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
		AffineTransform transform = g2d.getTransform();
		Font font = new Font("Courier New", Font.BOLD, 150);
		FontRenderContext frc = new FontRenderContext(null, true, true);
		LastHitCache<Shape> placedShapes = new LastHitCache<Shape>(checker);
		List<Entry<String, Double>> entries = weights.sortedEntries();
		// Collections.shuffle(entries, rand);
		for (Entry<String, Double> entry : entries) {
			// TODO Convert weights to font sizes
			float fontSize = 25f * (float) Math.log(entry.getValue());
			if (fontSize < 6f)
				continue;
			log.info("Laying out " + entry.getKey() + "...");
			font = font.deriveFont(fontSize);

			// font = font.deriveFont(20f * (float) Math.sqrt(entry.getValue()));
			char[] chars = entry.getKey().toCharArray();
			// TODO Need a placement strategy
			Point2D center = getStartingPlace();
			SpiralIterator spiral = new SpiralIterator(center, 400.0d, 500);
			while (spiral.hasNext()) {
				Point2D next = spiral.next();
				Shape nextShape = font.layoutGlyphVector(frc, chars, 0, chars.length, Font.LAYOUT_LEFT_TO_RIGHT).getOutline((float) next.getX(),
						(float) next.getY());
				if (!placedShapes.hitNCache(nextShape)) {
					int randColor = (int) (Math.random() * 150.0f + 25.0f);
					g2d.setTransform(transform);
					// TODO Color it according to type and weight, not random
					g2d.setColor(new Color(randColor, randColor, randColor));
					g2d.fill(nextShape);
					break;
				} else {
					if (entry.getKey().equals("getClass")) {
						// g2d.fill(nextShape);
						g2d.fillRect((int) next.getX(), (int) next.getY(), 3, 3);
					}
				}
			}
		}
		log.info("Writing image...");
		ImageIO.write(bi, "PNG", new File("output/summarizerepo.png"));
		log.info("Done!");
	}

	private static java.awt.geom.Point2D.Double getStartingPlace() {
		// return new Point2D.Double(rand.nextInt(300) + 150f, rand.nextInt(300) + 150.0f);
		return new Point2D.Double(250, 400);
	}
}
