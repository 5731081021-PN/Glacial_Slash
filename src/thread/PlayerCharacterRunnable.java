/*
 * Runnable to control the player character
 */

package thread;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import entity.PlayerCharacter;
import input.InputUtility;
import input.InputUtility.CommandKey;
import render.GameScreen;

public class PlayerCharacterRunnable implements Runnable {

	private PlayerCharacter player;
	
	public PlayerCharacterRunnable(PlayerCharacter player) {
		this.player = player;
	}

	public void run() {
//		while (true) {
		new Timer(20, new ActionListener() {
			
	@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

			player.updateBoundaries();
			player.fall();
			
			synchronized (GameScreen.getScreen()) {
				
				if (InputUtility.getKeyPressed(CommandKey.LEFT))
					player.walk(PlayerCharacter.LEFT);
				else if (InputUtility.getKeyPressed(CommandKey.RIGHT))
					player.walk(PlayerCharacter.RIGHT);
				else
					player.walk(0);

				if (InputUtility.getKeyTriggered(CommandKey.JUMP)) {
					if (player.isOnGround())
						player.jump();
				}
			//TODO slashing with the sabre
			//TODO use skills
			}
			
			player.moveX();
			player.moveY();

			InputUtility.clearKeyTriggered();
			
			GameScreen.getScreen().centerCameraAt(player.getCenterX(), player.getCenterY());
			GameScreen.getScreen().repaint();

			}
		}).start();
//		}
	}

}
