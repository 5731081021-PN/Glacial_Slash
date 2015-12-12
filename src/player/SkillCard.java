/*
 * Abstract class representing every kind of cards
 */

package player;

import java.awt.Graphics2D;
import java.awt.Image;

import exception.SkillCardUnusableException;
import render.GameScreen;
import render.Renderable;

public abstract class SkillCard implements Renderable, Comparable<SkillCard> {

	public static final SkillCard SKY_UPPERCUT = new SkyUppercut(), DOUBLE_JUMP = new DoubleJump(), GLACIAL_DRIFT = new GlacialDrift(), ICE_SUMMON = new IceSummon(), CONCENTRATION = new Concentration(null);
	public static final int CARD_IMAGE_WIDTH = 120, CARD_IMAGE_HEIGHT = 180;
	protected int cost;
	protected Image cardImage;
	private int x, y = GameScreen.SCREEN_HEIGHT - (CARD_IMAGE_HEIGHT + 20);
	
	public abstract void activate() throws SkillCardUnusableException;
	
	public SkillCard(int cost, Image cardImage) {
		this.cost = cost;
		this.cardImage = cardImage;
	}
	
	public static final SkillCard createSkillCard(String name) {
		// Use when add cards only
		// Use dummy constants for reference
		if (name.startsWith("S")) return new SkyUppercut();
		else if (name.startsWith("D")) return new DoubleJump();
		else if (name.startsWith("G")) return new GlacialDrift();
		else if (name.startsWith("I")) return new IceSummon();
		else if (name.startsWith("C")) {
			// TODO implement concentration
			String[] format = name.split("\\s+");
			int n = Integer.parseInt(format[1]);
			SkillCard[] drawnCards = new SkillCard[n];
			for (int i = 0; i < n; i++) {
				drawnCards[i] = SkillCard.createSkillCard(format[2+i]);
			}
			return new Concentration(drawnCards);
		}
		else return null;
	}
	
	public void playActivateAnimation() {
		// TODO play activate animation
	}
	
	@Override
	public int compareTo(SkillCard other) {
		int costCompare = Integer.compare(this.cost, other.cost);
		if (costCompare == 0)
			return this.getClass().getSimpleName().compareToIgnoreCase(other.getClass().getSimpleName());
		else
			return costCompare;
	}
	
	public void render(Graphics2D g, int index) {
		x = 10 + CARD_IMAGE_WIDTH*index;
		render(g);
	}
	
	@Override
	public void render(Graphics2D g) {
		g.drawImage(cardImage, x, y, null);
	}
	
	@Override
	public boolean isVisible() {
		return true;
	}
	
	@Override
	public int getZ() {
		return -1;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.getClass().getSimpleName().hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SkillCard other = (SkillCard) obj;
		return this.getClass().getSimpleName().equals(other.getClass().getSimpleName());
	}

}
