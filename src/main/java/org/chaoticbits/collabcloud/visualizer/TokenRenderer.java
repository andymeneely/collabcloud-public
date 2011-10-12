package org.chaoticbits.collabcloud.visualizer;

import org.chaoticbits.collabcloud.ISummaryTokenVisitor;
import org.chaoticbits.collabcloud.codeprocessor.java.JavaSummaryToken;
import org.chaoticbits.collabcloud.vc.Developer;
import org.chaoticbits.collabcloud.vc.DiffToken;

/**
 * Given a token, actually render the image onto the graphics - either as a shape or an avatar.
 * @author andy
 * 
 */
public class TokenRenderer implements ISummaryTokenVisitor<Boolean> {

	public Boolean visit(JavaSummaryToken token) {
		throw new IllegalStateException("unimplemented!");
	}

	public Boolean visit(Developer token) {
		throw new IllegalStateException("unimplemented!");
	}

	public Boolean visit(DiffToken token) {
		throw new IllegalStateException("unimplemented!");
	}

}
