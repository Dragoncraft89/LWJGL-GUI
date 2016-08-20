package gui.lwjgl.components;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.Display;

import gui.lwjgl.util.Texture;

public class IconList extends List {

	protected int elementHeight;
	protected int elementWidth;

	public IconList(int centerX, int centerY, int sizeX, int sizeY, int elementsVisible) {
		super(centerX, centerY, sizeX, sizeY, elementsVisible);
	}

	public IconList(int centerX, int centerY, int sizeX, int sizeY, int elementsVisible, ListElement[] elements) {
		super(centerX, centerY, sizeX, sizeY, elementsVisible, elements);
	}

	public void setElements(ListElement[] elements) {
		this.elements = elements;

		for (ListElement e : elements) {
			Texture tex = null;
			if (e != null)
				tex = e.getIcon();
			if (tex != null) {
				this.elementHeight = sizeY / elementsVisible - 2;
				this.elementWidth = (int) (tex.getWidth() / (float) tex.getHeight() * elementHeight);
			}
		}

		if (elementWidth != 0)
			this.slider.setMaxValue((elements.length) * elementWidth / sizeX);
		this.selected = -1;
	}
	
	@Override
	public void setTexture(Texture texture) {
		panel.setTexture(texture);
	}

	@Override
	public void paint(float delta) {
		panel.paint(delta);

		glPushMatrix();
		glTranslatef(centerX - sizeX / 2, centerY - sizeY / 2, 0);
		
		if (elementWidth == 0)
			return;
		int elementsX = sizeX / elementWidth;
		int offset = slider.getValue();
		for (int line = 0; line < elementsVisible; line++) {
			for (int i = 0; i < elementsX; i++) {
				Texture tex = null;
				if ((line + offset) * elementsX + i < elements.length
						&& elements[(line + offset) * elementsX + i] != null)
					tex = elements[(line + offset) * elementsX + i].getIcon();
				if (tex != null) {
					tex.bind();
					glBegin(GL_QUADS);
					if (selected == (line + offset) * elementsX + i)
						glColor3f(1, 1, 1);
					else
						glColor3f(0.5f, 0.5f, 0.5f);
					glTexCoord2f(0, 0);
					glVertex2f(elementWidth * i,
							(elementHeight + MARGIN_BETWEEN_ELEMENTS) * line);
					glTexCoord2f(0, 1);
					glVertex2f(elementWidth * i,
							(elementHeight + MARGIN_BETWEEN_ELEMENTS) * line + elementHeight);
					glTexCoord2f(1, 1);
					glVertex2f(elementWidth * (i + 1),
							-(elementHeight + MARGIN_BETWEEN_ELEMENTS) * line + elementHeight);
					glTexCoord2f(1, 0);
					glVertex2f(elementWidth * (i + 1),
							(elementHeight + MARGIN_BETWEEN_ELEMENTS) * line);
					glEnd();
					glBindTexture(GL_TEXTURE_2D, 0);
				}
			}
		}
		
		glPopMatrix();
	}

	@Override
	public boolean mouseDown(GUI gui, int button, int x, int y) {
		if (elementWidth != 0 && button == 0 && isInComponent(x, y)) {
			int oldSelect = this.selected;
			int elementsX = sizeX / elementWidth;
			int vertical = slider.getValue() + (Display.getHeight() - y - centerY + sizeY / 2) / elementHeight;
			int horizontal = (x - centerX + sizeX / 2) / elementWidth;
			this.selected = vertical * elementsX + horizontal;
			if (oldSelect != this.selected && selected >= 0 && selected < elements.length) {
				fireOnSelectEvent(elements[this.selected], this.selected);
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean isInComponent(int x, int y) {
		return (x > centerX - sizeX / 2 && x < centerX + sizeX / 2 - SLIDER_WIDTH)
		&& (y > centerY - sizeY / 2 && y < centerY + sizeY / 2);
	}
}
