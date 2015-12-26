package org.opendaylight.nemo.tool.eclipse.plugin.rest.actions;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.opendaylight.nemo.tool.eclipse.plugin.rest.collectinfo.GetContent;
import org.eclipse.jface.dialogs.MessageDialog;

/**
 * Our sample action implements workbench action delegate. The action proxy will
 * be created by the workbench and shown in the UI. When the user tries to use
 * the action, this delegate will be created and execution will be delegated to
 * it.
 *
 * @see IWorkbenchWindowActionDelegate
 */
public class NemoAction implements IWorkbenchWindowActionDelegate {
	private IWorkbenchWindow window;

	/**
	 * The constructor.
	 */
	public NemoAction() {
	}

	/**
	 * The action has been activated. The argument of the method represents the
	 * 'real' action sitting in the workbench UI.
	 *
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	public void run(IAction action) {
		String content = GetContent.activeContent();
		NemoParserImpl2 parserImpl = new NemoParserImpl2();
		parserImpl.format(content);
		boolean en = parserImpl.findRest();
		if (!en) {
             showDialog("Please add engine info.");
		}else{
			boolean us = parserImpl.findUser();
			if(!us){
				showDialog("Please add user info.");
			}else{
				boolean result = parserImpl.send();
				showDialog(parserImpl.getErrorInfo()+"\r\n"+parserImpl.getKeepResult());
			}
		}

	}

	private void showDialog(String log) {
		MessageDialog.openInformation(window.getShell(), "Run", log);
	}

	/**
	 * Selection in the workbench has been changed. We can change the state of
	 * the 'real' action here if we want, but this can only happen after the
	 * delegate has been created.
	 *
	 * @see IWorkbenchWindowActionDelegate#selectionChanged
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	}

	/**
	 * We can use this method to dispose of any system resources we previously
	 * allocated.
	 *
	 * @see IWorkbenchWindowActionDelegate#dispose
	 */
	public void dispose() {
	}

	/**
	 * We will cache window object in order to be able to provide parent shell
	 * for the message dialog.
	 *
	 * @see IWorkbenchWindowActionDelegate#init
	 */
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}
}