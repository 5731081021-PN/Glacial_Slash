package render;

import javax.swing.JComponent;

public class GameScreen extends JComponent {

	private static final long serialVersionUID = 8861317653703713044L;
	
	private static GameScreen screen = new GameScreen();
	
	public static GameScreen getScreen() {
		return screen;
	}

}
