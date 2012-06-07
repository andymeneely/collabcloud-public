package org.chaoticbits.collabcloud.eclipse;

import org.chaoticbits.collabcloud.eclipse.actions.RefreshAction;
import org.chaoticbits.collabcloud.eclipse.actions.SetSrcTreeAction;
import org.chaoticbits.collabcloud.eclipse.actions.StopAction;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

/**
 * This sample class demonstrates how to plug-in a new workbench view. The view shows data obtained from the
 * model. The sample creates a dummy model on the fly, but a real implementation would connect to the model
 * available either in this or another plug-in (e.g. the workspace). The view is connected to the model using
 * a content provider.
 * <p>
 * The view uses a label provider to define how model objects should be presented in the view. Each view can
 * present the same model objects using different labels and icons, if needed. Alternatively, a single label
 * provider can be shared between views in order to ensure that objects of the same type are presented in the
 * same way everywhere.
 * <p>
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