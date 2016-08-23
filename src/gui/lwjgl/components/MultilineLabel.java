package gui.lwjgl.components;

import static org.lwjgl.opengl.GL11.*;

import gui.lwjgl.style.StyleTemplate;

/**
 * This is a label<br>
 * <br>
 * default groups:<br>
 * all<br>
 * multiline<br>
 * <br>
 * The slider this component has the groups:
 * all<br>
 * slider<br>
 * multiline<br>
 * @author Florian
 *
 */
public class MultilineLabel extends Label {
	private static int SPACE_BETWEEN_LINES = -2;
	
	private static int padding = 5;

	private Slider slider;

	public MultilineLabel(int centerX, int centerY, int sizeX, int sizeY, String text) {
		super(centerX - 5, centerY, sizeX - 10, sizeY, text);
		slider = new Slider(centerX + sizeX / 2 - 5, centerY, 10, sizeY, false);
		setText(text);
		
		addToGroup("multiline");
		slider.addToGroup("multiline");
	}

	/**
	 * Sets the components text
	 */
	public void setText(String text) {
		this.text = text;
		slider.setMaxValue(Math.max(0, (int) Math.ceil(getLines(text) - ((float) sizeY / (font.getHeight() + SPACE_BETWEEN_LINES)))));
	}
	
	/**
	 * Sets the background color of the slider
	 * @param r
	 * @param g
	 * @param b
	 * @param a
	 */
	public void setSliderBackgroundColor(float r, float g, float b, float a) {
		slider.setBackgroundColor(r, g, b, a);
	}
	
	/**
	 * Sets the foreground color of the slider
	 * @param r
	 * @param g
	 * @param b
	 * @param a
	 */
	public void setSliderForegroundColor(float r, float g, float b, float a) {
		slider.setForegroundColor(r, g, b, a);
	}

	private int getLines(String text) {
		int lines = 0;

		int posX = centerX - sizeX / 2;
		for (String s : text.split("\n")) {
			s = s.replace("\r", "");
			for (String d : s.split(" ")) {
				if (posX + font.getWidth(d) > centerX + sizeX / 2) {
					posX = centerX - sizeX / 2;
					lines++;
				}
				posX += font.getWidth(d + " ");
			}
			lines++;
			posX = centerX - sizeX / 2;
		}

		return lines;
	}

	public void paint(float delta) {
		slider.paint(delta);
		
		glPushMatrix();
		glTranslatef(centerX - sizeX / 2, centerY - sizeY / 2, 0);

		bindTexture();

		glBegin(GL_QUADS);
		glColor4f(background_r, background_g, background_b, background_a);
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
		
		super.paintBorder();
		
		drawText();
		
		glPopMatrix();
	}
	
	private void drawText() {
		int y = padding;
		int posX = padding;

		int offset = slider.getValue();
		for (String s : text.split("\n")) {
			s = s.replace("\r", "");
			for (String d : s.split(" ")) {
				if (posX + font.getWidth(d) > sizeX - padding) {
					posX = padding;
					if (offset != 0) {
						offset--;
						continue;
					}
					y += font.getHeight(s) + SPACE_BETWEEN_LINES;
					if (y + font.getHeight() > sizeY - padding)
						return;
				}
				if (offset == 0)
					font.drawString(d, posX, y, text_r, text_g, text_b, text_a);
				posX += font.getWidth(d + " ");
			}
			posX = padding;
			if (offset != 0) {
				offset--;
				continue;
			}
			y += font.getHeight(s) + SPACE_BETWEEN_LINES;
			if (y + font.getHeight() > sizeY - padding)
				return;
		}
	}

	@Override
	public boolean keyUp(GUI gui, int eventKey, char character) {
		return slider.keyUp(gui, eventKey, character) || super.keyUp(gui, eventKey, character);
	}

	@Override
	public boolean keyDown(GUI gui, int eventKey, char character) {
		return slider.keyDown(gui, eventKey, character) || super.keyDown(gui, eventKey, character);
	}

	@Override
	public boolean mouseDown(GUI gui, int button, int x, int y) {
		return slider.mouseDown(gui, button, x, y) || super.mouseDown(gui, button, x, y);
	}

	public boolean mouseUp(GUI gui, int button, int x, int y) {
		return slider.mouseUp(gui, button, x, y) || super.mouseUp(gui, button, x, y);
	}

	@Override
	public boolean mouseMoved(GUI gui, int x, int y, int dX, int dY) {
		return slider.mouseMoved(gui, x, y, dX, dY) || super.mouseMoved(gui, x, y, dX, dY);
	}

	@Override
	public boolean mouseWheelChanged(GUI gui, int mouseWheel, int x, int y) {
		if (isInComponent(x, y)) {
			slider.setValue(slider.getValue() - mouseWheel / 120);
			return true;
		}

		return slider.mouseWheelChanged(gui, mouseWheel, x, y) || super.mouseWheelChanged(gui, mouseWheel, x, y);
	}

	@Override
	public void loadTemplate(StyleTemplate style) {
		style.load(this);
		
		slider.loadTemplate(style);
	}

}
