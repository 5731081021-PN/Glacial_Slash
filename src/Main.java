import javax.swing.JFrame;

import player.PlayerCharacterRunnable;
import render.MainFrame;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame mainFrame = new MainFrame();
		mainFrame.setVisible(true);
		Runnable gameLoop = new PlayerCharacterRunnable();
		new Thread(gameLoop).start();
		try {
			synchronized (gameLoop) {
				gameLoop.wait();
			}
		} catch (InterruptedException e) {}
		mainFrame.dispose();
	}

}
