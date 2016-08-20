package gui.lwjgl.components;

import static org.lwjgl.opengl.GL11.*;

public class Label extends Component {

	protected String text;

	public Label(int centerX, int centerY, int sizeX, int sizeY, String text) {
		super(centerX, centerY, sizeX, sizeY);
		this.text = text;
		
		addToGroup("labels");
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public void paint(float delta) {
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
		
		super.paintBorder();

		super.drawString(font, text, sizeX / 2, sizeY / 2, text_r, text_g, text_b, text_a);
		glPopMatrix();
	}
}
