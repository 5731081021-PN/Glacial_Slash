/*
 * Everything to be on screen must implement this for code readability
 */

package render;

public interface Renderable {

	public void render();
	public boolean isVisible();
	public int getZ();

}
