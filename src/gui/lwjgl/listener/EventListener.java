package gui.lwjgl.listener;

import gui.lwjgl.components.Component;

public interface EventListener {
	public static final int PRESSED = 1;
	public static final int RELEASED = 2;

	public void action(Component component, int x, int y, int type);
}
