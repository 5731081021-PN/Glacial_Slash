package player;

import exception.SkillCardUnusableException;
import res.Resource;

public class Concentration extends SkillCard {
	
	private SkillCard[] drawnCards;

	public Concentration(SkillCard[] drawnCards) {
		super(2, Resource.concentration);
		this.drawnCards = drawnCards;
	}

	@Override
	public void activate() throws SkillCardUnusableException {
		playActivateAnimation();
		for (SkillCard s : drawnCards)
			PlayerStatus.getPlayer().addCard(s);
	}
	
}
