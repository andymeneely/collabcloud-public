package org.chaoticbits.collabcloud.vc;

import java.util.Map;
import java.util.Set;

import org.chaoticbits.collabcloud.ISummarizable;
import org.chaoticbits.collabcloud.ISummaryToken;
import org.chaoticbits.collabcloud.visualizer.placement.INetworkBuilder;
import org.eclipse.jgit.revwalk.RevCommit;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;

public class TokenContributionNetwork implements INetworkBuilder {

	private final Map<Developer, Set<ISummarizable>> contributions;

	public TokenContributionNetwork(Map<Developer, Set<ISummarizable>> contributions) {
		this.contributions = contributions;
	}

	public Graph<ISummaryToken, Long> build() {
		Graph<ISummaryToken, Long> graph = new UndirectedSparseGraph<ISummaryToken, Long>();
		throw new IllegalStateException("unimplemented!");
		// try {
		// RevCommit next = itr.next();
		// RevTree tree = next.getTree();
		// TreeWalk walk = new TreeWalk(repo);
		// DiffFormatter formatter = new DiffFormatter(DisabledOutputStream.INSTANCE);
		// Git git = new Git(repo);
		// LogCommand log = git.log();
		//
		// walk.addTree(tree);
		// walk.setRecursive(true);
		// while (walk.next()) {
		// System.out.println(walk.getPathString());
		// }
		//
		// } catch (AmbiguousObjectException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// while (itr.hasNext()) {
		// RevCommit commit = itr.next();
		// try {
		// TreeWalk walk= new TreeWalk(repo);
		// walk.addTree(commit.getTree());
		// walk.setRecursive(true);
		// walk.next();
		// do{
		// String pathString = walk.getPathString();
		// System.out.println(pathString);
		// }while(walk.next());
		//
		// } catch (MissingObjectException e) {
		// e.printStackTrace();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// // graph.add(edgeCount++, author(commit), file);
		// }

		// return graph;

	}

	private Developer author(RevCommit commit) {
		return new Developer(commit.getAuthorIdent().getName(), commit.getAuthorIdent().getEmailAddress());
	}
}
