package gui.lwjgl.components;

import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.awt.Font;
import java.util.ArrayList;

import org.lwjgl.opengl.Display;

import gui.lwjgl.listener.EventListener;
import gui.lwjgl.util.LWJGLFont;

public abstract class Component {
	public static final LWJGLFont FONT_STANDARD;
	public static final LWJGLFont FONT_SMALL;
	
	static{
		LWJGLFont standard = new LWJGLFont(new Font("Arial", Font.PLAIN, 22));
		LWJGLFont small = new LWJGLFont(new Font("Arial", Font.PLAIN, 16));
		FONT_STANDARD = standard;
		FONT_SMALL = small;
	}
	
	protected Component parent;
	
	protected float background_r = 0.25f, background_g = 0.25f, background_b = 0.25f, background_a = 1;
	protected float foreground_r = 0.5f, foreground_g = 0.5f, foreground_b = 0.5f, foreground_a = 1;
	protected float border_r = 0f, border_g = 0f, border_b = 0f, border_a = 1;
	protected float text_r = 0, text_g = 0, text_b = 0, text_a = 1;

	protected int centerX;
	protected int centerY;
	protected int sizeX;
	protected int sizeY;

	private ArrayList<EventListener> listener;
	protected boolean editable = true;
	private boolean focused;
	protected boolean drawBorder = true;

	protected LWJGLFont font = FONT_STANDARD;
	protected LWJGLFont fontSmall = FONT_SMALL;

	public Component(int centerX, int centerY, int sizeX, int sizeY) {
		this.centerX = centerX;
		this.centerY = centerY;
		this.sizeX = sizeX;
		this.sizeY = sizeY;

		listener = new ArrayList<EventListener>();
	}

	public void moveTo(int centerX, int centerY) {
		this.centerX = centerX;
		this.centerY = centerY;
	}
	
	public void resize(int sizeX, int sizeY) {
		this.sizeX = sizeX;
		this.sizeY = sizeY;
	}

	public abstract void paint(float delta);

	protected void fireEvent(int x, int y, int type) {
		for (int i = 0; i < listener.size(); i++) {
			listener.get(i).action(this, x, y, type);
		}
	}

	public void addEventListener(EventListener listener) {
		this.listener.add(listener);
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public boolean isEditable() {
		return editable;
	}

	public final void lostFocus() {
		this.focused = false;
	}

	public final void gainFocus() {
		this.focused = true;
	}

	public final boolean isFocused() {
		return focused;
	}

	public boolean isInComponent(int x, int y) {
		return centerX - sizeX / 2 < x && centerX + sizeX / 2 > x && Display.getHeight() - (centerY + sizeY / 2) < y
				&& Display.getHeight() - (centerY - sizeY / 2) > y;
	}

	protected void onDisplay(GUI gui) {
	}

	public LWJGLFont getFont() {
		return font;
	}

	public void setFont(LWJGLFont font) {
		this.font = font;
	}
	
	public void setBackgroundColor(float r, float g, float b, float a) {
		if(r != -1)
			this.background_r = r;
		if(g != -1)
			this.background_g = g;
		if(b != -1)
			this.background_b = b;
		if(a != -1)
			this.background_a = a;
	}
	
	public void setTextColor(float r, float g, float b, float a) {
		if(r != -1)
			this.text_r = r;
		if(g != -1)
			this.text_g = g;
		if(b != -1)
			this.text_b = b;
		if(a != -1)
			this.text_a = a;
	}
	
	public void setForegroundColor(float r, float g, float b, float a) {
		if(r != -1)
			this.foreground_r = r;
		if(g != -1)
			this.foreground_g = g;
		if(b != -1)
			this.foreground_b = b;
		if(a != -1)
			this.foreground_a = a;
	}
	
	public void setBorderColor(float r, float g, float b, float a) {
		if(r != -1)
			this.border_r = r;
		if(g != -1)
			this.border_g = r;
		if(b != -1)
			this.border_b = r;
		if(a != -1)
			this.border_a = r;
	}

	public boolean keyUp(GUI gui, int eventKey, char character) {
		return false;
	}

	public boolean keyDown(GUI gui, int eventKey, char character) {
		return false;
	}

	public boolean mouseDown(GUI gui, int button, int x, int y) {
		if (!isEditable()) {
			return false;
		}
		
		if(isInComponent(x, y)) {
			fireEvent(x, y, EventListener.PRESSED);
			return true;
		}
		
		return false;
	}

	public boolean mouseUp(GUI gui, int button, int x, int y) {
		if (!isEditable()) {
			return false;
		}
		
		if(isInComponent(x, y)) {
			fireEvent(x, y, EventListener.RELEASED);
			return true;
		}
		
		return false;
	}

	public boolean mouseWheelChanged(GUI gui, int mouseWheel, int x, int y) {
		return isInComponent(x, y);
	}

	public boolean mouseMoved(GUI gui, int x, int y, int dX, int dY) {
		return isInComponent(x, y);
	}
	
	public void setDrawBorder(boolean drawBorder) {
		this.drawBorder = drawBorder;
	}
	
	public boolean isDrawBorder() {
		return drawBorder;
	}

	public void paintBorder() {
		if(!drawBorder)
			return;
		
		glBegin(GL_LINES);
		glColor4f(border_r, border_g, border_b, 1);
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

	public void drawString(LWJGLFont font, String text, int centerX, int centerY, float r, float g, float b, float a) {
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

	public void setParent(Component parent) {
		this.parent = parent;
	}
	
	public Component getParent() {
		return parent;
	}
}
