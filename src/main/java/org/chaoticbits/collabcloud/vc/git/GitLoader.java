package org.chaoticbits.collabcloud.vc.git;

import java.util.Set;

import org.chaoticbits.collabcloud.Developer;
import org.chaoticbits.collabcloud.codeprocessor.ISummarizable;
import org.chaoticbits.collabcloud.vc.IVersionControlLoader;

public class GitLoader implements IVersionControlLoader {

	public Set<Developer> getDevelopers() {
		throw new IllegalStateException("unimplemented!");
	}

	public Set<ISummarizable> getFiles() {
		throw new IllegalStateException("unimplemented!");
	}

}
