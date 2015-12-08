/*
 * Abstract class representing every kind of cards
 */

package entity.card;

import render.Renderable;

public abstract class SkillCard implements Renderable, Comparable<SkillCard> {

	protected int cost;
	protected int[] command;
	
	public abstract void activate();
	
	@Override
	public int compareTo(SkillCard other) {
		int costCompare = Integer.compare(this.cost, other.cost);
		if (costCompare == 0)
			return this.getClass().getSimpleName().compareToIgnoreCase(other.getClass().getSimpleName());
		else
			return costCompare;
	}

}
