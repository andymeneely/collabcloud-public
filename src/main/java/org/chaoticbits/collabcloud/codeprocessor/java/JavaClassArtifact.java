package org.chaoticbits.collabcloud.codeprocessor.java;

import org.chaoticbits.collabcloud.codeprocessor.ISummarizable;

public class JavaClassArtifact implements ISummarizable {

	private String path = "";

	public JavaClassArtifact(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JavaClassArtifact other = (JavaClassArtifact) obj;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return path;
	}
}
