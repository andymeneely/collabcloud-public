package org.chaoticbits.collabcloud.codeprocessor.java;


public class JavaPackageCollapserTest {
	
	//Stopped development on this - seems a little slow for what it does
	//Keeping it anyway just in case
	
	// @Test
	// public void allSamePrefix() throws Exception {
	// CloudWeights weights = new CloudWeights();
	// ISummaryToken a = token("org.chaoticbits.something");
	// ISummaryToken b = token("org.chaoticbits.something.else");
	// ISummaryToken c = token("org.chaoticbits.anotherthing");
	// weights.put(a, 1D);
	// weights.put(b, 1D);
	// weights.put(c, 1D);
	//
	// weights = new JavaPackageCollapser().collapse(weights);
	// assertEquals("something", a.getToken());
	// assertEquals("something.else", b.getToken());
	// assertEquals("anotherthing", c.getToken());
	//
	// assertEquals("org.chaoticbits.something", a.getFullName());
	// assertEquals("org.chaoticbits.something.else", b.getFullName());
	// assertEquals("org.chaoticbits.anotherthing", c.getFullName());
	// }
	//
	// private ISummaryToken token(String string) {
	// return new JavaSummaryToken(null, string, string, JavaTokenType.PACKAGE);
	// }
	//
	// @Test
	// public void oneDifferentPrefixNoChange() throws Exception {
	// CloudWeights weights = new CloudWeights();
	// ISummaryToken a = token("com.chaoticbits.something");
	// ISummaryToken b = token("org.chaoticbits.something.else");
	// ISummaryToken c = token("org.chaoticbits.anotherthing");
	// weights.put(a, 1D);
	// weights.put(b, 1D);
	// weights.put(c, 1D);
	//
	// weights = new JavaPackageCollapser().collapse(weights);
	// assertEquals("com.chaoticbits.something", a.getToken());
	// assertEquals("org.chaoticbits.something.else", b.getToken());
	// assertEquals("org.chaoticbits.anotherthing", c.getToken());
	//
	// assertEquals("com.chaoticbits.something", a.getFullName());
	// assertEquals("org.chaoticbits.something.else", b.getFullName());
	// assertEquals("org.chaoticbits.anotherthing", c.getFullName());
	// }
}
