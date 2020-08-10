package utils;

import java.util.ArrayList;

import org.eclipse.jface.text.TextViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

public class Console {
	
	private StyledText text;
	private TextViewer textview;
	private String adminPrefix = "";
	private String userPrefix = "";
	private int controlType = 0;
	private ArrayList<String> Lines = new ArrayList<String>();
	private int consoleLength = 0;
	
	public Console(StyledText text, TextViewer textview) {
		this.text = text;
		this.textview = textview;
		
		text.addListener(SWT.Verify, new Listener()
		{
		    @Override
		    public void handleEvent(Event e)
		    {
		    	if (controlType == 0) {
		    		if (text.getSelection().x < consoleLength + userPrefix.length()) {
		        		e.doit = false;
		        	} else if (text.getSelection().x == consoleLength + userPrefix.length()) {
		        		if (e.text.equals("")) {
		        			e.doit = false;
		        		}
		        	}
		    	}
		    }
		});
		text.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (text.getSelection().x >= consoleLength + userPrefix.length()) {
					if (e.keyCode == SWT.CR) {
						parseInput(text.getText(consoleLength + userPrefix.length(), text.getText().length() - 1));
						text.setTopIndex(text.getLineCount() - 1);
						text.setSelection(consoleLength + userPrefix.length());
					}
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {}
		
		});
	}

	public void setPrefixes(String admin, String user) {
		adminPrefix = admin + " ";
		userPrefix = user + " ";
	}
	
	public void enterPrompt() {
		text.setText(text.getText() + "\n" + userPrefix);
	}
	
	public void addLine(String line) {
		controlType = 1;
		Lines.add(adminPrefix + line);
		if (text.getText().equals("")) {
			text.setText(adminPrefix + line);
		} else {
			text.setText(text.getText(0, consoleLength - 2) + "\n" + adminPrefix + line);
		}
		enterPrompt();
		consoleLength += (adminPrefix + line).length() + 1;
		controlType = 0;
	}
	
	private void keepInput(String line) {
		controlType = 1;
		Lines.add(userPrefix + line);
		text.setText(text.getText(0, consoleLength - 2) + "\n" + userPrefix + line);
		consoleLength += (userPrefix + line).length() + 1;
		controlType = 0;
	}
	
	private void parseInput(String input) {
		String sanitary = input.trim();
		System.out.println(sanitary);
		System.out.println("trigger");
		keepInput(sanitary);
		if (sanitary.equals("hey")) {
			addLine("Wassup?");
		} else {
			addLine("Command not recognized.");
		}
	}
	
}
