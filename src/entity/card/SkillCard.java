package entity.card;

import render.Renderable;

public abstract class SkillCard implements Renderable {

	protected int cost;
	
	public abstract void activate();

}