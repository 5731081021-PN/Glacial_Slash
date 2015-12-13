package ui;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class TitleWindow extends JFrame {

	private static final long serialVersionUID = 6167186886002864573L;
	
	public static TitleWindow window;
	
	public static TitleWindow getWindow() {
		if (window == null)
			window = new TitleWindow();
		return window;
	}

	private TitleWindow() {
		super("Glacial Slash");
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.add(TitleScreen.getScreen());
		this.setResizable(false);
		this.pack();
	}
	
}
