package gui.lwjgl.components;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;

import org.lwjgl.opengl.Display;

import gui.lwjgl.listener.SelectionListener;
import gui.lwjgl.style.StyleTemplate;
import gui.lwjgl.util.LWJGLFont;
import gui.lwjgl.util.Texture;

/**
 * This is a list<br>
 * <br>
 * default groups:<br>
 * all
 * lists
 * @author Florian
 *
 */
public class List extends Component {
	protected static final int SLIDER_WIDTH = 10;
	protected static final int PADDING_LEFT = 5;
	protected static final int MARGIN_BETWEEN_ELEMENTS = 2;

	protected ArrayList<SelectionListener> listeners = new ArrayList<SelectionListener>();

	protected int elementsVisible;
	protected ListElement[] elements;

	protected Panel panel;
	protected Slider slider;

	protected int selected;
	
	protected float list_r;
	protected float list_g;
	protected float list_b;
	protected float list_a;

	/**
	 * Constructor
	 * @param centerX
	 * @param centerY
	 * @param sizeX
	 * @param sizeY
	 */
	public List(int centerX, int centerY, int sizeX, int sizeY) {
		super(centerX - SLIDER_WIDTH / 2, centerY, sizeX - SLIDER_WIDTH, sizeY);
		this.elementsVisible = sizeY / font.getHeight();
		this.elements = new ListElement[0];
		this.panel = new Panel(centerX - SLIDER_WIDTH / 2, centerY, sizeX - SLIDER_WIDTH, sizeY);
		this.slider = new Slider(sizeX - SLIDER_WIDTH / 2, sizeY / 2, SLIDER_WIDTH, sizeY, false);
		panel.addComponent(slider);
		setElements(new ListElement[1]);
		
		panel.addToGroup("lists");
		slider.addToGroup("lists");
		addToGroup("lists");
	}
	
	public void setBackgroundColor(float r, float g, float b, float a) {
		panel.setBackgroundColor(r, g, b, a);
		
		super.setBackgroundColor(r, g, b, a);
	}
	
	public void setSliderBackgroundColor(float r, float g, float b, float a) {
		slider.setBackgroundColor(r, g, b, a);
	}
	
	public void setSliderForegroundColor(float r, float g, float b, float a) {
		slider.setForegroundColor(r, g, b, a);
	}
	
	/**
	 * Sets the color for selected list entries (CSS attribute: list-color)
	 * @param r
	 * @param g
	 * @param b
	 * @param a
	 */
	public void setListColor(float r, float g, float b, float a) {
		list_r = r;
		list_g = g;
		list_b = b;
		list_a = a;
	}

	/**
	 * Constructor
	 * @param centerX
	 * @param centerY
	 * @param sizeX
	 * @param sizeY
	 * @param elements
	 */
	public List(int centerX, int centerY, int sizeX, int sizeY, ListElement[] elements) {
		this(centerX, centerY, sizeX, sizeY);
		setElements(elements);
	}
	
	public void setFont(LWJGLFont font) {
		super.setFont(font);
		
		elementsVisible = sizeY / font.getHeight();
	}

	/**
	 * Sets the elements this list should display
	 * @param elements
	 */
	public void setElements(ListElement[] elements) {
		this.elements = elements;
		this.slider.setMaxValue(Math.max(0, elements.length - elementsVisible));
		this.selected = -1;
	}

	@Override
	public void paint(float delta) {
		panel.paint(delta);

		glPushMatrix();
		glTranslatef(centerX - sizeX / 2, centerY - sizeY / 2, 0);
		int heightPerElement = this.sizeY / elementsVisible;
		int offset = slider.getValue();
		int y = 0;
		for (int i = offset; i < offset + elementsVisible; i++) {
			if (i < elements.length && elements[i] != null) {
				paintElement(heightPerElement, offset, y, i);
			}
			y += heightPerElement;
		}
		
		super.paintBorder();
		glPopMatrix();
	}

	/**
	 * This method draws a element of this list
	 * @param heightPerElement
	 * @param offset
	 * @param y
	 * @param i
	 */
	protected void paintElement(int heightPerElement, int offset, int y, int i) {
		glPushMatrix();
		glTranslatef(0, y, 0);
		
		bindTexture();
		glBegin(GL_QUADS);
		if (i == selected)
			glColor4f(list_r, list_g, list_b, list_a);
		else
			glColor4f(background_r, background_g, background_b, background_a);
		glTexCoord2f(0, 0);
		glVertex2f(1, 1);
		glTexCoord2f(1, 0);
		glVertex2f(sizeX, 1);
		glTexCoord2f(1, 1);
		glVertex2f(sizeX, heightPerElement - 1);
		glTexCoord2f(0, 1);
		glVertex2f(1, heightPerElement - 1);
		glEnd();
		
		glBindTexture(GL_TEXTURE_2D, 0);

		Texture tex = elements[i].getIcon();
		if (tex != null) {
			tex.bind();
			int width = (int) (tex.getWidth() / (float) tex.getHeight() * heightPerElement);
			glBegin(GL_QUADS);
			glColor4f(1, 1, 1, 1);
			glTexCoord2f(0, 0);
			glVertex2f(0, 0);
			glTexCoord2f(1, 0);
			glVertex2f(width, 0);
			glTexCoord2f(1, 1);
			glVertex2f(width, heightPerElement);
			glTexCoord2f(0, 1);
			glVertex2f(0, heightPerElement);
			glEnd();
			glBindTexture(GL_TEXTURE_2D, 0);

			String name = elements[i].getName();
			drawString(width, font, name, width + PADDING_LEFT,
					heightPerElement / 2, text_r, text_g, text_b, text_a);
		} else {
			String name = elements[i].getName();
			drawString(font, name, PADDING_LEFT,
					heightPerElement / 2, text_r, text_g, text_b, text_a);
		}
		
		glPopMatrix();
	}
	
	@Override
	public boolean keyDown(GUI gui, int eventKey, char character) {
		return panel.keyDown(gui, eventKey, character) || super.keyDown(gui, eventKey, character);
	}

	@Override
	public boolean keyUp(GUI gui, int eventKey, char character) {
		return panel.keyUp(gui, eventKey, character) || super.keyUp(gui, eventKey, character);
	}

	@Override
	public boolean mouseDown(GUI gui, int button, int x, int y) {
		if (button == 0 && isInComponent(x, y)) {
			int oldSelect = this.selected;
			this.selected = (int) (slider.getValue()
					+ (Display.getHeight() - y - centerY + sizeY / 2) / (float) sizeY * elementsVisible);
			if (oldSelect != this.selected && selected >= 0 && selected < elements.length) {
				fireOnSelectEvent(elements[this.selected], this.selected);
				return true;
			}
		} else {
			panel.mouseDown(gui, button, x, y);
		}

		return false;
	}
	
	@Override
	public boolean mouseUp(GUI gui, int button, int x, int y) {
		return panel.mouseUp(gui, button, x, y) || super.mouseUp(gui, button, x, y);
	}
	
	@Override
	public boolean mouseWheelChanged(GUI gui, int mouseWheel, int x, int y) {
		if (isInComponent(x, y)) {
			slider.setValue(slider.getValue() - mouseWheel / 120);
			return true;
		}

		return super.mouseWheelChanged(gui, mouseWheel, x, y);
	}
	
	@Override
	public boolean mouseMoved(GUI gui, int x, int y, int dX, int dY) {
		return panel.mouseMoved(gui, x, y, dX, dY) || super.mouseMoved(gui, x, y, dX, dY);
	}

	/**
	 * Adds a SelectionListener to this list
	 * @see SelectionListener
	 * @param selectionListener
	 */
	public void addSelectionListener(SelectionListener selectionListener) {
		this.listeners.add(selectionListener);
	}

	/**
	 * fires a selection event
	 * @param element
	 * @param index
	 */
	protected void fireOnSelectEvent(ListElement element, int index) {
		for (int i = 0; i < listeners.size(); i++) {
			listeners.get(i).onSelect(element, index);
		}
	}

	/**
	 * Returns the list's selected Element
	 * @return the selected index
	 */
	public ListElement getSelected() {
		if (selected >= 0 && selected < elements.length) {
			return elements[selected];
		}
		return null;
	}

	/**
	 * Returns the list's selected index
	 * @return the selected index
	 */
	public int getSelectedIndex() {
		if (selected >= 0 && selected < elements.length) {
			return selected;
		}
		return -1;
	}

	/**
	 * Sets the list's selected index
	 * @param selected
	 */
	public void setSelected(int selected) {
		this.selected = Math.min(selected, elements.length);
	}
	
	public void drawString(LWJGLFont font, String text, int leftX, int leftY, float r, float g, float b, float a) {
		drawString(0, font, text, leftX, leftY, r, g, b, a);
	}
	

	/**
	 * Draws the given text centered to the given coordinates with the given color<br>
	 * This function draws as much text as possible onto the component
	 * This function takes into account the ListElements texture width
	 * @param texWidth
	 * @param font
	 * @param text
	 * @param centerX
	 * @param centerY
	 * @param r
	 * @param g
	 * @param b
	 * @param a
	 */
	public void drawString(int texWidth, LWJGLFont font, String text, int leftX, int leftY, float r, float g, float b, float a) {
		String s = "";
		for(int i = 1; i <= text.length(); i++) {
			String old = s;
			s = text.substring(0, i);
			if(font.getWidth(s) >= sizeX - 10 - texWidth) {
				s = old;
				break;
			}
		}

		font.drawString(s, leftX, leftY - font.getHeight() / 2, r, g, b, a);
	}

	@Override
	public void loadTemplate(StyleTemplate style) {
		style.load(this);
		
		slider.loadTemplate(style);
	}
}
