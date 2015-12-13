package screen;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import player.GameLoop;
import player.PlayerStatus;
import render.RenderLoop;
import res.Resource;

public class TitleScreen extends JComponent {

	private static final long serialVersionUID = 8480810473369561460L;
	
	private static TitleScreen screen;
	
	public static synchronized TitleScreen getScreen() {
		if (screen == null)
			screen = new TitleScreen();
		return screen;
	}
	
	private TitleScreen() {
		// Placeholder
		JButton newGameButton = new JButton(new ImageIcon(Resource.startButton));
		JButton loadGameButton = new JButton(new ImageIcon(Resource.loadButton));
		JButton exitButton = new JButton(new ImageIcon(Resource.exitButton));
		makeTransparent(newGameButton);
		makeTransparent(loadGameButton);
		makeTransparent(exitButton);
		
		this.setPreferredSize(new Dimension(GameScreen.SCREEN_WIDTH, GameScreen.SCREEN_HEIGHT));
		// TODO Add ActionListener to buttons
		// Decided to put JFileChoosers here
		newGameButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
					PlayerStatus.newPlayer(fileChooser.getSelectedFile().getPath());
					synchronized (MainFrame.getFrame()) {
						MainFrame.getFrame().notifyAll();
					}
				}
			}
		});
		loadGameButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		exitButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		this.setLayout(new BorderLayout());
		JPanel outerButtonPanel = new JPanel(), buttonPanel = new JPanel();
		buttonPanel.setLayout(new BorderLayout());
		buttonPanel.add(newGameButton, BorderLayout.WEST);
		buttonPanel.add(loadGameButton, BorderLayout.CENTER);
		buttonPanel.add(exitButton, BorderLayout.EAST);
		buttonPanel.add(Box.createRigidArea(new Dimension(0, 100)), BorderLayout.SOUTH);
		buttonPanel.setOpaque(false);
		
		outerButtonPanel.setLayout(new BorderLayout());
		outerButtonPanel.add(buttonPanel, BorderLayout.CENTER);
		outerButtonPanel.add(Box.createRigidArea(new Dimension(100, 0)), BorderLayout.WEST);
		outerButtonPanel.add(Box.createRigidArea(new Dimension(100, 0)), BorderLayout.EAST);
		outerButtonPanel.setOpaque(false);

		this.add(outerButtonPanel, BorderLayout.SOUTH);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.drawImage(Resource.title, null, 0, 0);
	}
	
	private void makeTransparent(JButton button) {
		button.setOpaque(false);
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
		button.setFocusPainted(false);
	}
	
	private void startGame() {
		MainFrame mainFrame = MainFrame.getFrame();
		GameWindow gameWindow = GameWindow.getWindow();
		GameLoop gameLoop = new GameLoop();
		RenderLoop renderLoop = new RenderLoop();

		mainFrame.setVisible(false);
		gameWindow.setVisible(true);
		gameWindow.requestFocus();

		new Thread(gameLoop).start();
		new Thread(renderLoop).start();
		synchronized (gameLoop) {
			try {
				gameLoop.wait();
			} catch (InterruptedException e) {}
		}

		mainFrame.setVisible(true);
		gameWindow.setVisible(false);
		mainFrame.requestFocus();

	}

}
