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
		if (!map.isOnGround(new Rectangle((int)frontTile.getX()*map.getTileWidth(), (int)frontTile.getY()*map.getTileHeight(), map.getTileWidth(), map.getTileHeight()))) throw new SkillCardUnusableException(SkillCardUnusableException.UnusableType.ACTIVATE_CONDITION_NOT_MET);
		try {
			map.freeze((int)frontTile.getX(), (int)frontTile.getY());
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new SkillCardUnusableException(SkillCardUnusableException.UnusableType.ACTIVATE_CONDITION_NOT_MET);
		}
		playActivateAnimation();
	}

}
