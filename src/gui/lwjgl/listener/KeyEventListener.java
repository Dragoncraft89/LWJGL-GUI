package gui.lwjgl.listener;

import gui.lwjgl.components.Component;

/**
 * A KeyEventListener is notified when a key was pressed or released in a TextField
 * @author Florian
 *
 */
public interface KeyEventListener {
	public static final int PRESSED = 0;
	public static final int RELEASED = 1;

	/**
	 * Gets called when a KeyEvent is triggered
	 * @param component
	 * @param x
	 * @param y
	 * @param type
	 */
	public void action(Component c, int eventKey, char key, int action);
}
