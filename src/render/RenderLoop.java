package render;

import player.PlayerStatus;
import ui.GameScreen;

public class RenderLoop implements Runnable {

	@Override
	public void run() {
		
		final long FRAME_RATE = 60;
		final long RENDER_DELAY = 1000000000 / FRAME_RATE;

		long lastRenderTime = System.nanoTime();

		while (true) {
			
			long now = System.nanoTime();
		
			if (now - lastRenderTime >= RENDER_DELAY) {
				lastRenderTime = now;
				GameScreen.getScreen().centerCameraAt(PlayerStatus.getPlayer().getPlayerCharacter().getCenterX(), PlayerStatus.getPlayer().getPlayerCharacter().getCenterY());
				GameScreen.getScreen().repaint();

				synchronized (PlayerStatus.getPlayer().getPlayerCharacter()) {
					PlayerStatus.getPlayer().getPlayerCharacter().notifyAll();
				}

				synchronized (GameScreen.getScreen()) {
					GameScreen.getScreen().notifyAll();
					try {
						GameScreen.getScreen().wait();
					} catch (InterruptedException e) {}
				}
			}

		}

	}

}
