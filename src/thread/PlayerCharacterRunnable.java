/*
 * Runnable to control the player character
 */

package thread;

import java.awt.event.KeyEvent;

import entity.PlayerCharacter;
import entity.PlayerStatus;
import input.InputUtility;
import map.Terrain;

public class PlayerCharacterRunnable implements Runnable {

	private PlayerCharacter player;
	
	public PlayerCharacterRunnable(PlayerCharacter player) {
		this.player = player;
	}

	public void run() {
		// TODO Implement running
		while (true) {

			int terrain = PlayerStatus.getPlayer().getCurrentMap().getTerrain(player.getX(), player.getY());

			if (!Terrain.isGround(terrain)) {
				player.fall();
			}

			if (InputUtility.getKeyPressed(KeyEvent.VK_SPACE)) {
				if (Terrain.isGround(terrain)) {
					player.jump();
				}
			}

			if (InputUtility.getKeyPressed(KeyEvent.VK_LEFT)) {
				if (!Terrain.isLeftWall(terrain)) {
					player.walk(PlayerCharacter.LEFT);
				}
			}
			else if (InputUtility.getKeyPressed(KeyEvent.VK_RIGHT)) {
				if (!Terrain.isRightWall(terrain)) {
					player.walk(PlayerCharacter.RIGHT);
				}
			}
			
			//TODO use skills

			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {}
		}
	}

}
