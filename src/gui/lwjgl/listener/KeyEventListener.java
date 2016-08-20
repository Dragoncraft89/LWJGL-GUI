package gui.lwjgl.listener;

import gui.lwjgl.components.Component;

public interface KeyEventListener {
	public static final int PRESSED = 0;
	public static final int RELEASED = 1;

	public void action(Component c, int eventKey, char key, int action);
}
