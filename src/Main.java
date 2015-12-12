import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import player.GameLoop;
import render.RenderLoop;
import screen.MainFrame;

public class Main {
	
	private static Runnable gameLoop = new GameLoop(), renderLoop = new RenderLoop();
	private static JFrame mainFrame;

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				mainFrame = MainFrame.getFrame();
			}
		});

		new Thread(gameLoop).start();
		new Thread(renderLoop).start();

		try {
			synchronized (gameLoop) {
				gameLoop.wait();
			}
		} catch (InterruptedException e) {}
		mainFrame.dispose();
	}

}
