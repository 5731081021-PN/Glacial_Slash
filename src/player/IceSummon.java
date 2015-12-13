package player;

import java.awt.Point;
import java.awt.Rectangle;
import java.io.IOException;
import java.io.ObjectInputStream;

import exception.SkillCardUnusableException;
import map.GameMap;
import res.Resource;

public class IceSummon extends SkillCard {
	
	private transient Thread iceSummonThread;

	public IceSummon() {
		super(1, Resource.iceSummon);
	}

	@Override
	public void activate() throws SkillCardUnusableException {
		PlayerCharacter player = PlayerStatus.getPlayer().getPlayerCharacter();
		GameMap map = PlayerStatus.getPlayer().getCurrentMap();
		if (!player.isOnGround()) throw new SkillCardUnusableException(SkillCardUnusableException.UnusableType.ACTIVATE_CONDITION_NOT_MET);
		Point frontTile = player.getFrontTile();
		if (!map.getTileType((int)frontTile.getX(), (int)frontTile.getY()).isPassable()) throw new SkillCardUnusableException(SkillCardUnusableException.UnusableType.ACTIVATE_CONDITION_NOT_MET);
		Point spriteFrontTile = player.getSpriteFrontTile();
		if (!map.isOnGround(new Rectangle((int)spriteFrontTile.getX()*map.getTileWidth(), (int)spriteFrontTile.getY()*map.getTileHeight(), map.getTileWidth(), map.getTileHeight()))) throw new SkillCardUnusableException(SkillCardUnusableException.UnusableType.ACTIVATE_CONDITION_NOT_MET);
		if (!map.getTileType((int)spriteFrontTile.getX(), (int)spriteFrontTile.getY()).isPassable()) throw new SkillCardUnusableException(SkillCardUnusableException.UnusableType.ACTIVATE_CONDITION_NOT_MET);
		playActivateAnimation();
		synchronized (activateAnimationThread) {
			activateAnimationThread.notifyAll();
		}
		player.performIceSummon();
		iceSummonThread = new Thread (new Runnable() {
			
			@Override
			public void run() {
				try {
					player.getIceSummonAnimationThread().join();
				} catch (InterruptedException e) {
					return;
				}
				map.freeze((int)spriteFrontTile.getX(), (int)spriteFrontTile.getY());
			}
		});
		iceSummonThread.start();
	}
	
	protected Thread getIceSummonThread() {
		return iceSummonThread;
	}
	
	private void readObject(ObjectInputStream in) throws ClassNotFoundException, IOException {
		in.defaultReadObject();
		originalCardImage = SkillCard.ICE_SUMMON.cardImage;
		cardImage = originalCardImage;
	}

}
