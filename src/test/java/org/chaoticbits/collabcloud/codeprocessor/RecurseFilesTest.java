package org.chaoticbits.collabcloud.codeprocessor;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.chaoticbits.collabcloud.codeprocessor.java.RecurseJavaFiles;
import org.junit.Test;

public class RecurseFilesTest {

	@Test
	public void allFiles() throws Exception {
		File dir = new File("testgitrepo/mancala");
		List<File> list = new RecurseJavaFiles().loadRecursive(dir);
		sort(list);
		assertEquals(16, list.size());
		assertEquals("Board.java", list.get(0).getName());
		assertEquals("BatchGame.java", list.get(1).getName());
		assertEquals("Side.java", list.get(15).getName());
	}

	@Test
	public void nonDirectory() throws Exception {
		File dir = new File("testgitrepo/mancala/game/BatchGame.java");
		List<File> list = new RecurseJavaFiles().loadRecursive(dir);
		assertEquals(0, list.size());
	}

	private void sort(List<File> list) {
		Collections.sort(list, new Comparator<File>() {
			public int compare(File o1, File o2) {
				return o1.getAbsoluteFile().compareTo(o2.getAbsoluteFile());
			}
		});
	}

}
