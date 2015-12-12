/*
 * The character being controlled by the player
 */

package player;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import render.Renderable;
import res.Resource;
import screen.GameScreen;

public class PlayerCharacter implements Renderable {

	public static final int LEFT = -1, RIGHT = 1, IDLE = 0;
	public static final float WALK_SPEED = 16f, JUMP_INITIAL_SPEED = -60f, TERMINAL_SPEED = 32f, GRAVITY = 0.08f;
	private int x, y, facingDirection, prevX, prevY;
	private float xRemainder, yRemainder, xSpeed, ySpeed;
	private float xTargetSpeed, yTargetSpeed, xAcceleration, yAcceleration;
	private Rectangle boundaries;
	private int boundaryX, boundaryY, boundaryWidth, boundaryHeight;
	private Image sprite = Resource.standSprite[1];
	private int freezePlayerControlCount, airJumpCount;
	
	public PlayerCharacter() {
		xSpeed = 0f;
		ySpeed = 0f;
		xTargetSpeed = 0f;
		yTargetSpeed = TERMINAL_SPEED;
		xAcceleration = 0.9f;
		yAcceleration = GRAVITY;
		airJumpCount = 0;
		freezePlayerControlCount = 0;
		facingDirection = 1;
	}
	
	public void setInitialPosition() {
		Point initialPosition = PlayerStatus.getPlayer().getCurrentMap().getInitialPosition();
		x = (int)initialPosition.getX();
		y = (int)initialPosition.getY() - ((BufferedImage)sprite).getHeight();
		xRemainder = 0f;
		yRemainder = 0f;
		prevX = x;
		prevY = y;
		boundaryWidth = 42;
		boundaryHeight = 135;
		boundaryX = x + ((BufferedImage)sprite).getWidth()/2 - boundaryWidth/2;
		boundaryY = y;
		boundaries = new Rectangle(boundaryX, boundaryY, boundaryWidth, boundaryHeight);
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public int getCenterX() {
		return x + (int)(boundaries.getWidth()/2);
	}
	
	public int getCenterY() {
		return y + (int)(boundaries.getHeight()/2);
	}
		
	public Point getFrontTile() {
		return PlayerStatus.getPlayer().getCurrentMap().getFrontTile(boundaries, facingDirection);
	}

	public Point getSpriteFrontTile() {
		Rectangle spriteBoundary = new Rectangle(x, y, ((BufferedImage)sprite).getWidth(), ((BufferedImage)sprite).getHeight());
		return PlayerStatus.getPlayer().getCurrentMap().getFrontTile(spriteBoundary, facingDirection);
	}

	// Motion
	
	protected void walk(int direction) {
		xTargetSpeed = direction*WALK_SPEED;
	}

	protected void jump() {
		ySpeed = JUMP_INITIAL_SPEED;
		if (!this.isOnGround())
			airJumpCount--;
	}
		
	public int getFreezePlayerControlCount() {
		return freezePlayerControlCount;
	}	

	public void decreseFreezePlayerControlCount() {
		freezePlayerControlCount--;
	}

	public int getAirJumpCount() {
		return airJumpCount;
	}
	
	public boolean isOnGround() {
		return PlayerStatus.getPlayer().getCurrentMap().isOnGround(boundaries);
	}
	
	protected void fall() {
		if (this.isOnGround()) {
			yTargetSpeed = 0f;
			airJumpCount = 1;
		}
		else
			yTargetSpeed = TERMINAL_SPEED;
	}
	
	protected void updateBoundaries() {
		boundaryX += x - prevX;
		boundaryY += y - prevY;
		boundaries.setLocation(boundaryX, boundaryY);
	}

	protected void moveX() {
		prevX = x;
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
	
	protected void moveY() {
		prevY = y;
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

		sprite = Resource.standSprite[(facingDirection+1)/2];

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
		freezePlayerControlCount = 15;
		yAcceleration = 0f;
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while (getFreezePlayerControlCount() > 0) {
					xTargetSpeed = facingDirection*30f;
					ySpeed = -30f;
					Thread.yield();
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {}
				}
				yAcceleration = GRAVITY;
			}
		}).start();
		// TODO play sky uppercut animation
	}
	
	protected void performGlacialDrift() {
		freezePlayerControlCount = 4;
		yAcceleration = 0f;
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while (getFreezePlayerControlCount() > 0) {
					xTargetSpeed = facingDirection*200f;
					ySpeed = 0;
					Thread.yield();
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {}
				}
				yAcceleration = GRAVITY;
			}
		}).start();
	}
	
	protected void performIceSummon() {
		// TODO play ice summon animation
	}

	@Override
	public void render(Graphics2D g) {
		g.drawImage(sprite, x - GameScreen.getScreen().getCameraX(), y - GameScreen.getScreen().getCameraY(), null);
	}

}
