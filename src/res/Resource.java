package res;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;

public class Resource {

	public static BufferedImage playerIdleSprite;
	public static BufferedImage floorTile;

	public static File emptyMap, testMap, bigMap;
	public static File mana[], maxMana[], slash;
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
				mana[i] = new File(loader.getResource("res/mana/i.png" ).toURI());
			} catch (URISyntaxException e) {
				mana[i] = null;
			}
		}
		for (int i = 0; i <= 20; i++) {
			try {
				maxMana[i] = new File(loader.getResource("res/mana/mi.png" ).toURI());
			} catch (URISyntaxException e) {
				maxMana[i] = null;
			}
		}
			try {
				slash = new File(loader.getResource("res/mana/slash.png").toURI());
			} catch (URISyntaxException e) {
				slash = null;
			}

	}

}
