package render;

import javax.swing.JComponent;

import entity.PlayerCharacter;
import entity.PlayerStatus;
import map.Map;

public class GameScreen extends JComponent {

	private static final long serialVersionUID = 8861317653703713044L;
	
	private static GameScreen screen = new GameScreen();
	private Map currentMap;
	private PlayerStatus playerStatus;
	private PlayerCharacter playerCharacter;
	
	public static GameScreen getScreen() {
		return screen;
	}

}
