package gui.lwjgl.components;

import static org.lwjgl.opengl.GL11.*;

import gui.lwjgl.style.StyleTemplate;

/**
 * This is a simple button<br>
 * <br>
 * default groups:<br>
 * all<br>
 * buttons<br>
 * @author Florian
 *
 */
public class Button extends Component {
	protected String name;

	protected boolean highlight;

	/**
	 * Constructor
	 * @param centerX
	 * @param centerY
	 * @param sizeX
	 * @param sizeY
	 * @param name
	 */
	public Button(int centerX, int centerY, int sizeX, int sizeY, String name) {
		super(centerX, centerY, sizeX, sizeY);
		this.name = name;
		
		addToGroup("buttons");
	}

	@Override
	public void paint(float delta) {
		glPushMatrix();
		glTranslatef(centerX - sizeX / 2, centerY - sizeY / 2, 0);
		
		bindTexture();
		glBegin(GL_QUADS);
		
		if (!highlight)
			glColor4f(background_r, background_g, background_b, background_a);
		else
			glColor4f(foreground_r, foreground_g, foreground_b, foreground_a);
		if (!isEditable())
			glColor4f(background_r * 0.1f, background_g * 0.1f, background_b * 0.1f, background_a);
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
		
		glColor3f(1, 1, 1);
		super.drawString(font, name, sizeX / 2, sizeY / 2, text_r, text_g, text_b, text_a);
		
		glPopMatrix();
	}

	public boolean mouseMoved(GUI gui, int x, int y, int dX, int dY) {
		if (isInComponent(x, y)) {
			highlight = true;
			return false;
		}
		
		highlight = false;
		return false;
	}

	/**
	 * Sets the button's text
	 * @param name
	 */
	public void setText(String name) {
		this.name = name;
	}

	@Override
	public void loadTemplate(StyleTemplate style) {
		style.load(this);
	}

}
