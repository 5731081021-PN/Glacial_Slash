package render;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import entity.PlayerCharacter;
import entity.PlayerStatus;
import input.InputUtility;
import map.Map;

public class GameScreen extends JComponent {

	private static final long serialVersionUID = 8861317653703713044L;
	
	private static GameScreen screen;
	private Map currentMap;
	private PlayerStatus playerStatus;
	private PlayerCharacter playerCharacter;
	
	public static GameScreen getScreen() {
		if (screen == null)
			screen = new GameScreen();
		return screen;
	}
	
	private GameScreen() {
		this.setPreferredSize(new Dimension(1024, 576));
		InputMap inputMap = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap actionMap = this.getActionMap();
		// Using key binding
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

}
