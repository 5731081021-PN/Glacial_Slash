package player;

import java.io.IOException;
import java.io.ObjectInputStream;

import exception.SkillCardUnusableException;
import res.Resource;

public class DoubleJump extends SkillCard {

	public DoubleJump() {
		super(2, Resource.doubleJump);
	}

	@Override
	public void activate() throws SkillCardUnusableException {
		if (PlayerStatus.getPlayer().getPlayerCharacter().getAirJumpCount() <= 0) throw new SkillCardUnusableException(SkillCardUnusableException.UnusableType.ACTIVATE_CONDITION_NOT_MET);
		playActivateAnimation();
		PlayerStatus.getPlayer().getPlayerCharacter().jump();
	}
	
	private void readObject(ObjectInputStream in) throws ClassNotFoundException, IOException {
		in.defaultReadObject();
		originalCardImage = SkillCard.DOUBLE_JUMP.cardImage;
		cardImage = originalCardImage;
	}

}
