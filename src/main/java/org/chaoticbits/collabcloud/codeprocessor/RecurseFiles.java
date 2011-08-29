package org.chaoticbits.collabcloud.codeprocessor;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

public class RecurseFiles {

	private final FilenameFilter filter;

	public RecurseFiles(FilenameFilter filter) {
		this.filter = filter;
	}

	/**
	 * Loads a list of files recursively from the top directory given (can be slow!)
	 * @param topDir
	 * @return
	 */
	public List<File> loadRecursive(File topDir) {
		List<File> list = new ArrayList<File>();
		if (topDir.isDirectory()) {
			File[] files = topDir.listFiles();
			for (File file : files) {
				if (file.isDirectory())
					list.addAll(loadRecursive(file));
				else if (filter.accept(topDir, file.getName()))
					list.add(file);
			}
		}
		return list;
	}

}
