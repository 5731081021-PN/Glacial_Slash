package render;

import player.PlayerCharacter;
import player.PlayerStatus;
import ui.GameScreen;

public class RenderLoop implements Runnable {

	@Override
	public void run() {
		
		final long FRAME_RATE = 30;
		final long RENDER_DELAY = 1000000000 / FRAME_RATE;

		long lastRenderTime = System.nanoTime();
		PlayerCharacter player = PlayerStatus.getPlayer().getPlayerCharacter();

		while (true) {
			
			long now = System.nanoTime();
		
			if (now - lastRenderTime >= RENDER_DELAY) {
				lastRenderTime = now;
				GameScreen.getScreen().centerCameraAt(player.getCenterX(), player.getCenterY());
				GameScreen.getScreen().repaint();

				synchronized (player) {
					player.notifyAll();
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
