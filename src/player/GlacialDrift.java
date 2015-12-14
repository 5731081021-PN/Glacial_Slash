package player;

import java.io.IOException;
import java.io.ObjectInputStream;

import exception.SkillCardUnusableException;
import res.Resource;

public class GlacialDrift extends SkillCard {
	
	private static final long serialVersionUID = -951186494996787158L;

	public GlacialDrift() {
		super (2, Resource.glacialDrift);
	}
	
	@Override
	public void activate() throws SkillCardUnusableException {
		playActivateAnimation();
		PlayerStatus.getPlayer().getPlayerCharacter().performGlacialDrift();
	}
	
	private void readObject(ObjectInputStream in) throws ClassNotFoundException, IOException {
		in.defaultReadObject();
		originalCardImage = SkillCard.GLACIAL_DRIFT.cardImage;
		cardImage = originalCardImage;
	}

}
