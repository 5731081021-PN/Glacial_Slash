package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.WindowConstants;

import input.InputUtility;

public class GameWindow extends JFrame {

	private static final long serialVersionUID = -1357957008248515440L;
	
	private static GameWindow window;
	
	public static GameWindow getWindow() {
		if (window == null)
			window = new GameWindow();
		return window;
	}

	private GameWindow() {
		super("Glacial Slash");
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.add(GameScreen.getScreen());
		this.setResizable(false);
		this.pack();
		
		new Timer(1, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!window.isFocused()) {
					InputUtility.clearKeyPressed();
					InputUtility.clearKeyTriggered();
				}
			}
		}).start();

	}

}
