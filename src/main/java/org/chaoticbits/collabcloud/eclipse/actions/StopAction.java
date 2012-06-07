package org.chaoticbits.collabcloud.eclipse.actions;

import org.chaoticbits.collabcloud.eclipse.Activator;
import org.chaoticbits.collabcloud.eclipse.RepoCloudView;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;

public class StopAction extends Action {

	private final RepoCloudView view;

	public StopAction(RepoCloudView view) {
		this.view = view;
		setText("Stop");
		setToolTipText("Halt the current visualization process");
		setImageDescriptor(Activator.getImageDescriptor("icons/progress_stop.gif"));
	}

	@Override
	public void run() {
		MessageDialog.openInformation(view.getCanvas().getShell(), "Repository Cloud", "Stop! Not implemented (yet)");
	}

}
