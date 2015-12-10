package render;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import entity.PlayerCharacter;
import entity.PlayerStatus;
import entity.map.GameMap;
import input.InputUtility;
import input.InputUtility.CommandKey;
import input.InputUtility.KeyPressedAction;
import input.InputUtility.KeyReleasedAction;
import thread.PlayerCharacterRunnable;

public class GameScreen extends JComponent {

	private static final long serialVersionUID = 8861317653703713044L;
	
	private static GameScreen screen;
	private GameMap currentMap;
	private PlayerStatus playerStatus;
	private PlayerCharacter playerCharacter;
	private Point camera;
	
	public static GameScreen getScreen() {
		if (screen == null)
			screen = new GameScreen();
		return screen;
	}
	
	private GameScreen() {
		this.setPreferredSize(new Dimension(1280, 720));
		
		this.setDoubleBuffered(true);
		
		playerStatus = PlayerStatus.getPlayer();
		currentMap = playerStatus.getCurrentMap();
		playerCharacter = new PlayerCharacter();
		camera = new Point(0, 0);
		
		this.setKeyBinding();
		// For testing purpose
		new Thread(new PlayerCharacterRunnable(playerCharacter)).start();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D)g;

		// placeholder background
		g2d.setBackground(Color.BLACK);
		g2d.clearRect(0, 0, this.getWidth(), this.getHeight());
	
		// draw map
		currentMap.render(g2d);

		// draw player
		playerCharacter.render(g2d);
	
	}
	
	public int getCameraX() {
		return camera.x;
	}
	
	public int getCameraY() {
		return camera.y;
	}

	private void setKeyBinding() {
		InputMap inputMap = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap actionMap = this.getActionMap();
			
		for (CommandKey key : CommandKey.values()) {
			inputMap.put(KeyStroke.getKeyStroke(key.getKey(), 0, false), "pressed " + key.getName());
			actionMap.put("pressed " + key.getName(), new KeyPressedAction(key));
			inputMap.put(KeyStroke.getKeyStroke(key.getKey(), 0, true), "released " + key.getName());
			actionMap.put("released " + key.getName(), new KeyReleasedAction(key));
		}
	}

}
