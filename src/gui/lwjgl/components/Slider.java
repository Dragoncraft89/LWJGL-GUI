package gui.lwjgl.components;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;

import org.lwjgl.opengl.Display;

import gui.lwjgl.listener.ChangeEventListener;

public class Slider extends Component {
	private int value;

	private int maxValue;

	private boolean horizontal;

	private ArrayList<ChangeEventListener> listener = new ArrayList<ChangeEventListener>();

	public Slider(int centerX, int centerY, int sizeX, int sizeY, boolean horizontal) {
		super(centerX, centerY, sizeX, sizeY);
		this.horizontal = horizontal;
		maxValue = 1;
	}

	public Slider(int centerX, int centerY, int sizeX, int sizeY, boolean horizontal, int minValue, int maxValue) {
		super(centerX, centerY, sizeX, sizeY);
		this.horizontal = horizontal;
		this.maxValue = maxValue;
	}

	public void setMaxValue(int maxValue) {
		this.maxValue = maxValue;
	}

	@Override
	public void paint(float delta) {
		int d = maxValue + 1;
		float posX = sizeX / (float) d;
		float posY = sizeY / (float) d;

		glBegin(GL_QUADS);
		glColor4f(background_r, background_g, background_b, background_a);
		if (!isEditable())
			glColor4f(background_r * 0.5f, background_g * 0.5f, background_b * 0.5f, background_a);
		glVertex2f(centerX - sizeX / 2, centerY - sizeY / 2);
		glVertex2f(centerX - sizeX / 2, centerY + sizeY / 2);
		glVertex2f(centerX + sizeX / 2, centerY + sizeY / 2);
		glVertex2f(centerX + sizeX / 2, centerY - sizeY / 2);

		glColor4f(foreground_r, foreground_g, foreground_b, foreground_a);
		if (!isEditable())
			glColor4f(foreground_r * 0.5f, foreground_g * 0.5f, foreground_b * 0.5f, foreground_a);
		if (horizontal) {
			glVertex2f((centerX - sizeX / 2) + (value) * posX, centerY - sizeY / 2);
			glVertex2f((centerX - sizeX / 2) + (value) * posX, centerY + sizeY / 2);
			glVertex2f((centerX - sizeX / 2) + Math.max((value + 1) * posX, value * posX + 5), centerY + sizeY / 2);
			glVertex2f((centerX - sizeX / 2) + Math.max((value + 1) * posX, value * posX + 5), centerY - sizeY / 2);
		} else {
			glVertex2f(centerX - sizeX / 2, (centerY - sizeY / 2) + (value) * posY);
			glVertex2f(centerX - sizeX / 2, (centerY - sizeY / 2) + Math.max((value + 1) * posY, value * posY + 5));
			glVertex2f(centerX + sizeX / 2, (centerY - sizeY / 2) + Math.max((value + 1) * posY, value * posY + 5));
			glVertex2f(centerX + sizeX / 2, (centerY - sizeY / 2) + (value) * posY);
		}
		glEnd();
		
		super.paintBorder();
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
}