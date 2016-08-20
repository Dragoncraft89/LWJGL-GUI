package gui.lwjgl.components;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;

public class GUI {

	private long lastFrame;
	
	private Component focused;

	ArrayList<Component> components = new ArrayList<Component>();

	public GUI() {
	}

	public void open() {
		for (Component c : components) {
			c.onDisplay(this);
		}
		
		lastFrame = System.currentTimeMillis();
	}

	public void paint() {
		float delta = (System.currentTimeMillis() - lastFrame) / 1000f;
		lastFrame = System.currentTimeMillis();
		
		glEnable(GL_TEXTURE_2D);
		glBindTexture(GL_TEXTURE_2D, 0);
		glDisable(GL_DEPTH_TEST);
		for (int i = 0; i < components.size(); i++) {
			components.get(i).paint(delta);
		}
		glEnable(GL_DEPTH_TEST);
	}

	public void requestFocus(Component c) {
		if (focused != null)
			focused.lostFocus();
		focused = c;
		if (focused != null)
			focused.gainFocus();
	}

	public void addComponent(Component c) {
		components.add(c);
	}

	public void removeComponent(Component c) {
		components.remove(c);
	}

	public boolean keyUp(int eventKey, char character) {
		for (int i = 0; i < components.size(); i++) {
			if (components.get(i).keyUp(this, eventKey, character))
				return true;
		}
		return false;
	}

	public boolean keyDown(int eventKey, char character) {
		for (int i = 0; i < components.size(); i++) {
			if (components.get(i).keyDown(this, eventKey, character))
				return true;
		}
		return false;
	}

	public boolean mouseDown(int button, int x, int y) {
		for (int i = 0; i < components.size(); i++) {
			if (components.get(i).mouseDown(this, button, x, y))
				return true;
		}
		return false;
	}

	public boolean mouseUp(int button, int x, int y) {
		for (int i = 0; i < components.size(); i++) {
			if (components.get(i).mouseUp(this, button, x, y))
				return true;
		}
		return false;
	}

	public boolean mouseWheelChanged(int dwheel, int x, int y) {
		for (int i = 0; i < components.size(); i++) {
			if (components.get(i).mouseWheelChanged(this, dwheel, x, y))
				return true;
		}
		return false;
	}

	public boolean mouseMoved(int x, int y, int dX, int dY) {
		for (int i = 0; i < components.size(); i++) {
			if (components.get(i).mouseMoved(this, x, y, dX, dY))
				return true;
		}
		return false;
	}

}