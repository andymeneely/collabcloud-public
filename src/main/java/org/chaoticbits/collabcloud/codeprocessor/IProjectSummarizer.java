package org.chaoticbits.collabcloud.codeprocessor;

import java.io.File;
import java.io.IOException;

public interface IProjectSummarizer {
	/**
	 * Given a top directory, summarize the files as CloudWeights
	 * 
	 * @param pathToRepo
	 * @return
	 * @throws IOException
	 */
	public CloudWeights summarize(File pathToRepo) throws IOException;
}
