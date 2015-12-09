/*
 * Keyboard input checker
 */

package input;

import java.awt.event.KeyEvent;
import java.util.EnumMap;
import java.util.Map;

public class InputUtility {

//	private static boolean[] keyPressed = new boolean[256], keyTriggered = new boolean[256];
	private static Map<CommandKey, Boolean> keyPressed = new EnumMap<CommandKey, Boolean>(CommandKey.class);
	private static Map<CommandKey, Boolean> keyTriggered = new EnumMap<CommandKey, Boolean>(CommandKey.class);
	
	public static boolean getKeyPressed(CommandKey key) {
		try {
			return keyPressed.get(key);
		} catch (NullPointerException e) {
			keyPressed.put(key, false);
			return false;
		}
	}
		
	public static boolean getKeyTriggered(CommandKey key) {
		try {
			return keyTriggered.get(key);
		} catch (NullPointerException e) {
			keyTriggered.put(key, false);
			return false;
		}
	}

	public static void setKeyPressed(CommandKey key, boolean pressed) {
		keyPressed.put(key, pressed);
	}
	
	public static void setKeyTriggered(CommandKey key, boolean triggered) {
		keyTriggered.put(key, triggered);
	}

	public static enum CommandKey {
		// Direction keys
		UP (KeyEvent.VK_UP),
		LEFT (KeyEvent.VK_LEFT),
		DOWN (KeyEvent.VK_DOWN),
		RIGHT (KeyEvent.VK_RIGHT),

		// Action keys
		SLASH (KeyEvent.VK_F),
		HAND (KeyEvent.VK_D),
		JUMP (KeyEvent.VK_SPACE);
		
		private int key;
		
		private CommandKey(int key) {
			this.key = key;
		}
		
		public int getKey() {
			return key;
		}
	}

}
