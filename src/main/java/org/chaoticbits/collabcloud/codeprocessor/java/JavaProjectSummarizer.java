package org.chaoticbits.collabcloud.codeprocessor.java;

import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.chaoticbits.collabcloud.CloudWeights;
import org.chaoticbits.collabcloud.codeprocessor.IProjectSummarizer;

public class JavaProjectSummarizer implements IProjectSummarizer {

	private CloudWeights weights;

	public JavaProjectSummarizer() {
		weights = new CloudWeights();
	}

	public JavaProjectSummarizer(CloudWeights weights) {
		this.weights = weights;
	}

	/**
	 * Summarize a whole directory of Java files
	 * 
	 * @param pathToRepo
	 */
	public CloudWeights summarize(File pathToRepo) throws IOException {
		List<File> files = new RecurseJavaFiles().loadRecursive(pathToRepo);
		for (File file : files) {
			CompilationUnit unit;
			try {
				unit = JavaParser.parse(file);
				weights = unit.accept(new JavaSummarizeVisitor(new JavaClassSummarizable(file)), weights);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return weights;
	}

}
