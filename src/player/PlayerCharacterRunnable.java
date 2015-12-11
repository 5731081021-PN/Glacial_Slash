/*
 * Runnable to control the player character
 */

package player;

import exception.SkillCardUnusableException;
import input.InputUtility;
import input.InputUtility.CommandKey;
import render.GameScreen;

public class PlayerCharacterRunnable implements Runnable {

	private PlayerCharacter player;
	
	public PlayerCharacterRunnable() {
		this.player = PlayerStatus.getPlayer().getPlayerCharacter();
	}

	public void run() {
		while (true) {
			// TODO Auto-generated method stub
			try {
				Thread.sleep(35);
			} catch (InterruptedException e) {}
			
			playerInputUpdate();

			GameScreen.getScreen().centerCameraAt(player.getCenterX(), player.getCenterY());
			GameScreen.getScreen().repaint();
		}

	}
	
	private void playerInputUpdate() {
	
		player.updateBoundaries();
		player.fall();
	
		if (player.getFreezePlayerControlCount() <= 0) {

			synchronized (PlayerStatus.getPlayer().getHand()) {
				if (InputUtility.getKeyPressed(CommandKey.LEFT))
					player.walk(PlayerCharacter.LEFT);
				else if (InputUtility.getKeyPressed(CommandKey.RIGHT))
					player.walk(PlayerCharacter.RIGHT);
				else
					player.walk(PlayerCharacter.IDLE);

				if (InputUtility.getKeyTriggered(CommandKey.JUMP)) {
					if (player.isOnGround())
						player.jump();
					else
						try {
							PlayerStatus.getPlayer().useCard(SkillCard.DOUBLE_JUMP);
						} catch (SkillCardUnusableException e) {}
				}

				if (InputUtility.getKeyTriggered(CommandKey.SLASH)) {
					if (InputUtility.getKeyPressed(CommandKey.UP)) {
						try {
							PlayerStatus.getPlayer().useCard(SkillCard.SKY_UPPERCUT);
						} catch (SkillCardUnusableException e) {
							player.slash();
						}
					}
					else
						player.slash();
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
			}

		}
		else {
			player.decreseFreezePlayerControlCount();
		}

		player.moveX();
		player.moveY();

		InputUtility.clearKeyTriggered();
	}

}
