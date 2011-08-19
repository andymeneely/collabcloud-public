package org.chaoticbits.collabcloud.codeprocessor;

import japa.parser.ast.BlockComment;
import japa.parser.ast.Comment;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.LineComment;
import japa.parser.ast.PackageDeclaration;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.JavadocComment;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.expr.MethodCallExpr;

import java.io.IOException;
import java.util.Properties;

public class Summarizer extends ReturnArgVisitorAdapter<CloudWeights> {
	private static final String WEIGHT_PROPS_PREFIX = "org.chaoticbits.collabcloud.weights.";
	private Properties props;

	public Summarizer() {
		props = new Properties();
		try {
			props.load(getClass().getResourceAsStream("weights.properties"));
		} catch (IOException e) {
			System.err.println("No weights.properties found in this package");
			e.printStackTrace();
		}
	}

	private double weight(String key) {
		return Double.valueOf(props.getProperty(WEIGHT_PROPS_PREFIX + key));
	}

	@Override
	public CloudWeights visit(MethodDeclaration n, CloudWeights weights) {
		super.visit(n, weights);
		weights.increment(n.getName(), weight("methodDeclaration"));
		return weights;
	}

	@Override
	public CloudWeights visit(PackageDeclaration n, CloudWeights weights) {
		super.visit(n, weights);
		weights.increment(n.getName().getName(), weight("package"));
		return weights;
	}

	@Override
	public CloudWeights visit(MethodCallExpr n, CloudWeights weights) {
		super.visit(n, weights);
		weights.increment(n.getName(), weight("methodCall"));
		return weights;
	}

	@Override
	public CloudWeights visit(ClassOrInterfaceDeclaration n, CloudWeights weights) {
		super.visit(n, weights);
		weights.increment(n.getName(), weight("classOrInterfaceDeclaration"));
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
		weights.increment(n.getContent().trim(), weight("commentToken"));
		return weights;
	}

	@Override
	public CloudWeights visit(LineComment n, CloudWeights weights) {
		super.visit(n, weights);
		// TODO this should be chopped up using cue.language
		weights.increment(n.getContent().trim(), weight("commentToken"));
		return weights;
	}

	@Override
	public CloudWeights visit(JavadocComment n, CloudWeights weights) {
		super.visit(n, weights);
		// TODO this should be chopped up using cue.language
		weights.increment(n.getContent().trim(), weight("commentToken"));
		return weights;
	}

}
