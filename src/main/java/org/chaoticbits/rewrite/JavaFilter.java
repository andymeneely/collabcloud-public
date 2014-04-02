package org.chaoticbits.rewrite;

import java.util.List;

/**
 * Filters out words that may be Java keywords
 * or words that are in the 'excluded' list
 * @author Marissa Wilson
 *
 */
public class JavaFilter {
	/*
	 * Takes in repo files
	 * Calls Compare on each potential token in turn
	 * If true, moves on (could potentially cache for quicker reruns)
	 * If false, creates new token.
	 * Returns list of tokens
	 */
	public static List Filter(repofiles){
		
	}
	/*
	 * Takes in a potential token (potoken)
	 * Checks against the excluded words list
	 * Checks against Java keywords
	 * Returns true if potoken is found in either
	 * Returns false if potoken should be made into a token
	 */
	public static boolean Compare(String potoken){
		
	}
}
