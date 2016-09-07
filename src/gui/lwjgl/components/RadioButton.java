package gui.lwjgl.components;

import static org.lwjgl.opengl.GL11.*;

import gui.lwjgl.listener.ClickEventListener;
import gui.lwjgl.style.StyleTemplate;
import gui.lwjgl.util.LWJGLFont;
import gui.lwjgl.util.Texture;

public class RadioButton extends Component {
	
	class RadioButtonListener implements ClickEventListener{
		@Override
		public void action(Component component, int x, int y, int type) {
			if(type != RELEASED)
				return;
			
			RadioButton button = (RadioButton) component;
			button.setSelected(true);
		}

	}
	
	private boolean highlight;
	private boolean selected;
	
	private RadioButtonGroup group;
	
	private Texture texOn;
	private Texture texOff;

	private String name;

	public RadioButton(int centerX, int centerY, int sizeX, int sizeY, String name) {
		super(centerX, centerY, sizeX, sizeY);
		
		this.name = name;
		addToGroup("radiobuttons");
		
		addClickEventListener(new RadioButtonListener());
	}
	
	@Override
	public void paint(float delta) {
		glPushMatrix();
		glTranslatef(centerX - sizeX / 2, centerY - sizeY / 2, 0);
		
		int texWidth = sizeY;
		int texHeight = sizeY;
		
		if(selected && texOn != null) {
			texOn.bind();
		} else if(!selected && texOff != null) {
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
	 * Sets the selected texture for this radio button
	 * @param tex
	 */
	public void setTextureEnabled(Texture tex) {
		this.texOn = tex;
	}

	/**
	 * Sets the unselected texture for this radio button
	 * @param tex
	 */
	public void setTextureDisabled(Texture tex) {
		this.texOff = tex;
	}
	
	/**
	 * Sets if the radio button is selected and deselects the active radio button from the {@link RadioButtonGroup}
	 * @param selected
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
		
		if(selected) {
			group.select(this);
		}
	}
	
	/**
	 * Checks if the radio button is selected
	 * @return true if the radio button is selected, false otherwise
	 */
	public boolean isSelected() {
		return selected;
	}

	/**
	 * Sets the RadioButtonGroup for this component<br>
	 * <br>
	 * <b>This method should not be called, refer to {@link RadioButtonGroup#addToGroup(RadioButton)}</b>
	 * @param group
	 */
	public void setRadioButtonGroup(RadioButtonGroup group) {
		this.group = group;
	}
	
	@Override
	public boolean mouseMoved(GUI gui, int x, int y, int dX, int dY) {
		highlight = isInComponent(x, y);
		
		return super.mouseMoved(gui, x, y, dX, dY);
	}
}
