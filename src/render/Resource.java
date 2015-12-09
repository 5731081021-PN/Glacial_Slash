package render;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Resource {

	private static BufferedImage playerIdleSprite ,floor;
	
	
	static {
		try {
			ClassLoader loader = Resource.class.getClassLoader();
			playerIdleSprite = ImageIO.read(loader.getResource("res/player.png"));
			floor = ImageIO.read(loader.getResource("res/floor.png"));
		} catch (IOException e) {
			playerIdleSprite = null;
			floor = null;
		}
	}
}
