package res;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class Resource {

	public static BufferedImage playerIdleSprite;
	public static BufferedImage floorTile, tileLeft, tileRight, tileMid, tileHalf;
	public static BufferedImage[] mana = new BufferedImage[21], maxMana = new BufferedImage[21];
	public static BufferedImage slash;
	public static BufferedImage doubleJump, glacialDrift, iceSummon, skyUppercut, concentration;
	public static BufferedImage iceBlock;

	public static InputStream emptyMap, testMap, bigMap;

	private static ClassLoader loader = Resource.class.getClassLoader();

	static {

		try {
			playerIdleSprite = ImageIO.read(loader.getResource("res/sprite/player.png"));
		} catch (IOException e) {
			playerIdleSprite = null;
		}
		// Load tileset
		try {
			floorTile = ImageIO.read(loader.getResource("res/tile/floor.png"));
		} catch (IOException e) {
			floorTile = null;
		}
		try {
			tileLeft = ImageIO.read(loader.getResource("res/tile/tile_left.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			tileLeft =null;
		}
		try {
			tileRight = ImageIO.read(loader.getResource("res/tile/tile_right.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			tileRight =null;
		}
		try {
			tileMid = ImageIO.read(loader.getResource("res/tile/tile_mid.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			tileMid = null;
		}
		try {
			tileHalf = ImageIO.read(loader.getResource("res/tile/tile_half.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			tileHalf = null;
		}

		// Load mana,maxMana and slash
		for (int i = 0; i <= 20; i++) {
			try {
				mana[i] = ImageIO.read(loader.getResource("res/mana/" + i + ".png"));
			} catch (IOException e) {
				mana[i] = null;
			}
		}
		for (int i = 0; i <= 20; i++) {
			try {
				maxMana[i] = ImageIO.read(loader.getResource("res/mana/m" + i + ".png"));
			} catch (IOException e) {
				maxMana[i] = null;
			}
		}
		try {
			slash = ImageIO.read(loader.getResource("res/mana/slash.png"));
		} catch (IOException e) {
			slash = null;
		}

		try {
			doubleJump = ImageIO.read(loader.getResource("res/card/DoubleJump.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			doubleJump = null;
		}
		try {
			glacialDrift = ImageIO.read(loader.getResource("res/card/GlacialDrift.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			glacialDrift = null;
		}
		try {
			iceSummon = ImageIO.read(loader.getResource("res/card/IceSummon.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			iceSummon = null;
		}
		try {
			skyUppercut = ImageIO.read(loader.getResource("res/card/SkyUppercut.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			skyUppercut = null;
		}
		try {
			iceBlock = ImageIO.read(loader.getResource("res/ice/iceBlock.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			iceBlock = null;
		}
		try {
			concentration = ImageIO.read(loader.getResource("res/card/Concentration.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			concentration = null;
		}

		emptyMap = loader.getResourceAsStream("res/map/emptyMap.map");
		testMap = loader.getResourceAsStream("res/map/testMap.map");
		bigMap = loader.getResourceAsStream("res/map/bigMap.map");

	}

}
