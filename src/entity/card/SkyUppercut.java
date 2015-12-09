package entity.card;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class SkyUppercut extends SkillCard {

	public SkyUppercut() {
		cost = 2;
		command = new int[]{KeyEvent.VK_UP, KeyEvent.VK_F};
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
	public void activate() {
		// TODO Auto-generated method stub
		
	}

}
