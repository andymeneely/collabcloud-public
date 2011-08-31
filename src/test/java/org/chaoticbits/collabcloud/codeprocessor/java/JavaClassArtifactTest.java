package org.chaoticbits.collabcloud.codeprocessor.java;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;

import java.io.File;

import org.junit.Test;

public class JavaClassArtifactTest {

	@Test
	public void testToString() throws Exception {
		File file = new File("testgitrepo");
		JavaClassArtifact artifact = new JavaClassArtifact(file);
		assertEquals(file.toString(), artifact.toString());
	}
}
