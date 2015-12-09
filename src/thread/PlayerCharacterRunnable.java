/*
 * Runnable to control the player character
 */

package thread;

import java.awt.event.KeyEvent;

import entity.PlayerCharacter;
import entity.PlayerStatus;
import entity.map.Terrain;
import input.InputUtility;

public class PlayerCharacterRunnable implements Runnable {

	private PlayerCharacter player;
	
	public PlayerCharacterRunnable(PlayerCharacter player) {
		this.player = player;
	}

	public void run() {
		while (true) {

			int terrain = PlayerStatus.getPlayer().getCurrentMap().getTerrain(player.getX(), player.getY());

			if (!Terrain.isGround(terrain)) {
				player.fall();
			}

			if (InputUtility.getKeyTriggered(KeyEvent.VK_SPACE)) {
				if (Terrain.isGround(terrain)) {
					player.jump();
				}
				if (InputUtility.getKeyPressed(KeyEvent.VK_SPACE)) {
					player.highJump();
				}
			}

			if (InputUtility.getKeyTriggered(KeyEvent.VK_LEFT) || InputUtility.getKeyPressed(KeyEvent.VK_LEFT)) {
				if (!Terrain.isLeftWall(terrain)) {
					player.walk(PlayerCharacter.LEFT);
				}
			}
			else if (InputUtility.getKeyTriggered(KeyEvent.VK_RIGHT) || InputUtility.getKeyPressed(KeyEvent.VK_RIGHT)) {
				if (!Terrain.isRightWall(terrain)) {
					player.walk(PlayerCharacter.RIGHT);
				}
			}
			
			//TODO slashing with the sabre
			//TODO use skills

			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {}
		}
	}

}
