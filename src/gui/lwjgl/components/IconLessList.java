package gui.lwjgl.components;

import static org.lwjgl.opengl.GL11.*;

import gui.lwjgl.style.StyleTemplate;

public class IconLessList extends List {

	public IconLessList(int centerX, int centerY, int sizeX, int sizeY, int elementsVisible) {
		super(centerX, centerY, sizeX, sizeY, elementsVisible);
	}

	public IconLessList(int centerX, int centerY, int sizeX, int sizeY, int elementsVisible, ListElement[] elements) {
		this(centerX, centerY, sizeX, sizeY, elementsVisible);
		setElements(elements);
	}
	
	protected void paintElement(int heightPerElement, int offset, int y, int i) {
		glPushMatrix();
		glTranslatef(centerX - sizeX / 2, y, 0);
		
		bindTexture();
		glBegin(GL_QUADS);
		if (i == selected)
			glColor4f(background_r, background_g, background_b, background_a);
		else
			glColor4f(background_r * 0.5f, background_g * 0.5f, background_b * 0.5f, background_a);
		glTexCoord2f(0, 0);
		glVertex2f(0, 0);
		glTexCoord2f(1, 0);
		glVertex2f(sizeX - SLIDER_WIDTH, 0);
		glTexCoord2f(1, 1);
		glVertex2f(sizeX - SLIDER_WIDTH, heightPerElement);
		glTexCoord2f(0, 1);
		glVertex2f(0, heightPerElement);
		glEnd();

		String name = elements[i].getName();
		super.drawString(font, name, PADDING_LEFT,
				heightPerElement / 2, text_r, text_g, text_b, text_a);
		glPopMatrix();
	}

	@Override
	public void loadTemplate(StyleTemplate style) {
		style.load(this);
	}
}
