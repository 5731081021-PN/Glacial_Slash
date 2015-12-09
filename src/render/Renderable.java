/*
 * Everything to be on screen must implement this for code readability
 */

package render;

import java.awt.Graphics2D;

public interface Renderable {

	public void render(Graphics2D g);
	public boolean isVisible();
	public int getZ();

}
