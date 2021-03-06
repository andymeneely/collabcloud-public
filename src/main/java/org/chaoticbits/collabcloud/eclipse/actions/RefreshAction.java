package org.chaoticbits.collabcloud.eclipse.actions;

import org.chaoticbits.collabcloud.eclipse.Activator;
import org.chaoticbits.collabcloud.eclipse.RepoCloudView;
import org.eclipse.jface.action.Action;

public class RefreshAction extends Action {
	private static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(RefreshAction.class);

	private final RepoCloudView view;

	public RefreshAction(RepoCloudView view) {
		this.view = view;
		setText("Refresh");
		setToolTipText("Refresh visualization");
		setImageDescriptor(Activator.getImageDescriptor("icons/refresh.gif"));
	}

	@Override
	public void run() {
		log.info("Refresh initialized.");
		view.getCanvas().redraw();
		view.getCanvas().update();
	}

}
