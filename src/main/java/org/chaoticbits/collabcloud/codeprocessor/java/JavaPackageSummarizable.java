package org.chaoticbits.collabcloud.codeprocessor.java;

import java.io.File;

import org.chaoticbits.collabcloud.ISummarizable;

/**
 * A summarizable that represents a package
 * 
 * @author Andy
 * 
 */
public class JavaPackageSummarizable implements ISummarizable {
	private final String packageName;
	private final File file;

	public JavaPackageSummarizable(String packageName) {
		this.packageName = packageName;
		this.file = new File(packageName.replaceAll(".", "/"));
	}

	public String getPackageName() {
		return packageName;
	}

	public File getFile() {
		return file;
	}

	@Override
	public String toString() {
		return "JavaPackageSummarizable [packageName=" + packageName + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((file == null) ? 0 : file.hashCode());
		result = prime * result + ((packageName == null) ? 0 : packageName.hashCode());
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
		JavaPackageSummarizable other = (JavaPackageSummarizable) obj;
		if (file == null) {
			if (other.file != null)
				return false;
		} else if (!file.equals(other.file))
			return false;
		if (packageName == null) {
			if (other.packageName != null)
				return false;
		} else if (!packageName.equals(other.packageName))
			return false;
		return true;
	}
}
