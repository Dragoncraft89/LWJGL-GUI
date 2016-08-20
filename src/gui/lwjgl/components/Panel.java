package gui.lwjgl.components;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;

public class Panel extends Component {
	ArrayList<Component> components = new ArrayList<Component>();

	public Panel(int centerX, int centerY, int sizeX, int sizeY) {
		super(centerX, centerY, sizeX, sizeY);
		
		background_r = 0.1f;
		background_g = 0.1f;
		background_b = 0.1f;
	}

	@Override
	public void paint(float delta) {
		glPushMatrix();
		glTranslatef(centerX - sizeX / 2, centerY - sizeY / 2, 0);
		
		glBegin(GL_QUADS);
		glColor4f(background_r, background_g, background_b, background_a);
		glVertex2f(0, 0);
		glVertex2f(sizeX, 0);
		glVertex2f(sizeX, sizeY);
		glVertex2f(0, sizeY);
		glEnd();
		
		super.paintBorder();

		for (int i = 0; i < components.size(); i++) {
			components.get(i).paint(delta);
		}
		
		glPopMatrix();
	}

	public void addComponent(Component component) {
		components.add(component);
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
		for (int i = 0; i < components.size(); i++) {
			if (components.get(i).mouseDown(gui, button, x, y))
				return true;
		}
		return super.mouseDown(gui, button, x, y);
	}

	@Override
	public boolean mouseUp(GUI gui, int button, int x, int y) {
		for (int i = 0; i < components.size(); i++) {
			if (components.get(i).mouseUp(gui, button, x, y))
				return true;
		}
		return super.mouseUp(gui, button, x, y);
	}

	@Override
	public boolean mouseWheelChanged(GUI gui, int mouseWheel, int x, int y) {
		for (int i = 0; i < components.size(); i++) {
			if (components.get(i).mouseWheelChanged(gui, mouseWheel, x, y))
				return true;
		}
		return super.mouseWheelChanged(gui, mouseWheel, x, y);
	}

	@Override
	public boolean mouseMoved(GUI gui, int x, int y, int dX, int dY) {
		for (int i = 0; i < components.size(); i++) {
			if (components.get(i).mouseMoved(gui, x, y, dX, dY))
				return true;
		}
		return super.mouseMoved(gui, x, y, dX, dY);
	}

}
