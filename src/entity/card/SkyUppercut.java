package entity.card;

import java.awt.event.KeyEvent;

public class SkyUppercut extends SkillCard {

	public SkyUppercut() {
		cost = 2;
		command = new int[]{KeyEvent.VK_UP, KeyEvent.VK_F};
	}

}
