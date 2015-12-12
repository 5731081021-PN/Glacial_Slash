package render;

import java.awt.image.BufferedImage;

public abstract class Animation implements Runnable {

	protected BufferedImage[] animation;
	protected int frameCount, currentFrame;
	
	public Animation(BufferedImage[] animation) {
		this.animation = animation;
		frameCount = animation.length;
		currentFrame = 0;
	}

}
