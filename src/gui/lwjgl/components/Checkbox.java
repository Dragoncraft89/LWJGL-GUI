package gui.lwjgl.components;

import static org.lwjgl.opengl.GL11.*;

import gui.lwjgl.listener.ClickEventListener;
import gui.lwjgl.style.StyleTemplate;
import gui.lwjgl.util.LWJGLFont;
import gui.lwjgl.util.Texture;

/**
 * This class represents a checkbox<br>
 * <br>
 * default groups:<br>
 * all<br>
 * buttons<br>
 * checkboxes<br>
 * @author Florian
 *
 */
public class Checkbox extends Button {
	
	class CheckboxListener implements ClickEventListener{
		@Override
		public void action(Component component, int x, int y, int type) {
			if(type != RELEASED)
				return;
			
			Checkbox checkbox = (Checkbox) component;
			checkbox.setChecked(!checkbox.isChecked());
		}

	}
	
	private boolean checked;
	
	private Texture texOn;
	private Texture texOff;

	public Checkbox(int centerX, int centerY, int sizeX, int sizeY, String name) {
		super(centerX, centerY, sizeX, sizeY, name);
		
		addToGroup("checkboxes");
		
		addClickEventListener(new CheckboxListener());
	}

	@Override
	public void paint(float delta) {
		glPushMatrix();
		glTranslatef(centerX - sizeX / 2, centerY - sizeY / 2, 0);
		
		int texWidth = sizeY;
		int texHeight = sizeY;
		
		if(checked && texOn != null) {
			texWidth = (texOn.getWidth() * sizeY) / texOn.getHeight();
			texHeight = sizeY;
			texOn.bind();
		} else if(!checked && texOff != null) {
			texWidth = (texOff.getWidth() * sizeY) / texOff.getHeight();
			texHeight = sizeY;
			texOff.bind();
		}
		glBegin(GL_QUADS);
		if(isEditable()) {
			if(highlight)
				glColor3f(1, 1, 1);
			else
				glColor3f(0.8f, 0.8f, 0.8f);
		} else {
			glColor3f(0.5f, 0.5f, 0.5f);
		}
		glTexCoord2f(0, 0);
		glVertex2f(0, 0);
		glTexCoord2f(1, 0);
		glVertex2f(texWidth, 0);
		glTexCoord2f(1, 1);
		glVertex2f(texWidth, texHeight);
		glTexCoord2f(0, 1);
		glVertex2f(0, texHeight);
		glEnd();
		
		glBindTexture(GL_TEXTURE_2D, 0);
		bindTexture();
		
		glBegin(GL_QUADS);
		if (!highlight)
			glColor4f(background_r, background_g, background_b, background_a);
		else
			glColor4f(foreground_r, foreground_g, foreground_b, foreground_a);
		if (!isEditable())
			glColor4f(background_r * 0.1f, background_g * 0.1f, background_b * 0.1f, background_a);
		glTexCoord2f(0, 0);
		glVertex2f(texWidth + 1, 0);
		glTexCoord2f(1, 0);
		glVertex2f(sizeX, 0);
		glTexCoord2f(1, 1);
		glVertex2f(sizeX, sizeY);
		glTexCoord2f(0, 1);
		glVertex2f(texWidth + 1, sizeY);
		glEnd();
		
		glBindTexture(GL_TEXTURE_2D, 0);

		super.paintBorder();
		
		glColor3f(1, 1, 1);
		drawString(font, name, sizeX / 2, sizeY / 2, text_r, text_g, text_b, text_a, texWidth);
		
		glPopMatrix();
	}
	
	protected void drawString(LWJGLFont font, String text, int centerX, int centerY, float r, float g, float b, float a, int texWidth) {
		String s = "";
		for(int i = 1; i <= text.length(); i++) {
			String old = s;
			s = text.substring(0, i);
			if(font.getWidth(s) >= sizeX - texWidth) {
				s = old;
				break;
			}
		}
		
		font.drawString(s, centerX + texWidth / 2 - font.getWidth(s) / 2, centerY - font.getHeight() / 2, r, g, b, a);
	}

	@Override
	public void loadTemplate(StyleTemplate style) {
		style.load(this);
	}

	/**
	 * Sets the checked texture for this checkbox
	 * @param tex
	 */
	public void setTextureEnabled(Texture tex) {
		this.texOn = tex;
	}

	/**
	 * Sets the unchecked texture for this checkbox
	 * @param tex
	 */
	public void setTextureDisabled(Texture tex) {
		this.texOff = tex;
	}
	
	/**
	 * Sets if the checkbox is checked
	 * @param checked
	 */
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
	/**
	 * Checks if the checkbox is checked
	 * @return true if the checkbox is checked, false otherwise
	 */
	public boolean isChecked() {
		return checked;
	}
}
