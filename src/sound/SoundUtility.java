package sound;

import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;

public class SoundUtility {
	
	private SoundUtility() {}

	public static void playSoundEffect(byte[] source, AudioFormat format) {
		try {
			Clip soundClip = AudioSystem.getClip();
			soundClip.open(format, source, 0, source.length);
			soundClip.start();
			soundClip.addLineListener(new LineListener() {
				
				@Override
				public void update(LineEvent event) {
					
				}
			});
		} catch (Exception e) {}
	}

}
