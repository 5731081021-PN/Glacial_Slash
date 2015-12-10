/*
 * Runnable to control the player character
 */

package thread;

import java.awt.event.KeyEvent;

import entity.PlayerCharacter;
import entity.PlayerStatus;
import input.InputUtility;
import input.InputUtility.CommandKey;
import render.GameScreen;

public class PlayerCharacterRunnable implements Runnable {

	private PlayerCharacter player;
	
	public PlayerCharacterRunnable(PlayerCharacter player) {
		this.player = player;
	}

	public void run() {
		while (true) {

			player.updateBoundaries();
			player.fall();

			//TODO player controls
			synchronized (GameScreen.getScreen()) {

				if (InputUtility.getKeyTriggered(CommandKey.JUMP)) {
					player.jump();
				}
			//TODO slashing with the sabre
			//TODO use skills
			}
			
			player.moveX();
			player.moveY();

			InputUtility.clearKeyTriggered();
			
			GameScreen.getScreen().repaint();
	
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {}

		}
	}

}
