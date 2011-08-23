package org.chaoticbits.collabcloud.codeprocessor.java;

import org.chaoticbits.collabcloud.codeprocessor.ISummarizable;

public class JavaClassArtifact implements ISummarizable {

	private String path = "";
	private String name = "";

	public JavaClassArtifact(String path, String name) {
		this.path = path;
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public String getName() {
		return name;
	}
}
