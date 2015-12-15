package player;

import java.io.IOException;
import java.io.ObjectInputStream;

import exception.SkillCardUnusableException;
import res.Resource;

public class SkyUppercut extends SkillCard {

	private static final long serialVersionUID = 8033869263693885942L;

	public SkyUppercut() {
		super(3, Resource.skyUppercut);
	}

	@Override
	public void activate() throws SkillCardUnusableException {
		if (!PlayerStatus.getPlayer().getPlayerCharacter().isOnGround()) throw new SkillCardUnusableException(SkillCardUnusableException.UnusableType.ACTIVATE_CONDITION_NOT_MET);
		playActivateAnimation();
		PlayerStatus.getPlayer().getPlayerCharacter().performSkyUppercut();
	}
	
	private void readObject(ObjectInputStream in) throws ClassNotFoundException, IOException {
		in.defaultReadObject();
		originalCardImage = SkillCard.SKY_UPPERCUT.cardImage;
		cardImage = originalCardImage;
	}

}
