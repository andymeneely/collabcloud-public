package org.chaoticbits.collabcloud;

import japa.parser.ParseException;

import java.awt.Font;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import org.apache.log4j.PropertyConfigurator;
import org.chaoticbits.collabcloud.codeprocessor.CloudWeights;
import org.chaoticbits.collabcloud.codeprocessor.IWeightModifier;
import org.chaoticbits.collabcloud.codeprocessor.MultiplyModifier;
import org.chaoticbits.collabcloud.codeprocessor.java.JavaProjectSummarizer;
import org.chaoticbits.collabcloud.vc.git.GitLoader;
import org.chaoticbits.collabcloud.vc.git.GitLoaderTest;
import org.chaoticbits.collabcloud.visualizer.IColorScheme;
import org.chaoticbits.collabcloud.visualizer.IPlaceStrategy;
import org.chaoticbits.collabcloud.visualizer.Intersector;
import org.chaoticbits.collabcloud.visualizer.LastHitCache.IHitCheck;
import org.chaoticbits.collabcloud.visualizer.LayoutTokens;
import org.chaoticbits.collabcloud.visualizer.RandomPlacement;
import org.chaoticbits.collabcloud.visualizer.SpiralIterator;
import org.chaoticbits.collabcloud.visualizer.color.RandomGrey;

public class SummarizeRepo {
	private static final double LEAF_CUTOFF = 1.0d;
	private static final int SPIRAL_STEPS = 500;
	private static final double SPIRAL_MAX_RADIUS = 400.0d;
	private static final double SQUASHDOWN = 2;
	private static final SpiralIterator spiral = new SpiralIterator(SPIRAL_MAX_RADIUS, SPIRAL_STEPS, SQUASHDOWN);
	private static final File TEST_BED = new File("testgitrepo");
	// private static final File THIS_REPO = new File("");
	// private static final String THIS_REPO_SECOND_COMMIT_ID = "4cfde077a84185b06117bcff5d47c53644463b1f";
	private static final Random RAND = new Random();
	private static final IPlaceStrategy PLACE_STRATEGY = new RandomPlacement(RAND, new Rectangle2D.Double(300, 400, 50, 50));

	private static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(SummarizeRepo.class);
	private static IWeightModifier modifier = new MultiplyModifier(1.1);
	private static Font INITIAL_FONT = new Font("Courier New", Font.BOLD, 150);
	private static IColorScheme COLOR_SCHEME = new RandomGrey(RAND, 25, 175);

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
		log.info("Summarizing the project...");
		CloudWeights weights = new JavaProjectSummarizer().summarize(TEST_BED);
		log.info("Weighting against the repo...");
		weights = new GitLoader(new File(TEST_BED.getAbsolutePath() + "/.git"), GitLoaderTest.SECOND_COMMIT_ID).crossWithDiff(weights, modifier);
		// CloudWeights weights = new JavaProjectSummarizer().summarize(new File(THIS_REPO.getAbsolutePath()
		// + "/src"));
		// weights = new GitLoader(new File(THIS_REPO.getAbsolutePath() +
		// "/.git"),THIS_REPO_SECOND_COMMIT_ID).crossWithDiff(weights, modifier);
		// System.out.println("==Weights after Diff Adjustment==");
		// System.out.println(weights);
		new LayoutTokens(WIDTH, HEIGHT, INITIAL_FONT, RAND, checker, PLACE_STRATEGY, spiral, COLOR_SCHEME).makeImage(weights, new File(
				"output/summarizerepo.png"), "PNG");
	}

}
