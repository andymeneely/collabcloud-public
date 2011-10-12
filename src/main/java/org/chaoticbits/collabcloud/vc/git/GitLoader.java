package org.chaoticbits.collabcloud.vc.git;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.chaoticbits.collabcloud.CloudWeights;
import org.chaoticbits.collabcloud.ISummarizable;
import org.chaoticbits.collabcloud.ISummaryToken;
import org.chaoticbits.collabcloud.codeprocessor.IWeightModifier;
import org.chaoticbits.collabcloud.codeprocessor.MultiplyModifier;
import org.chaoticbits.collabcloud.codeprocessor.java.JavaProjectSummarizer;
import org.chaoticbits.collabcloud.vc.Developer;
import org.chaoticbits.collabcloud.vc.DiffParser;
import org.chaoticbits.collabcloud.vc.IVersionControlLoader;
import org.chaoticbits.collabcloud.vc.TokenContributionNetwork;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import edu.uci.ics.jung.graph.Graph;

/**
 * Uses JGit to parse the given Git repository and traverses the commits to
 * 
 * @author andy
 * 
 */
public class GitLoader implements IVersionControlLoader {
	private static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(GitLoader.class);

	private FileRepository repo;
	private ObjectId since;
	private final DiffParser diffParser = new DiffParser();
	private final Map<Developer, Set<ISummarizable>> contributions = new LinkedHashMap<Developer, Set<ISummarizable>>();
	private boolean loaded = false;

	// TODO Figure out when to pass these...
	private CloudWeights weights = new CloudWeights();
	@Deprecated
	private IWeightModifier modifier = new MultiplyModifier(1.1);

	public GitLoader(File repoDir) throws IOException {
		repo = new FileRepositoryBuilder().setGitDir(repoDir).readEnvironment().findGitDir().build();
	}

	public GitLoader(File repoDir, String sinceSHA1) throws IOException {
		repo = new FileRepositoryBuilder().setGitDir(repoDir).readEnvironment().findGitDir().build();
		since = repo.resolve(sinceSHA1);
	}

	public void markSince(ObjectId since) {
		if (since != null)
			this.since = since;
	}

	public Set<Developer> getDevelopers() {
		load();
		return contributions.keySet();
	}

	public Set<ISummarizable> getFilesChanged() throws IOException {
		load();
		Set<ISummarizable> files = new HashSet<ISummarizable>();
		for (Set<ISummarizable> set : contributions.values()) {
			files.addAll(set);
		}
		return files;
	}

	public Set<ISummarizable> getFilesContributed(Developer dev) {
		return contributions.get(dev);
	}

	private void checkSince() {
		if (since == null)
			throw new IllegalAccessError("Mark the since variable first.");
	}

	private void load() {
		if (!loaded) {
			checkSince();
			Iterator<RevCommit> itr = loadRevWalk().iterator();
			while (itr.hasNext()) {
				RevCommit commit = itr.next();
				Developer dev = new Developer(commit.getAuthorIdent().getName(), commit.getAuthorIdent().getEmailAddress());
				log.debug("Building diffstring, visiting commit: " + commit.getId().name());
				for (int parentIndex = 0; parentIndex < commit.getParentCount(); parentIndex++) {
					RevCommit parent = commit.getParent(parentIndex);
					ByteArrayOutputStream diff = new ByteArrayOutputStream();
					DiffFormatter formatter = new DiffFormatter(diff);
					formatter.setRepository(repo);
					ISummarizable currentSummarizable = null;
					try {
						formatter.format(commit, parent);
						Scanner scanner = new Scanner(diff.toString());
						while (scanner.hasNextLine()) { // scan until the diff part
							String line = scanner.nextLine();
							if (diffParser.isFile(line)) {
								currentSummarizable = diffParser.makeSummarizable(line);
							}
							if (currentSummarizable != null)
								diffParser.processTextLine(line, weights, contributions, dev, currentSummarizable);
						}
					} catch (IOException e) {
						System.err.println("IO Exception on commit " + commit.getId().toString());
						e.printStackTrace();
					}
				}
			}
			loaded = true;
		}
	}

	private RevWalk loadRevWalk() {
		RevWalk rw = new RevWalk(repo);
		try {
			log.debug("Loading revwalk...");
			ObjectId head = repo.resolve(Constants.HEAD);
			rw.markStart(rw.parseCommit(head));
			rw.markUninteresting(rw.parseCommit(since));
		} catch (MissingObjectException e) {
			System.err.println("Error loading git repo.");
			e.printStackTrace();
		} catch (IncorrectObjectTypeException e) {
			System.err.println("Error loading git repo.");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Error loading git repo.");
			e.printStackTrace();
		}
		return rw;
	}

	public FileRepository getRepo() {
		return repo;
	}

	public ObjectId getSince() {
		return since;
	}

	@Deprecated
	public CloudWeights crossWithDiff(CloudWeights weights, IWeightModifier modifier) throws IOException {
		this.weights = weights;
		this.modifier = modifier;
		loaded = false; // TODO Total hack - fix this
		load();
		return weights;
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

	public Graph<ISummaryToken, Long> contributionNetwork() {
		load();
		return new TokenContributionNetwork(contributions).build();
	}

	public String buildDiffString() throws IOException {
		RevWalk rw = loadRevWalk();
		StringBuilder builder = new StringBuilder();
		Iterator<RevCommit> itr = rw.iterator();
		while (itr.hasNext()) {
			RevCommit commit = itr.next();
			RevCommit parent = commit.getParent(0); // TODO Handle multiple
													// parents
			log.debug("Building diffstring, visiting commit: " + commit.getId().name());
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			DiffFormatter formatter = new DiffFormatter(out);
			formatter.setRepository(repo);
			formatter.format(commit, parent);
			builder.append(out.toString());
		}
		return builder.toString();
	}

}
