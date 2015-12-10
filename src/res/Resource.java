package res;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class Resource {

	public static BufferedImage playerIdleSprite;
	public static BufferedImage floorTile;
	
	public static InputStream emptyMap, testMap, bigMap;

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

		emptyMap = loader.getResourceAsStream("res/map/emptyMap.map");
		testMap = loader.getResourceAsStream("res/map/testMap.map");
		bigMap = loader.getResourceAsStream("res/map/bigMap.map");
//			bigMap = new File();

	}

}
