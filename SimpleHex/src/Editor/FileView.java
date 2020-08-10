package Editor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.eclipse.jface.text.TextViewer;

public class FileView {

	private TextViewer text;
	private Path path;
	
	public FileView(TextViewer text, String path) {
		this.text = text;
		this.path = Paths.get(path);
	}
	
	public FileInputStream getStream() throws FileNotFoundException {
		return new FileInputStream(new File(path.toString()));
	}
}
