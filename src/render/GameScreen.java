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
import entity.map.Map;
import input.InputUtility;

public class GameScreen extends JComponent {

	private static final long serialVersionUID = 8861317653703713044L;
	
	private static GameScreen screen;
	private Map currentMap;
	private PlayerStatus playerStatus;
	private PlayerCharacter playerCharacter;
	private Point camera;
	private Image screenImage;
	
	public static GameScreen getScreen() {
		if (screen == null)
			screen = new GameScreen();
		return screen;
	}
	
	private GameScreen() {
		this.setPreferredSize(new Dimension(1280, 720));
		InputMap inputMap = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap actionMap = this.getActionMap();
		// Using key binding
		
		playerStatus = PlayerStatus.getPlayer();
		currentMap = playerStatus.getCurrentMap();
		playerCharacter = new PlayerCharacter();
		camera = new Point(0, 0);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		screenImage = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.OPAQUE);
		Graphics2D g2d = ((BufferedImage)screenImage).createGraphics();

		// placeholder background
		g2d.setBackground(Color.WHITE);
		g2d.clearRect(0, 0, this.getWidth(), this.getHeight());

		// draw player
		playerCharacter.render(g2d);
		
		// draw map
//		currentMap.render(g2d);
		
		// draw things to the actual screen
		g.drawImage(screenImage, 0, 0, null);
	}
	
	public int getCameraX() {
		return camera.x;
	}
	
	public int getCameraY() {
		return camera.y;
	}

}
