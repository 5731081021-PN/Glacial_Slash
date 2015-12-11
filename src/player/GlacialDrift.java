package player;

import exception.SkillCardUnusableException;
import res.Resource;

public class GlacialDrift extends SkillCard {
	
	public GlacialDrift() {
		cost = 2;
		cardImage = Resource.glacialDrift;
	}
	
	@Override
	public void activate() throws SkillCardUnusableException {
		playActivateAnimation();
		PlayerStatus.getPlayer().getPlayerCharacter().performGlacialDrift();
	}

}
