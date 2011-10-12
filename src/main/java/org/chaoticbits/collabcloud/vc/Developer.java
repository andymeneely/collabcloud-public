package org.chaoticbits.collabcloud.vc;

import org.chaoticbits.collabcloud.ISummarizable;
import org.chaoticbits.collabcloud.ISummaryToken;
import org.chaoticbits.collabcloud.ISummaryTokenVisitor;
import org.chaoticbits.collabcloud.ITokenType;

public class Developer implements ISummaryToken {
	private String name = "";
	private String email = "";

	public Developer(String name, String email) {
		this.name = name;
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public String toString() {
		return getFullName();
	}

	public ISummarizable getParentSummarizable() {
		throw new IllegalStateException("unimplemented!");
	}

	// TODO this should be interfaced out to get an image...or something
	public String getToken() {
		return name;
	}

	public String getFullName() {
		return name + " <" + email + ">";
	}

	public ITokenType getType() {
		return DeveloperTokenType.COMMITTER;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Developer other = (Developer) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public <T> T accept(ISummaryTokenVisitor<T> visitor) {
		return visitor.visit(this);
	}
}
