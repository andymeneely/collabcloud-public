package org.chaoticbits.collabcloud.eclipse.actions;

import org.chaoticbits.collabcloud.eclipse.Activator;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Composite;

public class RefreshAction extends Action {

	private final Composite parent;

	public RefreshAction(Composite parent) {
		this.parent = parent;
		setText("Refresh");
		setToolTipText("Refresh visualization");
		setImageDescriptor(Activator.getImageDescriptor("icons/refresh.gif"));
	}

	@Override
	public void run() {
		MessageDialog.openInformation(parent.getShell(), "Repository Cloud", "Refresh! Not implemented (yet)");
	}

}
