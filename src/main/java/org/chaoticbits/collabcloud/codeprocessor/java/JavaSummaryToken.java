package org.chaoticbits.collabcloud.codeprocessor.java;

import org.chaoticbits.collabcloud.codeprocessor.ISummarizable;
import org.chaoticbits.collabcloud.codeprocessor.ISummaryToken;

/**
 * A specific element of a Java class. Could be a method name, variable name, package name, etc.
 * 
 * @author andy
 * 
 */
public class JavaSummaryToken implements ISummaryToken {

	private JavaClassArtifact summarizable;
	private String fullName;
	private String token;
	private String type;

	public JavaSummaryToken(JavaClassArtifact summarizable, String fullName, String token, String type) {
		this.summarizable = summarizable;
		this.fullName = fullName;
		this.token = token;
		this.type = type;
	}

	public ISummarizable getParentSummarizable() {
		return summarizable;
	}

	public String getToken() {
		return token;
	}

	public String getFullName() {
		return fullName;
	}

	public String getType() {
		return type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((token == null) ? 0 : token.hashCode());
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
		JavaSummaryToken other = (JavaSummaryToken) obj;
		if (token == null) {
			if (other.token != null)
				return false;
		} else if (!token.equals(other.token))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return token + "(" + fullName + ")";
	}

}