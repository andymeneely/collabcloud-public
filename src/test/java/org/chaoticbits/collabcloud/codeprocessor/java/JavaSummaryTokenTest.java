package org.chaoticbits.collabcloud.codeprocessor.java;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;

import org.junit.Test;

public class JavaSummaryTokenTest {

	@Test
	public void testToString() throws Exception {
		assertEquals("name(full name)", new JavaSummaryToken(null, "full name", "name", null).toString());
	}
	
	@Test
	public void testEqualsOnlyOnToken() throws Exception {
		JavaSummaryToken token = new JavaSummaryToken(null, null, "token", null);
		JavaSummaryToken token2 = new JavaSummaryToken(null, "different", "token", JavaTokenType.CLASS);
		assertNotSame(token, token2);
		assertEquals(token, token2);
		assertFalse(new JavaSummaryToken(null, null, "token", null).equals(new JavaSummaryToken(null, null, "token2", null)));
		assertFalse(new JavaSummaryToken(null, null, "token", null).equals(new JavaSummaryToken(null, null, null, null)));
		assertFalse(new JavaSummaryToken(null, null, null, null).equals(new JavaSummaryToken(null, null, "token2", null)));
	}
}
