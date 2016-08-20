package gui.lwjgl.components;

import static org.lwjgl.opengl.GL11.*;

public class ProgressBar extends Component {

	private boolean horizontal;
	private int minValue;
	private int maxValue;
	private int value;

	public ProgressBar(int centerX, int centerY, int sizeX, int sizeY, boolean horizontal, int minValue, int maxValue) {
		super(centerX, centerY, sizeX, sizeY);
		this.horizontal = horizontal;
		this.minValue = minValue;
		this.maxValue = maxValue;
		
		addToGroup("progressbars");
	}

	public ProgressBar(int centerX, int centerY, int sizeX, int sizeY, boolean horizontal) {
		this(centerX, centerY, sizeX, sizeY, horizontal, 0, 1);
	}

	public ProgressBar(int centerX, int centerY, int sizeX, int sizeY) {
		this(centerX, centerY, sizeX, sizeY, true, 0, 1);
	}

	@Override
	public void paint(float delta) {
		glPushMatrix();
		glTranslatef(centerX - sizeX / 2, centerY - sizeY / 2, 0);
		
		int d = maxValue - minValue + 1;
		float posX = sizeX / (float) d;
		float posY = sizeY / (float) d;

		glBegin(GL_QUADS);
		glColor4f(background_r, background_g, background_b, background_a);
		if (!isEditable())
			glColor4f(background_r * 0.5f, background_g * 0.5f, background_b * 0.5f, background_a);
		glVertex2f(0, 0);
		glVertex2f(0, sizeY);
		glVertex2f(sizeX, sizeY);
		glVertex2f(sizeX, 0);

		glColor4f(foreground_r, foreground_g, foreground_b, foreground_a);
		if (!isEditable())
			glColor4f(foreground_r * 0.5f, foreground_g * 0.5f, foreground_b * 0.5f, foreground_a);
		if (horizontal) {
			glVertex2f(0, 0);
			glVertex2f(0, sizeY);
			glVertex2f((value + 1) * posX, sizeY);
			glVertex2f((value + 1) * posX, 0);
		} else {
			glVertex2f(0, 0);
			glVertex2f(0, (value + 1) * posY);
			glVertex2f(sizeX, (value + 1) * posY);
			glVertex2f(sizeX, 0);
		}
		glEnd();
		
		super.paintBorder();
		glPopMatrix();
	}

	public void setMinValue(int minValue) {
		this.minValue = minValue;
	}

	public void setMaxValue(int maxValue) {
		this.maxValue = maxValue;
	}

	public void setHorizontal(boolean horizontal) {
		this.horizontal = horizontal;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
