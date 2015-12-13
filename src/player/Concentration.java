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
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					activateAnimationThread.join();
				} catch (InterruptedException e) {}
				for (SkillCard s : drawnCards)
					PlayerStatus.getPlayer().addCard(s);
			}
		}).start();
	}
	
}
