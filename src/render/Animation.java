package render;

import java.awt.image.BufferedImage;

public abstract class Animation implements Runnable {

	protected BufferedImage[] animation;
	protected int frameCount, currentFrame;
	protected int frameDelay, frameDelayCount;
	
	public Animation(BufferedImage[] animation, int frameDelay) {
		this.animation = animation;
		frameCount = animation.length;
		currentFrame = 0;
		this.frameDelay = frameDelay;
	}

}
