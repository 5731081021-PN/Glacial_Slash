package player;

import java.awt.Graphics2D;

import exception.SkillCardUnusableException;

public class DoubleJump extends SkillCard {

	public DoubleJump() {
		cost = 3;
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

	@Override
	public void activate() throws SkillCardUnusableException {
		if (PlayerStatus.getPlayer().getPlayerCharacter().getAirJumpCount() <= 0) throw new SkillCardUnusableException(SkillCardUnusableException.UnusableType.WRONG_USE_CONDITION);
		playActivateAnimation();
		PlayerStatus.getPlayer().getPlayerCharacter().jump();
	}

}
