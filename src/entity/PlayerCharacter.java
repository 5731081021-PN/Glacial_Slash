/*
 * The character being controlled by the player
 */

package entity;

import render.Renderable;

public class PlayerCharacter implements Renderable {

	private int x, y;
	
	public PlayerCharacter() {
		// TODO Auto-generated constructor stub
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}

	@Override
	public void render() {
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
