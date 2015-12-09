package render;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Resource {

	public static BufferedImage playerIdleSprite, floorSprite;
	private static ClassLoader loader = Resource.class.getClassLoader();
	
	static {

		try {
			playerIdleSprite = ImageIO.read(loader.getResource("res/player.png"));
		} catch (IOException e) {
			playerIdleSprite = null;
		}

		try {
			floorSprite = ImageIO.read(loader.getResource("res/floor.png"));
		} catch (IOException e) {
			floorSprite = null;
		}

	}

}
