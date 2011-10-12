package org.chaoticbits.collabcloud.visualizer.avatars;

import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import org.chaoticbits.collabcloud.CloudWeights;
import org.chaoticbits.collabcloud.ISummaryTokenVisitor;
import org.chaoticbits.collabcloud.codeprocessor.java.JavaSummaryToken;
import org.chaoticbits.collabcloud.vc.Developer;
import org.chaoticbits.collabcloud.vc.DiffToken;
import org.chaoticbits.collabcloud.visualizer.TokenRenderer;
import org.chaoticbits.collabcloud.visualizer.font.IFontTransformer;
import org.chaoticbits.collabcloud.visualizer.placement.IPlaceStrategy;

/**
 * Given each kind of token, render a shape that represents its boundary. Needs to work in concert with
 * {@link TokenRenderer}
 * 
 * @author andy
 * 
 */
public class TokenBoundaryRenderer implements ISummaryTokenVisitor<Shape> {

	public static final FontRenderContext DEFAULT_FONT_RENDER_CONTEXT = new FontRenderContext(null, true, true);

	public static final String DIFF_NOT_SUPPORTED = "Diff token rendering not supported. Cross-reference with a source code token (e.g. Java)";

	private final IFontTransformer fontTrans;
	private final IPlaceStrategy placeStrategy;
	private final CloudWeights weights;

	public TokenBoundaryRenderer(IFontTransformer fontTrans, IPlaceStrategy placeStrategy, CloudWeights weights) {
		this.fontTrans = fontTrans;
		this.placeStrategy = placeStrategy;
		this.weights = weights;
	}

	public Shape visit(JavaSummaryToken token) {
		GlyphVector glyph = fontTrans.transform(token, weights.get(token)).createGlyphVector(DEFAULT_FONT_RENDER_CONTEXT, token.getToken());
		Shape shape = glyph.getOutline();
		Point2D start = placeStrategy.getStartingPlace(token, shape);
		return glyph.getOutline((float) start.getX(), (float) start.getY());
	}

	public Shape visit(Developer token) {
		Point2D place = placeStrategy.getStartingPlace(token, null);// null?? Fix this.
		// TODO don't hardcode the developer avatars
		return new Rectangle2D.Double(place.getX(), place.getY(), 80, 80);
	}

	public Shape visit(DiffToken token) {
		throw new IllegalAccessError(DIFF_NOT_SUPPORTED);
	}

}
