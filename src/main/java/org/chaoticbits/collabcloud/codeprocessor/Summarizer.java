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
import java.util.HashSet;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;

import opennlp.tools.tokenize.SimpleTokenizer;

public class Summarizer extends ReturnArgVisitorAdapter<CloudWeights> {
	private static final String WEIGHT_PROPS_PREFIX = "org.chaoticbits.collabcloud.weights.";
	private Properties props;
	private Set<String> excludeWords;

	public Summarizer() {
		props = new Properties();
		try {
			props.load(getClass().getResourceAsStream("weights.properties"));
		} catch (IOException e) {
			System.err.println("No weights.properties found in this package");
			e.printStackTrace();
		}
		excludeWords = new HashSet<String>();
		Scanner scanner = new Scanner(getClass().getResourceAsStream("excludewords"));
		while (scanner.hasNextLine())
			excludeWords.add(scanner.nextLine());
	}

	private double weight(String key) {
		try {
			return Double.valueOf(props.getProperty(WEIGHT_PROPS_PREFIX + key));
		} catch (Throwable t) {
			t.printStackTrace();
			return 0.0;
		}
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
		return tokenizeComment(n, weights);
	}

	@Override
	public CloudWeights visit(LineComment n, CloudWeights weights) {
		super.visit(n, weights);
		return tokenizeComment(n, weights);
	}

	@Override
	public CloudWeights visit(JavadocComment n, CloudWeights weights) {
		super.visit(n, weights);
		return tokenizeComment(n, weights);
	}

	private CloudWeights tokenizeComment(Comment n, CloudWeights weights) {
		String[] tokenize = SimpleTokenizer.INSTANCE.tokenize(n.getContent());
		for (String string : tokenize) {
			String token = string.toLowerCase();
			if(!excludeWords.contains(token))
				weights.increment(token, weight("commentToken"));
		}
		return weights;
	}

}
