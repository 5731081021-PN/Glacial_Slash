package ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import exception.UnableToLoadGameException;
import player.PlayerStatus;
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
		
		this.setPreferredSize(new Dimension(GameScreen.SCREEN_WIDTH, GameScreen.SCREEN_HEIGHT));

		this.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				Point pressed = e.getPoint();
				if (Double.compare(Math.hypot(pressed.getX() - 200, pressed.getY() - 520), 100) <= 0) {
					JOptionPane.showMessageDialog(null, "Choose save file location\nFile name will automatically be appended with \".gls\"", "New game", JOptionPane.PLAIN_MESSAGE);
					JFileChooser fileChooser = new JFileChooser();
					if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
						File saveFile = fileChooser.getSelectedFile();
						String filePath = saveFile.getPath();
						String extension = "";
						int lastDot = filePath.lastIndexOf(".");
						if (lastDot >= 0)
							extension = filePath.substring(lastDot+1);
						if (!"gls".equalsIgnoreCase(extension)) {
							saveFile = new File(saveFile.toString() + ".gls");
						}
						PlayerStatus.newPlayer(saveFile.getPath());
						synchronized (TitleWindow.getWindow()) {
							TitleWindow.getWindow().notifyAll();
						}
					}
				}
				else if (Double.compare(Math.hypot(pressed.getX() - 640, pressed.getY() - 520), 100) <= 0) {
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.setFileFilter(new FileNameExtensionFilter("Glacial Slash save", "gls"));
					if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
						try {
							PlayerStatus.loadPlayer(fileChooser.getSelectedFile().getPath());
						} catch (UnableToLoadGameException e1) {
							return;
						}
						synchronized (TitleWindow.getWindow()) {
							TitleWindow.getWindow().notifyAll();
						}
					}
				}
				else if (Double.compare(Math.hypot(pressed.getX() - 1080, pressed.getY() - 520), 100) <= 0) {
					System.exit(0);
				}
			}
		});

	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.drawImage(Resource.title, null, 0, 0);
		g2d.drawImage(Resource.startButton, null, 100, 420);
		g2d.drawImage(Resource.loadButton, null, 540, 420);
		g2d.drawImage(Resource.exitButton, null, 980, 420);
	}

}
