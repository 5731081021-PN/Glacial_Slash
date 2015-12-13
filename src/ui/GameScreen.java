package ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.VolatileImage;

import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import input.InputUtility.CommandKey;
import input.InputUtility.KeyPressedAction;
import input.InputUtility.KeyReleasedAction;
import map.GameMap;
import player.PlayerCharacter;
import player.PlayerStatus;

public class GameScreen extends JComponent {

	private static final long serialVersionUID = 8861317653703713044L;
	
	public static final int SCREEN_WIDTH = 1280, SCREEN_HEIGHT = 720;
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
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		
		this.setDoubleBuffered(false);
		
		playerStatus = PlayerStatus.getPlayer();
		currentMap = playerStatus.getCurrentMap();
		playerCharacter = playerStatus.getPlayerCharacter();
		camera = new Point(0, 0);
		this.setKeyBinding();
		
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (!GameScreen.this.isDisplayable()) {
					Thread.yield();
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {}
				}

				buffer = GameScreen.this.createVolatileImage(SCREEN_WIDTH, SCREEN_HEIGHT);
			}
		}).start();
	}

	@Override
	protected void paintComponent(Graphics g) {
		
		Graphics2D g2d = ((VolatileImage)buffer).createGraphics();
		
		g2d.clearRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
	
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
		camera.x = x - SCREEN_WIDTH/2;
		if (camera.x < 0) camera.x = 0;
		if (camera.x + SCREEN_WIDTH > currentMap.getScreenWidth()) camera.x = currentMap.getScreenWidth() - SCREEN_WIDTH;
		camera.y = y - SCREEN_HEIGHT/2;
		if (camera.y < 0) camera.y = 0;
		if (camera.y + SCREEN_HEIGHT > currentMap.getScreenHeight()) camera.y = currentMap.getScreenHeight() - SCREEN_HEIGHT;
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
