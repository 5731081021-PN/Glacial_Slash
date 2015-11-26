/*
 * Abstract class representing every kind of cards
 */

package entity.card;

import render.Renderable;

public abstract class SkillCard implements Renderable {

	protected int cost;
	protected int[] command;
	
	public abstract void activate();

}
