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
				Thread.sleep(17);
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
						// Debug
						PlayerStatus.getPlayer().addCard(SkillCard.createSkillCard("Double Jump"));
						PlayerStatus.getPlayer().addCard(SkillCard.createSkillCard("Double Jump"));
						PlayerStatus.getPlayer().addCard(SkillCard.createSkillCard("Double Jump"));
						PlayerStatus.getPlayer().chargeMana(SkillCard.createSkillCard("Double Jump"));
						PlayerStatus.getPlayer().chargeMana(SkillCard.createSkillCard("Double Jump"));
						PlayerStatus.getPlayer().chargeMana(SkillCard.createSkillCard("Double Jump"));
						PlayerStatus.getPlayer().addCard(SkillCard.createSkillCard("Double Jump"));

						PlayerStatus.getPlayer().useCard(SkillCard.createSkillCard("Double Jump"));
					} catch (SkillCardUnusableException e) {}
			}

			if (InputUtility.getKeyTriggered(CommandKey.SLASH)) {
				if (InputUtility.getKeyPressed(CommandKey.UP)) {
					try {
						// Debug
						PlayerStatus.getPlayer().addCard(SkillCard.createSkillCard("Sky Uppercut"));
						PlayerStatus.getPlayer().addCard(SkillCard.createSkillCard("Sky Uppercut"));
						PlayerStatus.getPlayer().chargeMana(SkillCard.createSkillCard("Sky Uppercut"));
						PlayerStatus.getPlayer().chargeMana(SkillCard.createSkillCard("Sky Uppercut"));
						PlayerStatus.getPlayer().addCard(SkillCard.createSkillCard("Sky Uppercut"));

						PlayerStatus.getPlayer().useCard(SkillCard.createSkillCard("Sky Uppercut"));
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
						// Debug
						PlayerStatus.getPlayer().addCard(SkillCard.createSkillCard("Glacial Drift"));
						PlayerStatus.getPlayer().addCard(SkillCard.createSkillCard("Glacial Drift"));
						PlayerStatus.getPlayer().chargeMana(SkillCard.createSkillCard("Glacial Drift"));
						PlayerStatus.getPlayer().chargeMana(SkillCard.createSkillCard("Glacial Drift"));
						PlayerStatus.getPlayer().addCard(SkillCard.createSkillCard("Glacial Drift"));
						
						PlayerStatus.getPlayer().useCard(SkillCard.createSkillCard("Glacial Drift"));
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
