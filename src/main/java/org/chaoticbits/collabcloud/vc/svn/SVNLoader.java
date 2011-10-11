package org.chaoticbits.collabcloud.vc.svn;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import org.chaoticbits.collabcloud.CloudWeights;
import org.chaoticbits.collabcloud.ISummarizable;
import org.chaoticbits.collabcloud.codeprocessor.IWeightModifier;
import org.chaoticbits.collabcloud.vc.Developer;
import org.chaoticbits.collabcloud.vc.IVersionControlLoader;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNLogEntryPath;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNDiffClient;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

public class SVNLoader implements IVersionControlLoader {

	private final SVNRepository repo;
	private boolean loaded = false;
	private final long startRevision;
	private final long endRevision;
	private final Set<Developer> devs = new LinkedHashSet<Developer>();

	public SVNLoader(File repoDir, long startRevision, long endRevision) throws SVNException {
		this.startRevision = startRevision;
		this.endRevision = endRevision;
		FSRepositoryFactory.setup();
		repo = SVNRepositoryFactory.create(SVNURL.fromFile(repoDir));
	}

	public Set<Developer> getDevelopers() throws IOException {
		load();
		return devs;
	}

	private void load() {
		if (!loaded) {
			try {
				@SuppressWarnings("unchecked")
				Collection<SVNLogEntry> logEntries = (Collection<SVNLogEntry>) repo.log(new String[] { "" }, null, startRevision, endRevision,
						true, true);
				for (SVNLogEntry logEntry : logEntries) {
					devs.add(new Developer(logEntry.getAuthor(), ""));
				}
				loaded = true;
			} catch (SVNException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public Set<ISummarizable> getFilesChanged() throws IOException {
		throw new IllegalStateException("unimplemented!");
	}

	public CloudWeights crossWithDiff(CloudWeights weights, IWeightModifier modifier) throws IOException {
		throw new IllegalStateException("unimplemented!");
	}

}
