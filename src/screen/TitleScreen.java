package screen;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class TitleScreen extends JComponent {

	private static final long serialVersionUID = 8480810473369561460L;
	
	public TitleScreen() {
		// Placeholder
		JButton newGameButton = new JButton("New Game");
		JButton loadGameButton = new JButton("Load Game");
		JButton exitButton = new JButton("Exit");
		
		this.setPreferredSize(new Dimension(GameScreen.SCREEN_WIDTH, GameScreen.SCREEN_HEIGHT));
		// TODO Add ActionListener to buttons
		this.setLayout(new BorderLayout());
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		buttonPanel.add(newGameButton);
		buttonPanel.add(loadGameButton);
		buttonPanel.add(exitButton);
		
		this.add(buttonPanel, BorderLayout.SOUTH);
	}

}
