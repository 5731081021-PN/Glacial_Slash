/*
 * Runnable to control the player character
 */

package player;

import javax.swing.JOptionPane;

import exception.SkillCardUnusableException;
import input.InputUtility;
import input.InputUtility.CommandKey;
import ui.GameScreen;

public class GameLoop implements Runnable {

	private PlayerStatus playerStatus;
	private PlayerCharacter playerCharacter;
	
	public GameLoop() {
		this.playerStatus = PlayerStatus.getPlayer();
		this.playerCharacter = playerStatus.getPlayerCharacter();
	}

	public void run() {
		
		final long FRAME_RATE = 30;
		final long UPDATE_TIME = 1000000000 / FRAME_RATE;
		
		long lastUpdateTime = System.nanoTime();

		while (true) {

			long now = System.nanoTime();

			while (now - lastUpdateTime < UPDATE_TIME) {
				now = System.nanoTime();
				Thread.yield();
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {}
			}

			lastUpdateTime = now;
		
			if (InputUtility.getKeyTriggered(CommandKey.EXIT)) {
				InputUtility.clearKeyPressed();
				if (JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					synchronized (this) {
						this.notifyAll();
					}
				}
			}
			
			if (InputUtility.getKeyTriggered(CommandKey.RETURN)) {
				InputUtility.clearKeyPressed();
				if (JOptionPane.showConfirmDialog(null, "Return to last checkpoint?", "Got stuck?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					playerStatus.returnToLastCheckpoint();
				}
			}
		
			playerInputUpdate();
			mapComponentUpdate();

			synchronized (GameScreen.getScreen()) {
				GameScreen.getScreen().notifyAll();
				try {
					GameScreen.getScreen().wait();
				} catch (InterruptedException e) {}
			}

		}

	}
	
	private void playerInputUpdate() {
	
		playerCharacter.updateBoundaries();
		playerCharacter.fall();
		
		if (playerCharacter.getFreezePlayerControlCount() <= 0) {

			synchronized (playerStatus.getHand()) {
				if (InputUtility.getKeyPressed(CommandKey.LEFT))
					playerCharacter.walk(PlayerCharacter.LEFT);
				else if (InputUtility.getKeyPressed(CommandKey.RIGHT))
					playerCharacter.walk(PlayerCharacter.RIGHT);
				else
					playerCharacter.walk(PlayerCharacter.IDLE);

				if (InputUtility.getKeyTriggered(CommandKey.JUMP)) {
					if (playerCharacter.isOnGround())
						playerCharacter.jump();
					else
						try {
							playerStatus.useCard(SkillCard.DOUBLE_JUMP);
						} catch (SkillCardUnusableException e) {}
				}

				if (InputUtility.getKeyTriggered(CommandKey.SLASH)) {
					if (InputUtility.getKeyPressed(CommandKey.UP)) {
						try {
							playerStatus.useCard(SkillCard.SKY_UPPERCUT);
						} catch (SkillCardUnusableException e) {
							playerCharacter.slash();
						}
					}
					else
						playerCharacter.slash();
				}

				if (InputUtility.getKeyTriggered(CommandKey.DASH)) {
					if (InputUtility.getKeyPressed(CommandKey.LEFT) || InputUtility.getKeyPressed(CommandKey.RIGHT)) {
						try {
							playerStatus.useCard(SkillCard.GLACIAL_DRIFT);
						} catch (SkillCardUnusableException e) {}
					}
				}
				
				if (InputUtility.getKeyTriggered(CommandKey.HAND)) {
					if (InputUtility.getKeyPressed(CommandKey.DOWN))
						try {
							playerStatus.useCard(SkillCard.ICE_SUMMON);
						} catch (SkillCardUnusableException e) {}
				}
				
				if (InputUtility.getKeyTriggered(CommandKey.DRAW)) {
					try {
						playerStatus.useCard(SkillCard.CONCENTRATION);
					} catch (SkillCardUnusableException e) {}
				}

			}

		}
		else {
			playerCharacter.decreseFreezePlayerControlCount();
		}

		playerCharacter.moveX();
		playerCharacter.moveY();

		InputUtility.clearKeyTriggered();
	}
	
	private void mapComponentUpdate() {
		playerCharacter.collideCheckPoint();
		if (playerCharacter.collideTransitionPoint()) {
			playerStatus.goToNextMap();
		}
	}

}
