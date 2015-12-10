/*
 * The character being controlled by the player
 */

package entity;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import render.GameScreen;
import render.Renderable;
import res.Resource;

public class PlayerCharacter implements Renderable {

	public static final int LEFT = -1, RIGHT = 1;
	public static final float WALK_SPEED = 16f, JUMP_INITIAL_SPEED = -80f, TERMINAL_SPEED = 40f;
	private int x, y, facingDirection;
	private float xRemainder, yRemainder, xSpeed, ySpeed;
	private float xTargetSpeed, yTargetSpeed, xAcceleration, yAcceleration;
	private Rectangle boundaries;
	private Image sprite = Resource.playerIdleSprite;
	
	public PlayerCharacter() {
		// TODO Auto-generated constructor stub
		// implement x y
		x = 640;
		xRemainder = 0f;
		y = 360;
		yRemainder = 0f;
		xSpeed = 0f;
		ySpeed = 0f;
		xTargetSpeed = 0f;
		yTargetSpeed = TERMINAL_SPEED;
		xAcceleration = 1f;
		yAcceleration = 0.1f;
		boundaries = new Rectangle(x, y, ((BufferedImage)sprite).getWidth(), ((BufferedImage)sprite).getHeight());
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
	}
	
	public synchronized void fall() {
		// TODO implement fall
		if (Math.abs(PlayerStatus.getPlayer().getCurrentMap().movableHeight(boundaries, 1)) > 0)
			yTargetSpeed = TERMINAL_SPEED;
		else
			yTargetSpeed = 0f;
	}
	
	public synchronized void updateBoundaries() {
		boundaries.setLocation(x, y);
	}

	public synchronized void moveX() {
		xSpeed = xTargetSpeed*xAcceleration + xSpeed*(1-xAcceleration);
		int speedFloor = (int)Math.floor(xSpeed);
		int newX = x;
		float newXRemainder = xRemainder;
		newX += speedFloor;
		newXRemainder += xSpeed - speedFloor;
		facingDirection = Integer.signum(Float.compare(xSpeed, 0f));
		if (Float.compare(newXRemainder, 1f) > 0) {
			newXRemainder -= 1f;
			newX++;
		}
		// TODO Check collision
		
		int movableWidth = PlayerStatus.getPlayer().getCurrentMap().movableWidth(boundaries, Float.compare(xSpeed, 0f));
		if (Math.abs(movableWidth) <= Math.abs(newX - x)) {
			x += movableWidth;
			xRemainder = 0f;
			xSpeed = 0f;
		}
		else {
			x = newX;
			xRemainder = newXRemainder;
		}

	}
	
	public synchronized void moveY() {
		ySpeed = yTargetSpeed*yAcceleration + ySpeed*(1-yAcceleration);
		int speedFloor = (int)Math.floor(ySpeed);
		int newY = y;
		float newYRemainder = yRemainder;
		newY += speedFloor;
		newYRemainder += ySpeed - speedFloor;
		if (Float.compare(newYRemainder, 1f) > 0) {
			newYRemainder -= 1f;
			newY++;
		}

		int movableHeight = PlayerStatus.getPlayer().getCurrentMap().movableHeight(boundaries, Float.compare(ySpeed, 0f));
		if (Math.abs(movableHeight) <= Math.abs(newY - y)) {
			y += movableHeight;
			yRemainder = 0f;
			ySpeed = 0f;
		}
		else {
			y = newY;
			yRemainder = newYRemainder;
		}

		// TODO Change sprite to upward or downward motion accordingly
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
