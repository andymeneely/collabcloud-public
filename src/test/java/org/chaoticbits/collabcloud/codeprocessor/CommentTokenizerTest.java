package org.chaoticbits.collabcloud.codeprocessor;

import static org.junit.Assert.*;
import japa.parser.ast.body.JavadocComment;

import org.junit.Test;

public class CommentTokenizerTest {

	@Test
	public void simpleJavadoc() throws Exception {
		JavadocComment comment = new JavadocComment("* This method is named something. @param args \n	 * @throws IOException \n	 * @throws ParseException");
		CloudWeights cloudWeights = comment.accept(new Summarizer(), new CloudWeights());
		assertEquals("ignoring param", 0.0, cloudWeights.get("param"),0.000001);
		assertEquals("ignoring args", 0.0, cloudWeights.get("args"),0.000001);
		assertEquals("ignoring throws", 0.0, cloudWeights.get("throws"),0.000001);
		assertEquals("ignoring IOException", 0.0, cloudWeights.get("ioexception"),0.000001);
		assertEquals("ignoring ParseException", 0.0, cloudWeights.get("parseexception"),0.000001);
		assertEquals("ignoring This", 0.0, cloudWeights.get("This"),0.000001);
		assertEquals("ignoring this", 0.0, cloudWeights.get("this"),0.000001);
		assertEquals("tokenized method", 0.25, cloudWeights.get("method"),0.000001);
		assertEquals("ignoring is", 0.0, cloudWeights.get("is"),0.000001);
		assertEquals("tokenized named", 0.25, cloudWeights.get("named"),0.000001);
		assertEquals("tokenized something", 0.25, cloudWeights.get("something"),0.000001);
	}
	
}
