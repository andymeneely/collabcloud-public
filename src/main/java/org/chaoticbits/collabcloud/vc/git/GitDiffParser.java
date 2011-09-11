package org.chaoticbits.collabcloud.vc.git;

import java.util.Map.Entry;
import java.util.Set;

import org.chaoticbits.collabcloud.codeprocessor.CloudWeights;
import org.chaoticbits.collabcloud.codeprocessor.ISummaryToken;
import org.chaoticbits.collabcloud.codeprocessor.IWeightModifier;

public class GitDiffParser {

	private final String[] ignorePrefixes = { "index", "diff", "@@" };
	private final String javaDelimiters = "[ ,;\\(\\)\\[\\]<>\\{\\}\\.:&\\|\\/\\+\\-]";
	private Set<Entry<ISummaryToken, Double>> entries;

	public void processTextLine(CloudWeights weights, IWeightModifier modifier, String line) {
		if (ignoreIt(line))
			return;
		if (entries == null)
			entries = weights.unsortedEntries();
		String[] lineTokens = line.split(javaDelimiters);
		for (String lineToken : lineTokens) {
			for (Entry<ISummaryToken, Double> entry : entries) {
				if (lineToken.trim().equals(entry.getKey().getToken()))
					weights.put(entry.getKey(), modifier.modify(entry.getValue()));
			}
		}
	}

	private boolean ignoreIt(String line) {
		for (String prefix : ignorePrefixes) {
			if (line.startsWith(prefix))
				return true;
		}
		return false;
	}

}
