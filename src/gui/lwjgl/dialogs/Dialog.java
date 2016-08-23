package gui.lwjgl.dialogs;

import gui.lwjgl.components.GUI;

/**
 * The main class for creating dialogs<br>
 * most methods are inherited from GUI.<br>
 * It's just a bit inner logic to make dialogs work<br>
 * 
 * @see GUI
 * 
 * @author Florian
 *
 */
public class Dialog extends GUI {
	private DialogManager handler;
	
	/**
	 * Constructor
	 */
	public Dialog() {
		
	}
	
	public void open(DialogManager handler) {
		this.handler = handler;
		super.open(handler);
	}
	
	private void placeOnTop() {
		if(handler != null)
			handler.placeOnTop(this);
	}

	public boolean keyUp(int eventKey, char character) {
		if(super.keyUp(eventKey, character)) {
			placeOnTop();
			return true;
		}
		
		return false;
	}

	public boolean keyDown(int eventKey, char character) {
		if(super.keyDown(eventKey, character)) {
			placeOnTop();
			return true;
		}
		
		return false;
	}

	public boolean mouseDown(int button, int x, int y) {
		if(super.mouseDown(button, x, y)) {
			placeOnTop();
			return true;
		}
		
		return false;
	}

	public boolean mouseUp(int button, int x, int y) {
		if(super.mouseUp(button, x, y)) {
			placeOnTop();
			return true;
		}
		
		return false;
	}
}
