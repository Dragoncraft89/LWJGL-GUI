package gui.lwjgl.listener;

import gui.lwjgl.components.ListElement;

public interface SelectionListener {
	public abstract void onSelect(ListElement element, int index);
}
