package render;

import java.awt.image.BufferedImage;

import player.PlayerCharacter;

public class PlayerAnimation extends Animation {
	
	private BufferedImage[][] animationSet;
	private PlayerCharacter player;
	private boolean isLooping;
	
	public PlayerAnimation(BufferedImage[][] animationSet, PlayerCharacter player, int frameDelay, boolean isLooping) {
		super(animationSet[0], frameDelay);
		this.animationSet = animationSet;
		this.player = player;
		this.isLooping = isLooping;
	}

	@Override
	public void run() {
		frameDelayCount = frameDelay;
		while (currentFrame < frameCount) {
			if (frameDelayCount <= 0) {
				animation = animationSet[(player.getFacingDirection()+1)/2];
				player.setSprite(animation[currentFrame]);
				currentFrame++;
				if (isLooping && currentFrame >= frameCount)
					currentFrame = 0;
				frameDelayCount = frameDelay;
			}
			frameDelayCount--;

			synchronized (player) {
				try {
					player.wait();
				} catch (InterruptedException e) {
					return;
				}
			}

		}
	}

}
