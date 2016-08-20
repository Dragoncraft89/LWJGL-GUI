package gui.lwjgl.components;

import static org.lwjgl.opengl.GL11.*;

public class Button extends Component {
	private String name;

	private boolean highlight;

	public Button(int centerX, int centerY, int sizeX, int sizeY, String name) {
		super(centerX, centerY, sizeX, sizeY);
		this.name = name;
	}

	@Override
	public void paint(float delta) {
		glBegin(GL_QUADS);
		if (!highlight)
			glColor4f(background_r, background_g, background_b, background_a);
		else
			glColor4f(foreground_r, foreground_g, foreground_b, foreground_a);
		if (!isEditable())
			glColor4f(background_r * 0.1f, background_g * 0.1f, background_b * 0.1f, background_a);
		glVertex2f(centerX - sizeX / 2, centerY - sizeY / 2);
		glVertex2f(centerX + sizeX / 2, centerY - sizeY / 2);
		glVertex2f(centerX + sizeX / 2, centerY + sizeY / 2);
		glVertex2f(centerX - sizeX / 2, centerY + sizeY / 2);
		glEnd();

		super.paintBorder();
		
		glColor3f(1, 1, 1);
		super.drawString(font, name, centerX, centerY, text_r, text_g, text_b, text_a);
	}

	public boolean mouseMoved(GUI gui, int x, int y, int dX, int dY) {
		if (isInComponent(x, y)) {
			highlight = true;
			return true;
		}
		
		highlight = false;
		return false;
	}

	public void setText(String name) {
		this.name = name;
	}

}
