/*
 * Runnable to control the player character
 */

package thread;

import java.awt.event.KeyEvent;

import entity.PlayerCharacter;
import entity.PlayerStatus;
import entity.map.Terrain;
import input.InputUtility;
import render.GameScreen;

public class PlayerCharacterRunnable implements Runnable {

	private PlayerCharacter player;
	
	public PlayerCharacterRunnable(PlayerCharacter player) {
		this.player = player;
	}

	public void run() {
		while (true) {

			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {}

//			int terrain = PlayerStatus.getPlayer().getCurrentMap().getTerrain(player.getX(), player.getY());
			
			player.updateBoundaries();

			//TODO player controls
			//TODO slashing with the sabre
			//TODO use skills
			
			player.moveX();
			player.moveY();

			GameScreen.getScreen().repaint();
		}
	}

}
