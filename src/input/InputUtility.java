package input;

public class InputUtility {

	private boolean[] keyPressed = new boolean[256], keyTriggered = new boolean[256];
	
	public boolean getKeyPressed(int key) {
		if (key < 0 || key >= keyPressed.length)
			return false;
		return keyPressed[key];
	}
	
	public boolean getKeyTriggered(int key) {
		if (key < 0 || key >= keyTriggered.length)
			return false;
		return keyTriggered[key];
	}
	
	public void setKeyPressed(int key, boolean pressed) {
		if (key < 0 || key >= keyPressed.length)
			return;
		keyPressed[key] = pressed;
	}
	
	public void setKeyTriggered(int key, boolean triggered) {
		if (key < 0 || key >= keyTriggered.length)
			return;
		keyTriggered[key] = triggered;
	}

}
