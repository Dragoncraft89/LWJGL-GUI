package gui.lwjgl.dialogs;

import gui.lwjgl.components.GUI;

public class Dialog extends GUI {
	private DialogManager handler;
	
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

	public boolean mouseWheelChanged(int dwheel, int x, int y) {
		if(super.mouseWheelChanged(dwheel, x, y)) {
			placeOnTop();
			return true;
		}
		
		return false;
	}

	public boolean mouseMoved(int x, int y, int dX, int dY) {
		if(super.mouseMoved(x, y, dX, dY)) {
			placeOnTop();
			return true;
		}
		
		return false;
	}
}
