/*
 * Checkpoints act as a save point and also a turn indicator
 */

package map;

import java.awt.Graphics2D;
import java.io.Serializable;
import java.util.List;

import player.SkillCard;
import render.Renderable;
import res.Resource;
import ui.GameScreen;

public class Checkpoint implements Renderable, Serializable {
	
	private int x, y;
	private List<SkillCard> skillCards;
	private boolean used;
	
	public Checkpoint(int x, int y, List<SkillCard> skillCards) {
		this.x = x;
		this.y = y;
		this.skillCards = skillCards;
		used = false;
	}
	
	public List<SkillCard> drawCard() {
		used = true;
		return skillCards;
	}
	
	public boolean isUsed() {
		return used;
	}
	
	@Override
	public void render(Graphics2D g) {
		g.drawImage(Resource.checkpoint, null, x - GameScreen.getScreen().getCameraX(), y - GameScreen.getScreen().getCameraY());
	}

}
