package gui.lwjgl.listener;

public interface EventCallBack {
	public boolean keyUp(int eventKey, char character);

	public boolean keyDown(int eventKey, char character);

	public boolean mouseDown(int button, int x, int y);

	public boolean mouseUp(int button, int x, int y);

	public boolean mouseWheelChanged(int dwheel, int x, int y);

	public boolean mouseMoved(int x, int y, int dX, int dY);
}
