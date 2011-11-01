package org.chaoticbits.collabcloud;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;

import org.chaoticbits.collabcloud.vc.Developer;
import org.junit.Test;

public class DeveloperTest {

	@Test
	public void developerEquals() throws Exception {
		assertNotSame(new Developer("abc", "def"),new Developer("abc", "def"));
		assertEquals(new Developer("abc", "def"),new Developer("abc", "def"));
		assertFalse(new Developer("abc", "def").equals(new Developer("abc", "defX")));
		assertFalse(new Developer("abc", "def").equals(new Developer("abcX", "def")));
		assertFalse(new Developer("abc", "def").equals(new Developer(null, "def")));
		assertFalse(new Developer("abc", "def").equals(new Developer("abc", null)));
		assertFalse(new Developer("abc", "def").equals(null));
		assertEquals(new Developer(null, null),new Developer(null, null));
		try{
			something();
		} catch(Exception e){
			
		}
		Thread.currentThread().getStackTrace();
	}
	
	private void something() {
		throw new IllegalStateException("unimplemented!");
	}

	@Test
	public void getDeveloperStuff() throws Exception {
		Developer dev = new Developer("abc", "def");
		assertEquals("abc", dev.getName());
		assertEquals("def", dev.getEmail());
	}
	
	@Test
	public void getDeveloperToString() throws Exception {
		Developer dev = new Developer("abc", "def");
		assertEquals("abc <def>", dev.toString());
	}
}
