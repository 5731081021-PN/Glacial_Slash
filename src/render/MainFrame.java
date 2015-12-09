package render;

import javax.swing.JFrame;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 6167186886002864573L;

	public MainFrame() {
		super("Glacial Slash");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(GameScreen.getScreen());
		this.pack();
	}

}
