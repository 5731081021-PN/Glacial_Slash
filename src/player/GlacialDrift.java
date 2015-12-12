package player;

import exception.SkillCardUnusableException;
import res.Resource;

public class GlacialDrift extends SkillCard {
	
	public GlacialDrift() {
		super (2, Resource.glacialDrift);
	}
	
	@Override
	public void activate() throws SkillCardUnusableException {
		playActivateAnimation();
		PlayerStatus.getPlayer().getPlayerCharacter().performGlacialDrift();
	}

}
