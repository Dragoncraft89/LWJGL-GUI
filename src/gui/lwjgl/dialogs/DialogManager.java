package gui.lwjgl.dialogs;

import static org.lwjgl.opengl.GL11.*;

import java.util.Stack;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.glu.GLU;

import gui.lwjgl.components.GUI;
import gui.lwjgl.listener.EventCallBack;

/**
 * This class handles all open dialogs and the overlay<br>
 * <br>
 * For Legacy lwjgl (2.x) there is only one instance,<br>
 * which can be accessed per {@link #getDefault()}<br>
 * or via the xxxDefault() static methods<br>
 * 
 * <b>Note:</b> There need to be called two methods in the main rendering loop:<br>
 * {@link #paintDefault()} or {@link #paint()}<br>
 * {@link #dispatchEventsDefault()} or {@link #dispatchEvents()}<br>
 * 
 * @see #getDefault()
 * @see #paintDefault()
 * @see #dispatchEventsDefault()
 * @see #setOverlayDefault(GUI)
 * @see #openDialogDefault(Dialog)
 * 
 * @author Florian
 *
 */
public class DialogManager {
	private static DialogManager instance;
	
	private Stack<Dialog> activeDialogs;
	
	private GUI overlay;
	
	private EventCallBack callBack;
	
	private DialogManager() {
		activeDialogs = new Stack<Dialog>();
	}
	
	/**
	 * Opens a dialog
	 * 
	 * @param dialog - the dialog to open
	 */
	public void openDialog(Dialog dialog) {
		activeDialogs.push(dialog);
		dialog.open(this);
	}
	
	/**
	 * This method paints the overlay and the dialogs<br>
	 * <br>
	 * <b>Note:</b> This method needs to be called in the main rendering loop
	 */
	public void paint() {
		guiMatrix();
		if(overlay != null);
			overlay.paint();
		
		for(int i = 0; i < activeDialogs.size(); i++) {
			activeDialogs.get(i).paint();
		}
		popGUIMatrix();
	}
	
	private void guiMatrix() {
		glMatrixMode(GL_PROJECTION);
		glPushMatrix();
		glLoadIdentity();
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glHint(GL_POINT_SMOOTH_HINT, GL_NICEST);
		glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
		glDisable(GL_DEPTH_TEST);
		GLU.gluOrtho2D(0, Display.getWidth(), Display.getHeight(), 0);
		glMatrixMode(GL_MODELVIEW);
		glEnable(GL_COLOR_MATERIAL);
		glPushMatrix();
		glLoadIdentity();
	}
	
	private void popGUIMatrix() {
    	glPopMatrix();
		glMatrixMode(GL_PROJECTION);
		glPopMatrix();
		glMatrixMode(GL_MODELVIEW);
	}
	
	/**
	 * This method is triggered when a dialog is focused<br>
	 * <br>
	 * The dialog gets placed on top of the stack
	 * 
	 * @param dialog
	 */
	public void placeOnTop(Dialog dialog) {
		activeDialogs.remove(dialog);
		activeDialogs.push(dialog);
	}
	
	/**
	 * Sets the overlay
	 * @param gui - the overlay
	 */
	public void setOverlay(GUI gui) {
		this.overlay = gui;
		gui.open(this);
	}
	
	/**
	 * This method processes all events from the lwjgl window.<br>
	 * All events are passed to dialogs, to the overlay and to the EventCallBack if they don't get consumed by parts of the gui.<br>
	 * <br>
	 * <b>Note:</b> This method needs to be called in the main rendering loop
	 */
	public void dispatchEvents() {
		while (Keyboard.next()) {
			int eventKey = Keyboard.getEventKey();
			char character = Keyboard.getEventCharacter();
			if (Keyboard.getEventKeyState()) {
				keyDown(eventKey, character);
			} else {
				keyUp(eventKey, character);
			}
		}

		while (Mouse.next()) {
			int button = Mouse.getEventButton();
			int x = Mouse.getEventX();
			int y = Mouse.getEventY();
			int dwheel = Mouse.getEventDWheel();

			int dX = Mouse.getEventDX();
			int dY = Mouse.getEventDY();

			if (dX != 0 || dY != 0) {
				mouseMoved(x, y, dX, dY);
			}

			if (button != -1) {
				if (Mouse.getEventButtonState()) {
					mouseDown(button, x, y);
				} else {
					mouseUp(button, x, y);
				}
			} else if(dwheel != 0) {
				mouseWheelChanged(dwheel, x, y);
			}

		}
	}
	
	private void keyDown(int eventKey, char character) {
		for(int i = 1; i <= activeDialogs.size(); i++)
			if(activeDialogs.get(activeDialogs.size() - i).keyDown(eventKey, character)) return;
		
		if(overlay != null && overlay.keyDown(eventKey, character))
			return;
		
		if(callBack != null)
			callBack.keyDown(eventKey, character);
	}
	
	private void keyUp(int eventKey, char character) {
		for(int i = 1; i <= activeDialogs.size(); i++)
			if(activeDialogs.get(activeDialogs.size() - i).keyUp(eventKey, character)) return;
		
		if(overlay != null && overlay.keyUp(eventKey, character))
			return;

		if(callBack != null)
			callBack.keyUp(eventKey, character);
	}
	
	private void mouseMoved(int x, int y, int dX, int dY) {
		for(int i = 1; i <= activeDialogs.size(); i++)
			if(activeDialogs.get(activeDialogs.size() - i).mouseMoved(x, y, dX, dY)) return;
		
		if(overlay != null && overlay.mouseMoved(x, y, dX, dY))
			return;
		
		if(callBack != null)
			callBack.mouseMoved(x, y, dX, dY);
	}
	
	private void mouseDown(int button, int x, int y) {
		for(int i = 1; i <= activeDialogs.size(); i++)
			if(activeDialogs.get(activeDialogs.size() - i).mouseDown(button, x, y)) return;
		
		if(overlay != null && overlay.mouseDown(button, x, y))
			return;

		if(callBack != null)
			callBack.mouseDown(button, x, y);
	}
	
	private void mouseUp(int button, int x, int y) {
		for(int i = 1; i <= activeDialogs.size(); i++)
			if(activeDialogs.get(activeDialogs.size() - i).mouseUp(button, x, y)) return;
		
		if(overlay != null && overlay.mouseUp(button, x, y))
			return;

		if(callBack != null)
			callBack.mouseUp(button, x, y);
	}
	
	private void mouseWheelChanged(int dwheel, int x, int y) {
		for(int i = 1; i <= activeDialogs.size(); i++)
			if(activeDialogs.get(activeDialogs.size() - i).mouseWheelChanged(dwheel, x, y)) return;
		
		if(overlay != null && overlay.mouseWheelChanged(dwheel, x, y))
			return;

		if(callBack != null)
			callBack.mouseWheelChanged(dwheel, x, y);
	}

	/**
	 * This is equal to {@link #getDefault()}.{@link #openDialog(Dialog)}
	 * 
	 * @see DialogManager
	 * @see #openDialog(Dialog)
	 * @see #getDefault()
	 */
	public static void openDialogDefault(Dialog dialog) {
		getDefault().openDialog(dialog);
	}
	
	/**
	 * This is equal to {@link #getDefault()}.{@link #paint()}
	 * 
	 * @see DialogManager
	 * @see #paint()
	 * @see #getDefault()
	 */
	public static void paintDefault() {
		getDefault().paint();
	}

	/**
	 * This is equal to {@link #getDefault()}.{@link #setOverlay(GUI)}
	 * 
	 * @see DialogManager
	 * @see #setOverlay(GUI)
	 * @see #getDefault()
	 */
	public static void setOverlayDefault(GUI gui) {
		getDefault().setOverlay(gui);
	}

	/**
	 * This is equal to {@link #getDefault()}.{@link #dispatchEvents()}
	 * 
	 * @see DialogManager
	 * @see #dispatchEvents()
	 * @see #getDefault()
	 */
	public static void dispatchEventsDefault() {
		getDefault().dispatchEvents();
	}
	
	/**
	 * Returns the default DialogManager<br>
	 * <br>
	 * Because legacy lwjgl (2.x) is only capable of running one window,<br>
	 * new DialogManager-Objects need not be created and is therefore disabled
	 * 
	 * @return the default DialogManager
	 */
	public static DialogManager getDefault() {
		if(instance == null)
			instance = new DialogManager();
		
		return instance;
	}
}
