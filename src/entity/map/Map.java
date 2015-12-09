/*
 * Represent each map of the game
 */

package entity.map;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.File;

import render.Renderable;

public class Map implements Renderable {
	
	private int width, height;
	private Tile[][] tile;

	public Map(File mapFile) {
		
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public int movableWidth(Rectangle collisionBox, int direction) {
		int moveBoundary = (int)(collisionBox.getX() + (direction < 0 ? 0 : 1)*collisionBox.getWidth());
		int upperBoundary = (int)collisionBox.getY(), lowerBoundary = (int)(collisionBox.getY() + collisionBox.getHeight());
	}
	
	public int movableHeight(Rectangle collisionBox, int direction) {
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

	@Override
	public void render(Graphics2D g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isVisible() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getZ() {
		// TODO Auto-generated method stub
		return 0;
	}

}
