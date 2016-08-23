package gui.lwjgl.listener;

import gui.lwjgl.components.ListElement;
import gui.lwjgl.components.Slider;


/**
 * A SelectionListener is notified when a ListElement has been selected by the user
 * 
 * @see Slider
 * @author Florian
 *
 */
public interface SelectionListener {
	
	/**
	 * Gets called when a SelectionEvent is triggered
	 * @param component
	 * @param x
	 * @param y
	 * @param type
	 */
	public abstract void onSelect(ListElement element, int index);
}
