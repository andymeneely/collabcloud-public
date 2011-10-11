package org.chaoticbits.collabcloud.vc.svn;

import org.chaoticbits.collabcloud.vc.Developer;

public class SVNDeveloperParser {

	/**
	 * Attempts to parse out an email from the author line in an SVN commit. Specifically, it looks for < and
	 * > brackets and and @ symbol. E.g. Andy Meneely <andy@something> 
	 * @param nameEmail
	 * @return
	 */
	public Developer parse(String nameEmail) {
		String name = nameEmail;
		String email = "";
		if (nameEmail.matches("[\\w\\s\\.\\']*\\<[\\w\\.]*\\@[\\w\\.]*\\>[\\s]*")) {
			String[] line = name.split("[\\<\\>]");
			if (line.length > 1) {
				name = line[0];
				email = line[1];
			}
		}
		return new Developer(name.trim(), email.trim());
	}
}
