package test;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;

import static org.lwjgl.opengl.GL11.*;

public class StandardTest {
	public StandardTest() {
		try {
			Display.create();
			
			renderLoop();
			
			Display.destroy();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}
	
	public final void renderLoop() {
		while(!Display.isCloseRequested()) {
			glClear(GL_DEPTH_BUFFER_BIT | GL_COLOR_BUFFER_BIT);
			
			glLoadIdentity();
			
			render();
			
			
			
			Display.update();
		}
	}
}
