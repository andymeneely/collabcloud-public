package org.chaoticbits.collabcloud;

import org.chaoticbits.collabcloud.codeprocessor.java.JavaSummaryToken;
import org.chaoticbits.collabcloud.vc.Developer;
import org.chaoticbits.collabcloud.vc.DiffToken;

public interface ISummaryTokenVisitor<T> {

	public T visit(JavaSummaryToken token);
	
	public T visit(Developer token);
	
	public T visit(DiffToken token);
	
}
