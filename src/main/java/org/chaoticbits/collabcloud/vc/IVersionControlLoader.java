package org.chaoticbits.collabcloud.vc;

import java.util.Set;

import org.chaoticbits.collabcloud.Developer;
import org.chaoticbits.collabcloud.codeprocessor.ISummarizable;

public interface IVersionControlLoader {

	public abstract Set<Developer> getDevelopers();
	public abstract Set<ISummarizable> getFiles();
}
