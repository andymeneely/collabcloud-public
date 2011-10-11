package org.chaoticbits.collabcloud.vc.svn;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

import org.chaoticbits.collabcloud.CloudWeights;
import org.chaoticbits.collabcloud.ISummarizable;
import org.chaoticbits.collabcloud.codeprocessor.java.JavaClassSummarizable;
import org.chaoticbits.collabcloud.codeprocessor.java.JavaProjectSummarizer;
import org.chaoticbits.collabcloud.vc.Developer;
import org.chaoticbits.collabcloud.vc.DiffParser;
import org.chaoticbits.collabcloud.vc.IVersionControlLoader;
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNLogEntryPath;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNRevision;

public class SVNLoader implements IVersionControlLoader {

	private final SVNRepository repo;
	private boolean loaded = false;
	private final long startRevision;
	private final long endRevision;
	private final Map<Developer, Set<ISummarizable>> contributions = new LinkedHashMap<Developer, Set<ISummarizable>>();
	private final String topSVNPath;
	private final DiffParser diffParser = new DiffParser();
	private final SVNClientManager cm = SVNClientManager.newInstance();
	private final CloudWeights weights = new CloudWeights();

	public SVNLoader(File repoDir, String topSVNPath, long startRevision, long endRevision) throws SVNException {
		this.topSVNPath = topSVNPath;
		this.startRevision = startRevision;
		this.endRevision = endRevision;
		FSRepositoryFactory.setup();
		repo = SVNRepositoryFactory.create(SVNURL.fromFile(repoDir));
	}

	public Set<Developer> getDevelopers() throws IOException {
		load();
		return contributions.keySet();
	}

	private void load() {
		if (!loaded) {
			try {
				@SuppressWarnings("unchecked")
				// TODO add the topSVNPath to repo log command to speed it up
				Collection<SVNLogEntry> logEntries = (Collection<SVNLogEntry>) repo.log(new String[] {}, null, startRevision, endRevision, true,
						true);
				for (SVNLogEntry logEntry : logEntries) {
					Developer dev = new SVNDeveloperParser().parse(logEntry.getAuthor());
					Set<ISummarizable> changed = new LinkedHashSet<ISummarizable>();
					for (Object obj : logEntry.getChangedPaths().entrySet()) {
						@SuppressWarnings("unchecked")
						Entry<String, SVNLogEntryPath> entry = (Entry<String, SVNLogEntryPath>) obj;
						String svnPath = entry.getKey();
						if (svnPath.startsWith(topSVNPath)) {
							JavaClassSummarizable summarizable = new JavaClassSummarizable(new File(svnPath.replaceFirst(topSVNPath, "")));
							buildCloudWeights(dev, logEntry, summarizable);
							changed.add(summarizable);
						}
					}
					contributions.put(dev, changed);
				}
				loaded = true;
			} catch (SVNException e) {
				e.printStackTrace();
			}
		}
	}

	public Set<ISummarizable> getFilesChanged() throws IOException {
		load();
		Set<ISummarizable> files = new HashSet<ISummarizable>();
		for (Set<ISummarizable> set : contributions.values()) {
			files.addAll(set);
		}
		for (ISummarizable iSummarizable : files) {
			System.out.println(iSummarizable);
		}
		return files;
	}

	/**
	 * Based on {@link DiffParser}, return a set of CloudWeights that counts each token. Each token is
	 * counted once. Use this CloudWeights to intersect with the CloudWeights returned from somethign like a
	 * {@link JavaProjectSummarizer} to weigh it.
	 * 
	 * @return {@link CloudWeights}
	 * @throws {@link IOException}
	 */
	public CloudWeights getCloudWeights() throws IOException {
		load();
		return weights;
	}

	private void buildCloudWeights(Developer dev, SVNLogEntry logEntry, JavaClassSummarizable summarizable) {
		SVNRevision pegRevision = SVNRevision.create(logEntry.getRevision() - 1);
		SVNRevision rN = pegRevision;
		SVNRevision rM = SVNRevision.create(logEntry.getRevision());
		SVNDepth depth = SVNDepth.INFINITY;
		boolean useAncestry = true;
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try {
			cm.getDiffClient().doDiff(repo.getLocation(), pegRevision, rN, rM, depth, useAncestry, outputStream);
		} catch (SVNException e) {
			e.printStackTrace();
		}
		Scanner scanner = new Scanner(outputStream.toString());
		while (scanner.hasNextLine()) {
			String nextLine = scanner.nextLine();
			diffParser.processTextLine(nextLine, weights, summarizable);
		}
		scanner.close();
	}

	public Set<ISummarizable> getFilesContributed(Developer dev) {
		return contributions.get(dev);
	}

}
