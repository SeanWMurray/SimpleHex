import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Point;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.eclipse.jface.text.TextViewer;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Composite;

import utils.Console;
import utils.HexView;
import Editor.FileView;
import org.eclipse.wb.swt.SWTResourceManager;

public class View {

	protected Shell shell;

	public static final String TITLE = "SimpleHex";
	
	public static String path;
	private Table table;
	
	/**
	 * Launch the application.
	 * @param args
	 * @wbp.parser.entryPoint
	 */
	public static void begin() {
		try {
			View window = new View();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(1080, 725);
		shell.setText(TITLE);
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Menu toolbar = new Menu(shell, SWT.BAR);
		shell.setMenuBar(toolbar);
		
		MenuItem mntmFile = new MenuItem(toolbar, SWT.CASCADE);
		mntmFile.setText("File");
		
		Menu menu = new Menu(mntmFile);
		mntmFile.setMenu(menu);
		
		FileDialog pickFile = new FileDialog(shell, SWT.OPEN);
		
		MenuItem openFile = new MenuItem(menu, SWT.NONE);
		openFile.setToolTipText("Select a file to view");
		openFile.setText("Open");
		
		MenuItem mntmEdit = new MenuItem(toolbar, SWT.CASCADE);
		mntmEdit.setText("Edit");
		
		Menu menu_1 = new Menu(mntmEdit);
		mntmEdit.setMenu(menu_1);
		
		Composite contentContainer = new Composite(shell, SWT.BORDER);
		contentContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		SashForm upDownSash = new SashForm(contentContainer, SWT.VERTICAL);
		
		Composite composite = new Composite(upDownSash, SWT.NONE);
		composite.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		SashForm leftRightSash = new SashForm(composite, SWT.NONE);
		
		//What will be the hex view
		TextViewer textViewer = new TextViewer(leftRightSash, SWT.BORDER);
		StyledText styledText = textViewer.getTextWidget();
		styledText.setFont(SWTResourceManager.getFont("Source Code Pro", 12, SWT.NORMAL));
		styledText.setEditable(false);
		styledText.setWordWrap(true);
		
		HexView hexeditor = new HexView(styledText, textViewer);
		
		table = new Table(leftRightSash, SWT.BORDER | SWT.FULL_SELECTION);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn attributeColumn = new TableColumn(table, SWT.NONE);
		attributeColumn.setWidth(100);
		attributeColumn.setText("Attributes");
		
		
		leftRightSash.setWeights(new int[] {3, 1});
		

		
		TextViewer consoleViewer = new TextViewer(upDownSash, SWT.BORDER | SWT.V_SCROLL);
		StyledText consoleText = consoleViewer.getTextWidget();
		Console console = new Console(consoleText, consoleViewer);
		upDownSash.setWeights(new int[] {487, 172});
		console.setPrefixes("[Console] >", "[User] >");
		console.addLine("Yo");
		console.addLine("Testing");
		console.addLine("Testing");
		console.addLine("Testing");
		
		openFile.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event e) {
				path = pickFile.open();
				console.addLine("Opening file from: " + path);
				FileView view = new FileView(textViewer, path);
				try {
					hexeditor.load(view.getStream(), path);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		});

	}
	
	public void loadFile(String filepath) {
		
	}
	
	public void systemLog(String message) {
		
	}
	
}









