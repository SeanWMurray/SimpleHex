package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.eclipse.jface.text.TextViewer;
import org.eclipse.swt.custom.StyledText;

public class HexView {
	
	private StyledText text;
	private TextViewer textview;

	public HexView(StyledText text, TextViewer textview) {
		this.text = text;
		this.textview = textview;
	}
	
	public void load(FileInputStream stream, String path) throws IOException {
		String fileData;
		
		byte[] bytes = Files.readAllBytes(Paths.get(path));
		StringBuilder sb = new StringBuilder();
		for (byte b : bytes) {
			sb.append(String.format("%02x ", b));
		}
		text.setText(sb.toString().toUpperCase());
	}
	
}
