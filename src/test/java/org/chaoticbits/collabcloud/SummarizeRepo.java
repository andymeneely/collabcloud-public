package org.chaoticbits.collabcloud;

import japa.parser.ParseException;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import org.apache.log4j.PropertyConfigurator;
import org.chaoticbits.collabcloud.codeprocessor.CloudWeights;
import org.chaoticbits.collabcloud.codeprocessor.IWeightModifier;
import org.chaoticbits.collabcloud.codeprocessor.MultiplyModifier;
import org.chaoticbits.collabcloud.codeprocessor.java.JavaColorScheme;
import org.chaoticbits.collabcloud.codeprocessor.java.JavaProjectSummarizer;
import org.chaoticbits.collabcloud.vc.git.GitLoader;
import org.chaoticbits.collabcloud.vc.git.GitLoaderTest;
import org.chaoticbits.collabcloud.visualizer.Intersector;
import org.chaoticbits.collabcloud.visualizer.LastHitCache.IHitCheck;
import org.chaoticbits.collabcloud.visualizer.LayoutTokens;
import org.chaoticbits.collabcloud.visualizer.SpiralIterator;
import org.chaoticbits.collabcloud.visualizer.color.IColorScheme;
import org.chaoticbits.collabcloud.visualizer.font.BoundedLogFont;
import org.chaoticbits.collabcloud.visualizer.font.IFontTransformer;
import org.chaoticbits.collabcloud.visualizer.placement.CenteredTokenWrapper;
import org.chaoticbits.collabcloud.visualizer.placement.IPlaceStrategy;
import org.chaoticbits.collabcloud.visualizer.placement.ParentNetworkPlacement;
import org.chaoticbits.collabcloud.visualizer.placement.RandomPlacement;

public class SummarizeRepo {
	private static final double LEAF_CUTOFF = 1.0d;
	private static final int SPIRAL_STEPS = 1000;
	private static final double SPIRAL_MAX_RADIUS = 350.0d;
	private static final double SQUASHDOWN = 1;
	private static final SpiralIterator spiral = new SpiralIterator(SPIRAL_MAX_RADIUS, SPIRAL_STEPS, SQUASHDOWN);
	private static final File TEST_BED = new File("testgitrepo");
	private static final File THIS_REPO = new File("");
	private static final String THIS_REPO_SECOND_COMMIT_ID = "4cfde077a84185b06117bcff5d47c53644463b1f";
	private static final File JENKINS_REPO = new File("c:/data/jenkins");
	private static final String JENKINS_BACK_LIMIT_COMMIT_ID = "df1094651bdefeda57d974a97907521eb21aef7b";
	private static final Random RAND = new Random();
	private static final IPlaceStrategy RANDOM_PLACE_STRATEGY = new CenteredTokenWrapper(new RandomPlacement(RAND, new Rectangle2D.Double(600,
			600, 500, 500)));

	private static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(SummarizeRepo.class);
	private static IWeightModifier modifier = new MultiplyModifier(1.2);
	private static Font INITIAL_FONT = new Font("Lucida Sans", Font.BOLD, 150);
	// private static IColorScheme COLOR_SCHEME = new RandomGrey(RAND, 25, 175);
	private static IColorScheme COLOR_SCHEME = new JavaColorScheme(RAND, 20);
	private static double MAX_FONT_SIZE = 75.0d;

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

		CloudWeights weights;
		weights = testBed();
		// weights = thisRepo();
		// weights = jenkins();
		// System.out.println("==Weights after Diff Adjustment==");
		// System.out.println(weights);
		IFontTransformer FONT_TRANSFORMER = new BoundedLogFont(INITIAL_FONT, weights, MAX_FONT_SIZE);
		IPlaceStrategy networkPlaceStrategy = new CenteredTokenWrapper(new ParentNetworkPlacement(weights.tokens(), new Dimension(WIDTH / 2,
				HEIGHT / 2), new Point2D.Double(2 * WIDTH / 3, 2 * HEIGHT / 3)));
		new LayoutTokens(WIDTH, HEIGHT, FONT_TRANSFORMER, checker, networkPlaceStrategy, spiral, COLOR_SCHEME).makeImage(weights, new File(
				"output/summarizerepo.png"), "PNG");
	}

	private static CloudWeights jenkins() throws IOException {
		log.info("Summarizing the project...");
		CloudWeights weights = new JavaProjectSummarizer().summarize(new File(JENKINS_REPO.getAbsolutePath()));
		log.info("Weighting against the repo...");
		// weights = new GitLoader(new File(JENKINS_REPO.getAbsolutePath() + "/.git"),
		// JENKINS_BACK_LIMIT_COMMIT_ID).crossWithDiff(weights,
		// modifier);
		return weights;
	}

	private static CloudWeights thisRepo() throws IOException {
		log.info("Summarizing the project...");
		CloudWeights weights = new JavaProjectSummarizer().summarize(new File(THIS_REPO.getAbsolutePath() + "/src"));
		log.info("Weighting against the repo...");
		weights = new GitLoader(new File(THIS_REPO.getAbsolutePath() + "/.git"), THIS_REPO_SECOND_COMMIT_ID).crossWithDiff(weights, modifier);
		return weights;
	}

	private static CloudWeights testBed() throws IOException {
		log.info("Summarizing the project...");
		CloudWeights weights = new JavaProjectSummarizer().summarize(TEST_BED);
		log.info("Weighting against the repo...");
		weights = new GitLoader(new File(TEST_BED.getAbsolutePath() + "/.git"), GitLoaderTest.SECOND_COMMIT_ID).crossWithDiff(weights, modifier);
		return weights;
	}

}
