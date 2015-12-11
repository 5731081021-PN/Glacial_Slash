/*
 * Represent each map of the game
 */

package entity.map;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.InputStream;
import java.util.Scanner;

import render.GameScreen;
import render.Renderable;

public class GameMap implements Renderable {
	
	private int width, height;
	private Tile[][] tileMap;
	private static int tileWidth = 70, tileHeight = 70;

	public GameMap(InputStream mapFile) {
		Scanner fileScanner;

		fileScanner = new Scanner(mapFile);
		
		// TODO add some kind of file encoding
		
		width = Integer.parseInt(fileScanner.nextLine());
		height = Integer.parseInt(fileScanner.nextLine());
		
		tileMap = new Tile[width][height];
			
		for (int tileY = 0; tileY < height; tileY++) {
			String buffer = fileScanner.nextLine().trim();
			for (int tileX = 0; tileX < width; tileX++) {
				switch (buffer.charAt(tileX)) {
				case '#': tileMap[tileX][tileY] = Tile.GROUND; break;
				default : tileMap[tileX][tileY] = Tile.AIR;
				}
			}
		}
		
		fileScanner.close();
		
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public int getScreenWidth() {
		return width*tileWidth;
	}

	public int getScreenHeight() {
		return height*tileHeight;
	}

	public int movableWidth(Rectangle collisionBox, int direction) {
		direction = Integer.signum(direction);
		if (direction == 0)
			return 0;
		
		int frontBoundary = (int)(collisionBox.getX() + (direction < 0 ? 0 : 1)*collisionBox.getWidth());
		int upperBoundary = (int)collisionBox.getY() + 1, lowerBoundary = (int)(collisionBox.getY() + collisionBox.getHeight() - 1);
		int upperBoundaryTile = upperBoundary/tileHeight, lowerBoundaryTile = lowerBoundary/tileHeight;
		
		int startTile = frontBoundary/tileWidth;
		
		int maxMovableWidth = (direction < 0 ? 0 : 1)*(this.getScreenWidth()) - frontBoundary;

		for (int tileY = upperBoundaryTile; tileY <= lowerBoundaryTile; tileY++) {
			int tileX = startTile;
			try {
				while (tileMap[tileX][tileY].isPassable()) {
					tileX += direction;
				}
			} catch (ArrayIndexOutOfBoundsException e) {
				continue;
			}
			tileX += (direction < 0)? 1 : 0;
			if (Math.abs(tileX*tileWidth - frontBoundary) < Math.abs(maxMovableWidth)) {
				maxMovableWidth = tileX*tileWidth - frontBoundary;
			}
		}
		return maxMovableWidth;
	}

	public int movableHeight(Rectangle collisionBox, int direction) {
		direction = Integer.signum(direction);
		if (direction == 0)
			return 0;
		
		int frontBoundary = (int)(collisionBox.getY() + (direction < 0 ? 0 : 1)*collisionBox.getHeight());
		int leftBoundary = (int)collisionBox.getX() + 1, rightBoundary = (int)(collisionBox.getX() + collisionBox.getWidth() - 1);
		int leftBoundaryTile = leftBoundary/tileWidth, rightBoundaryTile = rightBoundary/tileWidth;
		
		int startTile = frontBoundary/tileHeight;
		
		int maxMovableHeight = (direction < 0 ? 0 : 1)*(this.getScreenHeight()) - frontBoundary;

		for (int tileX = leftBoundaryTile; tileX <= rightBoundaryTile; tileX++) {
			int tileY = startTile;
			try {
				while (tileMap[tileX][tileY].isPassable()) {
					tileY += direction;
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

	@Override
	public void render(Graphics2D g) {
		int cameraX = GameScreen.getScreen().getCameraX();
		int cameraY = GameScreen.getScreen().getCameraY();
		int firstTileX = cameraX/tileWidth;
		int firstTileY = cameraY/tileHeight;
		int lastTileX = firstTileX + GameScreen.SCREEN_WIDTH/tileWidth + 1;
		int lastTileY = firstTileY + GameScreen.SCREEN_HEIGHT/tileHeight + 1;
		
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
