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

	public void run() {
		
		final long FRAME_RATE = 60;
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
					PlayerStatus.getPlayer().returnToLastCheckPoint();
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
	
		PlayerStatus.getPlayer().getPlayerCharacter().updateBoundaries();
		PlayerStatus.getPlayer().getPlayerCharacter().fall();
		
		if (PlayerStatus.getPlayer().getPlayerCharacter().getFreezePlayerControlCount() <= 0) {

			synchronized (PlayerStatus.getPlayer().getHand()) {
				if (InputUtility.getKeyPressed(CommandKey.LEFT))
					PlayerStatus.getPlayer().getPlayerCharacter().walk(PlayerCharacter.LEFT);
				else if (InputUtility.getKeyPressed(CommandKey.RIGHT))
					PlayerStatus.getPlayer().getPlayerCharacter().walk(PlayerCharacter.RIGHT);
				else
					PlayerStatus.getPlayer().getPlayerCharacter().walk(PlayerCharacter.IDLE);

				if (InputUtility.getKeyTriggered(CommandKey.JUMP)) {
					if (PlayerStatus.getPlayer().getPlayerCharacter().isOnGround())
						PlayerStatus.getPlayer().getPlayerCharacter().jump();
					else
						try {
							PlayerStatus.getPlayer().useCard(SkillCard.DOUBLE_JUMP);
						} catch (SkillCardUnusableException e) {}
				}

				if (InputUtility.getKeyTriggered(CommandKey.SLASH)) {
					if (InputUtility.getKeyPressed(CommandKey.UP)) {
						try {
							PlayerStatus.getPlayer().useCard(SkillCard.SKY_UPPERCUT);
						} catch (SkillCardUnusableException e) {}
					}
				}

				if (InputUtility.getKeyTriggered(CommandKey.DASH)) {
					if (InputUtility.getKeyPressed(CommandKey.LEFT) || InputUtility.getKeyPressed(CommandKey.RIGHT)) {
						try {
							PlayerStatus.getPlayer().useCard(SkillCard.GLACIAL_DRIFT);
						} catch (SkillCardUnusableException e) {}
					}
				}
				
				if (InputUtility.getKeyTriggered(CommandKey.HAND)) {
					if (InputUtility.getKeyPressed(CommandKey.DOWN))
						try {
							PlayerStatus.getPlayer().useCard(SkillCard.ICE_SUMMON);
						} catch (SkillCardUnusableException e) {}
				}
				
				if (InputUtility.getKeyTriggered(CommandKey.DRAW)) {
					try {
						PlayerStatus.getPlayer().useCard(SkillCard.CONCENTRATION);
					} catch (SkillCardUnusableException e) {}
				}

			}

		}
		else {
			PlayerStatus.getPlayer().getPlayerCharacter().decreseFreezePlayerControlCount();
		}

		PlayerStatus.getPlayer().getPlayerCharacter().moveX();
		PlayerStatus.getPlayer().getPlayerCharacter().moveY();

		InputUtility.clearKeyTriggered();
	}
	
	private void mapComponentUpdate() {
		PlayerStatus.getPlayer().getPlayerCharacter().collideCheckPoint();
		if (PlayerStatus.getPlayer().getPlayerCharacter().collideTransitionPoint()) {
			PlayerStatus.getPlayer().goToNextMap();
		}
	}

}
