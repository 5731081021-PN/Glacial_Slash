/*
 * Represent each map of the game
 */

package entity.map;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JOptionPane;

import render.GameScreen;
import render.Renderable;

public class GameMap implements Renderable {
	
	private int width, height;
	private Tile[][] tileMap;
	private static int tileWidth = 32, tileHeight = 32;

	public GameMap(File mapFile) {
		Scanner fileScanner;

		try {
			fileScanner = new Scanner(mapFile);
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "File not found", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		// TODO change map file format
		// TODO add some kind of file encoding
		
		String buffer = "";
		height = 0;
		while (fileScanner.hasNextLine()) {
			buffer = fileScanner.nextLine().trim();
			height++;
		}
		width = buffer.length();
		fileScanner.close();
		
		tileMap = new Tile[width][height];
	
		try {
			fileScanner = new Scanner(mapFile);
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "File not found", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		for (int tileY = 0; tileY < height; tileY++) {
			buffer = fileScanner.nextLine().trim();
			for (int tileX = 0; tileX < width; tileX++) {
				switch (buffer.charAt(tileX)) {
				case '#': tileMap[tileX][tileY] = Tile.GROUND; break;
				default : tileMap[tileX][tileY] = Tile.AIR;
				}
			}
		}
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
/* TODO implement collision checking	
	public int movableWidth(Rectangle collisionBox, int direction) {
		int frontBoundary = (int)(collisionBox.getX() + (direction < 0 ? 0 : 1)*collisionBox.getWidth());
		int upperBoundary = (int)collisionBox.getY(), lowerBoundary = (int)(upperBoundary + collisionBox.getHeight());
	}
*/	
	public int movableHeight(Rectangle collisionBox, int direction) {
		direction = Integer.signum(direction);
		if (direction == 0)
			return 0;
		
		int frontBoundary = (int)(collisionBox.getY() + (direction < 0 ? 0 : 1)*collisionBox.getHeight());
		int leftBoundary = (int)collisionBox.getX(), rightBoundary = (int)(leftBoundary + collisionBox.getWidth());
		int leftBoundaryTile = leftBoundary/tileWidth, rightBoundaryTile = rightBoundary/tileWidth;
		
		int startTile = frontBoundary/tileHeight;
		
		int maxMovableHeight = direction*Integer.MAX_VALUE;

		for (int tileX = leftBoundaryTile; tileX <= rightBoundaryTile; tileX++) {
			int tileY = startTile;
			try {
				while (tileMap[tileX][tileY].isPassable()) {
					tileY++;
				}
			} catch (ArrayIndexOutOfBoundsException e) {
				continue;
			}
			tileY += (direction < 0)? 1 : 0;
			if (Math.abs(tileY*tileHeight - frontBoundary) < Math.abs(maxMovableHeight)) {
				maxMovableHeight = tileY*tileHeight - frontBoundary;
			}
		}
		return maxMovableHeight;
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
		int cameraX = GameScreen.getScreen().getCameraX();
		int cameraY = GameScreen.getScreen().getCameraY();
		int firstTileX = cameraX/tileWidth;
		int firstTileY = cameraY/tileHeight;
		int lastTileX = firstTileX + GameScreen.getScreen().getWidth()/tileWidth;
		int lastTileY = firstTileY + GameScreen.getScreen().getHeight()/tileHeight;
		
		try {
			for (int tileX = firstTileX; tileX <= lastTileX; tileX++) {
				try {
					for (int tileY = firstTileY; tileY <= lastTileY; tileY++) {
						g.drawImage(tileMap[tileX][tileY].getTileSprite(), tileX*tileWidth-cameraX, tileY*tileHeight-cameraY, null);
					}
				} catch (ArrayIndexOutOfBoundsException e) {}
			}
		} catch (ArrayIndexOutOfBoundsException e) {}
	}

	@Override
	public boolean isVisible() {
		return true;
	}

	@Override
	public int getZ() {
		return Integer.MAX_VALUE;
	}

}
