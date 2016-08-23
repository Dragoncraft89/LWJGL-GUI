package gui.lwjgl.listener;

/**
 * This interface defines methods for all events that can be obtained via keyboard and mouse<br>
 * <br>
 * It is used to get all events that did not get consumed by neither the overlay nor any dialog
 */
public interface EventCallBack {
	/**
	 * This method gets called when a key is released
	 * @param eventKey
	 * @param character
	 * @return true when event was consumed, false if not
	 */
	public boolean keyUp(int eventKey, char character);

	/**
	 * This method gets called when a key is pressed
	 * @param eventKey
	 * @param character
	 * @return true when event was consumed, false if not
	 */
	public boolean keyDown(int eventKey, char character);

	/**
	 * This method gets called when a mouse button is pressed
	 * @param eventKey
	 * @param character
	 * @return true when event was consumed, false if not
	 */
	public boolean mouseDown(int button, int x, int y);

	/**
	 * This method gets called when a mouse button is released
	 * @param eventKey
	 * @param character
	 * @return true when event was consumed, false if not
	 */
	public boolean mouseUp(int button, int x, int y);

	/**
	 * This method gets called when the mouse wheel has changed
	 * @param eventKey
	 * @param character
	 * @return true when event was consumed, false if not
	 */
	public boolean mouseWheelChanged(int dwheel, int x, int y);

	/**
	 * This method gets called when the mouse is moved
	 * @param eventKey
	 * @param character
	 * @return true when event was consumed, false if not
	 */
	public boolean mouseMoved(int x, int y, int dX, int dY);
}
