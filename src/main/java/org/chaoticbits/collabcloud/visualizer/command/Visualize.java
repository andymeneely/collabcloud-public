package org.chaoticbits.collabcloud.visualizer.command;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.chaoticbits.collabcloud.CloudWeights;
import org.chaoticbits.collabcloud.codeprocessor.MultiplyModifier;
import org.chaoticbits.collabcloud.codeprocessor.java.JavaColorScheme;
import org.chaoticbits.collabcloud.codeprocessor.java.JavaProjectSummarizer;
import org.chaoticbits.collabcloud.vc.IVersionControlLoader;
import org.chaoticbits.collabcloud.vc.git.GitLoader;
import org.chaoticbits.collabcloud.visualizer.Intersector;
import org.chaoticbits.collabcloud.visualizer.LayoutTokens;
import org.chaoticbits.collabcloud.visualizer.VisualizerConfigException;
import org.chaoticbits.collabcloud.visualizer.font.BoundedLogFont;
import org.chaoticbits.collabcloud.visualizer.font.IFontTransformer;
import org.chaoticbits.collabcloud.visualizer.placement.CenteredTokenWrapper;
import org.chaoticbits.collabcloud.visualizer.placement.ParentNetworkPlacement;
import org.chaoticbits.collabcloud.visualizer.spiral.SpiralIterator;

/**
 * This is the command pattern-ish class that handles the actual calling of the visualizer API. The main
 * functionality of this class is to handle all of the properties, defaults, algorithms, constructors, etc.
 * @author andy
 * 
 */
public class Visualize {

	private final File srcTree;
	private String since = "";
	// TODO create an auto-detect loader so users don't need to set it
	private IVersionControlLoader loader = null;
	private int width = 800;
	private int height = 800;
	private int maxTokens = 100;
	private double leafCutoff = 1.0d;
	private int spiralSteps = 500;
	// TODO Make this parameter computable from the width/height
	private double spiralMaxRadius = 350.0d;
	// TODO Make this parameter computable from the width/height
	private double squashdown = 1;
	private SpiralIterator spiral = new SpiralIterator(spiralMaxRadius, spiralSteps, squashdown);
	private long randSeed = System.nanoTime();
	private int maxFontSize = 50;
	private String font = "Lucida Sans";

	public Visualize(File srcTree) {
		this.srcTree = srcTree;
	}

	public BufferedImage call() throws VisualizerConfigException, IOException {
		validate();
		CloudWeights weights = new JavaProjectSummarizer().summarize(srcTree);
		weights = new GitLoader(new File(srcTree.getAbsolutePath() + "/.git"), since).crossWithDiff(weights,
				new MultiplyModifier(1.2));
		IFontTransformer fontTransformer = new BoundedLogFont(new Font(font, Font.BOLD, maxFontSize), weights,
				maxFontSize);
		Random rand = new Random(randSeed);
		LayoutTokens layoutTokens = new LayoutTokens(width, height, maxTokens, fontTransformer, new Intersector(10,
				leafCutoff), new CenteredTokenWrapper(new ParentNetworkPlacement(weights.tokens(), new Dimension(
				width / 2, height / 2), new Point2D.Double(3 * width / 4, 3 * height / 4))), spiral,
				new JavaColorScheme(rand, 20));
		BufferedImage bi = layoutTokens.makeImage(weights, new File("output/summarizerepo.png"), "PNG");
		return bi;
	}

	private void validate() throws VisualizerConfigException {
		List<String> errors = new ArrayList<String>();
		if ("".equals(since))
			errors.add("since revision");
		if (loader == null)
			errors.add("SVN or Git");
		if (!errors.isEmpty())
			throw new VisualizerConfigException("Missing the following properties: "
					+ errors.toString().substring(1, errors.toString().length() - 1));
	}

	public Visualize since(String sinceCommit) {
		this.since = sinceCommit;
		return this;
	}

	public void setSince(String since) {
		this.since = since;
	}

	public void setLoader(IVersionControlLoader loader) {
		this.loader = loader;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setMaxTokens(int maxTokens) {
		this.maxTokens = maxTokens;
	}

	public Visualize setLeafCutoff(double leafCutoff) {
		this.leafCutoff = leafCutoff;
		return this;
	}

	public Visualize setSpiralSteps(int spiralSteps) {
		this.spiralSteps = spiralSteps;
		return this;
	}

	public Visualize setSpiralMaxRadius(double spiralMaxRadius) {
		this.spiralMaxRadius = spiralMaxRadius;
		return this;
	}

	public Visualize setSquashdown(double squashdown) {
		this.squashdown = squashdown;
		return this;
	}

	public Visualize setSpiral(SpiralIterator spiral) {
		this.spiral = spiral;
		return this;
	}

	public Visualize setRandSeed(long randSeed) {
		this.randSeed = randSeed;
		return this;
	}

	public Visualize setMaxFontSize(int maxFontSize) {
		this.maxFontSize = maxFontSize;
		return this;
	}

	public Visualize setFont(String font) {
		this.font = font;
		return this;
	}

	public Visualize useGit() throws VisualizerConfigException {
		try {
			loader = new GitLoader(srcTree);
			return this;
		} catch (IOException e) {
			throw new VisualizerConfigException(e);
		}
	}

	public File getSrcTree() {
		return srcTree;
	}

	public String getSince() {
		return since;
	}

	public IVersionControlLoader getLoader() {
		return loader;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getMaxTokens() {
		return maxTokens;
	}

	public double getLeafCutoff() {
		return leafCutoff;
	}

	public int getSpiralSteps() {
		return spiralSteps;
	}

	public double getSpiralMaxRadius() {
		return spiralMaxRadius;
	}

	public double getSquashdown() {
		return squashdown;
	}

	public SpiralIterator getSpiral() {
		return spiral;
	}

	public long getRandSeed() {
		return randSeed;
	}

	public double getMaxFontSize() {
		return maxFontSize;
	}

	public String getFont() {
		return font;
	}

}
