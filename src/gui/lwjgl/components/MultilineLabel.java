package gui.lwjgl.components;

import static org.lwjgl.opengl.GL11.*;

public class MultilineLabel extends Label {

	private Slider slider;

	public MultilineLabel(int centerX, int centerY, int sizeX, int sizeY, String text) {
		super(centerX - 5, centerY, sizeX - 10, sizeY, text);
		slider = new Slider(centerX + sizeX / 2 - 5, centerY, 10, sizeY, false);
		setText(text);
		
		background_r = 0.1f;
		background_g = 0.1f;
		background_b = 0.1f;
		background_a = 1f;
	}

	public void setText(String text) {
		this.text = text;
		slider.setMaxValue(Math.max(0, (int) Math.ceil(getLines(text) - ((float) sizeY / (font.getHeight() + 5)))));
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

		glBegin(GL_QUADS);
		glColor4f(background_r, background_g, background_b, background_a);
		glVertex2f(centerX - sizeX / 2, centerY - sizeY / 2);
		glVertex2f(centerX + sizeX / 2, centerY - sizeY / 2);
		glVertex2f(centerX + sizeX / 2, centerY + sizeY / 2);
		glVertex2f(centerX - sizeX / 2, centerY + sizeY / 2);
		glEnd();
		
		super.paintBorder();

		int y = centerY - sizeY / 2;
		int posX = centerX - sizeX / 2;

		int offset = slider.getValue();
		for (String s : text.split("\n")) {
			s = s.replace("\r", "");
			for (String d : s.split(" ")) {
				if (posX + font.getWidth(d) > centerX + sizeX / 2) {
					posX = centerX - sizeX / 2;
					if (offset != 0) {
						offset--;
						continue;
					}
					y += font.getHeight(s) + 5;
					if (y > centerY + sizeY / 2)
						return;
				}
				if (offset == 0)
					font.drawString(d, posX + 3, y, text_r, text_g, text_b, text_a);
				posX += font.getWidth(d + " ");
			}
			posX = centerX - sizeX / 2;
			if (offset != 0) {
				offset--;
				continue;
			}
			y += font.getHeight(s) + 5;
			if (y > centerY + sizeY / 2 - font.getHeight())
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

}