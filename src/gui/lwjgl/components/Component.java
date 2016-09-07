package gui.lwjgl.components;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Font;
import java.util.ArrayList;

import org.lwjgl.opengl.Display;

import gui.lwjgl.listener.ClickEventListener;
import gui.lwjgl.style.StyleTemplate;
import gui.lwjgl.util.LWJGLFont;
import gui.lwjgl.util.Texture;

/**
 * This is the base class for all components<br>
 * <br>
 * A component's described by two things:<br>
 * 1. The component's center<br>
 * 2. The component's size<br>
 * <br>
 * Every component has a list of groups by which their design can be modified through CSS<br>
 * Browse a components javadoc in order to find out which groups are assigned by default<br>
 * @see #getGroups()
 * @see #addToGroup(String)
 * @see #removeFromGroup(String)
 * 
 * @author Florian
 *
 */
public abstract class Component {
	/**
	 * A components default font
	 */
	public static final LWJGLFont FONT_STANDARD;
	
	static{
		FONT_STANDARD = new LWJGLFont(new Font("Arial", Font.PLAIN, 22));
	}
	
	private ArrayList<String> groups;
	
	protected Component parent;
	
	protected float background_r, background_g, background_b, background_a;
	protected float foreground_r, foreground_g, foreground_b, foreground_a;
	protected float border_r, border_g, border_b, border_a;
	protected float text_r, text_g, text_b, text_a;
	
	protected Texture texture;

	protected int centerX;
	protected int centerY;
	protected int sizeX;
	protected int sizeY;

	private ArrayList<ClickEventListener> listener;
	protected boolean editable = true;
	private boolean focused;
	protected boolean drawBorder = true;
	
	private boolean isPressed;

	protected LWJGLFont font = FONT_STANDARD;

	/**
	 * Constructor
	 * 
	 * @param centerX
	 * @param centerY
	 * @param sizeX
	 * @param sizeY
	 */
	public Component(int centerX, int centerY, int sizeX, int sizeY) {
		this.centerX = centerX;
		this.centerY = centerY;
		this.sizeX = sizeX;
		this.sizeY = sizeY;

		listener = new ArrayList<ClickEventListener>();
		groups = new ArrayList<String>();
		
		groups.add("all");
	}
	
	/**
	 * Adds this component to a specific group, which can be layouted by CSS-files
	 * @param group
	 */
	public void addToGroup(String group) {
		groups.add(group);
	}

	/**
	 * Removes this component to a specific group
	 * @param group
	 */
	public void removeFromGroup(String group) {
		groups.remove(group);
	}

	/**
	 * Checks if this component is part of a specific group
	 * @param group
	 */
	public boolean hasGroup(String s) {
		return groups.contains(s);
	}
	
	/**
	 * Returns all assigned groups
	 * @return all assigned groups
	 */
	public String[] getGroups() {
		return groups.toArray(new String[groups.size()]);
	}

	/**
	 * Moves the component's center
	 * @param centerX
	 * @param centerY
	 */
	public void moveTo(int centerX, int centerY) {
		this.centerX = centerX;
		this.centerY = centerY;
	}
	
	/**
	 * Set's the component's size
	 * @param sizeX
	 * @param sizeY
	 */
	public void resize(int sizeX, int sizeY) {
		this.sizeX = sizeX;
		this.sizeY = sizeY;
	}

	/**
	 * paints the component<br>
	 * needs to be implemented by all subclasses
	 * 
	 * @param delta time in seconds after last update
	 */
	public abstract void paint(float delta);

	/**
	 * Fires a ClickEvent
	 * @param x
	 * @param y
	 * @param type either {@link ClickEventListener#RELEASED} or {@link ClickEventListener#PRESSED}
	 */
	protected void fireEvent(int x, int y, int type) {
		for (int i = 0; i < listener.size(); i++) {
			listener.get(i).action(this, x, y, type);
		}
	}

	/**
	 * Adds a ClickEventListener to this component
	 * @param listener
	 */
	public void addClickEventListener(ClickEventListener listener) {
		this.listener.add(listener);
	}

	/**
	 * Sets this component editable<br>
	 * A value of false disables interaction with buttons, textfields, etc.
	 * @param editable
	 */
	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	/**
	 * Checks if a component is editable
	 * @return true if the component is editable, false otherwise
	 */
	public boolean isEditable() {
		return editable;
	}

	/**
	 * Called when a component looses it's focus
	 */
	public final void lostFocus() {
		this.focused = false;
	}

	/**
	 * Called when a component gains focus
	 */
	public final void gainFocus() {
		this.focused = true;
	}

	/**
	 * Checks if the component is focused
	 * @return true if the component is focused, false otherwise
	 */
	public final boolean isFocused() {
		return focused;
	}

	/**
	 * Checks if a given location is inside the component<br>
	 * <b>Note:</b> (0|0) is in the bottom left corner, therefore mouse coordinates work fine
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isInComponent(int x, int y) {
		return centerX - sizeX / 2 < x && centerX + sizeX / 2 > x && Display.getHeight() - (centerY + sizeY / 2) < y
				&& Display.getHeight() - (centerY - sizeY / 2) > y;
	}

	/**
	 * Called when the {@link GUI#open(DialogManager)} is called
	 * @param gui
	 */
	protected void onDisplay(GUI gui) {
	}

	/**
	 * Returns the components font
	 * @return the components font
	 */
	public LWJGLFont getFont() {
		return font;
	}

	/**
	 * Sets the components font
	 * @param font
	 */
	public void setFont(LWJGLFont font) {
		this.font = font;
	}
	
	/**
	 * Sets the components texture<br>
	 * <b>Note:</b> This can also be done via CSS
	 * @param texture
	 */
	public void setTexture(Texture texture) {
		this.texture = texture;
	}
	
	/**
	 * Used by subclasses to bind this component's texture (if it has one)
	 */
	protected void bindTexture() {
		if(texture != null)
			texture.bind();
	}
	
	/**
	 * Sets the components background color<br>
	 * <b>Note:</b> This can also be done via CSS
	 * @param r
	 * @param g
	 * @param b
	 * @param a
	 */
	public void setBackgroundColor(float r, float g, float b, float a) {
			this.background_r = r;
			this.background_g = g;
			this.background_b = b;
			this.background_a = a;
	}
	
	/**
	 * Sets the components text color<br>
	 * <b>Note:</b> This can also be done via CSS
	 * @param r
	 * @param g
	 * @param b
	 * @param a
	 */
	public void setTextColor(float r, float g, float b, float a) {
			this.text_r = r;
			this.text_g = g;
			this.text_b = b;
			this.text_a = a;
	}
	
	/**
	 * Sets the components foreground color<br>
	 * <b>Note:</b> This can also be done via CSS
	 * @param r
	 * @param g
	 * @param b
	 * @param a
	 */
	public void setForegroundColor(float r, float g, float b, float a) {
			this.foreground_r = r;
			this.foreground_g = g;
			this.foreground_b = b;
			this.foreground_a = a;
	}
	
	/**
	 * Sets the components border color<br>
	 * <b>Note:</b> This can also be done via CSS
	 * @param r
	 * @param g
	 * @param b
	 * @param a
	 */
	public void setBorderColor(float r, float g, float b, float a) {
			this.border_r = r;
			this.border_g = g;
			this.border_b = b;
			this.border_a = a;
	}

	/**
	 * This method gets called when a key is released
	 * @param eventKey
	 * @param character
	 * @return true when event was consumed, false if not
	 */
	public boolean keyUp(GUI gui, int eventKey, char character) {
		return false;
	}

	/**
	 * This method gets called when a key is pressed
	 * @param eventKey
	 * @param character
	 * @return true when event was consumed, false if not
	 */
	public boolean keyDown(GUI gui, int eventKey, char character) {
		return false;
	}

	/**
	 * This method gets called when a mouse button is pressed
	 * @param eventKey
	 * @param character
	 * @return true when event was consumed, false if not
	 */
	public boolean mouseDown(GUI gui, int button, int x, int y) {
		if (!isEditable()) {
			return false;
		}
		
		if(button == 0 && !isPressed && isInComponent(x, y)) {
			fireEvent(x, y, ClickEventListener.PRESSED);
			isPressed = true;
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
	public boolean mouseUp(GUI gui, int button, int x, int y) {
		if (!isEditable()) {
			return false;
		}
		
		if(button == 0 && isPressed && isInComponent(x, y)) {
			isPressed = false;
			fireEvent(x, y, ClickEventListener.RELEASED);
			return true;
		} else if(button == 0 && isPressed) {
			isPressed = false;
		}
		
		return false;
	}

	/**
	 * This method gets called when the mouse wheel has changed
	 * @param eventKey
	 * @param character
	 * @return true when event was consumed, false if not
	 */
	public boolean mouseWheelChanged(GUI gui, int mouseWheel, int x, int y) {
		return isInComponent(x, y);
	}

	/**
	 * This method gets called when the mouse is moved
	 * @param eventKey
	 * @param character
	 * @return true when event was consumed, false if not
	 */
	public boolean mouseMoved(GUI gui, int x, int y, int dX, int dY) {
		return isInComponent(x, y);
	}
	
	/**
	 * Sets if the border for this component should be drawn<br>
	 * <b>Note:</b> This can also be done via CSS
	 * @param drawBorder
	 */
	public void setDrawBorder(boolean drawBorder) {
		this.drawBorder = drawBorder;
	}
	
	/**
	 * Checks if the component's border should be drawn
	 * @return true if the border should be drawn, false otherwise
	 */
	public boolean isDrawBorder() {
		return drawBorder;
	}

	/**
	 * Draws the component's border (if it is enabled)
	 */
	public void paintBorder() {
		if(!drawBorder)
			return;
		
		glBegin(GL_LINES);
		glColor4f(border_r, border_g, border_b, border_a);
		glVertex2f(0, 0);
		glVertex2f(sizeX, 0);
		
		glVertex2f(sizeX, 0);
		glVertex2f(sizeX, sizeY);
		
		glVertex2f(sizeX, sizeY - 1);
		glVertex2f(0, sizeY - 1);
		
		glVertex2f(1, 0);
		glVertex2f(1, sizeY);
		glEnd();
	}

	/**
	 * Draws the given text centered to the given coordinates with the given color<br>
	 * This function draws as much text as possible onto the component
	 * @param font
	 * @param text
	 * @param centerX
	 * @param centerY
	 * @param r
	 * @param g
	 * @param b
	 * @param a
	 */
	protected void drawString(LWJGLFont font, String text, int centerX, int centerY, float r, float g, float b, float a) {
		String s = "";
		for(int i = 1; i <= text.length(); i++) {
			String old = s;
			s = text.substring(0, i);
			if(font.getWidth(s) >= sizeX) {
				s = old;
				break;
			}
		}
		
		font.drawString(s, centerX - font.getWidth(s) / 2, centerY - font.getHeight() / 2, r, g, b, a);
	}

	/**
	 * Sets the component's parent (may be null)
	 * @param parent
	 */
	public void setParent(Component parent) {
		this.parent = parent;
	}
	
	/**
	 * Returns the parent (may be null)
	 * @return the parent
	 */
	public Component getParent() {
		return parent;
	}

	/**
	 * Loads a given Template<br>
	 * Because of the visitor pattern, every component needs to implement this<br>
	 * <br>
	 * A default implementation would be:<br>
	 * <code><br>
	 * public void loadTemplate(StyleTemplate style){<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;style.load(this);<br>
	 * }<br>
	 * </code>
	 * @param style
	 */
	public abstract void loadTemplate(StyleTemplate style);
}
