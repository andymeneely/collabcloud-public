package org.chaoticbits.collabcloud;

import japa.parser.ParseException;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import javax.imageio.ImageIO;

import org.apache.log4j.PropertyConfigurator;
import org.chaoticbits.collabcloud.codeprocessor.CloudWeights;
import org.chaoticbits.collabcloud.codeprocessor.ISummaryToken;
import org.chaoticbits.collabcloud.codeprocessor.IWeightModifier;
import org.chaoticbits.collabcloud.codeprocessor.MultiplyModifier;
import org.chaoticbits.collabcloud.codeprocessor.java.JavaProjectSummarizer;
import org.chaoticbits.collabcloud.vc.git.GitLoader;
import org.chaoticbits.collabcloud.vc.git.GitLoaderTest;
import org.chaoticbits.collabcloud.visualizer.IPlaceStrategy;
import org.chaoticbits.collabcloud.visualizer.Intersector;
import org.chaoticbits.collabcloud.visualizer.LastHitCache;
import org.chaoticbits.collabcloud.visualizer.LastHitCache.IHitCheck;
import org.chaoticbits.collabcloud.visualizer.RandomPlacement;
import org.chaoticbits.collabcloud.visualizer.SpiralIterator;
import org.chaoticbits.collabcloud.visualizer.font.BoundedLogFont;
import org.chaoticbits.collabcloud.visualizer.font.IFontTransformer;

public class SummarizeRepo {
	private static final double LEAF_CUTOFF = 1.0d;
	private static final int SPIRAL_STEPS = 500;
	private static final double SPIRAL_MAX_RADIUS = 400.0d;
	private static final double SQUASHDOWN = 2;
	private static final File TEST_BED = new File("testgitrepo");
	// private static final File THIS_REPO = new File("");
	// private static final String THIS_REPO_SECOND_COMMIT_ID = "4cfde077a84185b06117bcff5d47c53644463b1f";
	private static final Random RAND = new Random(123456789L);
	private static final IPlaceStrategy PLACE_STRATEGY = new RandomPlacement(RAND, new Rectangle2D.Double(300, 400, 50, 50)) ;

	private static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(SummarizeRepo.class);
	private static IWeightModifier modifier = new MultiplyModifier(1.1);
	private static Font INITIAL_FONT = new Font("Courier New", Font.BOLD, 150);

	private static final Intersector intersector = new Intersector(10, LEAF_CUTOFF);
	private static final IHitCheck<Shape> checker = new IHitCheck<Shape>() {
		public boolean hits(Shape a, Shape b) {
			return intersector.intersect(a, b);
		}
	};

	private static final int WIDTH = 800;
	private static final int HEIGHT = 800;

	public static void main(String[] args) throws ParseException, IOException {
		PropertyConfigurator.configure("log4j.properties");
		CloudWeights weights = new JavaProjectSummarizer().summarize(TEST_BED);
		weights = new GitLoader(new File(TEST_BED.getAbsolutePath() + "/.git"), GitLoaderTest.SECOND_COMMIT_ID).crossWithDiff(weights, modifier);
		// CloudWeights weights = new JavaProjectSummarizer().summarize(new File(THIS_REPO.getAbsolutePath()
		// + "/src"));
		// weights = new GitLoader(new File(THIS_REPO.getAbsolutePath() +
		// "/.git"),THIS_REPO_SECOND_COMMIT_ID).crossWithDiff(weights, modifier);
		System.out.println("==Weights after Diff Adjustment==");
		System.out.println(weights);
		layoutWords(weights);
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

		FontRenderContext frc = new FontRenderContext(null, true, true);
		LastHitCache<Shape> placedShapes = new LastHitCache<Shape>(checker);
		List<Entry<ISummaryToken, Double>> entries = weights.sortedEntries();
		IFontTransformer fontTransformer = new BoundedLogFont(INITIAL_FONT, weights, 75d);
		Collections.shuffle(entries, RAND);
		for (Entry<ISummaryToken, Double> entry : entries) {
			Font font = fontTransformer.transform(entry.getValue());
			if (font.getSize2D() < 6f)
				continue;
			log.info("Laying out " + entry.getKey() + "...[" + entry.getValue() + "]");

			char[] chars = entry.getKey().getToken().toCharArray();
			Point2D center = PLACE_STRATEGY.getStartingPlace(entry.getKey());
			SpiralIterator spiral = new SpiralIterator(center, SPIRAL_MAX_RADIUS, SPIRAL_STEPS, SQUASHDOWN);
			while (spiral.hasNext()) {
				Point2D next = spiral.next();
				Shape nextShape = font.layoutGlyphVector(frc, chars, 0, chars.length, Font.LAYOUT_LEFT_TO_RIGHT).getOutline((float) next.getX(),
						(float) next.getY());
				if (!placedShapes.hitNCache(nextShape)) {
					int randColor = (int) (RAND.nextFloat() * 150.0f + 25.0f);
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
}
