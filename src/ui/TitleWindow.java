package ui;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

import res.Resource;

public class TitleWindow extends JFrame {

	private static final long serialVersionUID = 6167186886002864573L;
	
	public static TitleWindow window;
	private Clip bgmClip;
	
	public static TitleWindow getWindow() {
		if (window == null)
			window = new TitleWindow();
		return window;
	}

	private TitleWindow() {
		super("Glacial Slash");
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.add(TitleScreen.getScreen());
		this.setResizable(false);
		this.pack();

	}
		
	public void startBGM() {
		try {
			bgmClip = AudioSystem.getClip();
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(Resource.titleBGM);
			bgmClip.open(audioIn);
			bgmClip.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (LineUnavailableException e) {
			JOptionPane.showMessageDialog(null, "BGM line unavailable", "Error", JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "File is not found", "Error", JOptionPane.ERROR_MESSAGE);
		} catch (UnsupportedAudioFileException e) {
			JOptionPane.showMessageDialog(null, "BGM file format incorrect", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void stopBGM() {
		bgmClip.stop();
	}

}
