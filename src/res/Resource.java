package res;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class Resource {

	public static BufferedImage playerIdleSprite;
	public static BufferedImage floorTile;
	public static BufferedImage[] mana = new BufferedImage[21], maxMana = new BufferedImage[21];
	public static BufferedImage slash;
	
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

		// Load mana,maxMana and slash
		for (int i = 0; i <= 20; i++) {
			try {
				mana[i] = ImageIO.read(loader.getResource("res/mana/" + i + ".png"));
			} catch (IOException e ) {
				mana[i] = null;
			}
		}
		for (int i = 0; i <= 20; i++) {
			try {
				maxMana[i] = ImageIO.read(loader.getResource("res/mana/m" + i + ".png"));
			} catch (IOException e ) {
				maxMana[i] = null;
			}
		}
		try {
			slash = ImageIO.read(loader.getResource("res/mana/slash.png"));
		} catch (IOException e) {
			slash = null;
		}

		emptyMap = loader.getResourceAsStream("res/map/emptyMap.map");
		testMap = loader.getResourceAsStream("res/map/testMap.map");
		bigMap = loader.getResourceAsStream("res/map/bigMap.map");

	}

}
