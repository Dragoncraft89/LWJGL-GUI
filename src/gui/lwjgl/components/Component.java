package gui.lwjgl.components;

import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.awt.Font;
import java.util.ArrayList;

import org.lwjgl.opengl.Display;

import gui.lwjgl.listener.ClickEventListener;
import gui.lwjgl.style.StyleTemplate;
import gui.lwjgl.util.LWJGLFont;
import gui.lwjgl.util.Texture;

public abstract class Component {
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

	protected LWJGLFont font = FONT_STANDARD;

	public Component(int centerX, int centerY, int sizeX, int sizeY) {
		this.centerX = centerX;
		this.centerY = centerY;
		this.sizeX = sizeX;
		this.sizeY = sizeY;

		listener = new ArrayList<ClickEventListener>();
		groups = new ArrayList<String>();
		
		groups.add("all");
	}
	
	public void addToGroup(String group) {
		groups.add(group);
	}
	
	public void removeFromGroup(String group) {
		groups.remove(group);
	}

	public boolean hasGroup(String s) {
		return groups.contains(s);
	}
	
	public String[] getGroups() {
		return groups.toArray(new String[groups.size()]);
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

	public void addEventListener(ClickEventListener listener) {
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
	
	public void setTexture(Texture texture) {
		this.texture = texture;
	}
	
	protected void bindTexture() {
		if(texture != null)
			texture.bind();
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
			this.border_g = g;
		if(b != -1)
			this.border_b = b;
		if(a != -1)
			this.border_a = a;
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
		
		if(button == 0 && isInComponent(x, y)) {
			fireEvent(x, y, ClickEventListener.PRESSED);
			return true;
		}
		
		return false;
	}

	public boolean mouseUp(GUI gui, int button, int x, int y) {
		if (!isEditable()) {
			return false;
		}
		
		if(button == 0 && isInComponent(x, y)) {
			fireEvent(x, y, ClickEventListener.RELEASED);
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

	public abstract void loadTemplate(StyleTemplate style);
}
