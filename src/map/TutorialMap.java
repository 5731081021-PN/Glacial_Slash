package map;

import java.awt.Graphics2D;

import res.Resource;
import ui.GameScreen;

public class TutorialMap extends GameMap {

	public TutorialMap() {
		super(Resource.tutorialMap);
	}
	
	@Override
	public void render(Graphics2D g) {
		super.render(g);
		GameScreen gameScreen = GameScreen.getScreen();
		g.drawImage(Resource.leftButton, null, 40 - gameScreen.getCameraX(), 900 - gameScreen.getCameraY());
		g.drawImage(Resource.rightButton, null, 120 - gameScreen.getCameraX(), 900 - gameScreen.getCameraY());
		g.drawImage(Resource.spaceButton, null, 700 - gameScreen.getCameraX(), 1000 - gameScreen.getCameraY());
		g.drawImage(Resource.downButton, null, 2800 - gameScreen.getCameraX(), 800 - gameScreen.getCameraY());
		g.drawImage(Resource.sButton, null, 2880 - gameScreen.getCameraX(), 800 - gameScreen.getCameraY());
		g.drawImage(Resource.spaceButton, null, 5400 - gameScreen.getCameraX(), 750 - gameScreen.getCameraY());
		g.drawImage(Resource.upButton, null, 7400 - gameScreen.getCameraX(), 800 - gameScreen.getCameraY());
		g.drawImage(Resource.fButton, null, 7480 - gameScreen.getCameraX(), 800 - gameScreen.getCameraY());
		// TODO E
		// TODO RIGHT+D
	}

}
