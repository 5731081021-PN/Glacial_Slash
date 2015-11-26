/*
 * Runnable to control the player character
 */

package thread;

import java.awt.event.KeyEvent;

import entity.PlayerCharacter;
import input.InputUtility;

public class PlayerCharacterRunnable implements Runnable {

	private PlayerCharacter player;
	
	public PlayerCharacterRunnable(PlayerCharacter player) {
		this.player = player;
	}

	public void run() {
		// TODO Implement running
	}

}
