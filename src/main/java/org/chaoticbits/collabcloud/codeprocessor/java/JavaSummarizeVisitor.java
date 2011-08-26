package org.chaoticbits.collabcloud.codeprocessor.java;

import japa.parser.ast.PackageDeclaration;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.EnumConstantDeclaration;
import japa.parser.ast.body.EnumDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.expr.MethodCallExpr;

import java.io.IOException;
import java.util.Properties;

import org.chaoticbits.collabcloud.codeprocessor.CloudWeights;

/**
 * A visitor that travels the AST of a Java compilation unit and counts stuff that is useful in summarizing
 * that unit. For example, the name of the unit, the names of methods declared, called, etc. This returns a
 * {@link CloudWeights} object, which is basically a map of the weights.
 * 
 * @author andy
 * 
 */
public class JavaSummarizeVisitor extends ReturnArgVisitorAdapter<CloudWeights> {
	private static final String WEIGHT_PROPS_PREFIX = "org.chaoticbits.collabcloud.weights.java.";
	private Properties props;
	private final JavaClassArtifact summarizable;

	public JavaSummarizeVisitor(JavaClassArtifact summarizable) {
		this.summarizable = summarizable;
		props = new Properties();
		try {
			props.load(getClass().getResourceAsStream("weights.properties"));
		} catch (IOException e) {
			System.err.println("No weights.properties found in this package");
			e.printStackTrace();
		}
	}

	private double weight(String token) {
		try {
			return Double.valueOf(props.getProperty(WEIGHT_PROPS_PREFIX + token));
		} catch (Throwable t) {
			t.printStackTrace();
			return 0.0;
		}
	}

	@Override
	public CloudWeights visit(MethodDeclaration n, CloudWeights weights) {
		super.visit(n, weights);
		JavaSummaryToken token = new JavaSummaryToken(summarizable, n.getName(), n.getName(), "method");
		weights.increment(token, weight("methodDeclaration"));
		return weights;
	}

	@Override
	public CloudWeights visit(PackageDeclaration n, CloudWeights weights) {
		super.visit(n, weights);
		JavaSummaryToken token = new JavaSummaryToken(summarizable, n.getName().toString(), n.getName().getName(), "package");
		weights.increment(token, weight("package"));
		return weights;
	}

	@Override
	public CloudWeights visit(MethodCallExpr n, CloudWeights weights) {
		super.visit(n, weights);
		JavaSummaryToken token = new JavaSummaryToken(summarizable, n.getName(), n.getName(), "method");
		weights.increment(token, weight("methodCall"));
		return weights;
	}

	@Override
	public CloudWeights visit(ClassOrInterfaceDeclaration n, CloudWeights weights) {
		super.visit(n, weights);
		JavaSummaryToken token = new JavaSummaryToken(summarizable, n.getName(), n.getName(), "class");
		weights.increment(token, weight("classOrInterfaceDeclaration"));
		return weights;
	}

	@Override
	public CloudWeights visit(EnumDeclaration n, CloudWeights weights) {
		super.visit(n, weights);
		JavaSummaryToken token = new JavaSummaryToken(summarizable, n.getName(), n.getName(), "enum");
		weights.increment(token, weight("enumType"));
		return weights;
	}

	@Override
	public CloudWeights visit(EnumConstantDeclaration n, CloudWeights weights) {
		super.visit(n, weights);
		JavaSummaryToken token = new JavaSummaryToken(summarizable, n.getName(), n.getName(), "enum");
		weights.increment(token, weight("enumConstant"));
		return weights;
	}

	// TODO Tokenize and parse comments in a smarter way, e.g. ignore @param and @author tags. Ignore Java
	// allusions, e.g. IOException
	// private CloudWeights tokenizeComment(Comment n, CloudWeights weights) {
	// String[] tokenize = SimpleTokenizer.INSTANCE.tokenize(n.getContent());
	//
	// for (String string : tokenize) {
	// String token = string.toLowerCase();
	// if(!excludeWords.contains(token))
	// weights.increment(token, weight("commentToken"));
	// }
	// return weights;
	// }
	// @Override
	// public CloudWeights visit(BlockComment n, CloudWeights weights) {
	// super.visit(n, weights);
	// return tokenizeComment(n, weights);
	// }
	//
	// @Override
	// public CloudWeights visit(LineComment n, CloudWeights weights) {
	// super.visit(n, weights);
	// return tokenizeComment(n, weights);
	// }
	//
	// @Override
	// public CloudWeights visit(JavadocComment n, CloudWeights weights) {
	// super.visit(n, weights);
	// return tokenizeComment(n, weights);
	// }
	// @Override
	// public CloudWeights visit(CompilationUnit n, CloudWeights weights) {
	// super.visit(n, weights);
	// if (n.getComments() != null) {
	// for (Comment comment : n.getComments()) {
	// // TODO this should be chopped up using cue.language
	// comment.accept(this, weights);
	// }
	// }
	// return weights;
	// }

}
