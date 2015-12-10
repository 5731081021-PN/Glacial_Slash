package res;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;

public class Resource {

	public static BufferedImage playerIdleSprite;
	public static BufferedImage floorTile;
	
	public static File emptyMap,testMap,bigMap;

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
	}

}
