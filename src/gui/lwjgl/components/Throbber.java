package gui.lwjgl.components;

import static org.lwjgl.opengl.GL11.*;

import gui.lwjgl.style.StyleTemplate;
import gui.lwjgl.util.Texture;

public class Throbber extends Component {
	private double rotation;
	
	private double rotationAmplifier = 1;
	
	private boolean paused;

	public Throbber(int centerX, int centerY, int sizeX, int sizeY) {
		super(centerX, centerY, sizeX, sizeY);
		
		addToGroup("throbbers");
	}
	
	public Throbber(int centerX, int centerY, int sizeX, int sizeY, Texture tex) {
		this(centerX, centerY, sizeX, sizeY);
		
		setTexture(tex);
	}

	@Override
	public void paint(float delta) {
		glPushMatrix();
		glTranslatef(centerX, centerY, 0);
		
		super.bindTexture();
		
		if(!paused)
			rotation += delta * rotationAmplifier * 10;

		glRotated(rotation, 0, 0, 1);
		
		glTranslatef(-sizeX/2, -sizeY/2, 0);
		
		glBegin(GL_QUADS);
		glTexCoord2f(0, 0);
		glColor3f(1, 1, 1);
		glVertex2f(0, 0);
		glTexCoord2f(0, 1);
		glVertex2f(0, sizeY);
		glTexCoord2f(1, 1);
		glVertex2f(sizeX, sizeY);
		glTexCoord2f(1, 0);
		glVertex2f(sizeX, 0);
		glEnd();
		
		glBindTexture(GL_TEXTURE_2D, 0);
		
		super.paintBorder();
		glPopMatrix();
	}
	
	public void setPaused(boolean paused) {
		this.paused = paused;
	}
	
	public void setRotationSpeed(double rotationAmplifier) {
		this.rotationAmplifier = rotationAmplifier;
	}

	public boolean isPaused() {
		return paused;
	}

	@Override
	public void loadTemplate(StyleTemplate style) {
		style.load(this);
	}
}
