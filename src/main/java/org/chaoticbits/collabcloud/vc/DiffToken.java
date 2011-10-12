package org.chaoticbits.collabcloud.vc;

import org.chaoticbits.collabcloud.ISummarizable;
import org.chaoticbits.collabcloud.ISummaryToken;
import org.chaoticbits.collabcloud.ISummaryTokenVisitor;
import org.chaoticbits.collabcloud.ITokenType;

public class DiffToken implements ISummaryToken {

	public enum DiffTokenType implements ITokenType {
		TOKEN
	}

	private final ISummarizable summarizable;
	private final String token;
	private final String fullName;

	public DiffToken(ISummarizable summarizable, String token, String fullName) {
		this.summarizable = summarizable;
		this.token = token;
		this.fullName = fullName;
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

	public ITokenType getType() {
		return DiffTokenType.TOKEN;
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
		DiffToken other = (DiffToken) obj;
		if (token == null) {
			if (other.token != null)
				return false;
		} else if (!token.equals(other.token))
			return false;
		return sameParent(other);
	}

	private boolean sameParent(DiffToken obj) {
		if (summarizable == null)
			return summarizable == obj.summarizable;
		return this.summarizable == obj.summarizable || summarizable.equals(obj.summarizable);
	}

	@Override
	public String toString() {
		return token + "(" + fullName + ", " + summarizable + ")";
	}

	public <T> T accept(ISummaryTokenVisitor<T> visitor) {
		return visitor.visit(this);
	}
	
}
