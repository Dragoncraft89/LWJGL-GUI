package gui.lwjgl.components;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;

import gui.lwjgl.dialogs.DialogManager;
import gui.lwjgl.style.StyleManager;
import gui.lwjgl.style.StyleTemplate;

/**
 * This is the main class for the gui.<br>
 * This class handles all components which are added via {@link #addComponent(Component)}.<br>
 * 
 * @author Florian
 *
 */
public class GUI {

	protected long lastFrame;
	
	protected Component focused;

	protected ArrayList<Component> components = new ArrayList<Component>();

	/**
	 * Constructs a new GUI
	 */
	public GUI() {
	}
	
	/**
	 * loads a StyleTemplate<br>
	 * This method is called during {@link #open(DialogManager)}<br>
	 * <br>
	 * It is not recommended to call this method by yourself (use {@link StyleManager#pushTemplate(StyleTemplate)})
	 * @param template
	 */
	public void loadTemplate(StyleTemplate template) {
		for (Component c : components) {
			c.loadTemplate(template);
		}
	}

	/**
	 * This method is called when this overlay/dialog is opened by the DialogManager<br>
	 * 
	 * @see DialogManager#setOverlay(GUI)
	 * @see DialogManager#openDialog(Dialog)
	 * @param manager
	 */
	public void open(DialogManager manager) {
		loadTemplate(StyleManager.getTemplate());
		
		for (Component c : components) {
			c.onDisplay(this);
		}
		
		lastFrame = System.currentTimeMillis();
	}

	/**
	 * Paints all components<br>
	 * <br>
	 * This will be called by the DialogManager
	 * 
	 * @see DialogManager#paint()
	 */
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
		glColor3f(1, 1, 1);
	}

	/**
	 * Transfers the focus to a component<br>
	 * <br>
	 * inner logic only
	 * @param c
	 */
	public void requestFocus(Component c) {
		if (focused != null)
			focused.lostFocus();
		focused = c;
		if (focused != null)
			focused.gainFocus();
	}

	/**
	 * Adds a component to this gui
	 * 
	 * @see #removeComponent(Component)
	 * @param c
	 */
	public void addComponent(Component c) {
		c.setParent(null);
		components.add(c);
	}

	/**
	 * Removes a component from this gui
	 * 
	 * @see #addComponent(Component)
	 * @param c
	 */
	public void removeComponent(Component c) {
		c.setParent(null);
		components.remove(c);
	}
	
	/**
	 * Removes all components from this gui
	 */
	public void clearComponents() {
		components.clear();
	}

	/**
	 * This method gets called when a key is released
	 * @param eventKey
	 * @param character
	 * @return true when event was consumed, false if not
	 */
	public boolean keyUp(int eventKey, char character) {
		for (int i = 0; i < components.size(); i++) {
			if (components.get(i).keyUp(this, eventKey, character))
				return true;
		}
		return false;
	}

	/**
	 * This method gets called when a key is pressed
	 * @param eventKey
	 * @param character
	 * @return true when event was consumed, false if not
	 */
	public boolean keyDown(int eventKey, char character) {
		for (int i = 0; i < components.size(); i++) {
			if (components.get(i).keyDown(this, eventKey, character))
				return true;
		}
		return false;
	}

	/**
	 * This method gets called when a mouse button is pressed
	 * @param eventKey
	 * @param character
	 * @return true when event was consumed, false if not
	 */
	public boolean mouseDown(int button, int x, int y) {
		for (int i = 0; i < components.size(); i++) {
			if (components.get(i).mouseDown(this, button, x, y))
				return true;
		}
		return false;
	}

	/**
	 * This method gets called when a mouse button is released
	 * @param eventKey
	 * @param character
	 * @return true when event was consumed, false if not
	 */
	public boolean mouseUp(int button, int x, int y) {
		for (int i = 0; i < components.size(); i++) {
			if (components.get(i).mouseUp(this, button, x, y))
				return true;
		}
		return false;
	}

	/**
	 * This method gets called when the mouse wheel has changed
	 * @param eventKey
	 * @param character
	 * @return true when event was consumed, false if not
	 */
	public boolean mouseWheelChanged(int dwheel, int x, int y) {
		for (int i = 0; i < components.size(); i++) {
			if (components.get(i).mouseWheelChanged(this, dwheel, x, y))
				return true;
		}
		return false;
	}

	/**
	 * This method gets called when the mouse is moved
	 * @param eventKey
	 * @param character
	 * @return true when event was consumed, false if not
	 */
	public boolean mouseMoved(int x, int y, int dX, int dY) {
		for (int i = 0; i < components.size(); i++) {
			if (components.get(i).mouseMoved(this, x, y, dX, dY))
				return true;
		}
		return false;
	}

}
