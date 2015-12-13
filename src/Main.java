import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

import player.GameLoop;
import render.RenderLoop;
import screen.MainFrame;

public class Main {
	
	private static Runnable gameLoop, renderLoop;

	public static void main(String[] args) {
	
		gameLoop = new GameLoop();
		renderLoop = new RenderLoop();

		try {
			SwingUtilities.invokeAndWait(new Runnable() {

				@Override
				public void run() {
					MainFrame.getFrame();
				}
			});
		} catch (InterruptedException e) {
		} catch (InvocationTargetException e) {}
	
		new Thread(gameLoop).start();
		new Thread(renderLoop).start();

		try {
			synchronized (gameLoop) {
				gameLoop.wait();
			}
		} catch (InterruptedException e) {}
		System.exit(0);
	}

}
