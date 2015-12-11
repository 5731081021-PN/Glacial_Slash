package render;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;

import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import entity.map.GameMap;
import input.InputUtility.CommandKey;
import input.InputUtility.KeyPressedAction;
import input.InputUtility.KeyReleasedAction;
import player.PlayerCharacter;
import player.PlayerCharacterRunnable;
import player.PlayerStatus;

public class GameScreen extends JComponent {

	private static final long serialVersionUID = 8861317653703713044L;
	
	private static GameScreen screen;
	private GameMap currentMap;
	private PlayerStatus playerStatus;
	private PlayerCharacter playerCharacter;
	private Point camera;
	private Image buffer;
	
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
		playerCharacter = playerStatus.getPlayerCharacter();
		camera = new Point(0, 0);
		buffer = new BufferedImage(1280, 720, BufferedImage.TYPE_INT_RGB);
		
		this.setKeyBinding();
		// For testing purpose
		new Thread(new PlayerCharacterRunnable()).start();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		
		Graphics2D g2d = ((BufferedImage)buffer).createGraphics();

		// placeholder background
		g2d.setBackground(Color.WHITE);
		g2d.clearRect(0, 0, this.getWidth(), this.getHeight());
	
		// draw map
		currentMap.render(g2d);

		// draw player
		playerCharacter.render(g2d);
		
		// draw playerStatus
		PlayerStatus.getPlayer().render(g2d);
	
		// draw things on actual screen
		g.drawImage(buffer, 0, 0, null);
	}
	
	public int getCameraX() {
		return camera.x;
	}
	
	public int getCameraY() {
		return camera.y;
	}
	
	public void centerCameraAt(int x, int y) {
		camera.x = x - 640;
		if (camera.x < 0) camera.x = 0;
		if (camera.x + 1280 > currentMap.getScreenWidth()) camera.x = currentMap.getScreenWidth() - 1280;
		camera.y = y - 360;
		if (camera.y < 0) camera.y = 0;
		if (camera.y + 720 > currentMap.getScreenHeight()) camera.y = currentMap.getScreenHeight() - 720;
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
