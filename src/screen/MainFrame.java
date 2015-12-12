package screen;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.Timer;

import input.InputUtility;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 6167186886002864573L;
	
	public static MainFrame frame;
	
	public static MainFrame getFrame() {
		if (frame == null)
			frame = new MainFrame();
		return frame;
	}

	private MainFrame() {
		super("Glacial Slash");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(GameScreen.getScreen());
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		
		new Timer(1, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!frame.isFocused()) {
					InputUtility.clearKeyPressed();
				}
			}
		}).start();
	}

}
