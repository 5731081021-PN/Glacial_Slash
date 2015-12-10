package res;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;

public class Resource {

	public static BufferedImage playerIdleSprite;
	public static BufferedImage floorTile;
	
	public static File emptyMap;

	private static ClassLoader loader = Resource.class.getClassLoader();
	
	static {

		try {
			playerIdleSprite = ImageIO.read(loader.getResource("res/sprite/player.png"));
		} catch (IOException e) {
			playerIdleSprite = null;
		}

		try {
			floorTile = ImageIO.read(loader.getResource("res/tile/tile.png"));
		} catch (IOException e) {
			floorTile = null;
		}
	
		try {
			emptyMap = new File(loader.getResource("res/map/emptyMap.map").toURI());
		} catch (URISyntaxException e) {
			emptyMap = null;
		}

	}

}
