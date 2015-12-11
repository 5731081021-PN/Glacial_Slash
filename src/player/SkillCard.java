/*
 * Abstract class representing every kind of cards
 */

package player;

import java.awt.Image;

import exception.WrongCardNameFormatException;
import render.Renderable;

public abstract class SkillCard implements Renderable, Comparable<SkillCard> {

	protected int cost;
	protected int[] command; // maybe hardcode the command to player
	protected Image cardImage;
	
	public abstract void activate();
	
	public static final SkillCard createSkillCard(String name) throws WrongCardNameFormatException {
		// TODO create new SkillCard from its name
		throw new WrongCardNameFormatException();
	}
	
	@Override
	public int compareTo(SkillCard other) {
		int costCompare = Integer.compare(this.cost, other.cost);
		if (costCompare == 0)
			return this.getClass().getSimpleName().compareToIgnoreCase(other.getClass().getSimpleName());
		else
			return costCompare;
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
