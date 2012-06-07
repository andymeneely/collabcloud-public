package org.chaoticbits.collabcloud.eclipse.actions;

import org.chaoticbits.collabcloud.eclipse.Activator;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Composite;

public class SetSrcTreeAction extends Action {
	private final Composite parent;

	public SetSrcTreeAction(Composite parent) {
		this.parent = parent;
		setText("Set source tree");
		setToolTipText("Change the root directory of the source code to visualize.");
		setImageDescriptor(Activator.getImageDescriptor("icons/add.gif"));
	}

	@Override
	public void run() {
		MessageDialog.openInformation(parent.getShell(), "Repository Cloud", "Set source tree! Not implemented (yet)");
	}
}
