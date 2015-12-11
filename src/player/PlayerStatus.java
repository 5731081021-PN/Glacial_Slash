/*
 * The physical player playing the game
 * Shows the status on the screen, such as mana and hand
 */

package player;

import java.util.List;

import entity.map.GameMap;
import exception.CardUnusableException;

import java.awt.Graphics2D;
import java.util.ArrayList;

import render.Renderable;
import res.Resource;

public class PlayerStatus implements Renderable {
	
	private static PlayerStatus player;
	private int currentMana, maxMana;
	private List<SkillCard> hand;
	private GameMap currentMap;
	private PlayerCharacter playerCharacter;
	
	public static synchronized PlayerStatus newPlayer() {
		player = new PlayerStatus();
		return player;
	}
	
	public static synchronized void loadPlayer(PlayerStatus player) {
		PlayerStatus.player = player;
	}
	
	public static synchronized PlayerStatus getPlayer() {
		if (player == null)
			player = newPlayer();
		return player;
	}

	private PlayerStatus() {
		maxMana = 2;
		currentMana = 2;
		hand = new ArrayList<SkillCard>();
		currentMap = new GameMap(Resource.bigMap);
		playerCharacter = new PlayerCharacter();
	}
	
	public PlayerCharacter getPlayerCharacter() {
		return playerCharacter;
	}
	
	public int getCurrentMana() {
		return currentMana;
	}
	
	public int getMaxMana() {
		return maxMana;
	}
	
	public void chargeMana(SkillCard charged) {
		if (hand.remove(charged)) {
			maxMana++;
			currentMana++;
		}
	}
	
	public List<SkillCard> getHand() {
		return hand;
	}
	
	public void addCard(SkillCard skillCard) {
		hand.add(skillCard);
	}
	
	public void useCard(SkillCard used) throws CardUnusableException {
		if (hand.contains(used)) {
			if (currentMana >= used.cost) {
				used.activate();
				currentMana -= used.cost;
				hand.remove(used);
			}
			else throw new CardUnusableException(CardUnusableException.UnusableType.NOT_ENOUGH_MANA);
		}
		else throw new CardUnusableException(CardUnusableException.UnusableType.NO_SUCH_CARD_IN_HAND);
	}
	
	public GameMap getCurrentMap() {
		return currentMap;
	}

	@Override
	public void render(Graphics2D g) {}
	
	@Override
	public boolean isVisible() {
		return true;
	}
	
	@Override
	public int getZ() {
		return 0;
	}

}
