package entity;

import render.Renderable;

public class Player implements Renderable {
	
	private int x, y;
	private int currentMana, maxMana;
	
	public Player() {
		maxMana = 2;
		currentMana = 2;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
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

	public void update() {
		
	}

	@Override
	public void draw() {}
	
	@Override
	public boolean isVisible() {
		return true;
	}
	
	@Override
	public int getZ() {
		return 0;
	}

}
