package org.chaoticbits.collabcloud.vc;

import java.io.IOException;
import java.util.Set;

import org.chaoticbits.collabcloud.Developer;
import org.chaoticbits.collabcloud.codeprocessor.CloudWeights;
import org.chaoticbits.collabcloud.codeprocessor.ISummarizable;

public interface IVersionControlLoader {

	public abstract Set<Developer> getDevelopers() throws IOException;

	public abstract Set<ISummarizable> getFilesChanged() throws IOException;

	public CloudWeights crossWithDiff(CloudWeights weights) throws IOException;
}
