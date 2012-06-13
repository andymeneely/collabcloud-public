package org.chaoticbits.collabcloud.eclipse;

import org.chaoticbits.collabcloud.eclipse.actions.RefreshAction;
import org.chaoticbits.collabcloud.eclipse.actions.SetSrcTreeAction;
import org.chaoticbits.collabcloud.eclipse.actions.StopAction;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

/**
 * The main UI for the cloud view
 * @author Andy Meneely
 * 
 */

public class RepoCloudView extends ViewPart {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "org.chaoticbits.collabcloud.eclipse.RepoCloudView";
	private Canvas canvas;

	/**
	 * The constructor.
	 */
	public RepoCloudView() {}

	public Canvas getCanvas() {
		return canvas;
	}

	/**
	 * This is a callback that will allow us to create the viewer and initialize it.
	 */
	public void createPartControl(Composite parent) {
		canvas = new Canvas(parent, SWT.NONE);
		canvas.addPaintListener(new RepoCloudPaintListener(parent));
		setFocus();
		buildMenu();
	}

	/**
	 * Passing the focus request to the view's canvas.
	 */
	public void setFocus() {
		canvas.setFocus();
	}

	/**
	 * Create and hook the different actions used in the view
	 */
	private void buildMenu() {
		SetSrcTreeAction setSrcTree = new SetSrcTreeAction(this);
		getViewSite().getActionBars().getMenuManager().add(setSrcTree);
		getViewSite().getActionBars().getToolBarManager().add(setSrcTree);

		RefreshAction refresh = new RefreshAction(this);
		getViewSite().getActionBars().getMenuManager().add(refresh);
		getViewSite().getActionBars().getToolBarManager().add(refresh);

		StopAction stop = new StopAction(this);
		getViewSite().getActionBars().getMenuManager().add(stop);
		getViewSite().getActionBars().getToolBarManager().add(stop);
		stop.setEnabled(false);
	}
}