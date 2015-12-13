import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

import player.GameLoop;
import render.RenderLoop;
import screen.GameWindow;
import screen.MainFrame;

public class Main {
	
	private static Runnable gameLoop, renderLoop;

	public static void main(String[] args) {
			
		MainFrame mainFrame = MainFrame.getFrame();
		try {
			SwingUtilities.invokeAndWait(new Runnable() {

				@Override
				public void run() {
					MainFrame.getFrame().setVisible(true);
					MainFrame.getFrame().requestFocus();
				}
			});
		} catch (InterruptedException e) {
		} catch (InvocationTargetException e) {}
		
		synchronized (mainFrame) {
			try {
				mainFrame.wait();
			} catch (InterruptedException e) {}
		}
		
		GameWindow gameWindow = GameWindow.getWindow();
		mainFrame.setVisible(false);
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
