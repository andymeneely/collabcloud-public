package org.chaoticbits.collabcloud.vc.svn;

import static org.junit.Assert.*;

import org.chaoticbits.collabcloud.vc.Developer;
import org.junit.Test;

public class SVNDeveloperParserTest {

	@Test
	public void conventionalEmailSignature() throws Exception {
		assertEquals(new Developer("abc", "def@ghi.jkl"), new SVNDeveloperParser().parse("abc<def@ghi.jkl>"));
	}

	@Test
	public void complicatedNames() throws Exception {
		assertEquals(new Developer("Abc", "def@ghi.jkl"), new SVNDeveloperParser().parse("Abc <def@ghi.jkl>"));
		assertEquals(new Developer("Conan O'Brien", "def@ghi.jkl"), new SVNDeveloperParser().parse("Conan O'Brien <def@ghi.jkl>"));
		assertEquals(new Developer("Conan O'Brien", "abc.def@ghi.jkl"), new SVNDeveloperParser().parse("Conan O'Brien <abc.def@ghi.jkl>"));
	}

	@Test
	public void justAName() throws Exception {
		assertEquals(new Developer("abc", ""), new SVNDeveloperParser().parse("abc"));
		assertEquals(new Developer("abc", ""), new SVNDeveloperParser().parse("abc "));
	}

	@Test
	public void brokenConvention() throws Exception {
		assertEquals(new Developer("abc<def@ghi.jkl", ""), new SVNDeveloperParser().parse("abc<def@ghi.jkl"));
	}
}
