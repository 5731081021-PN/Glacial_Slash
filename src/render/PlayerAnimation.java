package render;

import java.awt.image.BufferedImage;

import player.PlayerCharacter;

public class PlayerAnimation extends Animation {
	
	private BufferedImage[][] animationSet;
	private PlayerCharacter player;
	private boolean isLooping;
	
	public PlayerAnimation(BufferedImage[][] animationSet, PlayerCharacter player, boolean isLooping) {
		super(animationSet[0]);
		this.animationSet = animationSet;
		this.player = player;
		this.isLooping = isLooping;
	}

	@Override
	public void run() {
		while (currentFrame < frameCount) {

			animation = animationSet[(player.getFacingDirection()+1)/2];
			player.setSprite(animation[currentFrame]);
			currentFrame++;
	
			synchronized (player) {
				try {
					player.wait();
				} catch (InterruptedException e) {
					return;
				}
			}
			
			if (isLooping && currentFrame >= frameCount)
				currentFrame = 0;
		}
	}

}
