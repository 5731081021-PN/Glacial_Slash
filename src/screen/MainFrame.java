package screen;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 6167186886002864573L;
	
	public static MainFrame frame;
	
	public static MainFrame getFrame() {
		if (frame == null)
			frame = new MainFrame();
		return frame;
	}

	private MainFrame() {
		super("Glacial Slash");
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.add(TitleScreen.getScreen());
		this.setResizable(false);
		this.pack();
	}
	
}
