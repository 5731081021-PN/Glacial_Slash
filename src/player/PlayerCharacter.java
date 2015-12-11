/*
 * The character being controlled by the player
 */

package player;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import render.GameScreen;
import render.Renderable;
import res.Resource;

public class PlayerCharacter implements Renderable {

	public static final int LEFT = -1, RIGHT = 1, IDLE = 0;
	public static final float WALK_SPEED = 8f, JUMP_INITIAL_SPEED = -30f, TERMINAL_SPEED = 16f;
	private int x, y, facingDirection;
	private float xRemainder, yRemainder, xSpeed, ySpeed;
	private float xTargetSpeed, yTargetSpeed, xAcceleration, yAcceleration;
	private Rectangle boundaries;
	private Image sprite = Resource.playerIdleSprite;
	private int freezePlayerControlCount, airJumpCount;
	
	public PlayerCharacter() {
		// TODO Auto-generated constructor stub
		// implement x y
		x = 640;
		xRemainder = 0f;
		y = 100;
		yRemainder = 0f;
		xSpeed = 0f;
		ySpeed = 0f;
		xTargetSpeed = 0f;
		yTargetSpeed = TERMINAL_SPEED;
		xAcceleration = 0.9f;
		yAcceleration = 0.04f;
		boundaries = new Rectangle(x, y, ((BufferedImage)sprite).getWidth(), ((BufferedImage)sprite).getHeight());
		airJumpCount = 0;
		freezePlayerControlCount = 0;
		facingDirection = 1;
	}
	
	public synchronized int getX() {
		return x;
	}

	public synchronized int getY() {
		return y;
	}
	
	public synchronized int getCenterX() {
		return x + (int)(boundaries.getWidth()/2);
	}
	
	public synchronized int getCenterY() {
		return y + (int)(boundaries.getHeight()/2);
	}

	// Motion
	
	protected synchronized void walk(int direction) {
		xTargetSpeed = direction*WALK_SPEED;
	}

	protected synchronized void jump() {
		// TODO implement jump
		ySpeed = JUMP_INITIAL_SPEED;
		if (!this.isOnGround())
			airJumpCount--;
	}
		
	public synchronized int getFreezePlayerControlCount() {
		return freezePlayerControlCount;
	}	

	public synchronized void decreseFreezePlayerControlCount() {
		freezePlayerControlCount--;
	}

	public synchronized int getAirJumpCount() {
		return airJumpCount;
	}
	
	public boolean isOnGround() {
		return PlayerStatus.getPlayer().getCurrentMap().movableHeight(boundaries, 1) == 0;
	}
	
	protected synchronized void fall() {
		// TODO implement fall
		if (this.isOnGround()) {
			yTargetSpeed = 0f;
			airJumpCount = 1;
		}
		else
			yTargetSpeed = TERMINAL_SPEED;
	}
	
	protected void updateBoundaries() {
		boundaries.setLocation(x, y);
	}

	protected synchronized void moveX() {
		xSpeed = xTargetSpeed*xAcceleration + xSpeed*(1-xAcceleration);
		int speedFloor = (int)Math.floor(xSpeed);
		int newX = x;
		float newXRemainder = xRemainder;
		newX += speedFloor;
		newXRemainder += xSpeed - speedFloor;
		int movingDirection = Integer.signum(Float.compare(xSpeed, 0f));
		facingDirection = (movingDirection != 0)? movingDirection : facingDirection;
		if (Float.compare(newXRemainder, 1f) > 0) {
			newXRemainder -= 1f;
			newX++;
		}
		
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
	
	protected synchronized void moveY() {
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
	
	// Special moves
	protected void slash() {
		// TODO play slash animation
	}
	
	protected void performSkyUpperCut() {
		freezePlayerControlCount = 30;
		yAcceleration = 0f;
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while (getFreezePlayerControlCount() > 0) {
					xTargetSpeed = facingDirection*15f;
					ySpeed = -12f;
				}
				yAcceleration = 0.04f;
			}
		}).start();
		// TODO play sky uppercut animation
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