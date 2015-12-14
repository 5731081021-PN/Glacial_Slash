package player;

import java.io.IOException;
import java.io.ObjectInputStream;

import exception.SkillCardUnusableException;
import res.Resource;

public class Concentration extends SkillCard {
	
	private SkillCard[] drawnCards;
	private transient Thread concentrationThread;

	public Concentration(SkillCard[] drawnCards) {
		super(2, Resource.concentration);
		this.drawnCards = drawnCards;
	}

	@Override
	public void activate() throws SkillCardUnusableException {
		playActivateAnimation();
		concentrationThread = new Thread(new Runnable() {

			@Override
			public void run() {
				PlayerStatus.getPlayer().getPlayerCharacter().performConcentration();
				try {
					activateAnimationThread.join();
				} catch (InterruptedException e) {
					return;
				}
				for (SkillCard s : drawnCards) {
					synchronized (PlayerStatus.getPlayer().getHand()) {
						PlayerStatus.getPlayer().addCard(s);
					}
				}
			}
		});
		concentrationThread.start();
	}
	
	protected Thread getConcentrationThread() {
		return concentrationThread;
	}
	
	private void readObject(ObjectInputStream in) throws ClassNotFoundException, IOException {
		in.defaultReadObject();
		originalCardImage = SkillCard.CONCENTRATION.cardImage;
		cardImage = originalCardImage;
	}
	
}
