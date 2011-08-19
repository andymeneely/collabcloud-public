package org.chaoticbits.collabcloud.codeprocessor;

import japa.parser.ast.BlockComment;
import japa.parser.ast.Comment;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.LineComment;
import japa.parser.ast.PackageDeclaration;
import japa.parser.ast.body.JavadocComment;
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
	public CloudWeights visit(CompilationUnit n, CloudWeights weights) {
		super.visit(n, weights);
		if (n.getComments() != null) {
			for (Comment comment : n.getComments()) {
				// TODO this should be chopped up using cue.language
				comment.accept(this, weights);
			}
		}
		return weights;
	}

	@Override
	public CloudWeights visit(BlockComment n, CloudWeights weights) {
		super.visit(n, weights);
		// TODO this should be chopped up using cue.language
		weights.increment(n.getContent().trim());
		return weights;
	}

	@Override
	public CloudWeights visit(LineComment n, CloudWeights weights) {
		super.visit(n, weights);
		// TODO this should be chopped up using cue.language
		weights.increment(n.getContent().trim());
		return weights;
	}

	@Override
	public CloudWeights visit(JavadocComment n, CloudWeights weights) {
		super.visit(n, weights);
		// TODO this should be chopped up using cue.language
		weights.increment(n.getContent().trim());
		return weights;
	}

}
