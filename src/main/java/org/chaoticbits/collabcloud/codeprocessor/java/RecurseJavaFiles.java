package org.chaoticbits.collabcloud.codeprocessor.java;

import java.io.File;
import java.io.FilenameFilter;

import org.chaoticbits.collabcloud.codeprocessor.RecurseFiles;

public class RecurseJavaFiles extends RecurseFiles {
	public RecurseJavaFiles() {
		super(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(".java");
			}
		});
	}
}
