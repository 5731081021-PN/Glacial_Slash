/*
 * Checkpoints act as a save point and also a turn indicator
 */

package map;

import java.awt.Graphics2D;

import player.SkillCard;
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
	public void render(Graphics2D g) {
		// TODO Auto-generated method stub
		
	}

}
