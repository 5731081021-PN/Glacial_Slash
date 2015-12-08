/*
 * The character being controlled by the player
 */

package entity;

import render.Renderable;

public class PlayerCharacter implements Renderable {

	public static final int LEFT = -1, RIGHT = 1;
	private int x, y;
	
	public PlayerCharacter() {
		// TODO Auto-generated constructor stub
	}
	
	public synchronized int getX() {
		return x;
	}
	
	public synchronized int getY() {
		return y;
	}

	// Motion
	public synchronized void walk(int direction) {
		x++;
		// TODO play walking animation
	}
	
	public synchronized void jump() {
		// TODO implement jump
	}
	
	public synchronized void fall() {
		// TODO implement jumping and falling
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
