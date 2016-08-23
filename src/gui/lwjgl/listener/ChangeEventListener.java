package gui.lwjgl.listener;

import gui.lwjgl.components.Component;
import gui.lwjgl.components.Slider;

/**
 * A ChangEventListener is notified when a slider's value has been changed
 * 
 * @see Slider
 * @author Florian
 *
 */
public interface ChangeEventListener {

	/**
	 * Gets called when a ChangeEvent is triggered
	 * @param component
	 * @param x
	 * @param y
	 * @param type
	 */
	void action(Component c);

}
