/*
 * The physical player playing the game
 * Shows the status on the screen, such as mana and hand
 */

package entity;

import java.util.List;

import entity.card.SkillCard;

import java.util.ArrayList;

import render.Renderable;

public class PlayerStatus implements Renderable {
	
	private int currentMana, maxMana;
	private List<SkillCard> hand;
	
	public PlayerStatus() {
		maxMana = 2;
		currentMana = 2;
		hand = new ArrayList<SkillCard>();
	}
	
	public int getCurrentMana() {
		return currentMana;
	}
	
	public int getMaxMana() {
		return maxMana;
	}
	
	public void chargeMana() {
		maxMana++;
		currentMana++;
	}
	
	public List<SkillCard> getHand() {
		return hand;
	}
	
	public void addCard(SkillCard skillCard) {
		hand.add(skillCard);
	}

	@Override
	public void render() {}
	
	@Override
	public boolean isVisible() {
		return true;
	}
	
	@Override
	public int getZ() {
		return 0;
	}

}