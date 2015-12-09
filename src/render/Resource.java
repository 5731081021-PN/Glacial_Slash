package render;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Resource {

	public static BufferedImage playerIdleSprite, floorTile;
	private static ClassLoader loader = Resource.class.getClassLoader();
	
	static {

		try {
			playerIdleSprite = ImageIO.read(loader.getResource("res/sprite/player.png"));
		} catch (IOException e) {
			playerIdleSprite = null;
		}

		try {
			floorTile = ImageIO.read(loader.getResource("res/tile/floor.png"));
		} catch (IOException e) {
			floorTile = null;
		}

	}

}
