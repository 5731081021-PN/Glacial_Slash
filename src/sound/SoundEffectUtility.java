package sound;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundEffectUtility {
	
	private SoundEffectUtility() {}

	public static void playSoundEffect(URL source) {

		try {
		AudioInputStream audioIn = AudioSystem.getAudioInputStream(source);
		AudioFormat format = audioIn.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, format);
			Clip soundClip = (Clip)AudioSystem.getLine(info);
			soundClip.open(audioIn);
			soundClip.start();
		} catch (UnsupportedAudioFileException e) {
		} catch (IOException e) {
		} catch (LineUnavailableException e) {}

	}

}
