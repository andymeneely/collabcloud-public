package org.chaoticbits.collabcloud.vc;

import java.io.File;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Pattern;

import org.chaoticbits.collabcloud.CloudWeights;
import org.chaoticbits.collabcloud.ISummarizable;
import org.chaoticbits.collabcloud.ISummaryToken;
import org.chaoticbits.collabcloud.codeprocessor.java.JavaClassSummarizable;

public class DiffParser {
	private final String[] ignorePrefixes = { "index", "diff", "@@", "Index:" };
	private final String javaDelimiters = "[ ,;\\(\\)\\[\\]<>\\{\\}\\.:&\\|\\/\\+\\-]";
	private final Pattern wordRegex = Pattern.compile("[a-zA-Z][\\w]+");

	public DiffParser() {}

	public ISummarizable processTextLine(String line, CloudWeights weights, ISummarizable summarizable) {
		if (ignoreIt(line))
			return summarizable;
		String[] lineTokens = line.split(javaDelimiters);
		for (String lineToken : lineTokens) {
			lineToken = lineToken.trim();
			if (isWord(lineToken))
				weights.increment(new DiffToken(summarizable, lineToken, lineToken), 1.0);
		}
		return summarizable;
	}

	public ISummarizable processTextLine(String line, CloudWeights weights, Map<Developer, Set<ISummarizable>> contributions,
			Developer developer, ISummarizable summarizable) {
		if (isFile(line)) {
			addContribution(developer, contributions, line);
		}
		return processTextLine(line, weights, summarizable);
	}

	public boolean isFile(String line) {
		return line.startsWith("+++");
	}

	/**
	 * Requires that the line starts with +++ from the diff format
	 * @param plusLine
	 * @return
	 */
	public JavaClassSummarizable makeSummarizable(String plusLine) {
		return new JavaClassSummarizable(new File(plusLine.substring(6)));
	}

	private JavaClassSummarizable addContribution(Developer developer, Map<Developer, Set<ISummarizable>> contributions, String line) {
		Set<ISummarizable> files = contributions.get(developer);
		if (files == null)
			files = new LinkedHashSet<ISummarizable>();
		JavaClassSummarizable summarizable = makeSummarizable(line);
		files.add(summarizable);
		contributions.put(developer, files);
		return summarizable;
	}

	private boolean ignoreIt(String line) {
		for (String prefix : ignorePrefixes) {
			if (line.startsWith(prefix))
				return true;
		}
		return false;
	}

	private boolean isWord(String lineToken) {
		return wordRegex.matcher(lineToken).matches();
	}
}
