package org.chaoticbits.collabcloud.codeprocessor;

import japa.parser.ast.BlockComment;
import japa.parser.ast.PackageDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.expr.MethodCallExpr;

public class Summarizer extends ReturnArgVisitorAdapter<CloudWeights> {

	@Override
	public CloudWeights visit(MethodDeclaration n, CloudWeights weights) {
		super.visit(n, weights);
		weights.increment(n.getName());
		return weights;
	}

	@Override
	public CloudWeights visit(PackageDeclaration n, CloudWeights weights) {
		super.visit(n, weights);
		weights.increment(n.getName().getName());
		return weights;
	}

	@Override
	public CloudWeights visit(MethodCallExpr n, CloudWeights weights) {
		super.visit(n, weights);
		weights.increment(n.getName());
		return weights;
	}

	@Override
	public CloudWeights visit(BlockComment n, CloudWeights weights) {
		super.visit(n, weights);
		weights.increment(n.getContent());// TODO this should be chopped up using cue.language
		return weights;
	}

}
