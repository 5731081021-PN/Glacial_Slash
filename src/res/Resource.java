package res;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class Resource {

	public static BufferedImage playerIdleSprite;
	public static BufferedImage floorTile;
	public static BufferedImage mana[],maxMana[],slash;
	
<<<<<<< HEAD
=======
	
	public static InputStream emptyMap, testMap, bigMap;
>>>>>>> refs/remotes/5731025921-CJ/master

	public static File emptyMap, testMap, bigMap;
	
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

<<<<<<< HEAD
		try {
			emptyMap = new File(loader.getResource("res/map/emptyMap.map").toURI());
		} catch (URISyntaxException e) {
			emptyMap = null;
		}
		try {
			testMap = new File(loader.getResource("res/map/testMap.map").toURI());
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			testMap = null;
		}
		try {
			bigMap = new File(loader.getResource("res/map/bigMap.map").toURI());
		} catch (URISyntaxException e) {
			bigMap = null;
		}

		// Load mana,maxMana and slash
		for (int i = 0; i <= 20; i++) {
			try {
				mana[i] = ImageIO.read(loader.getResource("res/mana/i.png"));
			} catch (IOException e ) {
				mana[i] = null;
			}
		}
		for (int i = 0; i <= 20; i++) {
			try {
				maxMana[i] = ImageIO.read(loader.getResource("res/mana/mi.png"));
			} catch (IOException e ) {
				maxMana[i] = null;
			}
		}
			try {
				slash = ImageIO.read(loader.getResource("res/mana/slash.png"));
			} catch (IOException e) {
				slash = null;
			}
=======
		emptyMap = loader.getResourceAsStream("res/map/emptyMap.map");
		testMap = loader.getResourceAsStream("res/map/testMap.map");
		bigMap = loader.getResourceAsStream("res/map/bigMap.map");
//			bigMap = new File();
>>>>>>> refs/remotes/5731025921-CJ/master

	}

}
