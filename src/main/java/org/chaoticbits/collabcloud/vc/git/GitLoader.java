package org.chaoticbits.collabcloud.vc.git;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

import org.chaoticbits.collabcloud.CloudWeights;
import org.chaoticbits.collabcloud.ISummarizable;
import org.chaoticbits.collabcloud.codeprocessor.IWeightModifier;
import org.chaoticbits.collabcloud.codeprocessor.java.JavaClassSummarizable;
import org.chaoticbits.collabcloud.vc.Developer;
import org.chaoticbits.collabcloud.vc.IVersionControlLoader;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

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
	private final GitDiffParser diffParser = new GitDiffParser();

	public GitLoader(File repoDir) throws IOException {
		repo = new FileRepositoryBuilder().setGitDir(repoDir).readEnvironment().findGitDir().build();
	}

	public GitLoader(File repoDir, String sinceSHA1) throws IOException {
		repo = new FileRepositoryBuilder().setGitDir(repoDir).readEnvironment().findGitDir().build();
		since = repo.resolve(sinceSHA1);
	}

	public Set<Developer> getDevelopers() {
		checkSince();
		Set<Developer> set = new HashSet<Developer>();
		RevWalk rw = loadRevWalk();
		Iterator<RevCommit> itr = rw.iterator();
		while (itr.hasNext()) {
			RevCommit next = itr.next();
			set.add(new Developer(next.getAuthorIdent().getName(), next.getAuthorIdent().getEmailAddress()));
		}
		return set;
	}

	private void checkSince() {
		if (since == null)
			throw new IllegalAccessError("Mark the since variable first.");
	}

	public Set<ISummarizable> getFilesChanged() throws IOException {
		checkSince();
		return extractFiles(buildDiffString(), new HashSet<ISummarizable>());
	}

	private String buildDiffString() throws IOException {
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

	private Set<ISummarizable> extractFiles(String diffsString, Set<ISummarizable> set) {
		Scanner scanner = new Scanner(diffsString);
		while (scanner.hasNext()) {
			String line = scanner.nextLine();
			if (line.startsWith("+++") || line.startsWith("---")) {
				set.add(new JavaClassSummarizable(new File(line.substring(6))));
			}
		}
		return set;
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

	public void markSince(ObjectId since) {
		if (since != null)
			this.since = since;
	}

	public ObjectId getSince() {
		return since;
	}

	public CloudWeights crossWithDiff(CloudWeights weights, IWeightModifier modifier) throws IOException {
		Scanner scanner = new Scanner(buildDiffString());
		while (scanner.hasNextLine()) {
			diffParser.processTextLine(weights, modifier, scanner.nextLine());
		}
		return weights;
	}

}
