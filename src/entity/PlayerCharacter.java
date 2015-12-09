/*
 * The character being controlled by the player
 */

package entity;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import entity.map.Terrain;
import render.GameScreen;
import render.Renderable;
import res.Resource;

public class PlayerCharacter implements Renderable {

	public static final int LEFT = -1, RIGHT = 1;
	public static final float WALK_SPEED = 16f, JUMP_INITIAL_SPEED = -5f, TERMINAL_SPEED = 32f;
	private int x, y, facingDirection;
	private float xRemainder, yRemainder, xSpeed, ySpeed;
	private float xTargetSpeed, yTargetSpeed, xAcceleration, yAcceleration;
	private Rectangle boundaries;
	private Image sprite = Resource.playerIdleSprite;
	
	public PlayerCharacter() {
		// TODO Auto-generated constructor stub
		// implement x y
		x = 640;
		y = 360;
		facingDirection = 1;
	}
	
	public synchronized int getX() {
		return x;
	}
	public synchronized int getY() {
		return y;
	}

	// Motion
	
	public synchronized void walk(int direction) {
		xTargetSpeed = WALK_SPEED;
	}

	public synchronized void jump() {
		// TODO implement jump
		ySpeed = JUMP_INITIAL_SPEED;
		yTargetSpeed = TERMINAL_SPEED;
	}

	public synchronized void moveX() {
		xSpeed = xTargetSpeed*xAcceleration + xSpeed*(1-xAcceleration);
		int speedFloor = (int)Math.floor(xSpeed);
		int newX = x;
		float newXRemainder = xRemainder;
		newX += speedFloor;
		newXRemainder += xSpeed - speedFloor;
		if (Float.compare(newXRemainder, 1f) > 0) {
			newXRemainder -= 1f;
			newX++;
		}
		// TODO Check collision
	}
	
	public synchronized void moveY() {
		ySpeed = yTargetSpeed*yAcceleration + ySpeed*(1-xAcceleration);
		int speedFloor = (int)Math.floor(ySpeed);
		int newY = y;
		float newYRemainder = yRemainder;
		newY += speedFloor;
		newYRemainder += ySpeed - speedFloor;
		if (Float.compare(newYRemainder, 1f) > 0) {
			newYRemainder -= 1f;
			newY++;
		}
		// TODO Check collision
		// TODO Change sprite to upward or downward accordingly
	}

	@Override
	public void render(Graphics2D g) {
		// TODO Auto-generated method stub
		g.drawImage(sprite, x - GameScreen.getScreen().getCameraX(), y - GameScreen.getScreen().getCameraY(), null);
	}

	@Override
	public boolean isVisible() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public int getZ() {
		// TODO Auto-generated method stub
		return 0;
	}

}
