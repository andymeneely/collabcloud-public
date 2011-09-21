package org.chaoticbits.collabcloud.codeprocessor.java;

import java.io.File;

import org.chaoticbits.collabcloud.ISummarizable;

public class JavaClassSummarizable implements ISummarizable {

	private File file;

	public JavaClassSummarizable(File path) {
		this.file = path;
	}

	public File getFile() {
		return file;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((file == null) ? 0 : file.hashCode());
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
		JavaClassSummarizable other = (JavaClassSummarizable) obj;
		if (file == null) {
			if (other.file != null)
				return false;
		} else if (!file.equals(other.file))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return file.toString();
	}
}
