package player;

import java.awt.Graphics2D;

import exception.CardUnusableException;

public class SkyUppercut extends SkillCard {

	public SkyUppercut() {
		cost = 2;
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
	public void activate() throws CardUnusableException {
		// TODO Auto-generated method stub
		if (!PlayerStatus.getPlayer().getPlayerCharacter().isOnGround()) throw new CardUnusableException(CardUnusableException.UnusableType.WRONG_USE_CONDITION);
		playActivateAnimation();
		PlayerStatus.getPlayer().getPlayerCharacter().performSkyUpperCut();
	}

}
