package org.chaoticbits.collabcloud.eclipse.actions;

import org.chaoticbits.collabcloud.eclipse.Activator;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Composite;

public class StopAction extends Action {

	private final Composite parent;

	public StopAction(Composite parent) {
		this.parent = parent;
		setText("Stop");
		setToolTipText("Halt the current visualization process");
		setImageDescriptor(Activator.getImageDescriptor("icons/progress_stop.gif"));
	}

	@Override
	public void run() {
		MessageDialog.openInformation(parent.getShell(), "Repository Cloud", "Refresh! Not implemented (yet)");
	}

}
