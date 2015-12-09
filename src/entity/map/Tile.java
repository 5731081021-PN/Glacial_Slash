package entity.map;

import java.awt.Graphics2D;
import java.awt.Image;

import render.Renderable;
import render.Resource;

public enum Tile implements Renderable {
	
	AIR (true, null),
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
