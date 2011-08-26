package org.chaoticbits.collabcloud.codeprocessor;

public interface ISummaryToken {

	abstract public ISummarizable getParentSummarizable();

	abstract public String getToken();
	
	abstract public String getFullName();
	
	abstract public String getType();
	
}
