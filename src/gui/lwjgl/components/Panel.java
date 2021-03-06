package gui.lwjgl.components;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;

import gui.lwjgl.style.StyleTemplate;

/**
 * This is a panel<br>
 * All components assigned to this label are placed relatively to this component,<br>
 * therefore (0|0) is the top left corner of the panel
 * <br>
 * default groups:<br>
 * all<br>
 * panels<br>
 * @author Florian
 *
 */
public class Panel extends Component {
	ArrayList<Component> components = new ArrayList<Component>();

	/**
	 * Constructor
	 * @param centerX
	 * @param centerY
	 * @param sizeX
	 * @param sizeY
	 */
	public Panel(int centerX, int centerY, int sizeX, int sizeY) {
		super(centerX, centerY, sizeX, sizeY);
		
		addToGroup("panels");
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
		glBindTexture(GL_TEXTURE_2D, 0);
		
		super.paintBorder();

		for (int i = 0; i < components.size(); i++) {
			components.get(i).paint(delta);
		}
		
		glPopMatrix();
	}
	
	/**
	 * Adds this component to a specific group, which can be layouted by CSS-files<br>
	 * All groups that are added to this component are also added to components, which are added to this panel
	 * 
	 * @param group
	 */
	public void addToGroup(String group) {
		super.addToGroup(group);
		
		for(Component c:components) {
			c.addToGroup(group);
		}
	}

	/**
	 * Adds a component to this panel
	 * @param component
	 */
	public void addComponent(Component component) {
		components.add(component);
		component.setParent(this);
	}
	
	/**
	 * Removes a component from this panel
	 * @param component
	 */
	public void removeComponent(Component component) {
		components.remove(component);
		component.setParent(null);
	}
	
	/**
	 * Removes all components from this panel
	 */
	public void clearComponents() {
		components.clear();
	}

	@Override
	public void onDisplay(GUI gui) {
		for (Component c : components) {
			c.onDisplay(gui);
		}
	}

	@Override
	public boolean keyUp(GUI gui, int eventKey, char character) {
		for (int i = 0; i < components.size(); i++) {
			if (components.get(i).keyUp(gui, eventKey, character))
				return true;
		}
		return super.keyUp(gui, eventKey, character);
	}

	@Override
	public boolean keyDown(GUI gui, int eventKey, char character) {
		for (int i = 0; i < components.size(); i++) {
			if (components.get(i).keyDown(gui, eventKey, character))
				return true;
		}
		return super.keyDown(gui, eventKey, character);
	}

	@Override
	public boolean mouseDown(GUI gui, int button, int x, int y) {
		int relativeX = x - (centerX - sizeX / 2);
		int relativeY = y + (centerY - sizeY / 2);
		
		for (int i = 0; i < components.size(); i++) {
			if (components.get(i).mouseDown(gui, button, relativeX, relativeY))
				return true;
		}
		return super.mouseDown(gui, button, x, y);
	}

	@Override
	public boolean mouseUp(GUI gui, int button, int x, int y) {
		int relativeX = x - (centerX - sizeX / 2);
		int relativeY = y + (centerY - sizeY / 2);
		
		for (int i = 0; i < components.size(); i++) {
			if (components.get(i).mouseUp(gui, button, relativeX, relativeY))
				return true;
		}
		return super.mouseUp(gui, button, x, y);
	}

	@Override
	public boolean mouseWheelChanged(GUI gui, int mouseWheel, int x, int y) {
		int relativeX = x - (centerX - sizeX / 2);
		int relativeY = y + (centerY - sizeY / 2);
		
		for (int i = 0; i < components.size(); i++) {
			if (components.get(i).mouseWheelChanged(gui, mouseWheel, relativeX, relativeY))
				return true;
		}
		return super.mouseWheelChanged(gui, mouseWheel, x, y);
	}

	@Override
	public boolean mouseMoved(GUI gui, int x, int y, int dX, int dY) {
		int relativeX = x - (centerX - sizeX / 2);
		int relativeY = y + (centerY - sizeY / 2);
		
		for (int i = 0; i < components.size(); i++) {
			if (components.get(i).mouseMoved(gui, relativeX, relativeY, dX, dY))
				return true;
		}
		return super.mouseMoved(gui, x, y, dX, dY);
	}

	public void loadTemplate(StyleTemplate style) {
		style.load(this);
		
		for(int i = 0; i < components.size(); i++) {
			components.get(i).loadTemplate(style);
		}
	}
}
