package org.chaoticbits.collabcloud;

public interface ISummaryToken {

	//TODO A token should have multiple summarizables
	abstract public ISummarizable getParentSummarizable();

	abstract public String getToken();
	
	abstract public String getFullName();
	
	abstract public ITokenType getType();
	
	abstract public <T> T accept(ISummaryTokenVisitor<T> visitor);
	
}
