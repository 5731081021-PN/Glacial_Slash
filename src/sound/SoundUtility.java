package sound;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;

public class SoundUtility {
	
	private SoundUtility() {}

	public static void playSoundEffect(byte[] source, AudioFormat format) {
		try {
			DataLine.Info info = new DataLine.Info(Clip.class, format, source.length);
			Clip soundClip = (Clip)AudioSystem.getLine(info);
			soundClip.open(format, source, 0, source.length);
			soundClip.start();
		} catch (LineUnavailableException e) {}
	}

}
