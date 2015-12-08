/*
 * Represent each map of the game
 */

package map;

import java.io.File;

import render.GameScreen;

public class Map {
	
	private int width, height;

	public Map(File mapFile) {
		
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public boolean isOnScreen(int x, int y) {
		return (x >= 0 && x <= this.getWidth()) && (y >= 0 && y <= this.getHeight());
	}

	public int getTerrain(int x, int y) {
		//TODO terrain
		if (this.isOnScreen(x, y))
			return 0;
		return 0;
	}

}
