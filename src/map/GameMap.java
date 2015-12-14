/*
 * Represent each map of the game
 */

package map;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import player.PlayerStatus;
import player.SkillCard;
import render.Renderable;
import res.Resource;
import ui.GameScreen;

public class GameMap implements Renderable, Serializable {
	
	private int width, height;
	private Tile[][] tileMap;
	private ManaSource[] manaSources;
	private static int tileWidth = 70, tileHeight = 70;
	private Point initialPosition, transitionPoint;
	private boolean isManaSourceSaving;
	private String nextMapName;
	
	public static GameMap getGameMap(String mapName) {
		if ("tutorialMap".equalsIgnoreCase(mapName)) return new GameMap(Resource.tutorialMap);
		else if ("map5to8".equalsIgnoreCase(mapName)) return new GameMap(Resource.map5to8);
		else if ("map9to12".equalsIgnoreCase(mapName)) return new GameMap(Resource.map9to12);
		else if ("map13to14".equalsIgnoreCase(mapName)) return new GameMap(Resource.map13to14);
		else if ("map15".equalsIgnoreCase(mapName)) return new GameMap(Resource.map15);
		else return null;
	}

	public GameMap(InputStream mapFile) {
		Scanner fileScanner;
		fileScanner = new Scanner(mapFile);
		
		width = Integer.parseInt(fileScanner.nextLine());
		height = Integer.parseInt(fileScanner.nextLine());
		
		tileMap = new Tile[width][height];
			
		for (int tileY = 0; tileY < height; tileY++) {
			String buffer = fileScanner.nextLine().trim();
			for (int tileX = 0; tileX < width; tileX++) {
				switch (buffer.charAt(tileX)) {
				case '#': tileMap[tileX][tileY] = Tile.GROUND; break;
				case '<': tileMap[tileX][tileY] = Tile.TOPLEFT; break;
				case '_': tileMap[tileX][tileY] = Tile.TOP; break;
				case '>': tileMap[tileX][tileY] = Tile.TOPRIGHT; break;
				case '+': tileMap[tileX][tileY] = Tile.LEFT; break;
				case '-': tileMap[tileX][tileY] = Tile.MID; break;
				case '*': tileMap[tileX][tileY] = Tile.RIGHT; break;
				case 'X': transitionPoint = new Point(tileX*tileWidth, tileY*tileHeight);
				case 'P': initialPosition = new Point(tileX*tileWidth, (tileY+1)*tileHeight);
				default : tileMap[tileX][tileY] = Tile.AIR;
				}
			}
		}
		
		String saving = fileScanner.nextLine();
		if ("save".equalsIgnoreCase(saving))
			isManaSourceSaving = true;
		else
			isManaSourceSaving = false;
		
		int checkpointCount = Integer.parseInt(fileScanner.nextLine());
		manaSources = new ManaSource[checkpointCount];
		for (int i = 0; i < checkpointCount; i++) {
			int tileX = Integer.parseInt(fileScanner.nextLine());
			int tileY = Integer.parseInt(fileScanner.nextLine());
			int screenX = tileX*tileWidth + (tileWidth-Resource.manaSource.getWidth())/2;
			int screenY = (tileY-1)*tileHeight + (tileHeight-Resource.manaSource.getHeight());
	
			int cardCount = Integer.parseInt(fileScanner.nextLine());
			List<SkillCard> hand = new ArrayList<>();
			for (int j = 0; j < cardCount; j++) {
				hand.add(SkillCard.createSkillCard(fileScanner.nextLine().trim()));
			}
			
			manaSources[i] = new ManaSource(screenX, screenY, hand);
		}
		
		nextMapName = fileScanner.nextLine().trim();

		fileScanner.close();
		
	}
	
	public GameMap getNextMap() {
		return getGameMap(nextMapName);
	}
	
	public boolean collideWithTransitionPoint(Rectangle collisionBox) {
		try {
			return collisionBox.contains(transitionPoint);
		} catch (NullPointerException e) {
			return false;
		}
	}
	
	public void clearIceTiles() {
		for (int tileX = 0; tileX < width; tileX++)
			for (int tileY = 0; tileY < height; tileY++)
				if (tileMap[tileX][tileY] == Tile.ICE)
					tileMap[tileX][tileY] = Tile.AIR;
	}
	
	public Point getInitialPosition() {
		return initialPosition;
	}
	
	public Point getTransitionPoint() {
		return transitionPoint;
	}
	
	public String getNextMapName() {
		return nextMapName;
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public int getTileWidth() {
		return tileWidth;
	}
		
	public int getTileHeight() {
		return tileHeight;
	}

	public int getScreenWidth() {
		return width*tileWidth;
	}

	public int getScreenHeight() {
		return height*tileHeight;
	}
	
	public void freeze(int x, int y) {
		tileMap[x][y] = Tile.ICE;
	}
	
	public Tile getTileType(int tileX, int tileY) {
		return tileMap[tileX][tileY];
	}
	
	public Point getFrontTile(Rectangle collisionBox, int direction) {
		direction = Integer.signum(direction);
		int frontBoundary = (int)(collisionBox.getX() + (direction < 0 ? 0 : 1)*collisionBox.getWidth()) - direction;
		int lowerBoundary = (int)(collisionBox.getY() + collisionBox.getHeight()) - 1;
		int currentTileX = frontBoundary / tileWidth;
		int currentTileY = lowerBoundary / tileHeight;
		return new Point(currentTileX + direction, currentTileY);
	}
	
	public boolean isOnGround(Rectangle collisionBox) {
		return movableHeight(collisionBox, 1) == 0;
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
	
	public void collideManaSource(Rectangle collisionBox) {
		for (ManaSource s : manaSources) {
			if (s.getBoundaries().intersects(collisionBox)) {
				if (!s.isUsed()) {
					PlayerStatus.getPlayer().drawNewHand(s.drawCard());
					if (isManaSourceSaving)
						PlayerStatus.getPlayer().savePlayer();
				}
			}
		}
	}

	@Override
	public void render(Graphics2D g) {
		int cameraX = GameScreen.getScreen().getCameraX();
		int cameraY = GameScreen.getScreen().getCameraY();
		int firstTileX = cameraX/tileWidth;
		int firstTileY = cameraY/tileHeight;
		int lastTileX = firstTileX + GameScreen.SCREEN_WIDTH/tileWidth + 1;
		int lastTileY = firstTileY + GameScreen.SCREEN_HEIGHT/tileHeight + 1;
		
		g.drawImage(Resource.background, 0, 0, null);
		
		try {
			for (int tileX = firstTileX; tileX <= lastTileX; tileX++) {
				try {
					for (int tileY = firstTileY; tileY <= lastTileY; tileY++) {
						g.drawImage(tileMap[tileX][tileY].getTileSprite(), tileX*tileWidth-cameraX, tileY*tileHeight-cameraY, null);
					}
				} catch (ArrayIndexOutOfBoundsException e) {}
			}
		} catch (ArrayIndexOutOfBoundsException e) {}
		
		for (ManaSource c : manaSources) {
			c.render(g);
		}
	}

}
