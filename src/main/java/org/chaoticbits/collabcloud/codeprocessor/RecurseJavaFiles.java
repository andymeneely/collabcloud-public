package org.chaoticbits.collabcloud.codeprocessor;

import java.io.File;
import java.io.FilenameFilter;

public class RecurseJavaFiles extends RecurseFiles {
	public RecurseJavaFiles() {
		super(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(".java");
			}
		});
	}
}
