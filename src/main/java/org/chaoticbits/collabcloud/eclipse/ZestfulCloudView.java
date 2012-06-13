package org.chaoticbits.collabcloud.eclipse;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;

import org.chaoticbits.collabcloud.CloudWeights;
import org.chaoticbits.collabcloud.ISummaryToken;
import org.chaoticbits.collabcloud.codeprocessor.MultiplyModifier;
import org.chaoticbits.collabcloud.codeprocessor.java.JavaProjectSummarizer;
import org.chaoticbits.collabcloud.vc.git.GitLoader;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.zest.core.viewers.AbstractZoomableViewer;
import org.eclipse.zest.core.viewers.GraphViewer;
import org.eclipse.zest.core.viewers.IZoomableWorkbenchPart;
import org.eclipse.zest.core.viewers.ZoomContributionViewItem;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.GraphNode;
import org.eclipse.zest.core.widgets.ZestStyles;
import org.eclipse.zest.layouts.algorithms.SpringLayoutAlgorithm;

/**
 * The main UI for the cloud view
 * @author Andy Meneely
 * 
 */
public class ZestfulCloudView extends ViewPart implements IZoomableWorkbenchPart {
	private static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(ZestfulCloudView.class);
	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "org.chaoticbits.collabcloud.eclipse.ZestfulCloudView";
	private Graph graph;
	private GraphViewer viewer;

	/**
	 * The constructor.
	 */
	public ZestfulCloudView() {}

	/**
	 * This is a callback that will allow us to create the viewer and initialize it.
	 */
	public void createPartControl(Composite parent) {
		graph = new Graph(parent, SWT.NONE);
		File srcTree = new File("C:/local/workspaces/workspace/CollabCloud/testgitrepo");
		log.info("Loading source tree of: " + srcTree.toString());
		CloudWeights weights;
		try {
			weights = new JavaProjectSummarizer().summarize(srcTree);
			weights = new GitLoader(new File(srcTree.getAbsolutePath() + "/.git"),
					"bac7225dfb6ce2eb84c38f019defad21197514b6").crossWithDiff(weights, new MultiplyModifier(1.2));
			List<Entry<ISummaryToken, Double>> sortedEntries = weights.sortedEntries();
			if (sortedEntries.size() > 0) {
				double max = sortedEntries.get(0).getValue();
				double multiplier = 50.0d / Math.log(max);
				for (Entry<ISummaryToken, Double> entry : sortedEntries) {
					GraphNode node = new GraphNode(graph, ZestStyles.NODES_HIDE_TEXT, entry.getKey().getToken());
					// Color white = new Color(parent.getDisplay(), 255, 255, 255);
					// node.setBackgroundColor(white);
					// node.setBorderColor(white);
					// node.setNodeStyle(SWT.NONE);
					Image image = new Image(parent.getDisplay(), 200, 200);
					GC gc = new GC(image);

					int fontSize = (int) (multiplier * (Math.log(entry.getValue() + 1.0)));
					Font font = new Font(parent.getDisplay(), "Lucida Sans", fontSize, SWT.BOLD);
					node.setFont(font);

					gc.setFont(font);
					gc.drawString(entry.getKey().getToken(), 0, 0);
					node.setImage(image);
					gc.dispose();
				}
				// No edges...
			}
			// TODO try false here
			graph.setLayoutAlgorithm(new SpringLayoutAlgorithm(), true);
			graph.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					System.out.println(e);
				}
			});
			fillToolBar();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void fillToolBar() {
		// getViewSite().getActionBars().getMenuManager().add(new ZoomContributionViewItem(this));
	}

	/**
	 * Create and hook the different actions used in the view
	 */
	// private void buildMenu() {
	// SetSrcTreeAction setSrcTree = new SetSrcTreeAction(this);
	// getViewSite().getActionBars().getMenuManager().add(setSrcTree);
	// getViewSite().getActionBars().getToolBarManager().add(setSrcTree);
	//
	// RefreshAction refresh = new RefreshAction(this);
	// getViewSite().getActionBars().getMenuManager().add(refresh);
	// getViewSite().getActionBars().getToolBarManager().add(refresh);
	//
	// StopAction stop = new StopAction(this);
	// getViewSite().getActionBars().getMenuManager().add(stop);
	// getViewSite().getActionBars().getToolBarManager().add(stop);
	// stop.setEnabled(false);
	// }

	@Override
	public void setFocus() {}

	@Override
	public AbstractZoomableViewer getZoomableViewer() {
		return viewer;
	}
}