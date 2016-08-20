package gui.lwjgl.components;

import static org.lwjgl.opengl.GL11.*;

public class IconLessList extends List {

	public IconLessList(int centerX, int centerY, int sizeX, int sizeY, int elementsVisible) {
		super(centerX, centerY, sizeX, sizeY, elementsVisible);
		
		
	}

	public IconLessList(int centerX, int centerY, int sizeX, int sizeY, int elementsVisible, ListElement[] elements) {
		this(centerX, centerY, sizeX, sizeY, elementsVisible);
		setElements(elements);
	}
	
	protected void paintElement(int heightPerElement, int offset, int y, int i) {
		glBegin(GL_QUADS);
		if (i == selected)
			glColor4f(background_r, background_g, background_b, background_a);
		else
			glColor4f(background_r * 0.5f, background_g * 0.5f, background_b * 0.5f, background_a);
		glVertex2f(centerX - sizeX / 2, y);
		glVertex2f(centerX + sizeX / 2 - 10, y);
		glVertex2f(centerX + sizeX / 2 - 10, y + heightPerElement);
		glVertex2f(centerX - sizeX / 2, y + heightPerElement);
		glEnd();

		String name = elements[i].getName();
		super.drawString(font, name, centerX - sizeX / 2 + 5,
				y + heightPerElement / 2, text_r, text_g, text_b, text_a);
	}
}
