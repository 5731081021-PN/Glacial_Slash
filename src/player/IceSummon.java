package player;

import java.awt.Point;
import java.awt.Rectangle;

import entity.map.GameMap;
import exception.SkillCardUnusableException;
import res.Resource;

public class IceSummon extends SkillCard {

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
		player.performIceSummon();
		new Thread (new Runnable() {
			
			@Override
			public void run() {
				try {
					player.getIceSummonAnimationThread().join();
				} catch (InterruptedException e) {}
				map.freeze((int)spriteFrontTile.getX(), (int)spriteFrontTile.getY());
			}
		}).start();
	}

}
