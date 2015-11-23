/*
 * Checkpoints act as a save point and also a turn indicator
 */

package entity;

import entity.card.SkillCard;
import render.Renderable;

public class Checkpoint implements Renderable {
	
	private SkillCard[] skillCards;
	private boolean used;
	
	public Checkpoint(SkillCard[] skillCards) {
		this.skillCards = skillCards;
		used = false;
	}
	
	public SkillCard[] drawCard() {
		used = true;
		return skillCards;
	}
	
	public boolean isUsed() {
		return used;
	}
	
	@Override
	public void render() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isVisible() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public int getZ() {
		// TODO Auto-generated method stub
		return 0;
	}

}
