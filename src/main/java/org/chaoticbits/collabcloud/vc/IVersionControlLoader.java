package org.chaoticbits.collabcloud.vc;

import java.io.IOException;
import java.util.Set;

import org.chaoticbits.collabcloud.CloudWeights;
import org.chaoticbits.collabcloud.ISummarizable;
import org.chaoticbits.collabcloud.codeprocessor.IWeightModifier;

public interface IVersionControlLoader {

	public abstract Set<Developer> getDevelopers() throws IOException;

	public abstract Set<ISummarizable> getFilesChanged() throws IOException;

	public abstract CloudWeights crossWithDiff(CloudWeights weights, IWeightModifier modifier) throws IOException;
}
