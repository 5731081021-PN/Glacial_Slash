/*
 * Keyboard input checker
 */

package input;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

import javax.swing.AbstractAction;

import render.GameScreen;

public class InputUtility {

//	private static boolean[] keyPressed = new boolean[256], keyTriggered = new boolean[256];
	private static Set<CommandKey> keyPressed = Collections.synchronizedSet(EnumSet.noneOf(CommandKey.class));
	private static Set<CommandKey> keyTriggered = Collections.synchronizedSet(EnumSet.noneOf(CommandKey.class));
	
	public static boolean getKeyPressed(CommandKey key) {
		return keyPressed.contains(key);
	}
		
	public static boolean getKeyTriggered(CommandKey key) {
		return keyTriggered.contains(key);
	}

	public static void setKeyPressed(CommandKey key, boolean pressed) {
		if (pressed)
			keyPressed.add(key);
		else
			keyPressed.remove(key);
	}
	
	public static void setKeyTriggered(CommandKey key, boolean triggered) {
		if (triggered)
			keyTriggered.add(key);
		else
			keyTriggered.remove(key);
	}
	
	public static void clearKeyTriggered() {
		keyTriggered.clear();
	}

	public static enum CommandKey {
		// Direction keys
		UP (KeyEvent.VK_UP, "UP"),
		LEFT (KeyEvent.VK_LEFT, "LEFT"),
		DOWN (KeyEvent.VK_DOWN, "DOWN"),
		RIGHT (KeyEvent.VK_RIGHT, "RIGHT"),

		// Action keys
		SLASH (KeyEvent.VK_F, "SLASH"),
		HAND (KeyEvent.VK_S, "HAND"),
		DASH (KeyEvent.VK_D, "DASH"),
		JUMP (KeyEvent.VK_SPACE, "JUMP");
		
		private int key;
		private String name;
		
		private CommandKey(int key, String name) {
			this.key = key;
			this.name = name;
		}
		
		public int getKey() {
			return key;
		}
		
		public String getName() {
			return name;
		}

	}

	public static class KeyPressedAction extends AbstractAction {

		private CommandKey key;
		
		public KeyPressedAction(CommandKey key) {
			this.key = key;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			synchronized (GameScreen.getScreen()) {
				if (!InputUtility.getKeyPressed(key)) {
					InputUtility.setKeyTriggered(key, true);
					InputUtility.setKeyPressed(key, true);
				}
			}
		}
		
	}

	public static class KeyReleasedAction extends AbstractAction {

		private CommandKey key;
		
		public KeyReleasedAction(CommandKey key) {
			this.key = key;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			synchronized (GameScreen.getScreen()) {
				InputUtility.setKeyPressed(key, false);
			}
		}
		
	}

}
