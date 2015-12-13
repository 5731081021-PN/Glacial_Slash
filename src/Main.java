import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

import player.GameLoop;
import render.RenderLoop;
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
					TitleWindow.getWindow().setVisible(true);
					TitleWindow.getWindow().requestFocus();
				}
			});
		} catch (InterruptedException e) {
		} catch (InvocationTargetException e) {}
		
		synchronized (titleWindow) {
			try {
				titleWindow.wait();
			} catch (InterruptedException e) {}
		}
		
		GameWindow gameWindow = GameWindow.getWindow();
		titleWindow.dispose();
		gameWindow.setVisible(true);
		gameWindow.requestFocus();
		
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
