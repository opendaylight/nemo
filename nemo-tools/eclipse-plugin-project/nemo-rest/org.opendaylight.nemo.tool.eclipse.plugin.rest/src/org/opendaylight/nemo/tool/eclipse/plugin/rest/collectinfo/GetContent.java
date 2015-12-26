package org.opendaylight.nemo.tool.eclipse.plugin.rest.collectinfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

public class GetContent {

	public static String activeContent() {
		IEditorPart aditorPart = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		if (aditorPart == null) {
			return null;
		}
		String result = null;
		IEditorInput input = aditorPart.getEditorInput();
		if (input instanceof FileEditorInput) {
			IFile file = ((FileEditorInput) input).getFile();
			String name = file.getName();
			if (!name.endsWith(".nemo")) {
				return null;
			}
			try {
				InputStream is = file.getContents();
				result = getContent(is);
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}

	private static String getContent(InputStream input) {
		BufferedReader br = null;
		StringBuilder builder = new StringBuilder();

		String line;
		try {
			br = new BufferedReader(new InputStreamReader(input));
			while ((line = br.readLine()) != null) {
				if(line.indexOf("//")>=0)
					line = line.substring(0,line.indexOf("//"));
				line = line.replaceAll("\"", " ");
				String ss[] = line.split(" ");
				line = "";
				for(String s:ss){
					if(!s.trim().equals("")){
						line += (" "+s.trim());
					}
				}
				if (!line.trim().equals(""))
					builder.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return builder.toString();
	}
}
