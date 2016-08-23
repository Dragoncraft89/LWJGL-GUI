package gui.lwjgl.listener;

import gui.lwjgl.components.Component;

/**
 * A ClickEventListener gets notified when a component has been clicked<br>
 * A ClickEventListener can be added to any component
 * @author Florian
 *
 */
public interface ClickEventListener {
	/**
	 * Indicates that the left mouse button has been pressed over the component
	 */
	public static final int PRESSED = 1;
	/**
	 * Indicates that the left mouse button has been pressed and released over the component
	 */
	public static final int RELEASED = 2;

	/**
	 * Gets called when a ClickEvent is triggered
	 * @param component
	 * @param x
	 * @param y
	 * @param type
	 */
	public void action(Component component, int x, int y, int type);
}
