package org.chaoticbits.collabcloud.vc.git;

import java.io.File;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.chaoticbits.collabcloud.CloudWeights;
import org.chaoticbits.collabcloud.ISummarizable;
import org.chaoticbits.collabcloud.ISummaryToken;
import org.chaoticbits.collabcloud.codeprocessor.IWeightModifier;
import org.chaoticbits.collabcloud.codeprocessor.java.JavaClassSummarizable;
import org.chaoticbits.collabcloud.vc.Developer;

public class GitDiffParser {
	private static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(GitDiffParser.class);
	private final String[] ignorePrefixes = { "index", "diff", "@@" };
	private final String javaDelimiters = "[ ,;\\(\\)\\[\\]<>\\{\\}\\.:&\\|\\/\\+\\-]";
	private Set<Entry<ISummaryToken, Double>> entries;

	public GitDiffParser() {}

	public void processTextLine(String line, CloudWeights weights, IWeightModifier modifier, Map<Developer, Set<ISummarizable>> contributions,
			Developer developer) {
		if (isFile(line)) {
			addContribution(developer, contributions, line);
			return;
		}
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

	private boolean isFile(String line) {
		return line.startsWith("+++");
	}

	private void addContribution(Developer developer, Map<Developer, Set<ISummarizable>> contributions, String line) {
		Set<ISummarizable> files = contributions.get(developer);
		if (files == null)
			files = new LinkedHashSet<ISummarizable>();
		files.add(new JavaClassSummarizable(new File(line.substring(6))));
		contributions.put(developer, files);
	}

	private boolean ignoreIt(String line) {
		for (String prefix : ignorePrefixes) {
			if (line.startsWith(prefix))
				return true;
		}
		return false;
	}
}
