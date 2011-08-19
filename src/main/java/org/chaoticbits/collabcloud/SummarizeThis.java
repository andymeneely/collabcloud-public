package org.chaoticbits.collabcloud;

import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import org.chaoticbits.collabcloud.codeprocessor.CloudWeights;
import org.chaoticbits.collabcloud.codeprocessor.Summarizer;

public class SummarizeThis {

	/**
	 * @param args
	 * @throws IOException
	 * @throws ParseException
	 */
	public static void main(String[] args) throws ParseException, IOException {
		File[] files = new File("src/main/java/org/chaoticbits/collabcloud/codeprocessor").listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(".java");
			}
		});
		CloudWeights weights = new CloudWeights();
		for (File file : files) {
			CompilationUnit unit = JavaParser.parse(file);
			weights = unit.accept(new Summarizer(), weights);
		}
		System.out.println("==Weights of this source code==");
		System.out.println(weights);
	}
}
