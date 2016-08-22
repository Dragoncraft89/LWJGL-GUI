package gui.lwjgl.components;

import static org.lwjgl.opengl.GL11.*;

import gui.lwjgl.style.StyleTemplate;

public class Window extends Component {

	private int offsetX;
	private int offsetY;
	private Panel contentPane;

	private boolean focused;

	private Label label;

	public Window(Panel contentPane, String title) {
		super(contentPane.centerX, contentPane.centerY - 12, contentPane.sizeX, contentPane.sizeY + 25);
		this.contentPane = contentPane;

		this.label = new Label(centerX, centerY - sizeY / 2 + 12, sizeX, 25, title);
		
		addToGroup("windows");
		label.addToGroup("windows");
		contentPane.addToGroup("windows");
	}
	
	public Panel getContentPane() {
		return contentPane;
	}
	
	@Override
	public void setTextColor(float r, float g, float b, float a) {
		label.setTextColor(r, g, b, a);
	}
	
	@Override
	public void setBackgroundColor(float r, float g, float b, float a) {
		label.setBackgroundColor(r, g, b, a);
	}

	@Override
	public void paint(float delta) {
		glPushMatrix();
		glTranslatef(-offsetX, offsetY, 0);
		contentPane.paint(delta);
		label.paint(delta);
		glPopMatrix();
	}

	@Override
	public boolean keyUp(GUI gui, int eventKey, char character) {
		return label.keyUp(gui, eventKey, character) || contentPane.keyUp(gui, eventKey, character) || super.keyUp(gui, eventKey, character);
	}

	@Override
	public boolean keyDown(GUI gui, int eventKey, char character) {
		return label.keyDown(gui, eventKey, character) || contentPane.keyDown(gui, eventKey, character) || super.keyDown(gui, eventKey, character);
	}

	@Override
	public boolean mouseDown(GUI gui, int button, int x, int y) {
		x += offsetX;
		y += offsetY;
		if (button == 0 && label.isInComponent(x, y)) {
			focused = true;
			return true;
		}
		return label.mouseDown(gui, button, x, y) || contentPane.mouseDown(gui, button, x, y);
	}

	@Override
	public boolean mouseUp(GUI gui, int button, int x, int y) {
		focused = false;
		x += offsetX;
		y += offsetY;
		return contentPane.mouseUp(gui, button, x, y);
	}

	@Override
	public boolean mouseWheelChanged(GUI gui, int mouseWheel, int x, int y) {
		x += offsetX;
		y += offsetY;
		return contentPane.mouseWheelChanged(gui, mouseWheel, x, y);
	}

	@Override
	public boolean mouseMoved(GUI gui, int x, int y, int dX, int dY) {
		if (focused) {
			offsetX -= dX;
			offsetY -= dY;
		}
		x += offsetX;
		y += offsetY;
		return contentPane.mouseMoved(gui, x, y, dX, dY) || super.mouseMoved(gui, x, y, dX, dY);
	}

	@Override
	public void loadTemplate(StyleTemplate style) {
		style.load(this);
		
		label.loadTemplate(style);
		contentPane.loadTemplate(style);
	}

}
