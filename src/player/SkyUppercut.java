package player;

import exception.SkillCardUnusableException;
import res.Resource;

public class SkyUppercut extends SkillCard {

	public SkyUppercut() {
		cost = 2;
		cardImage = Resource.skyUppercut;
	}

	@Override
	public void activate() throws SkillCardUnusableException {
		if (!PlayerStatus.getPlayer().getPlayerCharacter().isOnGround()) throw new SkillCardUnusableException(SkillCardUnusableException.UnusableType.ACTIVATE_CONDITION_NOT_MET);
		playActivateAnimation();
		PlayerStatus.getPlayer().getPlayerCharacter().performSkyUpperCut();
	}

}
