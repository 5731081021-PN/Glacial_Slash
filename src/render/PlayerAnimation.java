package render;

import java.awt.image.BufferedImage;

import player.PlayerCharacter;
import screen.GameScreen;

public class PlayerAnimation extends Animation {
	
	private BufferedImage[][] animationSet;
	private PlayerCharacter player;
	
	public PlayerAnimation(BufferedImage[][] animationSet, PlayerCharacter player) {
		super(animationSet[0]);
		this.animationSet = animationSet;
		this.player = player;
	}

	@Override
	public void run() {
		while (currentFrame < frameCount) {

			animation = animationSet[(player.getFacingDirection()+1)/2];
			player.setSprite(animation[currentFrame]);
			currentFrame++;
	
			synchronized (GameScreen.getScreen()) {
				try {
					GameScreen.getScreen().wait();
				} catch (InterruptedException e) {
					return;
				}
			}
		}
	}

}
