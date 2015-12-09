/*
 * Holder class for terrain constants
 */

package entity.map;

public final class Terrain {

	public static final int RIGID = 0;
	public static final int GROUND = 1;
	public static final int AIR = 2;
	public static final int WALL_LEFT = 4;
	public static final int WALL_RIGHT = 8;
	
	public static boolean isRigid(int t) {
		return t == RIGID;
	}
	
	public static boolean isGround(int t) {
		return (t&GROUND) != 0;
	}
	
	public static boolean isAir(int t) {
		return (t&AIR) != 0;
	}
	
	public static boolean isLeftWall(int t) {
		return (t&WALL_LEFT) != 0;
	}
	
	public static boolean isRightWall(int t) {
		return (t&WALL_RIGHT) != 0;
	}
	
}
