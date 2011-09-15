package org.chaoticbits.collabcloud.codeprocessor.java;

import org.chaoticbits.collabcloud.ISummarizable;
import org.chaoticbits.collabcloud.ISummaryToken;

/**
 * A specific element of a Java class. Could be a method name, variable name,
 * package name, etc.
 * 
 * @author andy
 * 
 */
public class JavaSummaryToken implements ISummaryToken {

	private ISummarizable summarizable;
	private String fullName;
	private String token;
	private JavaTokenType type;

	public JavaSummaryToken(ISummarizable summarizable, String fullName, String token, JavaTokenType type) {
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

	public JavaTokenType getType() {
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
		return sameParent(other);
	}

	private boolean sameParent(JavaSummaryToken obj) {
		if (summarizable == null)
			return summarizable == obj.summarizable;
		return this.summarizable == obj.summarizable || summarizable.equals(obj.summarizable);
	}

	@Override
	public String toString() {
		return token + "(" + type + ", " + fullName + ", " + summarizable + ")";
	}
}
