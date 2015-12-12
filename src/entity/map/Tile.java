package entity.map;

import java.awt.Image;

import res.Resource;

public enum Tile {
	
	AIR (true, null),
	ICE (false, Resource.iceBlock),
	GROUND (false, Resource.floorTile);
	
	private boolean passable;
	private Image tileSprite;
	
	private Tile(boolean passable, Image tileSprite) {
		this.passable = passable;
		this.tileSprite = tileSprite;
	}
	
	public boolean isPassable() {
		return passable;
	}
	
	public Image getTileSprite() {
		return tileSprite;
	}

}
