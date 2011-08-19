package org.chaoticbits.collabcloud.codeprocessor;

import static org.junit.Assert.assertEquals;
import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

public class SummarizerTest {

	@Test
	public void declaredMethod() throws Exception {
		CloudWeights weights = JavaParser.parse(source("ContrivedExample.java")).accept(new Summarizer(), new CloudWeights());
		assertEquals("weight of 1.0", 1.0d, weights.get("uncalledMethod"), 0.0000001);
	}

	@Test
	public void mainMethodIncluded() throws Exception {
		CloudWeights weights = JavaParser.parse(source("HelloWorld.java")).accept(new Summarizer(), new CloudWeights());
		assertEquals("main method is included", 1.0d, weights.get("main"), 0.0000001);
	}

	@Test
	public void packageNames() throws Exception {
		CloudWeights weights = JavaParser.parse(source("IsPrime.java")).accept(new Summarizer(), new CloudWeights());
		assertEquals("include last word of package name", 1.0d, weights.get("testinputs"), 0.0000001);
	}

	@Test
	public void ignoreLocalVariables() throws Exception {
		CloudWeights weights = JavaParser.parse(source("IsPrime.java")).accept(new Summarizer(), new CloudWeights());
		assertEquals("local variables not included", 0.0d, weights.get("factors"), 0.0000001);
	}

	@Test
	public void methodCalls() throws Exception {
		CloudWeights weights = JavaParser.parse(source("ContrivedExample.java")).accept(new Summarizer(), new CloudWeights());
		assertEquals("methods called 5 times (*0.25), declared once ", 2.25d, weights.get("methodCalledMultipleTimes"), 0.0000001);
	}
	
	@Test
	public void externalMethodCalls() throws Exception {
		CloudWeights weights = JavaParser.parse(source("ContrivedExample.java")).accept(new Summarizer(), new CloudWeights());
		assertEquals("external method calls", 0.25d, weights.get("random"), 0.0000001);
	}
	
	@Test
	public void inlineComments() throws Exception {
		CloudWeights weights = JavaParser.parse(source("ContrivedExample.java")).accept(new Summarizer(), new CloudWeights());
		assertEquals("inline comments are included", 0.25d, weights.get("inline!"), 0.0000001);
	}
	
	@Test
	public void blockComments() throws Exception {
		CloudWeights weights = JavaParser.parse(source("ContrivedExample.java")).accept(new Summarizer(), new CloudWeights());
		assertEquals("block comments are included", 0.25d, weights.get("block!"), 0.0000001);
	}
	
	@Test
	public void javadocComments() throws Exception {
		CloudWeights weights = JavaParser.parse(source("ContrivedExample.java")).accept(new Summarizer(), new CloudWeights());
		assertEquals("javadoc comments are included", 0.5d, weights.get("* Javadoc!"), 0.0000001);
	}
	
	@Test
	public void classOrInterfaceName() throws Exception {
		CloudWeights weights = JavaParser.parse(source("ContrivedExample.java")).accept(new Summarizer(), new CloudWeights());
		assertEquals("include the compilation unit name", 2.0d, weights.get("ContrivedExample"), 0.0000001);
	}

	private File source(String name) {
		return new File("src/test/java/org/chaoticbits/collabcloud/testinputs/" + name);
	}
	
	public static void main(String[] args) throws ParseException, IOException {
		CompilationUnit unit = JavaParser.parse(new File("src/test/java/org/chaoticbits/collabcloud/testinputs/ContrivedExample.java"));
		System.out.println(unit);
	}

}
