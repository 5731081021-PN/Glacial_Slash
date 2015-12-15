import java.lang.reflect.InvocationTargetException;

import javax.sound.sampled.Clip;
import javax.swing.SwingUtilities;

import player.GameLoop;
import render.RenderLoop;
import res.Resource;
import ui.GameWindow;
import ui.TitleWindow;

public class Main {
	
	private static Runnable gameLoop, renderLoop;

	public static void main(String[] args) {
		
		TitleWindow titleWindow = TitleWindow.getWindow();
		try {
			SwingUtilities.invokeAndWait(new Runnable() {

				@Override
				public void run() {
					titleWindow.setLocationRelativeTo(null);
					titleWindow.setVisible(true);
					titleWindow.requestFocus();
					Resource.titleBGM.loop(Clip.LOOP_CONTINUOUSLY);
				}
			});
		} catch (InterruptedException e) {
		} catch (InvocationTargetException e) {}
		
		synchronized (titleWindow) {
			try {
				titleWindow.wait();
			} catch (InterruptedException e) {}
		}
		
		try {
			SwingUtilities.invokeAndWait(new Runnable() {

				@Override
				public void run() {
					GameWindow gameWindow = GameWindow.getWindow();
					titleWindow.dispose();
					Resource.titleBGM.stop();
					gameWindow.setLocationRelativeTo(null);
					gameWindow.setVisible(true);
					gameWindow.requestFocus();
					Resource.stageBGM.loop(Clip.LOOP_CONTINUOUSLY);
				}
			});
		} catch (InterruptedException e) {
		} catch (InvocationTargetException e) {}
		
		gameLoop = new GameLoop();
		renderLoop = new RenderLoop();

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
