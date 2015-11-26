/*
 * The character being controlled by the player
 */

package entity;

import render.Renderable;

public class PlayerCharacter implements Renderable {

	public static final int LEFT = -1, RIGHT = 1;
	private int speed;
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

	// Motion
	public void walk(int direction) {
		x += speed*direction;
		// TODO play walking animation
	}
	
	public void jump() {
		// TODO implement jump
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
