package gui.lwjgl.components;

import static org.lwjgl.opengl.GL11.*;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import gui.lwjgl.listener.KeyEventListener;

public class TextField extends Component {
	private StringBuilder value = new StringBuilder();
	
	private ArrayList<KeyEventListener> listener = new ArrayList<KeyEventListener>();
	
	private int cursorPos;
	private int firstChar;
	
	private int padding = 3;
	private int cursorWidth = 2;
	
	private float timer;

	public TextField(int centerX, int centerY, int sizeX, int sizeY) {
		super(centerX, centerY, sizeX, sizeY);
	}

	@Override
	public void paint(float delta) {
		glPushMatrix();
		glTranslatef(centerX - sizeX / 2, centerY - sizeY / 2, 0);
		
		if(isFocused() && isEditable())
			timer += delta;
		
		glBegin(GL_QUADS);
		glColor4f(background_r, background_g, background_b, background_a);
		if (!isEditable())
			glColor4f(background_r * 0.5f, background_g * 0.5f, background_b * 0.5f, background_a);
		glVertex2f(0, 0);
		glVertex2f(sizeX, 0);
		glVertex2f(sizeX, sizeY);
		glVertex2f(0, sizeY);
		glEnd();
		
		String s = "";
		for(int i = 1; i <= value.length() - firstChar; i++) {
			String old = s;
			s = value.substring(firstChar, firstChar + i);
			if(font.getWidth(s) >= sizeX - padding) {
				s = old;
				break;
			}
		}

		super.paintBorder();
		
		font.drawString(s, padding, (sizeY - font.getHeight(s)) / 2);
		
		if(timer % 1.5 <= 0.75 && isFocused() && isEditable())
			drawCursor();
		
		glPopMatrix();
	}
	
	private void drawCursor() {
		int size = font.getWidth(value.substring(firstChar, cursorPos)) + padding;
		
		glBegin(GL_QUADS);
		glColor3f(1, 1, 1);
		glVertex2f(size - cursorWidth / 2f, 0);
		glVertex2f(size - cursorWidth / 2f, sizeY);
		glVertex2f(size + cursorWidth / 2f, sizeY);
		glVertex2f(size + cursorWidth / 2f, 0);
		glEnd();
	}

	public void addKeyEventListener(KeyEventListener listener) {
		this.listener.add(listener);
	}

	protected void fireKeyEvent(int eventKey, char c, int action) {
		for (int i = 0; i < listener.size(); i++) {
			listener.get(i).action(this, eventKey, c, action);
		}
	}

	public String getValue() {
		return value.toString();
	}
	
	public void setText(String value) {
		this.value = new StringBuilder(value);
		cursorPos = value.length();
	}
	
	private boolean isPrintable(char c) {
	    Character.UnicodeBlock block = Character.UnicodeBlock.of( c );
	    return (!Character.isISOControl(c)) &&
	            c != KeyEvent.CHAR_UNDEFINED &&
	            block != null &&
	            block != Character.UnicodeBlock.SPECIALS;
	}

	@Override
	public boolean keyUp(GUI gui, int eventKey, char character) {
		if (isFocused()) {
			fireKeyEvent(eventKey, character, KeyEventListener.RELEASED);
		}
		return false;
	}

	@Override
	public boolean keyDown(GUI gui, int eventKey, char character) {
		if (isFocused() && isEditable()) {
			if (eventKey == Keyboard.KEY_BACK) {
				if (cursorPos == 0)
					return false;
				value.deleteCharAt(cursorPos - 1);
				cursorPos--;
				timer = 0;
			} else if(eventKey == Keyboard.KEY_DELETE) {
				if(cursorPos == value.length())
					return false;
				value.deleteCharAt(cursorPos);
				timer = 0;
			}else if(isPrintable(character)){
				value.insert(cursorPos, character);
				cursorPos++;
				timer = 0;
			} else if(eventKey == Keyboard.KEY_LEFT) {
				cursorPos = Math.max(0, --cursorPos);
				timer = 0;
			} else if(eventKey == Keyboard.KEY_RIGHT) {
				cursorPos = Math.min(value.length(), ++cursorPos);
				timer = 0;
			}
			
			if(cursorPos < firstChar) {
				firstChar = cursorPos;
			}
			
			while(font.getWidth(value.substring(firstChar, cursorPos)) + 2 * padding > sizeX) {
				firstChar++;
			}
			
			fireKeyEvent(eventKey, character, KeyEventListener.PRESSED);
		}
		return false;
	}

	@Override
	public boolean mouseDown(GUI gui, int button, int x, int y) {
		if (isInComponent(x, y)) {
			gui.requestFocus(this);
			
			setCursorPos(x - centerX + sizeX / 2, y - centerY + sizeY / 2);
			return true;
		} else if (isFocused()) {
			gui.requestFocus(null);
		}
		return false;
	}
	
	private void setCursorPos(int x, int y) {
		for(int i = firstChar; i <= value.length(); i++) {
			if(font.getWidth(value.substring(firstChar, i)) + padding > x) {
				cursorPos = i;
				return;
			}
		}
		
		cursorPos = value.length();
	}
}