package org.chaoticbits.collabcloud.eclipse.actions;

import org.chaoticbits.collabcloud.eclipse.Activator;
import org.chaoticbits.collabcloud.eclipse.RepoCloudView;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;

public class SetSrcTreeAction extends Action {
	private final RepoCloudView view;

	public SetSrcTreeAction(RepoCloudView view) {
		this.view = view;
		setText("Set source tree");
		setToolTipText("Change the root directory of the source code to visualize.");
		setImageDescriptor(Activator.getImageDescriptor("icons/add.gif"));
	}

	@Override
	public void run() {
		MessageDialog.openInformation(view.getCanvas().getShell(), "Repository Cloud", "Set source tree! Not implemented (yet)");
	}
}
