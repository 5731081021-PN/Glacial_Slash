package player;

import java.awt.Graphics2D;

import exception.SkillCardUnusableException;

public class GlacialDrift extends SkillCard {
	
	public GlacialDrift() {
		cost = 2;
	}
	
	@Override
	public void activate() throws SkillCardUnusableException {
		playActivateAnimation();
		PlayerStatus.getPlayer().getPlayerCharacter().performGlacialDrift();
	}

	@Override
	public void render(Graphics2D g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isVisible() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getZ() {
		// TODO Auto-generated method stub
		return 0;
	}

}
