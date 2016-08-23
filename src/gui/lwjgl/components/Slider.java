package gui.lwjgl.components;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;

import org.lwjgl.opengl.Display;

import gui.lwjgl.listener.ChangeEventListener;
import gui.lwjgl.style.StyleTemplate;

public class Slider extends Component {
	private static final int MINIMAL_SIZE = 5;
	
	private int value;

	private int maxValue;

	private boolean horizontal;

	private ArrayList<ChangeEventListener> listener = new ArrayList<ChangeEventListener>();

	public Slider(int centerX, int centerY, int sizeX, int sizeY, boolean horizontal) {
		this(centerX, centerY, sizeX, sizeY, horizontal, 0, 1);
	}

	public Slider(int centerX, int centerY, int sizeX, int sizeY, boolean horizontal, int minValue, int maxValue) {
		super(centerX, centerY, sizeX, sizeY);
		this.horizontal = horizontal;
		this.maxValue = maxValue;
		
		addToGroup("slider");
	}

	public void setMaxValue(int maxValue) {
		this.maxValue = maxValue;
	}

	@Override
	public void paint(float delta) {
		glPushMatrix();
		glTranslatef(centerX - sizeX / 2, centerY - sizeY / 2, 0);
		
		int d = maxValue + 1;
		float posX = sizeX / (float) d;
		float posY = sizeY / (float) d;
		
		bindTexture();

		glBegin(GL_QUADS);
		glColor4f(background_r, background_g, background_b, background_a);
		if (!isEditable())
			glColor4f(background_r * 0.5f, background_g * 0.5f, background_b * 0.5f, background_a);
		glTexCoord2f(0, 0);
		glVertex2f(0, 0);
		glTexCoord2f(1, 0);
		glVertex2f(sizeX, 0);
		glTexCoord2f(1, 1);
		glVertex2f(sizeX, sizeY);
		glTexCoord2f(0, 1);
		glVertex2f(0, sizeY);
		glEnd();
		
		glBindTexture(GL_TEXTURE_2D, 0);
		
		glBegin(GL_QUADS);

		glColor4f(foreground_r, foreground_g, foreground_b, foreground_a);
		if (!isEditable())
			glColor4f(foreground_r * 0.5f, foreground_g * 0.5f, foreground_b * 0.5f, foreground_a);
		if (horizontal) {
			glVertex2f((value) * posX, 0);
			glVertex2f((value) * posX, sizeY);
			glVertex2f(Math.max((value + 1) * posX, value * posX + MINIMAL_SIZE), sizeY);
			glVertex2f(Math.max((value + 1) * posX, value * posX + MINIMAL_SIZE), 0);
		} else {
			glVertex2f(0, (value) * posY);
			glVertex2f(0, Math.max((value + 1) * posY, value * posY + MINIMAL_SIZE));
			glVertex2f(sizeX, Math.max((value + 1) * posY, value * posY + MINIMAL_SIZE));
			glVertex2f(sizeX, (value) * posY);
		}
		glEnd();
		
		super.paintBorder();
		glPopMatrix();
	}

	private void checkState(int oldvalue) {
		value = Math.max(0, Math.min(value, maxValue));

		if (value != oldvalue) {
			fireChangeEvent();
		}
	}

	public int getValue() {
		return value;
	}

	private void fireChangeEvent() {
		for (int i = 0; i < listener.size(); i++) {
			listener.get(i).action(this);
		}
	}

	public void addChangeEventListener(ChangeEventListener listener) {
		this.listener.add(listener);
	}

	public void setValue(int value) {
		int oldvalue = this.value;

		this.value = value;

		checkState(oldvalue);
	}

	@Override
	public boolean mouseDown(GUI gui, int button, int x, int y) {
		int oldvalue = value;
		if (isInComponent(x, y)) {
			gui.requestFocus(this);

			int d = maxValue + 1;
			float dX = sizeX / (float) d;
			float dY = sizeY / (float) d;

			if (horizontal) {
				value = (int) ((x - centerX + sizeX / 2) / dX - 1.5f);
			} else {
				value = (int) ((Display.getHeight() - y - centerY + sizeY / 2) / dY - 1.5f);
			}

			checkState(oldvalue);
		}
		return false;
	}

	@Override
	public boolean mouseUp(GUI gui, int button, int x, int y) {
		if(isFocused()) {
			gui.requestFocus(null);
			return true;
		}
		
		return false;
	}

	@Override
	public boolean mouseWheelChanged(GUI gui, int mouseWheel, int x, int y) {
		int oldvalue = value;
		if (!isEditable())
			return false;
		if (!horizontal) {
			if (centerX - sizeX / 2 < x && centerX + sizeX / 2 > x && Display.getHeight() - (centerY + sizeY / 2) < y
					&& Display.getHeight() - (centerY - sizeY / 2) > y) {
				value += (int) (-mouseWheel / 120);
				checkState(oldvalue);
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean mouseMoved(GUI gui, int x, int y, int dX, int dY) {
		if(isFocused()) {
			int oldvalue = value;
			
			int d = maxValue + 1;
			float sliderDX = sizeX / (float) d;
			float sliderDY = sizeY / (float) d;

			if (horizontal) {
				value = (int) ((x - centerX + sizeX / 2) / sliderDX - 1.5f);
			} else {
				value = (int) ((Display.getHeight() - y - centerY + sizeY / 2) / sliderDY - 1.5f);
			}

			checkState(oldvalue);
		}
		
		return false;
	}

	@Override
	public void loadTemplate(StyleTemplate style) {
		style.load(this);
	}
}
