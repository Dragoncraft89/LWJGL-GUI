package gui.lwjgl.components;

import static org.lwjgl.opengl.GL11.*;

import gui.lwjgl.listener.ClickEventListener;
import gui.lwjgl.style.StyleTemplate;

/**
 * This class represents a tab<br>
 * <br>
 * default groups:<br>
 * all<br>
 * tab<br>
 * 
 * @author Florian
 */
public class Tab extends Component {
	
	private class TabClickEventListener implements ClickEventListener {

		@Override
		public void action(Component component, int x, int y, int type) {
			((Tab)component).setSelected(true);
		}
		
	}
	
	private String name;

	private boolean selected;
	
	private TabGroup group;
	
	/**
	 * Constructor
	 * 
	 * @param centerX
	 * @param centerY
	 * @param sizeX
	 * @param sizeY
	 * @param name
	 */
	public Tab(int centerX, int centerY, int sizeX, int sizeY, String name) {
		super(centerX, centerY, sizeX, sizeY);
		
		this.name = name;
		
		addToGroup("tab");
		addClickEventListener(new TabClickEventListener());
	}

	@Override
	public void paint(float delta) {
		glPushMatrix();
		glTranslatef(centerX - sizeX / 2, centerY - sizeY / 2, 0);
		
		bindTexture();
		glBegin(GL_QUADS);
		
		if (!selected)
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

	@Override
	public void loadTemplate(StyleTemplate style) {
		style.load(this);
	}
	
	/**
	 * Returns if the tab is selected
	 */
	public boolean isSelected() {
		return selected;
	}

	/**
	 * Sets the TabGroup for this component<br>
	 * <br>
	 * <b>This method should not be called, refer to {@link TabGroup#addToGroup(Tab)}</b>
	 * @param group
	 */
	public void setTabGroup(TabGroup group) {
		this.group = group;
	}
	
	/**
	 * Sets the tab selected/unselected
	 * @param selected
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
		
		if(selected && group != null) {
			group.select(this);
		}
	}

}
