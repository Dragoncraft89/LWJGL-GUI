package test;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;

import gui.lwjgl.dialogs.DialogManager;

import static org.lwjgl.opengl.GL11.*;

public abstract class Test {
	public Test() {
		try {
			Display.sync(60);
			Display.create();
			
			glClearColor(1, 1, 1, 1);
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}
	
	public final void renderLoop() {
		while(!Display.isCloseRequested()) {
			glClear(GL_DEPTH_BUFFER_BIT | GL_COLOR_BUFFER_BIT);
			
			glLoadIdentity();
			
			render();
			
			DialogManager.paintDefault();
			DialogManager.dispatchEventsDefault();
			
			Display.update();
		}
		
		Display.destroy();
	}
	
	public abstract void render();
}
